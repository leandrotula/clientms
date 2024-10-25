package com.demo.clients.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientDetailedResponse {
    private Long id;
    private String name;
    private Integer age;
    private LocalDate birthDate;
    private LocalDate probableDeathDate;
    private String lastName;
}
