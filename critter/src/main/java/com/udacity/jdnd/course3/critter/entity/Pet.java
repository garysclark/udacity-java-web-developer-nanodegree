package com.udacity.jdnd.course3.critter.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Nationalized;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Transactional
public class Pet {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private Customer owner;

	private PetType type;

	@Nationalized
	private String name;

	private LocalDate birthDate;

	@Nationalized
	private String notes;

	public Pet(Long id, PetType type, String name, Customer owner, LocalDate birthDate,
			String notes) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.owner = owner;
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
		return Objects.hash(birthDate, id, name, notes, owner, type);
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
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(notes, other.notes)
				&& Objects.equals(owner, other.owner) && type == other.type;
	}

	public void setOwner(Customer owner) {
		this.owner = owner;
	}

	public Customer getOwner() {
		return owner;
	}
}
