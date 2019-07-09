package com.klm.fmm.model;

import java.io.Serializable;
import java.util.List;

import com.klm.fmm.model.Meal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestObject implements Serializable {
    public List<Meal> meals;
}