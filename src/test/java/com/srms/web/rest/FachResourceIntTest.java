package com.srms.web.rest;

import com.srms.SrmsApp;

import com.srms.domain.Fach;
import com.srms.repository.FachRepository;
import com.srms.service.FachService;
import com.srms.service.dto.FachDTO;
import com.srms.service.mapper.FachMapper;
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
 * Test class for the FachResource REST controller.
 *
 * @see FachResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SrmsApp.class)
public class FachResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FachRepository fachRepository;

    @Autowired
    private FachMapper fachMapper;

    @Autowired
    private FachService fachService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFachMockMvc;

    private Fach fach;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FachResource fachResource = new FachResource(fachService);
        this.restFachMockMvc = MockMvcBuilders.standaloneSetup(fachResource)
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
    public static Fach createEntity(EntityManager em) {
        Fach fach = new Fach()
            .name(DEFAULT_NAME);
        return fach;
    }

    @Before
    public void initTest() {
        fach = createEntity(em);
    }

    @Test
    @Transactional
    public void createFach() throws Exception {
        int databaseSizeBeforeCreate = fachRepository.findAll().size();

        // Create the Fach
        FachDTO fachDTO = fachMapper.toDto(fach);
        restFachMockMvc.perform(post("/api/faches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fachDTO)))
            .andExpect(status().isCreated());

        // Validate the Fach in the database
        List<Fach> fachList = fachRepository.findAll();
        assertThat(fachList).hasSize(databaseSizeBeforeCreate + 1);
        Fach testFach = fachList.get(fachList.size() - 1);
        assertThat(testFach.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFachWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fachRepository.findAll().size();

        // Create the Fach with an existing ID
        fach.setId(1L);
        FachDTO fachDTO = fachMapper.toDto(fach);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFachMockMvc.perform(post("/api/faches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fachDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Fach> fachList = fachRepository.findAll();
        assertThat(fachList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fachRepository.findAll().size();
        // set the field null
        fach.setName(null);

        // Create the Fach, which fails.
        FachDTO fachDTO = fachMapper.toDto(fach);

        restFachMockMvc.perform(post("/api/faches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fachDTO)))
            .andExpect(status().isBadRequest());

        List<Fach> fachList = fachRepository.findAll();
        assertThat(fachList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFaches() throws Exception {
        // Initialize the database
        fachRepository.saveAndFlush(fach);

        // Get all the fachList
        restFachMockMvc.perform(get("/api/faches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fach.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFach() throws Exception {
        // Initialize the database
        fachRepository.saveAndFlush(fach);

        // Get the fach
        restFachMockMvc.perform(get("/api/faches/{id}", fach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fach.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFach() throws Exception {
        // Get the fach
        restFachMockMvc.perform(get("/api/faches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFach() throws Exception {
        // Initialize the database
        fachRepository.saveAndFlush(fach);
        int databaseSizeBeforeUpdate = fachRepository.findAll().size();

        // Update the fach
        Fach updatedFach = fachRepository.findOne(fach.getId());
        updatedFach
            .name(UPDATED_NAME);
        FachDTO fachDTO = fachMapper.toDto(updatedFach);

        restFachMockMvc.perform(put("/api/faches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fachDTO)))
            .andExpect(status().isOk());

        // Validate the Fach in the database
        List<Fach> fachList = fachRepository.findAll();
        assertThat(fachList).hasSize(databaseSizeBeforeUpdate);
        Fach testFach = fachList.get(fachList.size() - 1);
        assertThat(testFach.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFach() throws Exception {
        int databaseSizeBeforeUpdate = fachRepository.findAll().size();

        // Create the Fach
        FachDTO fachDTO = fachMapper.toDto(fach);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFachMockMvc.perform(put("/api/faches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fachDTO)))
            .andExpect(status().isCreated());

        // Validate the Fach in the database
        List<Fach> fachList = fachRepository.findAll();
        assertThat(fachList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFach() throws Exception {
        // Initialize the database
        fachRepository.saveAndFlush(fach);
        int databaseSizeBeforeDelete = fachRepository.findAll().size();

        // Get the fach
        restFachMockMvc.perform(delete("/api/faches/{id}", fach.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fach> fachList = fachRepository.findAll();
        assertThat(fachList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fach.class);
        Fach fach1 = new Fach();
        fach1.setId(1L);
        Fach fach2 = new Fach();
        fach2.setId(fach1.getId());
        assertThat(fach1).isEqualTo(fach2);
        fach2.setId(2L);
        assertThat(fach1).isNotEqualTo(fach2);
        fach1.setId(null);
        assertThat(fach1).isNotEqualTo(fach2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FachDTO.class);
        FachDTO fachDTO1 = new FachDTO();
        fachDTO1.setId(1L);
        FachDTO fachDTO2 = new FachDTO();
        assertThat(fachDTO1).isNotEqualTo(fachDTO2);
        fachDTO2.setId(fachDTO1.getId());
        assertThat(fachDTO1).isEqualTo(fachDTO2);
        fachDTO2.setId(2L);
        assertThat(fachDTO1).isNotEqualTo(fachDTO2);
        fachDTO1.setId(null);
        assertThat(fachDTO1).isNotEqualTo(fachDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fachMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fachMapper.fromId(null)).isNull();
    }
}
