package com.user.usermanagement.builders;

import com.user.usermanagement.dtos.AccountDTO;
import com.user.usermanagement.entity.Account;

public class AccountBuilder {

    public static AccountDTO toAccountDto (Account account)
    {
        return  AccountDTO.builder()
                .id(account.getId())
                .name(account.getName())
                .password(account.getPassword())
                .isAdmin(account.getIsAdmin())
                .token(null)
                .build();
    }

    public static Account toEntity(AccountDTO accountDTO)
    {
        return Account.builder()
                .id((accountDTO.getId()))
                .name(accountDTO.getName())
                .password(accountDTO.getPassword())
                .isAdmin(accountDTO.getIsAdmin())
                .build();
    }


}
