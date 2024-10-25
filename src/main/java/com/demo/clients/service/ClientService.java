package com.demo.clients.service;

import com.demo.clients.data.entity.ClientEntity;
import com.demo.clients.data.repository.AgeStatisticsDTO;
import com.demo.clients.data.repository.ClientRepository;
import com.demo.clients.exception.ClientAlreadyExistsException;
import com.demo.clients.web.model.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientCalculation clientCalculation;

    @Transactional
    public ClientResponseBody create(ClientRequestBody clientRequestBody) {
        clientCalculation.validateInputData(clientRequestBody.getAge(), clientRequestBody.getBirthdate());
        long quantity = clientRepository
                .countClientEntitiesByNameIgnoreCaseAndAgeAndLastNameIgnoreCaseAndBirthdate(
                clientRequestBody.getName(),
                clientRequestBody.getAge(),
                clientRequestBody.getLastName(),
                clientRequestBody.getBirthdate());

        if (quantity > 0) {
            throw new ClientAlreadyExistsException("Client already exists");
        }

        ClientEntity savedClient = clientRepository.save(ClientMapper.map(clientRequestBody));
        return ClientMapper.respond(savedClient);
    }

    public KpiClientResponse retrieveKpi() {
        AgeStatisticsDTO result = clientRepository.getAgeStatistics();
        return new KpiClientResponse(result.getAvgAge(), result.getStddevAge());
    }

    public PageDtoResponse<ClientDetailedResponse> getAllClients(int page, int size) {
        Page<ClientEntity> clientEntityPage = clientRepository.findAll(PageRequest.of(page, size));
        Page<ClientDetailedResponse> map = clientEntityPage.map(this::toClientWithProbableDeathDTO);
        return new PageDtoResponse<>(map);
    }

    private ClientDetailedResponse toClientWithProbableDeathDTO(ClientEntity client) {
        LocalDate probableDeathDate = clientCalculation.calculateProbableDeathDate(client.getBirthdate());
        return new ClientDetailedResponse(
                client.getId(),
                client.getName(),
                client.getAge(),
                client.getBirthdate(),
                probableDeathDate,
                client.getLastName()
        );
    }
}
