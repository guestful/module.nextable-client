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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class NextableDailyAvailability {

    final NextableRestaurant restaurant;
    final LocalDate day;
    final List<NextableTimeSlot> timeSlots = new ArrayList<>();

    NextableDailyAvailability(NextableRestaurant restaurant, LocalDate day) {
        this.restaurant = restaurant;
        this.day = day;
    }

    public NextableRestaurant getRestaurant() {
        return restaurant;
    }

    public LocalDate getDay() {
        return day;
    }

    public List<NextableTimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public NextableTimeSlot addTimeSlot(LocalTime time) {
        NextableTimeSlot slot = new NextableTimeSlot(this, time);
        timeSlots.add(slot);
        return slot;
    }

    public JsonObject toJson() {
        JsonArrayBuilder slots = Json.createArrayBuilder();
        for (NextableTimeSlot timeSlot : timeSlots) {
            slots.add(timeSlot.toJson());
        }
        return Json.createObjectBuilder()
            .add("day", day.toString())
            .add("timeSlots", slots)
            .build();
    }

    @Override
    public String toString() {
        return toJson().toString();
    }

    public boolean hasAvailability() {
        return !timeSlots.isEmpty();
    }

    public void remove() {
        restaurant.removeAvailabilities(this);
    }
}
