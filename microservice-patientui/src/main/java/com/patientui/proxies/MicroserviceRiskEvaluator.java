package com.patientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-riskevaluator", url = "localhost:9005")
public interface MicroserviceRiskEvaluator {

    @GetMapping("/risk/{patId}")
    public String riskToHaveDiabete(@PathVariable Integer patId);
}
