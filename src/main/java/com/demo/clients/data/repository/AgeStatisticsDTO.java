package com.demo.clients.data.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgeStatisticsDTO {
    private Double avgAge;
    private Double stddevAge;
}
