package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.GlobalProcessDetailsDelete;
@Repository
public interface RecruitmentProcessDetailsDeleteRepository extends JpaRepository<GlobalProcessDetailsDelete, String>{

	List<GlobalProcessDetailsDelete> findByOrgId(String orgId);

	@Query(value = "select a  from GlobalProcessDetailsDelete a  where a.processId=:processId" )
	public GlobalProcessDetailsDelete getProcessDetailsByProcessId(@Param(value="processId")String processId);

	List<GlobalProcessDetailsDelete> getRecriutmentsByOrgId(String orgId);
	

		
}
