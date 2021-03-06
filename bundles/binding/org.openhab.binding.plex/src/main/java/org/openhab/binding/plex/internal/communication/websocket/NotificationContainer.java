/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.plex.internal.communication.websocket;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * This object holds updates received from the Plex websocket connection for api level 2
 *
 * @author Jeroen Idserda
 * @since 1.9.0
 */
@JsonRootName(value = "NotificationContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationContainer {

    private String type;

    private Integer size;

    @JsonProperty(value = "PlaySessionStateNotification")
    private List<PlaySessionStateNotification> stateNotifications = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<PlaySessionStateNotification> getStateNotifications() {
        return stateNotifications;
    }

    public void setStateNotifications(List<PlaySessionStateNotification> stateNotifications) {
        this.stateNotifications = stateNotifications;
    }

}
