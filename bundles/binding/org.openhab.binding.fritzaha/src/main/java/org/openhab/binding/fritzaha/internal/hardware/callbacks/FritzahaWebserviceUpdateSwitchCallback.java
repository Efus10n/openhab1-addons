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
package org.openhab.binding.fritzaha.internal.hardware.callbacks;

import org.openhab.binding.fritzaha.internal.hardware.FritzahaWebInterface;
import org.openhab.core.library.types.OnOffType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Callback implementation for updating switch states Supports reauthorization
 *
 * @author Christian Brauers
 * @since 1.3.0
 */
public class FritzahaWebserviceUpdateSwitchCallback extends FritzahaReauthCallback {
    static final Logger logger = LoggerFactory.getLogger(FritzahaWebserviceUpdateSwitchCallback.class);
    /**
     * Item to update
     */
    private String itemName;

    /**
     * Constructor for retriable authentication and state updating
     * 
     * @param path
     *            Path to HTTP interface
     * @param args
     *            Arguments to use
     * @param webIface
     *            Web interface to use
     * @param httpMethod
     *            Method used
     * @param retries
     *            Number of retries
     * @param itemName
     *            Name of item to update
     */
    public FritzahaWebserviceUpdateSwitchCallback(String path, String args, FritzahaWebInterface webIface,
            Method httpMethod, int retries, String itemName) {
        super(path, args, webIface, httpMethod, retries);
        this.itemName = itemName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(int status, String response) {
        super.execute(status, response);
        if (validRequest) {
            logger.debug("Received State response " + response + " for item " + itemName);
            webIface.postUpdate(itemName, "1".equals(response.trim()) ? OnOffType.ON : OnOffType.OFF);
        }
    }

}
