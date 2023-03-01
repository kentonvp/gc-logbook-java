package com.mkmvpfam.gclogbook.data;

import com.mkmvpfam.gclogbook.data.enums.Specialty;
import com.mkmvpfam.gclogbook.data.enums.Gender;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findBySpecialty(Specialty specialty);
    List<Patient> findByGender(Gender gender);
    List<Patient> findByIndicationContaining(String infix);
    List<Patient> findByTestNamesContaining(String infix);
}
