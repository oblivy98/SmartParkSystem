package com.exam.smartpark.vehicle.dto.response;

import com.exam.smartpark.vehicle.entity.VehicleType;

public record VehicleListResponse(
        String licensePlate,
        VehicleType vehicleType,
        String ownerName
) {}
