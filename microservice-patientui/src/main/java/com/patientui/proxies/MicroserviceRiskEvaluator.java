package com.patientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-riskevaluator", url = "host.docker.internal:9005")
public interface MicroserviceRiskEvaluator {

    /**
     * Get the risk to have diabete
     * @param patId patient"s id
     * @return risk to have diabete
     */
    @GetMapping("/risk/{patId}")
    public String riskToHaveDiabete(@PathVariable Integer patId);
}
