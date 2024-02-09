package com.patientui.proxies;

import com.patientui.beans.PatientBean;
import com.patientui.beans.PatientNotesBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-back", url = "localhost:9002")
public interface MicroserviceBackProxy {

    @GetMapping("/back/list")
    List<PatientBean> showAllPatients();

    @GetMapping("/back/{id}")
    PatientBean showOnePatientInformations(@PathVariable Integer id);

    @GetMapping("/back/{patId}/notes")
    List<PatientNotesBean> showPatientNotes(@PathVariable Integer patId);

    @PostMapping("/back/add/validate")
    PatientBean addPatientValidate(@RequestBody PatientBean patient);

    @PostMapping("/back/update/{id}")
    PatientBean updatePatientValidate(@PathVariable("id") Integer id, @RequestBody PatientBean patient);
}
