package com.byrneclark.garydata.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.byrneclark.garydata.entity.Flower;
import com.byrneclark.garydata.entity.FlowerTests;
import com.byrneclark.garydata.service.PlantService;

@SpringBootTest
@AutoConfigureMockMvc
public class PlantControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PlantService plantService;
	
	@Autowired
	private PlantController plantController;
	
	@Test
	public void canAccessController() {
		assertNotNull(plantController);
	}
	
	@Test
	public void canGetPlantByNameJsonView() throws URISyntaxException, Exception {
		Flower flower = FlowerTests.getTestFlower();
		BDDMockito.given(plantService.getPlantByName(flower.getName())).willReturn(flower);

		ResultActions resultActions = mockMvc.perform(
				get(new URI("/plant/json"))
				.param("name", flower.getName())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		
		String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
		assertTrue(contentAsString.length() > 0);
		resultActions.andExpect(jsonPath("$.name").value(flower.getName()));
		resultActions.andExpect(jsonPath("$.price").value(flower.getPrice()));
		resultActions.andExpect(jsonPath("$.id").doesNotExist());
		resultActions.andExpect(jsonPath("$.delivery").doesNotExist());
	}
	
	@Test
	public void canGetPlantByNameDTO() throws URISyntaxException, Exception {
		Flower flower = FlowerTests.getTestFlower();
		BDDMockito.given(plantService.getPlantByName(flower.getName())).willReturn(flower);

		ResultActions resultActions = mockMvc.perform(
				get(new URI("/plant/dto"))
				.param("name", flower.getName())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		
		String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
		assertTrue(contentAsString.length() > 0);
		resultActions.andExpect(jsonPath("$.name").value(flower.getName()));
		resultActions.andExpect(jsonPath("$.price").value(flower.getPrice()));
		resultActions.andExpect(jsonPath("$.id").doesNotExist());
		resultActions.andExpect(jsonPath("$.delivery").doesNotExist());
	}
}
