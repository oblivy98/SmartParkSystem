package com.exam.smartpark.parking.lot.dto.response;

import java.math.BigDecimal;

public record CheckoutParkingResponse(
        BigDecimal parkingCost
) {}
