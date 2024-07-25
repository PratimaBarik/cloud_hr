package com.app.employeePortal.leads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.leads.entity.LeadsInfo;

@Repository
public interface LeadsInfoRepository extends JpaRepository<LeadsInfo, String> {

}
