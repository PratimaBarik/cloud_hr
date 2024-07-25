package com.app.employeePortal.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.permission.entity.ThirdParty;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, String> {

	public ThirdParty findByOrgId(String orgId);
}
