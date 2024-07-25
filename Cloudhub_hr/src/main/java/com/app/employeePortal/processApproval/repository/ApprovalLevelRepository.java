package com.app.employeePortal.processApproval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.processApproval.entity.ApprovalLevel;

@Repository
public interface ApprovalLevelRepository extends JpaRepository<ApprovalLevel, String>{

	ApprovalLevel findBySubProcessApprovalIdAndLiveInd(String subProcessApprovalId, boolean liveInd);

	ApprovalLevel findByIdAndLiveInd(String approvalLevelId, boolean liveInd);

	@Query(value = "select a  from ApprovalLevel a  where a.id=:Id" )
	ApprovalLevel getApprovalLevelDetail(@Param(value="Id")String Id);

}
