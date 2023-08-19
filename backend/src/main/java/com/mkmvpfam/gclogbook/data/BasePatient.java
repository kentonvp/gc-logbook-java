package com.mkmvpfam.gclogbook.data;

import com.mkmvpfam.gclogbook.data.enums.Gender;
import com.mkmvpfam.gclogbook.data.enums.Specialty;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class BasePatient {
	@Getter
	@Setter
	private Specialty specialty;

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

	BasePatient() {
	}

	public BasePatient(BasePatientBuilder builder) {
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

	// Builder pattern
	public static class BasePatientBuilder {
		private Date date;
		private Specialty specialty;
		private Gender gender;
		private int age;
		private String indication;
		private String summary;
		private boolean testsOrdered;
		private String testNames;
		private String testResults;

		public BasePatientBuilder() {
		}

		public BasePatientBuilder withDate(Date date) {
			this.date = date;
			return this;
		}

		public BasePatientBuilder withSpecialty(Specialty specialty) {
			this.specialty = specialty;
			return this;
		}

		public BasePatientBuilder withGender(Gender gender) {
			this.gender = gender;
			return this;
		}

		public BasePatientBuilder withAge(int age) {
			this.age = age;
			return this;
		}

		public BasePatientBuilder withIndication(String indication) {
			this.indication = indication;
			return this;
		}

		public BasePatientBuilder withSummary(String summary) {
			this.summary = summary;
			return this;
		}

		public BasePatientBuilder withTestsOrdered(boolean testsOrdered) {
			this.testsOrdered = testsOrdered;
			return this;
		}

		public BasePatientBuilder withTestNames(String testNames) {
			this.testNames = testNames;
			return this;
		}

		public BasePatientBuilder withTestResults(String testResults) {
			this.testResults = testResults;
			return this;
		}

		public BasePatient build() {
			return new BasePatient(this);
		}
	}
}
