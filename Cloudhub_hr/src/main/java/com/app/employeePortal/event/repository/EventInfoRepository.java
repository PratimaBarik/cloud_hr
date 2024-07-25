package com.app.employeePortal.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.event.entity.EventInfo;

@Repository
public interface EventInfoRepository extends JpaRepository<EventInfo, String>{

}
