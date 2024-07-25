package com.app.employeePortal.partner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.partner.entity.PartnerInfo;

@Repository
public interface PartnerInfoRepository extends JpaRepository<PartnerInfo, String>{

}
