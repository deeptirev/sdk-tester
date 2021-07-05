package com.deepti.sdktest.model;

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
