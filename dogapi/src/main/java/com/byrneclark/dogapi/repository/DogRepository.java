package com.byrneclark.dogapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.byrneclark.dogapi.entity.Dog;

public interface DogRepository extends CrudRepository<Dog, Long> {

}
