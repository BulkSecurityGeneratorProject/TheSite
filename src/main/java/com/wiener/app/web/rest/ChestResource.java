package com.wiener.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wiener.app.domain.Chest;
import com.wiener.app.repository.ChestRepository;
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
 * REST controller for managing Chest.
 */
@RestController
@RequestMapping("/api")
public class ChestResource {

    private final Logger log = LoggerFactory.getLogger(ChestResource.class);

    private static final String ENTITY_NAME = "chest";

    private final ChestRepository chestRepository;

    public ChestResource(ChestRepository chestRepository) {
        this.chestRepository = chestRepository;
    }

    /**
     * POST  /chests : Create a new chest.
     *
     * @param chest the chest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chest, or with status 400 (Bad Request) if the chest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chests")
    @Timed
    public ResponseEntity<Chest> createChest(@RequestBody Chest chest) throws URISyntaxException {
        log.debug("REST request to save Chest : {}", chest);
        if (chest.getId() != null) {
            throw new BadRequestAlertException("A new chest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Chest result = chestRepository.save(chest);
        return ResponseEntity.created(new URI("/api/chests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chests : Updates an existing chest.
     *
     * @param chest the chest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chest,
     * or with status 400 (Bad Request) if the chest is not valid,
     * or with status 500 (Internal Server Error) if the chest couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chests")
    @Timed
    public ResponseEntity<Chest> updateChest(@RequestBody Chest chest) throws URISyntaxException {
        log.debug("REST request to update Chest : {}", chest);
        if (chest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Chest result = chestRepository.save(chest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chests : get all the chests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chests in body
     */
    @GetMapping("/chests")
    @Timed
    public List<Chest> getAllChests() {
        log.debug("REST request to get all Chests");
        return chestRepository.findAll();
    }

    /**
     * GET  /chests/:id : get the "id" chest.
     *
     * @param id the id of the chest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chest, or with status 404 (Not Found)
     */
    @GetMapping("/chests/{id}")
    @Timed
    public ResponseEntity<Chest> getChest(@PathVariable Long id) {
        log.debug("REST request to get Chest : {}", id);
        Optional<Chest> chest = chestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(chest);
    }

    /**
     * DELETE  /chests/:id : delete the "id" chest.
     *
     * @param id the id of the chest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chests/{id}")
    @Timed
    public ResponseEntity<Void> deleteChest(@PathVariable Long id) {
        log.debug("REST request to delete Chest : {}", id);

        chestRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
