package com.app.employeePortal.support.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.support.entity.TicketProductLink;
@Repository
public interface TicketProductLinkRepository extends JpaRepository<TicketProductLink, String>{

	List<TicketProductLink> findByTicketIdAndLiveInd(String ticketId, boolean b);

//	public Team findByTeamIdAndLiveInd(String teamId, boolean b);
//	
//	public List<Team> findByUserIdAndLiveInd(String userId, boolean b);
//
//	public Team findByTeamLeadAndLiveInd(String teamLead, boolean liveInd);
	
	
}
