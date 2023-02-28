package com.mkmvpfam.gclogbook.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkmvpfam.gclogbook.data.enums.Specialty;
import com.mkmvpfam.gclogbook.data.enums.Gender;

@RestController
@RequestMapping("patients")
public class PatientController {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientRepository patientRepo;

    PatientController(PatientRepository patientRepo) {
        this.patientRepo = patientRepo;
    }

    @GetMapping(path="/{id}", produces="application/json")
    public Patient getPatientById(@PathVariable Long id) {
        logger.debug("Trying to get patient {}", id);
        return patientRepo.findById(id).get();
    }

    @GetMapping(produces="application/json")
    public List<Patient> getPatientByParams(
        @RequestParam Optional<Specialty> specialty,
        @RequestParam Optional<Gender> gender,
        @RequestParam(name="indication") Optional<String> partialIndication
    ) {
        List<Patient> patients = new ArrayList<>();
        if (specialty.isPresent()) {
            logger.debug("Trying to get patient by Specialty::{}", specialty);
            patients.addAll(patientRepo.findBySpecialty(specialty.get()));
        } else if (gender.isPresent()) {
            logger.debug("Trying to get patient by Gender::{}", gender);
            patients.addAll(patientRepo.findByGender(gender.get()));
        } else if (partialIndication.isPresent()) {
            logger.debug("trying to get patient by indication::{}", partialIndication);
            patients.addAll(patientRepo.findByIndicationContaining(partialIndication.get()));
        } else {
            return patientRepo.findAll();
        }

        return patients.stream()
            .filter(patient -> (
                (specialty.isEmpty() || specialty.get() == patient.getSpecialty()) &&
                (gender.isEmpty() || gender.get() == patient.getGender()) &&
                (partialIndication.isEmpty() || patient.getIndication().contains(partialIndication.get()))
            )).toList();
    }

    @PostMapping(path="/",produces="application/json")
    public long createPatient(@RequestBody BasePatient basePatient) {
        logger.debug("Creating patient from {}", basePatient);
        Patient patient = new Patient(
            basePatient.getDate(),
            basePatient.getSpecialty(),
            basePatient.getGender(),
            basePatient.getAge(),
            basePatient.getIndication(),
            basePatient.getSummary()
        );
        patientRepo.save(patient);
        return patient.getId();
    }
}
