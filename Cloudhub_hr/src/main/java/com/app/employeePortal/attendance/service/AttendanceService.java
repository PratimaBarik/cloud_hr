package com.app.employeePortal.attendance.service;

import java.util.List;
import java.util.Map;

import com.app.employeePortal.attendance.mapper.AttendanceDetailsMapper;
import com.app.employeePortal.attendance.mapper.OnTravelMapper;
import com.app.employeePortal.attendance.mapper.UserAvgHourWorkingPerDayDTO;
public interface AttendanceService {

	public AttendanceDetailsMapper startWork(AttendanceDetailsMapper attendanceDetailsMapper);

	public AttendanceDetailsMapper getAttendanceById(String userId);
	
	AttendanceDetailsMapper getTodayWorkDetails(String userId);

	public List<Map<String,Double>> getWorkingHoursByUserIdAndDateRange(String userId,String startDate,String endDate);

	public Object getAverageHourByUserIdAndDateRange(String userId, String startDate, String endDate);

	boolean getStartIndByUserId(String userId);

	public String updateAttendanceLocation(AttendanceDetailsMapper attendanceDetailsMapper);

	public AttendanceDetailsMapper getWorkingStatusByUserId(String userId);

	public Map getInOfficeCountByUserIdAndDateRange(String userId, String startDate, String endDate);

	public Map getOnTravelCountByUserIdAndDateRange(String userId, String startDate, String endDate);

	public Map getRemoteCountByUserIdAndDateRange(String userId, String startDate, String endDate);

	public List<AttendanceDetailsMapper> getRemoteListByUserIdAndDateRange(String userId, String startDate,
			String endDate);

	public List<AttendanceDetailsMapper> getInOfficeListByUserIdAndDateRange(String userId, String startDate,
			String endDate);

	public List<AttendanceDetailsMapper> getOnTravelListByUserIdAndDateRange(String userId, String startDate,
			String endDate);

	public List<OnTravelMapper> getOnTravelListByUserIdAndDateRangeCountryWise(String userId, String startDate,
			String endDate);

	public List<AttendanceDetailsMapper> getAllWorkingStatusByUserIdForDateRange(String userId, String startDate,
			String endDate);

	Map<String, Long> getWorkingStatusByUserIdForDateRangeWithTypeAndCountFilterData(String userId, String startDate, String endDate);

	Map<String, Long> getWorkingStatusByUserIdForDateRangeWithTypeAndCountAllData(String userId, String startDate, String endDate);

	List<AttendanceDetailsMapper> getFilterWorkingStatusByUserIdForDateRange(String userId, String startDate, String endDate);

	public UserAvgHourWorkingPerDayDTO getAverageAttendanceTimeByUserIdAndDateRange(String userId, String startDate,
			String endDate);

	public List<UserAvgHourWorkingPerDayDTO> getAverageAttendanceTimeByLocationIdAndDateRangeForAllUser(
			String locationId, String startDate, String endDate);

	//AttendanceDetailsMapper setRepairPauseResume(AttendanceDetailsMapper attendanceDetailsMapper);
}
