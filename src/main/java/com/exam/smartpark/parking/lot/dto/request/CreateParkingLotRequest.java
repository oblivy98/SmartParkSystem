package com.exam.smartpark.parking.lot.dto.request;

import jakarta.validation.constraints.Size;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;

public record CreateParkingLotRequest(

        @NonNull
        @Size(max = 50, message = "Lot id must not exceed 50 characters")
        String lotId,

        @NonNull
        String location,

        @NonNull
        Integer capacity,

        @NonNull
        BigDecimal costPerMinute
) {}
