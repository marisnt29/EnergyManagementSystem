package com.reader.csvReader.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceEnergyConsumption implements Serializable {
    private long timestamp;
    private String deviceId;
    private float energyConsumption;

    public DeviceEnergyConsumption(String deviceId, float energyConsumption, long timestamp){
        this.deviceId = deviceId;
        this.energyConsumption = energyConsumption;
        this.timestamp = timestamp;
    }


    @Override
    public String toString(){
        return "Timestamp: "+ timestamp +"\nDeviceId: " + deviceId+ "\n Energy Consumption: "+energyConsumption+" kWh.";
    }
}
