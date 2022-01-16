package com.byrneclark.garydata.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.byrneclark.garydata.entity.Delivery;
import com.byrneclark.garydata.entity.Plant;
import com.byrneclark.garydata.entity.RecipientAndPrice;

@Repository
@Transactional
public class DeliveryRepository {

	@PersistenceContext
	EntityManager entityManager;

	public void persist(Delivery delivery) {
		entityManager.persist(delivery);
	}

	public Delivery find(Long id) {
		return entityManager.find(Delivery.class, id);
	}

	public void delete(Long id) {
		Delivery delivery = find(id);
		entityManager.remove(delivery);
	}

	public Delivery merge(Delivery delivery) {
		return entityManager.merge(delivery);
	}

	public List<Delivery> getDeliveriesByName(String name) {
		TypedQuery<Delivery> query = entityManager.createNamedQuery("Delivery.findByName", Delivery.class);
		query.setParameter("name", name);
		return query.getResultList();
	}

	public RecipientAndPrice getDeliveryTotalById(Long id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<RecipientAndPrice> criteriaQuery = cb.createQuery(RecipientAndPrice.class);
		Root<Plant> root = criteriaQuery.from(Plant.class);
		criteriaQuery.select(
				cb.construct(
						RecipientAndPrice.class, 
						root.get("delivery").get("name"),
						cb.sum(root.get("price"))))
		.where(cb.equal(root.get("delivery").get("id"), id));
		RecipientAndPrice recipientAndPrice = entityManager.createQuery(criteriaQuery).getSingleResult();
		return recipientAndPrice;
	}

}
