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

    public List<PatientBean> showAllPatients() {
        return backProxy.showAllPatients();
    }

    public Optional<PatientBean> showOnePatientInformations(Integer id) {
        Optional<PatientBean> patient = backProxy.showOnePatientInformations(id);
        if (patient.isPresent()) {
            return backProxy.showOnePatientInformations(id);
        } else {
            throw new NotFoundException("Patient not found, wrong id");
        }
    }

    public void addPatientValidate(PatientBean patient) {
        backProxy.addPatientValidate(patient);
    }

    public void updatePatientValidate(Integer id, PatientBean patient) {
        backProxy.updatePatientValidate(id, patient);
    }

    public void addNoteForPatient(PatientNotesBean patientNotes) {
        mongoDBProxy.addNoteForPatient(patientNotes);
    }

    public List<PatientNotesBean> showPatientNotes(Integer patId) {
        List<PatientNotesBean> listNotes = mongoDBProxy.showPatientNotes(patId);
        if(listNotes.isEmpty()) {
            throw new NotFoundException("Notes not found, wrong patient id");
        } else {
            return mongoDBProxy.showPatientNotes(patId);
        }
    }

    public String riskToHaveDiabete(Integer patId) {
        return riskEvaluator.riskToHaveDiabete(patId);
    }
}
