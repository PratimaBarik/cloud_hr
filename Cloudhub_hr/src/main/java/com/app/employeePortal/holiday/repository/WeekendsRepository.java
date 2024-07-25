package com.app.employeePortal.holiday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.holiday.entity.Weekends;
@Repository
public interface WeekendsRepository extends JpaRepository<Weekends, String>{
	
	@Query("select a from Weekends a where a.orgId=:orgId and a.countryId=:countryId")
	Weekends getWeekendsListByOrganizationIdAndCountryId(@Param(value="orgId")String orgId,@Param(value="countryId")String countryId);
	
	
	
}
