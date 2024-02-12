package com.mongodb.services;

import com.mongodb.models.PatientNotes;
import com.mongodb.repositories.MongoDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoService {

    @Autowired
    private MongoDbRepository patientRepositoryMongoDB;

    /**
     * get all notes of a patient
     *
     * @param patId id of the patient to get notes
     * @return all the notes of a patient
     */
    public List<PatientNotes> getPatientNotesByPatId(Integer patId) {
        return patientRepositoryMongoDB.findByPatId(patId);
    }

    /**
     * add a note for a patient
     *
     * @param patientNote note to add
     */
    public void addPatientNote(PatientNotes patientNote) {
        patientRepositoryMongoDB.insert(patientNote);
    }
}
