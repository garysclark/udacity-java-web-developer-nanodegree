package com.udacity.jdnd.course3.critter.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.pet.PetType;

@Entity
@Transactional
public class Pet {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;

	private PetType type;

	private String name;

	private Long ownerId;

	private LocalDate birthDate;

	private String notes;

	public Pet(Long id, PetType type, String name, Long ownerId, LocalDate birthDate,
			String notes) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.ownerId = ownerId;
		this.birthDate = birthDate;
		this.notes = notes;
	}

	public Pet() {
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setType(PetType type) {
		this.type = type;
	}

	public PetType getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes() {
		return notes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthDate, customer, id, name, notes, ownerId, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pet other = (Pet) obj;
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(customer, other.customer)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(notes, other.notes) && Objects.equals(ownerId, other.ownerId) && type == other.type;
	}
}
