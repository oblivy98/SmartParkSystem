package com.exam.smartpark.parking.lot.dto.response;

public record VehiclesInParkingLotResponse (
        String licensePlate,
        String ownerName,
        long parkMinuteDuration
){}
