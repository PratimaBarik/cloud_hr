package com.app.employeePortal.leave.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.email.service.EmailService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.Notes;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.NotesRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.holiday.entity.Holiday;
import com.app.employeePortal.holiday.repository.HolidayRepository;
import com.app.employeePortal.leave.entity.LeaveDetails;
import com.app.employeePortal.leave.entity.LeaveInfo;
import com.app.employeePortal.leave.entity.LeaveNotesLink;
import com.app.employeePortal.leave.entity.OrganizationLeaveRule;
import com.app.employeePortal.leave.mapper.EmployeeLeaveMapper;
import com.app.employeePortal.leave.mapper.LeaveBalanceMapper;
import com.app.employeePortal.leave.mapper.LeavesMapper;
import com.app.employeePortal.leave.mapper.OrganizationLeaveRuleMapper;
import com.app.employeePortal.leave.repository.LeaveDetailsRepository;
import com.app.employeePortal.leave.repository.LeaveInfoRepository;
import com.app.employeePortal.leave.repository.LeaveNotesLinkRepository;
import com.app.employeePortal.leave.repository.OrganizationLeaveRuleRepository;
import com.app.employeePortal.mileage.entity.MileageRate;
import com.app.employeePortal.mileage.repository.MileageRateRepository;
import com.app.employeePortal.mileage.service.MileageService;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.processApproval.entity.ApprovalLevel;
import com.app.employeePortal.processApproval.repository.ApprovalLevelRepository;
import com.app.employeePortal.processApproval.service.ProcessApprovalService;
import com.app.employeePortal.registration.entity.Country;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.app.employeePortal.task.entity.EmployeeTaskLink;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.entity.TaskInfo;
import com.app.employeePortal.task.entity.TaskNotesLink;
import com.app.employeePortal.task.repository.EmployeeTaskRepository;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskInfoRepository;
import com.app.employeePortal.task.repository.TaskNotesLinkRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class LeaveServiceImpl implements LeaveService {
	@Autowired
	OrganizationLeaveRuleRepository organizationLeaveRuleRepository;

	@Autowired
	LeaveInfoRepository leaveInfoRepository;

	@Autowired
	LeaveDetailsRepository leaveDetailsRepository;

	@Autowired
	TaskInfoRepository taskInfoRepository;

	@Autowired
	TaskDetailsRepository taskDetailsRepository;

	@Autowired
	EmployeeTaskRepository employeeTaskRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	EmployeeService empService;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	HolidayRepository holidayRepository;
	@Autowired
	EmailService emailService;
	@Autowired
	LeaveService leaveService;
	@Autowired
	ProcessApprovalService processApprovalService;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	MileageRateRepository mileageRateRepository;
	@Autowired
	MileageService mileageService;
	@Autowired
	LeaveNotesLinkRepository leaveNotesLinkRepository;
	@Autowired
	NotesRepository notesRepository;
	@Autowired
	ApprovalLevelRepository approvalLevelRepository;
	@Autowired
	TaskNotesLinkRepository taskNotesLinkRepository;
	@Override
	public OrganizationLeaveRuleMapper saveToOrganizationLeaveRuleProcess(
			OrganizationLeaveRuleMapper organizationLeaveRuleMapper) {

		OrganizationLeaveRule organizationLeaveRuleDetails = organizationLeaveRuleRepository
				.getOrganizationLeaveRuleDetailsByOrgIdAndCountry(organizationLeaveRuleMapper.getOrganizationId(),
						organizationLeaveRuleMapper.getCountry());

		if (null != organizationLeaveRuleDetails) {
			organizationLeaveRuleDetails.setCarry_forward(organizationLeaveRuleMapper.getCarryForward());
			organizationLeaveRuleDetails.setCreation_date(new Date());
			organizationLeaveRuleDetails
					.setLeavesCappedTimesAnnualy(organizationLeaveRuleMapper.getLeavesCappedTimesAnnualy());
			organizationLeaveRuleDetails.setMaximum_leaves(organizationLeaveRuleMapper.getMaximumLeaves());
			organizationLeaveRuleDetails.setOrg_id(organizationLeaveRuleMapper.getOrganizationId());
			organizationLeaveRuleDetails.setLive_ind(true);
			organizationLeaveRuleDetails.setCountry(organizationLeaveRuleMapper.getCountry());
			organizationLeaveRuleDetails.setUpdationDate(new Date());
			organizationLeaveRuleDetails.setUpdatedBy(organizationLeaveRuleMapper.getUserId());
			organizationLeaveRuleDetails.setMaxOpsnlHoliday(organizationLeaveRuleMapper.getMaxOpsnlHoliday());
			try {
				organizationLeaveRuleDetails.setCarryForwardEffectiveDate(
						Utility.getDateFromISOString(organizationLeaveRuleMapper.getCarryForwardEffectiveDate()));
				organizationLeaveRuleDetails.setLeavesCappedTimesAnnualyEffectiveDate(Utility
						.getDateFromISOString(organizationLeaveRuleMapper.getLeavesCappedTimesAnnualyEffectiveDate()));
				organizationLeaveRuleDetails.setMaximumLeavesEffectiveDate(
						Utility.getDateFromISOString(organizationLeaveRuleMapper.getMaximumLeavesEffectiveDate()));
				organizationLeaveRuleDetails.setMileageRateEffectiveDate(
						Utility.getDateFromISOString(organizationLeaveRuleMapper.getMileageRateEffectiveDate()));

			} catch (Exception e) {
				e.printStackTrace();
			}
			OrganizationLeaveRule rule = organizationLeaveRuleRepository.save(organizationLeaveRuleDetails);

			String milageRateId = mileageService.saveMileageRateByCountry(organizationLeaveRuleMapper.getCountry(),
					organizationLeaveRuleMapper.getMileageRate(), organizationLeaveRuleMapper.getUserId(),
					organizationLeaveRuleMapper.getOrganizationId());

			OrganizationLeaveRuleMapper resultMapper = getLeaveRuleDetailsByLeaveRuleId(
					rule.getOrganization_leave_rule_id(), milageRateId);
			return resultMapper;
		} else {
			OrganizationLeaveRule organizationLeaveRule = new OrganizationLeaveRule();
			organizationLeaveRule.setCarry_forward(organizationLeaveRuleMapper.getCarryForward());
			organizationLeaveRule.setCreation_date(new Date());
			organizationLeaveRule
					.setLeavesCappedTimesAnnualy(organizationLeaveRuleMapper.getLeavesCappedTimesAnnualy());
			organizationLeaveRule.setMaximum_leaves(organizationLeaveRuleMapper.getMaximumLeaves());
			organizationLeaveRule.setOrg_id(organizationLeaveRuleMapper.getOrganizationId());
			organizationLeaveRule.setLive_ind(true);
			organizationLeaveRule.setCountry(organizationLeaveRuleMapper.getCountry());
			organizationLeaveRule.setUpdationDate(new Date());
			organizationLeaveRule.setUpdatedBy(organizationLeaveRuleMapper.getUserId());
			organizationLeaveRule.setMaxOpsnlHoliday(organizationLeaveRuleMapper.getMaxOpsnlHoliday());
			try {
				organizationLeaveRule.setCarryForwardEffectiveDate(
						Utility.getDateFromISOString(organizationLeaveRuleMapper.getCarryForwardEffectiveDate()));
				organizationLeaveRule.setLeavesCappedTimesAnnualyEffectiveDate(Utility
						.getDateFromISOString(organizationLeaveRuleMapper.getLeavesCappedTimesAnnualyEffectiveDate()));
				organizationLeaveRule.setMaximumLeavesEffectiveDate(
						Utility.getDateFromISOString(organizationLeaveRuleMapper.getMaximumLeavesEffectiveDate()));
				organizationLeaveRule.setMileageRateEffectiveDate(
						Utility.getDateFromISOString(organizationLeaveRuleMapper.getMileageRateEffectiveDate()));

			} catch (Exception e) {
				e.printStackTrace();
			}
			OrganizationLeaveRule rule = organizationLeaveRuleRepository.save(organizationLeaveRule);

			String milageRateId = mileageService.saveMileageRateByCountry(organizationLeaveRuleMapper.getCountry(),
					organizationLeaveRuleMapper.getMileageRate(), organizationLeaveRuleMapper.getUserId(),
					organizationLeaveRuleMapper.getOrganizationId());

			OrganizationLeaveRuleMapper resultMapper = getLeaveRuleDetailsByLeaveRuleId(
					rule.getOrganization_leave_rule_id(), milageRateId);
			return resultMapper;
		}

	}

	private OrganizationLeaveRuleMapper getLeaveRuleDetailsByLeaveRuleId(String organization_leave_rule_id,
			String milageRateId) {
		OrganizationLeaveRuleMapper organizationLeaveRuleMapper = new OrganizationLeaveRuleMapper();
		OrganizationLeaveRule organizationLeaveRuleDetails = organizationLeaveRuleRepository
				.getById(organization_leave_rule_id);
		if (null != organizationLeaveRuleDetails) {
			organizationLeaveRuleMapper.setCarryForward(organizationLeaveRuleDetails.getCarry_forward());
			organizationLeaveRuleMapper
					.setLeavesCappedTimesAnnualy(organizationLeaveRuleDetails.getLeavesCappedTimesAnnualy());
			organizationLeaveRuleMapper.setMaximumLeaves(organizationLeaveRuleDetails.getMaximum_leaves());
			organizationLeaveRuleMapper.setOrganizationId(organizationLeaveRuleDetails.getOrg_id());
			organizationLeaveRuleMapper
					.setOrganizationLeaveRuleId(organizationLeaveRuleDetails.getOrganization_leave_rule_id());
			organizationLeaveRuleMapper
					.setCreationDate(Utility.getISOFromDate(organizationLeaveRuleDetails.getCreation_date()));
			organizationLeaveRuleMapper
					.setUpdationDate(Utility.getISOFromDate(organizationLeaveRuleDetails.getUpdationDate()));
			organizationLeaveRuleMapper.setLiveInd(organizationLeaveRuleDetails.isLive_ind());
			organizationLeaveRuleMapper.setUserId(organizationLeaveRuleDetails.getUpdatedBy());
			organizationLeaveRuleMapper.setCarryForwardEffectiveDate(
					Utility.getISOFromDate(organizationLeaveRuleDetails.getCarryForwardEffectiveDate()));
			organizationLeaveRuleMapper.setLeavesCappedTimesAnnualyEffectiveDate(
					Utility.getISOFromDate(organizationLeaveRuleDetails.getLeavesCappedTimesAnnualyEffectiveDate()));
			organizationLeaveRuleMapper.setMaximumLeavesEffectiveDate(
					Utility.getISOFromDate(organizationLeaveRuleDetails.getMaximumLeavesEffectiveDate()));
			organizationLeaveRuleMapper.setMileageRateEffectiveDate(
					Utility.getISOFromDate(organizationLeaveRuleDetails.getMileageRateEffectiveDate()));
			organizationLeaveRuleMapper.setMaxOpsnlHoliday(organizationLeaveRuleDetails.getMaxOpsnlHoliday());
			Country country1 = countryRepository.getByCountryId(organizationLeaveRuleDetails.getCountry());
			if (null != country1) {
				organizationLeaveRuleMapper.setCountry(country1.getCountryName());
			}
			organizationLeaveRuleMapper
					.setUpdatedBy(employeeService.getEmployeeFullName(organizationLeaveRuleDetails.getUpdatedBy()));

			MileageRate ratee = mileageRateRepository.getMileageDetailsByMileageRateId(milageRateId);
			if (ratee != null) {
				organizationLeaveRuleMapper.setMileageRate(ratee.getMileageRate());
			}

		}
		return organizationLeaveRuleMapper;
	}

	@Override
	public OrganizationLeaveRuleMapper getOrganizationLeaveRuleDetails(String orgId, String country) {
		OrganizationLeaveRule organizationLeaveRuleDetails = organizationLeaveRuleRepository
				.getOrganizationLeaveRuleDetailsByOrgIdAndCountry(orgId, country);
		OrganizationLeaveRuleMapper organizationLeaveRuleMapper = new OrganizationLeaveRuleMapper();

		if (null != organizationLeaveRuleDetails) {
			String id = null;
			MileageRate ratee = mileageRateRepository.getMileageDetailsByCountryAndOrgId(country, orgId);
			if (ratee != null) {
				id = ratee.getMileageRateId();
			}
			organizationLeaveRuleMapper = getLeaveRuleDetailsByLeaveRuleId(
					organizationLeaveRuleDetails.getOrganization_leave_rule_id(), id);
		}
		return organizationLeaveRuleMapper;
	}

//	@Override
//	public OrganizationLeaveRuleMapper updateOrganizationLeaveRuleDetails(
//			OrganizationLeaveRuleMapper organizationLeaveRuleMapper) {
//		OrganizationLeaveRule organizationLeaveRule = organizationLeaveRuleRepository.getOrganizationLeaveRuleDetailsByOrgIdAndCountry(organizationLeaveRuleMapper.getOrganizationId(),organizationLeaveRuleMapper.getCountry());
//		
//		
//   	 if(0 != organizationLeaveRuleMapper.getCarryForward())
//   		organizationLeaveRule.setCarry_forward(organizationLeaveRuleMapper.getCarryForward());
//   	 
//   	if(0 != organizationLeaveRuleMapper.getLeavesCappedTimesAnnualy())
//   		organizationLeaveRule.setLeavesCappedTimesAnnualy(organizationLeaveRuleMapper.getLeavesCappedTimesAnnualy());
//   	
//	if(0 != organizationLeaveRuleMapper.getMaximumLeaves())
//   		organizationLeaveRule.setMaximum_leaves(organizationLeaveRuleMapper.getMaximumLeaves());
//   	  	
//   	organizationLeaveRuleRepository.save(organizationLeaveRule);  	
//    	     	 
//   	 OrganizationLeaveRuleMapper  resultMapper = getLeaveRuleDetailsByLeaveRuleId(organizationLeaveRuleMapper.getOrganizationLeaveRuleId());		 	 
//   	 	return resultMapper;
//
//	}

	@Override
	public String saveToLeaveProcess(LeavesMapper leavesMapper) throws Exception {
		String leaveId = null;

		LeaveInfo leaveInfo = new LeaveInfo();
		leaveInfo.setCreation_date(new Date());
		LeaveInfo info = leaveInfoRepository.save(leaveInfo);
		leaveId = info.getLeave_id();

		if (null != leaveId) {

			/* insert to task info */
			TaskInfo taskInfo = new TaskInfo();
			taskInfo.setCreation_date(new Date());
			TaskInfo task1 = taskInfoRepository.save(taskInfo);

			String taskId = task1.getTask_id();

			if (null != taskId) {

				/* insert to task details table */

				TaskDetails taskDetails = new TaskDetails();
				taskDetails.setTask_id(taskId);
				taskDetails.setCreation_date(new Date());
				taskDetails.setTask_description(leavesMapper.getReason());
				taskDetails.setPriority("Medium");
				taskDetails.setTask_type("leave");
				taskDetails.setTask_name(leavesMapper.getCoverDetails());
				taskDetails.setTask_status("To Start");
				taskDetails.setUser_id(leavesMapper.getEmployeeId());
				taskDetails.setOrganization_id(leavesMapper.getOrgId());
				taskDetails.setLiveInd(true);
				// taskDetails.setCompletion_ind(false);
				try {
					taskDetails.setStart_date(Utility.getDateFromISOString(leavesMapper.getStartDate()));
					taskDetails.setEnd_date(Utility.getDateFromISOString(leavesMapper.getEndDate()));

				} catch (Exception e) {
					e.printStackTrace();
				}

				// taskDetailsRepository.save(taskDetails);

				/* insert to employee task link table */
				processApprovalService.ProcessApprove(leavesMapper.getEmployeeId(), "Leave", taskDetails);

//			EmployeeDetails details = employeeRepository.getEmployeeDetailsByEmployeeId(leavesMapper.getEmployeeId(),true);
//			
//			String hrId = empService.getHREmployeeId(leavesMapper.getOrgId());
//			String adminId = empService.getAdminIdByOrgId(leavesMapper.getOrgId());
//			String reportingManagerId = null;
//			
//			if(null!=details) {
//				if(!StringUtils.isEmpty(details.getReportingManager())) {
//					reportingManagerId=details.getReportingManager();
//				}else if(StringUtils.isEmpty(details.getReportingManager())&& !StringUtils.isEmpty(hrId)) {
//					reportingManagerId=hrId;
//					
//				}else if(StringUtils.isEmpty(hrId)) {
//					reportingManagerId=adminId;
//				}
//				
//			  }
//					EmployeeTaskLink employeeTaskLink = new EmployeeTaskLink();
//					employeeTaskLink.setTask_id(taskId);
//					employeeTaskLink.setCreation_date(new Date());
//					employeeTaskLink.setLive_ind(true);
//				    employeeTaskLink.setEmployee_id(reportingManagerId);
//				
//				
//					employeeTaskRepository.save(employeeTaskLink);	
//					

				LeaveDetails leaveDetails = new LeaveDetails();
				leaveDetails.setLeave_id(leaveId);
				leaveDetails.setCover_details(leavesMapper.getCoverDetails());
				leaveDetails.setCreation_date(new Date());
				leaveDetails.setEmployee_id(leavesMapper.getEmployeeId());
				leaveDetails.setEnd_date(Utility.getDateFromISOString(leavesMapper.getEndDate()));
				leaveDetails.setReason(leavesMapper.getReason());
				leaveDetails.setStart_date(Utility.getDateFromISOString(leavesMapper.getStartDate()));
				leaveDetails.setLive_ind(true);
				leaveDetails.setOrg_id(leavesMapper.getOrgId());
				leaveDetails.setStatus("Pending");
				leaveDetails.setTask_id(taskId);
				leaveDetails.setHalfDayInd(leavesMapper.isHalfDayInd());
				leaveDetails.setSelfOtherInd(leavesMapper.isSelfOtherInd());
				leaveDetails.setOtherUser(leavesMapper.getOtherUser());
				if (leavesMapper.isHalfDayInd()) {
					leaveDetails.setEnd_date(Utility.getDateFromISOString(leavesMapper.getStartDate()));
					leaveDetails.setHalfDayType(leavesMapper.isHalfDayType());
				} else {
					if (Utility.removeTime(Utility.getDateFromISOString(leavesMapper.getEndDate()))
							.after(Utility.removeTime(Utility.getDateFromISOString(leavesMapper.getStartDate())))) {
						leaveDetails.setEnd_date(Utility.getDateFromISOString(leavesMapper.getEndDate()));
					} else {
						leaveDetails.setEnd_date(Utility.getDateFromISOString(leavesMapper.getStartDate()));
					}
				}
				leaveDetailsRepository.save(leaveDetails);

			}
			Notes notes = new Notes();
			notes.setCreation_date(new Date());
			notes.setNotes(leavesMapper.getReason());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);

			String notesId = note.getNotes_id();
			/* insert to LeaveNoteLink table */

			LeaveNotesLink leaveNote = new LeaveNotesLink();
			leaveNote.setLeaveId(leaveId);
			leaveNote.setNoteId(notesId);
			leaveNote.setCreationDate(new Date());
			leaveNotesLinkRepository.save(leaveNote);
			
			/* insert to TaskNoteLink table */
			TaskNotesLink taskNote = new TaskNotesLink();
			taskNote.setTaskId(taskId);
			taskNote.setNotesId(notesId);
			taskNote.setCreationDate(new Date());
			taskNotesLinkRepository.save(taskNote);
			

		}

		return leaveId;
	}

	@Override
	public LeavesMapper getLeaveDetails(String leaveId) {
		LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByLeaveId(leaveId);
		LeavesMapper leavesMapper = new LeavesMapper();

		if (null != leaveDetails) {
			leavesMapper.setCoverDetails(leaveDetails.getCover_details());
			leavesMapper.setEmployeeId(leaveDetails.getEmployee_id());
			leavesMapper.setEndDate(Utility.getISOFromDate(leaveDetails.getEnd_date()));
			leavesMapper.setLeaveId(leaveDetails.getLeave_id());
			leavesMapper.setReason(leaveDetails.getReason());
			leavesMapper.setStartDate(Utility.getISOFromDate(leaveDetails.getStart_date()));
			leavesMapper.setOrgId(leaveDetails.getOrg_id());
			leavesMapper.setStatus(leaveDetails.getStatus());
			leavesMapper.setCreationDate(Utility.getISOFromDate(leaveDetails.getCreation_date()));
			leavesMapper.setRejectReason(leaveDetails.getRejectReason());
			leavesMapper.setSelfOtherInd(leaveDetails.isSelfOtherInd());
			leavesMapper.setOtherUser(leaveDetails.getOtherUser());
			leavesMapper.setName(employeeService.getEmployeeFullName(leaveDetails.getEmployee_id()));
		}

		return leavesMapper;
	}

	@Override
	public LeavesMapper getLeaveDetailsMapper(LeaveDetails leaveDetails) {
//		LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByLeaveId(leaveId);
		LeavesMapper leavesMapper = new LeavesMapper();

		if (null != leaveDetails) {
			leavesMapper.setCoverDetails(leaveDetails.getCover_details());
			leavesMapper.setEmployeeId(leaveDetails.getEmployee_id());
			leavesMapper.setEndDate(Utility.getISOFromDate(leaveDetails.getEnd_date()));
			leavesMapper.setLeaveId(leaveDetails.getLeave_id());
			leavesMapper.setReason(leaveDetails.getReason());
			leavesMapper.setStartDate(Utility.getISOFromDate(leaveDetails.getStart_date()));
			leavesMapper.setOrgId(leaveDetails.getOrg_id());
			leavesMapper.setStatus(leaveDetails.getStatus());
			leavesMapper.setCreationDate(Utility.getISOFromDate(leaveDetails.getCreation_date()));
			leavesMapper.setRejectReason(leaveDetails.getRejectReason());
			leavesMapper.setHalfDayInd(leaveDetails.isHalfDayInd());
			leavesMapper.setHalfDayType(leaveDetails.isHalfDayType());
			leavesMapper.setName(employeeService.getEmployeeFullName(leaveDetails.getEmployee_id()));
			leavesMapper.setSelfOtherInd(leaveDetails.isSelfOtherInd());
			leavesMapper.setOtherUser(leaveDetails.getOtherUser());
		}

		return leavesMapper;
	}

	@Override
	public LeavesMapper updateLeaveDetails(LeavesMapper leavesMapper) throws Exception {
		LeavesMapper resultMapper = null;

		if (null != leavesMapper.getLeaveId()) {

			LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByLeaveId(leavesMapper.getLeaveId());

			if (null != leavesMapper.getCoverDetails())
				leaveDetails.setCover_details(leavesMapper.getCoverDetails());

			if (null != leavesMapper.getReason()) {

				List<LeaveNotesLink> list = leaveNotesLinkRepository.getNotesIdByLeaveId(leavesMapper.getLeaveId());
				if (null != list && !list.isEmpty()) {
					list.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
					Notes notes = notesRepository.findByNoteId(list.get(0).getNoteId());
					if (null != notes) {
						notes.setNotes(leavesMapper.getReason());
						notesRepository.save(notes);
					}
				}
			}

			if (null != leavesMapper.getStartDate())
				leaveDetails.setStart_date(Utility.getDateFromISOString(leavesMapper.getStartDate()));
			if (null != leavesMapper.getStatus())
				leaveDetails.setStatus(leavesMapper.getStatus());
			leaveDetails.setHalfDayInd(leavesMapper.isHalfDayInd());
			if (leavesMapper.isHalfDayInd()) {
				leaveDetails.setEnd_date(Utility.getDateFromISOString(leavesMapper.getStartDate()));
				leaveDetails.setHalfDayType(leavesMapper.isHalfDayType());

			} else {
				if (null != leavesMapper.getEndDate()) {
					if (Utility.removeTime(Utility.getDateFromISOString(leavesMapper.getEndDate()))
							.after(Utility.removeTime(Utility.getDateFromISOString(leavesMapper.getStartDate())))) {
						leaveDetails.setEnd_date(Utility.getDateFromISOString(leavesMapper.getEndDate()));
					} else {
						leaveDetails.setEnd_date(Utility.getDateFromISOString(leavesMapper.getStartDate()));
					}
				}
			}

			leaveDetails.setOrg_id(leavesMapper.getOrgId());

			leaveDetailsRepository.save(leaveDetails);

			TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(leaveDetails.getLeave_id());
			if (null != taskDetails) {
				taskDetails.setTask_description(leavesMapper.getReason());
				taskDetails.setTask_name(leavesMapper.getCoverDetails());
				taskDetailsRepository.save(taskDetails);
				List<TaskNotesLink> list = taskNotesLinkRepository.getNotesIdByTaskId(taskDetails.getTask_id());
				if (null != list && !list.isEmpty()) {
					list.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
					Notes notes = notesRepository.findByNoteId(list.get(0).getNotesId());
					if (null != notes) {
						notes.setNotes(leavesMapper.getReason());
						notesRepository.save(notes);
					}
				}
				
			}

		}
		resultMapper = getLeaveDetails(leavesMapper.getLeaveId());

		return resultMapper;
	}

	@Override
	public List<LeavesMapper> getEmployeeLeavesList(String employeeId) {
		List<LeavesMapper> resultList = new ArrayList<>();
		List<LeaveDetails> list = leaveDetailsRepository.getLeaveListByEmpId(employeeId);
		if (null != list && !list.isEmpty()) {
			resultList = list.stream().map(leaveDetails -> {
				LeavesMapper leavesMapper = getLeaveDetailsMapper(leaveDetails);

				List<EmployeeTaskLink> taskLinkList = employeeTaskRepository
						.getEmpListByTaskId(leaveDetails.getTask_id());
				int levelCount = 0;
				if (null != taskLinkList && !taskLinkList.isEmpty()) {
					ApprovalLevel approvalLevel = approvalLevelRepository
							.getApprovalLevelDetail(taskLinkList.get(0).getSubProcessApprovalId());
					if (approvalLevel != null) {
						levelCount = approvalLevel.getLevelCount();
//				  System.out.println("approvalLevel=="+approvalLevel.getId());
					}
				}
				long appCount = 0;
//				 if(approvalLevel.getApprovalType().equalsIgnoreCase("standard")) {
//				 System.out.println("taskLinkList=="+taskLinkList.size());
				if (null != taskLinkList && !taskLinkList.isEmpty()) {
					appCount = taskLinkList.stream()
							.filter(link -> link != null && "approved".equalsIgnoreCase(link.getApproveStatus()))
							.count();

				}
//				 if (taskLinkList.stream().anyMatch(link -> link.getApproveStatus().equalsIgnoreCase("approved"))) {
//			            leavesMapper.setStatus("approved");
//			        } else {
//			        	leavesMapper.setStatus("pending");
//			        }
				String status = (appCount > 0) ? "approved" : "pending";
				leavesMapper.setStatus(status);
				leavesMapper.setApprovalScore(appCount + "/" + levelCount);
				return leavesMapper;
			}).collect(Collectors.toList());
		}

		return resultList;
	}

	@Override
	public List<OrganizationLeaveRuleMapper> getOrganizationLeaveRuleDetailsByOrgId(String orgId) {
		List<OrganizationLeaveRule> organizationLeaveRuleList = organizationLeaveRuleRepository
				.getOrganizationLeaveRuleByOrgId(orgId);
		if (null != organizationLeaveRuleList && !organizationLeaveRuleList.isEmpty()) {
			return organizationLeaveRuleList.stream().map(orgLeaveRule -> {
				String id = null;
				MileageRate ratee = mileageRateRepository.getMileageDetailsByCountryAndOrgId(orgLeaveRule.getCountry(),
						orgId);
				if (ratee != null) {
					id = ratee.getMileageRateId();
				}
				OrganizationLeaveRuleMapper organizationLeaveRuleMapper = getLeaveRuleDetailsByLeaveRuleId(
						orgLeaveRule.getOrganization_leave_rule_id(), id);
				return organizationLeaveRuleMapper;
			}).collect(Collectors.toList());
		}
		return null;

	}

	@Override
	public LeaveBalanceMapper calculateLeaveBalanceByEmpId(String employeeId) {

		EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(employeeId, true);
		int max_leaves = 0;
		int approved_leaves = 0;
		int pending_leaves = 0;
		int rejected_leaves = 0;
		int total_applied_leaves = 0;
		int leave_balance = 0;
		String employee_name = null;
		String imageId = null;
		if (null != employeeDetails) {
			String orgId = employeeDetails.getOrgId();
			System.out.println("employeeDetails.getOrgId()+++++++++++++" + employeeDetails.getEmailId());
			String country = employeeDetails.getWorkplace();
			System.out.println("employeeDetails.getWorkplace()+++++++++++++" + employeeDetails.getWorkplace());
			Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(country,employeeDetails.getOrgId());
			if (null != country1) {
				OrganizationLeaveRule organizationLeaveRule = organizationLeaveRuleRepository
						.getOrganizationLeaveRuleDetailsByOrgIdAndCountry(orgId, country1.getCountry_id());
				System.out.println("country1.getcountryId++++++++2nd+++++" + country1.getCountry_id());
				if (null != organizationLeaveRule) {
					System.out.println("organizationLeaveRule.getMaximum_leaves()++++++++1st+++++"
							+ organizationLeaveRule.getMaximum_leaves());
					System.out.println("max_leaves++++++++2nd+++++" + max_leaves);
					max_leaves = organizationLeaveRule.getMaximum_leaves();

				}
			}
			if (!StringUtils.isEmpty(employeeDetails.getMiddleName())) {
				employee_name = employeeDetails.getFirstName() + " " + employeeDetails.getMiddleName() + " "
						+ employeeDetails.getLastName();

			} else {
				employee_name = employeeDetails.getFirstName() + " " + employeeDetails.getLastName();

			}
			imageId = employeeDetails.getImageId();
		}

		List<LeaveDetails> approvedLeaves = leaveDetailsRepository.getLeaveListByStatus(employeeId, "Approved");
		for (LeaveDetails leaveDetails : approvedLeaves) {
			if (leaveDetails.getEnd_date().after(leaveDetails.getStart_date())) {
				long differenceInMillis = Utility.removeTime(leaveDetails.getEnd_date()).getTime()
						- Utility.removeTime(leaveDetails.getStart_date()).getTime();
				// Convert milliseconds to days
				long daysDifference = TimeUnit.MILLISECONDS.toDays(differenceInMillis) + 1;

				for (int i = 0; i < daysDifference; i++) {
					long dayInMillis = TimeUnit.DAYS.toMillis(i);
					Date currentDate = new Date(leaveDetails.getStart_date().getTime() + dayInMillis);
					if (!confirmWeekend(Utility.removeTime(currentDate))) {
						approved_leaves++;
					}
				}
			} else {
				if (!confirmWeekend(Utility.removeTime(leaveDetails.getEnd_date()))) {
					approved_leaves++;
				}
			}
		}

		List<LeaveDetails> rejectedLeaves = leaveDetailsRepository.getLeaveListByStatus(employeeId, "Rejected");
		List<LeaveDetails> pendingLeaves = leaveDetailsRepository.getLeaveListByStatus(employeeId, "Pending");
//		if(null!=approvedLeaves && !approvedLeaves.isEmpty()) {
//			approved_leaves = approvedLeaves.size();
//		}
		if (null != rejectedLeaves && !rejectedLeaves.isEmpty()) {
			rejected_leaves = rejectedLeaves.size();
		}

		if (null != pendingLeaves && !pendingLeaves.isEmpty()) {
			pending_leaves = pendingLeaves.size();
		}

		System.out.println("max_leaves++++++++last+++++" + max_leaves);
		total_applied_leaves = approved_leaves + rejected_leaves + pending_leaves;
		leave_balance = max_leaves - approved_leaves;
		LeaveBalanceMapper leaveBalanceMapper = new LeaveBalanceMapper();
		leaveBalanceMapper.setEmployeeId(employeeId);
		leaveBalanceMapper.setTotalLeaves(max_leaves);
		leaveBalanceMapper.setTotalAppliedLeaves(total_applied_leaves);
		leaveBalanceMapper.setTotalPendingLeaves(pending_leaves);
		leaveBalanceMapper.setLeaveBalance(leave_balance);
		leaveBalanceMapper.setEmployeeName(employee_name);
		leaveBalanceMapper.setImageId(imageId);
		return leaveBalanceMapper;
	}

	@Override
	public List<LeavesMapper> getLeavesListByUserIdWithDateRange(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.getDateFromISOString(endDate));
			start_date = Utility.getDateFromISOString(startDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<LeaveDetails> leaveList = leaveDetailsRepository.getLeavessByEmployeeIdWithDateRange(userId, start_date,
				end_date);

		if (null != leaveList && !leaveList.isEmpty()) {
			return leaveList.stream().map(leaveDetails -> {
				LeavesMapper leavesMapper = new LeavesMapper();
				leavesMapper.setCoverDetails(leaveDetails.getCover_details());
				leavesMapper.setEmployeeId(leaveDetails.getEmployee_id());
				leavesMapper.setStartDate(Utility.getISOFromDate(leaveDetails.getStart_date()));
				leavesMapper.setEndDate(Utility.getISOFromDate(leaveDetails.getEnd_date()));
				leavesMapper.setLeaveId(leaveDetails.getLeave_id());
				leavesMapper.setReason(leaveDetails.getReason());
				leavesMapper.setStatus(leaveDetails.getStatus());
				return leavesMapper;
			}).collect(Collectors.toList());

		}
		return null;
	}

	@Override
	public List<LeavesMapper> getLeavesListByOrgIdWithDateRange(String orgId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.getDateFromISOString(endDate));
			start_date = Utility.getDateFromISOString(startDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<LeaveDetails> leaveList = leaveDetailsRepository.getLeavessByOrgIdWithDateRange(orgId, start_date,
				end_date);

		if (null != leaveList && !leaveList.isEmpty()) {
			return leaveList.stream().map(leaveDetails -> {
				LeavesMapper leavesMapper = new LeavesMapper();
				leavesMapper.setCoverDetails(leaveDetails.getCover_details());
				leavesMapper.setEmployeeId(leaveDetails.getEmployee_id());
				leavesMapper.setStartDate(Utility.getISOFromDate(leaveDetails.getStart_date()));
				leavesMapper.setEndDate(Utility.getISOFromDate(leaveDetails.getEnd_date()));
				leavesMapper.setLeaveId(leaveDetails.getLeave_id());
				leavesMapper.setReason(leaveDetails.getReason());
				leavesMapper.setStatus(leaveDetails.getStatus());
				return leavesMapper;
			}).collect(Collectors.toList());

		}
		return null;
	}

	public boolean confirmWeekend(Date date) {
		// Convert the java.util.Date to a Calendar object
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

// Get the day of the week (1 = Sunday, 2 = Monday, ..., 7 = Saturday)
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

// Check if it's Saturday (7) or Sunday (1)
		boolean fob = false;
		if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
			fob = true;
		} else if (confirmHoliday(date)) {
			fob = true;
		}
		return fob;
	}

	private boolean confirmHoliday(Date date) {
		Date startDate = date;
		Date endDate = Utility.getDateAfterEndDate(startDate);
		Holiday holiday = holidayRepository.getHolidayByDate(startDate, endDate);
		boolean fob = false;
		if (null != holiday) {
			fob = true;
		}
		return fob;
	}

	@Override
	public List<LeavesMapper> getEmployeeleaveStatusListByEmployeeId(String employeeId, String status) {

		List<LeavesMapper> resultList = new ArrayList<>();
		List<LeaveDetails> list = leaveDetailsRepository.getLeaveListByStatus(employeeId, status);
		if (null != list && !list.isEmpty()) {
			list.stream().map(leaveDetails -> {
				LeavesMapper leavesMapper = getLeaveDetailsMapper(leaveDetails);
				resultList.add(leavesMapper);
				return resultList;
			}).collect(Collectors.toList());
		}

		return resultList;
	}

	@Override
	public List<EmployeeLeaveMapper> getEmployeeLeaveListByOrgIdWithDateWise(String orgId, String startDate,
			String endDate) {

		List<EmployeeLeaveMapper> resultList = new ArrayList<>();

		Date startDate1 = null;
		Date endDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date start_date = startDate1;
		Date end_date = endDate1;
		List<EmployeeDetails> employeeList = employeeRepository.getEmployeesByOrgId(orgId);
		System.out.println("###########" + employeeList.toString());
		if (null != employeeList && !employeeList.isEmpty()) {
			return employeeList.stream().map(employee -> {
				EmployeeLeaveMapper mapper = new EmployeeLeaveMapper();
				List<LeaveDetails> leaveList = leaveDetailsRepository
						.getLeavessByEmployeeIdWithDateRange(employee.getEmployeeId(), start_date, end_date);
				List<LeavesMapper> leavesMapper1 = new ArrayList<>();
				if (null != leaveList && !leaveList.isEmpty()) {
					leavesMapper1 = leaveList.stream().map(leaveDetails -> {
						LeavesMapper leavesMapper = new LeavesMapper();
						leavesMapper.setCoverDetails(leaveDetails.getCover_details());
						leavesMapper.setEmployeeId(leaveDetails.getEmployee_id());
						leavesMapper.setStartDate(Utility.getISOFromDate(leaveDetails.getStart_date()));
						leavesMapper.setEndDate(Utility.getISOFromDate(leaveDetails.getEnd_date()));
						leavesMapper.setLeaveId(leaveDetails.getLeave_id());
						leavesMapper.setReason(leaveDetails.getReason());
						leavesMapper.setStatus(leaveDetails.getStatus());
						// leavesMapper1.add(leavesMapper);
						return leavesMapper;

					}).collect(Collectors.toList());
				}
				mapper.setEmployeeName(employeeService.getEmployeeFullName(employee.getEmployeeId()));
				mapper.setEmployeeId(employee.getEmployeeId());
				mapper.setLeaveList(leavesMapper1);
				// resultList.add(mapper);
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultList;
	}

	@Override
	public NotesMapper saveLeaveNotes(NotesMapper notesMapper) {

		String leaveNotesId = null;
		if (null != notesMapper) {
			Notes notes = new Notes();
			notes.setNotes(notesMapper.getNotes());
			notes.setCreation_date(new Date());
			notes.setUserId(notesMapper.getEmployeeId());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);
			leaveNotesId = note.getNotes_id();

			/* insert to leaves-notes-link */

			LeaveNotesLink leaveNotesLink = new LeaveNotesLink();
			leaveNotesLink.setLeaveId(notesMapper.getLeaveId());
			leaveNotesLink.setNoteId(leaveNotesId);
			leaveNotesLink.setCreationDate(new Date());

			leaveNotesId = leaveNotesLinkRepository.save(leaveNotesLink).getNoteId();

		}
		return getNotes(leaveNotesId);

	}

	@Override
	public List<NotesMapper> getNoteListByLeaveId(String leaveId) {
		List<LeaveNotesLink> leavesNotesLinkList = leaveNotesLinkRepository.getNotesIdByLeaveId(leaveId);
		if (leavesNotesLinkList != null && !leavesNotesLinkList.isEmpty()) {
			return leavesNotesLinkList.stream().map(leavesNotesLink -> {
				NotesMapper notesMapper = getNotes(leavesNotesLink.getNoteId());
				return notesMapper;
			}).collect(Collectors.toList());
		}
		return null;
	}

	private NotesMapper getNotes(String id) {
		Notes notes = notesRepository.findByNoteId(id);
		NotesMapper notesMapper = new NotesMapper();
		if (null != notes) {
			notesMapper.setNotesId(notes.getNotes_id());
			notesMapper.setNotes(notes.getNotes());
			notesMapper.setCreationDate(Utility.getISOFromDate(notes.getCreation_date()));
			if (!StringUtils.isEmpty(notes.getUserId())) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeesByuserId(notes.getUserId());
				String fullName = "";
				String middleName = "";
				String lastName = "";
				if (null != employeeDetails.getMiddleName()) {

					middleName = employeeDetails.getMiddleName();
				}
				if (null != employeeDetails.getLastName()) {
					lastName = employeeDetails.getLastName();
				}
				fullName = employeeDetails.getFirstName() + " " + middleName + " " + lastName;
				notesMapper.setOwnerName(fullName);
			}
		}
		return notesMapper;

	}

//	@Override
//	public NotesMapper updateNoteDetails(String notesId, NotesMapper notesMapper) {
//
//		NotesMapper resultMapper = employeeService.updateNotes(notesMapper);
//
//		return resultMapper;
//	}

	@Override
	public void deleteLeaveNotesById(String notesId) {
		LeaveNotesLink notesList = leaveNotesLinkRepository.findByNoteId(notesId);
		if (null != notesList) {

			Notes notes = notesRepository.findByNoteId(notesId);
			if (null != notes) {
				notes.setLiveInd(false);
				notesRepository.save(notes);
			}
		}
	}

	@Override
	public List<LeavesMapper> getEmployeeleaveStatusListByEmployeeIdWithDateWise(String employeeId, String status,
			String startDate, String endDate) {
		List<LeavesMapper> resultList = new ArrayList<>();
		Date start_date = null;
		Date end_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<LeaveDetails> list =null;
		if(status.equalsIgnoreCase("Approved")) {
		 list = leaveDetailsRepository.getLeavessByEmployeeIdWithDateRangeAndStatus(employeeId, start_date, end_date,status);
		}else {
			list = leaveDetailsRepository.getAllLeavessByEmployeeIdWithDateRange(employeeId, start_date, end_date);
		}
		if (null != list && !list.isEmpty()) {
			list.stream().map(leaveDetails -> {
				LeavesMapper leavesMapper = getLeaveDetailsMapper(leaveDetails);
				resultList.add(leavesMapper);
				return resultList;
			}).collect(Collectors.toList());
		}

		return resultList;
	}

	@Override
	public List<EmployeeLeaveMapper> getEmployeeLeaveListByOrgIdWithDateWiseAndStatus(String orgId, String startDate,
			String endDate, String status) {
		List<EmployeeLeaveMapper> resultList = new ArrayList<>();

		Date startDate1 = null;
		Date endDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date start_date = startDate1;
		Date end_date = endDate1;
		List<EmployeeDetails> employeeList = employeeRepository.getEmployeesByOrgId(orgId);
		System.out.println("###########" + employeeList.toString());
		if (null != employeeList && !employeeList.isEmpty()) {
			return employeeList.stream().map(employee -> {
				EmployeeLeaveMapper mapper = new EmployeeLeaveMapper();
				List<LeaveDetails> leaveList = null;
				if(status.equalsIgnoreCase("Approved")) {
				leaveList = leaveDetailsRepository
						.getLeavessByEmployeeIdWithDateRangeAndStatus(employee.getEmployeeId(), start_date, end_date,status);
				}else {
					leaveList = leaveDetailsRepository
							.getLeavessAllByEmployeeIdWithDateRange(employee.getEmployeeId(), start_date, end_date);
				}
				List<LeavesMapper> leavesMapper1 = new ArrayList<>();
				if (null != leaveList && !leaveList.isEmpty()) {
					leavesMapper1 = leaveList.stream().map(leaveDetails -> {
						LeavesMapper leavesMapper = new LeavesMapper();
						leavesMapper.setCoverDetails(leaveDetails.getCover_details());
						leavesMapper.setEmployeeId(leaveDetails.getEmployee_id());
						leavesMapper.setStartDate(Utility.getISOFromDate(leaveDetails.getStart_date()));
						leavesMapper.setEndDate(Utility.getISOFromDate(leaveDetails.getEnd_date()));
						leavesMapper.setLeaveId(leaveDetails.getLeave_id());
						leavesMapper.setReason(leaveDetails.getReason());
						leavesMapper.setStatus(leaveDetails.getStatus());
						// leavesMapper1.add(leavesMapper);
						return leavesMapper;

					}).collect(Collectors.toList());
				}
				mapper.setEmployeeName(employeeService.getEmployeeFullName(employee.getEmployeeId()));
				mapper.setEmployeeId(employee.getEmployeeId());
				mapper.setLeaveList(leavesMapper1);
				// resultList.add(mapper);
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultList;

	}

}
