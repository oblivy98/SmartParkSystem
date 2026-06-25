package com.exam.smartpark.vehicle.dto.request;

import com.exam.smartpark.vehicle.entity.VehicleType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateVehicleRequest(
        @NotNull(message = "License plate cannot be null.")
        @Pattern(regexp = "^[a-zA-Z0-9-]+$",
                 message = "License plate can only contain letters, numbers, and dashes.")
        String licensePlate,

        @NotNull(message = "Vehicle Type cannot be null.")
        VehicleType vehicleType,

        @NotNull(message = "License plate cannot be null.")
        @Pattern(regexp = "^[a-zA-Z\\s]*$",
                 message = "The field must contain only letters and spaces")
        String ownerName
) {}
