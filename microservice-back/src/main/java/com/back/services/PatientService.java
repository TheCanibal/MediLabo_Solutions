package com.back.services;

import com.back.models.Patient;
import com.back.models.PatientNotes;
import com.back.repositories.PatientRepositoryJPA;
import com.back.repositories.PatientRepositoryMongoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PatientService {

    @Autowired
    private PatientRepositoryJPA patientRepositoryJPA;

    @Autowired
    private PatientRepositoryMongoDB patientRepositoryMongoDB;

    public List<Patient> getAllPatients() {
        return patientRepositoryJPA.findAll();
    }

    public Optional<Patient> getPatientById(Integer id) {
        return patientRepositoryJPA.findById(id);
    }

    public Patient addPatient(Patient patient) {
        return patientRepositoryJPA.save(patient);
    }

    public Patient updatePatient(Patient patient) {
        return patientRepositoryJPA.save(patient);
    }

    public List<PatientNotes> getPatientNotesByPatId(Integer patId) {
        return patientRepositoryMongoDB.findByPatId(patId);
    }
}
