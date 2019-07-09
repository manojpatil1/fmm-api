package com.klm.fmm.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.klm.fmm.exception.FlightExist;
import com.klm.fmm.model.Flight;
import com.klm.fmm.model.Meal;
import com.klm.fmm.model.RequestObject;
import com.klm.fmm.service.FlightCertficateService;

import io.swagger.annotations.ApiOperation;

@RestController
@Validated
@RequestMapping("/api/flight")
public class FlightController {

	private FlightCertficateService flightCertificateService;

	public FlightController(FlightCertficateService flightCertificateService) {
		this.flightCertificateService = flightCertificateService;
	}

	@RequestMapping(method = RequestMethod.POST, produces = { "application/hal+json" })
	@ApiOperation(nickname = "createByFlight", value = "create flight", tags = "flight")
	public ResponseEntity<?>  createByFlight(@RequestBody Flight flight) throws FlightExist {
		Flight fl = flightCertificateService.createByFlight(flight);
		return new ResponseEntity<>(fl, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/hal+json" })
	@ApiOperation(nickname = "getFlights", value = "get flights", tags = "flight")
	public ResponseEntity<?>  getFlight() {
		List<Flight> flightList = flightCertificateService.getFlight();
		return new ResponseEntity<>(flightList, HttpStatus.OK);
	}


	@RequestMapping(value = "/{flightNumber}/{flightDepartureDate}/meals", method = RequestMethod.POST, produces = { "application/hal+json" })
	@ApiOperation(nickname = "createByFlight", value = "create flight", tags = "flight")
	public ResponseEntity<?>  createMealsForFlight(@RequestBody RequestObject requestObject, @PathVariable String flightNumber, @PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate flightDepartureDate) {
		Iterable<Meal> mealList = flightCertificateService.createMealsForFlight(requestObject, flightNumber, flightDepartureDate);
		if (mealList != null) {
			return new ResponseEntity<>(mealList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(mealList, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{flightNumber}/{flightDepartureDate}", method = RequestMethod.DELETE, produces = { "application/hal+json" })
	@ApiOperation(nickname = "createByFlight", value = "create flight", tags = "flight")
	public ResponseEntity<?>  deleteFlight(@PathVariable String flightNumber, @PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate flightDepartureDate) {
		flightCertificateService.deleteFlight(flightNumber, flightDepartureDate);
		return new ResponseEntity<>(null, HttpStatus.OK);

	}



}
