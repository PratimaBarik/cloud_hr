package com.app.employeePortal.unboardingWorkflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.unboardingWorkflow.entity.UnboardingStages;

@Repository
public interface UnboardingStagesRepository extends JpaRepository<UnboardingStages, String>{

	List<UnboardingStages> findByOrgId(String orgId);
	
	@Query(value = "select exp  from UnboardingStages exp  where exp.unboardingStagesId=:UnboardingStagesId and liveInd=true")
	public UnboardingStages getUnboardingStagesByUnboardingStagesId(@Param(value="UnboardingStagesId")String UnboardingStagesId);

	//List<UnboardingStages> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<UnboardingStages> findByOrgIdAndLiveInd(String orgId, boolean b);
	
	 List<UnboardingStages> findByOrgIdAndLiveIndAndPublishInd(String orgId, boolean b, boolean c);

	List<UnboardingStages> findByUnboardingWorkflowDetailsIdAndLiveInd(String unboardingWorkflowId, boolean b);

	

}
