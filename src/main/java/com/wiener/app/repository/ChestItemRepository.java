package com.wiener.app.repository;

import com.wiener.app.domain.ChestItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ChestItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChestItemRepository extends JpaRepository<ChestItem, Long> {

}
