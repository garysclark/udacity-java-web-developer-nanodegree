package com.byrneclark.dogapi.entity;


import java.util.Objects;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Dog {
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

	private String name;
	private String breed;
	private String origin;

	public Dog(Long id, String name, String breed, String origin) {
		this.id = id;
		this.name = name;
		this.breed = breed;
		this.origin = origin;
	}


	public Dog(String name, String breed, String origin) {
		this.name = name;
		this.breed = breed;
		this.origin = origin;
	}

	public Dog() {}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Override
	public int hashCode() {
		return Objects.hash(breed, id, name, origin);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dog other = (Dog) obj;
		return Objects.equals(breed, other.breed) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(origin, other.origin);
	}

}
