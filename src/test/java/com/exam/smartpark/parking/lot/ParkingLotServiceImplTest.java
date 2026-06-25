package com.exam.smartpark.parking.lot;

import com.exam.smartpark.exception.type.DuplicateKeyException;
import com.exam.smartpark.exception.type.ResourceNotFoundException;
import com.exam.smartpark.exception.type.UnprocessableContentException;
import com.exam.smartpark.parking.lot.dto.request.CreateParkingLotRequest;
import com.exam.smartpark.parking.lot.dto.response.AvailableParkingLotResponse;
import com.exam.smartpark.parking.lot.dto.response.CheckoutParkingResponse;
import com.exam.smartpark.parking.lot.dto.response.ParkingLotDetailsResponse;
import com.exam.smartpark.parking.lot.entity.ParkingLot;
import com.exam.smartpark.parking.lot.entity.ParkingLotVehicleHistory;
import com.exam.smartpark.parking.lot.mapper.ParkingLotMapper;
import com.exam.smartpark.parking.lot.mapper.ParkingLotVehicleMapper;
import com.exam.smartpark.parking.lot.repository.ParkingLotRepository;
import com.exam.smartpark.parking.lot.repository.ParkingLotVehicleRepository;
import com.exam.smartpark.parking.lot.service.ParkingLotServiceImpl;
import com.exam.smartpark.vehicle.entity.Vehicle;
import com.exam.smartpark.vehicle.entity.VehicleType;
import com.exam.smartpark.vehicle.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingLotServiceImplTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private ParkingLotMapper parkingLotMapper;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ParkingLotVehicleMapper parkingLotVehicleMapper;

    @Mock
    private ParkingLotVehicleRepository parkingLotVehicleRepository;

    @InjectMocks
    private ParkingLotServiceImpl parkingLotService;

    private ParkingLot parkingLot;
    private Vehicle vehicle;
    private ParkingLotVehicleHistory parkingHistory;

    @BeforeEach
    void setUp() {
        parkingLot = new ParkingLot(1L, "MAKATI-TOWER-A", "MAKATI CITY", 10, 3, BigDecimal.valueOf(5));

        vehicle = new Vehicle(1L, "ABC123", VehicleType.CAR, "Drenzo Andrei");

        parkingHistory = new ParkingLotVehicleHistory(1L, parkingLot, vehicle, LocalDateTime.now(), null, null, null);
    }

    @Test
    void shouldRegisterParkingLotSuccessfully() {
        CreateParkingLotRequest request =
                new CreateParkingLotRequest("MAKATI-TOWER-A", "MAKATI CITY", 10, BigDecimal.valueOf(5));

        ParkingLot mappedParkingLot = new ParkingLot();
        mappedParkingLot.setLotId("MAKATI-TOWER-A");

        when(parkingLotRepository.existsByLotId("MAKATI-TOWER-A")).thenReturn(false);
        when(parkingLotMapper.toParkingLot(request)).thenReturn(mappedParkingLot);

        parkingLotService.registerParkingLot(request);

        verify(parkingLotRepository).existsByLotId("MAKATI-TOWER-A");
        verify(parkingLotMapper).toParkingLot(request);
        verify(parkingLotRepository).save(mappedParkingLot);
    }

    @Test
    void shouldThrowDuplicateKeyExceptionWhenParkingLotIdAlreadyExists() {
        CreateParkingLotRequest request =
                new CreateParkingLotRequest("MAKATI-TOWER-A", "MAKATI CITY", 10, BigDecimal.valueOf(5));

        when(parkingLotRepository.existsByLotId("MAKATI-TOWER-A")).thenReturn(true);

        assertThatThrownBy(() -> parkingLotService.registerParkingLot(request))
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessage("There is already existing Parking Lot ID");

        verify(parkingLotRepository).existsByLotId("MAKATI-TOWER-A");
        verify(parkingLotMapper, never()).toParkingLot(any());
        verify(parkingLotRepository, never()).save(any());
    }


    @Test
    void shouldCheckinVehicleSuccessfully() {
        when(vehicleRepository.findByLicensePlate("ABC123")).thenReturn(Optional.of(vehicle));
        when(parkingLotRepository.findByLotId("MAKATI-TOWER-A")).thenReturn(Optional.of(parkingLot));
        when(parkingLotVehicleRepository.existsByVehicleAndCheckOutIsNull(vehicle)).thenReturn(false);
        when(parkingLotVehicleMapper.toParkingLotVehicleHistory(vehicle, parkingLot)).thenReturn(parkingHistory);

        parkingLotService.checkinParking("ABC123", "MAKATI-TOWER-A");

        verify(vehicleRepository).findByLicensePlate("ABC123");
        verify(parkingLotRepository).findByLotId("MAKATI-TOWER-A");
        verify(parkingLotVehicleRepository).existsByVehicleAndCheckOutIsNull(vehicle);
        verify(parkingLotVehicleMapper).toParkingLotVehicleHistory(vehicle, parkingLot);
        verify(parkingLotVehicleRepository).save(parkingHistory);

        assertThat(parkingLot.getOccupiedSpace()).isEqualTo(4);
        verify(parkingLotRepository).save(parkingLot);
    }

    @Test
    void shouldThrowUnprocessableContentExceptionWhenParkingLotIsFullDuringCheckin() {
        parkingLot.setCapacity(10);
        parkingLot.setOccupiedSpace(10);

        when(vehicleRepository.findByLicensePlate("ABC123")).thenReturn(Optional.of(vehicle));
        when(parkingLotRepository.findByLotId("MAKATI-TOWER-A")).thenReturn(Optional.of(parkingLot));

        assertThatThrownBy(() -> parkingLotService.checkinParking("ABC123", "MAKATI-TOWER-A"))
                .isInstanceOf(UnprocessableContentException.class)
                .hasMessage("There is no available parking space in this parking lot");

        verify(parkingLotVehicleRepository, never()).save(any());
        verify(parkingLotRepository, never()).save(any());
    }

    @Test
    void shouldThrowUnprocessableContentExceptionWhenVehicleIsAlreadyParked() {
        when(vehicleRepository.findByLicensePlate("ABC123")).thenReturn(Optional.of(vehicle));
        when(parkingLotRepository.findByLotId("MAKATI-TOWER-A")).thenReturn(Optional.of(parkingLot));
        when(parkingLotVehicleRepository.existsByVehicleAndCheckOutIsNull(vehicle)).thenReturn(true);

        assertThatThrownBy(() -> parkingLotService.checkinParking("ABC123", "MAKATI-TOWER-A"))
                .isInstanceOf(UnprocessableContentException.class)
                .hasMessage("The vehicle is currently parked");

        verify(parkingLotVehicleRepository, never()).save(any());
        verify(parkingLotRepository, never()).save(any());
    }

    @Test
    void shouldCheckoutVehicleSuccessfully() {
        parkingHistory.setCheckIn(LocalDateTime.now().minusMinutes(15));
        parkingHistory.setCostPerMinuteSnapshot(parkingLot.getCostPerMinute());

        when(vehicleRepository.findByLicensePlate("ABC123")).thenReturn(Optional.of(vehicle));
        when(parkingLotRepository.findByLotId("MAKATI-TOWER-A")).thenReturn(Optional.of(parkingLot));
        when(parkingLotVehicleRepository.findByVehicleAndParkingLotAndCheckOutIsNull(vehicle, parkingLot))
                .thenReturn(Optional.of(parkingHistory));

        CheckoutParkingResponse response = parkingLotService.checkoutParking("ABC123", "MAKATI-TOWER-A");

        verify(vehicleRepository).findByLicensePlate("ABC123");
        verify(parkingLotRepository).findByLotId("MAKATI-TOWER-A");
        verify(parkingLotVehicleRepository)
                .findByVehicleAndParkingLotAndCheckOutIsNull(vehicle, parkingLot);
        verify(parkingLotVehicleRepository).save(parkingHistory);

        assertThat(parkingHistory.getCheckOut()).isNotNull();
        assertThat(parkingHistory.getTotalParkingCost()).isNotNull();
        assertThat(parkingLot.getOccupiedSpace()).isEqualTo(2);
        assertThat(response).isNotNull();
        assertThat(response.parkingCost()).isEqualTo(parkingHistory.getTotalParkingCost());
    }


    @Test
    void shouldThrowResourceNotFoundExceptionWhenNoActiveParkingRecordExistsDuringCheckout() {
        when(vehicleRepository.findByLicensePlate("ABC123")).thenReturn(Optional.of(vehicle));
        when(parkingLotRepository.findByLotId("MAKATI-TOWER-A")).thenReturn(Optional.of(parkingLot));
        when(parkingLotVehicleRepository.findByVehicleAndParkingLotAndCheckOutIsNull(vehicle, parkingLot))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> parkingLotService.checkoutParking("ABC123", "MAKATI-TOWER-A"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No parked vehicle in this parking lot");

        verify(parkingLotVehicleRepository, never()).save(any());
    }

    // =========================================================
    // readAvailableParkingLot
    // =========================================================

    @Test
    void shouldReadAvailableParkingLotSuccessfully() {
        AvailableParkingLotResponse expectedResponse = mock(AvailableParkingLotResponse.class);

        when(parkingLotRepository.findByLotId("MAKATI-TOWER-A")).thenReturn(Optional.of(parkingLot));
        when(parkingLotMapper.toAvailableParkingLot(parkingLot)).thenReturn(expectedResponse);

        AvailableParkingLotResponse actualResponse = parkingLotService.readAvailableParkingLot("MAKATI-TOWER-A");

        assertThat(actualResponse).isEqualTo(expectedResponse);

        verify(parkingLotRepository).findByLotId("MAKATI-TOWER-A");
        verify(parkingLotMapper).toAvailableParkingLot(parkingLot);
    }

    @Test
    void shouldReadParkingLotDetailsSuccessfully() {
        ParkingLotDetailsResponse expectedResponse = mock(ParkingLotDetailsResponse.class);

        when(parkingLotRepository.findByLotId("MAKATI-TOWER-A")).thenReturn(Optional.of(parkingLot));
        when(parkingLotVehicleRepository.findByParkingLotAndCheckOutIsNull(parkingLot))
                .thenReturn(Optional.of(List.of(parkingHistory)));
        when(parkingLotMapper.toCurrentlyParkedVehicleInParkingLot(List.of(parkingHistory)))
                .thenReturn(expectedResponse);

        ParkingLotDetailsResponse actualResponse = parkingLotService.readParkingLotDetails("MAKATI-TOWER-A");

        assertThat(actualResponse).isEqualTo(expectedResponse);

        verify(parkingLotRepository).findByLotId("MAKATI-TOWER-A");
        verify(parkingLotVehicleRepository).findByParkingLotAndCheckOutIsNull(parkingLot);
        verify(parkingLotMapper).toCurrentlyParkedVehicleInParkingLot(List.of(parkingHistory));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenReadingParkingLotDetailsAndLotNotFound() {
        when(parkingLotRepository.findByLotId("MAKATI-TOWER-A")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> parkingLotService.readParkingLotDetails("MAKATI-TOWER-A"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No parking lot found");

        verify(parkingLotVehicleRepository, never()).findByParkingLotAndCheckOutIsNull(any());
    }

    @Test
    void shouldReturnNullWhenParkingLotDetailsListIsEmpty() {
        when(parkingLotRepository.findByLotId("LOT-A")).thenReturn(Optional.of(parkingLot));
        when(parkingLotVehicleRepository.findByParkingLotAndCheckOutIsNull(parkingLot))
                .thenReturn(Optional.of(List.of()));

        ParkingLotDetailsResponse response = parkingLotService.readParkingLotDetails("LOT-A");

        assertThat(response).isNull();

        verify(parkingLotMapper, never()).toCurrentlyParkedVehicleInParkingLot(any());
    }
}
