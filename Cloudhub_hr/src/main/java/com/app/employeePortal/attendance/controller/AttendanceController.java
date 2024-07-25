package com.app.employeePortal.attendance.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.attendance.entity.AttendanceDetails;
import com.app.employeePortal.attendance.mapper.AttendanceDetailsMapper;
import com.app.employeePortal.attendance.mapper.OnTravelMapper;
import com.app.employeePortal.attendance.mapper.UserAvgHourWorkingPerDayDTO;
import com.app.employeePortal.attendance.repository.AttendanceDetailsRepository;
import com.app.employeePortal.attendance.service.AttendanceService;
import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.util.Utility;

@RestController
@CrossOrigin(maxAge = 3600)
@CacheConfig(cacheNames = { "attendance" })
public class AttendanceController {

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	AttendanceService attendanceService;

	@Autowired
	AttendanceDetailsRepository attendanceDetailsRepository;

	@PostMapping("/api/v1/attendance")
	@CacheEvict(value = "attendance", allEntries = true)
	public ResponseEntity<?> saveAttendance(@RequestBody AttendanceDetailsMapper attendanceDetailsMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			attendanceDetailsMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			attendanceDetailsMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			Date startDate = Utility.removeTime(new Date());
			if(attendanceDetailsMapper.isStartInd()) {
			AttendanceDetails dbAttendanceDetails = attendanceDetailsRepository
					.getDataBetweenDateAndUserId(attendanceDetailsMapper.getUserId(), startDate);
			if (null != dbAttendanceDetails) {
				HashMap map = new HashMap<>();
				map.put("startInd", true);
				map.put("msg", "You are already started,so you can't start again!!!");
				return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
			}else {
				AttendanceDetailsMapper id = attendanceService.startWork(attendanceDetailsMapper);
				//AttendanceDetailsMapper resultMapper = attendanceService.getTodayWorkDetails(id);
				return new ResponseEntity<AttendanceDetailsMapper>(id, HttpStatus.OK);
			}
			}else {
				AttendanceDetailsMapper id = attendanceService.startWork(attendanceDetailsMapper);
//				AttendanceDetailsMapper resultMapper = attendanceService.getTodayWorkDetails(id);
				return new ResponseEntity<AttendanceDetailsMapper>(id, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/attendance/{userId}")

	public ResponseEntity<?> getAttendanceById(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			AttendanceDetailsMapper attendanceDetailsMapper = attendanceService.getAttendanceById(userId);

			return new ResponseEntity<>(attendanceDetailsMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/attendance/getWorkingHour/{userId}")

	public ResponseEntity<?> getWorkingHourById(@PathVariable("userId") String userId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return new ResponseEntity<>(attendanceService.getWorkingHoursByUserIdAndDateRange(userId, startDate, endDate),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/attendance/getAverageHour/{userId}")

	public ResponseEntity<?> getAverageHourByUserId(@PathVariable("userId") String userId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return new ResponseEntity<>(attendanceService.getAverageHourByUserIdAndDateRange(userId, startDate, endDate),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping("/api/v1/attendance/add/location")
	public ResponseEntity<?> updateAttendanceLocation(@RequestBody AttendanceDetailsMapper attendanceDetailsMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//
//			attendanceDetailsMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
//			attendanceDetailsMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			String id = attendanceService.updateAttendanceLocation(attendanceDetailsMapper);

			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/attendance/working/status/{userId}")

	public ResponseEntity<?> getWorkingStatusByUserId(@PathVariable("userId") String userId,
			//@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			AttendanceDetailsMapper attendanceDetailsMapper = attendanceService.getWorkingStatusByUserId(userId);

			return new ResponseEntity<>(attendanceDetailsMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/attendance/work-type/inOffice/count/{userId}")
	public ResponseEntity<?> getInOfficeCountByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request){
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
				map =attendanceService.getInOfficeCountByUserIdAndDateRange(userId,startDate,endDate);
				return new ResponseEntity<>(map, HttpStatus.OK);
			
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/attendance/work-type/onTravel/count/{userId}")
	public ResponseEntity<?> getOnTravelCountByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request){
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
				map =attendanceService.getOnTravelCountByUserIdAndDateRange(userId,startDate,endDate);
				return new ResponseEntity<>(map, HttpStatus.OK);
			
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/attendance/work-type/remote/count/{userId}")
	public ResponseEntity<?> getRemoteCountByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request){
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			
				map =attendanceService.getRemoteCountByUserIdAndDateRange(userId,startDate,endDate);
				return new ResponseEntity<>(map, HttpStatus.OK);
			
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/attendance/work-type/remote/list/{userId}")
	public ResponseEntity<?> getRemoteListByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<AttendanceDetailsMapper> attendanceDetailsMapper = attendanceService.getRemoteListByUserIdAndDateRange(userId,startDate,endDate);
			if (null!=attendanceDetailsMapper) {
				 Collections.sort(attendanceDetailsMapper, ( m1, m2) -> m1.getCreationDate().compareTo(m2.getCreationDate()));
			return new ResponseEntity<>(attendanceDetailsMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(attendanceDetailsMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/attendance/work-type/inOffice/list/{userId}")
	public ResponseEntity<?> getInOfficeListByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<AttendanceDetailsMapper> attendanceDetailsMapper = attendanceService.getInOfficeListByUserIdAndDateRange(userId,startDate,endDate);
			if (null!=attendanceDetailsMapper) {
				 Collections.sort(attendanceDetailsMapper, ( m1, m2) -> m1.getCreationDate().compareTo(m2.getCreationDate()));
			return new ResponseEntity<>(attendanceDetailsMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(attendanceDetailsMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/attendance/work-type/onTravel/list/{userId}")
	public ResponseEntity<?> getOnTravelListByUserIdAndDateRange(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<AttendanceDetailsMapper> attendanceDetailsMapper = attendanceService.getOnTravelListByUserIdAndDateRange(userId,startDate,endDate);
			if (null!=attendanceDetailsMapper) {
				 Collections.sort(attendanceDetailsMapper, ( m1, m2) -> m1.getCreationDate().compareTo(m2.getCreationDate()));
			return new ResponseEntity<>(attendanceDetailsMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(attendanceDetailsMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/attendance/onTravel/list/country-wise/{userId}")
	public ResponseEntity<?> getOnTravelListByUserIdAndDateRangeCountryWise(@PathVariable("userId") String userId,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<OnTravelMapper> onTravelMapper = attendanceService.getOnTravelListByUserIdAndDateRangeCountryWise(userId,startDate,endDate);
			if (null!=onTravelMapper && !onTravelMapper.isEmpty()) {
				 Collections.sort(onTravelMapper, ( m1, m2) -> m1.getStartDate().compareTo(m2.getStartDate()));
			return new ResponseEntity<>(onTravelMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(onTravelMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/attendance/date-range/working/all-data/status/{userId}")
	public ResponseEntity<?> getAllWorkingStatusByUserIdForDateRange(@PathVariable("userId") String userId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<AttendanceDetailsMapper> attendanceDetailsMapper = attendanceService.getAllWorkingStatusByUserIdForDateRange(userId,startDate,endDate);
			if (null!=attendanceDetailsMapper && !attendanceDetailsMapper.isEmpty()) {
				Collections.sort(attendanceDetailsMapper, ( m1, m2) -> m1.getCreationDate().compareTo(m2.getCreationDate()));
				return new ResponseEntity<>(attendanceDetailsMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(attendanceDetailsMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/attendance/date-range/working/filter-data/status/{userId}")
	public ResponseEntity<?> getFilterWorkingStatusByUserIdForDateRange(@PathVariable("userId") String userId,
																	 @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
																	 @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<AttendanceDetailsMapper> attendanceDetailsMapper = attendanceService.getFilterWorkingStatusByUserIdForDateRange(userId,startDate,endDate);
			if (null!=attendanceDetailsMapper && !attendanceDetailsMapper.isEmpty()) {
				Collections.sort(attendanceDetailsMapper, ( m1, m2) -> m1.getCreationDate().compareTo(m2.getCreationDate()));
				return new ResponseEntity<>(attendanceDetailsMapper, HttpStatus.OK);
			}
			return new ResponseEntity<>(attendanceDetailsMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/attendance/date-range/working/status/type-filter/typeAndCount/{userId}")
	public ResponseEntity<?> getWorkingStatusByUserIdForDateRangeWithTypeAndCountFilterData(@PathVariable("userId") String userId,
																  @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
																  @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map<String, Long> map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			map = attendanceService.getWorkingStatusByUserIdForDateRangeWithTypeAndCountFilterData(userId,startDate,endDate);
//			if (null!=map && !map.isEmpty()) {
//				//Collections.sort(attendanceDetailsMapper, ( m1, m2) -> m1.getCreationDate().compareTo(m2.getCreationDate()));
//				return new ResponseEntity<>(map, HttpStatus.OK);
//			}
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/attendance/date-range/working/status/type-all/typeAndCount/{userId}")
	public ResponseEntity<?> getWorkingStatusByUserIdForDateRangeWithTypeAndCountAllData(@PathVariable("userId") String userId,
																							@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
																							@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map<String, Long> map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			map = attendanceService.getWorkingStatusByUserIdForDateRangeWithTypeAndCountAllData(userId,startDate,endDate);
//			if (null!=map && !map.isEmpty()) {
//				//Collections.sort(attendanceDetailsMapper, ( m1, m2) -> m1.getCreationDate().compareTo(m2.getCreationDate()));
//				return new ResponseEntity<>(map, HttpStatus.OK);
//			}
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/attendance/avgTime/date-wise/{userId}")
	public ResponseEntity<?> getAverageAttendanceTimeByUserIdAndDateRange(@PathVariable String userId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			UserAvgHourWorkingPerDayDTO resultList = attendanceService
					.getAverageAttendanceTimeByUserIdAndDateRange(userId,startDate,endDate);
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/attendance/avgTime/date-wise/all-user/{locationId}")
	public ResponseEntity<?> getAverageAttendanceTimeByLocationIdAndDateRangeForAllUser(@PathVariable String locationId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<UserAvgHourWorkingPerDayDTO> resultList = attendanceService
					.getAverageAttendanceTimeByLocationIdAndDateRangeForAllUser(locationId,startDate,endDate);
			return new ResponseEntity<>(resultList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
}
