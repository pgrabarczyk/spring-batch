package com.pgrabarczyk.spring.batch.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope("prototype")
@Getter
@Setter
public class Car {
	private Integer year;
	private String brand;
	private String model;
}
