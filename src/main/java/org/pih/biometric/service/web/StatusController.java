/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.pih.biometric.service.web;

import org.pih.biometric.service.api.BiometricMatchingEngine;
import org.pih.biometric.service.model.BiometricsConfig;
import org.pih.biometric.service.model.BiometricsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides web services for system status
 */
@RestController
@CrossOrigin
public class StatusController {

    @Autowired
    BiometricsConfig config;

    @Autowired
    BiometricMatchingEngine engine;

    /**
     * @return the status of the system.
     */
    @RequestMapping("/status")
    @ResponseBody
    public BiometricsStatus status() {
        BiometricsStatus status = new BiometricsStatus();
        try {
            Integer numEnrolled = engine.getNumberEnrolled();
            status.setNumberEnrolled(numEnrolled);
            status.setStatus(BiometricsStatus.AVAILABLE_STATUS);
            status.setConfig(config);
        }
        catch (Exception e) {
            status.setStatus(BiometricsStatus.NOT_AVAILABLE_STATUS);
            status.setErrorDetails(e.getMessage());
        }
        return status;
    }
}