package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.RecruitmentProcessStageDetailsDelete;
@Repository
public interface RecruitmentProcessStageDetailsDeleteRepository extends JpaRepository<RecruitmentProcessStageDetailsDelete, String>{

	List<RecruitmentProcessStageDetailsDelete> findByOrgId(String orgId);

	@Query(value = "select a  from RecruitmentProcessStageDetailsDelete a  where a.recruitmentStageId=:stageId")
    public RecruitmentProcessStageDetailsDelete getRecruitmentStageDetailsByStageId(@Param(value="stageId")String stageId);

	

	
		
}
