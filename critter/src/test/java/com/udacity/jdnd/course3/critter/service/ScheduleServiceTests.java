package com.udacity.jdnd.course3.critter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.entity.ScheduleTests;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;

@SpringBootTest
@Transactional
public class ScheduleServiceTests {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@MockBean
	private ScheduleRepository scheduleRepository;

	@Test
	public void canAccessService() {
		assertNotNull(scheduleService);
	}
	
	@Test
	public void canSaveSchedule() {
		Schedule schedule = ScheduleTests.getTestSchedule();
		when(scheduleRepository.save(schedule)).thenReturn(schedule);
		
		Schedule createdSchedule = scheduleService.createSchedule(schedule);
		
		assertEquals(schedule, createdSchedule);
	}
	
	@Test
	public void canFindAllSchedules() {
		List<Schedule> schedules = Collections.singletonList(ScheduleTests.getTestSchedule());
		when(scheduleRepository.findAll()).thenReturn(schedules);
		
		List<Schedule> foundSchedules = scheduleService.findAllSchedules();
		
		assertEquals(schedules, foundSchedules);
	}
	
	@Test
	public void canFindAllSchedulesForEmployee() {
		Schedule schedule = ScheduleTests.getTestSchedule();
		List<Schedule> schedules = Collections.singletonList(schedule);
		when(scheduleRepository.findAllByEmployeesId(schedule.getEmployees().get(0).getId())).thenReturn(schedules);
		
		List<Schedule> foundSchedules = scheduleService.findAllSchedulesForEmployee(schedule.getEmployees().get(0).getId());
		
		assertEquals(1, foundSchedules.size());
		assertEquals(schedule, foundSchedules.get(0));
	}
	
	@Test
	public void canFindAllSchedulesForPet() {
		Schedule schedule = ScheduleTests.getTestSchedule();
		List<Schedule> schedules = Collections.singletonList(schedule);
		when(scheduleRepository.findAllByPetsId(schedule.getPets().get(0).getId())).thenReturn(schedules);
		
		List<Schedule> foundSchedules = scheduleService.findAllSchedulesForPet(schedule.getPets().get(0).getId());
		
		assertEquals(1, foundSchedules.size());
		assertEquals(schedule, foundSchedules.get(0));
	}
	
	@Test
	public void canFindAllSchedulesForCustomer() {
		Schedule schedule = ScheduleTests.getTestSchedule();
		List<Schedule> schedules = Collections.singletonList(schedule);
		when(scheduleRepository.findAllByPetsOwnerId(schedule.getPets().get(0).getOwner().getId())).thenReturn(schedules);
		
		List<Schedule> foundSchedules = scheduleService.findAllSchedulesForPetOwnerId(schedule.getPets().get(0).getOwner().getId());
		
		assertEquals(1, foundSchedules.size());
		assertEquals(schedule, foundSchedules.get(0));
	}
}
