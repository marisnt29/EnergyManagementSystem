package com.monitoring.monitoringService.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "device")
public class Device {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "deviceID")
    private Long deviceID;

    @Column(name = "max_consumption")
    private Float maxConsumption;




}
