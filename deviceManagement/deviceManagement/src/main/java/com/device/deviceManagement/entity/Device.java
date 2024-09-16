package com.device.deviceManagement.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "device")
public class Device {

        @Id
        @GeneratedValue
        private Long id;

        @Column(name = "max_consumption", nullable = false)
        private Float maximumConsumption;

        @Column(name = "description", nullable = false)
        private String description;

        @Column(name = "location", nullable = false)
        private String location;


        @ManyToOne
        @JoinColumn(name = "account_id")
        private Account account;


}
