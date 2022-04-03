package com.company.web.app.ws.ui.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.company.web.app.ws.service.EmployeeService;
import com.company.web.app.ws.ui.mode.response.OperationStatusModel;
import com.company.web.app.ws.ui.model.request.EmployeeDetailsRequestModel;
import com.company.web.app.ws.ui.model.response.EmployeeRest;
import com.company.web.app.ws.ui.model.response.RequestOperationStatus;


@RestController
@RequestMapping("employees") //http://localhost:8080/employees
public class EmployeeController {
	
	@Autowired(required = true)
	EmployeeService employeeService;

	@GetMapping(path = "/{id}")
	public EmployeeRest getEmployee(@PathVariable String id) {
		final EmployeeDto userDto = employeeService.getEmployeeByEmployeeId(id);
		final ModelMapper modelMapper = new ModelMapper();
		final EmployeeRest employeeRest = modelMapper.map(userDto, EmployeeRest.class);

		return employeeRest;
	}
	
	@PostMapping
	public EmployeeRest createEmployee(@RequestBody EmployeeDetailsRequestModel employeeDetails) {
		final ModelMapper modelMapper = new ModelMapper();
		final EmployeeDto employeeDto = new EmployeeDto();
		
		final EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
		final EmployeeRest employeeRest = modelMapper.map(createdEmployee, EmployeeRest.class);
		return employeeRest;
	}
	
	@PutMapping
	public EmployeeRest updateEmployee(@PathVariable String id, @RequestBody EmployeeDetailsRequestModel employeeDetails) {
		EmployeeRest returnValue = new EmployeeRest();

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto = new ModelMapper().map(employeeDetails, EmployeeDto.class);

		EmployeeDto updateUser = employeeService.updateEmployee(id, employeeDto);
		returnValue = new ModelMapper().map(updateUser, EmployeeRest.class);

		return returnValue;
	}
	
	@DeleteMapping
	public OperationStatusModel deleteEmployee(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());

		employeeService.deleteEmployee(id);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	@GetMapping()
	public List<EmployeeRest> getEmployees() {
		List<EmployeeRest> returnValue = new ArrayList<>();

		List<EmployeeRest> employees = employeeService.getEmployees();
		
		Type listType = new TypeToken<List<EmployeeRest>>() {
		}.getType();
		returnValue = new ModelMapper().map(employees, listType);

		return returnValue;
	}
}
