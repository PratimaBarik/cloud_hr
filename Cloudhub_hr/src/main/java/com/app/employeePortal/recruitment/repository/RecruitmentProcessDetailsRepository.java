package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.GlobalProcessDetails;
@Repository
public interface RecruitmentProcessDetailsRepository extends JpaRepository<GlobalProcessDetails, String>{

	
	@Query(value = "select a  from GlobalProcessDetails a  where a.orgId=:orgId and a.live_ind = true and a.publishInd = true" )
	public List<GlobalProcessDetails> getRecriutmentsByOrgId(@Param(value="orgId")String orgId);
			
		@Query(value = "select a  from GlobalProcessDetails a  where a.process_id=:processId and a.live_ind = true" )
		public GlobalProcessDetails getProcessDetailsByProcessId(@Param(value="processId")String processId);
		
		@Query(value = "select a  from GlobalProcessDetails a  where a.orgId=:orgId and a.publishInd = true and a.live_ind = true" )
		public List<GlobalProcessDetails> getpublishedProcessesOfAdmin(@Param(value="orgId")String orgId);
	
		@Query(value = "select a  from GlobalProcessDetails a  where a.orgId=:orgId and a.live_ind = true" )
		public List<GlobalProcessDetails> getRecriutmentsSettingProcessByOrgId(@Param(value="orgId")String orgId);
		
		@Query(value = "select a  from GlobalProcessDetails a  where a.process_id=:processId  and a.live_ind=true" )
		public GlobalProcessDetails getGlobalProcessDetailsById(@Param(value="processId") String processId);
		
		@Query(value = "select a  from GlobalProcessDetails a  where a.orgId=:orgId and a.live_ind = false" )
		public List<GlobalProcessDetails> getByLiveIndAndOrgId(@Param(value="orgId")String orgId);
		
}
