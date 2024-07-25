package com.app.employeePortal.source.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.source.entity.Source;
@Repository
public interface SourceRepository extends JpaRepository<Source, String>{

	Source findBySourceId(String sourceId);

	List<Source> findByOrgIdAndLiveInd(String orgId, boolean b);
	
	@Query(value = "select a from Source a  where a.name=:name and a.liveInd=true " )
	Source findBySourceName(@Param(value="name") String name);

	List<Source> findByNameAndOrgIdAndLiveInd(String name, String orgId, boolean b);
	
	@Query(value = "select a from Source a  where a.name=:name and a.orgId=:orgId and a.liveInd=true " )
	Source findByNameAndOrgId(@Param(value="name") String name,@Param(value="orgId") String orgId);

	Source findByNameAndUserId(String name, String userId);
}
