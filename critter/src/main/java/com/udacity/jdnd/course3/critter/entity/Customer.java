package com.udacity.jdnd.course3.critter.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.transaction.annotation.Transactional;

@Entity
@Transactional
public class Customer extends User{

	private String phoneNumber;
	private String notes;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Pet> pets = new ArrayList<>();

	public Customer(Long id, String name, String phoneNumber, String notes, List<Pet> pets) {
		super(id, name);
		this.phoneNumber = phoneNumber;
		this.notes = notes;
		this.pets = pets;
	}

	public Customer() {
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes() {
		return notes;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	public List<Pet> getPets() {
		return pets;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(notes, pets, phoneNumber);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(notes, other.notes) && Objects.equals(pets, other.pets)
				&& Objects.equals(phoneNumber, other.phoneNumber);
	}

}
