package com.app.employeePortal.holiday.service;

import java.util.List;

import com.app.employeePortal.holiday.mapper.HolidayMapper;
import com.app.employeePortal.holiday.mapper.WeekendsMapper;

public interface HoildayService {
	
	public HolidayMapper saveToHolidayProcess(HolidayMapper holidayMapper);
	
	public HolidayMapper getHolidayRelatedDetails(String holidayId);

	public List<HolidayMapper> getHolidayDetailsListByOrganizationId(String organizationId);
	
	public HolidayMapper updateHolidayDetails( HolidayMapper holidayMapper);

	public boolean deleteHoliday(String holidayId);

	public WeekendsMapper saveWeekends(WeekendsMapper weekendsMapper);

	public WeekendsMapper getWeekendsByOrgId(String orgId,String countryName);

	public List<HolidayMapper> getHolidayDetailsListByOrgIdAndCountryNameAndYear(String orgId,
			String countryName, int year);

	public WeekendsMapper getWeekendsByCountryAndOrgId(String orgIdFromToken, String countryName);

	public boolean holidayExistsByCountryAndYear(String holidayName, String country, String date);

	public List<HolidayMapper> getHolidaysByUserId(String orgIdFromToken, String userId);

	public HolidayMapper saveUserOptional(HolidayMapper holidayMapper);

	public boolean checkOptionalholidayByUser(HolidayMapper holidayMapper);

	public List<HolidayMapper> getHolidaysByCountryAndYearForUser(String orgIdFromToken, String userId, String countryName, int year);

		
	
}
