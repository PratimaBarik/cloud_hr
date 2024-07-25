package com.app.employeePortal.support.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.support.entity.Ticket;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, String>{

	Ticket findByTicketIdAndLiveInd(String ticketId, boolean b);

	public List<Ticket> findByUserIdAndLiveInd(String userId, boolean b);
//
//	public Team findByTeamLeadAndLiveInd(String teamLead, boolean liveInd);

	@Query(value = "select a  from Ticket a  where a.userId=:userId and a.liveInd=true" )
	Page<Ticket> findByUserIdAndLiveIndPageWise(@Param(value="userId")String userId, Pageable paging);
	
	
}
