package com.udacity.jdnd.course3.critter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Schedule;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule, Long>{

	List<Schedule> findAllByEmployeesId(Long id);

	List<Schedule> findAllByPetsId(Long id);

	List<Schedule> findAllByPetsOwnerId(Long ownerId);

}
