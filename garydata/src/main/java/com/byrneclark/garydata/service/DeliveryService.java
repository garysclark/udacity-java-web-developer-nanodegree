package com.byrneclark.garydata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.byrneclark.garydata.entity.Delivery;
import com.byrneclark.garydata.repository.DeliveryRepository;

@Service
public class DeliveryService {

	@Autowired
	private DeliveryRepository deliveryRepository;

	public Long save(Delivery delivery) {
		delivery.getPlants().forEach(plant -> plant.setDelivey(delivery));
		deliveryRepository.persist(delivery);
		return delivery.getId();
	}

	public Delivery get(Long deliveryId) {
		return deliveryRepository.find(deliveryId);
	}

}
