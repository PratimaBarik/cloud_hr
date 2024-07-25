package com.app.employeePortal.processApproval.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.processApproval.entity.ProcessApproval;

public interface ProcessApprovalRepository extends JpaRepository<ProcessApproval, String> {

	ProcessApproval findByProcessName(String processName);

	//ProcessApproval findById(String processId);

}
