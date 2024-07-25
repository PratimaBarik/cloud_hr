package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.RecruitmentProcessStageDetails;
@Repository
public interface RecruitmentStageDetailsRepository extends JpaRepository<RecruitmentProcessStageDetails, String>{

	@Query(value = "select a  from RecruitmentProcessStageDetails a  where a.recruitmentStageId=:stageId and a.liveInd = true" )
    public RecruitmentProcessStageDetails getRecruitmentStageDetailsByStageId(@Param(value="stageId")String stageId);



	@Query(value = "select a  from RecruitmentProcessStageDetails a  where a.recruitmentStageId=:recruitmentStageId and a.liveInd=true" )
	public RecruitmentProcessStageDetails findByRecruitmentStageId(@Param(value="recruitmentStageId")String stageId);
	

	@Query(value = "select a  from RecruitmentProcessStageDetails a  where a.orgId=:orgId" )
	public List<RecruitmentProcessStageDetails> getRecriutmentStageDetailsByorgId(@Param(value="orgId")String orgId);


	@Query(value = "select a  from RecruitmentProcessStageDetails a  where a.recruitmentStageId=:recruitmentStageId and a.publishInd=true and a.liveInd=true" )
	public RecruitmentProcessStageDetails getPublishedStageDetailsOfProcess(@Param(value="recruitmentStageId")String recruitmentStageId);

	@Query(value = "select a  from RecruitmentProcessStageDetails a  where a.recruitmentStageId=:recruitmentStageId and a.probability=:probability and a.liveInd=true" )
	public RecruitmentProcessStageDetails getByProbabilityAndRecruitmentStageId(@Param(value="probability")double probability,@Param(value="recruitmentStageId")String recruitmentStageId);



	//public RecruitmentProcessStageDetails getRecruitmentStageDetailsByStageId(String stage_id, boolean b);

}
 