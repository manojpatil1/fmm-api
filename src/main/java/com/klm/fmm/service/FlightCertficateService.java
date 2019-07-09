package com.klm.fmm.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klm.fmm.exception.BadRequestException;
import com.klm.fmm.exception.FlightExist;
import com.klm.fmm.exception.NotFoundException;
import com.klm.fmm.model.Flight;
import com.klm.fmm.model.Meal;
import com.klm.fmm.model.RequestObject;
import com.klm.fmm.repository.FlightRepository;
import com.klm.fmm.repository.MealRepository;

@Service
public class FlightCertficateService {

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	MealRepository mealRepository;

	public Flight createByFlightNumberAndFlightScheduleDate(String flightNumber, LocalDate flightScheduleDate) {
		Flight flight = new Flight();
		flight.setFlightNumber(flightNumber);
		flight.setFlightDepartureDate(flightScheduleDate);
		return flightRepository.save(flight);
	}

	public List<Flight> getFlight() {
		return (List<Flight>) flightRepository.findAll();
	}

	public Flight createByFlight(Flight flight) throws FlightExist {
		Optional<Flight> flExist = flightRepository.findByTitleAndDescription(flight.getFlightNumber(), flight.getFlightDepartureDate());
		if (!flExist.isPresent()) {
			return flightRepository.save(flight);
		} else {
			throw new FlightExist("Flight number "+flight.getFlightNumber()+" and departure date "+flight.getFlightDepartureDate()+" exist");
		}
	}

	public Iterable<Meal> createMealsForFlight(RequestObject reqObj, String flightNumber, LocalDate flightDepartureDate) {
		Optional<Flight> flight = flightRepository.findByTitleAndDescription(flightNumber, flightDepartureDate);
		if(flight.isPresent()) {
			Flight fl =  flight.get();
			if (fl != null) {
				reqObj.getMeals().stream().forEach(m -> m.setFlightId(fl.getId()));
				return mealRepository.saveAll(reqObj.getMeals());
			} else {
				throw new NotFoundException("Flight number "+flightNumber+" and departure date "+flightDepartureDate+" not found");
			}
		} else {
			throw new NotFoundException("Flight number "+flightNumber+" and departure date "+flightDepartureDate+" not found");
		}
	}

	public void deleteFlight(String flightNumber, LocalDate flightDepartureDate) {
		try {
			Optional<Flight> fl = flightRepository.findByTitleAndDescription(flightNumber, flightDepartureDate);
			if (fl.isPresent()) {
				Flight fl1 = fl.get();
				Optional<List<Meal>> m1 = mealRepository.findByFlightId(fl1.getId());
				if (m1.isPresent()) {
					List<Meal> mlst = m1.get();
					mealRepository.deleteAll(mlst);
					flightRepository.delete(fl1);
				}
			} else {
				throw new BadRequestException("Flight number "+flightNumber+" and departure date "+flightDepartureDate+" not found");
			}
		} catch(Exception e) {
			throw e;
		}
	}



}
