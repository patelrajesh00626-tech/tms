package com.translation.model.enums;

public enum Locale {
    EN("English"),
    FR("French"),
    ES("Spanish");
    
    private final String displayName;
    
    Locale(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public static Locale fromString(String value) {
        for (Locale locale : Locale.values()) {
            if (locale.name().equalsIgnoreCase(value)) {
                return locale;
            }
        }
        throw new IllegalArgumentException("No enum constant " + Locale.class.getCanonicalName() + "." + value);
    }
}
