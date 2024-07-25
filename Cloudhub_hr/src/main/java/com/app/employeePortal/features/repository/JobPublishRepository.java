package com.app.employeePortal.features.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.features.entity.JobPublishDetails;


public interface JobPublishRepository extends JpaRepository<JobPublishDetails, String>{

	
	@Query(value = "select a  from JobPublishDetails a  where a.website=:website and a.liveInd = true" )
	JobPublishDetails getDetailsByWebsite(@Param(value="website")String website);

	@Query(value = "select a  from JobPublishDetails a  where a.orgId=:orgId and a.liveInd = true" )
	JobPublishDetails getDetailsByOrgId(@Param(value="orgId")String orgId);


}
