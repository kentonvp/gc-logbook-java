package com.mkmvpfam.gclogbook.data;

import com.mkmvpfam.gclogbook.data.enums.Gender;
import com.mkmvpfam.gclogbook.data.enums.Specialty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@EqualsAndHashCode
@ToString
@Table(name = "patients")
public class Patient {
	@Id
	@GeneratedValue
	@Getter
	private Long id;

	@Enumerated(EnumType.STRING)
	@Getter
	@Setter
	private Specialty specialty;

	@Enumerated(EnumType.STRING)
	@Getter
	@Setter
	private Gender gender;

	@Getter
	@Setter
	private Date date;

	@Getter
	@Setter
	private int age;

	@Getter
	@Setter
	private String indication;

	@Getter
	@Setter
	@Column(length = 5000)
	private String summary;

	@Getter
	@Setter
	private boolean testsOrdered;

	@Getter
	@Setter
	private String testNames;

	@Getter
	@Setter
	private String testResults;

	Patient() {
	}

	public Patient(PatientBuilder builder) {
		this.date = builder.date;
		this.specialty = builder.specialty;
		this.gender = builder.gender;
		this.age = builder.age;
		this.indication = builder.indication;
		this.summary = builder.summary;
		this.testsOrdered = builder.testsOrdered;
		this.testNames = builder.testNames;
		this.testResults = builder.testResults;
	}

	public static class PatientBuilder {
		private Date date;
		private Specialty specialty;
		private Gender gender;
		private int age;
		private String indication;
		private String summary;
		private boolean testsOrdered;
		private String testNames;
		private String testResults;

		public PatientBuilder() {
		}

		public PatientBuilder withDate(Date date) {
			this.date = date;
			return this;
		}

		public PatientBuilder withSpecialty(Specialty specialty) {
			this.specialty = specialty;
			return this;
		}

		public PatientBuilder withGender(Gender gender) {
			this.gender = gender;
			return this;
		}

		public PatientBuilder withAge(int age) {
			this.age = age;
			return this;
		}

		public PatientBuilder withIndication(String indication) {
			this.indication = indication;
			return this;
		}

		public PatientBuilder withSummary(String summary) {
			this.summary = summary;
			return this;
		}

		public PatientBuilder withTestsOrdered(boolean testsOrdered) {
			this.testsOrdered = testsOrdered;
			return this;
		}

		public PatientBuilder withTestNames(String testNames) {
			this.testNames = testNames;
			return this;
		}

		public PatientBuilder withTestResults(String testResults) {
			this.testResults = testResults;
			return this;
		}

		public Patient build() {
			return new Patient(this);
		}
	}
}
