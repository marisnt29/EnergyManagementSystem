package com.user.usermanagement.repository;

import com.user.usermanagement.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface AccountRepository  extends JpaRepository<Account, Long> {




    @Query("select a from Account a where a.name = ?1 and a.password = ?2")
    Optional<Account> findByNameAndPassword(String name, String password);

    Optional<Account> findByName(String name);
}
