package com.exam.smartpark.parking.lot.dto.response;

import java.util.List;

public record ParkingLotDetailsResponse(
        String lotId,
        List<VehiclesInParkingLotResponse> vehiclesParked
) {}
