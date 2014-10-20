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
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.time.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class NextableRestaurant {

    final String id;
    final ZoneId timeZone;
    private List<NextableDailyAvailability> availabilities;

    private Duration granularity = Duration.ofMinutes(15);
    private String name;
    private String description;
    private String phoneNumber;
    private String countryCode;
    private String address;
    private String city;
    private String stateCode;
    private String postalCode;
    private double pricingScale;
    private Collection<String> cuisines;

    public NextableRestaurant(String id, ZoneId timeZone) {
        this.id = id;
        this.timeZone = timeZone;
    }

    public String getName() {
        return name;
    }

    public NextableRestaurant setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public NextableRestaurant setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public NextableRestaurant setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public NextableRestaurant setCountryCode(String countryCode) {
        if (countryCode != null) {
            if (countryCode.length() != 2) {
                this.countryCode = ISO.iso_3166_1_alpha_3_toalpha_2(countryCode);
            } else {
                this.countryCode = countryCode.toUpperCase();
            }
        } else {
            this.countryCode = null;
        }
        return this;
    }

    public String getAddress() {
        return address;
    }

    public NextableRestaurant setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public NextableRestaurant setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStateCode() {
        return stateCode;
    }

    public NextableRestaurant setStateCode(String stateCode) {
        if(stateCode != null && stateCode.length() == 2) {
            this.stateCode = stateCode.toUpperCase();
        } else {
            this.stateCode = null;
        }
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public NextableRestaurant setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public double getPricingScale() {
        return pricingScale;
    }

    public NextableRestaurant setPricingScale(double pricingScale) {
        this.pricingScale = pricingScale;
        return this;
    }

    public Collection<String> getCuisines() {
        return cuisines;
    }

    public NextableRestaurant setCuisines(Collection<String> cuisines) {
        this.cuisines = cuisines;
        return this;
    }

    public NextableRestaurant addCuisine(String cuisine) {
        if (cuisines == null) cuisines = new ArrayList<>();
        this.cuisines.add(cuisine);
        return this;
    }

    public List<NextableDailyAvailability> getAvailabilities() {
        return availabilities == null ? Collections.emptyList() : availabilities;
    }

    public String getId() {
        return id;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public Duration getGranularity() {
        return granularity;
    }

    public NextableRestaurant setGranularity(Duration granularity) {
        if (granularity.toMinutes() % 15 != 0) {
            throw new IllegalArgumentException("Bad granularity: " + granularity);
        }
        this.granularity = granularity;
        return this;
    }

    public NextableDailyAvailability addAvailability(LocalDate day) {
        if (availabilities == null) availabilities = new ArrayList<>();
        NextableDailyAvailability availability = new NextableDailyAvailability(this, day);
        availabilities.add(availability);
        return availability;
    }

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder()
            .add("id", id)
            .add("granularity", granularity.toMinutes())
            .add("timeZone", timeZone.getId())
            .add("pricingScale", pricingScale);

        if (availabilities != null) {
            JsonArrayBuilder availabilities = Json.createArrayBuilder();
            this.availabilities.stream().map(NextableDailyAvailability::toJson).forEach(availabilities::add);
            builder.add("availabilities", availabilities);
        }

        if (cuisines != null) {
            JsonArrayBuilder cuisines = Json.createArrayBuilder();
            this.cuisines.forEach(cuisines::add);
            builder.add("cuisines", cuisines);
        }

        if (name != null) builder.add("name", name);
        if (description != null) builder.add("description", description);
        if (phoneNumber != null) builder.add("phoneNumber", phoneNumber);
        if (address != null) builder.add("address", address);
        if (city != null) builder.add("city", city);
        if (stateCode != null) builder.add("stateCode", stateCode);
        if (postalCode != null) builder.add("postalCode", postalCode);
        if (countryCode != null) builder.add("countryCode", countryCode);

        return builder.build();
    }

    @Override
    public String toString() {
        return toJson().toString();
    }

    public boolean hasAvailability() {
        return availabilities.stream().filter(NextableDailyAvailability::hasAvailability).findFirst().isPresent();
    }

    public boolean hasAvailability(final LocalDateTime dateTime, int partySize) {
        ZonedDateTime zdt = ZonedDateTime.of(dateTime, getTimeZone());
        final LocalDate day = dateTime.toLocalDate();
        return availabilities.stream()
            .filter(a -> a.getDay().equals(day))
            .flatMap(a -> a.getTimeSlots().stream())
            .filter(t -> t.getPartySizes().contains(partySize) && (zdt.equals(t.getStart()) || zdt.isAfter(t.getStart()) && zdt.isBefore(t.getEnd())))
            .findFirst()
            .isPresent();
    }

    void removeAvailabilities(NextableDailyAvailability availability) {
        if (availabilities != null) availabilities.remove(availability);
    }
}
