package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.InvestorCategory;
@Repository
public interface InvestorCategoryRepository extends JpaRepository<InvestorCategory, String>{

	public InvestorCategory findByInvestorCategoryId(String investorCategoryId);

	public List<InvestorCategory> findByOrgIdAndLiveInd(String orgId, boolean b);

	public List<InvestorCategory> findByNameContaining(String name);

	public List<InvestorCategory> findByNameAndOrgIdAndLiveInd(String name, String orgId, boolean b);

	
	
}
