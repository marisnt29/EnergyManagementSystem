package com.user.usermanagement.dtos;

import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountDTO {

    private Long id;

    private String name;

    private String password;

    private Boolean isAdmin;

    private String token;

}
