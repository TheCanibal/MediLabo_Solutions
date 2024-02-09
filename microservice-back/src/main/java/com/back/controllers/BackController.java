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

    @GetMapping("/back/list")
    public List<Patient> showAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/back/{id}")
    public Optional<Patient> showOnePatientInformations(@PathVariable Integer id) {
        return patientService.getPatientById(id);
    }

    @GetMapping("/back/{patId}/notes")
    public List<PatientNotes> showPatientNotes(@PathVariable Integer patId) {
        return patientService.getPatientNotesByPatId(patId);
    }

    @PostMapping("/back/add/validate")
    public Patient addPatientValidate(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @PostMapping("/back/update/{id}")
    public Patient updatePatientValidate(@PathVariable Integer id, @RequestBody Patient patient) { return patientService.updatePatient(patient);}

    @PostMapping("/back/addNote")
    public PatientNotes addNoteForPatient(@RequestBody PatientNotes patientNote) {
        System.out.println(patientNote.getPatId() + " " + patientNote.getNote());
        return patientService.addPatientNote(patientNote);
    }
}
