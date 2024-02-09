package com.patientui.controllers;

import com.patientui.beans.PatientBean;
import com.patientui.beans.PatientNotesBean;
import com.patientui.proxies.MicroserviceBackProxy;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final MicroserviceBackProxy backProxy;

    public PatientController(MicroserviceBackProxy backProxy) {
        this.backProxy = backProxy;
    }

    @GetMapping("/list")
    public String showPatientsList(Model model) {
        List<PatientBean> patients = backProxy.showAllPatients();
        model.addAttribute("patients", patients);
        return "patient/list";
    }

    @GetMapping("/information/{id}")
    public String showPatientInformation(@PathVariable("id") Integer id, Model model) {
        PatientBean patientInfo = backProxy.showOnePatientInformations(id);
        List<PatientNotesBean> patientNotes = backProxy.showPatientNotes(id);
        PatientNotesBean patientNote = new PatientNotesBean();
        patientNote.setPatId(patientInfo.getId());
        patientNote.setPatient(patientInfo.getFirstName());
        model.addAttribute("patientInfo", patientInfo);
        model.addAttribute("patientNotes", patientNotes);
        model.addAttribute("patientNote", patientNote);
        return "patient/information";
    }

    @GetMapping("/add")
    public String showAddPatientPage(PatientBean patient, Model model) {
        model.addAttribute("patient", patient);
        return "patient/add";
    }

    @PostMapping("/add/validate")
    public String addPatientValidation(@Valid @ModelAttribute("patient") PatientBean patient, BindingResult result) {
        if (result.hasErrors()) {
            return "patient/add";
        }
        backProxy.addPatientValidate(patient);
        return "redirect:/patient/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePatientPage(@PathVariable("id") Integer id, Model model) {
        PatientBean patient = backProxy.showOnePatientInformations(id);
        model.addAttribute("patient", patient);
        return "patient/update";
    }

    @PostMapping("/update/{id}")
    public String updatePatientValidation(@PathVariable Integer id, @Valid PatientBean patient, BindingResult result) {
        if (result.hasErrors()) {
            return "patient/update";
        }
        backProxy.updatePatientValidate(id, patient);
        return "redirect:/patient/list";
    }

    @PostMapping("/addNote/{patId}")
    public String addNoteForPatient(@PathVariable Integer patId, PatientNotesBean patientNote) {
        System.out.println(patientNote.getPatient() + " patient " + patientNote.getPatId() + " id " );
        backProxy.addNoteForPatient(patientNote);
        return "redirect:/patient/information/{patId}";
    }

}
