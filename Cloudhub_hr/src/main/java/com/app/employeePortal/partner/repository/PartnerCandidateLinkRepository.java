package com.app.employeePortal.partner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.partner.entity.PartnerCandidateLink;

@Repository
public interface PartnerCandidateLinkRepository extends JpaRepository<PartnerCandidateLink, String> {

	List<PartnerCandidateLink> getCandidateByPartnerId(String partnerId);

}
