package com.mkmvpfam.gclogbook;

import com.mkmvpfam.gclogbook.data.Patient;
import com.mkmvpfam.gclogbook.data.PatientController;
import com.mkmvpfam.gclogbook.data.PatientRepository;
import com.mkmvpfam.gclogbook.data.enums.Gender;
import com.mkmvpfam.gclogbook.data.enums.Specialty;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// @TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:"})
class GcLogbookApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private PatientController patientController;

	@Autowired
	private PatientRepository patientRepo;

	private AtomicLong testId = new AtomicLong();

	@Autowired
	TestRestTemplate restTemplate;

	@BeforeEach
	void initDB() {
		patientRepo.deleteAll();
		Patient p = new Patient.PatientBuilder().withDate(new Date()).withSpecialty(Specialty.CANCER)
				.withGender(Gender.MALE).withAge(26).withIndication("indication example").withSummary("summary example")
				.withTestsOrdered(true).withTestNames("test example").withTestResults("results example").build();
		patientRepo.save(p);
		testId.set(p.getId());
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
		ResponseEntity<Patient> response = restTemplate.getForEntity(createUrl("/patients/" + testId.get()),
				Patient.class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		Patient patient = response.getBody();
		Assertions.assertEquals(testId.get(), patient.getId());
		Assertions.assertEquals(Gender.MALE, patient.getGender());
		Assertions.assertEquals(Specialty.CANCER, patient.getSpecialty());
		Assertions.assertEquals(26, patient.getAge());
		Assertions.assertTrue(patient.getIndication().contains("indication"));
		Assertions.assertTrue(patient.getSummary().contains("summary"));
	}

	@Test
	void getPatientById_NotFound() {
		ResponseEntity<Patient> response = restTemplate.getForEntity(createUrl("/patients/" + (testId.get() + 1)),
				Patient.class);
		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void getPatientsWithSingleParam() {
		String[] params = {"gender=MALE", "specialty=CANCER", "indication=indication", "testName=test",
				"summary=summary"};
		for (String param : params) {
			ResponseEntity<Patient[]> response = restTemplate.getForEntity(createUrl("/patients?" + param),
					Patient[].class);
			Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
			Assertions.assertEquals(1, response.getBody().length, "Failling param:" + param);

			Patient patient = response.getBody()[0];
			Assertions.assertEquals(1, patient.getId());
			Assertions.assertEquals(Gender.MALE, patient.getGender());
			Assertions.assertEquals(Specialty.CANCER, patient.getSpecialty());
			Assertions.assertEquals(26, patient.getAge());
			Assertions.assertTrue(patient.getIndication().contains("indication"));
			Assertions.assertTrue(patient.getSummary().contains("summary"));
		}
	}

	@Test
	void getPatientsMultiParam() {
		ResponseEntity<Patient[]> response = restTemplate
				.getForEntity(createUrl("/patients?gender=MALE&specialty=CANCER&summary=summary"), Patient[].class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertNotEquals(0, response.getBody().length);

		Patient patient = response.getBody()[0];
		Assertions.assertEquals(testId.get(), patient.getId());
		Assertions.assertEquals(Gender.MALE, patient.getGender());
		Assertions.assertEquals(Specialty.CANCER, patient.getSpecialty());
		Assertions.assertEquals(26, patient.getAge());
		Assertions.assertTrue(patient.getIndication().contains("indication"));
		Assertions.assertTrue(patient.getSummary().contains("summary"));
	}

	@Test
	void getPatientsMultiParam_NotFound() {
		ResponseEntity<Patient[]> response = restTemplate
				.getForEntity(createUrl("/patients?gender=UNKNOWN&specialty=CANCER"), Patient[].class);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		Assertions.assertEquals(0, response.getBody().length);
	}
}
