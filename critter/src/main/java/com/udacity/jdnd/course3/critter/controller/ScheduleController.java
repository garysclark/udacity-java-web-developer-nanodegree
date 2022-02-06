package com.udacity.jdnd.course3.critter.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PetService petService;

	@PostMapping
	public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
		Schedule schedule = dtoToSchedule(scheduleDTO);
		return scheduleToDto(scheduleService.createSchedule(schedule));
	}

	@GetMapping
	public List<ScheduleDTO> getAllSchedules() {
		List<ScheduleDTO> dtos = new ArrayList<>();
		List<Schedule> schedules = scheduleService.findAllSchedules();
		for(Schedule schedule:schedules) {
			dtos.add(scheduleToDto(schedule));
		}
		return dtos;
	}

	@GetMapping("/pet/{petId}")
	public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
		List<Schedule> schedules = scheduleService.findAllSchedulesForPet(petId);
		return schedulesToDtos(schedules);
	}

	@GetMapping("/employee/{employeeId}")
	public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
		List<Schedule> schedules = scheduleService.findAllSchedulesForEmployee(employeeId);
		return schedulesToDtos(schedules);
	}

	@GetMapping("/customer/{customerId}")
	public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
		List<Schedule> schedules = scheduleService.findAllSchedulesForPetOwnerId(customerId);
		return schedulesToDtos(schedules);
	}

	private List<ScheduleDTO> schedulesToDtos(List<Schedule> schedules) {
		List<ScheduleDTO> dtos = new ArrayList<>();
		for(Schedule schedule:schedules) {
			dtos.add(scheduleToDto(schedule));
		}
		return dtos;
	}

	private ScheduleDTO scheduleToDto(Schedule schedule) {
		ScheduleDTO dto = new ScheduleDTO();
		BeanUtils.copyProperties(schedule, dto);
		for(Employee employee:schedule.getEmployees()) {
			dto.getEmployeeIds().add(employee.getId());
		}
		for(Pet pet:schedule.getPets()) {
			dto.getPetIds().add(pet.getId());
		}
		return dto;
	}

	private Schedule dtoToSchedule(ScheduleDTO dto) {
		Schedule schedule = new Schedule();
		BeanUtils.copyProperties(dto, schedule);
		for(Long employeeId:dto.getEmployeeIds()) {
			schedule.getEmployees().add(employeeService.findById(employeeId));
		}
		for(Long petId:dto.getPetIds()) {
			schedule.getPets().add(petService.findPetById(petId));
		}
		return schedule;
	}
}
