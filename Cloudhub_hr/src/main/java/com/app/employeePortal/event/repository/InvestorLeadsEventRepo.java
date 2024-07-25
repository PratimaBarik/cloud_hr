package com.app.employeePortal.event.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.event.entity.InvestorLeadsEventLink;

public interface InvestorLeadsEventRepo extends JpaRepository<InvestorLeadsEventLink,String> {
	
	@Query(value = "select a  from InvestorLeadsEventLink a  where a.investorLeadsId=:investorLeadsId and live_ind=true" )
	Page<InvestorLeadsEventLink> getByInvestorLeadsId(String investorLeadsId, Pageable paging);
	
	@Query(value = "select a  from InvestorLeadsEventLink a  where a.investorLeadsId=:investorLeadsId and live_ind=true" )
	List<InvestorLeadsEventLink> getByInvestorLeadsIdAndLiveInd(String investorLeadsId);
	
	@Query("select a from InvestorLeadsEventLink a where a.investorLeadsId=:investorLeadsId and a.eventId=:eventId and a.liveInd=true")
	InvestorLeadsEventLink getByInvestorLeadsIdAndEventIdAndLiveInd(@Param("investorLeadsId")String investorLeadsId, @Param("eventId")String eventId);

}
