package com.demo.clients.web.model;

import com.demo.clients.validations.ValidAge;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class ClientRequestBody {

    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;
    @NotNull(message = "Age cannot be null")
    @Min(value = 1, message = "Age must be greater than 0")
    private Integer age;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "client's age valid is 18 years old or even older than that")
    @ValidAge
    private LocalDate birthdate;
}
