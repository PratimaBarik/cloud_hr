package com.app.employeePortal.Opportunity.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.Opportunity.entity.OpportunityIncludedLink;

public interface OpportunityIncludedRepository extends JpaRepository<OpportunityIncludedLink, String> {

	List<OpportunityIncludedLink> findByOpportunityId(String opportunityId);

	@Query(value = "select a  from OpportunityIncludedLink a  where a.employeeId=:empId and a.liveInd =true" )
	Page<OpportunityIncludedLink> getOpportunityIncludedLinkByUserIdWithPagination(@Param(value="empId")String empId, Pageable paging);
	
	@Query(value = "select a  from OpportunityIncludedLink a  where a.employeeId=:empId and a.liveInd =true" )
	List<OpportunityIncludedLink> getOpportunityIncludedLinkByUserId(@Param(value="empId")String empId);

	List<OpportunityIncludedLink> findByEmployeeIdAndCreationDateBetweenAndLiveInd(String userId, Date currDate,
			Date nextDate, boolean b);
	
}