package com.riskevaluator.controllers;

import com.riskevaluator.beans.PatientBean;
import com.riskevaluator.beans.PatientNotesBean;
import com.riskevaluator.proxies.MicroserviceBackProxy;
import com.riskevaluator.proxies.MicroserviceMongoDBProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RiskEvaluatorController {

    @Autowired
    private MicroserviceBackProxy backProxy;

    @Autowired
    private MicroserviceMongoDBProxy mongoDBProxy;

    private final List<String> triggersTerms = new ArrayList<String>(Arrays.asList("hémoglobine a1c", "microalbumine", "taille",
            "poids", "fume", "anormal", "cholestérol", "vertiges", "rechute", "réaction", "anticorps"));

    @GetMapping("/risk/{patId}")
    public String risks(@PathVariable Integer patId) {
        String waitedRisk;
        PatientBean patient = backProxy.showOnePatientInformations(patId);
        List<PatientNotesBean> patientNotes = mongoDBProxy.showPatientNotes(4);
        List<String> notes = new ArrayList<>();
        String concatenatedNotes = "";
        for (PatientNotesBean note : patientNotes) {
            notes.add(note.getNote());
        }
        List<String> notesLowerCase = notes.stream().map(String::toLowerCase).toList();
        System.out.println(notesLowerCase.size());
        for(int j = 0; j<notesLowerCase.size(); j++) {
            concatenatedNotes += " "+notesLowerCase.get(j);
        }
        System.out.println(concatenatedNotes);
        int i = 0;
        for (String term : triggersTerms) {

            if (concatenatedNotes.contains(term)) {
                i++;
                System.out.println(term);
            }
        }
        System.out.println(i);

        return "0";
    }
}
