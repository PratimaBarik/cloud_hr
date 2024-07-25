package com.app.employeePortal.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.employee.entity.EmployeeWorkflowLink;

@Repository
public interface EmployeeWorkflowLinkRepository extends JpaRepository<EmployeeWorkflowLink, String> {

	List<EmployeeWorkflowLink> findByEmployeeId(String employeeId);

	EmployeeWorkflowLink findByEmployeeIdAndLiveInd(String employeeId, boolean b);
	
	EmployeeWorkflowLink findByEmployeeWorkflowLinkIdAndLiveInd(String employeeWorkflowLinkId, boolean b);

	EmployeeWorkflowLink findByEmployeeIdAndUnboardingWorkflowDetailsIdAndLiveInd(String employeeId, String unboardingWorkflowDetailsId, boolean b);

	}
