package com.mkmvpfam.gclogbook.data;

import com.mkmvpfam.gclogbook.data.enums.Gender;
import com.mkmvpfam.gclogbook.data.enums.Specialty;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("patients")
public class PatientController {
	private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

	@Autowired
	PatientRepository patientRepo;

	PatientController(PatientRepository patientRepo) {
		this.patientRepo = patientRepo;
	}

	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
		logger.debug("getPatientById: {}", id);
		Optional<Patient> op = patientRepo.findById(id);
		if (op.isEmpty())
			return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(op.get(), HttpStatus.OK);
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Patient>> getPatientByParams(@RequestParam Optional<Specialty> specialty,
			@RequestParam Optional<Gender> gender,
			@RequestParam(name = "indication") Optional<String> partialIndication,
			@RequestParam(name = "testName") Optional<String> partialTestName,
			@RequestParam(name = "summary") Optional<String> partialSummary) {
		List<Patient> patients = new ArrayList<>();
		if (specialty.isPresent()) {
			logger.debug("getPatientByParams: specialty: {}", specialty.get());
			patients.addAll(patientRepo.findBySpecialty(specialty.get()));
		} else if (gender.isPresent()) {
			logger.debug("getPatientByParams: gender: {}", gender.get());
			patients.addAll(patientRepo.findByGender(gender.get()));
		} else if (partialIndication.isPresent()) {
			logger.debug("getPatientByParams: indication: {}", partialIndication.get());
			patients.addAll(patientRepo.findByIndicationContaining(partialIndication.get()));
		} else if (partialTestName.isPresent()) {
			logger.debug("getPatientByParams: testName: {}", partialTestName.get());
			patients.addAll(patientRepo.findByTestNamesContaining(partialTestName.get()));
		} else if (partialSummary.isPresent()) {
			logger.debug("getPatientByParams: summary: {}", partialSummary.get());
			patients.addAll(patientRepo.findBySummaryContaining(partialSummary.get()));
		} else {
			logger.debug("getPatientByParams: ALL");
			return new ResponseEntity<>(patientRepo.findAll(), new HttpHeaders(), HttpStatus.OK);
		}

		patients = patients.stream()
				.filter(patient -> ((specialty.isEmpty() || specialty.get() == patient.getSpecialty())
						&& (gender.isEmpty() || gender.get() == patient.getGender())
						&& (partialIndication.isEmpty() || patient.getIndication().contains(partialIndication.get()))
						&& (partialTestName.isEmpty() || patient.getTestNames().contains(partialTestName.get()))
						&& (partialSummary.isEmpty() || patient.getSummary().contains(partialSummary.get()))))
				.toList();

		logger.debug("Found {} patients matching params", patients.size());
		return new ResponseEntity<>(patients, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping(produces = "application/json")
	public ResponseEntity<Long> createPatient(@RequestBody BasePatient basePatient) {
		logger.debug("createPatient: {}", basePatient);
		Patient patient = new Patient(basePatient.getDate(), basePatient.getSpecialty(), basePatient.getGender(),
				basePatient.getAge(), basePatient.getIndication(), basePatient.getSummary(),
				basePatient.isTestsOrdered(), basePatient.getTestNames(), basePatient.getTestResults());
		patientRepo.save(patient);
		return new ResponseEntity<>(patient.getId(), new HttpHeaders(), HttpStatus.CREATED);
	}

	@PutMapping(produces = "application/json")
	public ResponseEntity<Long> updatePatient(@RequestBody Patient patient) {
		logger.debug("updatePatient: {}", patient);
		Optional<Patient> dbPatient = patientRepo.findById(patient.getId());
		if (dbPatient.isEmpty())
			return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);

		Patient op = dbPatient.get();
		op.setDate(patient.getDate());
		op.setSpecialty(patient.getSpecialty());
		op.setGender(patient.getGender());
		op.setAge(patient.getAge());
		op.setIndication(patient.getIndication());
		op.setSummary(patient.getIndication());
		op.setSummary(patient.getSummary());
		op.setTestsOrdered(patient.isTestsOrdered());
		op.setTestNames(patient.getTestNames());
		op.setTestResults(patient.getTestResults());

		patientRepo.save(op);
		return new ResponseEntity<>(op.getId(), new HttpHeaders(), HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Long> deletePatient(@PathVariable Long id) {
		logger.debug("deletePatient: {}", id);
		patientRepo.deleteById(id);
		return new ResponseEntity<>(id, new HttpHeaders(), HttpStatus.OK);
	}
}
