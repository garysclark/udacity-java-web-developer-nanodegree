package com.byrneclark.garydata.dao;

import java.util.List;

public interface CandyDAO {

	public List<CandyData> findAllCandy();

	public void addCandyToDelivery(Long candyId, Long deliveryId);

	public List<CandyData> findCandyOnDelivery(Long deliveryId);

}
