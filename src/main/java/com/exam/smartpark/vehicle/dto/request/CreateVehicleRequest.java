package com.exam.smartpark.vehicle.dto.request;

import com.exam.smartpark.vehicle.entity.VehicleType;

public record CreateVehicleRequest(
        String licensePlate,
        VehicleType vehicleType,
        String ownerName
) {}
