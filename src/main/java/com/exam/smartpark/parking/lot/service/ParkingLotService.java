package com.exam.smartpark.parking.lot.service;

import com.exam.smartpark.parking.lot.dto.request.CreateParkingLotRequest;
import com.exam.smartpark.parking.lot.dto.response.AvailableParkingLotResponse;
import com.exam.smartpark.parking.lot.dto.response.CheckoutParkingResponse;
import com.exam.smartpark.parking.lot.dto.response.ParkingLotDetailsResponse;

import java.util.List;

public interface ParkingLotService {

    void registerParkingLot(CreateParkingLotRequest request);

    void checkinParking(String licensePlate, String lotId);

    CheckoutParkingResponse checkoutParking(String licensePlate, String lotId);

    List<AvailableParkingLotResponse> readAvailableParkingLots();

    ParkingLotDetailsResponse readParkingLotDetails(String lotId);
}
