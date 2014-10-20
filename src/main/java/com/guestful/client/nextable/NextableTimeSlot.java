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
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class NextableTimeSlot {

    final NextableDailyAvailability availability;
    final List<Integer> partySizes = new ArrayList<>();

    private ZonedDateTime start;
    private Duration duration;

    NextableTimeSlot(NextableDailyAvailability availability, LocalTime startTime) {
        this.availability = availability;
        this.start = ZonedDateTime.of(availability.day, startTime, availability.restaurant.timeZone);
        this.duration = availability.getRestaurant().getGranularity();
    }

    public NextableDailyAvailability getAvailability() {
        return availability;
    }

    public LocalTime getStartTime() {
        return start.toLocalTime();
    }

    public NextableTimeSlot setStartTime(LocalTime startTime) {
        this.start = ZonedDateTime.of(availability.day, startTime, availability.restaurant.timeZone);
        return this;
    }

    public LocalTime getEndTime() {
        return getEnd().toLocalTime();
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return getStart().plus(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public List<Integer> getPartySizes() {
        return partySizes;
    }

    public int getMinPartySize() {
        return partySizes.get(0);
    }

    public int getMaxPartySize() {
        return partySizes.get(partySizes.size() - 1);
    }

    public void addPartySize(int n) {
        if (!partySizes.contains(n)) {
            partySizes.add(n);
            Collections.sort(partySizes);
        }
    }

    public NextableTimeSlot expand() {
        this.duration = this.duration.plus(availability.getRestaurant().getGranularity());
        return this;
    }

    public boolean hasSameProperties(NextableTimeSlot slot) {
        return partySizes.equals(slot.partySizes);
    }

    public void remove() {
        availability.timeSlots.remove(this);
    }

    public JsonObject toJson() {
        JsonArrayBuilder sizes = Json.createArrayBuilder();
        partySizes.forEach(sizes::add);
        return Json.createObjectBuilder()
            .add("start", getStartTime().toString())
            .add("duration", duration.toMinutes())
            .add("end", getEndTime().toString())
            .add("partySizes", sizes)
            .build();
    }

    @Override
    public String toString() {
        return toJson().toString();
    }

    public NextableTimeSlot copy() {
        NextableTimeSlot ts = new NextableTimeSlot(availability, getStartTime());
        ts.partySizes.addAll(this.partySizes);
        ts.duration = this.duration;
        return ts;
    }

}
