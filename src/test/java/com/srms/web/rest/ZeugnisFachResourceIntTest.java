package com.srms.web.rest;

import com.srms.SrmsApp;

import com.srms.domain.ZeugnisFach;
import com.srms.repository.ZeugnisFachRepository;
import com.srms.service.ZeugnisFachService;
import com.srms.service.dto.ZeugnisFachDTO;
import com.srms.service.mapper.ZeugnisFachMapper;
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
 * Test class for the ZeugnisFachResource REST controller.
 *
 * @see ZeugnisFachResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SrmsApp.class)
public class ZeugnisFachResourceIntTest {

    private static final Integer DEFAULT_NOTE = 1;
    private static final Integer UPDATED_NOTE = 2;

    @Autowired
    private ZeugnisFachRepository zeugnisFachRepository;

    @Autowired
    private ZeugnisFachMapper zeugnisFachMapper;

    @Autowired
    private ZeugnisFachService zeugnisFachService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restZeugnisFachMockMvc;

    private ZeugnisFach zeugnisFach;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ZeugnisFachResource zeugnisFachResource = new ZeugnisFachResource(zeugnisFachService);
        this.restZeugnisFachMockMvc = MockMvcBuilders.standaloneSetup(zeugnisFachResource)
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
    public static ZeugnisFach createEntity(EntityManager em) {
        ZeugnisFach zeugnisFach = new ZeugnisFach()
            .note(DEFAULT_NOTE);
        return zeugnisFach;
    }

    @Before
    public void initTest() {
        zeugnisFach = createEntity(em);
    }

    @Test
    @Transactional
    public void createZeugnisFach() throws Exception {
        int databaseSizeBeforeCreate = zeugnisFachRepository.findAll().size();

        // Create the ZeugnisFach
        ZeugnisFachDTO zeugnisFachDTO = zeugnisFachMapper.toDto(zeugnisFach);
        restZeugnisFachMockMvc.perform(post("/api/zeugnis-faches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zeugnisFachDTO)))
            .andExpect(status().isCreated());

        // Validate the ZeugnisFach in the database
        List<ZeugnisFach> zeugnisFachList = zeugnisFachRepository.findAll();
        assertThat(zeugnisFachList).hasSize(databaseSizeBeforeCreate + 1);
        ZeugnisFach testZeugnisFach = zeugnisFachList.get(zeugnisFachList.size() - 1);
        assertThat(testZeugnisFach.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createZeugnisFachWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zeugnisFachRepository.findAll().size();

        // Create the ZeugnisFach with an existing ID
        zeugnisFach.setId(1L);
        ZeugnisFachDTO zeugnisFachDTO = zeugnisFachMapper.toDto(zeugnisFach);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZeugnisFachMockMvc.perform(post("/api/zeugnis-faches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zeugnisFachDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ZeugnisFach> zeugnisFachList = zeugnisFachRepository.findAll();
        assertThat(zeugnisFachList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllZeugnisFaches() throws Exception {
        // Initialize the database
        zeugnisFachRepository.saveAndFlush(zeugnisFach);

        // Get all the zeugnisFachList
        restZeugnisFachMockMvc.perform(get("/api/zeugnis-faches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zeugnisFach.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @Test
    @Transactional
    public void getZeugnisFach() throws Exception {
        // Initialize the database
        zeugnisFachRepository.saveAndFlush(zeugnisFach);

        // Get the zeugnisFach
        restZeugnisFachMockMvc.perform(get("/api/zeugnis-faches/{id}", zeugnisFach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zeugnisFach.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    public void getNonExistingZeugnisFach() throws Exception {
        // Get the zeugnisFach
        restZeugnisFachMockMvc.perform(get("/api/zeugnis-faches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZeugnisFach() throws Exception {
        // Initialize the database
        zeugnisFachRepository.saveAndFlush(zeugnisFach);
        int databaseSizeBeforeUpdate = zeugnisFachRepository.findAll().size();

        // Update the zeugnisFach
        ZeugnisFach updatedZeugnisFach = zeugnisFachRepository.findOne(zeugnisFach.getId());
        updatedZeugnisFach
            .note(UPDATED_NOTE);
        ZeugnisFachDTO zeugnisFachDTO = zeugnisFachMapper.toDto(updatedZeugnisFach);

        restZeugnisFachMockMvc.perform(put("/api/zeugnis-faches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zeugnisFachDTO)))
            .andExpect(status().isOk());

        // Validate the ZeugnisFach in the database
        List<ZeugnisFach> zeugnisFachList = zeugnisFachRepository.findAll();
        assertThat(zeugnisFachList).hasSize(databaseSizeBeforeUpdate);
        ZeugnisFach testZeugnisFach = zeugnisFachList.get(zeugnisFachList.size() - 1);
        assertThat(testZeugnisFach.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingZeugnisFach() throws Exception {
        int databaseSizeBeforeUpdate = zeugnisFachRepository.findAll().size();

        // Create the ZeugnisFach
        ZeugnisFachDTO zeugnisFachDTO = zeugnisFachMapper.toDto(zeugnisFach);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restZeugnisFachMockMvc.perform(put("/api/zeugnis-faches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zeugnisFachDTO)))
            .andExpect(status().isCreated());

        // Validate the ZeugnisFach in the database
        List<ZeugnisFach> zeugnisFachList = zeugnisFachRepository.findAll();
        assertThat(zeugnisFachList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteZeugnisFach() throws Exception {
        // Initialize the database
        zeugnisFachRepository.saveAndFlush(zeugnisFach);
        int databaseSizeBeforeDelete = zeugnisFachRepository.findAll().size();

        // Get the zeugnisFach
        restZeugnisFachMockMvc.perform(delete("/api/zeugnis-faches/{id}", zeugnisFach.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ZeugnisFach> zeugnisFachList = zeugnisFachRepository.findAll();
        assertThat(zeugnisFachList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZeugnisFach.class);
        ZeugnisFach zeugnisFach1 = new ZeugnisFach();
        zeugnisFach1.setId(1L);
        ZeugnisFach zeugnisFach2 = new ZeugnisFach();
        zeugnisFach2.setId(zeugnisFach1.getId());
        assertThat(zeugnisFach1).isEqualTo(zeugnisFach2);
        zeugnisFach2.setId(2L);
        assertThat(zeugnisFach1).isNotEqualTo(zeugnisFach2);
        zeugnisFach1.setId(null);
        assertThat(zeugnisFach1).isNotEqualTo(zeugnisFach2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZeugnisFachDTO.class);
        ZeugnisFachDTO zeugnisFachDTO1 = new ZeugnisFachDTO();
        zeugnisFachDTO1.setId(1L);
        ZeugnisFachDTO zeugnisFachDTO2 = new ZeugnisFachDTO();
        assertThat(zeugnisFachDTO1).isNotEqualTo(zeugnisFachDTO2);
        zeugnisFachDTO2.setId(zeugnisFachDTO1.getId());
        assertThat(zeugnisFachDTO1).isEqualTo(zeugnisFachDTO2);
        zeugnisFachDTO2.setId(2L);
        assertThat(zeugnisFachDTO1).isNotEqualTo(zeugnisFachDTO2);
        zeugnisFachDTO1.setId(null);
        assertThat(zeugnisFachDTO1).isNotEqualTo(zeugnisFachDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(zeugnisFachMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(zeugnisFachMapper.fromId(null)).isNull();
    }
}
