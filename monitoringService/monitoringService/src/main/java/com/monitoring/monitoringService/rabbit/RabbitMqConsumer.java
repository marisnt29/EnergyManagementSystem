package com.monitoring.monitoringService.rabbit;

import com.monitoring.monitoringService.builders.DeviceConsumptionBuilder;
import com.monitoring.monitoringService.model.DeviceConsumption;
import com.monitoring.monitoringService.model.DeviceEnergyConsumption;

import com.monitoring.monitoringService.services.DeviceConsumptionService;
import com.monitoring.monitoringService.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RabbitMqConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqConsumer.class);
    private static Float currentConsumption = Float.valueOf(0);
    private final DeviceConsumptionService deviceConsumptionService;
    private final DeviceService deviceService;
    private final SimpMessagingTemplate template;

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(DeviceEnergyConsumption device) {
        if (device == null) return;
        LOGGER.info("Massage received and consumed -> " + device.toString());

        DeviceConsumption deviceConsumption = new DeviceConsumption();
        deviceConsumption.setDeviceID(device.getDeviceId());
        deviceConsumption.setEnergyConsumption(device.getEnergyConsumption());
        deviceConsumption.setTimestamp(device.getTimestamp());

        currentConsumption+=deviceConsumption.getEnergyConsumption();

//        if(currentConsumption > deviceService.getDeviceByDeviceID(deviceConsumption.getDeviceID()).getMaxConsumption())
//            this.template.convertAndSend("/topic/socket/user",
//                    "Device with ID  " + deviceConsumption.getDeviceID() +" has reached his maximum consumption");

        deviceConsumptionService.save(DeviceConsumptionBuilder.toDTO(deviceConsumption));

    }





}
