package com.wiener.app.web.rest;

import com.wiener.app.WienerApp;

import com.wiener.app.domain.MoneyTransfer;
import com.wiener.app.repository.MoneyTransferRepository;
import com.wiener.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.wiener.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MoneyTransferResource REST controller.
 *
 * @see MoneyTransferResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WienerApp.class)
public class MoneyTransferResourceIntTest {

    private static final Integer DEFAULT_PAYED_AMOUNT = 1;
    private static final Integer UPDATED_PAYED_AMOUNT = 2;

    private static final LocalDate DEFAULT_PAYED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYED_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PAYED_IN_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_PAYED_IN_CURRENCY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PAYMENT_SUCCESSFUL = false;
    private static final Boolean UPDATED_PAYMENT_SUCCESSFUL = true;

    private static final String DEFAULT_PAYMENT_MODE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_MODE = "BBBBBBBBBB";

    @Autowired
    private MoneyTransferRepository moneyTransferRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMoneyTransferMockMvc;

    private MoneyTransfer moneyTransfer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MoneyTransferResource moneyTransferResource = new MoneyTransferResource(moneyTransferRepository);
        this.restMoneyTransferMockMvc = MockMvcBuilders.standaloneSetup(moneyTransferResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoneyTransfer createEntity(EntityManager em) {
        MoneyTransfer moneyTransfer = new MoneyTransfer()
            .payedAmount(DEFAULT_PAYED_AMOUNT)
            .payedTime(DEFAULT_PAYED_TIME)
            .payedInCurrency(DEFAULT_PAYED_IN_CURRENCY)
            .paymentSuccessful(DEFAULT_PAYMENT_SUCCESSFUL)
            .paymentMode(DEFAULT_PAYMENT_MODE);
        return moneyTransfer;
    }

    @Before
    public void initTest() {
        moneyTransfer = createEntity(em);
    }

    @Test
    @Transactional
    public void createMoneyTransfer() throws Exception {
        int databaseSizeBeforeCreate = moneyTransferRepository.findAll().size();

        // Create the MoneyTransfer
        restMoneyTransferMockMvc.perform(post("/api/money-transfers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moneyTransfer)))
            .andExpect(status().isCreated());

        // Validate the MoneyTransfer in the database
        List<MoneyTransfer> moneyTransferList = moneyTransferRepository.findAll();
        assertThat(moneyTransferList).hasSize(databaseSizeBeforeCreate + 1);
        MoneyTransfer testMoneyTransfer = moneyTransferList.get(moneyTransferList.size() - 1);
        assertThat(testMoneyTransfer.getPayedAmount()).isEqualTo(DEFAULT_PAYED_AMOUNT);
        assertThat(testMoneyTransfer.getPayedTime()).isEqualTo(DEFAULT_PAYED_TIME);
        assertThat(testMoneyTransfer.getPayedInCurrency()).isEqualTo(DEFAULT_PAYED_IN_CURRENCY);
        assertThat(testMoneyTransfer.isPaymentSuccessful()).isEqualTo(DEFAULT_PAYMENT_SUCCESSFUL);
        assertThat(testMoneyTransfer.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
    }

    @Test
    @Transactional
    public void createMoneyTransferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moneyTransferRepository.findAll().size();

        // Create the MoneyTransfer with an existing ID
        moneyTransfer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoneyTransferMockMvc.perform(post("/api/money-transfers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moneyTransfer)))
            .andExpect(status().isBadRequest());

        // Validate the MoneyTransfer in the database
        List<MoneyTransfer> moneyTransferList = moneyTransferRepository.findAll();
        assertThat(moneyTransferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMoneyTransfers() throws Exception {
        // Initialize the database
        moneyTransferRepository.saveAndFlush(moneyTransfer);

        // Get all the moneyTransferList
        restMoneyTransferMockMvc.perform(get("/api/money-transfers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moneyTransfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].payedAmount").value(hasItem(DEFAULT_PAYED_AMOUNT)))
            .andExpect(jsonPath("$.[*].payedTime").value(hasItem(DEFAULT_PAYED_TIME.toString())))
            .andExpect(jsonPath("$.[*].payedInCurrency").value(hasItem(DEFAULT_PAYED_IN_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].paymentSuccessful").value(hasItem(DEFAULT_PAYMENT_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())));
    }
    
    @Test
    @Transactional
    public void getMoneyTransfer() throws Exception {
        // Initialize the database
        moneyTransferRepository.saveAndFlush(moneyTransfer);

        // Get the moneyTransfer
        restMoneyTransferMockMvc.perform(get("/api/money-transfers/{id}", moneyTransfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moneyTransfer.getId().intValue()))
            .andExpect(jsonPath("$.payedAmount").value(DEFAULT_PAYED_AMOUNT))
            .andExpect(jsonPath("$.payedTime").value(DEFAULT_PAYED_TIME.toString()))
            .andExpect(jsonPath("$.payedInCurrency").value(DEFAULT_PAYED_IN_CURRENCY.toString()))
            .andExpect(jsonPath("$.paymentSuccessful").value(DEFAULT_PAYMENT_SUCCESSFUL.booleanValue()))
            .andExpect(jsonPath("$.paymentMode").value(DEFAULT_PAYMENT_MODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMoneyTransfer() throws Exception {
        // Get the moneyTransfer
        restMoneyTransferMockMvc.perform(get("/api/money-transfers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMoneyTransfer() throws Exception {
        // Initialize the database
        moneyTransferRepository.saveAndFlush(moneyTransfer);

        int databaseSizeBeforeUpdate = moneyTransferRepository.findAll().size();

        // Update the moneyTransfer
        MoneyTransfer updatedMoneyTransfer = moneyTransferRepository.findById(moneyTransfer.getId()).get();
        // Disconnect from session so that the updates on updatedMoneyTransfer are not directly saved in db
        em.detach(updatedMoneyTransfer);
        updatedMoneyTransfer
            .payedAmount(UPDATED_PAYED_AMOUNT)
            .payedTime(UPDATED_PAYED_TIME)
            .payedInCurrency(UPDATED_PAYED_IN_CURRENCY)
            .paymentSuccessful(UPDATED_PAYMENT_SUCCESSFUL)
            .paymentMode(UPDATED_PAYMENT_MODE);

        restMoneyTransferMockMvc.perform(put("/api/money-transfers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMoneyTransfer)))
            .andExpect(status().isOk());

        // Validate the MoneyTransfer in the database
        List<MoneyTransfer> moneyTransferList = moneyTransferRepository.findAll();
        assertThat(moneyTransferList).hasSize(databaseSizeBeforeUpdate);
        MoneyTransfer testMoneyTransfer = moneyTransferList.get(moneyTransferList.size() - 1);
        assertThat(testMoneyTransfer.getPayedAmount()).isEqualTo(UPDATED_PAYED_AMOUNT);
        assertThat(testMoneyTransfer.getPayedTime()).isEqualTo(UPDATED_PAYED_TIME);
        assertThat(testMoneyTransfer.getPayedInCurrency()).isEqualTo(UPDATED_PAYED_IN_CURRENCY);
        assertThat(testMoneyTransfer.isPaymentSuccessful()).isEqualTo(UPDATED_PAYMENT_SUCCESSFUL);
        assertThat(testMoneyTransfer.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
    }

    @Test
    @Transactional
    public void updateNonExistingMoneyTransfer() throws Exception {
        int databaseSizeBeforeUpdate = moneyTransferRepository.findAll().size();

        // Create the MoneyTransfer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoneyTransferMockMvc.perform(put("/api/money-transfers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moneyTransfer)))
            .andExpect(status().isBadRequest());

        // Validate the MoneyTransfer in the database
        List<MoneyTransfer> moneyTransferList = moneyTransferRepository.findAll();
        assertThat(moneyTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMoneyTransfer() throws Exception {
        // Initialize the database
        moneyTransferRepository.saveAndFlush(moneyTransfer);

        int databaseSizeBeforeDelete = moneyTransferRepository.findAll().size();

        // Get the moneyTransfer
        restMoneyTransferMockMvc.perform(delete("/api/money-transfers/{id}", moneyTransfer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MoneyTransfer> moneyTransferList = moneyTransferRepository.findAll();
        assertThat(moneyTransferList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoneyTransfer.class);
        MoneyTransfer moneyTransfer1 = new MoneyTransfer();
        moneyTransfer1.setId(1L);
        MoneyTransfer moneyTransfer2 = new MoneyTransfer();
        moneyTransfer2.setId(moneyTransfer1.getId());
        assertThat(moneyTransfer1).isEqualTo(moneyTransfer2);
        moneyTransfer2.setId(2L);
        assertThat(moneyTransfer1).isNotEqualTo(moneyTransfer2);
        moneyTransfer1.setId(null);
        assertThat(moneyTransfer1).isNotEqualTo(moneyTransfer2);
    }
}
