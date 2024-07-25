package com.app.employeePortal.task.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
import com.app.employeePortal.Opportunity.entity.OpportunityNotesLink;
import com.app.employeePortal.Opportunity.repository.OpportunityDetailsRepository;
import com.app.employeePortal.Opportunity.repository.OpportunityNotesLinkRepository;
import com.app.employeePortal.candidate.entity.CandidateDetails;
import com.app.employeePortal.candidate.entity.CandidateEmailDetails;
import com.app.employeePortal.candidate.entity.CandidateEmailLink;
import com.app.employeePortal.candidate.entity.DefinationDetails;
import com.app.employeePortal.candidate.entity.SkillSetDetails;
import com.app.employeePortal.candidate.mapper.CandidateEmailDetailsMapper;
import com.app.employeePortal.candidate.repository.CandidateDetailsRepository;
import com.app.employeePortal.candidate.repository.CandidateEmailDetailsRepository;
import com.app.employeePortal.candidate.repository.CandidateEmailLinkRepository;
import com.app.employeePortal.candidate.repository.DefinationRepository;
import com.app.employeePortal.candidate.repository.SkillSetRepository;
import com.app.employeePortal.candidate.service.CandidateService;
import com.app.employeePortal.category.entity.Development;
import com.app.employeePortal.category.entity.Hour;
import com.app.employeePortal.category.entity.RoleType;
import com.app.employeePortal.category.entity.TaskChecklist;
import com.app.employeePortal.category.entity.TaskChecklistStageLink;
import com.app.employeePortal.category.repository.DevelopmentRepository;
import com.app.employeePortal.category.repository.HourRepository;
import com.app.employeePortal.category.repository.RoleTypeRepository;
import com.app.employeePortal.category.repository.TaskChecklistRepository;
import com.app.employeePortal.category.repository.TaskChecklistStageLinkRepository;
import com.app.employeePortal.category.service.CategoryService;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.entity.ContactDocumentLink;
import com.app.employeePortal.contact.entity.ContactNotesLink;
import com.app.employeePortal.contact.entity.ContactUserLink;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.repository.ContactDocumentLinkRepository;
import com.app.employeePortal.contact.repository.ContactNotesLinkRepository;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.contact.repository.ContactUserLinkRepository;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.entity.CustomerContactLink;
import com.app.employeePortal.customer.entity.CustomerDocumentLink;
import com.app.employeePortal.customer.repository.CustomerContactLinkRepository;
import com.app.employeePortal.customer.repository.CustomerDocumentLinkRepository;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.distributor.entity.Distributor;
import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.document.repository.DocumentDetailsRepository;
import com.app.employeePortal.document.repository.DocumentTypeRepository;
import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.Notes;
import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.NotesRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.entity.EventDetails;
import com.app.employeePortal.event.entity.EventType;
import com.app.employeePortal.event.mapper.EventViewMapper;
import com.app.employeePortal.expense.entity.ExpenseDetails;
import com.app.employeePortal.expense.entity.ExpenseNotesLink;
import com.app.employeePortal.expense.repository.ExpenseNotesLinkRepository;
import com.app.employeePortal.expense.repository.ExpenseRepository;
import com.app.employeePortal.investor.entity.InvestorDocumentLink;
import com.app.employeePortal.investor.repository.InvestorDocumentLinkRepo;
import com.app.employeePortal.investorleads.entity.InvestorLeadsDocumentLink;
import com.app.employeePortal.investorleads.repository.InvestorLeadsDocumentLinkRepository;
import com.app.employeePortal.leads.entity.LeadsDocumentLink;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.leads.repository.LeadsDocumentLinkRepository;
import com.app.employeePortal.leave.entity.LeaveDetails;
import com.app.employeePortal.leave.entity.LeaveNotesLink;
import com.app.employeePortal.leave.repository.LeaveDetailsRepository;
import com.app.employeePortal.leave.repository.LeaveNotesLinkRepository;
import com.app.employeePortal.mileage.entity.MileageNotesLink;
import com.app.employeePortal.mileage.repository.MileageNotesLinkRepository;
import com.app.employeePortal.notification.entity.NotificationDetails;
import com.app.employeePortal.notification.entity.Notificationparam;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.permission.entity.ThirdParty;
import com.app.employeePortal.permission.repository.ThirdPartyRepository;
import com.app.employeePortal.processApproval.entity.ApprovalLevel;
import com.app.employeePortal.processApproval.repository.ApprovalLevelRepository;
import com.app.employeePortal.processApproval.service.ProcessApprovalService;
import com.app.employeePortal.project.Entity.ProjectDetails;
import com.app.employeePortal.project.Repository.ProjectRepository;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.task.entity.CandidateTaskLink;
import com.app.employeePortal.task.entity.ContactTaskLink;
import com.app.employeePortal.task.entity.CustomerTaskLink;
import com.app.employeePortal.task.entity.EmployeeSubTaskLink;
import com.app.employeePortal.task.entity.EmployeeTaskLink;
import com.app.employeePortal.task.entity.InvestorLeadsTaskLink;
import com.app.employeePortal.task.entity.InvestorTaskLink;
import com.app.employeePortal.task.entity.LastActivityLog;
import com.app.employeePortal.task.entity.LeadsTaskLink;
import com.app.employeePortal.task.entity.RoomTaskLink;
import com.app.employeePortal.task.entity.SubTaskDetails;
import com.app.employeePortal.task.entity.TaskComment;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.entity.TaskDocumentLink;
import com.app.employeePortal.task.entity.TaskIncludedLink;
import com.app.employeePortal.task.entity.TaskInfo;
import com.app.employeePortal.task.entity.TaskNotesLink;
import com.app.employeePortal.task.entity.TaskSteps;
import com.app.employeePortal.task.entity.TaskType;
import com.app.employeePortal.task.entity.TaskTypeDelete;
import com.app.employeePortal.task.mapper.ApprovedStatusMapper;
import com.app.employeePortal.task.mapper.CandidateViewMapper;
import com.app.employeePortal.task.mapper.SubTaskMapper;
import com.app.employeePortal.task.mapper.SubViewTaskMapper;
import com.app.employeePortal.task.mapper.TaskCommentMapper;
import com.app.employeePortal.task.mapper.TaskDragDropMapper;
import com.app.employeePortal.task.mapper.TaskMapper;
import com.app.employeePortal.task.mapper.TaskStepsMapper;
import com.app.employeePortal.task.mapper.TaskTypeDropMapper;
import com.app.employeePortal.task.mapper.TaskViewMapper;
import com.app.employeePortal.task.mapper.TeamEmployeeMapper;
import com.app.employeePortal.task.repository.CandidateTaskLinkRepository;
import com.app.employeePortal.task.repository.ContactTaskRepo;
import com.app.employeePortal.task.repository.CustomerTaskRepo;
import com.app.employeePortal.task.repository.EmployeeSubTaskLinkRepository;
import com.app.employeePortal.task.repository.EmployeeTaskRepository;
import com.app.employeePortal.task.repository.InvestorLeadsTaskRepo;
import com.app.employeePortal.task.repository.InvestorTaskRepo;
import com.app.employeePortal.task.repository.LastActivityLogRepository;
import com.app.employeePortal.task.repository.LeadsTaskRepo;
import com.app.employeePortal.task.repository.SubTaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskCommentRepository;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskDocumentLinkRepository;
import com.app.employeePortal.task.repository.TaskIncludedLinkRepository;
import com.app.employeePortal.task.repository.TaskInfoRepository;
import com.app.employeePortal.task.repository.TaskNotesLinkRepository;
import com.app.employeePortal.task.repository.TaskStepsRepo;
import com.app.employeePortal.task.repository.TaskTypeDeleteRepository;
import com.app.employeePortal.task.repository.TaskTypeRepository;
import com.app.employeePortal.util.Utility;
import com.app.employeePortal.voucher.entity.VoucherDetails;
import com.app.employeePortal.voucher.entity.VoucherExpenseLink;
import com.app.employeePortal.voucher.repository.VoucherExpenseRepository;
import com.app.employeePortal.voucher.repository.VoucherRepository;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import com.app.employeePortal.distributor.repository.*;
import com.app.employeePortal.task.repository.*;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskInfoRepository taskInfoRepository;

    @Autowired
    TaskDetailsRepository taskDetailsRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EmployeeTaskRepository employeeTaskRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    LeaveDetailsRepository leaveDetailsRepository;

    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    CandidateDetailsRepository candidateDetailsRepository;
    @Autowired
    TaskTypeRepository taskTypeRepository;
    @Autowired
    CandidateTaskLinkRepository candidateTaskLinkRepository;
    @Autowired
    CustomerContactLinkRepository customerContactLinkRepository;
    @Autowired
    ContactUserLinkRepository contactUserLinkRepository;
    @Autowired
    ContactService contactService;
    @Autowired
	DocumentService documentService;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    CandidateEmailDetailsRepository candidateEmailDetailsRepository;
    @Autowired
    CandidateEmailLinkRepository candidateEmailLinkRepository;
    @Autowired
    SkillSetRepository skillSetRepository;
    @Autowired
    DefinationRepository definationRepository;
    @Autowired
    RoleTypeRepository roleTypeRepository;
    @Autowired
    ProcessApprovalService processApprovalService;
    @Autowired
    TaskTypeDeleteRepository taskTypeDeleteRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    CandidateService candidateService;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TaskChecklistRepository taskChecklistRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TaskCommentRepository taskCommentRepository;
    @Autowired
    HourRepository hourRepository;
    @Autowired
    VoucherExpenseRepository voucherExpenseRepository;
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    SubTaskDetailsRepository subTaskDetailsRepository;
    @Autowired
    EmployeeSubTaskLinkRepository employeeSubTaskLinkRepository;
    @Autowired
    TaskChecklistStageLinkRepository taskChecklistStageLinkRepository;
    @Autowired
    ApprovalLevelRepository approvalLevelRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    LeadsTaskRepo leadsTaskRepo;
    @Autowired
    DocumentDetailsRepository documentDetailsRepository;
    @Autowired
    TaskDocumentLinkRepository taskDocumentLinkRepository;
    @Autowired
    DocumentTypeRepository documentTypeRepository;
    @Autowired
    InvestorLeadsTaskRepo investorLeadsTaskRepo;
    @Autowired
    OpportunityDetailsRepository opportunityDetailsRepository;
    @Autowired
    CustomerTaskRepo customerTaskRepo;
    @Autowired
    ContactTaskRepo contactTaskRepo;
    @Autowired
    TaskNotesLinkRepository taskNotesLinkRepository;
    @Autowired
    NotesRepository notesRepository;
    @Autowired
	ContactNotesLinkRepository contactNotesLinkRepository;
    @Autowired
	OpportunityNotesLinkRepository opportunityNotesLinkRepository;
    @Autowired
	LeaveNotesLinkRepository leaveNotesLinkRepository;
    @Autowired
	MileageNotesLinkRepository mileageNotesLinkRepository;
    @Autowired
	ExpenseNotesLinkRepository expenseNotesLinkRepository;
    @Autowired
    InvestorTaskRepo investorTaskRepo;
    @Autowired
    TaskIncludedLinkRepository taskIncludedLinkRepository;
    @Autowired
    LastActivityLogRepository lastActivityLogRepository;
    @Autowired 
    ContactDocumentLinkRepository contactDocumentLinkRepository;
	@Autowired 
	CustomerDocumentLinkRepository customerDocumentLinkRepository;
	@Autowired 
	InvestorDocumentLinkRepo investorDocumentLinkRepo;
	@Autowired
	InvestorLeadsDocumentLinkRepository investorLeadsDocumentLinkRepository;
	@Autowired 
	LeadsDocumentLinkRepository leadsDocumentLinkRepository;
	@Autowired DistributorRepository distributorRepository;
	@Autowired RoomTaskRepo roomTaskRepo;
    
    
	private String[] taskType_headings = {"Name"};

    @Autowired
    NotificationService notificationService;
    
    final Configuration configuration;

    public TaskServiceImpl(Configuration configuration) {
        this.configuration = configuration;
    }
    @Value("${companyName}")
	private String companyName;

    @Autowired
    TaskStepsRepo taskStepsRepo;
    @Autowired
    DevelopmentRepository developmentRepository;
    @Override
    public TaskViewMapper saveTaskProcess(TaskMapper taskMapper,String through) throws IOException, TemplateException {

        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setCreation_date(new Date());
        TaskInfo info = taskInfoRepository.save(taskInfo);

        String taskId = info.getTask_id();
        TaskViewMapper resultMapper = null;
        if (null != taskId) {

            /* insert to task details table */

            TaskDetails taskDetails = new TaskDetails();
            taskDetails.setTask_id(taskId);
            taskDetails.setCreation_date(new Date());
            taskDetails.setTask_description(taskMapper.getTaskDescription());
            taskDetails.setPriority(taskMapper.getPriority());
            taskDetails.setTask_type(taskMapper.getTaskTypeId());
            taskDetails.setTask_name(taskMapper.getTaskName());
            taskDetails.setTask_status(taskMapper.getTaskStatus());
            taskDetails.setUser_id(taskMapper.getUserId());
            taskDetails.setOrganization_id(taskMapper.getOrganizationId());
            taskDetails.setTime_zone(taskMapper.getTimeZone());
            taskDetails.setLiveInd(true);
            taskDetails.setComplitionInd(false);
            taskDetails.setRating(0);
            taskDetails.setAssigned_to(taskMapper.getAssignedTo());
            taskDetails.setProjectName(taskMapper.getProjectName());
            taskDetails.setValue(taskMapper.getValue());
            taskDetails.setUnit(taskMapper.getUnit());
            taskDetails.setImageId(taskMapper.getImageId());
//            taskDetails.setDocumentId(taskMapper.getDocumentId());
            taskDetails.setComplexity(taskMapper.getComplexity());
            taskDetails.setCustomerId(taskMapper.getCustomerId());
            taskDetails.setTaskChecklistId(taskMapper.getTaskChecklistId());
            //taskDetails.setRatePerUnit(taskMapper.getRatePerUnit());
            taskDetails.setLink(taskMapper.getLink());
            taskDetails.setContact(taskMapper.getContact());
            taskDetails.setOppertunity(taskMapper.getOppertunity());
            taskDetails.setAssignedBy(taskMapper.getUserId());
            
            
            try {
            	if(!StringUtils.isEmpty(taskMapper.getStartDate())){
                taskDetails.setStart_date(Utility.getDateFromISOString(taskMapper.getStartDate()));
            	}else {
            		taskDetails.setStart_date(new Date());
            	}
                taskDetails.setEnd_date(Utility.getDateFromISOString(taskMapper.getEndDate()));
                taskDetails.setAssignedDate(Utility.getDateFromISOString(taskMapper.getAssignedDate()));

            } catch (Exception e) {
                e.printStackTrace();
            }

            taskDetailsRepository.save(taskDetails);

        }

        /* insert to employee call link table */
//		String assignTo = "";
//		if(!StringUtils.isEmpty(taskMapper.getAssignedTo())) {
//				assignTo  = employeeService.getEmployeeFullName(taskMapper.getAssignedTo());
//		}
        
        String ownerName = employeeService.getEmployeeFullName(taskMapper.getUserId());

        List<String> empList = new ArrayList<>();
        empList.add(taskMapper.getUserId());
        if (!StringUtils.isEmpty(taskMapper.getAssignedTo()) && 
        		!taskMapper.getUserId().equals(taskMapper.getAssignedTo())) {
            empList.add(taskMapper.getAssignedTo());
        }
        if(taskMapper.getIncluded()!=null && taskMapper.getIncluded().isEmpty()) {
        	List<String> includedids = new ArrayList<>(taskMapper.getIncluded());
        	empList.addAll(includedids);
        	 /*insert into task include table*/
         	   for (String id : taskMapper.getIncluded()) {
         	   TaskIncludedLink  taskIncludedLink  =new TaskIncludedLink();
         	   taskIncludedLink.setTaskId(taskId);
         	   taskIncludedLink.setEmployeeId(id);
         	   taskIncludedLink.setCreationDate(new Date());
         	   taskIncludedLink.setLiveInd(true);
         	   taskIncludedLink.setOrgId(taskMapper.getOrganizationId()); 	   
         	   taskIncludedLinkRepository.save(taskIncludedLink);
         	   }
        }
        if (null != empList && !empList.isEmpty()) {
            // empList.stream().map(employeeId->{
            empList.forEach(employeeId -> {
                EmployeeTaskLink employeeTaskLink = new EmployeeTaskLink();
                employeeTaskLink.setTask_id(taskId);
                employeeTaskLink.setEmployee_id(employeeId);
                employeeTaskLink.setCreation_date(new Date());
                employeeTaskLink.setLive_ind(true);
                employeeTaskRepository.save(employeeTaskLink);
            });

        }

        /* insert to CandidateEventLink table */
        Set<String> candidateIds = taskMapper.getCandidateId();
        if (null != candidateIds && !candidateIds.isEmpty()) {
            candidateIds.forEach(candidateId -> {
                CandidateTaskLink candidateTaskLink = new CandidateTaskLink();
                candidateTaskLink.setTaskId(taskId);
                candidateTaskLink.setCandidateId(candidateId);
                candidateTaskLink.setCreationdate(new Date());
                candidateTaskLink.setLiveInd(true);
                candidateTaskLink.setComplitionStatus("To Start");
                candidateTaskLinkRepository.save(candidateTaskLink);
				updateActivityTaskLog("candidate", taskId, candidateId, taskMapper);

                /* insert to Notification info */
                NotificationDetails notification = new NotificationDetails();
                notification.setNotificationType("task");
                notification
                        .setMessage(ownerName + " has assigned to a " + notification.getNotificationType() + " you ");
                notification.setAssignedTo(candidateId);
                notification.setUser_id(taskMapper.getUserId());
                notification.setNotificationDate(new Date());
                notification.setOrg_id(notification.getOrg_id());
                notification.setMessageReadInd(false);
                notificationRepository.save(notification);

            });
        }
        if (!StringUtils.isEmpty(taskMapper.getLeadsId())) {
            LeadsTaskLink leadsTaskLink = new LeadsTaskLink();
            leadsTaskLink.setTaskId(taskId);
            leadsTaskLink.setLeadsId(taskMapper.getLeadsId());
            leadsTaskLink.setCreationDate(new Date());
            leadsTaskLink.setLiveInd(true);
            leadsTaskLink.setComplitionStatus("To Start");
            leadsTaskRepo.save(leadsTaskLink);
            updateActivityTaskLog("leads", taskId, taskMapper.getLeadsId(), taskMapper);
        }
        /* insert into documentLink table */
        if (null != taskMapper.getDocumentId() && !taskMapper.getDocumentId().isEmpty()) {
//        	for (String docId : taskMapper.getDocumentIds()) {
			TaskDocumentLink taskDocumentLink = new TaskDocumentLink();
			taskDocumentLink.setTaskId(taskId);
			taskDocumentLink.setDocumentId(taskMapper.getDocumentId());
			taskDocumentLink.setCreationDate(new Date());
			taskDocumentLinkRepository.save(taskDocumentLink);
//        	}
		}
        
        /* insert into InvestorLeads table */
        if (!StringUtils.isEmpty(taskMapper.getInvestorLeadsId())) {
            InvestorLeadsTaskLink investorleadsTaskLink = new InvestorLeadsTaskLink();
            investorleadsTaskLink.setTaskId(taskId);
            investorleadsTaskLink.setInvestorLeadsId(taskMapper.getInvestorLeadsId());
            investorleadsTaskLink.setCreationDate(new Date());
            investorleadsTaskLink.setLiveInd(true);
            investorleadsTaskLink.setComplitionStatus("To Start");
            investorLeadsTaskRepo.save(investorleadsTaskLink);
            updateActivityTaskLog("investorLeads", taskId, taskMapper.getInvestorLeadsId(), taskMapper);
        }
        
        /* insert into contact table */
        if (!StringUtils.isEmpty(taskMapper.getContact())) {
        	ContactTaskLink contactTaskLink = new ContactTaskLink();
        	contactTaskLink.setTaskId(taskId);
        	contactTaskLink.setContactId(taskMapper.getContact());
        	contactTaskLink.setCreationDate(new Date());
        	contactTaskLink.setLiveInd(true);
        	contactTaskLink.setComplitionStatus("To Start");
            contactTaskRepo.save(contactTaskLink);
            updateActivityTaskLog("contact", taskId, taskMapper.getInvestorLeadsId(), taskMapper);
        }
        
        /* insert into customer table */
        if (!StringUtils.isEmpty(taskMapper.getCustomerId())) {
        	CustomerTaskLink customerTaskLink = new CustomerTaskLink();
        	customerTaskLink.setTaskId(taskId);
        	customerTaskLink.setCustomerId(taskMapper.getCustomerId());
        	customerTaskLink.setCreationDate(new Date());
        	customerTaskLink.setLiveInd(true);
        	customerTaskLink.setComplitionStatus("To Start");
        	customerTaskRepo.save(customerTaskLink);
        	updateActivityTaskLog("customer", taskId, taskMapper.getCustomerId(), taskMapper);
        }
        /* insert into Investor table */
        if (!StringUtils.isEmpty(taskMapper.getInvestorId())) {
            InvestorTaskLink investorTaskLink = new InvestorTaskLink();
            investorTaskLink.setTaskId(taskId);
            investorTaskLink.setInvestorId(taskMapper.getInvestorId());
            investorTaskLink.setCreationDate(new Date());
            investorTaskLink.setLiveInd(true);
            investorTaskLink.setComplitionStatus("To Start");
            investorTaskRepo.save(investorTaskLink);
            updateActivityTaskLog("investor", taskId, taskMapper.getInvestorId(), taskMapper);
        }
    
        /* insert into Room table */
        if (!StringUtils.isEmpty(taskMapper.getRoomId())) {
        	RoomTaskLink link = new RoomTaskLink();
        	link.setTaskId(taskId);
        	link.setRoomId(taskMapper.getRoomId());
        	link.setCreationDate(new Date());
        	link.setLiveInd(true);
        	link.setComplitionStatus("To Start");
            roomTaskRepo.save(link);
//            updateActivityTaskLog("room", taskId, taskMapper.getRoomId(), taskMapper);
        }
        
       Notes notes = new Notes();
		notes.setCreation_date(new Date());
		notes.setNotes(taskMapper.getTaskDescription());
		notes.setLiveInd(true);
		notes.setUserId(taskMapper.getUserId());
		Notes note = notesRepository.save(notes);

		String notesId = note.getNotes_id();
		/* insert to TaskNotesLink table */

		TaskNotesLink taskNote = new TaskNotesLink();
		taskNote.setTaskId(taskId);
		taskNote.setNotesId(notesId);
		taskNote.setCreationDate(new Date());
		taskNotesLinkRepository.save(taskNote);
        
		
		String msg = "";
		if(through.equalsIgnoreCase("normal")) {
		if (taskMapper.getTaskDescription() != null && !taskMapper.getTaskDescription().isEmpty() && !taskMapper.getLink().isEmpty() && taskMapper.getLink() != null) {
			msg = "\n"+"Notes :"+taskMapper.getTaskDescription()+"\n"+"Link :"+taskMapper.getLink();
		} else if (taskMapper.getTaskDescription() == null && taskMapper.getTaskDescription().isEmpty() && taskMapper.getLink() != null && !taskMapper.getLink().isEmpty()) {
			msg = "\n"+"Link :"+taskMapper.getLink();
		} else if (taskMapper.getTaskDescription() != null && !taskMapper.getTaskDescription().isEmpty() && taskMapper.getLink() == null && taskMapper.getLink().isEmpty()) {
			msg = "\n"+"Notes :"+taskMapper.getTaskDescription();
		} 
		
		if (taskMapper.getCustomerId() != null && !taskMapper.getCustomerId().isEmpty() && !taskMapper.getContact().isEmpty() && taskMapper.getContact() != null) {
			Customer customer = customerRepository.getcustomerDetailsById(taskMapper.getCustomerId());
			ContactDetails contact = contactRepository.getcontactDetailsById(taskMapper.getContact());
					msg = "\n"+customer.getName()+"\n"+ Utility.FullName(contact.getFirst_name(),contact.getMiddle_name(),contact.getLast_name());
		} else if (taskMapper.getCustomerId() == null && taskMapper.getCustomerId().isEmpty() && taskMapper.getContact() != null && !taskMapper.getContact().isEmpty()) {
			ContactDetails contact = contactRepository.getcontactDetailsById(taskMapper.getContact());
			msg = "\n"+Utility.FullName(contact.getFirst_name(),contact.getMiddle_name(),contact.getLast_name());
		} else if (taskMapper.getCustomerId() != null && !taskMapper.getCustomerId().isEmpty() && taskMapper.getContact() == null && taskMapper.getContact().isEmpty()) {
			Customer customer = customerRepository.getcustomerDetailsById(taskMapper.getCustomerId());
			msg = "\n"+customer.getName();
		}
		}
	//AxisDigital//Prospect
    // Albert// Contact Name
	    
		/* insert to Notification Table */		
		Notificationparam param = new Notificationparam();
		            EmployeeDetails emp = employeeRepository.getEmployeesByuserId(taskMapper.getUserId());
		            String name = employeeService.getEmployeeFullNameByObject(emp);
		            param.setEmployeeDetails(emp);
		            param.setAdminMsg("Task scheduled for "+taskMapper.getStartDate()+", "+"'"+taskMapper.getTaskName() + "' created by "+name+msg);
		            param.setOwnMsg("Task scheduled for "+taskMapper.getStartDate()+", "+"'"+taskMapper.getTaskName() +"' created."+msg);
		            param.setNotificationType("Task creation"); 
		            param.setProcessNmae("Task");
		            param.setType("create");
		            param.setEmailSubject("Korero alert - Task created");
		            param.setCompanyName(companyName);
		            param.setUserId(taskMapper.getUserId());   
		            
		            if(!taskMapper.getUserId().equals(taskMapper.getAssignedTo())) {
		            	List<String> assignToUserIds = new ArrayList<>();
		            	assignToUserIds.add(taskMapper.getAssignedTo());
		            	param.setAssignToUserIds(assignToUserIds); 
		                param.setAssignToMsg("Task "+"'"+taskMapper.getTaskName() + "' on "+ taskMapper.getStartDate()+ " assigned to "+employeeService.getEmployeeFullName(taskMapper.getAssignedTo())+" by "+name+msg);
		            }
		            if (null != taskMapper.getIncluded() && !taskMapper.getIncluded().isEmpty()) {
		            	List<String> includedids = new ArrayList<>(taskMapper.getIncluded());
		            param.setIncludeedUserIds(includedids); 
	                param.setIncludeMsg("You have been added to the Task "+"'"+taskMapper.getTaskName() + "' on " + taskMapper.getStartDate()+" by "+name+msg);
		            }
		            notificationService.createNotificationForDynamicUsers(param);
        resultMapper = getTaskDetails(taskId);
        return resultMapper;
    }

    @Override
    public TaskViewMapper getTaskDetails(String taskId) {

        TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskId);

        TaskViewMapper taskMapper = new TaskViewMapper();

        if (null != taskDetails) {

            if (taskDetails.getTask_type() != null && taskDetails.getTask_type().trim().length() > 0) {
                TaskType taskType = taskTypeRepository.findByTaskTypeId(taskDetails.getTask_type());
                if (null != taskType) {
                    taskMapper.setTaskType(taskType.getTaskType());
                    taskMapper.setTaskTypeId(taskType.getTaskTypeId());
                } else {
                    taskMapper.setTaskType(taskDetails.getTask_type());
                    if (taskDetails.getTask_type().equalsIgnoreCase("expense")) {
                        VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsByTaskId(taskId);
                        if (null != voucherDetails) {
                            List<VoucherExpenseLink> voucherExpenseLink = voucherExpenseRepository.getExpenseListByVoucherId(voucherDetails.getVoucher_id());
                            if (null != voucherExpenseLink && !voucherExpenseLink.isEmpty()) {
                                List<String> documentIds = new ArrayList<>();
                                for (VoucherExpenseLink link : voucherExpenseLink) {
                                    ExpenseDetails expenseDetails = expenseRepository.getExpenseDetailsById(link.getExpense_id());
                                    if (null != expenseDetails) {
                                        documentIds.add(expenseDetails.getDocumentId());
                                    }
                                }
                                taskMapper.setDocumentIds(documentIds);
                            }
                        }
                    }
                }
            }

            taskMapper.setTaskId(taskId);
            taskMapper.setEndDate(Utility.getISOFromDate(taskDetails.getEnd_date()));
            taskMapper.setStartDate(Utility.getISOFromDate(taskDetails.getStart_date()));
            taskMapper.setTaskDescription(taskDetails.getTask_description());
            taskMapper.setPriority(taskDetails.getPriority());
            taskMapper.setTaskStatus(taskDetails.getTask_status());
            taskMapper.setTaskName(taskDetails.getTask_name());
            taskMapper.setTimeZone(taskDetails.getTime_zone());
            taskMapper.setUserId(taskDetails.getUser_id());
            taskMapper.setOrganizationId(taskDetails.getOrganization_id());
            taskMapper.setAssignedOn(Utility.getISOFromDate(taskDetails.getCreation_date()));
            taskMapper.setCreationDate(Utility.getISOFromDate(taskDetails.getCreation_date()));
            taskMapper.setUpdateDate(Utility.getISOFromDate(taskDetails.getUpdateDate()));
            taskMapper.setCompletionInd(taskDetails.isComplitionInd());
            taskMapper.setRating(taskDetails.getRating());
            taskMapper.setLink(taskDetails.getLink());           
            taskMapper.setProjectId(taskDetails.getProjectName());
            System.out.println("taskDetails.getProjectName()==================" + taskDetails.getProjectName());
            if (!StringUtils.isEmpty(taskDetails.getProjectName())) {
                ProjectDetails projectDetails = projectRepository.getById(taskDetails.getProjectName());
                if (null != projectDetails) {
                    taskMapper.setProjectName(projectDetails.getProjectName());
                }
            }
            taskMapper.setValue(taskDetails.getValue());
            taskMapper.setUnit(taskDetails.getUnit());
            taskMapper.setImageId(taskDetails.getImageId());
//            taskMapper.setDocumentId(taskDetails.getDocumentId());
            taskMapper.setComplexity(taskDetails.getComplexity());
            taskMapper.setCustomerId(taskDetails.getCustomerId());
            taskMapper.setAssignedBy(employeeService.getEmployeeFullName(taskDetails.getAssignedBy()));
            taskMapper.setAssignedToName(employeeService.
                    getEmployeeFullNameAndEmployeeId(taskDetails.getAssigned_to()).getEmpName());
            taskMapper.setAssignedTo(taskDetails.getAssigned_to());
            taskMapper.setOwnerName(employeeService.
                    getEmployeeFullNameAndEmployeeId(taskDetails.getUser_id()).getEmpName());
            taskMapper.setTaskChecklistId(taskDetails.getTaskChecklistId());
            TaskChecklist taskChecklist = taskChecklistRepository.findByTaskChecklistId(taskDetails.getTaskChecklistId());
            if (null != taskChecklist) {
                taskMapper.setTaskChecklist(taskChecklist.getTaskChecklistName());
            }
            taskMapper.setTaskChecklistStageLinkMapper(categoryService.getAllTaskChecklistStageLinkByTaskChecklistId(taskDetails.getTaskChecklistId()));

            if(!StringUtils.isEmpty(taskDetails.getCustomerId())){
            if(taskDetails.getTask_type().equalsIgnoreCase("Contact To User")) {
            	Distributor distributor = distributorRepository.findById(taskDetails.getCustomerId()).get();
            	if(null != distributor) {
            		taskMapper.setCustomerName(distributor.getName());
            	}
            	}else {
            		Customer customer = customerRepository
                    .getCustomerDetailsByCustomerId(taskDetails.getCustomerId());
            if (null != customer) {
                taskMapper.setCustomerName(customer.getName());
            }
            }
        }
            taskMapper.setAssignedDate(Utility.getISOFromDate(taskDetails.getAssignedDate()));

            //taskMapper.setRatePerUnit(taskDetails.getRatePerUnit());

            int complitionCount = 0;
            List<CandidateTaskLink> candidateTaskLink = candidateTaskLinkRepository.getCandidateTaskLinkByTaskId(taskId);
            if (null != candidateTaskLink) {
                List<CandidateViewMapper> candiList = new ArrayList<>();
                for (CandidateTaskLink candidateTaskLink2 : candidateTaskLink) {

                    if (!StringUtils.isEmpty(candidateTaskLink2.getComplitionStatus())) {
                        if (candidateTaskLink2.getComplitionStatus().equalsIgnoreCase("completed")) {
                            complitionCount++;
                        }
                    }

                    CandidateViewMapper mapper = new CandidateViewMapper();
                    CandidateDetails candidateDetails = candidateDetailsRepository
                            .getcandidateDetailsById(candidateTaskLink2.getCandidateId());
                    if (null != candidateDetails) {
                        mapper.setCandidateId(candidateDetails.getCandidateId());
                        String middleName = " ";
                        String lastName = " ";
                        if (null != candidateDetails.getMiddleName()) {

                            middleName = candidateDetails.getMiddleName();
                        }
                        if (null != candidateDetails.getLastName()) {

                            lastName = candidateDetails.getLastName();
                        }

                        mapper.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
                    } else {

                        mapper.setCandidateId("");
                        mapper.setCandidateName("");
                    }
                    candiList.add(mapper);
                }
                taskMapper.setCandidates(candiList);

                System.out.println("complitionCount======" + complitionCount + "==========taskId++++++++++++++++++" + taskId);

                String completionStatus = null;

                if (complitionCount == 0) {
                    completionStatus = "To Start";
                } else if (complitionCount > 0 && complitionCount < candidateTaskLink.size()) {
                    completionStatus = "In Progress";
                } else if (complitionCount == candidateTaskLink.size()) {
                    completionStatus = "completed";
                }
                System.out.println("completionStatus======" + completionStatus + "============taskId++++++++++++++++++" + taskId + "=======size" + candidateTaskLink.size());
                taskMapper.setComplitionStatus(completionStatus);

            }
            // set event owner list
//            List<EmployeeShortMapper> empList = new ArrayList<>();
            List<EmployeeShortMapper> empList = new ArrayList<EmployeeShortMapper>();
            List<EmployeeTaskLink> employeeList = employeeTaskRepository.getEmpListByTaskId(taskId);
            boolean filterTaskInd = false;
            if (null != employeeList && !employeeList.isEmpty()) {
                filterTaskInd = employeeList.get(0).isFilterTaskInd();  
                for (EmployeeTaskLink employeeCallLink : employeeList) {
                if (!employeeCallLink.getEmployee_id().equals(taskDetails.getAssigned_to())) {
					if (!employeeCallLink.getEmployee_id().equals(taskDetails.getUser_id())) {
						EmployeeShortMapper employeeMapper = new EmployeeShortMapper();

						String employeeId = "";
						EmployeeDetails employeeDetails2 = employeeRepository
								.getEmployeeByUserId(employeeCallLink.getEmployee_id());
						if (null != employeeDetails2) {
							String middleName = " ";
							String lastName = " ";

							if (null != employeeDetails2.getLastName()) {

								lastName = employeeDetails2.getLastName();
							}
							if (null != employeeDetails2.getMiddleName()) {

								middleName = employeeDetails2.getMiddleName();
								employeeMapper.setEmpName(
										employeeDetails2.getFirstName() + " " + middleName + " " + lastName);
							} else {
								employeeMapper.setEmpName(employeeDetails2.getFirstName() + " " + lastName);
							}
							employeeId = employeeDetails2.getEmployeeId();
						}

						employeeMapper.setEmployeeId(employeeId);
						// employeeMapper.setUserId(employeeId);

						empList.add(employeeMapper);
					}
				}
                }
                taskMapper.setIncluded(empList);
            }
            if(!StringUtils.isEmpty(taskDetails.getContact())){
            ContactDetails contact = contactRepository.getcontactDetailsById(taskDetails.getContact());
    		if (contact != null) {
                if (contact.getFirst_name() != null && contact.getLast_name() != null) {
                	taskMapper.setContact(contact.getFirst_name() + " " + contact.getLast_name());
                }
            }
            }
    		OpportunityDetails opportunityDetails = opportunityDetailsRepository.getopportunityDetailsById(taskDetails.getOppertunity());
    		if (opportunityDetails != null) {
    			taskMapper.setOppertunity(opportunityDetails.getOpportunityName());
                
            }

//            taskMapper.setOwner(empList);
            taskMapper.setFilterTaskInd(filterTaskInd);
            if (null != taskDetails.getUser_id()) {
                System.out.println("user_id=========" + taskDetails.getUser_id());
//		EmployeeViewMapper empMapper = employeeService.getEmployeeDetailsByEmployeeId(taskDetails.getUser_id());
                EmployeeDetails employee = employeeRepository.getEmployeesByuserId(taskDetails.getUser_id());
                if (null != employee) {
                    taskMapper.setSubmittedBy(employee.getEmailId());
                    System.out.println("submittedBy........" + employee.getEmailId());

                }
            }
            if (null != taskDetails.getApproved_date()) {

                taskMapper.setApprovedDate(Utility.getISOFromDate(taskDetails.getApproved_date()));

            }
            taskMapper.setApprovedInd(taskDetails.getApproved_ind());
            if(taskDetails.getTask_id()!=null&&taskDetails.getTask_id().trim().length()>0) {
            	List<String> includedIds = taskIncludedLinkRepository.findByTaskId(taskDetails.getTask_id()).stream()
						.map(TaskIncludedLink::getEmployeeId).collect(Collectors.toList());
				List<EmployeeShortMapper> included = new ArrayList<>();
					if(null!=includedIds && !includedIds.isEmpty()) {
						for (String includedId : includedIds) {
							EmployeeShortMapper employeeMapper = employeeService.getEmployeeFullNameAndEmployeeId(includedId);
							included.add(employeeMapper);
						}
						taskMapper.setIncluded(included);
					}
            }
        }
        return taskMapper;
    }

	@Override
    public TaskViewMapper getTaskDetailsOjectResponse(TaskDetails taskDetails) {
        TaskViewMapper taskMapper = new TaskViewMapper();

        if (null != taskDetails) {

            if (taskDetails.getTask_type() != null && taskDetails.getTask_type().trim().length() > 0) {
                TaskType taskType = taskTypeRepository.findByTaskTypeId(taskDetails.getTask_type());
                if (null != taskType) {
                    taskMapper.setTaskType(taskType.getTaskType());
                    taskMapper.setTaskTypeId(taskType.getTaskTypeId());
                } else {
                    taskMapper.setTaskType(taskDetails.getTask_type());
                    if (taskDetails.getTask_type().equalsIgnoreCase("expense")) {
                        VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsByTaskId(taskDetails.getTask_id());
                        if (null != voucherDetails) {
                            List<VoucherExpenseLink> voucherExpenseLink = voucherExpenseRepository.getExpenseListByVoucherId(voucherDetails.getVoucher_id());
                            if (null != voucherExpenseLink && !voucherExpenseLink.isEmpty()) {
                                List<String> documentIds = new ArrayList<>();
                                for (VoucherExpenseLink link : voucherExpenseLink) {
                                    ExpenseDetails expenseDetails = expenseRepository.getExpenseDetailsById(link.getExpense_id());
                                    if (null != expenseDetails) {
                                        documentIds.add(expenseDetails.getDocumentId());
                                    }
                                }
                                taskMapper.setDocumentIds(documentIds);
                            }
                        }
                    }
                }
            }

            taskMapper.setTaskId(taskDetails.getTask_id());
            taskMapper.setEndDate(Utility.getISOFromDate(taskDetails.getEnd_date()));
            taskMapper.setStartDate(Utility.getISOFromDate(taskDetails.getStart_date()));
            taskMapper.setTaskDescription(taskDetails.getTask_description());
            taskMapper.setPriority(taskDetails.getPriority());
            taskMapper.setTaskStatus(taskDetails.getTask_status());
            taskMapper.setTaskName(taskDetails.getTask_name());
            taskMapper.setTimeZone(taskDetails.getTime_zone());
            taskMapper.setUserId(taskDetails.getUser_id());
            taskMapper.setOrganizationId(taskDetails.getOrganization_id());
            taskMapper.setAssignedOn(Utility.getISOFromDate(taskDetails.getCreation_date()));
            taskMapper.setCreationDate(Utility.getISOFromDate(taskDetails.getCreation_date()));
            taskMapper.setUpdateDate(Utility.getISOFromDate(taskDetails.getUpdateDate()));
            taskMapper.setCompletionInd(taskDetails.isComplitionInd());
            taskMapper.setRating(taskDetails.getRating());
            taskMapper.setLink(taskDetails.getLink());           
            taskMapper.setProjectId(taskDetails.getProjectName());
            System.out.println("taskDetails.getProjectName()==================" + taskDetails.getProjectName());
            if (!StringUtils.isEmpty(taskDetails.getProjectName())) {
                ProjectDetails projectDetails = projectRepository.getById(taskDetails.getProjectName());
                if (null != projectDetails) {
                    taskMapper.setProjectName(projectDetails.getProjectName());
                }
            }
            taskMapper.setValue(taskDetails.getValue());
            taskMapper.setUnit(taskDetails.getUnit());
            taskMapper.setImageId(taskDetails.getImageId());
//            taskMapper.setDocumentId(taskDetails.getDocumentId());
            taskMapper.setComplexity(taskDetails.getComplexity());
            taskMapper.setCustomerId(taskDetails.getCustomerId());
            taskMapper.setAssignedBy(employeeService.getEmployeeFullName(taskDetails.getAssignedBy()));
            taskMapper.setAssignedToName(employeeService.
                    getEmployeeFullNameAndEmployeeId(taskDetails.getAssigned_to()).getEmpName());
            taskMapper.setAssignedTo(taskDetails.getAssigned_to());
            taskMapper.setOwnerName(employeeService.
                    getEmployeeFullNameAndEmployeeId(taskDetails.getUser_id()).getEmpName());
            taskMapper.setTaskChecklistId(taskDetails.getTaskChecklistId());
            TaskChecklist taskChecklist = taskChecklistRepository.findByTaskChecklistId(taskDetails.getTaskChecklistId());
            if (null != taskChecklist) {
                taskMapper.setTaskChecklist(taskChecklist.getTaskChecklistName());
            }
            taskMapper.setTaskChecklistStageLinkMapper(categoryService.getAllTaskChecklistStageLinkByTaskChecklistId(taskDetails.getTaskChecklistId()));

            if(!StringUtils.isEmpty(taskDetails.getCustomerId())){
            if(taskDetails.getTask_type().equalsIgnoreCase("Contact To User")) {
            	Distributor distributor = distributorRepository.findById(taskDetails.getCustomerId()).get();
            	if(null != distributor) {
            		taskMapper.setCustomerName(distributor.getName());
            	}
            	}else {
            		Customer customer = customerRepository
                    .getCustomerDetailsByCustomerId(taskDetails.getCustomerId());
            if (null != customer) {
                taskMapper.setCustomerName(customer.getName());
            }
            }
        }
            taskMapper.setAssignedDate(Utility.getISOFromDate(taskDetails.getAssignedDate()));

            //taskMapper.setRatePerUnit(taskDetails.getRatePerUnit());

            int complitionCount = 0;
            List<CandidateTaskLink> candidateTaskLink = candidateTaskLinkRepository.getCandidateTaskLinkByTaskId(taskDetails.getTask_id());
            if (null != candidateTaskLink) {
                List<CandidateViewMapper> candiList = new ArrayList<>();
                for (CandidateTaskLink candidateTaskLink2 : candidateTaskLink) {

                    if (!StringUtils.isEmpty(candidateTaskLink2.getComplitionStatus())) {
                        if (candidateTaskLink2.getComplitionStatus().equalsIgnoreCase("completed")) {
                            complitionCount++;
                        }
                    }

                    CandidateViewMapper mapper = new CandidateViewMapper();
                    CandidateDetails candidateDetails = candidateDetailsRepository
                            .getcandidateDetailsById(candidateTaskLink2.getCandidateId());
                    if (null != candidateDetails) {
                        mapper.setCandidateId(candidateDetails.getCandidateId());
                        String middleName = " ";
                        String lastName = " ";
                        if (null != candidateDetails.getMiddleName()) {

                            middleName = candidateDetails.getMiddleName();
                        }
                        if (null != candidateDetails.getLastName()) {

                            lastName = candidateDetails.getLastName();
                        }

                        mapper.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
                    } else {

                        mapper.setCandidateId("");
                        mapper.setCandidateName("");
                    }
                    candiList.add(mapper);
                }
                taskMapper.setCandidates(candiList);

//                System.out.println("complitionCount======" + complitionCount + "==========taskId++++++++++++++++++" + taskId);

                String completionStatus = null;

                if (complitionCount == 0) {
                    completionStatus = "To Start";
                } else if (complitionCount > 0 && complitionCount < candidateTaskLink.size()) {
                    completionStatus = "In Progress";
                } else if (complitionCount == candidateTaskLink.size()) {
                    completionStatus = "completed";
                }
//                System.out.println("completionStatus======" + completionStatus + "============taskId++++++++++++++++++" + taskId + "=======size" + candidateTaskLink.size());
                taskMapper.setComplitionStatus(completionStatus);

            }
            // set event owner list
//            List<EmployeeShortMapper> empList = new ArrayList<>();
            List<EmployeeShortMapper> empList = new ArrayList<EmployeeShortMapper>();
            List<EmployeeTaskLink> employeeList = employeeTaskRepository.getEmpListByTaskId(taskDetails.getTask_id());
            boolean filterTaskInd = false;
            if (null != employeeList && !employeeList.isEmpty()) {
                filterTaskInd = employeeList.get(0).isFilterTaskInd();  
                for (EmployeeTaskLink employeeCallLink : employeeList) {
                if (!employeeCallLink.getEmployee_id().equals(taskDetails.getAssigned_to())) {
					if (!employeeCallLink.getEmployee_id().equals(taskDetails.getUser_id())) {
						EmployeeShortMapper employeeMapper = new EmployeeShortMapper();

						String employeeId = "";
						EmployeeDetails employeeDetails2 = employeeRepository
								.getEmployeeByUserId(employeeCallLink.getEmployee_id());
						if (null != employeeDetails2) {
							String middleName = " ";
							String lastName = " ";

							if (null != employeeDetails2.getLastName()) {

								lastName = employeeDetails2.getLastName();
							}
							if (null != employeeDetails2.getMiddleName()) {

								middleName = employeeDetails2.getMiddleName();
								employeeMapper.setEmpName(
										employeeDetails2.getFirstName() + " " + middleName + " " + lastName);
							} else {
								employeeMapper.setEmpName(employeeDetails2.getFirstName() + " " + lastName);
							}
							employeeId = employeeDetails2.getEmployeeId();
						}

						employeeMapper.setEmployeeId(employeeId);
						// employeeMapper.setUserId(employeeId);

						empList.add(employeeMapper);
					}
				}
                }
                taskMapper.setIncluded(empList);
            }
            if(!StringUtils.isEmpty(taskDetails.getContact())){
            ContactDetails contact = contactRepository.getcontactDetailsById(taskDetails.getContact());
    		if (contact != null) {
                if (contact.getFirst_name() != null && contact.getLast_name() != null) {
                	taskMapper.setContact(contact.getFirst_name() + " " + contact.getLast_name());
                }
            }
            }
    		OpportunityDetails opportunityDetails = opportunityDetailsRepository.getopportunityDetailsById(taskDetails.getOppertunity());
    		if (opportunityDetails != null) {
    			taskMapper.setOppertunity(opportunityDetails.getOpportunityName());
                
            }

//            taskMapper.setOwner(empList);
            taskMapper.setFilterTaskInd(filterTaskInd);
            if (null != taskDetails.getUser_id()) {
                System.out.println("user_id=========" + taskDetails.getUser_id());
//		EmployeeViewMapper empMapper = employeeService.getEmployeeDetailsByEmployeeId(taskDetails.getUser_id());
                EmployeeDetails employee = employeeRepository.getEmployeesByuserId(taskDetails.getUser_id());
                if (null != employee) {
                    taskMapper.setSubmittedBy(employee.getEmailId());
                    System.out.println("submittedBy........" + employee.getEmailId());

                }
            }
            if (null != taskDetails.getApproved_date()) {

                taskMapper.setApprovedDate(Utility.getISOFromDate(taskDetails.getApproved_date()));

            }
            taskMapper.setApprovedInd(taskDetails.getApproved_ind());
            if(taskDetails.getTask_id()!=null&&taskDetails.getTask_id().trim().length()>0) {
            	List<String> includedIds = taskIncludedLinkRepository.findByTaskId(taskDetails.getTask_id()).stream()
						.map(TaskIncludedLink::getEmployeeId).collect(Collectors.toList());
				List<EmployeeShortMapper> included = new ArrayList<>();
					if(null!=includedIds && !includedIds.isEmpty()) {
						for (String includedId : includedIds) {
							EmployeeShortMapper employeeMapper = employeeService.getEmployeeFullNameAndEmployeeId(includedId);
							included.add(employeeMapper);
						}
						taskMapper.setIncluded(included);
					}
            }
        }
        return taskMapper;
    }
    @Override
    public List<TaskViewMapper> getTaskDetailsByEmployeeIdPagination(String employeeId, int pageNo, boolean filterTaskInd) {
        List<TaskViewMapper> resultList = new ArrayList<TaskViewMapper>();
        Pageable page = PageRequest.of(pageNo, 20, Sort.by("creation_date").descending());
        System.out.println("ind==+++++++" + filterTaskInd);
        Page<EmployeeTaskLink> employeeTaskList;
        if (filterTaskInd) {
            System.out.println("ind in if c==+++++++" + filterTaskInd);
            employeeTaskList = employeeTaskRepository.getApproveTaskListByEmpIdPage(employeeId, page);
            System.out.println("true size=" + employeeTaskList.getSize());
        } else {
            employeeTaskList = employeeTaskRepository.getTaskListByEmpIdPage(employeeId, page);
            System.out.println("false size=" + employeeTaskList.getSize());
        }
        int size = employeeTaskList.getSize();
        System.out.println("Size==++++++++++++++++++" + size);
        if (null != employeeTaskList && !employeeTaskList.isEmpty()) {
            //int i = 0;
            //for (EmployeeTaskLink employeeTaskLink : employeeTaskList) {

            employeeTaskList.stream().map(employeeTaskLink -> {
                TaskViewMapper taskMapper = getTaskDetails(employeeTaskLink.getTask_id());
                if (null != taskMapper.getTaskId()) {
                    taskMapper.setApprovedInd(employeeTaskLink.getApproveStatus());
                    //					if (null != taskMapper.getTaskType()) {
//					if(taskMapper.getTaskType().equalsIgnoreCase("StageApproval")) {
//						taskMapper.setTaskStatus(employeeTaskLink.getApproveStatus());
//					}
//					}
                    taskMapper.setFilterTaskInd(employeeTaskLink.isFilterTaskInd());
                    taskMapper.setNoOfPages(size);
                    taskMapper.setPageCount(employeeTaskList.getTotalPages());
                    taskMapper.setDataCount(employeeTaskList.getSize());
                    taskMapper.setListCount(employeeTaskList.getTotalElements());
                    resultList.add(taskMapper);
                }

                return resultList;
            }).collect(Collectors.toList());
        }
        return resultList;
    }
    
	@Override
    public List<TaskViewMapper> getTasksByUserIdAndPriority(String employeeId,String priority, int pageNo, boolean filterTaskInd){
    	List<TaskViewMapper> resultList = new ArrayList<TaskViewMapper>();
        Pageable page = PageRequest.of(pageNo, 5, Sort.by("creation_date").descending());

        Page<TaskDetails> employeeTaskList  = taskDetailsRepository.findByUserIdAndPriority(employeeId,priority, page);
      
        if (null != employeeTaskList && !employeeTaskList.isEmpty()) {
        int size = employeeTaskList.getSize();   
        resultList = 
        		employeeTaskList.stream().map(taskDetails -> {
        			TaskViewMapper taskMapper = getTaskDetailsOjectResponse(taskDetails);

        			 taskMapper.setFilterTaskInd(false);
                     taskMapper.setNoOfPages(size);
                     taskMapper.setPageCount(employeeTaskList.getTotalPages());
                     taskMapper.setDataCount(employeeTaskList.getSize());
                     taskMapper.setListCount(employeeTaskList.getTotalElements());
                     
                return taskMapper;
            }).collect(Collectors.toList());
        }
        return resultList;                 
    }

    @Override
    public List<TaskViewMapper> getTaskDetailsByOrganizationId(String organizationId) {
        List<TaskViewMapper> resultList = new ArrayList<TaskViewMapper>();
        List<TaskDetails> taskList = taskDetailsRepository.getTaskListByOrgId(organizationId);

        if (null != taskList && !taskList.isEmpty()) {

            taskList.stream().map(taskDetails -> {
                TaskViewMapper taskMapper = getTaskDetails(taskDetails.getTask_id());
                if (null != taskMapper.getTaskId()) {
                    resultList.add(taskMapper);
                }


                return resultList;
            }).collect(Collectors.toList());
        }

        return resultList;
    }

    @Override
    public TaskViewMapper updateTaskDetails(String taskId, TaskMapper taskMapper) throws IOException, TemplateException {
    	
        if (null != taskId) {
            
            TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskId);
           

            if (null != taskDetails) {
            	 taskDetails.setLiveInd(false);
                 taskDetailsRepository.save(taskDetails);
                 TaskDetails newTaskDetailsData = new TaskDetails();
            	String taskNote1 = taskMapper.getTaskDescription();
        		String link = taskMapper.getLink();
        		String msg = "";
        		if (taskNote1 != null && link != null) {
        			msg = "/n"+"Note :"+taskNote1+"/n"+"Link :"+link;
        		} else if (taskNote1 == null && link != null) {
        			msg = "/n"+"Link :"+link;
        		} else if (taskNote1 != null && link == null) {
        			msg = "/n"+"Note :"+taskNote1;
        		} 
        		
        		/* Notification */
            	Notificationparam param = new Notificationparam();
                EmployeeDetails emp = employeeRepository.getEmployeesByuserId(taskMapper.getUserId());
                String name = employeeService.getEmployeeFullNameByObject(emp);
                param.setEmployeeDetails(emp);
                param.setAdminMsg("Task "+"'"+taskMapper.getTaskName() + "' updated by "+name+msg);
                param.setOwnMsg("Task "+taskMapper.getTaskName() +" updated.");
                param.setNotificationType("Task update"); 
                param.setProcessNmae("Task");
                param.setType("update");
                param.setEmailSubject("Korero alert- Task updated");
                param.setCompanyName(companyName);
                param.setUserId(taskMapper.getUserId());   

                if (taskMapper.getTaskName() != null) {
                    newTaskDetailsData.setTask_name(taskMapper.getTaskName());
                } else {
                    newTaskDetailsData.setTask_name(taskDetails.getTask_name());
                }

                if (taskMapper.getTaskDescription() != null) {
					List<TaskNotesLink> list = taskNotesLinkRepository.getNotesIdByTaskId(taskId);
					if (null != list && !list.isEmpty()) {
						list.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
						Notes notes = notesRepository.findByNoteId(list.get(0).getNotesId());
						if (null != notes) {
							notes.setNotes(taskMapper.getTaskDescription());
							notesRepository.save(notes);
						}
					}
				} 
                if (taskMapper.getTaskStatus() != null) {
                    newTaskDetailsData.setTask_status(taskMapper.getTaskStatus());
                } else {
                    newTaskDetailsData.setTask_status(taskDetails.getTask_status());
                }

                if (taskMapper.getTaskType() != null) {
                    newTaskDetailsData.setTask_type((taskMapper.getTaskType()));
                } else {
                    newTaskDetailsData.setTask_type(taskDetails.getTask_type());
                }

                if (taskMapper.getTimeZone() != null) {
                    newTaskDetailsData.setTime_zone(taskMapper.getTimeZone());
                } else {
                    newTaskDetailsData.setTime_zone(taskDetails.getTime_zone());
                }

                if (taskMapper.getStartDate() != null) {
                    try {
                        newTaskDetailsData.setStart_date(Utility.getDateFromISOString(taskMapper.getStartDate()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    newTaskDetailsData.setStart_date(taskDetails.getStart_date());
                }
                if (taskMapper.getEndDate() != null) {
                    try {
                        newTaskDetailsData.setEnd_date(Utility.getDateFromISOString(taskMapper.getEndDate()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    newTaskDetailsData.setEnd_date(taskDetails.getEnd_date());
                }

                if (taskMapper.getPriority() != null) {
                    newTaskDetailsData.setPriority(taskMapper.getPriority());
                } else {
                    newTaskDetailsData.setPriority(taskDetails.getPriority());
                }
                if (taskMapper.getAssignedTo() != null) {
                    newTaskDetailsData.setAssigned_to(taskMapper.getAssignedTo());
                } else {
                    newTaskDetailsData.setAssigned_to(taskDetails.getAssigned_to());
                }
                
                if (null  != taskDetails.getAssignedBy() ) {
					if (null  != taskMapper.getAssignedTo() && !taskDetails.getAssignedBy().equals(taskMapper.getAssignedTo())) {
						taskDetails.setAssignedBy(taskMapper.getUserId());
		            } else {
		            	taskDetails.setAssignedBy(taskDetails.getAssignedBy());
		            }
				}else {
					taskDetails.setAssignedBy(taskMapper.getUserId());
				}	
                
                if (taskMapper.getProjectName() != null) {
                    newTaskDetailsData.setProjectName(taskMapper.getProjectName());
                } else {
                    newTaskDetailsData.setProjectName(taskDetails.getProjectName());
                }

                if (taskMapper.getValue() != null) {
                    newTaskDetailsData.setValue(taskMapper.getValue());
                } else {
                    newTaskDetailsData.setValue(taskDetails.getValue());
                }
                if (taskMapper.getUnit() != null) {
                    newTaskDetailsData.setUnit(taskMapper.getUnit());
                } else {
                    newTaskDetailsData.setUnit(taskDetails.getUnit());
                }
                if (taskMapper.getImageId() != null) {
                    newTaskDetailsData.setImageId(taskMapper.getImageId());
                } else {
                    newTaskDetailsData.setImageId(taskDetails.getImageId());
                }
//                if (taskMapper.getDocumentId() != null) {
//                    newTaskDetailsData.setDocumentId(taskMapper.getDocumentId());
//                } else {
//                    newTaskDetailsData.setDocumentId(taskDetails.getDocumentId());
//                }
                if (taskMapper.getComplexity() != null) {
                    newTaskDetailsData.setComplexity(taskMapper.getComplexity());
                } else {
                    newTaskDetailsData.setComplexity(taskDetails.getComplexity());
                }
                if (taskMapper.getCustomerId() != null) {
                    newTaskDetailsData.setCustomerId(taskMapper.getCustomerId());
                } else {
                    newTaskDetailsData.setCustomerId(taskDetails.getCustomerId());
                }
                if (taskMapper.getTaskChecklistId() != null) {
                    newTaskDetailsData.setTaskChecklistId(taskMapper.getTaskChecklistId());
                } else {
                    newTaskDetailsData.setTaskChecklistId(taskDetails.getTaskChecklistId());
                }
                if (taskMapper.getAssignedDate() != null) {
                    try {
                        newTaskDetailsData.setAssignedDate(Utility.getDateFromISOString(taskMapper.getAssignedDate()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    newTaskDetailsData.setAssignedDate(taskDetails.getAssignedDate());
                }
                if (taskMapper.getLink() != null) {
                    newTaskDetailsData.setLink(taskMapper.getLink());
                } else {
                    newTaskDetailsData.setLink(taskDetails.getLink());
                }
                if (taskMapper.getContact() != null) {
                    newTaskDetailsData.setContact(taskMapper.getContact());
                } else {
                    newTaskDetailsData.setContact(taskDetails.getContact());
                }
                
                if (taskMapper.getOppertunity() != null) {
                    newTaskDetailsData.setOppertunity(taskMapper.getOppertunity());
                } else {
                    newTaskDetailsData.setOppertunity(taskDetails.getOppertunity());
                }
                newTaskDetailsData.setTask_id(taskId);
                newTaskDetailsData.setCreation_date(taskDetails.getCreation_date());
                newTaskDetailsData.setUser_id(taskMapper.getUserId());
                newTaskDetailsData.setOrganization_id(taskMapper.getOrganizationId());
                newTaskDetailsData.setLiveInd(true);
                taskDetailsRepository.save(newTaskDetailsData);
                
                /*insert to document link*/
                if (taskMapper.getDocumentId() != null) { 
                	TaskDocumentLink taskDocumentLink = new TaskDocumentLink();
        			taskDocumentLink.setTaskId(taskId);
        			taskDocumentLink.setDocumentId(taskMapper.getDocumentId());
        			taskDocumentLink.setCreationDate(new Date());
        			taskDocumentLinkRepository.save(taskDocumentLink);
                }
              //Edit Included
        		List<TaskIncludedLink> list = taskIncludedLinkRepository
        				.findByTaskId(taskId);
        		if (null != list && !list.isEmpty()) {
        			for (TaskIncludedLink taskIncludedLink1 : list) {
        				taskIncludedLinkRepository.delete(taskIncludedLink1);
        			}
        		}
//        		List<String> taskLinkList = employeeTaskRepository.getEmpListByTaskId(taskId).stream()
//        				.map(EmployeeTaskLink::getEmployee_id).collect(Collectors.toList());
        		
        		List<EmployeeTaskLink> taskLinkList = employeeTaskRepository.getEmpListByTaskId(taskId);
        		employeeTaskRepository.deleteAll(taskLinkList);
        		
        		List<String> empList = new ArrayList<>();
        		if (taskMapper.getIncluded() != null && !taskMapper.getIncluded().isEmpty()) {
        			empList.addAll(taskMapper.getIncluded());
        			} 		
    			empList.add(taskMapper.getUserId());
    			for (String id : empList) {
    				EmployeeTaskLink employeeTaskLink = new EmployeeTaskLink();
					employeeTaskLink.setTask_id(taskId);
					employeeTaskLink.setEmployee_id(id);
					employeeTaskLink.setCreation_date(new Date());
					employeeTaskLink.setLive_ind(true);
					employeeTaskRepository.save(employeeTaskLink);
				}
        		
					if (taskMapper.getIncluded() != null && !taskMapper.getIncluded().isEmpty()) {
						List<String> includedids = new ArrayList<>(taskMapper.getIncluded());
						param.setIncludeedUserIds(includedids);
						param.setIncludeMsg(
								"You have been added Task " + "'" + taskMapper.getIncluded() + "'" + " by " + name+msg);
						for (String id : taskMapper.getIncluded()) {
							TaskIncludedLink taskIncludedLink = new TaskIncludedLink();
							taskIncludedLink.setTaskId(taskId);
							taskIncludedLink.setEmployeeId(id);
							taskIncludedLink.setCreationDate(new Date());
							taskIncludedLink.setLiveInd(true);
							taskIncludedLink.setOrgId(taskMapper.getOrganizationId());
							taskIncludedLinkRepository.save(taskIncludedLink);
						}
					}

                // update call employee link

//			Set<String> empList = new HashSet<String>();
//			List<EmployeeTaskLink> employeeList = employeeTaskRepository.getEmpListByTaskId(taskId);
//			employeeTaskRepository.deleteAll(employeeList);

//			if (null != employeeList && !employeeList.isEmpty()) {
//				for (EmployeeTaskLink employeeTaskLink : employeeList) {
//				
//					 employeeTaskLink.setLive_ind(false);
//					employeeTaskRepository.save(employeeTaskLink);
//
//				}			
//			}
                String ownerName = employeeService.getEmployeeFullName(taskMapper.getUserId());
                /* insert to Notification Table */		
    			
                // insert new data to call employee link//

//			empList.add(taskMapper.getUserId());
//			empList.add(taskMapper.getAssignedTo());

                if (!StringUtils.isEmpty(taskMapper.getAssignedTo())) {
//                    if (!taskDetails.getAssigned_to().equalsIgnoreCase(taskMapper.getAssignedTo())) {
//                        List<EmployeeTaskLink> employeeTaskLinks = employeeTaskRepository.getEmpListByTaskId(taskId);
//                        if (null != employeeTaskLinks && !employeeTaskLinks.isEmpty()) {
//                            for (EmployeeTaskLink emp : employeeTaskLinks) {
                                if (!taskDetails.getAssigned_to().equalsIgnoreCase(taskMapper.getAssignedTo())) {
                                    EmployeeTaskLink employeeTaskLink = new EmployeeTaskLink();
                                    employeeTaskLink.setTask_id(taskId);
                                    employeeTaskLink.setEmployee_id(taskMapper.getAssignedTo());
                                    employeeTaskLink.setCreation_date(new Date());
                                    employeeTaskLink.setLive_ind(true);
                                    employeeTaskRepository.save(employeeTaskLink);
                                    /* insert to Notification info */
                                    List<String> assignToUserIds = new ArrayList<>();
        			            	assignToUserIds.add(taskMapper.getAssignedTo());
        			            	param.setAssignToUserIds(assignToUserIds); 
        			                param.setAssignToMsg("Task "+"'"+taskMapper.getTaskName() + "'"+ " assigned to "+employeeService.getEmployeeFullName(taskMapper.getAssignedTo())+" by "+name+msg);


                                    EmployeeTaskLink employeeTaskLink2 = employeeTaskRepository.getEmployeeTaskLink(taskDetails.getAssigned_to(), taskId);
                                    if (null != employeeTaskLink2) {
                                    employeeTaskRepository.delete(employeeTaskLink2);
                                    }
//                                    /* insert to Notification info */
//                                    NotificationDetails notification1 = new NotificationDetails();
//                                    notification1.setNotificationType("task");
//                                    notification1.setAssignedTo(taskDetails.getAssigned_to());
//                                    notification1.setMessage(
//                                            ownerName + " has removed to you " + notification.getNotificationType() + taskMapper.getTaskName());
//                                    notification1.setUser_id(taskMapper.getUserId());
//                                    notification1.setNotificationDate(new Date());
//                                    notification1.setOrg_id(notification.getOrg_id());
//                                    notification1.setMessageReadInd(false);
//                                    notificationRepository.save(notification1);


                                    List<Hour> hr = hourRepository.getByProjectManagerAndTaskId(taskDetails.getAssigned_to(), taskId);
                                    if (null != hr && !hr.isEmpty()) {
                                        for (Hour hour : hr) {
                                            hour.setProjectManager(taskMapper.getAssignedTo());
                                            hourRepository.save(hour);
                                        }
                                    }


//				}else {
//					EmployeeTaskLink employeeTaskLink2  = employeeTaskRepository.getEmployeeTaskLink(taskDetails.getAssigned_to(), taskId);
//					employeeTaskRepository.delete(employeeTaskLink2);
//					
//					/* insert to Notification info */
//					NotificationDetails notification = new NotificationDetails();
//					notification.setNotificationType("task");
//						notification.setAssignedTo(taskDetails.getAssigned_to());
//						notification.setMessage(
//								ownerName + " has removed to you " + notification.getNotificationType());
//					notification.setUser_id(taskMapper.getUserId());
//					notification.setNotificationDate(new Date());
//					notification.setOrg_id(notification.getOrg_id());
//					notification.setMessageReadInd(false);
//					notificationRepository.save(notification);
                                }
//                            }
//                        }
//                    }
                }
                // insert new data to call employee link//
                List<String> canTaskLinks = candidateTaskLinkRepository.getCandidateTaskLinkByTaskId(taskId).stream()
                        .map(CandidateTaskLink::getCandidateId)
                        .collect(Collectors.toList());

                List<String> candidateIdsList = new ArrayList<>();
                if (null != taskMapper.getCandidateId() && !taskMapper.getCandidateId().isEmpty()) {
                    candidateIdsList.addAll(taskMapper.getCandidateId());
                }
                candidateIdsList.addAll(canTaskLinks);
                if (null != candidateIdsList && !candidateIdsList.isEmpty()) {
                    // candidateIdsList.forEach(candidateId->{
                    System.out.println("candidateIdsList==" + candidateIdsList.toString());
                    for (String candidateId : candidateIdsList) {
                        //	if (taskMapper.getCandidateId().contains(candidateId)) {

                        System.out.println("candiXXXXX==" + candidateId);
                        if (canTaskLinks.contains(candidateId) && !taskMapper.getCandidateId().contains(candidateId)) {
                            System.out.println("CANVID==" + candidateId + "||" + taskId);
                            System.out.println("in 1st condition " + candidateId);

                            CandidateTaskLink candidateTaskLink = candidateTaskLinkRepository
                                    .findByCandidateIdAndTaskIdAndLiveInd(candidateId, taskId, true);
                            System.out.println("CANDIDATE==" + candidateTaskLink.toString());
                            candidateTaskLinkRepository.delete(candidateTaskLink);

                            /* insert to Notification info */
                            NotificationDetails notification = new NotificationDetails();
                            notification.setNotificationType("task");
                            notification.setMessage(
                                    ownerName + " has removed to you a " + notification.getNotificationType());
                            notification.setAssignedTo(candidateId);
                            notification.setUser_id(taskMapper.getUserId());
                            notification.setNotificationDate(new Date());
                            notification.setOrg_id(notification.getOrg_id());
                            notification.setMessageReadInd(false);
                            notificationRepository.save(notification);

                        } else if (!canTaskLinks.contains(candidateId) && taskMapper.getCandidateId().contains(candidateId)) {
                            System.out.println("in 2st condition " + candidateId);
                            CandidateTaskLink candidateTaskLink = new CandidateTaskLink();
                            candidateTaskLink.setTaskId(taskId);
                            candidateTaskLink.setCandidateId(candidateId);
                            candidateTaskLink.setCreationdate(new Date());
                            candidateTaskLink.setLiveInd(true);
                            candidateTaskLink.setComplitionStatus("To Start");
                            candidateTaskLinkRepository.save(candidateTaskLink);

                            /* insert to Notification info */
                            NotificationDetails notification = new NotificationDetails();
                            notification.setNotificationType("task");
                            notification.setMessage(
                                    ownerName + " has assigned to a " + notification.getNotificationType() + " you ");
                            notification.setAssignedTo(candidateId);
                            notification.setUser_id(taskMapper.getUserId());
                            notification.setNotificationDate(new Date());
                            notification.setOrg_id(notification.getOrg_id());
                            notification.setMessageReadInd(false);
                            notificationRepository.save(notification);

                        }
                        //}

                    }
                }
                notificationService.createNotificationForDynamicUsers(param);
            }
        }
        
        TaskViewMapper resultMapper = getTaskDetails(taskId);

        return resultMapper;

    }

    @Override
    public boolean delinkTask(String employeeId, String taskId) {

        EmployeeTaskLink employeeTaskLink = employeeTaskRepository.getEmployeeTaskLink(employeeId, taskId);

        if (null != employeeTaskLink) {
            employeeTaskLink.setLive_ind(false);
            employeeTaskRepository.save(employeeTaskLink);
            TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskId);
            if (null != taskDetails) {
            	taskDetails.setLiveInd(false);
            	taskDetailsRepository.save(taskDetails);
            }
            return true;
        }

        return false;

    }

    @Override
    public TaskViewMapper approveLeaveTask(String taskId) {

        TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskId);
        if (null != taskDetails) {
            taskDetails.setApproved_ind("Approved");
            taskDetails.setApproved_date(new Date());
            taskDetailsRepository.save(taskDetails);
        }

        LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByTaskId(taskId);

        if (null != leaveDetails) {
            leaveDetails.setStatus("Approved");
            leaveDetailsRepository.save(leaveDetails);
        }

        TaskViewMapper taskMapper = getTaskDetails(taskId);
        return taskMapper;
    }

    @Override
    public TaskViewMapper rejectLeaveTask(String taskId) {

        TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskId);
        if (null != taskDetails) {
            taskDetails.setApproved_ind("Rejected");
            taskDetails.setApproved_date(new Date());
            taskDetailsRepository.save(taskDetails);
        }

        LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByTaskId(taskId);

        if (null != leaveDetails) {
            leaveDetails.setStatus("Rejected");
            leaveDetailsRepository.save(leaveDetails);
        }

        TaskViewMapper taskMapper = getTaskDetails(taskId);
        return taskMapper;
    }

    @Override
    public TaskViewMapper approveTask(String taskId, String userId) {
        TaskViewMapper taskMapper = new TaskViewMapper();
        TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskId);
        if (null != taskDetails) {
//			taskDetails.setApproved_ind("Approved");
//			taskDetails.setApproved_date(new Date());
//			taskDetailsRepository.save(taskDetails);

            if (taskDetails.getTask_type().equalsIgnoreCase("leave")) {
//			LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByTaskId(taskId);
//
//			if (null != leaveDetails) {
//				if(leaveDetails.getStatus().equalsIgnoreCase("Approved")) {
//					leaveDetails.setLive_ind(false);	
//					leaveDetailsRepository.save(leaveDetails);
//				}else {
//				leaveDetails.setStatus("Approved");
//				leaveDetailsRepository.save(leaveDetails);

                /* insert to Notification */
		/*sb		NotificationDetails notification = new NotificationDetails();
				notification.setNotificationType("leave Approve");
				TaskDetails taskDetailss = taskDetailsRepository.getTaskDetailsById(taskId);
				notification.setUser_id(taskDetailss.getUser_id());
				EmployeeDetails employee = employeeRepository.getEmployeesByuserId(taskDetailss.getUser_id());
				EmployeeDetails employee2 = employeeRepository.getEmployeesByuserId(employee.getReportingManager());
				System.out.println("user name%%%%%%%%%%%%" + employee2.getFullName());
				notification.setAssignedBy(employee2.getFullName());
				notification.setAssignedTo(employee.getUserId());
				notification.setNotificationDate(new Date());
				System.out.println("notification date%%%%%%%%%" + notification.getNotificationDate());
				System.out.println("notification id%%%%%%%%%" + notification.getNotificationId());

				notification.setMessage("" + employee2.getFullName() + " has approved your leave ");
				notification.setOrg_id(notification.getOrg_id());
				notification.setMessageReadInd(false);
				notificationRepository.save(notification);   sb*/

                processApprovalService.approveLeaveProcess(taskId, userId, "leave");
                taskMapper.setApprovedInd("Approved");
                taskMapper = getTaskDetails(taskId);
//				}

            } else if (taskDetails.getTask_type().equalsIgnoreCase("mileage")) {

//			VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsByTaskId(taskId);
//			if (null != voucherDetails) {
//				voucherDetails.setStatus("Approved");
//				voucherRepository.save(voucherDetails);

                /* insert to Notification */
		/*sb		NotificationDetails notification = new NotificationDetails();
				notification.setNotificationType("mileage Approve");
				TaskDetails taskDetailss = taskDetailsRepository.getTaskDetailsById(taskId);
				notification.setUser_id(taskDetailss.getUser_id());
				EmployeeDetails employee = employeeRepository.getEmployeesByuserId(taskDetailss.getUser_id());
				EmployeeDetails employee2 = employeeRepository.getEmployeesByuserId(employee.getReportingManager());
				System.out.println("user name%%%%%%%%%%%%" + employee2.getFullName());
				notification.setAssignedBy(employee2.getFullName());
				notification.setAssignedTo(employee.getUserId());
				notification.setNotificationDate(new Date());
				System.out.println("notification date%%%%%%%%%" + notification.getNotificationDate());
				System.out.println("notification id%%%%%%%%%" + notification.getNotificationId());

				notification.setMessage("" + employee2.getFullName() + " has approved your mileage ");
				notification.setOrg_id(notification.getOrg_id());
				notification.setMessageReadInd(false);
				notificationRepository.save(notification);  sb*/
                processApprovalService.approveMileageTaskProcess(taskId, userId, taskDetails.getTask_type());
                taskMapper.setApprovedInd("Approved");
                taskMapper = getTaskDetails(taskId);
//			}
            }else if(taskDetails.getTask_type().equalsIgnoreCase("expense")) {
            	processApprovalService.expenseTaskApprove(taskId, userId, taskDetails.getTask_type());
                taskMapper.setApprovedInd("Approved");
                taskMapper = getTaskDetails(taskId);
            } else if (taskDetails.getTask_type().equalsIgnoreCase("Customer Contact To User")
            		|| taskDetails.getTask_type().equalsIgnoreCase("Supplier Contact To User")) {

            	processApprovalService.approveContactUserCreateProcess(taskId,userId,taskDetails.getTask_type());
                taskMapper = getTaskDetails(taskId);
            } else if (taskDetails.getTask_type().equalsIgnoreCase("Prospect To Customer")) {
            	List<EmployeeTaskLink> approvalList = employeeTaskRepository.getEmpListByTaskId(taskId);


        		approvalList.forEach(approveTaskLink->{
        			
        			if(approveTaskLink.getApprovedBy()==null) {
        			approveTaskLink.setApproveStatus("Approved");
        			if(userId!=null) {
        			approveTaskLink.setApprovedBy(userId);
        			}
        			if(taskDetails.getCurrentLevel()!=0) {
        			approveTaskLink.setLevel(taskDetails.getCurrentLevel());
        			}
        			approveTaskLink.setApprovedDate(new Date());
        			}
        			approveTaskLink.setFilterTaskInd(true);
        			employeeTaskRepository.save(approveTaskLink);
        		});
            } else if (taskDetails.getTask_type().equalsIgnoreCase("StageApproval")) {
                processApprovalService.approveStageApprovalProcess(taskId, userId, taskDetails.getTask_type());
                taskMapper = getTaskDetails(taskId);
            }
            taskMapper.setApprovedInd("Approved");
        }
        return taskMapper;
    }

    @Override
    public TaskViewMapper rejectTask(String taskId,String userId, String rejectReason) {
        TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskId);
        if (null != taskDetails) {
            taskDetails.setApproved_ind("Rejected");
            taskDetails.setApproved_date(new Date());
            taskDetailsRepository.save(taskDetails);

            /*
             * insert to Notification NotificationDetails notification = new
             * NotificationDetails(); notification.setNotificationType("mileage Rejected");
             * TaskDetails taskDetailss = taskDetailsRepository.getTaskDetailsById(taskId);
             * notification.setUser_id(taskDetailss.getUser_id()); EmployeeDetails employee
             * =employeeRepository.getEmployeesByuserId(taskDetailss.getUser_id());
             * EmployeeDetails employee2
             * =employeeRepository.getEmployeesByuserId(employee.getReportingManager());
             * System.out.println("user name%%%%%%%%%%%%" +employee2.getFullName());
             * notification.setAssignedBy(employee2.getFullName());
             * notification.setAssignedTo(employee.getUserId());
             * notification.setNotificationDate(new Date());
             * System.out.println("notification date%%%%%%%%%"+notification.
             * getNotificationDate());
             * System.out.println("notification id%%%%%%%%%"+notification.getNotificationId(
             * ));
             *
             * notification.setMessage("" +employee2.getFullName() +
             * " has Rejected your mileage ");
             * notification.setOrg_id(notification.getOrg_id());
             * notification.setMessageReadInd(false);
             * notificationRepository.save(notification);
             */
            
            EmployeeTaskLink taskLink = employeeTaskRepository.getEmployeeTaskLink(userId,taskId);
            taskLink.setApproveStatus("Rejected");
    		taskLink.setApprovedBy(userId);
    		taskLink.setApprovedDate(new Date());
    		employeeTaskRepository.save(taskLink);
    		
    		ApprovalLevel approvalLevel = approvalLevelRepository.getApprovalLevelDetail(taskLink.getSubProcessApprovalId());
    		if(approvalLevel.getApprovalType().equalsIgnoreCase("exception")) {
    		List<EmployeeTaskLink> approvalList = employeeTaskRepository.getEmpListByTaskId(taskId);
    		approvalList.forEach(approveTaskLink->{
    			taskLink.setApproveStatus("Rejected");
        		taskLink.setApprovedBy(userId);
        		taskLink.setApprovedDate(new Date());
        		employeeTaskRepository.save(approveTaskLink);
    		});
    		}
        }

        if (taskDetails.getTask_type().equalsIgnoreCase("leave")) {
            LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByTaskId(taskId);

            if (null != leaveDetails) {
                leaveDetails.setStatus("Rejected");
                leaveDetails.setRejectReason(rejectReason);
                leaveDetailsRepository.save(leaveDetails);

                /* insert to Notification */
                NotificationDetails notification = new NotificationDetails();
                notification.setNotificationType("leave Rejected");
                TaskDetails taskDetailss = taskDetailsRepository.getTaskDetailsById(taskId);
                notification.setUser_id(taskDetailss.getUser_id());
                EmployeeDetails employee = employeeRepository.getEmployeesByuserId(taskDetailss.getUser_id());
                EmployeeDetails employee2 = employeeRepository.getEmployeesByuserId(employee.getReportingManager());
                System.out.println("user name%%%%%%%%%%%%" + employee2.getFullName());
                notification.setAssignedBy(employee2.getFullName());
                notification.setAssignedTo(employee.getUserId());
                notification.setNotificationDate(new Date());
                System.out.println("notification date%%%%%%%%%" + notification.getNotificationDate());
                System.out.println("notification id%%%%%%%%%" + notification.getNotificationId());

                notification.setMessage("" + employee2.getFullName() + " has Rejected your leave ");
                notification.setOrg_id(notification.getOrg_id());
                notification.setMessageReadInd(false);
                notificationRepository.save(notification);
            }
        } else if (taskDetails.getTask_type().equalsIgnoreCase("mileage")
                || taskDetails.getTask_type().equalsIgnoreCase("expense")) {

            VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsByTaskId(taskId);
            if (null != voucherDetails) {
                voucherDetails.setStatus("Rejected");
                voucherDetails.setRejectReason(rejectReason);
                voucherRepository.save(voucherDetails);

                /* insert to Notification */
                NotificationDetails notification = new NotificationDetails();
                notification.setNotificationType("mileage Rejected");
                TaskDetails taskDetailss = taskDetailsRepository.getTaskDetailsById(taskId);
                notification.setUser_id(taskDetailss.getUser_id());
                EmployeeDetails employee = employeeRepository.getEmployeesByuserId(taskDetailss.getUser_id());
                EmployeeDetails employee2 = employeeRepository.getEmployeesByuserId(employee.getReportingManager());

                System.out.println("user name%%%%%%%%%%%%" + employee2.getFullName());
                notification.setAssignedBy(employee2.getFullName());
                notification.setAssignedTo(employee.getUserId());
                notification.setNotificationDate(new Date());
                System.out.println("notification date%%%%%%%%%" + notification.getNotificationDate());
                System.out.println("notification id%%%%%%%%%" + notification.getNotificationId());

                notification.setMessage("" + employee2.getFullName() + " has Rejected your mileage ");
                notification.setOrg_id(notification.getOrg_id());
                notification.setMessageReadInd(false);
                notificationRepository.save(notification);
            }
        }
        TaskViewMapper taskMapper = getTaskDetails(taskId);
        return taskMapper;
    }

    @Override
    public List<TaskViewMapper> getTaskListByUserIdWithDateRange(String userId, String startDate, String endDate) {
        Date end_date = null;
        Date start_date = null;
        List<TaskViewMapper> resultList = new ArrayList<TaskViewMapper>();
        try {
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<TaskDetails> taskList = taskDetailsRepository.getTasksByUserIdWithDateRange(userId, start_date, end_date);

        if (null != taskList && !taskList.isEmpty()) {


            taskList.stream().map(taskDetails -> {
                TaskViewMapper taskMapper = new TaskViewMapper();
                taskMapper.setApprovedDate(Utility.getISOFromDate(taskDetails.getApproved_date()));
                taskMapper.setEndDate(Utility.getISOFromDate(taskDetails.getEnd_date()));
                taskMapper.setStartDate(Utility.getISOFromDate(taskDetails.getStart_date()));
                taskMapper.setAssignedOn(Utility.getISOFromDate(taskDetails.getCreation_date()));
                taskMapper.setPriority(taskDetails.getPriority());
                taskMapper.setTaskDescription(taskDetails.getTask_description());
                taskMapper.setTaskId(taskDetails.getTask_id());
                taskMapper.setTaskName(taskDetails.getTask_name());
                taskMapper.setTaskStatus(taskDetails.getTask_status());
                taskMapper.setTaskType(taskDetails.getTask_type());

                taskMapper.setProjectName(taskDetails.getProjectName());
                taskMapper.setValue(taskDetails.getValue());
                taskMapper.setUnit(taskDetails.getUnit());
                taskMapper.setImageId(taskDetails.getImageId());
//                taskMapper.setDocumentId(taskDetails.getDocumentId());
                taskMapper.setComplexity(taskDetails.getComplexity());
                taskMapper.setCustomerId(taskDetails.getCustomerId());
                taskMapper.setAssignedDate(Utility.getISOFromDate(taskDetails.getAssignedDate()));
                //taskMapper.setRatePerUnit(taskDetails.getRatePerUnit());

                EmployeeViewMapper empMapper = employeeService.getEmployeeDetailsByEmployeeId(taskDetails.getUser_id());
                if (null != empMapper) {
                    taskMapper.setSubmittedBy(empMapper.getEmailId());

                }
                resultList.add(taskMapper);

                return resultList;
            }).collect(Collectors.toList());
        }
        return resultList;

    }

    @Override
    public List<TaskViewMapper> getTaskListByOrgIdWithDateRange(String orgId, String startDate, String endDate) {
        Date end_date = null;
        Date start_date = null;
        List<TaskViewMapper> resultList = new ArrayList<TaskViewMapper>();
        try {
            end_date = Utility.getDateAfterEndDate(Utility.getDateFromISOString(endDate));
            start_date = Utility.getDateFromISOString(startDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<TaskDetails> taskList = taskDetailsRepository.getTasksByOrgIdWithDateRange(orgId, start_date, end_date);

        if (null != taskList && !taskList.isEmpty()) {


            taskList.stream().map(taskDetails -> {
                TaskViewMapper taskMapper = new TaskViewMapper();
                taskMapper.setApprovedDate(Utility.getISOFromDate(taskDetails.getApproved_date()));
                taskMapper.setEndDate(Utility.getISOFromDate(taskDetails.getEnd_date()));
                taskMapper.setStartDate(Utility.getISOFromDate(taskDetails.getStart_date()));
                taskMapper.setAssignedOn(Utility.getISOFromDate(taskDetails.getCreation_date()));
                taskMapper.setPriority(taskDetails.getPriority());
                taskMapper.setTaskDescription(taskDetails.getTask_description());
                taskMapper.setTaskId(taskDetails.getTask_id());
                taskMapper.setTaskName(taskDetails.getTask_name());
                taskMapper.setTaskStatus(taskDetails.getTask_status());
                taskMapper.setTaskType(taskDetails.getTask_type());

                taskMapper.setProjectName(taskDetails.getProjectName());
                taskMapper.setValue(taskDetails.getValue());
                taskMapper.setUnit(taskDetails.getUnit());
                taskMapper.setImageId(taskDetails.getImageId());
//                taskMapper.setDocumentId(taskDetails.getDocumentId());
                taskMapper.setComplexity(taskDetails.getComplexity());
                taskMapper.setCustomerId(taskDetails.getCustomerId());
                taskMapper.setAssignedDate(Utility.getISOFromDate(taskDetails.getAssignedDate()));

                EmployeeViewMapper empMapper = employeeService.getEmployeeDetailsByEmployeeId(taskDetails.getUser_id());
                if (null != empMapper) {
                    taskMapper.setSubmittedBy(empMapper.getEmailId());

                }
                resultList.add(taskMapper);

                return resultList;
            }).collect(Collectors.toList());

        }
        return resultList;

    }

    @Override
    public String saveTaskType(TaskMapper taskMapper) {
        String taskTypeId = null;

        if (taskMapper != null) {
            TaskType taskType = new TaskType();

            taskType.setTaskType(taskMapper.getTaskType());
            taskType.setCreationDate(new Date());
            taskType.setUserId(taskMapper.getUserId());
            taskType.setOrgId(taskMapper.getOrganizationId());
            taskType.setLiveInd(true);
            taskType.setEditInd(taskMapper.isEditInd());

            TaskType dbTaskType = taskTypeRepository.save(taskType);
            taskTypeId = dbTaskType.getTaskTypeId();

            TaskTypeDelete taskTypeDelete = new TaskTypeDelete();
            taskTypeDelete.setUpdationDate(new Date());
            taskTypeDelete.setUserId(taskMapper.getUserId());
            taskTypeDelete.setOrgId(taskMapper.getOrganizationId());
            taskTypeDelete.setUpdatedBy(taskMapper.getUserId());
            taskTypeDeleteRepository.save(taskTypeDelete);

        }

        return taskTypeId;
    }

    @Override
    public List<TaskMapper> getTaskTypeByOrgId(String orgId) {
        List<TaskMapper> resultList = new ArrayList<TaskMapper>();
        List<TaskType> taskTypeList = taskTypeRepository.findByOrgIdAndLiveInd(orgId, true);

        if (null != taskTypeList && !taskTypeList.isEmpty()) {
            taskTypeList.stream().map(taskType -> {
                TaskMapper taskMapper = new TaskMapper();
                taskMapper.setTaskTypeId(taskType.getTaskTypeId());
                taskMapper.setTaskType(taskType.getTaskType());
                taskMapper.setEditInd(taskType.isEditInd());
                taskMapper.setTaskCheckListInd(taskType.isTaskCheckListInd());
                taskMapper.setCreationDate(Utility.getISOFromDate(taskType.getCreationDate()));

                resultList.add(taskMapper);
                return resultList;
            }).collect(Collectors.toList());

        }

        Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
        List<TaskTypeDelete> taskTypeDelete = taskTypeDeleteRepository.findByOrgId(orgId);
        if (null != taskTypeDelete && !taskTypeDelete.isEmpty()) {
            Collections.sort(taskTypeDelete,
                    (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

            resultList.get(0).setUpdationDate(Utility.getISOFromDate(taskTypeDelete.get(0).getUpdationDate()));
            EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(taskTypeDelete.get(0).getUserId());
            if (null != employeeDetails) {
                String middleName = " ";
                String lastName = "";

                if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                    lastName = employeeDetails.getLastName();
                }

                if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

                    middleName = employeeDetails.getMiddleName();
                    resultList.get(0).setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
                } else {

                    resultList.get(0).setName(employeeDetails.getFirstName() + " " + lastName);
                }
            }
        }

        return resultList;
    }

   
	@Override
	public List<TaskTypeDropMapper> getTaskTypeForDropDownByOrgId(String orgId) {
	    List<TaskType> taskTypeList = taskTypeRepository.findByOrgIdAndLiveInd(orgId, true);
	    List<TaskTypeDropMapper> list = new ArrayList<>();
	    list = taskTypeList.stream()
	            .map(taskType -> {
	                TaskTypeDropMapper taskMapper = new TaskTypeDropMapper();
	                taskMapper.setTaskTypeId(taskType.getTaskTypeId());
	                taskMapper.setTaskType(taskType.getTaskType());
	                return taskMapper;
	            })
	            .collect(Collectors.toList());
	    return list;
	}

    
    @Override
    public TaskMapper updateTaskType(String taskTypeId, TaskMapper taskMapper) {
        TaskMapper resultMapper = null;
        if (null != taskMapper.getTaskTypeId()) {
            TaskType taskType = taskTypeRepository.findByTaskTypeId(taskMapper.getTaskTypeId());

            if (null != taskType.getTaskTypeId()) {
                taskType.setTaskType(taskMapper.getTaskType());
                taskType.setEditInd(taskMapper.isEditInd());
                taskTypeRepository.save(taskType);

                TaskTypeDelete taskTypeDelete = taskTypeDeleteRepository.findByTaskTypeId(taskMapper.getTaskTypeId());
                if (null != taskTypeDelete) {
                    taskTypeDelete.setUpdationDate(new Date());
                    taskTypeDelete.setUpdatedBy(taskMapper.getUserId());
                    taskTypeDeleteRepository.save(taskTypeDelete);
                } else {
                    TaskTypeDelete taskTypeDelete1 = new TaskTypeDelete();
                    taskTypeDelete1.setTaskTypeId(taskTypeId);
                    taskTypeDelete1.setUserId(taskMapper.getUserId());
                    taskTypeDelete1.setOrgId(taskMapper.getOrganizationId());
                    taskTypeDelete1.setUpdationDate(new Date());
                    taskTypeDelete1.setUpdatedBy(taskMapper.getUserId());
                    taskTypeDeleteRepository.save(taskTypeDelete1);
                }
            }
            resultMapper = getTaskTypeById(taskTypeId);

        }
        return resultMapper;
    }

    public TaskMapper getTaskTypeById(String taskTypeId) {
        TaskType taskType = taskTypeRepository.findByTaskTypeId(taskTypeId);
        TaskMapper taskMapper = new TaskMapper();

        if (null != taskType) {
            taskMapper.setTaskTypeId(taskType.getTaskTypeId());

            taskMapper.setTaskType(taskType.getTaskType());
            taskMapper.setOrganizationId(taskType.getOrgId());
            taskMapper.setUserId(taskType.getUserId());
            taskMapper.setEditInd(taskType.isEditInd());
            taskMapper.setTaskCheckListInd(taskType.isTaskCheckListInd());
            taskMapper.setCreationDate(Utility.getISOFromDate(taskType.getCreationDate()));

            List<TaskTypeDelete> list = taskTypeDeleteRepository.findByOrgId(taskType.getOrgId());
            if (null != list && !list.isEmpty()) {
                Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

                taskMapper.setUpdateDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
                taskMapper.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
            }

        }
        return taskMapper;
    }

    @Override
    public List<TaskViewMapper> getTaskListOfCandidateByCandidateId(String candidateId) {

        List<CandidateTaskLink> list = candidateTaskLinkRepository.getTaskListByCandidateId(candidateId);
        List<TaskViewMapper> mappList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {

            mappList = list.stream().map(li -> getTaskDetails(li.getTaskId())).collect(Collectors.toList());
        }

        return mappList;
    }

    @Override
    public ContactViewMapper saveToTaskConvertPartnerContact(String contactId, String userId, String organizationId) {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setCreation_date(new Date());
        TaskInfo info = taskInfoRepository.save(taskInfo);

        String taskId = info.getTask_id();

        if (null != taskId) {

            TaskDetails taskDetails = new TaskDetails();
            taskDetails.setTask_id(taskId);
            taskDetails.setCreation_date(new Date());
            List<CustomerContactLink> customerContactLink = customerContactLinkRepository
                    .getCustomerLinkByContactId(contactId);
            if (null != customerContactLink && !customerContactLink.isEmpty()) {
                taskDetails.setTask_description("A approve request for converting Customer contact into User");
                taskDetails.setTask_name("Apply for Login Customer Contact to User");
            } else {
                taskDetails.setTask_description("A approve request for converting Vendor contact into User");
                taskDetails.setTask_name("Apply for Login Vendor Contact to User");
            }
            taskDetails.setPriority("High");
            taskDetails.setTask_type("ContactConvertToUser");// apply for login
            taskDetails.setTask_name("Portal access for Contact");
            taskDetails.setTask_status("Completed");
            taskDetails.setUser_id(userId);
            taskDetails.setOrganization_id(organizationId);
            taskDetails.setTime_zone("");
            taskDetails.setLiveInd(true);
            taskDetails.setComplitionInd(false);
            taskDetails.setRating(0);
            taskDetails.setStart_date(new Date());
            taskDetails.setEnd_date(new Date());
            taskDetailsRepository.save(taskDetails);
            ContactDetails contact = contactRepository.getcontactDetailsById(contactId);
            contact.setAccessInd(0);
            contactRepository.save(contact);
            ContactUserLink contactUserLink = new ContactUserLink();
            contactUserLink.setContactId(contactId);
            contactUserLink.setOwnerUser(userId);     
            contactUserLink.setTaskId(taskId);
            contactUserLinkRepository.save(contactUserLink);
        }

        /* insert into todo table */
//		if (null != taskId) {
//			ToDoDetails toDoDetails = new ToDoDetails();
//			toDoDetails.setTaskId(taskId);
//			toDoDetails.setComplitionInd(false);
//			toDoDetails.setCreationDate(new Date());
//			toDoDetails.setUserId(userId);
//			toDoDetails.setRating("Not Given");
//			toDoDetailsRepository.save(toDoDetails);
//		}

        List<EmployeeDetails> empList = employeeRepository.getActiveEmployeesByOrgIdAndRole(organizationId, "ADMIN");
        List<String> employeeIds = (empList.stream().map(li -> li.getUserId()).collect(Collectors.toList()));


        employeeIds.stream().map(employeeId -> {
            System.out.println("emplyeeId=====" + employeeIds);
            EmployeeTaskLink employeeTaskLink = new EmployeeTaskLink();
            employeeTaskLink.setTask_id(taskId);
            employeeTaskLink.setEmployee_id(employeeId);
            employeeTaskLink.setCreation_date(new Date());
            employeeTaskLink.setLive_ind(true);
            employeeTaskRepository.save(employeeTaskLink);

            return employeeTaskLink.getTask_employee_link_id();
        });

        ContactViewMapper resultMapper = contactService.getContactDetailsById(contactId);
        ThirdParty thirdParty = thirdPartyRepository.findByOrgId(organizationId);
        if (thirdParty != null) {
            resultMapper.setThirdPartyAccessInd(thirdParty.isPartnerContactInd());
        }
        return resultMapper;
    }

    @Override
    public String createEmailTask(String candidateId, String userId, String orgId, String body) {
        String taskId = null;
        TaskInfo taskInfo = new TaskInfo();

        taskInfo.setCreation_date(new Date());
        taskId = taskInfoRepository.save(taskInfo).getTask_id();

        if (null != taskId) {

            CandidateDetails candidate = candidateDetailsRepository.getcandidateDetailsById(candidateId);

            String candidateName = "";

            if (null != candidate) {

                candidateName = candidate.getFirstName() + " " + candidate.getMiddleName() + " "
                        + candidate.getLastName();

            }

            TaskDetails taskDetails = new TaskDetails();
            taskDetails.setTask_id(taskId);
            taskDetails.setTask_name("Mailed to" + " " + candidateName);
            taskDetails.setCreation_date(new Date());
            taskDetails.setTask_type("Email");
            taskDetails.setTask_status("Completed");
            taskDetails.setPriority("Low");
            taskDetails.setUser_id(userId);
            taskDetails.setLiveInd(true);
            taskDetails.setTask_description(body);

            String detailsId = taskDetailsRepository.save(taskDetails).getTask_details_id();

            CandidateTaskLink candidateTaskLink = new CandidateTaskLink();
            candidateTaskLink.setCandidateId(candidateId);
            candidateTaskLink.setTaskId(taskId);
            candidateTaskLink.setCreationdate(new Date());
            candidateTaskLink.setLiveInd(true);
            candidateTaskLinkRepository.save(candidateTaskLink);

            EmployeeTaskLink employeeTaskLink = new EmployeeTaskLink();
            employeeTaskLink.setTask_id(taskId);
            employeeTaskLink.setEmployee_id(userId);
            employeeTaskLink.setCreation_date(new Date());
            employeeTaskLink.setLive_ind(true);
            employeeTaskRepository.save(employeeTaskLink);
        }
        return taskId;
    }

    @Override
    public String saveCandidateEmailDetails(CandidateEmailDetailsMapper candidateEmailDetailsMapper) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, TemplateException, IOException {
        String candidateEmailsDetailsId = null;

        CandidateEmailDetails candidateEmailDetails = new CandidateEmailDetails();

        candidateEmailDetails.setAvailableDateInd(candidateEmailDetailsMapper.isAvailableDateInd());
        candidateEmailDetails.setEmailInd(candidateEmailDetailsMapper.isEmailInd());
        candidateEmailDetails.setMobileInd(candidateEmailDetailsMapper.isMobileNoInd());
        candidateEmailDetails.setNameInd(candidateEmailDetailsMapper.isNameInd());
        candidateEmailDetails.setRoleInd(candidateEmailDetailsMapper.isRoleInd());
        candidateEmailDetails.setSkillsInd(candidateEmailDetailsMapper.isSkillInd());

        candidateEmailDetails.setBcc(candidateEmailDetailsMapper.getBcc());
        candidateEmailDetails.setCc(candidateEmailDetailsMapper.getCc());
        candidateEmailDetails.setMessage(candidateEmailDetailsMapper.getMessage());
        candidateEmailDetails.setOrganizationId(candidateEmailDetailsMapper.getOrganizationId());
        candidateEmailDetails.setSubject(candidateEmailDetailsMapper.getSubject());
        candidateEmailDetails.setTo(candidateEmailDetailsMapper.getTo());
        candidateEmailDetails.setUserId(candidateEmailDetailsMapper.getUserId());

        candidateEmailDetails.setCustomer1(candidateEmailDetailsMapper.getCustomer1());
        candidateEmailDetails.setCustomer2(candidateEmailDetailsMapper.getCustomer2());
        candidateEmailDetails.setCustomer3(candidateEmailDetailsMapper.getCustomer3());
        candidateEmailDetails.setContact1(candidateEmailDetailsMapper.getContact1());
        candidateEmailDetails.setContact2(candidateEmailDetailsMapper.getContact2());
        candidateEmailDetails.setContact3(candidateEmailDetailsMapper.getContact3());


        candidateEmailsDetailsId = candidateEmailDetailsRepository.save(candidateEmailDetails).getId();
        String subject = candidateEmailDetailsMapper.getSubject();
        String msg = "<h1>" + candidateEmailDetailsMapper.getMessage() + "</h1></br>" +
                candidateShareTemplate(candidateEmailsDetailsId, candidateEmailDetailsMapper);

        String serverUrl = "https://develop.tekorero.com/kite/email/send";
        String fromEmail = "support@innoverenit.com";


        //body.add("toEmail", candidateEmailDetailsMapper.getTo());
        List<String> to = new ArrayList<>();
        to.add(candidateEmailDetailsMapper.getTo());
        if (!StringUtils.isEmpty(candidateEmailDetailsMapper.getCc())) {
            to.add(candidateEmailDetailsMapper.getCc());
        }
        if (!StringUtils.isEmpty(candidateEmailDetailsMapper.getBcc())) {
            to.add(candidateEmailDetailsMapper.getBcc());
        }
        //	to.add("skb4mail@gmail.com");
//
//        for (String one : to) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            body.add("fromEmail", fromEmail);
//            body.add("message", msg);
//            body.add("subject", subject);
//            body.add("toEmail", one);
//
//            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
////		System.out.println("response=================="+response.toString());
////		System.out.println("User_emailId======================="+one);
////		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        }
        return candidateEmailsDetailsId = candidateEmailDetailsRepository.save(candidateEmailDetails).getId();

    }

    private String candidateShareTemplate(String candidateEmailsDetailsId,
                                          CandidateEmailDetailsMapper candidateEmailDetailsMapper) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, TemplateException, IOException {
        List<String> candiList = candidateEmailDetailsMapper.getCandidateIds();
        HashMap root = new LinkedHashMap<>();
        List<List> candidatList = new ArrayList<>();
        List<String> header = new ArrayList<>();
        if (candidateEmailDetailsMapper.isNameInd()) {
            header.add("Name");
        }
        if (candidateEmailDetailsMapper.isEmailInd()) {
            header.add("Email");
        }
        if (candidateEmailDetailsMapper.isMobileNoInd()) {
            header.add("Mobile");
        }
        if (candidateEmailDetailsMapper.isAvailableDateInd()) {
            header.add("AvailableDate");
        }
        if (candidateEmailDetailsMapper.isSkillInd()) {
            header.add("Skill");
        }
        if (candidateEmailDetailsMapper.isRoleInd()) {
            header.add("Role");
        }
        if (candidateEmailDetailsMapper.isCategoryInd()) {
            header.add("Category");
        }
        if (candidateEmailDetailsMapper.isBillingInd()) {
            header.add("Billing");
        }

        if (candidateEmailDetailsMapper.isExperienceInd()) {
            header.add("Exprience");
        }
        if (candidateEmailDetailsMapper.isSkillWiseExperienceInd()) {
            header.add("SkillWise Exprience");
        }
        if (candidateEmailDetailsMapper.isIdentityCardInd()) {
            header.add("Identity Card");
        }
        if (candidateEmailDetailsMapper.isResumeInd()) {
            header.add("Resume");
        }
        candidatList.add(header);

        if (null != candiList && !candiList.isEmpty()) {
            for (String candidateId : candiList) {
                //CandidateEmailResponseMapper candidateEmailResponseMapper = new CandidateEmailResponseMapper();
                CandidateEmailLink CandidateEmailLink = new CandidateEmailLink();
                CandidateEmailLink.setCandidateId(candidateId);
                CandidateEmailLink.setCandidateEmailsDetailsId(candidateEmailsDetailsId);
                candidateEmailLinkRepository.save(CandidateEmailLink);

                CandidateDetails candidate = candidateDetailsRepository.getcandidateDetailsById(candidateId);

                List<String> cn = new ArrayList<>();

                if (candidateEmailDetailsMapper.isNameInd()) {
                    cn.add(candidate.getFullName());
                }
                if (candidateEmailDetailsMapper.isEmailInd()) {
                    cn.add(candidate.getEmailId());
                }
                if (candidateEmailDetailsMapper.isMobileNoInd()) {
                    if (!StringUtils.isEmpty(candidate.getMobileNumber())) {
                        cn.add(candidate.getMobileNumber());
                    } else {
                        cn.add("");
                    }
                }
                if (candidateEmailDetailsMapper.isAvailableDateInd()) {
                    if (candidate.getAvailableDate() != null) {
                        cn.add(Utility.getISOFromDate(candidate.getAvailableDate()));
                    } else {
                        cn.add("");
                    }
                }
                if (candidateEmailDetailsMapper.isSkillInd()) {
                    String skill = "";


                    List<String> skillList1 = new ArrayList<String>();
                    List<SkillSetDetails> list = skillSetRepository.getSkillSetById(candidateId);
                    if (null != list && !list.isEmpty()) {
                        for (SkillSetDetails skillSetDetails : list) {
                            SkillSetDetails list2 = skillSetRepository.getById(skillSetDetails.getSkillSetDetailsId());

                            if (null != list2) {
                                DefinationDetails definationDetails1 = definationRepository.findByDefinationId(list2.getSkillName());
                                if (null != definationDetails1) {
                                    skillList1.add(definationDetails1.getName());

                                }
                            }
                        }

                    }
                    for (String s : skillList1) {
                        if (s.equalsIgnoreCase(skillList1.get(skillList1.size() - 1))) {
                            skill += s;
                        } else {
                            skill += s + ",";
                        }
                    }
                    cn.add(skill);
                }
                if (candidateEmailDetailsMapper.isRoleInd()) {
                    if (!StringUtils.isEmpty(candidate.getRoleType())) {
                        RoleType roleType = roleTypeRepository.findByRoleTypeId(candidate.getRoleType());
                        if (null != roleType) {
                            cn.add(roleType.getRoleType());
                        }
                    } else {
                        cn.add("");
                    }
                }
                if (candidateEmailDetailsMapper.isCategoryInd()) {
                    if (!StringUtils.isEmpty(candidate.getCategory())) {
                        cn.add(candidate.getCategory());
                    } else {
                        cn.add("");
                    }
                }
                if (candidateEmailDetailsMapper.isBillingInd()) {
                    System.out.println("Billing>>>>>" + candidate.getBilling());
                    if (!StringUtils.isEmpty(candidate.getBilling())) {
                        cn.add(candidate.getBilling());
                    } else {
                        cn.add("");
                    }
                }

                if (candidateEmailDetailsMapper.isExperienceInd()) {
                    cn.add(String.valueOf(candidateService.getExprience(candidate.getCreationDate()) + candidate.getExperience()));

                }
                if (candidateEmailDetailsMapper.isIdentityCardInd()) {
                    if (candidate.getIdProof() != null && candidate.getIdNumber() != null) {
                        cn.add(candidate.getIdProof() + "=" + candidate.getIdNumber());
                    } else {
                        cn.add("");
                    }
                }

                if (candidateEmailDetailsMapper.isSkillWiseExperienceInd()) {
                    String skill = "";


                    List<String> skillList1 = new ArrayList<String>();
                    List<SkillSetDetails> list = skillSetRepository.getSkillSetById(candidateId);
                    if (null != list && !list.isEmpty()) {
                        for (SkillSetDetails skillSetDetails : list) {
                            DefinationDetails definationDetails1 = definationRepository.findByDefinationId(skillSetDetails.getSkillName());
                            if (null != definationDetails1) {
                                skillList1.add(definationDetails1.getName() + "-" + String.valueOf(skillSetDetails.getExperience()));
                            }
                        }

                    }
                    for (String s : skillList1) {
                        if (s.equalsIgnoreCase(skillList1.get(skillList1.size() - 1))) {
                            skill += s;
                        } else {
                            skill += s + ",";
                        }
                    }
                    cn.add(skill);
                }
                if (candidateEmailDetailsMapper.isResumeInd()) {
                    //	candidateService.resentResume(candidate.getCandidateId());
                    if (null != candidateService.resentResume(candidate.getCandidateId())) {
                        String docId = candidateService.resentResume(candidate.getCandidateId()).getDocumentId();
                        String url = "https://develop.tekorero.com/employeePortal/api/v1/document/" + docId;
//			        		System.out.println("DOCID1======"+docId);
//			        		System.out.println("url======"+url);
                        cn.add(url);
                    } else {
                        cn.add("");
//			        		System.out.println("77777");
                    }
                }
                candidatList.add(cn);
            }
            root.put("root", candidatList);
        }

        //System.out.println("root>>>>>>>>>"+candidatList.toString());
        StringWriter stringWriter = new StringWriter();


        configuration.getTemplate("candidateShare.ftlh").process(root, stringWriter);

        return stringWriter.getBuffer().toString();
    }

    @Override
    public List<TaskMapper> getTasktypeByNameByOrgLevel(String name, String orgId) {

        List<TaskType> list = taskTypeRepository.findByTaskTypeContainingAndOrgId(name,orgId);
        List<TaskMapper> resultList = new ArrayList<TaskMapper>();

        if (null != list && !list.isEmpty()) {
            list.stream().map(taskType -> {
                System.out.println("TaskTypeById=========" + taskType.getTaskTypeId());
                TaskMapper taskMapper = getTaskTypeById(taskType.getTaskTypeId());
                if (null != taskMapper) {
                    resultList.add(taskMapper);
                }
                return resultList;
            }).collect(Collectors.toList());

        }
        return resultList;
    }

    @Override
    public boolean checkTaskNameInTaskTypeByOrgLevel(String taskType, String orgId) {
        List<TaskType> taskTypes = taskTypeRepository.findByTaskTypeAndLiveIndAndOrgId(taskType,true,orgId);
        if (!taskTypes.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteTaskTypeById(String taskTypeId) {
        if (null != taskTypeId) {
            TaskType taskType = taskTypeRepository.findByTaskTypeId(taskTypeId);

            TaskTypeDelete taskTypeDelete = taskTypeDeleteRepository.findByTaskTypeId(taskTypeId);
            if (null != taskTypeDelete) {
                taskTypeDelete.setUpdationDate(new Date());
                taskTypeDelete.setUpdatedBy(taskType.getUserId());
                taskTypeDeleteRepository.save(taskTypeDelete);
            }
            taskType.setLiveInd(false);
            taskTypeRepository.save(taskType);
        }

    }

    @Override
    public HashMap getTaskListsByUserIdStartdateAndEndDate(String userId, String startDate, String endDate) {
        HashMap map = new HashMap();

        Date end_date = null;
        Date start_date = null;
        try {
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<TaskDetails> taskDetails = taskDetailsRepository.getTaskListsByUserIdAndStartdateAndEndDateAndLiveInd(userId, start_date, end_date);
        if (null != taskDetails && !taskDetails.isEmpty()) {
            map.put("totalTask", taskDetails.size());

            System.out.println("totalTask" + taskDetails.size());

        } else {
            map.put("totalTask", taskDetails.size() + " , No Task Created");

            System.out.println("totalTask" + taskDetails.size());
        }

        List<TaskDetails> taskDetails1 = taskDetailsRepository.getTaskListsByUserIdAndStartdateAndEndDateAndComplitionInd(userId, start_date, end_date);
        if (null != taskDetails1 && !taskDetails1.isEmpty()) {
            map.put("totalTaskCompleted", taskDetails1.size());
        } else {
            map.put("totalTaskCompleted", taskDetails1.size() + " , No Task Completed");
        }

        List<TaskDetails> taskDetails2 = taskDetailsRepository.getTaskListByUserIdAndStartdateAndEndDateAndComplitionInd(userId, start_date, end_date);
        if (null != taskDetails2 && !taskDetails2.isEmpty()) {
            map.put("totalTaskInCompleted", taskDetails2.size());
        } else {
            map.put("totalTaskInCompleted", taskDetails2.size() + " , No Task Pending");
        }

        return map;
    }

    @Override
    public Map<String, List<TaskViewMapper>> getTaskListsByTaskTypeAndUserIdAndStartdateAndEndDate(String userId, String startDate, String endDate) {

        List<TaskViewMapper> resultList = new ArrayList<TaskViewMapper>();

        int taskTypeCount = 0;
        int taskDetailsCount = 0;
        int total = 0;

        Date end_date = null;
        Date start_date = null;
        try {
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<TaskType> taskTypes = taskTypeRepository.findByUserIdAndLiveInd(userId, true);
        if (null != taskTypes && !taskTypes.isEmpty()) {
            for (TaskType taskType : taskTypes) {

                taskTypeCount++;

                List<TaskDetails> taskDetails = taskDetailsRepository.getTaskListsByTaskTypeIdAndStartdateAndEndDateAndLiveInd(taskType.getTaskTypeId(), start_date, end_date);
                if (null != taskDetails && !taskDetails.isEmpty()) {
                    for (TaskDetails taskDetail : taskDetails) {
                        taskDetailsCount++;
                        TaskViewMapper taskViewMapper = getTaskDetails(taskDetail.getTask_id());
                        resultList.add(taskViewMapper);
                    }
                }
            }

        }

//		System.out.println("eventTypeCount==========="+eventTypeCount);
//		System.out.println("eventDetailsCount==========="+eventDetailsCount);
//		System.out.println("total==========="+resultList.size());

        Map<String, List<TaskViewMapper>> EmpByDepartment = new HashMap<>();


        resultList.stream().map(p -> {
            if (!EmpByDepartment.containsKey(p.getTaskType())) {
                EmpByDepartment.put(p.getTaskType(), new ArrayList<>());
            }
            EmpByDepartment.get(p.getTaskType()).add(p);

            return EmpByDepartment;
        }).collect(Collectors.toList());
        //System.out.println("EmpByDepartment================="+EmpByDepartment);

        return EmpByDepartment;
    }

    @Override
    public List<TaskViewMapper> getTaskByCandidateIdForWebsite(String candidateId) {
        List<TaskViewMapper> resultList = new ArrayList<TaskViewMapper>();
        List<CandidateTaskLink> candidateTaskList = candidateTaskLinkRepository.getTaskListByCandidateId(candidateId);
        System.out.println("Size==++++++++++++++++++" + candidateTaskList.size());
        if (null != candidateTaskList && !candidateTaskList.isEmpty()) {

            candidateTaskList.stream().map(candidateTaskLink -> {
                TaskViewMapper taskMapper = getTaskDetailsForCandidate(candidateTaskLink.getCandidateId(), candidateTaskLink.getTaskId());
                if (null != taskMapper.getTaskId()) {
                    resultList.add(taskMapper);
                }

                return resultList;
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public TaskViewMapper updateCandidateComplitionStatus(TaskMapper taskMapper, String candidateId, String taskId) {
        TaskViewMapper resultMapper = new TaskViewMapper();
        if (null != candidateId && null != taskId) {
            CandidateTaskLink candidateTaskLink = candidateTaskLinkRepository.findByCandidateIdAndTaskIdAndLiveInd(candidateId, taskId, true);
            if (null != candidateTaskLink) {
                candidateTaskLink.setComplitionStatus(taskMapper.getComplitionStatus());
                candidateTaskLinkRepository.save(candidateTaskLink);
                resultMapper.setComplitionStatus(taskMapper.getComplitionStatus());
            }
        }
        resultMapper = getTaskDetailsForCandidate(candidateId, taskId);
        return resultMapper;
    }


    @Override
    public TaskViewMapper getTaskDetailsForCandidate(String candidateId, String taskId) {

        TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskId);

        TaskViewMapper taskMapper = new TaskViewMapper();

        if (null != taskDetails) {

            if (taskDetails.getTask_type() != null && taskDetails.getTask_type().trim().length() > 0) {
                TaskType taskType = taskTypeRepository.findByTaskTypeId(taskDetails.getTask_type());
                if (null != taskType) {
                    taskMapper.setTaskType(taskType.getTaskType());
                    taskMapper.setTaskTypeId(taskType.getTaskTypeId());
                } else {
                    taskMapper.setTaskType(taskDetails.getTask_type());
                }
            }
            System.out.println("setTaskId========2==" + taskId);
            taskMapper.setTaskId(taskId);
            taskMapper.setEndDate(Utility.getISOFromDate(taskDetails.getEnd_date()));
            taskMapper.setStartDate(Utility.getISOFromDate(taskDetails.getStart_date()));
            taskMapper.setTaskDescription(taskDetails.getTask_description());
            taskMapper.setPriority(taskDetails.getPriority());
            taskMapper.setTaskStatus(taskDetails.getTask_status());
            taskMapper.setTaskName(taskDetails.getTask_name());
            taskMapper.setTimeZone(taskDetails.getTime_zone());
            taskMapper.setUserId(taskDetails.getUser_id());
            taskMapper.setOrganizationId(taskDetails.getOrganization_id());
            taskMapper.setAssignedOn(Utility.getISOFromDate(taskDetails.getCreation_date()));
            taskMapper.setCreationDate(Utility.getISOFromDate(taskDetails.getCreation_date()));
            taskMapper.setUpdateDate(Utility.getISOFromDate(taskDetails.getUpdateDate()));
            taskMapper.setCompletionInd(taskDetails.isComplitionInd());
            taskMapper.setRating(taskDetails.getRating());

            taskMapper.setProjectId(taskDetails.getProjectName());
            if (null != taskDetails.getProjectName() && !taskDetails.getProjectName().isEmpty()) {
                ProjectDetails projectDetails = projectRepository.getById(taskDetails.getProjectName());
                System.out.println("getProjectName========1==" + taskDetails.getProjectName());
                if (null != projectDetails) {
                    System.out.println("getProjectName========2==" + projectDetails.getProjectName());
                    taskMapper.setProjectName(projectDetails.getProjectName());
                }
            }

            taskMapper.setValue(taskDetails.getValue());
            taskMapper.setUnit(taskDetails.getUnit());
            taskMapper.setImageId(taskDetails.getImageId());
//            taskMapper.setDocumentId(taskDetails.getDocumentId());
            taskMapper.setComplexity(taskDetails.getComplexity());
            taskMapper.setCustomerId(taskDetails.getCustomerId());
            taskMapper.setAssignedToName(employeeService.getEmployeeFullName(taskDetails.getAssigned_to()));
            taskMapper.setAssignedTo(taskDetails.getAssigned_to());


            taskMapper.setTaskChecklistId(taskDetails.getTaskChecklistId());
            TaskChecklist taskChecklist = taskChecklistRepository.findByTaskChecklistId(taskDetails.getTaskChecklistId());
            if (null != taskChecklist) {
                taskMapper.setTaskChecklist(taskChecklist.getTaskChecklistName());
            }
            if (null != taskDetails.getTaskChecklistId() && !taskDetails.getTaskChecklistId().isEmpty()) {
                taskMapper.setTaskChecklistStageLinkMapper(categoryService.getAllTaskChecklistStageLinkByTaskChecklistId(taskDetails.getTaskChecklistId()));
            }


            Customer customer = customerRepository
                    .getCustomerDetailsByCustomerId(taskDetails.getCustomerId());
            if (null != customer) {
                taskMapper.setCustomerName(customer.getName());
            }

            taskMapper.setAssignedDate(Utility.getISOFromDate(taskDetails.getAssignedDate()));

            //taskMapper.setRatePerUnit(taskDetails.getRatePerUnit());


            CandidateTaskLink candidateTaskLink = candidateTaskLinkRepository.findByCandidateIdAndTaskIdAndLiveInd(candidateId, taskId, true);
            if (null != candidateTaskLink) {
                taskMapper.setComplitionStatus(candidateTaskLink.getComplitionStatus());
            }

            System.out.println("user_id=========" + taskDetails.getUser_id());
            EmployeeViewMapper empMapper = employeeService.getEmployeeDetailsByEmployeeId(taskDetails.getUser_id());
            if (null != empMapper) {
                taskMapper.setSubmittedBy(empMapper.getEmailId());
                System.out.println("submittedBy........" + empMapper.getEmailId());

            }

            if (null != taskDetails.getApproved_date()) {

                taskMapper.setApprovedDate(Utility.getISOFromDate(taskDetails.getApproved_date()));

            }
            taskMapper.setApprovedInd(taskDetails.getApproved_ind());
        }
        return taskMapper;
    }

    @Override
    public List<TaskViewMapper> getListOfTaskByProjectId(String projectId) {
        List<TaskViewMapper> resultList = new ArrayList<TaskViewMapper>();

        List<TaskDetails> taskDetails = taskDetailsRepository.getByProjectNameAndLiveInd(projectId, true);
        if (null != taskDetails && !taskDetails.isEmpty()) {
            taskDetails.stream().map(task -> {
                TaskViewMapper taskMapper = getTaskDetails(task.getTask_id());
                if (null != taskMapper) {
                    resultList.add(taskMapper);
                }
                return resultList;
            }).collect(Collectors.toList());
        }

        return resultList;
    }

    @Scheduled(cron = "0 0 0 * * *")
    //@Scheduled(cron = "0 48 16 * * ?")
    public void saveTaskToNoificaation() {
        System.out.println("SCHEDULAR STARTED.................");
        Date startDate = Utility.removeTime(new Date());
        Date endDate = Utility.getDateAfterEndDate(Utility.removeTime(startDate));
        System.out.println(startDate + "\\" + endDate);
        List<TaskDetails> taskDetailsList = taskDetailsRepository.getTodayTaskDetailsList(startDate, endDate);
        System.out.println("taskDetailsList==" + taskDetailsList.toString());
        //taskDetails.forEach(task -> {
        for (TaskDetails task : taskDetailsList) {

            List<String> empIds = new ArrayList<>();
            ;
            if (!StringUtils.isEmpty(task.getAssigned_to())) {
                empIds.add(task.getAssigned_to());
            }
            empIds.add(task.getUser_id());
            for (String empId : empIds) {
                /* insert to Notification info */
                NotificationDetails notification = new NotificationDetails();
                notification.setNotificationType("task");
                notification.setMessage(" Today is the Last date of the " + task.getTask_name() + "task.");
                notification.setAssignedTo(empId);
                notification.setUser_id(task.getUser_id());
                notification.setNotificationDate(new Date());
                notification.setOrg_id(notification.getOrg_id());
                notification.setMessageReadInd(false);
                String u = notificationRepository.save(notification).getNotificationId();
                System.out.println("printinggggg.......1");
                System.out.println("KKKK======" + u);
            }

            /* insert to CandidateEventLink table */
            List<CandidateTaskLink> candidateTaskLinkList = candidateTaskLinkRepository.getCandidateTaskLinkByTaskId(task.getTask_id());
            if (null != candidateTaskLinkList) {
                //candidateTaskLink.forEach(candidate -> {
                for (CandidateTaskLink candidate : candidateTaskLinkList) {
                    /* insert to Notification info */
                    NotificationDetails notification = new NotificationDetails();
                    notification.setNotificationType("task");
                    notification.setMessage(" Today is the Last date of the " + task.getTask_name() + "task.");
                    notification.setAssignedTo(candidate.getCandidateId());
                    notification.setUser_id(task.getUser_id());
                    notification.setNotificationDate(new Date());
                    notification.setOrg_id(notification.getOrg_id());
                    notification.setMessageReadInd(false);
                    notificationRepository.save(notification);
                    System.out.println("printinggggg.......2");
                }
                //});
            }
        }
        //});
    }

    @Override
    public TaskViewMapper updatetaskCompletionInd(String taskId, TaskMapper taskMapper) {
        if (null != taskId) {
            TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskId);
            if (null != taskDetails) {
                taskDetails.setTask_status(taskMapper.getTaskStatus());
                if(taskMapper.getTaskStatus().equalsIgnoreCase("Completed")){
                	taskDetails.setComplitionInd(true);
                	}else {
                		taskDetails.setComplitionInd(false);
                	}
                taskDetailsRepository.save(taskDetails);
                
                List<EmployeeTaskLink> empList =
                        employeeTaskRepository.getEmpListByTaskId(taskId);
                if (null != empList && !empList.isEmpty()) {
                    empList.forEach(employee -> {
                    	employee.setApproveStatus(taskMapper.getTaskStatus());   
                    	employee.setApprovedBy(taskMapper.getUserId()); 
                    	employeeTaskRepository.save(employee);
                    });
                }
            }
        }
        TaskViewMapper resultMapper = getTaskDetails(taskId);

        return resultMapper;
    }

    @Override
    public List<TaskViewMapper> getListOfTaskByCandidateId(String candidateId) {
        List<TaskViewMapper> resultList = new ArrayList<TaskViewMapper>();

        List<CandidateTaskLink> candidateTaskLinkList = candidateTaskLinkRepository.getTaskListByCandidateId(candidateId);
        if (null != candidateTaskLinkList && !candidateTaskLinkList.isEmpty()) {

            candidateTaskLinkList.stream().map(task -> {
                TaskViewMapper taskMapper = getUnCompletedTaskDetailsForCandidate(candidateId, task.getTaskId());
                if (null != taskMapper.getTaskId()) {

                    resultList.add(taskMapper);
                }
                return resultList;
            }).collect(Collectors.toList());
        }

        return resultList;
    }


    @Override
    public TaskViewMapper getUnCompletedTaskDetailsForCandidate(String candidateId, String taskId) {

        TaskDetails taskDetails = taskDetailsRepository.getUnCompletedTaskDetailsByTaskId(taskId);

        TaskViewMapper taskMapper = new TaskViewMapper();

        if (null != taskDetails) {

            if (taskDetails.getTask_type() != null && taskDetails.getTask_type().trim().length() > 0) {
                TaskType taskType = taskTypeRepository.findByTaskTypeId(taskDetails.getTask_type());
                if (null != taskType) {
                    taskMapper.setTaskType(taskType.getTaskType());
                    taskMapper.setTaskTypeId(taskType.getTaskTypeId());
                } else {
                    taskMapper.setTaskType(taskDetails.getTask_type());
                }
            }

            taskMapper.setTaskId(taskId);
            taskMapper.setEndDate(Utility.getISOFromDate(taskDetails.getEnd_date()));
            taskMapper.setStartDate(Utility.getISOFromDate(taskDetails.getStart_date()));
            taskMapper.setTaskDescription(taskDetails.getTask_description());
            taskMapper.setPriority(taskDetails.getPriority());
            taskMapper.setTaskStatus(taskDetails.getTask_status());
            taskMapper.setTaskName(taskDetails.getTask_name());
            taskMapper.setTimeZone(taskDetails.getTime_zone());
            taskMapper.setUserId(taskDetails.getUser_id());
            taskMapper.setOrganizationId(taskDetails.getOrganization_id());
            taskMapper.setAssignedOn(Utility.getISOFromDate(taskDetails.getCreation_date()));
            taskMapper.setCreationDate(Utility.getISOFromDate(taskDetails.getCreation_date()));
            taskMapper.setUpdateDate(Utility.getISOFromDate(taskDetails.getUpdateDate()));
            taskMapper.setCompletionInd(taskDetails.isComplitionInd());
            taskMapper.setRating(taskDetails.getRating());

            taskMapper.setProjectId(taskDetails.getProjectName());
            ProjectDetails projectDetails = projectRepository.getById(taskDetails.getProjectName());
            if (null != projectDetails) {
                taskMapper.setProjectName(projectDetails.getProjectName());
            }

            taskMapper.setValue(taskDetails.getValue());
            taskMapper.setUnit(taskDetails.getUnit());
            taskMapper.setImageId(taskDetails.getImageId());
//            taskMapper.setDocumentId(taskDetails.getDocumentId());
            taskMapper.setComplexity(taskDetails.getComplexity());
            taskMapper.setCustomerId(taskDetails.getCustomerId());
            taskMapper.setAssignedToName(employeeService.getEmployeeFullName(taskDetails.getAssigned_to()));
            taskMapper.setAssignedTo(taskDetails.getAssigned_to());


            taskMapper.setTaskChecklistId(taskDetails.getTaskChecklistId());
            TaskChecklist taskChecklist = taskChecklistRepository.findByTaskChecklistId(taskDetails.getTaskChecklistId());
            if (null != taskChecklist) {
                taskMapper.setTaskChecklist(taskChecklist.getTaskChecklistName());
            }
            taskMapper.setTaskChecklistStageLinkMapper(categoryService.getAllTaskChecklistStageLinkByTaskChecklistId(taskDetails.getTaskChecklistId()));


            Customer customer = customerRepository
                    .getCustomerDetailsByCustomerId(taskDetails.getCustomerId());
            if (null != customer) {
                taskMapper.setCustomerName(customer.getName());
            }

            taskMapper.setAssignedDate(Utility.getISOFromDate(taskDetails.getAssignedDate()));


            CandidateTaskLink candidateTaskLink = candidateTaskLinkRepository.findByCandidateIdAndTaskIdAndLiveInd(candidateId, taskId, true);
            if (null != candidateTaskLink) {
                taskMapper.setComplitionStatus(candidateTaskLink.getComplitionStatus());
            }

            System.out.println("user_id=========" + taskDetails.getUser_id());
            EmployeeViewMapper empMapper = employeeService.getEmployeeDetailsByEmployeeId(taskDetails.getUser_id());
            if (null != empMapper) {
                taskMapper.setSubmittedBy(empMapper.getEmailId());
                System.out.println("submittedBy........" + empMapper.getEmailId());

            }

            if (null != taskDetails.getApproved_date()) {

                taskMapper.setApprovedDate(Utility.getISOFromDate(taskDetails.getApproved_date()));

            }
            taskMapper.setApprovedInd(taskDetails.getApproved_ind());
        }
        return taskMapper;
    }

    @Override
    public List<TaskViewMapper> getDeletedTaskListByEmpId(String employeeId) {
        List<TaskViewMapper> resultList = new ArrayList<TaskViewMapper>();

        List<EmployeeTaskLink> employeeTaskLink = employeeTaskRepository.getDeletedTaskListByEmpId(employeeId);
        System.out.println("Size==++++++++++++++++++" + employeeTaskLink.size());
        if (null != employeeTaskLink && !employeeTaskLink.isEmpty()) {
            //int i = 0;
            //for (EmployeeTaskLink employeeTaskLink : employeeTaskList) {

            employeeTaskLink.stream().map(taskLink -> {
                TaskViewMapper taskMapper = getTaskDetails(taskLink.getTask_id());
                if (null != taskMapper.getTaskId()) {
                    if (null != taskMapper.getTaskType()) {
                        if (taskMapper.getTaskType().equalsIgnoreCase("StageApproval")) {
                            taskMapper.setTaskStatus(taskLink.getApproveStatus());
                        }
                    }
                    resultList.add(taskMapper);
                }

                return resultList;
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public TaskCommentMapper createTaskComment(TaskCommentMapper taskContactMapper) {
        TaskComment taskComment = new TaskComment();

        taskComment.setCreationDate(new Date());
        taskComment.setComment(taskContactMapper.getComment());
        taskComment.setLiveInd(true);
        taskComment.setOrgId(taskContactMapper.getOrgId());
        taskComment.setProviderId(taskContactMapper.getProviderId());
        taskComment.setTaskId(taskContactMapper.getTaskId());

        TaskCommentMapper taskCommentMapper1 = getTaskCommentByTaskCommentId(taskCommentRepository.save(taskComment).getTaskCommentId());

        return taskCommentMapper1;
    }

    @Override
    public TaskCommentMapper getTaskCommentByTaskCommentId(String taskCommentId) {
        TaskCommentMapper taskCommentMapper1 = new TaskCommentMapper();
        TaskComment taskComment = taskCommentRepository.getById(taskCommentId);
        if (null != taskComment) {
            taskCommentMapper1.setComment(taskComment.getComment());
            taskCommentMapper1.setCreationDate(Utility.getISOFromDate(taskComment.getCreationDate()));
            taskCommentMapper1.setLiveInd(taskComment.isLiveInd());
            taskCommentMapper1.setOrgId(taskComment.getOrgId());
            taskCommentMapper1.setTaskCommentId(taskComment.getTaskCommentId());
            taskCommentMapper1.setTaskId(taskComment.getTaskId());
            taskCommentMapper1.setProviderId(taskComment.getProviderId());

            EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(taskComment.getProviderId(), true);
            if (null != employeeDetails) {
                taskCommentMapper1.setProviderName(employeeService.getEmployeeFullName(taskComment.getProviderId()));
                System.out.println("setProviderName........1" + employeeService.getEmployeeFullName(taskComment.getProviderId()));
                System.out.println("setProviderName........2" + taskCommentMapper1.getProviderName());

            } else {
                taskCommentMapper1.setProviderName(candidateService.getCandidateFullName(taskComment.getProviderId()));
                System.out.println("setProviderName........3" + candidateService.getCandidateFullName(taskComment.getProviderId()));
                System.out.println("setProviderName........4" + taskCommentMapper1.getProviderName());
            }

        }

        return taskCommentMapper1;
    }

    @Override
    public List<TaskCommentMapper> getTaskCommentListByTaskId(String taskId) {
        List<TaskCommentMapper> resultList = new ArrayList<TaskCommentMapper>();
        List<TaskComment> taskComment = taskCommentRepository.getByTaskId(taskId);
        if (null != taskComment && !taskComment.isEmpty()) {
            taskComment.stream().map(taskComment1 -> {

                TaskCommentMapper taskCommentMapper1 = getTaskCommentByTaskCommentId(taskComment1.getTaskCommentId());
                resultList.add(taskCommentMapper1);

                return resultList;
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public TaskViewMapper reApplyLeavesByLeaveId(String leaveId) {
        String newTaskId = null;
        LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByLeaveId(leaveId);

        if (null != leaveDetails) {

            TaskDetails dbTaskDetails = taskDetailsRepository.getTaskDetailsById(leaveDetails.getTask_id());
            if (null != dbTaskDetails) {
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.setCreation_date(new Date());
                String taskId = taskInfoRepository.save(taskInfo).getTask_id();

                TaskDetails taskDetails = new TaskDetails();
                setPropertyOnInput(dbTaskDetails, taskDetails);
                taskDetails.setReapplyCount(taskDetails.getReapplyCount() + 1);
                taskDetailsRepository.save(taskDetails);

                leaveDetails.setStatus("");
                leaveDetails.setTask_id(taskId);
                newTaskId = leaveDetailsRepository.save(leaveDetails).getTask_id();
            }
        }
        TaskViewMapper taskMapper = getTaskDetails(newTaskId);
        return taskMapper;
    }

    @Override
    public String deleteLeave(String leaveId) {
        LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByLeaveId(leaveId);
        String msg = "Leave is not deleted becoz it Approved or Rejected";
        if (null != leaveDetails) {
            if (!leaveDetails.getStatus().equalsIgnoreCase("Approved")) {
                TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(leaveDetails.getTask_id());
                taskDetails.setLiveInd(false);
                taskDetailsRepository.save(taskDetails);
                leaveDetails.setLive_ind(false);
                leaveDetailsRepository.save(leaveDetails);
                msg = "Leave is deleted successfully";
            } else if (leaveDetails.getStatus().equalsIgnoreCase("Approved")) {
                TaskDetails dbTaskDetails = taskDetailsRepository.getTaskDetailsById(leaveDetails.getTask_id());
                String str = employeeService.getEmployeeFullName(dbTaskDetails.getUser_id()) +
                        "Wants to delete this leave. || ";
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.setCreation_date(new Date());
                String taskId = taskInfoRepository.save(taskInfo).getTask_id();

                if (null != taskId) {
                    /* insert to task details table */
                    TaskDetails taskDetails = new TaskDetails();
                    setPropertyOnInput(dbTaskDetails, taskDetails);
                    taskDetails.setTask_id(taskId);
                    taskDetails.setTask_name(str + dbTaskDetails.getTask_name());
                    taskDetailsRepository.save(taskDetails);
                }
                EmployeeTaskLink dbEmployeeTaskLink =
                        employeeTaskRepository.getEmpByTaskId(dbTaskDetails.getTask_id());
                EmployeeTaskLink employeeTaskLink = new EmployeeTaskLink();
                employeeTaskLink.setTask_id(taskId);
                employeeTaskLink.setCreation_date(new Date());
                employeeTaskLink.setLive_ind(true);
                employeeTaskLink.setEmployee_id(dbEmployeeTaskLink.getEmployee_id());
                employeeTaskRepository.save(employeeTaskLink);

                leaveDetails.setTask_id(taskId);
                leaveDetailsRepository.save(leaveDetails);
                msg = "Admin will delete the leave..";
            }
        }
        return msg;
    }

    @Override
    public List<TaskViewMapper> getTaskDetailsByEmployeeId(String userId) {
        List<TaskViewMapper> resultList = new ArrayList<TaskViewMapper>();
        List<EmployeeTaskLink> employeeTaskList = employeeTaskRepository.getMyTaskListByEmpId(userId);
//		System.out.println("Size==++++++++++++++++++" + employeeTaskList.size());
        if (null != employeeTaskList && !employeeTaskList.isEmpty()) {
            //int i = 0;
            //for (EmployeeTaskLink employeeTaskLink : employeeTaskList) {

            employeeTaskList.stream().map(employeeTaskLink -> {
                TaskViewMapper taskMapper = getTaskDetails(employeeTaskLink.getTask_id());
                if (null != taskMapper.getTaskId()) {
                    if (null != taskMapper.getTaskType()) {
                        if (taskMapper.getTaskType().equalsIgnoreCase("StageApproval")) {
                            taskMapper.setTaskStatus(employeeTaskLink.getApproveStatus());
                        }
                    }
                    resultList.add(taskMapper);
                }

                return resultList;
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    private void setPropertyOnInput(TaskDetails dbTaskDetails, TaskDetails taskDetails) {

        /* insert to task details table */
        taskDetails.setCreation_date(new Date());
        taskDetails.setTask_description(dbTaskDetails.getTask_description());
        taskDetails.setPriority("Medium");
        taskDetails.setTask_type("leave");
        taskDetails.setTask_status("Completed");
        taskDetails.setUser_id(dbTaskDetails.getUser_id());
        taskDetails.setOrganization_id(dbTaskDetails.getOrganization_id());
        taskDetails.setLiveInd(true);
        // taskDetails.setCompletion_ind(false);
        taskDetails.setStart_date(dbTaskDetails.getStart_date());
        taskDetails.setEnd_date(dbTaskDetails.getEnd_date());

    }
	
	
	
/*sb	@Override
	public TaskViewMapper getFilterTaskDetails(String taskId, boolean filterTaskInd) {

		TaskDetails taskDetails = taskDetailsRepository.getFilterTaskDetails(taskId,filterTaskInd);

		TaskViewMapper taskMapper = new TaskViewMapper();

		if (null != taskDetails) {

			if (taskDetails.getTask_type() != null && taskDetails.getTask_type().trim().length() > 0) {
				TaskType taskType = taskTypeRepository.findByTaskTypeId(taskDetails.getTask_type());
				if (null != taskType) {
					taskMapper.setTaskType(taskType.getTaskType());
					taskMapper.setTaskTypeId(taskType.getTaskTypeId());
				} else {
					taskMapper.setTaskType(taskDetails.getTask_type());
					if(taskDetails.getTask_type().equalsIgnoreCase("expense")) {
						VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsByTaskId(taskId);
						if(null!=voucherDetails) {
							List<VoucherExpenseLink> voucherExpenseLink = voucherExpenseRepository.getExpenseListByVoucherId(voucherDetails.getVoucher_id());
							if(null!=voucherExpenseLink && !voucherExpenseLink.isEmpty()) {
								List<String> documentIds = new ArrayList<>();
								for(VoucherExpenseLink link : voucherExpenseLink) {
								ExpenseDetails expenseDetails = expenseRepository.getExpenseDetailsById(link.getExpense_id());
								if(null!=expenseDetails) {
									documentIds.add(expenseDetails.getDocumentId());
								}
								}
								taskMapper.setDocumentIds(documentIds);
							}
						}
					}
				}
			}

			taskMapper.setTaskId(taskId);
			taskMapper.setEndDate(Utility.getISOFromDate(taskDetails.getEnd_date()));
			taskMapper.setStartDate(Utility.getISOFromDate(taskDetails.getStart_date()));
			taskMapper.setTaskDescription(taskDetails.getTask_description());
			taskMapper.setPriority(taskDetails.getPriority());
			taskMapper.setTaskStatus(taskDetails.getTask_status());
			taskMapper.setTaskName(taskDetails.getTask_name());
			taskMapper.setTimeZone(taskDetails.getTime_zone());
			taskMapper.setUserId(taskDetails.getUser_id());
			taskMapper.setOrganizationId(taskDetails.getOrganization_id());
			taskMapper.setAssignedOn(Utility.getISOFromDate(taskDetails.getCreation_date()));
			taskMapper.setCreationDate(Utility.getISOFromDate(taskDetails.getCreation_date()));
			taskMapper.setUpdateDate(Utility.getISOFromDate(taskDetails.getUpdateDate()));
			taskMapper.setCompletionInd(taskDetails.isComplitionInd());
			taskMapper.setRating(taskDetails.getRating());
			
			taskMapper.setProjectId(taskDetails.getProjectName());
			System.out.println("taskDetails.getProjectName()=================="+taskDetails.getProjectName());
			if(!StringUtils.isEmpty(taskDetails.getProjectName())) {
			ProjectDetails projectDetails = projectRepository.getById(taskDetails.getProjectName());
			if(null!=projectDetails) {
				taskMapper.setProjectName(projectDetails.getProjectName());
			}
			}
			taskMapper.setValue(taskDetails.getValue());
			taskMapper.setUnit(taskDetails.getUnit());
			taskMapper.setImageId(taskDetails.getImageId());
			taskMapper.setDocumentId(taskDetails.getDocumentId());
			taskMapper.setComplexity(taskDetails.getComplexity());
			taskMapper.setCustomerId(taskDetails.getCustomerId());
			taskMapper.setAssignedToName(employeeService.getEmployeeFullName(taskDetails.getAssigned_to()));
			taskMapper.setAssignedTo(taskDetails.getAssigned_to());
			
			
			taskMapper.setTaskChecklistId(taskDetails.getTaskChecklistId());
			TaskChecklist taskChecklist = taskChecklistRepository.findByTaskChecklistId(taskDetails.getTaskChecklistId());
			if(null!=taskChecklist) {
				taskMapper.setTaskChecklist(taskChecklist.getTaskChecklistName());
			}
			taskMapper.setTaskChecklistStageLinkMapper(categoryService.getAllTaskChecklistStageLinkByTaskChecklistId(taskDetails.getTaskChecklistId()));
			
			
			Customer customer = customerRepository
					.getCustomerDetailsByCustomerId(taskDetails.getCustomerId());
			if(null!=customer) {
				taskMapper.setCustomerName(customer.getName());
			}
			
			taskMapper.setAssignedDate(Utility.getISOFromDate(taskDetails.getAssignedDate()));
			
			//taskMapper.setRatePerUnit(taskDetails.getRatePerUnit());
		
		int complitionCount =0;
		List<CandidateTaskLink> candidateTaskLink = candidateTaskLinkRepository.getCandidateTaskLinkByTaskId(taskId);
		if (null != candidateTaskLink) {
			List<CandidateViewMapper> candiList = new ArrayList<>();
			for (CandidateTaskLink candidateTaskLink2 : candidateTaskLink) {
				
				if(!StringUtils.isEmpty(candidateTaskLink2.getComplitionStatus())) {
						if(candidateTaskLink2.getComplitionStatus().equalsIgnoreCase("completed")) {
						complitionCount++;
						}
				}
					
				CandidateViewMapper mapper = new CandidateViewMapper();
			CandidateDetails candidateDetails = candidateDetailsRepository
					.getcandidateDetailsById(candidateTaskLink2.getCandidateId());
			if (null != candidateDetails) {
			mapper.setCandidateId(candidateDetails.getCandidateId());
			String middleName = " ";
			String lastName = " ";
			if (null != candidateDetails.getMiddleName()) {

				middleName = candidateDetails.getMiddleName();
			}
			if (null != candidateDetails.getLastName()) {

				lastName = candidateDetails.getLastName();
			}

			mapper.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
		} else {

			mapper.setCandidateId("");
			mapper.setCandidateName("");
		}
			candiList.add(mapper);
		}
	taskMapper.setCandidates(candiList);
	
	System.out.println("complitionCount======"+complitionCount+"==========taskId++++++++++++++++++"+taskId);
	
	String completionStatus=null;
	 
	if(complitionCount==0) {
		completionStatus="To Start";
	}else if(complitionCount > 0 && complitionCount < candidateTaskLink.size()){
		completionStatus="In Progress";
	}else if(complitionCount==candidateTaskLink.size()) {
		completionStatus = "completed";
	}
	System.out.println("completionStatus======"+completionStatus+"============taskId++++++++++++++++++"+taskId+"=======size"+candidateTaskLink.size());
	taskMapper.setComplitionStatus(completionStatus);
	
		}
		// set event owner list
		List<EmployeeViewMapper> empList = new ArrayList<EmployeeViewMapper>();

		List<String> employeeIds = employeeTaskRepository.getEmpListByTaskId(taskId).stream()
				.map(EmployeeTaskLink::getEmployee_id).collect(Collectors.toList());

		
		if(null!=employeeIds && !employeeIds.isEmpty()) {
			employeeIds.remove(taskDetails.getUser_id());
			if(!StringUtils.isEmpty(taskMapper.getAssignedTo())) {
				employeeIds.remove(taskDetails.getAssigned_to());
			}
			for (String empId : employeeIds) {
			EmployeeViewMapper employeeMapper = employeeService.getEmployeeDetailsByEmployeeId(empId);
			empList.add(employeeMapper);
				}
			}			

		taskMapper.setOwner(empList);
		if(null!=taskDetails.getUser_id()) {
		System.out.println("user_id=========" + taskDetails.getUser_id());
		EmployeeViewMapper empMapper = employeeService.getEmployeeDetailsByEmployeeId(taskDetails.getUser_id());
		if (null != empMapper) {
			taskMapper.setSubmittedBy(empMapper.getEmailId());
			System.out.println("submittedBy........" + empMapper.getEmailId());

		}
		}
		if (null != taskDetails.getApproved_date()) {

			taskMapper.setApprovedDate(Utility.getISOFromDate(taskDetails.getApproved_date()));

		}
		taskMapper.setApprovedInd(taskDetails.getApproved_ind());
		}
		return taskMapper;
	}
	sb*/

    @Override
    public TaskMapper activeTaskCheckList(TaskMapper taskMapper) {
        TaskMapper resultMapper = null;
        if (null != taskMapper.getTaskTypeId()) {
            TaskType taskType = taskTypeRepository.findByTaskTypeId(taskMapper.getTaskTypeId());

            if (null != taskType.getTaskTypeId()) {
                taskType.setTaskCheckListInd(taskMapper.isTaskCheckListInd());
                taskTypeRepository.save(taskType);

                TaskTypeDelete taskTypeDelete = taskTypeDeleteRepository.findByTaskTypeId(taskMapper.getTaskTypeId());
                if (null != taskTypeDelete) {
                    taskTypeDelete.setUpdationDate(new Date());
                    taskTypeDelete.setUpdatedBy(taskMapper.getUserId());
                    taskTypeDeleteRepository.save(taskTypeDelete);
                } else {
                    TaskTypeDelete taskTypeDelete1 = new TaskTypeDelete();
                    taskTypeDelete1.setTaskTypeId(taskMapper.getTaskTypeId());
                    taskTypeDelete1.setUserId(taskMapper.getUserId());
                    taskTypeDelete1.setOrgId(taskMapper.getOrganizationId());
                    taskTypeDelete1.setUpdationDate(new Date());
                    taskTypeDelete1.setUpdatedBy(taskMapper.getUserId());
                    taskTypeDeleteRepository.save(taskTypeDelete1);
                }
            }
            resultMapper = getTaskTypeById(taskMapper.getTaskTypeId());

        }
        return resultMapper;
    }

    @Override
    public Map getOpenTaskListsByUserId(String userId) {
        HashMap map = new HashMap();
        List<TaskDetails> taskDetails = taskDetailsRepository.getTaskListsByUserIdAndStatusAndLiveInd(userId, "Completed");
        if (null != taskDetails && !taskDetails.isEmpty()) {
            map.put("totalTask", taskDetails.size());

            System.out.println("totalTask" + taskDetails.size());

        }

        return map;
    }

    @Override
    public List<TeamEmployeeMapper> getTaskTeamListByTaskId(String taskId) {
        List<TeamEmployeeMapper> empList = new ArrayList<>();

        TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskId);
        if (null != taskDetails) {

            List<String> employeeIds = employeeTaskRepository.getEmpListByTaskId(taskId).stream()
                    .map(EmployeeTaskLink::getEmployee_id).collect(Collectors.toList());

            if (null != employeeIds && !employeeIds.isEmpty()) {
                employeeIds.remove(taskDetails.getUser_id());

                for (String empId : employeeIds) {
                    TeamEmployeeMapper mapper = new TeamEmployeeMapper();
                    String name = employeeService.getEmployeeFullName(empId);
                    mapper.setEmployeeId(empId);
                    mapper.setName(name);
                    empList.add(mapper);
                }
            }
        }
        return empList;
    }

    @Override
    public List<TaskMapper> getAllTaskTypeTaskcheckList(String orgId) {
        List<TaskMapper> resultList = new ArrayList<TaskMapper>();
        List<TaskType> taskTypeList = taskTypeRepository.findByOrgIdAndLiveIndAndTaskCheckListInd(orgId, true, true);

        if (null != taskTypeList && !taskTypeList.isEmpty()) {
            taskTypeList.stream().map(taskType -> {
                TaskMapper taskMapper = new TaskMapper();
                taskMapper.setTaskTypeId(taskType.getTaskTypeId());
                taskMapper.setTaskType(taskType.getTaskType());
                taskMapper.setEditInd(taskType.isEditInd());
                taskMapper.setCreationDate(Utility.getISOFromDate(taskType.getCreationDate()));

                resultList.add(taskMapper);
                return resultList;
            }).collect(Collectors.toList());

        }
        return resultList;
    }

    @Override
    public SubViewTaskMapper createSubTask(SubTaskMapper subTaskMapper) throws Exception {
        TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(subTaskMapper.getTaskId());
        if (null != taskDetails) {

            SubTaskDetails subtask = new SubTaskDetails();
            subtask.setCreationDate(new Date());
            subtask.setLiveInd(true);
            subtask.setStartDate(Utility.getDateFromISOString(subTaskMapper.getStartDate()));
            subtask.setEndDate(Utility.getDateFromISOString(subTaskMapper.getEndDate()));
            subtask.setTaskChecklistStagelinkId(subTaskMapper.getTaskChecklistStagelinkId());
            subtask.setTaskId(subTaskMapper.getTaskId());
            subTaskDetailsRepository.save(subtask);

            List<String> employeeids = subTaskMapper.getIncluded();
            for (String empId : employeeids) {
                EmployeeSubTaskLink employeeSubTaskLink = new EmployeeSubTaskLink();
                employeeSubTaskLink.setEmployeeId(empId);
                employeeSubTaskLink.setTaskChecklistStagelinkId(subTaskMapper.getTaskChecklistStagelinkId());
                employeeSubTaskLink.setTaskId(subTaskMapper.getTaskId());
                employeeSubTaskLinkRepository.save(employeeSubTaskLink);
            }
        }
        SubViewTaskMapper subTaskMapper1 =
                getPreviewSubTaskByTaskId(subTaskMapper.getTaskId(), subTaskMapper.getTaskChecklistStagelinkId());
        return subTaskMapper1;
    }

    @Override
    public List<SubViewTaskMapper> getSubTaskByTaskId(String taskId) {
        List<SubViewTaskMapper> resultList = new ArrayList<>();
        TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskId);
        if (null != taskDetails) {

//			List<String> employeeIds = employeeTaskRepository.getEmpListByTaskId(taskId).stream()
//					.map(EmployeeTaskLink::getEmployee_id).collect(Collectors.toList());
//		
//			if(null!=employeeIds && !employeeIds.isEmpty()) {
//				employeeIds.remove(taskDetails.getUser_id());
//			}
//			List<EmployeeViewMapper> empList = new ArrayList<EmployeeViewMapper>();
//			for (String empId : employeeIds) {
//				EmployeeViewMapper employeeMapper = employeeService.getEmployeeDetailsByEmployeeId(empId);
//				if(null != employeeMapper) {
//				empList.add(employeeMapper);
//				}
//					}

            List<TaskChecklistStageLink> taskChecklistStageLinks = taskChecklistStageLinkRepository.
                    findByTaskChecklistIdAndLiveInd(taskDetails.getTaskChecklistId(), true);

            if (null != taskChecklistStageLinks && !taskChecklistStageLinks.isEmpty()) {
                taskChecklistStageLinks.stream().map(taskChecklistStageLink -> {
                    SubViewTaskMapper mapper = new SubViewTaskMapper();

                    SubTaskDetails subtask = subTaskDetailsRepository.
                            findByTaskIdAndTaskChecklistStagelinkId(taskId, taskChecklistStageLink.getTaskChecklistStagelinkId());
                    if (null != subtask) {
                        mapper.setStartDate(Utility.getISOFromDate(subtask.getStartDate()));
                        mapper.setEndDate(Utility.getISOFromDate(subtask.getStartDate()));
                    }
                    mapper.setCreationDate(Utility.getISOFromDate(taskChecklistStageLink.getCreationDate()));
                    mapper.setTaskChecklistStagelinkId(taskChecklistStageLink.getTaskChecklistStagelinkId());
                    mapper.setTaskChecklistStageName(taskChecklistStageLink.getTaskChecklistStageName());
                    mapper.setProbability(taskChecklistStageLink.getProbability());
                    mapper.setDays(taskChecklistStageLink.getDays());
                    mapper.setTaskChecklistId(taskChecklistStageLink.getTaskChecklistId());

                    List<EmployeeViewMapper> empList = new ArrayList<EmployeeViewMapper>();
                    List<String> employeeIds = employeeSubTaskLinkRepository.
                            findByTaskIdAndTaskChecklistStagelinkId(taskId, taskChecklistStageLink.getTaskChecklistStagelinkId())
                            .stream()
                            .map(EmployeeSubTaskLink::getEmployeeId).collect(Collectors.toList());
                    for (String empId : employeeIds) {
                        EmployeeViewMapper employeeMapper = employeeService.getEmployeeDetailsByEmployeeId(empId);
                        if (null != employeeMapper) {
                            empList.add(employeeMapper);
                        }
                    }
                    mapper.setIncluded(empList);
                    resultList.add(mapper);

                    return resultList;
                }).collect(Collectors.toList());
            }
        }
        return resultList;
    }


    public SubViewTaskMapper getPreviewSubTaskByTaskId(String taskId, String TaskChecklistStagelinkId) {
        SubViewTaskMapper mapper = new SubViewTaskMapper();
        TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskId);
        if (null != taskDetails) {

            TaskChecklistStageLink taskChecklistStageLinks = taskChecklistStageLinkRepository.
                    findByTaskChecklistIdAndTaskChecklistStagelinkIdAndLiveInd(taskDetails.getTaskChecklistId(), TaskChecklistStagelinkId, true);

            if (null != taskChecklistStageLinks) {

                SubTaskDetails subtask = subTaskDetailsRepository.
                        findByTaskIdAndTaskChecklistStagelinkId(taskId, taskChecklistStageLinks.getTaskChecklistStagelinkId());
                if (null != subtask) {
                    mapper.setStartDate(Utility.getISOFromDate(subtask.getStartDate()));
                    mapper.setEndDate(Utility.getISOFromDate(subtask.getStartDate()));
                }
                mapper.setCreationDate(Utility.getISOFromDate(taskChecklistStageLinks.getCreationDate()));
                mapper.setTaskChecklistStagelinkId(taskChecklistStageLinks.getTaskChecklistStagelinkId());
                mapper.setTaskChecklistStageName(taskChecklistStageLinks.getTaskChecklistStageName());
                mapper.setProbability(taskChecklistStageLinks.getProbability());
                mapper.setDays(taskChecklistStageLinks.getDays());
                mapper.setTaskChecklistId(taskChecklistStageLinks.getTaskChecklistId());

                List<EmployeeViewMapper> empList = new ArrayList<EmployeeViewMapper>();
                List<String> employeeIds = employeeSubTaskLinkRepository.
                        findByTaskIdAndTaskChecklistStagelinkId(taskId, taskChecklistStageLinks.getTaskChecklistStagelinkId())
                        .stream()
                        .map(EmployeeSubTaskLink::getEmployeeId).collect(Collectors.toList());
                for (String empId : employeeIds) {
                    EmployeeViewMapper employeeMapper = employeeService.getEmployeeDetailsByEmployeeId(empId);
                    if (null != employeeMapper) {
                        empList.add(employeeMapper);
                    }
                }
                mapper.setIncluded(empList);


            }

        }
        return mapper;
    }

   @Override
    public List<Map<String, Integer>> getOpenTaskCountByUserId(String userId, String organizationId) {
        List<Map<String, Integer>> map = new ArrayList<>();

//		List<String> taskIds = employeeTaskRepository.getEmpListByTaskId(userId).stream()
//				.map(EmployeeTaskLink::getTask_id).collect(Collectors.toList());
        List<EmployeeTaskLink> list = employeeTaskRepository.
                getByEmployeeId(userId);
        List<TaskType> taskTypes = taskTypeRepository.findByUserIdAndLiveInd(userId, true);
       if(null!=taskTypes && !taskTypes.isEmpty()) {
        for (TaskType taskType : taskTypes) {
        	 int count = 0;
            System.out.println("taskType===" + taskType.getTaskType());
            System.out.println("taskTypeIdd===" + taskType.getTaskTypeId());
            for (EmployeeTaskLink employeeTaskLink : list) {
//			List<TaskDetails> task = taskDetailsRepository.
//					getDataByTaskIdAndTaskTypeAndComplitionInd(taskIds,taskType.getTaskTypeId(),false);
                TaskDetails task = taskDetailsRepository.
                        getDataByTaskIdAndTaskTypeAndComplitionInd(employeeTaskLink.getTask_id(), taskType.getTaskTypeId(), false);
//			System.out.println("Size==="+task.size());
//			System.out.println("taskType==="+taskType.getTaskType());
//			System.out.println("taskTypeIdd==="+taskType.getTaskTypeId());
                if (null!=task) {
                    count++;
                    System.out.println("inside if count"+count);
                }
//			getTaskByTasktypAndTaskId
//			true
//			count++
            }
//			int size = task.size();
//			if(count>0) {
            Map map1 = new HashMap<>();
            map1.put("name", taskType.getTaskType());
            System.out.println("name"+ taskType.getTaskType());
            map1.put("count", count);
            System.out.println("count"+count);
            map.add(map1);
		}
        }
        return map;
    }

    @Override
    public List<ApprovedStatusMapper> getApproveStatusByTaskId(String taskId, String userId) {

        List<ApprovedStatusMapper> resultList = new ArrayList<>();
        
            List<EmployeeTaskLink> taskLinkList = employeeTaskRepository.getEmpListByTaskId(taskId).
                    stream().sorted(Comparator.comparing(EmployeeTaskLink::getLevel))
                    .collect(Collectors.toList());
            System.out.println("task id=="+taskId);
//            System.out.println("taskLinkList=="+taskLinkList.toString());
            ApprovalLevel approvalLevel = approvalLevelRepository.
            		getApprovalLevelDetail(taskLinkList.get(0).getSubProcessApprovalId());
//            System.out.println("approvalLevel Id in taskLink=="+taskLinkList.get(0).getSubProcessApprovalId());
//            System.out.println("task d Id=="+taskLinkList.get(0).getId());
//            System.out.println("approvalLevel Id=="+approvalLevel.getId());
//            System.out.println("approvalLevel=="+approvalLevel.getApprovalType());           
            if (null != taskLinkList && !taskLinkList.isEmpty() && null != approvalLevel) {
            	if(approvalLevel.getApprovalType().equalsIgnoreCase("standard")) {

            		EmployeeDetails EmployeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userId);
    				EmployeeDetails reportingManager = employeeRepository.
    						getEmployeeDetailsByEmployeeId(EmployeeDetails.getReportingManager());
    				EmployeeDetails reportingManager_1 = employeeRepository.
    						getEmployeeDetailsByEmployeeId(reportingManager.getReportingManager());
          
    			for(int i=1;i<=approvalLevel.getLevelCount();i++) {
            	 final int currentLevel = i;
            	 
            	 ApprovedStatusMapper mapper = new ApprovedStatusMapper();
            	       	                
            	 switch (currentLevel) {
// 				String content ="";

					case 1:
						String content ="";
						Optional<EmployeeTaskLink> matchingTaskLink = taskLinkList.stream()
						        .filter(taskLink -> taskLink.getLevel() ==currentLevel )
						        .findFirst();
						if(approvalLevel.getReportingTo().equalsIgnoreCase("ReportingManager")) {
							 content = "Reporting Manager " +
									employeeService.
									getEmployeeFullNameByObject(reportingManager);
							}else if(approvalLevel.getReportingTo().equalsIgnoreCase("ReportingManager+1")) {
								 content = "Reporting Manager+1 " +
										employeeService.
			                            getEmployeeFullNameByObject(reportingManager_1);
							}else {
								 content = approvalLevel.getReportingTo3() +" "+approvalLevel.getRoleType();
								if(!StringUtils.isEmpty(matchingTaskLink.get().getApprovedBy())) {
									content =" " + employeeService.
				                            getEmployeeFullNameAndEmployeeId(matchingTaskLink.get().getApprovedBy()).getEmpName();
				                    }
							}
						mapper.setEmployeeName(content);
		                
						if (matchingTaskLink.isPresent()) {
							mapper.setApprovedDate(Utility.getISOFromDate(matchingTaskLink.get().getApprovedDate()));
			                mapper.setApprovedStatus(matchingTaskLink.get().getApproveStatus()); 
			                mapper.setCreatedOn(Utility.getISOFromDate(matchingTaskLink.get().getCreation_date())); 
						} else {
							mapper.setApprovedDate("");
			                mapper.setApprovedStatus("Pending"); 
			                mapper.setCreatedOn(""); 
						}
		                resultList.add(mapper);
		                
		            break;
					case 2:
						 content ="";
						 matchingTaskLink = taskLinkList.stream()
						        .filter(taskLink -> taskLink.getLevel() == currentLevel)
						        .findFirst();
						if(approvalLevel.getReportingTo2().equalsIgnoreCase("ReportingManager")) {
							 content = "Reporting Manager " +
									employeeService.
									getEmployeeFullNameByObject(reportingManager);
							}else if(approvalLevel.getReportingTo2().equalsIgnoreCase("ReportingManager+1")) {
								 content = "Reporting Manager+1 " +
										employeeService.
			                            getEmployeeFullNameByObject(reportingManager_1);
							}else {
								 content = approvalLevel.getReportingTo2() +" "+approvalLevel.getRoleType();
								if(!StringUtils.isEmpty(matchingTaskLink.get().getApprovedBy())) {
									content =" " + employeeService.
				                            getEmployeeFullNameAndEmployeeId(matchingTaskLink.get().getApprovedBy()).getEmpName();
				                    }
							}
						mapper.setEmployeeName(content);
		                						
						if (matchingTaskLink.isPresent()) {
							mapper.setApprovedDate(Utility.getISOFromDate(matchingTaskLink.get().getApprovedDate()));
			                mapper.setApprovedStatus(matchingTaskLink.get().getApproveStatus()); 
			                mapper.setCreatedOn(Utility.getISOFromDate(matchingTaskLink.get().getCreation_date())); 
						} else {
							mapper.setApprovedDate("");
			                mapper.setApprovedStatus("Pending"); 
			                mapper.setCreatedOn(""); 
						}
	                
		                resultList.add(mapper);
		                
		            break;
					case 3:
						 content ="";
						 matchingTaskLink = taskLinkList.stream()
						        .filter(taskLink -> taskLink.getLevel() == currentLevel)
						        .findFirst();
						if(approvalLevel.getReportingTo3().equalsIgnoreCase("ReportingManager")) {
							 content = "Reporting Manager " +
									employeeService.
									getEmployeeFullNameByObject(reportingManager);
							}else if(approvalLevel.getReportingTo3().equalsIgnoreCase("ReportingManager+1")) {
								 content = "Reporting Manager+1 " +
										employeeService.
			                            getEmployeeFullNameByObject(reportingManager_1);
							}else {
								 content = approvalLevel.getReportingTo3() +" "+approvalLevel.getRoleType();
								if(!StringUtils.isEmpty(matchingTaskLink.get().getApprovedBy())) {
									content =" " + employeeService.
				                            getEmployeeFullNameAndEmployeeId(matchingTaskLink.get().getApprovedBy()).getEmpName();
				                    }
							}
						mapper.setEmployeeName(content);
		                
						if (matchingTaskLink.isPresent()) {
							mapper.setApprovedDate(Utility.getISOFromDate(matchingTaskLink.get().getApprovedDate()));
			                mapper.setApprovedStatus(matchingTaskLink.get().getApproveStatus()); 
			                mapper.setCreatedOn(Utility.getISOFromDate(matchingTaskLink.get().getCreation_date())); 
						} else {
							mapper.setApprovedDate("");
			                mapper.setApprovedStatus("Pending"); 
			                mapper.setCreatedOn(""); 
						}
		                resultList.add(mapper);
		                
		            break;
            		}
            	 
            }	
            			
            	}else {
            		ApprovedStatusMapper mapper = new ApprovedStatusMapper();

                    mapper.setApprovedDate(Utility.getISOFromDate(taskLinkList.get(0).getApprovedDate()));
                  String content = "";
                if ((approvalLevel.getDesignationId() != null || !approvalLevel.getDesignationId().isEmpty()) && 
                		(approvalLevel.getDepartmentId() != null || !approvalLevel.getDepartmentId().isEmpty())) {
                	RoleType roleType = roleTypeRepository
    						.findByRoleTypeId(approvalLevel.getDesignationId());
                	String role="";
                	if (null != roleType) {
                		role = roleType.getRoleType();
                	}
                	Department department = departmentRepository.getDepartmentDetails(approvalLevel.getDepartmentId());
                	String dept="";
    				if (null != department) {
    					dept = department.getDepartmentName();
    				}
                  content = dept +" "+role;
                  }
					if(!StringUtils.isEmpty(taskLinkList.get(0).getApprovedBy())) {
						content = employeeService.
	                            getEmployeeFullNameAndEmployeeId(taskLinkList.get(0).getApprovedBy()).getEmpName();
	    
	                    }
					
					mapper.setEmployeeName(content);
					mapper.setApprovedStatus(taskLinkList.get(0).getApproveStatus());
                    mapper.setCreatedOn(Utility.getISOFromDate(taskLinkList.get(0).getCreation_date()));
                    resultList.add(mapper);
            	}
            }

        return resultList;
    }

    @Override
    public List<ApprovedStatusMapper> getLeaveStatusByLeaveId(String leaveId) {
        LeaveDetails leaveDetails = leaveDetailsRepository.getLeaveDetailsByLeaveId(leaveId);
        if (null != leaveDetails) {
            List<ApprovedStatusMapper> resultList
                    = getApproveStatusByTaskId(leaveDetails.getTask_id(),leaveDetails.getEmployee_id());
            return resultList;
        }

        return null;
    }

    @Override
    public List<ApprovedStatusMapper> getMileageStatusByVoucherId(String voucherId) {
        VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsById(voucherId);
        if (null != voucherDetails) {
            List<ApprovedStatusMapper> resultList
                    = getApproveStatusByTaskId(voucherDetails.getTask_id(),voucherDetails.getUser_id());
            return resultList;
        }
        return null;
    }

    @Override
    public List<ApprovedStatusMapper> getExpenseStatusByVoucherId(String voucherId) {
        VoucherDetails voucherDetails = voucherRepository.getVoucherDetailsById(voucherId);
        if (null != voucherDetails) {
            List<ApprovedStatusMapper> resultList
                    = getApproveStatusByTaskId(voucherDetails.getTask_id(),voucherDetails.getUser_id());
            return resultList;
        }
        return null;
    }

    @Override
    public List<Map<String, List<TaskViewMapper>>> getTaskListByUserId(String userId) {
        List<Map<String, List<TaskViewMapper>>> mapList = new ArrayList<>();
        List<TaskType> taskTypes = taskTypeRepository.findByUserIdAndLiveInd(userId, true);

        for (TaskType taskType : taskTypes) {
            //Map<String, List<TaskViewMapper>> map = new HashMap<>();
        	Map map=new HashMap<>();
            List<TaskViewMapper> resultList = new ArrayList<TaskViewMapper>();

            List<EmployeeTaskLink> list = employeeTaskRepository.getByEmployeeId(userId);
            for (EmployeeTaskLink employeeTaskLink : list) {

                TaskDetails task = taskDetailsRepository.
                        getDataByTaskIdAndTaskTypeAndComplitionInd(employeeTaskLink.getTask_id(), taskType.getTaskTypeId(), false);

                if (null != task) {
                    TaskViewMapper taskViewMapper = getTaskDetails(employeeTaskLink.getTask_id());
                    resultList.add(taskViewMapper);
                }

            }
            map.put("type", taskType.getTaskType());
            map.put("list", resultList);
            mapList.add(map);

        }
        return mapList;
    }

    @Override
    public List<TaskViewMapper> getTaskDetailsByLeads(String leadsId, int pageNo) {
        Pageable page = PageRequest.of(pageNo, 20, Sort.by("creationDate").descending());
        Page<LeadsTaskLink> list = leadsTaskRepo.getTaskListByLeadsId(leadsId,page);
        List<TaskViewMapper> mappList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {

         list.stream().map(li ->{ 
            	
            	TaskViewMapper mapper =	getTaskDetails(li.getTaskId());
            	 if(null!=mapper.getTaskId()) {
            		mapper.setPageCount(list.getTotalPages());
            		 mapper.setDataCount(list.getSize());
                     mapper.setListCount (list.getTotalElements());
            		 mappList.add(mapper);
            	 }
            	return mapper;
            }).collect(Collectors.toList());
        }

        return mappList;
    }

	@Override
	public Map getHighPriorityByUserIdStartdateAndEndDate(String userId, String startDate, String endDate) {
		HashMap map = new HashMap();
		 Date end_date = null;
	        Date start_date = null;
	        try {
	            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
	            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        
	        List<TaskDetails> tasklist = taskDetailsRepository.
	        		getTaskListsByPriorityAndStartdateAndEndDateAndLiveInd("High",start_date,end_date);
	        int count = 0;
	            for (TaskDetails task : tasklist) {
	            	EmployeeTaskLink taskLink = employeeTaskRepository.
	            			getMyTaskByUserIdAndTaskId(userId,task.getTask_id());
                if (null != taskLink) {
	                    count++;
	                    System.out.println("task=="+task.getTask_id());
	                    System.out.println("count=="+count);
	                }
			
	        }
	            map.put("no", count);
	        return map;
	}

	@Override
	public List<DocumentMapper> getDocumentListByTaskId(String taskId) {
		List<DocumentMapper> result = new ArrayList<>();
			List<TaskDocumentLink> docList = taskDocumentLinkRepository.findByTaskId(taskId);
	        Set<String> documentIds = docList.stream().map(TaskDocumentLink::getDocumentId).collect(Collectors.toSet());

		        if (documentIds != null && !documentIds.isEmpty()) {
		        	result = documentIds.stream().map(documentId -> {
		                return documentService.getDocument(documentId);
		            }).collect(Collectors.toList());

		        }
		return result;
	}

	@Override
	public void deleteDocumentsById(String documentId) {
		TaskDocumentLink docList = taskDocumentLinkRepository.findByDocumentId(documentId);
		if (null != docList) {
			taskDocumentLinkRepository.delete(docList);
			DocumentDetails document = documentDetailsRepository.getDocumentDetailsById(documentId);
			if (null!=document) {
			documentDetailsRepository.delete(document);
			}
		}		
	}

	@Override
	public Map<String, Integer> getOpenTaskCountByUserIdBetwennStartDate(String userId, String startDate, String endDate) {
		Map<String, Integer> map = new HashMap<>();
		 Date end_date = null;
	        Date start_date = null;
	        try {
	            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
	            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
       
	        List<TaskDetails> tasklist = taskDetailsRepository.
	        		getTaskListsByComplitionIndAndStartdateAndEndDateAndLiveInd(false,start_date,end_date);
    
	        long count = tasklist.stream()
	                .map(TaskDetails::getTask_id)
	                .map(taskId -> employeeTaskRepository.getMyTaskByUserIdAndTaskId(userId, taskId))
	                .filter(Objects::nonNull)
	                .count();

	        map.put("no", (int) count);
	        return map;
	}

	@Override
	public Map<String, Integer> getDeadlineTaskByUserIdBetweenDate(String userId, String startDate, String endDate) {
		Map<String, Integer> map = new HashMap<>();
		 Date end_date = null;
	        Date start_date = null;
	        try {
	            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
	            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }        
	        List<TaskDetails> tasklist = taskDetailsRepository.getDeadlineTaskByUserIdBetweenDate(false, start_date, end_date);

	        long count = tasklist.stream()
	                .map(TaskDetails::getTask_id)
	                .map(taskId -> employeeTaskRepository.getMyTaskByUserIdAndTaskId(userId, taskId))
	                .filter(Objects::nonNull)
	                .count();

	        map.put("no", (int) count);
	        return map;
	}

	@Override
	public List<TaskViewMapper> getOpenTasktByUserIdAndDateRange(String userId, String startDate, String endDate) {
		List<TaskViewMapper> resultMapper = new ArrayList<>();
		
		Date end_date = null;
        Date start_date = null;
        try {
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
   
        List<TaskDetails> tasklist = taskDetailsRepository.
        		getTaskListsByComplitionIndAndStartdateAndEndDateAndLiveInd(false,start_date,end_date);
            
        for (TaskDetails task : tasklist) {
            	EmployeeTaskLink taskLink = employeeTaskRepository.
            			getMyTaskByUserIdAndTaskId(userId,task.getTask_id());
           if (null != taskLink) {
        	   resultMapper.add(getTaskViewMapper(task));
           }
            }
		return resultMapper;
	}

	@Override
	public List<TaskViewMapper> getMyTasktByUserIdAndDateRange(String userId, String startDate, String endDate) {
		List<TaskViewMapper> resultMapper = new ArrayList<>();
		
		Date end_date = null;
        Date start_date = null;
        try {
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
   
        List<TaskDetails> tasklist = taskDetailsRepository.
        		getTaskListsByStartdateAndEndDateAndLiveInd(start_date,end_date);
            
        for (TaskDetails task : tasklist) {
            	EmployeeTaskLink taskLink = employeeTaskRepository.
            			getMyTaskByUserIdAndTaskId(userId,task.getTask_id());
           if (null != taskLink) {
        	   resultMapper.add(getTaskViewMapper(task));
           }
            }
		return resultMapper;
	}
	
	
	public TaskViewMapper getTaskViewMapper(TaskDetails taskDetails) {
		TaskViewMapper taskMapper = new TaskViewMapper();

        if (taskDetails.getTask_type() != null && taskDetails.getTask_type().trim().length() > 0) {
            TaskType taskType = taskTypeRepository.findByTaskTypeId(taskDetails.getTask_type());
            if (null != taskType) {
                taskMapper.setTaskType(taskType.getTaskType());
                taskMapper.setTaskTypeId(taskType.getTaskTypeId());
            }
        }
            
            taskMapper.setTaskId(taskDetails.getTask_id());
            taskMapper.setEndDate(Utility.getISOFromDate(taskDetails.getEnd_date()));
            taskMapper.setStartDate(Utility.getISOFromDate(taskDetails.getStart_date()));
            taskMapper.setTaskDescription(taskDetails.getTask_description());
            taskMapper.setPriority(taskDetails.getPriority());
            taskMapper.setTaskStatus(taskDetails.getTask_status());
            taskMapper.setTaskName(taskDetails.getTask_name());
            taskMapper.setTimeZone(taskDetails.getTime_zone());
            taskMapper.setUserId(taskDetails.getUser_id());
            taskMapper.setOrganizationId(taskDetails.getOrganization_id());
            taskMapper.setAssignedOn(Utility.getISOFromDate(taskDetails.getCreation_date()));
            taskMapper.setCreationDate(Utility.getISOFromDate(taskDetails.getCreation_date()));
            taskMapper.setUpdateDate(Utility.getISOFromDate(taskDetails.getUpdateDate()));
            taskMapper.setCompletionInd(taskDetails.isComplitionInd());
            taskMapper.setRating(taskDetails.getRating());
            taskMapper.setAssignedDate(Utility.getISOFromDate(taskDetails.getAssignedDate()));
	return taskMapper;
	}

	@Override
	public List<TaskViewMapper>  getOpenTaskListByUserId(String userId) {
	    return employeeTaskRepository.getMyTaskListByEmpId(userId).stream()
	            .map(EmployeeTaskLink::getTask_id)
	            .map(taskDetailsRepository::getUnCompletedTaskDetailsByTaskId)
	            .filter(Objects::nonNull)
	            .map(this::getTaskViewMapper)
	            .collect(Collectors.toList());
	}

	@Override
	public List<TaskViewMapper> getTaskDetailsByInvestorLeadsId(String investorLeadsId, int pageNo) {
        Pageable page = PageRequest.of(pageNo, 20, Sort.by("creationDate").descending());
        Page<InvestorLeadsTaskLink> list = investorLeadsTaskRepo.getTaskListByInvestorLeadsId(investorLeadsId,page);
        List<TaskViewMapper> mappList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {

        	list.stream().map(li ->{ 
            	
            	TaskViewMapper mapper =	getTaskDetails(li.getTaskId());
            	 if(null!=mapper.getTaskId()) {
            		 mapper.setPageCount(list.getTotalPages());
            		 mapper.setDataCount(list.getSize());
                     mapper.setListCount(list.getTotalElements());
            		 mappList.add(mapper);
            	 }
            	return mapper;
            }).collect(Collectors.toList());
        }

        return mappList;
    }

	@Override
	public List<Map<String, Integer>> getCountTaskListsByTaskTypeAndUserIdAndStartdateAndEndDate(String userId,
			String startDate, String endDate) {
	Date end_date = null;
	Date start_date = null;
	try {
		end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
		start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
	} catch (Exception e) {
		e.printStackTrace();
	}
	List<Map<String, Integer>> map = new ArrayList<>();
    List<EmployeeTaskLink> list = employeeTaskRepository.getByEmployeeId(userId);
    if(null!=list && !list.isEmpty()) {
    List<TaskType> taskTypes = taskTypeRepository.findByUserIdAndLiveInd(userId, true);
    
   if(null!=taskTypes && !taskTypes.isEmpty()) {
    for (TaskType taskType : taskTypes) {
    	 int count = 0;
//        System.out.println("taskType===" + taskType.getTaskType());
//        System.out.println("taskTypeIdd===" + taskType.getTaskTypeId());
        for (EmployeeTaskLink employeeTaskLink : list) {
            TaskDetails task = taskDetailsRepository.
            		getTaskListByTaskTypeIdAndStartdateAndEndDateAndComplitionInd(taskType.getTaskTypeId(),employeeTaskLink.getTask_id(), start_date, end_date);
//            System.out.println("inside 2for tasksize"+task.toString());
            if (null!=task) {
                count++;
                System.out.println("inside if count"+count);
            }
        }

        Map map1 = new HashMap<>();
        map1.put("name", taskType.getTaskType());
        System.out.println("name"+ taskType.getTaskType());
        map1.put("count", count);
        System.out.println("count"+count);
        map.add(map1);
	}
    }
	}
    return map;
	}

	@Override
	public List<TaskViewMapper> getCompletedTaskListsByTaskTypeAndUserIdAndStartdateAndEndDate(
			String userId, String startDate, String endDate,String typeName) {
		Date end_date = null;
	    Date start_date = null;
	    try {
	        end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
	        start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }	
	        

	    List<TaskViewMapper> resultList = new ArrayList<>();

	    TaskType taskType = taskTypeRepository.findByTaskType(typeName);
	    if(null!= taskType) {

	        List<EmployeeTaskLink> list = employeeTaskRepository.getByEmployeeId(userId);
	        for (EmployeeTaskLink employeeTaskLink : list) {

	            TaskDetails task = taskDetailsRepository.
	            		getTaskListByTaskTypeIdAndStartdateAndEndDateAndComplitionInd(taskType.getTaskTypeId(),employeeTaskLink.getTask_id(), start_date, end_date);

	            if (null != task) {
	                TaskViewMapper taskViewMapper = getTaskDetails(task.getTask_id());
	                resultList.add(taskViewMapper);
	            }

	        }

	    }
	    return resultList;      
    }

	@Override
	public String saveTaskNotes(NotesMapper notesMapper) {

		String taskNotesId = null;
		if (null != notesMapper) {
			Notes notes = new Notes();
			notes.setNotes(notesMapper.getNotes());
			notes.setCreation_date(new Date());
			notes.setUserId(notesMapper.getEmployeeId());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);
			taskNotesId = note.getNotes_id();

			/* insert to leads-notes-link */

			TaskNotesLink taskNotesLink = new TaskNotesLink();
			taskNotesLink.setTaskId(notesMapper.getTaskId());
			taskNotesLink.setNotesId(taskNotesId);
			taskNotesLink.setCreationDate(new Date());
			taskNotesLinkRepository.save(taskNotesLink);
			
			/* insert to leads-notes-link */
			TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(notesMapper.getTaskId());
			if(!StringUtils.isEmpty(taskDetails.getOppertunity())) {
				
			OpportunityNotesLink opportunityNotesLink = new OpportunityNotesLink();
			opportunityNotesLink.setOpportunity_id(taskDetails.getOppertunity());
			opportunityNotesLink.setNotesId(taskNotesId);
			opportunityNotesLink.setCreation_date(new Date());
			opportunityNotesLinkRepository.save(opportunityNotesLink);
			}
			
			/*insert to customer-notes-link*/
			if(!StringUtils.isEmpty(taskDetails.getContact())) {
				
	            ContactNotesLink contactNotesLink = new ContactNotesLink();
	            contactNotesLink.setContact_id(taskDetails.getContact());
	            contactNotesLink.setNotesId(taskNotesId);
	            contactNotesLink.setCreation_date(new Date());
	            contactNotesLinkRepository.save(contactNotesLink);
			}
			
			/* insert to leaves-notes-link */
			if(taskDetails.getTask_type().equalsIgnoreCase("leave")) {
			LeaveNotesLink leaveNotesLink = new LeaveNotesLink();
			leaveNotesLink.setLeaveId(notesMapper.getLeaveId());
			leaveNotesLink.setNoteId(taskNotesId);
			leaveNotesLink.setCreationDate(new Date());
			leaveNotesLinkRepository.save(leaveNotesLink);
			}
			
			/* insert to milage-notes-link */
			if(taskDetails.getTask_type().equalsIgnoreCase("mileage")) {
				MileageNotesLink mileageNotesLink = new MileageNotesLink();
				mileageNotesLink.setMileageId(notesMapper.getMileageId());
				mileageNotesLink.setNoteId(taskNotesId);
				mileageNotesLink.setCreationDate(new Date());
				mileageNotesLinkRepository.save(mileageNotesLink);
			}
			
			/* insert to leaves-notes-link */
			if(taskDetails.getTask_type().equalsIgnoreCase("expense")) {
				ExpenseNotesLink leaveNotesLink = new ExpenseNotesLink();
				leaveNotesLink.setExpenseId(notesMapper.getExpenseId());
				leaveNotesLink.setNoteId(taskNotesId);
				leaveNotesLink.setCreationDate(new Date());
				expenseNotesLinkRepository.save(leaveNotesLink);
			}

		}
		return taskNotesId;

	}

	@Override
	public List<NotesMapper> getNoteListByTaskId(String taskId) {
		List<TaskNotesLink> leadsNotesLinkList = taskNotesLinkRepository.getNotesIdByTaskId(taskId);
		if (leadsNotesLinkList != null && !leadsNotesLinkList.isEmpty()) {
			return leadsNotesLinkList.stream().map(leadsNotesLink->{
				NotesMapper notesMapper = getNotes(leadsNotesLink.getNotesId());
				return notesMapper;
			}).collect(Collectors.toList());
		}
		return null;
	}
	private NotesMapper getNotes(String id) {
		Notes notes = notesRepository.findByNoteId(id);
		NotesMapper notesMapper = new NotesMapper();
		if(null!=notes) {
		notesMapper.setNotesId(notes.getNotes_id());
		notesMapper.setNotes(notes.getNotes());
		notesMapper.setCreationDate(Utility.getISOFromDate(notes.getCreation_date()));
		notesMapper.setLiveInd(notes.isLiveInd());
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
//	public NotesMapper updateNoteDetails(NotesMapper notesMapper) {
//
//		NotesMapper resultMapper = employeeService.updateNotes(notesMapper);
//
//		return resultMapper;
//	}

	@Override
	public List<TaskViewMapper> getTaskListByTaskTypeIdAndUserId(String userId, String Typeame) {
        List<TaskViewMapper> resultList = new ArrayList<>();
  
        TaskType taskType = taskTypeRepository.findByTaskType(Typeame);
        if(null!= taskType) {

            List<EmployeeTaskLink> list = employeeTaskRepository.getByEmployeeId(userId);
            for (EmployeeTaskLink employeeTaskLink : list) {

                TaskDetails task = taskDetailsRepository.
                        getDataByTaskIdAndTaskTypeAndComplitionInd(employeeTaskLink.getTask_id(), taskType.getTaskTypeId(), false);

                if (null != task) {
                    TaskViewMapper taskViewMapper = getTaskDetails(task.getTask_id());
                    resultList.add(taskViewMapper);
                }

            }

        }
        return resultList;
    }

	@Override
	public void deleteTaskNotesById(String notesId) {
		TaskNotesLink notesList = taskNotesLinkRepository.findByNotesId(notesId);
		if (null != notesList) {
			
			Notes notes = notesRepository.findByNoteId(notesId);
			if (null!=notes) {
				notes.setLiveInd(false);
				notesRepository.save(notes);
			}
		}		
	}

	@Override
	public ActivityMapper saveActivityTask(TaskMapper taskMapper) {

		 ActivityMapper mapper=new ActivityMapper();
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setCreation_date(new Date());
        TaskInfo info = taskInfoRepository.save(taskInfo);

        String taskId = info.getTask_id();
       
        if (null != taskId) {

            /* insert to task details table */

            TaskDetails taskDetails = new TaskDetails();
            taskDetails.setTask_id(taskId);
            taskDetails.setCreation_date(new Date());
            taskDetails.setTask_description(taskMapper.getTaskDescription());
            taskDetails.setPriority(taskMapper.getPriority());
            taskDetails.setTask_type(taskMapper.getTaskTypeId());
            taskDetails.setTask_name(taskMapper.getTaskName());
            taskDetails.setTask_status(taskMapper.getTaskStatus());
            taskDetails.setUser_id(taskMapper.getUserId());
            taskDetails.setOrganization_id(taskMapper.getOrganizationId());
            taskDetails.setTime_zone(taskMapper.getTimeZone());
            taskDetails.setLiveInd(true);
            taskDetails.setComplitionInd(false);
            taskDetails.setRating(0);
            taskDetails.setAssigned_to(taskMapper.getAssignedTo());
            taskDetails.setProjectName(taskMapper.getProjectName());
            taskDetails.setValue(taskMapper.getValue());
            taskDetails.setUnit(taskMapper.getUnit());
            taskDetails.setImageId(taskMapper.getImageId());
//            taskDetails.setDocumentId(taskMapper.getDocumentId());
            taskDetails.setComplexity(taskMapper.getComplexity());
            taskDetails.setCustomerId(taskMapper.getCustomerId());
            taskDetails.setTaskChecklistId(taskMapper.getTaskChecklistId());
            //taskDetails.setRatePerUnit(taskMapper.getRatePerUnit());
            taskDetails.setLink(taskMapper.getLink());
            taskDetails.setContact(taskMapper.getContact());
            taskDetails.setOppertunity(taskMapper.getOppertunity());
            taskDetails.setAssignedBy(taskMapper.getUserId());
            
            
            try {
                taskDetails.setStart_date(Utility.getDateFromISOString(taskMapper.getStartDate()));
                taskDetails.setEnd_date(Utility.getDateFromISOString(taskMapper.getEndDate()));
                taskDetails.setAssignedDate(Utility.getDateFromISOString(taskMapper.getAssignedDate()));

            } catch (Exception e) {
                e.printStackTrace();
            }

            taskDetailsRepository.save(taskDetails);

        

        /* insert to employee call link table */
//		String assignTo = "";
//		if(!StringUtils.isEmpty(taskMapper.getAssignedTo())) {
//				assignTo  = employeeService.getEmployeeFullName(taskMapper.getAssignedTo());
//		}
        String ownerName = employeeService.getEmployeeFullName(taskMapper.getUserId());

        Set<String> empList = taskMapper.getIncluded();
        empList.add(taskMapper.getUserId());
        if (!StringUtils.isEmpty(taskMapper.getAssignedTo()) && 
        		!taskMapper.getUserId().equals(taskMapper.getAssignedTo())) {
            empList.add(taskMapper.getAssignedTo());
        }
        if (null != empList && !empList.isEmpty()) {

            // empList.stream().map(employeeId->{
            empList.forEach(employeeId -> {
                EmployeeTaskLink employeeTaskLink = new EmployeeTaskLink();
                employeeTaskLink.setTask_id(taskId);
                employeeTaskLink.setEmployee_id(employeeId);
                employeeTaskLink.setCreation_date(new Date());
                employeeTaskLink.setLive_ind(true);
                employeeTaskRepository.save(employeeTaskLink);

                /* insert to Notification info */
                NotificationDetails notification = new NotificationDetails();
                notification.setNotificationType("task");

                if (employeeId.equals(taskMapper.getUserId())) {
                    if (!StringUtils.isEmpty(taskMapper.getAssignedTo())) {

                        String assignTo2 = employeeService.getEmployeeFullName(taskMapper.getAssignedTo());
                        notification.setMessage(" You have created a " + notification.getNotificationType() + "With " + assignTo2);
                    } else {
                        notification.setMessage(" You have created a " + notification.getNotificationType());
                    }
                    notification.setAssignedTo(employeeId);
                } else {
                    notification.setMessage(
                            ownerName + " has assigned to a " + notification.getNotificationType() + " you ");
                    notification.setAssignedTo(employeeId);
                }
                notification.setUser_id(taskMapper.getUserId());
                notification.setNotificationDate(new Date());
                notification.setOrg_id(notification.getOrg_id());
                notification.setMessageReadInd(false);
                notificationRepository.save(notification);
            });

        }

        /* insert to CandidateEventLink table */
        Set<String> candidateIds = taskMapper.getCandidateId();
        if (null != candidateIds && !candidateIds.isEmpty()) {
            candidateIds.forEach(candidateId -> {
                CandidateTaskLink candidateTaskLink = new CandidateTaskLink();
                candidateTaskLink.setTaskId(taskId);
                candidateTaskLink.setCandidateId(candidateId);
                candidateTaskLink.setCreationdate(new Date());
                candidateTaskLink.setLiveInd(true);
                candidateTaskLink.setComplitionStatus("To Start");
                candidateTaskLinkRepository.save(candidateTaskLink);

                /* insert to Notification info */
                NotificationDetails notification = new NotificationDetails();
                notification.setNotificationType("task");
                notification
                        .setMessage(ownerName + " has assigned to a " + notification.getNotificationType() + " you ");
                notification.setAssignedTo(candidateId);
                notification.setUser_id(taskMapper.getUserId());
                notification.setNotificationDate(new Date());
                notification.setOrg_id(notification.getOrg_id());
                notification.setMessageReadInd(false);
                notificationRepository.save(notification);

            });
        }
        if (!StringUtils.isEmpty(taskMapper.getLeadsId())) {
            LeadsTaskLink leadsTaskLink = new LeadsTaskLink();
            leadsTaskLink.setTaskId(taskId);
            leadsTaskLink.setLeadsId(taskMapper.getLeadsId());
            leadsTaskLink.setCreationDate(new Date());
            leadsTaskLink.setLiveInd(true);
            leadsTaskLink.setComplitionStatus("To Start");
            leadsTaskRepo.save(leadsTaskLink);
          
            LeadsDocumentLink leadsDocumentLink = new LeadsDocumentLink();
			leadsDocumentLink.setLeadsId(taskMapper.getLeadsId());
			leadsDocumentLink.setDocumentId(taskMapper.getDocumentId());
			leadsDocumentLink.setCreationDate(new Date());
			leadsDocumentLink.setSharedUser(taskMapper.getUserId());
			leadsDocumentLink.setShareInd(true);
			leadsDocumentLink.setContractInd(true);
			leadsDocumentLinkRepository.save(leadsDocumentLink);
        }
        
        /* insert into documentLink table */
        if (!StringUtils.isEmpty(taskMapper.getDocumentId())) {
			TaskDocumentLink taskDocumentLink = new TaskDocumentLink();
			taskDocumentLink.setTaskId(taskId);
			taskDocumentLink.setDocumentId(taskMapper.getDocumentId());
			taskDocumentLink.setCreationDate(new Date());

			taskDocumentLinkRepository.save(taskDocumentLink);
		}
        
        /* insert into InvestorLeads table */
        if (!StringUtils.isEmpty(taskMapper.getInvestorLeadsId())) {
            InvestorLeadsTaskLink investorleadsTaskLink = new InvestorLeadsTaskLink();
            investorleadsTaskLink.setTaskId(taskId);
            investorleadsTaskLink.setInvestorLeadsId(taskMapper.getInvestorLeadsId());
            investorleadsTaskLink.setCreationDate(new Date());
            investorleadsTaskLink.setLiveInd(true);
            investorleadsTaskLink.setComplitionStatus("To Start");
            investorLeadsTaskRepo.save(investorleadsTaskLink);
            
            InvestorLeadsDocumentLink investorLeadsDocumentLink = new InvestorLeadsDocumentLink();
			investorLeadsDocumentLink.setInvestorleadsId(taskMapper.getInvestorLeadsId());
			investorLeadsDocumentLink.setDocumentId(taskMapper.getDocumentId());
			investorLeadsDocumentLink.setCreationDate(new Date());
			investorLeadsDocumentLink.setSharedUser(taskMapper.getUserId());
			investorLeadsDocumentLink.setShareInd(true);
			investorLeadsDocumentLink.setContractInd(true);
			investorLeadsDocumentLinkRepository.save(investorLeadsDocumentLink);
        }
        
        /* insert into contact table */
        if (!StringUtils.isEmpty(taskMapper.getContact())) {
        	ContactTaskLink contactTaskLink = new ContactTaskLink();
        	contactTaskLink.setTaskId(taskId);
        	contactTaskLink.setContactId(taskMapper.getContact());
        	contactTaskLink.setCreationDate(new Date());
        	contactTaskLink.setLiveInd(true);
        	contactTaskLink.setComplitionStatus("To Start");
            contactTaskRepo.save(contactTaskLink);
            
            ContactDocumentLink contactDocumentLink = new ContactDocumentLink();
			contactDocumentLink.setContact_id(taskMapper.getContact());
			contactDocumentLink.setDocument_id(taskMapper.getDocumentId());
			contactDocumentLink.setCreation_date(new Date());
			contactDocumentLink.setSharedUser(taskMapper.getUserId());
			contactDocumentLink.setShareInd(true);
			contactDocumentLink.setContractInd(true);
			contactDocumentLinkRepository.save(contactDocumentLink);
        }
        
        /* insert into customer table */
        if (!StringUtils.isEmpty(taskMapper.getCustomerId())) {
        	CustomerTaskLink customerTaskLink = new CustomerTaskLink();
        	customerTaskLink.setTaskId(taskId);
        	customerTaskLink.setCustomerId(taskMapper.getCustomerId());
        	customerTaskLink.setCreationDate(new Date());
        	customerTaskLink.setLiveInd(true);
        	customerTaskLink.setComplitionStatus("To Start");
        	customerTaskRepo.save(customerTaskLink);
        	
        	CustomerDocumentLink customerDocumentLink = new CustomerDocumentLink();
            customerDocumentLink.setCustomerId(taskMapper.getCustomerId());
            customerDocumentLink.setDocumentId(taskMapper.getDocumentId());
            customerDocumentLink.setContractInd(true);
            customerDocumentLink.setSharedUser(taskMapper.getUserId());
            customerDocumentLink.setShareInd(true);
            customerDocumentLinkRepository.save(customerDocumentLink);
        }
        /* insert into Investor table */
        if (!StringUtils.isEmpty(taskMapper.getInvestorId())) {
            InvestorTaskLink investorTaskLink = new InvestorTaskLink();
            investorTaskLink.setTaskId(taskId);
            investorTaskLink.setInvestorId(taskMapper.getInvestorId());
            investorTaskLink.setCreationDate(new Date());
            investorTaskLink.setLiveInd(true);
            investorTaskLink.setComplitionStatus("To Start");
            investorTaskRepo.save(investorTaskLink);
            
            InvestorDocumentLink investorDocumentLink = new InvestorDocumentLink();
			investorDocumentLink.setInvestorId(taskMapper.getInvestorId());
			investorDocumentLink.setDocumentId(taskMapper.getDocumentId());
			investorDocumentLink.setSharedUser(taskMapper.getUserId());
			investorDocumentLink.setShareInd(true);
			investorDocumentLink.setContractInd(true);
			investorDocumentLinkRepo.save(investorDocumentLink);
        }
        
        /* insert to Notes table */
        Notes notes = new Notes();
		notes.setCreation_date(new Date());
		notes.setNotes(taskMapper.getTaskDescription());
		notes.setLiveInd(true);
		notes.setUserId(taskMapper.getUserId());
		Notes note = notesRepository.save(notes);

		String notesId = note.getNotes_id();
		/* insert to TaskNotesLink table */

		TaskNotesLink taskNote = new TaskNotesLink();
		taskNote.setTaskId(taskId);
		taskNote.setNotesId(notesId);
		taskNote.setCreationDate(new Date());
		taskNotesLinkRepository.save(taskNote);
		
		mapper = getTaskDetailsById(taskId);
	} 
        return mapper;
    
	}

	private ActivityMapper getTaskDetailsById(String taskId) {
		ActivityMapper activityMapper=new ActivityMapper();
		TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskId);
		 if(null!=taskDetails) {
			 activityMapper.setActivityType(taskDetails.getTask_name());
			 activityMapper.setTaskId(taskDetails.getTask_id());
			 activityMapper.setCategory("Task");
			 activityMapper.setCreationDate(Utility.getISOFromDate(taskDetails.getCreation_date()));
			 activityMapper.setStartDate(Utility.getISOFromDate(taskDetails.getStart_date()));
			 activityMapper.setEndDate(Utility.getISOFromDate(taskDetails.getEnd_date()));
			 activityMapper.setDescription(taskDetails.getTask_description());
			 activityMapper.setUserId(taskDetails.getUser_id());
			 activityMapper.setOrganizationId(taskDetails.getOrganization_id());
			 activityMapper.setWoner(employeeService.getEmployeeFullName(taskDetails.getUser_id()));
			 activityMapper.setTaskStatus(taskDetails.getTask_status());
			 activityMapper.setAssignedBy(employeeService.getEmployeeFullName(taskDetails.getAssignedBy())); 	
			 activityMapper.setAssignedTo(employeeService.getEmployeeFullName(taskDetails.getAssigned_to())); 	
		 }
		return activityMapper;
	}
	
	 @Override
	    public ActivityMapper updateActivityTaskDetails(String taskId, TaskMapper taskMapper) throws IOException, TemplateException {
		 ActivityMapper mapper=new ActivityMapper();
	        if (null != taskId) {
	            
	            TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(taskId);
	           

	            if (null != taskDetails) {
//	            	 taskDetails.setLiveInd(false);
//	                 taskDetailsRepository.save(taskDetails);
//	                 TaskDetails newTaskDetailsData = new TaskDetails();
	            	String taskNote1 = taskMapper.getTaskDescription();
	        		String link = taskMapper.getLink();
	        		String msg = "";
	        		if (taskNote1 != null && link != null) {
	        			msg = "/n"+"Note :"+taskNote1+"/n"+"Link :"+link;
	        		} else if (taskNote1 == null && link != null) {
	        			msg = "/n"+"Link :"+link;
	        		} else if (taskNote1 != null && link == null) {
	        			msg = "/n"+"Note :"+taskNote1;
	        		} 
	        		
	        		/* Notification */
	            	Notificationparam param = new Notificationparam();
	                EmployeeDetails emp = employeeRepository.getEmployeesByuserId(taskMapper.getUserId());
	                String name = employeeService.getEmployeeFullNameByObject(emp);
	                param.setEmployeeDetails(emp);
	                param.setAdminMsg("Task "+"'"+taskMapper.getTaskName() + "' updated by "+name+msg);
	                param.setOwnMsg("Task "+taskMapper.getTaskName() +" updated.");
	                param.setNotificationType("Task update"); 
	                param.setProcessNmae("Task");
	                param.setType("update");
	                param.setEmailSubject("Korero alert- Task updated");
	                param.setCompanyName(companyName);
	                param.setUserId(taskMapper.getUserId());   

	                if (taskMapper.getTaskName() != null) {
	                	taskDetails.setTask_name(taskMapper.getTaskName());
	                } else {
	                	taskDetails.setTask_name(taskDetails.getTask_name());
	                }

	                if (taskMapper.getTaskDescription() != null) {
						List<TaskNotesLink> list = taskNotesLinkRepository.getNotesIdByTaskId(taskId);
						if (null != list && !list.isEmpty()) {
							list.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
							Notes notes = notesRepository.findByNoteId(list.get(0).getNotesId());
							if (null != notes) {
								notes.setNotes(taskMapper.getTaskDescription());
								notesRepository.save(notes);
							}
						}
					} 
	                if (taskMapper.getTaskStatus() != null) {
	                	taskDetails.setTask_status(taskMapper.getTaskStatus());
	                } else {
	                	taskDetails.setTask_status(taskDetails.getTask_status());
	                }

	                if (taskMapper.getTaskType() != null) {
	                	taskDetails.setTask_type((taskMapper.getTaskType()));
	                } else {
	                	taskDetails.setTask_type(taskDetails.getTask_type());
	                }

	                if (taskMapper.getTimeZone() != null) {
	                	taskDetails.setTime_zone(taskMapper.getTimeZone());
	                } else {
	                	taskDetails.setTime_zone(taskDetails.getTime_zone());
	                }

	                if (taskMapper.getStartDate() != null) {
	                    try {
	                    	taskDetails.setStart_date(Utility.getDateFromISOString(taskMapper.getStartDate()));
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                } else {
	                	taskDetails.setStart_date(taskDetails.getStart_date());
	                }
	                if (taskMapper.getEndDate() != null) {
	                    try {
	                    	taskDetails.setEnd_date(Utility.getDateFromISOString(taskMapper.getEndDate()));
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                } else {
	                	taskDetails.setEnd_date(taskDetails.getEnd_date());
	                }

	                if (taskMapper.getPriority() != null) {
	                	taskDetails.setPriority(taskMapper.getPriority());
	                } else {
	                	taskDetails.setPriority(taskDetails.getPriority());
	                }
	                if (taskMapper.getAssignedTo() != null) {
	                	taskDetails.setAssigned_to(taskMapper.getAssignedTo());
	                } else {
	                	taskDetails.setAssigned_to(taskDetails.getAssigned_to());
	                }
	                
	                if (null  != taskDetails.getAssignedBy() ) {
						if (null  != taskMapper.getAssignedTo() && !taskDetails.getAssignedBy().equals(taskMapper.getAssignedTo())) {
							taskDetails.setAssignedBy(taskMapper.getUserId());
			            } else {
			            	taskDetails.setAssignedBy(taskDetails.getAssignedBy());
			            }
					}else {
						taskDetails.setAssignedBy(taskMapper.getUserId());
					}	
	                
	                if (taskMapper.getProjectName() != null) {
	                	taskDetails.setProjectName(taskMapper.getProjectName());
	                } else {
	                	taskDetails.setProjectName(taskDetails.getProjectName());
	                }

	                if (taskMapper.getValue() != null) {
	                	taskDetails.setValue(taskMapper.getValue());
	                } else {
	                	taskDetails.setValue(taskDetails.getValue());
	                }
	                if (taskMapper.getUnit() != null) {
	                	taskDetails.setUnit(taskMapper.getUnit());
	                } else {
	                	taskDetails.setUnit(taskDetails.getUnit());
	                }
	                if (taskMapper.getImageId() != null) {
	                	taskDetails.setImageId(taskMapper.getImageId());
	                } else {
	                	taskDetails.setImageId(taskDetails.getImageId());
	                }
//	                if (taskMapper.getDocumentId() != null) {
//	                    newTaskDetailsData.setDocumentId(taskMapper.getDocumentId());
//	                } else {
//	                    newTaskDetailsData.setDocumentId(taskDetails.getDocumentId());
//	                }
	                if (taskMapper.getComplexity() != null) {
	                	taskDetails.setComplexity(taskMapper.getComplexity());
	                } else {
	                	taskDetails.setComplexity(taskDetails.getComplexity());
	                }
	                if (taskMapper.getCustomerId() != null) {
	                	taskDetails.setCustomerId(taskMapper.getCustomerId());
	                } else {
	                	taskDetails.setCustomerId(taskDetails.getCustomerId());
	                }
	                if (taskMapper.getTaskChecklistId() != null) {
	                	taskDetails.setTaskChecklistId(taskMapper.getTaskChecklistId());
	                } else {
	                	taskDetails.setTaskChecklistId(taskDetails.getTaskChecklistId());
	                }
	                if (taskMapper.getAssignedDate() != null) {
	                    try {
	                    	taskDetails.setAssignedDate(Utility.getDateFromISOString(taskMapper.getAssignedDate()));
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                } else {
	                	taskDetails.setAssignedDate(taskDetails.getAssignedDate());
	                }
	                if (taskMapper.getLink() != null) {
	                	taskDetails.setLink(taskMapper.getLink());
	                } else {
	                	taskDetails.setLink(taskDetails.getLink());
	                }
	                if (taskMapper.getContact() != null) {
	                	taskDetails.setContact(taskMapper.getContact());
	                } else {
	                	taskDetails.setContact(taskDetails.getContact());
	                }
	                
	                if (taskMapper.getOppertunity() != null) {
	                	taskDetails.setOppertunity(taskMapper.getOppertunity());
	                } else {
	                	taskDetails.setOppertunity(taskDetails.getOppertunity());
	                }
	                taskDetails.setTask_id(taskId);
	                taskDetails.setCreation_date(taskDetails.getCreation_date());
	                taskDetails.setUser_id(taskMapper.getUserId());
	                taskDetails.setOrganization_id(taskMapper.getOrganizationId());
	                taskDetails.setLiveInd(true);
	                taskDetailsRepository.save(taskDetails);
	                
	                /*insert to document link*/
	                if (taskMapper.getDocumentId() != null) { 
	                	TaskDocumentLink taskDocumentLink = new TaskDocumentLink();
	        			taskDocumentLink.setTaskId(taskId);
	        			taskDocumentLink.setDocumentId(taskMapper.getDocumentId());
	        			taskDocumentLink.setCreationDate(new Date());
	        			taskDocumentLinkRepository.save(taskDocumentLink);
	                }
	              //Edit Included
	        		List<TaskIncludedLink> list = taskIncludedLinkRepository
	        				.findByTaskId(taskId);
	        		if (null != list && !list.isEmpty()) {
	        			for (TaskIncludedLink taskIncludedLink1 : list) {
	        				taskIncludedLinkRepository.delete(taskIncludedLink1);
	        			}
	        		}
//	        		List<String> taskLinkList = employeeTaskRepository.getEmpListByTaskId(taskId).stream()
//	        				.map(EmployeeTaskLink::getEmployee_id).collect(Collectors.toList());
	        		
	        		List<EmployeeTaskLink> taskLinkList = employeeTaskRepository.getEmpListByTaskId(taskId);
	        		employeeTaskRepository.deleteAll(taskLinkList);
	        		
	        		List<String> empList = new ArrayList<>();
	        		if (taskMapper.getIncluded() != null && !taskMapper.getIncluded().isEmpty()) {
	        			empList.addAll(taskMapper.getIncluded());
	        			} 		
	    			empList.add(taskMapper.getUserId());
	    			for (String id : empList) {
	    				EmployeeTaskLink employeeTaskLink = new EmployeeTaskLink();
						employeeTaskLink.setTask_id(taskId);
						employeeTaskLink.setEmployee_id(id);
						employeeTaskLink.setCreation_date(new Date());
						employeeTaskLink.setLive_ind(true);
						employeeTaskRepository.save(employeeTaskLink);
					}
	        		
						if (taskMapper.getIncluded() != null && !taskMapper.getIncluded().isEmpty()) {
							List<String> includedids = new ArrayList<>(taskMapper.getIncluded());
							param.setIncludeedUserIds(includedids);
							param.setIncludeMsg(
									"You have been added Task " + "'" + taskMapper.getIncluded() + "'" + " by " + name+msg);
							for (String id : taskMapper.getIncluded()) {
								TaskIncludedLink taskIncludedLink = new TaskIncludedLink();
								taskIncludedLink.setTaskId(taskId);
								taskIncludedLink.setEmployeeId(id);
								taskIncludedLink.setCreationDate(new Date());
								taskIncludedLink.setLiveInd(true);
								taskIncludedLink.setOrgId(taskMapper.getOrganizationId());
								taskIncludedLinkRepository.save(taskIncludedLink);
							}
						}

	                // update call employee link

//				Set<String> empList = new HashSet<String>();
//				List<EmployeeTaskLink> employeeList = employeeTaskRepository.getEmpListByTaskId(taskId);
//				employeeTaskRepository.deleteAll(employeeList);

//				if (null != employeeList && !employeeList.isEmpty()) {
//					for (EmployeeTaskLink employeeTaskLink : employeeList) {
//					
//						 employeeTaskLink.setLive_ind(false);
//						employeeTaskRepository.save(employeeTaskLink);
	//
//					}			
//				}
	                String ownerName = employeeService.getEmployeeFullName(taskMapper.getUserId());
	                /* insert to Notification Table */		
	    			
	                // insert new data to call employee link//

//				empList.add(taskMapper.getUserId());
//				empList.add(taskMapper.getAssignedTo());

	                if (!StringUtils.isEmpty(taskMapper.getAssignedTo())) {
//	                    if (!taskDetails.getAssigned_to().equalsIgnoreCase(taskMapper.getAssignedTo())) {
//	                        List<EmployeeTaskLink> employeeTaskLinks = employeeTaskRepository.getEmpListByTaskId(taskId);
//	                        if (null != employeeTaskLinks && !employeeTaskLinks.isEmpty()) {
//	                            for (EmployeeTaskLink emp : employeeTaskLinks) {
	                                if (!taskDetails.getAssigned_to().equalsIgnoreCase(taskMapper.getAssignedTo())) {
	                                    EmployeeTaskLink employeeTaskLink = new EmployeeTaskLink();
	                                    employeeTaskLink.setTask_id(taskId);
	                                    employeeTaskLink.setEmployee_id(taskMapper.getAssignedTo());
	                                    employeeTaskLink.setCreation_date(new Date());
	                                    employeeTaskLink.setLive_ind(true);
	                                    employeeTaskRepository.save(employeeTaskLink);
	                                    /* insert to Notification info */
	                                    List<String> assignToUserIds = new ArrayList<>();
	        			            	assignToUserIds.add(taskMapper.getAssignedTo());
	        			            	param.setAssignToUserIds(assignToUserIds); 
	        			                param.setAssignToMsg("Task "+"'"+taskMapper.getTaskName() + "'"+ " assigned to "+employeeService.getEmployeeFullName(taskMapper.getAssignedTo())+" by "+name+msg);


	                                    EmployeeTaskLink employeeTaskLink2 = employeeTaskRepository.getEmployeeTaskLink(taskDetails.getAssigned_to(), taskId);
	                                    if (null != employeeTaskLink2) {
	                                    employeeTaskRepository.delete(employeeTaskLink2);
	                                    }
//	                                    /* insert to Notification info */
//	                                    NotificationDetails notification1 = new NotificationDetails();
//	                                    notification1.setNotificationType("task");
//	                                    notification1.setAssignedTo(taskDetails.getAssigned_to());
//	                                    notification1.setMessage(
//	                                            ownerName + " has removed to you " + notification.getNotificationType() + taskMapper.getTaskName());
//	                                    notification1.setUser_id(taskMapper.getUserId());
//	                                    notification1.setNotificationDate(new Date());
//	                                    notification1.setOrg_id(notification.getOrg_id());
//	                                    notification1.setMessageReadInd(false);
//	                                    notificationRepository.save(notification1);


	                                    List<Hour> hr = hourRepository.getByProjectManagerAndTaskId(taskDetails.getAssigned_to(), taskId);
	                                    if (null != hr && !hr.isEmpty()) {
	                                        for (Hour hour : hr) {
	                                            hour.setProjectManager(taskMapper.getAssignedTo());
	                                            hourRepository.save(hour);
	                                        }
	                                    }


//					}else {
//						EmployeeTaskLink employeeTaskLink2  = employeeTaskRepository.getEmployeeTaskLink(taskDetails.getAssigned_to(), taskId);
//						employeeTaskRepository.delete(employeeTaskLink2);
//						
//						/* insert to Notification info */
//						NotificationDetails notification = new NotificationDetails();
//						notification.setNotificationType("task");
//							notification.setAssignedTo(taskDetails.getAssigned_to());
//							notification.setMessage(
//									ownerName + " has removed to you " + notification.getNotificationType());
//						notification.setUser_id(taskMapper.getUserId());
//						notification.setNotificationDate(new Date());
//						notification.setOrg_id(notification.getOrg_id());
//						notification.setMessageReadInd(false);
//						notificationRepository.save(notification);
	                                }
//	                            }
//	                        }
//	                    }
	                }
	                // insert new data to call employee link//
	                List<String> canTaskLinks = candidateTaskLinkRepository.getCandidateTaskLinkByTaskId(taskId).stream()
	                        .map(CandidateTaskLink::getCandidateId)
	                        .collect(Collectors.toList());

	                List<String> candidateIdsList = new ArrayList<>();
	                if (null != taskMapper.getCandidateId() && !taskMapper.getCandidateId().isEmpty()) {
	                    candidateIdsList.addAll(taskMapper.getCandidateId());
	                }
	                candidateIdsList.addAll(canTaskLinks);
	                if (null != candidateIdsList && !candidateIdsList.isEmpty()) {
	                    // candidateIdsList.forEach(candidateId->{
	                    System.out.println("candidateIdsList==" + candidateIdsList.toString());
	                    for (String candidateId : candidateIdsList) {
	                        //	if (taskMapper.getCandidateId().contains(candidateId)) {

	                        System.out.println("candiXXXXX==" + candidateId);
	                        if (canTaskLinks.contains(candidateId) && !taskMapper.getCandidateId().contains(candidateId)) {
	                            System.out.println("CANVID==" + candidateId + "||" + taskId);
	                            System.out.println("in 1st condition " + candidateId);

	                            CandidateTaskLink candidateTaskLink = candidateTaskLinkRepository
	                                    .findByCandidateIdAndTaskIdAndLiveInd(candidateId, taskId, true);
	                            System.out.println("CANDIDATE==" + candidateTaskLink.toString());
	                            candidateTaskLinkRepository.delete(candidateTaskLink);

	                            /* insert to Notification info */
	                            NotificationDetails notification = new NotificationDetails();
	                            notification.setNotificationType("task");
	                            notification.setMessage(
	                                    ownerName + " has removed to you a " + notification.getNotificationType());
	                            notification.setAssignedTo(candidateId);
	                            notification.setUser_id(taskMapper.getUserId());
	                            notification.setNotificationDate(new Date());
	                            notification.setOrg_id(notification.getOrg_id());
	                            notification.setMessageReadInd(false);
	                            notificationRepository.save(notification);

	                        } else if (!canTaskLinks.contains(candidateId) && taskMapper.getCandidateId().contains(candidateId)) {
	                            System.out.println("in 2st condition " + candidateId);
	                            CandidateTaskLink candidateTaskLink = new CandidateTaskLink();
	                            candidateTaskLink.setTaskId(taskId);
	                            candidateTaskLink.setCandidateId(candidateId);
	                            candidateTaskLink.setCreationdate(new Date());
	                            candidateTaskLink.setLiveInd(true);
	                            candidateTaskLink.setComplitionStatus("To Start");
	                            candidateTaskLinkRepository.save(candidateTaskLink);

	                            /* insert to Notification info */
	                            NotificationDetails notification = new NotificationDetails();
	                            notification.setNotificationType("task");
	                            notification.setMessage(
	                                    ownerName + " has assigned to a " + notification.getNotificationType() + " you ");
	                            notification.setAssignedTo(candidateId);
	                            notification.setUser_id(taskMapper.getUserId());
	                            notification.setNotificationDate(new Date());
	                            notification.setOrg_id(notification.getOrg_id());
	                            notification.setMessageReadInd(false);
	                            notificationRepository.save(notification);

	                        }
	                        //}

	                    }
	                }
	                notificationService.createNotificationForDynamicUsers(param);
	                
	                mapper = getTaskDetailsById(taskId);
	            }
	        }
	       
	        return mapper;

	    }
	
	@Override
	public List<TaskViewMapper> getCloseTaskListByUserId(String userId, String type) {
		List<EmployeeTaskLink> taskList = new ArrayList<>();
		if (type.equalsIgnoreCase("mytask")) {
			taskList = employeeTaskRepository.getMyTaskListByEmpId(userId);
		} else {
			taskList = employeeTaskRepository.getMyApprovalTaskListByEmpId(userId);
		}
		return taskList.stream().map(EmployeeTaskLink::getTask_id)
				.map(taskDetailsRepository::getCompletedTaskDetailsByTaskId).filter(Objects::nonNull)
				.map(this::getTaskViewMapper).collect(Collectors.toList());
	}

	@Override
	public List<TaskViewMapper> getTaskListPageWiseByIncludedUserId(String userId, int pageNo,
			int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "creationDate"));
		List<TaskViewMapper> opportunities = new ArrayList<>();
		Page<TaskIncludedLink> opportunityIncludedLink = taskIncludedLinkRepository
				.getTaskIncludedLinkByUserIdWithPagination(userId, paging);
		if (null != opportunityIncludedLink && !opportunityIncludedLink.isEmpty()) {
			opportunityIncludedLink.stream().filter(li -> (li != null)).map(li -> {
				TaskDetails task = taskDetailsRepository
						.getTaskDetailsById(li.getTaskId());
				if (null != task) {
					TaskViewMapper mapper = getTaskDetails(task.getTask_id());
					if (null != mapper) {
						mapper.setPageCount(opportunityIncludedLink.getTotalPages());
						mapper.setDataCount(opportunityIncludedLink.getSize());
						mapper.setListCount(opportunityIncludedLink.getTotalElements());
						opportunities.add(mapper);
						return mapper;
					}
				}
				return null;
			}).collect(Collectors.toList());					
		}
		return opportunities;
	}

	@Override
	public HashMap getCountListByIncludedUserId(String userId) {
		List<TaskIncludedLink> taskIncludedLinkList = taskIncludedLinkRepository
				.getTaskIncludedLinkByUserId(userId);
		HashMap map = new HashMap();
		map.put("TaskCount", taskIncludedLinkList.size());
		return map;
	}
	
	private void updateActivityTaskLog(String userType, String callId,String userTypeId, TaskMapper mapper) {
		LastActivityLog dbData = lastActivityLogRepository.findByUserTypeAndUserTypeId(userType,userTypeId);
		if(null!=dbData) {
		
			dbData.setActivityId(callId);
			dbData.setActivityType("task");
			dbData.setCreationDate(new Date());
			dbData.setDescription(mapper.getTaskDescription());
			try {
				dbData.setStartDate(Utility.getDateFromISOString((mapper.getStartDate())));
				dbData.setEndDate(Utility.getDateFromISOString(mapper.getEndDate()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			dbData.setStartTime(mapper.getStartTime());
//			dbData.setEndTime(mapper.getEndTime());
			dbData.setSubject(mapper.getTaskName());
			dbData.setUserId(mapper.getUserId());
			dbData.setOrganizationId(mapper.getOrganizationId());
			dbData.setTimeZone(mapper.getTimeZone());
			dbData.setUserType(userType);
			dbData.setUserTypeId(userTypeId);
			lastActivityLogRepository.save(dbData);
		}else
		{
			LastActivityLog dynamo = new LastActivityLog();
			dynamo.setActivityId(callId);
			dynamo.setActivityType("task");
			dynamo.setCreationDate(new Date());
			dynamo.setDescription(mapper.getTaskDescription());
			try {
				dynamo.setStartDate(Utility.getDateFromISOString((mapper.getStartDate())));
				dynamo.setEndDate(Utility.getDateFromISOString(mapper.getEndDate()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			dynamo.setStartTime(mapper.getStartTime());
//			dynamo.setEndTime(mapper.getEndTime());
			dynamo.setSubject(mapper.getTaskName());
			dynamo.setUserId(mapper.getUserId());
			dynamo.setOrganizationId(mapper.getOrganizationId());
			dynamo.setTimeZone(mapper.getTimeZone());
			dynamo.setUserType(userType);
			dynamo.setUserTypeId(userTypeId);
			lastActivityLogRepository.save(dynamo);
		}
		}

	@Override
	public HashMap getTaskTypeCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<TaskType> list = taskTypeRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("TaskTypeCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportTaskTypeListToExcel(List<TaskMapper> list) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("candidate");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < taskType_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(taskType_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != list && !list.isEmpty()) {
			for (TaskMapper taskMapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(taskMapper.getTaskType());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < taskType_headings.length; i++) {
			sheet.autoSizeColumn(i);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(outputStream.toByteArray());

	}

	@Override
	public List<TaskViewMapper> getTeamTaskByUnderUserId(String userId, int pageNo, int pageSize) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
                .map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);
		
		List<String> userIdss = new ArrayList<>(userIds);
		
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		 Page<EmployeeTaskLink> employeeTaskList = employeeTaskRepository.getTaskListByEmpIdsPage(userIdss, paging);
     
     int size = employeeTaskList.getSize();
     List<TaskViewMapper> resultList = new ArrayList<TaskViewMapper>();
     if (null != employeeTaskList && !employeeTaskList.isEmpty()) {
         employeeTaskList.stream().map(employeeTaskLink -> {
             TaskViewMapper taskMapper = getTaskDetails(employeeTaskLink.getTask_id());
             if (null != taskMapper.getTaskId()) {
                 taskMapper.setApprovedInd(employeeTaskLink.getApproveStatus());
                 taskMapper.setFilterTaskInd(employeeTaskLink.isFilterTaskInd());
                 taskMapper.setNoOfPages(size);
                 taskMapper.setPageCount(employeeTaskList.getTotalPages());
                 taskMapper.setDataCount(employeeTaskList.getSize());
                 taskMapper.setListCount(employeeTaskList.getTotalElements());
                 resultList.add(taskMapper);
             }

             return resultList;
         }).collect(Collectors.toList());
     }
     return resultList;
	}

	@Override
	public HashMap getTeamTaskCountByUnderUserId(String userId) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
                .map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);
		
		List<String> userIdss = new ArrayList<>(userIds);
		
		HashMap map = new HashMap();
		List<EmployeeTaskLink> employeeTaskList = employeeTaskRepository.getTasCountByEmpIdsPage(userIdss);
		map.put("taskTeam",employeeTaskList.size());
		
	      return map;
		
	}

	@Override
	public List<TaskViewMapper> getTaskListByTaskTypeIdAndUserIdAndQuarterAndYear(String userId, String typeName,
			String quarter, int year) {
		
		 List<TaskViewMapper> resultList = new ArrayList<>();
		 Date end_date = null;
		    Date start_date = null;
		    try {
				 if(quarter.equalsIgnoreCase("Q1")) {
					 end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getPlusDate(Utility.getQuarterEndDate(year,1),21)));
					 start_date = Utility.removeTime(Utility.getQuarterStartDate(year,1));
				 }
				 if(quarter.equalsIgnoreCase("Q2")) {
					end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getPlusDate(Utility.getQuarterEndDate(year,2),21)));
					start_date = Utility.removeTime(Utility.getQuarterStartDate(year,2));
				 }
				 if(quarter.equalsIgnoreCase("Q3")) {
					 end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getPlusDate(Utility.getQuarterEndDate(year,3),21)));
					 start_date = Utility.removeTime(Utility.getQuarterStartDate(year,3));
				 }
				 if(quarter.equalsIgnoreCase("Q4")) {
					 end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getPlusDate(Utility.getQuarterEndDate(year,4),21)));
					 start_date = Utility.removeTime(Utility.getQuarterStartDate(year,4));
				 }
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		


	    TaskType taskType = taskTypeRepository.findByTaskType(typeName);
	    if(null!= taskType) {

	        List<EmployeeTaskLink> list = employeeTaskRepository.getByEmployeeId(userId);
	        for (EmployeeTaskLink employeeTaskLink : list) {

	            TaskDetails task = taskDetailsRepository.
	            		getTaskListByTaskTypeIdAndStartdateAndEndDateQuarterWise(taskType.getTaskTypeId(),employeeTaskLink.getTask_id(), start_date, end_date);

	            if (null != task) {
	                TaskViewMapper taskViewMapper = getTaskDetails(task.getTask_id());
	                if(null!=taskViewMapper) {
	                resultList.add(taskViewMapper);
	                }
	            }

	        }

	    }
		return resultList;
	}
	
	@Override
	public List<TaskViewMapper> getPendingTaskListByTaskTypeIdAndUserId(String userId, String typeName) {
		 List<TaskViewMapper> resultList = new ArrayList<>();
		 Date start_date = null;
		 Date todayDate = new Date();
		 System.out.println("todayDate===" + todayDate);
		 int weakNo = Utility.getWeekNoFromDate(todayDate);
		 System.out.println("weakNo===" + weakNo);
		 int year = Utility.getYearFromDate(todayDate);
		 System.out.println("year===" + year);
		 Date weakStartDate = Utility.getStartDateFromWeekNo(weakNo, year);
		 System.out.println("weakStartDate===" + weakStartDate);
		 
		    try {
				start_date = Utility.removeTime(weakStartDate);
				 System.out.println("start_date===" + start_date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		    
		    System.out.println("start_date===+++++++=========="+start_date);
		    TaskType taskType = taskTypeRepository.findByTaskType(typeName);
		    if(null!= taskType) {

		        List<EmployeeTaskLink> list = employeeTaskRepository.getByEmployeeId(userId);
		        for (EmployeeTaskLink employeeTaskLink : list) {

		            TaskDetails task = taskDetailsRepository.
		            		getTaskListByTaskTypeIdAndEndDateBeforeStartdate(taskType.getTaskTypeId(),employeeTaskLink.getTask_id(), start_date);

		            if (null != task) {
		                TaskViewMapper taskViewMapper = getTaskDetails(task.getTask_id());
		                if(null!=taskViewMapper) {
		                resultList.add(taskViewMapper);
		                }
		            }

		        }

		    }
		return resultList;
	}

	@Override
	public TaskStepsMapper createTaskStep(TaskStepsMapper mapper, String loggedInUserId) {
		TaskSteps step = new TaskSteps();
		step.setCreationDate(new Date());
		step.setUserId(loggedInUserId);
		step.setLiveInd(true);
		step.setStatus(mapper.getStatus());
		step.setStep(mapper.getStep());
		step.setTaskId(mapper.getTaskId());
		try {
			step.setEndDate(Utility.getDateFromISOString(mapper.getEndDate()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		TaskSteps updateStep = taskStepsRepo.save(step);
		return getTaskSteps(updateStep);
	}
	
	private TaskStepsMapper getTaskSteps(TaskSteps step) {
		TaskStepsMapper mapper = new TaskStepsMapper();
		mapper.setStatus(step.getStatus());
		mapper.setStep(step.getStep());
		try {
			mapper.setEndDate(Utility.getISOFromDate(step.getEndDate()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapper.setId(step.getId());	
		return mapper;	
	}

	@Override
	public List<TaskStepsMapper> getTaskStepsByTaskId(String taskId) {
		List<TaskSteps> list = taskStepsRepo.findByTaskId(taskId);
		list.sort(Comparator.comparing(TaskSteps::getCreationDate).reversed());
		List<TaskStepsMapper> resultList = new ArrayList<>();
		
		resultList = list.stream()
         .map(this::getTaskSteps)
         .collect(Collectors.toList());
		
		return resultList;
	}

	@Override
	public TaskStepsMapper updateTaskStepsByStepId(TaskStepsMapper mapper,String loggedInUserId) {
		TaskSteps step = taskStepsRepo.findById(mapper.getId()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task step with ID " + mapper.getId()+"Not Found"));
		
			step.setCreationDate(new Date());
			step.setUserId(loggedInUserId);
			step.setLiveInd(true);
			step.setStatus(mapper.getStatus());
			step.setStep(mapper.getStep());
			step.setTaskId(mapper.getTaskId());
			try {
				step.setEndDate(Utility.getDateFromISOString(mapper.getEndDate()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			TaskSteps updateStep = taskStepsRepo.save(step);
			return getTaskSteps(updateStep);
	}

	@Override
	public String deleteTaskStepsByStepId(String stepId) {
		TaskSteps step = taskStepsRepo.findById(stepId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task step with ID " + stepId +"Not Found"));
		taskStepsRepo.delete(step);
		return "Successfully Deleted";
	}

	@Override
	public TaskViewMapper transferTaskOneWeakToAnother(TaskDragDropMapper mapper) {

	        TaskViewMapper taskMapper = new TaskViewMapper();
	        TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(mapper.getTaskId());
	        if (null != taskDetails) {
	        	Date end_date = null;
	    		Date start_date = null;
	    		Date taskEndDate = null;
	    		System.out.println("mapper.getTargetWeakStartDate()===" + mapper.getTargetWeakStartDate());
	    		System.out.println("mapper.getPresentWeakStartDate()===" + mapper.getPresentWeakStartDate());
	    		try {
	    			end_date = Utility.removeTime(Utility.getDateFromISOString(mapper.getTargetWeakStartDate()));
	    			start_date = Utility.removeTime(Utility.getDateFromISOString(mapper.getPresentWeakStartDate()));
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    		
	    		System.out.println("end_date===" + end_date);
	    		System.out.println("start_date===" + start_date);
	    		
	    		int count = 0;
	    		Date temp_date = start_date;
	    		while (end_date.after(temp_date)) {
	    			count++;
	    			temp_date = new Date(temp_date.getTime() + 24 * 60 * 60 * 1000);
	    		}
	    		if (count == 0) {
	    			count++;
	    		}
	    		System.out.println("taskDetails.getEnd_date()===" + taskDetails.getEnd_date());
	    		System.out.println("count===" + count);
	    		try {
	    			taskEndDate = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getPlusDate(taskDetails.getEnd_date(),count)));
					 } catch (Exception e) {
			    			e.printStackTrace();
			    	 }
	    		System.out.println("taskEndDate===" + taskEndDate);
	    		taskDetails.setEnd_date(taskEndDate);	
	    		taskMapper = getTaskDetails(taskDetailsRepository.save(taskDetails).getTask_id());
	    		
	    		}
		return taskMapper;
	}
	
	@Override
	public List<Map<String, Object>> calculateHoursAndDefinedHours(String startDate, String endDate, String userId) {
		EmployeeDetails emDetails = employeeRepository.getEmployeeByUserId(userId);
		  List<Map<String, Object>> result = new ArrayList<>();

		  Date end_date = null;
	        Date start_date = null;
	        try {
	            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
	            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        List<TaskDetails> tasks = taskDetailsRepository.getTaskListsByUserIdAndStartdateAndEndDateAndLiveInd(userId, start_date, end_date);
//	        System.out.println("tasks=="+tasks.toString());

	      Map<String, Double> totalHoursByTaskType = new HashMap<>();
	      for (TaskDetails task : tasks) {
	          String taskType = task.getTask_type();
//	          System.out.println("task Id=="+task.getTask_id());
	          double hours = calculateHours(task.getStart_date(), task.getEnd_date());
	          totalHoursByTaskType.put(taskType, totalHoursByTaskType.getOrDefault(taskType, 0.0) + hours);
	      }

	      for (String taskType : totalHoursByTaskType.keySet()) {
	          double totalHours = totalHoursByTaskType.get(taskType);
	          double definedHours = getDefinedHours(emDetails.getDepartment(),emDetails.getRoleType(),emDetails.getOrgId() ,taskType);
	          String taskTypeName = getTaskTypeName(taskType); // Fetch TaskTypeName
	            Map<String, Object> taskMap = new HashMap<>();
	            taskMap.put("typeName", taskTypeName);
	            taskMap.put("calculateHour", totalHours);
	            taskMap.put("definedHour", definedHours);
	            result.add(taskMap);
	      }

	      return result;
	  }
	
	private String getTaskTypeName(String taskTypeId) {
        TaskType taskType = taskTypeRepository.findByTaskTypeIdAndLiveInd(taskTypeId,true);
        return taskType != null ? taskType.getTaskType() : "***";
    }
	
	private double calculateHours(Date startDate, Date endDate) {
	    long diffInMilliseconds = Math.abs(endDate.getTime() - startDate.getTime());
	    long hours = TimeUnit.MILLISECONDS.toHours(diffInMilliseconds);
	    return hours;
	}


	  private double getDefinedHours(String department, String roleType,String orgId, String taskType) {
//	      TaskType type = taskTypeRepository.findByTaskType(taskType);
//	      if (type == null) {
//	          return 0;
//	      }
	      Development development = developmentRepository.findByDepartmentIdAndRoletypeIdAndTaskTypeIdAndOrgIdAndLiveInd(department,roleType,taskType,orgId,true);
	      if (development != null && development.getDevelopmentType().equals("percentage")) {
	          return development.getValue() * 40 / 100; // Assuming 40 hours per week
	      } else if (development != null) {
	          return development.getValue(); // Assuming value is in hours
	      } else {
	          return 0; // If taskTypeName is not found in Development table
	      }
	  }

	@Override
	public List<TaskViewMapper> getTaskListByUserIdWithYearAndQuarter(String userId) {
		List<TaskViewMapper> resultList = new ArrayList<>();
		 Date todayDate = new Date();
//		 Calendar calendar = Calendar.getInstance();
//		 calendar.set(2024, Calendar.JUNE, 15);
//	        Date todayDate = calendar.getTime();
		 System.out.println("todayDate"+todayDate);
		 Date end_date = null;
		    Date start_date = null;
		    
		    int quarter = Utility.getQuarterNumber(todayDate);
		    System.out.println("quarter"+quarter);
		    int year = Utility.getYearFromDate(todayDate);
			 System.out.println("year===" + year);
		    try {
				 
					 end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getPlusDate(Utility.getQuarterEndDate(year,quarter),21)));
					 start_date = Utility.removeTime(Utility.getQuarterStartDate(year,quarter));
					 System.out.println("end_date===" + end_date);
					 System.out.println("start_date===" + start_date);
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    String typeName = "Sales Plan";

	    TaskType taskType = taskTypeRepository.findByTaskType(typeName);
	    if(null!= taskType) {

	        List<EmployeeTaskLink> list = employeeTaskRepository.getByEmployeeId(userId);
	        for (EmployeeTaskLink employeeTaskLink : list) {

	            TaskDetails task = taskDetailsRepository.
	            		getTaskListByTaskTypeIdAndStartdateAndEndDateQuarterWise(taskType.getTaskTypeId(),employeeTaskLink.getTask_id(), start_date, end_date);

	            if (null != task) {
	                TaskViewMapper taskViewMapper = getTaskDetails(task.getTask_id());
	                if(null!=taskViewMapper) {
	                resultList.add(taskViewMapper);
	                }
	            }

	        }

	    }
		return resultList;
	}

	@Override
	public List<TaskViewMapper> getTaskListsByTaskTypeIdAndUserIdAndStartdateAndEndDate(String userId, String startDate,
			String endDate, String type) {
		Date end_date = null;
	    Date start_date = null;
	    try {
	        end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
	        start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }	
	    List<TaskViewMapper> resultList = new ArrayList<>();
	        List<EmployeeTaskLink> list2 = employeeTaskRepository.getTaskListByEmpId(userId);
	        for (EmployeeTaskLink employeeTaskLink : list2) {
				
	        		            TaskDetails task = taskDetailsRepository.
	            		getTaskListByTaskTypeIdAndStartdateAndEndDate(type,employeeTaskLink.getTask_id(), start_date, end_date);
	            if (null != task) {
	                TaskViewMapper taskViewMapper = getTaskDetails(task.getTask_id());
	                resultList.add(taskViewMapper);
	            }

	        }
	    return resultList;      
	}
	
	@Override
	public List<TaskViewMapper> getTaskDetailsByRoomId(String roomId, int pageNo) {
        Pageable page = PageRequest.of(pageNo, 20, Sort.by("creationDate").descending());
        Page<RoomTaskLink> list = roomTaskRepo.getTaskListByRoomId(roomId,page);
        List<TaskViewMapper> mappList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {

        	list.stream().map(li ->{ 
            	
            	TaskViewMapper mapper =	getTaskDetails(li.getTaskId());
            	 if(null!=mapper.getTaskId()) {
            		 mapper.setPageCount(list.getTotalPages());
            		 mapper.setDataCount(list.getSize());
                     mapper.setListCount(list.getTotalElements());
            		 mappList.add(mapper);
            	 }
            	return mapper;
            }).collect(Collectors.toList());
        }

        return mappList;
    }

	@Override
	public List<TaskViewMapper> getTaskDetailsByNameByOrgId(String name, String orgId) {
		List<TaskViewMapper> mapperList = new ArrayList<>();

		TaskType taskType = taskTypeRepository.getByTaskTypeAndOrgId(name, orgId);
		if (taskType != null) {
			List<TaskDetails> list = taskDetailsRepository
					.getByTaskTypeAndOrgId(taskType.getTaskTypeId(), orgId);
			if (list != null && !list.isEmpty()) {
				mapperList = list.stream().map(li -> getTaskDetails(li.getTask_id())).collect(Collectors.toList());
			}
		}

		return mapperList;
	}

	@Override
	public List<TaskViewMapper> getTaskDetailsByNameForTeam(String name, String userId, String orgId) {

		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);
	
		List<TaskViewMapper> mapperList = new ArrayList<>();

		TaskType taskType = taskTypeRepository.getByTaskTypeAndOrgId(name, orgId);
		if (taskType != null) {
			List<TaskDetails> list = taskDetailsRepository
					.getByTaskTypeAndUserIds(taskType.getTaskTypeId(), userIds);
			if (list != null && !list.isEmpty()) {
				mapperList = list.stream().map(li -> getTaskDetails(li.getTask_id())).collect(Collectors.toList());
			}
		}

		return mapperList;
	}

	@Override
	public List<TaskViewMapper> getTaskDetailsByNameByUserId(String name, String userId, String orgId) {
		List<TaskViewMapper> mapperList = new ArrayList<>();

		TaskType taskType = taskTypeRepository.getByTaskTypeAndOrgId(name, orgId);
		if (taskType != null) {
			List<TaskDetails> list = taskDetailsRepository
					.getByTaskTypeAndLiveIndAndUserId(taskType.getTaskTypeId(), userId);
			if (list != null && !list.isEmpty()) {
				mapperList = list.stream().map(li -> getTaskDetails(li.getTask_id())).collect(Collectors.toList());
			}
		}

		return mapperList;
	}
	
//	@Scheduled(fixedRate = 60000) 
//    public void sendTaskNotifications() {
//        
//		Date endDate = Utility.removeTime(new Date());
//		long endTime = Utility.getTime() +1;
////        List<TaskDetails> tasksEndingSoon = taskDetailsRepository.getEndingTasks(endDate, endTime);
////
////        for (TaskDetails task : tasksEndingSoon) {
//            String message = "Task '"  + "' is ending in 1 hour. Please complete it.";
//            System.out.println("message==="+message);
////        }
//    }
}

