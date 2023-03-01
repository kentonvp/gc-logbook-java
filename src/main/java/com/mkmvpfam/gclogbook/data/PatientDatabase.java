package com.mkmvpfam.gclogbook.data;

import com.mkmvpfam.gclogbook.data.enums.Gender;
import com.mkmvpfam.gclogbook.data.enums.Specialty;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PatientDatabase {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDatabase.class);

    // @Bean
    // CommandLineRunner initDatabase(PatientRepository repository) {
    //     return args -> {
    //         Patient patient = repository.save( new Patient(
    //             new Date(),
    //             Specialty.CANCER,
    //             Gender.MALE,
    //             26,
    //             "indication example",
    //             "summary example"
    //         ));
    //         LOGGER.debug("Preloading {}", patient);
    //     };
    // }
}
