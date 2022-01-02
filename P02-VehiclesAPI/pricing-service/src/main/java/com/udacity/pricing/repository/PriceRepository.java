package com.udacity.pricing.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.udacity.pricing.entity.Price;


public interface PriceRepository extends CrudRepository<Price, Long> {
	Price findByVehicleid(@Param("vehicleid") Long vehicleid);
}
