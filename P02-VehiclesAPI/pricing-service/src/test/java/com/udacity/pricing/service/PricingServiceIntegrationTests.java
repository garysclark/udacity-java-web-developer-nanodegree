package com.udacity.pricing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.udacity.pricing.entity.Price;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class PricingServiceIntegrationTests {
	
	@LocalServerPort
	private int port = 8762;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void canGetAllPrices() {
		ResponseEntity <List> response = 
				restTemplate.getForEntity("http://localhost:" + port + "/prices", List.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void canGetPrice() {
			ResponseEntity<Price> response =
					this.restTemplate.getForEntity("http://localhost:" + port + "/prices/1", Price.class);
			assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
