package com.byrneclark.garydata.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.byrneclark.garydata.entity.Delivery;
import com.byrneclark.garydata.entity.DeliveryTests;
import com.byrneclark.garydata.service.DeliveryService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class DeliveryControllerTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<Delivery> json;

	@MockBean
	private DeliveryService deliveryService;

	@Test
	public void canScheduleDelivery() throws URISyntaxException, IOException, Exception {
		Delivery delivery = DeliveryTests.getTestDelivery();
		given(deliveryService.save(delivery)).willReturn(delivery.getId());

		ResultActions resultActions = mvc.perform(
				post(new URI("/delivery"))
				.content(json.write(delivery).getJson())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		
		String result = resultActions.andReturn().getResponse().getContentAsString();
		assertNotNull(result);
	}
}
