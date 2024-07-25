package com.app.employeePortal.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.recruitment.entity.RecruitmentAverageFeedback;

public interface RecruitmentAvgFeedbackRepository extends JpaRepository<RecruitmentAverageFeedback, String>{

	RecruitmentAverageFeedback findByCandidateId(String candidateId);

}
