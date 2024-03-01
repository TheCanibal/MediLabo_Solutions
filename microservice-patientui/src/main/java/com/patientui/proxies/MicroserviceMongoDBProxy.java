package com.patientui.proxies;

import com.patientui.beans.PatientBean;
import com.patientui.beans.PatientNotesBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "microservice-mongodb", url = "host.docker.internal:9004")
public interface MicroserviceMongoDBProxy {

    /**
     * Add a note for a patient thanks to his id
     * @param patientNote note to add
     */
    @PostMapping("/back/addNote")
    void addNoteForPatient(@RequestBody PatientNotesBean patientNote);

    /**
     * Get all patient notes with his id
     * @param patId patient's id
     * @return all notes of patient
     */
    @GetMapping("/back/{patId}/notes")
    List<PatientNotesBean> showPatientNotes(@PathVariable Integer patId);
}
