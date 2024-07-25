package com.app.employeePortal.attendance.service;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.Opportunity.entity.Phone;
import com.app.employeePortal.Opportunity.repository.PhoneRepository;
import com.app.employeePortal.attendance.entity.AttendanceDetails;
import com.app.employeePortal.attendance.entity.ManufactureTimes;
import com.app.employeePortal.attendance.entity.PhoneTimes;
import com.app.employeePortal.attendance.mapper.AttendanceDetailsMapper;
import com.app.employeePortal.attendance.mapper.OnTravelMapper;
import com.app.employeePortal.attendance.mapper.UserAvgHourDTO;
import com.app.employeePortal.attendance.mapper.UserAvgHourWorkingPerDayDTO;
import com.app.employeePortal.attendance.repository.AttendanceDetailsRepository;
import com.app.employeePortal.attendance.repository.ManufactureTimesRepository;
import com.app.employeePortal.attendance.repository.PhoneTimesRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	AttendanceDetailsRepository attendanceDetailsRepository;
	@Autowired
    EmployeeRepository employeeRepository;
	@Autowired
	PhoneTimesRepository phoneTimesRepository;
	@Autowired
	PhoneRepository phoneRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	ManufactureTimesRepository manufactureTimesRepository;

	@Override
	public AttendanceDetailsMapper startWork(AttendanceDetailsMapper attendanceDetailsMapper) {
		AttendanceDetailsMapper attendanceId = new AttendanceDetailsMapper();
		
		if (attendanceDetailsMapper.isStartInd()) {
			LocalTime localTime = LocalTime.now();
			AttendanceDetails newAttendanceDetails = new AttendanceDetails();
			newAttendanceDetails.setUserId(attendanceDetailsMapper.getUserId());
			newAttendanceDetails.setOrgId(attendanceDetailsMapper.getOrgId());
			newAttendanceDetails.setStartDate(Utility.removeTime(new Date()));
			newAttendanceDetails.setStartInd(true);
			newAttendanceDetails.setStartTime(localTime);
			newAttendanceDetails.setCreationDate(new Date());
			if(null!=attendanceDetailsMapper.getLocation()) {
				newAttendanceDetails.setLocation(attendanceDetailsMapper.getLocation());
			}
			if(null!=attendanceDetailsMapper.getCountry()) {
				newAttendanceDetails.setCountry(attendanceDetailsMapper.getCountry());
			}
			if(null!=attendanceDetailsMapper.getOther()) {
				newAttendanceDetails.setOther(attendanceDetailsMapper.getOther());
			}
			if(null!=attendanceDetailsMapper.getReturnDate()) {
				try {
					newAttendanceDetails.setReturnDate(Utility.getDateFromISOString(attendanceDetailsMapper.getReturnDate()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			attendanceId = getTodayWorkDetails(attendanceDetailsRepository.save(newAttendanceDetails).getId());
		} else {
			Date startDate = Utility.removeTime(new Date());
			AttendanceDetails dbAttendanceDetails = attendanceDetailsRepository
					.getDataBetweenDateAndUserId(attendanceDetailsMapper.getUserId(), startDate);
			System.out.println("New Date@@@@@@@@@@@@" + Utility.removeTime(new Date()));
			if (null != dbAttendanceDetails) {
				LocalTime localTime = LocalTime.now();
				dbAttendanceDetails.setStopTime(localTime);
				dbAttendanceDetails.setStartInd(false);
				dbAttendanceDetails.setCreationDate(new Date());
				System.out.println("start time" + dbAttendanceDetails.getStartTime());
				System.out.println();
				System.out.println("stop time" + localTime);
				Duration duration = Duration.between(dbAttendanceDetails.getStartTime(), localTime);
				DecimalFormat decimalFormat=new DecimalFormat("#.##");
				double d=Double.parseDouble(decimalFormat.format(duration.toMillis() / (60.0 * 60.0 * 1000.0)));
				dbAttendanceDetails	.setWorkingHours(d);
				System.out.println("working hours" + duration.toMillis() / (60.0 * 60.0 * 1000.0));
				attendanceId = getTodayWorkDetails(attendanceDetailsRepository.save(dbAttendanceDetails).getId());
				
				
				
			}
			setRepairPauseResume(attendanceDetailsMapper);
			
			ManufactureTimes manufactureTimes2 = manufactureTimesRepository.
					findByUserIdAndActiveAndStartInd(attendanceDetailsMapper.getUserId(), true , true);
			if(null!=manufactureTimes2 ) {
				LocalTime localTime = LocalTime.now();
				manufactureTimes2.setEndTime(localTime);
				manufactureTimes2.setModifiedBy(attendanceDetailsMapper.getUserId());
				manufactureTimes2.setModifiedAt(new Date());
				manufactureTimes2.setStartInd(false);
				manufactureTimes2.setActive(false);
				Duration duration = Duration.between(manufactureTimes2.getStartTime(), localTime);
				DecimalFormat decimalFormat = new DecimalFormat("#.##");
				double d = Double.parseDouble(decimalFormat.format(duration.toMillis() / (60.0 * 60.0 * 1000.0)));
				manufactureTimes2.setWorkingDuration(d);
				manufactureTimesRepository.save(manufactureTimes2);
			}
			
			
		}
		return attendanceId;
	}
	
	
	public void setRepairPauseResume(AttendanceDetailsMapper attendanceDetailsMapper) {
		
//		List<PhoneTimes> phoneTimesList = phoneTimesRepository
//				.findByUserIdAndResumeInd(attendanceDetailsMapper.getUserId(), true);
//		System.out.println("phone times size..."+phoneTimesList.size());
//		if (phoneTimesList.size() > 0) {
//			for (PhoneTimes phoneTimes : phoneTimesList) {
//				LocalTime localTime = LocalTime.now();
//				phoneTimes.setUserId(attendanceDetailsMapper.getUserId());
//				phoneTimes.setPauseInd(true);
//				phoneTimes.setPauseTime(localTime);
//				phoneTimes.setResumeInd(false);
//				phoneTimesRepository.save(phoneTimes);
//			}
//		}
		Phone phn = phoneRepository.findByUserIdAndActiveStatus(attendanceDetailsMapper.getUserId(),"Active");
		if(null != phn) {
			PhoneTimes phoneTimes1 = phoneTimesRepository.getTheLatestPhoneTime(phn.getId());
			if(phoneTimes1!=null) {
				if(phoneTimes1.isPauseInd()==false) {
					LocalTime localTime = LocalTime.now();
					PhoneTimes phoneTimes = new PhoneTimes();
					phoneTimes.setUserId(attendanceDetailsMapper.getUserId());
					phoneTimes.setPhoneId(phn.getId());
					phoneTimes.setActive(true);
					phoneTimes.setPauseInd(true);
					phoneTimes.setPauseStartTime(localTime);
					phoneTimes.setCreateAt(new Date());
					phoneTimesRepository.save(phoneTimes);
				}
			}else {
				LocalTime localTime = LocalTime.now();
				PhoneTimes phoneTimes = new PhoneTimes();
				phoneTimes.setUserId(attendanceDetailsMapper.getUserId());
				phoneTimes.setPhoneId(phn.getId());
				phoneTimes.setActive(true);
				phoneTimes.setPauseInd(true);
				phoneTimes.setPauseStartTime(localTime);
				phoneTimes.setCreateAt(new Date());
				phoneTimesRepository.save(phoneTimes);
			}
		}
		
	}
	

	@Override
	public AttendanceDetailsMapper getAttendanceById(String userId) {
		AttendanceDetailsMapper resultMapper = new AttendanceDetailsMapper();
		if (!StringUtils.isEmpty(userId)) {
				AttendanceDetails dbAttendanceDetails =null;
				Date startDate = Utility.removeTime(new Date());
//				Date startDate =Utility.removeTime(Utility.getBeforeDate(startDate1));
				Date endDate = Utility.removeTime(Utility.getDateAfterEndDate(startDate));
				System.out.println("startDate===================="+startDate);
				List<AttendanceDetails> list = attendanceDetailsRepository.
						getcurrentAttendanceByUserId(userId,startDate,endDate);
				System.out.println("list=="+list.toString());
				System.out.println("====================");
				if(null!=list && !list.isEmpty()) {
				Collections.sort(list,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
				 dbAttendanceDetails = list.get(0);
				 System.out.println("dbAttendanceDetails=="+dbAttendanceDetails.toString());
				}
				if (null != dbAttendanceDetails) {
					resultMapper.setStartInd(dbAttendanceDetails.isStartInd()); 
					resultMapper.setAttendanceId(dbAttendanceDetails.getId());
					resultMapper.setLocation(dbAttendanceDetails.getLocation());
					resultMapper.setCountry(dbAttendanceDetails.getCountry());
					resultMapper.setOther(dbAttendanceDetails.getOther());
					resultMapper.setStartTime(dbAttendanceDetails.getStartTime());
					resultMapper.setStopTime(dbAttendanceDetails.getStopTime());
					resultMapper.setReturnDate(Utility.getISOFromDate(dbAttendanceDetails.getReturnDate()));
					
					if (null!=dbAttendanceDetails.getReturnDate()) {
					Date beforeReturnDate = Utility.removeTime(Utility.getBeforeDate(dbAttendanceDetails.getReturnDate(),1));
					if(startDate.compareTo(beforeReturnDate) == 0) {
						resultMapper.setSmsInd(true);
						}
					}
				}
		}
		return resultMapper;

	}

	@Override
	public AttendanceDetailsMapper getTodayWorkDetails(String attendanceId) {
		AttendanceDetailsMapper resultMapper = new AttendanceDetailsMapper();
		if (!StringUtils.isEmpty(attendanceId)) {
			AttendanceDetails dbAttendanceDetails = attendanceDetailsRepository.getById(attendanceId);
			if (null != dbAttendanceDetails) {

				resultMapper.setStartDate(Utility.getISOFromDate(dbAttendanceDetails.getStartDate()));
				resultMapper.setCreationDate(Utility.getISOFromDate(dbAttendanceDetails.getCreationDate()));
				resultMapper.setStartTime(dbAttendanceDetails.getStartTime());
				resultMapper.setStopTime(dbAttendanceDetails.getStopTime());
				resultMapper.setStartInd(dbAttendanceDetails.isStartInd());
				resultMapper.setAttendanceId(attendanceId);
				resultMapper.setUserId(dbAttendanceDetails.getUserId());
				resultMapper.setCountry(dbAttendanceDetails.getCountry());
				resultMapper.setLocation(dbAttendanceDetails.getLocation());
				resultMapper.setOther(dbAttendanceDetails.getOther());
				resultMapper.setReturnDate(Utility.getISOFromDate(dbAttendanceDetails.getReturnDate()));
				resultMapper.setWorkingHours(dbAttendanceDetails.getWorkingHours());
				
				Date todayDate = Utility.removeTime(new Date());
				if (null!=dbAttendanceDetails.getReturnDate()) {
				Date beforeReturnDate = Utility.removeTime(Utility.getBeforeDate(dbAttendanceDetails.getReturnDate(),1));
				if(todayDate.compareTo(beforeReturnDate) == 0) {
					resultMapper.setSmsInd(true);
				}
				}
			}
		}
		return resultMapper;

	}

	@Override
	public List< Map<String, Double>> getWorkingHoursByUserIdAndDateRange(String userId, String startDate, String endDate) {
		List<Map<String,Double>> map=new ArrayList<>();

		Date startDate1 = null;
		Date endDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int daysDifference = 0;
		Date temp_date = startDate1;
		while (endDate1.after(temp_date)) {
			daysDifference++;
			temp_date = new Date(temp_date.getTime() + 24 * 60 * 60 * 1000);
		}
		if (daysDifference == 0) {
			daysDifference++;
		}
		
//		long differenceInMillis = endDate1.getTime() -
//				startDate1.getTime();
        // Convert milliseconds to days
//        long daysDifference = TimeUnit.MILLISECONDS.toDays(differenceInMillis) + 1;
System.out.println("Days  "+daysDifference);
		for(int i=0;i<daysDifference;i++) {
			Date startDate2=startDate1;
			Date endDate2=Utility.getDateAfterEndDate(startDate1);

			Map map1=new HashMap<>();
			List<AttendanceDetails> attendanceDetails= attendanceDetailsRepository.getBetweenDatesAndUserId(userId,startDate2,endDate2);

			double hours=0;
			for(AttendanceDetails attendanceDetails2:attendanceDetails) {
				hours=hours+attendanceDetails2.getWorkingHours();
			}
			DecimalFormat decimalFormat=new DecimalFormat("#.##");
			int c=i;
			map1.put("name",++c);
			map1.put("Date",startDate1);
			map1.put("hours", Double.parseDouble(decimalFormat.format(hours)));
			map.add(map1);
			startDate1=Utility.getPlusDate(startDate1, 1);
		}
		return map;
	
	}

	@Override
	public Object getAverageHourByUserIdAndDateRange(String userId, String startDate, String endDate) {
		Map<String,Double> map=new HashMap<>();
		Date startDate1 = null;
		Date endDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int daysDifference = 0;
		Date temp_date = startDate1;
		while (endDate1.after(temp_date)) {
			daysDifference++;
			temp_date = new Date(temp_date.getTime() + 24 * 60 * 60 * 1000);
		}
		if (daysDifference == 0) {
			daysDifference++;
		}
System.out.println("Days  "+daysDifference);
double hours=0;
		for(int i=0;i<daysDifference;i++) {
			Date startDate2=startDate1;
			Date endDate2=Utility.getDateAfterEndDate(startDate1);
			List<AttendanceDetails> attendanceDetails= attendanceDetailsRepository.getBetweenDatesAndUserId(userId,startDate2,endDate2);
			for(AttendanceDetails attendanceDetails2:attendanceDetails) {
				hours=hours+attendanceDetails2.getWorkingHours();
			}
			startDate1=Utility.getPlusDate(startDate1, 1);
		}
		DecimalFormat decimalFormat=new DecimalFormat("#.##");
		System.out.println("hours "+hours);
		map.put("hours", Double.parseDouble(decimalFormat.format(hours/daysDifference)));
		return map;
	}

	@Override
	public boolean getStartIndByUserId(String userId) {
		boolean startInd = false;
	
		if (!StringUtils.isEmpty(userId)) {
			AttendanceDetails dbAttendanceDetails =null;
			Date startDate = Utility.removeTime(new Date());
//			Date startDate =Utility.removeTime(Utility.getBeforeDate(startDate1));
			Date endDate = Utility.removeTime(Utility.getDateAfterEndDate(startDate));
			System.out.println("startDate===================="+startDate);
			List<AttendanceDetails> list = attendanceDetailsRepository.
					getcurrentAttendanceByUserId(userId,startDate,endDate);
			System.out.println("list=="+list.toString());
			System.out.println("====================");
			if(null!=list && !list.isEmpty()) {
			Collections.sort(list,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
			 dbAttendanceDetails = list.get(0);
			 System.out.println("dbAttendanceDetails=="+dbAttendanceDetails.toString());
			}
			if (null != dbAttendanceDetails) {
				startInd = dbAttendanceDetails.isStartInd();
			}
		}
		return startInd;
	}

	@Override
	public String updateAttendanceLocation(AttendanceDetailsMapper attendanceDetailsMapper) {
		String id = null;
		AttendanceDetails dbAttendanceDetails = attendanceDetailsRepository.getById(attendanceDetailsMapper.getAttendanceId());
		if (null != dbAttendanceDetails) {
			if(null!=attendanceDetailsMapper.getLocation()) {
				dbAttendanceDetails.setLocation(attendanceDetailsMapper.getLocation());
			}
			if(null!=attendanceDetailsMapper.getCountry()) {
				dbAttendanceDetails.setCountry(attendanceDetailsMapper.getCountry());
			}
			if(null!=attendanceDetailsMapper.getOther()) {
				dbAttendanceDetails.setOther(attendanceDetailsMapper.getOther());
			}
			if(null!=attendanceDetailsMapper.getReturnDate()) {
				try {
					dbAttendanceDetails.setReturnDate(Utility.getDateFromISOString(attendanceDetailsMapper.getReturnDate()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			id = attendanceDetailsRepository.save(dbAttendanceDetails).getId();
		}
		return id;
	}

	@Override
	public AttendanceDetailsMapper getWorkingStatusByUserId(String userId) {
		
		AttendanceDetailsMapper resultMapper = new AttendanceDetailsMapper();
		
		Date todayDate = new Date();
		Date startDate1 = null;
		Date endDate1 = null;
		try {
			startDate1 = Utility.removeTime(todayDate);
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(todayDate));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<AttendanceDetails> list= attendanceDetailsRepository.getcurrentAttendanceByUserId(userId,startDate1,endDate1);
		if(null!=list && !list.isEmpty()) {
			Collections.sort(list,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
			AttendanceDetails attendanceDetails = list.get(0);
			if(null!=attendanceDetails) {
				resultMapper = getTodayWorkDetails(attendanceDetails.getId());
			}
		}
		
		
		return resultMapper;
	}
	
	@Scheduled(cron = "0 10 23 * * *")
	public void autoStartForUser() {
		System.out.println("ss schedular started");
		
		Date todayDate = new Date();
		Date startDate1 = null;
		Date endDate1 = null;
		try {
			startDate1 = Utility.removeTime(todayDate);
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(todayDate));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Date startDate2 = startDate1;
		Date endDate2 = endDate1;
		
		String beforeDate = Utility.getMinusDate(todayDate,1);
		Date startDate3 = null;
		Date endDate3 = null;
		try {
			startDate3 = Utility.removeTime(Utility.getDateFromISOString(beforeDate));
			endDate3 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(beforeDate)));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Date startDate4 = startDate3;
		Date endDate4 = endDate3;
		
		 List<EmployeeDetails> employeeList = employeeRepository.getEmployeesByLiveIndAndSuspendInd();
	        System.out.println("###########" + employeeList.toString());
	        if (null != employeeList && !employeeList.isEmpty()) {
	            employeeList.stream().map(employee -> {
	            
	            	List<AttendanceDetails> list= attendanceDetailsRepository.getcurrentAttendanceByUserId(employee.getEmployeeId(),startDate2,endDate2);
	        		if(list.isEmpty()) {
	        			List<AttendanceDetails> list1= attendanceDetailsRepository.getcurrentAttendanceByUserId(employee.getEmployeeId(),startDate4,endDate4);
	        			if(null != list1 && !list1.isEmpty()) {
	        				Collections.sort(list1,(v1,v2)->v2.getCreationDate().compareTo(v1.getCreationDate()));
	        				AttendanceDetails attendanceDetails = list1.get(0);
	        				if(null!=attendanceDetails  && null!=attendanceDetails.getReturnDate()) {
	        					if(attendanceDetails.getReturnDate().compareTo(todayDate) > 0 || attendanceDetails.getReturnDate().compareTo(todayDate) == 0) {
	        						System.out.println("attendanceDetails.getReturnDate()" +attendanceDetails.getReturnDate() );
	        						AttendanceDetails newAttendanceDetails = new AttendanceDetails();
	        						newAttendanceDetails.setUserId(attendanceDetails.getUserId());
	        						newAttendanceDetails.setOrgId(attendanceDetails.getOrgId());
	        						newAttendanceDetails.setStartDate(Utility.removeTime(new Date()));
	        						newAttendanceDetails.setStartInd(false);
	        						newAttendanceDetails.setStartTime(LocalTime.of(9, 0, 0));
	        						newAttendanceDetails.setStopTime(LocalTime.of(17, 0, 0));
	        						newAttendanceDetails.setCreationDate(new Date());
	        						newAttendanceDetails.setLocation(attendanceDetails.getLocation());
	        						newAttendanceDetails.setCountry(attendanceDetails.getCountry());
	        						newAttendanceDetails.setOther(attendanceDetails.getOther());
	        						newAttendanceDetails.setReturnDate(attendanceDetails.getReturnDate());
	        						Duration duration = Duration.between(LocalTime.of(9, 0, 0), LocalTime.of(17, 0, 0));
	        						DecimalFormat decimalFormat=new DecimalFormat("#.##");
	        						double d=Double.parseDouble(decimalFormat.format(duration.toMillis() / (60.0 * 60.0 * 1000.0)));
	        						newAttendanceDetails.setWorkingHours(d);
	        						System.out.println("working hours" + duration.toMillis() / (60.0 * 60.0 * 1000.0));
	        						String id = attendanceDetailsRepository.save(newAttendanceDetails).getId();
	        						System.out.println("id" +id );
	        					}
	        				}
	        			}
	        		}
	        		 return list;
	            }).collect(Collectors.toList());
	        }
		
	}

	@Override
	public Map getInOfficeCountByUserIdAndDateRange(String userId, String startDate, String endDate) {
		Date end_date =null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("leadsList>>>" + userId);
		System.out.println("leadsList>>>" + start_date);
		System.out.println("leadsList>>>" + end_date);
		
		
		HashMap map = new HashMap();
		List<AttendanceDetails> list= attendanceDetailsRepository.getcurrentAttendanceByUserIdAndLocation(userId,"In Office",start_date,end_date);
		
		List<AttendanceDetails> list1 = new ArrayList<AttendanceDetails>();
		HashMap<Date,Integer> map1 = new HashMap<Date,Integer>();
		for(AttendanceDetails attendanceDetails : list) {
			
			if(!map1.containsKey(Utility.removeTime(attendanceDetails.getCreationDate()))) {
				System.out.println("Utility.removeTime(attendanceDetails.getCreationDate())========="+Utility.removeTime(attendanceDetails.getCreationDate()));
				map1.put(Utility.removeTime(attendanceDetails.getCreationDate()), 1);
				list1.add(attendanceDetails);
			}
		}
		
		map.put("inOfficeList", list1.size());
		return map;
	}

	@Override
	public Map getOnTravelCountByUserIdAndDateRange(String userId, String startDate, String endDate) {
		Date end_date =null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("leadsList>>>" + userId);
		System.out.println("leadsList>>>" + start_date);
		System.out.println("leadsList>>>" + end_date);
		
		
		HashMap map = new HashMap();
		List<AttendanceDetails> list= attendanceDetailsRepository.getcurrentAttendanceByUserIdAndLocation(userId,"On Travel",start_date,end_date);
		
		List<AttendanceDetails> list1 = new ArrayList<AttendanceDetails>();
		HashMap<Date,Integer> map1 = new HashMap<Date,Integer>();
		for(AttendanceDetails attendanceDetails : list) {
			
			if(!map1.containsKey(Utility.removeTime(attendanceDetails.getCreationDate()))) {
				System.out.println("Utility.removeTime(attendanceDetails.getCreationDate())========="+Utility.removeTime(attendanceDetails.getCreationDate()));
				map1.put(Utility.removeTime(attendanceDetails.getCreationDate()), 1);
				list1.add(attendanceDetails);
			}
		}
		
		map.put("onTravelList", list1.size());
		return map;
	}

	@Override
	public Map getRemoteCountByUserIdAndDateRange(String userId, String startDate, String endDate) {
		Date end_date =null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("leadsList>>>" + userId);
		System.out.println("leadsList>>>" + start_date);
		System.out.println("leadsList>>>" + end_date);
		
		
		HashMap map = new HashMap();
		List<AttendanceDetails> list= attendanceDetailsRepository.getcurrentAttendanceByUserIdAndLocation(userId,"Remote",start_date,end_date);
		
		List<AttendanceDetails> list1 = new ArrayList<AttendanceDetails>();
		HashMap<Date,Integer> map1 = new HashMap<Date,Integer>();
		for(AttendanceDetails attendanceDetails : list) {
			
			if(!map1.containsKey(Utility.removeTime(attendanceDetails.getCreationDate()))) {
				System.out.println("Utility.removeTime(attendanceDetails.getCreationDate())========="+Utility.removeTime(attendanceDetails.getCreationDate()));
				map1.put(Utility.removeTime(attendanceDetails.getCreationDate()), 1);
				list1.add(attendanceDetails);
			}
		}
		
		map.put("remoteList", list1.size());
		return map;
	}

	@Override
	public List<AttendanceDetailsMapper> getRemoteListByUserIdAndDateRange(String userId, String startDate,
			String endDate) {
		
		List<AttendanceDetailsMapper> resultList = new ArrayList<>();
		
		Date end_date =null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("leadsList>>>" + userId);
		System.out.println("leadsList>>>" + start_date);
		System.out.println("leadsList>>>" + end_date);
		
		List<AttendanceDetails> list= attendanceDetailsRepository.getcurrentAttendanceByUserIdAndLocation(userId,"Remote",start_date,end_date);
		if(null!=list && !list.isEmpty()) {
			  list.stream().map(attendanceDetails -> {
				  
				  AttendanceDetailsMapper mapper = getTodayWorkDetails(attendanceDetails.getId());
				  resultList.add(mapper);
				  return mapper;
			  }).collect(Collectors.toList());
		}
		
		return resultList;
	}

	@Override
	public List<AttendanceDetailsMapper> getInOfficeListByUserIdAndDateRange(String userId, String startDate,
			String endDate) {
List<AttendanceDetailsMapper> resultList = new ArrayList<>();
		
		Date end_date =null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("leadsList>>>" + userId);
		System.out.println("leadsList>>>" + start_date);
		System.out.println("leadsList>>>" + end_date);
		
		List<AttendanceDetails> list= attendanceDetailsRepository.getcurrentAttendanceByUserIdAndLocation(userId,"In Office",start_date,end_date);
		if(null!=list && !list.isEmpty()) {
			  list.stream().map(attendanceDetails -> {
				  
				  AttendanceDetailsMapper mapper = getTodayWorkDetails(attendanceDetails.getId());
				  resultList.add(mapper);
				  return mapper;
			  }).collect(Collectors.toList());
		}
		
		return resultList;
	}

	@Override
	public List<AttendanceDetailsMapper> getOnTravelListByUserIdAndDateRange(String userId, String startDate,
			String endDate) {
List<AttendanceDetailsMapper> resultList = new ArrayList<>();
		
		Date end_date =null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("leadsList>>>" + userId);
		System.out.println("leadsList>>>" + start_date);
		System.out.println("leadsList>>>" + end_date);
		
		List<AttendanceDetails> list= attendanceDetailsRepository.getcurrentAttendanceByUserIdAndLocation(userId,"On Travel",start_date,end_date);
		if(null!=list && !list.isEmpty()) {
			  list.stream().map(attendanceDetails -> {
				  
				  AttendanceDetailsMapper mapper = getTodayWorkDetails(attendanceDetails.getId());
				  resultList.add(mapper);
				  return mapper;
			  }).collect(Collectors.toList());
		}
		
		return resultList;
	}

	@Override
	public List<OnTravelMapper> getOnTravelListByUserIdAndDateRangeCountryWise(String userId, String startDate,
			String endDate) {
		List<OnTravelMapper> resultLists = new ArrayList<>();
		
		
		Date end_date =null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("userId>>>" + userId);
		System.out.println("start_date>>>" + start_date);
		System.out.println("end_date>>>" + end_date);
		
		List<AttendanceDetails> list= attendanceDetailsRepository.getcurrentAttendanceByUserIdAndLocation(userId,"On Travel",start_date,end_date);
		System.out.println("list.size()0>>>" + list.size());
		if(null!=list && !list.isEmpty()) {
			System.out.println("list.size()>>>" + list.size());
			List<String> list1 =list.stream().map(s->{
				if(s.getCountry().equalsIgnoreCase("Other")) {
				return s.getOther();
				}else {
					return s.getCountry();
				}
			}).distinct().collect(Collectors.toList());
			
			for(String country : list1) {
				List<AttendanceDetailsMapper> resultList = new ArrayList<>();
				list.stream().map(s->{
					if(s.getCountry().equalsIgnoreCase("Other")) {
						if(s.getOther().equalsIgnoreCase(country)) {
							AttendanceDetailsMapper mapper = getTodayWorkDetails(s.getId());
							  resultList.add(mapper);
							  return mapper;
						}
						}else {
							if(s.getCountry().equalsIgnoreCase(country)) {
								AttendanceDetailsMapper mapper = getTodayWorkDetails(s.getId());
								  resultList.add(mapper);
								  return mapper;
							}
							
						}
					return null;
				}).collect(Collectors.toList());
				
				Date end_date1 =null;
				Date start_date1 = null;
				
				OnTravelMapper onTravelMapper = new OnTravelMapper();
				 Collections.sort(resultList, ( m1, m2) -> m1.getCreationDate().compareTo(m2.getCreationDate()));
				 try {
					start_date1 = Utility.removeTime(Utility.getDateFromISOString(resultList.get(0).getCreationDate()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 onTravelMapper.setStartDate(resultList.get(0).getCreationDate());
				 Collections.sort(resultList, ( m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				 try {
					end_date1 = Utility.removeTime(Utility.getDateFromISOString(resultList.get(0).getReturnDate()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 onTravelMapper.setEndDate(resultList.get(0).getReturnDate());
				 int daysDifference = 0;
					Date temp_date = start_date1;
					while (end_date1.after(temp_date)) {
						daysDifference++;
						temp_date = new Date(temp_date.getTime() + 24 * 60 * 60 * 1000);
					}
					if (daysDifference == 0) {
						daysDifference++;
					}
					 onTravelMapper.setNoOfDays(daysDifference);
					 onTravelMapper.setLocation(resultList.get(0).getLocation());
					 onTravelMapper.setCountry(country);
					 onTravelMapper.setUserId(userId);
				 
				resultLists.add(onTravelMapper);
			}
			
		}	
		
		
		
		return resultLists;
	}

	@Override
	public List<AttendanceDetailsMapper> getAllWorkingStatusByUserIdForDateRange(String userId, String startDate,
			String endDate) {
		List<AttendanceDetailsMapper> resultMapper = new ArrayList<>();

		Date endDate1 =null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<AttendanceDetails> list= attendanceDetailsRepository.getcurrentAttendanceByUserId(userId,startDate1,endDate1);
		if(null!=list && !list.isEmpty()) {
			list.stream()
					.filter(li -> li.getLocation()!=null)
					.map(li-> {
				AttendanceDetailsMapper mapper = getTodayWorkDetails(li.getId());
				if(null!=mapper) {
					resultMapper.add(mapper);
				}
					return mapper;
				}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public List<AttendanceDetailsMapper> getFilterWorkingStatusByUserIdForDateRange(String userId, String startDate, String endDate) {
		List<AttendanceDetailsMapper> resultMapper = new ArrayList<>();

		Date endDate1 =null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<AttendanceDetails> list= attendanceDetailsRepository.getcurrentAttendanceByUserId(userId,startDate1,endDate1);
		if(null!=list && !list.isEmpty()) {
			Set<AttendanceDetails> uniqueResults = list.stream()
					.filter(li -> li.getLocation()!=null)
					.collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(AttendanceDetails::getStartDate ).thenComparing(AttendanceDetails::getLocation))));
			if(null!=uniqueResults) {
				uniqueResults.stream()
						.map(li -> {
							AttendanceDetailsMapper mapper = getTodayWorkDetails(li.getId());
							if (null != mapper) {
								resultMapper.add(mapper);
							}
							return mapper;
						}).collect(Collectors.toList());
			}
		}
		return resultMapper;
	}

	@Override
	public Map<String, Long> getWorkingStatusByUserIdForDateRangeWithTypeAndCountFilterData(String userId, String startDate, String endDate) {
		Date endDate1 =null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Long> attendanceTypeCounts = new HashMap<>();
		List<AttendanceDetails> list= attendanceDetailsRepository.getcurrentAttendanceByUserId(userId,startDate1,endDate1);
		if(null!=list && !list.isEmpty()) {
			Set<AttendanceDetails> uniqueResults = list.stream()
					.filter(li -> li.getLocation()!=null)
					.collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(AttendanceDetails::getStartDate ).thenComparing(AttendanceDetails::getLocation))));
			if(null!=uniqueResults) {
				attendanceTypeCounts= uniqueResults.stream().collect(Collectors.groupingBy(AttendanceDetails::getLocation, Collectors.counting()));
			}
		}
		return attendanceTypeCounts;
	}

	@Override
	public Map<String, Long> getWorkingStatusByUserIdForDateRangeWithTypeAndCountAllData(String userId, String startDate, String endDate) {
		Date endDate1 =null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Long> attendanceTypeCounts = new HashMap<>();
		List<AttendanceDetails> list= attendanceDetailsRepository.getcurrentAttendanceByUserId(userId,startDate1,endDate1);
		if(null!=list && !list.isEmpty()) {
//			Set<AttendanceDetails> uniqueResults = list.stream()
//					.filter(li -> li.getLocation()!=null)
//					.collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(AttendanceDetails::getStartDate ).thenComparing(AttendanceDetails::getLocation))));
//			if(null!=uniqueResults) {
				attendanceTypeCounts= list.stream()
						.filter(li -> li.getLocation()!=null)
						.collect(Collectors.groupingBy(AttendanceDetails::getLocation, Collectors.counting()));
//			}
		}
		return attendanceTypeCounts;
	}


	@Override
	public UserAvgHourWorkingPerDayDTO getAverageAttendanceTimeByUserIdAndDateRange(String userId, String startDate,
			String endDate) {
		UserAvgHourWorkingPerDayDTO resultDto = new UserAvgHourWorkingPerDayDTO();
		System.out.println("startDate"+startDate);
		System.out.println("endDate"+endDate);
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.removeTime(Utility.getDateFromISOString(endDate));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("start_date"+start_date);
		System.out.println("end_date"+end_date);
		System.out.println("userId"+userId);
		 List<Date> dates = Utility.getDatesBetweenByDateClass(start_date, end_date);
		 System.out.println("dates.size()"+dates.size());
		 if(null!=dates && !dates.isEmpty()) {
			 List<UserAvgHourDTO> listDto = new ArrayList<>();
			 for (Date date : dates) {
				 System.out.println("date"+date);
				 Date date1 = Utility.getDateAfterEndDate(date);
				 List<AttendanceDetails> manufactureTimes = attendanceDetailsRepository
							.findByUserIdAndCreationDateBetween(userId, date, date1);
				 UserAvgHourDTO dto = new UserAvgHourDTO();
					if (manufactureTimes != null && !manufactureTimes.isEmpty()) {
						
						double total = manufactureTimes.stream().mapToDouble(AttendanceDetails::getWorkingHours).sum();
						
						dto.setTotalTimeSpend(total);

						String str = Double.toString(total);
						String arr[] = str.split("\\.");
						float hours = Float.parseFloat(arr[0]);
						float mins = (float) ((total-hours) * 60);

						dto.setTotalTimeTakenInHours(hours);
						dto.setTotalTimeTakenInMinutes(mins);
						
						resultDto.setOrgId(manufactureTimes.get(0).getOrgId());
					}
					dto.setDate(Utility.getISOFromDate(date));
					listDto.add(dto);
					
			}
			 resultDto.setDto(listDto);
			 resultDto.setUserId(userId);
			 resultDto.setUserName(employeeService.getEmployeeFullName(userId));
		 }
		
		return resultDto;
	}


	@Override
	public List<UserAvgHourWorkingPerDayDTO> getAverageAttendanceTimeByLocationIdAndDateRangeForAllUser(
			String locationId, String startDate, String endDate) {
		List<UserAvgHourWorkingPerDayDTO> resultList = new ArrayList<>();
		
		System.out.println("startDate"+startDate);
		System.out.println("endDate"+endDate);
		
		 List<EmployeeDetails> employeeList = employeeRepository.getEmployeeListByLocationId(locationId);
	        System.out.println("employeeList.size()###########" + employeeList.size());
	        if (null != employeeList && !employeeList.isEmpty()) {
	        	employeeList.stream().map(employee -> {
	        		UserAvgHourWorkingPerDayDTO resultDto = getAverageAttendanceTimeByUserIdAndDateRange(employee.getUserId(), startDate, endDate);
	                if(null!=resultDto) {
	                	resultList.add(resultDto);
	                }
	        		return resultDto;
	            }).collect(Collectors.toList());
	        }
		return resultList;
	}

}
