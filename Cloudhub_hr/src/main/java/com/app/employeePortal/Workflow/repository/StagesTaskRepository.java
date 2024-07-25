package com.app.employeePortal.Workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Workflow.entity.StagesTask;

@Repository
public interface StagesTaskRepository extends JpaRepository<StagesTask, String> {

	List<StagesTask> findByStageIdAndOrgIdAndLiveInd(String stageId, String orgId, boolean b);

	@Query(value = "select exp  from StagesTask exp  where exp.stagesTaskId=:stagesTaskId and liveInd=true")
	public StagesTask getByStagesTaskId(@Param(value = "stagesTaskId") String stagesTaskId);
//
//	List<Stages> findByOrgIdAndLiveIndAndPublishInd(String orgId, boolean b, boolean c);


}
