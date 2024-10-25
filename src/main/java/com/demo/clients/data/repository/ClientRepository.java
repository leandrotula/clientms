package com.demo.clients.data.repository;

import com.demo.clients.data.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    Long countClientEntitiesByNameIgnoreCaseAndAgeAndLastNameIgnoreCaseAndBirthdate(String name,
                                                                                    Integer age,
                                                                                    String lastName,
                                                                                    LocalDate birthdate);

    @Query(value = "SELECT new com.demo.clients.data.repository.AgeStatisticsDTO(AVG(age), STDDEV(age)) FROM ClientEntity")
    AgeStatisticsDTO getAgeStatistics();

    Page<ClientEntity> findAll(Pageable pageable);

}