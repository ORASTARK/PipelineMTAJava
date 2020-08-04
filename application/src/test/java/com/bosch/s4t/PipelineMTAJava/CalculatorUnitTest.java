package com.bosch.s4t.PipelineMTAJava;

import org.junit.Test;
import com.bosch.s4t.PipelineMTAJava.services.CalculatorService;
import org.junit.Assert;

public class CalculatorUnitTest {
	
	@Test
	public void addTest() {
		CalculatorService calculatorSrvice = new CalculatorService();
		double result = calculatorSrvice.add(1, 2);
		Assert.assertEquals(3.0, result, 0);
	}

}
