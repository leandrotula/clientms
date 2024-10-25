package com.demo.clients.service;

import com.demo.clients.data.entity.ClientEntity;
import com.demo.clients.data.repository.AgeStatisticsDTO;
import com.demo.clients.data.repository.ClientRepository;
import com.demo.clients.exception.ClientAlreadyExistsException;
import com.demo.clients.web.model.ClientDetailedResponse;
import com.demo.clients.web.model.ClientRequestBody;
import com.demo.clients.web.model.ClientResponseBody;
import com.demo.clients.web.model.KpiClientResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ClientService {

    private static final int AVERAGE_LIFE_EXPECTANCY = 80;


    private final ClientRepository clientRepository;

    @Transactional
    public ClientResponseBody create(ClientRequestBody clientRequestBody) {
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

    public Page<ClientDetailedResponse> getAllClients(int page, int size) {
        Page<ClientEntity> clientEntityPage = clientRepository.findAll(PageRequest.of(page, size));
        return clientEntityPage.map(this::toClientWithProbableDeathDTO);
    }

    private ClientDetailedResponse toClientWithProbableDeathDTO(ClientEntity client) {
        LocalDate probableDeathDate = calculateProbableDeathDate(client.getBirthdate());
        return new ClientDetailedResponse(
                client.getId(),
                client.getName(),
                client.getAge(),
                client.getBirthdate(),
                probableDeathDate
        );
    }

    private LocalDate calculateProbableDeathDate(LocalDate birthDate) {
        return birthDate.plusYears(AVERAGE_LIFE_EXPECTANCY);
    }

}
