package com.wiener.app.repository;

import com.wiener.app.domain.MoneyTransfer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MoneyTransfer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoneyTransferRepository extends JpaRepository<MoneyTransfer, Long> {

}
