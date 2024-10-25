package com.demo.clients.web.controller;

import com.demo.clients.service.ClientService;
import com.demo.clients.web.model.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/clients")
@AllArgsConstructor
public class ClientController {

    private ClientService clientService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create a new client", description = "This API creates a new client.")
    public ResponseEntity<ClientResponseBody> create(@RequestBody @Valid ClientRequestBody clientRequestBody) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(clientRequestBody));
    }

    @GetMapping("/kpi")
    @Operation(summary = "Retrieve KPI information about the client", description = "Client's KPI.")
    public ResponseEntity<KpiClientResponse> kpi() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.retrieveKpi());
    }

    @GetMapping
    @Operation(summary = "Retrieve all clients plus probable death date", description = "All client info plus possibly death date")
    public PageDtoResponse<ClientDetailedResponse> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return clientService.getAllClients(page, size);
    }
}
