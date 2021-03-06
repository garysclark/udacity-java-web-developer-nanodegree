package com.byrneclark.garydata.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.byrneclark.garydata.entity.Plant;
import com.byrneclark.garydata.entity.PricedPlant;
import com.byrneclark.garydata.service.PlantService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/plant")
public class PlantController {

	@Autowired
	private PlantService plantService;
	
	@JsonView(PricedPlant.class)
	@GetMapping(path = "/json")
	public Plant getFilteredPlant(@RequestParam String  name) {
		return plantService.getPlantByName(name);
	}
	
	@GetMapping(path = "/dto")
	public PlantDTO getPlantDto(@RequestParam String name) {
		return createDto(plantService.getPlantByName(name));
	}
	
	@GetMapping(path = "/delivered/{id}")
	public Boolean isPlantDelivered(@PathVariable Long id) {
		return plantService.isPlantDelivered(id);
	}
	
	@JsonView(PricedPlant.class)
	@GetMapping(path = "/pricelimit/{price}")
	public List<Plant> getPlantsLessThanPrice(@PathVariable BigDecimal price){
		return plantService.getPlantsLessThanPrice(price);
	}

	@PostMapping(path = "/save")
	public Long savePlant(@Validated @RequestBody Plant plant) {
		return plantService.save(plant);
	}
	
	private PlantDTO createDto(Plant plant) {
		PlantDTO dto = new PlantDTO();
		BeanUtils.copyProperties(plant, dto);
		return dto;
	}
}
