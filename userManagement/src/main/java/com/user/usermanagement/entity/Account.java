package com.user.usermanagement.entity;



import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "account", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Account {


    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false,unique = true)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "isAdmin",nullable = false)
    private Boolean isAdmin;


}
