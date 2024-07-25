package com.app.employeePortal.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.OpportunityRecuitInfo;


@Repository

public interface RecruitmentInfoRepository extends JpaRepository<OpportunityRecuitInfo, String> {

}
