package com.monitoring.monitoringService.controllers;
import com.monitoring.monitoringService.dto.DeviceConsumptionDTO;
import com.monitoring.monitoringService.services.DeviceConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deviceConsumptions")
public class DeviceConsumptionController {

    private final DeviceConsumptionService deviceConsumptionService;

    @Autowired
    public DeviceConsumptionController(DeviceConsumptionService deviceConsumptionService) {
        this.deviceConsumptionService = deviceConsumptionService;
    }

    @GetMapping("/{id}")
    public DeviceConsumptionDTO getDeviceConsumptionById(@PathVariable Long id) {
        return deviceConsumptionService.getDeviceConsumptionById(id);
    }

    @GetMapping("/byDeviceID/{deviceID}")
    public List<DeviceConsumptionDTO> getDeviceConsumptionsByDeviceID(@PathVariable Long deviceID) {
        return deviceConsumptionService.getDeviceConsumptionsByDeviceID(deviceID);

    }

    @PostMapping
    public ResponseEntity saveDeviceConsumption(@RequestBody DeviceConsumptionDTO deviceConsumptionDTO) {
        return  ResponseEntity.status(HttpStatus.OK).body(deviceConsumptionService.save(deviceConsumptionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDeviceConsumption(@PathVariable Long id) {
        deviceConsumptionService.deleteDeviceConsumption(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
