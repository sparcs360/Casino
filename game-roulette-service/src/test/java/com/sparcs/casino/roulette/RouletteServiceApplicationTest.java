package com.sparcs.casino.roulette;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sparcs.casino.BaseTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RouletteServiceApplicationTest extends BaseTest {

	private static final Logger log = LoggerFactory.getLogger(RouletteServiceApplicationTest.class);
	
	@Test
	public void contextLoads() {
		log.debug("Success!");
	}
}
