package com.device.deviceManagement.controller;

import com.device.deviceManagement.dtos.DeviceDTO;
import com.device.deviceManagement.security.AuthProvider;
import com.device.deviceManagement.services.DeviceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceServiceImpl deviceService;

    private final AuthProvider userAuthenticationProvider;


    @GetMapping("/")
    public ResponseEntity<?> getAllDevices(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String tokenSubstring = token.substring(7);
        if (userAuthenticationProvider.validateToken(tokenSubstring).isAuthenticated()) {
            return ResponseEntity.ok(deviceService.getAllDevices());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getDeviceById(@PathVariable Long id, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String tokenSubstring = token.substring(7);
        if (userAuthenticationProvider.validateToken(tokenSubstring).isAuthenticated()) {
            DeviceDTO deviceDTO = deviceService.getDeviceById(id);
            if (deviceDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found");
            } else {
                return ResponseEntity.ok(deviceDTO);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }


    @PostMapping("/addDevice")
    public ResponseEntity<?> saveDevice(@RequestBody DeviceDTO deviceDTO, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String tokenSubstring = token.substring(7);
        if (userAuthenticationProvider.validateToken(tokenSubstring).isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.saveDevice(deviceDTO));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDevice(@RequestBody DeviceDTO deviceDTO, @PathVariable Long id, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String tokenSubstring = token.substring(7);
        if (userAuthenticationProvider.validateToken(tokenSubstring).isAuthenticated()) {
            deviceDTO.setId(id);
            deviceService.updateDevice(deviceDTO);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable Long id, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String tokenSubstring = token.substring(7);
        if (userAuthenticationProvider.validateToken(tokenSubstring).isAuthenticated()) {
            DeviceDTO deviceDTO = deviceService.getDeviceById(id);
            if (deviceDTO != null) {
                deviceService.deleteDevice(deviceDTO);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

}
