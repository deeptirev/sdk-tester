package com.deepti.sdktest.service;

import com.deepti.sdktest.exceptionHandling.ServiceValidationException;
import com.deepti.sdktest.model.ActivityData;
import com.deepti.sdktest.model.DeviceCheckDetailsObject;
import com.deepti.sdktest.model.SessionData;
import com.deepti.sdktest.repository.SessionDataRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceValidationServiceTest {

    @Mock
    SessionDataRepository sessionDataRepository;

    @InjectMocks
    ServiceValidationService unitUnderTest;

    @Test
    public void validateSuccess() {
        List<DeviceCheckDetailsObject> deviceCheckDetailsObjects = Arrays.asList(getDeviceCheckDetailsObject());
        assertDoesNotThrow(() -> unitUnderTest.validate(deviceCheckDetailsObjects));
    }

    @Test
    public void validateBadCheckType() {
        DeviceCheckDetailsObject deviceCheckDetailsObject = getDeviceCheckDetailsObject();
        deviceCheckDetailsObject.setCheckType("WRONG_ENUM");
        List<DeviceCheckDetailsObject> deviceCheckDetailsObjects = Arrays.asList(deviceCheckDetailsObject);
        assertThrows(ServiceValidationException.class, () -> unitUnderTest.validate(deviceCheckDetailsObjects));
    }

    @Test
    public void validateBadActivityType() {
        DeviceCheckDetailsObject deviceCheckDetailsObject = getDeviceCheckDetailsObject();
        deviceCheckDetailsObject.setActivityType("WRONG_ENUM");
        List<DeviceCheckDetailsObject> deviceCheckDetailsObjects = Arrays.asList(deviceCheckDetailsObject);
        assertThrows(ServiceValidationException.class, () -> unitUnderTest.validate(deviceCheckDetailsObjects));
    }

    @Test
    public void validateBadActivityTypeMissing() {
        DeviceCheckDetailsObject deviceCheckDetailsObject = getDeviceCheckDetailsObject();
        deviceCheckDetailsObject.setActivityType(null);
        List<DeviceCheckDetailsObject> deviceCheckDetailsObjects = Arrays.asList(deviceCheckDetailsObject);
        assertThrows(ServiceValidationException.class, () -> unitUnderTest.validate(deviceCheckDetailsObjects));
    }

    @Test
    public void validateBadActivityDataMissingKey() {
        DeviceCheckDetailsObject deviceCheckDetailsObject = getDeviceCheckDetailsObject();
        deviceCheckDetailsObject.getActivityData().get(0).setKvpKey(null);
        List<DeviceCheckDetailsObject> deviceCheckDetailsObjects = Arrays.asList(deviceCheckDetailsObject);
        assertThrows(ServiceValidationException.class, () -> unitUnderTest.validate(deviceCheckDetailsObjects));
    }

    @Test
    public void validateBadActivityDataMissingValue() {
        DeviceCheckDetailsObject deviceCheckDetailsObject = getDeviceCheckDetailsObject();
        deviceCheckDetailsObject.getActivityData().get(0).setKvpValue(null);
        List<DeviceCheckDetailsObject> deviceCheckDetailsObjects = Arrays.asList(deviceCheckDetailsObject);
        assertThrows(ServiceValidationException.class, () -> unitUnderTest.validate(deviceCheckDetailsObjects));
    }

    @Test
    public void validateBadActivityDataMissingType() {
        DeviceCheckDetailsObject deviceCheckDetailsObject = getDeviceCheckDetailsObject();
        deviceCheckDetailsObject.getActivityData().get(0).setKvpType(null);
        List<DeviceCheckDetailsObject> deviceCheckDetailsObjects = Arrays.asList(deviceCheckDetailsObject);
        assertThrows(ServiceValidationException.class, () -> unitUnderTest.validate(deviceCheckDetailsObjects));
    }

    @Test
    public void validateBadActivityDataBadType() {
        DeviceCheckDetailsObject deviceCheckDetailsObject = getDeviceCheckDetailsObject();
        deviceCheckDetailsObject.getActivityData().get(0).setKvpType("WRONG_ENUM");
        List<DeviceCheckDetailsObject> deviceCheckDetailsObjects = Arrays.asList(deviceCheckDetailsObject);
        assertThrows(ServiceValidationException.class, () -> unitUnderTest.validate(deviceCheckDetailsObjects));
    }

    @Test
    public void validateBadActivityDataIncompatibleBool() {
        DeviceCheckDetailsObject deviceCheckDetailsObject = getDeviceCheckDetailsObject();
        deviceCheckDetailsObject.getActivityData().get(0).setKvpValue("not bool");
        deviceCheckDetailsObject.getActivityData().get(0).setKvpType("general.bool");
        List<DeviceCheckDetailsObject> deviceCheckDetailsObjects = Arrays.asList(deviceCheckDetailsObject);
        assertThrows(ServiceValidationException.class, () -> unitUnderTest.validate(deviceCheckDetailsObjects));
    }

    @Test
    public void validateBadActivityDataIncompatibleFloat() {
        DeviceCheckDetailsObject deviceCheckDetailsObject = getDeviceCheckDetailsObject();
        deviceCheckDetailsObject.getActivityData().get(0).setKvpValue("not float");
        deviceCheckDetailsObject.getActivityData().get(0).setKvpType("general.float");
        List<DeviceCheckDetailsObject> deviceCheckDetailsObjects = Arrays.asList(deviceCheckDetailsObject);
        assertThrows(ServiceValidationException.class, () -> unitUnderTest.validate(deviceCheckDetailsObjects));
    }

    @Test
    public void validateBadActivityDataIncompatibleInt() {
        DeviceCheckDetailsObject deviceCheckDetailsObject = getDeviceCheckDetailsObject();
        deviceCheckDetailsObject.getActivityData().get(0).setKvpValue("not int");
        deviceCheckDetailsObject.getActivityData().get(0).setKvpType("general.integer");
        List<DeviceCheckDetailsObject> deviceCheckDetailsObjects = Arrays.asList(deviceCheckDetailsObject);
        assertThrows(ServiceValidationException.class, () -> unitUnderTest.validate(deviceCheckDetailsObjects));
    }

    @Test
    public void validateBadActivityDataDupeKeys() {
        DeviceCheckDetailsObject deviceCheckDetailsObject = getDeviceCheckDetailsObject();

        List<DeviceCheckDetailsObject> deviceCheckDetailsObjects = Arrays.asList(deviceCheckDetailsObject);
        assertDoesNotThrow(() -> unitUnderTest.validate(deviceCheckDetailsObjects));

        ActivityData activityData = new ActivityData();
        activityData.setKvpKey(deviceCheckDetailsObject.getActivityData().get(0).getKvpKey());
        activityData.setKvpValue("some.string");
        activityData.setKvpType("general.string");
        deviceCheckDetailsObject.getActivityData().add(activityData);

        List<DeviceCheckDetailsObject> deviceCheckDetailsObjectsBad = Arrays.asList(deviceCheckDetailsObject);
        assertThrows(ServiceValidationException.class, () -> unitUnderTest.validate(deviceCheckDetailsObjectsBad));
    }

    @Test
    public void validateBadSessionKey() {
        DeviceCheckDetailsObject deviceCheckDetailsObject = getDeviceCheckDetailsObject();
        deviceCheckDetailsObject.setCheckSessionKey(null);

        List<DeviceCheckDetailsObject> deviceCheckDetailsObjectsBad = Arrays.asList(deviceCheckDetailsObject);
        assertThrows(ServiceValidationException.class, () -> unitUnderTest.validate(deviceCheckDetailsObjectsBad));
    }

    @Test
    public void validateBadSessionKeyDupe() {
        DeviceCheckDetailsObject deviceCheckDetailsObject = getDeviceCheckDetailsObject();
        List<DeviceCheckDetailsObject> deviceCheckDetailsObjectsBad = Arrays.asList(deviceCheckDetailsObject);
        when(sessionDataRepository.findById(deviceCheckDetailsObject.getCheckSessionKey())).thenReturn(Optional.of(new SessionData()));
        assertThrows(ServiceValidationException.class, () -> unitUnderTest.validate(deviceCheckDetailsObjectsBad));
    }

    private DeviceCheckDetailsObject getDeviceCheckDetailsObject() {
        DeviceCheckDetailsObject deviceCheckDetailsObject = new DeviceCheckDetailsObject();
        deviceCheckDetailsObject.setCheckType("DEVICE");
        deviceCheckDetailsObject.setActivityType("SIGNUP");
        deviceCheckDetailsObject.setCheckSessionKey("abc");
        List<ActivityData> activityDataList = new ArrayList<>();
        ActivityData activityData = new ActivityData();
        activityData.setKvpKey("some.key");
        activityData.setKvpValue("some.string");
        activityData.setKvpType("general.string");
        activityDataList.add(activityData);
        deviceCheckDetailsObject.setActivityData(activityDataList);
        return deviceCheckDetailsObject;
    }
}
