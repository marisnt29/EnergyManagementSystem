package com.monitoring.monitoringService.repository;

import com.monitoring.monitoringService.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query("select d from Device d where d.deviceID = ?1")
    Optional<Device> findByDeviceID(Long id);

}