package com.back.controllers;

import com.back.models.Patient;
import com.back.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BackController {

    @Autowired
    private PatientService patientService;

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
}
