package com.reader.csvReader.producer;

import com.reader.csvReader.model.DeviceEnergyConsumption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqProducer.class);
    private final RabbitTemplate producerTemplate;
    @Autowired
    public RabbitMqProducer(RabbitTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    public void send(DeviceEnergyConsumption deviceEnergyConsumption){
        producerTemplate.convertAndSend(exchange, routingKey, deviceEnergyConsumption);
        LOGGER.info("Device consumption was sent -> \n"+ deviceEnergyConsumption.toString());
    }
}