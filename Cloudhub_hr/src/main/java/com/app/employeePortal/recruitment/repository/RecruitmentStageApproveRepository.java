package com.app.employeePortal.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.recruitment.entity.RecruitmentStageApprove;

public interface RecruitmentStageApproveRepository extends JpaRepository<RecruitmentStageApprove, String>{

	RecruitmentStageApprove findByStageIdAndLiveInd(String stageId, boolean liveInd);

	RecruitmentStageApprove findByStageIdAndProcessIdAndLiveInd(String stageId, String processId, boolean liveInd);

}
