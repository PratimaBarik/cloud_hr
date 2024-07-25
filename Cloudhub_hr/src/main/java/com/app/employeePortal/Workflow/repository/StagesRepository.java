package com.app.employeePortal.Workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Workflow.entity.Stages;

@Repository
public interface StagesRepository extends JpaRepository<Stages, String> {

	List<Stages> findByWorkflowDetailsIdAndLiveInd(String WorkflowDetailsId, boolean b);

	@Query(value = "select exp  from Stages exp  where exp.stagesId=:stagesId and liveInd=true")
	public Stages getByStagesId(@Param(value = "stagesId") String stagesId);

	List<Stages> findByOrgIdAndLiveIndAndPublishInd(String orgId, boolean b, boolean c);


}
