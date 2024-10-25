package com.demo.clients.service;

import com.demo.clients.exception.InvalidDataException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class ClientDataCalculation implements ClientCalculation{

    private static final int AVERAGE_LIFE_EXPECTANCY = 80;

    @Override
    public LocalDate calculateProbableDeathDate(LocalDate birthDate) {
        return birthDate.plusYears(AVERAGE_LIFE_EXPECTANCY);
    }

    @Override
    public void validateInputData(Integer inputAge, LocalDate birthDate) {

        int calculatedAge = Period.between(birthDate, LocalDate.now()).getYears();

        if (calculatedAge != inputAge) {
            throw new InvalidDataException("Age and birthdate are not equivalent");
        }
    }
}
