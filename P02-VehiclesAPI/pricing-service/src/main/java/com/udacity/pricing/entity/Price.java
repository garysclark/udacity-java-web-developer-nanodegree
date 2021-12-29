package com.udacity.pricing.entity;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents the price of a given vehicle, including currency.
 */
@Entity
public class Price {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY) 
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

	@Override
	public int hashCode() {
		return Objects.hash(currency, id, price, vehicleid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Price other = (Price) obj;
		return Objects.equals(currency, other.currency) && Objects.equals(id, other.id)
				&& Objects.equals(price, other.price) && Objects.equals(vehicleid, other.vehicleid);
	}
}
