package com.srms.web.rest;

import com.srms.SrmsApp;

import com.srms.domain.Anschrift;
import com.srms.repository.AnschriftRepository;
import com.srms.service.AnschriftService;
import com.srms.service.dto.AnschriftDTO;
import com.srms.service.mapper.AnschriftMapper;
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

/**
 * Test class for the AnschriftResource REST controller.
 *
 * @see AnschriftResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SrmsApp.class)
public class AnschriftResourceIntTest {

    private static final String DEFAULT_ORT = "AAAAAAAAAA";
    private static final String UPDATED_ORT = "BBBBBBBBBB";

    private static final Integer DEFAULT_POSTLEITZAHL = 1;
    private static final Integer UPDATED_POSTLEITZAHL = 2;

    private static final String DEFAULT_STRASSENNAME = "AAAAAAAAAA";
    private static final String UPDATED_STRASSENNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_HAUSNUMMER = 1;
    private static final Integer UPDATED_HAUSNUMMER = 2;

    @Autowired
    private AnschriftRepository anschriftRepository;

    @Autowired
    private AnschriftMapper anschriftMapper;

    @Autowired
    private AnschriftService anschriftService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnschriftMockMvc;

    private Anschrift anschrift;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnschriftResource anschriftResource = new AnschriftResource(anschriftService);
        this.restAnschriftMockMvc = MockMvcBuilders.standaloneSetup(anschriftResource)
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
    public static Anschrift createEntity(EntityManager em) {
        Anschrift anschrift = new Anschrift()
            .ort(DEFAULT_ORT)
            .postleitzahl(DEFAULT_POSTLEITZAHL)
            .strassenname(DEFAULT_STRASSENNAME)
            .hausnummer(DEFAULT_HAUSNUMMER);
        return anschrift;
    }

    @Before
    public void initTest() {
        anschrift = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnschrift() throws Exception {
        int databaseSizeBeforeCreate = anschriftRepository.findAll().size();

        // Create the Anschrift
        AnschriftDTO anschriftDTO = anschriftMapper.toDto(anschrift);
        restAnschriftMockMvc.perform(post("/api/anschrifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anschriftDTO)))
            .andExpect(status().isCreated());

        // Validate the Anschrift in the database
        List<Anschrift> anschriftList = anschriftRepository.findAll();
        assertThat(anschriftList).hasSize(databaseSizeBeforeCreate + 1);
        Anschrift testAnschrift = anschriftList.get(anschriftList.size() - 1);
        assertThat(testAnschrift.getOrt()).isEqualTo(DEFAULT_ORT);
        assertThat(testAnschrift.getPostleitzahl()).isEqualTo(DEFAULT_POSTLEITZAHL);
        assertThat(testAnschrift.getStrassenname()).isEqualTo(DEFAULT_STRASSENNAME);
        assertThat(testAnschrift.getHausnummer()).isEqualTo(DEFAULT_HAUSNUMMER);
    }

    @Test
    @Transactional
    public void createAnschriftWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anschriftRepository.findAll().size();

        // Create the Anschrift with an existing ID
        anschrift.setId(1L);
        AnschriftDTO anschriftDTO = anschriftMapper.toDto(anschrift);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnschriftMockMvc.perform(post("/api/anschrifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anschriftDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Anschrift> anschriftList = anschriftRepository.findAll();
        assertThat(anschriftList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrtIsRequired() throws Exception {
        int databaseSizeBeforeTest = anschriftRepository.findAll().size();
        // set the field null
        anschrift.setOrt(null);

        // Create the Anschrift, which fails.
        AnschriftDTO anschriftDTO = anschriftMapper.toDto(anschrift);

        restAnschriftMockMvc.perform(post("/api/anschrifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anschriftDTO)))
            .andExpect(status().isBadRequest());

        List<Anschrift> anschriftList = anschriftRepository.findAll();
        assertThat(anschriftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostleitzahlIsRequired() throws Exception {
        int databaseSizeBeforeTest = anschriftRepository.findAll().size();
        // set the field null
        anschrift.setPostleitzahl(null);

        // Create the Anschrift, which fails.
        AnschriftDTO anschriftDTO = anschriftMapper.toDto(anschrift);

        restAnschriftMockMvc.perform(post("/api/anschrifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anschriftDTO)))
            .andExpect(status().isBadRequest());

        List<Anschrift> anschriftList = anschriftRepository.findAll();
        assertThat(anschriftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStrassennameIsRequired() throws Exception {
        int databaseSizeBeforeTest = anschriftRepository.findAll().size();
        // set the field null
        anschrift.setStrassenname(null);

        // Create the Anschrift, which fails.
        AnschriftDTO anschriftDTO = anschriftMapper.toDto(anschrift);

        restAnschriftMockMvc.perform(post("/api/anschrifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anschriftDTO)))
            .andExpect(status().isBadRequest());

        List<Anschrift> anschriftList = anschriftRepository.findAll();
        assertThat(anschriftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHausnummerIsRequired() throws Exception {
        int databaseSizeBeforeTest = anschriftRepository.findAll().size();
        // set the field null
        anschrift.setHausnummer(null);

        // Create the Anschrift, which fails.
        AnschriftDTO anschriftDTO = anschriftMapper.toDto(anschrift);

        restAnschriftMockMvc.perform(post("/api/anschrifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anschriftDTO)))
            .andExpect(status().isBadRequest());

        List<Anschrift> anschriftList = anschriftRepository.findAll();
        assertThat(anschriftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnschrifts() throws Exception {
        // Initialize the database
        anschriftRepository.saveAndFlush(anschrift);

        // Get all the anschriftList
        restAnschriftMockMvc.perform(get("/api/anschrifts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anschrift.getId().intValue())))
            .andExpect(jsonPath("$.[*].ort").value(hasItem(DEFAULT_ORT.toString())))
            .andExpect(jsonPath("$.[*].postleitzahl").value(hasItem(DEFAULT_POSTLEITZAHL)))
            .andExpect(jsonPath("$.[*].strassenname").value(hasItem(DEFAULT_STRASSENNAME.toString())))
            .andExpect(jsonPath("$.[*].hausnummer").value(hasItem(DEFAULT_HAUSNUMMER)));
    }

    @Test
    @Transactional
    public void getAnschrift() throws Exception {
        // Initialize the database
        anschriftRepository.saveAndFlush(anschrift);

        // Get the anschrift
        restAnschriftMockMvc.perform(get("/api/anschrifts/{id}", anschrift.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anschrift.getId().intValue()))
            .andExpect(jsonPath("$.ort").value(DEFAULT_ORT.toString()))
            .andExpect(jsonPath("$.postleitzahl").value(DEFAULT_POSTLEITZAHL))
            .andExpect(jsonPath("$.strassenname").value(DEFAULT_STRASSENNAME.toString()))
            .andExpect(jsonPath("$.hausnummer").value(DEFAULT_HAUSNUMMER));
    }

    @Test
    @Transactional
    public void getNonExistingAnschrift() throws Exception {
        // Get the anschrift
        restAnschriftMockMvc.perform(get("/api/anschrifts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnschrift() throws Exception {
        // Initialize the database
        anschriftRepository.saveAndFlush(anschrift);
        int databaseSizeBeforeUpdate = anschriftRepository.findAll().size();

        // Update the anschrift
        Anschrift updatedAnschrift = anschriftRepository.findOne(anschrift.getId());
        updatedAnschrift
            .ort(UPDATED_ORT)
            .postleitzahl(UPDATED_POSTLEITZAHL)
            .strassenname(UPDATED_STRASSENNAME)
            .hausnummer(UPDATED_HAUSNUMMER);
        AnschriftDTO anschriftDTO = anschriftMapper.toDto(updatedAnschrift);

        restAnschriftMockMvc.perform(put("/api/anschrifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anschriftDTO)))
            .andExpect(status().isOk());

        // Validate the Anschrift in the database
        List<Anschrift> anschriftList = anschriftRepository.findAll();
        assertThat(anschriftList).hasSize(databaseSizeBeforeUpdate);
        Anschrift testAnschrift = anschriftList.get(anschriftList.size() - 1);
        assertThat(testAnschrift.getOrt()).isEqualTo(UPDATED_ORT);
        assertThat(testAnschrift.getPostleitzahl()).isEqualTo(UPDATED_POSTLEITZAHL);
        assertThat(testAnschrift.getStrassenname()).isEqualTo(UPDATED_STRASSENNAME);
        assertThat(testAnschrift.getHausnummer()).isEqualTo(UPDATED_HAUSNUMMER);
    }

    @Test
    @Transactional
    public void updateNonExistingAnschrift() throws Exception {
        int databaseSizeBeforeUpdate = anschriftRepository.findAll().size();

        // Create the Anschrift
        AnschriftDTO anschriftDTO = anschriftMapper.toDto(anschrift);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnschriftMockMvc.perform(put("/api/anschrifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anschriftDTO)))
            .andExpect(status().isCreated());

        // Validate the Anschrift in the database
        List<Anschrift> anschriftList = anschriftRepository.findAll();
        assertThat(anschriftList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnschrift() throws Exception {
        // Initialize the database
        anschriftRepository.saveAndFlush(anschrift);
        int databaseSizeBeforeDelete = anschriftRepository.findAll().size();

        // Get the anschrift
        restAnschriftMockMvc.perform(delete("/api/anschrifts/{id}", anschrift.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Anschrift> anschriftList = anschriftRepository.findAll();
        assertThat(anschriftList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anschrift.class);
        Anschrift anschrift1 = new Anschrift();
        anschrift1.setId(1L);
        Anschrift anschrift2 = new Anschrift();
        anschrift2.setId(anschrift1.getId());
        assertThat(anschrift1).isEqualTo(anschrift2);
        anschrift2.setId(2L);
        assertThat(anschrift1).isNotEqualTo(anschrift2);
        anschrift1.setId(null);
        assertThat(anschrift1).isNotEqualTo(anschrift2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnschriftDTO.class);
        AnschriftDTO anschriftDTO1 = new AnschriftDTO();
        anschriftDTO1.setId(1L);
        AnschriftDTO anschriftDTO2 = new AnschriftDTO();
        assertThat(anschriftDTO1).isNotEqualTo(anschriftDTO2);
        anschriftDTO2.setId(anschriftDTO1.getId());
        assertThat(anschriftDTO1).isEqualTo(anschriftDTO2);
        anschriftDTO2.setId(2L);
        assertThat(anschriftDTO1).isNotEqualTo(anschriftDTO2);
        anschriftDTO1.setId(null);
        assertThat(anschriftDTO1).isNotEqualTo(anschriftDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(anschriftMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(anschriftMapper.fromId(null)).isNull();
    }
}
