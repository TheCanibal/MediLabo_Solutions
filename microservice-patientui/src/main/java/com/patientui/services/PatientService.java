package com.patientui.services;

import com.patientui.beans.PatientBean;
import com.patientui.beans.PatientNotesBean;
import com.patientui.exceptions.NotFoundException;
import com.patientui.proxies.MicroserviceBackProxy;
import com.patientui.proxies.MicroserviceMongoDBProxy;
import com.patientui.proxies.MicroserviceRiskEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private MicroserviceBackProxy backProxy;

    @Autowired
    private MicroserviceMongoDBProxy mongoDBProxy;

    @Autowired
    private MicroserviceRiskEvaluator riskEvaluator;

    /**
     * Recover all patients from database
     * @return all patients from database
     */
    public List<PatientBean> showAllPatients() {
        return backProxy.showAllPatients();
    }

    /**
     * Recover patient with his id
     * @param id patient's id
     * @return patient if exists else throw NotFoundException
     */
    public Optional<PatientBean> showOnePatientInformations(Integer id) {
        Optional<PatientBean> patient = backProxy.showOnePatientInformations(id);
        if (patient.isPresent()) {
            return backProxy.showOnePatientInformations(id);
        } else {
            throw new NotFoundException("Patient not found, wrong id");
        }
    }

    /**
     * Add patient in database
     * @param patient patient to add
     */
    public void addPatientValidate(PatientBean patient) {
        backProxy.addPatientValidate(patient);
    }

    /**
     * Update patient in database
     * @param id patient's id
     * @param patient patient to update
     */
    public void updatePatientValidate(Integer id, PatientBean patient) {
        backProxy.updatePatientValidate(id, patient);
    }

    /**
     * Add a note for a patient thanks to his id
     * @param patientNote note to add
     */
    public void addNoteForPatient(PatientNotesBean patientNote) {
        mongoDBProxy.addNoteForPatient(patientNote);
    }

    /**
     * Get all patient notes with his id
     * @param patId patient's id
     * @return all notes of patient
     */
    public List<PatientNotesBean> showPatientNotes(Integer patId) {
        List<PatientNotesBean> listNotes = mongoDBProxy.showPatientNotes(patId);
        if(listNotes.isEmpty()) {
            throw new NotFoundException("Notes not found, wrong patient id");
        } else {
            return mongoDBProxy.showPatientNotes(patId);
        }
    }

    /**
     * Get the risk to have diabete
     * @param patId patient"s id
     * @return risk to have diabete
     */
    public String riskToHaveDiabete(Integer patId) {
        return riskEvaluator.riskToHaveDiabete(patId);
    }
}
