package com.wiener.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wiener.app.domain.ChestItem;
import com.wiener.app.repository.ChestItemRepository;
import com.wiener.app.web.rest.errors.BadRequestAlertException;
import com.wiener.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ChestItem.
 */
@RestController
@RequestMapping("/api")
public class ChestItemResource {

    private final Logger log = LoggerFactory.getLogger(ChestItemResource.class);

    private static final String ENTITY_NAME = "chestItem";

    private final ChestItemRepository chestItemRepository;

    public ChestItemResource(ChestItemRepository chestItemRepository) {
        this.chestItemRepository = chestItemRepository;
    }

    /**
     * POST  /chest-items : Create a new chestItem.
     *
     * @param chestItem the chestItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chestItem, or with status 400 (Bad Request) if the chestItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chest-items")
    @Timed
    public ResponseEntity<ChestItem> createChestItem(@RequestBody ChestItem chestItem) throws URISyntaxException {
        log.debug("REST request to save ChestItem : {}", chestItem);
        if (chestItem.getId() != null) {
            throw new BadRequestAlertException("A new chestItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChestItem result = chestItemRepository.save(chestItem);
        return ResponseEntity.created(new URI("/api/chest-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chest-items : Updates an existing chestItem.
     *
     * @param chestItem the chestItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chestItem,
     * or with status 400 (Bad Request) if the chestItem is not valid,
     * or with status 500 (Internal Server Error) if the chestItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chest-items")
    @Timed
    public ResponseEntity<ChestItem> updateChestItem(@RequestBody ChestItem chestItem) throws URISyntaxException {
        log.debug("REST request to update ChestItem : {}", chestItem);
        if (chestItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChestItem result = chestItemRepository.save(chestItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chestItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chest-items : get all the chestItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chestItems in body
     */
    @GetMapping("/chest-items")
    @Timed
    public List<ChestItem> getAllChestItems() {
        log.debug("REST request to get all ChestItems");
        return chestItemRepository.findAll();
    }

    /**
     * GET  /chest-items/:id : get the "id" chestItem.
     *
     * @param id the id of the chestItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chestItem, or with status 404 (Not Found)
     */
    @GetMapping("/chest-items/{id}")
    @Timed
    public ResponseEntity<ChestItem> getChestItem(@PathVariable Long id) {
        log.debug("REST request to get ChestItem : {}", id);
        Optional<ChestItem> chestItem = chestItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(chestItem);
    }

    /**
     * DELETE  /chest-items/:id : delete the "id" chestItem.
     *
     * @param id the id of the chestItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chest-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteChestItem(@PathVariable Long id) {
        log.debug("REST request to delete ChestItem : {}", id);

        chestItemRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
