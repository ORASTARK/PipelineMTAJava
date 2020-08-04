package com.bosch.s4t.PipelineMTAJava.services;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
	
	public double add(double a, double b) {
		return a + b;
	}	
}
