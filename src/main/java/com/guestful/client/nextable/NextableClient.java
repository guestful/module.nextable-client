/**
 * Copyright (C) 2013 Guestful (info@guestful.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.guestful.client.nextable;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class NextableClient {

    private static final Logger LOGGER = Logger.getLogger(NextableClient.class.getName());
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private final String token;
    private final WebTarget target;
    private final Client client;

    private boolean enabled = true;

    public NextableClient(String token) {
        this(ClientBuilder.newClient(), token);
    }

    public NextableClient(Client client, String token) {
        this.token = token;
        this.client = client;
        this.target = buildWebTarget();
    }

    public String getToken() {
        return token;
    }

    public Client getClient() {
        return client;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    protected WebTarget buildWebTarget() {
        return getClient().target("http://services.nextable.com/api/v1");
    }

    public NextableRestaurant getRestaurant(String restaurantId) {
        if (restaurantId == null) throw new NullPointerException();
        JsonObject body = request(HttpMethod.GET, "restaurants/" + restaurantId).readEntity(JsonObject.class);
        NextableRestaurant restaurant = new NextableRestaurant(restaurantId, ZoneId.of(body.getString("timeZone")))
            .setName(body.getString("restaurantName"))
            .setGranularity(Duration.ofMinutes(body.getInt("timeSlotSpan")))
            .setDescription(body.getString("description", null))
            .setPhoneNumber(body.getString("businessPhone", null))
            .setAddress(body.getString("address", null))
            .setCity(body.getString("city", null))
            .setStateCode(body.getString("state", null))
            .setPostalCode(body.getString("zip", null))
            .setCountryCode(body.getString("country", null))
            .setPricingScale(body.getString("price", "").length() / 4);
        for (int i = 1; body.containsKey("cuisine" + i); i++) {
            String c = body.getString("cuisine" + i, null);
            if(c != null && c.length() > 0) {
                restaurant.addCuisine(c);
            }
        }
        return restaurant;
    }

    public NextableReservation getReservation(String reservationId, String restaurantId) {
        if (reservationId == null) throw new NullPointerException();
        if (restaurantId == null) throw new NullPointerException();
        MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
        queryParams.putSingle("reservationId", reservationId);
        queryParams.putSingle("restaurantId", restaurantId);
        JsonObject body = request(HttpMethod.GET, "reservations", queryParams).readEntity(JsonObject.class);
        return new NextableReservation()
            .setId(reservationId)
            .setRestaurantId(restaurantId)
            .setFirstName(body.getString("firstName", null))
            .setLastName(body.getString("lastName", null))
            .setEmail(body.getString("email", null))
            .setPhoneNumber(body.getString("phone", null))
            .setPhoneCode(body.getString("phoneCode", null))
            .setStart(LocalDateTime.parse(body.getString("dateAndTime")))
            .setPartySize(body.getInt("partySize"))
            .setSpecialRequest(body.getString("note", null));
    }

    public NextableReservation createReservation(NextableReservation reservation) {
        JsonObject response = request(HttpMethod.POST, "reservations", reservation.toJson()).readEntity(JsonObject.class);
        JsonObject reservationPlaced = response.getJsonObject("result");
        String id = reservationPlaced.getString("key");
        reservation.setId(id);
        return reservation;
    }

    public void cancelReservation(NextableReservation reservation) {
        cancelReservation(reservation.getId(), reservation.getRestaurantId());
    }

    public void cancelReservation(String reservationId, String restaurantId) {
        if (reservationId == null) throw new NullPointerException();
        if (restaurantId == null) throw new NullPointerException();
        MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
        queryParams.putSingle("reservationId", reservationId);
        queryParams.putSingle("restaurantId", restaurantId);
        try {
            request(HttpMethod.DELETE, "reservations", queryParams);
        } catch (NextableException e) {
            if(!(e.getStatusType().getStatusCode() == 40 && e.getResponse() != null && e.getResponse().getString("message", "").equals("Reservation already cancelled"))) {
                throw e;
            }
        }
    }

    public NextableReservation editReservation(NextableReservation reservation) {
        if (reservation.getId() == null) throw new NullPointerException();
        if (reservation.getRestaurantId() == null) throw new NullPointerException();
        JsonObject response = request(HttpMethod.PUT, "reservations", reservation.toJson()).readEntity(JsonObject.class);
        JsonObject body = response.getJsonObject("result");
        return new NextableReservation()
            .setId(body.getString("key"))
            .setRestaurantId(body.getString("restaurantId"))
            .setFirstName(body.getString("firstName", null))
            .setLastName(body.getString("lastName", null))
            .setEmail(body.getString("email", null))
            .setPhoneNumber(body.getString("phone", null))
            .setPhoneCode(body.getString("phoneCode", null))
            .setStart(LocalDateTime.parse(body.getString("dateAndTime")))
            .setPartySize(body.getInt("partySize"))
            .setSpecialRequest(body.getString("note", null));
    }

    public List<NextableRestaurant> searchAvailabilities(NextableAvailabilitySearchCriteria criteria) throws NextableException {
        MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<>();
        for (String id : criteria.getRestaurantIds()) {
            queryParams.add("restaurantIds", id);
        }
        queryParams.putSingle("includeClosed", false);
        queryParams.putSingle("page", criteria.getPage());
        queryParams.putSingle("pageSize", criteria.getPageSize());
        if (criteria.getPartySize() > 0) {
            queryParams.putSingle("partySize", criteria.getPartySize());
        }
        if (criteria.getFrom() != null) {
            queryParams.putSingle("beginDateTime", criteria.getFrom().format(DATE_TIME_FORMATTER));
        }
        if (criteria.getTo() != null) {
            queryParams.putSingle("endDateTime", criteria.getTo().format(DATE_TIME_FORMATTER));
        }
        JsonArray in_list = request(HttpMethod.GET, "reservations/availability", queryParams).readEntity(JsonArray.class);
        List<NextableRestaurant> restaurants = new ArrayList<>(in_list.size());
        for (int i = 0; i < in_list.size(); i++) {
            JsonObject in_resto = in_list.getJsonObject(i);
            JsonArray in_avail = in_resto.getJsonArray("availability");
            if (in_avail != null && !in_avail.isEmpty()) {
                NextableRestaurant nextableRestaurant = new NextableRestaurant(in_resto.getString("id"), ZoneId.of(in_resto.getString("timeZone")))
                    .setGranularity(Duration.ofMinutes(in_resto.getInt("slotSize")));
                for (int j = 0; j < in_avail.size(); j++) {
                    JsonObject in_day = in_avail.getJsonObject(j);
                    JsonArray in_slots = in_day.getJsonArray("slots");
                    if (in_slots != null && !in_slots.isEmpty()) {
                        NextableDailyAvailability availability = nextableRestaurant.addAvailability(LocalDate.parse(in_day.getString("date").substring(0, 10)));
                        NextableTimeSlot previous = null;
                        for (int k = 0; k < in_slots.size(); k++) {
                            JsonObject in_slot = in_slots.getJsonObject(k);
                            JsonArray in_partySizes = in_slot.getJsonArray("partySizes");
                            if (in_partySizes != null && !in_partySizes.isEmpty()) {
                                NextableTimeSlot slot = availability.addTimeSlot(LocalTime.parse(in_slot.getString("time24")));
                                for (int p = 0; p < in_partySizes.size(); p++) {
                                    slot.addPartySize(in_partySizes.getJsonObject(p).getInt("size"));
                                }
                                if (previous != null && previous.getEndTime().equals(slot.getStartTime()) && previous.hasSameProperties(slot)) {
                                    previous.expand();
                                    slot.remove();
                                } else {
                                    previous = slot;
                                }
                            } else {
                                previous = null;
                            }
                        }
                        if (!availability.hasAvailability()) {
                            availability.remove();
                        }
                    }
                }
                if (nextableRestaurant.hasAvailability()) {
                    restaurants.add(nextableRestaurant);
                }
            }
        }
        return restaurants;
    }

    Response request(String method, String path) throws NextableException {
        return request(method, path, new MultivaluedHashMap<>());
    }

    Response request(String method, String path, MultivaluedMap<String, Object> queryParams) throws NextableException {
        return request(method, path, null, queryParams);
    }

    Response request(String method, String path, JsonObject body) throws NextableException {
        return request(method, path, body, new MultivaluedHashMap<>());
    }

    Response request(String method, String path, JsonObject body, MultivaluedMap<String, Object> queryParams) throws NextableException {
        WebTarget t = target.path(path);
        for (Map.Entry<String, List<Object>> entry : queryParams.entrySet()) {
            t = t.queryParam(entry.getKey(), entry.getValue().toArray(new Object[entry.getValue().size()]));
        }
        // add api key
        if (HttpMethod.GET.equals(method) || HttpMethod.DELETE.equals(method)) {
            t = t.queryParam("apiKey", getToken());
        } else {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            if (body != null) body.forEach(builder::add);
            builder.add("apiKey", getToken());
            body = builder.build();
        }
        if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.finest(method + " " + t.getUri() + (body == null ? "" : (" : " + body)));
        }
        if (!isEnabled()) {
            return Response.ok().build();
        } else {
            Invocation.Builder b = t.request(MediaType.APPLICATION_JSON_TYPE);
            // {{-- only for testing with a tunnel
            //System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
            //b = b.header("Host", "services-dev.nextable.com");
            // --}}
            Response response = body != null ? b.method(method, Entity.entity(body, "application/json; charset=utf-8")) : b.method(method);
            if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                throw new NextableException(response, body);
            }
            return response;
        }
    }

}
