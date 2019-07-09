package com.klm.fmm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Meal {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(columnDefinition = "CHAR(50)")
	private String id;

	private String mealClass;
	private int breakfast;
	private int lightSnack;
	private int lunch;
	private int dinner;
	private String flightId;
	
	
}
