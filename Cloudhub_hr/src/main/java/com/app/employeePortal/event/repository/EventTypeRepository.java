package com.app.employeePortal.event.repository;

import java.util.List;

import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.event.entity.EventType;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, String> {

	// public List<EventType> findByorgId(String orgId);

	public EventType findByEventTypeId(String eventTypeId);

//	public List<EventType> findByEventTypeContaining(String name);

	public List<EventType> findByEventType(String eventType);

	public List<EventType> findByOrgIdAndLiveInd(String orgId, boolean liveInd);

	public List<EventType> findByUserIdAndLiveInd(String userId, boolean b);
	
	public List<EventType> findByEventTypeAndLiveIndAndOrgId(String eventType, boolean b, String orgId);

	public List<EventType> findByEventTypeContainingAndOrgId(String name, String orgId);

	public EventType findByEventTypeAndOrgIdAndLiveInd(String name, String orgId, boolean b);
}
