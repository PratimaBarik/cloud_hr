package com.app.employeePortal.features.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.features.entity.AdvanceFeatureDetails;

@Repository
public interface AdvanceFeatureRepository extends JpaRepository<AdvanceFeatureDetails, String>{

	@Query(value = "select a  from AdvanceFeatureDetails a  where a.orgId=:orgId and a.liveInd = true" )
	List<AdvanceFeatureDetails> getAdavnceFeatureByOrgId(@Param(value="orgId")String orgId);

	@Query(value = "select a  from AdvanceFeatureDetails a  where a.orgId=:orgId and a.type=:type and a.liveInd = true" )
	AdvanceFeatureDetails getAdavnceFeatureByOrgIdAndType(@Param(value="orgId")String orgId, @Param(value="type") String type);

	@Query(value = "select a  from AdvanceFeatureDetails a  where a.id=:id and a.liveInd = true" )
	AdvanceFeatureDetails getAdavnceFeatureById(@Param(value="id")String id);

}
