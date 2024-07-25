package com.app.employeePortal.leads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.leads.entity.LeadsInnitiativeLink;
@Repository
public interface LeadsInnitiativeLinkRepository  extends JpaRepository<LeadsInnitiativeLink, String>{

	@Query(value = "select a  from LeadsInnitiativeLink a  where a.initiativeDetailsId=:initiativeDetailsId and a.liveInd=true" )
	List<LeadsInnitiativeLink> getSkilListByInitiativeDetailsIdAndLiveInd(@Param(value="initiativeDetailsId") String initiativeDetailsId);

	
	
}
