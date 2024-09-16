package com.monitoring.monitoringService.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "consumption")
public class DeviceConsumption {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "deviceID")
    private Long deviceID;
    @Column(name = "consumption")
    private Float energyConsumption;
    @Column(name = "timestamp")
    private Long timestamp;



}
