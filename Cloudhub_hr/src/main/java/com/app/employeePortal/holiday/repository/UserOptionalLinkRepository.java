package com.app.employeePortal.holiday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.holiday.entity.UserOptionalHolidayLink;
@Repository
public interface UserOptionalLinkRepository extends JpaRepository<UserOptionalHolidayLink, String>{

	UserOptionalHolidayLink findByUserOptionalLinkId(String id);

	List<UserOptionalHolidayLink> findByUserIdAndYearAndLiveInd(String userId, int currentYear, boolean b);

	UserOptionalHolidayLink findByUserIdAndHolidayIdAndYearAndLiveInd(String userId, String holiday_id, int year,
			boolean b);

	UserOptionalHolidayLink findByUserOptionalLinkIdAndLiveInd(String userOptionalLinkId, boolean b);
	
}
