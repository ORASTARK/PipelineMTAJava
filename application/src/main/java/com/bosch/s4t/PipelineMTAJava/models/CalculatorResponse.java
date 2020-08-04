package com.bosch.s4t.PipelineMTAJava.models;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("PMD.UnusedPrivateField")
public class CalculatorResponse {
	@JsonProperty("value")
    private double result;

    public CalculatorResponse(double result) {
        this.result = result;
    }
    
    public double getResult() {
    	return this.result;
    }
    
    public void setResult(double result) {
    	this.result = result;
    }
    
}