package com.demo.clients.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class KpiClientResponse {

    private Double mean;
    private Double standardDeviation;

}
