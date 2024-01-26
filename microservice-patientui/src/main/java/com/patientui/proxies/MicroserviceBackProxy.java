package com.patientui.proxies;

import com.patientui.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name ="microservice-back", url = "localhost:9002")
public interface MicroserviceBackProxy {

    @GetMapping("/patients")
    List<PatientBean> showAllPatients();

    @GetMapping("/patients/{id}")
    PatientBean showOnePatientInformations(@PathVariable Integer id);
}
