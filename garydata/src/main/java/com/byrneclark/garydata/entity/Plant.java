package com.byrneclark.garydata.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonView;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Plant {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Nationalized
	@JsonView(PricedPlant.class)
	private String name;
	@Column(precision=12, scale=4)
	@JsonView(PricedPlant.class)
	private BigDecimal price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;

	public Plant() {}

	public Plant(Long id, String name, BigDecimal price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setDelivey(Delivery delivery) {
		this.delivery = delivery;
	}

	public Delivery getDelivery() {
		return delivery;
	}

}
