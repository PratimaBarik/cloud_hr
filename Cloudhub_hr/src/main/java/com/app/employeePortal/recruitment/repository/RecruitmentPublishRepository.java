package com.app.employeePortal.recruitment.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.recruitment.entity.RecruitmentPublishDetails;


public interface RecruitmentPublishRepository extends JpaRepository<RecruitmentPublishDetails, String>{
	
	@Query(value = "select a  from RecruitmentPublishDetails a  where a.recruiter_id=:id and a.liveInd=true")
	RecruitmentPublishDetails getRecruitmentPublishDetails(@Param(value ="id") String id);

	@Query(value = "select a  from RecruitmentPublishDetails a  where a.org_id=:id and a.liveInd=true")
	List<RecruitmentPublishDetails> getRecruitmentDetailsByOrgId(@Param(value ="id") String id);
	 
	@Query(value = "select a  from RecruitmentPublishDetails a  where  a.liveInd=true")
	List<RecruitmentPublishDetails> getRecruitmentDetails();

	@Query(value = "select a  from RecruitmentPublishDetails a  where a.recruiter_id=:recruitmentId and a.liveInd=true")
	RecruitmentPublishDetails getRecruitmentPulishPing(@Param(value ="recruitmentId") String recruitmentId);

	@Query(value = "select a  from RecruitmentPublishDetails a  where a.recruiter_id=:id and a.liveInd=false")
	List<RecruitmentPublishDetails> getDeletedRecruitmentPublishDetails(@Param(value ="id") String id);

	List<RecruitmentPublishDetails> findByCreationDateBetweenAndLiveInd(Date currDate, Date nextDate, boolean b);
}
