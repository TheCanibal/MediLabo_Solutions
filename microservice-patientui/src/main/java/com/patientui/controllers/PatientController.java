package com.patientui.controllers;

import com.patientui.beans.PatientBean;
import com.patientui.proxies.MicroserviceBackProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/{id}")
    public String patientInformation(@PathVariable Integer id, Model model) {
        PatientBean patient = backProxy.showOnePatientInformations(id);
        model.addAttribute("patient", patient);
        return "patient/list";
    }

}
