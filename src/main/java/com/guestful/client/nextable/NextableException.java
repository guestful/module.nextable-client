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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class NextableException extends RuntimeException {

    private final Response.StatusType statusType;
    private final JsonObject response;
    private final JsonObject request;
    private final String nexTableTrxId;

    NextableException(Response response, JsonObject request) {
        this(response.getStatusInfo(),
            MediaType.APPLICATION_JSON_TYPE.isCompatible(response.getMediaType()) ? response.readEntity(JsonObject.class) : Json.createObjectBuilder().add("error", response.readEntity(String.class)).build(),
            request,
            response.getHeaderString("NexTableTrxId"));
    }

    private NextableException(Response.StatusType statusType, JsonObject response, JsonObject request, String nexTableTrxId) {
        super("Nextable request error. " + statusType.getStatusCode() + " " + statusType.getReasonPhrase() + ". NexTableTrxId: " + nexTableTrxId + "\n" + response + (request == null ? "" : "\nRequest:\n" + request));
        this.statusType = statusType;
        this.response = response;
        this.request = request;
        this.nexTableTrxId = nexTableTrxId;
    }

    public String getNexTableTrxId() {
        return nexTableTrxId;
    }

    @Override
    public String getMessage() {
        if (statusType.getStatusCode() >= 500 && response.containsKey("exceptionMessage")) {
            return "Nextable request error. " + statusType.getStatusCode() + " " + statusType.getReasonPhrase() + ". NexTableTrxId: " + nexTableTrxId + "\n" + response.getString("exceptionType", "") + ": " + response.getString("exceptionMessage") + "\n" + response.getString("stackTrace", "") + (request == null ? "" : "\nRequest:\n" + request);
        }
        return "Nextable request error. " + statusType.getStatusCode() + " " + statusType.getReasonPhrase() + ". NexTableTrxId: " + nexTableTrxId + "\n" + response + (request == null ? "" : "\nRequest:\n" + request);
    }

    public Response.StatusType getStatusType() {
        return statusType;
    }

    public JsonObject getResponse() {
        return response;
    }

    public JsonObject getRequest() {
        return request;
    }

}
