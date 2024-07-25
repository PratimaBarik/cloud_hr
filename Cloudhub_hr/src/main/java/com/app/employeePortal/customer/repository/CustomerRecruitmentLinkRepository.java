package com.app.employeePortal.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.customer.entity.CustomerRecruitmentLink;

@Repository
public interface CustomerRecruitmentLinkRepository extends JpaRepository<CustomerRecruitmentLink, String>{

}
