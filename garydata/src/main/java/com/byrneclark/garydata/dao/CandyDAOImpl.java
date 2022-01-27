package com.byrneclark.garydata.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CandyDAOImpl implements CandyDAO {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final String CANDY_ID = "candyId";

	private static final String DELIVERY_ID = "deliveryId";
	
	private static final String SELECT_ALL_CANDIES = "SELECT * FROM Candy";
	
	private static final String SELECT_ALL_CANDIES_BY_DELIVERY = "SELECT c.* FROM Candy_Delivery AS cd JOIN Candy AS c on c.id = cd.candyId WHERE cd.deliveryId = :" + DELIVERY_ID;

	private static final String ADD_CANDY_TO_DELIVERY = "INSERT INTO Candy_Delivery (candyId, deliveryId) VALUES (:" + CANDY_ID + ", :" + DELIVERY_ID + ")";
	
	private static final RowMapper<CandyData> candyDataRowMapper = new BeanPropertyRowMapper<>(CandyData.class);
	
	@Override
	public List<CandyData> findAllCandy() {
		return template.query(SELECT_ALL_CANDIES, candyDataRowMapper);
	}

	@Override
	public void addCandyToDelivery(Long candyId, Long deliveryId) {
		template.update(ADD_CANDY_TO_DELIVERY, new MapSqlParameterSource().addValue(CANDY_ID, candyId).addValue(DELIVERY_ID, deliveryId));
	}

	@Override
	public List<CandyData> findCandyOnDelivery(Long deliveryId) {
		return template.query(SELECT_ALL_CANDIES_BY_DELIVERY, new MapSqlParameterSource().addValue(DELIVERY_ID, deliveryId), candyDataRowMapper);
	}

}
