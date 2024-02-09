package com.back.repositories;

import com.back.models.PatientNotes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepositoryMongoDB extends MongoRepository<PatientNotes, String> {

    public List<PatientNotes> findByPatId(Integer patId);


}
