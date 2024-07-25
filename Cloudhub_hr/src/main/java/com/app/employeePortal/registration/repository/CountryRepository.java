package com.app.employeePortal.registration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.registration.entity.Country;


@Repository
public interface CountryRepository extends JpaRepository<Country, String>{
	
	@Query(value = "select a  from Country a  where a.countryName=:country and a.orgId=:orgId" )
	public Country getCountryDetailsByCountryNameAndOrgId(@Param(value="country")String country, @Param(value="orgId")String orgId);
	
	List<Country> findByMandatoryIndAndOrgId(boolean b, String orgId);

	@Query(value = "select exp  from Country exp  where exp.country_id=:countryId " )
	Country getByCountryId(@Param(value="countryId") String countryId);
	
	 public List<Country> findByCountryNameContainingAndOrgId(String name, String orgId);

	public List<Country> findByOrgId(String orgId);

	//public List<Country> findByOrgIdAndMandatoryInd(String orgId, boolean b);


}
