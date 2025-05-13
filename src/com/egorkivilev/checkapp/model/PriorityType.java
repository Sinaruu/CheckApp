package com.egorkivilev.checkapp.model;

public enum PriorityType {
    NONE("None"),
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    VERY_HIGH("Very High");

    private final String displayName;

    PriorityType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static PriorityType fromString(String string) {
        for (PriorityType type : PriorityType.values()) {
            if (type.toString().equals(string)) {
                return type;
            }
        }

        return null;
    }
}