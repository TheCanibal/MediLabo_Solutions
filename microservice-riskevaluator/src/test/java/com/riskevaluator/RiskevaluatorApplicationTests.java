package com.riskevaluator;

import com.riskevaluator.util.RiskEvaluatorUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RiskevaluatorApplicationTests {

	@Autowired
	private RiskEvaluatorUtil riskEvaluatorUtil;
	@Test
	void contextLoads() {
	}

	@Test
	public void testRiskEvaluatorWhenRiskIsNone() {
		String riskToHaveDiabete = riskEvaluatorUtil.getRiskToHaveDiabete(1);
		assertEquals(riskToHaveDiabete, "No risks determinated");
	}

	@Test
	public void testRiskEvaluatorWhenRiskIsEarlyOnSet() {
		String riskToHaveDiabete = riskEvaluatorUtil.getRiskToHaveDiabete(4);
		assertEquals(riskToHaveDiabete, "Early onset");
	}
	@Test
	public void testRiskEvaluatorWhenRiskIsDanger() {
		String riskToHaveDiabete = riskEvaluatorUtil.getRiskToHaveDiabete(3);
		assertEquals(riskToHaveDiabete, "In Danger");
	}
	@Test
	public void testRiskEvaluatorWhenRiskIsBorderline() {
		String riskToHaveDiabete = riskEvaluatorUtil.getRiskToHaveDiabete(2);
		assertEquals(riskToHaveDiabete, "Borderline");
	}

}
