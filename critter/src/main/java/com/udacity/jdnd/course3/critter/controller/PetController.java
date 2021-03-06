package com.udacity.jdnd.course3.critter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

	@Autowired
    private PetService petService;
	@Autowired
	private CustomerService customerService;

	@PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
    	Pet pet = dtoToPet(petDTO);
    	return petToDto(petService.savePet(pet));
    }

	@GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
		Pet pet = petService.getPetById(petId);
		return petToDto(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
    	List<Pet> pets = petService.getAllPets();
    	return createDTOList(pets);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
    	List<Pet> pets = petService.getPetsByCustomerId(ownerId);
    	return createDTOList(pets);
    }

    private PetDTO petToDto(Pet pet) {
    	PetDTO dto = new PetDTO();
    	BeanUtils.copyProperties(pet, dto);
    	dto.setOwnerId(pet.getOwner().getId());
    	return dto;
	}

	private Pet dtoToPet(PetDTO dto) {
    	Pet pet = new Pet();
    	BeanUtils.copyProperties(dto, pet);
    	pet.setOwner(customerService.getCustomerById(dto.getOwnerId()));
		return pet;
	}
    
    private List<PetDTO> createDTOList(List<Pet> pets) {
    	List<PetDTO> dtos = new ArrayList<PetDTO>();
    	for (Pet pet:pets) {
    		dtos.add(petToDto(pet));
    	}
		return dtos;
	}

}
