package com.demo.clients.service;

import java.time.LocalDate;

public interface ClientCalculation {

    LocalDate calculateProbableDeathDate(LocalDate localDate);

    void validateInputData(Integer inputAge, LocalDate birthDate);
}
