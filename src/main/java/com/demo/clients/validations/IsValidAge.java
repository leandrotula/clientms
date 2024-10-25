package com.demo.clients.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;

public class IsValidAge implements ConstraintValidator<ValidAge, LocalDate> {

    @Override
    public void initialize(ValidAge constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (ObjectUtils.isEmpty(date)) {
            return true;
        }
        LocalDate now = LocalDate.now();
        return !date.isAfter(now);

    }
}
