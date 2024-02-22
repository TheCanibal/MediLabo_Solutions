package com.back.services;

import com.back.exceptions.PatientNotFoundException;
import com.back.models.Patient;
import com.back.repositories.PatientRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepositoryJPA patientRepositoryJPA;

    /**
     * get all patients in database
     *
     * @return all patient from database
     */
    public List<Patient> getAllPatients() {
        return patientRepositoryJPA.findAll();
    }

    /**
     * get one patient with his ID
     *
     * @param id patient's id
     * @return patient with id
     */
    public Optional<Patient> getPatientById(Integer id) {
        Optional<Patient> patient = patientRepositoryJPA.findById(id);
        if (patient.isPresent()) {
            return patientRepositoryJPA.findById(id);
        } else {
            throw new PatientNotFoundException("No patient with this id");
        }
    }

    /**
     * Add a patient to database
     *
     * @param patient patient to add
     */
    public void addPatient(Patient patient) {
        patientRepositoryJPA.save(patient);
    }

    /**
     * update a patient in database
     *
     * @param patient patient to update
     */
    public void updatePatient(Patient patient) {
        patientRepositoryJPA.save(patient);
    }
}
