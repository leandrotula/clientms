package com.demo.clients.service;

import com.demo.clients.data.entity.ClientEntity;
import com.demo.clients.data.repository.AgeStatisticsDTO;
import com.demo.clients.data.repository.ClientRepository;
import com.demo.clients.exception.ClientAlreadyExistsException;
import com.demo.clients.exception.InvalidDataException;
import com.demo.clients.web.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientCalculation clientCalculation;

    @Test
    void create_error_due_invalid_age() {
        //given
        String name = "test";
        String lastName = "test";
        Integer age = 21;
        LocalDate birthday = LocalDate.of(1990, 1, 1);

        doThrow(new InvalidDataException("Invalid input data"))
                .when(clientCalculation)
                .validateInputData(age, birthday);

        ClientRequestBody requestBody  = new ClientRequestBody(name, lastName, age, birthday);
        Exception ex = Assertions.assertThrows(InvalidDataException.class, () -> clientService.create(requestBody));
        Assertions.assertEquals(InvalidDataException.class, ex.getClass());
        Mockito.verify(clientRepository, never()).save(any(ClientEntity.class));
    }

    @Test
    void create_error_due_existing_record() {
        //given
        String name = "test";
        String lastName = "test";
        Integer age = 21;
        LocalDate birthday = LocalDate.of(1990, 1, 1);

        ClientRequestBody requestBody  = new ClientRequestBody(name, lastName, age, birthday);
        when(clientRepository
                .countClientEntitiesByNameIgnoreCaseAndAgeAndLastNameIgnoreCaseAndBirthdate(name, age, lastName, birthday))
                .thenReturn(1L);
        Exception ex = Assertions.assertThrows(ClientAlreadyExistsException.class, () -> clientService.create(requestBody));
        Assertions.assertEquals(ClientAlreadyExistsException.class, ex.getClass());
        Mockito.verify(clientRepository, never()).save(any(ClientEntity.class));
    }

    @Test
    void create_ok() {
        //given
        String name = "test";
        String lastName = "test";
        Integer age = 21;
        LocalDate birthday = LocalDate.of(1990, 1, 1);

        ClientRequestBody requestBody  = new ClientRequestBody(name, lastName, age, birthday);
        when(clientRepository
                .countClientEntitiesByNameIgnoreCaseAndAgeAndLastNameIgnoreCaseAndBirthdate(name, age, lastName, birthday))
                .thenReturn(0L);
        ClientEntity clientEntity = ClientEntity
                .builder().id(1L).name(name).lastName(lastName).age(age).birthdate(birthday)
                .build();
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        ClientResponseBody responseBody = clientService.create(requestBody);
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(name, responseBody.getName());
        Assertions.assertEquals(lastName, responseBody.getLastName());
        Assertions.assertEquals(birthday, responseBody.getBirthdate());
        Assertions.assertEquals(age, responseBody.getAge());

        Mockito.verify(clientRepository, times(1)).save(any(ClientEntity.class));
    }

    @Test
    void retrieveKpi_ok() {
        when(clientRepository.getAgeStatistics()).thenReturn(new AgeStatisticsDTO(2.0, 0.0));
        KpiClientResponse kpiClientResponse = clientService.retrieveKpi();
        Assertions.assertNotNull(kpiClientResponse);
        Assertions.assertEquals(2.0, kpiClientResponse.getMean());
        Assertions.assertEquals(0.0, kpiClientResponse.getStandardDeviation());
    }

    @Test
    void getAllClients_ok() {

        String name = "test";
        String lastName = "test";
        Integer age = 21;
        var deathDate = LocalDate.of(2070, 1, 1);
        LocalDate birthday = LocalDate.of(1990, 1, 1);
        ClientEntity clientEntity = ClientEntity
                .builder().id(1L).name(name).lastName(lastName).age(age).birthdate(birthday)
                .build();
        Page<ClientEntity> clientPage = new PageImpl<>(List.of(clientEntity));

        when(clientRepository.findAll(PageRequest.of(1, 10))).thenReturn(clientPage);
        when(clientCalculation.calculateProbableDeathDate(birthday)).thenReturn(deathDate);

        PageDtoResponse<ClientDetailedResponse> clientDetailedResponses = clientService.getAllClients(1, 10);
        Assertions.assertNotNull(clientDetailedResponses);
        List<ClientDetailedResponse> detailedResponses = clientDetailedResponses.getContent();
        Assertions.assertNotNull(detailedResponses);
        Assertions.assertEquals(1, detailedResponses.size());
        ClientDetailedResponse clientDetailedResponse = detailedResponses.get(0);
        Assertions.assertEquals(name, clientDetailedResponse.getName());
        Assertions.assertEquals(lastName, clientDetailedResponse.getLastName());
        Assertions.assertEquals(age, clientDetailedResponse.getAge());
        Assertions.assertEquals(birthday, clientDetailedResponse.getBirthDate());
        Assertions.assertEquals(deathDate, clientDetailedResponse.getProbableDeathDate());
    }
}
