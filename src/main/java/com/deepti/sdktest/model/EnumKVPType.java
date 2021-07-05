package com.deepti.sdktest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EnumKVPType {
  @JsonProperty("general.string")
  STRING("general.string"),
  @JsonProperty("general.integer")
  INTEGER("general.integer"),
  @JsonProperty("general.float")
  FLOAT("general.float"),
  @JsonProperty("general.bool")
  BOOL("general.bool");

  private String value;

  EnumKVPType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static EnumKVPType fromValue(String text) {
    for (EnumKVPType b : EnumKVPType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
