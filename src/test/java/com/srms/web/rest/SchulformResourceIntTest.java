package com.srms.web.rest;

import com.srms.SrmsApp;

import com.srms.domain.Schulform;
import com.srms.repository.SchulformRepository;
import com.srms.service.SchulformService;
import com.srms.service.dto.SchulformDTO;
import com.srms.service.mapper.SchulformMapper;
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
 * Test class for the SchulformResource REST controller.
 *
 * @see SchulformResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SrmsApp.class)
public class SchulformResourceIntTest {

    private static final String DEFAULT_BEZEICHNUNG = "AAAAAAAAAA";
    private static final String UPDATED_BEZEICHNUNG = "BBBBBBBBBB";

    @Autowired
    private SchulformRepository schulformRepository;

    @Autowired
    private SchulformMapper schulformMapper;

    @Autowired
    private SchulformService schulformService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchulformMockMvc;

    private Schulform schulform;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchulformResource schulformResource = new SchulformResource(schulformService);
        this.restSchulformMockMvc = MockMvcBuilders.standaloneSetup(schulformResource)
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
    public static Schulform createEntity(EntityManager em) {
        Schulform schulform = new Schulform()
            .bezeichnung(DEFAULT_BEZEICHNUNG);
        return schulform;
    }

    @Before
    public void initTest() {
        schulform = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchulform() throws Exception {
        int databaseSizeBeforeCreate = schulformRepository.findAll().size();

        // Create the Schulform
        SchulformDTO schulformDTO = schulformMapper.toDto(schulform);
        restSchulformMockMvc.perform(post("/api/schulforms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schulformDTO)))
            .andExpect(status().isCreated());

        // Validate the Schulform in the database
        List<Schulform> schulformList = schulformRepository.findAll();
        assertThat(schulformList).hasSize(databaseSizeBeforeCreate + 1);
        Schulform testSchulform = schulformList.get(schulformList.size() - 1);
        assertThat(testSchulform.getBezeichnung()).isEqualTo(DEFAULT_BEZEICHNUNG);
    }

    @Test
    @Transactional
    public void createSchulformWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schulformRepository.findAll().size();

        // Create the Schulform with an existing ID
        schulform.setId(1L);
        SchulformDTO schulformDTO = schulformMapper.toDto(schulform);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchulformMockMvc.perform(post("/api/schulforms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schulformDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Schulform> schulformList = schulformRepository.findAll();
        assertThat(schulformList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBezeichnungIsRequired() throws Exception {
        int databaseSizeBeforeTest = schulformRepository.findAll().size();
        // set the field null
        schulform.setBezeichnung(null);

        // Create the Schulform, which fails.
        SchulformDTO schulformDTO = schulformMapper.toDto(schulform);

        restSchulformMockMvc.perform(post("/api/schulforms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schulformDTO)))
            .andExpect(status().isBadRequest());

        List<Schulform> schulformList = schulformRepository.findAll();
        assertThat(schulformList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchulforms() throws Exception {
        // Initialize the database
        schulformRepository.saveAndFlush(schulform);

        // Get all the schulformList
        restSchulformMockMvc.perform(get("/api/schulforms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schulform.getId().intValue())))
            .andExpect(jsonPath("$.[*].bezeichnung").value(hasItem(DEFAULT_BEZEICHNUNG.toString())));
    }

    @Test
    @Transactional
    public void getSchulform() throws Exception {
        // Initialize the database
        schulformRepository.saveAndFlush(schulform);

        // Get the schulform
        restSchulformMockMvc.perform(get("/api/schulforms/{id}", schulform.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schulform.getId().intValue()))
            .andExpect(jsonPath("$.bezeichnung").value(DEFAULT_BEZEICHNUNG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchulform() throws Exception {
        // Get the schulform
        restSchulformMockMvc.perform(get("/api/schulforms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchulform() throws Exception {
        // Initialize the database
        schulformRepository.saveAndFlush(schulform);
        int databaseSizeBeforeUpdate = schulformRepository.findAll().size();

        // Update the schulform
        Schulform updatedSchulform = schulformRepository.findOne(schulform.getId());
        updatedSchulform
            .bezeichnung(UPDATED_BEZEICHNUNG);
        SchulformDTO schulformDTO = schulformMapper.toDto(updatedSchulform);

        restSchulformMockMvc.perform(put("/api/schulforms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schulformDTO)))
            .andExpect(status().isOk());

        // Validate the Schulform in the database
        List<Schulform> schulformList = schulformRepository.findAll();
        assertThat(schulformList).hasSize(databaseSizeBeforeUpdate);
        Schulform testSchulform = schulformList.get(schulformList.size() - 1);
        assertThat(testSchulform.getBezeichnung()).isEqualTo(UPDATED_BEZEICHNUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingSchulform() throws Exception {
        int databaseSizeBeforeUpdate = schulformRepository.findAll().size();

        // Create the Schulform
        SchulformDTO schulformDTO = schulformMapper.toDto(schulform);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchulformMockMvc.perform(put("/api/schulforms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schulformDTO)))
            .andExpect(status().isCreated());

        // Validate the Schulform in the database
        List<Schulform> schulformList = schulformRepository.findAll();
        assertThat(schulformList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchulform() throws Exception {
        // Initialize the database
        schulformRepository.saveAndFlush(schulform);
        int databaseSizeBeforeDelete = schulformRepository.findAll().size();

        // Get the schulform
        restSchulformMockMvc.perform(delete("/api/schulforms/{id}", schulform.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Schulform> schulformList = schulformRepository.findAll();
        assertThat(schulformList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Schulform.class);
        Schulform schulform1 = new Schulform();
        schulform1.setId(1L);
        Schulform schulform2 = new Schulform();
        schulform2.setId(schulform1.getId());
        assertThat(schulform1).isEqualTo(schulform2);
        schulform2.setId(2L);
        assertThat(schulform1).isNotEqualTo(schulform2);
        schulform1.setId(null);
        assertThat(schulform1).isNotEqualTo(schulform2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchulformDTO.class);
        SchulformDTO schulformDTO1 = new SchulformDTO();
        schulformDTO1.setId(1L);
        SchulformDTO schulformDTO2 = new SchulformDTO();
        assertThat(schulformDTO1).isNotEqualTo(schulformDTO2);
        schulformDTO2.setId(schulformDTO1.getId());
        assertThat(schulformDTO1).isEqualTo(schulformDTO2);
        schulformDTO2.setId(2L);
        assertThat(schulformDTO1).isNotEqualTo(schulformDTO2);
        schulformDTO1.setId(null);
        assertThat(schulformDTO1).isNotEqualTo(schulformDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(schulformMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(schulformMapper.fromId(null)).isNull();
    }
}
