package com.monitoring.monitoringService.controllers;

import com.monitoring.monitoringService.dto.DeviceDTO;
import com.monitoring.monitoringService.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/monitoring_devices")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/{id}")
    public DeviceDTO getDeviceById(@PathVariable Long id) {
        return deviceService.getDeviceByDeviceID(id);
    }

    @PostMapping("/createDevice")
    public ResponseEntity saveDevice(@RequestBody DeviceDTO deviceDTO) {
        DeviceDTO newDeviceDTO = new DeviceDTO();
        newDeviceDTO.setDeviceID(deviceDTO.getDeviceID());
        newDeviceDTO.setMaxConsumption(deviceDTO.getMaxConsumption());
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.saveDevice(newDeviceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}