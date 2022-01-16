package com.byrneclark.garydata.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.byrneclark.garydata.entity.Plant;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {

	Boolean existsPlantByIdAndDeliveryIsComplete(Long id, boolean b);

	//you can return a primitive directly
	@Query("select p.delivery.isComplete from Plant p where p.id = :plantId")
	Boolean deliveryIsComplete(Long plantId);

	Boolean existsPlantById(Long id);

	List<Plant> priceLessThan(BigDecimal testMaxPrice);

	Plant findPlantByName(String name);

}
