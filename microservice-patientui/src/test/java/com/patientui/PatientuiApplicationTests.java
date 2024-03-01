package com.patientui;

import com.patientui.beans.PatientBean;
import com.patientui.beans.PatientNotesBean;
import com.patientui.services.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PatientuiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientService patientService;

    @Test
    void contextLoads() {
    }

    @Test
    public void PatientTest() {
        PatientBean patient = new PatientBean("First Name", "Last Name", LocalDate.of(2000, 2, 2), "F", "testaddress", "phone");

        // Find
        List<PatientBean> listResult = patientService.showAllPatients();
        assertFalse(listResult.isEmpty());

        // Save
        patientService.addPatientValidate(patient);
        patient = patientService.showOnePatientInformations(patientService.showAllPatients().size()).get();
        assertNotNull(patient.getId());
        assertEquals(patient.getFirstName(), "First Name", "First Name");

        // Update
        patient.setFirstName("First Name Updated");
        patientService.updatePatientValidate(patient.getId(), patient);
        assertEquals(patient.getFirstName(), "First Name Updated", "First Name Updated");
    }

    @Test
    public void PatientNotesTest() {
        PatientNotesBean patientNotes = new PatientNotesBean(1, "TestNone", "Le patient est en pleine forme");
        int listPatientNotesSize = patientService.showPatientNotes(1).size();

        // Save
        patientService.addNoteForPatient(patientNotes);
        assertEquals(patientNotes.getPatient(), "TestNone", "TestNone");
        assertEquals(patientService.showPatientNotes(1).size(), listPatientNotesSize + 1);

        // Find
        List<PatientNotesBean> listResult = patientService.showPatientNotes(1);
        assertFalse(listResult.isEmpty());
    }

    @Test
    public void shouldReturnPatientListPage() throws Exception {
        mockMvc.perform(get("/patient/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("patients"))
                .andExpect(view().name("patient/list"));
    }

    @Test
    public void shouldReturnPatientInformationsPage() throws Exception {
        mockMvc.perform(get("/patient/information/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("patientInfo", "patientNotes", "patientNote", "risk"))
                .andExpect(view().name("patient/information"));
    }

    @Test
    public void shouldReturnPatientInformationsNotFound() throws Exception {
        mockMvc.perform(get("/patient/information/{id}", 999999999))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnAddPatientPage() throws Exception {
        mockMvc.perform(get("/patient/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient/add"));
    }

    @Test
    public void shouldReturnPatientUpdatePage() throws Exception {
        mockMvc.perform(get("/patient/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("patient/update"));
    }

    @Test
    public void shouldReturnPatientUpdateNotFound() throws Exception {
        mockMvc.perform(get("/patient/update/{id}", 999999999))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addPatientToDatabase() throws Exception {
        PatientBean patient = new PatientBean("First Name", "Last Name", LocalDate.of(2000, 2, 2), "F", "testaddress", "phone");
        int listPatientSize = patientService.showAllPatients().size();

        mockMvc.perform(post("/patient/add/validate").flashAttr("patient", patient))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:http://localhost:9003/patient/list"));

        assertEquals(patientService.showAllPatients().size(), listPatientSize + 1);
    }

    @Test
    public void addPatientToDatabaseWithEmptyField() throws Exception {

        mockMvc.perform(post("/patient/add/validate").param("firstName", "").param("lastName", "").param("birthdate", "").param("gender", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("patient/add"))
                .andExpect(model().attributeHasFieldErrors("patient", "firstName", "lastName", "birthdate", "gender"));
    }

    @Test
    public void addPatientNoteToDatabase() throws Exception {
        PatientNotesBean patientNote = new PatientNotesBean(1, "TestNone", "Le patient est en pleine forme");
        int listPatientNotesSize = patientService.showPatientNotes(1).size();

        mockMvc.perform(post("/patient/addNote").flashAttr("patientNote", patientNote))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:http://localhost:9003/patient/information/" + patientNote.getPatId()));

        assertEquals(patientService.showPatientNotes(1).size(), listPatientNotesSize + 1);

    }

    @Test
    public void updatePatientInDatabase() throws Exception {
        PatientBean patientToAdd = new PatientBean("First Name", "Last Name", LocalDate.of(2000, 2, 2), "F", "testaddress", "phone");
        patientService.addPatientValidate(patientToAdd);
        PatientBean patient = patientService.showOnePatientInformations(patientService.showAllPatients().size()).get();
        assertNotNull(patient.getId());
        assertEquals(patient.getFirstName(), "First Name");

        Integer id = patient.getId();

        mockMvc.perform(post("/patient/update/{id}", id).flashAttr("patient", patient).param("firstName", "First Name Modify").param("lastName", "Last Name Modify")
                        .param("birthdate", "2000-02-02").param("gender", "F"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:http://localhost:9003/patient/list"));

        patient = patientService.showOnePatientInformations(id).get();
        assertEquals(patient.getFirstName(), "First Name Modify");
        assertEquals(patient.getLastName(), "Last Name Modify");

    }

    @Test
    public void updatePatientInDatabaseWithEmptyField() throws Exception {
        PatientBean patientToAdd = new PatientBean("First Name", "Last Name", LocalDate.of(2000, 2, 2), "F", "testaddress", "phone");
        patientService.addPatientValidate(patientToAdd);
        PatientBean patient = patientService.showOnePatientInformations(patientService.showAllPatients().size()).get();
        assertNotNull(patient.getId());
        assertEquals(patient.getFirstName(), "First Name");

        Integer id = patient.getId();

        mockMvc.perform(post("/patient/update/{id}", id).flashAttr("patient", patient).param("firstName", "").param("lastName", "").param("birthdate", "").param("gender", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("patient/update"))
                .andExpect(model().attributeHasFieldErrors("patient", "firstName", "lastName", "birthdate", "gender"));
    }

}
