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
package org.openhab.binding.lightwaverf.internal.command;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.openhab.binding.lightwaverf.internal.LightwaveRfType;
import org.openhab.binding.lightwaverf.internal.exception.LightwaveRfMessageException;
import org.openhab.core.library.types.DateTimeType;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.types.State;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A message received from the Energy Monitor. On
 * the LAN the messages look like: *!{"trans":215955,"mac":"03:41:C4","time":1435620183,
 * "prod":"pwrMtr","serial":"9470FE","signal":79,"type":"energy",
 * "cUse":271,"maxUse":2812,"todUse":8414,"yesUse":8377}
 *
 * @author Neil Renaud
 * @since 1.7.0
 */
public class LightwaveRfEnergyMonitorMessage extends AbstractLightwaveRfJsonMessage
        implements LightwaveRfSerialMessage {
    private static final Logger logger = LoggerFactory.getLogger(LightwaveRfEnergyMonitorMessage.class);

    private static final Pattern MAC_ID_REG_EXP = Pattern.compile(".*\"mac\":\"([^\"}]*)\".*");
    private static final Pattern TIME_ID_REG_EXP = Pattern.compile(".*\"time\":([^,}]*).*");
    private static final Pattern PROD_REG_EXP = Pattern.compile(".*\"prod\":\"([^\"}]*)\".*");
    private static final Pattern SERIAL_ID_REG_EXP = Pattern.compile(".*\"serial\":\"([^\"}]*)\".*");
    private static final Pattern SIGNAL_REG_EXP = Pattern.compile(".*\"signal\":([^,}]*).*");
    private static final Pattern TYPE_REG_EXP = Pattern.compile(".*\"type\":\"([^\"}]*)\".*");
    private static final Pattern CURRENT_USE_REG_EXP = Pattern.compile(".*\"cUse\":([^,}]*).*");
    private static final Pattern MAX_USE_REG_EXP = Pattern.compile(".*\"maxUse\":([^,}]*).*");
    private static final Pattern TODAY_USE_REG_EXP = Pattern.compile(".*\"todUse\":([^,}]*).*");
    private static final Pattern YESTERDAY_USE_REG_EXP = Pattern.compile(".*\"yesUse\":([^,}]*).*");

    private String mac;
    private Date time;
    private String prod;
    private String serial;
    private int signal;
    private String type;
    private int cUse;
    private int maxUse;
    private int todUse;
    private int yesUse;

    public LightwaveRfEnergyMonitorMessage(String message) throws LightwaveRfMessageException {
        super(message);
        try {
            mac = getStringFromText(MAC_ID_REG_EXP, message);
        } catch(LightwaveRfMessageException le) {
            logger.debug("Failed to get a mac attribute from the message '{}'", message);
        }

        try {
            time = getDateFromText(TIME_ID_REG_EXP, message);
        } catch(LightwaveRfMessageException le) {
            logger.debug("Failed to get a time attribute from the message '{}'", message);
        }

        try {
            prod = getStringFromText(PROD_REG_EXP, message);
        } catch(LightwaveRfMessageException le) {
            logger.debug("Failed to get a prod attribute from the message '{}'", message);
        }

        try {
            serial = getStringFromText(SERIAL_ID_REG_EXP, message);
        } catch(LightwaveRfMessageException le) {
            logger.debug("Failed to get a serial attribute from the message '{}'", message);
        }

        try {
            signal = getIntFromText(SIGNAL_REG_EXP, message);
        } catch(LightwaveRfMessageException le) {
            logger.debug("Failed to get a signal attribute from the message '{}'", message);
        }

        try {
            type = getStringFromText(TYPE_REG_EXP, message);
        } catch(LightwaveRfMessageException le) {
            logger.debug("Failed to get a type attribute from the message '{}'", message);
        }

        try {
            cUse = getIntFromText(CURRENT_USE_REG_EXP, message);
        } catch(LightwaveRfMessageException le) {
            logger.debug("Failed to get a cUse attribute from the message '{}'", message);
        }

        try {
            maxUse = getIntFromText(MAX_USE_REG_EXP, message);
        } catch(LightwaveRfMessageException le) {
            logger.debug("Failed to get a maxUse attribute from the message '{}'", message);
        }

        try {
            todUse = getIntFromText(TODAY_USE_REG_EXP, message);
        } catch(LightwaveRfMessageException le) {
            logger.debug("Failed to get a todUse attribute from the message '{}'", message);
        }

        try {
            yesUse = getIntFromText(YESTERDAY_USE_REG_EXP, message);
        } catch(LightwaveRfMessageException le) {
            logger.debug("Failed to get a yesUse attribute from the message '{}'", message);
        }
    }

    @Override
    public String getLightwaveRfCommandString() {
        return new StringBuilder("*!{").append("\"trans\":").append(getMessageId().getMessageIdString())
                .append(",\"mac\":\"").append(mac).append("\",\"time\":").append(time.getTime()).append(",\"prod\":\"")
                .append(prod).append("\",\"serial\":\"").append(serial).append("\",\"signal\":").append(signal)
                .append(",\"type\":\"").append(type).append(",\"cUse\":\"").append(type).append(",\"maxUse\":\"")
                .append(type).append(",\"todUse\":\"").append(type).append(",\"yesUse\":\"").append(type).append("}")
                .toString();
    }

    @Override
    public State getState(LightwaveRfType type) {
        switch (type) {
            case SIGNAL:
                return new DecimalType(getSignal());
            case ENERGY_CURRENT_USAGE:
                return new DecimalType(getcUse());
            case ENERGY_MAX_USAGE:
                return new DecimalType(getMaxUse());
            case ENERGY_TODAY_USAGE:
                return new DecimalType(getTodUse());
            case ENERGY_YESTERDAY_USAGE:
                return new DecimalType(getYesUse());
            case UPDATETIME:
                Calendar cal = Calendar.getInstance();
                // The date seems to be in a strange timezone so at the moment we
                // use server date.
                // cal.setTime(getTime());
                return new DateTimeType(cal);
            default:
                return null;
        }
    }

    public static boolean matches(String message) {
        if (message.contains("pwrMtr")) {
            return true;
        }
        return false;
    }

    @Override
    public LightwaveRfMessageType getMessageType() {
        return LightwaveRfMessageType.SERIAL;
    }

    @Override
    public String getSerial() {
        return serial;
    }

    public String getMac() {
        return mac;
    }

    public int getMaxUse() {
        return maxUse;
    }

    public String getProd() {
        return prod;
    }

    public int getSignal() {
        return signal;
    }

    public Date getTime() {
        return time;
    }

    public int getTodUse() {
        return todUse;
    }

    public String getType() {
        return type;
    }

    public int getYesUse() {
        return yesUse;
    }

    public int getcUse() {
        return cUse;
    }
}
