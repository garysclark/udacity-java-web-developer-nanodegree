package com.udacity.jdnd.course3.critter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;

@Service
@Transactional
public class ScheduleService {

	@Autowired
	private ScheduleRepository repository;

	public Schedule createSchedule(Schedule schedule) {
		return repository.save(schedule);
	}

	public List<Schedule> findAllSchedules() {
		return repository.findAll();
	}

	public List<Schedule> findAllSchedulesForEmployee(Long id) {
		return repository.findAllByEmployeesId(id);
	}

	public List<Schedule> findAllSchedulesForPet(Long id) {
		return repository.findAllByPetsId(id);
	}

	public List<Schedule> findAllSchedulesForPetOwnerId(Long ownerId) {
		return repository.findAllByPetsOwnerId(ownerId);
	}

}
