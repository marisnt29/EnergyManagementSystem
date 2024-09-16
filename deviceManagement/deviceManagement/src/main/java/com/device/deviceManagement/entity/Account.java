package com.device.deviceManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "account", uniqueConstraints = @UniqueConstraint(columnNames = "account_id"))
public class Account {


    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "account_id", nullable = false,unique = true)
    private Long accountID;

    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Device> devices;


}
