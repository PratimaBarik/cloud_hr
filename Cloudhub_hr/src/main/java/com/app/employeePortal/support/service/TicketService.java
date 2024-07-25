package com.app.employeePortal.support.service;

import java.util.List;

import com.app.employeePortal.support.mapper.TicketRequestMapper;
import com.app.employeePortal.support.mapper.TicketResponseMapper;

public interface TicketService {

	TicketResponseMapper createTicket(TicketRequestMapper ticketRequestMapper);

	TicketResponseMapper getTicketByTicketId(String ticketId);

	List<TicketResponseMapper> getTicketByUserId(String userId, int pageNo, int pageSize);

	String createTicketThroughWebsite(TicketRequestMapper ticketRequestMapper);

	TicketResponseMapper updateTicket(String ticketId, TicketRequestMapper ticketRequestMapper);

	void deleteTicket(String ticketId);

	
	

}
