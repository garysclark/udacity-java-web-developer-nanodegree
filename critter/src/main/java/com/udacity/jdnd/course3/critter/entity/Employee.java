package com.udacity.jdnd.course3.critter.entity;

import java.time.DayOfWeek;
import java.util.Objects;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.transaction.annotation.Transactional;

@Entity
@Transactional
public class Employee extends User{

	@ElementCollection(targetClass = EmployeeSkill.class)
	@Enumerated(EnumType.STRING)
	private Set<EmployeeSkill> skills;
	@ElementCollection(targetClass = DayOfWeek.class)
	@Enumerated(EnumType.STRING)
	private Set<DayOfWeek> daysAvailable;

	public Employee(Long id, String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable) {
		super(id, name);
		this.skills = skills;
		this.daysAvailable = daysAvailable;
	}

	public Employee() {
	}

	public void setSkills(Set<EmployeeSkill> skills) {
		this.skills = skills;
	}

	public Set<EmployeeSkill> getSkills() {
		return skills;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(skills);
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
		Employee other = (Employee) obj;
		return Objects.equals(skills, other.skills);
	}

	public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
		this.daysAvailable = daysAvailable;
	}

	public Set<DayOfWeek> getDaysAvailable() {
		return daysAvailable;
	}

}
