package com.udacity.jdnd.course3.critter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.CustomerTests;
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
	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void canAccessRepository() {
		assertNotNull(scheduleRepository);
	}
	
	@Test
	public void canSaveAndFindSchedule() {
		Schedule savedSchedule = scheduleRepository.save(getSchedule());
		assertNotNull(savedSchedule);
		Optional<Schedule> optionalSchedule = scheduleRepository.findById(savedSchedule.getId());
		assertTrue(optionalSchedule.isPresent());
		assertEquals(savedSchedule, optionalSchedule.get());
	}

	@Test
	public void canFindAllSchedules() {
		Schedule schedule1 = scheduleRepository.save(getSchedule());
		Schedule schedule2 = scheduleRepository.save(getSchedule());
		List<Schedule> schedules = scheduleRepository.findAll();
		assertEquals(2, schedules.size());
		assertTrue(schedules.containsAll(Arrays.asList(schedule1, schedule2)));
	}
	
	@Test
	public void canFindScheduleByEmployee() {
		Schedule savedSchedule = scheduleRepository.save(getSchedule());
		List<Schedule> foundSchedules = scheduleRepository.findAllByEmployeesId(savedSchedule.getEmployees().get(0).getId());
		assertNotNull(foundSchedules);
		assertEquals(1, foundSchedules.size());
		assertEquals(savedSchedule, foundSchedules.get(0));
	}
	
	@Test
	public void canFindScheduleByPet() {
		Schedule savedSchedule = scheduleRepository.save(getSchedule());
		List<Schedule> foundSchedules = scheduleRepository.findAllByPetsId(savedSchedule.getPets().get(0).getId());
		assertNotNull(foundSchedules);
		assertEquals(1, foundSchedules.size());
		assertEquals(savedSchedule, foundSchedules.get(0));
	}
	
	@Test
	public void canFindScheduleByCustomer() {
		Schedule savedSchedule = scheduleRepository.save(getSchedule());
		List<Schedule> foundSchedules = scheduleRepository.findAllByPetsOwnerId(savedSchedule.getPets().get(0).getOwner().getId());
		assertNotNull(foundSchedules);
		assertEquals(1, foundSchedules.size());
		assertEquals(savedSchedule, foundSchedules.get(0));
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
		pet.setOwner(getCustomer());
		return Collections.singletonList(petRepository.save(pet));
	}

	private List<Employee> getEmployees() {
		Employee employee = EmployeeTests.getTestEmployee();
		employee.setId(null);
		return Collections.singletonList(employeeRepository.save(employee));
	}
	
	private Customer getCustomer() {
		Customer customer = CustomerTests.getTestCustomer();
		customer.setId(null);
		customer.setPets(new ArrayList<>());
		return customerRepository.save(customer);
	}
}
