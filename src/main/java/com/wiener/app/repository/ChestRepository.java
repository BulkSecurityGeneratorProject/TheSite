package com.wiener.app.repository;

import com.wiener.app.domain.Chest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Chest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChestRepository extends JpaRepository<Chest, Long> {

}
