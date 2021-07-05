package com.deepti.sdktest.model;

import lombok.Data;

import java.util.List;

@Data
public class DeviceCheckDetailsObject {
  private String checkType = null;

  private String activityType = null;

  private String checkSessionKey = null;

  private List<ActivityData> activityData = null;
}
