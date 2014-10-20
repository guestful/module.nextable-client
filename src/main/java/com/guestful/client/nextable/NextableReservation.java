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
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.time.LocalDateTime;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class NextableReservation {

    private String id;
    private String restaurantId;

    private LocalDateTime start;
    private int partySize;
    private String specialRequest;

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String phoneCode;

    public String getId() {
        return id;
    }

    public NextableReservation setId(String id) {
        this.id = id;
        return this;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public NextableReservation setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public NextableReservation setStart(LocalDateTime start) {
        this.start = start;
        return this;
    }

    public int getPartySize() {
        return partySize;
    }

    public NextableReservation setPartySize(int partySize) {
        this.partySize = partySize;
        return this;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public NextableReservation setSpecialRequest(String specialRequest) {
        specialRequest = cut(specialRequest, 500);
        this.specialRequest = specialRequest;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public NextableReservation setEmail(String email) {
        email = cut(email, 255);
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public NextableReservation setFirstName(String firstName) {
        firstName = cut(firstName, 50);
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public NextableReservation setLastName(String lastName) {
        lastName = cut(lastName, 50);
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public NextableReservation setPhoneNumber(String phoneNumber) {
        phoneNumber = cut(phoneNumber, 10);
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public NextableReservation setPhoneCode(String phoneCode) {
        phoneCode = cut(phoneCode, 5);
        this.phoneCode = phoneCode;
        return this;
    }

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        if (start != null) builder.add("dateAndTime", start.format(NextableClient.DATE_TIME_FORMATTER));
        if (email != null) builder.add("email", email);
        if (firstName != null) builder.add("firstName", firstName);
        if (lastName != null) builder.add("lastName", lastName);
        if (phoneNumber != null) builder.add("phone", phoneNumber);
        if (phoneCode != null) builder.add("phoneCode", phoneCode);
        if (partySize > 0) builder.add("partySize", partySize);
        if (restaurantId != null) builder.add("restaurantId", restaurantId);
        if (specialRequest != null) builder.add("note", specialRequest);
        if (id != null) builder.add("reservationId", id);
        return builder.build();
    }

    @Override
    public String toString() {
        return toJson().toString();
    }

    private static String cut(String s, int len) {
        return s == null ? null : s.substring(0, Math.min(len, s.length()));
    }
}
