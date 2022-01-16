package com.byrneclark.garydata.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.byrneclark.garydata.entity.Delivery;
import com.byrneclark.garydata.entity.DeliveryTests;
import com.byrneclark.garydata.entity.RecipientAndPrice;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@Transactional
public class DeliveryRepositoryTests {

	private static final String TEST_MERGE_NAME = "merge name";
	private static final Long TEST_ID = 1l;
	@Autowired
	private DeliveryRepository repository;
	private Delivery delivery;
	
	@BeforeEach
	public void beforeEach() {
		delivery = DeliveryTests.getTestDelivery();
		delivery.setId(null);
		repository.persist(delivery);
	}
	
	@Test
	public void canCreateRepository() {
		assertNotNull(repository);
	}
	
	@Test
	public void canPersistDelivery() {
		assertNotNull(delivery.getId());
	}
	
	@Test
	public void canFindDelivery() {
		Delivery foundDelivery = repository.find(delivery.getId());
		DeliveryTests.validatePropertiesAreEqual(foundDelivery, delivery);
	}
	
	@Test
	public void canMergeDelivery() {
		delivery.setName(TEST_MERGE_NAME);
		Delivery mergedDelivery = repository.merge(delivery);
		assertEquals(delivery.getId(), mergedDelivery.getId());
		assertEquals(TEST_MERGE_NAME, mergedDelivery.getName());
	}
	
	@Test
	public void canDeleteDelivery() {
		repository.delete(delivery.getId());
		assertNull(repository.find(delivery.getId()));
	}
	
	@Test
	public void canGetDeliveriesByName() {
		List<Delivery> deliveries = repository.getDeliveriesByName("Bobby");
		assertNotNull(deliveries);
	}
	
	@Test
	public void canGetDeliveryTotalById() {
		RecipientAndPrice recipientAndPrice = repository.getDeliveryTotalById(TEST_ID);
		assertNotNull(recipientAndPrice);
	}
}
