package com.byrneclark.dogapi.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.byrneclark.dogapi.entity.Dog;
import com.byrneclark.dogapi.exception.DogNotFoundException;
import com.byrneclark.dogapi.repository.DogRepository;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;


@Component
public class Query implements GraphQLQueryResolver {

	public static final String DOG_NOT_FOUND_ERROR = "Invalid Dog Id: ";
	private DogRepository dogRepository;
	
	public Query(DogRepository dogRepository) {
		this.dogRepository = dogRepository;
	}
	
	public List<Dog> findAllDogs() {
		return (List<Dog>) dogRepository.findAll();
	}

	public Dog findDogById(Long id) {
		Optional<Dog> optionalDog = dogRepository.findById(id);
		if(optionalDog.isEmpty()) {
			throw new DogNotFoundException(DOG_NOT_FOUND_ERROR + id, id);
		}
		return optionalDog.get();
	}

	public List<String> findAllDogNames() {
		List<Dog> dogs = findAllDogs();
		List<String> names = new ArrayList<String>();
		for(Dog dog:dogs) {
			names.add(dog.getName());
		}
		return names;
	}

	public List<String> findAllDogBreeds() {
		List<Dog> dogs = findAllDogs();
		List<String> breeds = new ArrayList<String>();
		for(Dog dog:dogs) {
			breeds.add(dog.getBreed());
		}
		return breeds;
	}

}
