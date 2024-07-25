package com.app.employeePortal.candidate.service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
import com.app.employeePortal.Opportunity.repository.OpportunityDetailsRepository;
import com.app.employeePortal.address.entity.AddressDetails;
import com.app.employeePortal.address.entity.AddressInfo;
import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.address.repository.AddressInfoRepository;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.call.entity.CallCandidateLink;
import com.app.employeePortal.call.entity.CallDetails;
import com.app.employeePortal.call.repository.CallCandidateLinkRepository;
import com.app.employeePortal.call.repository.CallDetailsRepository;
import com.app.employeePortal.call.service.CallService;
import com.app.employeePortal.candidate.convertor.RecruitmentProfileDetailsConvertor;
import com.app.employeePortal.candidate.entity.CandidateAddressLink;
import com.app.employeePortal.candidate.entity.CandidateBankDetails;
import com.app.employeePortal.candidate.entity.CandidateCertificationLink;
import com.app.employeePortal.candidate.entity.CandidateDetails;
import com.app.employeePortal.candidate.entity.CandidateDocumentLink;
import com.app.employeePortal.candidate.entity.CandidateEducationDetails;
import com.app.employeePortal.candidate.entity.CandidateEmploymentHistory;
import com.app.employeePortal.candidate.entity.CandidateInfo;
import com.app.employeePortal.candidate.entity.CandidateNotesLink;
import com.app.employeePortal.candidate.entity.CandidateTraining;
import com.app.employeePortal.candidate.entity.CandidateVideoLink;
import com.app.employeePortal.candidate.entity.DefinationDetails;
import com.app.employeePortal.candidate.entity.DefinationDetailsDelete;
import com.app.employeePortal.candidate.entity.DefinationInfo;
import com.app.employeePortal.candidate.entity.FilterDetails;
import com.app.employeePortal.candidate.entity.SkillCandidateNo;
import com.app.employeePortal.candidate.entity.SkillSetDetails;
import com.app.employeePortal.candidate.mapper.ActivityMapper;
import com.app.employeePortal.candidate.mapper.CandidateBankDetailsMapper;
import com.app.employeePortal.candidate.mapper.CandidateCertificationLinkMapper;
import com.app.employeePortal.candidate.mapper.CandidateCommonMapper;
import com.app.employeePortal.candidate.mapper.CandidateDocumentMapper;
import com.app.employeePortal.candidate.mapper.CandidateDropDownMapper;
import com.app.employeePortal.candidate.mapper.CandidateEducationDetailsMapper;
import com.app.employeePortal.candidate.mapper.CandidateEmailRequestMapper;
import com.app.employeePortal.candidate.mapper.CandidateEmailResponseMapper;
import com.app.employeePortal.candidate.mapper.CandidateEmploymentHistoryMapper;
import com.app.employeePortal.candidate.mapper.CandidateMapper;
import com.app.employeePortal.candidate.mapper.CandidateRoleCountMapper;
import com.app.employeePortal.candidate.mapper.CandidateTrainingMapper;
import com.app.employeePortal.candidate.mapper.CandidateTreeMapper;
import com.app.employeePortal.candidate.mapper.CandidateViewMapper;
import com.app.employeePortal.candidate.mapper.CandidateWebsiteMapper;
import com.app.employeePortal.candidate.mapper.DefinationMapper;
import com.app.employeePortal.candidate.mapper.EducationDetailsTreeMapper;
import com.app.employeePortal.candidate.mapper.FilterMapper;
import com.app.employeePortal.candidate.mapper.SkillCandidateNoMapper;
import com.app.employeePortal.candidate.mapper.SkillSetMapper;
import com.app.employeePortal.candidate.repository.CandidateAddressLinkRepository;
import com.app.employeePortal.candidate.repository.CandidateBankDetailsRepository;
import com.app.employeePortal.candidate.repository.CandidateCertificationLinkRepository;
import com.app.employeePortal.candidate.repository.CandidateDetailsRepository;
import com.app.employeePortal.candidate.repository.CandidateDocumentLinkRepository;
import com.app.employeePortal.candidate.repository.CandidateEducationDetailsRepository;
import com.app.employeePortal.candidate.repository.CandidateEmploymentHistoryRepository;
import com.app.employeePortal.candidate.repository.CandidateInfoRepository;
import com.app.employeePortal.candidate.repository.CandidateNotesLinkRepository;
import com.app.employeePortal.candidate.repository.CandidateTrainingRepository;
import com.app.employeePortal.candidate.repository.CandidateVideoLinkRepository;
import com.app.employeePortal.candidate.repository.DefinationDeleteRepository;
import com.app.employeePortal.candidate.repository.DefinationInfoRepository;
import com.app.employeePortal.candidate.repository.DefinationRepository;
import com.app.employeePortal.candidate.repository.FilterDetailsRepository;
import com.app.employeePortal.candidate.repository.SkillCandidateNoRepository;
import com.app.employeePortal.candidate.repository.SkillSetRepository;
import com.app.employeePortal.category.entity.CertificationLibrary;
import com.app.employeePortal.category.entity.RoleType;
import com.app.employeePortal.category.repository.CertificationLibraryRepository;
import com.app.employeePortal.category.repository.RoleTypeRepository;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.entity.DocumentType;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.document.repository.DocumentDetailsRepository;
import com.app.employeePortal.document.repository.DocumentTypeRepository;
import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.education.entity.EducationType;
import com.app.employeePortal.education.repository.EducationTypeRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.Notes;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.NotesRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.entity.CandidateEventLink;
import com.app.employeePortal.event.entity.EventDetails;
import com.app.employeePortal.event.entity.EventType;
import com.app.employeePortal.event.repository.CandidateEventLinkRepository;
import com.app.employeePortal.event.repository.EventDetailsRepository;
import com.app.employeePortal.event.repository.EventTypeRepository;
import com.app.employeePortal.expense.entity.ExpenseDetails;
import com.app.employeePortal.expense.repository.ExpenseRepository;
import com.app.employeePortal.investor.entity.Investor;
import com.app.employeePortal.investor.entity.InvestorOpportunity;
import com.app.employeePortal.investor.repository.InvestorContactLinkRepo;
import com.app.employeePortal.investor.repository.InvestorOpportunityRepo;
import com.app.employeePortal.investor.repository.InvestorRepository;
import com.app.employeePortal.investorleads.entity.InvestorLeads;
import com.app.employeePortal.investorleads.repository.InvestorLeadsRepository;
import com.app.employeePortal.leads.entity.Leads;
import com.app.employeePortal.leads.repository.LeadsRepository;
import com.app.employeePortal.leave.entity.LeaveDetails;
import com.app.employeePortal.leave.repository.LeaveDetailsRepository;
import com.app.employeePortal.location.entity.LocationDetails;
import com.app.employeePortal.location.repository.LocationDetailsRepository;
import com.app.employeePortal.mileage.entity.MileageDetails;
import com.app.employeePortal.mileage.repository.MileageRepository;
import com.app.employeePortal.notification.entity.NotificationDetails;
import com.app.employeePortal.notification.repository.NotificationConfigRepository;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.notification.repository.NotificationRuleRepository;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.partner.entity.PartnerCandidateLink;
import com.app.employeePortal.partner.entity.PartnerDetails;
import com.app.employeePortal.partner.repository.PartnerCandidateLinkRepository;
import com.app.employeePortal.partner.repository.PartnerDetailsRepository;
import com.app.employeePortal.permission.entity.Permission;
import com.app.employeePortal.permission.repository.PermissionRepository;
import com.app.employeePortal.project.Entity.ProjectDetails;
import com.app.employeePortal.project.Repository.ProjectRepository;
import com.app.employeePortal.recruitment.entity.OpportunityRecruitDetails;
import com.app.employeePortal.recruitment.entity.RecruitProfileLinkDetails;
import com.app.employeePortal.recruitment.entity.RecruitProfileStatusLink;
import com.app.employeePortal.recruitment.entity.RecruitStageNoteLink;
import com.app.employeePortal.recruitment.entity.RecruitmentAddressLink;
import com.app.employeePortal.recruitment.entity.RecruitmentCandidateLink;
import com.app.employeePortal.recruitment.entity.RecruitmentProfileInfo;
import com.app.employeePortal.recruitment.entity.RecruitmentPublishDetails;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.RecruitStageNotelinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentAddressLinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentCandidateLinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentOpportunityDetailsRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProfileDetailsRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProfileInfoRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProfileStatusLinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentPublishRepository;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;
import com.app.employeePortal.recruitment.service.RecruitmentService;
import com.app.employeePortal.registration.entity.Country;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.entity.Designation;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.registration.repository.DesignationRepository;
import com.app.employeePortal.registration.repository.UserSettingsRepository;
import com.app.employeePortal.sector.entity.SectorDetails;
import com.app.employeePortal.sector.repository.SectorDetailsRepository;
import com.app.employeePortal.task.entity.CandidateTaskLink;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.entity.TaskType;
import com.app.employeePortal.task.repository.CandidateTaskLinkRepository;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskTypeRepository;
import com.app.employeePortal.util.Utility;
import com.app.employeePortal.videoClips.entity.VideoClipsDetails;
import com.app.employeePortal.videoClips.mapper.VideoClipsMapper;
import com.app.employeePortal.videoClips.repository.VideoClipsDetailsRepository;
import com.app.employeePortal.videoClips.repository.VideoClipsTypeRepository;

