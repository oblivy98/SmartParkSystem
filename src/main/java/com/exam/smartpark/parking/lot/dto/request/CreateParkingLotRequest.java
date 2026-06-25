package com.exam.smartpark.parking.lot.dto.request;

import java.math.BigDecimal;

public record CreateParkingLotRequest(
        String lotId,
        String location,
        Integer capacity,
        BigDecimal costPerMinute
) {}
