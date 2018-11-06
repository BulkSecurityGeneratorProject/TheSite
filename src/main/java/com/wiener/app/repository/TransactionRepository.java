package com.wiener.app.repository;

import com.wiener.app.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Transaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select distinct transaction from Transaction transaction left join fetch transaction.chestItems",
        countQuery = "select count(distinct transaction) from Transaction transaction")
    Page<Transaction> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct transaction from Transaction transaction left join fetch transaction.chestItems")
    List<Transaction> findAllWithEagerRelationships();

    @Query("select transaction from Transaction transaction left join fetch transaction.chestItems where transaction.id =:id")
    Optional<Transaction> findOneWithEagerRelationships(@Param("id") Long id);

}
