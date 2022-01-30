package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.service.CustomerService;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
    private CustomerService service;

	@PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
    	Customer customer = dtoToCustomer(customerDTO);
    	Customer savedCustomer = service.save(customer);
        return customerToDto(savedCustomer);
    }

    private CustomerDTO customerToDto(Customer customer) {
    	CustomerDTO customerDTO = new CustomerDTO();
    	BeanUtils.copyProperties(customer, customerDTO);
		return customerDTO;
	}

	private Customer dtoToCustomer(CustomerDTO customerDTO) {
    	Customer customer = new Customer();
    	BeanUtils.copyProperties(customerDTO, customer);
		return customer;
	}

	@GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
		List<Customer> customers = service.findAll();
		return createCustomerDTOList(customers);
    }

    private List<CustomerDTO> createCustomerDTOList(List<Customer> customers) {
    	List<CustomerDTO> dtos = new ArrayList<CustomerDTO>();
    	for (Customer customer:customers) {
    		dtos.add(customerToDto(customer));
    	}
		return dtos;
	}

	@GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

}
