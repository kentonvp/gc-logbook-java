package com.mkmvpfam.gclogbook.data;

import java.util.Date;

import com.mkmvpfam.gclogbook.data.enums.Gender;
import com.mkmvpfam.gclogbook.data.enums.Specialty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode @ToString
public class BasePatient {
    @Getter @Setter
    private Specialty specialty;

    @Getter @Setter
    private Gender gender;

    @Getter @Setter
    private Date date;

    @Getter @Setter
    private int age;

    @Getter @Setter
    private String indication;

    @Getter @Setter
    private String summary;

    @Getter @Setter
    private boolean testsOrdered;

    @Getter @Setter
    private String testNames;

    @Getter @Setter
    private String testResults;


    BasePatient() {}

    BasePatient(Date date, Specialty specialty, Gender gender, int age, String indication, String summary, boolean testsOrdered, String testNames, String testResults) {
        this.date = date;
        this.specialty = specialty;
        this.gender = gender;
        this.age = age;
        this.indication = indication;
        this.summary = summary;
        this.testsOrdered = testsOrdered;
        this.testNames = testNames;
        this.testResults = testResults;
    }
}
