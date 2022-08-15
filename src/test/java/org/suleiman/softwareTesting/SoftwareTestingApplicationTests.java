package org.suleiman.softwareTesting;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class SoftwareTestingApplicationTests {
	Logger logger = LoggerFactory.getLogger(SoftwareTestingApplicationTests.class.getName());
	Calculator additionTest = new Calculator();

	@Test
	void itShouldAddNumbers() {
		// given
		int firstNumber = 10;
		int secondNumber = 20;
		logger.info(Integer.toString(secondNumber));
		logger.info(Integer.toString(firstNumber));
		// when
		int result = additionTest.add(firstNumber,secondNumber);

		// expected results
		int expected = 30;

		// performing the assertions to confirm
		assertThat(result).isEqualTo(expected);
	}
	@Test
	void contextLoads() {

	}

	class Calculator {
		public int add(int a, int b) {
			return a+b;
		}
	}

}
