package com.riskevaluator.proxies;

import com.riskevaluator.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "microservice-back", url = "localhost:9002")
public interface MicroserviceBackProxy {

    /**
     * Recover patient with his id
     * @param id patient's id
     * @return patient if exists else throw NotFoundException
     */
    @GetMapping("/back/{id}")
    PatientBean showOnePatientInformations(@PathVariable Integer id);
}
