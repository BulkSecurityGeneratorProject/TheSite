package com.wiener.app.web.rest;

import com.wiener.app.WienerApp;

import com.wiener.app.domain.Chest;
import com.wiener.app.repository.ChestRepository;
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
 * Test class for the ChestResource REST controller.
 *
 * @see ChestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WienerApp.class)
public class ChestResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ROLL_PRICE = 1;
    private static final Integer UPDATED_ROLL_PRICE = 2;

    private static final Long DEFAULT_NUM_OF_TIMES_ROLLED = 1L;
    private static final Long UPDATED_NUM_OF_TIMES_ROLLED = 2L;

    @Autowired
    private ChestRepository chestRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChestMockMvc;

    private Chest chest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChestResource chestResource = new ChestResource(chestRepository);
        this.restChestMockMvc = MockMvcBuilders.standaloneSetup(chestResource)
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
    public static Chest createEntity(EntityManager em) {
        Chest chest = new Chest()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .rollPrice(DEFAULT_ROLL_PRICE)
            .numOfTimesRolled(DEFAULT_NUM_OF_TIMES_ROLLED);
        return chest;
    }

    @Before
    public void initTest() {
        chest = createEntity(em);
    }

    @Test
    @Transactional
    public void createChest() throws Exception {
        int databaseSizeBeforeCreate = chestRepository.findAll().size();

        // Create the Chest
        restChestMockMvc.perform(post("/api/chests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chest)))
            .andExpect(status().isCreated());

        // Validate the Chest in the database
        List<Chest> chestList = chestRepository.findAll();
        assertThat(chestList).hasSize(databaseSizeBeforeCreate + 1);
        Chest testChest = chestList.get(chestList.size() - 1);
        assertThat(testChest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChest.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testChest.getRollPrice()).isEqualTo(DEFAULT_ROLL_PRICE);
        assertThat(testChest.getNumOfTimesRolled()).isEqualTo(DEFAULT_NUM_OF_TIMES_ROLLED);
    }

    @Test
    @Transactional
    public void createChestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chestRepository.findAll().size();

        // Create the Chest with an existing ID
        chest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChestMockMvc.perform(post("/api/chests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chest)))
            .andExpect(status().isBadRequest());

        // Validate the Chest in the database
        List<Chest> chestList = chestRepository.findAll();
        assertThat(chestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllChests() throws Exception {
        // Initialize the database
        chestRepository.saveAndFlush(chest);

        // Get all the chestList
        restChestMockMvc.perform(get("/api/chests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chest.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rollPrice").value(hasItem(DEFAULT_ROLL_PRICE)))
            .andExpect(jsonPath("$.[*].numOfTimesRolled").value(hasItem(DEFAULT_NUM_OF_TIMES_ROLLED.intValue())));
    }
    
    @Test
    @Transactional
    public void getChest() throws Exception {
        // Initialize the database
        chestRepository.saveAndFlush(chest);

        // Get the chest
        restChestMockMvc.perform(get("/api/chests/{id}", chest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chest.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.rollPrice").value(DEFAULT_ROLL_PRICE))
            .andExpect(jsonPath("$.numOfTimesRolled").value(DEFAULT_NUM_OF_TIMES_ROLLED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingChest() throws Exception {
        // Get the chest
        restChestMockMvc.perform(get("/api/chests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChest() throws Exception {
        // Initialize the database
        chestRepository.saveAndFlush(chest);

        int databaseSizeBeforeUpdate = chestRepository.findAll().size();

        // Update the chest
        Chest updatedChest = chestRepository.findById(chest.getId()).get();
        // Disconnect from session so that the updates on updatedChest are not directly saved in db
        em.detach(updatedChest);
        updatedChest
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .rollPrice(UPDATED_ROLL_PRICE)
            .numOfTimesRolled(UPDATED_NUM_OF_TIMES_ROLLED);

        restChestMockMvc.perform(put("/api/chests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChest)))
            .andExpect(status().isOk());

        // Validate the Chest in the database
        List<Chest> chestList = chestRepository.findAll();
        assertThat(chestList).hasSize(databaseSizeBeforeUpdate);
        Chest testChest = chestList.get(chestList.size() - 1);
        assertThat(testChest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChest.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testChest.getRollPrice()).isEqualTo(UPDATED_ROLL_PRICE);
        assertThat(testChest.getNumOfTimesRolled()).isEqualTo(UPDATED_NUM_OF_TIMES_ROLLED);
    }

    @Test
    @Transactional
    public void updateNonExistingChest() throws Exception {
        int databaseSizeBeforeUpdate = chestRepository.findAll().size();

        // Create the Chest

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChestMockMvc.perform(put("/api/chests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chest)))
            .andExpect(status().isBadRequest());

        // Validate the Chest in the database
        List<Chest> chestList = chestRepository.findAll();
        assertThat(chestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChest() throws Exception {
        // Initialize the database
        chestRepository.saveAndFlush(chest);

        int databaseSizeBeforeDelete = chestRepository.findAll().size();

        // Get the chest
        restChestMockMvc.perform(delete("/api/chests/{id}", chest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Chest> chestList = chestRepository.findAll();
        assertThat(chestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chest.class);
        Chest chest1 = new Chest();
        chest1.setId(1L);
        Chest chest2 = new Chest();
        chest2.setId(chest1.getId());
        assertThat(chest1).isEqualTo(chest2);
        chest2.setId(2L);
        assertThat(chest1).isNotEqualTo(chest2);
        chest1.setId(null);
        assertThat(chest1).isNotEqualTo(chest2);
    }
}
