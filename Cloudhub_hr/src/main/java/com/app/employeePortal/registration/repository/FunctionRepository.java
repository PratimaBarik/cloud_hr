package com.app.employeePortal.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.registration.entity.FunctionDetails;

@Repository
public interface FunctionRepository extends JpaRepository<FunctionDetails, String>{

	public FunctionDetails findByFunctionTypeId(String functionTypeId);

}
