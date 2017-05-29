package com.srms.web.rest;

import com.srms.SrmsApp;

import com.srms.domain.Fehlzeiten;
import com.srms.repository.FehlzeitenRepository;
import com.srms.service.FehlzeitenService;
import com.srms.service.dto.FehlzeitenDTO;
import com.srms.service.mapper.FehlzeitenMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.srms.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FehlzeitenResource REST controller.
 *
 * @see FehlzeitenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SrmsApp.class)
public class FehlzeitenResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATUM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATUM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_DAUER = 1;
    private static final Integer UPDATED_DAUER = 2;

    @Autowired
    private FehlzeitenRepository fehlzeitenRepository;

    @Autowired
    private FehlzeitenMapper fehlzeitenMapper;

    @Autowired
    private FehlzeitenService fehlzeitenService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFehlzeitenMockMvc;

    private Fehlzeiten fehlzeiten;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FehlzeitenResource fehlzeitenResource = new FehlzeitenResource(fehlzeitenService);
        this.restFehlzeitenMockMvc = MockMvcBuilders.standaloneSetup(fehlzeitenResource)
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
    public static Fehlzeiten createEntity(EntityManager em) {
        Fehlzeiten fehlzeiten = new Fehlzeiten()
            .datum(DEFAULT_DATUM)
            .dauer(DEFAULT_DAUER);
        return fehlzeiten;
    }

    @Before
    public void initTest() {
        fehlzeiten = createEntity(em);
    }

    @Test
    @Transactional
    public void createFehlzeiten() throws Exception {
        int databaseSizeBeforeCreate = fehlzeitenRepository.findAll().size();

        // Create the Fehlzeiten
        FehlzeitenDTO fehlzeitenDTO = fehlzeitenMapper.toDto(fehlzeiten);
        restFehlzeitenMockMvc.perform(post("/api/fehlzeitens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fehlzeitenDTO)))
            .andExpect(status().isCreated());

        // Validate the Fehlzeiten in the database
        List<Fehlzeiten> fehlzeitenList = fehlzeitenRepository.findAll();
        assertThat(fehlzeitenList).hasSize(databaseSizeBeforeCreate + 1);
        Fehlzeiten testFehlzeiten = fehlzeitenList.get(fehlzeitenList.size() - 1);
        assertThat(testFehlzeiten.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testFehlzeiten.getDauer()).isEqualTo(DEFAULT_DAUER);
    }

    @Test
    @Transactional
    public void createFehlzeitenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fehlzeitenRepository.findAll().size();

        // Create the Fehlzeiten with an existing ID
        fehlzeiten.setId(1L);
        FehlzeitenDTO fehlzeitenDTO = fehlzeitenMapper.toDto(fehlzeiten);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFehlzeitenMockMvc.perform(post("/api/fehlzeitens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fehlzeitenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Fehlzeiten> fehlzeitenList = fehlzeitenRepository.findAll();
        assertThat(fehlzeitenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = fehlzeitenRepository.findAll().size();
        // set the field null
        fehlzeiten.setDatum(null);

        // Create the Fehlzeiten, which fails.
        FehlzeitenDTO fehlzeitenDTO = fehlzeitenMapper.toDto(fehlzeiten);

        restFehlzeitenMockMvc.perform(post("/api/fehlzeitens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fehlzeitenDTO)))
            .andExpect(status().isBadRequest());

        List<Fehlzeiten> fehlzeitenList = fehlzeitenRepository.findAll();
        assertThat(fehlzeitenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDauerIsRequired() throws Exception {
        int databaseSizeBeforeTest = fehlzeitenRepository.findAll().size();
        // set the field null
        fehlzeiten.setDauer(null);

        // Create the Fehlzeiten, which fails.
        FehlzeitenDTO fehlzeitenDTO = fehlzeitenMapper.toDto(fehlzeiten);

        restFehlzeitenMockMvc.perform(post("/api/fehlzeitens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fehlzeitenDTO)))
            .andExpect(status().isBadRequest());

        List<Fehlzeiten> fehlzeitenList = fehlzeitenRepository.findAll();
        assertThat(fehlzeitenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFehlzeitens() throws Exception {
        // Initialize the database
        fehlzeitenRepository.saveAndFlush(fehlzeiten);

        // Get all the fehlzeitenList
        restFehlzeitenMockMvc.perform(get("/api/fehlzeitens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fehlzeiten.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(sameInstant(DEFAULT_DATUM))))
            .andExpect(jsonPath("$.[*].dauer").value(hasItem(DEFAULT_DAUER)));
    }

    @Test
    @Transactional
    public void getFehlzeiten() throws Exception {
        // Initialize the database
        fehlzeitenRepository.saveAndFlush(fehlzeiten);

        // Get the fehlzeiten
        restFehlzeitenMockMvc.perform(get("/api/fehlzeitens/{id}", fehlzeiten.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fehlzeiten.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(sameInstant(DEFAULT_DATUM)))
            .andExpect(jsonPath("$.dauer").value(DEFAULT_DAUER));
    }

    @Test
    @Transactional
    public void getNonExistingFehlzeiten() throws Exception {
        // Get the fehlzeiten
        restFehlzeitenMockMvc.perform(get("/api/fehlzeitens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFehlzeiten() throws Exception {
        // Initialize the database
        fehlzeitenRepository.saveAndFlush(fehlzeiten);
        int databaseSizeBeforeUpdate = fehlzeitenRepository.findAll().size();

        // Update the fehlzeiten
        Fehlzeiten updatedFehlzeiten = fehlzeitenRepository.findOne(fehlzeiten.getId());
        updatedFehlzeiten
            .datum(UPDATED_DATUM)
            .dauer(UPDATED_DAUER);
        FehlzeitenDTO fehlzeitenDTO = fehlzeitenMapper.toDto(updatedFehlzeiten);

        restFehlzeitenMockMvc.perform(put("/api/fehlzeitens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fehlzeitenDTO)))
            .andExpect(status().isOk());

        // Validate the Fehlzeiten in the database
        List<Fehlzeiten> fehlzeitenList = fehlzeitenRepository.findAll();
        assertThat(fehlzeitenList).hasSize(databaseSizeBeforeUpdate);
        Fehlzeiten testFehlzeiten = fehlzeitenList.get(fehlzeitenList.size() - 1);
        assertThat(testFehlzeiten.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testFehlzeiten.getDauer()).isEqualTo(UPDATED_DAUER);
    }

    @Test
    @Transactional
    public void updateNonExistingFehlzeiten() throws Exception {
        int databaseSizeBeforeUpdate = fehlzeitenRepository.findAll().size();

        // Create the Fehlzeiten
        FehlzeitenDTO fehlzeitenDTO = fehlzeitenMapper.toDto(fehlzeiten);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFehlzeitenMockMvc.perform(put("/api/fehlzeitens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fehlzeitenDTO)))
            .andExpect(status().isCreated());

        // Validate the Fehlzeiten in the database
        List<Fehlzeiten> fehlzeitenList = fehlzeitenRepository.findAll();
        assertThat(fehlzeitenList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFehlzeiten() throws Exception {
        // Initialize the database
        fehlzeitenRepository.saveAndFlush(fehlzeiten);
        int databaseSizeBeforeDelete = fehlzeitenRepository.findAll().size();

        // Get the fehlzeiten
        restFehlzeitenMockMvc.perform(delete("/api/fehlzeitens/{id}", fehlzeiten.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fehlzeiten> fehlzeitenList = fehlzeitenRepository.findAll();
        assertThat(fehlzeitenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fehlzeiten.class);
        Fehlzeiten fehlzeiten1 = new Fehlzeiten();
        fehlzeiten1.setId(1L);
        Fehlzeiten fehlzeiten2 = new Fehlzeiten();
        fehlzeiten2.setId(fehlzeiten1.getId());
        assertThat(fehlzeiten1).isEqualTo(fehlzeiten2);
        fehlzeiten2.setId(2L);
        assertThat(fehlzeiten1).isNotEqualTo(fehlzeiten2);
        fehlzeiten1.setId(null);
        assertThat(fehlzeiten1).isNotEqualTo(fehlzeiten2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FehlzeitenDTO.class);
        FehlzeitenDTO fehlzeitenDTO1 = new FehlzeitenDTO();
        fehlzeitenDTO1.setId(1L);
        FehlzeitenDTO fehlzeitenDTO2 = new FehlzeitenDTO();
        assertThat(fehlzeitenDTO1).isNotEqualTo(fehlzeitenDTO2);
        fehlzeitenDTO2.setId(fehlzeitenDTO1.getId());
        assertThat(fehlzeitenDTO1).isEqualTo(fehlzeitenDTO2);
        fehlzeitenDTO2.setId(2L);
        assertThat(fehlzeitenDTO1).isNotEqualTo(fehlzeitenDTO2);
        fehlzeitenDTO1.setId(null);
        assertThat(fehlzeitenDTO1).isNotEqualTo(fehlzeitenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fehlzeitenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fehlzeitenMapper.fromId(null)).isNull();
    }
}
