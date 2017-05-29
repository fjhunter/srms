package com.srms.web.rest;

import com.srms.SrmsApp;

import com.srms.domain.Lehrer;
import com.srms.repository.LehrerRepository;
import com.srms.service.LehrerService;
import com.srms.service.dto.LehrerDTO;
import com.srms.service.mapper.LehrerMapper;
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
 * Test class for the LehrerResource REST controller.
 *
 * @see LehrerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SrmsApp.class)
public class LehrerResourceIntTest {

    private static final String DEFAULT_VORNAME = "AAAAAAAAAA";
    private static final String UPDATED_VORNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAMEN = "AAAAAAAAAA";
    private static final String UPDATED_NAMEN = "BBBBBBBBBB";

    @Autowired
    private LehrerRepository lehrerRepository;

    @Autowired
    private LehrerMapper lehrerMapper;

    @Autowired
    private LehrerService lehrerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLehrerMockMvc;

    private Lehrer lehrer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LehrerResource lehrerResource = new LehrerResource(lehrerService);
        this.restLehrerMockMvc = MockMvcBuilders.standaloneSetup(lehrerResource)
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
    public static Lehrer createEntity(EntityManager em) {
        Lehrer lehrer = new Lehrer()
            .vorname(DEFAULT_VORNAME)
            .namen(DEFAULT_NAMEN);
        return lehrer;
    }

    @Before
    public void initTest() {
        lehrer = createEntity(em);
    }

