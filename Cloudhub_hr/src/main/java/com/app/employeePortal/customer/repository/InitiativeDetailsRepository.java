package com.app.employeePortal.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.customer.entity.InitiativeDetails;
@Repository

public interface InitiativeDetailsRepository extends JpaRepository<InitiativeDetails, String> {

	//List<InitiativeDetails> getInitiativeListByCustomerId(String customerId);

	@Query(value = "select a  from InitiativeDetails a  where a.customerId=:customerId" )
	public List<InitiativeDetails>getInitiativeListByCustomerId(@Param(value="customerId") String customerId);

	@Query(value = "select a  from InitiativeDetails a  where a.userId=:userId" )
    public List<InitiativeDetails> getInitiativeListByUserId(@Param(value="userId")String userId);
	
	@Query(value = "select a  from InitiativeDetails a  where a.initiativeDetailsId=:initiativeDetailsId" )
    public InitiativeDetails getInitiativeDetailsByInitiativeDetailsId(@Param(value="initiativeDetailsId")String initiativeDetailsId);

	@Query(value = "select a  from InitiativeDetails a  where a.leadsId=:leadsId" )
	public List<InitiativeDetails>getInitiativeListByLeadsId(@Param(value="leadsId") String leadsId);
}
