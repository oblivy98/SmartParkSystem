package com.exam.smartpark.vehicle.entity;

public enum VehicleType {
    CAR,
    MOTORCYCLE,
    TRUCK;

    public static boolean isValid(String value) {
        for (VehicleType type : VehicleType.values()) {
            if (type.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
