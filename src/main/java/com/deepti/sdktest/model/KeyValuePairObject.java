package com.deepti.sdktest.model;

import lombok.Data;

@Data
public class KeyValuePairObject {
  private String kvpKey = null;

  private String kvpValue = null;

  private EnumKVPType kvpType = null;
}
