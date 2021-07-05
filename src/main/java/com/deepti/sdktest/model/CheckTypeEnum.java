package com.deepti.sdktest.model;

import java.util.List;

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

    public static String[] names() {
        CheckTypeEnum[] enums = values();
        String[] names = new String[enums.length];

        for (int i = 0; i < names.length; i++) {
            names[i] = enums[i].name();
        }

        return names;
    }
}