package com.srms.web.rest;

import com.srms.SrmsApp;

import com.srms.domain.KlasseFach;
import com.srms.repository.KlasseFachRepository;
import com.srms.service.KlasseFachService;
import com.srms.service.dto.KlasseFachDTO;
import com.srms.service.mapper.KlasseFachMapper;
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
 * Test class for the KlasseFachResource REST controller.
 *
 * @see KlasseFachResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SrmsApp.class)
public class KlasseFachResourceIntTest {

    @Autowired
    private KlasseFachRepository klasseFachRepository;

    @Autowired
    private KlasseFachMapper klasseFachMapper;

    @Autowired
    private KlasseFachService klasseFachService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKlasseFachMockMvc;

    private KlasseFach klasseFach;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KlasseFachResource klasseFachResource = new KlasseFachResource(klasseFachService);
        this.restKlasseFachMockMvc = MockMvcBuilders.standaloneSetup(klasseFachResource)
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
    public static KlasseFach createEntity(EntityManager em) {
        KlasseFach klasseFach = new KlasseFach();
        return klasseFach;
    }

    @Before
    public void initTest() {
        klasseFach = createEntity(em);
    }

    @Test
    @Transactional
    public void createKlasseFach() throws Exception {
        int databaseSizeBeforeCreate = klasseFachRepository.findAll().size();

        // Create the KlasseFach
        KlasseFachDTO klasseFachDTO = klasseFachMapper.toDto(klasseFach);
        restKlasseFachMockMvc.perform(post("/api/klasse-faches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klasseFachDTO)))
            .andExpect(status().isCreated());

        // Validate the KlasseFach in the database
        List<KlasseFach> klasseFachList = klasseFachRepository.findAll();
        assertThat(klasseFachList).hasSize(databaseSizeBeforeCreate + 1);
        KlasseFach testKlasseFach = klasseFachList.get(klasseFachList.size() - 1);
    }

    @Test
    @Transactional
    public void createKlasseFachWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = klasseFachRepository.findAll().size();

        // Create the KlasseFach with an existing ID
        klasseFach.setId(1L);
        KlasseFachDTO klasseFachDTO = klasseFachMapper.toDto(klasseFach);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKlasseFachMockMvc.perform(post("/api/klasse-faches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klasseFachDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<KlasseFach> klasseFachList = klasseFachRepository.findAll();
        assertThat(klasseFachList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKlasseFaches() throws Exception {
        // Initialize the database
        klasseFachRepository.saveAndFlush(klasseFach);

        // Get all the klasseFachList
        restKlasseFachMockMvc.perform(get("/api/klasse-faches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(klasseFach.getId().intValue())));
    }

    @Test
    @Transactional
    public void getKlasseFach() throws Exception {
        // Initialize the database
        klasseFachRepository.saveAndFlush(klasseFach);

        // Get the klasseFach
        restKlasseFachMockMvc.perform(get("/api/klasse-faches/{id}", klasseFach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(klasseFach.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingKlasseFach() throws Exception {
        // Get the klasseFach
        restKlasseFachMockMvc.perform(get("/api/klasse-faches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKlasseFach() throws Exception {
        // Initialize the database
        klasseFachRepository.saveAndFlush(klasseFach);
        int databaseSizeBeforeUpdate = klasseFachRepository.findAll().size();

        // Update the klasseFach
        KlasseFach updatedKlasseFach = klasseFachRepository.findOne(klasseFach.getId());
        KlasseFachDTO klasseFachDTO = klasseFachMapper.toDto(updatedKlasseFach);

        restKlasseFachMockMvc.perform(put("/api/klasse-faches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klasseFachDTO)))
            .andExpect(status().isOk());

        // Validate the KlasseFach in the database
        List<KlasseFach> klasseFachList = klasseFachRepository.findAll();
        assertThat(klasseFachList).hasSize(databaseSizeBeforeUpdate);
        KlasseFach testKlasseFach = klasseFachList.get(klasseFachList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingKlasseFach() throws Exception {
        int databaseSizeBeforeUpdate = klasseFachRepository.findAll().size();

        // Create the KlasseFach
        KlasseFachDTO klasseFachDTO = klasseFachMapper.toDto(klasseFach);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKlasseFachMockMvc.perform(put("/api/klasse-faches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(klasseFachDTO)))
            .andExpect(status().isCreated());

        // Validate the KlasseFach in the database
        List<KlasseFach> klasseFachList = klasseFachRepository.findAll();
        assertThat(klasseFachList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKlasseFach() throws Exception {
        // Initialize the database
        klasseFachRepository.saveAndFlush(klasseFach);
        int databaseSizeBeforeDelete = klasseFachRepository.findAll().size();

        // Get the klasseFach
        restKlasseFachMockMvc.perform(delete("/api/klasse-faches/{id}", klasseFach.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KlasseFach> klasseFachList = klasseFachRepository.findAll();
        assertThat(klasseFachList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KlasseFach.class);
        KlasseFach klasseFach1 = new KlasseFach();
        klasseFach1.setId(1L);
        KlasseFach klasseFach2 = new KlasseFach();
        klasseFach2.setId(klasseFach1.getId());
        assertThat(klasseFach1).isEqualTo(klasseFach2);
        klasseFach2.setId(2L);
        assertThat(klasseFach1).isNotEqualTo(klasseFach2);
        klasseFach1.setId(null);
        assertThat(klasseFach1).isNotEqualTo(klasseFach2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KlasseFachDTO.class);
        KlasseFachDTO klasseFachDTO1 = new KlasseFachDTO();
        klasseFachDTO1.setId(1L);
        KlasseFachDTO klasseFachDTO2 = new KlasseFachDTO();
        assertThat(klasseFachDTO1).isNotEqualTo(klasseFachDTO2);
        klasseFachDTO2.setId(klasseFachDTO1.getId());
        assertThat(klasseFachDTO1).isEqualTo(klasseFachDTO2);
        klasseFachDTO2.setId(2L);
        assertThat(klasseFachDTO1).isNotEqualTo(klasseFachDTO2);
        klasseFachDTO1.setId(null);
        assertThat(klasseFachDTO1).isNotEqualTo(klasseFachDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(klasseFachMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(klasseFachMapper.fromId(null)).isNull();
    }
}
