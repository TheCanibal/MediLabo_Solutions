package com.patientui.controllers;

import com.patientui.beans.PatientBean;
import com.patientui.beans.PatientNotesBean;
import com.patientui.proxies.MicroserviceBackProxy;
import com.patientui.proxies.MicroserviceMongoDBProxy;
import com.patientui.proxies.MicroserviceRiskEvaluator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private MicroserviceBackProxy backProxy;

    @Autowired
    private MicroserviceMongoDBProxy mongoDBProxy;

    @Autowired
    private MicroserviceRiskEvaluator riskEvaluator;


    /**
     * show the patient list in a html page
     *
     * @param model
     * @return html template
     */
    @GetMapping("/list")
    public String showPatientsList(Model model) {
        List<PatientBean> patients = backProxy.showAllPatients();
        model.addAttribute("patients", patients);
        return "patient/list";
    }

    /**
     * show a patient information page with his id
     *
     * @param id    patient's id
     * @param model
     * @return html template
     */
    @GetMapping("/information/{id}")
    public String showPatientInformation(@PathVariable("id") Integer id, Model model) {
        PatientBean patientInfo = backProxy.showOnePatientInformations(id);
        List<PatientNotesBean> patientNotes = mongoDBProxy.showPatientNotes(id);
        PatientNotesBean patientNote = new PatientNotesBean();
        patientNote.setPatId(patientInfo.getId());
        patientNote.setPatient(patientInfo.getFirstName());
        riskEvaluator.risks(id);
        model.addAttribute("patientInfo", patientInfo);
        model.addAttribute("patientNotes", patientNotes);
        model.addAttribute("patientNote", patientNote);
        return "patient/information";
    }

    /**
     * show the add a patient page
     *
     * @param patient patient to add as model attribute
     * @param model
     * @return html template
     */
    @GetMapping("/add")
    public String showAddPatientPage(PatientBean patient, Model model) {
        model.addAttribute("patient", patient);
        return "patient/add";
    }

    /**
     * Add a patient to the database after validation
     *
     * @param patient patient to add in database
     * @param result
     * @return add page if errors or redirection to the patient's list updated
     */
    @PostMapping("/add/validate")
    public String addPatientValidation(@Valid @ModelAttribute("patient") PatientBean patient, BindingResult result) {
        if (result.hasErrors()) {
            return "patient/add";
        }
        backProxy.addPatientValidate(patient);
        return "redirect:http://localhost:9003/patient/list";
    }

    /**
     * show the update patient page
     *
     * @param id    patient's id
     * @param model
     * @return html template
     */
    @GetMapping("/update/{id}")
    public String showUpdatePatientPage(@PathVariable Integer id, Model model) {
        PatientBean patient = backProxy.showOnePatientInformations(id);
        model.addAttribute("patient", patient);
        return "patient/update";
    }

    /**
     * Update a patient in the database
     *
     * @param id      patient's id
     * @param patient patient to update
     * @param result
     * @return update page if errors or redirection to the patient's list updated
     */
    @PostMapping("/update/{id}")
    public String updatePatientValidation(@PathVariable Integer id, @Valid PatientBean patient, BindingResult result) {
        if (result.hasErrors()) {
            return "patient/update";
        }
        backProxy.updatePatientValidate(id, patient);
        return "redirect:http://localhost:9003/patient/list";
    }

    /**
     * Add a note for a patient in database
     *
     * @param patientNote note to add in database
     * @return redirection to the information patient page
     */
    @PostMapping("/addNote")
    public String addNoteForPatient(PatientNotesBean patientNote) {
        System.out.println(patientNote.getPatient() + " patient " + patientNote.getPatId() + " id ");
        mongoDBProxy.addNoteForPatient(patientNote);
        return "redirect:http://localhost:9003/patient/information/" + patientNote.getPatId();
    }

}
