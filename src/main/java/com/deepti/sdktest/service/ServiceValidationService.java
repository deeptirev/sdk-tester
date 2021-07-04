package com.deepti.sdktest.service;

import com.deepti.sdktest.exceptionHandling.ApplicationException;
import com.deepti.sdktest.model.DeviceCheckDetailsObject;
import com.deepti.sdktest.model.KeyValuePairObject;
import com.deepti.sdktest.model.PuppyObject;
import com.deepti.sdktest.model.SessionData;
import com.deepti.sdktest.repository.SessionDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class ServiceValidationService {

    @Autowired
    SessionDataRepository sessionDataRepository;

    public PuppyObject validate(List<DeviceCheckDetailsObject> deviceCheckObjects) {
        for (DeviceCheckDetailsObject deviceCheckDetailsObject : deviceCheckObjects) {
            SessionData sessionData = new SessionData();
            sessionData.setSessionId(deviceCheckDetailsObject.getCheckSessionKey());
            if (sessionDataRepository.findById(deviceCheckDetailsObject.getCheckSessionKey()).isPresent()) {
                throw new ApplicationException("Duplicate Session Id");
            }
            checkActivityData(deviceCheckDetailsObject.getActivityData());
            sessionDataRepository.save(sessionData);
        }
        PuppyObject obj = new PuppyObject();
        obj.setPuppy(true);
        return obj;
    }

    private void checkActivityData(List<KeyValuePairObject> keyValuePairObjects) {
        HashSet<String> keys = new HashSet<>();
        for (KeyValuePairObject keyValuePairObject : keyValuePairObjects) {
            if (isEmpty(keyValuePairObject.getKvpKey()) || isEmpty(keyValuePairObject.getKvpValue()) ||
                    keyValuePairObject.getKvpType() == null) {

            }

            keys.add(keyValuePairObject.getKvpKey());

            try {
                String value = keyValuePairObject.getKvpValue();
                switch (keyValuePairObject.getKvpType()) {
                    case BOOL:
                        if (!(value.equals("true") || value.equals("false"))) throw new Exception();
                        break;
                    case FLOAT:
                        Float.parseFloat(keyValuePairObject.getKvpValue());
                        break;
                    case STRING:
                        break;
                    case INTEGER:
                        Integer.parseInt(keyValuePairObject.getKvpValue());
                }
            } catch (NumberFormatException e) {
                throw new ApplicationException("Wrong Key Value passed");
            } catch (Exception e) {
                throw new ApplicationException("Wrong Key Value passed");
            }

        }
        if (keys.size() != keyValuePairObjects.size()) {
            throw new ApplicationException("Duplicate keys in the request payload");
        }
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
