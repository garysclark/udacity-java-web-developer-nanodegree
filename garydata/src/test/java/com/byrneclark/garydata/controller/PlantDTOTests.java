package com.byrneclark.garydata.controller;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import com.byrneclark.garydata.entity.Flower;
import com.byrneclark.garydata.entity.FlowerTests;

public class PlantDTOTests {

	private PlantDTO dto;

	@BeforeEach
	public void beforeEach() {
		dto = new PlantDTO();
	}
	
	@Test
	public void canCreateDTO() {
		assertNotNull(dto);
	}
	
	@Test
	public void canCreateDTOFromPlant() {
		Flower flower = FlowerTests.getTestFlower();
		BeanUtils.copyProperties(flower, dto);
		assertEquals(flower.getName(), dto.getName());
		assertEquals(flower.getPrice(), dto.getPrice());
	}
}
