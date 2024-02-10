package com.back.controllers;

import com.back.models.Patient;
import com.back.models.PatientNotes;
import com.back.services.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BackController {

    private final PatientService patientService;

    public BackController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Get all patients in database
     *
     * @return all patients in database
     */
    @GetMapping("/back/list")
    public List<Patient> showAllPatients() {
        return patientService.getAllPatients();
    }

    /**
     * get a patient with his ID if exists
     *
     * @param id patient's id
     * @return one patient with the ID
     */
    @GetMapping("/back/{id}")
    public Optional<Patient> showOnePatientInformations(@PathVariable Integer id) {
        return patientService.getPatientById(id);
    }

    /**
     * get all the patient's notes with his ID
     *
     * @param patId patient's ID
     * @return all the notes of the patient
     */
    @GetMapping("/back/{patId}/notes")
    public List<PatientNotes> showPatientNotes(@PathVariable Integer patId) {
        return patientService.getPatientNotesByPatId(patId);
    }

    /**
     * Add a patient to database
     *
     * @param patient patient to add in database
     */
    @PostMapping("/back/add/validate")
    public void addPatientValidate(@RequestBody Patient patient) {
        patientService.addPatient(patient);
    }

    /**
     * Update patient information after validation criteria
     *
     * @param id      patient's ID needed to update it
     * @param patient patient to update
     */
    @PostMapping("/back/update/{id}")
    public void updatePatientValidate(@PathVariable Integer id, @RequestBody Patient patient) {
        patientService.updatePatient(patient);
    }

    /**
     * Add a note to a patient in database
     *
     * @param patientNote note to add
     */
    @PostMapping("/back/addNote")
    public void addNoteForPatient(@RequestBody PatientNotes patientNote) {
        patientService.addPatientNote(patientNote);
    }
}
