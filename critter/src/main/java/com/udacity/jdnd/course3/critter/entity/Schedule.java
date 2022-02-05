package com.udacity.jdnd.course3.critter.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

public class Schedule {

	private Long id;
	private List<Employee> employees;
	private List<Pet> pets;
	private LocalDate date;
	private Set<EmployeeSkill> activities;

	public Schedule(Long id, List<Employee> employees, List<Pet> pets, LocalDate date,
			Set<EmployeeSkill> activities) {
		this.id = id;
		this.employees = employees;
		this.pets = pets;
		this.date = date;
		this.activities = activities;
	}

	public Schedule() {
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setActivities(Set<EmployeeSkill> activities) {
		this.activities = activities;
	}

	public Set<EmployeeSkill> getActivities() {
		return activities;
	}

	@Override
	public int hashCode() {
		return Objects.hash(activities, date, employees, id, pets);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		return Objects.equals(activities, other.activities) && Objects.equals(date, other.date)
				&& Objects.equals(employees, other.employees) && Objects.equals(id, other.id)
				&& Objects.equals(pets, other.pets);
	}

}
