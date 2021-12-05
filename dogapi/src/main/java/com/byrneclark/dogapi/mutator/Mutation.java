package com.byrneclark.dogapi.mutator;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.byrneclark.dogapi.entity.Dog;
import com.byrneclark.dogapi.exception.DogNotFoundException;
import com.byrneclark.dogapi.repository.DogRepository;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;

@Component
public class Mutation implements GraphQLMutationResolver{

	private DogRepository dogRepository;
	private boolean deleted = false;
	
	public Mutation(DogRepository dogRepository) {
		this.dogRepository = dogRepository;
	}
	
	public Boolean deleteDogBreed(String breed) {
		Iterable<Dog> dogs = dogRepository.findAll();
		
		for(Dog dog:dogs) {
			if(dog.getBreed().equals(breed)) {
				dogRepository.delete(dog);
				deleted  = true;
			}
		}
		return deleted;
	}

	public Dog updateDogName(Long id, String name) {
		Optional<Dog> optionalDog = dogRepository.findById(id);
		if(optionalDog.isEmpty()) {
			throw new DogNotFoundException(DogNotFoundException.EXT_INVALID_DOG_ID + id, id);
		}
		Dog dog = optionalDog.get();
		dog.setName(name);
		dogRepository.save(dog);
		return dog;
	}
}
