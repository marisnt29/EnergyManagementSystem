package com.reader.csvReader.producer;

import com.reader.csvReader.model.DeviceEnergyConsumption;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class SensorSimulator {
    private final RabbitMqProducer producer;

    @Value("${sensor.device.guid}")
    private String deviceGuid;

    @Value("${time.interval}")
    private int timeInterval;

    @Value("${sensor.path}")
    private String path;
    @PostConstruct
    public void simulateEnergyOnStartup() {
        simulateEnergy(path,timeInterval);
    }

    public void simulateEnergy(String path, int timeInterval){
        System.out.println("Generating data for sensor. With time interval of " + timeInterval + " minutes.");
        try {
            File sensorFile = new File(path);
            Scanner sensorScanner = new Scanner(sensorFile);
            while (sensorScanner.hasNextLine()) {
                long time = System.currentTimeMillis();
                String data = sensorScanner.nextLine();
                System.out.println("["+ time + "]" + "-Sensor has the energy consumption = " + data);

                DeviceEnergyConsumption deviceObject = this.createDeviceObject(data,time);
                producer.send(deviceObject);

                Thread.sleep((long) timeInterval  * 60 * 1000);
            }
            sensorScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private DeviceEnergyConsumption createDeviceObject(String energyConsumptionValue, long timestamp){
        float energyConsumption = Float.parseFloat(energyConsumptionValue);

        return new DeviceEnergyConsumption(deviceGuid,energyConsumption,timestamp);
    }

}
