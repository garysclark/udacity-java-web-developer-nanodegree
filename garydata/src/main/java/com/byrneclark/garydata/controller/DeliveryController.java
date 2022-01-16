package com.byrneclark.garydata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byrneclark.garydata.entity.Delivery;
import com.byrneclark.garydata.service.DeliveryService;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

	@Autowired
	private DeliveryService deliveryService;
	
	@PostMapping
	public Long scheduleDelivery(@RequestBody Delivery delivery) {
		return deliveryService.save(delivery);
	}
}
