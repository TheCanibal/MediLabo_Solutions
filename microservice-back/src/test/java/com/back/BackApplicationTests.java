package com.back;

import com.back.models.Patient;
import com.back.services.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BackApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientService patientService;

    @Test
    void contextLoads() {
    }

    @Test
    public void PatientTest() {
        Patient patient = new Patient("First Name", "Last Name", LocalDate.of(2000, 2, 2), "F", "testaddress", "phone");

        // Save
        patientService.addPatient(patient);
        assertNotNull(patient.getId());
        assertEquals(patient.getFirstName(), "First Name", "First Name");

        // Update
        patient.setFirstName("First Name Updated");
        patientService.updatePatient(patient);
        assertEquals(patient.getFirstName(), "First Name Updated", "First Name Updated");

        // Find
        List<Patient> listResult = patientService.getAllPatients();
        assertFalse(listResult.isEmpty());
    }

    @Test
    public void shouldReturnAllPatient() throws Exception {
        mockMvc.perform(get("/back/list")).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOnePatientInformations() throws Exception {
        Patient patient = new Patient("First Name", "Last Name", LocalDate.of(2000, 2, 2), "F", "testaddress", "phone");

        patientService.addPatient(patient);
        assertNotNull(patient.getId());
        assertEquals(patient.getFirstName(), "First Name", "First Name");

        Integer id = patient.getId();

        mockMvc.perform(get("/back/{id}", id)).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundPatientException() throws Exception {

        mockMvc.perform(get("/back/{id}", 999999999)).andExpect(status().isNotFound());
    }

    @Test
    public void addPatientToDatabase() throws Exception {
        Patient patient = new Patient("First Name", "Last Name", LocalDate.of(2000, 2, 2), "F", "testaddress", "phone");
        int listPatientsSize = patientService.getAllPatients().size();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        mockMvc.perform(post("/back/add/validate").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(patient)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        assertEquals(patientService.getAllPatients().size(), listPatientsSize + 1);
    }

    @Test
    public void updatePatientInDatabase() throws Exception {

        Patient patient = patientService.getPatientById(1).get();
        assertEquals(patient.getFirstName(), "TestNone");
        patient.setFirstName("First Name Modify");
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        mockMvc.perform(post("/back/update/{id}", 1).content(mapper.writeValueAsString(patient)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        patient = patientService.getPatientById(1).get();
        assertEquals(patient.getFirstName(), "First Name Modify");


    }

}
