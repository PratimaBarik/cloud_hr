package com.app.employeePortal.holiday.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.holiday.entity.Holiday;
import com.app.employeePortal.holiday.entity.UserOptionalHolidayLink;
import com.app.employeePortal.holiday.entity.Weekends;
import com.app.employeePortal.holiday.mapper.HolidayMapper;
import com.app.employeePortal.holiday.mapper.WeekendsMapper;
import com.app.employeePortal.holiday.repository.HolidayRepository;
import com.app.employeePortal.holiday.repository.UserOptionalLinkRepository;
import com.app.employeePortal.holiday.repository.WeekendsRepository;
import com.app.employeePortal.leave.entity.OrganizationLeaveRule;
import com.app.employeePortal.leave.repository.OrganizationLeaveRuleRepository;
import com.app.employeePortal.registration.entity.Country;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.app.employeePortal.util.Utility;

@Service
public class HoildayServiceImpl implements HoildayService {
	@Autowired
	HolidayRepository holidayRepository;
	@Autowired
	WeekendsRepository weekendsRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	UserOptionalLinkRepository userOptionalLinkRepository;
	@Autowired
	OrganizationLeaveRuleRepository organizationLeaveRuleRepository;

	@Override
	public HolidayMapper saveToHolidayProcess(HolidayMapper holidayMapper) {

		Holiday holiday = new Holiday();
		holiday.setCreation_date(new Date());
		try {
			holiday.setDate(Utility.getDateFromISOString(holidayMapper.getDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			holiday.setYear(Utility.getYearFromDate(Utility.getDateFromISOString(holidayMapper.getDate())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		holiday.setHoliday_name(holidayMapper.getHolidayName());
		holiday.setHoliday_type(holidayMapper.getHolidayType());
		holiday.setOrg_id(holidayMapper.getOrganizationId());
		holiday.setCountryId(holidayMapper.getCountry());
		holiday.setLive_ind(true);
		holiday.setUpdatedBy(holidayMapper.getUserId());
		holiday.setUpdationDate(new Date());
//		holiday.setOrgOptnlHoliday(holidayMapper.getOrgOptnlHoliday());
//		holiday.setUserOptnlHolidayApplied(holidayMapper.getUserOptnlHolidayApplied());

		Holiday holidayys = holidayRepository.save(holiday);

		HolidayMapper id = getHolidayRelatedDetails(holidayys.getHoliday_id());

		return id;
	}

	@Override
	public List<HolidayMapper> getHolidayDetailsListByOrganizationId(String organizationId) {
		List<Holiday> holidayList = holidayRepository.getHolidayListByOrganizationId(organizationId);

		List<HolidayMapper> mapperList = new ArrayList<HolidayMapper>();
		holidayList.stream().map(hoildayDetails -> {
			HolidayMapper holidayMapper = getHolidayRelatedDetails(hoildayDetails.getHoliday_id());
			mapperList.add(holidayMapper);

			return mapperList;
		}).collect(Collectors.toList());

		return mapperList;
	}

	@Override
	public List<HolidayMapper> getHolidayDetailsListByOrgIdAndCountryNameAndYear(String orgId, String countryName,
			int year) {
		List<HolidayMapper> mapperList = new ArrayList<HolidayMapper>();
		Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(countryName,orgId);
		if (null != country1) {
			List<Holiday> holidayList = holidayRepository.getHolidayListByOrgIdAndCountryAndYear(orgId,
					country1.getCountry_id(), year);
			if (null != holidayList && !holidayList.isEmpty()) {
				holidayList.stream().map(hoildayDetails -> {
//			HolidayMapper holidayMapper = getHolidayRelatedDetails(hoildayDetails.getHoliday_id());

					Holiday holidayeDetails = holidayRepository.getById(hoildayDetails.getHoliday_id());
					HolidayMapper holidayMapper = new HolidayMapper();

					if (null != holidayeDetails) {

						holidayMapper.setDate(Utility.getISOFromDate(holidayeDetails.getDate()));
						holidayMapper.setHolidayName(holidayeDetails.getHoliday_name());
						holidayMapper.setHolidayType(holidayeDetails.getHoliday_type());
						holidayMapper.setOrganizationId(holidayeDetails.getOrg_id());
						holidayMapper.setHolidayId(holidayeDetails.getHoliday_id());
						holidayMapper.setLiveInd(holidayeDetails.isLive_ind());
						holidayMapper.setYear(holidayeDetails.getYear());
						holidayMapper.setUserId(holidayeDetails.getUpdatedBy());
						
						Country country = countryRepository.getByCountryId(holidayeDetails.getCountryId());
						if (null != country) {
							holidayMapper.setCountry(country.getCountryName());
						}
					}
					mapperList.add(holidayMapper);

					return mapperList;
				}).collect(Collectors.toList());
			}

		}
		if (null != mapperList && !mapperList.isEmpty()) {
			Collections.sort(mapperList, (m1, m2) -> m1.getDate().compareTo(m2.getDate()));
			List<Holiday> holidayList = holidayRepository.getHolidayListByOrgIdAndCountryAndYear(orgId,
					country1.getCountry_id(), year);
			if (null != holidayList && !holidayList.isEmpty()) {
				Collections.sort(holidayList, (m1, m2) -> m1.getUpdationDate().compareTo(m2.getUpdationDate()));

				mapperList.get(0).setUpdationDate(Utility.getISOFromDate(holidayList.get(0).getUpdationDate()));
				mapperList.get(0).setUpdatedBy(employeeService.getEmployeeFullName(holidayList.get(0).getUpdatedBy()));
			}
		}
		return mapperList;
	}

	public HolidayMapper getHolidayRelatedDetails(String holidayId) {

		Holiday holidayeDetails = holidayRepository.getById(holidayId);
		HolidayMapper holidayMapper = new HolidayMapper();

		if (null != holidayeDetails) {

			holidayMapper.setDate(Utility.getISOFromDate(holidayeDetails.getDate()));
			holidayMapper.setHolidayName(holidayeDetails.getHoliday_name());
			holidayMapper.setHolidayType(holidayeDetails.getHoliday_type());
			holidayMapper.setOrganizationId(holidayeDetails.getOrg_id());
			holidayMapper.setHolidayId(holidayeDetails.getHoliday_id());
			holidayMapper.setLiveInd(holidayeDetails.isLive_ind());
			holidayMapper.setYear(holidayeDetails.getYear());
			holidayMapper.setUserId(holidayeDetails.getUpdatedBy());
//			holidayMapper.setOrgOptnlHoliday(holidayeDetails.getOrgOptnlHoliday());
//			holidayMapper.setUserOptnlHolidayApplied(holidayeDetails.getUserOptnlHolidayApplied());

			Country country1 = countryRepository.getByCountryId(holidayeDetails.getCountryId());
			if (null != country1) {
				holidayMapper.setCountry(country1.getCountryName());
			}

			holidayMapper.setUpdationDate(Utility.getISOFromDate(holidayeDetails.getUpdationDate()));
			holidayMapper.setUpdatedBy(employeeService.getEmployeeFullName(holidayeDetails.getUpdatedBy()));

		}

		return holidayMapper;
	}

	@Override
	public HolidayMapper updateHolidayDetails(HolidayMapper holidayMapper) {

		HolidayMapper resultMapper = null;
		if (null != holidayMapper.getHolidayId()) {

			Holiday holidayDetails = holidayRepository.getById(holidayMapper.getHolidayId());

			if (holidayMapper.getDate() != null) {
				try {
					holidayDetails.setDate(Utility.getDateFromISOString(holidayMapper.getDate()));
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
			if (holidayMapper.getHolidayName() != null)
				holidayDetails.setHoliday_name(holidayMapper.getHolidayName());

			if (holidayMapper.getHolidayType() != null)
				holidayDetails.setHoliday_type(holidayMapper.getHolidayType());

			holidayDetails.setUpdatedBy(holidayMapper.getUserId());
			holidayDetails.setUpdationDate(new Date());

			holidayRepository.save(holidayDetails);
		}

		resultMapper = getHolidayRelatedDetails(holidayMapper.getHolidayId());
		return resultMapper;
	}

	@Override
	public boolean deleteHoliday(String holidayId) {
		Holiday holiday = holidayRepository.getById(holidayId);

		if (holiday != null) {

			holidayRepository.delete(holiday);
			return true;

		}

		return false;
	}

	@Override
	public WeekendsMapper saveWeekends(WeekendsMapper weekendsMapper) {
		WeekendsMapper resultList = new WeekendsMapper();

		Weekends weekends = weekendsRepository.getWeekendsListByOrganizationIdAndCountryId(
				weekendsMapper.getOrganizationId(), weekendsMapper.getCountry());
		if (null != weekends) {

			weekends.setCreationDate(new Date());
			weekends.setOrgId(weekendsMapper.getOrganizationId());
			weekends.setUserId(weekendsMapper.getUserId());
			weekends.setMondayInd(weekendsMapper.isMondayInd());
			weekends.setTuesdayInd(weekendsMapper.isTuesdayInd());
			weekends.setWednesdayInd(weekendsMapper.isWednesdayInd());
			weekends.setThursdayInd(weekendsMapper.isThursdayInd());
			weekends.setFridayInd(weekendsMapper.isFridayInd());
			weekends.setSaturdayInd(weekendsMapper.isSaturdayInd());
			weekends.setSundayInd(weekendsMapper.isSundayInd());
			weekends.setCountryId(weekendsMapper.getCountry());
			weekends.setUpdatedBy(weekendsMapper.getUserId());
			weekends.setUpdationDate(new Date());

			Weekends weekends2 = weekendsRepository.save(weekends);

			resultList = getWeekendsByOrgId(weekends2.getOrgId(), weekends2.getCountryId());
		} else {
			Weekends weekend = new Weekends();

			weekend.setCreationDate(new Date());
			weekend.setOrgId(weekendsMapper.getOrganizationId());
			weekend.setUserId(weekendsMapper.getUserId());
			weekend.setMondayInd(weekendsMapper.isMondayInd());
			weekend.setTuesdayInd(weekendsMapper.isTuesdayInd());
			weekend.setWednesdayInd(weekendsMapper.isWednesdayInd());
			weekend.setThursdayInd(weekendsMapper.isThursdayInd());
			weekend.setFridayInd(weekendsMapper.isFridayInd());
			weekend.setSaturdayInd(weekendsMapper.isSaturdayInd());
			weekend.setSundayInd(weekendsMapper.isSundayInd());
			weekend.setCountryId(weekendsMapper.getCountry());
			weekend.setUpdatedBy(weekendsMapper.getUserId());
			weekend.setUpdationDate(new Date());

			Weekends weekends2 = weekendsRepository.save(weekend);

			resultList = getWeekendsByOrgId(weekends2.getOrgId(), weekends2.getCountryId());
		}

		return resultList;
	}

	@Override
	public WeekendsMapper getWeekendsByOrgId(String organizationId, String countryId) {
		WeekendsMapper weekendsMapper = new WeekendsMapper();

		Weekends weekends = weekendsRepository.getWeekendsListByOrganizationIdAndCountryId(organizationId, countryId);
		if (null != weekends) {
			weekendsMapper.setCreationDate(Utility.getISOFromDate(weekends.getCreationDate()));
			weekendsMapper.setWeekendsId(weekends.getWeekendsId());
			weekendsMapper.setOrganizationId(weekends.getOrgId());
			weekendsMapper.setUserId(weekends.getUserId());
			weekendsMapper.setMondayInd(weekends.isMondayInd());
			weekendsMapper.setTuesdayInd(weekends.isTuesdayInd());
			weekendsMapper.setWednesdayInd(weekends.isWednesdayInd());
			weekendsMapper.setThursdayInd(weekends.isThursdayInd());
			weekendsMapper.setFridayInd(weekends.isFridayInd());
			weekendsMapper.setSaturdayInd(weekends.isSaturdayInd());
			weekendsMapper.setSundayInd(weekends.isSundayInd());
			Country country1 = countryRepository.getByCountryId(weekends.getCountryId());
			if (null != country1) {
				weekendsMapper.setCountry(country1.getCountryName());
			}

			weekendsMapper.setUpdatedBy(employeeService.getEmployeeFullName(weekends.getUpdatedBy()));
			weekendsMapper.setUpdationDate(Utility.getISOFromDate(weekends.getUpdationDate()));

		}
		return weekendsMapper;
	}

	@Override
	public WeekendsMapper getWeekendsByCountryAndOrgId(String orgIdFromToken, String countryName) {
		WeekendsMapper weekendsMapper = new WeekendsMapper();
		Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(countryName,orgIdFromToken);
		if (null != country1) {
			Weekends weekends = weekendsRepository.getWeekendsListByOrganizationIdAndCountryId(orgIdFromToken,
					country1.getCountry_id());
			if (null != weekends) {
				weekendsMapper.setCreationDate(Utility.getISOFromDate(weekends.getCreationDate()));
				weekendsMapper.setWeekendsId(weekends.getWeekendsId());
				weekendsMapper.setOrganizationId(weekends.getOrgId());
				weekendsMapper.setUserId(weekends.getUserId());
				weekendsMapper.setMondayInd(weekends.isMondayInd());
				weekendsMapper.setTuesdayInd(weekends.isTuesdayInd());
				weekendsMapper.setWednesdayInd(weekends.isWednesdayInd());
				weekendsMapper.setThursdayInd(weekends.isThursdayInd());
				weekendsMapper.setFridayInd(weekends.isFridayInd());
				weekendsMapper.setSaturdayInd(weekends.isSaturdayInd());
				weekendsMapper.setSundayInd(weekends.isSundayInd());
				Country country2 = countryRepository.getByCountryId(weekends.getCountryId());
				if (null != country2) {
					weekendsMapper.setCountry(country2.getCountryName());
				}

				weekendsMapper.setUpdatedBy(employeeService.getEmployeeFullName(weekends.getUpdatedBy()));
				weekendsMapper.setUpdationDate(Utility.getISOFromDate(weekends.getUpdationDate()));
			}

		}

		return weekendsMapper;
	}

	@Override
	public boolean holidayExistsByCountryAndYear(String holidayName, String country, String date) {
		int year = 0;
		try {
			year = (Utility.getYearFromDate(Utility.getDateFromISOString(date)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Holiday holidayList = holidayRepository.getHolidayListByHolidayNameAndCountryAndYear(holidayName, country,
				year);
		if (null != holidayList) {
			return true;
		}

		return false;
	}

	@Override
	public List<HolidayMapper> getHolidaysByUserId(String orgIdFromToken, String userId) {
		List<HolidayMapper> mapperList = new ArrayList<HolidayMapper>();
		String countryName = employeeRepository.getEmployeeByUserId(userId).getWorkplace();
		String countryCode = null;
		Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(countryName,orgIdFromToken);
		if (null != countryCode) {
			countryCode = country.getCountry_id();
		}
		Date currentDate = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		// Retrieve the current year from the Calendar instance
		int currentYear = calendar.get(Calendar.YEAR);
		System.out.println(orgIdFromToken + "//" + countryCode + "//" + currentYear);
		if (null != countryCode) {
			List<Holiday> holidayList = holidayRepository.getHolidayListByOrgIdAndCountryAndYear(orgIdFromToken,
					countryCode, currentYear);
			if (null != holidayList && !holidayList.isEmpty()) {
				holidayList.stream().map(hoildayDetails -> {
					HolidayMapper holidayMapper = getHolidayRelatedDetails(hoildayDetails.getHoliday_id());
					mapperList.add(holidayMapper);

					return mapperList;
				}).collect(Collectors.toList());
				System.out.println("SIZE==" + mapperList.size());
			}
		}
		return mapperList;
	}

	@Override
	public HolidayMapper saveUserOptional(HolidayMapper holidayMapper) {
		String userOptionalLinkId = null;
		if (null != holidayMapper) {
			UserOptionalHolidayLink uop = new UserOptionalHolidayLink();
			uop.setCreationDate(new Date());
			uop.setHolidayId(holidayMapper.getHolidayId());
			uop.setLiveInd(true);
			uop.setOrgId(holidayMapper.getOrganizationId());
			uop.setUserId(holidayMapper.getUserId());
			uop.setYear(holidayMapper.getYear());
			uop.setCountryId(holidayMapper.getCountryId());
			uop.setUpdatedBy(holidayMapper.getUserId());
			uop.setUpdationDate(new Date());
			userOptionalLinkId = userOptionalLinkRepository.save(uop).getUserOptionalLinkId();
		}
		
		return getHolidayRelatedDetailsByUserOptionalLinkIdAndHolidayId(userOptionalLinkId,holidayMapper.getHolidayId());

	}

	private HolidayMapper getHolidayRelatedDetailsByUserOptionalLinkIdAndHolidayId(String userOptionalLinkId, String holidayId) {
		Holiday holidayeDetails = holidayRepository.getById(holidayId);
		HolidayMapper holidayMapper = new HolidayMapper();

		if (null != holidayeDetails) {

			holidayMapper.setDate(Utility.getISOFromDate(holidayeDetails.getDate()));
			holidayMapper.setHolidayName(holidayeDetails.getHoliday_name());
			holidayMapper.setHolidayType(holidayeDetails.getHoliday_type());
			holidayMapper.setOrganizationId(holidayeDetails.getOrg_id());
			holidayMapper.setHolidayId(holidayeDetails.getHoliday_id());
			holidayMapper.setLiveInd(holidayeDetails.isLive_ind());
			holidayMapper.setYear(holidayeDetails.getYear());
			holidayMapper.setUserId(holidayeDetails.getUpdatedBy());
//			holidayMapper.setOrgOptnlHoliday(holidayeDetails.getOrgOptnlHoliday());
//			holidayMapper.setUserOptnlHolidayApplied(holidayeDetails.getUserOptnlHolidayApplied());

			Country country1 = countryRepository.getByCountryId(holidayeDetails.getCountryId());
			if (null != country1) {
				holidayMapper.setCountry(country1.getCountryName());
				holidayMapper.setCountryId(country1.getCountry_id());
			}
			
			UserOptionalHolidayLink list = userOptionalLinkRepository
					.findByUserOptionalLinkIdAndLiveInd(userOptionalLinkId, true);
			if (null != list) {
				holidayMapper.setOptnlHolidayApplyInd(true);
				holidayMapper.setUpdationDate(Utility.getISOFromDate(list.getUpdationDate()));
				holidayMapper.setUpdatedBy(employeeService.getEmployeeFullName(list.getUpdatedBy()));
			}
			OrganizationLeaveRule optionalLeave = organizationLeaveRuleRepository
					.getOrganizationLeaveRuleDetailsByOrgIdAndCountry(holidayeDetails.getOrg_id(),
							holidayeDetails.getCountryId());
			if(null!=optionalLeave) {
				holidayMapper.setOrgOptnlHoliday(optionalLeave.getMaxOpsnlHoliday());
			}
			
			List<UserOptionalHolidayLink> list1 = userOptionalLinkRepository
					.findByUserIdAndYearAndLiveInd(holidayeDetails.getUpdatedBy(), holidayeDetails.getYear(), true);
			if (null != list1 && !list1.isEmpty()) {
				holidayMapper.setUserOptnlHolidayApplied(list1.size());
			}
		}

		return holidayMapper;
	}

	@Override
	public boolean checkOptionalholidayByUser(HolidayMapper holidayMapper) {

		Date currentDate = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		// Retrieve the current year from the Calendar instance
		int currentYear = calendar.get(Calendar.YEAR);
		List<UserOptionalHolidayLink> list = userOptionalLinkRepository
				.findByUserIdAndYearAndLiveInd(holidayMapper.getUserId(), currentYear, true);
		if (null != list) {

			OrganizationLeaveRule optionalLeave = organizationLeaveRuleRepository
					.getOrganizationLeaveRuleDetailsByOrgIdAndCountry(holidayMapper.getOrganizationId(),
							holidayMapper.getCountry());
			if (list.size() >= optionalLeave.getMaxOpsnlHoliday()) {
				return true;

			}
		}
		return false;

	}

	@Override
	public List<HolidayMapper> getHolidaysByCountryAndYearForUser(String orgId, String userId, String countryName, int year) {
		List<HolidayMapper> mapperList = new ArrayList<HolidayMapper>();
		Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(countryName,orgId);
		if (null != country1) {
			List<Holiday> holidayList = holidayRepository.getHolidayListByOrgIdAndCountryAndYear(orgId,
					country1.getCountry_id(), year);
			if (null != holidayList && !holidayList.isEmpty()) {
				holidayList.stream().map(hoildayDetails -> {
					Holiday holidayeDetails = holidayRepository.getById(hoildayDetails.getHoliday_id());
					HolidayMapper holidayMapper = new HolidayMapper();

					if (null != holidayeDetails) {

						holidayMapper.setDate(Utility.getISOFromDate(holidayeDetails.getDate()));
						holidayMapper.setHolidayName(holidayeDetails.getHoliday_name());
						holidayMapper.setHolidayType(holidayeDetails.getHoliday_type());
						holidayMapper.setOrganizationId(holidayeDetails.getOrg_id());
						holidayMapper.setHolidayId(holidayeDetails.getHoliday_id());
						holidayMapper.setLiveInd(holidayeDetails.isLive_ind());
						holidayMapper.setYear(holidayeDetails.getYear());
						holidayMapper.setUserId(holidayeDetails.getUpdatedBy());
						
						Country country = countryRepository.getByCountryId(holidayeDetails.getCountryId());
						if (null != country) {
							holidayMapper.setCountry(country.getCountryName());
							holidayMapper.setCountryId(country.getCountry_id());
						}
						
						UserOptionalHolidayLink list = userOptionalLinkRepository
								.findByUserIdAndHolidayIdAndYearAndLiveInd(userId,holidayeDetails.getHoliday_id(), year, true);
						if (null != list) {
							holidayMapper.setOptnlHolidayApplyInd(true);
						}
						
					}
					mapperList.add(holidayMapper);

					return mapperList;
				}).collect(Collectors.toList());
			}
		}
		if (null != mapperList && !mapperList.isEmpty()) {
			Collections.sort(mapperList, (m1, m2) -> m1.getDate().compareTo(m2.getDate()));
			OrganizationLeaveRule optionalLeave = organizationLeaveRuleRepository
					.getOrganizationLeaveRuleDetailsByOrgIdAndCountry(orgId,
							countryName);
			if(null!=optionalLeave) {
				mapperList.get(0).setOrgOptnlHoliday(optionalLeave.getMaxOpsnlHoliday());
			}
			
			List<UserOptionalHolidayLink> list = userOptionalLinkRepository
					.findByUserIdAndYearAndLiveInd(userId, year, true);
			if (null != list && !list.isEmpty()) {
				
				Collections.sort(list, (m1, m2) -> m1.getUpdationDate().compareTo(m2.getUpdationDate()));
				mapperList.get(0).setUserOptnlHolidayApplied(list.size());
				mapperList.get(0).setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
				mapperList.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
			}
		}
		return mapperList;
	}

}
