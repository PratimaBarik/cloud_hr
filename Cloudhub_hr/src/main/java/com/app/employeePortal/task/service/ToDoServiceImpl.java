package com.app.employeePortal.task.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.call.entity.CallDetails;
import com.app.employeePortal.call.entity.ContactCallLink;
import com.app.employeePortal.call.entity.CustomerCallLink;
import com.app.employeePortal.call.entity.EmployeeCallLink;
import com.app.employeePortal.call.entity.InvestorCallLink;
import com.app.employeePortal.call.entity.InvestorLeadsCallLink;
import com.app.employeePortal.call.entity.LeadsCallLink;
import com.app.employeePortal.call.repository.CallDetailsRepository;
import com.app.employeePortal.call.repository.ContactCallLinkRepo;
import com.app.employeePortal.call.repository.CustomerCallLinkRepo;
import com.app.employeePortal.call.repository.EmployeeCallRepository;
import com.app.employeePortal.call.repository.InvestorCallLinkRepo;
import com.app.employeePortal.call.repository.InvestorLeadsCallLinkRepo;
import com.app.employeePortal.call.repository.LeadsCallLinkRepo;
import com.app.employeePortal.call.service.CallService;
import com.app.employeePortal.email.service.EmailService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.entity.ContactEventLink;
import com.app.employeePortal.event.entity.CustomerEventLink;
import com.app.employeePortal.event.entity.EmployeeEventLink;
import com.app.employeePortal.event.entity.EventDetails;
import com.app.employeePortal.event.entity.EventType;
import com.app.employeePortal.event.entity.InvestorEventLink;
import com.app.employeePortal.event.entity.InvestorLeadsEventLink;
import com.app.employeePortal.event.entity.LeadsEventLink;
import com.app.employeePortal.event.repository.ContactEventRepo;
import com.app.employeePortal.event.repository.CustomerEventRepo;
import com.app.employeePortal.event.repository.EmployeeEventRepository;
import com.app.employeePortal.event.repository.EventDetailsRepository;
import com.app.employeePortal.event.repository.EventTypeRepository;
import com.app.employeePortal.event.repository.InvestorEventRepo;
import com.app.employeePortal.event.repository.InvestorLeadsEventRepo;
import com.app.employeePortal.event.repository.LeadsEventRepo;
import com.app.employeePortal.event.service.EventService;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.task.entity.ContactTaskLink;
import com.app.employeePortal.task.entity.CustomerTaskLink;
import com.app.employeePortal.task.entity.EmployeeTaskLink;
import com.app.employeePortal.task.entity.InvestorLeadsTaskLink;
import com.app.employeePortal.task.entity.InvestorTaskLink;
import com.app.employeePortal.task.entity.LastActivityLog;
import com.app.employeePortal.task.entity.LeadsTaskLink;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.entity.TaskType;
import com.app.employeePortal.task.mapper.TeamEmployeeMapper;
import com.app.employeePortal.task.mapper.ToDoDetailsMapper;
import com.app.employeePortal.task.mapper.ToDoMapper;
import com.app.employeePortal.task.mapper.UpcomingEventMapper;
import com.app.employeePortal.task.mapper.UserPlannerMapper;
import com.app.employeePortal.task.repository.CandidateTaskLinkRepository;
import com.app.employeePortal.task.repository.ContactTaskRepo;
import com.app.employeePortal.task.repository.CustomerTaskRepo;
import com.app.employeePortal.task.repository.EmployeeTaskRepository;
import com.app.employeePortal.task.repository.InvestorLeadsTaskRepo;
import com.app.employeePortal.task.repository.InvestorTaskRepo;
import com.app.employeePortal.task.repository.LastActivityLogRepository;
import com.app.employeePortal.task.repository.LeadsTaskRepo;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskTypeRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class ToDoServiceImpl implements ToDoService {

	@Autowired
	EmployeeService employeeService;
	@Autowired
	CandidateTaskLinkRepository candidateTaskLinkRepository;
	@Autowired
	EmployeeEventRepository employeeEventRepository;
	@Autowired
	TaskDetailsRepository taskDetailsRepository;
	@Autowired
	EventDetailsRepository eventDetailsRepository;
	@Autowired
	TaskTypeRepository taskTypeRepository;
	@Autowired
	EventTypeRepository eventTypeRepository;
	@Autowired
	EmployeeCallRepository employeeCallRepository;
	@Autowired
	CallDetailsRepository callDetailsRepository;
	@Autowired
	EmployeeTaskRepository employeeTaskRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EmailService emailService;
	@Autowired
    CustomerTaskRepo customerTaskRepo;
    @Autowired
    ContactTaskRepo contactTaskRepo;
	@Autowired
	LastActivityLogRepository lastActivityLogRepository;
	@Autowired 
	CallService callService;
	@Autowired 
	EventService eventService;
	@Autowired 
	TaskService taskService;
	@Autowired
	CustomerEventRepo customerEventRepo;
	@Autowired
	CustomerCallLinkRepo customerCallLinkRepo;
	@Autowired
	ContactCallLinkRepo contactCallLinkRepo;
	@Autowired
	ContactEventRepo contactEventRepo;
	@Autowired
	LeadsCallLinkRepo leadsCallLinkRepo;
	@Autowired
	LeadsEventRepo leadsEventRepo;
	@Autowired
    LeadsTaskRepo leadsTaskRepo;
	@Autowired
	InvestorCallLinkRepo investorCallLinkRepo;
	@Autowired
	InvestorLeadsCallLinkRepo investorLeadsCallLinkRepo;
	@Autowired
	InvestorEventRepo investorEventRepo;
	@Autowired
	InvestorLeadsEventRepo investorLeadsEventRepo;
	@Autowired
    InvestorLeadsTaskRepo investorLeadsTaskRepo;
	@Autowired
    InvestorTaskRepo investorTaskRepo;

	@Override
	public List<ToDoMapper> getToDoByUserId(String userId, String startDate, String endDate) {
		List<ToDoMapper> todoMapperList = new ArrayList<>();

		List<ToDoMapper> eventMapperList = new ArrayList<>();
		List<ToDoMapper> callMapperList = new ArrayList<>();
		List<ToDoMapper> taskMapperList = new ArrayList<>();

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("end_date>" + end_date);
		System.out.println("start_date>" + start_date);
		List<EmployeeEventLink> employeeEventLink = employeeEventRepository.getByEmployeeId(userId);
		System.out.println("employeeEventLink>>" + employeeEventLink.size());
		if (null != employeeEventLink && !employeeEventLink.isEmpty()) {
			for (EmployeeEventLink eventDetails2 : employeeEventLink) {

				System.out.println("Starting:1>>>>>>>>>>>>>>>>>>>>>>>");
				List<EventDetails> eventdetailsList = eventDetailsRepository
						.getEventByEventIdAndDateRange(eventDetails2.getEvent_id(), start_date, end_date);
				// List<EventDetails> eventdetailsList =
				// eventDetailsRepository.getEventDetailssById(eventDetails2.getEvent_id());
				System.out.println("Starting:2>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println("eventdetailsList>>" + eventdetailsList.size());
				if (null != eventdetailsList && !eventdetailsList.isEmpty()) {
					for (EventDetails eventDetails1 : eventdetailsList) {
						System.out.println("Starting:3>>>>>>>>>>>>>>>>>>>>>>>");
						System.out.println("start_date=" + start_date);
						System.out.println("DBstart_date=" + eventDetails1.getStart_date());
						EventDetails eventDetails = eventDetailsRepository
								.getEventDetailsById(eventDetails1.getEvent_id());
						ToDoMapper eventMapper = new ToDoMapper();

						if (!StringUtils.isEmpty(eventDetails.getEvent_type())) {
							System.out.println("eventDetails.getEvent_type()>>" + eventDetails.getEvent_type());
							EventType eventType = eventTypeRepository.findByEventTypeId(eventDetails.getEvent_type());
							if (null != eventType.getEventType() && !StringUtils.isEmpty(eventType.getEventType())) {
								eventMapper.setType(eventType.getEventType());
								eventMapper.setEventTypeId(eventType.getEventTypeId());
							}
						}
						eventMapper.setStartDate(Utility.getISOFromDate(eventDetails.getStart_date()));
						eventMapper.setEndDate(Utility.getISOFromDate(eventDetails.getEnd_date()));
						eventMapper.setCreationDate(Utility.getISOFromDate(eventDetails.getCreation_date()));
						eventMapper.setEventId(eventDetails.getEvent_id());
						eventMapper.setTopic(eventDetails.getSubject());
						eventMapper.setStartTime(eventDetails.getStart_time());
						eventMapper.setRating(eventDetails.getRating());
						eventMapper.setCompletionInd(eventDetails.isComplitionInd());
						eventMapper.setActivity("Event");

						eventMapperList.add(eventMapper);

					}
				}
			}
		}

		List<EmployeeCallLink> employeeCallLink = employeeCallRepository.getByEmployeeId(userId);
		if (null != employeeCallLink && !employeeCallLink.isEmpty()) {

			for (EmployeeCallLink callDetails2 : employeeCallLink) {

				List<CallDetails> callList = callDetailsRepository
						.getCallListByCallIdAndStartDate(callDetails2.getCall_id(), start_date, end_date);

				if (null != callList && !callList.isEmpty()) {

					callList.stream().map(callDetails1 -> {
						CallDetails callDetails = callDetailsRepository.getCallDetailsById(callDetails1.getCall_id());

						ToDoMapper callMapper = new ToDoMapper();

						callMapper.setEndDate(Utility.getISOFromDate(callDetails.getCall_end_date()));
						callMapper.setStartDate(Utility.getISOFromDate(callDetails.getCall_start_date()));
						callMapper.setCallId(callDetails.getCall_id());
						callMapper.setType(callDetails.getCallType());
						callMapper.setTopic(callDetails.getSubject());
						callMapper.setStartTime(callDetails.getCall_start_time());
						callMapper.setEndTime(callDetails.getCall_end_time());
						callMapper.setCreationDate(Utility.getISOFromDate(callDetails.getCreation_date()));
						callMapper.setRating(callDetails.getRating());
						callMapper.setCompletionInd(callDetails.isComplitionInd());
						callMapper.setActivity("Call");

						callMapperList.add(callMapper);

						return callMapperList;
					}).collect(Collectors.toList());
				}
			}

		}

		List<EmployeeTaskLink> employeeTaskLink = employeeTaskRepository.getByEmployeeId(userId);
		if (null != employeeTaskLink && !employeeTaskLink.isEmpty()) {

			for (EmployeeTaskLink taskLink : employeeTaskLink) {

				List<TaskDetails> taskList = taskDetailsRepository
						.getTaskListByTaskIdAndStartDate(taskLink.getTask_id(), start_date, end_date);
				System.out.println("taskList>>" + taskList.toString());
				if (null != taskList && !taskList.isEmpty()) {

					for (TaskDetails taskLink1 : taskList) {

						TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskLink1.getTask_id());
						if (null != taskDetails) {
							ToDoMapper taskMapper = new ToDoMapper();

							if (!StringUtils.isEmpty(taskDetails.getTask_type())) {
								List<TaskType> taskType = taskTypeRepository
										.getByTaskTypeId(taskDetails.getTask_type());
								if (null != taskType && !taskType.isEmpty()) {

									taskType.stream().map(taskType1 -> {
										taskMapper.setType(taskType1.getTaskType());
										taskMapper.setTaskTypeId(taskType1.getTaskTypeId());

										return taskMapper;
									}).collect(Collectors.toList());

								}
							}
							taskMapper.setStartDate(Utility.getISOFromDate(taskDetails.getStart_date()));
							taskMapper.setEndDate(Utility.getISOFromDate(taskDetails.getEnd_date()));
							taskMapper.setTaskId(taskDetails.getTask_id());
							taskMapper.setPriority(taskDetails.getPriority());
							taskMapper.setTopic(taskDetails.getTask_name());
							taskMapper.setStatus(taskDetails.getTask_status());
							taskMapper.setCreationDate(Utility.getISOFromDate(taskDetails.getCreation_date()));
							taskMapper.setRating(taskDetails.getRating());
							taskMapper.setCompletionInd(taskDetails.isComplitionInd());
							taskMapper.setActivity("Task");

							System.out.println("taskMapperList>>" + taskMapperList.toString());

							List<String> employeeIds = employeeTaskRepository
									.getEmpListByTaskId(taskDetails.getTask_id()).stream()
									.map(EmployeeTaskLink::getEmployee_id).collect(Collectors.toList());
							List<TeamEmployeeMapper> team = new ArrayList<>();

							if (null != employeeIds && !employeeIds.isEmpty()) {
								employeeIds.remove(taskDetails.getUser_id());
								employeeIds.remove(taskDetails.getAssigned_to());

								for (String empId : employeeIds) {
									TeamEmployeeMapper mapper = new TeamEmployeeMapper();
									employeeService.getEmployeeFullName(empId);
									mapper.setEmployeeId(empId);
									mapper.setName(employeeService.getEmployeeFullName(empId));
									team.add(mapper);
								}
								taskMapper.setTeam(team);
							}

							taskMapper.setAssignedToName(
									employeeService.getEmployeeFullName(taskDetails.getAssigned_to()));
							taskMapper.setAssignedTo(taskDetails.getAssigned_to());

							taskMapperList.add(taskMapper);
						}
					}
				}
			}
		}
		System.out.println("taskMapperList2>>" + taskMapperList.toString());

		todoMapperList.addAll(callMapperList);
		todoMapperList.addAll(eventMapperList);
		todoMapperList.addAll(taskMapperList);

		return todoMapperList;
	}

	@Override
	public ToDoMapper updateToDoByCallId(String eventCallTaskId, ToDoDetailsMapper toDoDetailsMapper) {
		ToDoMapper todoMapper = new ToDoMapper();
		CallDetails callDetails = callDetailsRepository.getCallDetailsById(eventCallTaskId);
		if (null != callDetails) {
			if (false != toDoDetailsMapper.isCompletionInd()) {
				callDetails.setComplitionInd(toDoDetailsMapper.isCompletionInd());
			} else {
				callDetails.setComplitionInd(toDoDetailsMapper.isCompletionInd());
			}
			if (0 != toDoDetailsMapper.getRating()) {
				callDetails.setRating(toDoDetailsMapper.getRating());
			}
			callDetails.setUpdateDate(new Date());
			callDetailsRepository.save(callDetails);
		}
		todoMapper = getByCallId(eventCallTaskId);
		return todoMapper;
	}

	private ToDoMapper getByCallId(String eventCallTaskId) {
		CallDetails callDetails = callDetailsRepository.getCallDetailsById(eventCallTaskId);

		ToDoMapper callMapper = new ToDoMapper();

		callMapper.setEndDate(Utility.getISOFromDate(callDetails.getCall_end_date()));
		callMapper.setStartDate(Utility.getISOFromDate(callDetails.getCall_start_date()));
		callMapper.setCallId(callDetails.getCall_id());
		callMapper.setType(callDetails.getCallType());
		callMapper.setTopic(callDetails.getSubject());
		callMapper.setStartTime(callDetails.getCall_start_time());
		callMapper.setEndTime(callDetails.getCall_end_time());
		callMapper.setCreationDate(Utility.getISOFromDate(callDetails.getCreation_date()));
		callMapper.setCompletionInd(callDetails.isComplitionInd());
		callMapper.setRating(callDetails.getRating());
		callMapper.setActivity("Call");

		return callMapper;
	}

	@Override
	public ToDoMapper updateToDoByTaskId(String eventCallTaskId, ToDoDetailsMapper toDoDetailsMapper) {
		ToDoMapper todoMapper = new ToDoMapper();
		TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(eventCallTaskId);
		if (null != taskDetails) {
			if (false != toDoDetailsMapper.isCompletionInd()) {
				taskDetails.setComplitionInd(toDoDetailsMapper.isCompletionInd());
			} else {
				taskDetails.setComplitionInd(toDoDetailsMapper.isCompletionInd());
			}
			if (0 != toDoDetailsMapper.getRating()) {
				taskDetails.setRating(toDoDetailsMapper.getRating());
			}
			taskDetails.setUpdateDate(new Date());
			taskDetailsRepository.save(taskDetails);
		}
		todoMapper = getByTaskId(eventCallTaskId);
		return todoMapper;
	}

	private ToDoMapper getByTaskId(String eventCallTaskId) {
		TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(eventCallTaskId);
		ToDoMapper taskMapper = new ToDoMapper();

		taskMapper.setStartDate(Utility.getISOFromDate(taskDetails.getStart_date()));
		taskMapper.setEndDate(Utility.getISOFromDate(taskDetails.getEnd_date()));
		taskMapper.setTaskId(taskDetails.getTask_id());
		taskMapper.setPriority(taskDetails.getPriority());
		taskMapper.setTopic(taskDetails.getTask_name());
		taskMapper.setStatus(taskDetails.getTask_status());
		taskMapper.setCreationDate(Utility.getISOFromDate(taskDetails.getCreation_date()));
		taskMapper.setCompletionInd(taskDetails.isComplitionInd());
		taskMapper.setRating(taskDetails.getRating());
		taskMapper.setActivity("Task");

		if (!StringUtils.isEmpty(taskDetails.getTask_type())) {
			List<TaskType> taskType = taskTypeRepository.getByTaskTypeId(taskDetails.getTask_type());
			if (null != taskType && !taskType.isEmpty()) {

				taskType.stream().map(taskType1 -> {
					taskMapper.setType(taskType1.getTaskType());
					taskMapper.setTaskTypeId(taskType1.getTaskTypeId());

					return taskMapper;
				});

			}
		}

		return taskMapper;
	}

	@Override
	public ToDoMapper updateToDoByEventId(String eventCallTaskId, ToDoDetailsMapper toDoDetailsMapper) {
		ToDoMapper todoMapper = new ToDoMapper();
		EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(eventCallTaskId);
		if (null != eventDetails) {
			if (false != toDoDetailsMapper.isCompletionInd()) {
				eventDetails.setComplitionInd(toDoDetailsMapper.isCompletionInd());
			} else {
				eventDetails.setComplitionInd(toDoDetailsMapper.isCompletionInd());
			}
			if (0 != toDoDetailsMapper.getRating()) {
				eventDetails.setRating(toDoDetailsMapper.getRating());
			}
			eventDetails.setUpdateDate(new Date());
			eventDetailsRepository.save(eventDetails);
		}
		todoMapper = getByEventId(eventCallTaskId);
		return todoMapper;
	}

	private ToDoMapper getByEventId(String eventCallTaskId) {
		EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(eventCallTaskId);
		ToDoMapper eventMapper = new ToDoMapper();

		if (!StringUtils.isEmpty(eventDetails.getEvent_type())) {
			System.out.println("eventDetails.getEvent_type()>>" + eventDetails.getEvent_type());
			EventType eventType = eventTypeRepository.findByEventTypeId(eventDetails.getEvent_type());
			if (null != eventType.getEventType() && !StringUtils.isEmpty(eventType.getEventType())) {
				eventMapper.setType(eventType.getEventType());
				eventMapper.setEventTypeId(eventType.getEventTypeId());
			}
		}
		eventMapper.setStartDate(Utility.getISOFromDate(eventDetails.getStart_date()));
		eventMapper.setEndDate(Utility.getISOFromDate(eventDetails.getEnd_date()));
		eventMapper.setCreationDate(Utility.getISOFromDate(eventDetails.getCreation_date()));
		eventMapper.setEventId(eventDetails.getEvent_id());
		eventMapper.setTopic(eventDetails.getSubject());
		eventMapper.setStartTime(eventDetails.getStart_time());
		eventMapper.setRating(eventDetails.getRating());
		eventMapper.setCompletionInd(eventDetails.isComplitionInd());
		eventMapper.setActivity("Event");

		return eventMapper;
	}

	@Override
	public List<UpcomingEventMapper> upcomingBirthdayAndAniversary() {
		List<UpcomingEventMapper> upcomingEventMappers = new ArrayList<>();
//	 Calendar calendar = Calendar.getInstance();
//     calendar.setTime(new Date());
//     calendar.set(Calendar.DAY_OF_MONTH, 1);
//     Date startDate=calendar.getTime();
//     System.out.println(startDate);
//     calendar.add(Calendar.MONTH, 1);
//     Date endDate = calendar.getTime();
//     System.out.println(endDate);
		Date startDate = new Date(); // Current date
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		System.out.println(startDate);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date endDate = calendar.getTime(); // End of the current month
		System.out.println(endDate);
		List<EmployeeDetails> details = employeeRepository.findByJoiningDate(startDate, endDate);
		for (EmployeeDetails employeeDetails : details) {
			if (!Utility.removeTime(employeeDetails.getDateOfJoining()).equals(Utility.removeTime(new Date()))) {
				UpcomingEventMapper eventMapper = new UpcomingEventMapper();
				eventMapper.setDate(Utility.getISOFromDate(employeeDetails.getDateOfJoining()));
				eventMapper.setEmail(employeeDetails.getEmailId());
				eventMapper.setEventType("Job Anniversary");
				eventMapper.setUserId(employeeDetails.getUserId());
				eventMapper.setUserName(employeeService.getEmployeeFullName(employeeDetails.getUserId()));
				upcomingEventMappers.add(eventMapper);
			}
		}
		List<EmployeeDetails> details1 = employeeRepository.findByDOB(startDate, endDate);
		for (EmployeeDetails details2 : details1) {
			if (!Utility.removeTime(details2.getDob()).equals(Utility.removeTime(new Date()))) {
				UpcomingEventMapper eventMapper = new UpcomingEventMapper();
				eventMapper.setDate(Utility.getISOFromDate(details2.getDob()));
				eventMapper.setEmail(details2.getEmailId());
				eventMapper.setEventType("Birthday");
				eventMapper.setUserId(details2.getUserId());
				eventMapper.setUserName(employeeService.getEmployeeFullName(details2.getUserId()));
				upcomingEventMappers.add(eventMapper);
			}
		}
		return upcomingEventMappers;
	}

//	@Scheduled(cron = "0 0 0 * * *")
	public void wishBirthdayAndAniversary() {

		Date startDate = new Date(); // Current date
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		List<EmployeeDetails> details = employeeRepository.getAnniversary(startDate);
		for (EmployeeDetails details1 : details) {
			if (!Utility.removeTime(details1.getDateOfJoining()).equals(Utility.removeTime(new Date()))) {
				System.out.println("Happy Job Anivesary" + details1.getFirstName());
				String text = "<div style=' display: block; margin-top: 100px; '>"
						+ "    <div style='  text-align: center;'> </div>"
						+ "    <div style='  margin: 0 auto; width: 300px; background-color: #f4f4f4;  height: 250px; border: 1px #ccc solid; padding: 50px;'>"
						+ "        <div class='box-2' style='  text-align: center;'>"
						+ "            <h1 style='text-align: center; padding: 10px;'>Dear " + details1.getFirstName()
						+ ", </h1>"
						+ "            <p style='text-align: center;'>fullName + \" requested approval for \" + notification.getNotificationType());"
						+ "            </div>" + "        </div>" + "    </div>" + "</div>";
				try {
					emailService.sendMail("support@innoverenit.com", details1.getEmailId(), "Anniversary Wish", text);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		List<EmployeeDetails> details2 = employeeRepository.getBirthday(startDate);
		for (EmployeeDetails details1 : details2) {
			if (!Utility.removeTime(details1.getDob()).equals(Utility.removeTime(new Date()))) {
				System.out.println("Happy BirthDay" + details1.getFirstName());
				String text = "<div style=' display: block; margin-top: 100px; '>"
						+ "    <div style='  text-align: center;'> </div>"
						+ "    <div style='  margin: 0 auto; width: 300px; background-color: #f4f4f4;  height: 250px; border: 1px #ccc solid; padding: 50px;'>"
						+ "        <div class='box-2' style='  text-align: center;'>"
						+ "            <h1 style='text-align: center; padding: 10px;'>Dear " + details1.getFirstName()
						+ " ,</h1>"
						+ "            <p style='text-align: center;'>fullName + \" requested approval for \" + notification.getNotificationType());"
						+ "            </div>" + "        </div>" + "    </div>" + "</div>";

				try {
					emailService.sendMail("support@innoverenit.com", details1.getEmailId(), "Birthday Wish", text);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Map<String, Long> getToDoCountByUserId(String userId, String startDate, String endDate) {
		Map<String, Long> map = new HashMap<>();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		long count = 0;
		
		List<EmployeeEventLink> employeeEventLink = employeeEventRepository.getByEmployeeId(userId);
		System.out.println("employeeEventLink>>" + employeeEventLink.size());
		if (null != employeeEventLink && !employeeEventLink.isEmpty()) {
			for (EmployeeEventLink eventDetails2 : employeeEventLink) {
				List<EventDetails> eventdetailsList = eventDetailsRepository
						.getEventByEventIdAndDateRange(eventDetails2.getEvent_id(), start_date, end_date);
				count += eventdetailsList.size();
			}

			List<EmployeeCallLink> employeeCallLink = employeeCallRepository.getByEmployeeId(userId);
			if (null != employeeCallLink && !employeeCallLink.isEmpty()) {
				for (EmployeeCallLink callDetails2 : employeeCallLink) {
					List<CallDetails> callList = callDetailsRepository
							.getCallListByCallIdAndStartDate(callDetails2.getCall_id(), start_date, end_date);
					count += callList.size();
				}
			}

			List<EmployeeTaskLink> employeeTaskLink = employeeTaskRepository.getByEmployeeId(userId);
			if (null != employeeTaskLink && !employeeTaskLink.isEmpty()) {
				for (EmployeeTaskLink taskLink : employeeTaskLink) {
					List<TaskDetails> taskList = taskDetailsRepository
							.getTaskListByTaskIdAndStartDate(taskLink.getTask_id(), start_date, end_date);
					count += taskList.size();
				}
			}

			map.put("todo", count);
		}
		return map;
	}

	@Override
	public ToDoMapper getLastActivityByUserId(String id) {
		LastActivityLog dbData = lastActivityLogRepository.findByUserTypeId(id);
		ToDoMapper eventMapper = new ToDoMapper();
		if(null!=dbData) {
		
		eventMapper.setStartDate(Utility.getISOFromDate(dbData.getStartDate()));
		eventMapper.setEndDate(Utility.getISOFromDate(dbData.getEndDate()));
		eventMapper.setCreationDate(Utility.getISOFromDate(dbData.getCreationDate()));
		eventMapper.setEventId(dbData.getActivityType());
		eventMapper.setTopic(dbData.getSubject());
		eventMapper.setDescription(dbData.getDescription());
		eventMapper.setStartTime(dbData.getStartTime());
		eventMapper.setEndTime(dbData.getEndTime());
		}
		return eventMapper;
	}

	@Override
	public List<NotesMapper> getActivityNotesList(String type, String id) {
		List<NotesMapper> resultList = new ArrayList<>();
		if(type.equalsIgnoreCase("call")) {
			resultList = callService.getNoteListByCallId(id);
		}else if(type.equalsIgnoreCase("event")) {
			resultList = eventService.getNoteListByEventId(id);
		}else {
			resultList = taskService.getNoteListByTaskId(id);
		}
		return resultList;
	}

	@Override
	public String saveActivityNotes(NotesMapper notesMapper, String type, String id) {
		String reponse = null;
		if(type.equalsIgnoreCase("call")) {
			notesMapper.setCallId(id);
			reponse = callService.saveCallNotes(notesMapper);
		}else if(type.equalsIgnoreCase("event")) {
			notesMapper.setEventId(id);
			reponse = eventService.createEventNotes(notesMapper);
		}else {
			notesMapper.setTaskId(id);
			reponse = taskService.saveTaskNotes(notesMapper);
		}
		return reponse;
	}

	@Override
	public String deleteActivity(String activityType, String activityId, String creatorType, String creatorId) {
		String msg = null;
		if(creatorType.equalsIgnoreCase("Customer")) {
			
			if(activityType.equalsIgnoreCase("call")) {
				CustomerCallLink customerCallLink = customerCallLinkRepo.getByCustomerIdAndCallIdAndLiveInd(creatorId,activityId);
				if(null!=customerCallLink) {
					customerCallLink.setLiveInd(false);
						CallDetails callDetails = callDetailsRepository.getCallDetailsById(activityId);
						if (null != callDetails) {
							callDetails.setLive_ind(false);
							callDetailsRepository.save(callDetails);
						}
						customerCallLinkRepo.save(customerCallLink);
					msg = "Activity Deleted Successfully";
				}
				
			}else if(activityType.equalsIgnoreCase("event")) {
				
				CustomerEventLink customerEventLink = customerEventRepo.getByCustomerIdAndEventIdAndLiveInd(creatorId,activityId);
				if(null!=customerEventLink) {
					customerEventLink.setLiveInd(false);
						EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(activityId);
						if (null != eventDetails) {
							eventDetails.setLive_ind(false);
							eventDetailsRepository.save(eventDetails);
						}
			            customerEventRepo.save(customerEventLink);
					msg = "Activity Deleted Successfully";
				}
				
			}else if(activityType.equalsIgnoreCase("task")) {
				
				CustomerTaskLink customerTaskLink = customerTaskRepo.getTaskListByCustomerIdAndTaskIdAndLiveInd(creatorId,activityId);
				if(null!=customerTaskLink) {
					customerTaskLink.setLiveInd(false);
					 TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(activityId);
			            if (null != taskDetails) {
			            	taskDetails.setLiveInd(false);
			            	taskDetailsRepository.save(taskDetails);
			            }
					customerTaskRepo.save(customerTaskLink);
					msg = "Activity Deleted Successfully";
				}
				
			}
			
		}else if(creatorType.equalsIgnoreCase("Contact")) {
			if(activityType.equalsIgnoreCase("call")) {
				ContactCallLink contactCallLink = contactCallLinkRepo.getByContactIdAndCallIdAndLiveInd(creatorId,activityId);
				if(null!=contactCallLink) {
					contactCallLink.setLiveInd(false);
						CallDetails callDetails = callDetailsRepository.getCallDetailsById(activityId);
						if (null != callDetails) {
							callDetails.setLive_ind(false);
							callDetailsRepository.save(callDetails);
						}
						contactCallLinkRepo.save(contactCallLink);
					msg = "Activity Deleted Successfully";
				}
				
			}else if(activityType.equalsIgnoreCase("event")) {
				
				ContactEventLink contactEventLink = contactEventRepo.getByContactIdAndEventIdAndLiveInd(creatorId,activityId);
				if(null!=contactEventLink) {
					contactEventLink.setLiveInd(false);
						EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(activityId);
						if (null != eventDetails) {
							eventDetails.setLive_ind(false);
							eventDetailsRepository.save(eventDetails);
						}
						contactEventRepo.save(contactEventLink);
					msg = "Activity Deleted Successfully";
				}
				
			}else if(activityType.equalsIgnoreCase("task")) {
				
				ContactTaskLink contactTaskLink = contactTaskRepo.getTaskListByContactIdAndTaskIdAndLiveInd(creatorId,activityId);
				if(null!=contactTaskLink) {
					contactTaskLink.setLiveInd(false);
					 TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(activityId);
			            if (null != taskDetails) {
			            	taskDetails.setLiveInd(false);
			            	taskDetailsRepository.save(taskDetails);
			            }
			            contactTaskRepo.save(contactTaskLink);
					msg = "Activity Deleted Successfully";
				}
				
			}
		}else if(creatorType.equalsIgnoreCase("Leads")) {
			
			if(activityType.equalsIgnoreCase("call")) {
				LeadsCallLink leadsCallLink = leadsCallLinkRepo.getByLeadsIdAndCallIdAndLiveInd(creatorId,activityId);
				if(null!=leadsCallLink) {
					leadsCallLink.setLiveInd(false);
						CallDetails callDetails = callDetailsRepository.getCallDetailsById(activityId);
						if (null != callDetails) {
							callDetails.setLive_ind(false);
							callDetailsRepository.save(callDetails);
						}
						leadsCallLinkRepo.save(leadsCallLink);
					msg = "Activity Deleted Successfully";
				}
				
			}else if(activityType.equalsIgnoreCase("event")) {
				
				LeadsEventLink leadsEventLink = leadsEventRepo.getByLeadsIdAndEventIdAndLiveInd(creatorId,activityId);
				if(null!=leadsEventLink) {
					leadsEventLink.setLiveInd(false);
						EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(activityId);
						if (null != eventDetails) {
							eventDetails.setLive_ind(false);
							eventDetailsRepository.save(eventDetails);
						}
						leadsEventRepo.save(leadsEventLink);
					msg = "Activity Deleted Successfully";
				}
				
			}else if(activityType.equalsIgnoreCase("task")) {
				
				LeadsTaskLink leadsTaskLink = leadsTaskRepo.getTaskListByLeadsIdAndTaskIdAndLiveInd(creatorId,activityId);
				if(null!=leadsTaskLink) {
					leadsTaskLink.setLiveInd(false);
					 TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(activityId);
			            if (null != taskDetails) {
			            	taskDetails.setLiveInd(false);
			            	taskDetailsRepository.save(taskDetails);
			            }
			            leadsTaskRepo.save(leadsTaskLink);
					msg = "Activity Deleted Successfully";
				}
				
			}
			
		}else if(creatorType.equalsIgnoreCase("Investor")) {
			
			if(activityType.equalsIgnoreCase("call")) {
				InvestorCallLink investorCallLink = investorCallLinkRepo.getByInvestorIdAndCallIdAndLiveInd(creatorId,activityId);
				if(null!=investorCallLink) {
					investorCallLink.setLiveInd(false);
						CallDetails callDetails = callDetailsRepository.getCallDetailsById(activityId);
						if (null != callDetails) {
							callDetails.setLive_ind(false);
							callDetailsRepository.save(callDetails);
						}
						investorCallLinkRepo.save(investorCallLink);
					msg = "Activity Deleted Successfully";
				}
				
			}else if(activityType.equalsIgnoreCase("event")) {
				
				InvestorEventLink investorEventLink = investorEventRepo.getByInvestorIdAndEventIdAndLiveInd(creatorId,activityId);
				if(null!=investorEventLink) {
					investorEventLink.setLiveInd(false);
						EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(activityId);
						if (null != eventDetails) {
							eventDetails.setLive_ind(false);
							eventDetailsRepository.save(eventDetails);
						}
						investorEventRepo.save(investorEventLink);
					msg = "Activity Deleted Successfully";
				}
				
			}else if(activityType.equalsIgnoreCase("task")) {
				
				InvestorTaskLink investorTaskLink = investorTaskRepo.getTaskListByInvestorIdAndTaskIdAndLiveInd(creatorId,activityId);
				if(null!=investorTaskLink) {
					investorTaskLink.setLiveInd(false);
					 TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(activityId);
			            if (null != taskDetails) {
			            	taskDetails.setLiveInd(false);
			            	taskDetailsRepository.save(taskDetails);
			            }
			            investorTaskRepo.save(investorTaskLink);
					msg = "Activity Deleted Successfully";
				}
				
			}
			
		}else if(creatorType.equalsIgnoreCase("Investor Leads")) {
			
			if(activityType.equalsIgnoreCase("call")) {
				InvestorLeadsCallLink investorCallLink = investorLeadsCallLinkRepo.getByInvestorLeadsIdAndCallIdAndLiveInd(creatorId,activityId);
				if(null!=investorCallLink) {
					investorCallLink.setLiveInd(false);
						CallDetails callDetails = callDetailsRepository.getCallDetailsById(activityId);
						if (null != callDetails) {
							callDetails.setLive_ind(false);
							callDetailsRepository.save(callDetails);
						}
						investorLeadsCallLinkRepo.save(investorCallLink);
					msg = "Activity Deleted Successfully";
				}
				
			}else if(activityType.equalsIgnoreCase("event")) {
				
				InvestorLeadsEventLink investorLeadsEventLink = investorLeadsEventRepo.getByInvestorLeadsIdAndEventIdAndLiveInd(creatorId,activityId);
				if(null!=investorLeadsEventLink) {
					investorLeadsEventLink.setLiveInd(false);
						EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(activityId);
						if (null != eventDetails) {
							eventDetails.setLive_ind(false);
							eventDetailsRepository.save(eventDetails);
						}
						investorLeadsEventRepo.save(investorLeadsEventLink);
					msg = "Activity Deleted Successfully";
				}
				
			}else if(activityType.equalsIgnoreCase("task")) {
				
				InvestorLeadsTaskLink investorleadsTaskLink = investorLeadsTaskRepo.getTaskListByInvestorLeadsIdAndTaskIdAndLiveInd(creatorId,activityId);
				if(null!=investorleadsTaskLink) {
					investorleadsTaskLink.setLiveInd(false);
					 TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(activityId);
			            if (null != taskDetails) {
			            	taskDetails.setLiveInd(false);
			            	taskDetailsRepository.save(taskDetails);
			            }
			            investorLeadsTaskRepo.save(investorleadsTaskLink);
					msg = "Activity Deleted Successfully";
				}
				
			}
			
		}
		
		return msg;
	}

	@Override
	public List<UserPlannerMapper> getPlannerDataByUserId(String userId, String startDate, String endDate) {
		List<UserPlannerMapper> plannerMapperList = new ArrayList<>();

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("end_date>" + end_date);
		System.out.println("start_date>" + start_date);
		Set<String> eventIds = employeeEventRepository.getByEmployeeId(userId).stream()
				.map(EmployeeEventLink::getEvent_id).collect(Collectors.toSet());
		List<String> eventIdss = new ArrayList<>(eventIds);
		System.out.println("eventIdss>>" + eventIdss.size());
		if (null != eventIdss && !eventIdss.isEmpty()) {
			System.out.println("Starting:1>>>>>>>>>>>>>>>>>>>>>>>");
			List<EventDetails> eventdetailsList = eventDetailsRepository
					.getEventByEventIdsAndDateRange(eventIdss, start_date, end_date);
			System.out.println("Starting:2>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println("eventdetailsList>>" + eventdetailsList.size());
			if (null != eventdetailsList && !eventdetailsList.isEmpty()) {
				for (EventDetails eventDetails : eventdetailsList) {
					System.out.println("Starting:3>>>>>>>>>>>>>>>>>>>>>>>");
					System.out.println("start_date=" + start_date);
					System.out.println("DBstart_date=" + eventDetails.getStart_date());
						
					UserPlannerMapper eventMapper = new UserPlannerMapper();

					eventMapper.setStartDate(Utility.getISOFromDate(eventDetails.getStart_date()));
					eventMapper.setEndDate(Utility.getISOFromDate(eventDetails.getEnd_date()));
					eventMapper.setEndTime(eventDetails.getEnd_time());
					eventMapper.setStartTime(eventDetails.getStart_time());
						
					plannerMapperList.add(eventMapper);

				}
			}
		}
		
		Set<String> callIds = employeeCallRepository.getByEmployeeId(userId).stream()
				.map(EmployeeCallLink::getCall_id).collect(Collectors.toSet());
		List<String> callIdss = new ArrayList<>(callIds);
		if (null != callIdss && !callIdss.isEmpty()) {
			List<CallDetails> callList = callDetailsRepository
					.getCallListByCallIdsAndStartDate(callIdss, start_date, end_date);
			if (null != callList && !callList.isEmpty()) {
				callList.stream().map(callDetails -> {

					UserPlannerMapper callMapper = new UserPlannerMapper();

					callMapper.setEndDate(Utility.getISOFromDate(callDetails.getCall_end_date()));
					callMapper.setStartDate(Utility.getISOFromDate(callDetails.getCall_start_date()));		
					callMapper.setStartTime(callDetails.getCall_start_time());
					callMapper.setEndTime(callDetails.getCall_end_time());
				
					plannerMapperList.add(callMapper);

					return callMapper;
				}).collect(Collectors.toList());
			}
		}
		
		Set<String> taskIds = employeeTaskRepository.getByEmployeeId(userId).stream()
				.map(EmployeeTaskLink::getTask_id).collect(Collectors.toSet());
		List<String> taskIdss = new ArrayList<>(taskIds);
		if (null != taskIdss && !taskIdss.isEmpty()) {

			List<TaskDetails> taskList = taskDetailsRepository
					.getTaskListByTaskIdsAndStartDate(taskIdss, start_date, end_date);
			System.out.println("taskList>>" + taskList.toString());
			if (null != taskList && !taskList.isEmpty()) {
				for (TaskDetails taskDetails : taskList) {
					if (null != taskDetails) {
						UserPlannerMapper taskMapper = new UserPlannerMapper();

						taskMapper.setStartDate(Utility.getISOFromDate(taskDetails.getStart_date()));
						taskMapper.setEndDate(Utility.getISOFromDate(taskDetails.getEnd_date()));
						taskMapper.setStartTime(taskDetails.getStart_time());
						taskMapper.setEndTime(taskDetails.getEnd_time());
							
						plannerMapperList.add(taskMapper);
					}
				}
			}
		}
	
		return plannerMapperList;
	}
}
