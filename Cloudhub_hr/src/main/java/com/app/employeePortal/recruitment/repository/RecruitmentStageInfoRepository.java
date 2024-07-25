package com.app.employeePortal.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.recruitment.entity.RecruitmentProcessStageInfo;

public interface RecruitmentStageInfoRepository extends JpaRepository<RecruitmentProcessStageInfo, String>{

}
