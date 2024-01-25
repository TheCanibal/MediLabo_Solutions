package com.back.controllers;

import com.back.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/list")
    public String showAllPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patient/list";
    }
}
