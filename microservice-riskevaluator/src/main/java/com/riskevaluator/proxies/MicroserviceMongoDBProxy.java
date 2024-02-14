package com.riskevaluator.proxies;

import com.riskevaluator.beans.PatientBean;
import com.riskevaluator.beans.PatientNotesBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "microservice-mongodb", url = "localhost:9004")
public interface MicroserviceMongoDBProxy {

    @PostMapping("/back/addNote")
    PatientBean addNoteForPatient(@RequestBody PatientNotesBean patientNote);

    @GetMapping("/back/{patId}/notes")
    List<PatientNotesBean> showPatientNotes(@PathVariable Integer patId);
}
