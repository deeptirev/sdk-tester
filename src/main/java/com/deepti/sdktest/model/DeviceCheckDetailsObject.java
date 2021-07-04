package com.deepti.sdktest.model;

import lombok.Data;

import java.util.List;

@Data
public class DeviceCheckDetailsObject {

  public enum CheckTypeEnum {
    DEVICE("DEVICE"),
    BIOMETRIC("BIOMETRIC"),
    COMBO("COMBO");

    private String value;

    CheckTypeEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static CheckTypeEnum fromValue(String text) {
      for (CheckTypeEnum b : CheckTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

  }

  private CheckTypeEnum checkType = null;

  public enum ActivityTypeEnum {
    SIGNUP("SIGNUP"),
    LOGIN("LOGIN"),
    PAYMENT("PAYMENT"),
    CONFIRMATION("CONFIRMATION"),
    __VENDOR_SPECIFIC_LIST_("_<Vendor Specific List>");

    private String value;

    ActivityTypeEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static ActivityTypeEnum fromValue(String text) {
      for (ActivityTypeEnum b : ActivityTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  private ActivityTypeEnum activityType = null;

  private String checkSessionKey = null;

  private List<KeyValuePairObject> activityData = null;
}
