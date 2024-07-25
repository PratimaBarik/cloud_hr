package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.TicketsType;

@Repository
public interface TicketsTypeRepository extends JpaRepository<TicketsType, String> {
	
	List<TicketsType> findByTicketType(String ticketsType);
	
	TicketsType findByTicketTypeIdAndLiveInd(String ticketsType, boolean b);

	List<TicketsType> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<TicketsType> findByOrgId(String orgId);

	List<TicketsType> findByTicketTypeContainingAndLiveInd(String name, boolean b);

}
