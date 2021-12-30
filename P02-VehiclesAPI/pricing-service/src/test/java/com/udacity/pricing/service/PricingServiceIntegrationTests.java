package com.udacity.pricing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.udacity.pricing.entity.Price;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PricingServiceIntegrationTests {
	
	private static final Integer TEST_DB_PRICE_COUNT = 5;
	private static final String TEST_DB_CURRENCY = "US Dollars";
	private static final BigDecimal TEST_DB_PRICE = new BigDecimal("9999.99");
	private static final Long TEST_DB_VEHICLE_ID = 21l;
	MockMvc mvc;
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void canGetAllPrices() throws URISyntaxException {
		ParameterizedTypeReference<PagedModel<Price>> responseType = new ParameterizedTypeReference<PagedModel<Price>>() {};
		RequestEntity<PagedModel<Price>> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI("http://localhost:" + port + "/prices"));
		ResponseEntity <PagedModel<Price>> response = restTemplate.exchange(requestEntity,responseType);
		assertNotNull(response);
	    PagedModel<Price> resources = response.getBody();
	    Collection<Price> prices = resources.getContent();
	    assertEquals(TEST_DB_PRICE_COUNT, prices.size());
	    Price price = prices.iterator().next();
	    assertEquals(TEST_DB_CURRENCY, price.getCurrency());
	    assertEquals(TEST_DB_PRICE, price.getPrice());
	    assertEquals(TEST_DB_VEHICLE_ID, price.getVehicleId());
	}
	
	@Test
	public void canGetPrice() {
			ResponseEntity<Price> response =
					this.restTemplate.getForEntity("http://localhost:" + port + "/prices/1", Price.class);
			assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
