package com.mongodb.controllers;

import com.mongodb.models.PatientNotes;
import com.mongodb.services.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class MongoController {

    @Autowired
    private MongoService mongoService;

    /**
     * get all the patient's notes with his ID
     *
     * @param patId patient's ID
     * @return all the notes of the patient
     */
    @GetMapping("/back/{patId}/notes")
    public List<PatientNotes> showPatientNotes(@PathVariable Integer patId) {
        return mongoService.getPatientNotesByPatId(patId);
    }

    /**
     * Add a note to a patient in database
     *
     * @param patientNote note to add
     */
    @PostMapping("/back/addNote")
    public void addNoteForPatient(@RequestBody PatientNotes patientNote) {
        mongoService.addPatientNote(patientNote);
    }
}
