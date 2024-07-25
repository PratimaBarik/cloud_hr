package com.app.employeePortal.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.customer.entity.CustomerInitiativeLink;
@Repository

public interface CustomerInitiativeLinkRepository extends JpaRepository<CustomerInitiativeLink, String>{

	
	@Query(value = "select a  from CustomerInitiativeLink a  where a.initiativeDetailsId=:initiativeDetailsId and a.liveInd=true" )
	List<CustomerInitiativeLink> getSkilListByInitiativeDetailsIdAndLiveInd(@Param(value="initiativeDetailsId") String initiativeDetailsId);

	@Query(value = "select a  from CustomerInitiativeLink a  where a.userId=:userId and a.liveInd=true" )
    List<CustomerInitiativeLink> getSkilListByUserId(@Param(value="userId")String userId);

	@Query(value = "select a  from CustomerInitiativeLink a  where a.initiativeDetailsId=:initiativeDetailsId and a.liveInd=true" )
	List<CustomerInitiativeLink> getByInitiativeDetailsId(@Param(value="initiativeDetailsId")String initiativeDetailsId);

	//List<CustomerInitiativeLink> getInitiativeListByCustomerId(String customerId);
	
	//@Query(value = "select a  from CustomerInitiativeLink a  where a.customerId=:customerId" )
	//public List<CustomerInitiativeLink>getInitiativeListByCustomerId(@Param(value="customerId") String customerId);
	

}

