package com.app.employeePortal.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.task.entity.CandidateTaskLink;

@Repository
public interface CandidateTaskLinkRepository extends JpaRepository<CandidateTaskLink, String>{

	public List<CandidateTaskLink> findByCandidateIdAndLiveInd(String candidateId, boolean liveInd);

	@Query(value = "select a  from CandidateTaskLink a  where a.candidateId=:candidateId and a.liveInd=true")
	public List<CandidateTaskLink> getTaskListByCandidateId(@Param(value = "candidateId") String candidateId);

	@Query(value = "select a  from CandidateTaskLink a  where a.candidateId=:candidateId")
	public List<CandidateTaskLink> getTaskListByCandidateIdWithOutLiveInd(@Param(value = "candidateId") String candidateId);

	@Query(value = "select a  from CandidateTaskLink a  where a.taskId=:taskId and a.liveInd=true")
	public List<CandidateTaskLink> getCandidateTaskLinkByTaskId(@Param(value = "taskId") String taskId);

	public CandidateTaskLink findByCandidateIdAndTaskIdAndLiveInd(String candidateId, String taskId, boolean liveInd);

}
