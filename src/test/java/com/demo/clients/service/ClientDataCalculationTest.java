package com.demo.clients.service;

import com.demo.clients.exception.InvalidDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class ClientDataCalculationTest {

    @InjectMocks
    private ClientDataCalculation clientDataCalculation;

    @Test
    void calculateProbableDeathDate() {
        LocalDate birthday = LocalDate.of(1990, 1, 1);
        LocalDate expected = LocalDate.of(2070, 1, 1);
        LocalDate result = clientDataCalculation.calculateProbableDeathDate(birthday);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void calculateDeathDate() {
        LocalDate birthday = LocalDate.of(1990, 1, 1);
        Assertions.assertThrows(InvalidDataException.class, () -> clientDataCalculation
                .validateInputData(10, birthday));
    }
}
