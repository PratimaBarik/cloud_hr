package com.app.employeePortal.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.event.entity.EventAddressLink;

@Repository
public interface EventAddressRepository extends JpaRepository<EventAddressLink, String> {

	@Query(value = "select a  from EventAddressLink a  where a.event_id=:eventId and live_ind=true" )
	public List<EventAddressLink> getAddressListByEventId(@Param(value="eventId") String eventId);
	
	
}
