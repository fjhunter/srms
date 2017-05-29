package com.srms.web.rest;

import com.srms.SrmsApp;

import com.srms.domain.Klasse;
import com.srms.repository.KlasseRepository;
import com.srms.service.KlasseService;
import com.srms.service.dto.KlasseDTO;
import com.srms.service.mapper.KlasseMapper;
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
 * Test class for the KlasseResource REST controller.
 *
 * @see KlasseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SrmsApp.class)
public class KlasseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private KlasseRepository klasseRepository;

    @Autowired
    private KlasseMapper klasseMapper;

    @Autowired
    private KlasseService klasseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKlasseMockMvc;

    private Klasse klasse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KlasseResource klasseResource = new KlasseResource(klasseService);
        this.restKlasseMockMvc = MockMvcBuilders.standaloneSetup(klasseResource)
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
    public static Klasse createEntity(EntityManager em) {
        Klasse klasse = new Klasse()
            .name(DEFAULT_NAME);
        return klasse;
    }

    @Before
    public void initTest() {
        klasse = createEntity(em);
    }

    @Test
    @Transactional
    public void createKlasse() throws Exception {
        int databaseSizeBeforeCreate = klasseRepository.findAll().size();

        // Create the Klasse
        KlasseDTO klasseDTO = klasseMapper.toDto(klasse);
        restKlasseMockMvc.perform(post("/api/klasses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klasseDTO)))
            .andExpect(status().isCreated());

        // Validate the Klasse in the database
        List<Klasse> klasseList = klasseRepository.findAll();
        assertThat(klasseList).hasSize(databaseSizeBeforeCreate + 1);
        Klasse testKlasse = klasseList.get(klasseList.size() - 1);
        assertThat(testKlasse.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createKlasseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = klasseRepository.findAll().size();

        // Create the Klasse with an existing ID
        klasse.setId(1L);
        KlasseDTO klasseDTO = klasseMapper.toDto(klasse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKlasseMockMvc.perform(post("/api/klasses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klasseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Klasse> klasseList = klasseRepository.findAll();
        assertThat(klasseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = klasseRepository.findAll().size();
        // set the field null
        klasse.setName(null);

        // Create the Klasse, which fails.
        KlasseDTO klasseDTO = klasseMapper.toDto(klasse);

        restKlasseMockMvc.perform(post("/api/klasses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klasseDTO)))
            .andExpect(status().isBadRequest());

        List<Klasse> klasseList = klasseRepository.findAll();
        assertThat(klasseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKlasses() throws Exception {
        // Initialize the database
        klasseRepository.saveAndFlush(klasse);

        // Get all the klasseList
        restKlasseMockMvc.perform(get("/api/klasses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(klasse.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getKlasse() throws Exception {
        // Initialize the database
        klasseRepository.saveAndFlush(klasse);

        // Get the klasse
        restKlasseMockMvc.perform(get("/api/klasses/{id}", klasse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(klasse.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKlasse() throws Exception {
        // Get the klasse
        restKlasseMockMvc.perform(get("/api/klasses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKlasse() throws Exception {
        // Initialize the database
        klasseRepository.saveAndFlush(klasse);
        int databaseSizeBeforeUpdate = klasseRepository.findAll().size();

        // Update the klasse
        Klasse updatedKlasse = klasseRepository.findOne(klasse.getId());
        updatedKlasse
            .name(UPDATED_NAME);
        KlasseDTO klasseDTO = klasseMapper.toDto(updatedKlasse);

        restKlasseMockMvc.perform(put("/api/klasses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klasseDTO)))
            .andExpect(status().isOk());

        // Validate the Klasse in the database
        List<Klasse> klasseList = klasseRepository.findAll();
        assertThat(klasseList).hasSize(databaseSizeBeforeUpdate);
        Klasse testKlasse = klasseList.get(klasseList.size() - 1);
        assertThat(testKlasse.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingKlasse() throws Exception {
        int databaseSizeBeforeUpdate = klasseRepository.findAll().size();

        // Create the Klasse
        KlasseDTO klasseDTO = klasseMapper.toDto(klasse);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKlasseMockMvc.perform(put("/api/klasses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klasseDTO)))
            .andExpect(status().isCreated());

        // Validate the Klasse in the database
        List<Klasse> klasseList = klasseRepository.findAll();
        assertThat(klasseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKlasse() throws Exception {
        // Initialize the database
        klasseRepository.saveAndFlush(klasse);
        int databaseSizeBeforeDelete = klasseRepository.findAll().size();

        // Get the klasse
        restKlasseMockMvc.perform(delete("/api/klasses/{id}", klasse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Klasse> klasseList = klasseRepository.findAll();
        assertThat(klasseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Klasse.class);
        Klasse klasse1 = new Klasse();
        klasse1.setId(1L);
        Klasse klasse2 = new Klasse();
        klasse2.setId(klasse1.getId());
        assertThat(klasse1).isEqualTo(klasse2);
        klasse2.setId(2L);
        assertThat(klasse1).isNotEqualTo(klasse2);
        klasse1.setId(null);
        assertThat(klasse1).isNotEqualTo(klasse2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KlasseDTO.class);
        KlasseDTO klasseDTO1 = new KlasseDTO();
        klasseDTO1.setId(1L);
        KlasseDTO klasseDTO2 = new KlasseDTO();
        assertThat(klasseDTO1).isNotEqualTo(klasseDTO2);
        klasseDTO2.setId(klasseDTO1.getId());
        assertThat(klasseDTO1).isEqualTo(klasseDTO2);
        klasseDTO2.setId(2L);
        assertThat(klasseDTO1).isNotEqualTo(klasseDTO2);
        klasseDTO1.setId(null);
        assertThat(klasseDTO1).isNotEqualTo(klasseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(klasseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(klasseMapper.fromId(null)).isNull();
    }
}
