package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.RecruitmentPartnerLink;

@Repository
public interface RecruitmentPartnerLinkRepository extends JpaRepository<RecruitmentPartnerLink, String>{

	List<RecruitmentPartnerLink> findByRecruitmentIdAndLiveInd(String recruitment_id, boolean liveInd);

}
