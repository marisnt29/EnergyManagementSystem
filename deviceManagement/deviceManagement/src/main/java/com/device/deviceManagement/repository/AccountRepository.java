package com.device.deviceManagement.repository;

import com.device.deviceManagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {



 @Query("select a from Account a where a.accountID = ?1")
 Optional<Account> findAccountByAccountID(Long accountId);
}
