package com.app.employeePortal.mileage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.mileage.entity.MileageRate;
@Repository
public interface MileageRateRepository extends JpaRepository<MileageRate, String> {
	
	@Query(value = "select a  from MileageRate a  where a.userId=:userId and a.liveInd=true" )
	MileageRate getMileageDetails(@Param(value="userId")String userId);
	
	@Query(value = "select a  from MileageRate a  where  a.liveInd=true")
	MileageRate getAll();
	
	@Query(value = "select a  from MileageRate a  where a.organizationId=:orgId and a.liveInd=true" )
	List<MileageRate> getMileageDetailByOrgId(@Param(value="orgId")String orgId);

	List<MileageRate> findByOrganizationId(String organizationId);
	
	@Query(value = "select a  from MileageRate a  where a.country=:country and a.liveInd=true" )
	MileageRate getMileageDetailsByCountry(@Param(value="country")String country);
	
	@Query(value = "select a  from MileageRate a  where a.country=:country and a.organizationId=:orgId and a.liveInd=true" )
	MileageRate getMileageDetailsByCountryAndOrgId(@Param(value="country")String country, @Param(value="orgId")String orgId);
	
	@Query(value = "select a  from MileageRate a  where a.mileageRateId=:mileageRateId and a.liveInd=true" )
	MileageRate getMileageDetailsByMileageRateId(@Param(value="mileageRateId")String mileageRateId);

	@Query(value = "select a  from MileageRate a  where a.country=:country and a.organizationId=:orgId and a.liveInd=true" )
	MileageRate findByCountryAndOrganizationId(String country, String orgId);
}
