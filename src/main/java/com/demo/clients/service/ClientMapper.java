package com.demo.clients.service;

import com.demo.clients.data.entity.ClientEntity;
import com.demo.clients.web.model.ClientRequestBody;
import com.demo.clients.web.model.ClientResponseBody;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClientMapper {

    public static ClientEntity map(ClientRequestBody clientRequestBody) {
        return ClientEntity.builder()
                .age(clientRequestBody.getAge())
                .birthdate(clientRequestBody.getBirthdate())
                .name(clientRequestBody.getName())
                .lastName(clientRequestBody.getLastName())
                .build();
    }

    public static ClientResponseBody respond(ClientEntity clientEntity) {
        return ClientResponseBody.builder()
                .age(clientEntity.getAge())
                .name(clientEntity.getName())
                .lastName(clientEntity.getLastName())
                .birthdate(clientEntity.getBirthdate())
                .build();
    }
}
