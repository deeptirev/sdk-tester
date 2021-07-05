package com.deepti.sdktest.service;

import com.deepti.sdktest.exceptionHandling.ServiceValidationException;
import com.deepti.sdktest.model.*;
import com.deepti.sdktest.repository.SessionDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class ServiceValidationService {

    @Autowired
    SessionDataRepository sessionDataRepository;

    /* These are the validation Rules :
      - checkType needs to be valid type
      - activityType needs to be valid type
      - Activity Data - data complete with key, type and value
      - Activity Data - keys are unique
      - Activity Data - value can be converted to type. Value-type are compatible
      - checkSessionKey needs to be unique
     */

    public PuppyObject validate(List<DeviceCheckDetailsObject> deviceCheckObjects) {
        for (DeviceCheckDetailsObject deviceCheckDetailsObject : deviceCheckObjects) {
            validateCheckType(deviceCheckDetailsObject.getCheckType());

            validateActivityType(deviceCheckDetailsObject.getActivityType());

            checkActivityData(deviceCheckDetailsObject.getActivityData());

            validateSessionKey(deviceCheckDetailsObject.getCheckSessionKey());
        }

        PuppyObject obj = new PuppyObject();
        obj.setPuppy(true);
        return obj;
    }

    private void validateCheckType(String checkType) {
        if (CheckTypeEnum.fromValue(checkType) == null)
            throw new ServiceValidationException("checkType not one of: " + java.util.Arrays.asList(CheckTypeEnum.values()));
    }

    private void validateActivityType(String checkType) {
        if (ActivityTypeEnum.fromValue(checkType) == null)
            throw new ServiceValidationException("activityType not one of: " + java.util.Arrays.asList(ActivityTypeEnum.values()));
    }

    private void validateKvpType(String kvyType) {
        if (EnumKVPType.fromValue(kvyType) == null)
            throw new ServiceValidationException("kvpType not one of: " + java.util.Arrays.asList(EnumKVPType.values()));
    }

    private void validateSessionKey(String sessionKey) {
        if (isEmpty(sessionKey)) throw new ServiceValidationException("checkSessionKey needs to be a valid string.");
        SessionData sessionData = new SessionData();
        sessionData.setSessionId(sessionKey);
        if (sessionDataRepository.findById(sessionKey).isPresent()) {
            throw new ServiceValidationException("checkSessionKey Invalid. Duplicate Id.");
        }
        sessionDataRepository.save(sessionData);
    }

    private void checkActivityData(List<ActivityData> activityDataList) {
        HashSet<String> keys = new HashSet<>();
        for (ActivityData activityData : activityDataList) {
            if (isEmpty(activityData.getKvpKey()) || isEmpty(activityData.getKvpValue()) ||
                    activityData.getKvpType() == null) {
                throw new ServiceValidationException("Activity Data Invalid. kvpKey, kvpValue, kvpType needs to be passed");
            }

            validateKvpType(activityData.getKvpType());

            keys.add(activityData.getKvpKey());

            try {
                String value = activityData.getKvpValue();
                switch (EnumKVPType.fromValue(activityData.getKvpType())) {
                    case BOOL:
                        if (!(value.equals("true") || value.equals("false"))) throw new ServiceValidationException();
                        break;
                    case FLOAT:
                        Float.parseFloat(activityData.getKvpValue());
                        break;
                    case STRING:
                        break;
                    case INTEGER:
                        Integer.parseInt(activityData.getKvpValue());
                }
            } catch (NumberFormatException e) {
                throw new ServiceValidationException("Activity Data Invalid. kvpValue-kvpType not compatible.");
            } catch (ServiceValidationException e) {
                throw new ServiceValidationException("Activity Data Invalid. kvpValue-kvpType not compatible.");
            }

        }
        if (keys.size() != activityDataList.size()) {
            throw new ServiceValidationException("Activity Data Invalid. Duplicate keys in the request payload");
        }
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
