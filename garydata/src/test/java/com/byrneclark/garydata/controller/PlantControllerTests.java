package com.byrneclark.garydata.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.byrneclark.garydata.entity.Flower;
import com.byrneclark.garydata.entity.FlowerTests;
import com.byrneclark.garydata.entity.Plant;
import com.byrneclark.garydata.entity.PlantTests;
import com.byrneclark.garydata.service.PlantService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class PlantControllerTests {

	private static final Long TEST_PLANT_ID = 99l;

	private static final BigDecimal TEST_MAX_PRICE = new BigDecimal("9.99");

	private static final String TEST_JSON_PREFIX_EMBEDDED_ARRAY_RECORD = "$._embedded.plantList[0]";

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PlantService plantService;
	
	@Autowired
	private PlantController plantController;

	@Autowired
	private JacksonTester<Plant> json;
	
//	@Autowired
//	private ObjectMapper objectMapper;
	
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

	@Test
	public void canVerifyDeliveryComplete() throws URISyntaxException, IOException, Exception {
		given(plantService.isPlantDelivered(TEST_PLANT_ID)).willReturn(true);

		ResultActions resultActions = mockMvc.perform(
				get(new URI("/plant/delivered/" + TEST_PLANT_ID))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		
		String result = resultActions.andReturn().getResponse().getContentAsString();
		assertNotNull(result);
		assertEquals(true, Boolean.valueOf(result));
	}
	
	@Test
	public void canCreatePlant() throws URISyntaxException, Exception {
		Plant plant = PlantTests.getTestPlant_1();
		given(plantService.save(plant)).willReturn(plant.getId());
		

		ResultActions resultActions = mockMvc.perform(
				post(new URI("/plant/save"))
				.content(json.write(plant).getJson())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		
		String result = resultActions.andReturn().getResponse().getContentAsString();
		assertNotNull(result);
		assertEquals(plant.getId(), Long.valueOf(result));
	}
	
	@Test
	public void canGetPlantsLessThanPrice() throws URISyntaxException, Exception {
		Plant plant = PlantTests.getTestPlant_1();
		
		given(plantService.getPlantsLessThanPrice(TEST_MAX_PRICE)).willReturn(Collections.singletonList(plant));
		
		ResultActions resultActions = mockMvc.perform(
				get(new URI("/plant/pricelimit/" + TEST_MAX_PRICE))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		
		validatePlantResult(resultActions, plant, TEST_JSON_PREFIX_EMBEDDED_ARRAY_RECORD);
	}

	private void validatePlantResult(ResultActions resultActions, Plant plant,
			String prefix) throws Exception {
//		String expectedResult =  objectMapper.writeValueAsString(Collections.singletonList(plant));
//		String actualResult = resultActions.andReturn().getResponse().getContentAsString();
		resultActions.andExpect(jsonPath("[0].id").doesNotHaveJsonPath());
		resultActions.andExpect(jsonPath("[0].price").value(plant.getPrice()));
		resultActions.andExpect(jsonPath("[0].name").value(plant.getName()));
		resultActions.andExpect(jsonPath("[0].delivery").doesNotHaveJsonPath());
//		resultActions.andExpect(content().json(expectedResult));
//		assertEquals(expectedResult, actualResult);
	}
}
