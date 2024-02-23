package com.riskevaluator.util;

import com.riskevaluator.beans.PatientBean;
import com.riskevaluator.beans.PatientNotesBean;
import com.riskevaluator.proxies.MicroserviceBackProxy;
import com.riskevaluator.proxies.MicroserviceMongoDBProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RiskEvaluatorUtil {

    @Autowired
    private MicroserviceBackProxy backProxy;

    @Autowired
    private MicroserviceMongoDBProxy mongoDBProxy;

    /**
     * List of the trigger terms to calculate risk to have diabete
     */
    private final List<String> triggersTerms = new ArrayList<String>(Arrays.asList("hémoglobine a1c", "microalbumine", "taille",
            "poids", "fume", "anormal", "cholestérol", "vertiges", "rechute", "réaction", "anticorps"));

    /**
     * Get the number of trigger terms in patient's notes
     * @param patId patient's id
     * @return number of trigger terms in notes
     */
    public int getNumberOfTriggerTermsByPatId(Integer patId) {
        int i = 0;
        String concatenatedNotes = concatenateAllPatientNotes(lowerCasePatientNotes(getNotesWithPatId(patId)));
        for (String term : triggersTerms) {

            if (concatenatedNotes.contains(term)) {
                i++;
            }
        }
        return i;
    }

    /**
     * Get all notes of a patient
     * @param patId patient's id
     * @return all patient's note
     */
    public List<String> getNotesWithPatId(Integer patId) {
        List<PatientNotesBean> patientNotes = mongoDBProxy.showPatientNotes(patId);
        List<String> notes = new ArrayList<>();
        for (PatientNotesBean note : patientNotes) {
            notes.add(note.getNote());
        }
        return notes;
    }

    /**
     * Lower case notes
     * @param notes all patient's notes
     * @return all notes lower cased
     */
    public List<String> lowerCasePatientNotes(List<String> notes) {
        return notes.stream().map(String::toLowerCase).toList();
    }

    /**
     * Concatenate all notes in one note
     * @param notes list of notes to concatenante
     * @return one note (concatenation of all notes oin the list)
     */
    public String concatenateAllPatientNotes(List<String> notes) {
        StringBuilder concatenatedNotes = new StringBuilder();
        for (String s : lowerCasePatientNotes(notes)) {
            concatenatedNotes.append(" ").append(s);
        }
        return concatenatedNotes.toString();
    }

    /**
     * get patient's age
     * @param patId patient's id
     * @return patient's age
     */
    public int getAge(Integer patId) {
        PatientBean patient = backProxy.showOnePatientInformations(patId);
        return Period.between(patient.getBirthdate(), LocalDate.now()).getYears();
    }

    /**
     * get patient's gender
     * @param patId patient's id
     * @return patient's gender
     */
    public String getGender(Integer patId) {
        PatientBean patient = backProxy.showOnePatientInformations(patId);
        return patient.getGender();
    }

    /**
     * Show the risk to have diabete
     * @param patId patient's id
     * @return risk to have diabete
     */
    public String getRiskToHaveDiabete(Integer patId) {
        int numberOfTriggerTerms = getNumberOfTriggerTermsByPatId(patId);
        int patientAge = getAge(patId);
        String patientGender = getGender(patId);
        if (numberOfTriggerTerms == 0) {
            return "None";
        } else if (numberOfTriggerTerms >= 2 && numberOfTriggerTerms <= 5 && patientAge > 30) {
            return "Borderline";
        } else if ((patientGender.equals("M") && patientAge <= 30 && numberOfTriggerTerms >= 5)
                || (patientGender.equals("F") && patientAge <= 30 && numberOfTriggerTerms >= 7)
                || (patientAge > 30 && numberOfTriggerTerms >= 8)) {
            return "Early onset";
        } else if ((patientGender.equals("M") && patientAge <= 30 && numberOfTriggerTerms >= 3)
                || (patientGender.equals("F") && patientAge <= 30 && numberOfTriggerTerms >= 5)
                || (patientAge > 30 && numberOfTriggerTerms >= 6)) {
            return "In Danger";
        } else {
            return "No risks determinated";
        }
    }
}
