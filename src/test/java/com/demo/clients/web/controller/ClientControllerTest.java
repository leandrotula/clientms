package com.demo.clients.web.controller;

import com.demo.clients.service.ClientService;
import com.demo.clients.web.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {


    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        JavaTimeModule module = new JavaTimeModule();
        objectMapper = new ObjectMapper()
                .registerModule(module)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @ParameterizedTest
    @MethodSource("dataprovider")
    public void testCreateClient_invalid_payloads(ClientRequestBody clientRequestBody) throws Exception {

        String content = objectMapper.writeValueAsString(clientRequestBody);
        mockMvc.perform(post("/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateClient() throws Exception {
        LocalDate birthday = LocalDate.of(1990, 1, 1);

        ClientRequestBody requestBody = new ClientRequestBody("test", "test", 21, birthday);
        ClientResponseBody responseBody = new ClientResponseBody(
                "test", "test", 21, birthday
        );

        when(clientService.create(any(ClientRequestBody.class))).thenReturn(responseBody);

        String content = objectMapper.writeValueAsString(requestBody);
        mockMvc.perform(post("/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(responseBody.getName()))
                .andExpect(jsonPath("$.lastName").value(responseBody.getLastName()))
                .andExpect(jsonPath("$.age").value(responseBody.getAge()))
                .andExpect(jsonPath("$.birthdate").exists());
    }

    @Test
    public void testKpi() throws Exception {
        KpiClientResponse kpiResponse = new KpiClientResponse(2.0, 0.0);

        when(clientService.retrieveKpi()).thenReturn(kpiResponse);

        mockMvc.perform(get("/v1/clients/kpi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mean").value(kpiResponse.getMean()))
                .andExpect(jsonPath("$.standardDeviation").value(kpiResponse.getStandardDeviation()));
    }

    @Test
    public void testGetAllClients() throws Exception {
        ClientDetailedResponse response = new ClientDetailedResponse(1L, "test",
                21,
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2070, 1, 1),
                "test");
        Page<ClientDetailedResponse> clientPage = new PageImpl<>(List.of(response));
        PageDtoResponse<ClientDetailedResponse> mockedResponse = new PageDtoResponse<>(clientPage);

        when(clientService.getAllClients(0, 10)).thenReturn(mockedResponse);

        mockMvc.perform(get("/v1/clients?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").exists())
                .andExpect(jsonPath("$.content[0].name").exists())
                .andExpect(jsonPath("$.content[0].lastName").exists())
                .andExpect(jsonPath("$.content[0].age").exists())
                .andExpect(jsonPath("$.content[0].probableDeathDate").exists());
    }

    public static Stream<Arguments> dataprovider() {
        return Stream.of(Arguments
                .of(new ClientRequestBody("", "test", 10, LocalDate.of(1990, 1, 1)),
                        new ClientRequestBody("test", "", 10, LocalDate.of(1990, 1, 1)),
                        new ClientRequestBody("test", "test", 0, LocalDate.of(1990, 1, 1))));
    }
}
