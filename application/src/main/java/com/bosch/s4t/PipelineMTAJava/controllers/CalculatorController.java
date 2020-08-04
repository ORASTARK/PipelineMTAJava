package com.bosch.s4t.PipelineMTAJava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bosch.s4t.PipelineMTAJava.models.CalculatorResponse;
import com.bosch.s4t.PipelineMTAJava.services.CalculatorService;

@RestController
@RequestMapping
public class CalculatorController
{
	@Autowired
	private CalculatorService calculatorService;
	
    @GetMapping("/add")
    public ResponseEntity<CalculatorResponse> add(@RequestParam("p1") double a, @RequestParam("p2") double b)
    {
    	double result = calculatorService.add(a, b);
    	return ResponseEntity.ok(new CalculatorResponse(result));
    }    
}