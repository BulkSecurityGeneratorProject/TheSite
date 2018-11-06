package com.wiener.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wiener.app.domain.MoneyTransfer;
import com.wiener.app.repository.MoneyTransferRepository;
import com.wiener.app.web.rest.errors.BadRequestAlertException;
import com.wiener.app.web.rest.util.HeaderUtil;
import com.wiener.app.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MoneyTransfer.
 */
@RestController
@RequestMapping("/api")
public class MoneyTransferResource {

    private final Logger log = LoggerFactory.getLogger(MoneyTransferResource.class);

    private static final String ENTITY_NAME = "moneyTransfer";

    private final MoneyTransferRepository moneyTransferRepository;

    public MoneyTransferResource(MoneyTransferRepository moneyTransferRepository) {
        this.moneyTransferRepository = moneyTransferRepository;
    }

    /**
     * POST  /money-transfers : Create a new moneyTransfer.
     *
     * @param moneyTransfer the moneyTransfer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moneyTransfer, or with status 400 (Bad Request) if the moneyTransfer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/money-transfers")
    @Timed
    public ResponseEntity<MoneyTransfer> createMoneyTransfer(@RequestBody MoneyTransfer moneyTransfer) throws URISyntaxException {
        log.debug("REST request to save MoneyTransfer : {}", moneyTransfer);
        if (moneyTransfer.getId() != null) {
            throw new BadRequestAlertException("A new moneyTransfer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoneyTransfer result = moneyTransferRepository.save(moneyTransfer);
        return ResponseEntity.created(new URI("/api/money-transfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /money-transfers : Updates an existing moneyTransfer.
     *
     * @param moneyTransfer the moneyTransfer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moneyTransfer,
     * or with status 400 (Bad Request) if the moneyTransfer is not valid,
     * or with status 500 (Internal Server Error) if the moneyTransfer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/money-transfers")
    @Timed
    public ResponseEntity<MoneyTransfer> updateMoneyTransfer(@RequestBody MoneyTransfer moneyTransfer) throws URISyntaxException {
        log.debug("REST request to update MoneyTransfer : {}", moneyTransfer);
        if (moneyTransfer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MoneyTransfer result = moneyTransferRepository.save(moneyTransfer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, moneyTransfer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /money-transfers : get all the moneyTransfers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of moneyTransfers in body
     */
    @GetMapping("/money-transfers")
    @Timed
    public ResponseEntity<List<MoneyTransfer>> getAllMoneyTransfers(Pageable pageable) {
        log.debug("REST request to get a page of MoneyTransfers");
        Page<MoneyTransfer> page = moneyTransferRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/money-transfers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /money-transfers/:id : get the "id" moneyTransfer.
     *
     * @param id the id of the moneyTransfer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moneyTransfer, or with status 404 (Not Found)
     */
    @GetMapping("/money-transfers/{id}")
    @Timed
    public ResponseEntity<MoneyTransfer> getMoneyTransfer(@PathVariable Long id) {
        log.debug("REST request to get MoneyTransfer : {}", id);
        Optional<MoneyTransfer> moneyTransfer = moneyTransferRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(moneyTransfer);
    }

    /**
     * DELETE  /money-transfers/:id : delete the "id" moneyTransfer.
     *
     * @param id the id of the moneyTransfer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/money-transfers/{id}")
    @Timed
    public ResponseEntity<Void> deleteMoneyTransfer(@PathVariable Long id) {
        log.debug("REST request to delete MoneyTransfer : {}", id);

        moneyTransferRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
