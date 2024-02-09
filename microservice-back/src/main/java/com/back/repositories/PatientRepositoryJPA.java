package com.back.repositories;

import com.back.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepositoryJPA extends JpaRepository<Patient, Integer> {
}
