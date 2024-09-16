package com.monitoring.monitoringService.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class DeviceEnergyConsumption implements Serializable {
    private Long timestamp;
    private Long deviceId;
    private Float energyConsumption;





    @Override
    public String toString(){
        return "Timestamp: "+ timestamp +"\nDeviceId: " + deviceId+ "\n Energy Consumption: "+energyConsumption+" kWh.";
    }
}