package com.exam.smartpark.parking.lot.dto.response;

import java.math.BigDecimal;

public record AvailableParkingLotResponse(
        String lotId,
        Integer capacity,
        Integer occupiedSpace,
        BigDecimal costPerMinute
) {
}
