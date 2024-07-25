package com.app.employeePortal.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.event.entity.InvestorEventLink;

public interface InvestorEventRepo extends JpaRepository<InvestorEventLink,String> {

	
	@Query(value = "select a  from InvestorEventLink a  where a.investorId=:investorId and live_ind=true" )
	List<InvestorEventLink> getByInvestorIdAndLiveInd(@Param(value="investorId")String investorId);
	
	@Query("select a from InvestorEventLink a where a.investorId=:investorId and a.eventId=:eventId and a.liveInd=true")
	InvestorEventLink getByInvestorIdAndEventIdAndLiveInd(@Param("investorId")String investorId, @Param("eventId")String eventId);

}
