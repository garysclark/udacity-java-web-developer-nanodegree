package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
@Transactional
public class UserController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PetService petService;

	@PostMapping("/customer")
	public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
		Customer customer = dtoToCustomer(customerDTO);
		Customer savedCustomer = customerService.save(customer);
		return customerToDto(savedCustomer);
	}

	@GetMapping("/customer")
	public List<CustomerDTO> getAllCustomers(){
		List<Customer> customers = customerService.findAll();
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
		Pet pet = petService.findPetById(petId);
		CustomerDTO dto = new CustomerDTO();
		if(pet != null) {
			Customer customer = customerService.findById(pet.getOwner().getId());
			if(customer != null) {
				dto = customerToDto(customer);
			}
		}
		
		return dto;
	}

	@PostMapping("/employee")
	public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
		Employee employee = dtoToEmployee(employeeDTO);
		Employee savedEmployee = employeeService.save(employee);
		return employeeToDto(savedEmployee);
	}

	@PostMapping("/employee/{employeeId}")
	public EmployeeDTO getEmployee(@PathVariable long employeeId) {
		Employee employee = employeeService.findById(employeeId);
		return employeeToDto(employee);
	}

	@PutMapping("/employee/{employeeId}")
	public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
		Employee employee = employeeService.findById(employeeId);
		employee.setDaysAvailable(daysAvailable);
		employeeService.save(employee);
	}

	@GetMapping("/employee/availability")
	public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
		Set<EmployeeSkill> services = employeeDTO.getSkills();
		LocalDate date = employeeDTO.getDate();
		List<Employee> employees = employeeService.findEmployeesForServicesOnDate(services, date);
		return createEmployeeDTOList(employees);
	}

	private CustomerDTO customerToDto(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		BeanUtils.copyProperties(customer, customerDTO);
		List<Pet> pets = customer.getPets();
		if(pets.size() > 0) {
			List<Long> petIds = new ArrayList<>();
			for(Pet pet:pets) {
				petIds.add(pet.getId());
			}
			customerDTO.setPetIds(petIds);
		}
		return customerDTO;
	}

	private Customer dtoToCustomer(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDTO, customer);
		return customer;
	}

	private EmployeeDTO employeeToDto(Employee employee) {
		EmployeeDTO dto = new EmployeeDTO();
		BeanUtils.copyProperties(employee, dto);
		return dto;
	}

	private Employee dtoToEmployee(EmployeeDTO dto) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(dto, employee);
		return employee;
	}

	private List<EmployeeDTO> createEmployeeDTOList(List<Employee> employees) {
		List<EmployeeDTO> dtos = new ArrayList<EmployeeDTO>();
		for (Employee employee:employees) {
			dtos.add(employeeToDto(employee));
		}
		return dtos;
	}

}
