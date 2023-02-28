package com.mkmvpfam.gclogbook.data;

import java.util.Date;

import com.mkmvpfam.gclogbook.data.enums.Gender;
import com.mkmvpfam.gclogbook.data.enums.Specialty;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity @EqualsAndHashCode @ToString @Table(name="patients")
public class Patient {
    @Id @GeneratedValue @Getter
    private Long id;

    @Enumerated(EnumType.STRING) @Getter @Setter
    private Specialty specialty;
    @Enumerated(EnumType.STRING) @Getter @Setter
    private Gender gender;

    @Getter @Setter
    private Date date;
    @Getter @Setter
    private int age;
    @Getter @Setter
    private String indication;
    @Getter @Setter
    private String summary;

    Patient() {}

    Patient(Date date, Specialty specialty, Gender gender, int age, String indication, String summary) {
        this.date = date;
        this.specialty = specialty;
        this.gender = gender;
        this.age = age;
        this.indication = indication;
        this.summary = summary;
    }
}