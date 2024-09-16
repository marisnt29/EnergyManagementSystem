package com.monitoring.monitoringService.repository;

import com.monitoring.monitoringService.model.DeviceConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceConsumptionRepository extends JpaRepository<DeviceConsumption, Long> {


    @Query("select d from DeviceConsumption d where d.deviceID = ?1")
    List<DeviceConsumption> getAllDeviceConsumptionsByDeviceID(Long deviceID);

}