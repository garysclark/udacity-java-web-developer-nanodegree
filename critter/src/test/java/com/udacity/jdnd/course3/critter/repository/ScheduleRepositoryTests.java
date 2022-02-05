package com.udacity.jdnd.course3.critter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeTests;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.PetTests;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.entity.ScheduleTests;

@SpringBootTest
@Transactional
public class ScheduleRepositoryTests {
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private PetRepository petRepository;

	@Test
	public void canAccessRepository() {
		assertNotNull(scheduleRepository);
	}
	
	@Test
	public void canSaveAndFindSchedule() {
		Schedule schedule = getSchedule();
		Schedule savedSchedule = scheduleRepository.save(schedule);
		assertNotNull(savedSchedule);
		Optional<Schedule> optionalSchedule = scheduleRepository.findById(savedSchedule.getId());
		assertTrue(optionalSchedule.isPresent());
		assertEquals(savedSchedule, optionalSchedule.get());
	}

	private Schedule getSchedule() {
		Schedule schedule = ScheduleTests.getTestSchedule();
		schedule.setId(null);
		schedule.setEmployees(getEmployees());
		schedule.setPets(getPets());
		return schedule;
	}

	private List<Pet> getPets() {
		Pet pet = PetTests.getTestPet();
		pet.setId(null);
		return Collections.singletonList(petRepository.save(pet));
	}

	private List<Employee> getEmployees() {
		Employee employee = EmployeeTests.getTestEmployee();
		employee.setId(null);
		return Collections.singletonList(employeeRepository.save(employee));
	}
}
