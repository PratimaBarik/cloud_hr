package com.app.employeePortal.holiday.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.holiday.entity.Holiday;
@Repository
public interface HolidayRepository extends JpaRepository<Holiday, String>{
	
	@Query("select a from Holiday a where a.org_id=:orgId")
	List<Holiday> getHolidayListByOrganizationId(@Param(value="orgId")String orgId);

	@Query(value = "select a  from Holiday a  where  a.live_ind=true and a.date BETWEEN :startDate AND :endDate")
	Holiday getHolidayByDate(@Param(value ="startDate") Date startDate, 
			@Param(value ="endDate") Date endDate);
	@Query("select a from Holiday a where a.org_id=:orgId and a.countryId=:country and a.year=:year and a.live_ind=true")
	List<Holiday> getHolidayListByOrgIdAndCountryAndYear(@Param(value="orgId")String orgId,@Param(value="country") String country,
			@Param(value="year") int year);
	
	@Query("select a from Holiday a where a.holiday_name=:holidayName and a.countryId=:country and a.year=:year and a.live_ind=true")
	Holiday getHolidayListByHolidayNameAndCountryAndYear(@Param(value="holidayName")String holidayName,@Param(value="country") String country,
			@Param(value="year") int year);
}
