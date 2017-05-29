package com.srms.web.rest;

import com.srms.SrmsApp;

import com.srms.domain.Schueler;
import com.srms.repository.SchuelerRepository;
import com.srms.service.SchuelerService;
import com.srms.service.dto.SchuelerDTO;
import com.srms.service.mapper.SchuelerMapper;
import com.srms.web.rest.errors.ExceptionTranslator;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.srms.domain.enumeration.Schulform;
/**
 * Test class for the SchuelerResource REST controller.
 *
 * @see SchuelerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SrmsApp.class)
public class SchuelerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VORNAME = "AAAAAAAAAA";
    private static final String UPDATED_VORNAME = "BBBBBBBBBB";

    private static final Schulform DEFAULT_SCHULFORM = Schulform.HAUPTSCHULE;
    private static final Schulform UPDATED_SCHULFORM = Schulform.REALSCHULE;

    @Autowired
    private SchuelerRepository schuelerRepository;

    @Autowired
    private SchuelerMapper schuelerMapper;

    @Autowired
    private SchuelerService schuelerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchuelerMockMvc;

    private Schueler schueler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchuelerResource schuelerResource = new SchuelerResource(schuelerService);
        this.restSchuelerMockMvc = MockMvcBuilders.standaloneSetup(schuelerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schueler createEntity(EntityManager em) {
        Schueler schueler = new Schueler()
            .name(DEFAULT_NAME)
            .vorname(DEFAULT_VORNAME)
            .schulform(DEFAULT_SCHULFORM);
        return schueler;
    }

    @Before
    public void initTest() {
        schueler = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchueler() throws Exception {
        int databaseSizeBeforeCreate = schuelerRepository.findAll().size();

        // Create the Schueler
        SchuelerDTO schuelerDTO = schuelerMapper.toDto(schueler);
        restSchuelerMockMvc.perform(post("/api/schuelers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schuelerDTO)))
            .andExpect(status().isCreated());

        // Validate the Schueler in the database
        List<Schueler> schuelerList = schuelerRepository.findAll();
        assertThat(schuelerList).hasSize(databaseSizeBeforeCreate + 1);
        Schueler testSchueler = schuelerList.get(schuelerList.size() - 1);
        assertThat(testSchueler.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchueler.getVorname()).isEqualTo(DEFAULT_VORNAME);
        assertThat(testSchueler.getSchulform()).isEqualTo(DEFAULT_SCHULFORM);
    }

    @Test
    @Transactional
    public void createSchuelerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schuelerRepository.findAll().size();

        // Create the Schueler with an existing ID
        schueler.setId(1L);
        SchuelerDTO schuelerDTO = schuelerMapper.toDto(schueler);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchuelerMockMvc.perform(post("/api/schuelers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schuelerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Schueler> schuelerList = schuelerRepository.findAll();
        assertThat(schuelerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schuelerRepository.findAll().size();
        // set the field null
        schueler.setName(null);

        // Create the Schueler, which fails.
        SchuelerDTO schuelerDTO = schuelerMapper.toDto(schueler);

        restSchuelerMockMvc.perform(post("/api/schuelers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schuelerDTO)))
            .andExpect(status().isBadRequest());

        List<Schueler> schuelerList = schuelerRepository.findAll();
        assertThat(schuelerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVornameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schuelerRepository.findAll().size();
        // set the field null
        schueler.setVorname(null);

        // Create the Schueler, which fails.
        SchuelerDTO schuelerDTO = schuelerMapper.toDto(schueler);

        restSchuelerMockMvc.perform(post("/api/schuelers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schuelerDTO)))
            .andExpect(status().isBadRequest());

        List<Schueler> schuelerList = schuelerRepository.findAll();
        assertThat(schuelerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSchulformIsRequired() throws Exception {
        int databaseSizeBeforeTest = schuelerRepository.findAll().size();
        // set the field null
        schueler.setSchulform(null);

        // Create the Schueler, which fails.
        SchuelerDTO schuelerDTO = schuelerMapper.toDto(schueler);

        restSchuelerMockMvc.perform(post("/api/schuelers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schuelerDTO)))
            .andExpect(status().isBadRequest());

        List<Schueler> schuelerList = schuelerRepository.findAll();
        assertThat(schuelerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchuelers() throws Exception {
        // Initialize the database
        schuelerRepository.saveAndFlush(schueler);

        // Get all the schuelerList
        restSchuelerMockMvc.perform(get("/api/schuelers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schueler.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].vorname").value(hasItem(DEFAULT_VORNAME.toString())))
            .andExpect(jsonPath("$.[*].schulform").value(hasItem(DEFAULT_SCHULFORM.toString())));
    }

    @Test
    @Transactional
    public void getSchueler() throws Exception {
        // Initialize the database
        schuelerRepository.saveAndFlush(schueler);

        // Get the schueler
        restSchuelerMockMvc.perform(get("/api/schuelers/{id}", schueler.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schueler.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.vorname").value(DEFAULT_VORNAME.toString()))
            .andExpect(jsonPath("$.schulform").value(DEFAULT_SCHULFORM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchueler() throws Exception {
        // Get the schueler
        restSchuelerMockMvc.perform(get("/api/schuelers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchueler() throws Exception {
        // Initialize the database
        schuelerRepository.saveAndFlush(schueler);
        int databaseSizeBeforeUpdate = schuelerRepository.findAll().size();

        // Update the schueler
        Schueler updatedSchueler = schuelerRepository.findOne(schueler.getId());
        updatedSchueler
            .name(UPDATED_NAME)
            .vorname(UPDATED_VORNAME)
            .schulform(UPDATED_SCHULFORM);
        SchuelerDTO schuelerDTO = schuelerMapper.toDto(updatedSchueler);

        restSchuelerMockMvc.perform(put("/api/schuelers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schuelerDTO)))
            .andExpect(status().isOk());

        // Validate the Schueler in the database
        List<Schueler> schuelerList = schuelerRepository.findAll();
        assertThat(schuelerList).hasSize(databaseSizeBeforeUpdate);
        Schueler testSchueler = schuelerList.get(schuelerList.size() - 1);
        assertThat(testSchueler.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchueler.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testSchueler.getSchulform()).isEqualTo(UPDATED_SCHULFORM);
    }

    @Test
    @Transactional
    public void updateNonExistingSchueler() throws Exception {
        int databaseSizeBeforeUpdate = schuelerRepository.findAll().size();

        // Create the Schueler
        SchuelerDTO schuelerDTO = schuelerMapper.toDto(schueler);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchuelerMockMvc.perform(put("/api/schuelers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schuelerDTO)))
            .andExpect(status().isCreated());

        // Validate the Schueler in the database
        List<Schueler> schuelerList = schuelerRepository.findAll();
        assertThat(schuelerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchueler() throws Exception {
        // Initialize the database
        schuelerRepository.saveAndFlush(schueler);
        int databaseSizeBeforeDelete = schuelerRepository.findAll().size();

        // Get the schueler
        restSchuelerMockMvc.perform(delete("/api/schuelers/{id}", schueler.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Schueler> schuelerList = schuelerRepository.findAll();
        assertThat(schuelerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Schueler.class);
        Schueler schueler1 = new Schueler();
        schueler1.setId(1L);
        Schueler schueler2 = new Schueler();
        schueler2.setId(schueler1.getId());
        assertThat(schueler1).isEqualTo(schueler2);
        schueler2.setId(2L);
        assertThat(schueler1).isNotEqualTo(schueler2);
        schueler1.setId(null);
        assertThat(schueler1).isNotEqualTo(schueler2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchuelerDTO.class);
        SchuelerDTO schuelerDTO1 = new SchuelerDTO();
        schuelerDTO1.setId(1L);
        SchuelerDTO schuelerDTO2 = new SchuelerDTO();
        assertThat(schuelerDTO1).isNotEqualTo(schuelerDTO2);
        schuelerDTO2.setId(schuelerDTO1.getId());
        assertThat(schuelerDTO1).isEqualTo(schuelerDTO2);
        schuelerDTO2.setId(2L);
        assertThat(schuelerDTO1).isNotEqualTo(schuelerDTO2);
        schuelerDTO1.setId(null);
        assertThat(schuelerDTO1).isNotEqualTo(schuelerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(schuelerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(schuelerMapper.fromId(null)).isNull();
    }
}
