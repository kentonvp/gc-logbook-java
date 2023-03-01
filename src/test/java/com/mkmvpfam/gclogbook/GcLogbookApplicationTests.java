package com.mkmvpfam.gclogbook;

import com.mkmvpfam.gclogbook.data.PatientController;
import com.mkmvpfam.gclogbook.data.PatientRepository;
import com.mkmvpfam.gclogbook.data.enums.Gender;
import com.mkmvpfam.gclogbook.data.enums.Specialty;
import com.mkmvpfam.gclogbook.data.Patient;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class GcLogbookApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private PatientController patientController;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeEach
    void initTestDB() {
        Patient p = new Patient(
            new Date(),
            Specialty.CANCER,
            Gender.MALE,
            26,
            "indication example",
            "summary example",
            true,
            "test example",
            "results example"
        );
        patientRepo.save(p);
    }

    @Test
    void loadController() {
        Assertions.assertNotNull(patientController);
    }

    String createUrl(String path) {
        return "http://localhost:" + port + path;
    }


    @Test
    void getPatientById() {
        ResponseEntity<Patient> response = restTemplate.getForEntity(
            createUrl("/patients/1"),
            Patient.class
        );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Patient patient = response.getBody();
        Assertions.assertEquals(1, patient.getId());
        Assertions.assertEquals(Gender.MALE, patient.getGender());
        Assertions.assertEquals(Specialty.CANCER, patient.getSpecialty());
        Assertions.assertEquals(26, patient.getAge());
        Assertions.assertTrue(patient.getIndication().contains("indication"));
        Assertions.assertTrue(patient.getSummary().contains("summary"));
    }

    @Test
    void getPatientById_NotFound() {
        ResponseEntity<Patient> response = restTemplate.getForEntity(
            createUrl("/patients/2"),
            Patient.class
        );
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = { "gender=MALE", "specialty=CANCER", "indication=indication", "testName=panel" }) // three parameters
    void getPatientsWithSingleParam(String param) {
        ResponseEntity<Patient[]> response = restTemplate.getForEntity(
            createUrl("/patients?" + param),
            Patient[].class
        );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Patient patient = response.getBody()[0];
        Assertions.assertEquals(1, patient.getId());
        Assertions.assertEquals(Gender.MALE, patient.getGender());
        Assertions.assertEquals(Specialty.CANCER, patient.getSpecialty());
        Assertions.assertEquals(26, patient.getAge());
        Assertions.assertTrue(patient.getIndication().contains("indication"));
        Assertions.assertTrue(patient.getSummary().contains("summary"));
    }

    @Test
    void getPatientsMultiParam() {
        ResponseEntity<Patient[]> response = restTemplate.getForEntity(
            createUrl("/patients?gender=MALE&specialty=CANCER"),
            Patient[].class
        );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1, response.getBody().length);

        Patient patient = response.getBody()[0];
        Assertions.assertEquals(1, patient.getId());
        Assertions.assertEquals(Gender.MALE, patient.getGender());
        Assertions.assertEquals(Specialty.CANCER, patient.getSpecialty());
        Assertions.assertEquals(26, patient.getAge());
        Assertions.assertTrue(patient.getIndication().contains("indication"));
        Assertions.assertTrue(patient.getSummary().contains("summary"));
    }

    @Test
    void getPatientsMultiParam_NotFound() {
        ResponseEntity<Patient[]> response = restTemplate.getForEntity(
            createUrl("/patients?gender=UNKNOWN&specialty=CANCER"),
            Patient[].class
        );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(0, response.getBody().length);
    }
}
