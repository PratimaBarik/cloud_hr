package com.app.employeePortal.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.recruitment.entity.GlobalProcessInfo;


public interface RecruitmentProcessInfoRepository extends JpaRepository<GlobalProcessInfo, String>{

}
