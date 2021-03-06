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
package org.openhab.binding.tinkerforge.internal.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TF Interrupt Listener Configuration</b></em>'.
 *
 * @author Theo Weiss
 * @since 1.4.0
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.openhab.binding.tinkerforge.internal.model.TFInterruptListenerConfiguration#getDebouncePeriod
 *        <em>Debounce Period</em>}</li>
 *        </ul>
 *
 * @see org.openhab.binding.tinkerforge.internal.model.ModelPackage#getTFInterruptListenerConfiguration()
 * @model
 * @generated
 */
public interface TFInterruptListenerConfiguration extends TFConfig {

    /**
     * Returns the value of the '<em><b>Debounce Period</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Debounce Period</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Debounce Period</em>' attribute.
     * @see #setDebouncePeriod(long)
     * @see org.openhab.binding.tinkerforge.internal.model.ModelPackage#getTFInterruptListenerConfiguration_DebouncePeriod()
     * @model unique="false"
     * @generated
     */
    long getDebouncePeriod();

    /**
     * Sets the value of the
     * '{@link org.openhab.binding.tinkerforge.internal.model.TFInterruptListenerConfiguration#getDebouncePeriod
     * <em>Debounce Period</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Debounce Period</em>' attribute.
     * @see #getDebouncePeriod()
     * @generated
     */
    void setDebouncePeriod(long value);
} // TFInterruptListenerConfiguration
