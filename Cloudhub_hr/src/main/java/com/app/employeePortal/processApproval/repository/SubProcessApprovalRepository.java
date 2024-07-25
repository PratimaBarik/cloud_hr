package com.app.employeePortal.processApproval.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.processApproval.entity.SubProcessApproval;

public interface SubProcessApprovalRepository extends JpaRepository<SubProcessApproval, String> {

	SubProcessApproval findByProcessIdAndSubProcessName(String processId, String subProcessName);

	SubProcessApproval findByProcessId(String processId);

}
