package com.klm.fmm.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.klm.fmm.model.Flight;

@RepositoryRestResource
public interface FlightRepository extends CrudRepository<Flight, String> {
	
	@Query("SELECT t FROM Flight t where t.flightNumber = ?1 AND t.flightDepartureDate = ?2")
    public Optional<Flight> findByTitleAndDescription(String flightNumber, LocalDate flightDepartureDate);

	@Query("Delete FROM Flight t where t.flightNumber = ?1 AND t.flightDepartureDate = ?2")
    public void deleteByFlightNumberAndDepartureDate(String flightNumber, LocalDate flightDepartureDate);
}
