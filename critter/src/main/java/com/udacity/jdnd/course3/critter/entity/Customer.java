package com.udacity.jdnd.course3.critter.entity;

import java.util.List;
import java.util.Objects;

public class Customer {

	private Long id;
	private String name;
	private String phoneNumber;
	private String notes;
	private List<Pet> pets;

	public Customer(Long id, String name, String phoneNumber, String notes, List<Pet> pets) {
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.notes = notes;
		this.pets = pets;
	}

	public Customer() {
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
		return Objects.hash(id, name, notes, pets, phoneNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(notes, other.notes)
				&& Objects.equals(pets, other.pets) && Objects.equals(phoneNumber, other.phoneNumber);
	}

}
