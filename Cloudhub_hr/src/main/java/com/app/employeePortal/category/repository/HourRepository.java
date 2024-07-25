package com.app.employeePortal.category.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Hour;

@Repository
public interface HourRepository extends JpaRepository<Hour, String> {

	Hour findByCreationDateAndProjectNameAndTaskId(Date todayDate, String projectName, String taskId);

	Hour findByHourId(String hourId);

	@Query(value = "select a  from Hour a  where a.candidateId=:candidateId and a.creationDate BETWEEN :startDate AND :endDate")
	List<Hour> getByCandidateIdAndCreationDate(@Param(value ="candidateId")String candidateId,@Param(value ="startDate") Date startDate,@Param(value ="endDate") Date endDate);

	@Query(value = "select a  from Hour a  where a.userId=:userId and a.creationDate BETWEEN :startDate AND :endDate")
	List<Hour> getByUserIdAndCreationDate(@Param(value ="userId")String userId,@Param(value ="startDate") Date startDate,@Param(value ="endDate") Date endDate);
	
	@Query(value = "select a  from Hour a  where a.candidateId=:candidateId and a.projectName=:projectName and a.creationDate BETWEEN :startDate AND :endDate")
	List<Hour> getByCandidateIdAndProjectNameAndCreationDate(@Param(value ="candidateId")String candidateId, @Param(value="projectName")String projectName,@Param(value ="startDate") Date startDate,@Param(value ="endDate") Date endDate);

	List<Hour> getByCandidateId(String candidateId);

	List<Hour> getByProjectManager(String userId);

	Hour getByHourId(String hourId);

	List<Hour> getByProjectManagerAndTaskId(String userId, String taskId);

	List<Hour> getByCandidateIdAndTaskId(String candidateId, String taskId);

	Hour findByPlannerStartDateAndProjectNameAndTaskIdAndCandidateId(Date plannerStartDate, String projectName, String taskId,
			String candidateId);

}
