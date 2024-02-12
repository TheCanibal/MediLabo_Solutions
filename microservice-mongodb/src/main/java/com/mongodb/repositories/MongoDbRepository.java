package com.mongodb.repositories;

import com.mongodb.models.PatientNotes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoDbRepository extends MongoRepository<PatientNotes, String> {

    /**
     * Get all notes with the patId
     *
     * @param patId patId to get his notes
     * @return all notes of the patient with patId
     */
    public List<PatientNotes> findByPatId(Integer patId);
}
