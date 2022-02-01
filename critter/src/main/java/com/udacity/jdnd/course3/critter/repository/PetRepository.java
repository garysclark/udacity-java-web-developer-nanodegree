package com.udacity.jdnd.course3.critter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.Pet;

@Repository
@Transactional
public interface PetRepository extends JpaRepository<Pet, Long> {

	List<Pet> findByOwnerId(Long id);

}
