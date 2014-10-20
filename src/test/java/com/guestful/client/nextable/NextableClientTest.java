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

import org.glassfish.jersey.client.filter.EncodingFilter;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.message.GZipEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.logging.LogManager;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
@RunWith(JUnit4.class)
public class NextableClientTest {

    @Test
    public void test() {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
        LoggerFactory.getILoggerFactory();

        Client restClient = ClientBuilder.newBuilder().build();
        restClient
            .register(JsonProcessingFeature.class)
            .register(GZipEncoder.class)
            .register(EncodingFilter.class)
        ;

        ZoneId zoneId = ZoneId.of("America/Montreal");

        NextableClient nextable = new NextableClient(restClient, System.getenv("NEXTABLE_TOKEN")) {
            @Override
            protected WebTarget buildWebTarget() {
                return getClient().target("http://services-dev.nextable.com/api/v1");
                //return getClient().target("http://127.0.0.1:4444/api/v1");
            }
        };

        /*List<NextableRestaurant> nextableRestaurants = nextable.searchAvailabilities(new NextableAvailabilitySearchCriteria()
                .addRestaurantId("17856R898")
                .setPartySize(2)
                .setFrom(LocalDateTime.parse("2014-08-29T15:30:00"))
                .setTo(LocalDateTime.parse("2014-08-29T17:30:00"))
        );
        assertFalse(nextableRestaurants.isEmpty());
        NextableRestaurant nextableRestaurant = nextableRestaurants.get(0);
        assertTrue(nextableRestaurant.hasAvailability(LocalDateTime.parse("2014-08-29T16:30:00"), 2));*/

        System.out.println("resto-CRD9H5807\n" + nextable.getRestaurant("CRD9H5807"));
        System.out.println("resto-17856R898\n" + nextable.getRestaurant("17856R898"));

        List<NextableRestaurant> restaurants = nextable.searchAvailabilities(new NextableAvailabilitySearchCriteria()
                .setFrom(LocalDateTime.now(zoneId))
                .setTo(LocalDateTime.now(zoneId).plusDays(7))
                .addRestaurantId("CRD9H5807")
                .addRestaurantId("17856R898")
        );
        System.out.println("availablities\n" + restaurants);

        NextableTimeSlot slot = restaurants.get(0).getAvailabilities().get(0).getTimeSlots().get(0);
        System.out.println("slot\n" + slot);

        String restaurantId = slot.getAvailability().getRestaurant().getId();
        System.out.println("restaurantId\n" + restaurantId);
        NextableReservation reservation = nextable.createReservation(new NextableReservation()
                .setRestaurantId(restaurantId)
                .setEmail("guestful-ci@guestful.com")
                .setFirstName("Éric")
                .setLastName("é-à")
                .setPartySize(slot.getPartySizes().get(slot.getPartySizes().size() - 1))
                .setPhoneCode("33")
                .setPhoneNumber("0478509846")
                .setSpecialRequest("Accents testés")
                .setStart(slot.getStart().toLocalDateTime())
        );
        System.out.println("created\n" + reservation);

        System.out.println("get\n" + nextable.getReservation(reservation.getId(), reservation.getRestaurantId()));

        reservation
            .setFirstName("Toto")
            .setPhoneNumber("5146604287");

        System.out.println("edited\n" + nextable.editReservation(reservation));

        nextable.cancelReservation(reservation);
    }

}
