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

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.entity.ScheduleTests;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;

@SpringBootTest
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
}
