package com.udacity.jdnd.course3.critter.entity;

import java.util.Objects;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ElementCollection(targetClass = EmployeeSkill.class)
	@Enumerated(EnumType.STRING)
	private Set<EmployeeSkill> skills;

	public Employee(Long id, String name, Set<EmployeeSkill> skills) {
		this.id = id;
		this.name = name;
		this.skills = skills;
	}

	public Employee() {
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

	public void setSkills(Set<EmployeeSkill> skills) {
		this.skills = skills;
	}

	public Set<EmployeeSkill> getSkills() {
		return skills;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, skills);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(skills, other.skills);
	}

}
