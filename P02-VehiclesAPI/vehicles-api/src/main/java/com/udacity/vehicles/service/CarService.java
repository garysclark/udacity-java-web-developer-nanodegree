package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

	private final CarRepository repository;
	private final MapsClient mapsClient;
	private final PriceClient priceClient;

    public CarService(CarRepository repository, MapsClient mapsClient, PriceClient priceClient) {
        this.repository = repository;
        this.mapsClient = mapsClient;
        this.priceClient = priceClient; 
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
    	List<Car> cars = repository.findAll();
    	for(Car car : cars) {
    		addSupportingContent(car);
    	}
        return cars;
    }

	/**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
    	
    	Car car = findCar(id);
    	addSupportingContent(car);

        return car;
    }

	/**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
        	Optional<Car> optionalCar = repository.findById(car.getId());
        	if(optionalCar.isEmpty()) {
        		throw new CarNotFoundException(CarNotFoundException.ERROR_INVALID_CAR_ID + car.getId());
        	}
        	// preserve creation date from existing car, all other attributes will be set based on input Car
        	car.setCreatedAt(optionalCar.get().getCreatedAt());
        }

        Car savedCar = repository.save(car);
        addSupportingContent(savedCar);
        
        return repository.save(savedCar);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
    	
    	Car car = findCar(id);
    	repository.delete(car);
    }


    private void addSupportingContent(Car car) {
    	car.setPrice(priceClient.getPrice(car.getId()));
    	Location location = mapsClient.getAddress(car.getLocation());
    	car.setLocation(location);
	}

    private Car findCar(Long id) {
    	Optional<Car> optionalCar = repository.findById(id);
    	
    	if(optionalCar.isEmpty()) {
    		throw new CarNotFoundException(CarNotFoundException.ERROR_INVALID_CAR_ID + id);
    	}
    	
    	return optionalCar.get();
	}
}
