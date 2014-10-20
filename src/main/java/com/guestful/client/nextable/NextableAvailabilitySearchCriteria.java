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

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class NextableAvailabilitySearchCriteria {

    private final Set<String> restaurantIds = new TreeSet<>();
    private LocalDateTime from;
    private LocalDateTime to;
    private int partySize;
    private int page = 0;
    private int pageSize = 365;

    public NextableAvailabilitySearchCriteria addRestaurantId(String id) {
        this.restaurantIds.add(id);
        return this;
    }

    public Collection<String> getRestaurantIds() {
        return restaurantIds;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public NextableAvailabilitySearchCriteria setFrom(LocalDateTime from) {
        this.from = from;
        return this;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public NextableAvailabilitySearchCriteria setTo(LocalDateTime to) {
        this.to = to;
        return this;
    }

    public int getPartySize() {
        return partySize;
    }

    public NextableAvailabilitySearchCriteria setPartySize(int partySize) {
        this.partySize = partySize;
        return this;
    }

    public int getPage() {
        return page;
    }

    public NextableAvailabilitySearchCriteria setPage(int page) {
        this.page = page;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public NextableAvailabilitySearchCriteria setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public String toString() {
        return "NextableAvailabilitySearchCriteria{" +
            "restaurantIds=" + restaurantIds +
            ", from=" + from +
            ", to=" + to +
            ", partySize=" + partySize +
            ", page=" + page +
            ", pageSize=" + pageSize +
            '}';
    }
}
