package com.company.web.app.ws.service;

import java.util.List;
import com.company.web.app.ws.ui.controller.EmployeeDto;
import com.company.web.app.ws.ui.model.response.EmployeeRest;

public interface EmployeeService {
	EmployeeDto createEmployee(EmployeeDto employee);
	EmployeeDto getEmployeeByEmployeeId(String id);
	void deleteEmployee(String id);
	EmployeeDto updateEmployee(String id, EmployeeDto userDto);
	List<EmployeeRest> getEmployees();
	EmployeeDto getEmployeeByEmail(String email);
}
