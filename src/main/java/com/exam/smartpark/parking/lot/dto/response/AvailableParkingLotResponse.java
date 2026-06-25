package com.exam.smartpark.parking.lot.dto.response;

public record AvailableParkingLotResponse(
        String lotId,
        Integer capacity,
        Integer occupiedSpace
) {
}
