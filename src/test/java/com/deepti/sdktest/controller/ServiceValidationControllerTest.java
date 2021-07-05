package com.deepti.sdktest.controller;

import com.deepti.sdktest.model.PuppyObject;
import com.deepti.sdktest.service.ServiceValidationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceValidationControllerTest {

    @Mock
    ServiceValidationService serviceValidationService;

    @InjectMocks
    ServiceValidationController unitUnderTest = new ServiceValidationController();

    @Test
    public void testController() {
        PuppyObject puppyObject = new PuppyObject();
        puppyObject.setPuppy(true);
        when(serviceValidationService.validate(any(List.class))).thenReturn(puppyObject);
        PuppyObject retObj = unitUnderTest.validateService(new ArrayList<>());
        assertEquals(true, retObj.getPuppy());
    }
}
