package com.app.employeePortal.recruitment.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.RecruitmentRecruiterLink;

@Repository
public interface RecruitmentRecruiterLinkRepository extends JpaRepository<RecruitmentRecruiterLink, String> {
	
	@Query(value = "select a  from RecruitmentRecruiterLink a  where a.recruitmentId=:recruitmentId and a.liveInd=true")
	List<RecruitmentRecruiterLink> getRecruitmentRecruiterLinkByRecruitmentId(@Param(value ="recruitmentId") String recruitmentId);

	@Query(value = "select a  from RecruitmentRecruiterLink a  where a.recruiterId=:recruiterId and a.liveInd=true and a.creationDate BETWEEN :startDate AND :endDate")
	List<RecruitmentRecruiterLink> getRecruitmentRecruiterLinkByRecruitmentIdAndDateRange(@Param(value ="recruiterId") String recruiterId,
			@Param(value ="startDate") Date startDate, 
			@Param(value ="endDate") Date endDate);

	@Query(value = "select a  from RecruitmentRecruiterLink a  where a.recruiterId=:recruiterId and a.liveInd=true")
	List<RecruitmentRecruiterLink> getrecruiterListByRecruiterId(@Param(value ="recruiterId") String recruiterId);

	//List<RecruitmentRecruiterLink> getRecruitmentRecruiterLinkByOrgId(String orgId);

}
