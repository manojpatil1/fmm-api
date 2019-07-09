package com.klm.fmm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.klm.fmm.model.Meal;

@RepositoryRestResource
public interface MealRepository extends CrudRepository<Meal, String> {
	
	@Query("SELECT t FROM Meal t where t.flightId = ?1")
    public Optional<List<Meal>> findByFlightId(String flightId);

}
