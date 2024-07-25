package com.app.employeePortal.action.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.action.entity.ActionDetails;
@Repository
public interface ActionDetailsRepositoty extends JpaRepository<ActionDetails, String> {

	

	@Query(value = "select a  from ActionDetails a  where a.organizationId=:organizationId" )
	List<ActionDetails> getActionByOrgId(@Param(value="organizationId") String organizationId);

	List<ActionDetails> findByUserIdAndCreationDateBetween(String userId, Date currDate, Date nextDate);
	

}

