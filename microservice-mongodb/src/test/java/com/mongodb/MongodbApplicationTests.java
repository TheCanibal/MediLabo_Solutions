package com.mongodb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.models.PatientNotes;
import com.mongodb.services.MongoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MongodbApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoService mongoService;

    @Test
    public void PatientTest() {
        PatientNotes patientNotes = new PatientNotes(1, "TestNone", "Le patient est en pleine forme");

        // Save
        mongoService.addPatientNote(patientNotes);
        assertNotNull(patientNotes.getId());
        assertEquals(patientNotes.getPatient(), "TestNone", "TestNone");

        // Find
        List<PatientNotes> listResult = mongoService.getPatientNotesByPatId(1);
        assertFalse(listResult.isEmpty());
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void shouldReturnAllPatientNotesById() throws Exception {
        mockMvc.perform(get("/back/{patId}/notes", 1)).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotesNotFoundException() throws Exception {
        mockMvc.perform(get("/back/{patId}/notes", 7)).andExpect(status().isNotFound());
    }

    @Test
    public void testAddNotesToOnePatient() throws Exception {
        PatientNotes patientNotes = new PatientNotes(1, "TestNone", "Le patient est en pleine forme");
        int patientNotesSize = mongoService.getPatientNotesByPatId(1).size();
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/back/addNote").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(patientNotes)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        assertEquals(mongoService.getPatientNotesByPatId(1).size(), patientNotesSize + 1);
    }

}
