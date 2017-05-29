package com.srms.web.rest;

import com.srms.SrmsApp;

import com.srms.domain.Zeugnis;
import com.srms.repository.ZeugnisRepository;
import com.srms.service.ZeugnisService;
import com.srms.service.dto.ZeugnisDTO;
import com.srms.service.mapper.ZeugnisMapper;
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

import com.srms.domain.enumeration.Zeugnis_typ;
/**
 * Test class for the ZeugnisResource REST controller.
 *
 * @see ZeugnisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SrmsApp.class)
public class ZeugnisResourceIntTest {

    private static final Integer DEFAULT_SOZIALVERHALTEN = 1;
    private static final Integer UPDATED_SOZIALVERHALTEN = 2;

    private static final Integer DEFAULT_ARBEITSVERHALTEN = 1;
    private static final Integer UPDATED_ARBEITSVERHALTEN = 2;

    private static final ZonedDateTime DEFAULT_DATUM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATUM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Zeugnis_typ DEFAULT_ZEUGNISTYP = Zeugnis_typ.HALBJAHRESZEUGNIS;
    private static final Zeugnis_typ UPDATED_ZEUGNISTYP = Zeugnis_typ.ZEUGNIS;

    @Autowired
    private ZeugnisRepository zeugnisRepository;

    @Autowired
    private ZeugnisMapper zeugnisMapper;

    @Autowired
    private ZeugnisService zeugnisService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restZeugnisMockMvc;

    private Zeugnis zeugnis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ZeugnisResource zeugnisResource = new ZeugnisResource(zeugnisService);
        this.restZeugnisMockMvc = MockMvcBuilders.standaloneSetup(zeugnisResource)
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
    public static Zeugnis createEntity(EntityManager em) {
        Zeugnis zeugnis = new Zeugnis()
            .sozialverhalten(DEFAULT_SOZIALVERHALTEN)
            .arbeitsverhalten(DEFAULT_ARBEITSVERHALTEN)
            .datum(DEFAULT_DATUM)
            .zeugnistyp(DEFAULT_ZEUGNISTYP);
        return zeugnis;
    }

    @Before
    public void initTest() {
        zeugnis = createEntity(em);
    }

    @Test
    @Transactional
    public void createZeugnis() throws Exception {
        int databaseSizeBeforeCreate = zeugnisRepository.findAll().size();

        // Create the Zeugnis
        ZeugnisDTO zeugnisDTO = zeugnisMapper.toDto(zeugnis);
        restZeugnisMockMvc.perform(post("/api/zeugnis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zeugnisDTO)))
            .andExpect(status().isCreated());

        // Validate the Zeugnis in the database
        List<Zeugnis> zeugnisList = zeugnisRepository.findAll();
        assertThat(zeugnisList).hasSize(databaseSizeBeforeCreate + 1);
        Zeugnis testZeugnis = zeugnisList.get(zeugnisList.size() - 1);
        assertThat(testZeugnis.getSozialverhalten()).isEqualTo(DEFAULT_SOZIALVERHALTEN);
        assertThat(testZeugnis.getArbeitsverhalten()).isEqualTo(DEFAULT_ARBEITSVERHALTEN);
        assertThat(testZeugnis.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testZeugnis.getZeugnistyp()).isEqualTo(DEFAULT_ZEUGNISTYP);
    }

    @Test
    @Transactional
    public void createZeugnisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zeugnisRepository.findAll().size();

        // Create the Zeugnis with an existing ID
        zeugnis.setId(1L);
        ZeugnisDTO zeugnisDTO = zeugnisMapper.toDto(zeugnis);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZeugnisMockMvc.perform(post("/api/zeugnis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zeugnisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Zeugnis> zeugnisList = zeugnisRepository.findAll();
        assertThat(zeugnisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = zeugnisRepository.findAll().size();
        // set the field null
        zeugnis.setDatum(null);

        // Create the Zeugnis, which fails.
        ZeugnisDTO zeugnisDTO = zeugnisMapper.toDto(zeugnis);

        restZeugnisMockMvc.perform(post("/api/zeugnis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zeugnisDTO)))
            .andExpect(status().isBadRequest());

        List<Zeugnis> zeugnisList = zeugnisRepository.findAll();
        assertThat(zeugnisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkZeugnistypIsRequired() throws Exception {
        int databaseSizeBeforeTest = zeugnisRepository.findAll().size();
        // set the field null
        zeugnis.setZeugnistyp(null);

        // Create the Zeugnis, which fails.
        ZeugnisDTO zeugnisDTO = zeugnisMapper.toDto(zeugnis);

        restZeugnisMockMvc.perform(post("/api/zeugnis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zeugnisDTO)))
            .andExpect(status().isBadRequest());

        List<Zeugnis> zeugnisList = zeugnisRepository.findAll();
        assertThat(zeugnisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllZeugnis() throws Exception {
        // Initialize the database
        zeugnisRepository.saveAndFlush(zeugnis);

        // Get all the zeugnisList
        restZeugnisMockMvc.perform(get("/api/zeugnis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zeugnis.getId().intValue())))
            .andExpect(jsonPath("$.[*].sozialverhalten").value(hasItem(DEFAULT_SOZIALVERHALTEN)))
            .andExpect(jsonPath("$.[*].arbeitsverhalten").value(hasItem(DEFAULT_ARBEITSVERHALTEN)))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(sameInstant(DEFAULT_DATUM))))
            .andExpect(jsonPath("$.[*].zeugnistyp").value(hasItem(DEFAULT_ZEUGNISTYP.toString())));
    }

    @Test
    @Transactional
    public void getZeugnis() throws Exception {
        // Initialize the database
        zeugnisRepository.saveAndFlush(zeugnis);

        // Get the zeugnis
        restZeugnisMockMvc.perform(get("/api/zeugnis/{id}", zeugnis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zeugnis.getId().intValue()))
            .andExpect(jsonPath("$.sozialverhalten").value(DEFAULT_SOZIALVERHALTEN))
            .andExpect(jsonPath("$.arbeitsverhalten").value(DEFAULT_ARBEITSVERHALTEN))
            .andExpect(jsonPath("$.datum").value(sameInstant(DEFAULT_DATUM)))
            .andExpect(jsonPath("$.zeugnistyp").value(DEFAULT_ZEUGNISTYP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingZeugnis() throws Exception {
        // Get the zeugnis
        restZeugnisMockMvc.perform(get("/api/zeugnis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZeugnis() throws Exception {
        // Initialize the database
        zeugnisRepository.saveAndFlush(zeugnis);
        int databaseSizeBeforeUpdate = zeugnisRepository.findAll().size();

        // Update the zeugnis
        Zeugnis updatedZeugnis = zeugnisRepository.findOne(zeugnis.getId());
        updatedZeugnis
            .sozialverhalten(UPDATED_SOZIALVERHALTEN)
            .arbeitsverhalten(UPDATED_ARBEITSVERHALTEN)
            .datum(UPDATED_DATUM)
            .zeugnistyp(UPDATED_ZEUGNISTYP);
        ZeugnisDTO zeugnisDTO = zeugnisMapper.toDto(updatedZeugnis);

        restZeugnisMockMvc.perform(put("/api/zeugnis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zeugnisDTO)))
            .andExpect(status().isOk());

        // Validate the Zeugnis in the database
        List<Zeugnis> zeugnisList = zeugnisRepository.findAll();
        assertThat(zeugnisList).hasSize(databaseSizeBeforeUpdate);
        Zeugnis testZeugnis = zeugnisList.get(zeugnisList.size() - 1);
        assertThat(testZeugnis.getSozialverhalten()).isEqualTo(UPDATED_SOZIALVERHALTEN);
        assertThat(testZeugnis.getArbeitsverhalten()).isEqualTo(UPDATED_ARBEITSVERHALTEN);
        assertThat(testZeugnis.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testZeugnis.getZeugnistyp()).isEqualTo(UPDATED_ZEUGNISTYP);
    }

    @Test
    @Transactional
    public void updateNonExistingZeugnis() throws Exception {
        int databaseSizeBeforeUpdate = zeugnisRepository.findAll().size();

        // Create the Zeugnis
        ZeugnisDTO zeugnisDTO = zeugnisMapper.toDto(zeugnis);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restZeugnisMockMvc.perform(put("/api/zeugnis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zeugnisDTO)))
            .andExpect(status().isCreated());

        // Validate the Zeugnis in the database
        List<Zeugnis> zeugnisList = zeugnisRepository.findAll();
        assertThat(zeugnisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteZeugnis() throws Exception {
        // Initialize the database
        zeugnisRepository.saveAndFlush(zeugnis);
        int databaseSizeBeforeDelete = zeugnisRepository.findAll().size();

        // Get the zeugnis
        restZeugnisMockMvc.perform(delete("/api/zeugnis/{id}", zeugnis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Zeugnis> zeugnisList = zeugnisRepository.findAll();
        assertThat(zeugnisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zeugnis.class);
        Zeugnis zeugnis1 = new Zeugnis();
        zeugnis1.setId(1L);
        Zeugnis zeugnis2 = new Zeugnis();
        zeugnis2.setId(zeugnis1.getId());
        assertThat(zeugnis1).isEqualTo(zeugnis2);
        zeugnis2.setId(2L);
        assertThat(zeugnis1).isNotEqualTo(zeugnis2);
        zeugnis1.setId(null);
        assertThat(zeugnis1).isNotEqualTo(zeugnis2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZeugnisDTO.class);
        ZeugnisDTO zeugnisDTO1 = new ZeugnisDTO();
        zeugnisDTO1.setId(1L);
        ZeugnisDTO zeugnisDTO2 = new ZeugnisDTO();
        assertThat(zeugnisDTO1).isNotEqualTo(zeugnisDTO2);
        zeugnisDTO2.setId(zeugnisDTO1.getId());
        assertThat(zeugnisDTO1).isEqualTo(zeugnisDTO2);
        zeugnisDTO2.setId(2L);
        assertThat(zeugnisDTO1).isNotEqualTo(zeugnisDTO2);
        zeugnisDTO1.setId(null);
        assertThat(zeugnisDTO1).isNotEqualTo(zeugnisDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(zeugnisMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(zeugnisMapper.fromId(null)).isNull();
    }
}
