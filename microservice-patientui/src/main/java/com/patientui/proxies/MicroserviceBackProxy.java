package com.patientui.proxies;

import com.patientui.beans.PatientBean;
import com.patientui.beans.PatientNotesBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "microservice-back", url = "localhost:9002")
public interface MicroserviceBackProxy {

    /**
     * Recover all patients from database
     * @return all patients from database
     */
    @GetMapping("/back/list")
    List<PatientBean> showAllPatients();

    /**
     * Recover patient with his id
     * @param id patient's id
     * @return patient if exists else throw NotFoundException
     */
    @GetMapping("/back/{id}")
    Optional<PatientBean> showOnePatientInformations(@PathVariable Integer id);

    /**
     * Add patient in database
     * @param patient patient to add
     */
    @PostMapping("/back/add/validate")
    void addPatientValidate(@RequestBody PatientBean patient);

    /**
     * Update patient in database
     * @param id patient's id
     * @param patient patient to update
     */
    @PostMapping("/back/update/{id}")
    void updatePatientValidate(@PathVariable Integer id, @RequestBody PatientBean patient);
}
