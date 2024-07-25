package com.app.employeePortal.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.event.entity.EventTypeDelete;

@Repository
public interface EventTypeDeleteRepository extends JpaRepository<EventTypeDelete, String> {

	List<EventTypeDelete> findByOrgId(String orgId);

	EventTypeDelete findByEventTypeId(String eventTypeId);

	
}
