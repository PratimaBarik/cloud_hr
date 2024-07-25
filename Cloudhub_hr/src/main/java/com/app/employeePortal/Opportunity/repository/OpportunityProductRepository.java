package com.app.employeePortal.Opportunity.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.employeePortal.Opportunity.entity.OpportunityProductLink;

@Repository
public interface OpportunityProductRepository extends JpaRepository<OpportunityProductLink, String> {

	List<OpportunityProductLink> findByOpportunityIdAndActive(String opportunityId, boolean active);

}
