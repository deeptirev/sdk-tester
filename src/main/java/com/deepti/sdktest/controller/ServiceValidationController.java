package com.deepti.sdktest.controller;

import com.deepti.sdktest.model.DeviceCheckDetailsObject;
import com.deepti.sdktest.model.PuppyObject;
import com.deepti.sdktest.service.ServiceValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceValidationController {

    @Autowired
    ServiceValidationService validateService;

    @PostMapping("/isgood")
    public PuppyObject testService(@RequestBody List<DeviceCheckDetailsObject> deviceCheckObjects) {
        return validateService.validate(deviceCheckObjects);
    }
}