    @Test
    @Transactional
    public void createLehrer() throws Exception {
        int databaseSizeBeforeCreate = lehrerRepository.findAll().size();

        // Create the Lehrer
        LehrerDTO lehrerDTO = lehrerMapper.toDto(lehrer);
        restLehrerMockMvc.perform(post("/api/lehrers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lehrerDTO)))
            .andExpect(status().isCreated());

        // Validate the Lehrer in the database
        List<Lehrer> lehrerList = lehrerRepository.findAll();
        assertThat(lehrerList).hasSize(databaseSizeBeforeCreate + 1);
        Lehrer testLehrer = lehrerList.get(lehrerList.size() - 1);
        assertThat(testLehrer.getVorname()).isEqualTo(DEFAULT_VORNAME);
        assertThat(testLehrer.getNamen()).isEqualTo(DEFAULT_NAMEN);
    }

    @Test
    @Transactional
    public void createLehrerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lehrerRepository.findAll().size();

        // Create the Lehrer with an existing ID
        lehrer.setId(1L);
        LehrerDTO lehrerDTO = lehrerMapper.toDto(lehrer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLehrerMockMvc.perform(post("/api/lehrers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lehrerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Lehrer> lehrerList = lehrerRepository.findAll();
        assertThat(lehrerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkVornameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lehrerRepository.findAll().size();
        // set the field null
        lehrer.setVorname(null);

        // Create the Lehrer, which fails.
        LehrerDTO lehrerDTO = lehrerMapper.toDto(lehrer);

        restLehrerMockMvc.perform(post("/api/lehrers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lehrerDTO)))
            .andExpect(status().isBadRequest());

        List<Lehrer> lehrerList = lehrerRepository.findAll();
        assertThat(lehrerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNamenIsRequired() throws Exception {
        int databaseSizeBeforeTest = lehrerRepository.findAll().size();
        // set the field null
        lehrer.setNamen(null);

        // Create the Lehrer, which fails.
        LehrerDTO lehrerDTO = lehrerMapper.toDto(lehrer);

        restLehrerMockMvc.perform(post("/api/lehrers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lehrerDTO)))
            .andExpect(status().isBadRequest());

        List<Lehrer> lehrerList = lehrerRepository.findAll();
        assertThat(lehrerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLehrers() throws Exception {
        // Initialize the database
        lehrerRepository.saveAndFlush(lehrer);

        // Get all the lehrerList
        restLehrerMockMvc.perform(get("/api/lehrers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lehrer.getId().intValue())))
            .andExpect(jsonPath("$.[*].vorname").value(hasItem(DEFAULT_VORNAME.toString())))
            .andExpect(jsonPath("$.[*].namen").value(hasItem(DEFAULT_NAMEN.toString())));
    }

    @Test
    @Transactional
    public void getLehrer() throws Exception {
        // Initialize the database
        lehrerRepository.saveAndFlush(lehrer);

        // Get the lehrer
        restLehrerMockMvc.perform(get("/api/lehrers/{id}", lehrer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lehrer.getId().intValue()))
            .andExpect(jsonPath("$.vorname").value(DEFAULT_VORNAME.toString()))
            .andExpect(jsonPath("$.namen").value(DEFAULT_NAMEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLehrer() throws Exception {
        // Get the lehrer
        restLehrerMockMvc.perform(get("/api/lehrers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLehrer() throws Exception {
        // Initialize the database
        lehrerRepository.saveAndFlush(lehrer);
        int databaseSizeBeforeUpdate = lehrerRepository.findAll().size();

        // Update the lehrer
        Lehrer updatedLehrer = lehrerRepository.findOne(lehrer.getId());
        updatedLehrer
            .vorname(UPDATED_VORNAME)
            .namen(UPDATED_NAMEN);
        LehrerDTO lehrerDTO = lehrerMapper.toDto(updatedLehrer);

        restLehrerMockMvc.perform(put("/api/lehrers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lehrerDTO)))
            .andExpect(status().isOk());

        // Validate the Lehrer in the database
        List<Lehrer> lehrerList = lehrerRepository.findAll();
        assertThat(lehrerList).hasSize(databaseSizeBeforeUpdate);
        Lehrer testLehrer = lehrerList.get(lehrerList.size() - 1);
        assertThat(testLehrer.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testLehrer.getNamen()).isEqualTo(UPDATED_NAMEN);
    }

    @Test
    @Transactional
    public void updateNonExistingLehrer() throws Exception {
        int databaseSizeBeforeUpdate = lehrerRepository.findAll().size();

        // Create the Lehrer
        LehrerDTO lehrerDTO = lehrerMapper.toDto(lehrer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLehrerMockMvc.perform(put("/api/lehrers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lehrerDTO)))
            .andExpect(status().isCreated());

        // Validate the Lehrer in the database
        List<Lehrer> lehrerList = lehrerRepository.findAll();
        assertThat(lehrerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLehrer() throws Exception {
        // Initialize the database
        lehrerRepository.saveAndFlush(lehrer);
        int databaseSizeBeforeDelete = lehrerRepository.findAll().size();

        // Get the lehrer
        restLehrerMockMvc.perform(delete("/api/lehrers/{id}", lehrer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lehrer> lehrerList = lehrerRepository.findAll();
        assertThat(lehrerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lehrer.class);
        Lehrer lehrer1 = new Lehrer();
        lehrer1.setId(1L);
        Lehrer lehrer2 = new Lehrer();
        lehrer2.setId(lehrer1.getId());
        assertThat(lehrer1).isEqualTo(lehrer2);
        lehrer2.setId(2L);
        assertThat(lehrer1).isNotEqualTo(lehrer2);
        lehrer1.setId(null);
        assertThat(lehrer1).isNotEqualTo(lehrer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LehrerDTO.class);
        LehrerDTO lehrerDTO1 = new LehrerDTO();
        lehrerDTO1.setId(1L);
        LehrerDTO lehrerDTO2 = new LehrerDTO();
        assertThat(lehrerDTO1).isNotEqualTo(lehrerDTO2);
        lehrerDTO2.setId(lehrerDTO1.getId());
        assertThat(lehrerDTO1).isEqualTo(lehrerDTO2);
        lehrerDTO2.setId(2L);
        assertThat(lehrerDTO1).isNotEqualTo(lehrerDTO2);
        lehrerDTO1.setId(null);
        assertThat(lehrerDTO1).isNotEqualTo(lehrerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lehrerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lehrerMapper.fromId(null)).isNull();
    }
}
