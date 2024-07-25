package com.app.employeePortal.holiday.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.holiday.mapper.HolidayMapper;
import com.app.employeePortal.holiday.mapper.WeekendsMapper;
import com.app.employeePortal.holiday.service.HoildayService;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/v1")
public class HolidayController {

	@Autowired
	HoildayService holidayService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/holiday")
	public ResponseEntity<?> saveHoliday(@RequestBody HolidayMapper holidayMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		Map map = new HashMap();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			holidayMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			holidayMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));

			if (!StringUtils.isEmpty(holidayMapper.getHolidayName())) {
				boolean c = holidayService.holidayExistsByCountryAndYear(holidayMapper.getHolidayName(),
						holidayMapper.getCountry(), holidayMapper.getDate());
				if (c == true) {
					map.put("holidayInd", true);
					map.put("message", "holiday with same name already exists.");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					HolidayMapper Id = holidayService.saveToHolidayProcess(holidayMapper);
					return new ResponseEntity<>(Id, HttpStatus.OK);
				}
			} else {
				map.put("message", " please provide holiday name  !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/holiday/{holidayId}")

	public ResponseEntity<?> getHolidayById(@PathVariable("holidayId") String holidayId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			HolidayMapper holidayMapper = holidayService.getHolidayRelatedDetails(holidayId);
			return new ResponseEntity<>(holidayMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/holidays")

	public ResponseEntity<?> getHolidaysByOrgId(HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<HolidayMapper> holidayList = holidayService
					.getHolidayDetailsListByOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			Collections.sort(holidayList, (HolidayMapper m1, HolidayMapper m2) -> m1.getDate().compareTo(m2.getDate()));
			return new ResponseEntity<>(holidayList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/holidays/{countryName}/{year}")

	public ResponseEntity<?> getHolidaysByCountryAndYear(HttpServletRequest request,
			@PathVariable("countryName") String countryName, @PathVariable("year") int year,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<HolidayMapper> holidayList = holidayService.getHolidayDetailsListByOrgIdAndCountryNameAndYear(
					jwtTokenUtil.getOrgIdFromToken(authToken), countryName, year);
			if (null != holidayList && !holidayList.isEmpty()) {
				Collections.sort(holidayList,
						(HolidayMapper m1, HolidayMapper m2) -> m1.getDate().compareTo(m2.getDate()));
				return new ResponseEntity<>(holidayList, HttpStatus.OK);
			}
			return new ResponseEntity<>(holidayList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/holiday")
	public ResponseEntity<?> updateMileage(@RequestBody HolidayMapper holidayMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			holidayMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			holidayMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			HolidayMapper resultMapper = holidayService.updateHolidayDetails(holidayMapper);

			return new ResponseEntity<HolidayMapper>(resultMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/holiday/{holidayId}")
	public ResponseEntity<?> deleteHoliday(@PathVariable("holidayId") String holidayId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			boolean b = holidayService.deleteHoliday(holidayId);
			return new ResponseEntity<>(b, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/holiday/weekends/save")
	public ResponseEntity<?> saveWeekends(@RequestBody WeekendsMapper weekendsMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			weekendsMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			weekendsMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			WeekendsMapper Id = holidayService.saveWeekends(weekendsMapper);

			return new ResponseEntity<>(Id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	@GetMapping("/holiday/weekends/{orgId}")
//	public ResponseEntity<?> getWeekendsByOrgId(@PathVariable("orgId")String orgId,
//	                                        HttpServletRequest request,
//			                                @RequestHeader("Authorization") String authorization){
//			                    
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			WeekendsMapper weekendsMapper = holidayService.getWeekendsByOrgId(orgId);
//        return new ResponseEntity<>(weekendsMapper, HttpStatus.OK);
//       }
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}

	@GetMapping("/holiday/weekends/{countryName}")

	public ResponseEntity<?> getWeekendsByCountryAndOrgId(HttpServletRequest request,
			@PathVariable("countryName") String countryName, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			WeekendsMapper weekendsList = holidayService
					.getWeekendsByCountryAndOrgId(jwtTokenUtil.getOrgIdFromToken(authToken), countryName);

			return new ResponseEntity<>(weekendsList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/holidays/planner/{userId}")
	public ResponseEntity<?> getHolidaysByUserId(HttpServletRequest request, @PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<HolidayMapper> holidayList = holidayService
					.getHolidaysByUserId(jwtTokenUtil.getOrgIdFromToken(authToken), userId);
			if (null != holidayList && !holidayList.isEmpty()) {
				Collections.sort(holidayList,
						(HolidayMapper m1, HolidayMapper m2) -> m1.getDate().compareTo(m2.getDate()));
				return new ResponseEntity<>(holidayList, HttpStatus.OK);
			}
			return new ResponseEntity<>(holidayList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/holiday/user/apply/optional/holiday")
	public ResponseEntity<?> saveUserOptional(@RequestBody HolidayMapper holidayMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			holidayMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			holidayMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			Map map = new HashMap();
			if (!StringUtils.isEmpty(holidayMapper.getHolidayId())) {
				boolean c = holidayService.checkOptionalholidayByUser(holidayMapper);
				if (c == true) {
					map.put("message", "optional Holiday Exceeded");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					HolidayMapper Id = holidayService.saveUserOptional(holidayMapper);
					return new ResponseEntity<>(Id, HttpStatus.OK);
				}
			}else {
				map.put("message", "Please Provide HolidayId");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/holidays/user/{countryName}/{year}")
	public ResponseEntity<?> getHolidaysByCountryAndYearForUser(HttpServletRequest request,
			@PathVariable("countryName") String countryName, @PathVariable("year") int year,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<HolidayMapper> holidayList = holidayService.getHolidaysByCountryAndYearForUser(
					jwtTokenUtil.getOrgIdFromToken(authToken), jwtTokenUtil.getUserIdFromToken(authToken), countryName, year);
			if (null != holidayList && !holidayList.isEmpty()) {
				Collections.sort(holidayList,
						(HolidayMapper m1, HolidayMapper m2) -> m1.getDate().compareTo(m2.getDate()));
				return new ResponseEntity<>(holidayList, HttpStatus.OK);
			}
			return new ResponseEntity<>(holidayList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

}
