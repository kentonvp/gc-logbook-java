package com.mkmvpfam.gclogbook;

import com.mkmvpfam.gclogbook.data.PatientController;
import com.mkmvpfam.gclogbook.data.enums.Gender;
import com.mkmvpfam.gclogbook.data.enums.Specialty;
import com.mkmvpfam.gclogbook.data.Patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class GcLogbookApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private PatientController patientController;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void loadController() {
        Assertions.assertNotNull(patientController);
    }

    String createUrl(String path) {
        return "http://localhost:" + port + path;
    }


    @Test
    void getPatientById() {
        var patient = restTemplate.getForObject(
            createUrl("/patients/1"),
            Patient.class
        );
        Assertions.assertEquals(1, patient.getId());
        Assertions.assertEquals(Gender.MALE, patient.getGender());
        Assertions.assertEquals(Specialty.CANCER, patient.getSpecialty());
        Assertions.assertEquals(26, patient.getAge());
        Assertions.assertTrue(patient.getIndication().contains("indication"));
        Assertions.assertTrue(patient.getSummary().contains("summary"));
    }

    @Test
    void getPatientsWithGenderParam() {
    }

    @Test
    void getPatientsWithIndicationParam() {
    }

    @Test
    void getPatientsWithSpecialtyParam() {
    }

    @Test
    void getPatientsMultiParam() {
    }
}
