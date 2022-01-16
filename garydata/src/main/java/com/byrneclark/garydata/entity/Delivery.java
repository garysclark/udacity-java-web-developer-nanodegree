package com.byrneclark.garydata.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;

@NamedQuery(
		name = "Delivery.findByName",
		query = "select d from Delivery d where d.name = :name")
@Entity
public class Delivery {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Nationalized
	private String name;

	@Column(name = "address_full", length = 500)
	private String address;

	private LocalDateTime deliveryTime;
	@Type(type = "yes_no")
	private Boolean isComplete;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "delivery", cascade = CascadeType.ALL)
	private List<Plant> plants;

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

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setDeliveryTime(LocalDateTime deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public LocalDateTime getDeliveryTime() {
		return deliveryTime;
	}

	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}

	public Boolean getIsComplete() {
		return isComplete;
	}

	public List<Plant> getPlants() {
		return plants;
	}

	public void setPlants(List<Plant> plants) {
		this.plants = plants;
	}
}
