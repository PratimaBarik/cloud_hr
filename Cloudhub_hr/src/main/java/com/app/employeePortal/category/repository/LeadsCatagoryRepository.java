package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.LeadsCatagory;

@Repository
public interface LeadsCatagoryRepository extends JpaRepository<LeadsCatagory, String> {

	LeadsCatagory findByOrgId(String orgId);

	LeadsCatagory findByLeadsCatagoryId(String leadsCatagoryId);

	List<LeadsCatagory> findByOrgIdAndLiveInd(String orgId, boolean b);

	LeadsCatagory findByLiveInd(boolean b);
	
}
