package com.company.web.app.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.web.app.ws.exceptions.EmployeeServiceException;
import com.company.web.app.ws.io.entity.EmployeeEntity;
import com.company.web.app.ws.io.repository.EmployeeRepository;
import com.company.web.app.ws.shared.Utils;
import com.company.web.app.ws.ui.controller.EmployeeDto;
import com.company.web.app.ws.ui.model.response.EmployeeRest;

@Service
public class EmployeeServiceImplementation implements EmployeeService{
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	Utils utils;

	@Override
	public EmployeeDto createEmployee(EmployeeDto employee) {
		if(Objects.nonNull(employeeRepository.findEmployeeByEmail(employee.getEmail())))
			throw new EmployeeServiceException("Record already exists");

		final ModelMapper modelMapper = new ModelMapper();
		final EmployeeEntity userEntity = modelMapper.map(employee, EmployeeEntity.class);

		String publicUserId = utils.generateUserId(30);
		userEntity.setUserId(publicUserId);

		final EmployeeEntity storedUserDetails = employeeRepository.save(userEntity);
 
		final EmployeeDto returnValue  = modelMapper.map(storedUserDetails, EmployeeDto.class);
		
		return returnValue;
	}

	@Override
	public EmployeeDto getEmployeeByEmployeeId(String id) {
		EmployeeDto returnValue = new EmployeeDto();
		final EmployeeEntity employeeEntity = employeeRepository.findEmployeeByUserId(id);

		if (Objects.isNull(employeeEntity))
			throw new EmployeeServiceException("Employee with ID: " + id + " not found");

		BeanUtils.copyProperties(employeeEntity, returnValue);

		return returnValue;
	}

	@Override
	public void deleteEmployee(String id) {
		EmployeeEntity userEntity = employeeRepository.findEmployeeByUserId(id);

		if (userEntity == null)
			throw new EmployeeServiceException("Employee with ID: " + id + " not found");

		employeeRepository.delete(userEntity);
	}

	@Override
	public EmployeeDto updateEmployee(String id, EmployeeDto employeeDto) {
		EmployeeDto returnValue = new EmployeeDto();

		final EmployeeEntity userEntity = employeeRepository.findEmployeeByUserId(id);

		if (userEntity == null)
			throw new EmployeeServiceException("Employee with ID: " + id + " not found");

		userEntity.setFirstName(employeeDto.getFirstName());
		userEntity.setLastName(employeeDto.getLastName());

		final EmployeeEntity updatedUserDetails = employeeRepository.save(userEntity);
		returnValue = new ModelMapper().map(updatedUserDetails, EmployeeDto.class);

		return returnValue;
	}

	@Override
	public List<EmployeeRest> getEmployees() {
		final List<EmployeeEntity> employeeEntities = employeeRepository.findEmployees();
		List<EmployeeRest> rests = new ArrayList<>();
		
		for (EmployeeEntity employeeEntity : employeeEntities) {
			EmployeeRest employeeRest = new EmployeeRest();
			BeanUtils.copyProperties(employeeEntity, employeeRest);
			rests.add(employeeRest);
		}
		
		return rests;
	}

	@Override
	public EmployeeDto getEmployeeByEmail(String email) {
		EmployeeEntity employeeEntity = employeeRepository.findEmployeeByEmail(email);

		if (Objects.isNull(employeeEntity))
			throw new EmployeeServiceException("Email not found");

		EmployeeDto returnValue = new EmployeeDto();
		BeanUtils.copyProperties(employeeEntity, returnValue);
 
		return returnValue;
	}
}
