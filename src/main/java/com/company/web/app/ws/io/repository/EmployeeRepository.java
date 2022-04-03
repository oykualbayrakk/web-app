package com.company.web.app.ws.io.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.company.web.app.ws.io.entity.EmployeeEntity;

public interface EmployeeRepository extends PagingAndSortingRepository<EmployeeEntity, Long> {
	
	@Query(value="select * from employees e where e.first_name = ?1",nativeQuery=true)
	List<EmployeeEntity> findEmployeeByFirstName(String firstName);
	
	@Query(value="select * from employees e where e.last_name = :lastName",nativeQuery=true)
	List<EmployeeEntity> findEmployeeByLastName(@Param("lastName") String lastName);
	
	@Query(value="select * from employees e where e.user_Id = :userId",nativeQuery=true)
	EmployeeEntity findEmployeeByUserId(@Param("userId") String userId);
	
	@Query(value="select * from employees",nativeQuery=true)
	List<EmployeeEntity> findEmployees();
	
	@Query(value="select * from employees e where e.email = : email",nativeQuery=true)
	EmployeeEntity findEmployeeByEmail(@Param("email") String email);
}
