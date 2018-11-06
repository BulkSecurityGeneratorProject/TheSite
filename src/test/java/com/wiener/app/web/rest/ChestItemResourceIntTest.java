package com.wiener.app.web.rest;

import com.wiener.app.WienerApp;

import com.wiener.app.domain.ChestItem;
import com.wiener.app.repository.ChestItemRepository;
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
import java.util.List;


import static com.wiener.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ChestItemResource REST controller.
 *
 * @see ChestItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WienerApp.class)
public class ChestItemResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final Long DEFAULT_NUM_OF_TIMES_ROLLED = 1L;
    private static final Long UPDATED_NUM_OF_TIMES_ROLLED = 2L;

    private static final Long DEFAULT_NUM_OF_TIMES_ACCEPTED = 1L;
    private static final Long UPDATED_NUM_OF_TIMES_ACCEPTED = 2L;

    @Autowired
    private ChestItemRepository chestItemRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChestItemMockMvc;

    private ChestItem chestItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChestItemResource chestItemResource = new ChestItemResource(chestItemRepository);
        this.restChestItemMockMvc = MockMvcBuilders.standaloneSetup(chestItemResource)
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
    public static ChestItem createEntity(EntityManager em) {
        ChestItem chestItem = new ChestItem()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .price(DEFAULT_PRICE)
            .numOfTimesRolled(DEFAULT_NUM_OF_TIMES_ROLLED)
            .numOfTimesAccepted(DEFAULT_NUM_OF_TIMES_ACCEPTED);
        return chestItem;
    }

    @Before
    public void initTest() {
        chestItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createChestItem() throws Exception {
        int databaseSizeBeforeCreate = chestItemRepository.findAll().size();

        // Create the ChestItem
        restChestItemMockMvc.perform(post("/api/chest-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chestItem)))
            .andExpect(status().isCreated());

        // Validate the ChestItem in the database
        List<ChestItem> chestItemList = chestItemRepository.findAll();
        assertThat(chestItemList).hasSize(databaseSizeBeforeCreate + 1);
        ChestItem testChestItem = chestItemList.get(chestItemList.size() - 1);
        assertThat(testChestItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChestItem.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testChestItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testChestItem.getNumOfTimesRolled()).isEqualTo(DEFAULT_NUM_OF_TIMES_ROLLED);
        assertThat(testChestItem.getNumOfTimesAccepted()).isEqualTo(DEFAULT_NUM_OF_TIMES_ACCEPTED);
    }

    @Test
    @Transactional
    public void createChestItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chestItemRepository.findAll().size();

        // Create the ChestItem with an existing ID
        chestItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChestItemMockMvc.perform(post("/api/chest-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chestItem)))
            .andExpect(status().isBadRequest());

        // Validate the ChestItem in the database
        List<ChestItem> chestItemList = chestItemRepository.findAll();
        assertThat(chestItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllChestItems() throws Exception {
        // Initialize the database
        chestItemRepository.saveAndFlush(chestItem);

        // Get all the chestItemList
        restChestItemMockMvc.perform(get("/api/chest-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chestItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].numOfTimesRolled").value(hasItem(DEFAULT_NUM_OF_TIMES_ROLLED.intValue())))
            .andExpect(jsonPath("$.[*].numOfTimesAccepted").value(hasItem(DEFAULT_NUM_OF_TIMES_ACCEPTED.intValue())));
    }
    
    @Test
    @Transactional
    public void getChestItem() throws Exception {
        // Initialize the database
        chestItemRepository.saveAndFlush(chestItem);

        // Get the chestItem
        restChestItemMockMvc.perform(get("/api/chest-items/{id}", chestItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chestItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.numOfTimesRolled").value(DEFAULT_NUM_OF_TIMES_ROLLED.intValue()))
            .andExpect(jsonPath("$.numOfTimesAccepted").value(DEFAULT_NUM_OF_TIMES_ACCEPTED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingChestItem() throws Exception {
        // Get the chestItem
        restChestItemMockMvc.perform(get("/api/chest-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChestItem() throws Exception {
        // Initialize the database
        chestItemRepository.saveAndFlush(chestItem);

        int databaseSizeBeforeUpdate = chestItemRepository.findAll().size();

        // Update the chestItem
        ChestItem updatedChestItem = chestItemRepository.findById(chestItem.getId()).get();
        // Disconnect from session so that the updates on updatedChestItem are not directly saved in db
        em.detach(updatedChestItem);
        updatedChestItem
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .price(UPDATED_PRICE)
            .numOfTimesRolled(UPDATED_NUM_OF_TIMES_ROLLED)
            .numOfTimesAccepted(UPDATED_NUM_OF_TIMES_ACCEPTED);

        restChestItemMockMvc.perform(put("/api/chest-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChestItem)))
            .andExpect(status().isOk());

        // Validate the ChestItem in the database
        List<ChestItem> chestItemList = chestItemRepository.findAll();
        assertThat(chestItemList).hasSize(databaseSizeBeforeUpdate);
        ChestItem testChestItem = chestItemList.get(chestItemList.size() - 1);
        assertThat(testChestItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChestItem.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testChestItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testChestItem.getNumOfTimesRolled()).isEqualTo(UPDATED_NUM_OF_TIMES_ROLLED);
        assertThat(testChestItem.getNumOfTimesAccepted()).isEqualTo(UPDATED_NUM_OF_TIMES_ACCEPTED);
    }

    @Test
    @Transactional
    public void updateNonExistingChestItem() throws Exception {
        int databaseSizeBeforeUpdate = chestItemRepository.findAll().size();

        // Create the ChestItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChestItemMockMvc.perform(put("/api/chest-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chestItem)))
            .andExpect(status().isBadRequest());

        // Validate the ChestItem in the database
        List<ChestItem> chestItemList = chestItemRepository.findAll();
        assertThat(chestItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChestItem() throws Exception {
        // Initialize the database
        chestItemRepository.saveAndFlush(chestItem);

        int databaseSizeBeforeDelete = chestItemRepository.findAll().size();

        // Get the chestItem
        restChestItemMockMvc.perform(delete("/api/chest-items/{id}", chestItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChestItem> chestItemList = chestItemRepository.findAll();
        assertThat(chestItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChestItem.class);
        ChestItem chestItem1 = new ChestItem();
        chestItem1.setId(1L);
        ChestItem chestItem2 = new ChestItem();
        chestItem2.setId(chestItem1.getId());
        assertThat(chestItem1).isEqualTo(chestItem2);
        chestItem2.setId(2L);
        assertThat(chestItem1).isNotEqualTo(chestItem2);
        chestItem1.setId(null);
        assertThat(chestItem1).isNotEqualTo(chestItem2);
    }
}
