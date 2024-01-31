package com.patientui.controllers;

import com.patientui.beans.PatientBean;
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

    public PatientController(MicroserviceBackProxy backProxy) {this.backProxy = backProxy;}

    @GetMapping("/list")
    public String patientsList(Model model) {
        List<PatientBean> patients = backProxy.showAllPatients();
        model.addAttribute("patients", patients);
        return "patient/list";
    }

    @GetMapping("/add")
    public String addPatientPage(Model model) {
        model.addAttribute("patient", new PatientBean());
        return "patient/add";
    }

    @PostMapping("/add/validate")
    public String addPatientValidation(@Valid @ModelAttribute("patient") PatientBean patient, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "patient/add";
        }
        backProxy.addPatientValidate(patient);
        return "redirect:/patient/list";
    }

}
