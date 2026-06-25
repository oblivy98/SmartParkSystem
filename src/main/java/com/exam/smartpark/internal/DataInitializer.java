package com.exam.smartpark.internal;

import com.exam.smartpark.parking.lot.entity.ParkingLot;
import com.exam.smartpark.parking.lot.repository.ParkingLotRepository;
import com.exam.smartpark.parking.lot.repository.ParkingLotVehicleRepository;
import com.exam.smartpark.user.entity.SmartParkUser;
import com.exam.smartpark.user.repository.SmartParkUserRepository;
import com.exam.smartpark.vehicle.entity.Vehicle;
import com.exam.smartpark.vehicle.entity.VehicleType;
import com.exam.smartpark.vehicle.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeDummyData (SmartParkUserRepository smartParkUserRepository,
                                           VehicleRepository vehicleRepository,
                                           ParkingLotRepository parkingLotRepository,
                                           ParkingLotVehicleRepository parkingLotVehicleRepository,
                                           PasswordEncoder passwordEncoder) {
        return args -> {
            SmartParkUser user = new SmartParkUser();
            user.setUsername("SP_ADMIN");
            user.setPassword(passwordEncoder.encode("SP_PASSWORD123"));

            smartParkUserRepository.save(user);

            Vehicle vehicle1 = new Vehicle();
            vehicle1.setOwnerName("Drenzo");
            vehicle1.setLicensePlate("505XAG");
            vehicle1.setVehicleType(VehicleType.MOTORCYCLE);
            vehicleRepository.save(vehicle1);

            Vehicle vehicle2 = new Vehicle();
            vehicle2.setOwnerName("Andrei");
            vehicle2.setLicensePlate("UIC123");
            vehicle2.setVehicleType(VehicleType.CAR);
            vehicleRepository.save(vehicle2);

            ParkingLot parkingLot1 = new ParkingLot();
            parkingLot1.setLotId("AYALA_MAKATI_TOWER-1");
            parkingLot1.setLocation("MAKATI CITY, Salcedo");
            parkingLot1.setCapacity(50);
            parkingLot1.setOccupiedSpace(0);
            parkingLot1.setCostPerMinute(BigDecimal.valueOf(5));
            parkingLotRepository.save(parkingLot1);

            ParkingLot parkingLot2 = new ParkingLot();
            parkingLot2.setLotId("AYALA_MAKATI_TOWER-2");
            parkingLot2.setLocation("MAKATI CITY, Salcedo");
            parkingLot2.setCapacity(100);
            parkingLot2.setOccupiedSpace(0);
            parkingLot2.setCostPerMinute(BigDecimal.valueOf(3));
            parkingLotRepository.save(parkingLot2);

            ParkingLot parkingLot3 = new ParkingLot();
            parkingLot3.setLotId("AYALA_MAKATI_TOWER-3");
            parkingLot3.setLocation("MAKATI CITY, Dela Rosa");
            parkingLot3.setCapacity(2);
            parkingLot3.setOccupiedSpace(0);
            parkingLot3.setCostPerMinute(BigDecimal.valueOf(10));
            parkingLotRepository.save(parkingLot3);
        };
    }
}