@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	CandidateDetailsRepository candidateDetailsRepository;

	@Autowired
	SkillSetRepository skillSetRepository;

	@Autowired
	CandidateTrainingRepository candidateTrainingRepository;

	@Autowired
	CandidateDocumentLinkRepository candidateDocumentLinkRepository;

	@Autowired
	NotesRepository notesRepository;

	@Autowired
	CandidateInfoRepository candidateInfoRepository;

	@Autowired
	CandidateNotesLinkRepository candidateNotesLinkRepository;

	@Autowired
	DocumentDetailsRepository documentDetailsRepository;

	@Autowired
	ContactService contactService;

	@Autowired
	DocumentService documentService;

	@Autowired
	AddressInfoRepository addressInfoRepository;

	@Autowired
	AddressRepository addressRepository;
	@Autowired
	CandidateAddressLinkRepository candidateAddressLinkRepository;
	@Autowired
	CallCandidateLinkRepository callCandidateLinkRepository;
	@Autowired
	DesignationRepository designationRepository;
	@Autowired
	EducationTypeRepository educationTypeRepository;
	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	DefinationRepository definationRepository;

	@Autowired
	CandidateEducationDetailsRepository candidateEducationDetailsRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	CandidateBankDetailsRepository candidateBankDetailsRepository;
	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	PermissionRepository permissionRepository;

	@Autowired
	CandidateService candidateService;
	@Autowired
	PartnerDetailsRepository partnerDetailsRepository;
	@Autowired
	CandidateTaskLinkRepository candidateTaskLinkRepository;
	@Autowired
	CandidateEventLinkRepository candidateEventLinkRepository;
	@Autowired
	TaskDetailsRepository taskDetailsRepository;
	@Autowired
	EventDetailsRepository eventDetailsRepository;
	@Autowired
	TaskTypeRepository taskTypeRepository;
	@Autowired
	EventTypeRepository eventTypeRepository;

	@Autowired
	DefinationInfoRepository definationInfoRepository;

	@Autowired
	PartnerCandidateLinkRepository partnerCandidateLinkRepository;
	@Autowired
	WebsiteRepository websiteRepository;
	@Autowired
	FilterDetailsRepository filterDetailsRepository;
	@Autowired
	VideoClipsDetailsRepository videoClipsDetailsRepository;
	@Autowired
	CandidateVideoLinkRepository candidateVideoLinkRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	DefinationDeleteRepository definationDeleteRepository;
	@Autowired
	RecruitmentPublishRepository recruitmentPublishRepository;
	@Autowired
	RecruitmentService recruitmentService;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	LeadsRepository leadsRepository;
	@Autowired
	MileageRepository mileageRepository;
	@Autowired
	RecruitmentProfileInfoRepository recruitmentProfileInfoRepository;
	@Autowired
	 RecruitStageNotelinkRepository recruitStageNotelinkRepository;
	private String[] candidate_headings = { "candidate Id", "First Name", "Middle Name", "Last Name", "Mobile#",
			"phone#", "Email", "LinkedIn", "Notes", "Rate", "Availability", "Skills" };

	@Autowired
	CandidateEmploymentHistoryRepository candidateEmploymentHistoryRepository;
	@Autowired
	CallDetailsRepository callDetailsRepository;
	@Autowired
	DocumentTypeRepository documentTypeRepository;
	@Autowired
	RoleTypeRepository roleTypeRepository;
	@Autowired
	RecruitmentOpportunityDetailsRepository recruitmentOpportunityDetailsRepository;
	@Autowired
	RecruitmentCandidateLinkRepository recruitmentCandidateLinkRepository;
	@Autowired
	CallService callService;
	@Autowired
	RecruitmentAddressLinkRepository recruitmentAddressLinkRepository;
	@Autowired
	VideoClipsTypeRepository videoClipsTypeRepository;
	@Autowired
	OpportunityDetailsRepository opportunityDetailsRepository;
	@Autowired
	CertificationLibraryRepository certificationLibraryRepository;
	@Autowired
	CandidateCertificationLinkRepository candidateCertificationLinkRepository;
	@Autowired
	RecruitmentProfileDetailsRepository recruitmentProfileDetailsRepository;
	@Autowired
	RecruitmentProfileStatusLinkRepository recruitmentProfileStatusLinkRepository;
	@Autowired
	SkillCandidateNoRepository skillCandidateNoRepository;
	@Autowired
	UserSettingsRepository userSettingsRepository;
	@Autowired
	SectorDetailsRepository sectorDetailsRepository;
	@Autowired 
	EmployeeService employeeService;
	@Autowired
	InvestorRepository investorRepo;
	@Autowired
	InvestorLeadsRepository investorLeadsRepository;
	@Autowired
	InvestorOpportunityRepo investorOpportunityRepo;
	@Autowired
	InvestorContactLinkRepo investorContactLinkRepo;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	LeaveDetailsRepository leaveDetailsRepository;
	@Autowired
	ExpenseRepository expenseRepository;
	@Autowired
	LocationDetailsRepository locationDetailsRepository;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	NotificationConfigRepository notificationConfigRepository;
	@Autowired
	NotificationRuleRepository notificationRuleRepository;
	@Autowired
	NotificationService notificationService;
	
	
	@Override
	public CandidateViewMapper saveCandidate(CandidateMapper candidateMapper) {

		CandidateViewMapper candidateMapperr = new CandidateViewMapper();
		String candidateId = null;
		String documentTypeId = null;

//		if (candidateMapper != null) {
//
//			if (candidateMapper.getEmailId() != null) {
//
//				CandidateDetails candidatee = candidateDetailsRepository.findByEmail(candidateMapper.getEmailId());
//
//				if (candidatee != null) {
//					return "Candidate can not be created as same emailId already exist";
//
//				} else {

		CandidateInfo candidateInfo = new CandidateInfo();

		candidateInfo.setCreationDate(new Date());

		CandidateInfo candidatInfo = candidateInfoRepository.save(candidateInfo);
		candidateId = candidatInfo.getCandidateId();

		CandidateDetails candidate = new CandidateDetails();
		candidate.setCandidateId(candidateId);
		setPropertyOnInput(candidateMapper, candidate, candidateId);

		CandidateDetails candidatedb = candidateDetailsRepository.save(candidate);

		/* insert to notification table */

		NotificationDetails notification = new NotificationDetails();
		String middleName2 =" ";
		String lastName2 ="";
		String satutation1 ="";

		if(!StringUtils.isEmpty(candidateMapper.getLastName())) {
			 
			lastName2 = candidateMapper.getLastName();
		 }
		 if(candidateMapper.getSalutation() != null && candidateMapper.getSalutation().length()>0) {
			 satutation1 = candidateMapper.getSalutation();
		 }


		 if(candidateMapper.getMiddleName() != null && candidateMapper.getMiddleName().length()>0) {

		 
			 middleName2 = candidateMapper.getMiddleName();
			 notification.setMessage(satutation1+" "+candidateMapper.getFirstName()+" "+middleName2+" "+lastName2+ " Created as a Candidate.");
		 }else {
			 
			 notification.setMessage(satutation1+" "+candidateMapper.getFirstName()+" "+lastName2+ " Created as a Candidate.");
		 }
		
		 notification.setAssignedTo(candidateMapper.getUserId());
		 
		notification.setMessageReadInd(false);
		notification.setNotificationDate(new Date());
		notification.setNotificationType("Candidate Creation");
		notification.setUser_id(candidateMapper.getUserId());
		
		
		
		notificationRepository.save(notification);

		/*
		 * List<String> skillList= candidateMapper.getSkills(); if(null!=skillList &&
		 * !skillList.isEmpty()) { for (String skillName : skillList) {
		 * System.out.println("skill>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+skillName); boolean
		 * b =checkSkillInSkillLibery(skillName);
		 * System.out.println("boolean>>>>>>>>>"+b); if(b==false){
		 * 
		 * DefinationInfo definationInfo = new DefinationInfo();
		 * 
		 * definationInfo.setCreation_date(new Date()); String id
		 * =definationInfoRepository.save(definationInfo).getDefination_info_id();
		 * 
		 * DefinationDetails newDefinationDetails = new DefinationDetails();
		 * newDefinationDetails.setDefination_id(id);
		 * newDefinationDetails.setName(skillName);
		 * newDefinationDetails.setOrg_id(candidateMapper.getOrganizationId());
		 * newDefinationDetails.setUser_id(candidateMapper.getUserId());
		 * newDefinationDetails.setCreation_date(new Date());
		 * newDefinationDetails.setLive_ind(true);
		 * definationRepository.save(newDefinationDetails);
		 * 
		 * } } }
		 */
		if (!StringUtils.isEmpty(candidateMapper.getDocumentId())) {
			DocumentDetails documentDetails = documentDetailsRepository
					.getDocumentDetailsById(candidateMapper.getDocumentId());
			if (null != documentDetails) {
				DocumentType documentType = documentTypeRepository.getDocumentTypeByName("Resume");
				if (null != documentType) {
					documentDetails.setDocument_type(documentType.getDocument_type_id());
				} else {
					DocumentType newDocumentType = new DocumentType();
					newDocumentType.setDocumentTypeName("Resume");
					newDocumentType.setCreator_id(candidateMapper.getUserId());
					newDocumentType.setOrgId(candidateMapper.getOrganizationId());
					newDocumentType.setCreation_date(new Date());
					newDocumentType.setLive_ind(true);
					documentTypeId = documentTypeRepository.save(newDocumentType).getDocument_type_id();
					documentDetails.setDocument_type(documentTypeId);
				}
				documentDetails.setDocument_title("Resume Upload");
				documentDetails.setDoc_description(candidatedb.getFullName() + " Resume Uploaded");
				documentDetails.setDocument_id(documentDetails.getDocument_id());
				documentDetails.setCreation_date(new Date());
				// documentDetails.setDocumentType(documentMapper.getDocumentTypeId());

				documentDetailsRepository.save(documentDetails);
			}
			/* insert candidateDocument link table */

			CandidateDocumentLink candidateDocumentLink = new CandidateDocumentLink();
			candidateDocumentLink.setCandidateId(candidateId);
			candidateDocumentLink.setDocumentId(candidateMapper.getDocumentId());
			candidateDocumentLink.setCreationDate(new Date());
			candidateDocumentLinkRepository.save(candidateDocumentLink);
		}

		/* Upload video profile */

		if (!StringUtils.isEmpty(candidateMapper.getVideoClipsId())) {
			VideoClipsDetails VideoClipsDetails = videoClipsDetailsRepository
					.getVideoClipsDetailsById(candidateMapper.getVideoClipsId());
			if (null != VideoClipsDetails) {
				VideoClipsDetails.setVideoClipsTitle("Profile Video");
				VideoClipsDetails.setVideoDescription(candidatedb.getFullName() + " Video Uploaded");
				videoClipsDetailsRepository.save(VideoClipsDetails);
			}
			CandidateVideoLink candidateVideoLink = new CandidateVideoLink();
			candidateVideoLink.setCandidateId(candidateId);
			candidateVideoLink.setVideoClipsId(candidateMapper.getVideoClipsId());
			candidateVideoLink.setCreationDate(new Date());
			candidateVideoLinkRepository.save(candidateVideoLink);
		}
		/* insert candidateId link table */

		PartnerCandidateLink partnerCandidateLink = new PartnerCandidateLink();
		partnerCandidateLink.setPartnerId(candidateMapper.getPartnerId());
		partnerCandidateLink.setCandidateId(candidateMapper.getCandidateId());
		partnerCandidateLink.setCreationDate(new Date());

		candidateMapperr = getCandidateDetailsById(candidateId);

		return candidateMapperr;
	}


	private void setPropertyOnInput(CandidateMapper candidateMapperr, CandidateDetails candidate, String candidateId) {

		candidate.setFirstName(candidateMapperr.getFirstName());
		candidate.setLastName(candidateMapperr.getLastName());
		candidate.setEmailId(candidateMapperr.getEmailId());
		candidate.setMobileNumber(candidateMapperr.getMobileNumber());
		candidate.setMiddleName(candidateMapperr.getMiddleName());
		candidate.setLinkedin(candidateMapperr.getLinkedin());
		candidate.setLinkedinPublicUrl(candidateMapperr.getLinkedin_public_url());
		candidate.setNotes(candidateMapperr.getNotes());
		candidate.setPhoneNumber(candidateMapperr.getPhoneNumber());
		candidate.setCurrentCtc(candidateMapperr.getCurrentCtc());
		candidate.setSalutation(candidateMapperr.getSalutation());
		candidate.setActive(candidateMapperr.isActive());
		candidate.setCategory(candidateMapperr.getCategory());
		candidate.setBenifit(candidateMapperr.getBenifit());
		candidate.setCostType(candidateMapperr.getCostType());
		candidate.setNoticeDetail(candidateMapperr.getNoticeDetail());
		candidate.setWhatsApp(candidateMapperr.getWhatsApp());
		candidate.setWorkLocation(candidateMapperr.getWorkLocation());
		candidate.setWorkPreferance(candidateMapperr.getWorkPreference());
		candidate.setCurrentCtcCurency(candidateMapperr.getCurrentCtcCurency());
		candidate.setPartnerContact(candidateMapperr.getPartnerContact());
		candidate.setChannel(candidateMapperr.getChannel());
		candidate.setCandiProcessInd(false);
		candidate.setTAndCInd(candidateMapperr.isTAndCInd());
		candidate.setPreferredDistance(200);

		if (null != candidateMapperr.getAvailableDate()) {
			try {
				candidate.setAvailableDate(
						Utility.removeTime(Utility.getDateFromISOString(candidateMapperr.getAvailableDate())));
				System.out.println("getDateFromISOString........." + candidateMapperr.getAvailableDate());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * if(null!=callDTO.getStartDate()) { try {
		 * call.setStartDate(UtilService.getDateFromISOString(callDTO.getStartDate()));
		 * } catch (Exception e) { e.printStackTrace(); } }
		 */
		candidate.setCountryDialcode(candidateMapperr.getCountryDialCode());
		candidate.setCountryDialcode1(candidateMapperr.getCountryDialCode1());
		candidate.setCurrency(candidateMapperr.getCurrency());
		candidate.setDepartment(candidateMapperr.getDepartmentId());
		candidate.setDesignation(candidateMapperr.getDesignationTypeId());
		candidate.setRoleType(candidateMapperr.getRoleTypeId());
		candidate.setSector(candidateMapperr.getSectorId());
		candidate.setTagWithCompany(candidateMapperr.getTag_with_company());
		candidate.setBilling(candidateMapperr.getBilling());

		candidate.setGender(candidateMapperr.getGender());
		candidate.setDateOfBirth(candidateMapperr.getDateOfBirth());
		candidate.setNationality(candidateMapperr.getNationality());
		candidate.setIdProof(candidateMapperr.getIdProof());
		candidate.setIdNumber(candidateMapperr.getIdNumber());
		candidate.setEducatioin(candidateMapperr.getEducation());
		candidate.setExperience(candidateMapperr.getExperience());
		candidate.setImageId(candidateMapperr.getImageId());

		candidate.setLanguage(candidateMapperr.getLanguage());
		candidate.setWorkLocation(candidateMapperr.getWorkLocation());
		candidate.setWorkType(candidateMapperr.getWorkType());
		candidate.setCountry(candidateMapperr.getCountry());
		candidate.setUserId(candidateMapperr.getUserId());
		candidate.setOrganizationId(candidateMapperr.getOrganizationId());
		candidate.setPartnerId(candidateMapperr.getPartnerId());
		candidate.setNoticePeriod(candidateMapperr.getNoticePeriod());
		candidate.setAllowSharing(candidateMapperr.getAllowSharing());
		candidate.setEmpInd(candidateMapperr.isEmpInd());

		candidate.setCreationDate(new Date());

		List<String> skillList = candidateMapperr.getSkills();
		if (null != skillList && !skillList.isEmpty()) {
			for (String skillName : skillList) {
				SkillSetDetails skillSetDetails1 = new SkillSetDetails();
				skillSetDetails1.setSkillName(skillName);
				skillSetDetails1.setCandidateId(candidateId);
				skillSetDetails1.setCreationDate(new Date());
				skillSetRepository.save(skillSetDetails1);

				SkillCandidateNo SkillCandidateNo = skillCandidateNoRepository.findBySkill(skillName);
				if(null!=SkillCandidateNo) {
					int no = SkillCandidateNo.getNumber();
					SkillCandidateNo.setNumber(no+1);
					skillCandidateNoRepository.save(SkillCandidateNo);
				}
				
			}
		}
		candidate.setLiveInd(true);
		candidate.setBlockListInd(false);
		candidate.setCandidateId(candidateId);
		String middleName = " ";
		String lastName = "";

		if (!StringUtils.isEmpty(candidateMapperr.getLastName())) {

			lastName = candidateMapperr.getLastName();
		}

		if (candidateMapperr.getMiddleName() != null && candidateMapperr.getMiddleName().length() > 0) {

			middleName = candidateMapperr.getMiddleName();
			candidate.setFullName(candidateMapperr.getFirstName() + " " + middleName + " " + lastName);
		} else {

			candidate.setFullName(candidateMapperr.getFirstName() + " " + lastName);
		}
		if(null!=candidateMapperr.getAddress() && !candidateMapperr.getAddress().isEmpty()) {
		if (candidateMapperr.getAddress().size() > 0) {
			for (AddressMapper addressMapper : candidateMapperr.getAddress()) {
				/* insert to address info & address deatils & customeraddressLink */

				AddressInfo addressInfo = new AddressInfo();
				addressInfo.setCreationDate(new Date());
				// addressInfo.setCreatorId(candidateMapperr.getUserId());
				AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

				String addressId = addressInfoo.getId();

				if (null != addressId) {

					AddressDetails addressDetails = new AddressDetails();
					addressDetails.setAddressId(addressId);
					addressDetails.setAddressLine1(addressMapper.getAddress1());
					addressDetails.setAddressLine2(addressMapper.getAddress2());
					addressDetails.setAddressType(addressMapper.getAddressType());
					addressDetails.setCountry(addressMapper.getCountry());
					addressDetails.setCreationDate(new Date());
					addressDetails.setStreet(addressMapper.getStreet());
					addressDetails.setCity(addressMapper.getCity());
					addressDetails.setPostalCode(addressMapper.getPostalCode());
					addressDetails.setTown(addressMapper.getTown());
					addressDetails.setState(addressMapper.getState());
					addressDetails.setLatitude(addressMapper.getLatitude());
					addressDetails.setLongitude(addressMapper.getLongitude());
					addressDetails.setLiveInd(true);
					addressDetails.setHouseNo(addressMapper.getHouseNo());
					addressRepository.save(addressDetails);

					CandidateAddressLink candidateAddressLink = new CandidateAddressLink();
					candidateAddressLink.setCandidateId(candidateId);
					candidateAddressLink.setAddressId(addressId);
					candidateAddressLink.setCreationDate(new Date());

					candidateAddressLinkRepository.save(candidateAddressLink);

				}
			}
		}
	}
		candidateDetailsRepository.save(candidate);

		Notes notes = new Notes();
		notes.setCreation_date(new Date());
		notes.setNotes(candidateMapperr.getNotes());
		notes.setLiveInd(true);
		Notes note = notesRepository.save(notes);

		String notesId = note.getNotes_id();

		CandidateNotesLink notesLink = new CandidateNotesLink();
		notesLink.setCandidate_id(candidateId);
		notesLink.setNotesId(notesId);
		notesLink.setCreation_date(new Date());

		/* insert to Notification Table */
		String msg = "A Candidate is created By ";
		notificationService.createNotification(candidateMapperr.getUserId(),"candidate create", msg,"Candidate","Create");	
	}

	@Override
	public CandidateViewMapper getCandidateDetailsById(String candidateId) {
		CandidateDetails candidateDetails = candidateDetailsRepository.getcandidateDetailsById(candidateId);
		// CandidateNotesLink candidatenotesLink =
		// candidateNotesLinkRepository.getnotesLinkById(candidateId);
		List<CandidateAddressLink> candidateAddressLinkList = candidateAddressLinkRepository
				.getAddressListByCandidateId(candidateId);
		List<AddressMapper> addressList = new ArrayList<AddressMapper>();
		//List<SkillSetMapper> skillList = new ArrayList<SkillSetMapper>();
		CandidateViewMapper candidateMapper = new CandidateViewMapper();

		if (null != candidateDetails) {
			System.out.println("candidate id......" + candidateDetails.getCandidateId());
			candidateMapper.setCandidateId(candidateId);
			candidateMapper.setFirstName(candidateDetails.getFirstName());
			candidateMapper.setMiddleName(candidateDetails.getMiddleName());
			candidateMapper.setLastName(candidateDetails.getLastName());
			candidateMapper.setMobileNumber(candidateDetails.getMobileNumber());
			candidateMapper.setPhoneNumber(candidateDetails.getPhoneNumber());
			candidateMapper.setEmailId(candidateDetails.getEmailId());
			candidateMapper.setLinkedin(candidateDetails.getLinkedin());
			candidateMapper.setLinkedin_public_url(candidateDetails.getLinkedinPublicUrl());
			candidateMapper.setCountryDialCode(candidateDetails.getCountryDialcode());
			candidateMapper.setCountryDialCode1(candidateDetails.getCountryDialcode1());
			candidateMapper.setCurrency(candidateDetails.getCurrency());
			// candidateMapper.setDepartment(candidateDetails.getDepartment());
			candidateMapper.setTag_with_company(candidateDetails.getTagWithCompany());
			candidateMapper.setBilling(candidateDetails.getBilling());
			candidateMapper.setSalutation(candidateDetails.getSalutation());
			candidateMapper.setCountry(candidateDetails.getCountry());
			candidateMapper.setNotes(candidateDetails.getNotes());
			candidateMapper.setUserId(candidateDetails.getUserId());
			candidateMapper.setOrganizationId(candidateDetails.getOrganizationId());
			candidateMapper.setCreationDate(Utility.getISOFromDate(candidateDetails.getCreationDate()));
			candidateMapper.setCurrentCtc(candidateDetails.getCurrentCtc());
			candidateMapper.setModifiedAt(Utility.getISOFromDate(candidateDetails.getModifiedAt()));
			candidateMapper.setEmpInd(candidateDetails.isEmpInd());
			candidateMapper.setCurrentCtcCurency(candidateDetails.getCurrentCtcCurency());
			candidateMapper.setPartnerContact(candidateDetails.getPartnerContact());
			candidateMapper.setCandiProcessInd(candidateDetails.isCandiProcessInd());
			candidateMapper.setTAndCInd(candidateDetails.isTAndCInd());

			String middleName = " ";
			String lastName = "";

			if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

				lastName = candidateDetails.getLastName();
			}

			if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

				middleName = candidateDetails.getMiddleName();
				candidateMapper.setFullName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
			} else {

				candidateMapper.setFullName(candidateDetails.getFirstName() + " " + lastName);
			}

			candidateMapper.setGender(candidateDetails.getGender());
			candidateMapper.setDateOfBirth(candidateDetails.getDateOfBirth());
			candidateMapper.setNationality(candidateDetails.getNationality());
			candidateMapper.setIdProof(candidateDetails.getIdProof());
			candidateMapper.setIdNumber(candidateDetails.getIdNumber());
			candidateMapper.setEducation(candidateDetails.getEducatioin());
			candidateMapper
					.setExperience(getExprience(candidateDetails.getCreationDate()) + candidateDetails.getExperience());
			System.out.println("exprience::" + candidateDetails.getExperience());
			System.out.println("candidate exp>>" + getExprience(candidateDetails.getCreationDate())
					+ candidateDetails.getExperience());
			candidateMapper.setImageId(candidateDetails.getImageId());
			candidateMapper.setAvailableDate(Utility.getISOFromDate(candidateDetails.getAvailableDate()));
			// candidateMapper.setFullName(candidateDetails.getFullName());
			candidateMapper.setPartnerId(candidateDetails.getPartnerId());
			candidateMapper.setNoticePeriod(candidateDetails.getNoticePeriod());
			candidateMapper.setCategory(candidateDetails.getCategory());
			candidateMapper.setBenifit(candidateDetails.getBenifit());
			candidateMapper.setCostType(candidateDetails.getCostType());
			candidateMapper.setNoticeDetail(candidateDetails.getNoticeDetail());
			candidateMapper.setDepartmentId(candidateDetails.getDepartment());
			candidateMapper.setRoleTypeId(candidateDetails.getRoleType());
			candidateMapper.setWhatsApp(candidateDetails.getWhatsApp());
			candidateMapper.setWorkLocation(candidateDetails.getWorkLocation());
			candidateMapper.setWorkPreference(candidateDetails.getWorkPreferance());
			candidateMapper.setChannel(candidateDetails.getChannel());
			candidateMapper.setBlockListInd(candidateDetails.isBlockListInd());
			candidateMapper.setDoNotCallInd(candidateDetails.isDoNotCallInd());
			candidateMapper.setAllowSharing(candidateDetails.getAllowSharing());
			candidateMapper.setPreferredDistance(candidateDetails.getPreferredDistance());

			PartnerDetails partnerDetails = partnerDetailsRepository
					.getPartnerDetailsById(candidateDetails.getPartnerId());
			if (null != partnerDetails) {
				candidateMapper.setPartnerName(partnerDetails.getPartnerName());
			} else {
				candidateMapper.setPartnerName("");
				candidateMapper.setPartnerId("");
			}

			CandidateVideoLink candidateVideoLink = candidateVideoLinkRepository
					.getVidioDetailsByCandidateId(candidateId);
			if (null != candidateVideoLink) {
				candidateMapper.setVideoClipsId(candidateVideoLink.getVideoClipsId());
			}

			if (!StringUtils.isEmpty(candidateDetails.getDesignation())) {
				Designation designation = designationRepository
						.findByDesignationTypeId(candidateDetails.getDesignation());
				if (null != designation) {
					candidateMapper.setDesignationTypeId(designation.getDesignationTypeId());
					candidateMapper.setDesignation(designation.getDesignationType());
				} else {
					candidateMapper.setDesignation(candidateDetails.getDesignation());
				}
			}
			if (!StringUtils.isEmpty(candidateDetails.getDepartment())) {
				Department department = departmentRepository.getDepartmentDetails(candidateDetails.getDepartment());
				if (null != department) {
					candidateMapper.setDepartmentId(department.getDepartment_id());
					candidateMapper.setDepartmentName(department.getDepartmentName());
				}
			}
			if (!StringUtils.isEmpty(candidateDetails.getRoleType())) {
				RoleType roleType = roleTypeRepository.findByRoleTypeId(candidateDetails.getRoleType());
				if (null != roleType) {
					candidateMapper.setRoleTypeId(roleType.getRoleTypeId());
					candidateMapper.setRoleType(roleType.getRoleType());
				}
			}
			
			if (!StringUtils.isEmpty(candidateDetails.getSector())) {
				SectorDetails sector= sectorDetailsRepository.getSectorDetailsBySectorId(candidateDetails.getSector());;
				if (null != sector) {
					candidateMapper.setSectorId(sector.getSectorId());
					candidateMapper.setSector(sector.getSectorName());
				}
			}

			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeDetailsByEmployeeId(candidateDetails.getUserId());
			if (null != employeeDetails) {
				String OwnermiddleName = " ";
				String OwnerlastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					OwnerlastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					OwnermiddleName = employeeDetails.getMiddleName();
					candidateMapper
							.setOwnerName(employeeDetails.getFirstName() + " " + OwnermiddleName + " " + OwnerlastName);
				} else {

					candidateMapper.setOwnerName(employeeDetails.getFirstName() + " " + OwnerlastName);
				}

				candidateMapper.setOwnerImageId(employeeDetails.getImageId());
			}

//			List<String> skillList1 = new ArrayList<String>();
//			List<SkillSetDetails> list = skillSetRepository.getSkillSetById(candidateId);
//			if (null != list && !list.isEmpty()) {
//				for (SkillSetDetails skillSetDetails : list) {
//					SkillSetDetails list2 = skillSetRepository.getById(skillSetDetails.getSkillSetDetailsId());
//
//					// String mapper = new String();
//					if (null != list2) {
//
//						DefinationDetails definationDetails1 = definationRepository
//								.findByDefinationId(list2.getSkillName());
//						if (null != definationDetails1) {
//							skillList1.add(definationDetails1.getName());
//
//						}
//
//					}
//				}
			List<String> skillList1=skillSetRepository.getSkillSetById(candidateId).stream().map(sk->{
				SkillSetDetails list2 = skillSetRepository.getById(sk.getSkillSetDetailsId());
					if (null != list2) {
						DefinationDetails definationDetails1 = definationRepository
								.findByDefinationId(list2.getSkillName());
						if (null != definationDetails1) {
							return definationDetails1.getName();
						}}
				return null;
			}).filter(Objects::nonNull).collect(Collectors.toList());
				candidateMapper.setSkillList(skillList1);
			

//			List<SkillSetDetails> skillLists = skillSetRepository.getSkillSetById(candidateId);
//			if (null != skillLists) {
//
//				candidateMapper.setSkillList((skillLists.stream().filter(li -> li != null).map(li -> li.getSkillName())
//						.collect(Collectors.toList())));
//			}

			candidateMapper.setLanguage(candidateDetails.getLanguage());
			candidateMapper.setWorkLocation(candidateDetails.getWorkLocation());
			candidateMapper.setWorkType(candidateDetails.getWorkType());
			candidateMapper.setActive(candidateDetails.isActive());
			if (candidateMapper.isDoNotCallInd()) {
				candidateMapper.setDoNotCall(callService.getUpdatedDoNotCallDetail(candidateId));
			}
		
			if (null != candidateAddressLinkList && !candidateAddressLinkList.isEmpty()) {

				for (CandidateAddressLink candidateAddressLink : candidateAddressLinkList) {
					AddressDetails addressDetails = addressRepository
							.getAddressDetailsByAddressId(candidateAddressLink.getAddressId());

					AddressMapper addressMapper = new AddressMapper();
					if (null != addressDetails) {

						addressMapper.setAddress1(addressDetails.getAddressLine1());
						addressMapper.setAddress2(addressDetails.getAddressLine2());
						addressMapper.setAddressType(addressDetails.getAddressType());
						addressMapper.setPostalCode(addressDetails.getPostalCode());
						addressMapper.setStreet(addressDetails.getStreet());
						addressMapper.setCity(addressDetails.getCity());
						addressMapper.setTown(addressDetails.getTown());
						addressMapper.setCountry(addressDetails.getCountry());
						addressMapper.setLatitude(addressDetails.getLatitude());
						addressMapper.setLongitude(addressDetails.getLongitude());
						addressMapper.setState(addressDetails.getState());
						addressMapper.setAddressId(addressDetails.getAddressId());
						addressMapper.setHouseNo(addressDetails.getHouseNo());
						Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(addressDetails.getCountry(),candidateDetails.getOrganizationId());
						if(null!=country) {
							addressMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
							addressMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
						}
						
						addressList.add(addressMapper);
					}
				}

				System.out.println("addressList.......... " + addressList);
			}
		}
			candidateMapper.setAddress(addressList);
		return candidateMapper;
	}

	@Override
	public CandidateViewMapper upateCandidateDetailsById(String candidateId, CandidateMapper candidateMapper) {
		CandidateViewMapper candidateMapperr = null;
		CandidateDetails candidateDetails = candidateDetailsRepository.getcandidateDetailsById(candidateId);

		if (null != candidateDetails) {
			candidateDetails.setLiveInd(false);

			candidateDetailsRepository.save(candidateDetails);
		}
		CandidateDetails candidateDetailss = new CandidateDetails();

		candidateDetailss.setCandidateId(candidateId);

		if (candidateMapper.getFirstName() != null && !candidateMapper.getFirstName().isEmpty()) {
			candidateDetailss.setFirstName(candidateMapper.getFirstName());

		} else {
			candidateDetailss.setFirstName(candidateDetails.getFirstName());
		}

		if (candidateMapper.getMiddleName() != null && !candidateMapper.getMiddleName().isEmpty()) {
			candidateDetailss.setMiddleName(candidateMapper.getMiddleName());

		} else {
			candidateDetailss.setMiddleName(candidateDetails.getMiddleName());
		}
		if (candidateMapper.getLastName() != null && !candidateMapper.getLastName().isEmpty()) {
			candidateDetailss.setLastName(candidateMapper.getLastName());

		} else {
			candidateDetailss.setLastName(candidateDetails.getLastName());
		}
		if (candidateMapper.getMobileNumber() != null && !candidateMapper.getMobileNumber().isEmpty()) {
			candidateDetailss.setMobileNumber(candidateMapper.getMobileNumber());

		} else {
			candidateDetailss.setMobileNumber(candidateDetails.getMobileNumber());
		}

		if (candidateMapper.getPhoneNumber() != null && !candidateMapper.getPhoneNumber().isEmpty()) {
			candidateDetailss.setPhoneNumber(candidateMapper.getPhoneNumber());

		} else {
			candidateDetailss.setPhoneNumber(candidateDetails.getPhoneNumber());
		}

		if (candidateMapper.getEmailId() != null && !candidateMapper.getEmailId().isEmpty()) {
			candidateDetailss.setEmailId(candidateMapper.getEmailId());

		} else {
			candidateDetailss.setEmailId(candidateDetails.getEmailId());
		}

		if (candidateMapper.getLinkedin() != null && !candidateMapper.getLinkedin().isEmpty()) {
			candidateDetailss.setLinkedin(candidateMapper.getLinkedin());

		} else {
			candidateDetailss.setLinkedin(candidateDetails.getLinkedin());
		}
		if (candidateMapper.getNotes() != null && !candidateMapper.getNotes().isEmpty()) {
			List<CandidateNotesLink> list =candidateNotesLinkRepository.getNoteListByCandidateId(candidateId);
			if (list !=null && !list.isEmpty()) {
				list.sort((m1, m2) -> m2.getCreation_date().compareTo(m1.getCreation_date()));
				Notes notes = notesRepository.findByNoteId(list.get(0).getNotesId());
				if (null != notes) {
					notes.setNotes(candidateMapper.getNotes());
					notesRepository.save(notes);
				}
			}
		}

		if (candidateMapper.getTag_with_company() != null && !candidateMapper.getTag_with_company().isEmpty()) {
			candidateDetailss.setTagWithCompany(candidateMapper.getTag_with_company());

		} else {
			candidateDetailss.setTagWithCompany(candidateDetails.getTagWithCompany());
		}

		if (candidateMapper.getBilling() != null && !candidateMapper.getBilling().isEmpty()) {
			candidateDetailss.setBilling(candidateMapper.getBilling());

		} else {
			candidateDetailss.setBilling(candidateDetails.getBilling());
		}

		if (candidateMapper.getSalutation() != null && !candidateMapper.getSalutation().isEmpty()) {
			candidateDetailss.setSalutation(candidateMapper.getSalutation());

		} else {
			candidateDetailss.setSalutation(candidateDetails.getSalutation());
		}

		if (candidateMapper.getCountryDialCode() != null && !candidateMapper.getCountryDialCode().isEmpty()) {
			candidateDetailss.setCountryDialcode(candidateMapper.getCountryDialCode());

		} else {
			candidateDetailss.setCountryDialcode(candidateDetails.getCountryDialcode());
		}

		if (candidateMapper.getCurrency() != null && !candidateMapper.getCurrency().isEmpty()) {
			candidateDetailss.setCurrency(candidateMapper.getCurrency());

		} else {
			candidateDetailss.setCurrency(candidateDetails.getCurrency());
		}

		if (candidateMapper.getDepartmentId() != null && !candidateMapper.getDepartmentId().isEmpty()) {
			candidateDetailss.setDepartment(candidateMapper.getDepartmentId());

		} else {
			candidateDetailss.setDepartment(candidateDetails.getDepartment());
		}

		if (candidateMapper.getRoleTypeId() != null && !candidateMapper.getRoleTypeId().isEmpty()) {
			candidateDetailss.setRoleType(candidateMapper.getRoleTypeId());

		} else {
			candidateDetailss.setRoleType(candidateDetails.getRoleType());
		}
		
		if (candidateMapper.getSectorId() != null && !candidateMapper.getSectorId().isEmpty()) {
			candidateDetailss.setSector(candidateMapper.getSectorId());

		} else {
			candidateDetailss.setSector(candidateDetails.getSector());
		}

		if (candidateMapper.getCountry() != null && !candidateMapper.getCountry().isEmpty()) {
			candidateDetailss.setCountry(candidateMapper.getCountry());

		} else {
			candidateDetailss.setCountry(candidateDetails.getCountry());
		}

		if (candidateMapper.getDesignation() != null && !candidateMapper.getDesignation().isEmpty()) {
			candidateDetailss.setDesignation(candidateMapper.getDesignation());

		} else {
			candidateDetailss.setDesignation(candidateDetails.getDesignation());
		}
		if (candidateMapper.getCountryDialCode1() != null && !candidateMapper.getCountryDialCode1().isEmpty()) {
			candidateDetailss.setCountryDialcode1(candidateMapper.getCountryDialCode1());

		} else {
			candidateDetailss.setCountryDialcode1(candidateDetails.getCountryDialcode1());
		}
		if (candidateMapper.getLinkedin_public_url() != null && !candidateMapper.getLinkedin_public_url().isEmpty()) {
			candidateDetailss.setLinkedinPublicUrl(candidateMapper.getLinkedin_public_url());

		} else {
			candidateDetailss.setLinkedinPublicUrl(candidateDetails.getLinkedinPublicUrl());
		}
		if (candidateMapper.getDateOfBirth() != null && !candidateMapper.getDateOfBirth().isEmpty()) {
			candidateDetailss.setDateOfBirth(candidateMapper.getDateOfBirth());

		} else {
			candidateDetailss.setDateOfBirth(candidateDetails.getDateOfBirth());
		}
		if (candidateMapper.getEducation() != null && !candidateMapper.getEducation().isEmpty()) {
			candidateDetailss.setEducatioin(candidateMapper.getEducation());

		} else {
			candidateDetailss.setEducatioin(candidateDetails.getEducatioin());
		}
		if (candidateMapper.getExperience() != 0) {
			candidateDetailss.setExperience(candidateMapper.getExperience());

		} else {
			candidateDetailss.setExperience(candidateDetails.getExperience());
		}
		if (candidateMapper.getGender() != null && !candidateMapper.getGender().isEmpty()) {
			candidateDetailss.setGender(candidateMapper.getGender());

		} else {
			candidateDetailss.setGender(candidateDetails.getGender());
		}
		if (candidateMapper.getIdNumber() != null && !candidateMapper.getIdNumber().isEmpty()) {
			candidateDetailss.setIdNumber(candidateMapper.getIdNumber());

		} else {
			candidateDetailss.setIdNumber(candidateDetails.getIdNumber());
		}
		if (candidateMapper.getIdProof() != null && !candidateMapper.getIdProof().isEmpty()) {
			candidateDetailss.setIdProof(candidateMapper.getIdProof());

		} else {
			candidateDetailss.setIdProof(candidateDetails.getIdProof());
		}
		if (candidateMapper.getLanguage() != null && !candidateMapper.getLanguage().isEmpty()) {
			candidateDetailss.setLanguage(candidateMapper.getLanguage());

		} else {
			candidateDetailss.setLanguage(candidateDetails.getLanguage());
		}
		if (candidateMapper.getNationality() != null && !candidateMapper.getNationality().isEmpty()) {
			candidateDetailss.setNationality(candidateMapper.getNationality());

		} else {
			candidateDetailss.setNationality(candidateDetails.getNationality());
		}
		if (candidateMapper.getWorkLocation() != null && !candidateMapper.getWorkLocation().isEmpty()) {
			candidateDetailss.setWorkLocation(candidateMapper.getWorkLocation());

		} else {
			candidateDetailss.setWorkLocation(candidateDetails.getWorkLocation());
		}
		if (candidateMapper.getWorkPreference() != null && !candidateMapper.getWorkPreference().isEmpty()) {
			candidateDetailss.setWorkPreferance(candidateMapper.getWorkPreference());

		} else {
			candidateDetailss.setWorkPreferance(candidateDetails.getWorkPreferance());
		}
		if (candidateMapper.getChannel() != null && !candidateMapper.getChannel().isEmpty()) {
			candidateDetailss.setChannel(candidateMapper.getChannel());

		} else {
			candidateDetailss.setChannel(candidateDetails.getChannel());
		}
		if (candidateMapper.getCurrentCtc() != null && !candidateMapper.getCurrentCtc().isEmpty()) {
			candidateDetailss.setCurrentCtc(candidateMapper.getCurrentCtc());

		} else {
			candidateDetailss.setCurrentCtc(candidateDetails.getCurrentCtc());
		}
		if (candidateMapper.getCurrentCtcCurency() != null && !candidateMapper.getCurrentCtcCurency().isEmpty()) {
			candidateDetailss.setCurrentCtcCurency(candidateMapper.getCurrentCtcCurency());

		} else {
			candidateDetailss.setCurrentCtcCurency(candidateDetails.getCurrentCtcCurency());
		}
		if (candidateMapper.getPartnerContact() != null && !candidateMapper.getPartnerContact().isEmpty()) {
			candidateDetailss.setPartnerContact(candidateMapper.getPartnerContact());

		} else {
			candidateDetailss.setPartnerContact(candidateDetails.getPartnerContact());
		}

		if (false != candidateMapper.isActive()) {

			candidateDetailss.setActive(candidateMapper.isActive());
			System.out.println("inside if");
		} else {
			candidateDetailss.setActive(candidateMapper.isActive());
			System.out.println("inside else");
		}
		if (candidateMapper.getWorkType() != null && !candidateMapper.getWorkType().isEmpty()) {
			candidateDetailss.setWorkType(candidateMapper.getWorkType());

		} else {
			candidateDetailss.setWorkType(candidateDetails.getWorkType());
		}
		if (candidateMapper.getNoticePeriod() != 0) {
			candidateDetailss.setNoticePeriod(candidateMapper.getNoticePeriod());

		} else {
			candidateDetailss.setNoticePeriod(candidateDetails.getNoticePeriod());
		}

		if (candidateMapper.getPartnerId() != null && !candidateMapper.getPartnerId().isEmpty()) {
			candidateDetailss.setPartnerId(candidateMapper.getPartnerId());

		} else {
			candidateDetailss.setPartnerId(candidateDetails.getPartnerId());
		}
		candidateDetailss.setLiveInd(true);

		if (candidateMapper.getCategory() != null && !candidateMapper.getCategory().isEmpty()) {
			candidateDetailss.setCategory(candidateMapper.getCategory());

		} else {
			candidateDetailss.setCategory(candidateDetails.getCategory());
		}
		if (candidateMapper.getBenifit() != null && !candidateMapper.getBenifit().isEmpty()) {
			candidateDetailss.setBenifit(candidateMapper.getBenifit());
		} else {
			candidateDetailss.setBenifit(candidateDetails.getBenifit());
		}
		if (candidateMapper.getCostType() != null && !candidateMapper.getCostType().isEmpty()) {
			candidateDetailss.setCostType(candidateMapper.getCostType());
		} else {
			candidateDetailss.setCostType(candidateDetails.getCostType());
		}
		if (candidateMapper.getNoticeDetail() != null && !candidateMapper.getNoticeDetail().isEmpty()) {
			candidateDetailss.setNoticeDetail(candidateMapper.getNoticeDetail());
		} else {
			candidateDetailss.setNoticeDetail(candidateDetails.getNoticeDetail());
		}
		if (candidateMapper.getWhatsApp() != null && !candidateMapper.getWhatsApp().isEmpty()) {
			candidateDetailss.setWhatsApp(candidateMapper.getWhatsApp());
		} else {
			candidateDetailss.setWhatsApp(candidateDetails.getWhatsApp());
		}
		if (candidateMapper.getAllowSharing() != null && !candidateMapper.getAllowSharing().isEmpty()) {
			candidateDetailss.setAllowSharing(candidateMapper.getAllowSharing());

		} else {
			candidateDetailss.setAllowSharing(candidateDetails.getAllowSharing());
		}

		if (false != candidateMapper.isEmpInd()) {

			candidateDetailss.setEmpInd(candidateMapper.isEmpInd());
			System.out.println("inside if");
		} else {
			candidateDetailss.setEmpInd(candidateMapper.isEmpInd());
			System.out.println("inside else");
		}
		
		candidateDetailss.setCandiProcessInd(candidateDetails.isCandiProcessInd());
		

//		if(candidateMapper.getWorkLocation()!=null) {
//			candidateDetailss.setWorkLocation(candidateMapper.getWorkLocation());
//		}else {
//			candidateDetailss.setWorkLocation(candidateDetails.getWorkLocation());
//		}

		candidateDetailss.setPreferredDistance(candidateDetails.getPreferredDistance());
		candidateDetailss.setOrganizationId(candidateDetails.getOrganizationId());
		candidateDetailss.setUserId(candidateDetails.getUserId());
		candidateDetailss.setCreationDate(candidateDetails.getCreationDate());
		candidateDetailss.setModifiedAt(new Date());

		if (null != candidateMapper.getFirstName()) {
			String middleName = "";
			String lastName = "";

			if (candidateMapper.getLastName() != null) {

				lastName = candidateMapper.getLastName();
			}
			if (candidateMapper.getMiddleName() != null && candidateMapper.getMiddleName().length() > 0) {

				middleName = candidateMapper.getMiddleName();
				candidateDetailss.setFullName(candidateMapper.getFirstName() + " " + middleName + " " + lastName);
			} else {

				candidateDetailss.setFullName(candidateMapper.getFirstName() + " " + lastName);
			}
		} else {
			candidateDetailss.setFullName(candidateDetails.getFullName());
		}

		if (candidateMapper.getAvailableDate() != null && !candidateMapper.getAvailableDate().isEmpty()) {
			try {
				candidateDetailss.setAvailableDate(
						Utility.removeTime(Utility.getDateFromISOString(candidateMapper.getAvailableDate())));
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {
			candidateDetailss.setAvailableDate(candidateDetails.getAvailableDate());
		}

		// candidateMapperr = getCandidateDetailsById(candidateId);
		if (null != candidateMapper.getAddress()) {
			List<AddressMapper> addressList = candidateMapper.getAddress();

			for (AddressMapper addressMapper : addressList) {

				String addressId = addressMapper.getAddressId();
				if (null != addressId) {

					AddressDetails addressDetails = addressRepository.getAddressDetailsByAddressId(addressId);
					if (null != addressDetails) {

						addressDetails.setLiveInd(false);
						addressRepository.save(addressDetails);
					}

					
					AddressDetails newAddressDetailss = new AddressDetails();

					newAddressDetailss.setAddressId(addressMapper.getAddressId());
					System.out.println("ADDID@@@@@@@" + addressId);

					if (null != addressMapper.getAddress1()) {
						newAddressDetailss.setAddressLine1(addressMapper.getAddress1());

					} else {
						newAddressDetailss.setAddressLine1(addressDetails.getAddressLine1());
					}

					if (null != addressMapper.getAddress2()) {
						newAddressDetailss.setAddressLine2(addressMapper.getAddress2());
					} else {
						newAddressDetailss.setAddressLine2(addressDetails.getAddressLine2());
					}
					if (null != addressMapper.getAddressType()) {
						newAddressDetailss.setAddressType(addressMapper.getAddressType());
					} else {
						newAddressDetailss.setAddressType(addressDetails.getAddressType());
					}
					if (null != addressMapper.getTown()) {
						newAddressDetailss.setTown(addressMapper.getTown());
					} else {
						newAddressDetailss.setTown(addressDetails.getTown());
					}
					if (null != addressMapper.getStreet()) {
						newAddressDetailss.setStreet(addressMapper.getStreet());
					} else {
						newAddressDetailss.setStreet(addressDetails.getStreet());
					}

					if (null != addressMapper.getCity()) {
						newAddressDetailss.setCity(addressMapper.getCity());
					} else {
						newAddressDetailss.setCity(addressDetails.getCity());
					}

					if (null != addressMapper.getPostalCode()) {
						newAddressDetailss.setPostalCode(addressMapper.getPostalCode());
					} else {
						newAddressDetailss.setPostalCode(addressDetails.getPostalCode());
					}

					if (null != addressMapper.getState()) {
						newAddressDetailss.setState(addressMapper.getState());
					} else {
						newAddressDetailss.setState(addressDetails.getState());
					}

					if (null != addressMapper.getCountry()) {
						newAddressDetailss.setCountry(addressMapper.getCountry());
					} else {
						newAddressDetailss.setTown(addressDetails.getTown());
					}

					if (null != addressMapper.getLatitude()) {
						newAddressDetailss.setLatitude(addressMapper.getLatitude());
					} else {
						newAddressDetailss.setLatitude(addressDetails.getLatitude());
					}

					if (null != addressMapper.getLongitude()) {
						newAddressDetailss.setLongitude(addressMapper.getLongitude());
					} else {
						newAddressDetailss.setLongitude(addressDetails.getLongitude());
					}
					
					if (null != addressMapper.getHouseNo()) {
						newAddressDetailss.setHouseNo(addressMapper.getHouseNo());
					} else {
						newAddressDetailss.setHouseNo(addressDetails.getHouseNo());
					}

					newAddressDetailss.setCreatorId("");
					newAddressDetailss.setCreationDate(new Date());
					newAddressDetailss.setLiveInd(true);
					addressRepository.save(newAddressDetailss);
					System.out.println("AddressId::"+addressRepository.save(newAddressDetailss).getAddressId());
				}else {
					
					AddressInfo addressInfo = new AddressInfo();
					addressInfo.setCreationDate(new Date());
					//addressInfo.setCreatorId(candidateMapperr.getUserId());
					AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

					String addressId1 = addressInfoo.getId();

					if (null != addressId1) {
					
					AddressDetails newAddressDetailss = new AddressDetails();

					newAddressDetailss.setAddressId(addressId1);
					
					if (null != addressMapper.getAddress1()) {
						newAddressDetailss.setAddressLine1(addressMapper.getAddress1());
					}
					if (null != addressMapper.getAddress2()) {
						newAddressDetailss.setAddressLine2(addressMapper.getAddress2());
					} 
					if (null != addressMapper.getAddressType()) {
						newAddressDetailss.setAddressType(addressMapper.getAddressType());
					}
					if (null != addressMapper.getTown()) {
						newAddressDetailss.setTown(addressMapper.getTown());
					}
					if (null != addressMapper.getStreet()) {
						newAddressDetailss.setStreet(addressMapper.getStreet());
					}
					if (null != addressMapper.getCity()) {
						newAddressDetailss.setCity(addressMapper.getCity());
					}
					if (null != addressMapper.getPostalCode()) {
						newAddressDetailss.setPostalCode(addressMapper.getPostalCode());
					}
					if (null != addressMapper.getState()) {
						newAddressDetailss.setState(addressMapper.getState());
					} 
					if (null != addressMapper.getCountry()) {
						newAddressDetailss.setCountry(addressMapper.getCountry());
					}
					if (null != addressMapper.getLatitude()) {
						newAddressDetailss.setLatitude(addressMapper.getLatitude());
					} 
					if (null != addressMapper.getLongitude()) {
						newAddressDetailss.setLongitude(addressMapper.getLongitude());
					}
					if (null != addressMapper.getHouseNo()) {
						newAddressDetailss.setHouseNo(addressMapper.getHouseNo());
					}
					
					newAddressDetailss.setCreatorId("");
					newAddressDetailss.setCreationDate(new Date());
					newAddressDetailss.setLiveInd(true);
					addressRepository.save(newAddressDetailss);
					System.out.println("AddressId::"+addressRepository.save(newAddressDetailss).getAddressId());
					
					CandidateAddressLink candidateAddressLink = new CandidateAddressLink();
					candidateAddressLink.setCandidateId(candidateId);
					candidateAddressLink.setAddressId(addressId1);
					candidateAddressLink.setCreationDate(new Date());

					candidateAddressLinkRepository
							.save(candidateAddressLink);
				}
				}
			}
		}

		/*Create Notification */
		NotificationDetails notification = new NotificationDetails();
		String middleName2 =" ";
		String lastName2 ="";
		String satutation1 ="";

		if(!StringUtils.isEmpty(candidateMapper.getLastName())) {
			 
			lastName2 = candidateMapper.getLastName();
		 }
		 if(candidateMapper.getSalutation() != null && candidateMapper.getSalutation().length()>0) {
			 satutation1 = candidateMapper.getSalutation();
		 }


		 if(candidateMapper.getMiddleName() != null && candidateMapper.getMiddleName().length()>0) {

		 
			 middleName2 = candidateMapper.getMiddleName();
			 notification.setMessage(satutation1+" "+candidateMapper.getFirstName()+" "+middleName2+" "+lastName2+ " Candidate updated.");
		 }else {
			 
			 notification.setMessage(satutation1+" "+candidateMapper.getFirstName()+" "+lastName2+ " Candidate updated.");
		 }
		
		 notification.setAssignedTo(candidateMapper.getUserId());
		 
		notification.setMessageReadInd(false);
		notification.setNotificationDate(new Date());
		notification.setNotificationType("Candidate Creation");
		notification.setUser_id(candidateMapper.getUserId());
		notification.setOrg_id(candidateMapper.getOrganizationId());
		notification.setLiveInd(true);
		notification.setCreationDate(new Date());
		notificationRepository.save(notification);
		
		String msg = "A Candidate is updated By ";
		notificationService.createNotification(candidateMapper.getUserId(),"candidate update", msg, "Candidate", "Update" );
		CandidateDetails updateCandidate = candidateDetailsRepository.save(candidateDetailss);

		candidateMapperr = getCandidateMapper(updateCandidate);

		return candidateMapperr;
	}

	@Override
	public String saveSkillSet(SkillSetMapper skillSetMapper) {
		DefinationDetails definationDetails1 = definationRepository
				.getBySkillNameAndLiveInd(skillSetMapper.getSkillName());

		if (null != definationDetails1) {
			SkillSetDetails skillSetDetails1 = new SkillSetDetails();
			skillSetDetails1.setSkillName(definationDetails1.getDefinationId());
			skillSetDetails1.setExperience(skillSetMapper.getExperience());
			skillSetDetails1.setCandidateId(skillSetMapper.getCandidateId());
			skillSetDetails1.setCreationDate(new Date());
			// skillSetDetails1.setEditInd(skillSetMapper.isEditInd());
			skillSetRepository.save(skillSetDetails1);
			
			SkillCandidateNo SkillCandidateNo = skillCandidateNoRepository.findBySkill(definationDetails1.getDefinationId());
			if(null!=SkillCandidateNo) {
				int no = SkillCandidateNo.getNumber();
				SkillCandidateNo.setNumber(no+1);
				skillCandidateNoRepository.save(SkillCandidateNo);
			}else {
				SkillCandidateNo skillCandidateNo = new SkillCandidateNo();
				int no = 0;
				skillCandidateNo.setSkill(definationDetails1.getDefinationId());
				skillCandidateNo.setNumber(no);
				skillCandidateNo.setUserId(skillSetMapper.getUserId());
				skillCandidateNo.setOrganizationId(skillSetMapper.getOrganizationId());
				skillCandidateNo.setCreationDate(new Date());
				skillCandidateNoRepository.save(skillCandidateNo);
			}
		} else {

			DefinationInfo definationInfo = new DefinationInfo();

			definationInfo.setCreation_date(new Date());
			String id = definationInfoRepository.save(definationInfo).getDefination_info_id();

			DefinationDetails newDefinationDetails = new DefinationDetails();
			newDefinationDetails.setDefinationId(id);
			newDefinationDetails.setName(skillSetMapper.getSkillName());
			newDefinationDetails.setOrg_id(skillSetMapper.getOrganizationId());
			newDefinationDetails.setUser_id(skillSetMapper.getUserId());
			newDefinationDetails.setCreation_date(new Date());
			newDefinationDetails.setLiveInd(true);
			newDefinationDetails.setEditInd(true);
			definationRepository.save(newDefinationDetails);

			SkillSetDetails skillSetDetails2 = new SkillSetDetails();
			skillSetDetails2.setSkillName(id);
			skillSetDetails2.setExperience(skillSetMapper.getExperience());
			skillSetDetails2.setCandidateId(skillSetMapper.getCandidateId());
			skillSetDetails2.setCreationDate(new Date());
			skillSetRepository.save(skillSetDetails2);
			
			SkillCandidateNo skillCandidateNo1 = skillCandidateNoRepository.findBySkill(id);
			if(null!=skillCandidateNo1) {
				int no = skillCandidateNo1.getNumber();
				skillCandidateNo1.setNumber(no+1);
				skillCandidateNoRepository.save(skillCandidateNo1);
			}else {
				SkillCandidateNo skillCandidateNo = new SkillCandidateNo();
				int no = 0;
				skillCandidateNo.setSkill(id);
				skillCandidateNo.setNumber(no);
				skillCandidateNo.setUserId(skillSetMapper.getUserId());
				skillCandidateNo.setOrganizationId(skillSetMapper.getOrganizationId());
				skillCandidateNo.setCreationDate(new Date());
				skillCandidateNoRepository.save(skillCandidateNo);
			}
			
			DefinationDetailsDelete definationDetailsDelete=new DefinationDetailsDelete();
			definationDetailsDelete.setOrgId(skillSetMapper.getOrganizationId());
			definationDetailsDelete.setUserId(skillSetMapper.getUserId());
			definationDetailsDelete.setUpdationDate(new Date());
			definationDeleteRepository.save(definationDetailsDelete);
		}

		return skillSetMapper.getSkillName();
	}

	@Override
	public List<SkillSetMapper> getSkillSetDetails(String candidateId) {
//		List<SkillSetDetails> skillList = skillSetRepository.getSkillSetById(candidateId);
//		List<SkillSetMapper> resultList = new ArrayList<SkillSetMapper>();
//		if (null != skillList && !skillList.isEmpty()) {
//
//			for (SkillSetDetails skillSetDetails : skillList) {
//				SkillSetMapper skillSetMapper = getSkillSet(skillSetDetails.getSkillSetDetailsId());
//				if (null != skillSetMapper) {
//					resultList.add(skillSetMapper);
//				}
//
//			}
//
//		}
//
//		return resultList;
		return skillSetRepository.getSkillSetById(candidateId).stream().map(s->getSkillSet(s.getSkillSetDetailsId())).collect(Collectors.toList());
	}

	@Override
	public SkillSetMapper getSkillSet(String id) {
		SkillSetDetails skillSetDetails = skillSetRepository.getById(id);
		SkillSetMapper skillSetMapper = new SkillSetMapper();

		if (null != skillSetDetails) {

			DefinationDetails definationDetails1 = definationRepository
					.findByDefinationId(skillSetDetails.getSkillName());
			if (null != definationDetails1) {
				skillSetMapper.setSkillName(definationDetails1.getName());

			}
			
			if (skillSetDetails.isPauseInd()==true) {
				skillSetMapper.setExperience(skillSetDetails.getPauseExperience());
			}else {
				if(null!=skillSetDetails.getUnpauseDate()) {
					skillSetMapper.setExperience(getExprience(skillSetDetails.getUnpauseDate()) + skillSetDetails.getPauseExperience());
				}else {
					skillSetMapper.setExperience(getExprience(skillSetDetails.getCreationDate()) + skillSetDetails.getExperience());
				}
			}
			//skillSetMapper.setExperience(skillSetDetails.getExperience());
			skillSetMapper.setCreationDate(Utility.getISOFromDate(skillSetDetails.getCreationDate()));
			skillSetMapper.setSkillSetDetailsId(skillSetDetails.getSkillSetDetailsId());
			skillSetMapper.setCandidateId(skillSetDetails.getCandidateId());
			skillSetMapper.setSkillRole(skillSetDetails.getSkillRole());
		}

		return skillSetMapper;
	}

	@Override
	public String saveCandidateTraining(CandidateTrainingMapper candidateTrainingMapper) {
		String id = null;
		if (null != candidateTrainingMapper) {

			CandidateTraining candidateTraining = new CandidateTraining();
			candidateTraining.setCourseName(candidateTrainingMapper.getCourseName());
			candidateTraining.setCandidateId(candidateTrainingMapper.getCandidateId());
			candidateTraining.setGrade(candidateTrainingMapper.getGrade());
			candidateTraining.setOrganization(candidateTrainingMapper.getOrganization());
			candidateTraining.setCreationDate(new Date());
			try {
				if (null != candidateTrainingMapper.getStartDate()) {
					candidateTraining
							.setStartDate(Utility.getDateFromISOString(candidateTrainingMapper.getStartDate()));

				}
				if (null != candidateTrainingMapper.getEndDate()) {
					candidateTraining.setEndDate(Utility.getDateFromISOString(candidateTrainingMapper.getEndDate()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			candidateTraining.setDocumentId(candidateTrainingMapper.getDocumentId());
			candidateTraining.setLiveInd(true);

			CandidateTraining candidate = candidateTrainingRepository.save(candidateTraining);
			id = candidate.getCandidateTrainingId();

		}
		return id;
	}

	@Override
	public List<CandidateTrainingMapper> getCandidateTraining(String candidateId) {
//		List<CandidateTraining> trainingList = candidateTrainingRepository.getCandidateTrainingById(candidateId);
//		List<CandidateTrainingMapper> resultList = new ArrayList<CandidateTrainingMapper>();
//		if (null != trainingList && !trainingList.isEmpty()) {
//
//			for (CandidateTraining candidateTraining : trainingList) {
//				CandidateTrainingMapper candidateTrainingMapper = getCandidateTrainings(
//						candidateTraining.getCandidateTrainingId());
//				if (null != candidateTrainingMapper) {
//					resultList.add(candidateTrainingMapper);
//				}
//
//			}
//
//		}
//
//		return resultList;
		return candidateTrainingRepository.getCandidateTrainingById(candidateId).stream().map(s->getCandidateTrainings(
						s.getCandidateTrainingId())).collect(Collectors.toList());
	}

	@Override
	public CandidateTrainingMapper getCandidateTrainings(String id) {

		CandidateTraining candidateTraining = candidateTrainingRepository.getById(id);
		CandidateTrainingMapper candidateTrainingMapper = new CandidateTrainingMapper();

		if (null != candidateTraining) {
			candidateTrainingMapper.setCandidateTrainingId(candidateTraining.getCandidateTrainingId());
			candidateTrainingMapper.setCourseName(candidateTraining.getCourseName());
			candidateTrainingMapper.setGrade(candidateTraining.getGrade());
			candidateTrainingMapper.setOrganization(candidateTraining.getOrganization());
			candidateTrainingMapper.setDocumentId(candidateTraining.getDocumentId());
			candidateTrainingMapper.setCreationDate(Utility.getISOFromDate(candidateTraining.getCreationDate()));
			candidateTrainingMapper.setStartDate(Utility.getISOFromDate(candidateTraining.getStartDate()));
			candidateTrainingMapper.setEndDate(Utility.getISOFromDate(candidateTraining.getEndDate()));
			candidateTrainingMapper.setCandidateId(candidateTraining.getCandidateId());
		}

		return candidateTrainingMapper;
	}

	@Override
	public List<CandidateViewMapper> getCandidateListPageWiseByUserId(String userId, int pageNo, int pageSize) {
		List<CandidateViewMapper> resultMapper = new ArrayList<>();

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		Page<CandidateDetails> candidateList = candidateDetailsRepository.getCandidateListPageWiseByUserId(userId,paging);
		if (null != candidateList && !candidateList.isEmpty()) {
			resultMapper = candidateList.stream().map(candidate->{
				CandidateViewMapper candidateMapper = getCandidateDetailsById(candidate.getCandidateId());
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(candidate.getLastName())) {

					lastName = candidate.getLastName();
				}

				if (candidate.getMiddleName() != null && candidate.getMiddleName().length() > 0) {

					middleName = candidate.getMiddleName();
					candidateMapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
				} else {

					candidateMapper.setFullName(candidate.getFirstName() + " " + lastName);
				}
				candidateMapper.setPageCount(candidateList.getTotalPages());
				candidateMapper.setDataCount(candidateList.getSize());
				candidateMapper.setListCount(candidateList.getTotalElements());

				return candidateMapper;
				
//				RecruitmentCandidateLink candidateLink = recruitmentCandidateLinkRepository
//						.getCandidateRecruitmentLinkByCandidateIds(candidate.getCandidateId());
//				if(null!=candidateLink) {
//					candidateMapper.setCandiProcessInd(true);
//				}else {
//					candidateMapper.setCandiProcessInd(false);
//				}

			}).collect(Collectors.toList());
//			return	resultMapper;
		}
		return resultMapper;
	}	

	@Override
	public List<CandidateViewMapper> getCandidateListPageWiseByUserIds(List<String> userId, int pageNo, int pageSize) {
		List<CandidateViewMapper> resultMapper = new ArrayList<>();

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		Page<CandidateDetails> candidateList = candidateDetailsRepository.getCandidateListPageWiseByUserIds(userId,paging);
		if (null != candidateList && !candidateList.isEmpty()) {
			resultMapper = candidateList.stream().map(candidate->{
				CandidateViewMapper candidateMapper = getCandidateDetailsById(candidate.getCandidateId());
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(candidate.getLastName())) {

					lastName = candidate.getLastName();
				}

				if (candidate.getMiddleName() != null && candidate.getMiddleName().length() > 0) {

					middleName = candidate.getMiddleName();
					candidateMapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
				} else {

					candidateMapper.setFullName(candidate.getFirstName() + " " + lastName);
				}
				candidateMapper.setPageCount(candidateList.getTotalPages());
				candidateMapper.setDataCount(candidateList.getSize());
				candidateMapper.setListCount(candidateList.getTotalElements());

				return candidateMapper;
				
//				RecruitmentCandidateLink candidateLink = recruitmentCandidateLinkRepository
//						.getCandidateRecruitmentLinkByCandidateIds(candidate.getCandidateId());
//				if(null!=candidateLink) {
//					candidateMapper.setCandiProcessInd(true);
//				}else {
//					candidateMapper.setCandiProcessInd(false);
//				}

			}).collect(Collectors.toList());
//			return	resultMapper;
		}
		return resultMapper;
	}	
	@Override
	public String saveCandidateNotes(NotesMapper notesMapper) {
		String notesId = null;

		if (null != notesMapper) {
			Notes notes = new Notes();
			notes.setNotes(notesMapper.getNotes());
			notes.setCreation_date(new Date());
			notes.setUserId(notesMapper.getEmployeeId());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);
			notesId = note.getNotes_id();

			/* insert to customer-notes-link */

			CandidateNotesLink candidateNotesLink = new CandidateNotesLink();

			candidateNotesLink.setCandidate_id(notesMapper.getCandidateId());
			candidateNotesLink.setNotesId(notesId);
			candidateNotesLink.setCreation_date(new Date());

			candidateNotesLinkRepository.save(candidateNotesLink);

		}
		return notesId;

	}

	@Override
	public List<NotesMapper> getNoteListByCandidateId(String candidateId) {
//		List<CandidateNotesLink> candidateNotesLinkList = candidateNotesLinkRepository
//				.getNoteListByCandidateId(candidateId);
//
//		// System.out.println("contactNotesLinkList............"+contactNotesLinkList.size());
//		List<NotesMapper> resultList = new ArrayList<NotesMapper>();
//
//		if (candidateNotesLinkList != null && !candidateNotesLinkList.isEmpty()) {
//
//			for (CandidateNotesLink candidateNotesLink : candidateNotesLinkList) {
//
//				NotesMapper notesMapper = getNotes(candidateNotesLink.getNotes_id());
//				resultList.add(notesMapper);
//			}
//
//		}
//		return resultList;
		return candidateNotesLinkRepository.getNoteListByCandidateId(candidateId).stream().map(n->getNotes(n.getNotesId())).filter(o->o!=null).collect(Collectors.toList());
	}

	@Override
	public NotesMapper getNotes(String id) {
		Notes notes = notesRepository.findByNoteId(id);
//		Notes notes = notesRepository.getById(id);
		NotesMapper notesMapper = new NotesMapper();
		if(notes!=null) {
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
			return notesMapper;
		}else {
			return null;
		}
	}

	public CandidateViewMapper getCandidateMapper(CandidateDetails candidate) {

		CandidateViewMapper candidateMapper = new CandidateViewMapper();
		candidateMapper.setFirstName(candidate.getFirstName());

		candidateMapper.setMiddleName(candidate.getMiddleName());
		candidateMapper.setLastName(candidate.getLastName());
		candidateMapper.setMobileNumber(candidate.getMobileNumber());
		candidateMapper.setPhoneNumber(candidate.getPhoneNumber());
		candidateMapper.setEmailId(candidate.getEmailId());
		candidateMapper.setLinkedin(candidate.getLinkedin());
		candidateMapper.setLinkedin_public_url(candidate.getLinkedinPublicUrl());
		candidateMapper.setCountryDialCode(candidate.getCountryDialcode());
		candidateMapper.setCountryDialCode1(candidate.getCountryDialcode1());
		candidateMapper.setCurrency(candidate.getCurrency());

		candidateMapper.setCurrentCtc(candidate.getCurrentCtc());
		candidateMapper.setBenifit(candidate.getBenifit());
		candidateMapper.setCountry(candidate.getCountry());
		candidateMapper.setWorkPreference(candidate.getWorkPreferance());
		candidateMapper.setWhatsApp(candidate.getWhatsApp());
		candidateMapper.setWorkLocation(candidate.getWorkLocation());
		candidateMapper.setWorkType(candidate.getWorkType());
		candidateMapper.setIdProof(candidate.getIdProof());
		candidateMapper.setIdNumber(candidate.getIdNumber());
		candidateMapper.setCandidateId(candidate.getCandidateId());
		candidateMapper.setGender(candidate.getGender());
		candidateMapper.setCategory(candidate.getCategory());
		candidateMapper.setChannel(candidate.getChannel());
		candidateMapper.setCostType(candidate.getCostType());
		candidateMapper.setCreationDate(Utility.getISOFromDate(candidate.getCreationDate()));
		candidateMapper.setEducation(candidate.getEducatioin());
		candidateMapper.setImageId(candidate.getImageId());
		candidateMapper.setLanguage(candidate.getLanguage());
		candidateMapper.setFullName(candidate.getFullName());
		candidateMapper.setNationality(candidate.getNationality());
		candidateMapper.setOrganizationId(candidate.getOrganizationId());
		candidateMapper.setSalutation(candidate.getSalutation());
		candidateMapper.setExperience(candidate.getExperience());
		candidateMapper.setAllowSharing(candidate.getAllowSharing());
		candidateMapper.setNoticePeriod(candidate.getNoticePeriod());
		candidateMapper.setCurrentCtcCurency(candidate.getCurrentCtcCurency());
		candidateMapper.setPartnerContact(candidate.getPartnerContact());
		candidateMapper.setCandiProcessInd(candidate.isCandiProcessInd());
		candidateMapper.setPreferredDistance(candidate.getPreferredDistance());

		// candidateMapper.setDepartment(candidate.getDepartment());
		// candidateMapper.setDesignation(candidate.getDesignation());
		if (!StringUtils.isEmpty(candidate.getDesignation())) {
			Designation designation = designationRepository.findByDesignationTypeId(candidate.getDesignation());
			if (null != designation) {
				candidateMapper.setDesignationTypeId(designation.getDesignationTypeId());
				candidateMapper.setDesignation(designation.getDesignationType());
			} else {
				candidateMapper.setDesignation(candidate.getDesignation());
			}
		}
		if (!StringUtils.isEmpty(candidate.getDepartment())) {
			Department department = departmentRepository.getDepartmentDetails(candidate.getDepartment());
			if (null != department) {
				candidateMapper.setDepartmentId(department.getDepartment_id());
				candidateMapper.setDepartmentName(department.getDepartmentName());
			}
		}
		if (!StringUtils.isEmpty(candidate.getRoleType())) {
			RoleType roleType = roleTypeRepository.findByRoleTypeId(candidate.getRoleType());
			if (null != roleType) {
				candidateMapper.setRoleTypeId(roleType.getRoleTypeId());
				candidateMapper.setRoleType(roleType.getRoleType());
			}
		}
		if (!StringUtils.isEmpty(candidate.getPartnerId())) {
			PartnerDetails partnerDetails = partnerDetailsRepository
					.getPartnerDetailsByIdAndLiveInd(candidate.getPartnerId());
			if (null != partnerDetails) {
				candidateMapper.setPartnerId(partnerDetails.getPartnerId());
				candidateMapper.setPartnerName(partnerDetails.getPartnerName());
			}
		}
		
		//candidate skill-set list
		List<String> skillList1=skillSetRepository.getSkillSetById(candidate.getCandidateId()).stream().map(sk->{
			SkillSetDetails list2 = skillSetRepository.getById(sk.getSkillSetDetailsId());
				if (null != list2) {
					DefinationDetails definationDetails1 = definationRepository
							.findByDefinationId(list2.getSkillName());
					if (null != definationDetails1) {
						return definationDetails1.getName();
					}}
			return null;
		}).filter(Objects::nonNull).collect(Collectors.toList());
			candidateMapper.setSkillList(skillList1);
			

		candidateMapper.setTag_with_company(candidate.getTagWithCompany());
		candidateMapper.setBilling(candidate.getBilling());
		candidateMapper.setAvailableDate(Utility.getISOFromDate(candidate.getAvailableDate()));
		candidateMapper.setDateOfBirth(candidate.getDateOfBirth());
		candidateMapper.setNotes(candidate.getNotes());
		candidateMapper.setUserId(candidate.getUserId());
		candidateMapper.setOrganizationId(candidate.getOrganizationId());
		// candidateMapper.setCreationDate(new Date());

		List<CandidateAddressLink> candidateAddressLinkList = candidateAddressLinkRepository
				.getAddressListByCandidateId(candidate.getCandidateId());
		List<AddressMapper> addressList = new ArrayList<AddressMapper>();
		if (null != candidateAddressLinkList && !candidateAddressLinkList.isEmpty()) {

			for (CandidateAddressLink candidateAddressLink : candidateAddressLinkList) {
				AddressDetails addressDetails = addressRepository
						.getAddressDetailsByAddressId(candidateAddressLink.getAddressId());

				AddressMapper addressMapper = new AddressMapper();
				if (null != addressDetails) {

					addressMapper.setAddress1(addressDetails.getAddressLine1());
					addressMapper.setAddress2(addressDetails.getAddressLine2());
					addressMapper.setAddressType(addressDetails.getAddressType());
					addressMapper.setPostalCode(addressDetails.getPostalCode());
					addressMapper.setStreet(addressDetails.getStreet());
					addressMapper.setCity(addressDetails.getCity());
					addressMapper.setTown(addressDetails.getTown());
					addressMapper.setCountry(addressDetails.getCountry());
					addressMapper.setLatitude(addressDetails.getLatitude());
					addressMapper.setLongitude(addressDetails.getLongitude());
					addressMapper.setState(addressDetails.getState());
					addressMapper.setAddressId(addressDetails.getAddressId());
					addressMapper.setHouseNo(addressDetails.getHouseNo());
					addressList.add(addressMapper);
				}
			}

			System.out.println("addressList.......... " + addressList);
		}
		candidateMapper.setAddress(addressList);

		return candidateMapper;

	}

	@Override
	public List<DocumentMapper> getCandidateDocumentListByCandidateId(String candidateId) {
		String documentId = null;
		List<CandidateDocumentLink> candidateDocumentLinkList = candidateDocumentLinkRepository
				.getDocumentByCandidateId(candidateId);
		List<DocumentMapper> resultList = new ArrayList<DocumentMapper>();

		if (candidateDocumentLinkList != null && !candidateDocumentLinkList.isEmpty()) {

			for (CandidateDocumentLink candidateDocumentLink : candidateDocumentLinkList) {

				DocumentMapper documentMapper = documentService.getDocument(candidateDocumentLink.getDocumentId());
				documentMapper.setShareInd(candidateDocumentLink.isShareInd());
				resultList.add(documentMapper);
			}

		}

		if (null != resultList && !resultList.isEmpty()) {
			List<DocumentMapper> documentMapper2 = new ArrayList<DocumentMapper>();
			for (DocumentMapper documentMapper1 : resultList) {
				if(null!=documentMapper1.getDocumentContentType() && !documentMapper1.getDocumentContentType().isEmpty()){
				if (documentMapper1.getDocumentContentType().equalsIgnoreCase("Resume")) {

					documentMapper2.add(documentMapper1);
				}
				}
			}
			Collections.sort(documentMapper2, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			if (null != documentMapper2 && !documentMapper2.isEmpty()) {
				documentId = documentMapper2.get(0).getDocumentId();
			}
		}
		if (null != resultList && !resultList.isEmpty()) {
			for (DocumentMapper documentMapper2 : resultList) {
				if(null!=documentMapper2.getDocumentId() && !documentMapper2.getDocumentId().isEmpty()){
				if (documentMapper2.getDocumentId().equalsIgnoreCase(documentId)) {
					documentMapper2.setLatestInd(true);
				}
				}
			}
		}

		return resultList;
	}

	@Override
	public String saveCandidateEducationDetails(CandidateEducationDetailsMapper candidateEducationDetailsMapper) {
		String id = null;
		if (candidateEducationDetailsMapper.getCandidateId() != null) {
			CandidateEducationDetails candidateEducationDetails = new CandidateEducationDetails();
			candidateEducationDetails.setCourseName(candidateEducationDetailsMapper.getCourseName());
			candidateEducationDetails.setCourseType(candidateEducationDetailsMapper.getCourseType());
			candidateEducationDetails.setCandidateId(candidateEducationDetailsMapper.getCandidateId());
			candidateEducationDetails.setEducationType(candidateEducationDetailsMapper.getEducationTypeId());
			candidateEducationDetails.setMarksSecured(candidateEducationDetailsMapper.getMarksSecured());
			candidateEducationDetails.setMarksType(candidateEducationDetailsMapper.getMarksType());
			candidateEducationDetails.setSpecialization(candidateEducationDetailsMapper.getSpecialization());
			candidateEducationDetails.setUniversity(candidateEducationDetailsMapper.getUniversity());
			candidateEducationDetails.setYearOfPassing(candidateEducationDetailsMapper.getYearOfPassing());
			candidateEducationDetails.setUserId(candidateEducationDetailsMapper.getUserId());
			candidateEducationDetails.setLiveInd(true);
			candidateEducationDetails.setCreationDate(new Date());
			candidateEducationDetails.setDocumentId(candidateEducationDetailsMapper.getDocumentId());
			candidateEducationDetails.setDocumentTypeId(candidateEducationDetailsMapper.getDocumentTypeId());

			CandidateEducationDetails candidateEducation = candidateEducationDetailsRepository
					.save(candidateEducationDetails);
			id = candidateEducation.getId();
		}
		return id;
	}

	@Override
	public CandidateEducationDetailsMapper getEducationDetails(String id) {
		CandidateEducationDetails candidateEducationDetails = candidateEducationDetailsRepository.getById(id);
		CandidateEducationDetailsMapper candidateEducationDetailsMapper = new CandidateEducationDetailsMapper();
		if (null != candidateEducationDetails) {
			candidateEducationDetailsMapper.setId(candidateEducationDetails.getId());
			candidateEducationDetailsMapper.setCandidateId(candidateEducationDetails.getCandidateId());
			candidateEducationDetailsMapper.setCourseName(candidateEducationDetails.getCourseName());
			candidateEducationDetailsMapper.setCourseType(candidateEducationDetails.getCourseType());
			// candidateEducationDetailsMapper.setEducationType(candidateEducationDetails.getEducationType());
			if (!StringUtils.isEmpty(candidateEducationDetails.getEducationType())) {
				EducationType educationType = educationTypeRepository
						.findByEducationTypeId(candidateEducationDetails.getEducationType());
				if (null != educationType) {
					candidateEducationDetailsMapper.setEducationTypeId(educationType.getEducationTypeId());
					candidateEducationDetailsMapper.setEducationType(educationType.getEducationType());
				}
			}
			candidateEducationDetailsMapper.setMarksSecured(candidateEducationDetails.getMarksSecured());
			candidateEducationDetailsMapper.setSpecialization(candidateEducationDetails.getSpecialization());
			candidateEducationDetailsMapper.setUniversity(candidateEducationDetails.getUniversity());
			candidateEducationDetailsMapper.setYearOfPassing(candidateEducationDetails.getYearOfPassing());
			candidateEducationDetailsMapper.setMarksType(candidateEducationDetails.getMarksType());
			candidateEducationDetailsMapper.setUserId(candidateEducationDetails.getUserId());
			candidateEducationDetailsMapper
					.setCreationDate(Utility.getISOFromDate(candidateEducationDetails.getCreationDate()));
			candidateEducationDetailsMapper.setDocumentId(candidateEducationDetails.getDocumentId());
			candidateEducationDetailsMapper.setUserId(candidateEducationDetails.getUserId());
			// candidateEducationDetailsMapper.setDocumentTypeId(candidateEducationDetails.getDocumentTypeId());
			if (!StringUtils.isEmpty(candidateEducationDetails.getDocumentTypeId())) {
				DocumentType documentType = documentTypeRepository
						.getTypeDetails(candidateEducationDetails.getDocumentTypeId());
				if (null != documentType) {
					candidateEducationDetailsMapper.setDocumentTypeId(documentType.getDocument_type_id());
					candidateEducationDetailsMapper.setDocumentTypeName(documentType.getDocumentTypeName());
				}
			}
		}

		return candidateEducationDetailsMapper;
	}

	@Override
	public List<CandidateEducationDetailsMapper> getCandidateEducationDetails(String candidateId) {
		List<CandidateEducationDetailsMapper> resultList = new ArrayList<>();
		List<CandidateEducationDetails> educationalList = candidateEducationDetailsRepository
				.getCandidateEducationDetailsById(candidateId);
		if (null != educationalList && !educationalList.isEmpty()) {
			return resultList = educationalList.stream().map(educationalDetails->{
				CandidateEducationDetailsMapper candidateEducationDetailsMapper = getEducationDetails(
						educationalDetails.getId());
				if (null != candidateEducationDetailsMapper) {
					return candidateEducationDetailsMapper;
				}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public List<CandidateEducationDetailsMapper> getCandidateEducationDetailsByUserId(String userId) {
		List<CandidateEducationDetails> educationalList = candidateEducationDetailsRepository
				.getCandidateEducationDetailsByuserId(userId);
		if (null != educationalList && !educationalList.isEmpty()) {
       return educationalList.stream().map(educationalDetails->{
		   CandidateEducationDetailsMapper candidateEducationDetailsMapper = getEducationDetails(
						educationalDetails.getId());
				if (null != candidateEducationDetailsMapper) {
					return candidateEducationDetailsMapper;
				}
				return null;
			}).collect(Collectors.toList());

		}

		return null;
	}

	@Override
	public String saveCandidateEmployment(CandidateEmploymentHistoryMapper candidateEmploymentHistoryMapper) {
		String id = null;
		if (candidateEmploymentHistoryMapper.getCandidateId() != null) {
			CandidateEmploymentHistory candidateEmploymentHistory = new CandidateEmploymentHistory();
			candidateEmploymentHistory.setCompanyName(candidateEmploymentHistoryMapper.getCompanyName());
			// candidateEmploymentHistory.setTenure(candidateEmploymentHistoryMapper.getTenure());
			candidateEmploymentHistory.setCandidateId(candidateEmploymentHistoryMapper.getCandidateId());
			candidateEmploymentHistory.setCurrency(candidateEmploymentHistoryMapper.getCurrency());
			candidateEmploymentHistory.setSalary(candidateEmploymentHistoryMapper.getSalary());
			candidateEmploymentHistory.setSalaryType(candidateEmploymentHistoryMapper.getSalaryType());
			candidateEmploymentHistory.setDescription(candidateEmploymentHistoryMapper.getDescription());
			try {
				if (null != candidateEmploymentHistoryMapper.getStartDate()) {
					candidateEmploymentHistory.setStartDate(
							Utility.getDateFromISOString(candidateEmploymentHistoryMapper.getStartDate()));

				}
				if (null != candidateEmploymentHistoryMapper.getEndDate()) {
					candidateEmploymentHistory
							.setEndDate(Utility.getDateFromISOString(candidateEmploymentHistoryMapper.getEndDate()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			candidateEmploymentHistory.setLiveInd(true);
			candidateEmploymentHistory.setCreationDate(new Date());
			candidateEmploymentHistory.setDocumentId(candidateEmploymentHistoryMapper.getDocumentId());
			candidateEmploymentHistory.setDocumentTypeId(candidateEmploymentHistoryMapper.getDocumentTypeId());
			candidateEmploymentHistory.setDesignation(candidateEmploymentHistoryMapper.getDesignationTypeId());

			CandidateEmploymentHistory candidateEmployment = candidateEmploymentHistoryRepository
					.save(candidateEmploymentHistory);
			id = candidateEmployment.getId();
		}
		return id;
	}

	@Override
	public CandidateEmploymentHistoryMapper getEmploymentHisory(String id) {
		CandidateEmploymentHistory employementHistory = candidateEmploymentHistoryRepository.getById(id);
		CandidateEmploymentHistoryMapper employmentHistoryMapper = new CandidateEmploymentHistoryMapper();
		if (null != employementHistory) {
			employmentHistoryMapper.setId(employementHistory.getId());
			employmentHistoryMapper.setCandidateId(employementHistory.getCandidateId());
			employmentHistoryMapper.setCompanyName(employementHistory.getCompanyName());
			employmentHistoryMapper.setDescription(employementHistory.getDescription());
			employmentHistoryMapper.setCreationDate(Utility.getISOFromDate(employementHistory.getCreationDate()));
			employmentHistoryMapper.setDocumentId(employementHistory.getDocumentId());
			employmentHistoryMapper.setDocumentTypeId(employementHistory.getDocumentTypeId());
			if (null != employementHistory.getStartDate()) {
				employmentHistoryMapper.setStartDate(Utility.getISOFromDate(employementHistory.getStartDate()));

			} else {
				employmentHistoryMapper.setStartDate("");
			}
			if (null != employementHistory.getEndDate()) {
				employmentHistoryMapper.setEndDate(Utility.getISOFromDate(employementHistory.getEndDate()));

			} else {
				employmentHistoryMapper.setEndDate("");
			}
			employmentHistoryMapper.setSalary(employementHistory.getSalary());
			employmentHistoryMapper.setSalaryType(employementHistory.getSalaryType());
		}
		if (!StringUtils.isEmpty(employementHistory.getDesignation())) {
			Designation designation = designationRepository
					.findByDesignationTypeId(employementHistory.getDesignation());
			if (null != designation) {
				employmentHistoryMapper.setDesignationTypeId(designation.getDesignationTypeId());
				employmentHistoryMapper.setDesignationType(designation.getDesignationType());
			} else {
				employmentHistoryMapper.setDesignationType(employementHistory.getDesignation());
			}
		}
		employmentHistoryMapper.setCurrency(employementHistory.getCurrency());
		return employmentHistoryMapper;
	}

	@Override
	public List<CandidateEmploymentHistoryMapper> getCandidateEmploymentHisory(String candidateId) {
		List<CandidateEmploymentHistoryMapper> resultList = new ArrayList<>();
		List<CandidateEmploymentHistory> employmentlList = candidateEmploymentHistoryRepository
				.getCandidateEmploymentHistoryById(candidateId);
		if (null != employmentlList && !employmentlList.isEmpty()) {
         return resultList = employmentlList.stream().map(candidateEmploymentHistory->{
			 CandidateEmploymentHistoryMapper candidateEmploymentHistoryMapper = getEmploymentHisory(
						candidateEmploymentHistory.getId());
				if (null != candidateEmploymentHistoryMapper) {
					return candidateEmploymentHistoryMapper;

				}
				return null;
			}).collect(Collectors.toList());

		}

		return resultList;
	}

	@Override
	public CandidateEducationDetailsMapper updateCandidateEducationalDetails(
			CandidateEducationDetailsMapper candidateEducationDetailsMapper) {
		CandidateEducationDetailsMapper resultMapper = null;
		CandidateEducationDetails candidateEducationDetails = candidateEducationDetailsRepository
				.getById(candidateEducationDetailsMapper.getId());

		if (0 != candidateEducationDetailsMapper.getMarksSecured())
			candidateEducationDetails.setMarksSecured(candidateEducationDetailsMapper.getMarksSecured());
		if (0 != candidateEducationDetailsMapper.getYearOfPassing())
			candidateEducationDetails.setYearOfPassing(candidateEducationDetailsMapper.getYearOfPassing());
		if (null != candidateEducationDetailsMapper.getCourseName())
			candidateEducationDetails.setCourseName(candidateEducationDetailsMapper.getCourseName());
		if (null != candidateEducationDetailsMapper.getCourseType())
			candidateEducationDetails.setCourseType(candidateEducationDetailsMapper.getCourseType());
		if (null != candidateEducationDetailsMapper.getEducationTypeId())
			candidateEducationDetails.setEducationType(candidateEducationDetailsMapper.getEducationTypeId());
		if (null != candidateEducationDetailsMapper.getSpecialization())
			candidateEducationDetails.setSpecialization(candidateEducationDetailsMapper.getSpecialization());
		if (null != candidateEducationDetailsMapper.getUniversity())
			candidateEducationDetails.setUniversity(candidateEducationDetailsMapper.getUniversity());
		if (null != candidateEducationDetailsMapper.getMarksType())
			candidateEducationDetails.setMarksType(candidateEducationDetailsMapper.getMarksType());
		/*
		 * if(null != candidateEducationDetailsMapper.getUserId())
		 * candidateEducationDetails.setUserId(candidateEducationDetailsMapper.getUserId
		 * ());
		 */

		candidateEducationDetailsRepository.save(candidateEducationDetails);

		resultMapper = getEducationDetails(candidateEducationDetailsMapper.getId());

		return resultMapper;
	}

	@Override
	public void deleteCandidateEducationDetailsById(String id) {
		CandidateEducationDetails candidateEducationDetails = candidateEducationDetailsRepository.getById(id);
		if (null != candidateEducationDetails) {
			candidateEducationDetails.setLiveInd(true);
			candidateEducationDetailsRepository.save(candidateEducationDetails);
		}

	}

	@Override
	public List<CandidateViewMapper> getCandidateDetailsByNameAndOrgId(String fullName, String orgId) {
		List<CandidateDetails> list = candidateDetailsRepository
				.findByFullNameContainingAndLiveIndAndOrganizationId(fullName, true, orgId);
		if (null != list && !list.isEmpty()) {
           return list.stream().map(candidateDetails->{
			   CandidateViewMapper candidateMapper = getCandidateDetailsById(candidateDetails.getCandidateId());
				if (null != candidateMapper) {
					return candidateMapper;

				}
				return null;
			}).collect(Collectors.toList());

		}
		return null;
	}

	@Override
	public CandidateBankDetailsMapper getBankDetails(String id) {

		CandidateBankDetails bankDetails = candidateBankDetailsRepository.getById(id);
		CandidateBankDetailsMapper bankDetailsMapper = new CandidateBankDetailsMapper();

		if (null != bankDetails) {
			bankDetailsMapper.setId(bankDetails.getId());
			bankDetailsMapper.setBankName(bankDetails.getBankName());
			bankDetailsMapper.setAccountHolderName(bankDetails.getAccountHolderName());

			bankDetailsMapper.setBranchName(bankDetails.getBranchName());
			bankDetailsMapper.setCandidateId(bankDetails.getCandidateId());
			bankDetailsMapper.setIfscCode(bankDetails.getIfscCode());
			bankDetailsMapper.setAccountNo(bankDetails.getAccountNo());
			bankDetailsMapper.setCreationDate(Utility.getISOFromDate(bankDetails.getCreationDate()));
			bankDetailsMapper.setDefaultInd(bankDetails.isDefaultInd());

		}

		return bankDetailsMapper;
	}

	@Override
	public List<CandidateBankDetailsMapper> getCandidateBankDetails(String candidateId) {
		List<CandidateBankDetailsMapper> resultList = new ArrayList<>();
		List<CandidateBankDetails> bankDetailsList = candidateBankDetailsRepository.getBankDetailsById(candidateId);
		if (null != bankDetailsList && !bankDetailsList.isEmpty()) {
			return resultList = bankDetailsList.stream().map(bankDetails->{
				CandidateBankDetailsMapper bankDetailsMapper = getBankDetails(bankDetails.getId());
				if (null != bankDetailsMapper) {
					return bankDetailsMapper;
				}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public CandidateBankDetailsMapper updateBankDetails(CandidateBankDetailsMapper bankDetailsMapper) {
		CandidateBankDetails bankDetails = candidateBankDetailsRepository.getById(bankDetailsMapper.getId());

		if (null != bankDetailsMapper.getBankName())
			bankDetails.setBankName(bankDetailsMapper.getBankName());
		if (null != bankDetailsMapper.getAccountHolderName())
			bankDetails.setAccountHolderName(bankDetailsMapper.getAccountHolderName());
		if (null != bankDetailsMapper.getBranchName())
			bankDetails.setBranchName(bankDetailsMapper.getBranchName());
		if (null != bankDetailsMapper.getIfscCode())
			bankDetails.setIfscCode(bankDetailsMapper.getIfscCode());
		if (null != bankDetailsMapper.getAccountNo())
			bankDetails.setAccountNo(bankDetailsMapper.getAccountNo());
		if (false != bankDetailsMapper.isDefaultInd()) {
			CandidateBankDetails bankDetails1 = candidateBankDetailsRepository
					.getByCandidateIdAndDefaultInd(bankDetails.getCandidateId());
			if (null != bankDetails1) {
				bankDetails1.setDefaultInd(false);
				candidateBankDetailsRepository.save(bankDetails1);
			}
			bankDetails.setDefaultInd(bankDetailsMapper.isDefaultInd());
		}
		candidateBankDetailsRepository.save(bankDetails);

		CandidateBankDetailsMapper resultMapper = getBankDetails(bankDetailsMapper.getId());

		return resultMapper;
	}

	@Override
	public List<CandidateViewMapper> getCandidateDetailsBySkillAndOrgId(String skill, String orgId) {

		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		List<DefinationDetails> list = definationRepository.getByNameAndOrgId(skill, orgId);
		if (null != list && !list.isEmpty()) {
			for (DefinationDetails definationDetails : list) {
				List<SkillSetDetails> skilllist = skillSetRepository
						.getSkillLinkBySkill(definationDetails.getDefinationId());
				System.out.println("@@@@@@@@@@@@^^^^^^^^^^^^" + skilllist.toString());
				System.out.println("skill%%%%%%" + skill);
				if (null != skilllist && !skilllist.isEmpty()) {

					for (SkillSetDetails skillSetDetails : skilllist) {
						CandidateViewMapper candidateMapper = getCandidateDetailsById(skillSetDetails.getCandidateId());
						if (null != candidateMapper) {
							resultList.add(candidateMapper);

						}
					}

				}
			}
		}
		return resultList;
	}

	@Override
	public ByteArrayInputStream exportCandidateListToExcel(List<CandidateViewMapper> candidateList) {
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
		for (int i = 0; i < candidate_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(candidate_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != candidateList && !candidateList.isEmpty()) {
			for (CandidateViewMapper candidate : candidateList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(candidate.getCandidateId());

				row.createCell(1).setCellValue(candidate.getFirstName());
				row.createCell(2).setCellValue(candidate.getMiddleName());
				row.createCell(3).setCellValue(candidate.getLastName());
				row.createCell(4).setCellValue(candidate.getMobileNumber());
				row.createCell(5).setCellValue(candidate.getPhoneNumber());
				row.createCell(6).setCellValue(candidate.getEmailId());
				row.createCell(7).setCellValue(candidate.getLinkedin());

				row.createCell(8).setCellValue(candidate.getNotes());
				row.createCell(9).setCellValue(candidate.getRoleType());
				row.createCell(10).setCellValue(candidate.getAvailableDate());
				// row.createCell(11).setCellValue(candidate.getSkillList());

			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < candidate_headings.length; i++) {
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

	public String saveCandidateBankDetails(CandidateBankDetailsMapper candidateBankDetailsMapper) {
		String id = null;
		if (null != candidateBankDetailsMapper.getCandidateId()) {

			CandidateBankDetails bankDetails1 = candidateBankDetailsRepository
					.getByCandidateIdAndDefaultInd(candidateBankDetailsMapper.getCandidateId());
			if (null != bankDetails1) {
				bankDetails1.setDefaultInd(false);
				candidateBankDetailsRepository.save(bankDetails1);
			}

			CandidateBankDetails bankDetails = new CandidateBankDetails();
			bankDetails.setCandidateId(candidateBankDetailsMapper.getCandidateId());

			bankDetails.setAccountHolderName(candidateBankDetailsMapper.getAccountHolderName());
			bankDetails.setBankName(candidateBankDetailsMapper.getBankName());
			bankDetails.setBranchName(candidateBankDetailsMapper.getBranchName());
			bankDetails.setAccountNo(candidateBankDetailsMapper.getAccountNo());
			bankDetails.setIfscCode(candidateBankDetailsMapper.getIfscCode());
			bankDetails.setCreationDate(new Date());
			bankDetails.setLiveInd(true);
			bankDetails.setDefaultInd(true);

			CandidateBankDetails bank = candidateBankDetailsRepository.save(bankDetails);
			id = bank.getId();

		}
		return id;
	}

	@Override
	public void deleteBankDetailsById(String id) {
		CandidateBankDetails bank = candidateBankDetailsRepository.getById(id);
		System.out.println("@@@222" + bank);
		if (null != bank) {
			bank.setLiveInd(false);
			bank.setDefaultInd(false);
			candidateBankDetailsRepository.save(bank);
			// candidateBankDetailsRepository.delete(bank);

		}

	}

	@Override
	public List<CandidateViewMapper> getAllCandidateList(int pageNo, int pageSize) {
		List<CandidateViewMapper> resultMapper = new ArrayList<CandidateViewMapper>();

//		List<Permission> permission = permissionRepository.getUserList();
//		System.out.println(" user$$$$$$$$$$$$==" + permission.toString());
//
//		if (null != permission && !permission.isEmpty()) {
//
//			for (Permission permissionn : permission) {
//
//				List<CandidateViewMapper> mp = candidateService.getCandidateListPageWiseByUserId(permissionn.getUserId(),pageNo, pageSize);
//
//				System.out.println(" userId$$$$$$$$$$$$==" + permissionn.getUserId());
//
//				resultMapper.addAll(mp);
//			}
//
//		}
//		return resultMapper;
		List<String> userId = permissionRepository.getUserList()
				.stream().map(Permission::getUserId)
				.collect(Collectors.toList());
		return candidateService.getCandidateListPageWiseByUserIds(userId,pageNo, pageSize);
	}

	/*
	 * @Override public HashMap getCountList() {
	 * 
	 * List<CandidateDetails> candidateList =
	 * candidateDetailsRepository.findByActive(true); HashMap map = new HashMap();
	 * map.put("candidateDetails", candidateList.size());
	 * 
	 * return map;
	 * 
	 * <<<<<<< HEAD
	 * 
	 * 
	 * @Override public void deleteSkilsset(String id) { if (null != id) {
	 * SkillSetDetails skillSetDetails = skillSetRepository.findOne(id);
	 * System.out.println("@@@sushi" +skillSetDetails); if(null!=skillSetDetails) {
	 * skillSetRepository.delete(skillSetDetails); } }
	 * 
	 * } ======= }
	 */

	@Override
	public HashMap getCountListByuserId(String userId) {
		List<CandidateDetails> candidateList = candidateDetailsRepository.findByUserIdandLiveInd(userId);
		HashMap map = new HashMap();
		map.put("candidateDetails", candidateList.size());

		return map;
	}

	@Override
	public void deleteSkilsset(String id) {
		if (null != id) {
			SkillSetDetails skillSetDetails = skillSetRepository.findBySkillSetDetailsId(id);
			if (null != skillSetDetails) {
				skillSetRepository.delete(skillSetDetails);
				
				SkillCandidateNo SkillCandidateNo = skillCandidateNoRepository.findBySkill(skillSetDetails.getSkillName());
				if(null!=SkillCandidateNo) {
					int no = SkillCandidateNo.getNumber();
					SkillCandidateNo.setNumber(no-1);
					skillCandidateNoRepository.save(SkillCandidateNo);
				}
				
				
			}
		}
	}

	@Override
	public void deleteDocumentById(String documentId) {

		if (null != documentId) {
			DocumentDetails document = documentDetailsRepository.getDocumentDetailsById(documentId);
			document.setLive_ind(false);
			documentDetailsRepository.save(document);
		}
	}

	@Override

	public CandidateTrainingMapper updateCandidateTraining(CandidateTrainingMapper candidateTrainingMapper)
			throws Exception {
		CandidateTraining candidateTraining = candidateTrainingRepository
				.getById(candidateTrainingMapper.getCandidateTrainingId());

		if (null != candidateTrainingMapper.getCourseName())
			candidateTraining.setCourseName(candidateTrainingMapper.getCourseName());
		if (null != candidateTrainingMapper.getGrade())
			candidateTraining.setGrade(candidateTrainingMapper.getGrade());
		if (null != candidateTrainingMapper.getOrganization())
			candidateTraining.setOrganization(candidateTrainingMapper.getOrganization());
		if (null != candidateTrainingMapper.getStartDate())
			candidateTraining.setStartDate(Utility.getDateFromISOString(candidateTrainingMapper.getStartDate()));
		if (null != candidateTrainingMapper.getEndDate())
			candidateTraining.setEndDate(Utility.getDateFromISOString(candidateTrainingMapper.getEndDate()));
		candidateTraining.setCreationDate(new Date());
		if (null != candidateTrainingMapper.getDocumentId())
			candidateTraining.setDocumentId(candidateTrainingMapper.getDocumentId());

		candidateTrainingRepository.save(candidateTraining);

		CandidateTrainingMapper resultMapper = getCandidateTrainings(candidateTraining.getCandidateTrainingId());

		return resultMapper;
	}

	@Override
	public CandidateEmploymentHistoryMapper updateEmploymentHistory(
			CandidateEmploymentHistoryMapper candidateEmploymentHistoryMapper) {

		CandidateEmploymentHistory candidateEmploymentHistory = candidateEmploymentHistoryRepository
				.getById(candidateEmploymentHistoryMapper.getId());

		if (candidateEmploymentHistoryMapper.getCompanyName() != null)
			candidateEmploymentHistory.setCompanyName(candidateEmploymentHistoryMapper.getCompanyName());

		// candidateEmploymentHistory.setTenure(candidateEmploymentHistoryMapper.getTenure());
		if (candidateEmploymentHistoryMapper.getCandidateId() != null)
			candidateEmploymentHistory.setCandidateId(candidateEmploymentHistoryMapper.getCandidateId());
		if (candidateEmploymentHistoryMapper.getCurrency() != null)
			candidateEmploymentHistory.setCurrency(candidateEmploymentHistoryMapper.getCurrency());
		if (candidateEmploymentHistoryMapper.getSalary() != 0)
			candidateEmploymentHistory.setSalary(candidateEmploymentHistoryMapper.getSalary());
		if (candidateEmploymentHistoryMapper.getSalaryType() != null)
			candidateEmploymentHistory.setSalaryType(candidateEmploymentHistoryMapper.getSalaryType());
		if (candidateEmploymentHistoryMapper.getDescription() != null)
			candidateEmploymentHistory.setDescription(candidateEmploymentHistoryMapper.getDescription());
		if (candidateEmploymentHistoryMapper.getDesignationTypeId() != null)
			candidateEmploymentHistory.setDesignation(candidateEmploymentHistoryMapper.getDesignationTypeId());

		try {
			if (null != candidateEmploymentHistoryMapper.getStartDate()) {
				candidateEmploymentHistory
						.setStartDate(Utility.getDateFromISOString(candidateEmploymentHistoryMapper.getStartDate()));

			}

			if (null != candidateEmploymentHistoryMapper.getEndDate()) {
				candidateEmploymentHistory
						.setEndDate(Utility.getDateFromISOString(candidateEmploymentHistoryMapper.getEndDate()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		candidateEmploymentHistory.setCreationDate(new Date());
		// candidateEmploymentHistory.setDocumentId(candidateEmploymentHistoryMapper.getDocumentId());
		// candidateEmploymentHistory.setDocumentTypeId(candidateEmploymentHistoryMapper.getDocumentTypeId());

		CandidateEmploymentHistoryMapper resultMapper = getEmploymentHisory(candidateEmploymentHistoryMapper.getId());
		return resultMapper;
	}

	@Override
	public List<CandidateViewMapper> getCandidateListByName(String name) {
		List<CandidateDetails> list = candidateDetailsRepository.getCandidateListByFullName(name);
		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		if (null != list && !list.isEmpty()) {
			return list.stream().map(candidateDetails->{
				CandidateViewMapper candidateMapper = getCandidateDetailsById(candidateDetails.getCandidateId());
				if (null != candidateMapper) {
					return candidateMapper;

				}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public List<String> getSkillSetOfCandidatesOfUser(String orgId) {

		Set<String> skillSet = new HashSet<>();
		List<String> skills = null;
		List<List<CandidateDetails>> list = new ArrayList<>();
		List<CandidateDetails> contactList = new ArrayList<>();
		List<EmployeeDetails> employeeList = employeeRepository.getEmployeesByOrgId(orgId);
		if (null != employeeList && !employeeList.isEmpty()) {
			for (EmployeeDetails employeeDetails : employeeList) {
				contactList = candidateDetailsRepository.getCandidateByUserId(employeeDetails.getEmployeeId());

				list.add(contactList);
				System.out.println("contactList@@@@@@@@" + list.size());
				;
			}
		}

		if (null != list && !list.isEmpty()) {

			skillSet = list.stream()
					.flatMap(li -> li.stream().map(a -> skillSetRepository.getSkillSetById(a.getCandidateId()))
							.filter(d -> (d != null && !d.isEmpty()))
							.flatMap(a -> a.stream().map(c -> c.getSkillName())))
					.collect(Collectors.toSet());

			/*
			 * for(CandidateDetails candidateDetails : list) {
			 * 
			 * List<SkillSetDetails> skillList =
			 * skillSetRepository.getSkillSetById(candidateDetails.getCandidateId());
			 * if(null !=skillList && !skillList.isEmpty()) { for(SkillSetDetails skill :
			 * skillList) { skillSet.add(skill.getSkillName());
			 * 
			 * System.out.println("skilset@@@@"+skillSet); } } }
			 */
		}

		if (null != skillSet && !skillSet.isEmpty()) {
			skills = new ArrayList<>(skillSet);
		}

		return skills;
	}

	@Override
	public List<CandidateViewMapper> filterListOfCandidateBasedOnRecruitment(String skillName, String orgId,
			String recruitmentId) throws Exception {

		/*
		 * Date previousDate =
		 * Utility.getUtilDateByLocalDate(Utility.getLocalDateByDate(availableDate).
		 * minusDays(Math.round(7))); Date availability =
		 * Utility.getUtilDateByLocalDate(Utility.getLocalDateByDate(availableDate).
		 * plusDays(Math.round(1)));
		 */
		OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
				.getRecruitmentDetailsByRecruitId(recruitmentId);
		System.out.println("DATE sk>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ Utility.removeTime(opportunityRecruitDetails.getAvailable_date()));

		List<CandidateDetails> candidateList = candidateDetailsRepository
				.getCandidateDetailsBasedOnrectuitmentDetails(orgId);

		List<CandidateViewMapper> mapperList = new ArrayList<>();

		if (null != candidateList && !candidateList.isEmpty()) {

			for (CandidateDetails candidateDetails1 : candidateList) {
				System.out.println("candidateId..." + candidateDetails1.getCandidateId());

				List<CandidateAddressLink> candidateAddressLinkList = candidateAddressLinkRepository
						.getAddressListByCandidateId(candidateDetails1.getCandidateId());

				System.out.println("candidateId=" + candidateDetails1.getCandidateId() + "skill=" + skillName);

				DefinationDetails definationList1 = definationRepository.getBySkillNameAndLiveInd(skillName);
				if (null != definationList1) {
					SkillSetDetails skillSetDetails = skillSetRepository
							.getCandidateBySkill(definationList1.getDefinationId(), candidateDetails1.getCandidateId());

				//	System.out.println("skill details@@@@@@@@@@@@@@@@@@@@@@@" + skillSetDetails);
					if (null != skillSetDetails) {
						System.out.println("skillName....." + skillName + "&&&&&" + "candidateId......"
								+ skillSetDetails.getCandidateId());

//						CandidateDetails candidateDetails1 = candidateDetailsRepository
//								.getcandidateDetailsById(skillSetDetails.getCandidateId());
					//	System.out.println(
					//			"candidateDetails1@@@@@@@@@@@@@@@@@@@@@@" + candidateDetails1.getCandidateId());
						CandidateViewMapper candidateViewMapper = new CandidateViewMapper();
						// candidateViewMapper.setFirstName(candidateDetails1.getFirstName());
						candidateViewMapper.setFullName(candidateDetails1.getFullName());
						candidateViewMapper.setUserId(candidateDetails1.getUserId());
						candidateViewMapper.setCandidateId(candidateDetails1.getCandidateId());
						candidateViewMapper.setSkillName(skillSetDetails.getSkillName());
						candidateViewMapper.setBilling(candidateDetails1.getBilling());
						candidateViewMapper.setCurrency(candidateDetails1.getCurrency());

						candidateViewMapper.setCostType(candidateDetails1.getCostType());
						candidateViewMapper.setCountry(candidateDetails1.getCountry());
						candidateViewMapper.setCategory(candidateDetails1.getCategory());
						candidateViewMapper.setWorkPreference(candidateDetails1.getWorkPreferance());
						candidateViewMapper.setWorkType(candidateDetails1.getWorkType());

						if (!StringUtils.isEmpty(candidateDetails1.getPartnerId())) {
							PartnerDetails partnerDetails = partnerDetailsRepository
									.getPartnerDetailsById(candidateDetails1.getPartnerId());
							if (null != partnerDetails) {
								candidateViewMapper.setPartnerName(partnerDetails.getPartnerName());
							} else {
								candidateViewMapper.setPartnerName("");
								candidateViewMapper.setPartnerId("");
							}
						}
						candidateViewMapper.setCountry(candidateDetails1.getCountry());
						if (!StringUtils.isEmpty(candidateDetails1.getRoleType())) {
							RoleType roleType = roleTypeRepository.findByRoleTypeId(candidateDetails1.getRoleType());
							if (null != roleType) {
								candidateViewMapper.setRoleTypeId(roleType.getRoleTypeId());
								candidateViewMapper.setRoleType(roleType.getRoleType());
							}
						}
						candidateViewMapper.setWorkType(candidateDetails1.getWorkType());
						candidateViewMapper.setWorkLocation(candidateDetails1.getWorkLocation());

						if (null != candidateDetails1.getAvailableDate()) {
							candidateViewMapper
									.setAvailableDate(Utility.getISOFromDate(candidateDetails1.getAvailableDate()));
						}
						candidateViewMapper.setImageId(candidateDetails1.getImageId());

						List<SkillSetDetails> skillList = skillSetRepository
								.getSkillSetById(candidateDetails1.getCandidateId());
						List<String> candidateSkill = new ArrayList<>();
						if (null != skillList && !skillList.isEmpty()) {
							for (SkillSetDetails SkillSetDetails : skillList) {
								DefinationDetails definationList12 = definationRepository
										.findByDefinationId(SkillSetDetails.getSkillName());
								if (null != definationList12) {
									candidateSkill.add(definationList12.getName());
									// List<String> candidateSkill = (skillList.stream().map(li ->
									// li.getSkillName()).collect(Collectors.toList()));
								}
							}
							candidateViewMapper.setSkillList(candidateSkill);
						}
						List<AddressMapper> addressList = new ArrayList<AddressMapper>();
						if (null != candidateAddressLinkList && !candidateAddressLinkList.isEmpty()) {

							for (CandidateAddressLink candidateAddressLink : candidateAddressLinkList) {
								AddressDetails addressDetails = addressRepository
										.getAddressDetailsByAddressId(candidateAddressLink.getAddressId());

								AddressMapper addressMapper = new AddressMapper();
								if (null != addressDetails) {

									addressMapper.setAddress1(addressDetails.getAddressLine1());
									addressMapper.setAddress2(addressDetails.getAddressLine2());
									addressMapper.setAddressType(addressDetails.getAddressType());
									addressMapper.setPostalCode(addressDetails.getPostalCode());
									addressMapper.setStreet(addressDetails.getStreet());
									addressMapper.setCity(addressDetails.getCity());
									addressMapper.setTown(addressDetails.getTown());
									addressMapper.setCountry(addressDetails.getCountry());
									addressMapper.setLatitude(addressDetails.getLatitude());
									addressMapper.setLongitude(addressDetails.getLongitude());
									addressMapper.setState(addressDetails.getState());
									addressMapper.setAddressId(addressDetails.getAddressId());
									addressMapper.setHouseNo(addressDetails.getHouseNo());
									addressList.add(addressMapper);
								}
							}
						}
						candidateViewMapper.setAddress(addressList);
						System.out.println("skb>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>5");
						/*
						 * candidateViewMapper.setSkillList( (skillList.stream().map(li ->
						 * li.getSkillName()).collect( Collectors.toList())));
						 */
						List<DefinationDetails> definationList = definationRepository.getDefinationsOfAdmin(orgId);
						List<String> skillLibraryList = (definationList.stream().map(li -> li.getName())
								.collect(Collectors.toList()));
						ArrayList<String> words = new ArrayList<>();
						String description = opportunityRecruitDetails.getDescription().replace(",", " ");
						int descriptionSkillNo = 0;
						int matchSkillNo = 0;
						if (null != candidateSkill) {
							String[] definationArr = new String[candidateSkill.size()];
							definationArr = candidateSkill.toArray(definationArr);

							float persentageSkill =0;
							if (!StringUtils.isEmpty(description)) {
								// List<String> descriptionList = Arrays.asList(description.split("\\s*,\\s*"));
								List<String> descriptionList = Arrays.asList(description.split(" "));
							//sk	int matchSkillNo = 0;
								for (String libraySkill : skillLibraryList) {
									for (String description1 : descriptionList) {
										if (description1.equalsIgnoreCase(libraySkill)) {
											descriptionSkillNo++;
											System.out.println("name##########" + libraySkill);
										}
									}
								}
								System.out.println("descriptionSkillNo%%%%%%" + descriptionSkillNo);
								System.out.println("descriptionList%%%" + descriptionList.toString());
								for (String word : definationArr) {
									for (String description1 : descriptionList) {
										if (!words.contains(word)) {
											if (description1.equalsIgnoreCase(word)) {

												words.add(word);
												matchSkillNo++;
												System.out.println("word>>>>>>>>>>" + word);
											}
										}
									}
									candidateViewMapper.setMatchSkill(words);
								}
							
								System.out.println("matchSkillNo%%%%%%%%%%%%%%%%%%%" + matchSkillNo);
								int candiSkill = skillList.size();
								System.out.println("candiSkill%%%%%%%%%%%%%%%%%%%%%" + candiSkill);
							//sk	float persentageSkill = (float) (matchSkillNo * 1.0 / candiSkill) * 100;
								 persentageSkill = (float) (matchSkillNo * 1.0 / candiSkill) * 100;
								System.out.println("persentageSkill>>>>>>>>" + persentageSkill);
							}
							
							//new code start herer
							float expYear = 0;
										if (!StringUtils.isEmpty(opportunityRecruitDetails.getExperience())) {
											expYear = Float.parseFloat((opportunityRecruitDetails.getExperience())
													.replaceAll("[^0-9]", ""));
											System.out.println("Jov exp==" + expYear);
										}
										double candidateBilling = 0;
										if (!StringUtils.isEmpty(candidateDetails1.getBilling())) {
											 candidateBilling = Double.parseDouble(candidateDetails1.getBilling());
										}

										int totalSuggScore = 0;
										int billingScore = 0;
										int exprienceScore = 0;
										int availabilityScore = 0;
										int locationScore = 0;
										float skillScore = 0;

										System.out.println(
												"the recruiter billing is : " + opportunityRecruitDetails.getBilling()
														+ " and the candidate billing is :" + candidateBilling);

										if (opportunityRecruitDetails.getBilling() >= candidateBilling) {
											billingScore = 100;
										} else if (opportunityRecruitDetails.getBilling()
												+ opportunityRecruitDetails.getBilling() * 0.05 >= candidateBilling) {
											billingScore = 90;
										} else if (opportunityRecruitDetails.getBilling()
												+ opportunityRecruitDetails.getBilling() * 0.1 >= candidateBilling) {
											billingScore = 80;
										} else if (opportunityRecruitDetails.getBilling()
												+ opportunityRecruitDetails.getBilling() * 0.15 >= candidateBilling) {
											billingScore = 60;
										} else if (opportunityRecruitDetails.getBilling()
												+ opportunityRecruitDetails.getBilling() * 0.2 >= candidateBilling) {
											billingScore = 30;
										}

										System.out.println("billingScore" + billingScore);
										System.out.println("job_billing=" + opportunityRecruitDetails.getBilling()
												+ "||" + "candidateBilling=" + candidateBilling);

										if (candidateDetails1.getExperience() >= (expYear)) {
											exprienceScore = 100;
										} else if (candidateDetails1.getExperience() == (expYear - 1)) {
											exprienceScore = 80;
										} else if (candidateDetails1.getExperience() == (expYear - 2)) {
											exprienceScore = 50;
										}

										System.out.println("Candidate Exprience=" + candidateDetails1.getExperience()
												+ "||||" + "Jov Exprience=" + expYear + "candiName="
												+ candidateDetails1.getFirstName());
										System.out.println("JobName%%%%%" + opportunityRecruitDetails.getName());
										System.out.println("exprienceScore" + exprienceScore);

										int diffDays = 60;
										System.out.println("after diffDays=" + diffDays);
										if (null != candidateDetails1.getAvailableDate()) {
											long diffMilliSec = candidateDetails1.getAvailableDate().getTime()
													- opportunityRecruitDetails.getAvailable_date().getTime();
											diffDays = (int) (diffMilliSec / (1000 * 60 * 60 * 24));
										}
										System.out.println("before diffDays=" + diffDays);
										if (diffDays < 15) {
											availabilityScore = 100;
										} else if (diffDays >= 15 && diffDays < 30) {
											availabilityScore = 90;
										} else if (diffDays >= 30 && diffDays < 45) {
											availabilityScore = 80;
										} else if (diffDays >= 45 && diffDays < 60) {
											availabilityScore = 50;
										} else if (diffDays > 60) {
											availabilityScore = 20;
										}
										System.out.println("candiAvi=" + candidateDetails1.getAvailableDate() + "||"
												+ "JobAvi=" + opportunityRecruitDetails.getAvailable_date());
										System.out.println("diffDays==" + diffDays);

										AddressDetails addressDetails = null;
										if (candidateAddressLinkList.size() != 0) {
											addressDetails = addressRepository.getAddressDetailsByAddressId(
													candidateAddressLinkList.get(0).getAddressId());
											// System.out.println("candiAddId=" + addressDetails.getAddressId());
										}
										List<RecruitmentAddressLink> recruitmentAddressLink = recruitmentAddressLinkRepository
												.getAddressListByRecruitmentId(recruitmentId);
										AddressDetails recruitAddress = null;
										if (recruitmentAddressLink.size() != 0) {
											recruitAddress = addressRepository.getAddressDetailsByAddressId(
													recruitmentAddressLink.get(0).getAddressId());
											System.out.println("recruitAddId=" + recruitAddress.getAddressId());
											System.out.println("candiLatitude=" + addressDetails.getLatitude()
													+ ",candiLongitude" + addressDetails.getLongitude()
													+ "||jobLatitude" + recruitAddress.getLatitude() + "jobLongitude"
													+ recruitAddress.getLongitude());
										}

										int distance = 0;
										double candiLatitude = 0;
										double candiLongitude = 0;
										double jobLatitude;
										double jobLongitude;
										final double pi = Math.PI;

										if (null != addressDetails && addressDetails.getLatitude() != null
												&& !addressDetails.getLatitude().equals("")
												&& addressDetails.getLongitude() != null
												&& !addressDetails.getLongitude().equals("") && null != recruitAddress
												&& recruitAddress.getLatitude() != null
												&& !recruitAddress.getLatitude().equals("")
												&& recruitAddress.getLongitude() != null
												&& !recruitAddress.getLongitude().equals("")) {
											System.out.println("addressId%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"
													+ addressDetails.getAddressId());

											if (!StringUtils.isEmpty(addressDetails.getLatitude())
													&& !StringUtils.isEmpty(addressDetails.getLongitude())) {
												candiLatitude = Double.parseDouble(addressDetails.getLatitude());
												candiLongitude = Double.parseDouble(addressDetails.getLongitude());
												jobLatitude = Double.parseDouble(recruitAddress.getLatitude());
												jobLongitude = Double.parseDouble(recruitAddress.getLongitude());
												System.out.println("candiLatitude=" + candiLatitude + ",candiLongitude"
														+ candiLongitude + "||jobLatitude" + jobLatitude
														+ "jobLongitude" + jobLongitude);
												distance = (int) (((Math.acos(Math.sin(candiLatitude * pi / 180)
														* Math.sin(jobLatitude * pi / 180)
														+ Math.cos(candiLatitude * pi / 180)
																* Math.cos(jobLatitude * pi / 180)
																* Math.cos((candiLongitude - jobLongitude) * pi / 180)))
														* 180 / pi) * 60 * 1.1515 * 1.609344);
												System.out.println("distance=" + distance);
												candidateViewMapper.setDistance(distance);
											}
											if (distance == 0) {
												locationScore = 100;
											} else if (distance > 150) {
												locationScore = 0;
											} else if (distance > 100) {
												locationScore = 0;
											}
										} else {
											locationScore = 0;
										}
										System.out.println("88888888888888888888888888888888888888888888888888888888888888888888888888888888888888");
										System.out.println("locationScore" + locationScore);
										System.out.println("distance=" + distance);
										System.out.println("candidateName="+candidateDetails1.getFirstName());
										System.out.println("88888888888888888888888888888888888888888888888888888888888888888888888888888888888888");
										/*
										 * if(matchSkillNo!= 0) { skillScore = candiSkill/matchSkillNo*25; }
										 */
										try {
											System.out.println(matchSkillNo + "||" + descriptionSkillNo);
											skillScore = ((float) matchSkillNo / descriptionSkillNo) * 100;
											System.out.println("************************************************");
										} catch (ArithmeticException e) {
											skillScore = 0;
										}
										if (Float.isNaN(skillScore)) {
											skillScore = 0;
										}
										System.out.println("skillScore=" + skillScore);
										System.out.println("matchSkillNo=" + matchSkillNo);
										System.out.println("descriptionSkillNo=" + descriptionSkillNo);
										totalSuggScore = (int) ((billingScore + exprienceScore + availabilityScore
												+ locationScore + skillScore) / 5);
										System.out.println("billingScore=" + billingScore + ",exprienceScore="
												+ exprienceScore + ",availabilityScore=" + availabilityScore
												+ ",locationScore" + locationScore + ",skillScore" + skillScore);
										System.out.println("totalSuggScore" + totalSuggScore);
										if (totalSuggScore >= 95 ) {
											candidateViewMapper.setMatch(" Best Match");
										}else if(totalSuggScore>=80 && totalSuggScore<95){
											candidateViewMapper.setMatch(totalSuggScore + " Suggested");
										}else {
											candidateViewMapper.setMatch(String.valueOf(totalSuggScore));
										}
										candidateViewMapper.setScore(totalSuggScore);
									}
									/*
									 * }else { candidateViewMapper.setMatch(""); }
									 */
							//old	}

						//	}

						//sk}
						// candidateViewMapper.setMsNo(matchSkillNo);
						// System.out.println("matchSkillNo>>>>>>>>>>>>"+matchSkillNo);
						System.out.println("candidateSkill>>>>>>>>>>>>" + skillList.size());

						// int persentageSkill = (matchSkillNo/skillList.size())*100;
						// System.out.println("persentageSkill>>>>>>>>>>>>>>>>"+persentageSkill);

						List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
								.getCandidateList(recruitmentId);
						if (null != recruitmentCandidateLink && !recruitmentCandidateLink.isEmpty()) {
							for (RecruitmentCandidateLink recruitmentCandidateLink1 : recruitmentCandidateLink) {
								if (recruitmentCandidateLink1.getCandidate_id() == candidateDetails1.getCandidateId()) {
									candidateViewMapper.setCandidateInd(true);
								}

							}
						}

						mapperList.add(candidateViewMapper);
					}
				}
			}
		}
		mapperList.sort(Comparator.comparingInt(CandidateViewMapper::getScore).reversed());
		return mapperList;
	}

	@Override
	public List<ActivityMapper> getCandidateActivityByCandidateId(String candidateId) {
		List<ActivityMapper> activityMapperList = new ArrayList<>();

		List<ActivityMapper> eventMapperList = new ArrayList<>();
		List<ActivityMapper> callMapperList = new ArrayList<>();
		List<ActivityMapper> taskMapperList = new ArrayList<>();

		List<CandidateEventLink> eventdetailsList = candidateEventLinkRepository.getEventListByCandidateId(candidateId);

		if (null != eventdetailsList && !eventdetailsList.isEmpty()) {
			for (CandidateEventLink candidateEventLink : eventdetailsList) {

				EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(candidateEventLink.getEventId());
				ActivityMapper eventMapper = new ActivityMapper();

				if (!StringUtils.isEmpty(eventDetails.getEvent_type())) {
					EventType eventType = eventTypeRepository.findByEventTypeId(eventDetails.getEvent_type());
					eventMapper.setType(eventType.getEventType());
					eventMapper.setEventTypeId(eventType.getEventTypeId());
				}
				eventMapper.setStartDate(Utility.getISOFromDate(eventDetails.getStart_date()));
				eventMapper.setEndDate(Utility.getISOFromDate(eventDetails.getEnd_date()));
				eventMapper.setCreationDate(Utility.getISOFromDate(eventDetails.getCreation_date()));
				eventMapper.setTopic(eventDetails.getSubject());
				eventMapper.setStartTime(eventDetails.getStart_time());
				// eventMapper.setRating(eventDetails.getRating());
				eventMapper.setCompletionInd(eventDetails.isComplitionInd());
				eventMapper.setActivity("Event");

				eventMapperList.add(eventMapper);

			}
		}

		List<CallCandidateLink> callList = callCandidateLinkRepository.getCallListByCandidateId(candidateId);

		if (null != callList && !callList.isEmpty()) {

			for (CallCandidateLink callCandidateLink : callList) {
				CallDetails callDetails = callDetailsRepository.getCallDetailsById(callCandidateLink.getCallId());

				ActivityMapper callMapper = new ActivityMapper();

				callMapper.setEndDate(Utility.getISOFromDate(callDetails.getCall_end_date()));
				callMapper.setStartDate(Utility.getISOFromDate(callDetails.getCall_start_date()));
				callMapper.setType(callDetails.getCallType());
				callMapper.setTopic(callDetails.getSubject());
				callMapper.setStartTime(callDetails.getCall_start_time());
				callMapper.setEndTime(callDetails.getCall_end_time());
				callMapper.setCreationDate(Utility.getISOFromDate(callDetails.getCreation_date()));
				callMapper.setActivity("Call");

				callMapperList.add(callMapper);

			}
		}

		List<CandidateTaskLink> taskList = candidateTaskLinkRepository.getTaskListByCandidateId(candidateId);

		if (null != taskList && !taskList.isEmpty()) {

			for (CandidateTaskLink candidateTaskLink : taskList) {

				TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(candidateTaskLink.getTaskId());
				ActivityMapper taskMapper = new ActivityMapper();

				if (!StringUtils.isEmpty(taskDetails.getTask_type())) {
					TaskType taskType = taskTypeRepository.findByTaskTypeId(taskDetails.getTask_type());
					taskMapper.setType(taskType.getTaskType());
					taskMapper.setTaskTypeId(taskType.getTaskTypeId());
				}
				taskMapper.setStartDate(Utility.getISOFromDate(taskDetails.getStart_date()));
				taskMapper.setEndDate(Utility.getISOFromDate(taskDetails.getEnd_date()));
				taskMapper.setPriority(taskDetails.getPriority());
				taskMapper.setTopic(taskDetails.getTask_name());
				taskMapper.setStatus(taskDetails.getTask_status());
				// taskMapper.setRating(taskDetails.getRating());
				taskMapper.setCreationDate(Utility.getISOFromDate(taskDetails.getCreation_date()));
				taskMapper.setActivity("Task");

				taskMapperList.add(taskMapper);

			}
		}

		activityMapperList.addAll(callMapperList);
		activityMapperList.addAll(eventMapperList);
		activityMapperList.addAll(taskMapperList);

		return activityMapperList;
	}

	@Override
	public String candidateExistsByEmail(String userId, String emailId) {
		List<CandidateDetails> list = candidateDetailsRepository.getCandidateByUserIdAndEmailId(userId, emailId);
		String ownerName = null;
		if (list != null && !list.isEmpty()) {
			EmployeeDetails emp = employeeRepository.getEmployeesByuserId(list.get(0).getUserId());
			ownerName = emp.getFullName();
			String middleName = "";
			String lastName = "";
			if (null != emp.getMiddleName()) {

				middleName = emp.getMiddleName();
			}
			if (null != emp.getLastName()) {
				lastName = emp.getLastName();
			}
			ownerName = emp.getFirstName() + " " + middleName + " " + lastName;
		}

		return ownerName;
	}

	@Override
	public DefinationMapper saveCandidateDefination(DefinationMapper definationMapper) {

		DefinationInfo definationInfo = new DefinationInfo();

		definationInfo.setCreation_date(new Date());
		String id = definationInfoRepository.save(definationInfo).getDefination_info_id();

		DefinationDetails definationDetails = new DefinationDetails();
		definationDetails.setDefinationId(id);
		definationDetails.setName(definationMapper.getName());
		definationDetails.setOrg_id(definationMapper.getOrgId());
		definationDetails.setUser_id(definationMapper.getUserId());
		definationDetails.setCreation_date(new Date());
		definationDetails.setLiveInd(true);
		definationDetails.setEditInd(definationMapper.isEditInd());

		DefinationDetailsDelete  definationDetailsDelete=new DefinationDetailsDelete();
		definationDetailsDelete.setOrgId(definationMapper.getOrgId());
		definationDetailsDelete.setUserId(definationMapper.getUserId());
		definationDetailsDelete.setDefinationId(id);
		definationDetailsDelete.setUpdationDate(new Date());
		definationDetailsDelete.setName(definationMapper.getUserId());
		definationDeleteRepository.save(definationDetailsDelete);
		
		SkillCandidateNo skillCandidateNo = new SkillCandidateNo();
		int no = 0;
		skillCandidateNo.setSkill(id);
		skillCandidateNo.setNumber(no);
		skillCandidateNo.setUserId(definationMapper.getUserId());
		skillCandidateNo.setOrganizationId(definationMapper.getOrgId());
		skillCandidateNo.setCreationDate(new Date());
		skillCandidateNoRepository.save(skillCandidateNo);
		
		definationRepository.save(definationDetails);

		return getDefinationDetails(id);
	}

	@Override
	public List<DefinationMapper> getDefinationsOfAdmin(String orgId) {

		List<DefinationDetails> list = definationRepository.getDefinationsOfAdmin(orgId);
		List<DefinationMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {

			list.stream().map(li -> {
				DefinationMapper definationMapper = new DefinationMapper();

				definationMapper.setName(li.getName());
				definationMapper.setDefinationId(li.getDefinationId());
				definationMapper.setEditInd(li.isEditInd());
				definationMapper.setCreationDate(Utility.getISOFromDate(li.getCreation_date()));
				mapperList.add(definationMapper);

				return mapperList;
			}).collect(Collectors.toList());
		}
		 Collections.sort(mapperList, ( m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		
		List<DefinationDetailsDelete> definationDetailsDelete = definationDeleteRepository.findByOrgId(orgId);
		if (null != definationDetailsDelete && !definationDetailsDelete.isEmpty()) {
			Collections.sort(definationDetailsDelete,
					( p1,p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			
			mapperList.get(0).setUpdationDate(Utility.getISOFromDate(definationDetailsDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(definationDetailsDelete.get(0).getUserId());
			if(null!=employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					mapperList.get(0).setUpdatedName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					mapperList.get(0).setUpdatedName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		
		return mapperList;
	}

	@Override
	public List<CandidateDropDownMapper> getCandidateByOrgId(String orgId) {
	
	List<CandidateDetails> candidateList = candidateDetailsRepository.getCandidateListByOrgId(orgId);
	if (null != candidateList && !candidateList.isEmpty()) {
		return candidateList.stream().map(candidate->{
			CandidateDropDownMapper candidateMapper = new CandidateDropDownMapper();
			candidateMapper.setCandidateId(candidate.getCandidateId());
			candidateMapper.setCreationDate(Utility.getISOFromDate(candidate.getCreationDate()));
			candidateMapper.setEmailId(candidate.getEmailId());
			candidateMapper.setLiveInd(candidate.isLiveInd());
			candidateMapper.setOrganizationId(candidate.getOrganizationId());
			candidateMapper.setUserId(candidate.getUserId());
			String middleName = " ";
			String lastName = "";

			if (!StringUtils.isEmpty(candidate.getLastName())) {

				lastName = candidate.getLastName();
			}

			if (candidate.getMiddleName() != null && candidate.getMiddleName().length() > 0) {

				middleName = candidate.getMiddleName();
				candidateMapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
			} else {

				candidateMapper.setFullName(candidate.getFirstName() + " " + lastName);
			}

			return candidateMapper;
		}).collect(Collectors.toList());

	}		
		return null;
	}

	@Override
	public List<CandidateViewMapper> filterListOfCandidateBasedOnRecruitmentByUserId(String skill, String userId) {
		List<CandidateDetails> candidateList = candidateDetailsRepository.getCandidateByUserId(userId);

		List<CandidateViewMapper> mapperList = new ArrayList<>();

		if (null != candidateList && !candidateList.isEmpty()) {

			for (CandidateDetails candidateDetails2 : candidateList) {
				System.out.println("candidateId..." + candidateDetails2.getCandidateId());

				DefinationDetails definationList1 = definationRepository.getBySkillNameAndLiveInd(skill);
				if (null != definationList1) {

					SkillSetDetails skillSetDetails = skillSetRepository
							.getCandidateBySkill(definationList1.getDefinationId(), candidateDetails2.getCandidateId());

					System.out.println("skill details@@@@@@@@@@@@@@@@@@@@@@@" + skillSetDetails);
					if (null != skillSetDetails) {
						System.out.println("skillName....." + skill + "&&&&&" + "candidateId......"
								+ skillSetDetails.getCandidateId());

						CandidateDetails candidateDetails1 = candidateDetailsRepository
								.getcandidateDetailsById(skillSetDetails.getCandidateId());
						System.out.println(
								"candidateDetails1@@@@@@@@@@@@@@@@@@@@@@" + candidateDetails1.getCandidateId());
						CandidateViewMapper candidateViewMapper = new CandidateViewMapper();
						// candidateViewMapper.setFirstName(candidateDetails1.getFirstName());
						candidateViewMapper.setFullName(candidateDetails1.getFullName());
						candidateViewMapper.setUserId(candidateDetails1.getUserId());
						candidateViewMapper.setCandidateId(candidateDetails1.getCandidateId());
						candidateViewMapper.setSkillName(skillSetDetails.getSkillName());
						candidateViewMapper.setBilling(candidateDetails1.getBilling());
						candidateViewMapper.setCurrency(candidateDetails1.getCurrency());

						candidateViewMapper.setWorkPreference(candidateDetails1.getWorkPreferance());
						candidateViewMapper.setWorkType(candidateDetails1.getWorkType());

						candidateViewMapper.setCostType(candidateDetails1.getCostType());
						candidateViewMapper.setCountry(candidateDetails1.getCountry());
						candidateViewMapper.setCategory(candidateDetails1.getCategory());

						if (!StringUtils.isEmpty(candidateDetails1.getPartnerId())) {
							PartnerDetails partnerDetails = partnerDetailsRepository
									.getPartnerDetailsById(candidateDetails1.getPartnerId());
							if (null != partnerDetails) {
								candidateViewMapper.setPartnerName(partnerDetails.getPartnerName());
							} else {
								candidateViewMapper.setPartnerName("");
								candidateViewMapper.setPartnerId("");
							}
						}
						candidateViewMapper.setCountry(candidateDetails1.getCountry());
						if (!StringUtils.isEmpty(candidateDetails1.getRoleType())) {
							RoleType roleType = roleTypeRepository.findByRoleTypeId(candidateDetails1.getRoleType());
							if (null != roleType) {
								candidateViewMapper.setRoleTypeId(roleType.getRoleTypeId());
								candidateViewMapper.setRoleType(roleType.getRoleType());
							}
						}
						candidateViewMapper.setWorkType(candidateDetails1.getWorkType());
						candidateViewMapper.setWorkLocation(candidateDetails1.getWorkLocation());

						if (null != candidateDetails1.getAvailableDate()) {
							candidateViewMapper
									.setAvailableDate(Utility.getISOFromDate(candidateDetails1.getAvailableDate()));
						} else {
							candidateViewMapper.setAvailableDate("");
						}
						candidateViewMapper.setImageId(candidateDetails1.getImageId());

						List<CandidateAddressLink> candidateAddressLinkList = candidateAddressLinkRepository
								.getAddressListByCandidateId(candidateDetails1.getCandidateId());
						List<AddressMapper> addressList = new ArrayList<AddressMapper>();
						if (null != candidateAddressLinkList && !candidateAddressLinkList.isEmpty()) {

							for (CandidateAddressLink candidateAddressLink : candidateAddressLinkList) {
								AddressDetails addressDetails = addressRepository
										.getAddressDetailsByAddressId(candidateAddressLink.getAddressId());

								AddressMapper addressMapper = new AddressMapper();
								if (null != addressDetails) {

									addressMapper.setAddress1(addressDetails.getAddressLine1());
									addressMapper.setAddress2(addressDetails.getAddressLine2());
									addressMapper.setAddressType(addressDetails.getAddressType());
									addressMapper.setPostalCode(addressDetails.getPostalCode());
									addressMapper.setStreet(addressDetails.getStreet());
									addressMapper.setCity(addressDetails.getCity());
									addressMapper.setTown(addressDetails.getTown());
									addressMapper.setCountry(addressDetails.getCountry());
									addressMapper.setLatitude(addressDetails.getLatitude());
									addressMapper.setLongitude(addressDetails.getLongitude());
									addressMapper.setState(addressDetails.getState());
									addressMapper.setAddressId(addressDetails.getAddressId());
									addressMapper.setHouseNo(addressDetails.getHouseNo());
									addressList.add(addressMapper);
								}
							}

						}
						candidateViewMapper.setAddress(addressList);

//					List<SkillSetDetails> skillList = skillSetRepository
//							.getSkillSetById(candidateDetails1.getCandidateId());
//					candidateViewMapper.setSkillList(
//							(skillList.stream().map(li -> li.getSkillName()).collect(Collectors.toList())));

						List<SkillSetDetails> skillList = skillSetRepository
								.getSkillSetById(candidateDetails1.getCandidateId());
						List<String> candidateSkill = new ArrayList<>();
						if (null != skillList && !skillList.isEmpty()) {
							for (SkillSetDetails skillSetDetails1 : skillList) {
								DefinationDetails definationList12 = definationRepository
										.findByDefinationId(skillSetDetails1.getSkillName());
								if (null != definationList12) {
									candidateSkill.add(definationList12.getName());
									// List<String> candidateSkill = (skillList.stream().map(li ->
									// li.getSkillName()).collect(Collectors.toList()));
								}
							}
							candidateViewMapper.setSkillList(candidateSkill);
						}

						mapperList.add(candidateViewMapper);
					}
				}
			}
		}
		return mapperList;
	}

	@Override
	public boolean checkSkillInSkillLibery(String skillName) {
		List<DefinationDetails> definationDetails = definationRepository.findByNameContainingAndLiveInd(skillName,true);
		if (definationDetails.size() > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean checkSkillInSkillLiberyInUpdate(String skillName,String definationId) {
		List<DefinationDetails> definationDetails = definationRepository.findByNameContainingAndDefinationIdNotAndLiveInd(skillName,definationId,true);
		if(definationDetails.size()>0){
			return true;
		}
		return false;	
	}

	@Override
	public List<SkillSetMapper> getWordCloudHistory(String orgId) {

		List<CandidateDetails> candidates = candidateDetailsRepository
				.getCandidateDetailsBasedOnrectuitmentDetails(orgId);
		List<SkillSetMapper> mapperList = new ArrayList<>();
		if (null != candidates && !candidates.isEmpty()) {

			candidates.stream().map(candidate -> skillSetRepository.getSkillSetById(candidate.getCandidateId()))
					.filter(li -> (li != null && !li.isEmpty())).flatMap(skills -> skills.stream()).map(skill -> {
						SkillSetMapper skillSetMapper = new SkillSetMapper();
						List<SkillSetDetails> skills = skillSetRepository.getSkillLinkBySkill(skill.getSkillName());

						DefinationDetails DefinationDetails = definationRepository
								.findByDefinationId(skill.getSkillName());

						System.out.println("times it occured" + skills.size());
						skillSetMapper.setCount(skills.size());
						skillSetMapper.setSkillName(DefinationDetails.getName());
						mapperList.add(skillSetMapper);

						return mapperList;
					}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public String blackListedCandidate(String candidateId) {
		CandidateDetails candidatedetails = candidateDetailsRepository.getcandidateDetailsById(candidateId);
		if (null != candidatedetails) {
			candidatedetails.setBlockListInd(true);
			// CandidateViewMapper.setBlockListInd(candidatedetails.isBlockListInd());
			candidateDetailsRepository.save(candidatedetails);
			return "Successfully Blacklistted";
		}
		return "Sorry Candidate not Found";
	}

	@Override
	public String unBlockCandidate(String candidateId) {
		CandidateDetails candidatedetails = candidateDetailsRepository.getcandidateDetailsById(candidateId);
		if (null != candidatedetails) {
			candidatedetails.setBlockListInd(false);
			candidateDetailsRepository.save(candidatedetails);
			return "Successfully UnBlacklistted";
		}
		return "Sorry Candidate not Found";
	}

@Override
public List<CandidateViewMapper> getcandidateLisstByCategory(String category, String userId, int pageNo, int pageSize) {
Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by("creationDate").descending());
	List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
	Page<CandidateDetails> candidateList = candidateDetailsRepository.getCandidateListByCategoryAndUserIdAndLiveInd(category,userId,paging);
	resultList = candidateList.stream()
    .map(li -> {
        CandidateViewMapper candidateViewMapper = getCandidateDetailsById(li.getCandidateId());
        // Assuming 'list' is the instance of some object providing pagination details
        candidateViewMapper.setPageCount(candidateList.getTotalPages());
        candidateViewMapper.setDataCount(candidateList.getSize());
        candidateViewMapper.setListCount(candidateList.getTotalElements());
        return candidateViewMapper;
    })
    .collect(Collectors.toList());
	
	return resultList;
}

@Override
public List<CandidateViewMapper> getcandidateLisstByCategoryAndUserIds(String category, List<String> userId, int pageNo, int pageSize) {
Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by("creationDate").descending());
	List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
	Page<CandidateDetails> candidateList = candidateDetailsRepository.getCandidateListByCategoryAndUserIdsAndLiveInd(category,userId,paging);
	resultList = candidateList.stream()
    .map(li -> {
        CandidateViewMapper candidateViewMapper = getCandidateDetailsById(li.getCandidateId());
        // Assuming 'list' is the instance of some object providing pagination details
        candidateViewMapper.setPageCount(candidateList.getTotalPages());
        candidateViewMapper.setDataCount(candidateList.getSize());
        candidateViewMapper.setListCount(candidateList.getTotalElements());
        return candidateViewMapper;
    })
    .collect(Collectors.toList());
	
	return resultList;
}

	@Override
	public List<CandidateViewMapper> getCandidateList(String fullName) {
		List<CandidateDetails> list = candidateDetailsRepository.findByFullNameLike("%" + fullName + "%");
		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		if (null != list && !list.isEmpty()) {

			resultList = list.stream().map(li -> getCandidateDetailsById(li.getCandidateId()))
					.collect(Collectors.toList());
		}
		return resultList;
	}

	@Override
	public HashMap getCountListBycategory(String category, String userId) {
		List<CandidateDetails> candidateList = candidateDetailsRepository.findByCategoryAndUserIdAndLiveIndAndBlockListIndAndReInStateInd(category,
				userId, true,false,false);
		HashMap map = new HashMap();
		if(null!=candidateList && !candidateList.isEmpty()){
		map.put("candidateDetails", candidateList.size());
		}
		return map;

	}

	@Override
	public List<CandidateViewMapper> getCandidateDetailsByIdNumber(String IdNumber, String orgId) {
		// System.out.println("the id is"+IdNumber);
		List<CandidateDetails> list = candidateDetailsRepository.findByIdNumberAndLiveIndAndOrganizationId(IdNumber,
				true, orgId);

		// System.out.println("LIST value is.........."+list);

		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		resultList = list.stream().map(li -> getCandidateDetailsById(li.getCandidateId())).collect(Collectors.toList());

		// System.out.println("Result list is......."+resultList);
		return resultList;
	}

	@Override
	public List<CandidateViewMapper> getCandidateListByRollAndCost(String role, String cost) {
		List<CandidateDetails> list = candidateDetailsRepository.findByRoleTypeAndBillingLessThanEqualAndLiveInd(role,
				cost, true);
		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		resultList = list.stream().map(li -> getCandidateDetailsById(li.getCandidateId())).collect(Collectors.toList());

		return resultList;
	}

	@Override
	public List<CandidateViewMapper> getCandidateSkillByOrgId(String organizationId, String skill) {
		List<CandidateDetails> list = candidateDetailsRepository.findCandidatesBySkillAndOrganizationId(organizationId,
				skill);

		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();

		if (null != list) {
			list.stream().map(li -> {
				CandidateViewMapper candidateMapper = new CandidateViewMapper();
				candidateMapper.setCandidateId(li.getCandidateId());
				candidateMapper.setSkillName(li.getSkill());
				// candidateMapper.setAvailableDate(li.getAvailableDate());
				return resultList.add(candidateMapper);
			}).collect(Collectors.toList());
		}

		return resultList;
	}

	@Override
	public SkillSetMapper updateCandidateSkill(String skillId, SkillSetMapper skillSetMapper) {

		SkillSetDetails skill = skillSetRepository.getById(skillId);
		// CandidateMapper candidateMapper1 = new CandidateMapper();
		if (null != skill) {
			// skill.setSkillName(skillSetMapper.getSkillName());
			skill.setExperience(skillSetMapper.getExperience());

			skillSetRepository.save(skill);
		}
		SkillSetMapper result = getSkillSet(skillId);
		return result;
	}

	@Override
	public HashMap getCandidateCountListBySkill(String skill, String orgId) {
		int record = 0;
		HashMap map = new HashMap();
		List<DefinationDetails> list = definationRepository.getByNameAndOrgId(skill, orgId);
		if (null != list && !list.isEmpty()) {
			for (DefinationDetails definationDetails : list) {
				List<SkillSetDetails> skilllist = skillSetRepository
						.getSkillLinkBySkill(definationDetails.getDefinationId());
				System.out.println("@@@@@@@@@@@@^^^^^^^^^^^^" + skilllist.toString());

				if (null != skilllist && !skilllist.isEmpty()) {

					for (SkillSetDetails skillSetDetails : skilllist) {
						CandidateDetails candidateDetails = candidateDetailsRepository
								.getcandidateDetailsById(skillSetDetails.getCandidateId());
						record++;

					}
					map.put("count", record);
				}
			}
		}
		return map;
	}

	@Override
	public List<CandidateViewMapper> getCandidatesBasedOnCategory(String recruitmentId, String orgId) {
		List<CandidateViewMapper> mapperList = new ArrayList<>();
		OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
				.getRecruitmentDetailsByRecruitId(recruitmentId);
		if (null != opportunityRecruitDetails) {
			List<CandidateDetails> list = candidateDetailsRepository
					.findByCategoryAndOrganizationIdAndLiveInd(opportunityRecruitDetails.getCategory(), orgId, true);

			if (null != list) {
				list.stream().map(li2 -> {
					System.out.println("the candidate id is" + li2.getCandidateId());
					List<SkillSetDetails> skillSetDetails = skillSetRepository.getSkillSetById(li2.getCandidateId());
					CandidateViewMapper candidateViewMapper = new CandidateViewMapper();
					// candidateViewMapper.setFirstName(candidateDetails1.getFirstName());
					candidateViewMapper.setFullName(li2.getFullName());
					candidateViewMapper.setUserId(li2.getUserId());
					candidateViewMapper.setCandidateId(li2.getCandidateId());
					candidateViewMapper.setSkillName("");
					candidateViewMapper.setBilling(li2.getBilling());
					candidateViewMapper.setCurrency(li2.getCurrency());
					candidateViewMapper.setWorkLocation(li2.getWorkLocation());
					candidateViewMapper.setCostType(li2.getCostType());

					if (!StringUtils.isEmpty(li2.getPartnerId())) {
						PartnerDetails partnerDetails = partnerDetailsRepository
								.getPartnerDetailsById(li2.getPartnerId());
						if (null != partnerDetails) {
							System.out.println("     the partner name is : " + partnerDetails.getPartnerName() + " ..."
									+ partnerDetails.getPartnerId());
							candidateViewMapper.setPartnerName(partnerDetails.getPartnerName());
						} else {
							System.out.println("     this is else condition");
							candidateViewMapper.setPartnerName("");
							candidateViewMapper.setPartnerId("");
						}
					}
					candidateViewMapper.setCountry(li2.getCountry());
					if (!StringUtils.isEmpty(li2.getRoleType())) {
						RoleType roleType = roleTypeRepository.findByRoleTypeId(li2.getRoleType());
						if (null != roleType) {
							System.out.println("    the roll type id is : " + roleType.getRoleTypeId() + "..."
									+ roleType.getRoleType());
							candidateViewMapper.setRoleTypeId(roleType.getRoleTypeId());
							candidateViewMapper.setRoleType(roleType.getRoleType());
						}
					}
					System.out.println("     the work type of the candidate is " + li2.getWorkType());
					candidateViewMapper.setWorkType(li2.getWorkType());
					candidateViewMapper.setCategory(li2.getCategory());

					if (null != li2.getAvailableDate()) {
						candidateViewMapper.setAvailableDate(Utility.getISOFromDate(li2.getAvailableDate()));
					}
					candidateViewMapper.setImageId(li2.getImageId());
					List<SkillSetDetails> skillList = skillSetRepository.getSkillSetById(li2.getCandidateId());

					List<String> candidateSkill = (skillList.stream().map(li -> li.getSkillName())
							.collect(Collectors.toList()));
					candidateViewMapper.setSkillList(candidateSkill);

					ArrayList<String> words = new ArrayList<>();
					OpportunityRecruitDetails recrutimentDetails = recruitmentOpportunityDetailsRepository
							.getRecruitmentDetailsByRecruitId(recruitmentId);
					String description = recrutimentDetails.getDescription().replace(",", " ");

					if (null != candidateSkill) {
						String[] definationArr = new String[candidateSkill.size()];
						definationArr = candidateSkill.toArray(definationArr);

						if (!StringUtils.isEmpty(description)) {
							// List<String> descriptionList = Arrays.asList(description.split("\\s*,\\s*"));
							List<String> descriptionList = Arrays.asList(description.split(" "));
							for (String word : definationArr) {

								for (String description1 : descriptionList) {
									if (description1.equalsIgnoreCase(word)) {

										words.add(word);
										System.out.println("word>>>>>>>>>>" + word);
									}
								}
								candidateViewMapper.setMatchSkill(words);

							}
						}

					}

					mapperList.add(candidateViewMapper);
					return mapperList;
				}).collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<CandidateViewMapper> getCandidatesBasedOnOrgLevelAndUserLevel(String recriutmentId, String orgId,
			String userId) {
		List<CandidateViewMapper> mapperList = new ArrayList<>();
		// OpportunityRecruitDetails opportunityRecruitDetails =
		// recruitmentOpportunityDetailsRepository.getRecruitmentDetailsByRecruitId(recruitmentId);
		// if (null != opportunityRecruitDetails) {
		EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userId);
		if (employeeDetails.getEmployeeType().equalsIgnoreCase("employee")) {
			List<CandidateDetails> list = candidateDetailsRepository
					.getCandidateDetailsBasedOnrectuitmentDetails(orgId);

			if (null != list) {
				list.stream().map(li2 -> {
					System.out.println("the candidate id is" + li2.getCandidateId());
					// List <SkillSetDetails> skillSetDetails =
					// skillSetRepository.getSkillSetById(li2.getCandidateId());
					CandidateViewMapper candidateViewMapper = new CandidateViewMapper();
					// candidateViewMapper.setFirstName(candidateDetails1.getFirstName());
					candidateViewMapper.setFullName(li2.getFullName());
					candidateViewMapper.setUserId(li2.getUserId());
					candidateViewMapper.setCandidateId(li2.getCandidateId());
					candidateViewMapper.setSkillName("");
					candidateViewMapper.setBilling(li2.getBilling());
					candidateViewMapper.setCurrency(li2.getCurrency());
					candidateViewMapper.setWorkLocation(li2.getWorkLocation());
					candidateViewMapper.setCostType(li2.getCostType());
					candidateViewMapper.setWorkPreference(li2.getWorkPreferance());
					candidateViewMapper.setWorkType(li2.getWorkType());

					if (!StringUtils.isEmpty(li2.getPartnerId())) {
						PartnerDetails partnerDetails = partnerDetailsRepository
								.getPartnerDetailsById(li2.getPartnerId());
						if (null != partnerDetails) {
							System.out.println("     the partner name is : " + partnerDetails.getPartnerName() + " ..."
									+ partnerDetails.getPartnerId());
							candidateViewMapper.setPartnerName(partnerDetails.getPartnerName());
						} else {
							System.out.println("     this is else condition");
							candidateViewMapper.setPartnerName("");
							candidateViewMapper.setPartnerId("");
						}
					}
					candidateViewMapper.setCountry(li2.getCountry());
					if (!StringUtils.isEmpty(li2.getRoleType())) {
						RoleType roleType = roleTypeRepository.findByRoleTypeId(li2.getRoleType());
						if (null != roleType) {
							System.out.println("    the roll type id is : " + roleType.getRoleTypeId() + "..."
									+ roleType.getRoleType());
							candidateViewMapper.setRoleTypeId(roleType.getRoleTypeId());
							candidateViewMapper.setRoleType(roleType.getRoleType());
						}
					}
					System.out.println("     the work type of the candidate is " + li2.getWorkType());
					candidateViewMapper.setWorkType(li2.getWorkType());
					candidateViewMapper.setCategory(li2.getCategory());

					if (null != li2.getAvailableDate()) {
						candidateViewMapper.setAvailableDate(Utility.getISOFromDate(li2.getAvailableDate()));
					}
					candidateViewMapper.setImageId(li2.getImageId());
					List<SkillSetDetails> skillList = skillSetRepository.getSkillSetById(li2.getCandidateId());

					List<String> candidateSkill = (skillList.stream().map(li -> li.getSkillName())
							.collect(Collectors.toList()));
					candidateViewMapper.setSkillList(candidateSkill);

					List<CandidateAddressLink> candidateAddressLinkList = candidateAddressLinkRepository
							.getAddressListByCandidateId(li2.getCandidateId());
					List<AddressMapper> addressList = new ArrayList<AddressMapper>();
					if (null != candidateAddressLinkList && !candidateAddressLinkList.isEmpty()) {

						for (CandidateAddressLink candidateAddressLink : candidateAddressLinkList) {
							AddressDetails addressDetails = addressRepository
									.getAddressDetailsByAddressId(candidateAddressLink.getAddressId());

							AddressMapper addressMapper = new AddressMapper();
							if (null != addressDetails) {

								addressMapper.setAddress1(addressDetails.getAddressLine1());
								addressMapper.setAddress2(addressDetails.getAddressLine2());
								addressMapper.setAddressType(addressDetails.getAddressType());
								addressMapper.setPostalCode(addressDetails.getPostalCode());
								addressMapper.setStreet(addressDetails.getStreet());
								addressMapper.setCity(addressDetails.getCity());
								addressMapper.setTown(addressDetails.getTown());
								addressMapper.setCountry(addressDetails.getCountry());
								addressMapper.setLatitude(addressDetails.getLatitude());
								addressMapper.setLongitude(addressDetails.getLongitude());
								addressMapper.setState(addressDetails.getState());
								addressMapper.setAddressId(addressDetails.getAddressId());
								addressMapper.setHouseNo(addressDetails.getHouseNo());
								addressList.add(addressMapper);
							}
						}

					}
					candidateViewMapper.setAddress(addressList);

					ArrayList<String> words = new ArrayList<>();
					OpportunityRecruitDetails recrutimentDetails = recruitmentOpportunityDetailsRepository
							.getRecruitmentDetailsByRecruitId(recriutmentId);
					if (null != recrutimentDetails) {
						String description = recrutimentDetails.getDescription().replace(",", " ");

						if (null != candidateSkill) {
							String[] definationArr = new String[candidateSkill.size()];
							definationArr = candidateSkill.toArray(definationArr);

							if (!StringUtils.isEmpty(description)) {
								// List<String> descriptionList = Arrays.asList(description.split("\\s*,\\s*"));
								List<String> descriptionList = Arrays.asList(description.split(" "));
								for (String word : definationArr) {

									for (String description1 : descriptionList) {
										if (description1.equalsIgnoreCase(word)) {

											words.add(word);
											System.out.println("word>>>>>>>>>>" + word);
										}
									}
									candidateViewMapper.setMatchSkill(words);

								}
							}

						}
					}
					mapperList.add(candidateViewMapper);
					return mapperList;
				}).collect(Collectors.toList());
			}
		} else {
			List<CandidateDetails> list = candidateDetailsRepository.getCandidateByUserId(userId);

			if (null != list) {
				list.stream().map(li2 -> {
					System.out.println("the candidate id is" + li2.getCandidateId());
					// List <SkillSetDetails> skillSetDetails =
					// skillSetRepository.getSkillSetById(li2.getCandidateId());
					CandidateViewMapper candidateViewMapper = new CandidateViewMapper();
					// candidateViewMapper.setFirstName(candidateDetails1.getFirstName());
					candidateViewMapper.setFullName(li2.getFullName());
					candidateViewMapper.setUserId(li2.getUserId());
					candidateViewMapper.setCandidateId(li2.getCandidateId());
					candidateViewMapper.setSkillName("");
					candidateViewMapper.setBilling(li2.getBilling());
					candidateViewMapper.setCurrency(li2.getCurrency());
					candidateViewMapper.setWorkLocation(li2.getWorkLocation());
					candidateViewMapper.setCostType(li2.getCostType());

					if (!StringUtils.isEmpty(li2.getPartnerId())) {
						PartnerDetails partnerDetails = partnerDetailsRepository
								.getPartnerDetailsById(li2.getPartnerId());
						if (null != partnerDetails) {
							System.out.println("     the partner name is : " + partnerDetails.getPartnerName() + " ..."
									+ partnerDetails.getPartnerId());
							candidateViewMapper.setPartnerName(partnerDetails.getPartnerName());
						} else {
							System.out.println("     this is else condition");
							candidateViewMapper.setPartnerName("");
							candidateViewMapper.setPartnerId("");
						}
					}
					candidateViewMapper.setCountry(li2.getCountry());
					if (!StringUtils.isEmpty(li2.getRoleType())) {
						RoleType roleType = roleTypeRepository.findByRoleTypeId(li2.getRoleType());
						if (null != roleType) {
							System.out.println("    the roll type id is : " + roleType.getRoleTypeId() + "..."
									+ roleType.getRoleType());
							candidateViewMapper.setRoleTypeId(roleType.getRoleTypeId());
							candidateViewMapper.setRoleType(roleType.getRoleType());
						}
					}
					System.out.println("     the work type of the candidate is " + li2.getWorkType());
					candidateViewMapper.setWorkType(li2.getWorkType());
					candidateViewMapper.setCategory(li2.getCategory());

					if (null != li2.getAvailableDate()) {
						candidateViewMapper.setAvailableDate(Utility.getISOFromDate(li2.getAvailableDate()));
					}
					candidateViewMapper.setImageId(li2.getImageId());
					List<SkillSetDetails> skillList = skillSetRepository.getSkillSetById(li2.getCandidateId());

					List<String> candidateSkill = (skillList.stream().map(li -> li.getSkillName())
							.collect(Collectors.toList()));
					candidateViewMapper.setSkillList(candidateSkill);

					List<CandidateAddressLink> candidateAddressLinkList = candidateAddressLinkRepository
							.getAddressListByCandidateId(li2.getCandidateId());
					List<AddressMapper> addressList = new ArrayList<AddressMapper>();
					if (null != candidateAddressLinkList && !candidateAddressLinkList.isEmpty()) {

						for (CandidateAddressLink candidateAddressLink : candidateAddressLinkList) {
							AddressDetails addressDetails = addressRepository
									.getAddressDetailsByAddressId(candidateAddressLink.getAddressId());

							AddressMapper addressMapper = new AddressMapper();
							if (null != addressDetails) {

								addressMapper.setAddress1(addressDetails.getAddressLine1());
								addressMapper.setAddress2(addressDetails.getAddressLine2());
								addressMapper.setAddressType(addressDetails.getAddressType());
								addressMapper.setPostalCode(addressDetails.getPostalCode());
								addressMapper.setStreet(addressDetails.getStreet());
								addressMapper.setCity(addressDetails.getCity());
								addressMapper.setTown(addressDetails.getTown());
								addressMapper.setCountry(addressDetails.getCountry());
								addressMapper.setLatitude(addressDetails.getLatitude());
								addressMapper.setLongitude(addressDetails.getLongitude());
								addressMapper.setState(addressDetails.getState());
								addressMapper.setAddressId(addressDetails.getAddressId());
								addressMapper.setHouseNo(addressDetails.getHouseNo());
								addressList.add(addressMapper);
							}
						}

					}
					candidateViewMapper.setAddress(addressList);
					ArrayList<String> words = new ArrayList<>();
					OpportunityRecruitDetails recrutimentDetails = recruitmentOpportunityDetailsRepository
							.getRecruitmentDetailsByRecruitId(recriutmentId);
					if (null != recrutimentDetails) {
						String description = recrutimentDetails.getDescription().replace(",", " ");

						if (null != candidateSkill) {
							String[] definationArr = new String[candidateSkill.size()];
							definationArr = candidateSkill.toArray(definationArr);

							if (!StringUtils.isEmpty(description)) {
								// List<String> descriptionList = Arrays.asList(description.split("\\s*,\\s*"));
								List<String> descriptionList = Arrays.asList(description.split(" "));
								for (String word : definationArr) {

									for (String description1 : descriptionList) {
										if (description1.equalsIgnoreCase(word)) {

											words.add(word);
											System.out.println("word>>>>>>>>>>" + word);
										}
									}
									candidateViewMapper.setMatchSkill(words);

								}
							}

						}
					}
					mapperList.add(candidateViewMapper);
					return mapperList;
				}).collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<String> updateTransferOneUserToAnother(String userId, CandidateMapper candidateMapper) {

		List<String> candiList = candidateMapper.getCandidateIds();
		System.out.println("candiList::::::::::::::::::::::::::::::::::::::::::::::::::::" + candiList);
		if (null != candiList && !candiList.isEmpty()) {
			for (String candidateId : candiList) {
				System.out.println("the candiate id is : " + candidateId);
				System.out.println("the user id is : " + userId);
				CandidateDetails candidate = candidateDetailsRepository.getcandidateDetailsById(candidateId);
				System.out.println("candidate::::::::::::::::::::::::::::::::::::::::::::::::::::" + candidate);
				System.out.println("the user id is :=== " + userId);

				candidate.setUserId(userId);
				candidateDetailsRepository.save(candidate);
			}

		}
		return candiList;

	}

	@Override
	public List<String> getSkillSetOfSkillLibery(String orgId) {

		List<String> skills = null;

		List<DefinationDetails> list = definationRepository.getDefinationsOfAdmin(orgId);
		if (null != list && !list.isEmpty()) {

			skills = (list.stream().map(li -> li.getName()).collect(Collectors.toList()));
		}

		return skills;
	}

	@Override
	public DefinationMapper updateDefinations(DefinationMapper definationMapper) {
		DefinationDetails definationDetails = definationRepository
				.findByDefinationId(definationMapper.getDefinationId());
		if (null != definationMapper.getName() && !definationMapper.getName().isEmpty()) {
			definationDetails.setName(definationMapper.getName());
		}
		if (null != definationMapper.getOrgId()) {
			definationDetails.setOrg_id(definationMapper.getOrgId());
		}
		if (null != definationMapper.getUserId()) {
			definationDetails.setUser_id(definationMapper.getUserId());
		}
		definationDetails.setEditInd(definationMapper.isEditInd());

		definationRepository.save(definationDetails);

		DefinationDetailsDelete definationDetailsDelete=definationDeleteRepository.findByDefinationId(definationMapper.getDefinationId());
			if(null!=definationDetailsDelete) {
			System.out.println("definationMapper.getOrgId()===================="+definationMapper.getOrgId());
			if (null != definationMapper.getOrgId()) {
				System.out.println("definationMapper.getOrgId()===================="+definationMapper.getOrgId());
				definationDetailsDelete.setOrgId(definationMapper.getOrgId());
			}
			if (null != definationMapper.getUserId()) {
				definationDetailsDelete.setUserId(definationMapper.getUserId());
			}
			definationDetailsDelete.setUpdationDate(new Date());
			definationDetailsDelete.setName(definationMapper.getUserId());
			definationDeleteRepository.save(definationDetailsDelete);
		}else {
			DefinationDetailsDelete definationDelete1 = new DefinationDetailsDelete();
			definationDelete1.setDefinationId(definationMapper.getDefinationId());
			definationDelete1.setUserId(definationMapper.getUserId());
			definationDelete1.setOrgId(definationMapper.getOrgId());
			definationDelete1.setUpdationDate(new Date());
			definationDelete1.setName(definationMapper.getUserId());
			definationDeleteRepository.save(definationDelete1);
		}
		DefinationMapper resultMapper = getDefinationDetails(definationDetails.getDefinationId());		
		return resultMapper;
	}

	private DefinationMapper getDefinationDetails(String definationId) {
		DefinationDetails definationDetails = definationRepository.findByDefinationId(definationId);

		DefinationMapper definationMapper = new DefinationMapper();

		if (null != definationDetails) {
			definationMapper.setDefinationId(definationDetails.getDefinationId());
			definationMapper.setName(definationDetails.getName());
			definationMapper.setOrgId(definationDetails.getOrg_id());
			definationMapper.setEditInd(definationDetails.isEditInd());

			definationMapper.setUserId(definationDetails.getUser_id());
			List<DefinationDetailsDelete>list=definationDeleteRepository.findByOrgId(definationDetails.getOrg_id());
			if(null!=list&& !list.isEmpty()) {
				Collections.sort(list,(p1,p2)->p2.getUpdationDate().compareTo(p1.getUpdationDate()));
				definationMapper.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
				definationMapper.setUpdatedName(employeeService.getEmployeeFullName(list.get(0).getUserId()));
			}
		}
		return definationMapper;
	}

	@Override
	public boolean ipAddressExists(String url) {
		Website web = websiteRepository.getByUrl(url);
		System.out.println("web=======" + web.toString());
		if (null != web) {
			return true;
		}
		return false;
	}

	@Override
	public List<CandidateViewMapper> getCandidateFilterList(FilterMapper filterMapper, String userId, String orgId) {

		FilterDetails FilterDetails = new FilterDetails();
		FilterDetails.setOrgId(orgId);
		FilterDetails.setUserId(userId);
		FilterDetails.setBilling(filterMapper.getBilling());
		FilterDetails.setRoleType(filterMapper.getRoleType());
		FilterDetails.setWorkLocation(filterMapper.getWorkLocation());
		FilterDetails.setWorkPreference(filterMapper.getWorkPreference());
		FilterDetails.setOrAnd(filterMapper.getOrAnd());
		FilterDetails.setCreationDate(new Date());
		filterDetailsRepository.save(FilterDetails);

		List<CandidateDetails> list = new ArrayList<CandidateDetails>();

//		boolean flag=false;
//		
//		if(filterMapper.getWorkLocation()==null && filterMapper.getWorkPreference()==null) {
//			flag=true;
//			if (filterMapper.getBilling()!=null) {
//				list = candidateDetailsRepository.findByBillingAndLiveInd(filterMapper.getBilling(),true);
//			} else {
//				list = candidateDetailsRepository.findByRoleTypeAndLiveInd(filterMapper.getRoleType(),true);
//			}
//		}
//		
//		if(filterMapper.getBilling()==null && filterMapper.getRoleType()==null) {
//			flag=true;
//			if (filterMapper.getWorkLocation()!=null) {
//				list = candidateDetailsRepository.findByWorkLocationAndLiveInd(filterMapper.getBilling(),true);
//			} else {
//				list = candidateDetailsRepository.findByWorkPreferanceAndLiveInd(filterMapper.getRoleType(),true);
//			}
//		}
//		
//		if(!flag) {
		if (filterMapper.getOrAnd().equalsIgnoreCase("Or")) {
			if (filterMapper.getWorkLocation() != null) {
				if (filterMapper.getRoleType() != null) {
					list = candidateDetailsRepository.findByRoleTypeOrWorkLocationAndLiveInd(filterMapper.getRoleType(),
							filterMapper.getWorkLocation(), true);
				} else if (filterMapper.getBilling() != null) {
					list = candidateDetailsRepository.findByBillingOrWorkLocationAndLiveInd(filterMapper.getBilling(),
							filterMapper.getWorkLocation(), true);
				}
			} else if (filterMapper.getWorkPreference() != null) {
				if (filterMapper.getRoleType() != null) {
					list = candidateDetailsRepository.findByRoleTypeOrWorkPreferanceAndLiveInd(
							filterMapper.getRoleType(), filterMapper.getWorkPreference(), true);
				} else if (filterMapper.getBilling() != null) {
					list = candidateDetailsRepository.findByBillingOrWorkPreferanceAndLiveInd(filterMapper.getBilling(),
							filterMapper.getWorkPreference(), true);
				}
			}
		} else {
			if (filterMapper.getWorkLocation() != null) {
				if (filterMapper.getRoleType() != null) {
					list = candidateDetailsRepository.findByRoleTypeAndWorkLocationAndLiveInd(
							filterMapper.getRoleType(), filterMapper.getWorkLocation(), true);
				} else if (filterMapper.getBilling() != null) {
					list = candidateDetailsRepository.findByBillingAndWorkLocationAndLiveInd(filterMapper.getBilling(),
							filterMapper.getWorkLocation(), true);
				}
			} else if (filterMapper.getWorkPreference() != null) {
				if (filterMapper.getRoleType() != null) {
					list = candidateDetailsRepository.findByRoleTypeAndWorkPreferanceAndLiveInd(
							filterMapper.getRoleType(), filterMapper.getWorkPreference(), true);
				} else if (filterMapper.getBilling() != null) {
					list = candidateDetailsRepository.findByBillingAndWorkPreferanceAndLiveInd(
							filterMapper.getBilling(), filterMapper.getWorkPreference(), true);
				}
			}
		}
		// }

		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		resultList = list.stream().map(li -> getCandidateDetailsById(li.getCandidateId())).collect(Collectors.toList());

		return resultList;
	}

	@Override
	public boolean emailExistsByWebsite(String emailId) {
		List<CandidateDetails> list = candidateDetailsRepository.findByEmailId(emailId);
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean emailExistsInCandidate(String emailId) {
		List<CandidateDetails> list = candidateDetailsRepository.findByEmailId(emailId);
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public List<CandidateEmailResponseMapper> getCandidateDetailsByEmail(
			CandidateEmailRequestMapper candidateEmailRequestMapper) {

		List<CandidateEmailResponseMapper> mapperList = new ArrayList<>();
		List<String> candiList = candidateEmailRequestMapper.getCandidateIds();
		if (null != candiList && !candiList.isEmpty()) {
			for (String candidateId : candiList) {
				CandidateEmailResponseMapper candidateEmailResponseMapper = new CandidateEmailResponseMapper();
				CandidateDetails candidate = candidateDetailsRepository.getcandidateDetailsById(candidateId);

				if (candidateEmailRequestMapper.isNameInd() == true) {
					candidateEmailResponseMapper.setName(candidate.getFullName());
					candidateEmailResponseMapper.setNameInd(candidateEmailRequestMapper.isNameInd());
				}
				if (candidateEmailRequestMapper.isBillingInd() == true) {
					candidateEmailResponseMapper.setBilling(candidate.getBilling());
					candidateEmailResponseMapper.setBillingInd(candidateEmailRequestMapper.isBillingInd());
				}
				if (candidateEmailRequestMapper.isCategoryInd() == true) {
					candidateEmailResponseMapper.setCategory(candidate.getCategory());
					candidateEmailResponseMapper.setCategoryInd(candidateEmailRequestMapper.isCategoryInd());
				}
				if (candidateEmailRequestMapper.isAvailableDateInd() == true) {
					candidateEmailResponseMapper.setAvailableDate(Utility.getISOFromDate(candidate.getAvailableDate()));
					candidateEmailResponseMapper.setAvailableDateInd(candidateEmailRequestMapper.isAvailableDateInd());
				}
				if (candidateEmailRequestMapper.isMobileNoInd() == true) {
					candidateEmailResponseMapper.setMobileNo(candidate.getMobileNumber());
					candidateEmailResponseMapper.setMobileNoInd(candidateEmailRequestMapper.isMobileNoInd());
				}
				if (candidateEmailRequestMapper.isRoleInd() == true) {
					RoleType RoleType = roleTypeRepository.findByRoleTypeId(candidate.getRoleType());
					if (null != RoleType) {
						candidateEmailResponseMapper.setRole(RoleType.getRoleType());
					}
					candidateEmailResponseMapper.setRoleInd(candidateEmailRequestMapper.isRoleInd());
				}
				if (candidateEmailRequestMapper.isSkillInd() == true) {
					List<String> skillName = new ArrayList<>();
					List<SkillSetDetails> skills = skillSetRepository.getSkillSetById(candidate.getCandidateId());
					if (null != skills && !skills.isEmpty()) {
						for (SkillSetDetails skill : skills) {
							DefinationDetails definationDetails1 = definationRepository
									.findByDefinationId(skill.getSkillName());
							if (null != definationDetails1) {
							
							skillName.add(definationDetails1.getName());
							}
						}
						candidateEmailResponseMapper.setSkill(skillName);
					}
					candidateEmailResponseMapper.setSkillInd(candidateEmailRequestMapper.isSkillInd());
				}
				if (candidateEmailRequestMapper.isEmailInd() == true) {
					candidateEmailResponseMapper.setEmail(candidate.getEmailId());
					candidateEmailResponseMapper.setEmailInd(candidateEmailRequestMapper.isEmailInd());
				}

				if (candidateEmailRequestMapper.isResumeInd() == true) {
					candidateEmailResponseMapper.setDocumentMapper(resentResume(candidateId));
					candidateEmailResponseMapper.setResumeInd(candidateEmailRequestMapper.isResumeInd());
				}

				if (candidateEmailRequestMapper.isExperienceInd() == true) {
					candidateEmailResponseMapper.setExperience(candidate.getExperience());
					candidateEmailResponseMapper.setExperienceInd(candidateEmailRequestMapper.isExperienceInd());
				}
				if (candidateEmailRequestMapper.isSkillWiseExperienceInd() == true) {

					List<SkillSetDetails> skillList = skillSetRepository.getSkillSetById(candidate.getCandidateId());
					List<SkillSetMapper> resultList = new ArrayList<SkillSetMapper>();
					if (null != skillList && !skillList.isEmpty()) {

						for (SkillSetDetails skillSetDetails : skillList) {
							SkillSetMapper skillSetMapper = getSkillSet(skillSetDetails.getSkillSetDetailsId());
							if (null != skillSetMapper) {
								resultList.add(skillSetMapper);
							}

						}

					}
					candidateEmailResponseMapper.setSkillWiseExperience(resultList);
					candidateEmailResponseMapper
							.setSkillWiseExperienceInd(candidateEmailRequestMapper.isSkillWiseExperienceInd());
				}
				if (candidateEmailRequestMapper.isIdentityCardInd() == true) {
					candidateEmailResponseMapper.setIdProof(candidate.getIdProof());
					candidateEmailResponseMapper.setIdNumber(candidate.getIdNumber());
					candidateEmailResponseMapper.setIdentityCardInd(candidateEmailRequestMapper.isIdentityCardInd());
				}
				mapperList.add(candidateEmailResponseMapper);
			}

		}

		return mapperList;
	}

	@Override
	public DocumentMapper resentResume(String candidateId) {
		DocumentMapper updateDoc =null;
	List<DocumentMapper> documentMapper1 = new ArrayList<>();
	List<CandidateDocumentLink> CandidateDocumentLink = candidateDocumentLinkRepository
			.getDocumentByCandidateId(candidateId);
	if (null != CandidateDocumentLink && !CandidateDocumentLink.isEmpty()) {
		for (CandidateDocumentLink candidateDocumentLink1 : CandidateDocumentLink) {
			DocumentDetails documentDetails = documentDetailsRepository
					.getDocumentDetailsById(candidateDocumentLink1.getDocumentId());
			if (null != documentDetails) {
				if (null != documentDetails.getDocument_type()) {
					DocumentType documentType = documentTypeRepository
							.getTypeDetails(documentDetails.getDocument_type());
					if (null != documentType) {
						if (documentType.getDocumentTypeName().equalsIgnoreCase("Resume")) {
							DocumentMapper documentMapper = documentService
									.getDocument(candidateDocumentLink1.getDocumentId());
							documentMapper.setShareInd(candidateDocumentLink1.isShareInd());
							System.out.println("documentId==="+documentMapper.getDocumentId());
							documentMapper1.add(documentMapper);
						}
					}
				}
			}

		}
		System.out.println("documentMapper1=="+documentMapper1.size());
		if(documentMapper1.size()>1) {
		Collections.sort(documentMapper1,
				(m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		updateDoc = documentMapper1.get(0);
		}else if(documentMapper1.size()==1){
			updateDoc = documentMapper1.get(0);
		}
	}
	return updateDoc;
	}
	/*
	 * @Override public List<CandidateViewMapper> getAllBlackListCandidate(String
	 * orgIdFromToken) { List<CandidateViewMapper> resultMapper = new
	 * ArrayList<CandidateViewMapper>();
	 * 
	 * 
	 * List<Permission> permission = permissionRepository.getUserList();
	 * System.out.println(" user$$$$$$$$$$$$=="+permission.toString());
	 * 
	 * if (null != permission && !permission.isEmpty()) {
	 * 
	 * for(Permission permissionn : permission) {
	 * 
	 * List<CandidateViewMapper> mp
	 * =candidateService.getCandidateListByUserId(permissionn.getUserId());
	 * 
	 * System.out.println(" userId$$$$$$$$$$$$=="+permissionn.getUserId());
	 * 
	 * resultMapper.addAll(mp); }
	 * 
	 * } return resultMapper;
	 * 
	 * }
	 */

	@Override
	public List<CandidateViewMapper> getAllBlackListCandidateByUserId(String userId) {
		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();

		List<CandidateDetails> contactList = candidateDetailsRepository.getBlackListCandidateByUserId(userId);
		if (null != contactList && !contactList.isEmpty()) {
			contactList.stream().map(candidate->{
				//for(CandidateDetails candidate : contactList) {
				CandidateViewMapper candidateMapper = getCandidateDetailsById(candidate.getCandidateId());

				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(candidate.getLastName())) {

					lastName = candidate.getLastName();
				}

				if (candidate.getMiddleName() != null && candidate.getMiddleName().length() > 0) {

					middleName = candidate.getMiddleName();
					candidateMapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
				} else {

					candidateMapper.setFullName(candidate.getFirstName() + " " + lastName);
				}
				resultList.add(candidateMapper);
               return resultList;
			
			}).filter(l -> l != null).collect(Collectors.toList());

		}
		return resultList;    
	}

	@Override
	public List<DefinationMapper> getDefinationsByUrl(String url) {
		Website web = websiteRepository.getByUrl(url);
		List<DefinationDetails> list = definationRepository.getDefinationsOfAdmin(web.getOrgId());
		List<DefinationMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {

			list.stream().map(li -> {
				DefinationMapper definationMapper = new DefinationMapper();

				definationMapper.setName(li.getName());
				definationMapper.setDefinationId(li.getDefinationId());
				mapperList.add(definationMapper);

				return mapperList;
			}).collect(Collectors.toList());
		}

		return mapperList;
	}

	@Override
	public float getExprience(Date endDate) {
		Date startDate = new Date();
		System.out.println("startDate::" + startDate + "||" + "endDate::" + endDate);
		long differenceInTime = Math.abs(endDate.getTime() - startDate.getTime());
		float expoYear = (float) (TimeUnit.DAYS.convert(differenceInTime, TimeUnit.MILLISECONDS) / 365.0);
		System.out.println("Exprience in service::" + expoYear);
		// diffDays =diffDays/365;
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		float result = Float.valueOf(decimalFormat.format(expoYear));
		System.out.println("Result::" + result);
		return result;

	}

	@Override
	public CandidateDocumentMapper getCandidateDocumentDetailsById(String candidateId) {
		CandidateDocumentMapper resultList = new CandidateDocumentMapper();
		List<CandidateTraining> trainingList = candidateTrainingRepository.getCandidateTrainingById(candidateId);
		List<CandidateTrainingMapper> resultList1 = new ArrayList<CandidateTrainingMapper>();
		if (null != trainingList && !trainingList.isEmpty()) {
			trainingList.stream().map(candidateTraining->{
				CandidateTrainingMapper candidateTrainingMapper = getCandidateTrainings(
						candidateTraining.getCandidateTrainingId());
				if (null != candidateTrainingMapper) {
					resultList1.add(candidateTrainingMapper);
					return resultList1;
				}
              return null;
			}).collect(Collectors.toList());
			resultList.setCandidateTrainingMapper(resultList1);
		}

		return resultList;
	}

	@Override
	public String getCandidateDetailsByEmailId(String emailId) {
		String id = null;
		CandidateDetails candidate = candidateDetailsRepository.findByEmail(emailId);
		System.out.println("candidateID" + candidate.getCandidateId());
		id = candidate.getCandidateId();
		return id;
	}

	@Override
	public String saveCandidateThroughWebsite(CandidateWebsiteMapper candidateMapper) {
		String candidateId = null;
		String documentTypeId = null;

//		if (candidateMapper != null) {
//
//			if (candidateMapper.getEmailId() != null) {
//
//				CandidateDetails candidatee = candidateDetailsRepository.findByEmail(candidateMapper.getEmailId());
//
//				if (candidatee != null) {
//					return "Candidate can not be created as same emailId already exist";
//
//				} else {

		CandidateInfo candidateInfo = new CandidateInfo();

		candidateInfo.setCreationDate(new Date());

		CandidateInfo candidatInfo = candidateInfoRepository.save(candidateInfo);
		candidateId = candidatInfo.getCandidateId();

		CandidateDetails candidate = new CandidateDetails();
		candidate.setCandidateId(candidateId);
		setPropertyOnInputForWebsite(candidateMapper, candidate, candidateId);

		CandidateDetails candidatedb = candidateDetailsRepository.save(candidate);

		/* insert to notification table */

		NotificationDetails notification = new NotificationDetails();
		String middleName2 =" ";
		String lastName2 ="";
		String satutation1 ="";

		if(!StringUtils.isEmpty(candidateMapper.getLastName())) {
			 
			lastName2 = candidateMapper.getLastName();
		 }
		 if(candidateMapper.getSalutation() != null && candidateMapper.getSalutation().length()>0) {
			 satutation1 = candidateMapper.getSalutation();
		 }


		 if(candidateMapper.getMiddleName() != null && candidateMapper.getMiddleName().length()>0) {

		 
			 middleName2 = candidateMapper.getMiddleName();
			 notification.setMessage(satutation1+" "+candidateMapper.getFirstName()+" "+middleName2+" "+lastName2+ " Created as a Candidate.");
		 }else {
			 
			 notification.setMessage(satutation1+" "+candidateMapper.getFirstName()+" "+lastName2+ " Created as a Candidate.");
		 }
		
		 notification.setAssignedTo(candidateMapper.getUserId());
		 
		notification.setMessageReadInd(false);
		notification.setNotificationDate(new Date());
		notification.setNotificationType("Candidate Creation");
		notification.setUser_id(candidateMapper.getUserId());

		/*
		 * List<String> skillList= candidateMapper.getSkills(); if(null!=skillList &&
		 * !skillList.isEmpty()) { for (String skillName : skillList) {
		 * System.out.println("skill>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+skillName); boolean
		 * b =checkSkillInSkillLibery(skillName);
		 * System.out.println("boolean>>>>>>>>>"+b); if(b==false){
		 * 
		 * DefinationInfo definationInfo = new DefinationInfo();
		 * 
		 * definationInfo.setCreation_date(new Date()); String id
		 * =definationInfoRepository.save(definationInfo).getDefination_info_id();
		 * 
		 * DefinationDetails newDefinationDetails = new DefinationDetails();
		 * newDefinationDetails.setDefination_id(id);
		 * newDefinationDetails.setName(skillName);
		 * newDefinationDetails.setOrg_id(candidateMapper.getOrganizationId());
		 * newDefinationDetails.setUser_id(candidateMapper.getUserId());
		 * newDefinationDetails.setCreation_date(new Date());
		 * newDefinationDetails.setLive_ind(true);
		 * definationRepository.save(newDefinationDetails);
		 * 
		 * } } }
		 */
		if (!StringUtils.isEmpty(candidateMapper.getDocumentId())) {
			DocumentDetails documentDetails = documentDetailsRepository
					.getDocumentDetailsById(candidateMapper.getDocumentId());
			if (null != documentDetails) {
				DocumentType documentType = documentTypeRepository.getDocumentTypeByName("Resume");
				if (null != documentType) {
					documentDetails.setDocument_type(documentType.getDocument_type_id());
				} else {
					DocumentType newDocumentType = new DocumentType();
					newDocumentType.setDocumentTypeName("Resume");
					newDocumentType.setCreator_id(candidateMapper.getUserId());
					newDocumentType.setOrgId(candidateMapper.getOrganizationId());
					newDocumentType.setCreation_date(new Date());
					newDocumentType.setLive_ind(true);
					documentTypeId = documentTypeRepository.save(newDocumentType).getDocument_type_id();
					documentDetails.setDocument_type(documentTypeId);
				}
				documentDetails.setDocument_title("Resume Upload");
				documentDetails.setDoc_description(candidatedb.getFullName() + " Resume Uploaded");
				documentDetails.setDocument_id(documentDetails.getDocument_id());
				documentDetails.setCreation_date(new Date());
				// documentDetails.setDocumentType(documentMapper.getDocumentTypeId());

				documentDetailsRepository.save(documentDetails);
			}
			/* insert candidateDocument link table */

			CandidateDocumentLink candidateDocumentLink = new CandidateDocumentLink();
			candidateDocumentLink.setCandidateId(candidateId);
			candidateDocumentLink.setDocumentId(candidateMapper.getDocumentId());
			candidateDocumentLink.setCreationDate(new Date());
			candidateDocumentLinkRepository.save(candidateDocumentLink);

			/* insert candidateId link table */

			PartnerCandidateLink partnerCandidateLink = new PartnerCandidateLink();
			partnerCandidateLink.setPartnerId(candidateMapper.getPartnerId());
			partnerCandidateLink.setCandidateId(candidateMapper.getCandidateId());
			partnerCandidateLink.setCreationDate(new Date());
			partnerCandidateLinkRepository.save(partnerCandidateLink);

		}

		return candidateId;
	}

	private void setPropertyOnInputForWebsite(CandidateWebsiteMapper candidateMapperr, CandidateDetails candidate,
			String candidateId) {

		candidate.setFirstName(candidateMapperr.getFirstName());
		candidate.setLastName(candidateMapperr.getLastName());
		candidate.setEmailId(candidateMapperr.getEmailId());
		candidate.setMobileNumber(candidateMapperr.getMobileNumber());
		candidate.setMiddleName(candidateMapperr.getMiddleName());
		candidate.setLinkedinPublicUrl(candidateMapperr.getLinkedin_public_url());
		candidate.setNotes(candidateMapperr.getNotes());
		candidate.setPhoneNumber(candidateMapperr.getPhoneNumber());
		candidate.setCurrentCtc(candidateMapperr.getCurrentCtc());
		candidate.setSalutation(candidateMapperr.getSalutation());
		candidate.setActive(candidateMapperr.isActive());
		candidate.setCategory(candidateMapperr.getCategory());
		candidate.setBenifit(candidateMapperr.getBenifit());
		candidate.setCostType(candidateMapperr.getCostType());
		candidate.setNoticeDetail(candidateMapperr.getNoticeDetail());
		candidate.setWhatsApp(candidateMapperr.getWhatsApp());
		candidate.setWorkLocation(candidateMapperr.getWorkLocation());
		candidate.setWorkPreferance(candidateMapperr.getWorkPreference());
		candidate.setChannel(candidateMapperr.getChannel());
		candidate.setTAndCInd(candidateMapperr.isTAndCInd());
		candidate.setPreferredDistance(200);
		if (null != candidateMapperr.getAvailableDate()) {
			try {
				candidate.setAvailableDate(
						Utility.removeTime(Utility.getDateFromISOString(candidateMapperr.getAvailableDate())));
				System.out.println("getDateFromISOString........." + candidateMapperr.getAvailableDate());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * if(null!=callDTO.getStartDate()) { try {
		 * call.setStartDate(UtilService.getDateFromISOString(callDTO.getStartDate()));
		 * } catch (Exception e) { e.printStackTrace(); } }
		 */
		candidate.setCountryDialcode(candidateMapperr.getCountryDialCode());
		candidate.setCountryDialcode1(candidateMapperr.getCountryDialCode1());
		candidate.setCurrency(candidateMapperr.getCurrency());
		candidate.setDepartment(candidateMapperr.getDepartmentId());
		candidate.setDesignation(candidateMapperr.getDesignationTypeId());
		candidate.setRoleType(candidateMapperr.getRoleTypeId());
		candidate.setTagWithCompany(candidateMapperr.getTag_with_company());
		candidate.setBilling(candidateMapperr.getBilling());

		candidate.setGender(candidateMapperr.getGender());
		candidate.setDateOfBirth(candidateMapperr.getDateOfBirth());
		candidate.setNationality(candidateMapperr.getNationality());
		candidate.setIdProof(candidateMapperr.getIdProof());
		candidate.setIdNumber(candidateMapperr.getIdNumber());
		candidate.setEducatioin(candidateMapperr.getEducation());
		candidate.setExperience(candidateMapperr.getExperience());
		candidate.setImageId(candidateMapperr.getImageId());

		candidate.setLanguage(candidateMapperr.getLanguage());
		candidate.setWorkLocation(candidateMapperr.getWorkLocation());
		candidate.setWorkType(candidateMapperr.getWorkType());
		candidate.setCountry(candidateMapperr.getCountry());
		candidate.setUserId(candidateMapperr.getUserId());
		candidate.setOrganizationId(candidateMapperr.getOrganizationId());
		candidate.setPartnerId(candidateMapperr.getPartnerId());
		candidate.setNoticePeriod(candidateMapperr.getNoticePeriod());
		candidate.setAllowSharing(candidateMapperr.getAllowSharing());

		candidate.setCreationDate(new Date());

		List<String> skillList = candidateMapperr.getSkills();
		if (null != skillList && !skillList.isEmpty()) {
			for (String skillName : skillList) {
				
				DefinationDetails definationDetails = definationRepository.getBySkillNameAndLiveInd(skillName);
				if(null!=definationDetails) {
				
				SkillSetDetails skillSetDetails1 = new SkillSetDetails();
				skillSetDetails1.setSkillName(definationDetails.getDefinationId());
				skillSetDetails1.setCandidateId(candidateId);
				skillSetDetails1.setCreationDate(new Date());
				skillSetRepository.save(skillSetDetails1);
				}
			}
		}
		candidate.setLiveInd(true);
		candidate.setBlockListInd(false);
		candidate.setCandidateId(candidateId);
		String middleName;
		String lastName = "";

		if (!StringUtils.isEmpty(candidateMapperr.getLastName())) {

			lastName = candidateMapperr.getLastName();
		}

		if (candidateMapperr.getMiddleName() != null && candidateMapperr.getMiddleName().length() > 0) {

			middleName = candidateMapperr.getMiddleName();
			candidate.setFullName(candidateMapperr.getFirstName() + " " + middleName + " " + lastName);
		} else {

			candidate.setFullName(candidateMapperr.getFirstName() + " " + lastName);
		}
		
		if(null!=candidateMapperr.getAddress() && !candidateMapperr.getAddress().isEmpty()) {
		if (candidateMapperr.getAddress().size() > 0) {
			for (AddressMapper addressMapper : candidateMapperr.getAddress()) {
				/* insert to address info & address deatils & customeraddressLink */

				AddressInfo addressInfo = new AddressInfo();
				addressInfo.setCreationDate(new Date());
				// addressInfo.setCreatorId(candidateMapperr.getUserId());
				AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

				String addressId = addressInfoo.getId();

				if (null != addressId) {

					AddressDetails addressDetails = new AddressDetails();
					addressDetails.setAddressId(addressId);
					addressDetails.setAddressLine1(addressMapper.getAddress1());
					addressDetails.setAddressLine2(addressMapper.getAddress2());
					addressDetails.setAddressType(addressMapper.getAddressType());
					addressDetails.setCountry(addressMapper.getCountry());
					addressDetails.setCreationDate(new Date());
					addressDetails.setStreet(addressMapper.getStreet());
					addressDetails.setCity(addressMapper.getCity());
					addressDetails.setPostalCode(addressMapper.getPostalCode());
					addressDetails.setTown(addressMapper.getTown());
					addressDetails.setState(addressMapper.getState());
					addressDetails.setLatitude(addressMapper.getLatitude());
					addressDetails.setLongitude(addressMapper.getLongitude());
					addressDetails.setLiveInd(true);
					addressDetails.setHouseNo(addressMapper.getHouseNo());
					addressRepository.save(addressDetails);

					CandidateAddressLink candidateAddressLink = new CandidateAddressLink();
					candidateAddressLink.setCandidateId(candidateId);
					candidateAddressLink.setAddressId(addressId);
					candidateAddressLink.setCreationDate(new Date());

					candidateAddressLinkRepository.save(candidateAddressLink);

				}
			}
		}
		}else {
			AddressInfo addressInfo = new AddressInfo();
			addressInfo.setCreationDate(new Date());
			// addressInfo.setCreatorId(candidateMapperr.getUserId());
			AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

			String addressId = addressInfoo.getId();

			if (null != addressId) {

				AddressDetails addressDetails = new AddressDetails();
				addressDetails.setAddressId(addressId);
				addressDetails.setCreationDate(new Date());
				addressDetails.setLiveInd(true);
				addressRepository.save(addressDetails);

				CandidateAddressLink candidateAddressLink = new CandidateAddressLink();
				candidateAddressLink.setCandidateId(candidateId);
				candidateAddressLink.setAddressId(addressId);
				candidateAddressLink.setCreationDate(new Date());

				candidateAddressLinkRepository.save(candidateAddressLink);

			}
		}
		candidateDetailsRepository.save(candidate);

		Notes notes = new Notes();
		notes.setCreation_date(new Date());
		notes.setNotes(candidateMapperr.getNotes());
		Notes note = notesRepository.save(notes);

		String notesId = note.getNotes_id();

		CandidateNotesLink notesLink = new CandidateNotesLink();
		notesLink.setCandidate_id(candidateId);
		notesLink.setNotesId(notesId);
		notesLink.setCreation_date(new Date());

		/* insert to Notification Table */
		NotificationDetails notification = new NotificationDetails();
		notification.setNotificationType("Candidate Creation");
		// notification.setOrg_id(candidateMapperr.getOrgId());
		notification.setUser_id(candidateMapperr.getUserId());
		EmployeeDetails emp = employeeRepository.getEmployeesByuserId(candidateMapperr.getUserId());
		
		 String middleName1 =" ";
			String lastName1 ="";
			String satutation ="";

			if(!StringUtils.isEmpty(emp.getLastName())) {
				 
				lastName1 = emp.getLastName();
			 }
			 if(emp.getSalutation() != null && emp.getSalutation().length()>0) {
				 satutation = emp.getSalutation();
			 }


			 if(emp.getMiddleName() != null && emp.getMiddleName().length()>0) {

			 
				 middleName1 = emp.getMiddleName();
				 notification.setMessage("A Candidate is created By " +satutation+" "+emp.getFirstName()+" "+middleName1+" "+lastName1);
			 }else {
				 
				 notification.setMessage("A Candidate is created By " +satutation+" "+emp.getFirstName()+" "+lastName1);
			 }
		
			 
			 String middleName2 =" ";
				String lastName2 ="";
				String satutation1 ="";

				if(!StringUtils.isEmpty(emp.getLastName())) {
					 
					lastName2 = emp.getLastName();
				 }
				 if(emp.getSalutation() != null && emp.getSalutation().length()>0) {
					 satutation1 = emp.getSalutation();
				 }


				 if(emp.getMiddleName() != null && emp.getMiddleName().length()>0) {

				 
					 middleName2 = emp.getMiddleName();
					 notification.setAssignedBy(satutation1+" "+emp.getFirstName()+" "+middleName2+" "+lastName2);
				 }else {
					 
					 notification.setAssignedBy(satutation1+" "+emp.getFirstName()+" "+lastName2);
				 }
		
		//notification.setAssignedBy(emp.getFullName());
		notification.setAssignedTo(emp.getReportingManager());
		notification.setNotificationDate(new Date());
		//notification.setMessage("A Candidate is created By " + emp.getFullName());
		notification.setMessageReadInd(false);
		notificationRepository.save(notification);
	}

	@Override
	public CandidateTreeMapper getCandidateTreeByCandidateId(String candidateId) {
		CandidateTreeMapper parrent = new CandidateTreeMapper();
		List<EducationDetailsTreeMapper> childList = new ArrayList<>();
		List<CandidateCommonMapper> innerChildList = new ArrayList<>();
		List<CandidateCommonMapper> innerChildList1 = new ArrayList<>();

		CandidateDetails candidateDetails = candidateDetailsRepository.getcandidateDetailsById(candidateId);
		if (null != candidateDetails) {
			String middleName = " ";
			String lastName = "";
			String satutation = "";

			if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

				lastName = candidateDetails.getLastName();
			}
			if (candidateDetails.getSalutation() != null && candidateDetails.getSalutation().length() > 0) {
				satutation = candidateDetails.getSalutation();
			}

			if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

				middleName = candidateDetails.getMiddleName();
				parrent.setName(satutation + " " + candidateDetails.getFirstName() + " " + middleName + " " + lastName);
			} else {

				parrent.setName(satutation + " " + candidateDetails.getFirstName() + " " + lastName);
			}
			List<CandidateEducationDetails> educationList = candidateEducationDetailsRepository
					.getCandidateEducationDetailsById(candidateId);
			if (null != educationList && !educationList.isEmpty()) {
				EducationDetailsTreeMapper mainChildren = new EducationDetailsTreeMapper();
				mainChildren.setName("Education");
				for (CandidateEducationDetails candidateEducationDetails : educationList) {
					CandidateCommonMapper innChild = new CandidateCommonMapper();
					innChild.setName(candidateEducationDetails.getCourseName());
					innerChildList.add(innChild);
				}
				mainChildren.setChildren(innerChildList);
				childList.add(mainChildren);
			}

			List<CandidateTraining> traininglist = candidateTrainingRepository.getCandidateTrainingById(candidateId);
			if (null != traininglist && !traininglist.isEmpty()) {
				EducationDetailsTreeMapper secondChild = new EducationDetailsTreeMapper();
				secondChild.setName("Training");
				for (CandidateTraining candidateTraining : traininglist) {
					CandidateCommonMapper innChild1 = new CandidateCommonMapper();
					innChild1.setName(candidateTraining.getCourseName());
					innerChildList1.add(innChild1);
				}
				secondChild.setChildren(innerChildList1);
				childList.add(secondChild);
			}
			parrent.setChildren(childList);

		}
		return parrent;
	}

	@Override
	public List<FilterMapper> getAllFilterListByUserId(String userId) {
		List<FilterMapper> resultList = new ArrayList<>();
		List<FilterDetails> list = filterDetailsRepository.findByUserId(userId);
		if (null != list) {
			list.stream().map(filterDetails->{
				FilterMapper mapper = new FilterMapper();
				mapper.setBilling(filterDetails.getBilling());
				mapper.setCreationDate(Utility.getISOFromDate(filterDetails.getCreationDate()));
				mapper.setOrAnd(filterDetails.getOrAnd());
System.out.println("id======="+filterDetails.getFilterDetailsId());
System.out.println("creationDate======="+Utility.getISOFromDate(filterDetails.getCreationDate()));
				RoleType roleType = roleTypeRepository.findByRoleTypeId(filterDetails.getRoleType());
				if (null != roleType) {
					mapper.setRoleType(roleType.getRoleType());
				}
				mapper.setWorkLocation(filterDetails.getWorkLocation());
				mapper.setWorkPreference(filterDetails.getWorkPreference());
				mapper.setOrgId(filterDetails.getOrgId());
				mapper.setUserId(filterDetails.getUserId());
				mapper.setFilterDetailsId(filterDetails.getFilterDetailsId());
				resultList.add(mapper);
				return resultList;
			}).collect(Collectors.toList());
		}

		return resultList;
	}

	@Override
	public List<DefinationMapper> getDefinationDetailsByName(String name) {
		List<DefinationDetails> list = definationRepository.findByNameContaining(name);
		List<DefinationMapper> resultList = new ArrayList<DefinationMapper>();
		if (null != list && !list.isEmpty()) {
			return list.stream().map(definationDetails->{
				System.out.println("DefinationDetails=========" + definationDetails.getDefinationId());
				DefinationMapper definationMapper = getDefinationDetails(definationDetails.getDefinationId());
				if (null != definationMapper) {
					resultList.add(definationMapper);
                    return definationMapper;
				}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;

	}

	@Override
	public SkillSetMapper updateCandidateSkillRoleBySkillSetDetailsId(String skillSetDetailsId,
			SkillSetMapper skillSetMapper) {
		SkillSetDetails skillSetDetails = skillSetRepository.getCandidateDetailsBySkillSetDetailsId(skillSetDetailsId);

		SkillSetMapper skillSetMapperr = new SkillSetMapper();
		if (skillSetDetails != null) {
			if (null != skillSetMapper.getSkillRole() && !skillSetMapper.getSkillRole().isEmpty()) {
				System.out.println("SkillRole======" + skillSetMapper.getSkillRole());
				skillSetDetails.setSkillRole(skillSetMapper.getSkillRole());
				skillSetRepository.save(skillSetDetails);

			}
			skillSetMapperr = candidateService.getSkillSet(skillSetDetails.getSkillSetDetailsId());
		}
		return skillSetMapperr;
	}

	@Override
	public List<CandidateRoleCountMapper> getCandidaterRoleByOrgId(String orgId) {

		List<CandidateRoleCountMapper> resultList = new ArrayList<CandidateRoleCountMapper>();

		List<RoleType> list = roleTypeRepository.findByorgId(orgId);

		if (null != list && !list.isEmpty()) {
			int count = 0;
			for (RoleType roleType : list) {

				List<CandidateDetails> list1 = candidateDetailsRepository
						.getCandidateRoleByRoleType(roleType.getRoleTypeId());

				if (null != list1 && !list1.isEmpty()) {

					CandidateRoleCountMapper candidateRoleCountMapper = new CandidateRoleCountMapper();

					for (CandidateDetails candidateDetails : list1) {

						CandidateDetails candidateDetails1 = candidateDetailsRepository
								.getcandidateDetailsById(candidateDetails.getCandidateId());

						if (null != candidateDetails1) {
							count++;
						}
					}
					candidateRoleCountMapper.setNumber(count);
					candidateRoleCountMapper.setName(roleType.getRoleType());
					resultList.add(candidateRoleCountMapper);
				}

			}

		}
		return resultList;
	}

	@Override
	public List<CandidateRoleCountMapper> getCandidaterRolesByUserId(String userId) {

		List<CandidateRoleCountMapper> resultList1 = new ArrayList<CandidateRoleCountMapper>();

		List<RoleType> list1 = roleTypeRepository.findByUserId(userId);

		if (null != list1 && !list1.isEmpty()) {
			int count = 0;
			for (RoleType roleType : list1) {

				List<CandidateDetails> list2 = candidateDetailsRepository
						.getCandidateRoleByRoleType(roleType.getRoleTypeId());

				if (null != list2 && !list2.isEmpty()) {

					CandidateRoleCountMapper candidateRoleCountMapper = new CandidateRoleCountMapper();

					for (CandidateDetails candidateDetails : list2) {

						CandidateDetails candidateDetails1 = candidateDetailsRepository
								.getcandidateDetailsById(candidateDetails.getCandidateId());

						if (null != candidateDetails1) {
							count++;
						}
					}
					candidateRoleCountMapper.setNumber(count);
					candidateRoleCountMapper.setName(roleType.getRoleType());
					resultList1.add(candidateRoleCountMapper);
				}

			}

		}
		return resultList1;
	}

	@Override
	public List<VideoClipsMapper> getCandidateVedioListByCandidateId(String candidateId) {

		List<CandidateVideoLink> candidateVedioLinkList = candidateVideoLinkRepository
				.getVedioByCandidateId(candidateId);
		List<VideoClipsMapper> resultList = new ArrayList<VideoClipsMapper>();

		if (candidateVedioLinkList != null && !candidateVedioLinkList.isEmpty()) {
			candidateVedioLinkList.stream().map(candidateVedioLink->{
				VideoClipsDetails voc = videoClipsDetailsRepository
						.getVideoClipsDetailsById(candidateVedioLink.getVideoClipsId());
				VideoClipsMapper videoClipsMapper = new VideoClipsMapper();

				videoClipsMapper.setShareInd(candidateVedioLink.isShareInd());
				videoClipsMapper.setCandidateId(candidateVedioLink.getCandidateId());

				if (null != voc) {

					videoClipsMapper.setVideoClipsId(voc.getVideoClipsId());
					videoClipsMapper.setVideoClipsName(voc.getVideoClipsName());
					videoClipsMapper.setVideoClipsTitle(voc.getVideoClipsTitle());
					videoClipsMapper.setVideoClipsDescription(voc.getVideoDescription());
					videoClipsMapper.setVideoClipsSize(voc.getVideoClipsSize());
					videoClipsMapper.setVideoClipsType(voc.getVideoClipsType());
					videoClipsMapper.setVideoClipsPath(voc.getVideoClipsPath());
					videoClipsMapper.setLastUpdateDate(Utility.getISOFromDate(voc.getLastUpdateDate()));
					videoClipsMapper.setCreationDate(Utility.getISOFromDate(voc.getCreationDate()));
					videoClipsMapper.setCreatorId(voc.getCreatorId());
					videoClipsMapper.setEmployeeId(voc.getEmployeeId());
					videoClipsMapper.setOrganizationId(voc.getOrgId());
					videoClipsMapper.setLiveInd(voc.isLiveInd());
					videoClipsMapper.setResource(voc.getResource());
					videoClipsMapper.setExtensionType(voc.getExtensionType());

					resultList.add(videoClipsMapper);
					return resultList;
				}
				return null;
			}).collect(Collectors.toList());
		}

		return resultList;
	}

	@Override
	public HashMap getRecordOfToday(String userId) {

		// Millseconds in a day
		final long ONE_DAY_MILLI_SECONDS = (24 * 60 * 60 * 1000) - 1;

		// date format
		LocalDateTime now = LocalDateTime.now();
		Date currDate = new GregorianCalendar(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth()).getTime();
		long nextDayMilliSeconds = currDate.getTime() + ONE_DAY_MILLI_SECONDS;
		Date nextDate = new Date(nextDayMilliSeconds);
		HashMap map = new HashMap();

		List<CandidateDetails> candidateList = candidateDetailsRepository
				.findByUserIdAndCreationDateBetweenAndLiveInd(userId, currDate, nextDate, true);
		System.out.println("candidate size" + candidateList.size());
		map.put("CandidateNo", candidateList.size());

		List<Customer> customer = customerRepository.getByUserIdAndCreationDateBetween(userId, currDate,
				nextDate);
		System.out.println("customer size" + customer.size());
		map.put("CustomerNo", customer.size());

		List<PartnerDetails> partnerDetails = partnerDetailsRepository
				.findByUserIdAndCreationDateBetweenAndLiveInd(userId, currDate, nextDate, true);
		System.out.println("Partner size" + partnerDetails.size());
		map.put("PartnerNo", partnerDetails.size());

		List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
				.findByUserIdAndCreationDateBetweenAndLiveInd(userId, currDate, nextDate, true);
		System.out.println("Recruit size" + recruitList.size());
		map.put("RecruitmentList", recruitList.size());

		List<OpportunityDetails> opList = opportunityDetailsRepository
				.findByUserIdAndCreationDateBetweenAndLiveInd(userId, currDate, nextDate, true);
		System.out.println("opportunity size" + opList.size());
		map.put("opportunityList", opList.size());

		List<RecruitmentPublishDetails> publishDetails = recruitmentPublishRepository
				.findByCreationDateBetweenAndLiveInd(currDate, nextDate, true);
		System.out.println("Publish size" + publishDetails.size());
		map.put("Publish", publishDetails.size());

		List<CallDetails> callDetails = callDetailsRepository
				.getCallListsByUserIdAndStartdateAndEndDateAndLiveInd(userId, currDate, nextDate);
		System.out.println("Call size" + callDetails.size());
		map.put("Call", callDetails.size());
		

		List<EventDetails> eventDetails = eventDetailsRepository
				.getEventListsByUserIdAndStartdateAndEndDateAndLiveInd(userId, currDate, nextDate);
		System.out.println("Event size" + eventDetails.size());
		map.put("Event", eventDetails.size());
		
		List<TaskDetails> taskDetails = taskDetailsRepository
				.getTaskListsByUserIdAndStartdateAndEndDateAndLiveInd(userId, currDate, nextDate);
		System.out.println("Task size" + taskDetails.size());
		map.put("Task", taskDetails.size());
		
		List<EmployeeDetails> empList = employeeRepository.getEmployeeListsByStartdateAndEndDateAndLiveInd(currDate, nextDate);
		System.out.println("Employee size" + empList.size());
		map.put("Employee", empList.size());
		
		List<Investor> investor = investorRepo.getInvestorListByUserIdAndDateRange(userId, currDate, nextDate);
		System.out.println("investor size" + investor.size());
		map.put("investor", investor.size());
		
	
		List<InvestorOpportunity> investorOpportunityList = investorOpportunityRepo.getAddedInvOpportunityDetailsUserIdWithDateRange(userId, currDate, nextDate);
		System.out.println("investorOpportunity size" + investorOpportunityList.size());
		map.put("investorOpportunity", investorOpportunityList.size());
		
		
		List<InvestorLeads> investorLeadsList = investorLeadsRepository.getLeadsListByUserIdAndDateRange(userId, currDate, nextDate);
		System.out.println("investorLeads size" + investorLeadsList.size());
		map.put("investorLeads", investorLeadsList.size());
		
		List<ContactDetails> contact=contactRepository.findByUserIdAndCreationDateBetweenAndContactType(userId,"Investor", currDate, nextDate);
		System.out.println("investorcontact size" + contact.size());
		map.put("investorcontact", contact.size());
		
		List<Leads> leads=leadsRepository.getLeadsListByUserIdAndDateRange(userId, currDate, nextDate);
		System.out.println("leads size" + leads.size());
		map.put("leads", leads.size());
		
		List<LeaveDetails> leave=leaveDetailsRepository.getLeavessByEmployeeIdWithDateRange(userId, currDate, nextDate);
		System.out.println("leave size" + leave.size());
		map.put("leave", leave.size());
		
		List<ExpenseDetails> expense=expenseRepository.getExpensesByEmployeeIdWithDateRange(userId, currDate, nextDate);
		System.out.println("expense size" + expense.size());
		map.put("expense", expense.size());
		
		List<MileageDetails> mileage=mileageRepository.getMileageByUserIdWithDateRange(userId, currDate, nextDate);
		System.out.println("mileage size" + mileage.size());
		map.put("mileage", mileage.size());
		
		List<ContactDetails> customerContact=contactRepository.findByUserIdAndCreationDateBetweenAndContactType(userId,"Customer", currDate, nextDate);
		System.out.println("customerContact size" + customerContact.size());
		map.put("customerContact", customerContact.size());
		
		List<LocationDetails> location = locationDetailsRepository.getLocationByUserIdWithDateRange(userId, currDate, nextDate);
		System.out.println("customerContact size" + customerContact.size());
		map.put("location", location.size());
		
		return map;
	}

	@Override
	public HashMap getCandidateCountListByName(String fullName, String orgId) {
		HashMap map = new HashMap();
		List<CandidateDetails> list = candidateDetailsRepository
				.findByFullNameContainingAndLiveIndAndOrganizationId(fullName, true, orgId);
		if (null != list && !list.isEmpty()) {
			map.put("record", list.size());
		}
		return map;
	}

	@Override
	public HashMap getCandidateCountListByIdNumber(String IdNumber, String orgId) {
		HashMap map = new HashMap();
		List<CandidateDetails> list = candidateDetailsRepository.findByIdNumberAndLiveIndAndOrganizationId(IdNumber,
				true, orgId);
		if (null != list && !list.isEmpty()) {
			map.put("record", list.size());
		}

		return map;
	}

	@Override
	public HashMap getCandidateCountListBySkillAndUserId(String skill, String userId) {
		int record = 0;
		HashMap map = new HashMap();
		List<DefinationDetails> list = definationRepository.getByNameAndUserId(skill, userId);
		if (null != list && !list.isEmpty()) {
			List<SkillSetDetails> skilllist = skillSetRepository.getSkillLinkBySkill(skill);

			if (null != skilllist && !skilllist.isEmpty()) {

				for (SkillSetDetails skillSetDetails : skilllist) {
					CandidateDetails candidateDetails = candidateDetailsRepository
							.getcandidateDetailsById(skillSetDetails.getCandidateId());
					record++;

				}
				map.put("count", record);
			}
		}
		return map;
	}

	@Override
	public HashMap getCandidateCountListByNameAndUserId(String fullName, String userId) {
		HashMap map = new HashMap();
		List<CandidateDetails> list = candidateDetailsRepository.findByFullNameContainingAndLiveIndAndUserId(fullName,
				true, userId);
		if (null != list && !list.isEmpty()) {
			map.put("record", list.size());
		}
		return map;
	}

	@Override
	public HashMap getCandidateCountListByIdNumberAndUserId(String IdNumber, String userId) {
		HashMap map = new HashMap();
		List<CandidateDetails> list = candidateDetailsRepository.findByIdNumberAndLiveIndAndUserId(IdNumber, true,
				userId);
		if (null != list && !list.isEmpty()) {
			map.put("record", list.size());
		}

		return map;
	}

	@Override
	public List<CandidateViewMapper> getCandidateDetailsBySkill(String skill) {

		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		List<DefinationDetails> list = definationRepository.getBySkillName(skill);
		if (null != list && !list.isEmpty()) {
			List<SkillSetDetails> skilllist = skillSetRepository.getSkillLinkBySkill(skill);
			System.out.println("@@@@@@@@@@@@^^^^^^^^^^^^" + skilllist.toString());
			System.out.println("skill%%%%%%" + skill);
			if (null != skilllist && !skilllist.isEmpty()) {
				skilllist.stream().map(skillSetDetails->{
					CandidateViewMapper candidateMapper = getCandidateDetailsById(skillSetDetails.getCandidateId());
					if (null != candidateMapper) {
						resultList.add(candidateMapper);
						return resultList;
					}
					return null;
				}).collect(Collectors.toList());

			}
		}
		return resultList;
	}

	@Override
	public List<CandidateViewMapper> getCandidateDetailsByName(String fullName) {
		List<CandidateDetails> list = candidateDetailsRepository.findByFullNameContainingAndLiveInd(fullName, true);
		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(candidateDetails->{
				CandidateViewMapper candidateMapper = getCandidateDetailsById(candidateDetails.getCandidateId());
				if (null != candidateMapper) {
					resultList.add(candidateMapper);
                    return resultList;
				}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public List<CandidateViewMapper> getCandidateDetailsBySkillAndUserId(String skill, String userId) {
		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		List<DefinationDetails> list = definationRepository.getByNameAndUserId(skill, userId);
		if (null != list && !list.isEmpty()) {
			List<SkillSetDetails> skilllist = skillSetRepository.getSkillLinkBySkill(skill);
			System.out.println("@@@@@@@@@@@@^^^^^^^^^^^^" + skilllist.toString());
			System.out.println("skill%%%%%%" + skill);
			if (null != skilllist && !skilllist.isEmpty()) {
				skilllist.stream().map(skillSetDetails->{
					CandidateViewMapper candidateMapper = getCandidateDetailsById(skillSetDetails.getCandidateId());
					if (null != candidateMapper) {
						resultList.add(candidateMapper);
                        return resultList;
					}
					return null;
				}).collect(Collectors.toList());

			}
		}
		return resultList;
	}

	@Override
	public List<CandidateViewMapper> getCandidateDetailsByNameAndUserId(String fullName, String userId) {
		List<CandidateDetails> list = candidateDetailsRepository.findByFullNameContainingAndLiveIndAndUserId(fullName,
				true, userId);
		System.out
				.println("candidate list====================----------------;;;;;;;;;;;;;////=====" + list.toString());
		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(candidateDetails->{
				CandidateViewMapper candidateMapper = getCandidateDetailsById(candidateDetails.getCandidateId());
				if (null != candidateMapper) {
					resultList.add(candidateMapper);
                    return resultList;
				}
				return null;
			}).collect(Collectors.toList());
		}
		return resultList;
	}

	@Override
	public List<CandidateViewMapper> getCandidateDetailsByIdNumberAndUserId(String IdNumber, String userId) {
		// System.out.println("the id is"+IdNumber);
		List<CandidateDetails> list = candidateDetailsRepository.findByIdNumberAndLiveIndAndUserId(IdNumber, true,
				userId);

		// System.out.println("LIST value is.........."+list);

		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		resultList = list.stream().map(li -> getCandidateDetailsById(li.getCandidateId())).collect(Collectors.toList());

		// System.out.println("Result list is......."+resultList);
		return resultList;
	}

	@Override
	public String saveCandidateCertification(CandidateCertificationLinkMapper candidateCertificationLinkMapper) {

		CertificationLibrary certificationLibrary1 = certificationLibraryRepository
				.getByCandidateCertificationNameAndLiveInd(
						candidateCertificationLinkMapper.getCandidateCertificationName());

		if (null != certificationLibrary1) {
			CandidateCertificationLink candidateCertificationLink1 = new CandidateCertificationLink();
			candidateCertificationLink1
					.setCandidateCertificationName(certificationLibrary1.getCertificationId());
			candidateCertificationLink1.setCandidateId(candidateCertificationLinkMapper.getCandidateId());
			candidateCertificationLink1.setCreationDate(new Date());
			candidateCertificationLink1.setUserId(candidateCertificationLinkMapper.getUserId());
			candidateCertificationLink1.setOrgId(candidateCertificationLinkMapper.getOrganizationId());

			candidateCertificationLinkRepository.save(candidateCertificationLink1);
		} else {

			CertificationLibrary newCertificationLibrary = new CertificationLibrary();
			
			newCertificationLibrary.setName(candidateCertificationLinkMapper.getCandidateCertificationName());
			newCertificationLibrary.setOrgId(candidateCertificationLinkMapper.getOrganizationId());
			newCertificationLibrary.setUserId(candidateCertificationLinkMapper.getUserId());
			newCertificationLibrary.setCreationDate(new Date());
			newCertificationLibrary.setLiveInd(true);
			newCertificationLibrary.setEditInd(true);
			certificationLibraryRepository.save(newCertificationLibrary);

			CandidateCertificationLink candidateCertificationLink1 = new CandidateCertificationLink();
			candidateCertificationLink1
					.setCandidateCertificationName(newCertificationLibrary.getCertificationId());
			candidateCertificationLink1.setCandidateId(candidateCertificationLinkMapper.getCandidateId());
			candidateCertificationLink1.setCreationDate(new Date());
			candidateCertificationLink1.setUserId(candidateCertificationLinkMapper.getUserId());
			candidateCertificationLink1.setOrgId(candidateCertificationLinkMapper.getOrganizationId());
			candidateCertificationLinkRepository.save(candidateCertificationLink1);
		}

		return candidateCertificationLinkMapper.getCandidateCertificationName();
	}

	@Override
	public List<CandidateCertificationLinkMapper> getCandidateCertificationDetails(String candidateId) {

		List<CandidateCertificationLink> candidateCertificationLinkList = candidateCertificationLinkRepository
				.getCandidateCertificationById(candidateId);
		List<CandidateCertificationLinkMapper> resultList = new ArrayList<CandidateCertificationLinkMapper>();
		if (null != candidateCertificationLinkList && !candidateCertificationLinkList.isEmpty()) {
			candidateCertificationLinkList.stream().map(candidateCertificationDetails->{
				CandidateCertificationLinkMapper candidateCertificationLinkMapper = new CandidateCertificationLinkMapper();
				
				CertificationLibrary certificationLibrary1 = certificationLibraryRepository
						.findByCertificationId(candidateCertificationDetails.getCandidateCertificationName());
				if(null!=certificationLibrary1) {

					candidateCertificationLinkMapper.setCandidateCertificationName(certificationLibrary1.getName());
					candidateCertificationLinkMapper.setCandiCertiLinkId(candidateCertificationDetails.getCandidateCertificationLinkId());
					candidateCertificationLinkMapper.setCandidateId(candidateCertificationDetails.getCandidateId());
					candidateCertificationLinkMapper.setCreationDate(Utility.getISOFromDate(candidateCertificationDetails.getCreationDate()));
					candidateCertificationLinkMapper.setOrganizationId(candidateCertificationDetails.getOrgId());
					candidateCertificationLinkMapper.setUserId(candidateCertificationDetails.getUserId());

					resultList.add(candidateCertificationLinkMapper);
				}
					return resultList;
			}).collect(Collectors.toList());

		}

		return resultList;
	}

	@Override
	public String deleteCandidateCertification(String candiCertiLinkId) {
		String message =null;
			if (null != candiCertiLinkId) {
				CandidateCertificationLink candidateCertificationLink = candidateCertificationLinkRepository
						.findByCandidateCertificationLinkId(candiCertiLinkId);
				if (null != candidateCertificationLink) {
					candidateCertificationLinkRepository.delete(candidateCertificationLink);
					message = "This is deleted successfully";
				}
			}
			return message;

		}

	@Override
	public boolean checkSkillInCustomerSkillSet(String skillName,String candidateId) {
		List<DefinationDetails> definationDetails = definationRepository.findByNameContaining(skillName);
		for (DefinationDetails definationDetails2 : definationDetails) {
		if (null!=definationDetails2) {
			List<SkillSetDetails> skillSetLink =skillSetRepository.findBySkillNameAndCandidateId(definationDetails2.getDefinationId(),candidateId);
			if (skillSetLink.size() > 0) {
			return true;
			}
		}
		}
		return false;
	}

	@Override
	public List<CandidateViewMapper> getCandidateOnboardedListByRecruitmentId(String userId, int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(0, 1);
		Pageable paging1 = PageRequest.of(pageNo, pageSize);
		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		List<RecruitProfileLinkDetails> recruitProfileLinkDetails = recruitmentProfileDetailsRepository.
				getProfileDetailsByUserIdAndOnboardedInd(userId,paging1).stream().map(data ->RecruitmentProfileDetailsConvertor.convertToDatabaseColumn(data) )
		        .collect(Collectors.toList());;

		if(null!=recruitProfileLinkDetails && !recruitProfileLinkDetails.isEmpty()) {
			System.out.println("recruitProfileLinkDetails.size()=====111111111111========/////"+recruitProfileLinkDetails.size());
			for(RecruitProfileLinkDetails CandidateId :recruitProfileLinkDetails) {
				System.out.println("recruitProfileLinkDetails1.getCandidateId()=====1========/////"+CandidateId.getCandidateId());
			List<RecruitProfileLinkDetails> recruitProfileLinkDetails2 = recruitmentProfileDetailsRepository.
					getProfileDetailsByCandidateIdAndOnboardedInd(CandidateId.getCandidateId(),paging);
			if(null!=recruitProfileLinkDetails2 && !recruitProfileLinkDetails2.isEmpty()) {
				System.out.println("recruitProfileLinkDetails2.size()=====22222222222========/////"+recruitProfileLinkDetails2.size());
				System.out.println("recruitProfileLinkDetails2.getOnboard_date()=====3========/////"+recruitProfileLinkDetails2.get(0).getOnboard_date());
			System.out.println("recruitProfileLinkDetails2.get(0).getCandidateId()======2=======/////"+recruitProfileLinkDetails2.get(0).getCandidateId());
				CandidateViewMapper candidateMapper = getCandidateDetailsById(recruitProfileLinkDetails2.get(0).getCandidateId());
				if(null!=candidateMapper) {
					System.out.println("candidateMapper.getCandidateId()=====3========/////"+candidateMapper.getCandidateId());
					candidateMapper.setOnboardCurrency(recruitProfileLinkDetails2.get(0).getOnboardCurrency());
					candidateMapper.setOnboardDate(Utility.getISOFromDate(recruitProfileLinkDetails2.get(0).getOnboard_date()));
					candidateMapper.setProjectName(recruitProfileLinkDetails2.get(0).getProjectName());
					candidateMapper.setActualEndDate(Utility.getISOFromDate(recruitProfileLinkDetails2.get(0).getActualEndDate()));
					candidateMapper.setFinalBilling(recruitProfileLinkDetails2.get(0).getFinalBilling());
					candidateMapper.setBillableHour(recruitProfileLinkDetails2.get(0).getBillableHour());
					
					OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository.getRecruitmentDetailsByRecruitId(recruitProfileLinkDetails2.get(0).getRecruitment_id());
					if(null!=opportunityRecruitDetails) {
						candidateMapper.setRequirementName(opportunityRecruitDetails.getName());
						candidateMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
					}
//					candidateMapper.setPageCount(list.getTotalPages());
//					candidateMapper.setDataCount(list.getSize());
//					candidateMapper.setListCount(list.getTotalElements());
					resultList.add(candidateMapper);
				}
		}
				System.out.println("999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
		}
	}
			
		return resultList;
	}

	@Override
	public String saveSkillCandidateNo(SkillCandidateNoMapper skillCandidateNoMapper) {
		String id = null;
		
		if (null != skillCandidateNoMapper) {
		SkillCandidateNo skillCandidateNo2 = new SkillCandidateNo();
		skillCandidateNo2.setSkill(skillCandidateNoMapper.getSkill());
		skillCandidateNo2.setNumber(skillCandidateNoMapper.getNumber());
		skillCandidateNo2.setUserId(skillCandidateNoMapper.getUserId());
		skillCandidateNo2.setOrganizationId(skillCandidateNoMapper.getOrganizationId());
		skillCandidateNo2.setCreationDate(new Date());
		id=skillCandidateNoRepository.save(skillCandidateNo2).getSkillCandidateNoId();
		
	}

		return id;
}

	@Override
	public List<SkillCandidateNoMapper> getskillCandidateNo(String orgId) {
		List<SkillCandidateNo> list =skillCandidateNoRepository.getskillCandidateNo(orgId);
		List<SkillCandidateNoMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {

			list.stream().map(li -> {
				SkillCandidateNoMapper skillCandidateNoMapper = new SkillCandidateNoMapper();

				DefinationDetails definationDetails1 = definationRepository.findByDefinationId(li.getSkill());
				if (null != definationDetails1) {
					skillCandidateNoMapper.setSkill(definationDetails1.getName());

				}
				skillCandidateNoMapper.setUserId(li.getUserId());
				skillCandidateNoMapper.setOrganizationId(li.getOrganizationId());
				skillCandidateNoMapper.setSkillCandidateNoId(li.getSkillCandidateNoId());
				skillCandidateNoMapper.setNumber(li.getNumber());
				skillCandidateNoMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
				mapperList.add(skillCandidateNoMapper);

				return mapperList;
			}).collect(Collectors.toList());
		}
		return mapperList;	
    }
	
		/* Collections.sort(mapperList, ( m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		
		List<DefinationDetailsDelete> definationDetailsDelete = definationDeleteRepository.findByOrgId(orgId);
		if (null != definationDetailsDelete && !definationDetailsDelete.isEmpty()) {
			Collections.sort(definationDetailsDelete,
					( p1,p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			
			mapperList.get(0).setUpdationDate(Utility.getISOFromDate(definationDetailsDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(definationDetailsDelete.get(0).getUserId());
			if(null!=employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					mapperList.get(0).setUpdatedName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					mapperList.get(0).setUpdatedName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}*/
	
	@Override
	public String deleteAndReinstateCandidateByCandidateId(String candidateId,
			CandidateMapper candidateMapper) {
		String message =null;
		CandidateDetails candidateDetails = candidateDetailsRepository.getcandidateDetailsById(candidateId);
		if(null!=candidateDetails) {
			if(candidateMapper.isReInStateInd()==true) {
			candidateDetails.setReInStateInd(candidateMapper.isReInStateInd());
			candidateDetailsRepository.save(candidateDetails);
			message = "Candidate deleted successfully ";
			
			NotificationDetails notification = new NotificationDetails();
			String middleName2 =" ";
			String lastName2 ="";
			String satutation1 ="";

			if(!StringUtils.isEmpty(candidateMapper.getLastName())) {
				 
				lastName2 = candidateMapper.getLastName();
			 }
			 if(candidateMapper.getSalutation() != null && candidateMapper.getSalutation().length()>0) {
				 satutation1 = candidateMapper.getSalutation();
			 }


			 if(candidateMapper.getMiddleName() != null && candidateMapper.getMiddleName().length()>0) {

			 
				 middleName2 = candidateMapper.getMiddleName();
				 notification.setMessage(satutation1+" "+candidateMapper.getFirstName()+" "+middleName2+" "+lastName2+ " Candidate deleted.");
			 }else {
				 
				 notification.setMessage(satutation1+" "+candidateMapper.getFirstName()+" "+lastName2+ " Candidate deleted.");
			 }
			
			 notification.setAssignedTo(candidateMapper.getUserId());
			 
			notification.setMessageReadInd(false);
			notification.setNotificationDate(new Date());
			notification.setNotificationType("Candidate delete");
			notification.setUser_id(candidateMapper.getUserId());
			notification.setOrg_id(candidateMapper.getOrganizationId());
			notification.setLiveInd(true);
			notification.setCreationDate(new Date());
			notificationRepository.save(notification);
			
			notificationService.createNotification(candidateMapper.getUserId(),"candidate delete","candidate delete by "+ message,"Candidate","Delete");	
			}else {
				candidateDetails.setReInStateInd(candidateMapper.isReInStateInd());
				candidateDetailsRepository.save(candidateDetails);
				
				NotificationDetails notification = new NotificationDetails();
				String middleName2 =" ";
				String lastName2 ="";
				String satutation1 ="";

				if(!StringUtils.isEmpty(candidateMapper.getLastName())) {
					 
					lastName2 = candidateMapper.getLastName();
				 }
				 if(candidateMapper.getSalutation() != null && candidateMapper.getSalutation().length()>0) {
					 satutation1 = candidateMapper.getSalutation();
				 }


				 if(candidateMapper.getMiddleName() != null && candidateMapper.getMiddleName().length()>0) {

				 
					 middleName2 = candidateMapper.getMiddleName();
					 notification.setMessage(satutation1+" "+candidateMapper.getFirstName()+" "+middleName2+" "+lastName2+ " Candidate reinstate.");
				 }else {
					 
					 notification.setMessage(satutation1+" "+candidateMapper.getFirstName()+" "+lastName2+ " Candidate reinstate.");
				 }
				
				 notification.setAssignedTo(candidateMapper.getUserId());
				 
				notification.setMessageReadInd(false);
				notification.setNotificationDate(new Date());
				notification.setNotificationType("Candidate reinstate");
				notification.setUser_id(candidateMapper.getUserId());
				notification.setOrg_id(candidateMapper.getOrganizationId());
				notification.setLiveInd(true);
				notification.setCreationDate(new Date());
				notificationRepository.save(notification);
				
				message = "Candidate reinstate successfully ";
				notificationService.createNotification(candidateMapper.getUserId(),"candidate reinstate", "candidate reinstate by " +message,"Candidate","Delete");	
			}
		}
		return message;
	}

	@Override
	public List<CandidateViewMapper> getDeletedCandidateDetailsByUserId(String userId, int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by("creationDate").descending());
		
		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		Page<CandidateDetails> candidateList = candidateDetailsRepository.getDeletedCandidatePageByUserId(userId,paging);
		if (null != candidateList && !candidateList.isEmpty()) {
			candidateList.stream().map(candidate->{
				CandidateViewMapper candidateMapper = getCandidateDetailsById(candidate.getCandidateId());
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(candidate.getLastName())) {

					lastName = candidate.getLastName();
				}

				if (candidate.getMiddleName() != null && candidate.getMiddleName().length() > 0) {

					middleName = candidate.getMiddleName();
					candidateMapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
				} else {

					candidateMapper.setFullName(candidate.getFirstName() + " " + lastName);
				}
				candidateMapper.setPageCount(candidateList.getTotalPages());
				candidateMapper.setDataCount(candidateList.getSize());
				candidateMapper.setListCount(candidateList.getTotalElements());
				resultList.add(candidateMapper);
				return resultList;

			}).collect(Collectors.toList());
		}
		return resultList;
	}
	
	@Override
	public List<CandidateViewMapper> getCandidateListByUserId(String userId) {
	
		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();

		List<CandidateDetails> candidateList = candidateDetailsRepository.getCandidateByUserId(userId);
		if (null != candidateList && !candidateList.isEmpty()) {
			candidateList.stream().map(candidate->{
				CandidateViewMapper candidateMapper = getCandidateDetailsById(candidate.getCandidateId());
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(candidate.getLastName())) {

					lastName = candidate.getLastName();
				}

				if (candidate.getMiddleName() != null && candidate.getMiddleName().length() > 0) {

					middleName = candidate.getMiddleName();
					candidateMapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
				} else {

					candidateMapper.setFullName(candidate.getFirstName() + " " + lastName);
				}
				resultList.add(candidateMapper);
				return resultList;
			}).collect(Collectors.toList());

			}
		return resultList;
	}
	
	@Override
	public List<CandidateViewMapper> getAllCandidateListCount() {
		List<CandidateViewMapper> resultMapper = new ArrayList<CandidateViewMapper>();
		List<Permission> permission = permissionRepository.getUserList();
		System.out.println(" user$$$$$$$$$$$$==" + permission.toString());
		if (null != permission && !permission.isEmpty()) {
			permission.stream().map(permissionn->{
				List<CandidateViewMapper> mp = candidateService.getCandidateListByUserId(permissionn.getUserId());
				System.out.println(" userId$$$$$$$$$$$$==" + permissionn.getUserId());
				resultMapper.addAll(mp);
				return resultMapper;
			}).collect(Collectors.toList());
            return null;
		}
		return resultMapper;

	}
	
	@Override
	public List<CandidateViewMapper> getAllCandidateListByCategory(int pageNo, int pageSize,String category) {
//		List<CandidateViewMapper> resultMapper = new ArrayList<CandidateViewMapper>();
//		List<Permission> permission = permissionRepository.getUserList();
//		System.out.println(" user$$$$$$$$$$$$==" + permission.toString());
//		if (null != permission && !permission.isEmpty()) {
//			permission.stream().map(permissionn->{
//				List<CandidateViewMapper> mp  = candidateService.getcandidateLisstByCategory(category,permissionn.getUserId(),pageNo,pageSize);
//				if (null != mp && !mp.isEmpty()) {
//				System.out.println(" userId$$$$$$$$$$$$==" + permissionn.getUserId());
//				System.out.println(" resultMapper=======$$$$$$$$$$$$==" + resultMapper.toString());
//				resultMapper.addAll(mp);
//				return resultMapper;
//				}
//				return null;
//			}).collect(Collectors.toList());
//		}
//		System.out.println(" resultMapper=======$$$$$$outer$$$$$==" + resultMapper.toString());
//		return resultMapper;
		List<String> userId = permissionRepository.getUserList()
				.stream()
				.map(Permission::getUserId).collect(Collectors.toList());
		return candidateService.getcandidateLisstByCategoryAndUserIds(category,userId,pageNo,pageSize);
	}
	
	@Override
	public List<CandidateViewMapper> getCandidateDetailsBySkillAndCategory(String skill, String category) {
		List<SkillSetDetails> skilllist = skillSetRepository.getSkillLinkBySkill(skill);
		System.out.println("@@@@@@@@@@@@^^^^^^^^^^^^" + skilllist.toString());
		System.out.println("skill%%%%%%" + skill);
		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		if (null != skilllist && !skilllist.isEmpty()) {
			skilllist.stream().map(skillSetDetails->{
				CandidateDetails candidateDetails =candidateDetailsRepository.getCandidateListByCategoryAndCandidateIdAndLiveInd(category,skillSetDetails.getCandidateId());
				if (null != candidateDetails) {
				CandidateViewMapper candidateMapper = getCandidateDetailsById(candidateDetails.getCandidateId());
				if (null != candidateMapper) {
					resultList.add(candidateMapper);
                    return resultList;
				}
			}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;
	}
	
	@Override
	public List<CandidateViewMapper> getCandidateDetailsByNameAndCategory(String fullName, String category) {
		List<CandidateDetails> list = candidateDetailsRepository.findByFullNameContainingAndCategoryAndLiveInd(fullName,category,true);
		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(candidateDetails->{
				CandidateViewMapper candidateMapper = getCandidateDetailsById(candidateDetails.getCandidateId());
				if (null != candidateMapper) {
					resultList.add(candidateMapper);
					return resultList;

				}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;
	}
	
	@Override
	public List<CandidateViewMapper> getCandidateDetailsByIdNumberAndCategory(String idNumber, String category) {
		List<CandidateDetails> list = candidateDetailsRepository.findByIdNumberAndCategoryAndLiveInd(idNumber,category,true);
		
		//System.out.println("LIST value is.........."+list);
		
		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		resultList = list.stream().map(li->getCandidateDetailsById(li.getCandidateId())).collect(Collectors.toList());
		
		//System.out.println("Result list is......."+resultList);
		return resultList;
	}

	@Override
	public SkillSetMapper pauseAndUnpauseCandidateSkillExperience(String skillId, SkillSetMapper skillSetMapper) {
	SkillSetMapper result = new SkillSetMapper();
		SkillSetDetails skill = skillSetRepository.getById(skillId);
		if (null != skill) {
			if (skillSetMapper.isPauseInd()==true) {
			skill.setPauseInd(skillSetMapper.isPauseInd());
			skill.setPauseDate(new Date());
			skill.setPauseExperience(getExprience(skill.getCreationDate()) + skill.getExperience());
			skillSetRepository.save(skill);
			}else {
				skill.setPauseInd(skillSetMapper.isPauseInd());
				skill.setUnpauseDate(new Date());
				skillSetRepository.save(skill);
			}
			result = getSkillSet(skillId);
		}
		
		return result;
	}
	
	@Override
	public String getCandidateFullName(String candidateId) {
		String fullName = "";

		CandidateDetails candidateDetails = candidateDetailsRepository.getcandidateDetailsById(candidateId);
		if (null != candidateDetails) {
			String middleName = " ";
			String lastName = "";
		
		if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

			lastName = candidateDetails.getLastName();
		}

		if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

			middleName = candidateDetails.getMiddleName();
			fullName = (candidateDetails.getFirstName() + " " + middleName + " " + lastName);
		} else {

			fullName = (candidateDetails.getFirstName() + " " + lastName);
		}
		}
	
	return fullName;
	}

	@Override
	public List<CandidateViewMapper> getBillabeCandidateListByUserId(String userId, int pageNo, int pageSize,String month, String year) {
		Pageable paging = PageRequest.of(0, 1);
		Pageable paging1 = PageRequest.of(pageNo, pageSize);
		
		List<String> arr2 = new ArrayList<>();
		arr2.add("Jan");
		arr2.add("Feb");
		arr2.add("Mar");
		arr2.add("Apr");
		arr2.add("May");
		arr2.add("Jun");
		arr2.add("Jul");
		arr2.add("Aug");
		arr2.add("Sep");
		arr2.add("Oct");
		arr2.add("Nov");
		arr2.add("Dec");

		System.out.println("arr.size===========================" + arr2.size());

		int monthNo = arr2.indexOf(month) + 1;
		int year1 = Integer.parseInt(year);

		YearMonth yearMonth = YearMonth.of(year1, monthNo); // 2015-01. January of 2015.
		LocalDate startDateOfMonth = yearMonth.atDay(1); // 2015-01-01
		Date start_date = null;

		try {
			start_date = Utility.getUtilDateByLocalDate(startDateOfMonth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		List<CandidateViewMapper> resultList = new ArrayList<CandidateViewMapper>();
		List<RecruitProfileLinkDetails> recruitProfileLinkDetails = recruitmentProfileDetailsRepository.
				getProfileDetailsByUserIdAndOnboardedInd(userId,paging1).stream().map(data ->RecruitmentProfileDetailsConvertor.convertToDatabaseColumn(data) )
		        .collect(Collectors.toList());;

		if(null!=recruitProfileLinkDetails && !recruitProfileLinkDetails.isEmpty()) {
			System.out.println("recruitProfileLinkDetails.size()=====111111111111========/////"+recruitProfileLinkDetails.size());
			for(RecruitProfileLinkDetails CandidateId :recruitProfileLinkDetails) {
				System.out.println("recruitProfileLinkDetails1.getCandidateId()=====1========/////"+CandidateId.getCandidateId());
			List<RecruitProfileLinkDetails> recruitProfileLinkDetails2 = recruitmentProfileDetailsRepository.
					getProfileDetailsByCandidateIdAndOnboardedInd(CandidateId.getCandidateId(),paging);
			if(null!=recruitProfileLinkDetails2 && !recruitProfileLinkDetails2.isEmpty()) {
				System.out.println("recruitProfileLinkDetails2.size()=====22222222222========/////"+recruitProfileLinkDetails2.size());
				System.out.println("recruitProfileLinkDetails2.getOnboard_date()=====3========/////"+recruitProfileLinkDetails2.get(0).getOnboard_date());
			System.out.println("recruitProfileLinkDetails2.get(0).getCandidateId()======2=======/////"+recruitProfileLinkDetails2.get(0).getCandidateId());
		//	if(start_date.compareTo(end_date)&&(recruitProfileLinkDetails2.get(0).getActualEndDate()).compareTo(end_date)) {
				if(recruitProfileLinkDetails2.get(0).getActualEndDate().compareTo(start_date)>=0){
				CandidateViewMapper candidateMapper = getCandidateDetailsById(recruitProfileLinkDetails2.get(0).getCandidateId());
				if(null!=candidateMapper) {
					System.out.println("candidateMapper.getCandidateId()=====3========/////"+candidateMapper.getCandidateId());
					candidateMapper.setOnboardCurrency(recruitProfileLinkDetails2.get(0).getOnboardCurrency());
					candidateMapper.setOnboardDate(Utility.getISOFromDate(recruitProfileLinkDetails2.get(0).getOnboard_date()));
					candidateMapper.setProjectId(recruitProfileLinkDetails2.get(0).getProjectName());
					ProjectDetails projectDetails = projectRepository.getById(recruitProfileLinkDetails2.get(0).getProjectName());
					if (null != projectDetails) {
						candidateMapper.setProjectName(projectDetails.getProjectName());
					}
					
					candidateMapper.setCustomerId(recruitProfileLinkDetails2.get(0).getCustomerId());
					Customer customer = customerRepository
							.getCustomerDetailsByCustomerId(recruitProfileLinkDetails2.get(0).getCustomerId());
					if (null != customer) {
						candidateMapper.setCustomerName(customer.getName());
					}
					candidateMapper.setActualEndDate(Utility.getISOFromDate(recruitProfileLinkDetails2.get(0).getActualEndDate()));
					candidateMapper.setFinalBilling(recruitProfileLinkDetails2.get(0).getFinalBilling());
					candidateMapper.setBillableHour(recruitProfileLinkDetails2.get(0).getBillableHour());
					
					OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository.getRecruitmentDetailsByRecruitId(recruitProfileLinkDetails2.get(0).getRecruitment_id());
					if(null!=opportunityRecruitDetails) {
						candidateMapper.setRequirementName(opportunityRecruitDetails.getName());
						candidateMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
					}
					resultList.add(candidateMapper);
				}
		}
			}
				System.out.println("999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
		}
	}
			
		return resultList;
	}
	
	@Override
	public CandidateViewMapper activeAndInActiveCandidateByCandidateId(String candidateId,
			CandidateMapper candidateMapper) {
		CandidateViewMapper candidateViewMapper= new CandidateViewMapper();
		CandidateDetails candidateDetails = candidateDetailsRepository.getcandidateDetailsById(candidateId);
		if(null!=candidateDetails) {
			if(candidateMapper.isActive()==true) {
				candidateDetails.setActive(true);
				CandidateDetails updateCandidate = candidateDetailsRepository.save(candidateDetails);
				candidateViewMapper = getCandidateMapper(updateCandidate);
				
			}else {
				candidateDetails.setActive(false);
				CandidateDetails updateCandidate = candidateDetailsRepository.save(candidateDetails);
				candidateViewMapper = getCandidateMapper(updateCandidate);
			}
		}
		return candidateViewMapper;
	}


	@Override
	public void deleteDefination(String definationId) {
		DefinationDetails definationDetails=definationRepository.findByDefinationId(definationId);
		if(null!=definationDetails) {
			DefinationDetailsDelete definationDelete=definationDeleteRepository.findByDefinationId(definationId);
			if(null!=definationDelete) {
				definationDelete.setUpdationDate(new Date());
				definationDelete.setName(definationDetails.getUser_id());
				definationDeleteRepository.save(definationDelete);
			}
			
			definationDetails.setLiveInd(false);
			definationRepository.save(definationDetails);
		}
		
	}

//	@Override
//	public NotesMapper updateNoteDetails(NotesMapper notesMapper) {
//	
//		NotesMapper resultMapper = employeeService.updateNotes(notesMapper);
//	
//		return resultMapper;
//	}
	
	@Override
	public void deleteCandidateNotesById(String notesId) {
		CandidateNotesLink notesList = candidateNotesLinkRepository.findByNotesId(notesId);
		if (null != notesList) {
			
			Notes notes = notesRepository.findByNoteId(notesId);
			if (null!=notes) {
				notes.setLiveInd(false);
				notesRepository.save(notes);
			}
		}		
	}

	@Override
	public String hardDeleteCandidate(String candidateId) {
		String msg = null;
		//Candidate Info
		CandidateInfo candidateInfo = candidateInfoRepository.getById(candidateId);
		if(null!=candidateInfo){
			candidateInfoRepository.delete(candidateInfo);
		}
		//Candidate
		List<CandidateDetails> candidateDetails = candidateDetailsRepository.findByCandidateId(candidateId);
		if(null!=candidateDetails && !candidateDetails.isEmpty()) {
			candidateDetailsRepository.deleteAll(candidateDetails);
		}
		//Video
		List<CandidateVideoLink> candidateVideoLink = candidateVideoLinkRepository
				.getVedioByCandidateId(candidateId);
		if (null != candidateVideoLink && !candidateVideoLink.isEmpty()) {
			for (CandidateVideoLink candidateVedioLink1:candidateVideoLink){
				VideoClipsDetails voc = videoClipsDetailsRepository
						.getVideoClipsDetailsByIdWithOutLiveInd(candidateVedioLink1.getVideoClipsId());
				if (null != voc) {
					videoClipsDetailsRepository.delete(voc);
				}
			}
			candidateVideoLinkRepository.deleteAll(candidateVideoLink);
		}
		//Skill Set
		List<SkillSetDetails> skillList1=skillSetRepository.getSkillSetById(candidateId);
		if (null != skillList1 && !skillList1.isEmpty()) {
			skillSetRepository.deleteAll(skillList1);
		}
		//Address
		List<CandidateAddressLink> candidateAddressLinkList = candidateAddressLinkRepository
				.getAddressListByCandidateId(candidateId);
		if (null != candidateAddressLinkList && !candidateAddressLinkList.isEmpty()) {
			for (CandidateAddressLink address : candidateAddressLinkList) {
				AddressDetails addressDetails = addressRepository
						.getAddressDetailsByAddressIdWithOutLiveInd(address.getAddressId());
				if(null!=addressDetails){
					addressRepository.delete(addressDetails);
				}
			}
			candidateAddressLinkRepository.deleteAll(candidateAddressLinkList);
		}
		//Training
		List<CandidateTraining> trainingList = candidateTrainingRepository.getCandidateTrainingById(candidateId);
		if (null != trainingList && !trainingList.isEmpty()) {
			candidateTrainingRepository.deleteAll(trainingList);
		}
		//Notes
		List<CandidateNotesLink> candidateNotesLinkList = candidateNotesLinkRepository
				.getNoteListByCandidateId(candidateId);
		if (candidateNotesLinkList != null && !candidateNotesLinkList.isEmpty()) {
			for (CandidateNotesLink candidateNotesLink : candidateNotesLinkList) {
				Notes notes = notesRepository.findByNoteIdWithOutLiveInd(candidateNotesLink.getNotesId());
				if(notes!=null) {
					notesRepository.delete(notes);
				}
			}
			candidateNotesLinkRepository.deleteAll(candidateNotesLinkList);
		}
		//Document
		List<CandidateDocumentLink> candidateDocumentLinkList = candidateDocumentLinkRepository
				.getDocumentByCandidateId(candidateId);
		List<DocumentMapper> resultList = new ArrayList<DocumentMapper>();

		if (candidateDocumentLinkList != null && !candidateDocumentLinkList.isEmpty()) {

			for (CandidateDocumentLink candidateDocumentLink : candidateDocumentLinkList) {
				DocumentDetails doc = documentDetailsRepository.getDocumentDetailsByIdWithOutLiveInd(candidateDocumentLink.getDocumentId());
				if (null != doc) {
					documentDetailsRepository.delete(doc);
				}
			}
			candidateDocumentLinkRepository.deleteAll(candidateDocumentLinkList);
		}
		//Education
		List<CandidateEducationDetails> educationalList = candidateEducationDetailsRepository
				.getCandidateEducationDetailsById(candidateId);
		if (null != educationalList && !educationalList.isEmpty()) {
			candidateEducationDetailsRepository.deleteAll(educationalList);
		}
		//Employeement
		List<CandidateEmploymentHistory> employmentlList = candidateEmploymentHistoryRepository
				.getCandidateEmploymentHistoryByIdWithOutLiveInd(candidateId);
		if (null != employmentlList && !employmentlList.isEmpty()) {
			candidateEmploymentHistoryRepository.deleteAll(employmentlList);
		}
		//BankDetails
		List<CandidateBankDetails> bankDetailsList = candidateBankDetailsRepository.getBankDetailsByIdWithOutLiveInd(candidateId);
		if (null != bankDetailsList && !bankDetailsList.isEmpty()) {
			candidateBankDetailsRepository.deleteAll(bankDetailsList);
		}
		//event
		List<CandidateEventLink> eventdetailsList = candidateEventLinkRepository.getEventListByCandidateIdWithOutLiveInd(candidateId);
		if (null != eventdetailsList && !eventdetailsList.isEmpty()) {
			for (CandidateEventLink candidateEventLink : eventdetailsList) {
				EventDetails eventDetails = eventDetailsRepository.getEventDetailsByIdWithOutLiveInd(candidateEventLink.getEventId());
				if(null!=eventDetails){
					eventDetailsRepository.delete(eventDetails);
				}
			}
			candidateEventLinkRepository.deleteAll(eventdetailsList);
		}
		//Call
		List<CallCandidateLink> callList = callCandidateLinkRepository.getCallListByCandidateIdWithOutLiveInd(candidateId);
		if (null != callList && !callList.isEmpty()) {
			for (CallCandidateLink callCandidateLink : callList) {
				CallDetails callDetails = callDetailsRepository.getCallDetailsByIdWithOutLiveInd(callCandidateLink.getCallId());
				if (null!=callDetails) {
					callDetailsRepository.delete(callDetails);
				}
			}
			callCandidateLinkRepository.deleteAll(callList);
		}
		//Task
		List<CandidateTaskLink> taskList = candidateTaskLinkRepository.getTaskListByCandidateIdWithOutLiveInd(candidateId);
		if (null != taskList && !taskList.isEmpty()) {
			for (CandidateTaskLink candidateTaskLink : taskList) {
				TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsByIdWithOutLiveInd(candidateTaskLink.getTaskId());
				if (null != taskDetails) {
					taskDetailsRepository.delete(taskDetails);
				}
			}
			candidateTaskLinkRepository.deleteAll(taskList);
		}
		//Certification
		List<CandidateCertificationLink> candidateCertificationLinkList = candidateCertificationLinkRepository
				.getCandidateCertificationById(candidateId);
		if (null != candidateCertificationLinkList && !candidateCertificationLinkList.isEmpty()) {
			for (CandidateCertificationLink candidateCertificationDetails:candidateCertificationLinkList) {
				CertificationLibrary certificationLibrary1 = certificationLibraryRepository
						.findByCertificationId(candidateCertificationDetails.getCandidateCertificationName());
				if (null != certificationLibrary1) {
					certificationLibraryRepository.delete(certificationLibrary1);
				}
			}
			candidateCertificationLinkRepository.deleteAll(candidateCertificationLinkList);
		}
		//Requirment Process
		List<RecruitmentCandidateLink> recruitCandidateList = recruitmentCandidateLinkRepository
				.getCandidateRecruitmentLinkByCandidateIdWithOutLiveInd(candidateId);
		if (null != recruitCandidateList && !recruitCandidateList.isEmpty()) {
			recruitmentCandidateLinkRepository.deleteAll(recruitCandidateList);
		}

		List<RecruitProfileLinkDetails> profile = recruitmentProfileDetailsRepository
				.getProfileDetailsByCandidateIdWithOutLiveInd(candidateId);
		if (null != recruitCandidateList && !recruitCandidateList.isEmpty()) {
			for (RecruitProfileLinkDetails li :profile){
				RecruitProfileStatusLink status = recruitmentProfileStatusLinkRepository.
						getRecruitProfileStatusLinkByProfileId(li.getProfile_id());
				if (null != status) {
					recruitmentProfileStatusLinkRepository.delete(status);
				}
				RecruitStageNoteLink recruitStageNoteLink = recruitStageNotelinkRepository.getByProfileId(li.getProfile_id());
				if(null!=recruitStageNoteLink) {
					recruitStageNotelinkRepository.delete(recruitStageNoteLink);
				}
				RecruitmentProfileInfo recruitmentProfileInfo = recruitmentProfileInfoRepository.getById(li.getProfile_id());
				if(null!=recruitmentProfileInfo){
					recruitmentProfileInfoRepository.delete(recruitmentProfileInfo);
				}

			}
			recruitmentProfileDetailsRepository.deleteAll(profile);
		}

		msg= "deleted all data of this candidate";
		return msg;
	}


	@Override
	public HashMap getDefinationsCountByOrgId(String orgId) {
		List<DefinationDetails> list = definationRepository.getDefinationsOfAdmin(orgId);
		HashMap map = new HashMap();
        map.put("definationCount", list.size());
        return map;
	}

}