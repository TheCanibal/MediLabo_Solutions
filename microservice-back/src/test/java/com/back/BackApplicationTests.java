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
import org.springframework.util.LinkedMultiValueMap;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
		Patient patient = new Patient("First Name", "Last Name", LocalDate.of(2000,2,2), "F", "testaddress", "phone");

		// Save
		patientService.addPatient(patient);
		assertNotNull(patient.getId());
		assertEquals(patient.getFirstName(), "First Name", "First Name");

		// Update
		patient.setFirstName("First Name Updated");
		patientService.updatePatient(patient);
		assertEquals(patient.getFirstName(), "First Name Updated","First Name Updated");

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
		Patient patient = new Patient("First Name", "Last Name", LocalDate.of(2000,2,2), "F", "testaddress", "phone");

		patientService.addPatient(patient);
		assertNotNull(patient.getId());
		assertEquals(patient.getFirstName(), "First Name", "First Name");

		Integer id = patient.getId();

		mockMvc.perform(get("/back/{id}", id)).andExpect(status().isOk());
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
		Patient patient = new Patient("First Name", "Last Name", LocalDate.of(2000, 2, 2), "F", "testaddress", "phone");
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();

		patientService.addPatient(patient);
		assertNotNull(patient.getId());
		assertEquals(patient.getFirstName(), "First Name");

		Integer id = patient.getId();

		mockMvc.perform(post("/back/update/{id}", id).flashAttr("patient", patient).param("firstName", "First Name Modify").content(mapper.writeValueAsString(patient)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertEquals(patient.getFirstName(), "First Name Modify");

	}

}
