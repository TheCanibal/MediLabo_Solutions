package com.riskevaluator.controllers;

import com.riskevaluator.beans.PatientBean;
import com.riskevaluator.beans.PatientNotesBean;
import com.riskevaluator.proxies.MicroserviceBackProxy;
import com.riskevaluator.proxies.MicroserviceMongoDBProxy;
import com.riskevaluator.util.RiskEvaluatorUtil;
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
    private RiskEvaluatorUtil riskEvaluatorUtil;

    @GetMapping("/risk/{patId}")
    public String riskToHaveDiabete(@PathVariable Integer patId) {
        return riskEvaluatorUtil.getRiskToHaveDiabete(patId);
    }
}
