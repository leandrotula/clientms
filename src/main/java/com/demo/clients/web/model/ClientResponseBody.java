package com.demo.clients.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class ClientResponseBody {
    private String name;
    private String lastName;
    private Integer age;
    private LocalDate birthdate;
}
