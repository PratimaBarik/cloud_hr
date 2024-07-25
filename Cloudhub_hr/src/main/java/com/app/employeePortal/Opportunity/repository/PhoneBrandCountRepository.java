package com.app.employeePortal.Opportunity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.Opportunity.entity.PhoneBrandCount;

public interface PhoneBrandCountRepository  extends JpaRepository<PhoneBrandCount, String>{

//	List<PhoneBrandCount> findByOrderPhoneId(String orderPhoneId);
//
//	PhoneBrandCount findByOrderPhoneIdAndCompany(String orderPhoneId, String company);

	@Query(value = "select count(e) from PhoneBrandCount e where e.opportunityId =:opportunityId And e.company=:company")
	int countBygetOpportunityIdAndCompany(@Param(value="opportunityId")String opportunityId,@Param(value="company") String company);

	PhoneBrandCount findByOpportunityIdAndCompany(String opportunityId, String company);

}
