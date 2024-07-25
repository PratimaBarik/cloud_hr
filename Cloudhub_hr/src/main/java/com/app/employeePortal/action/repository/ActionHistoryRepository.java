package com.app.employeePortal.action.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.action.entity.ActionHistory;

@Repository
public interface ActionHistoryRepository extends JpaRepository<ActionHistory, String> {

	List<ActionHistory> findByOpportunityId(String opportunityId);

}
