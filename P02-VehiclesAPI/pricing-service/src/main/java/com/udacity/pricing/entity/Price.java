package com.udacity.pricing.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * Represents the price of a given vehicle, including currency.
 */
@Entity
public class Price {
	@Id
	@GeneratedValue(
			strategy= GenerationType.AUTO, 
			generator="native"
			)
	@GenericGenerator(
			name = "native", 
			strategy = "native"
			)

	private Long id;
    private String currency;
    private BigDecimal price;
    private Long vehicleid;

    public Price() {
    }

    public Price(Long id, String currency, BigDecimal price, Long vehicleId) {
    	this.id = id;
        this.currency = currency;
        this.price = price;
        this.vehicleid = vehicleId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getVehicleId() {
        return vehicleid;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleid = vehicleId;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
