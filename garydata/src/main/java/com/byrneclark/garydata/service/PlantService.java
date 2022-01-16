package com.byrneclark.garydata.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.byrneclark.garydata.entity.Plant;
import com.byrneclark.garydata.repository.PlantRepository;

@Service
public class PlantService {

	@Autowired
	private PlantRepository plantRepository;

	public Long save(Plant plant) {
		Plant savedPlant = plantRepository.save(plant);
		return savedPlant.getId();
	}

	public Plant getPlantByName(String name) {
		return plantRepository.findPlantByName(name);
	}

	public Boolean isPlantDelivered(Long id) {
		return plantRepository.deliveryIsComplete(id);
	}

	public List<Plant> getPlantsLessThanPrice(BigDecimal price) {
		return plantRepository.priceLessThan(price);
	}

}
