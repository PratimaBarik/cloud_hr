package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.Visa;

@Repository
public interface VisaRepository extends JpaRepository<Visa, String> {

	List<Visa> findByUserIdAndLiveInd(String userId, boolean b);

	public Visa findByVisaId(String visaId);
	
}
