package com.app.employeePortal.investor.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.investor.entity.InvestorOppIncludedLink;

public interface InvestorOppIncludedLinkRepo extends JpaRepository<InvestorOppIncludedLink,String> {

	List<InvestorOppIncludedLink> findByInvestorOppId(String investorOppId);

	@Query(value = "select a  from InvestorOppIncludedLink a  where a.employeeId=:empId and a.liveInd =true" )
	Page<InvestorOppIncludedLink> getInvestorOppIncludedLinkByUserIdWithPagination(@Param(value="empId")String empId, Pageable paging);

	@Query(value = "select a  from InvestorOppIncludedLink a  where a.employeeId=:empId and a.liveInd =true" )
	List<InvestorOppIncludedLink> getInvestorOppIncludedLinkByUserId(@Param(value="empId")String empId);

	List<InvestorOppIncludedLink> findByEmployeeIdAndCreationDateBetweenAndLiveInd(String userId, Date currDate,
			Date nextDate, boolean b);

	
	
}
 