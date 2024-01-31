package com.back.controllers;

import com.back.models.Patient;
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

    @GetMapping("/back/list")
    public List<Patient> showAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/back/{id}")
    public Optional<Patient> showOnePatientInformations(@PathVariable Integer id) {
        return patientService.getPatientById(id);
    }

    @PostMapping("/back/add/validate")
    public Patient addPatientValidate(Patient patient) {
        return patientService.addPatient(patient);
    }
}
