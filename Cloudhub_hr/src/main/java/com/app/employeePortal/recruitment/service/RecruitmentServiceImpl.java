 package com.app.employeePortal.recruitment.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
import com.app.employeePortal.Opportunity.entity.OpportunitySalesUserLink;
import com.app.employeePortal.Opportunity.repository.OpportunityDetailsRepository;
import com.app.employeePortal.Opportunity.repository.OpportunitySalesUserRepository;
import com.app.employeePortal.action.entity.ActionHistory;
import com.app.employeePortal.action.mapper.ActionHistoryMapper;
import com.app.employeePortal.action.repository.ActionHistoryRepository;
import com.app.employeePortal.address.entity.AddressDetails;
import com.app.employeePortal.address.entity.AddressInfo;
import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.address.repository.AddressInfoRepository;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.candidate.entity.CandidateAddressLink;
import com.app.employeePortal.candidate.entity.CandidateDetails;
import com.app.employeePortal.candidate.entity.CandidateDocumentLink;
import com.app.employeePortal.candidate.entity.DefinationDetails;
import com.app.employeePortal.candidate.entity.SkillSetDetails;
import com.app.employeePortal.candidate.mapper.CandidateMapper;
import com.app.employeePortal.candidate.mapper.CandidateViewMapper;
import com.app.employeePortal.candidate.mapper.CandidateWebsiteMapper;
import com.app.employeePortal.candidate.repository.CandidateAddressLinkRepository;
import com.app.employeePortal.candidate.repository.CandidateDetailsRepository;
import com.app.employeePortal.candidate.repository.CandidateDocumentLinkRepository;
import com.app.employeePortal.candidate.repository.DefinationRepository;
import com.app.employeePortal.candidate.repository.SkillSetRepository;
import com.app.employeePortal.candidate.service.CandidateService;
import com.app.employeePortal.category.repository.RoleTypeRepository;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.entity.CustomerAddressLink;
import com.app.employeePortal.customer.entity.CustomerRecruitUpdate;
import com.app.employeePortal.customer.mapper.CustomerNetflixMapper;
import com.app.employeePortal.customer.mapper.CustomerRecruitmentMapper;
import com.app.employeePortal.customer.repository.CustomerAddressLinkRepository;
import com.app.employeePortal.customer.repository.CustomerRecruitUpdateRepository;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.entity.DocumentType;
import com.app.employeePortal.document.repository.DocumentDetailsRepository;
import com.app.employeePortal.document.repository.DocumentTypeRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.mapper.EmployeeMapper;
import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.features.entity.JobPublishDetails;
import com.app.employeePortal.features.repository.JobPublishRepository;
import com.app.employeePortal.organization.repository.OrganizationRepository;
import com.app.employeePortal.partner.entity.PartnerDetails;
import com.app.employeePortal.partner.mapper.PartnerMapper;
import com.app.employeePortal.partner.repository.PartnerDetailsRepository;
import com.app.employeePortal.project.Entity.ProjectDetails;
import com.app.employeePortal.project.Repository.ProjectRepository;
import com.app.employeePortal.recruitment.entity.ApprovalTaskLink;
import com.app.employeePortal.recruitment.entity.Commission;
import com.app.employeePortal.recruitment.entity.GlobalProcessDetails;
import com.app.employeePortal.recruitment.entity.GlobalProcessDetailsDelete;
import com.app.employeePortal.recruitment.entity.GlobalProcessInfo;
import com.app.employeePortal.recruitment.entity.GlobalProcessStageLink;
import com.app.employeePortal.recruitment.entity.OpportunityRecruitDetails;
import com.app.employeePortal.recruitment.entity.OpportunityRecuitInfo;
import com.app.employeePortal.recruitment.entity.ProcessDocumentLink;
import com.app.employeePortal.recruitment.entity.ProcessStageTaskLink;
import com.app.employeePortal.recruitment.entity.RecruitProfileLinkDetails;
import com.app.employeePortal.recruitment.entity.RecruitProfileStatusLink;
import com.app.employeePortal.recruitment.entity.RecruitStageNoteLink;
import com.app.employeePortal.recruitment.entity.RecruitmentAddressLink;
import com.app.employeePortal.recruitment.entity.RecruitmentAverageFeedback;
import com.app.employeePortal.recruitment.entity.RecruitmentCandidateLink;
import com.app.employeePortal.recruitment.entity.RecruitmentCloseRule;
import com.app.employeePortal.recruitment.entity.RecruitmentPartnerLink;
import com.app.employeePortal.recruitment.entity.RecruitmentProcessStageDetails;
import com.app.employeePortal.recruitment.entity.RecruitmentProcessStageDetailsDelete;
import com.app.employeePortal.recruitment.entity.RecruitmentProcessStageInfo;
import com.app.employeePortal.recruitment.entity.RecruitmentProfileInfo;
import com.app.employeePortal.recruitment.entity.RecruitmentPublishDetails;
import com.app.employeePortal.recruitment.entity.RecruitmentRecruiterLink;
import com.app.employeePortal.recruitment.entity.RecruitmentSkillsetLink;
import com.app.employeePortal.recruitment.entity.RecruitmentStageApprove;
import com.app.employeePortal.recruitment.entity.TaskLevelLink;
import com.app.employeePortal.recruitment.entity.Upwork;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.entity.WebsitePartnerLink;
import com.app.employeePortal.recruitment.mapper.CandidateProjectMapper;
import com.app.employeePortal.recruitment.mapper.CommissionMapper;
import com.app.employeePortal.recruitment.mapper.FunnelMapper;
import com.app.employeePortal.recruitment.mapper.JobPublishAddressMapper;
import com.app.employeePortal.recruitment.mapper.JobVacancyMapper;
import com.app.employeePortal.recruitment.mapper.PingMapper;
import com.app.employeePortal.recruitment.mapper.ProcessDocumentLinkMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentActionMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentCloseRuleMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentOpportunityMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentProcessMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentRecruitOwnerMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentStageApproveMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentStageMapper;
import com.app.employeePortal.recruitment.mapper.UpworkMapper;
import com.app.employeePortal.recruitment.mapper.WebSiteRecruitmentOpportunityMapper;
import com.app.employeePortal.recruitment.mapper.WebsiteMapper;
import com.app.employeePortal.recruitment.mapper.WebsitePartnerLinkMapper;
import com.app.employeePortal.recruitment.repository.ApprovalTaskLinkRepository;
import com.app.employeePortal.recruitment.repository.CommissionRepository;
import com.app.employeePortal.recruitment.repository.OpportunityRecruiterLinkRepository;
import com.app.employeePortal.recruitment.repository.ProcessDocumentLinkRepository;
import com.app.employeePortal.recruitment.repository.ProcessStageTaskLinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitStageNotelinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentAddressLinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentAvgFeedbackRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentCandidateLinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentCloseRuleRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentCustomerRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentInfoRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentOpportunityDetailsRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentPartnerLinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProcessDetailsDeleteRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProcessDetailsRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProcessInfoRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProcessStageDetailsDeleteRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProcessStageLinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProfileDetailsRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProfileInfoRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProfileStatusLinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentPublishRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentRecruiterLinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentSkillsetLinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentStageApproveRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentStageDetailsRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentStageInfoRepository;
import com.app.employeePortal.recruitment.repository.TaskLevelLinkRepository;
import com.app.employeePortal.recruitment.repository.UpworkRepository;
import com.app.employeePortal.recruitment.repository.WebsitePartnerLinkRepository;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;
import com.app.employeePortal.registration.entity.Country;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.entity.Designation;
import com.app.employeePortal.registration.entity.FunctionDetails;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.registration.repository.DesignationRepository;
import com.app.employeePortal.registration.repository.FunctionRepository;
import com.app.employeePortal.task.entity.EmployeeTaskLink;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.entity.TaskInfo;
import com.app.employeePortal.task.entity.TaskType;
import com.app.employeePortal.task.mapper.TaskViewMapper;
import com.app.employeePortal.task.repository.EmployeeTaskRepository;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskInfoRepository;
import com.app.employeePortal.task.repository.TaskTypeRepository;
import com.app.employeePortal.util.Utility;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Service
@Transactional
public class RecruitmentServiceImpl implements RecruitmentService {

	@Autowired
	RecruitmentInfoRepository recruitmentInfoRepository;
	@Autowired
	RecruitmentOpportunityDetailsRepository recruitmentOpportunityDetailsRepository;
	@Autowired
	RecruitmentProcessInfoRepository processInfoRepository;
	@Autowired
	RecruitmentProcessDetailsRepository processDetailsRepository;

	@Autowired
	RecruitmentStageInfoRepository recruitmentStageInfoRepository;
	@Autowired
	RecruitmentStageDetailsRepository recruitmentStageDetailsRepository;
	@Autowired
	RecruitmentProcessStageLinkRepository recruitmentProcessStageLinkRepository;
	@Autowired
	RecruitmentProfileInfoRepository recruitmentProfileInfoRepository;
	@Autowired
	RecruitmentProfileDetailsRepository recruitProfileDetailsRepository;
	@Autowired
	CandidateService candidateService;
	@Autowired
	RecruitmentSkillsetLinkRepository recruitmentSkillsetLinkRepository;
	@Autowired
	RecruitmentCandidateLinkRepository candidateLinkRepository;
	@Autowired
	CandidateDetailsRepository candidateDetailsRepository;
	@Autowired
	RecruitStageNotelinkRepository recruitStageNotelinkRepository;

	@Autowired
	RecruitmentCandidateLinkRepository recruitmentCandidateLinkRepository;
	@Autowired
	RecruitmentStageApproveRepository recruitmentStageApproveRepository;
	@Autowired
	DesignationRepository designationRepository;
	@Autowired
	FunctionRepository functionRepository;
	@Autowired
	SkillSetRepository skillSetRepository;
	@Autowired
	TaskInfoRepository taskInfoRepository;
	@Autowired
	TaskDetailsRepository taskDetailsRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EmployeeService empService;
	@Autowired
	EmployeeTaskRepository employeeTaskRepository;
	@Autowired
	OpportunityDetailsRepository opportunityDetailsRepository;
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	DefinationRepository definationRepository;

	@Autowired
	RecruitmentPublishRepository recruitmentPublishRepository;
	@Autowired
	JobPublishRepository jobPublishRepository;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	TaskDetailsRepository taskRepository;
	@Autowired
	ProcessStageTaskLinkRepository processStageTaskLinkRepository;
	@Autowired
	FunctionRepository functionDetailsRepository;
	@Autowired
	TaskLevelLinkRepository taskLevelLinkRepository;
	@Autowired
	ApprovalTaskLinkRepository approvalTaskLinkRepository;
	@Autowired
	RecruitmentProfileDetailsRepository recruitmentProfileDetailsRepository;
	@Autowired
	TaskTypeRepository taskTypeRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	RecruitmentProfileStatusLinkRepository recruitmentProfileStatusLinkRepository;
	@Autowired
	RecruitmentRecruiterLinkRepository recruitmentRecruiterLinkRepository;
	@Autowired
	RecruitmentPartnerLinkRepository recruitmentPartnerLinkRepository;
	@Autowired
	PartnerDetailsRepository partnerDetailsRepository;
	@Autowired
	CommissionRepository commissionRepository;
	@Autowired
	WebsiteRepository websiteRepository;
	@Autowired
	UpworkRepository upworkRepository;
	@Autowired
	RecruitmentService recruitmentService;
	@Autowired
	OpportunitySalesUserRepository opportunitySalesUserRepository;
	@Autowired
	RecruitmentCustomerRepository recruitmentCustomerRepository;

	@Autowired
	OpportunityRecruiterLinkRepository opportunityRecruiterLinkRepository;
	@Autowired
	WebsitePartnerLinkRepository websitePartnerLinkRepository;
	@Autowired
	AddressInfoRepository addressInfoRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	RecruitmentAddressLinkRepository recruitmentAddressLinkRepository;
	@Autowired
	CustomerRecruitUpdateRepository customerRecruitUpdateRepository;

	final Configuration configuration;
	@Autowired
	RoleTypeRepository roleTypeRepository;
	@Autowired
	OrganizationRepository organizationRepository;
	@Autowired
	RecruitmentAvgFeedbackRepository recruitmentAvgFeedbackRepository;
	@Autowired
	RecruitmentCloseRuleRepository recruitmentCloseRuleRepository;
	@Autowired
	ProcessDocumentLinkRepository processDocumentLinkRepository;
	@Autowired
	DocumentTypeRepository documentTypeRepository;
	@Autowired
	RecruitmentProcessStageDetailsDeleteRepository recruitmentProcessStageDetailsDeleteRepository;
	@Autowired
	RecruitmentProcessDetailsDeleteRepository recruitmentProcessDeleteRepository;

	@Autowired
	CandidateDocumentLinkRepository candidateDocumentLinkRepository;
	@Autowired
	DocumentDetailsRepository documentDetailsRepository;
	@Autowired
	ActionHistoryRepository actionHistoryRepository;
	@Autowired
	CandidateAddressLinkRepository candidateAddressLinkRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	CustomerAddressLinkRepository customerAddressLinkRepository;

	private String[] summary_headings = { "Requirements", "Positions", "Sponsor", "Filled", "Unfilled", "Submitted",
			"Rejected" };

	public RecruitmentServiceImpl(Configuration configuration) {
		this.configuration = configuration;
	}

	private String getProcessName(String ProcessId) {
		String name = "";
		GlobalProcessDetails globalProcessDetails = processDetailsRepository.getProcessDetailsByProcessId(ProcessId);
		if (null != globalProcessDetails) {
			name = globalProcessDetails.getProcess_name();
		}
		return name;
	}

	private String getStageName(String stageId) {

		RecruitmentProcessStageDetails recruitmentProcessStageDetails = recruitmentStageDetailsRepository
				.getRecruitmentStageDetailsByStageId(stageId);

		String stageName = recruitmentProcessStageDetails.getStage_name();
		return stageName;
	}

	private String cadndidateDetails(String candidateId) {

		CandidateDetails candidateDetails = candidateDetailsRepository.getcandidateDetailsById(candidateId);
		String middleName = "";
		String lastName = "";
		if (!StringUtils.isEmpty(candidateDetails.getMiddleName())) {

			middleName = candidateDetails.getMiddleName();
		}
		if (!StringUtils.isEmpty(candidateDetails.getLastName())) {
			lastName = candidateDetails.getLastName();
		}
		String name = candidateDetails.getFirstName() + " " + middleName + " " + lastName;
		return name;
	}

	private String cadndidateBillingDetails(String candidateId) {

		CandidateDetails candidateDetails = candidateDetailsRepository.getcandidateDetailsById(candidateId);
		String cadndidateBilling = "";
		if (null != candidateDetails.getBilling()) {

			cadndidateBilling = candidateDetails.getBilling();
		}
		return cadndidateBilling;
	}

	public String candidateNo(int candiNo) {
		String candidateNo = "";
		if (candiNo >= 3) {
			candidateNo = Integer.toString(candiNo - 2) + " More...";
		}
		return candidateNo;
	}

	@Override
	public RecruitmentOpportunityMapper linkRecruitmentToOpportunity(
			RecruitmentOpportunityMapper recruitmentOpportunityMapper) {

		String recruitId = null;

		OpportunityRecuitInfo recruitmentInfo = new OpportunityRecuitInfo();

		recruitmentInfo.setCreation_date(new Date());
		recruitId = recruitmentInfoRepository.save(recruitmentInfo).getRecruitment_id();

		OpportunityRecruitDetails recruitmentOpportunityDetails = new OpportunityRecruitDetails();

		recruitmentOpportunityDetails.setRecruitment_id(recruitId);
		recruitmentOpportunityDetails.setOpportunity_id(recruitmentOpportunityMapper.getOpportunityId());
		recruitmentOpportunityDetails.setName(recruitmentOpportunityMapper.getRequirementName());
		recruitmentOpportunityDetails.setCurrency(recruitmentOpportunityMapper.getCurrency());
		recruitmentOpportunityDetails.setBilling(recruitmentOpportunityMapper.getBilling());
		recruitmentOpportunityDetails.setSponser_id(recruitmentOpportunityMapper.getSponserId());
		recruitmentOpportunityDetails.setDescription(recruitmentOpportunityMapper.getDescription());
		recruitmentOpportunityDetails.setType(recruitmentOpportunityMapper.getType());
		recruitmentOpportunityDetails.setRecruitment_process_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
		recruitmentOpportunityDetails.setNumber(recruitmentOpportunityMapper.getNumber());
		recruitmentOpportunityDetails.setJob_order(recruitmentOpportunityMapper.getJobOrder());
		recruitmentOpportunityDetails.setUserId(recruitmentOpportunityMapper.getTagUserId());
		recruitmentOpportunityDetails.setOrgId(recruitmentOpportunityMapper.getOrgId());
		recruitmentOpportunityDetails.setExperience(recruitmentOpportunityMapper.getExperience());
		recruitmentOpportunityDetails.setLocation(recruitmentOpportunityMapper.getLocation());
		recruitmentOpportunityDetails.setCategory(recruitmentOpportunityMapper.getCategory());
		recruitmentOpportunityDetails.setCountry(recruitmentOpportunityMapper.getCountry());
		recruitmentOpportunityDetails.setDepartment(recruitmentOpportunityMapper.getDepartment());
		recruitmentOpportunityDetails.setRole(recruitmentOpportunityMapper.getRole());
		recruitmentOpportunityDetails.setWorkPreferance(recruitmentOpportunityMapper.getWorkPreference());
		recruitmentOpportunityDetails.setWorkType(recruitmentOpportunityMapper.getWorkType());
		recruitmentOpportunityDetails.setWorkDays(recruitmentOpportunityMapper.getWorkDays());
		try {
			recruitmentOpportunityDetails
					.setAvailable_date(Utility.getDateFromISOString(recruitmentOpportunityMapper.getAvilableDate()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			recruitmentOpportunityDetails
					.setEnd_date(Utility.getDateFromISOString(recruitmentOpportunityMapper.getEndDate()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			recruitmentOpportunityDetails
					.setCloseByDate(Utility.getDateFromISOString(recruitmentOpportunityMapper.getCloseByDate()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		recruitmentOpportunityDetails.setCreationDate(new Date());
		recruitmentOpportunityDetails.setLiveInd(true);
		recruitmentOpportunityDetails.setOpenInd(true);

		/* insert into RecruitmentRecruiterLink */
		if (null != recruitmentOpportunityMapper.getRecruitersId()
				&& !recruitmentOpportunityMapper.getRecruitersId().isEmpty()) {
			for (String recruiterId : recruitmentOpportunityMapper.getRecruitersId()) {
				RecruitmentRecruiterLink recruitmentRecruiterLink = new RecruitmentRecruiterLink();
				recruitmentRecruiterLink.setRecruitmentId(recruitId);
				recruitmentRecruiterLink.setRecruiterId(recruiterId);
				recruitmentRecruiterLink.setCreationDate(new Date());
				recruitmentRecruiterLink.setLiveInd(true);

				recruitmentRecruiterLinkRepository.save(recruitmentRecruiterLink);
			}
		}
		/* insert into RecruitmentPartnerLink */
		if (null != recruitmentOpportunityMapper.getPartnerId()
				&& !recruitmentOpportunityMapper.getPartnerId().isEmpty()) {
			for (String partnerId : recruitmentOpportunityMapper.getPartnerId()) {
				RecruitmentPartnerLink recruitmentPartnerLink = new RecruitmentPartnerLink();
				recruitmentPartnerLink.setRecruitmentId(recruitId);
				recruitmentPartnerLink.setPartnerId(partnerId);
				recruitmentPartnerLink.setCreationDate(new Date());
				recruitmentPartnerLink.setLiveInd(true);
				recruitmentPartnerLinkRepository.save(recruitmentPartnerLink);
			}
		}

		if (recruitmentOpportunityMapper.getAddress().size() > 0) {
			for (AddressMapper addressMapper : recruitmentOpportunityMapper.getAddress()) {
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

					RecruitmentAddressLink recruitmentAddressLink = new RecruitmentAddressLink();
					recruitmentAddressLink.setRecruitmentId(recruitId);
					recruitmentAddressLink.setAddressId(addressId);
					recruitmentAddressLink.setCreationDate(new Date());

					recruitmentAddressLinkRepository.save(recruitmentAddressLink);
				}
			}
		}
		if (recruitmentOpportunityMapper.getOpportunityId() != null
				|| recruitmentOpportunityMapper.getOpportunityId() != "") {
			OpportunityDetails oppDetails = opportunityDetailsRepository
					.getopportunityDetailsById(recruitmentOpportunityMapper.getOpportunityId());
			CustomerRecruitUpdate dbCustomerRecruitUpdate = customerRecruitUpdateRepository
					.findByCustomerId(oppDetails.getCustomerId());
			System.out.println("customerId=" + oppDetails.getCustomerId());
			if (dbCustomerRecruitUpdate != null) {
				dbCustomerRecruitUpdate.setCustomerId(oppDetails.getCustomerId());
				dbCustomerRecruitUpdate.setUpdatedDate(new Date());
				dbCustomerRecruitUpdate.setRecruitmentId(recruitId);
				if (recruitmentOpportunityMapper.getSponserId() != null
						|| recruitmentOpportunityMapper.getSponserId() != "") {
					dbCustomerRecruitUpdate.setContactId(recruitmentOpportunityMapper.getSponserId());
					dbCustomerRecruitUpdate.setContactUpdatedOn(new Date());
				}
				customerRecruitUpdateRepository.save(dbCustomerRecruitUpdate);
			} else {
				CustomerRecruitUpdate cusRecruitUpdate = new CustomerRecruitUpdate();
				cusRecruitUpdate.setCustomerId(oppDetails.getCustomerId());
				cusRecruitUpdate.setUpdatedDate(new Date());
				cusRecruitUpdate.setRecruitmentId(recruitId);
				if (recruitmentOpportunityMapper.getSponserId() != null
						|| recruitmentOpportunityMapper.getSponserId() != "") {
					cusRecruitUpdate.setContactId(recruitmentOpportunityMapper.getSponserId());
					cusRecruitUpdate.setContactUpdatedOn(new Date());
				}
				customerRecruitUpdateRepository.save(cusRecruitUpdate);
			}
		}
		recruitmentOpportunityDetailsRepository.save(recruitmentOpportunityDetails);

		RecruitmentOpportunityMapper recruitmentOpportunityMapper1 = getRecriutmentListByOppIdandRecruitId(
				recruitmentOpportunityMapper.getOpportunityId(), recruitId, recruitmentOpportunityMapper.getOrgId());

		return recruitmentOpportunityMapper1;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getRecriutmentListByOppId(String opportunityId, String orgId,
			String userId) throws Exception {

		List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
				.getOpenRecriutmentsByOpportunityIdAndCloseInd(opportunityId);
		System.out.println("recruitDetailsList==============" + recruitDetailsList.toString());
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {
			recruitDetailsList.stream().map(li -> {

				RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
				recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
				System.out.println("getRecruitment_id---------------" + li.getRecruitment_id());
				recruitmentOpportunityMapper.setOpportunityId(li.getOpportunity_id());
				System.out.println("li.getOpportunity_id()----/-------/----" + li.getOpportunity_id());
				/*
				 * recruitmentOpportunityMapper.setStageId(li.getStage_id());
				 * recruitmentOpportunityMapper.setRecruitmentProcessId(li.getProcess_id());
				 * recruitmentOpportunityMapper.setProcessName(getProcessName(li.getProcess_id()
				 * ));
				 * recruitmentOpportunityMapper.setStageName(getStageName(li.getStage_id()));
				 * recruitmentOpportunityMapper.setProfileId(li.getProfile_id());
				 */
				recruitmentOpportunityMapper.setLocation(li.getLocation());
				recruitmentOpportunityMapper.setRequirementName(li.getName());
				recruitmentOpportunityMapper.setSponserId(li.getSponser_id());
				recruitmentOpportunityMapper.setDescription(li.getDescription());
				recruitmentOpportunityMapper.setNumber(li.getNumber());
				recruitmentOpportunityMapper.setBilling(li.getBilling());
				recruitmentOpportunityMapper.setCurrency(li.getCurrency());
				recruitmentOpportunityMapper.setType(li.getType());
				recruitmentOpportunityMapper.setAvilableDate(Utility.getISOFromDate(li.getAvailable_date()));
				recruitmentOpportunityMapper.setEndDate(Utility.getISOFromDate(li.getEnd_date()));
				recruitmentOpportunityMapper.setCloseByDate(Utility.getISOFromDate(li.getCloseByDate()));
				recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
				recruitmentOpportunityMapper.setWorkType(li.getWorkType());
				recruitmentOpportunityMapper.setWorkDays(li.getWorkDays());
				recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcess(li.getRecruitment_process_id()));

				recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
				recruitmentOpportunityMapper.setExperience(li.getExperience());
				recruitmentOpportunityMapper.setLocation(li.getLocation());
				recruitmentOpportunityMapper.setCategory(li.getCategory());
				recruitmentOpportunityMapper.setCountry(li.getCountry());
				recruitmentOpportunityMapper.setDepartment(li.getDepartment());
				recruitmentOpportunityMapper.setRole(li.getRole());
				recruitmentOpportunityMapper.setRecruitmentProcessId(li.getRecruitment_process_id());
				recruitmentOpportunityMapper.setWorkPreference(li.getWorkPreferance());
				recruitmentOpportunityMapper.setCloseInd(li.isCloseInd());
				if (!StringUtils.isEmpty(li.getUserId())) {
					EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(li.getUserId());
					if (null != employeeDetails) {
						String middleName = " ";
						String lastName = " ";

						if (null != employeeDetails.getLastName()) {

							lastName = employeeDetails.getLastName();
						}
						if (null != employeeDetails.getMiddleName()) {

							middleName = employeeDetails.getMiddleName();
							recruitmentOpportunityMapper
									.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
						} else {
							recruitmentOpportunityMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
						}
					}
				}
				List<RecruitProfileLinkDetails> onbordingList = recruitmentProfileDetailsRepository
						.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());
				if (null != onbordingList) {
					recruitmentOpportunityMapper.setOnBoardNo(onbordingList.size());
				}

				RecruitmentPublishDetails recruitmentPublishDetails = recruitmentPublishRepository
						.getRecruitmentPublishDetails(li.getRecruitment_id());
				if (null != recruitmentPublishDetails) {

					recruitmentOpportunityMapper.setPublishInd(true);
				} else {

					recruitmentOpportunityMapper.setPublishInd(false);
				}

				if (!StringUtils.isEmpty(li.getRecruiter_id())) {
					EmployeeDetails employeeDetails = employeeRepository
							.getEmployeeDetailsByEmployeeId(li.getRecruiter_id());
					String middleName = " ";
					String lastName = " ";
					if (employeeDetails.getMiddleName() != null) {

						middleName = employeeDetails.getMiddleName();
					}
					if (employeeDetails.getLastName() != null) {

						lastName = employeeDetails.getLastName();
					}

					recruitmentOpportunityMapper
							.setRecruiterName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				}

				if (!StringUtils.isEmpty(li.getSponser_id())) {

					ContactDetails contactDetails = contactRepository.getContactDetailsById(li.getSponser_id());
					String middleName = " ";
					String lastName = " ";
					if (null != contactDetails.getMiddle_name()) {

						middleName = contactDetails.getMiddle_name();
					}
					if (null != contactDetails.getLast_name()) {

						lastName = contactDetails.getLast_name();
					}
					recruitmentOpportunityMapper
							.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
				}

				if (!StringUtils.isEmpty(li.getOpportunity_id())) {
					OpportunityDetails opportunityDetails = opportunityDetailsRepository
							.findByOppId(li.getOpportunity_id());
					if (null != opportunityDetails) {
						if (!StringUtils.isEmpty(opportunityDetails.getCustomerId())) {
							recruitmentOpportunityMapper.setCustomerId(opportunityDetails.getCustomerId());
							Customer customer = customerRepository
									.getCustomerByIdAndLiveInd(opportunityDetails.getCustomerId());
							if (null != customer) {
								if (!StringUtils.isEmpty(customer.getName())) {
									recruitmentOpportunityMapper.setCustomerName(customer.getName());
								}
							}
						}
					}
				}

				/* Add skill set */
				recruitmentOpportunityMapper.setSkillSetList(candidateService.getSkillSetOfSkillLibery(orgId));
				List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
						.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
				if (recruitmentSkillsetLink.size() != 0) {
					recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

				}

				List<RecruitmentCandidateLink> recruitmentCandidateLink = candidateLinkRepository
						.getCandidateList(li.getRecruitment_id());
				System.out.println("recruitmentCandidateLink::::::" + recruitmentCandidateLink.toString());
				if (null != recruitmentCandidateLink && !recruitmentCandidateLink.isEmpty()) {
					System.out.println("size:::::::::" + recruitmentCandidateLink.size());

					List<CandidateMapper> resultList = new ArrayList<>();
					int count = 0;
					for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
						CandidateMapper mapper = new CandidateMapper();
						CandidateDetails candidate = candidateDetailsRepository
								.getcandidateDetailsById(candidateLink.getCandidate_id());
						if (null != candidate) {
							String middleName = " ";
							String lastName = " ";
							if (null != candidate.getMiddleName()) {

								middleName = candidate.getMiddleName();
							}
							if (null != candidate.getLastName()) {

								lastName = candidate.getLastName();
							}
							mapper.setCandidateId(candidate.getCandidateId());
							mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
							mapper.setImageId(candidate.getImageId());
						}
						resultList.add(mapper);
						count++;
						if (count == 2) {
							break;
						}
					}
					recruitmentOpportunityMapper.setCandidatetList(resultList);

					List<CandidateMapper> resultList2 = new ArrayList<>();
					for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
						CandidateMapper mapper = new CandidateMapper();
						CandidateDetails candidate = candidateDetailsRepository
								.getcandidateDetailsById(candidateLink.getCandidate_id());
						if (null != candidate) {
							String middleName = " ";
							String lastName = " ";
							if (null != candidate.getMiddleName()) {

								middleName = candidate.getMiddleName();
							}
							if (null != candidate.getLastName()) {

								lastName = candidate.getLastName();
							}
							mapper.setCandidateId(candidate.getCandidateId());
							mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
							mapper.setImageId(candidate.getImageId());
						}
						resultList2.add(mapper);
					}
					recruitmentOpportunityMapper.setFiltercandidatetList(resultList2);
				}
				recruitmentOpportunityMapper.setCandidateNo(candidateNo(recruitmentCandidateLink.size()));
				/*
				 * RecruitProfileStatusLink recruitProfileStatusLink =
				 * recruitmentProfileStatusLinkRepository
				 * .getRecruitProfileStatusLinkByProfileId(li.getProfile_id()); if (null !=
				 * recruitProfileStatusLink) {
				 * recruitmentOpportunityMapper.setApproveInd(recruitProfileStatusLink.
				 * isApproveInd());
				 * recruitmentOpportunityMapper.setRejectInd(recruitProfileStatusLink.
				 * isRejectInd()); }
				 */

				/*
				 * List<RecruitmentRecruiterLink> recruiterList =
				 * recruitmentRecruiterLinkRepository
				 * .getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id()); if (null
				 * != recruiterList && !recruiterList.isEmpty()) { List<String> resultList = new
				 * ArrayList<>(); for (RecruitmentRecruiterLink recruiter : recruiterList) {
				 * //EmployeeMapper mapper = new EmployeeMapper(); String recruiters = null;
				 * EmployeeDetails emp =
				 * employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
				 * System.out.println("name....." + emp.getFullName()); recruiters
				 * =emp.getFullName(); recruiters=emp.getId(); resultList.add(recruiters); }
				 * recruitmentOpportunityMapper.setRecruiterNames(resultList); }
				 */

				List<RecruitmentRecruiterLink> recruiterList = recruitmentRecruiterLinkRepository
						.getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id());
				if (null != recruiterList && !recruiterList.isEmpty()) {
					List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
					for (RecruitmentRecruiterLink recruiter : recruiterList) {
						EmployeeMapper mapper = new EmployeeMapper();
						EmployeeDetails emp = employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
						// System.out.println("name....." + emp.getFullName());
						mapper.setFullName(emp.getFullName());
						mapper.setEmployeeId(recruiter.getRecruiterId());

						resultList.add(mapper);
					}
					recruitmentOpportunityMapper.setRecruiterList(resultList);
				}

				List<RecruitmentAddressLink> recruitmentAddressLink = recruitmentAddressLinkRepository
						.getAddressListByRecruitmentId(li.getRecruitment_id());
				List<AddressMapper> addressList = new ArrayList<AddressMapper>();
				if (null != recruitmentAddressLink && !recruitmentAddressLink.isEmpty()) {

					for (RecruitmentAddressLink recruitmentAddressLink1 : recruitmentAddressLink) {
						AddressDetails addressDetails = addressRepository
								.getAddressDetailsByAddressId(recruitmentAddressLink1.getAddressId());

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
				recruitmentOpportunityMapper.setAddress(addressList);

				List<RecruitmentPartnerLink> recruitmentPartnerLinkList = recruitmentPartnerLinkRepository
						.findByRecruitmentIdAndLiveInd(li.getRecruitment_id(), true);
				if (null != recruitmentPartnerLinkList && !recruitmentPartnerLinkList.isEmpty()) {
					List<PartnerMapper> partnerResultList = new ArrayList<PartnerMapper>();
					for (RecruitmentPartnerLink partner : recruitmentPartnerLinkList) {
						PartnerMapper mapper = new PartnerMapper();
						System.out.println("partner::::::::::" + partner.getPartnerId());
						System.out.println("recID:::::::::::" + partner.getRecruitmentId());
						PartnerDetails dbPartner = partnerDetailsRepository
								.getPartnerDetailsById(partner.getPartnerId());
						if (null != dbPartner) {
							mapper.setPartnerName(dbPartner.getPartnerName());
							mapper.setPartnerId(dbPartner.getPartnerId());

							partnerResultList.add(mapper);
						}
					}
					recruitmentOpportunityMapper.setPartnerList(partnerResultList);
				}
				return mapperList.add(recruitmentOpportunityMapper);

			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public String saveRecruitmentProcess(RecruitmentProcessMapper recruitmentProcessMapper) {

		ArrayList<String> names = new ArrayList<String>(Arrays.asList("Selected", "Drop"));

		GlobalProcessInfo recruitmentProcessInfo = new GlobalProcessInfo();

		recruitmentProcessInfo.setCreation_date(new Date());
		String processId = processInfoRepository.save(recruitmentProcessInfo).getGlobal_process_info_id();

		GlobalProcessDetails recruitmentProcessDetails = new GlobalProcessDetails();
		recruitmentProcessDetails.setProcess_id(processId);
		recruitmentProcessDetails.setProcess_name(recruitmentProcessMapper.getRecruitmentProcessName());
		recruitmentProcessDetails.setOrgId(recruitmentProcessMapper.getOrganizationId());
		recruitmentProcessDetails.setUserId(recruitmentProcessMapper.getUserId());
		recruitmentProcessDetails.setCreation_date(new Date());
		recruitmentProcessDetails.setLive_ind(true);
		recruitmentProcessDetails.setPublishInd(false);
		processDetailsRepository.save(recruitmentProcessDetails);

		GlobalProcessDetailsDelete globalProcessDetailsDelete = new GlobalProcessDetailsDelete();
		globalProcessDetailsDelete.setProcessId(processId);
		globalProcessDetailsDelete.setOrgId(recruitmentProcessMapper.getOrganizationId());
		globalProcessDetailsDelete.setUserId(recruitmentProcessMapper.getUserId());
		globalProcessDetailsDelete.setUpdationDate(new Date());
		recruitmentProcessDeleteRepository.save(globalProcessDetailsDelete);

		for (int i = 0; i < names.size(); i++) {

			RecruitmentProcessStageInfo stageInfo = new RecruitmentProcessStageInfo();

			stageInfo.setCreationDate(new Date());
			String stageId = recruitmentStageInfoRepository.save(stageInfo).getRecruitment_process_stage_id();

			RecruitmentProcessStageDetails details = new RecruitmentProcessStageDetails();

			details.setRecruitmentStageId(stageId);
			details.setCreation_date(new Date());
			details.setStage_name(names.get(i));
			details.setOrgId(recruitmentProcessMapper.getOrganizationId());
			details.setUserId(recruitmentProcessMapper.getUserId());
			details.setCreation_date(new Date());
			details.setLiveInd(true);
			details.setPublishInd(false);

			if (names.get(i) == "Selected") {

				details.setProbability(100);
			} else {

				details.setProbability(0);
			}

			details.setLiveInd(true);
			recruitmentStageDetailsRepository.save(details);

			/* insert to stageUserOrgLink */

			/* insert to processStageLink */

			GlobalProcessStageLink recruitmentProcessStageLink = new GlobalProcessStageLink();
			recruitmentProcessStageLink.setRecruitmentProcessId(processId);
			recruitmentProcessStageLink.setRecruitmentStageId(stageId);
			recruitmentProcessStageLink.setCreation_date(new Date());
			recruitmentProcessStageLink.setLiveInd(true);
			recruitmentProcessStageLinkRepository.save(recruitmentProcessStageLink);
		}
		return processId;
	}

	@Override
	public List<RecruitmentProcessMapper> getProcessesOfAdmin(String orgId) {

		List<GlobalProcessDetails> processList = processDetailsRepository.getRecriutmentsByOrgId(orgId);
		List<RecruitmentProcessMapper> mapperList = new ArrayList<>();
		if (null != processList && !processList.isEmpty()) {

			processList.stream().map(li -> {

				RecruitmentProcessMapper processMapper = new RecruitmentProcessMapper();

				processMapper.setRecruitmentProcessName(li.getProcess_name());
				processMapper.setRecruitmentProcessId(li.getProcess_id());
				processMapper.setPublishInd(li.isPublishInd());
				mapperList.add(processMapper);

				return mapperList;
			}).collect(Collectors.toList());
		}

		List<GlobalProcessDetailsDelete> recruitmentStageDelete = recruitmentProcessDeleteRepository
				.getRecriutmentsByOrgId(orgId);
		if (null != recruitmentStageDelete && !recruitmentStageDelete.isEmpty()) {
			Collections.sort(recruitmentStageDelete, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			mapperList.get(0).setUpdationDate(Utility.getISOFromDate(recruitmentStageDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeByUserId(recruitmentStageDelete.get(0).getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					mapperList.get(0).setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					mapperList.get(0).setName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		return mapperList;
	}

	@Override
	public String createStagesForProcess(RecruitmentStageMapper recruitmentStageMapper) {
		String stageId = null;
		if (null != recruitmentStageMapper.getRecruitmentProcessId()) {
			RecruitmentProcessStageInfo recruitmentStageInfo = new RecruitmentProcessStageInfo();
			recruitmentStageInfo.setCreationDate(new Date());
			stageId = recruitmentStageInfoRepository.save(recruitmentStageInfo).getRecruitment_process_stage_id();

			if (null != stageId && !stageId.isEmpty()) {

				RecruitmentProcessStageDetails details = new RecruitmentProcessStageDetails();

				details.setRecruitmentStageId(stageId);
				details.setCreation_date(new Date());
				details.setStage_name(recruitmentStageMapper.getStageName());
				details.setProbability(recruitmentStageMapper.getProbability());
				details.setDays(recruitmentStageMapper.getDays());
				details.setOrgId(recruitmentStageMapper.getOrganizationId());
				details.setCreation_date(new Date());
				details.setLiveInd(true);
				details.setUserId(recruitmentStageMapper.getUserId());

				details.setPublishInd(true);
				details.setResponsible(recruitmentStageMapper.getResponsible());

				details.setPublishInd(false);

				recruitmentStageDetailsRepository.save(details);

				GlobalProcessStageLink recruitmentProcessStageLink = new GlobalProcessStageLink();
				recruitmentProcessStageLink.setRecruitmentProcessId(recruitmentStageMapper.getRecruitmentProcessId());
				recruitmentProcessStageLink.setRecruitmentStageId(stageId);
				recruitmentProcessStageLink.setCreation_date(new Date());
				recruitmentProcessStageLink.setLiveInd(true);
				recruitmentProcessStageLinkRepository.save(recruitmentProcessStageLink);

				RecruitmentProcessStageDetailsDelete recruitmentStageDelete = new RecruitmentProcessStageDetailsDelete();
				recruitmentStageDelete.setRecruitmentStageId(stageId);
				recruitmentStageDelete.setOrgId(recruitmentStageMapper.getOrganizationId());
				recruitmentStageDelete.setUserId(recruitmentStageMapper.getUserId());
				recruitmentStageDelete.setUpdationDate(new Date());
				recruitmentProcessStageDetailsDeleteRepository.save(recruitmentStageDelete);
			}
		}
		return stageId;
	}

	@Override
	public List<RecruitmentStageMapper> getStagesOfProcess(String processId) {

		List<GlobalProcessStageLink> stageList = recruitmentProcessStageLinkRepository
				.getRecruitmentProcessStageLinkByProcessId(processId);
		List<RecruitmentStageMapper> mapperList = new ArrayList<>();
		if (null != stageList && !stageList.isEmpty()) {

			stageList.stream().map(li -> {

				RecruitmentProcessStageDetails recruitmentProcessStageDetails = recruitmentStageDetailsRepository
						.getRecruitmentStageDetailsByStageId(li.getRecruitmentStageId());
				if (null != recruitmentProcessStageDetails) {

					RecruitmentStageMapper recruitmentStageMapper = new RecruitmentStageMapper();
					recruitmentStageMapper.setStageId(recruitmentProcessStageDetails.getRecruitmentStageId());
					recruitmentStageMapper.setStageName(recruitmentProcessStageDetails.getStage_name());
					recruitmentStageMapper.setProbability(recruitmentProcessStageDetails.getProbability());
					recruitmentStageMapper.setDays(recruitmentProcessStageDetails.getDays());
					recruitmentStageMapper.setPublishInd(recruitmentProcessStageDetails.isPublishInd());
					recruitmentStageMapper.setResponsible(recruitmentProcessStageDetails.getResponsible());
					mapperList.add(recruitmentStageMapper);

					if (null != mapperList && !mapperList.isEmpty()) {

						Collections.sort(mapperList, (RecruitmentStageMapper c1, RecruitmentStageMapper c2) -> Double
								.compare(c1.getProbability(), c2.getProbability()));
					}
				}
				return mapperList;

			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentStageMapper> getAllProcessStagesOfAdmin(String orgId) {

		List<GlobalProcessDetails> processList = processDetailsRepository.getpublishedProcessesOfAdmin(orgId);

		List<RecruitmentStageMapper> mapperList = new ArrayList<>();

		if (null != processList && !processList.isEmpty()) {

			return processList.stream().map(recruitmentProcessDetails -> {
				List<GlobalProcessStageLink> stageList = new ArrayList<GlobalProcessStageLink>();
				stageList = recruitmentProcessStageLinkRepository
						.getRecruitmentProcessStageLinkByProcessId(recruitmentProcessDetails.getProcess_id());

				return stageList.stream().map(recruitmentProcessStageLink -> {
					RecruitmentProcessStageDetails recruitmentProcessStageDetails = recruitmentStageDetailsRepository
							.getPublishedStageDetailsOfProcess(recruitmentProcessStageLink.getRecruitmentStageId());

					if (null != recruitmentProcessStageDetails) {
						RecruitmentStageMapper recruitmentStageMapper = new RecruitmentStageMapper();
						recruitmentStageMapper.setStageId(recruitmentProcessStageDetails.getRecruitmentStageId());
						recruitmentStageMapper.setStageName(recruitmentProcessStageDetails.getStage_name());
						recruitmentStageMapper.setProbability(recruitmentProcessStageDetails.getProbability());
						recruitmentStageMapper.setRecruitmentProcessId(recruitmentProcessDetails.getProcess_id());

						return recruitmentStageMapper;
					}
					return null;
				}).filter(Objects::nonNull).collect(Collectors.toList());

			}).flatMap(l -> l.stream()).collect(Collectors.toList());
		}

		List<RecruitmentProcessStageDetailsDelete> recruitmentStageDelete = recruitmentProcessStageDetailsDeleteRepository
				.findByOrgId(orgId);
		if (null != recruitmentStageDelete && !recruitmentStageDelete.isEmpty()) {
			Collections.sort(recruitmentStageDelete, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			mapperList.get(0).setUpdationDate(Utility.getISOFromDate(recruitmentStageDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeByUserId(recruitmentStageDelete.get(0).getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					mapperList.get(0).setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					mapperList.get(0).setName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		return mapperList;
	}

	@Override
	public String linkProfileToRecruitment(RecruitmentOpportunityMapper recruitmentOpportunityMapper) {

		RecruitmentProfileInfo recruitmentProfileInfo = new RecruitmentProfileInfo();
		recruitmentProfileInfo.setCreation_date(new Date());
		String profileId = recruitmentProfileInfoRepository.save(recruitmentProfileInfo)
				.getRecruitment_profile_info_id();

		List<RecruitProfileLinkDetails> dBrecruitProfileLinkDetails = recruitProfileDetailsRepository
				.getProfileDetailByRecruitmentId(recruitmentOpportunityMapper.getRecruitmentId());
		if (null != dBrecruitProfileLinkDetails) {
			RecruitProfileLinkDetails recruitProfileLinkDetails = new RecruitProfileLinkDetails();

			List<RecruitmentStageMapper> list = getStagesOfProcess(
					recruitmentOpportunityMapper.getRecruitmentProcessId());
			String stageIdd = list.get(1).getStageId();
			recruitProfileLinkDetails.setStage_id(stageIdd);
			recruitProfileLinkDetails.setProfile_id(profileId);
			recruitProfileLinkDetails.setOpp_id(recruitmentOpportunityMapper.getOpportunityId());
			recruitProfileLinkDetails.setRecruitment_id(recruitmentOpportunityMapper.getRecruitmentId());
			recruitProfileLinkDetails.setProcess_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
			recruitProfileLinkDetails.setCreation_date(new Date());
			recruitProfileLinkDetails.setLive_ind(true);
			recruitProfileDetailsRepository.save(recruitProfileLinkDetails);
			return profileId;
		}
		RecruitProfileLinkDetails recruitProfileLinkDetails = new RecruitProfileLinkDetails();

		recruitProfileLinkDetails.setProfile_id(profileId);
		recruitProfileLinkDetails.setOpp_id(recruitmentOpportunityMapper.getOpportunityId());
		recruitProfileLinkDetails.setRecruitment_id(recruitmentOpportunityMapper.getRecruitmentId());
		recruitProfileLinkDetails.setStage_id(recruitmentOpportunityMapper.getStageId());
		recruitProfileLinkDetails.setProcess_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
		recruitProfileLinkDetails.setCreation_date(new Date());
		recruitProfileLinkDetails.setLive_ind(true);

		recruitProfileDetailsRepository.save(recruitProfileLinkDetails);

		return profileId;
	}

	@Override
	public String updateProcessOfRecruiter(RecruitmentProcessMapper recruitmentProcessMapper) {

		GlobalProcessDetails globalProcessDetails = processDetailsRepository
				.getProcessDetailsByProcessId(recruitmentProcessMapper.getRecruitmentProcessId());
		globalProcessDetails.setLive_ind(false);

		GlobalProcessDetails newGlobalProcessDetails = new GlobalProcessDetails();
		if (!StringUtils.isEmpty(recruitmentProcessMapper.getRecruitmentProcessName())) {
			newGlobalProcessDetails.setProcess_name(recruitmentProcessMapper.getRecruitmentProcessName());
		} else {
			newGlobalProcessDetails.setProcess_name(globalProcessDetails.getProcess_name());
		}

		newGlobalProcessDetails.setProcess_id(recruitmentProcessMapper.getRecruitmentProcessId());
		newGlobalProcessDetails.setOrgId(recruitmentProcessMapper.getOrganizationId());
		newGlobalProcessDetails.setUserId(recruitmentProcessMapper.getUserId());
		newGlobalProcessDetails.setCreation_date(globalProcessDetails.getCreation_date());
		newGlobalProcessDetails.setPublishInd(globalProcessDetails.isPublishInd());
		newGlobalProcessDetails.setLive_ind(true);
		processDetailsRepository.save(newGlobalProcessDetails);

		GlobalProcessDetailsDelete globalProcessDetailsDelete = recruitmentProcessDeleteRepository
				.getProcessDetailsByProcessId(recruitmentProcessMapper.getRecruitmentProcessId());
		if (null != globalProcessDetailsDelete) {
			globalProcessDetailsDelete.setUserId(recruitmentProcessMapper.getUserId());
			globalProcessDetailsDelete.setOrgId(recruitmentProcessMapper.getOrganizationId());
			globalProcessDetailsDelete.setUpdationDate(new Date());
			recruitmentProcessDeleteRepository.save(globalProcessDetailsDelete);
		} else {
			GlobalProcessDetailsDelete globalProcessDetailsDelete1 = new GlobalProcessDetailsDelete();
			globalProcessDetailsDelete1.setUserId(recruitmentProcessMapper.getUserId());
			globalProcessDetailsDelete1.setOrgId(recruitmentProcessMapper.getOrganizationId());
			globalProcessDetailsDelete1.setUpdationDate(new Date());
			recruitmentProcessDeleteRepository.save(globalProcessDetailsDelete1);
		}
		return recruitmentProcessMapper.getRecruitmentProcessId();
	}

	@Override
	public RecruitmentProcessMapper getProcessMapperByProcessId(String processId) {
		GlobalProcessDetails globalProcessDetails = processDetailsRepository.getProcessDetailsByProcessId(processId);
		RecruitmentProcessMapper recruitmentProcessMapper = new RecruitmentProcessMapper();

		if (null != globalProcessDetails) {

			recruitmentProcessMapper.setRecruitmentProcessName(globalProcessDetails.getProcess_name());
			recruitmentProcessMapper.setPublishInd(globalProcessDetails.isPublishInd());
			recruitmentProcessMapper.setRecruitmentProcessId(globalProcessDetails.getProcess_id());
		}
		return recruitmentProcessMapper;
	}

	@Override
	public String updateStageOfProcess(RecruitmentStageMapper recruitmentStageMapper) {

		RecruitmentProcessStageDetails stageDetails = recruitmentStageDetailsRepository
				.getRecruitmentStageDetailsByStageId(recruitmentStageMapper.getStageId());

		stageDetails.setLiveInd(false);
		RecruitmentProcessStageDetails stageDetailsNew = new RecruitmentProcessStageDetails();

		if (!StringUtils.isEmpty(recruitmentStageMapper.getStageName())) {

			stageDetailsNew.setStage_name(recruitmentStageMapper.getStageName());
		} else {
			stageDetailsNew.setStage_name(stageDetails.getStage_name());
		}
		if ((recruitmentStageMapper.getProbability()) > 0) {

			stageDetailsNew.setProbability(recruitmentStageMapper.getProbability());
		} else {
			stageDetailsNew.setProbability(stageDetails.getProbability());
		}
		if (recruitmentStageMapper.getDays() > 0) {

			stageDetailsNew.setDays(recruitmentStageMapper.getDays());
		} else {
			stageDetailsNew.setDays(stageDetails.getDays());
		}
		if (!StringUtils.isEmpty(recruitmentStageMapper.getResponsible())) {

			stageDetailsNew.setResponsible(recruitmentStageMapper.getResponsible());
		} else {
			stageDetailsNew.setResponsible(stageDetails.getResponsible());
		}
		stageDetailsNew.setRecruitmentStageId(recruitmentStageMapper.getStageId());
		stageDetailsNew.setOrgId(stageDetails.getOrgId());
		stageDetailsNew.setUserId(stageDetails.getUserId());
		stageDetailsNew.setCreation_date(stageDetails.getCreation_date());
		stageDetailsNew.setLiveInd(true);

		recruitmentStageDetailsRepository.save(stageDetailsNew);

		RecruitmentProcessStageDetailsDelete recruitmentStageDelete = recruitmentProcessStageDetailsDeleteRepository
				.getRecruitmentStageDetailsByStageId(recruitmentStageMapper.getStageId());
		recruitmentStageDelete.setUpdationDate(new Date());
		recruitmentProcessStageDetailsDeleteRepository.save(recruitmentStageDelete);

		return recruitmentStageMapper.getStageId();
	}

	@Override
	public RecruitmentStageMapper getStageDetailsByStageId(String id) {

		RecruitmentProcessStageDetails recruitmentProcessStageDetails = recruitmentStageDetailsRepository
				.getRecruitmentStageDetailsByStageId(id);
		RecruitmentStageMapper recruitmentStageMapper = new RecruitmentStageMapper();
		if (null != recruitmentProcessStageDetails) {

			recruitmentStageMapper.setStageId(recruitmentProcessStageDetails.getRecruitmentStageId());
			recruitmentStageMapper.setStageName(recruitmentProcessStageDetails.getStage_name());
			recruitmentStageMapper.setProbability(recruitmentProcessStageDetails.getProbability());
			recruitmentStageMapper.setDays(recruitmentProcessStageDetails.getDays());
			recruitmentStageMapper.setPublishInd(recruitmentProcessStageDetails.isPublishInd());
			recruitmentStageMapper.setResponsible(recruitmentProcessStageDetails.getResponsible());
		}

		return recruitmentStageMapper;
	}

	@Override
	public String linkSkillToRecruitment(RecruitmentOpportunityMapper recruitmentOpportunityMapper) {

		RecruitmentSkillsetLink recruitmentSkillsetLink = recruitmentSkillsetLinkRepository.getRecruitmentSkillSetLink(
				recruitmentOpportunityMapper.getOpportunityId(), recruitmentOpportunityMapper.getRecruitmentId());
		String id = null;
		if (null != recruitmentSkillsetLink) {

			recruitmentSkillsetLink.setLive_ind(false);

			RecruitmentSkillsetLink recruitmentSkillsetLink1 = new RecruitmentSkillsetLink();
			recruitmentSkillsetLink1.setProfile_id(recruitmentOpportunityMapper.getProfileId());
			recruitmentSkillsetLink1.setProcess_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
			recruitmentSkillsetLink1.setRecruitment_id(recruitmentOpportunityMapper.getRecruitmentId());
			recruitmentSkillsetLink1.setOpportunity_id(recruitmentOpportunityMapper.getOpportunityId());
			recruitmentSkillsetLink1.setSkillName(recruitmentOpportunityMapper.getSkillName());
			recruitmentSkillsetLink1.setCreation_date(recruitmentSkillsetLink.getCreation_date());
			recruitmentSkillsetLink1.setLive_ind(true);

			id = recruitmentSkillsetLinkRepository.save(recruitmentSkillsetLink1).getRecruitment_skillset_link_id();
		} else {

			RecruitmentSkillsetLink recruitmentSkillsetLink1 = new RecruitmentSkillsetLink();
			recruitmentSkillsetLink1.setProfile_id(recruitmentOpportunityMapper.getProfileId());
			recruitmentSkillsetLink1.setProcess_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
			recruitmentSkillsetLink1.setRecruitment_id(recruitmentOpportunityMapper.getRecruitmentId());
			recruitmentSkillsetLink1.setOpportunity_id(recruitmentOpportunityMapper.getOpportunityId());
			recruitmentSkillsetLink1.setSkillName(recruitmentOpportunityMapper.getSkillName());
			recruitmentSkillsetLink1.setCreation_date(new Date());
			recruitmentSkillsetLink1.setLive_ind(true);
			id = recruitmentSkillsetLinkRepository.save(recruitmentSkillsetLink1).getRecruitment_skillset_link_id();

		}

		return recruitmentOpportunityMapper.getOpportunityId();
	}

	@Override
	public RecruitmentOpportunityMapper getRecuitmentByProfile(String opportunityId, String profileId, String orgId,
			String userId) throws Exception {

		RecruitProfileLinkDetails recruitProfileLinkDetails = recruitProfileDetailsRepository
				.getProfilesByOppIdAndProfileId(opportunityId, profileId);

		RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
		recruitmentOpportunityMapper.setRecruitmentId(recruitProfileLinkDetails.getRecruitment_id());
		recruitmentOpportunityMapper.setProfileId(profileId);
		recruitmentOpportunityMapper.setRecruitmentProcessId(recruitProfileLinkDetails.getProcess_id());
		recruitmentOpportunityMapper.setOpportunityId(recruitProfileLinkDetails.getOpp_id());
		recruitmentOpportunityMapper.setStageId(recruitProfileLinkDetails.getStage_id());
		recruitmentOpportunityMapper.setProcessName(getProcessName(recruitProfileLinkDetails.getProcess_id()));
		recruitmentOpportunityMapper.setStageName(getStageName(recruitProfileLinkDetails.getStage_id()));

		OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
				.getRecriutmentsByOpportunitysId(opportunityId, recruitProfileLinkDetails.getRecruitment_id());
		recruitmentOpportunityMapper.setRequirementName(opportunityRecruitDetails.getName());
		recruitmentOpportunityMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
		recruitmentOpportunityMapper.setCategory(opportunityRecruitDetails.getCategory());
		recruitmentOpportunityMapper
				.setCreationDate(Utility.getISOFromDate(opportunityRecruitDetails.getCreationDate()));
		recruitmentOpportunityMapper.setSponserId(opportunityRecruitDetails.getSponser_id());
		recruitmentOpportunityMapper.setDescription(opportunityRecruitDetails.getDescription());
		recruitmentOpportunityMapper.setNumber(opportunityRecruitDetails.getNumber());
		recruitmentOpportunityMapper.setBilling(opportunityRecruitDetails.getBilling());

		OpportunityDetails oppDetails = opportunityDetailsRepository
				.getOpportunityDetailsByOpportunityId(opportunityRecruitDetails.getOpportunity_id());
		String customerName = "";
		if (!StringUtils.isEmpty(oppDetails.getCustomerId())) {
			customerName = customerRepository.getCustomerDetailsByCustomerId(oppDetails.getCustomerId()).getName();
		}
		recruitmentOpportunityMapper.setCustomerName(customerName);

		String role = "";
		if (!StringUtils.isEmpty(opportunityRecruitDetails.getRole())) {
			role = roleTypeRepository.findByRoleTypeId(opportunityRecruitDetails.getRole()).getRoleType();
		}
		recruitmentOpportunityMapper.setRole(role);

		String OrgName = organizationRepository.getOrganizationDetailsById(oppDetails.getOrgId()).getName();
		recruitmentOpportunityMapper.setOrgName(OrgName);

		RecruitmentCandidateLink candidateLink = recruitmentCandidateLinkRepository
				.getCandidateProfile(recruitProfileLinkDetails.getProfile_id());
		if (null != candidateLink) {
			CandidateDetails candidate = candidateDetailsRepository
					.getcandidateDetailsById(candidateLink.getCandidate_id());
			recruitmentOpportunityMapper.setCandidateBilling(candidate.getBilling());
			recruitmentOpportunityMapper.setCandidateName(cadndidateDetails(candidateLink.getCandidate_id()));
			recruitmentOpportunityMapper.setEmailId(candidate.getEmailId());
		}

		recruitmentOpportunityMapper.setCurrency(opportunityRecruitDetails.getCurrency());
		recruitmentOpportunityMapper.setType(opportunityRecruitDetails.getType());
		recruitmentOpportunityMapper.setWorkPreference(opportunityRecruitDetails.getWorkPreferance());
		recruitmentOpportunityMapper
				.setAvilableDate(Utility.getISOFromDate(opportunityRecruitDetails.getAvailable_date()));

		if (!StringUtils.isEmpty(recruitProfileLinkDetails.getRecruitOwner())) {
			EmployeeDetails employee = employeeRepository
					.getEmployeeDetailsByEmployeeId(recruitProfileLinkDetails.getRecruitOwner());
			String middleName = " ";
			String lastName = "";

			if (!StringUtils.isEmpty(employee.getLastName())) {

				lastName = employee.getLastName();
			}
			if (employee.getMiddleName() != null && employee.getMiddleName().length() > 0) {
				middleName = employee.getMiddleName();
				recruitmentOpportunityMapper
						.setRecruitOwner(employee.getFirstName() + " " + middleName + " " + lastName);
			} else {
				recruitmentOpportunityMapper.setRecruitOwner(employee.getFirstName() + " " + lastName);
			}
		}

		recruitmentOpportunityMapper
				.setStageList(getActiveStagesOfProcess(opportunityRecruitDetails.getRecruitment_process_id()));
		RecruitmentPublishDetails recruitmentPublishDetails = recruitmentPublishRepository
				.getRecruitmentPublishDetails(recruitProfileLinkDetails.getRecruitment_id());
		if (null != recruitmentPublishDetails) {

			recruitmentOpportunityMapper.setPublishInd(true);
		} else {

			recruitmentOpportunityMapper.setPublishInd(false);
		}
		if (!StringUtils.isEmpty(opportunityRecruitDetails.getRecruiter_id())) {
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeDetailsByEmployeeId(opportunityRecruitDetails.getRecruiter_id());
			String middleName = " ";
			String lastName = " ";
			if (employeeDetails.getMiddleName() != null) {

				middleName = employeeDetails.getMiddleName();
			}
			if (employeeDetails.getLastName() != null) {

				lastName = employeeDetails.getLastName();
			}

			recruitmentOpportunityMapper
					.setRecruiterName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
		}
		/* Add skill set */
		/*
		 * recruitmentOpportunityMapper.setSkillSetList(candidateService.
		 * getSkillSetOfCandidatesOfUser(orgId)); RecruitmentSkillsetLink
		 * recruitmentSkillsetLink =
		 * recruitmentSkillsetLinkRepository.getRecruitmentSkillSetLink(
		 * recruitmentOpportunityMapper.getOpportunityId(),
		 * recruitmentOpportunityMapper.getProfileId());
		 *
		 * if (null != recruitmentSkillsetLink) {
		 * recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.
		 * getSkillName()); } else { recruitmentOpportunityMapper.setSkillName(""); }
		 */
		/*
		 * skb RecruitmentCandidateLink recruitmentCandidateLink =
		 * candidateLinkRepository.getRecruitmentCandidateLink(
		 * recruitmentOpportunityMapper.getOpportunityId(),
		 * recruitmentOpportunityMapper.getProfileId());
		 *
		 * if (null != recruitmentCandidateLink) {
		 *
		 * recruitmentOpportunityMapper.setCandidateId(recruitmentCandidateLink.
		 * getCandidate_id()); recruitmentOpportunityMapper
		 * .setCandidateName(cadndidateDetails(recruitmentCandidateLink.getCandidate_id(
		 * ))); recruitmentOpportunityMapper
		 * .setCandidateBilling(cadndidateBillingDetails(recruitmentCandidateLink.
		 * getCandidate_id()));
		 *
		 *
		 * } /* Add ApproveInd
		 */
		RecruitProfileStatusLink recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
				.getRecruitProfileStatusLinkByProfileId(recruitmentOpportunityMapper.getProfileId());

		if (null != recruitProfileStatusLink) {
			recruitmentOpportunityMapper.setRejectInd(recruitProfileStatusLink.isRejectInd());
			recruitmentOpportunityMapper.setApproveInd(recruitProfileStatusLink.isApproveInd());
		}

		return recruitmentOpportunityMapper;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getRecriutmentListByOppId(String opportunityId) {

		List<OpportunityRecruitDetails> opportunityRecruitDetailsList = recruitmentOpportunityDetailsRepository
				.getRecriutmentsByOpportunityId(opportunityId);
		// List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		if (null != opportunityRecruitDetailsList && !opportunityRecruitDetailsList.isEmpty()) {
			return opportunityRecruitDetailsList.stream().map(opportunityRecruitDetails -> {
				RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();

				recruitmentOpportunityMapper.setRequirementName(opportunityRecruitDetails.getName());
				recruitmentOpportunityMapper.setRecruitmentId(opportunityRecruitDetails.getRecruitment_id());
				recruitmentOpportunityMapper
						.setRecruitmentProcessId(opportunityRecruitDetails.getRecruitment_process_id());
				recruitmentOpportunityMapper
						.setProcessName(getProcessName(opportunityRecruitDetails.getRecruitment_process_id()));
				recruitmentOpportunityMapper
						.setCreationDate(Utility.getISOFromDate(opportunityRecruitDetails.getCreationDate()));
				return recruitmentOpportunityMapper;
			}).collect(Collectors.toList());
		}

		return null;
	}

	@Override
	public List<CandidateViewMapper> getCandidatesBasedOnSkill(String skill, String recriutmentId, String oppId,
			String orgId) throws Exception {
		System.out.println("orgId@@@@@@@@@" + orgId);

		/*
		 * sk RecruitProfileLinkDetails profileDetails =
		 * recruitProfileDetailsRepository.getProfilesByOppIdAndProfileId(oppId,
		 * profileId);
		 */
		OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
				.getRecriutmentsByOpportunitysId(oppId, recriutmentId);

		List<CandidateViewMapper> list = candidateService.filterListOfCandidateBasedOnRecruitment(skill, orgId,
				recriutmentId);

		return list;
	}

	@Override
	public void linkCandidateToRecruitment(RecruitmentOpportunityMapper recruitmentOpportunityMapper) {
		List<RecruitmentCandidateLink> recruitmentCandidateLinkList = candidateLinkRepository
				.getCandidateList(recruitmentOpportunityMapper.getRecruitmentId());

		List<String> candiList = recruitmentOpportunityMapper.getCandidateIds();
		if (null != recruitmentCandidateLinkList && !recruitmentCandidateLinkList.isEmpty()) {
			if (null != candiList && !candiList.isEmpty()) {
				for (String candidateId : candiList) {

					boolean flag = false;
					for (RecruitmentCandidateLink recruitmentCandidateLink : recruitmentCandidateLinkList) {
						if (recruitmentCandidateLink.getCandidate_id().equals(candidateId)) {

							flag = true;
							break;

						}
					}
					if (flag == false) {
						RecruitmentProfileInfo recruitmentProfileInfo = new RecruitmentProfileInfo();
						recruitmentProfileInfo.setCreation_date(new Date());
						String profileId = recruitmentProfileInfoRepository.save(recruitmentProfileInfo)
								.getRecruitment_profile_info_id();

						RecruitProfileLinkDetails recruitProfileLinkDetails = new RecruitProfileLinkDetails();

						List<RecruitmentStageMapper> list = getStagesOfProcess(
								recruitmentOpportunityMapper.getRecruitmentProcessId());
						Collections.sort(list, (RecruitmentStageMapper c1, RecruitmentStageMapper c2) -> Double
								.compare(c1.getProbability(), c2.getProbability()));
						System.out.println("stageList>>>>" + list.toString());
						if (list.size() != 0) {
							String stageIdd = list.get(1).getStageId();
							recruitProfileLinkDetails.setStage_id(stageIdd);
						}
						recruitProfileLinkDetails.setProfile_id(profileId);
						recruitProfileLinkDetails.setOpp_id(recruitmentOpportunityMapper.getOpportunityId());
						recruitProfileLinkDetails.setRecruitment_id(recruitmentOpportunityMapper.getRecruitmentId());
						recruitProfileLinkDetails.setProcess_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
						recruitProfileLinkDetails.setCreation_date(new Date());
						recruitProfileLinkDetails.setLive_ind(true);
						recruitProfileLinkDetails.setIntrestInd(recruitmentOpportunityMapper.isIntrestInd());
						recruitProfileLinkDetails.setCandidateId(candidateId);

						if (null != recruitmentOpportunityMapper.getRecruitOwner()
								&& !recruitmentOpportunityMapper.getRecruitOwner().isEmpty()) {
							recruitProfileLinkDetails.setRecruitOwner(recruitmentOpportunityMapper.getRecruitOwner());
						} else {
							recruitProfileLinkDetails.setRecruitOwner(recruitmentOpportunityMapper.getTagUserId());
						}

						CandidateDetails candidateDetail = candidateDetailsRepository
								.getcandidateDetailsById(candidateId);
						if (null != candidateDetail) {
							candidateDetail.setCandiProcessInd(true);
						}

						recruitProfileDetailsRepository.save(recruitProfileLinkDetails);
						System.out.println("profileId=1::::::::::" + profileId);
						RecruitmentCandidateLink recruitmentCandidateLink1 = new RecruitmentCandidateLink();

						recruitmentCandidateLink1.setCandidate_id(candidateId);
						recruitmentCandidateLink1.setOpportunity_id(recruitmentOpportunityMapper.getOpportunityId());
						recruitmentCandidateLink1
								.setRecruitment_process_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
						recruitmentCandidateLink1.setProfileId(profileId);
						recruitmentCandidateLink1.setRecruitment_id(recruitmentOpportunityMapper.getRecruitmentId());
						recruitmentCandidateLink1.setCreation_date(new Date());
						recruitmentCandidateLink1.setLive_ind(true);
						recruitmentCandidateLink1.setUser_id(recruitmentOpportunityMapper.getTagUserId());
						candidateLinkRepository.save(recruitmentCandidateLink1);
						System.out.println(
								"profileId=" + candidateLinkRepository.save(recruitmentCandidateLink1).getProfileId()
										+ " " + "candidateId="
										+ candidateLinkRepository.save(recruitmentCandidateLink1).getCandidate_id());
						System.out.println("object:::::::::::"
								+ candidateLinkRepository.save(recruitmentCandidateLink1).toString());
					}
				}
			}

		} else {
			if (null != candiList && !candiList.isEmpty()) {
				for (String candidateId : candiList) {
					RecruitmentProfileInfo recruitmentProfileInfo = new RecruitmentProfileInfo();
					recruitmentProfileInfo.setCreation_date(new Date());
					String profileId = recruitmentProfileInfoRepository.save(recruitmentProfileInfo)
							.getRecruitment_profile_info_id();

					RecruitProfileLinkDetails recruitProfileLinkDetails = new RecruitProfileLinkDetails();

					List<RecruitmentStageMapper> list = getStagesOfProcess(
							recruitmentOpportunityMapper.getRecruitmentProcessId());
					Collections.sort(list, (RecruitmentStageMapper c1, RecruitmentStageMapper c2) -> Double
							.compare(c1.getProbability(), c2.getProbability()));
					System.out.println("stageList>>>>" + list.toString());
					if (list.size() != 0) {
						String stageIdd = list.get(1).getStageId();
						recruitProfileLinkDetails.setStage_id(stageIdd);
					}
					recruitProfileLinkDetails.setProfile_id(profileId);
					recruitProfileLinkDetails.setOpp_id(recruitmentOpportunityMapper.getOpportunityId());
					recruitProfileLinkDetails.setRecruitment_id(recruitmentOpportunityMapper.getRecruitmentId());
					recruitProfileLinkDetails.setProcess_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
					recruitProfileLinkDetails.setCreation_date(new Date());
					recruitProfileLinkDetails.setLive_ind(true);
					recruitProfileLinkDetails.setCandidateId(candidateId);
					recruitProfileLinkDetails.setIntrestInd(recruitmentOpportunityMapper.isIntrestInd());

					if (null != recruitmentOpportunityMapper.getRecruitOwner()
							&& !recruitmentOpportunityMapper.getRecruitOwner().isEmpty()) {
						recruitProfileLinkDetails.setRecruitOwner(recruitmentOpportunityMapper.getRecruitOwner());
					} else {
						recruitProfileLinkDetails.setRecruitOwner(recruitmentOpportunityMapper.getTagUserId());
					}

					recruitProfileDetailsRepository.save(recruitProfileLinkDetails);
					System.out.println("profileId=1::::::::::" + profileId);
					RecruitmentCandidateLink recruitmentCandidateLink1 = new RecruitmentCandidateLink();

					recruitmentCandidateLink1.setCandidate_id(candidateId);
					recruitmentCandidateLink1.setOpportunity_id(recruitmentOpportunityMapper.getOpportunityId());
					recruitmentCandidateLink1
							.setRecruitment_process_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
					recruitmentCandidateLink1.setProfileId(profileId);
					recruitmentCandidateLink1.setRecruitment_id(recruitmentOpportunityMapper.getRecruitmentId());
					recruitmentCandidateLink1.setCreation_date(new Date());
					recruitmentCandidateLink1.setLive_ind(true);
					recruitmentCandidateLink1.setUser_id(recruitmentOpportunityMapper.getTagUserId());
					candidateLinkRepository.save(recruitmentCandidateLink1);
					System.out.println(
							"profileId=" + candidateLinkRepository.save(recruitmentCandidateLink1).getProfileId() + " "
									+ "candidateId="
									+ candidateLinkRepository.save(recruitmentCandidateLink1).getCandidate_id());
					System.out.println(
							"object:::::::::::" + candidateLinkRepository.save(recruitmentCandidateLink1).toString());
				}
			}
		}
	}

	@Override
	public String createRecruitmentStageApprove(RecruitmentStageApproveMapper recruitmentStageApproveMapper) {

		RecruitmentStageApprove recruitmentStageApprove = recruitmentStageApproveRepository
				.findByStageIdAndProcessIdAndLiveInd(recruitmentStageApproveMapper.getStageId(),
						recruitmentStageApproveMapper.getProcessId(), true);

		String id = null;
		if (null != recruitmentStageApprove) {
			if (recruitmentStageApprove.isApprovalIndicator() == false) {

				recruitmentStageApprove.setLiveInd(false);
				recruitmentStageApproveRepository.save(recruitmentStageApprove);
			} else {

				recruitmentStageApprove.setLiveInd(false);
				recruitmentStageApproveRepository.save(recruitmentStageApprove);

				RecruitmentStageApprove dbRecruitmentStageApprove = new RecruitmentStageApprove();

				if (recruitmentStageApproveMapper.getApprovalType().equalsIgnoreCase("standard")) {
					dbRecruitmentStageApprove.setLevel1(recruitmentStageApproveMapper.getLevel1());
					dbRecruitmentStageApprove.setLevel2(recruitmentStageApproveMapper.getLevel2());
					dbRecruitmentStageApprove.setThreshold(recruitmentStageApproveMapper.getThreshold());

				} else if (recruitmentStageApproveMapper.getApprovalType().equalsIgnoreCase("exception")) {
					dbRecruitmentStageApprove.setFunctionId(recruitmentStageApproveMapper.getFunctionTypeId());
					dbRecruitmentStageApprove.setDesignationId(recruitmentStageApproveMapper.getDesignationTypeId());
					dbRecruitmentStageApprove.setJobLevel(recruitmentStageApproveMapper.getJobLevel());
				}

				dbRecruitmentStageApprove.setApprovalType(recruitmentStageApproveMapper.getApprovalType());
				dbRecruitmentStageApprove.setOrganizationId(recruitmentStageApproveMapper.getOrganizationId());
				dbRecruitmentStageApprove.setUserId(recruitmentStageApproveMapper.getUserId());
				dbRecruitmentStageApprove.setApprovalIndicator(recruitmentStageApproveMapper.isApprovalIndicator());
				dbRecruitmentStageApprove.setCreationDate(new Date());
				dbRecruitmentStageApprove.setLiveInd(true);
				dbRecruitmentStageApprove.setStageId(recruitmentStageApproveMapper.getStageId());
				dbRecruitmentStageApprove.setProcessId(recruitmentStageApproveMapper.getProcessId());
				recruitmentStageApproveRepository.save(dbRecruitmentStageApprove);

				RecruitmentStageApprove dbRecruitmentStageApprove1 = recruitmentStageApproveRepository
						.save(dbRecruitmentStageApprove);
				id = dbRecruitmentStageApprove1.getRecruitmentStageApproveId();
				return id;
			}

		} else {

			RecruitmentStageApprove dbRecruitmentStageApprove = new RecruitmentStageApprove();

			if (recruitmentStageApproveMapper.getApprovalType().equalsIgnoreCase("standard")) {

				dbRecruitmentStageApprove.setLevel1(recruitmentStageApproveMapper.getLevel1());
				dbRecruitmentStageApprove.setLevel2(recruitmentStageApproveMapper.getLevel2());
				dbRecruitmentStageApprove.setThreshold(recruitmentStageApproveMapper.getThreshold());

			} else if (recruitmentStageApproveMapper.getApprovalType().equalsIgnoreCase("exception")) {

				dbRecruitmentStageApprove.setFunctionId(recruitmentStageApproveMapper.getFunctionTypeId());
				dbRecruitmentStageApprove.setDesignationId(recruitmentStageApproveMapper.getDesignationTypeId());
				dbRecruitmentStageApprove.setJobLevel(recruitmentStageApproveMapper.getJobLevel());
			}

			dbRecruitmentStageApprove.setApprovalType(recruitmentStageApproveMapper.getApprovalType());
			dbRecruitmentStageApprove.setOrganizationId(recruitmentStageApproveMapper.getOrganizationId());
			dbRecruitmentStageApprove.setUserId(recruitmentStageApproveMapper.getUserId());
			dbRecruitmentStageApprove.setApprovalIndicator(recruitmentStageApproveMapper.isApprovalIndicator());
			dbRecruitmentStageApprove.setCreationDate(new Date());
			dbRecruitmentStageApprove.setLiveInd(true);
			dbRecruitmentStageApprove.setStageId(recruitmentStageApproveMapper.getStageId());
			dbRecruitmentStageApprove.setProcessId(recruitmentStageApproveMapper.getProcessId());

			RecruitmentStageApprove dbRecruitmentStageApprove1 = recruitmentStageApproveRepository
					.save(dbRecruitmentStageApprove);
			System.out.println("reportinggg============" + dbRecruitmentStageApprove.getLevel1());
			id = dbRecruitmentStageApprove1.getRecruitmentStageApproveId();
			return id;
		}

		/* /*insert to task info */
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setCreation_date(new Date());
		TaskInfo task1 = taskInfoRepository.save(taskInfo);

		String taskId = task1.getTask_id();

		if (null != taskId) {

			/* insert to task details table */

			TaskDetails taskDetails = new TaskDetails();
			taskDetails.setTask_id(taskId);
			taskDetails.setCreation_date(new Date());
			// taskDetails.setTask_description(.getReason());
			taskDetails.setPriority("Medium");
			taskDetails.setTask_type("stage");
			// taskDetails.setTask_name(.getCoverDetails());
			taskDetails.setTask_status("Completed");
			taskDetails.setUser_id(recruitmentStageApproveMapper.getEmployeeId());
			taskDetails.setOrganization_id(recruitmentStageApproveMapper.getOrganizationId());
			taskDetails.setLiveInd(true);
			// taskDetails.setCompletion_ind(false);
			/*
			 * try { taskDetails.setStart_date(Utility.getDateFromISOString(
			 * recruitmentStageApproveMapper.getStartDate()));
			 * taskDetails.setEnd_date(Utility.getDateFromISOString(
			 * recruitmentStageApproveMapper.getEndDate()));
			 *
			 * } catch (Exception e) { e.printStackTrace(); }
			 */

			taskDetailsRepository.save(taskDetails);

		}

		/* insert to employee task link table */

		EmployeeDetails details = employeeRepository
				.getEmployeeDetailsByEmployeeId(recruitmentStageApproveMapper.getEmployeeId(), true);

		String hrId = empService.getHREmployeeId(recruitmentStageApproveMapper.getOrganizationId());
		String adminId = empService.getAdminIdByOrgId(recruitmentStageApproveMapper.getOrganizationId());
		String reportingManagerId = null;

		if (null != details) {
			if (!StringUtils.isEmpty(details.getReportingManager())) {
				reportingManagerId = details.getReportingManager();
			} else if (StringUtils.isEmpty(details.getReportingManager()) && !StringUtils.isEmpty(hrId)) {
				reportingManagerId = hrId;

			} else if (StringUtils.isEmpty(hrId)) {
				reportingManagerId = adminId;
			}
		}
		EmployeeTaskLink employeeTaskLink = new EmployeeTaskLink();
		employeeTaskLink.setTask_id(taskId);
		employeeTaskLink.setCreation_date(new Date());
		employeeTaskLink.setLive_ind(true);
		employeeTaskLink.setEmployee_id(reportingManagerId);

		employeeTaskRepository.save(employeeTaskLink);

		return null;
	}

	@Override
	public RecruitmentStageApproveMapper getRecruitmentStageApprove(String stageId) {
		RecruitmentStageApprove recruitmentStageApprove = recruitmentStageApproveRepository
				.findByStageIdAndLiveInd(stageId, true);

		RecruitmentStageApproveMapper recruitmentStageApproveMapper = new RecruitmentStageApproveMapper();

		if (null != recruitmentStageApprove) {

			if (recruitmentStageApprove.getApprovalType().equalsIgnoreCase("exception")) {
				recruitmentStageApproveMapper.setApprovalIndicator(recruitmentStageApprove.isApprovalIndicator());
				Designation designationDetails = designationRepository
						.findByDesignationTypeId(recruitmentStageApprove.getDesignationId());
				// orElseThrow(() ->
				// new ResponseStatusException(HttpStatus.NOT_FOUND, "designation not found " +
				// recruitmentStageApprove.getDesignationId()));

				recruitmentStageApproveMapper.setDesignationTypeId(designationDetails.getDesignationTypeId());
				recruitmentStageApproveMapper.setDesignationName(designationDetails.getDesignationType());

				FunctionDetails function = functionRepository
						.findByFunctionTypeId(recruitmentStageApprove.getFunctionId());
				recruitmentStageApproveMapper.setFunctionTypeId(function.getFunctionTypeId());
				recruitmentStageApproveMapper.setFunctionName(function.getFunctionType());
				recruitmentStageApproveMapper.setApprovalType(recruitmentStageApprove.getApprovalType());

				return recruitmentStageApproveMapper;
			} else {
				recruitmentStageApproveMapper.setApprovalIndicator(recruitmentStageApprove.isApprovalIndicator());
				recruitmentStageApproveMapper.setApprovalType(recruitmentStageApprove.getApprovalType());
				recruitmentStageApproveMapper.setLevel1(recruitmentStageApprove.getLevel1());
				recruitmentStageApproveMapper.setLevel2(recruitmentStageApprove.getLevel2());
				recruitmentStageApproveMapper.setThreshold(recruitmentStageApprove.getThreshold());
			}
		}
		return recruitmentStageApproveMapper;
	}

	@Override
	public String createNoteForRecrutmentStage(RecruitmentOpportunityMapper recruitmentOpportunityMapper) {

		int score = 0;
		float avg = 0;
		int count = 0;
		if (null != recruitmentOpportunityMapper.getNote() && !recruitmentOpportunityMapper.getNote().isEmpty()) {

			RecruitStageNoteLink recruitStageNoteLink = new RecruitStageNoteLink();

			// recruitStageNoteLink.setRecruit_id(recruitmentOpportunityMapper.getRecruitmentId());
			recruitStageNoteLink.setNote(recruitmentOpportunityMapper.getNote());
			recruitStageNoteLink.setScore(recruitmentOpportunityMapper.getScore());
			recruitStageNoteLink.setStage_id(recruitmentOpportunityMapper.getStageId());
			// recruitStageNoteLink.setOpp_id(recruitmentOpportunityMapper.getOpportunityId());
			recruitStageNoteLink.setProfile_id(recruitmentOpportunityMapper.getProfileId());
			if (!StringUtils.isEmpty(recruitmentOpportunityMapper.getReviewer())) {

				recruitStageNoteLink.setReviewer(recruitmentOpportunityMapper.getReviewer());

			}
			recruitStageNoteLink.setUserId(recruitmentOpportunityMapper.getTagUserId());
			recruitStageNoteLink.setCreationDate(new Date());
			recruitStageNoteLink.setLive_ind(true);

			// recruitmentDao.insertToRecrutiStageNoteLink(recruitStageNoteLink);
			recruitStageNotelinkRepository.save(recruitStageNoteLink);

			List<RecruitmentCandidateLink> candidateList = recruitmentCandidateLinkRepository
					.getCandidateRecruitmentLinkByCandidateId(recruitmentOpportunityMapper.getCandidateId());

			if (null != candidateList && !candidateList.isEmpty()) {
				for (RecruitmentCandidateLink recruitmentCandidateLink : candidateList) {

					List<RecruitStageNoteLink> recruitStageNoteList = recruitStageNotelinkRepository
							.getStageNoteLink(recruitmentCandidateLink.getProfileId());

					for (RecruitStageNoteLink recruitStageNoteLink2 : recruitStageNoteList) {

						score += recruitStageNoteLink2.getScore();
						count++;
						System.out.println("count=" + count);
					}
				}
				System.out.println("TotalCount=" + count + "||" + count);
				System.out.println("score=" + score);
			}
			try {
				avg = (float) score / count;
				System.out.println("avg=" + avg + "||" + score / count);
			} catch (ArithmeticException e) {
				avg = 0;
			}

			RecruitmentAverageFeedback recruitmentAverageFeedback = recruitmentAvgFeedbackRepository
					.findByCandidateId(recruitmentOpportunityMapper.getCandidateId());
			if (null != recruitmentAverageFeedback) {
				recruitmentAverageFeedback.setAverage(avg);
				recruitmentAvgFeedbackRepository.save(recruitmentAverageFeedback);
			}
			RecruitmentAverageFeedback newRecruitmentAverageFeedback = new RecruitmentAverageFeedback();
			newRecruitmentAverageFeedback.setAverage(avg);
			recruitmentAvgFeedbackRepository.save(newRecruitmentAverageFeedback);
		}
		return recruitmentOpportunityMapper.getStageId();
	}

	@Override
	public List<RecruitmentOpportunityMapper> getRecruitStageNote(String profileId) {
		List<RecruitStageNoteLink> list = recruitStageNotelinkRepository.getStageNoteLink(profileId);
		System.out.println("list.............." + list.toString());
		// List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();

		if (null != list && !list.isEmpty()) {
			return list.stream().map(recruitStageNoteLink2 -> {

				RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();

				recruitmentOpportunityMapper
						.setRecruitmentStageNoteId(recruitStageNoteLink2.getRecruitmentStageNoteId());
				recruitmentOpportunityMapper.setNote(recruitStageNoteLink2.getNote());
				recruitmentOpportunityMapper.setScore(recruitStageNoteLink2.getScore());
				recruitmentOpportunityMapper.setReviewer(recruitStageNoteLink2.getReviewer());
				recruitmentOpportunityMapper.setStageId(recruitStageNoteLink2.getStage_id());
				recruitmentOpportunityMapper.setProfileId(recruitStageNoteLink2.getProfile_id());
				recruitmentOpportunityMapper.setUpdatedOn(Utility.getISOFromDate(recruitStageNoteLink2.getUpdatedOn()));

				recruitmentOpportunityMapper
						.setCreationDate(Utility.getISOFromDate(recruitStageNoteLink2.getCreationDate()));

				// RecruitmentStageDetails stageDetails =
				// recruitmentDao.getRecruitmentStageDetailsByStageId(recruitStageNoteLink2.getStage_id());
				RecruitmentProcessStageDetails stageDetails = recruitmentStageDetailsRepository
						.getRecruitmentStageDetailsByStageId(recruitStageNoteLink2.getStage_id());

				if (null != stageDetails) {

					recruitmentOpportunityMapper.setStageName(stageDetails.getStage_name());

				}
				EmployeeDetails employeeDetails = employeeRepository
						.getEmployeeDetailsByEmployeeId(recruitStageNoteLink2.getUserId());
				if (null != employeeDetails) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						recruitmentOpportunityMapper
								.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						recruitmentOpportunityMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
					}
				}
				return recruitmentOpportunityMapper;
			}).collect(Collectors.toList());
		}
		return null;
	}

	public RecruitmentOpportunityMapper updateStageOfAProfile(
			RecruitmentOpportunityMapper recruitmentOpportunityMapper) {

		// RecruitProfileLinkDetails recruitProfileLinkDetails =
		// recruitProfileDetailsRepository
		// .getProfilesByOppIdAndProfileId(recruitmentOpportunityMapper.getOpportunityId(),
		// recruitmentOpportunityMapper.getProfileId());
		RecruitProfileLinkDetails recruitProfileLinkDetails = recruitProfileDetailsRepository
				.getprofiledetails(recruitmentOpportunityMapper.getProfileId());
		if (null != recruitProfileLinkDetails) {
			System.out.println("recruitProfileLinkDetails....." + recruitProfileLinkDetails.toString());
			recruitProfileLinkDetails.setLive_ind(false);
			recruitProfileDetailsRepository.save(recruitProfileLinkDetails);

			RecruitProfileLinkDetails recruitProfileLinkDetails1 = new RecruitProfileLinkDetails();

			recruitProfileLinkDetails1.setRecruitment_id(recruitProfileLinkDetails.getRecruitment_id());
			recruitProfileLinkDetails1.setProfile_id(recruitProfileLinkDetails.getProfile_id());
			recruitProfileLinkDetails1.setProcess_id(recruitProfileLinkDetails.getProcess_id());
			recruitProfileLinkDetails1.setStage_id(recruitmentOpportunityMapper.getStageId());
			recruitProfileLinkDetails1.setOpp_id(recruitProfileLinkDetails.getOpp_id());
			recruitProfileLinkDetails1.setModification_date(new Date());
			recruitProfileLinkDetails1.setCreation_date(recruitProfileLinkDetails.getCreation_date());
			recruitProfileLinkDetails1.setLive_ind(true);
			recruitProfileLinkDetails1.setRecruitOwner(recruitProfileLinkDetails.getRecruitOwner());
			recruitProfileDetailsRepository.save(recruitProfileLinkDetails1);
		}
		System.out.println("test::::::::::::::::::::::::");
		return getProfileDetails(recruitmentOpportunityMapper.getProfileId());
	}

	@Override
	public List<RecruitmentOpportunityMapper> getrecruitmentsOfACandidate(String candidateId) {
		List<RecruitmentCandidateLink> recruitCandidateList = recruitmentCandidateLinkRepository
				.getCandidateRecruitmentLinkByCandidateId(candidateId);
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		if (null != recruitCandidateList && !recruitCandidateList.isEmpty()) {

			recruitCandidateList.stream().map(li -> {
				RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
				OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
						.getRecriutmentsByOpportunitysId(li.getOpportunity_id(), li.getRecruitment_id());
				if (null != opportunityRecruitDetails) {
					System.out.println("opportunityRecruitDetails/:::::::::" + opportunityRecruitDetails.toString());

					System.out.println("oppid......." + li.getOpportunity_id());
					System.out.println("recruitmentId........." + opportunityRecruitDetails.getRecruitment_id());
					recruitmentOpportunityMapper.setRequirementName(opportunityRecruitDetails.getName());
					recruitmentOpportunityMapper.setRecruitmentId(opportunityRecruitDetails.getRecruitment_id());
					recruitmentOpportunityMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
					recruitmentOpportunityMapper
							.setAvilableDate(Utility.getISOFromDate(opportunityRecruitDetails.getAvailable_date()));
					if (null != opportunityRecruitDetails.getEnd_date()) {
						recruitmentOpportunityMapper
								.setEndDate(Utility.getISOFromDate(opportunityRecruitDetails.getEnd_date()));
					}
					recruitmentOpportunityMapper
							.setRecruitmentProcessId(opportunityRecruitDetails.getRecruitment_process_id());
					recruitmentOpportunityMapper.setStageList(
							getActiveStagesOfProcess(opportunityRecruitDetails.getRecruitment_process_id()));
					if (!StringUtils.isEmpty(opportunityRecruitDetails.getRecruitment_process_id())) {
						recruitmentOpportunityMapper
								.setProcessName(getProcessName(opportunityRecruitDetails.getRecruitment_process_id()));
					}
					recruitmentOpportunityMapper.setProfileId(li.getProfileId());
				}
				RecruitProfileLinkDetails profile = recruitmentProfileDetailsRepository
						.getprofiledetails(li.getProfileId());
				if (profile.isOnboard_ind() == true) {
					recruitmentOpportunityMapper.setResult("OnBoarded");
				} else {
					RecruitProfileStatusLink status = recruitmentProfileStatusLinkRepository
							.getRecruitProfileStatusLinkByProfileId(li.getProfileId());
					if (null != status) {
						if (status.isApproveInd() == true) {
							recruitmentOpportunityMapper.setResult("Selected");
						} else {
							recruitmentOpportunityMapper.setResult("Rejected");
						}
					}
				}
				RecruitStageNoteLink recruitStageNoteLink = recruitStageNotelinkRepository
						.getByProfileId(li.getProfileId());
				if (null != recruitStageNoteLink) {
					recruitmentOpportunityMapper.setFeedBackScore(recruitStageNoteLink.getScore());
				}

				OpportunityDetails opportunityDetails = opportunityDetailsRepository
						.getOpportunityDetailsByOpportunityId(li.getOpportunity_id());
				if (null != opportunityDetails) {
					System.out.println(
							"opportunityDetails.......*************" + opportunityDetails.getOpportunityName());
					recruitmentOpportunityMapper.setOpprtunityName(opportunityDetails.getOpportunityName());

					if (null != opportunityDetails.getCustomerId()) {

						if (null != customerRepository
								.getCustomerDetailsByCustomerId(opportunityDetails.getCustomerId())) {
							recruitmentOpportunityMapper.setAccountName(customerRepository
									.getCustomerDetailsByCustomerId(opportunityDetails.getCustomerId()).getName());
						}

					}
				}
				return mapperList.add(recruitmentOpportunityMapper);
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public int getNoOfRecruitmentOfOpportunity(String opportunityId) {

		List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
				.getRecriutmentsByOpportunityId(opportunityId);
		int count = 0;
		if (null != recruitList && !recruitList.isEmpty()) {
			count = recruitList.size();

		}
		return count;
	}

	@Override
	public int getTotalNoOfPositionOfRecruitments(String opportunityId) {

		List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
				.getRecriutmentsByOpportunityId(opportunityId);
		int total = 0;
		if (null != recruitList && !recruitList.isEmpty()) {
			for (OpportunityRecruitDetails opportunityRecruitDetails : recruitList) {

				total = total + (int) opportunityRecruitDetails.getNumber();
			}
		}
		return total;
	}

	@Override
	public int getTotalFilledPosition(String opportunityId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map getSkillsCountInDescription(String recruitmentId, String orgId) {

		List<DefinationDetails> list = definationRepository.getDefinationsOfAdmin(orgId);
		Map<String, Long> map = new LinkedHashMap<String, Long>();
		ArrayList<String> words = new ArrayList<>();
		OpportunityRecruitDetails recrutimentDetails = recruitmentOpportunityDetailsRepository
				.getRecruitmentDetailsByRecruitId(recruitmentId);
		String description = recrutimentDetails.getDescription().replace(",", " ");
		if (null != list && !list.isEmpty()) {

			List<String> definations = list.stream().map(li -> li.getName()).collect(Collectors.toList());

			if (null != definations) {
				String[] definationArr = new String[definations.size()];
				definationArr = definations.toArray(definationArr);

				if (!StringUtils.isEmpty(description)) {
					// List<String> descriptionList = Arrays.asList(description.split("\\s*,\\s*"));
					List<String> descriptionList = Arrays.asList(description.split(" "));
					for (String word : definationArr) {

						for (String description1 : descriptionList) {
							if (description1.equalsIgnoreCase(word)) {

								words.add(word);
							}
						}
					}
					map = words.stream().collect(Collectors.groupingBy(s -> s.toString(), Collectors.counting()));
				}
			}
		}
		return map;
	}

	@Override
	public boolean updateProcessPublish(RecruitmentProcessMapper recruitmentProcessMapper) {

		List<GlobalProcessStageLink> dbGlobalProcessStageLink1 = recruitmentProcessStageLinkRepository
				.getRecruitmentProcessStageLinkByProcessId(recruitmentProcessMapper.getRecruitmentProcessId());
		if (null != dbGlobalProcessStageLink1 && !dbGlobalProcessStageLink1.isEmpty()) {
			if (dbGlobalProcessStageLink1.size() > 2) {

				GlobalProcessDetails globalProcessDetails = processDetailsRepository
						.getProcessDetailsByProcessId(recruitmentProcessMapper.getRecruitmentProcessId());
				if (null != globalProcessDetails) {
					globalProcessDetails.setPublishInd(recruitmentProcessMapper.isPublishInd());
					processDetailsRepository.save(globalProcessDetails);

					List<GlobalProcessStageLink> dbGlobalProcessStageLink = recruitmentProcessStageLinkRepository
							.getRecruitmentProcessStageLinkByProcessId(globalProcessDetails.getProcess_id());
					if (null != dbGlobalProcessStageLink && !dbGlobalProcessStageLink.isEmpty()) {
						for (GlobalProcessStageLink globalProcessStageLink : dbGlobalProcessStageLink) {

							RecruitmentProcessStageDetails stageDetails = recruitmentStageDetailsRepository
									.getRecruitmentStageDetailsByStageId(
											globalProcessStageLink.getRecruitmentStageId());
							if (null != stageDetails) {
								stageDetails.setPublishInd(recruitmentProcessMapper.isPublishInd());
								recruitmentStageDetailsRepository.save(stageDetails);
							}
						}
					}
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public boolean unpublishTheStages(RecruitmentProcessMapper recruitmentProcessMapper) {

		RecruitmentProcessStageDetails stageDetails = recruitmentStageDetailsRepository
				.getRecruitmentStageDetailsByStageId(recruitmentProcessMapper.getStageId());
		if (null != stageDetails) {
			stageDetails.setPublishInd(recruitmentProcessMapper.isPublishInd());
			recruitmentStageDetailsRepository.save(stageDetails);

			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean publishTheRecruitment(RecruitmentOpportunityMapper recruitmentOpportunityMapper) {
		String id = null;
		RecruitmentPublishDetails recruitmentPublishDetails = recruitmentPublishRepository
				.getRecruitmentPublishDetails(recruitmentOpportunityMapper.getRecruitmentId());

		if (null != recruitmentPublishDetails) {
			recruitmentPublishDetails.setLiveInd(false);
			id = recruitmentPublishRepository.save(recruitmentPublishDetails).getRecruitment_publish_details_id();
		} else {
			RecruitmentPublishDetails newRecruitmentPublishDetails = new RecruitmentPublishDetails();
			newRecruitmentPublishDetails.setRecruiter_id(recruitmentOpportunityMapper.getRecruitmentId());
			newRecruitmentPublishDetails.setOrg_id(recruitmentOpportunityMapper.getOrgId());
			newRecruitmentPublishDetails.setCreationDate(new Date());
			newRecruitmentPublishDetails.setLiveInd(true);

			id = recruitmentPublishRepository.save(newRecruitmentPublishDetails).getRecruiter_id();
		}
		if (id != null) {
			return true;
		} else {

			return false;
		}
	}

	@Override
	public boolean UnpublishTheRecruitment(RecruitmentOpportunityMapper recruitmentOpportunityMapper) {

		RecruitmentPublishDetails recruitmentPublishDetails = recruitmentPublishRepository
				.getRecruitmentPublishDetails(recruitmentOpportunityMapper.getRecruitmentId());

		recruitmentPublishDetails.setLiveInd(false);
		String id = recruitmentPublishRepository.save(recruitmentPublishDetails).getRecruitment_publish_details_id();

		if (id != null) {
			return true;
		} else {

			return false;
		}
	}

	@Override
	public List<RecruitmentOpportunityMapper> getPublishedRecruitemntsToWebsite(String website) {

		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		JobPublishDetails jobPublishDetails = jobPublishRepository.getDetailsByWebsite(website);

		if (null != jobPublishDetails) {

			List<RecruitmentPublishDetails> list = recruitmentPublishRepository
					.getRecruitmentDetailsByOrgId(jobPublishDetails.getOrgId());

			if (null != list && !list.isEmpty()) {

				list.stream().map(li -> {
					OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
							.getRecruitmentDetailsByRecruitId(li.getRecruiter_id());
					RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
					recruitmentOpportunityMapper.setRequirementName(opportunityRecruitDetails.getName());
					recruitmentOpportunityMapper.setDescription(opportunityRecruitDetails.getDescription());
					recruitmentOpportunityMapper.setNumber(opportunityRecruitDetails.getNumber());
					recruitmentOpportunityMapper.setBilling(opportunityRecruitDetails.getBilling());
					recruitmentOpportunityMapper.setCurrency(opportunityRecruitDetails.getCurrency());
					recruitmentOpportunityMapper.setType(opportunityRecruitDetails.getType());
					recruitmentOpportunityMapper
							.setAvilableDate(Utility.getISOFromDate(opportunityRecruitDetails.getAvailable_date()));

					return mapperList.add(recruitmentOpportunityMapper);
				}).collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getRecruitmentSummary(String oppId) {
		List<OpportunityRecruitDetails> list = recruitmentOpportunityDetailsRepository
				.getRecriutmentsByOpportunityId(oppId);
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {

			list.stream().map(li -> {

				RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
				recruitmentOpportunityMapper.setRecruiterName(li.getName());
				recruitmentOpportunityMapper.setNumber(li.getNumber());
				recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
				recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
				recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
				recruitmentOpportunityMapper.setCloseInd(li.isCloseInd());
				if (!StringUtils.isEmpty(li.getSponser_id())) {

					ContactDetails contactDetails = contactRepository.getContactDetailsById(li.getSponser_id());
					String middleName = " ";
					String lastName = " ";
					if (null != contactDetails.getMiddle_name()) {

						middleName = contactDetails.getMiddle_name();
					}
					if (null != contactDetails.getLast_name()) {

						lastName = contactDetails.getLast_name();
					}
					recruitmentOpportunityMapper
							.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
				}
				List<RecruitProfileLinkDetails> listRecruitProfileLinkDetails = recruitmentProfileDetailsRepository
						.getProfileDetailByRecruitmentId(li.getRecruitment_id());
				if (null != listRecruitProfileLinkDetails && !listRecruitProfileLinkDetails.isEmpty()) {
					List<CandidateMapper> list1 = new ArrayList<>();
					int count = 0;
					// int internalCandiNo = 0;
					// int websiteCandiNo = 0;
					for (RecruitProfileLinkDetails recruitProfileLinkDetails : listRecruitProfileLinkDetails) {
						RecruitmentCandidateLink recruitmentCandidateLink = recruitmentCandidateLinkRepository
								.getCandidateProfile(recruitProfileLinkDetails.getProfile_id());

						if (null != recruitmentCandidateLink) {
							CandidateMapper candidate = new CandidateMapper();
							CandidateDetails contactDetails = candidateDetailsRepository
									.getcandidateDetailsById(recruitmentCandidateLink.getCandidate_id());
							if (null != contactDetails) {
								String middleName = " ";
								String lastName = " ";
								if (null != contactDetails.getMiddleName()) {

									middleName = contactDetails.getMiddleName();
								}
								if (null != contactDetails.getLastName()) {

									lastName = contactDetails.getLastName();
								}
								candidate
										.setFullName(contactDetails.getFirstName() + " " + middleName + " " + lastName);
								candidate.setImageId(contactDetails.getImageId());
								list1.add(candidate);
								count++;
								if (count == 2) {
									break;
								}
							}
							// candidateMapperList.add(candidateMapperList);
						}
						recruitmentOpportunityMapper.setCandidatetList(list1);
					}
				}

				int internalCandiNo = 0;
				int websiteCandiNo = 0;
				List<RecruitmentCandidateLink> candidatelist = recruitmentCandidateLinkRepository
						.getCandidateLinkByRecruitmentId(li.getRecruitment_id());

				if (null != candidatelist && !candidatelist.isEmpty()) {

					for (RecruitmentCandidateLink recruitmentCandidateLink : candidatelist) {
						CandidateDetails candidateDetails = candidateDetailsRepository
								.getcandidateDetailsById(recruitmentCandidateLink.getCandidate_id());
						if (candidateDetails.getChannel().equalsIgnoreCase("self")) {

							internalCandiNo++;
						}
						if (candidateDetails.getChannel().equalsIgnoreCase("Website")) {
							websiteCandiNo++;
						}
					}
				}
				recruitmentOpportunityMapper.setInternalCandiNo(internalCandiNo);
				recruitmentOpportunityMapper.setWebsiteCandiNo(websiteCandiNo);

				List<RecruitProfileLinkDetails> onbordingList = recruitmentProfileDetailsRepository
						.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());
				if (null != onbordingList) {
					recruitmentOpportunityMapper.setOnBoardNo(onbordingList.size());
				}
				List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
						.getCandidateRecruitmentLinkByOppIdAndRecruitId(oppId, li.getRecruitment_id());
				if (null != recruitmentCandidateLink) {
					recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
				}
				List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
						.getRecruitProfileStatusLinkByRecruitmentId(li.getRecruitment_id());
				if (null != recruitProfileStatusLink) {
					recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
				}
				RecruitmentPublishDetails recruitmentPublishDetails = recruitmentPublishRepository
						.getRecruitmentPublishDetails(li.getRecruitment_id());
				if (null != recruitmentPublishDetails) {
					recruitmentOpportunityMapper.setPublishInd(true);
				} else {
					recruitmentOpportunityMapper.setPublishInd(false);
				}

				int count = 0;
				List<RecruitmentRecruiterLink> recruiterList = recruitmentRecruiterLinkRepository
						.getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id());
				if (null != recruiterList && !recruiterList.isEmpty()) {
					List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
					for (RecruitmentRecruiterLink recruiter : recruiterList) {
						EmployeeMapper mapper = new EmployeeMapper();
						EmployeeDetails emp = employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
						System.out.println("name....." + emp.getFullName());
						mapper.setFullName(emp.getFullName());
						mapper.setImageId(emp.getImageId());

						resultList.add(mapper);
						count++;
						if (count == 2) {
							break;
						}
					}

					recruitmentOpportunityMapper.setRecruiterList(resultList);
				}
				List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
						.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
				if (recruitmentSkillsetLink.size() != 0) {
					recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

				}
				recruitmentOpportunityMapper.setCandidateNo(candidateNo(recruitmentCandidateLink.size()));
				return mapperList.add(recruitmentOpportunityMapper);

			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentStageMapper> getActiveStagesOfProcess(String processId) {

		List<GlobalProcessStageLink> stageList = recruitmentProcessStageLinkRepository
				.getRecruitmentProcessStageLinkByProcessId(processId);
		List<RecruitmentStageMapper> mapperList = new ArrayList<>();
		if (null != stageList && !stageList.isEmpty()) {

			stageList.stream().map(li -> {

				RecruitmentProcessStageDetails recruitmentProcessStageDetails = recruitmentStageDetailsRepository
						.getPublishedStageDetailsOfProcess(li.getRecruitmentStageId());
				if (null != recruitmentProcessStageDetails) {

					RecruitmentStageMapper recruitmentStageMapper = new RecruitmentStageMapper();
					recruitmentStageMapper.setStageId(recruitmentProcessStageDetails.getRecruitmentStageId());
					recruitmentStageMapper.setStageName(recruitmentProcessStageDetails.getStage_name());
					recruitmentStageMapper.setProbability(recruitmentProcessStageDetails.getProbability());
					mapperList.add(recruitmentStageMapper);

					if (null != mapperList && !mapperList.isEmpty()) {

						Collections.sort(mapperList, (RecruitmentStageMapper c1, RecruitmentStageMapper c2) -> Double
								.compare(c2.getProbability(), c1.getProbability()));
					}
				}
				return mapperList;

			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public ByteArrayInputStream exportRecruitmentSummary(List<RecruitmentOpportunityMapper> summaryList) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("summary");

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
		for (int i = 0; i < summary_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(summary_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != summaryList && !summaryList.isEmpty()) {
			for (RecruitmentOpportunityMapper recruitment : summaryList) {

				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(recruitment.getRecruiterName());
				row.createCell(1).setCellValue(recruitment.getNumber());
				row.createCell(2).setCellValue(recruitment.getSponserName());
				row.createCell(3).setCellValue(recruitment.getClosedPosition());
				row.createCell(4).setCellValue(recruitment.getOpenedPosition());
				row.createCell(5).setCellValue(recruitment.getOffered());
				row.createCell(6).setCellValue(recruitment.getRejected());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < summary_headings.length; i++) {
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
	public String createTaskForProfile(RecruitmentStageApproveMapper recruitmentStageApproveMapper) {
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setCreation_date(new Date());
		TaskInfo info = taskInfoRepository.save(taskInfo);

		String taskId = info.getTask_id();

		if (null != taskId) {

			TaskDetails task = new TaskDetails();
			task.setTask_id(taskId);
			task.setTask_description(recruitmentStageApproveMapper.getDescription());
			// task.setStartDate(UtilService.getDateFromISOString(taskDTO.getStartDate()));
			// task.setEndDate(UtilService.getDateFromISOString(taskDTO.getEndDate()));

			task.setStart_date(new Date());
			task.setEnd_date(new Date());
			task.setTask_name(recruitmentStageApproveMapper.getTaskName());
			task.setTask_status(recruitmentStageApproveMapper.getTaskStatus());
			task.setPriority(recruitmentStageApproveMapper.getPriority());
			task.setTask_type(recruitmentStageApproveMapper.getTaskType());
			task.setTask_description(recruitmentStageApproveMapper.getDescription());
			task.setTask_type(recruitmentStageApproveMapper.getTaskTypeId());
			task.setTime_zone("GMT+00:00 New Delhi");
			task.setUser_id(recruitmentStageApproveMapper.getUserId());

			String id = taskRepository.save(task).getTask_details_id();

			ProcessStageTaskLink processStageTaskLink = new ProcessStageTaskLink();
			processStageTaskLink.setTaskId(taskId);
			processStageTaskLink.setStageId(recruitmentStageApproveMapper.getStageId());
			processStageTaskLink.setLiveInd(true);
			processStageTaskLinkRepository.save(processStageTaskLink);

			TaskLevelLink taskLevelLink = new TaskLevelLink();

			taskLevelLink.setTaskId(taskId);
			taskLevelLink.setOwnerDepartment(recruitmentStageApproveMapper.getDepartmentId());
			taskLevelLink.setLevel(recruitmentStageApproveMapper.getJobLevel());
			taskLevelLink.setUnit(recruitmentStageApproveMapper.getUnit());
			taskLevelLink.setUnitValues(recruitmentStageApproveMapper.getUnitValue());
			taskLevelLink.setLevelType(recruitmentStageApproveMapper.getExecutorLevelType());
			taskLevelLink.setLiveInd(true);

			taskLevelLink.setExecutorFunction(recruitmentStageApproveMapper.getExecutorFunctionId());

			taskLevelLinkRepository.save(taskLevelLink);

			if (recruitmentStageApproveMapper.isApprovalInd() == true) {

				ApprovalTaskLink approvalTaskLink = new ApprovalTaskLink();

				approvalTaskLink.setTaskId(taskId);
				approvalTaskLink.setApprovalDepartment(recruitmentStageApproveMapper.getDepartmentId());
				approvalTaskLink.setApprovalLevel(recruitmentStageApproveMapper.getApprovalLevel());
				approvalTaskLink.setApprovalUnit(recruitmentStageApproveMapper.getApprovalUnit());
				approvalTaskLink.setApprovalUnitValue(recruitmentStageApproveMapper.getApprovalUnitValue());
				approvalTaskLink.setApprovalLevelType(recruitmentStageApproveMapper.getApproverLevelType());
				approvalTaskLink.setApprovalInd(recruitmentStageApproveMapper.isApprovalInd());
				approvalTaskLink.setApprovalFunction(recruitmentStageApproveMapper.getApproverFunctionId());
				approvalTaskLinkRepository.save(approvalTaskLink);
			}
		}
		return recruitmentStageApproveMapper.getStageId();
	}

	@Override
	public List<RecruitmentStageApproveMapper> getTasksOfStages(String stageId) {
		List<RecruitmentStageApproveMapper> mapperList = new ArrayList<>();
		List<ProcessStageTaskLink> stageList = processStageTaskLinkRepository.getTasksOfStages(stageId);
		if (null != stageList && !stageList.isEmpty()) {
			return stageList.stream().map(processStageTaskLink -> {
				// for (ProcessStageTaskLink processStageTaskLink : stageList) {
				TaskDetails task = taskRepository.getTaskDetailsById(processStageTaskLink.getTaskId());

				RecruitmentStageApproveMapper recruitmentStageApproveMapper = new RecruitmentStageApproveMapper();
				recruitmentStageApproveMapper.setTaskName(task.getTask_name());
				recruitmentStageApproveMapper.setTaskId(task.getTask_id());
				recruitmentStageApproveMapper.setDescription(task.getTask_description());
				recruitmentStageApproveMapper.setPriority(task.getPriority());
				if (!StringUtils.isEmpty(task.getTask_type())) {
					TaskType taskType = taskTypeRepository.findByTaskTypeId(task.getTask_type());
					if (null != taskType) {
						recruitmentStageApproveMapper.setTaskType(taskType.getTaskType());
						recruitmentStageApproveMapper.setTaskTypeId(taskType.getTaskTypeId());
					}
				}
				TaskLevelLink taskLevelLink = taskLevelLinkRepository
						.getTasklevelLink(processStageTaskLink.getTaskId());
				recruitmentStageApproveMapper.setUnit(taskLevelLink.getUnit());
				recruitmentStageApproveMapper.setUnitValue(taskLevelLink.getUnitValues());
				recruitmentStageApproveMapper.setJobLevel(taskLevelLink.getLevel());
				if (!StringUtils.isEmpty(taskLevelLink.getOwnerDepartment())) {
					Department department = departmentRepository
							.getDepartmentDetails(taskLevelLink.getOwnerDepartment());
					if (null != department) {
						recruitmentStageApproveMapper.setOwnerDepartmentName(department.getDepartmentName());
						recruitmentStageApproveMapper.setDepartmentId(department.getDepartment_id());
					}
				}
				recruitmentStageApproveMapper.setExecutorLevelType(taskLevelLink.getLevelType());
				if (!StringUtils.isEmpty(taskLevelLink.getExecutorFunction())) {
					FunctionDetails function = functionDetailsRepository
							.findByFunctionTypeId(taskLevelLink.getExecutorFunction());
					if (null != function) {
						recruitmentStageApproveMapper.setExecutorFunctionName(function.getFunctionType());
						recruitmentStageApproveMapper.setFunctionTypeId(function.getFunctionTypeId());
					}
				}
				ApprovalTaskLink approvalLink = approvalTaskLinkRepository
						.getTaskApprovalLink(processStageTaskLink.getTaskId());

				if (null != approvalLink) {
					if (!StringUtils.isEmpty(approvalLink.getApprovalDepartment())) {
						Department department = departmentRepository
								.getDepartmentDetails(approvalLink.getApprovalDepartment());
						if (null != department) {
							recruitmentStageApproveMapper.setApprovalDepartment(department.getDepartmentName());
							recruitmentStageApproveMapper.setDepartmentId(department.getDepartment_id());
						}
					}
					recruitmentStageApproveMapper.setApprovalLevel(approvalLink.getApprovalLevel());
					recruitmentStageApproveMapper.setApprovalUnit(approvalLink.getApprovalUnit());
					recruitmentStageApproveMapper.setApprovalUnitValue(approvalLink.getApprovalUnitValue());
					recruitmentStageApproveMapper.setApproverLevelType(approvalLink.getApprovalLevelType());
					recruitmentStageApproveMapper.setApprovalInd(approvalLink.isApprovalInd());
					if (!StringUtils.isEmpty(approvalLink.getApprovalFunction())) {
						FunctionDetails functionDetails = functionDetailsRepository
								.findByFunctionTypeId(approvalLink.getApprovalFunction());
						if (null != functionDetails) {
							recruitmentStageApproveMapper.setApproverFunctionName(functionDetails.getFunctionType());
							recruitmentStageApproveMapper.setApproverFunctionId(functionDetails.getFunctionTypeId());
						}
					}
					return recruitmentStageApproveMapper;
				}
				return null;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentStageApproveMapper> getOfProfoileDetails(String profileId) {

		List<RecruitmentStageApproveMapper> mapperList = new ArrayList<>();
		String stageId = recruitmentProfileDetailsRepository.getprofiledetails(profileId).getStage_id();

		List<ProcessStageTaskLink> stageList = processStageTaskLinkRepository.getTasksOfStages(stageId);
		if (null != stageList && !stageList.isEmpty()) {
			return stageList.stream().map(processStageTaskLink -> {
				// for (ProcessStageTaskLink processStageTaskLink : stageList) {
				TaskDetails task = taskRepository.getTaskDetailsById(processStageTaskLink.getTaskId());

				RecruitmentStageApproveMapper recruitmentStageApproveMapper = new RecruitmentStageApproveMapper();
				recruitmentStageApproveMapper.setTaskName(task.getTask_name());
				recruitmentStageApproveMapper.setTaskId(task.getTask_id());
				recruitmentStageApproveMapper.setDescription(task.getTask_description());
				recruitmentStageApproveMapper.setPriority(task.getPriority());
				if (!StringUtils.isEmpty(task.getTask_type())) {
					TaskType taskType = taskTypeRepository.findByTaskTypeId(task.getTask_type());
					if (null != taskType) {
						recruitmentStageApproveMapper.setTaskType(taskType.getTaskType());
						recruitmentStageApproveMapper.setTaskTypeId(taskType.getTaskTypeId());
					}
					return recruitmentStageApproveMapper;
				}
				return null;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<CandidateViewMapper> getCandidatesBasedOnSkillByUserId(String skill, String recriutmentId, String oppId,
			String userId) throws Exception {
		System.out.println("userId@@@@@@@@@" + userId);

		/*
		 * sk RecruitProfileLinkDetails profileDetails =
		 * recruitProfileDetailsRepository.getProfilesByOppIdAndProfileId(oppId,
		 * profileId);
		 */
		OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
				.getRecriutmentsByOpportunitysId(oppId, recriutmentId);

		List<CandidateViewMapper> list = candidateService.filterListOfCandidateBasedOnRecruitmentByUserId(skill,
				userId);

		return list;
	}

	@Override
	public boolean deleteProfilesOfARecruitment(String recruitId) {
		List<RecruitmentCandidateLink> recruitmentCandidateLinkList = candidateLinkRepository
				.getCandidateList(recruitId);

		OpportunityRecruitDetails oppRecruitDetails = recruitmentOpportunityDetailsRepository
				.getRecruitmentDetailsByRecruitId(recruitId);
		if (null != oppRecruitDetails) {
			oppRecruitDetails.setLiveInd(false);
			recruitmentOpportunityDetailsRepository.save(oppRecruitDetails);

			for (RecruitmentCandidateLink recruitmentCandidateLink : recruitmentCandidateLinkList) {

				/* Recruit candidate LINK */
				RecruitmentCandidateLink updaterecruitmentCandidateLink = recruitmentCandidateLinkRepository
						.getRecruitmentCandidateLinkByRecruitId(recruitId, recruitmentCandidateLink.getProfileId());
				if (null != recruitmentCandidateLink) {
					recruitmentCandidateLink.setLive_ind(false);
					recruitmentCandidateLinkRepository.save(updaterecruitmentCandidateLink);
				}

				/* recruit profile link details */
				RecruitProfileLinkDetails recruitProfileLinkDetails = recruitProfileDetailsRepository
						.getprofiledetails(recruitmentCandidateLink.getProfileId());
				if (null != recruitProfileLinkDetails) {
					recruitProfileLinkDetails.setLive_ind(false);
					recruitProfileDetailsRepository.save(recruitProfileLinkDetails);
				}

				/* recruit profile status link details */
				RecruitProfileStatusLink recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
						.getRecruitProfileStatusLinkByProfileId(recruitmentCandidateLink.getProfileId());
				if (null != recruitProfileStatusLink) {
					recruitProfileStatusLink.setLiveInd(false);
					recruitmentProfileStatusLinkRepository.save(recruitProfileStatusLink);
				}

				/* RecruitmentPublishDetails details */
				RecruitmentPublishDetails recPublishDetails = recruitmentPublishRepository
						.getRecruitmentPublishDetails(recruitId);
				if (null != recPublishDetails) {
					recPublishDetails.setLiveInd(false);
					recruitmentPublishRepository.save(recPublishDetails);
				}
				/* recruit setSkill link details */
				RecruitmentSkillsetLink recruitSkillLink = recruitmentSkillsetLinkRepository
						.getRecruitmentSkillSetLinkDetailsByRecruitmentId(recruitId);
				if (null != recruitSkillLink) {
					recruitSkillLink.setLive_ind(false);
					recruitmentSkillsetLinkRepository.save(recruitSkillLink);

				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String updateRecriutment(RecruitmentOpportunityMapper recruitmentOpportunityMapper) {
		System.out.println("start....1");
		OpportunityRecruitDetails recruitmentOpportunityDetails = recruitmentOpportunityDetailsRepository
				.getRecriutmentsByOpportunitysId(recruitmentOpportunityMapper.getOpportunityId(),
						recruitmentOpportunityMapper.getRecruitmentId());
		System.out.println("recruitmentOpportunityDetails" + recruitmentOpportunityDetails.toString());
		System.out.println("start....2");
		if (null != recruitmentOpportunityDetails) {
			if (null != recruitmentOpportunityMapper.getJobOrder())
				recruitmentOpportunityDetails.setJob_order(recruitmentOpportunityMapper.getJobOrder());
			if (null != recruitmentOpportunityMapper.getRecruitmentId())
				recruitmentOpportunityDetails.setRecruitment_id(recruitmentOpportunityMapper.getRecruitmentId());
			if (null != recruitmentOpportunityMapper.getOpportunityId())
				recruitmentOpportunityDetails.setOpportunity_id(recruitmentOpportunityMapper.getOpportunityId());
			if (null != recruitmentOpportunityMapper.getRequirementName())
				recruitmentOpportunityDetails.setName(recruitmentOpportunityMapper.getRequirementName());
			if (null != recruitmentOpportunityMapper.getCurrency())
				recruitmentOpportunityDetails.setCurrency(recruitmentOpportunityMapper.getCurrency());
			if (0 != recruitmentOpportunityMapper.getBilling())
				recruitmentOpportunityDetails.setBilling(recruitmentOpportunityMapper.getBilling());
			if (null != recruitmentOpportunityMapper.getSponserId())
				recruitmentOpportunityDetails.setSponser_id(recruitmentOpportunityMapper.getSponserId());
			if (null != recruitmentOpportunityMapper.getDescription())
				recruitmentOpportunityDetails.setDescription(recruitmentOpportunityMapper.getDescription());
			if (null != recruitmentOpportunityMapper.getType())
				recruitmentOpportunityDetails.setType(recruitmentOpportunityMapper.getType());
			// recruitmentOpportunityDetails.setRecruitment_process_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
			if (0 != recruitmentOpportunityMapper.getNumber())
				recruitmentOpportunityDetails.setNumber(recruitmentOpportunityMapper.getNumber());
			// recruitmentOpportunityDetails.setRecruiter_id(recruitmentOpportunityMapper.getRecruiterId());;
			if (null != recruitmentOpportunityMapper.getCategory())
				recruitmentOpportunityDetails.setCategory(recruitmentOpportunityMapper.getCategory());
			if (null != recruitmentOpportunityMapper.getExperience())
				recruitmentOpportunityDetails.setExperience(recruitmentOpportunityMapper.getExperience());
			if (null != recruitmentOpportunityMapper.getWorkPreference())
				recruitmentOpportunityDetails.setWorkPreferance(recruitmentOpportunityMapper.getWorkPreference());

			if (null != recruitmentOpportunityMapper.getDepartment())
				recruitmentOpportunityDetails.setDepartment(recruitmentOpportunityMapper.getDepartment());

			if (null != recruitmentOpportunityMapper.getRole())
				recruitmentOpportunityDetails.setRole(recruitmentOpportunityMapper.getRole());

			if (null != recruitmentOpportunityMapper.getWorkType())
				recruitmentOpportunityDetails.setWorkType(recruitmentOpportunityMapper.getWorkType());

			if (null != recruitmentOpportunityMapper.getAvilableDate())
				try {
					recruitmentOpportunityDetails.setAvailable_date(
							Utility.getDateFromISOString(recruitmentOpportunityMapper.getAvilableDate()));
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (null != recruitmentOpportunityMapper.getEndDate())
				try {
					recruitmentOpportunityDetails
							.setEnd_date(Utility.getDateFromISOString(recruitmentOpportunityMapper.getEndDate()));
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (null != recruitmentOpportunityMapper.getCloseByDate())
				try {
					recruitmentOpportunityDetails.setCloseByDate(
							Utility.getDateFromISOString(recruitmentOpportunityMapper.getCloseByDate()));
				} catch (Exception e) {
					e.printStackTrace();
				}

			recruitmentOpportunityDetailsRepository.save(recruitmentOpportunityDetails);
			System.out.println("start....3");
			/* update into RecruitmentRecruiterLink */
			if (null != recruitmentOpportunityMapper.getRecruitersId()
					&& !recruitmentOpportunityMapper.getRecruitersId().isEmpty()) {

				List<RecruitmentRecruiterLink> recruiterLinkList = recruitmentRecruiterLinkRepository
						.getRecruitmentRecruiterLinkByRecruitmentId(recruitmentOpportunityMapper.getRecruitmentId());
				System.out.println("recruiterLinkList>>" + recruiterLinkList.toString());
				// for (RecruitmentRecruiterLink recruitmentRecruiterLink : recruiterLinkList) {
				recruitmentRecruiterLinkRepository.deleteAll(recruiterLinkList);
				// }
				for (String recruiterId : recruitmentOpportunityMapper.getRecruitersId()) {
					RecruitmentRecruiterLink recruitmentRecruiterLink = new RecruitmentRecruiterLink();
					recruitmentRecruiterLink.setRecruitmentId(recruitmentOpportunityMapper.getRecruitmentId());
					recruitmentRecruiterLink.setRecruiterId(recruiterId);
					recruitmentRecruiterLink.setCreationDate(new Date());
					recruitmentRecruiterLink.setLiveInd(true);

					recruitmentRecruiterLinkRepository.save(recruitmentRecruiterLink);
				}
			}

			if (null != recruitmentOpportunityMapper.getAddress()) {
				List<AddressMapper> addressList = recruitmentOpportunityMapper.getAddress();

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
						System.out.println("AddressId::" + addressRepository.save(newAddressDetailss).getAddressId());
					}
				}
			}

			/* update into RecruitmentPartnerLink */
			if (null != recruitmentOpportunityMapper.getPartnerId()
					&& !recruitmentOpportunityMapper.getPartnerId().isEmpty()) {
				List<RecruitmentPartnerLink> recruitmentPartnerLinkList = recruitmentPartnerLinkRepository
						.findByRecruitmentIdAndLiveInd(recruitmentOpportunityMapper.getRecruitmentId(), true);
				recruitmentPartnerLinkRepository.deleteAll(recruitmentPartnerLinkList);

				System.out.println("PartnerList>>>" + recruitmentPartnerLinkList.toString());

				for (String partnerId : recruitmentOpportunityMapper.getPartnerId()) {
					RecruitmentPartnerLink recruitmentPartnerLink = new RecruitmentPartnerLink();
					recruitmentPartnerLink.setRecruitmentId(recruitmentOpportunityMapper.getRecruitmentId());
					recruitmentPartnerLink.setPartnerId(partnerId);
					recruitmentPartnerLink.setCreationDate(new Date());
					recruitmentPartnerLink.setLiveInd(true);
					recruitmentPartnerLinkRepository.save(recruitmentPartnerLink);
				}
			}

			if (recruitmentOpportunityMapper.getOpportunityId() != null
					|| recruitmentOpportunityMapper.getOpportunityId() != "") {
				OpportunityDetails oppDetails = opportunityDetailsRepository
						.getopportunityDetailsById(recruitmentOpportunityMapper.getOpportunityId());
				CustomerRecruitUpdate dbCustomerRecruitUpdate = customerRecruitUpdateRepository
						.findByCustomerId(oppDetails.getCustomerId());
				System.out.println("customerId=" + oppDetails.getCustomerId());
				if (dbCustomerRecruitUpdate != null) {
					dbCustomerRecruitUpdate.setCustomerId(oppDetails.getCustomerId());
					dbCustomerRecruitUpdate.setUpdatedDate(new Date());
					dbCustomerRecruitUpdate.setRecruitmentId(recruitmentOpportunityMapper.getRecruitmentId());
					if (recruitmentOpportunityMapper.getSponserId() != null
							|| recruitmentOpportunityMapper.getSponserId() != "") {
						dbCustomerRecruitUpdate.setContactId(recruitmentOpportunityMapper.getSponserId());
						dbCustomerRecruitUpdate.setContactUpdatedOn(new Date());
					}
					customerRecruitUpdateRepository.save(dbCustomerRecruitUpdate);
				} else {
					CustomerRecruitUpdate cusRecruitUpdate = new CustomerRecruitUpdate();
					cusRecruitUpdate.setCustomerId(oppDetails.getCustomerId());
					cusRecruitUpdate.setUpdatedDate(new Date());
					cusRecruitUpdate.setRecruitmentId(recruitmentOpportunityMapper.getRecruitmentId());
					if (recruitmentOpportunityMapper.getSponserId() != null
							|| recruitmentOpportunityMapper.getSponserId() != "") {
						cusRecruitUpdate.setContactId(recruitmentOpportunityMapper.getSponserId());
						cusRecruitUpdate.setContactUpdatedOn(new Date());
					}
					customerRecruitUpdateRepository.save(cusRecruitUpdate);
				}
			}
		}
		return recruitmentOpportunityMapper.getRecruitmentId();
	}

	@Override
	public RecruitmentOpportunityMapper getRecriutmentListByOppIdandRecruitId(String opportunityId,
			String recruitmentId, String orgId) {
		OpportunityRecruitDetails recruitmentOpportunityDetails = recruitmentOpportunityDetailsRepository
				.getRecriutmentsByOpportunitysId(opportunityId, recruitmentId);
		// GlobalProcessDetails globalProcessDetails =processDetailsRepository.
		// getProcessDetailsByProcessId(recruitmentOpportunityDetails.getRecruitment_process_id());
		// System.out.println("recruit
		// details======"+recruitmentOpportunityDetails.toString());
		// System.out.println("processsId====="+recruitmentOpportunityDetails.getRecruitment_process_id());
		// System.out.println("process
		// Name===="+globalProcessDetails.getProcess_name());
		RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();

		if (null != recruitmentOpportunityDetails) {
			recruitmentOpportunityMapper.setOpportunityId(opportunityId);
			recruitmentOpportunityMapper.setRecruitmentId(recruitmentId);
			recruitmentOpportunityMapper.setRequirementName(recruitmentOpportunityDetails.getName());
			if (!StringUtils.isEmpty(recruitmentOpportunityDetails.getSponser_id())) {
				ContactDetails contactDetails = contactRepository
						.getContactDetailsById(recruitmentOpportunityDetails.getSponser_id());
				String middleName = " ";
				String lastName = " ";
				if (null != contactDetails.getMiddle_name()) {

					middleName = contactDetails.getMiddle_name();
				}
				if (null != contactDetails.getLast_name()) {

					lastName = contactDetails.getLast_name();
				}
				recruitmentOpportunityMapper
						.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
				recruitmentOpportunityMapper.setSponserId(recruitmentOpportunityDetails.getSponser_id());
			}
			recruitmentOpportunityMapper
					.setRecruitmentProcessId(recruitmentOpportunityDetails.getRecruitment_process_id());
			recruitmentOpportunityMapper
					.setProcessName(getProcessName(recruitmentOpportunityDetails.getRecruitment_process_id()));
			recruitmentOpportunityMapper
					.setStageList(getActiveStagesOfProcess(recruitmentOpportunityDetails.getRecruitment_process_id()));
			recruitmentOpportunityMapper.setType(recruitmentOpportunityDetails.getType());
			recruitmentOpportunityMapper.setBilling(recruitmentOpportunityDetails.getBilling());
			recruitmentOpportunityMapper.setCurrency(recruitmentOpportunityDetails.getCurrency());
			recruitmentOpportunityMapper.setWorkPreference(recruitmentOpportunityDetails.getWorkPreferance());
			recruitmentOpportunityMapper.setDescription(recruitmentOpportunityDetails.getDescription());
			recruitmentOpportunityMapper.setJobOrder(recruitmentOpportunityDetails.getJob_order());
			recruitmentOpportunityMapper.setCategory(recruitmentOpportunityDetails.getCategory());
			recruitmentOpportunityMapper.setExperience(recruitmentOpportunityDetails.getExperience());
			recruitmentOpportunityMapper.setNumber(recruitmentOpportunityDetails.getNumber());
			recruitmentOpportunityMapper.setWorkType(recruitmentOpportunityDetails.getWorkType());

			if (null != recruitmentOpportunityDetails.getAvailable_date()) {
				recruitmentOpportunityMapper
						.setAvilableDate(Utility.getISOFromDate(recruitmentOpportunityDetails.getAvailable_date()));
			} else {
				recruitmentOpportunityMapper.setAvilableDate("");
			}
			if (null != recruitmentOpportunityDetails.getEnd_date()) {

				recruitmentOpportunityMapper
						.setEndDate(Utility.getISOFromDate(recruitmentOpportunityDetails.getEnd_date()));
			} else {
				recruitmentOpportunityMapper.setEndDate("");
			}
			if (null != recruitmentOpportunityDetails.getCloseByDate()) {
				recruitmentOpportunityMapper
						.setCloseByDate(Utility.getISOFromDate(recruitmentOpportunityDetails.getCloseByDate()));
			} else {
				recruitmentOpportunityMapper.setCloseByDate("");
			}
			if (null != recruitmentOpportunityDetails.getCreationDate()) {
				recruitmentOpportunityMapper
						.setCreationDate(Utility.getISOFromDate(recruitmentOpportunityDetails.getCreationDate()));
			} else {
				recruitmentOpportunityMapper.setCreationDate("");
			}
			recruitmentOpportunityMapper.setSkillSetList(candidateService.getSkillSetOfSkillLibery(orgId));
			List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
					.getRecruitmentSkillSetLinkByRecruitmentId(recruitmentOpportunityDetails.getRecruitment_id());
			if (recruitmentSkillsetLink.size() != 0) {
				recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

			}
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeByUserId(recruitmentOpportunityDetails.getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = " ";

				if (null != employeeDetails.getLastName()) {

					lastName = employeeDetails.getLastName();
				}
				if (null != employeeDetails.getMiddleName()) {

					middleName = employeeDetails.getMiddleName();
					recruitmentOpportunityMapper
							.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {
					recruitmentOpportunityMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
				}
			}

			List<RecruitmentAddressLink> recruitmentAddressLink = recruitmentAddressLinkRepository
					.getAddressListByRecruitmentId(recruitmentId);
			List<AddressMapper> addressList = new ArrayList<AddressMapper>();
			if (null != recruitmentAddressLink && !recruitmentAddressLink.isEmpty()) {

				for (RecruitmentAddressLink recruitmentAddressLink1 : recruitmentAddressLink) {
					AddressDetails addressDetails = addressRepository
							.getAddressDetailsByAddressId(recruitmentAddressLink1.getAddressId());

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
			recruitmentOpportunityMapper.setAddress(addressList);

			List<RecruitmentRecruiterLink> recruiterList = recruitmentRecruiterLinkRepository
					.getRecruitmentRecruiterLinkByRecruitmentId(recruitmentOpportunityDetails.getRecruitment_id());
			if (null != recruiterList && !recruiterList.isEmpty()) {
				List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
				for (RecruitmentRecruiterLink recruiter : recruiterList) {
					EmployeeMapper mapper = new EmployeeMapper();
					EmployeeDetails emp = employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
					// System.out.println("name....." + emp.getFullName());
					mapper.setFullName(emp.getFullName());
					mapper.setEmployeeId(recruiter.getRecruiterId());

					resultList.add(mapper);
				}
				recruitmentOpportunityMapper.setRecruiterList(resultList);
			}

			List<RecruitmentPartnerLink> recruitmentPartnerLinkList = recruitmentPartnerLinkRepository
					.findByRecruitmentIdAndLiveInd(recruitmentOpportunityDetails.getRecruitment_id(), true);
			if (null != recruitmentPartnerLinkList && !recruitmentPartnerLinkList.isEmpty()) {
				List<PartnerMapper> partnerResultList = new ArrayList<PartnerMapper>();
				for (RecruitmentPartnerLink partner : recruitmentPartnerLinkList) {
					PartnerMapper mapper = new PartnerMapper();
					System.out.println("partner::::::::::" + partner.getPartnerId());
					System.out.println("recID:::::::::::" + partner.getRecruitmentId());
					PartnerDetails dbPartner = partnerDetailsRepository.getPartnerDetailsById(partner.getPartnerId());
					mapper.setPartnerName(dbPartner.getPartnerName());
					mapper.setPartnerId(dbPartner.getPartnerId());

					partnerResultList.add(mapper);
				}
				recruitmentOpportunityMapper.setPartnerList(partnerResultList);
			}
		}
		return recruitmentOpportunityMapper;
	}

	@Override
	public String updateStatusOfRecrutment(RecruitmentOpportunityMapper recruitmentOpportunityMapper) {

		RecruitProfileStatusLink recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
				.getRecruitProfileStatusLink(recruitmentOpportunityMapper.getCandidateId(),
						recruitmentOpportunityMapper.getProfileId());
		// String status = null;
		// String message = null;
		if (null != recruitProfileStatusLink) {

			recruitProfileStatusLink.setLiveInd(false);
			recruitmentProfileStatusLinkRepository.save(recruitProfileStatusLink);

			RecruitProfileStatusLink newRecruitProfileStatusLink = new RecruitProfileStatusLink();

			if (recruitmentOpportunityMapper.isApproveInd()) {
				newRecruitProfileStatusLink.setApproveInd(recruitmentOpportunityMapper.isApproveInd());
				newRecruitProfileStatusLink.setOfferDate(new Date());
			}

			if (recruitmentOpportunityMapper.isRejectInd()) {
				newRecruitProfileStatusLink.setRejectInd(recruitmentOpportunityMapper.isRejectInd());
				newRecruitProfileStatusLink.setRejectDate(new Date());
			}
			newRecruitProfileStatusLink.setRecruitmentId(recruitmentOpportunityMapper.getRecruitmentId());
			newRecruitProfileStatusLink.setProfileId(recruitmentOpportunityMapper.getProfileId());
			newRecruitProfileStatusLink.setCandidateId(recruitmentOpportunityMapper.getCandidateId());
			newRecruitProfileStatusLink.setLiveInd(true);
			newRecruitProfileStatusLink.setUserId(recruitmentOpportunityMapper.getTagUserId());
			recruitmentProfileStatusLinkRepository.save(newRecruitProfileStatusLink);
		} else {
			RecruitProfileStatusLink newRecruitProfileStatusLink = new RecruitProfileStatusLink();

			if (recruitmentOpportunityMapper.isApproveInd()) {
				newRecruitProfileStatusLink.setApproveInd(recruitmentOpportunityMapper.isApproveInd());
				newRecruitProfileStatusLink.setOfferDate(new Date());
			}

			if (recruitmentOpportunityMapper.isRejectInd()) {
				newRecruitProfileStatusLink.setRejectInd(recruitmentOpportunityMapper.isRejectInd());
				newRecruitProfileStatusLink.setRejectDate(new Date());
			}
			newRecruitProfileStatusLink.setRecruitmentId(recruitmentOpportunityMapper.getRecruitmentId());
			newRecruitProfileStatusLink.setProfileId(recruitmentOpportunityMapper.getProfileId());
			newRecruitProfileStatusLink.setCandidateId(recruitmentOpportunityMapper.getCandidateId());
			newRecruitProfileStatusLink.setLiveInd(true);
			newRecruitProfileStatusLink.setUserId(recruitmentOpportunityMapper.getTagUserId());
			recruitmentProfileStatusLinkRepository.save(newRecruitProfileStatusLink);

		}
		/*
		 * CandidateDetails candidateDetails =
		 * candidateDetailsRepository.getcandidateDetailsById(
		 * recruitmentOpportunityMapper.getCandidateId()); OpportunityRecruitDetails
		 * oppRecruitDetails =
		 * recruitmentOpportunityDetailsRepository.getRecruitmentDetailsByRecruitId(
		 * recruitmentOpportunityMapper.getRecruitmentId()); OpportunityDetails
		 * oppDetails =
		 * opportunityDetailsRepository.getOpportunityDetailsByOpportunityId(
		 * oppRecruitDetails.getOpportunity_id()); String customerName = "";
		 * if(!StringUtils.isEmpty(oppDetails.getCustomerId())){ customerName =
		 * customerRepository.findByCustomerId(oppDetails.getCustomerId()).getName(); }
		 * String role = ""; if(!StringUtils.isEmpty(oppRecruitDetails.getRole())) {
		 * role=roleTypeRepository.findByRoleTypeId(oppRecruitDetails.getRole()).
		 * getRoleType(); } RecruitProfileLinkDetails recruitProfileLinkDetails =
		 * recruitProfileDetailsRepository.
		 * getprofiledetails(recruitmentOpportunityMapper.getProfileId()); String
		 * recruiterOwner ="";
		 * if(!StringUtils.isEmpty(recruitProfileLinkDetails.getRecruitOwner())){
		 * recruiterOwner =
		 * employeeRepository.getEmployeesByuserId(recruitProfileLinkDetails.
		 * getRecruitOwner()).getFullName(); } String OrgName =
		 * organizationRepository.getOrganizationDetailsById(oppDetails.getOrgId()).
		 * getName();
		 *
		 * if(!StringUtils.isEmpty(recruitmentOpportunityMapper.getCandidateId())){
		 * String fromEmail = "support@innoverenit.com"; String to =
		 * candidateDetails.getEmailId(); String subject = null ; String message = null;
		 * if(recruitmentOpportunityMapper.isApproveInd()==true) { subject =
		 * "Congratulations!!"; message = "<div><p>Dear" +
		 * candidateDetails.getFullName()
		 * +"</p></div>  <br> <div>    <p>Congratulations!!</p>  </div>" + "<div> " +
		 * "<p> We are happy to inform you that you have been selected by " +
		 * customerName + " as a " +role +
		 * " More details about the offer will be shared with you soon.</p></div>" +
		 * " <div><p> Warm Regards " +recruiterOwner+ "<br>" +OrgName+ "</p>  </div>"; }
		 * if(recruitmentOpportunityMapper.isRejectInd()==true) { subject = "Sorry";
		 * message = "<div><p>Dear" + candidateDetails.getFullName()
		 * +"</p></div>  <br>  <div> " +
		 * "<p> We regret to inform you that you have not been selected by " +
		 * customerName + " as a " +role + " For more details contact " + customerName
		 * +"</p></div>" + " <div><p> Warm Regards " +recruiterOwner+ "<br>" +OrgName+
		 * "</p>  </div>"; } System.out.println("MSG>>"+message); String serverUrl
		 * ="https://develop.tekorero.com/kite/email/send"; HttpHeaders headers = new
		 * HttpHeaders(); headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		 * MultiValueMap<String, Object> body= new LinkedMultiValueMap<>();
		 * body.add("fromEmail", fromEmail); body.add("message", message);
		 * body.add("subject",subject); body.add("toEmail", to);
		 * HttpEntity<MultiValueMap<String, Object>> requestEntity= new
		 * HttpEntity<>(body, headers); RestTemplate restTemplate = new RestTemplate();
		 * ResponseEntity<String> response = restTemplate .postForEntity(serverUrl,
		 * requestEntity, String.class); }
		 */

		return recruitmentOpportunityMapper.getRecruitmentId();
	}

	@Override
	public RecruitmentOpportunityMapper getRecruitProfileStatusLinkByProfileId(String profileId) {
		RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();

		RecruitProfileStatusLink recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
				.getRecruitProfileStatusLinkByProfileId(profileId);

		if (null != recruitProfileStatusLink) {
			recruitmentOpportunityMapper.setCandidateId(recruitProfileStatusLink.getCandidateId());
			recruitmentOpportunityMapper.setProfileId(profileId);
			recruitmentOpportunityMapper.setRecruitmentId(recruitProfileStatusLink.getRecruitmentId());
			recruitmentOpportunityMapper.setRejectInd(recruitProfileStatusLink.isRejectInd());
			recruitmentOpportunityMapper.setApproveInd(recruitProfileStatusLink.isApproveInd());
		}
		return recruitmentOpportunityMapper;
	}

	@Override
	public HashMap getRecruitDashBoardRecordByuserIdAndDateRange(String userId, String startDate, String endDate) {
		int openRequirement = 0;
		int numOfOfferCandidate = 0;
		int numOfOnBoardCandidate = 0;
		int openPosition = 0;

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<RecruitmentRecruiterLink> recruterList = recruitmentRecruiterLinkRepository
				.getRecruitmentRecruiterLinkByRecruitmentId(userId);
		// System.out.println("recruterList....." + recruterList.size());

		/*
		 * Date currentDate = new Date();
		 *
		 * List<RecruitmentRecruiterLink> sortedOpportunityList = recruterList.stream()
		 * .filter(list ->
		 * Utility.removeTime(list.getCreationDate()).compareTo(Utility.removeTime(
		 * currentDate)) == 0) .collect(Collectors.toList()); todayRequirement =
		 * sortedOpportunityList.size();
		 */

		if (null != recruterList && !recruterList.isEmpty()) {
			for (RecruitmentRecruiterLink recruitmentRecruiterLink : recruterList) {

				OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
						.getRecruitmentListByRecruitIdAndDateRange(recruitmentRecruiterLink.getRecruitmentId(),
								start_date, end_date);
				if (opportunityRecruitDetails.isCloseInd() == false) {
					List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
							.getProfileDetailByRecruitmentIdAndonBoardIndAndDateRange(
									recruitmentRecruiterLink.getRecruitmentId(), start_date, end_date);

					int profileSize = profileList.size();
					int positionSize = (int) opportunityRecruitDetails.getNumber();
					if (profileSize < positionSize) {
						openRequirement++;
						openPosition += opportunityRecruitDetails.getNumber();
					}
				}
			}
		}
		//
		/*
		 * if (null != recruterList) { for (RecruitmentRecruiterLink
		 * recruitmentRecruiterLink : recruterList) { List<RecruitmentCandidateLink>
		 * candidateList = recruitmentCandidateLinkRepository
		 * .getCandidateList(recruitmentRecruiterLink.getRecruitmentId());
		 * numOfAllCandidate += candidateList.size(); } }
		 */
		if (null != recruterList) {
			for (RecruitmentRecruiterLink recruitmentRecruiterLink : recruterList) {
				List<RecruitProfileStatusLink> offerCandidateList = recruitmentProfileStatusLinkRepository
						.getRecruitProfileStatusLinkByRecruitmentIdOnDateRange(
								recruitmentRecruiterLink.getRecruitmentId(), start_date, end_date);
				numOfOfferCandidate += offerCandidateList.size();
			}
		}
		if (null != recruterList) {
			for (RecruitmentRecruiterLink recruitmentRecruiterLink : recruterList) {
				List<RecruitProfileLinkDetails> onboardedCandidateList = recruitmentProfileDetailsRepository
						.getProfileDetailByRecruitmentIdAndonBoardIndAndDateRange(
								recruitmentRecruiterLink.getRecruitmentId(), start_date, end_date);
				numOfOnBoardCandidate += onboardedCandidateList.size();
			}
		}
		HashMap map = new HashMap();
		map.put("openRequirement", openRequirement);
		map.put("openPosition", openPosition);
		map.put("selectted", numOfOfferCandidate);
		map.put("onboarded", numOfOnBoardCandidate);

		return map;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getOpenRecruitmentByuserIdAndDateRange(String recruiterId,
			String startDate, String endDate) {

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();

		List<RecruitmentRecruiterLink> oprecruiterList = recruitmentRecruiterLinkRepository
				.getRecruitmentRecruiterLinkByRecruitmentIdAndDateRange(recruiterId, start_date, end_date);
		if (null != oprecruiterList && !oprecruiterList.isEmpty()) {
			return oprecruiterList.stream().map(recruitmentRecruiterLink -> {
				List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
						.getProfileDetailByRecruitmentId(recruitmentRecruiterLink.getRecruitmentId());
				System.out.println("profile Lits2@@@@@@@@@" + profileList.size());
				if (null != profileList && !profileList.isEmpty()) {
					profileList.stream().map(li -> {
						OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
								.getRecruitmentDetailsByRecruitId(recruitmentRecruiterLink.getRecruitmentId());
						int profileSize = profileList.size();
						int positionSize = (int) opportunityRecruitDetails.getNumber();
						if (profileSize < positionSize) {
							RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
							recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
							recruitmentOpportunityMapper.setRequirementName(opportunityRecruitDetails.getName());
							recruitmentOpportunityMapper
									.setRecruitmentId(opportunityRecruitDetails.getRecruitment_id());
							recruitmentOpportunityMapper.setNumber(opportunityRecruitDetails.getNumber());
							recruitmentOpportunityMapper.setCreationDate(
									Utility.getISOFromDate(opportunityRecruitDetails.getCreationDate()));
							recruitmentOpportunityMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
							recruitmentOpportunityMapper.setExperience(opportunityRecruitDetails.getExperience());
							recruitmentOpportunityMapper.setLocation(opportunityRecruitDetails.getLocation());

							recruitmentOpportunityMapper.setAvilableDate(
									Utility.getISOFromDate(opportunityRecruitDetails.getAvailable_date()));
							recruitmentOpportunityMapper.setBilling(opportunityRecruitDetails.getBilling());
							recruitmentOpportunityMapper.setStageList(
									getActiveStagesOfProcess(opportunityRecruitDetails.getRecruitment_process_id()));

							if (!StringUtils.isEmpty(opportunityRecruitDetails.getSponser_id())) {

								ContactDetails contactDetails = contactRepository
										.getContactDetailsById(opportunityRecruitDetails.getSponser_id());
								String middleName = " ";
								String lastName = " ";
								if (null != contactDetails.getMiddle_name()) {

									middleName = contactDetails.getMiddle_name();
								}
								if (null != contactDetails.getLast_name()) {

									lastName = contactDetails.getLast_name();
								}
								recruitmentOpportunityMapper.setSponserName(
										contactDetails.getFirst_name() + " " + middleName + " " + lastName);
							}

							List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
									.getCandidateList(li.getRecruitment_id());
							if (null != recruitmentCandidateLink) {
								recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
							}

							List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
									.getRecruitProfileStatusLinkByRecruitmentId(li.getRecruitment_id());
							if (null != recruitProfileStatusLink) {
								recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
							}

							List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
									.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());
							if (null != profileLinkDetailsList) {
								recruitmentOpportunityMapper.setOnBoardNo(profileLinkDetailsList.size());
							}

							/* Add skill set */
							// recruitmentOpportunityMapper.setSkillSetList(candidateService.getSkillSetOfCandidatesOfUser(recruitmentOpportunityMapper.getOrgId()));

							List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
									.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
							if (recruitmentSkillsetLink.size() != 0) {
								recruitmentOpportunityMapper
										.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

							}
							mapperList.add(recruitmentOpportunityMapper);
						}
						return mapperList;
					}).collect(Collectors.toList());
				}
				return mapperList;
			}).flatMap(l -> l.stream()).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getOfferedCandidateByuserIdAndDateRange(String userId, String startDate,
			String endDate) {

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateFromISOString(endDate);
			start_date = Utility.getDateFromISOString(startDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		List<OpportunitySalesUserLink> salesList = opportunitySalesUserRepository
				.getemployeeListByEmployeeIdAndDateRange(userId, start_date, end_date);
		if (null != salesList) {
			return salesList.stream().map(opportunitySalesUserLink -> {
				List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
						.getRecriutmentsByOpportunityId(opportunitySalesUserLink.getOpportunity_id());

				if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {
					recruitDetailsList.stream().map(li -> {

						List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());

						RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
						recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
						recruitmentOpportunityMapper.setRequirementName(li.getName());
						recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
						recruitmentOpportunityMapper.setNumber(li.getNumber());
						recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
						recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
						recruitmentOpportunityMapper.setExperience(li.getExperience());
						recruitmentOpportunityMapper.setLocation(li.getLocation());
						recruitmentOpportunityMapper.setAvilableDate(Utility.getISOFromDate(li.getAvailable_date()));
						recruitmentOpportunityMapper.setBilling(li.getBilling());
						recruitmentOpportunityMapper
								.setStageList(getActiveStagesOfProcess(li.getRecruitment_process_id()));

						if (!StringUtils.isEmpty(li.getSponser_id())) {

							ContactDetails contactDetails = contactRepository.getContactDetailsById(li.getSponser_id());
							String middleName = " ";
							String lastName = " ";
							if (null != contactDetails.getMiddle_name()) {

								middleName = contactDetails.getMiddle_name();
							}
							if (null != contactDetails.getLast_name()) {

								lastName = contactDetails.getLast_name();
							}
							recruitmentOpportunityMapper
									.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
						}
						List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
								.getCandidateList(li.getRecruitment_id());
						if (null != recruitmentCandidateLink) {
							recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
						}

						List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
								.getRecruitProfileStatusLinkByRecruitmentId(li.getRecruitment_id());
						if (null != recruitProfileStatusLink) {
							recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
						}

						List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());
						if (null != profileLinkDetailsList) {
							recruitmentOpportunityMapper.setOnBoardNo(profileLinkDetailsList.size());
						}
						List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
								.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
						if (recruitmentSkillsetLink.size() != 0) {
							recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

						}
						mapperList.add(recruitmentOpportunityMapper);
						return mapperList;

					}).collect(Collectors.toList());
				}
				return mapperList;
			}).flatMap(l -> l.stream()).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getOnBoardedCandidateByuserIdAndDateRange(String recruiterId,
			String startDate, String endDate) {

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateFromISOString(endDate);
			start_date = Utility.getDateFromISOString(startDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<RecruitmentRecruiterLink> recruterList = recruitmentRecruiterLinkRepository
				.getRecruitmentRecruiterLinkByRecruitmentIdAndDateRange(recruiterId, start_date, end_date);
		if (null != recruterList) {
			for (RecruitmentRecruiterLink recruitmentRecruiterLink : recruterList) {
				List<RecruitProfileStatusLink> offerCandidateList = recruitmentProfileStatusLinkRepository
						.getRecruitProfileStatusLinkByRecruitmentId(recruitmentRecruiterLink.getRecruitmentId());
			}
		}
		return null;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getRecruitmentOfRecruiterId(String recruiterId, String orgId) {

		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();

		List<RecruitmentRecruiterLink> oprecruiterList = recruitmentRecruiterLinkRepository
				.getrecruiterListByRecruiterId(recruiterId);
		System.out.println("opprtunity recruit list@@@@@@@@" + oprecruiterList.size());

		if (null != oprecruiterList && !oprecruiterList.isEmpty()) {
			System.out.println("***************************888START88*************************************");
			for (RecruitmentRecruiterLink recruitmentRecruiterLink : oprecruiterList) {
				// System.out.println("profile Lits2@@@@@@@@@" + recruitList.size());
				// if (null != recruitList && !recruitList.isEmpty()) {
				OpportunityRecruitDetails li = recruitmentOpportunityDetailsRepository
						.getRecruitmentDetailsByRecruitId(recruitmentRecruiterLink.getRecruitmentId());
				if (null != li) {
					// recruitList.stream().map(li -> {
					RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
					recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
					recruitmentOpportunityMapper.setOpportunityId(li.getOpportunity_id());
					// recruitmentOpportunityMapper.setStageId(li.getStag());
					recruitmentOpportunityMapper.setRecruitmentProcessId(li.getRecruitment_process_id());
					recruitmentOpportunityMapper.setProcessName(getProcessName(li.getRecruitment_process_id()));
					// recruitmentOpportunityMapper.setStageName(getStageName(li.getStage_id()));
					// recruitmentOpportunityMapper.setProfileId(li.getProfile_id());
					recruitmentOpportunityMapper.setRequirementName(li.getName());
					recruitmentOpportunityMapper.setCategory(li.getCategory());
					recruitmentOpportunityMapper.setSponserId(li.getSponser_id());
					recruitmentOpportunityMapper.setDescription(li.getDescription());
					recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
					recruitmentOpportunityMapper.setNumber(li.getNumber());
					recruitmentOpportunityMapper.setBilling(li.getBilling());
					recruitmentOpportunityMapper.setCurrency(li.getCurrency());
					recruitmentOpportunityMapper.setType(li.getType());
					recruitmentOpportunityMapper.setSponserId(li.getSponser_id());

					if (!StringUtils.isEmpty(li.getSponser_id())) {

						ContactDetails contactDetails = contactRepository.getContactDetailsById(li.getSponser_id());
						String middleName = " ";
						String lastName = " ";
						if (null != contactDetails.getMiddle_name()) {

							middleName = contactDetails.getMiddle_name();
						}
						if (null != contactDetails.getLast_name()) {

							lastName = contactDetails.getLast_name();
						}
						recruitmentOpportunityMapper
								.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
					}

					List<RecruitmentRecruiterLink> recruiterList = recruitmentRecruiterLinkRepository
							.getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id());
					if (null != recruiterList && !recruiterList.isEmpty()) {
						List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
						for (RecruitmentRecruiterLink recruiter : recruiterList) {
							EmployeeMapper mapper = new EmployeeMapper();
							EmployeeDetails emp = employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
							// System.out.println("name....." + emp.getFullName());
							mapper.setFullName(emp.getFullName());
							mapper.setEmployeeId(recruiter.getRecruiterId());

							resultList.add(mapper);
						}
						recruitmentOpportunityMapper.setRecruiterList(resultList);
					}

					recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
					recruitmentOpportunityMapper.setAvilableDate(Utility.getISOFromDate(li.getAvailable_date()));
					recruitmentOpportunityMapper.setEndDate(Utility.getISOFromDate(li.getEnd_date()));
					recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcess(li.getRecruitment_process_id()));
					recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
					recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
					recruitmentOpportunityMapper.setExperience(li.getExperience());
					recruitmentOpportunityMapper.setLocation(li.getLocation());
					RecruitmentPublishDetails recruitmentPublishDetails = recruitmentPublishRepository
							.getRecruitmentPublishDetails(li.getRecruitment_id());
					if (null != recruitmentPublishDetails) {

						recruitmentOpportunityMapper.setPublishInd(true);
					} else {

						recruitmentOpportunityMapper.setPublishInd(false);
					}

//                      if (!StringUtils.isEmpty(li.getRecruiter_id())) {
//                          EmployeeDetails employeeDetails = employeeRepository
//                                  .getEmployeeDetailsByEmployeeId(li.getRecruiter_id());
//                          String middleName = " ";
//                          String lastName = " ";
//                          if (employeeDetails.getMiddleName() != null) {
//
//                              middleName = employeeDetails.getMiddleName();
//                          }
//                          if (employeeDetails.getLastName() != null) {
//
//                              lastName = employeeDetails.getLastName();
//                          }
//
//                          recruitmentOpportunityMapper.setRecruiterName(
//                                  employeeDetails.getFirstName() + " " + middleName + " " + lastName);
//                      }

					if (!StringUtils.isEmpty(li.getUserId())) {
						EmployeeDetails employee = employeeRepository.getEmployeeDetailsByEmployeeId(li.getUserId());
						recruitmentOpportunityMapper.setRecruitOwner(employee.getFullName());
					}
					/* Add skill set */
					// sk
					// recruitmentOpportunityMapper.setSkillSetList(candidateService.getSkillSetOfCandidatesOfUser(orgId));
					recruitmentOpportunityMapper.setSkillSetList(candidateService.getSkillSetOfSkillLibery(orgId));
					List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
							.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
					if (recruitmentSkillsetLink.size() != 0) {
						recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

					}

					List<RecruitmentCandidateLink> recruitmentCandidateLink = candidateLinkRepository
							.getCandidateList(li.getRecruitment_id());
					System.out.println("recruitmentCandidateLink::::::" + recruitmentCandidateLink.toString());
					if (null != recruitmentCandidateLink && !recruitmentCandidateLink.isEmpty()) {
						System.out.println("size:::::::::" + recruitmentCandidateLink.size());

						List<CandidateMapper> resultList = new ArrayList<>();
						int count = 0;
						for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
							CandidateMapper mapper = new CandidateMapper();
							CandidateDetails candidate = candidateDetailsRepository
									.getcandidateDetailsById(candidateLink.getCandidate_id());
							if (null != candidate) {
								String middleName = " ";
								String lastName = " ";
								if (null != candidate.getMiddleName()) {

									middleName = candidate.getMiddleName();
								}
								if (null != candidate.getLastName()) {

									lastName = candidate.getLastName();
								}
								mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
								mapper.setImageId(candidate.getImageId());
							}
							resultList.add(mapper);
							count++;
							if (count == 2) {
								break;
							}
							System.out.println("recruitmentCandidateLink.....2");
						}
						recruitmentOpportunityMapper.setCandidatetList(resultList);
					}
					recruitmentOpportunityMapper.setCandidateNo(candidateNo(recruitmentCandidateLink.size()));
					/*
					 * RecruitProfileStatusLink recruitProfileStatusLink =
					 * recruitmentProfileStatusLinkRepository
					 * .getRecruitProfileStatusLinkByProfileId(li.getProfile_id()); if (null !=
					 * recruitProfileStatusLink) {
					 * recruitmentOpportunityMapper.setApproveInd(recruitProfileStatusLink.
					 * isApproveInd());
					 * recruitmentOpportunityMapper.setRejectInd(recruitProfileStatusLink.
					 * isRejectInd()); }
					 */
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>3");
					mapperList.add(recruitmentOpportunityMapper);

					// }).collect(Collectors.toList());
				}
				System.out.println("444444444444444444444444444444444444444444444444444444444444444444444");
			}
			System.out.println("555555555555555555555555555555555555555555555555555555555");
		}
		System.out.println("***************************888ENDDD88*************************************");

		return mapperList;
	}

	@Override
	public List<RecruitmentProcessMapper> getProcessOfAdminInSetting(String orgId) {
		List<GlobalProcessDetails> processList = processDetailsRepository.getRecriutmentsSettingProcessByOrgId(orgId);
		List<RecruitmentProcessMapper> mapperList = new ArrayList<>();
		if (null != processList && !processList.isEmpty()) {

			processList.stream().map(li -> {

				List<GlobalProcessStageLink> stageList = recruitmentProcessStageLinkRepository
						.getRecruitmentProcessStageLinkByProcessId(li.getProcess_id());
				if (null != stageList && !stageList.isEmpty()) {
					if (stageList.size() > 2) {
						RecruitmentProcessMapper processMapper = new RecruitmentProcessMapper();

						processMapper.setRecruitmentProcessName(li.getProcess_name());
						processMapper.setRecruitmentProcessId(li.getProcess_id());
						processMapper.setPublishInd(li.isPublishInd());
						mapperList.add(processMapper);
					}
				}
				return mapperList;
			}).collect(Collectors.toList());
		}
		List<GlobalProcessDetailsDelete> globalProcessDetailsDelete = recruitmentProcessDeleteRepository
				.findByOrgId(orgId);
		if (null != globalProcessDetailsDelete && !globalProcessDetailsDelete.isEmpty()) {
			Collections.sort(globalProcessDetailsDelete,
					(p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			mapperList.get(0)
					.setUpdationDate(Utility.getISOFromDate(globalProcessDetailsDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeByUserId(globalProcessDetailsDelete.get(0).getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					mapperList.get(0).setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					mapperList.get(0).setName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		return mapperList;
	}

	@Override
	public List<EmployeeViewMapper> getAllRecruiterByDepartmentId() {
		Department department = departmentRepository.findByName("Recruiter");
		List<EmployeeDetails> list = employeeRepository.getEmployeeListByDepartmentId(department.getDepartment_id());

		List<EmployeeViewMapper> result = new ArrayList<EmployeeViewMapper>();

		for (EmployeeDetails li : list) {
			if (li == null) {

				continue;
			}
			EmployeeViewMapper employeeViewMapper = empService.getEmployeeDetails(li);
			result.add(employeeViewMapper);
		}
		return result;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getSelectedRecruitmentByuserIdAndDateRange(String recruiterId,
			String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateFromISOString(endDate);
			start_date = Utility.getDateFromISOString(startDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		List<RecruitmentRecruiterLink> recruterList = recruitmentRecruiterLinkRepository
				.getRecruitmentRecruiterLinkByRecruitmentIdAndDateRange(recruiterId, start_date, end_date);
		if (null != recruterList) {
			return recruterList.stream().map(recruitmentRecruiterLink -> {

				// for (RecruitmentRecruiterLink recruitmentRecruiterLink : recruterList) {
				List<RecruitProfileStatusLink> selectedCandidateList = recruitmentProfileStatusLinkRepository
						.getRecruitProfileStatusLinkByRecruitmentId(recruitmentRecruiterLink.getRecruitmentId());

				if (null != selectedCandidateList && !selectedCandidateList.isEmpty()) {
					selectedCandidateList.stream().map(li -> {

						OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
								.getRecruitmentDetailsByRecruitId(li.getRecruitmentId());

						RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
						recruitmentOpportunityMapper.setRecruitmentId(opportunityRecruitDetails.getRecruitment_id());
						recruitmentOpportunityMapper.setRequirementName(opportunityRecruitDetails.getName());
						recruitmentOpportunityMapper.setRecruitmentId(opportunityRecruitDetails.getRecruitment_id());
						recruitmentOpportunityMapper.setNumber(opportunityRecruitDetails.getNumber());
						recruitmentOpportunityMapper
								.setCreationDate(Utility.getISOFromDate(opportunityRecruitDetails.getCreationDate()));
						recruitmentOpportunityMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
						recruitmentOpportunityMapper.setExperience(opportunityRecruitDetails.getExperience());
						recruitmentOpportunityMapper.setLocation(opportunityRecruitDetails.getLocation());
						recruitmentOpportunityMapper
								.setAvilableDate(Utility.getISOFromDate(opportunityRecruitDetails.getAvailable_date()));
						recruitmentOpportunityMapper.setBilling(opportunityRecruitDetails.getBilling());
						recruitmentOpportunityMapper.setStageList(
								getActiveStagesOfProcess(opportunityRecruitDetails.getRecruitment_process_id()));

						if (!StringUtils.isEmpty(opportunityRecruitDetails.getSponser_id())) {

							ContactDetails contactDetails = contactRepository
									.getContactDetailsById(opportunityRecruitDetails.getSponser_id());
							String middleName = " ";
							String lastName = " ";
							if (null != contactDetails.getMiddle_name()) {

								middleName = contactDetails.getMiddle_name();
							}
							if (null != contactDetails.getLast_name()) {

								lastName = contactDetails.getLast_name();
							}
							recruitmentOpportunityMapper
									.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
						}
						List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
								.getCandidateList(li.getRecruitmentId());
						if (null != recruitmentCandidateLink) {
							recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
						}

						List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
								.getRecruitProfileStatusLinkByRecruitmentId(li.getRecruitmentId());
						if (null != recruitProfileStatusLink) {
							recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
						}

						List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitmentId());
						if (null != profileLinkDetailsList) {
							recruitmentOpportunityMapper.setOnBoardNo(profileLinkDetailsList.size());
						}
						List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
								.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitmentId());
						if (recruitmentSkillsetLink.size() != 0) {
							recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

						}

						mapperList.add(recruitmentOpportunityMapper);
						return mapperList;

					}).collect(Collectors.toList());
				}
				return mapperList;
			}).flatMap(l -> l.stream()).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public int getNoOfRecruitmentByJobOrderAndCategory(String category) {
		int noOfRecruit = 0;
		List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
				.getRecruitmentByJobOrderAndCategory(category);
		System.out.println(recruitList);
		noOfRecruit = recruitList.size();
		return noOfRecruit;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getOpenRecruitmentByJobOrderAndCategory(String category) {

		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		List<OpportunityRecruitDetails> opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
				.getRecruitmentByJobOrderAndCategory(category);
		if (null != opportunityRecruitDetails && !opportunityRecruitDetails.isEmpty()) {
			opportunityRecruitDetails.stream().map(li -> {

				RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
				recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
				recruitmentOpportunityMapper.setRequirementName(li.getName());
				recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
				recruitmentOpportunityMapper.setNumber(li.getNumber());
				recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
				recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
				recruitmentOpportunityMapper.setExperience(li.getExperience());
				recruitmentOpportunityMapper.setLocation(li.getLocation());

				/* Add skill set */
				// recruitmentOpportunityMapper.setSkillSetList(candidateService.getSkillSetOfCandidatesOfUser(recruitmentOpportunityMapper.getOrgId()));
				/*
				 * RecruitmentSkillsetLink recruitmentSkillsetLink =
				 * recruitmentSkillsetLinkRepository .getRecruitmentSkillSetLink(li.getOpp_id(),
				 * li.getProfile_id());
				 * 
				 * if (null != recruitmentSkillsetLink) {
				 * recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.
				 * getSkillName()); }
				 */

				return mapperList.add(recruitmentOpportunityMapper);
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getJobOrderListByOppId(String opportunityId) {
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();

		List<OpportunityRecruitDetails> opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
				.getRecriutmentsByOpportunityId(opportunityId);
		if (null != opportunityRecruitDetails && !opportunityRecruitDetails.isEmpty()) {
			opportunityRecruitDetails.stream().map(li -> {

				RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
				recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
				recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
				recruitmentOpportunityMapper.setRecruitmentProcessId(li.getRecruitment_process_id());
				recruitmentOpportunityMapper.setProcessName(getProcessName(li.getRecruitment_process_id()));
				recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
				return mapperList.add(recruitmentOpportunityMapper);
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getPublishRequirement() {
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		List<RecruitmentPublishDetails> list = recruitmentPublishRepository.getRecruitmentDetails();
		System.out.println("publishList>>>>>>>>>>>" + list.toString());
		if (null != list && !list.isEmpty()) {
			String orgId = list.get(0).getOrg_id();
			list.stream().map(li -> {
				OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
						.getRecruitmentDetailsByRecruitId(li.getRecruiter_id());
				String skills = null;
				RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
				System.out.println("requirementName>>>>>>>>>>" + opportunityRecruitDetails.getRecruitment_id());
				recruitmentOpportunityMapper.setRequirementName(opportunityRecruitDetails.getName());
				recruitmentOpportunityMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
				recruitmentOpportunityMapper.setDescription(opportunityRecruitDetails.getDescription());
				recruitmentOpportunityMapper.setExperience(opportunityRecruitDetails.getExperience());
				recruitmentOpportunityMapper.setLocation(opportunityRecruitDetails.getLocation());
				recruitmentOpportunityMapper.setRecruitmentId(opportunityRecruitDetails.getRecruitment_id());
				recruitmentOpportunityMapper.setSponserId(opportunityRecruitDetails.getSponser_id());
				recruitmentOpportunityMapper
						.setCreationDate(Utility.getISOFromDate(opportunityRecruitDetails.getCreationDate()));
				recruitmentOpportunityMapper.setNumber(opportunityRecruitDetails.getNumber());
				recruitmentOpportunityMapper
						.setAvilableDate(Utility.getISOFromDate(opportunityRecruitDetails.getAvailable_date()));
				recruitmentOpportunityMapper.setBilling(opportunityRecruitDetails.getBilling());
				recruitmentOpportunityMapper.setCategory(opportunityRecruitDetails.getCategory());
				recruitmentOpportunityMapper.setCountry(opportunityRecruitDetails.getCountry());
				recruitmentOpportunityMapper.setCurrency(opportunityRecruitDetails.getCurrency());
				recruitmentOpportunityMapper
						.setEndDate(Utility.getISOFromDate(opportunityRecruitDetails.getEnd_date()));
				recruitmentOpportunityMapper.setExperience(opportunityRecruitDetails.getExperience());
				recruitmentOpportunityMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
				recruitmentOpportunityMapper.setLocation(opportunityRecruitDetails.getLocation());
				recruitmentOpportunityMapper.setOpportunityId(opportunityRecruitDetails.getOpportunity_id());
				recruitmentOpportunityMapper.setWorkPreference(opportunityRecruitDetails.getWorkPreferance());
				recruitmentOpportunityMapper.setCloseInd(opportunityRecruitDetails.isCloseInd());
				recruitmentOpportunityMapper
						.setRecruitmentProcessId(opportunityRecruitDetails.getRecruitment_process_id());

				// recruitmentOpportunityMapper.setOrgId(null)
				if (!StringUtils.isEmpty(opportunityRecruitDetails.getDepartment())) {
					Department department = departmentRepository
							.getDepartmentDetailsById(opportunityRecruitDetails.getDepartment());
					if (null != department) {
						recruitmentOpportunityMapper.setDepartment(department.getDepartmentName());
					}
				}
				EmployeeDetails employeeDetails = employeeRepository
						.getEmployeeDetailsByEmployeeId(opportunityRecruitDetails.getUserId());
				if (null != employeeDetails) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						recruitmentOpportunityMapper
								.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						recruitmentOpportunityMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
					}
				}

				OpportunityDetails opportunityDetails = opportunityDetailsRepository
						.getOpportunityDetailsByOpportunityId(opportunityRecruitDetails.getOpportunity_id());
				if (null != opportunityDetails) {
					recruitmentOpportunityMapper.setOpprtunityName(opportunityDetails.getOpportunityName());
					Customer customer = customerRepository
							.getCustomerDetailsByCustomerId(opportunityDetails.getCustomerId());
					if (null != customer) {
						recruitmentOpportunityMapper.setCustomerName(customer.getName());
					}
				}

				List<RecruitmentRecruiterLink> recruiterList = recruitmentRecruiterLinkRepository
						.getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruiter_id());
				if (null != recruiterList && !recruiterList.isEmpty()) {
					List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
					for (RecruitmentRecruiterLink recruiter : recruiterList) {
						EmployeeMapper mapper = new EmployeeMapper();
						EmployeeDetails emp = employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
						// System.out.println("name....." + emp.getFullName());
						mapper.setFullName(emp.getFullName());
						mapper.setEmployeeId(recruiter.getRecruiterId());

						resultList.add(mapper);
					}
					recruitmentOpportunityMapper.setRecruiterList(resultList);
				}

				List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
						.getCandidateList(li.getRecruiter_id());
				if (null != recruitmentCandidateLink) {
					recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
				}

				List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
						.getRecruitProfileStatusLinkByRecruitmentId(li.getRecruiter_id());
				if (null != recruitProfileStatusLink) {
					recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
				}

				List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
						.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruiter_id());
				if (null != profileLinkDetailsList) {
					recruitmentOpportunityMapper.setOnBoardNo(profileLinkDetailsList.size());
				}
				if (!StringUtils.isEmpty(opportunityRecruitDetails.getSponser_id())) {

					ContactDetails contactDetails = contactRepository
							.getContactDetailsById(opportunityRecruitDetails.getSponser_id());
					String middleName = " ";
					String lastName = " ";
					if (null != contactDetails.getMiddle_name()) {

						middleName = contactDetails.getMiddle_name();
					}
					if (null != contactDetails.getLast_name()) {

						lastName = contactDetails.getLast_name();
					}
					recruitmentOpportunityMapper
							.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
				}

				recruitmentOpportunityMapper
						.setCreationDate(Utility.getISOFromDate(opportunityRecruitDetails.getCreationDate()));

				RecruitmentPublishDetails recruitmentPublishDetails = recruitmentPublishRepository
						.getRecruitmentPublishDetails(li.getRecruiter_id());
				recruitmentOpportunityMapper.setPublishInd(recruitmentPublishDetails.isLiveInd());
				recruitmentOpportunityMapper.setPingInd(recruitmentPublishDetails.isPingInd());

				List<String> skillLibery = candidateService.getSkillSetOfSkillLibery(orgId);
				System.out.println("skillLibery============" + skillLibery + orgId);
				ArrayList<String> requiredSkills = new ArrayList<>();
				String description = opportunityRecruitDetails.getDescription().replace(",", " ");
				if (!StringUtils.isEmpty(description)) {
					// List<String> descriptionList = Arrays.asList(description.split("\\s*,\\s*"));

					List<String> descriptionList = Arrays.asList(description.split(" "));
					for (String word : skillLibery) {
						for (String description1 : descriptionList) {
							if (!requiredSkills.contains(word)) {
								if (description1.equalsIgnoreCase(word)) {
									requiredSkills.add(word);
								}
							}
						}
					}
					skills = requiredSkills.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(","));
				}
				recruitmentOpportunityMapper.setSkillName(skills);
				return mapperList.add(recruitmentOpportunityMapper);

			}).collect(Collectors.toList());
		}

		return mapperList;
	}

	/*
	 * @Override public HashMap getNoOfRecruitmentByCreationDate() { // int
	 * noOfRecruitment = 0; // Millseconds in a day final long ONE_DAY_MILLI_SECONDS
	 * = (24 * 60 * 60 * 1000) - 1; // date format LocalDateTime now =
	 * LocalDateTime.now(); Date currDate = new GregorianCalendar(now.getYear(),
	 * now.getMonthValue() - 1, now.getDayOfMonth()).getTime();
	 * 
	 * long nextDayMilliSeconds = currDate.getTime() + ONE_DAY_MILLI_SECONDS; Date
	 * nextDate = new Date(nextDayMilliSeconds);
	 * 
	 * List<OpportunityRecruitDetails> recruitList =
	 * recruitmentOpportunityDetailsRepository
	 * .findByCreationDateBetweenAndLiveInd(currDate, nextDate, true);
	 * System.out.println(recruitList); HashMap map = new HashMap();
	 * map.put("recruitmentList", recruitList.size()); return map; }
	 */

	@Override
	public String saveCommission(CommissionMapper commissionMapper) {
		String commissionId = null;

		Commission commission = new Commission();
		commission.setUserId(commissionMapper.getUserId());
		commission.setOrgId(commissionMapper.getOrgId());
		commission.setCommissionPrice(commissionMapper.getCommissionPrice());
		commission.setCurrency(commissionMapper.getCurrency());
		commission.setCalculatedOn(commissionMapper.getCalculatedOn());
		commission.setComPersion(commissionMapper.getComPersion());
		commission.setType(commissionMapper.getType());

		commissionId = commissionRepository.save(commission).getCommissionId();

		return commissionId;
	}

	@Override
	public List<CommissionMapper> getCommissionByOrgId(String orgId, String type) {

		List<Commission> commissionList = commissionRepository.findByOrgIdAndType(orgId, type);
		List<CommissionMapper> mapperList = new ArrayList<>();
		if (null != commissionList && !commissionList.isEmpty()) {

			commissionList.stream().map(li -> {

				CommissionMapper commissionMapper = new CommissionMapper();

				commissionMapper.setCommissionId(li.getCommissionId());
				commissionMapper.setOrgId(li.getOrgId());
				// commissionMapper.setComPersion(li.getComPerson());
				commissionMapper.setCommissionPrice(li.getCommissionPrice());
				commissionMapper.setCurrency(li.getCurrency());
				commissionMapper.setCalculatedOn(li.getCalculatedOn());

				EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(li.getComPersion());

				commissionMapper.setComPersion(employeeDetails.getFullName());

				mapperList.add(commissionMapper);

				return mapperList;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public CommissionMapper getCommissionMapperByUserId(String userId) {

		Commission commission = commissionRepository.findByUserId(userId);
		CommissionMapper commissionMapper = new CommissionMapper();
		if (null != commission) {

			commissionMapper.setCommissionId(commission.getCommissionId());
			commissionMapper.setUserId(commission.getUserId());
			commissionMapper.setCommissionPrice(commission.getCommissionPrice());
			commissionMapper.setOrgId(commission.getOrgId());
			commissionMapper.setCurrency(commission.getCurrency());
			commissionMapper.setCalculatedOn(commission.getCalculatedOn());
			commissionMapper.setComPersion(commission.getComPersion());
		}
		return commissionMapper;
	}

	@Override
	public void updateCommission(CommissionMapper commissionMapper) {
		Commission commission = commissionRepository.findByUserId(commissionMapper.getUserId());
		if (null != commission) {
			commission.setUserId(commissionMapper.getUserId());
			commission.setOrgId(commissionMapper.getOrgId());
			commission.setCommissionPrice(commissionMapper.getCommissionPrice());
			commission.setCurrency(commissionMapper.getCurrency());
			commission.setCalculatedOn(commissionMapper.getCalculatedOn());
			commission.setComPersion(commissionMapper.getComPersion());
			commissionRepository.save(commission);
		}
	}

	@Override
	public RecruitmentOpportunityMapper updateCandidateOnboarding(
			RecruitmentOpportunityMapper recruitmentOpportunityMapper) throws IOException, TemplateException {
		RecruitmentOpportunityMapper recruitmentOpportunityMapper1 = null;

		RecruitProfileLinkDetails recruitProfileLinkDetails = recruitProfileDetailsRepository
				.getprofiledetails(recruitmentOpportunityMapper.getProfileId());
		CandidateDetails candidateDetails = candidateDetailsRepository
				.getcandidateDetailsById(recruitmentOpportunityMapper.getCandidateId());

		if (null != recruitProfileLinkDetails) {

			List<RecruitProfileLinkDetails> onboard = recruitProfileDetailsRepository
					.getProfileDetailByRecruitmentIdAndonBoardInd(recruitProfileLinkDetails.getRecruitment_id());
			OpportunityRecruitDetails recruitmentNo = recruitmentOpportunityDetailsRepository
					.getRecruitmentDetailsByRecruitId(recruitProfileLinkDetails.getRecruitment_id());
			if (onboard.size() == recruitmentNo.getNumber()) {
				return recruitmentOpportunityMapper1;
			} else {
				recruitProfileLinkDetails.setOnboard_ind(recruitmentOpportunityMapper.isOnboardInd());
				recruitProfileLinkDetails.setFinalBilling(recruitmentOpportunityMapper.getFinalBilling());
				recruitProfileLinkDetails.setOnboardCurrency(recruitmentOpportunityMapper.getOnboardCurrency());
				recruitProfileLinkDetails.setProjectName(recruitmentOpportunityMapper.getProjectName());
				recruitProfileLinkDetails.setUserId(recruitmentOpportunityMapper.getUserId());
				recruitProfileLinkDetails.setOrgId(recruitmentOpportunityMapper.getOrgId());
				recruitProfileLinkDetails.setBillableHour(recruitmentOpportunityMapper.getBillableHour());
				recruitProfileLinkDetails.setCustomerId(recruitmentOpportunityMapper.getCustomerId());
				try {
					recruitProfileLinkDetails.setOnboard_date(
							Utility.getDateFromISOString(recruitmentOpportunityMapper.getOnboardDate()));
					recruitProfileLinkDetails.setActualEndDate(
							Utility.getDateFromISOString(recruitmentOpportunityMapper.getActualEndDate()));
					if (recruitmentOpportunityMapper.isOnboardInd() == true) {
						List<RecruitProfileLinkDetails> onbordedCandidate = recruitmentProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(
										recruitProfileLinkDetails.getRecruitment_id());
						int onBoardedNo = onbordedCandidate.size() + 1;
						OpportunityRecruitDetails oppRecruitDetails = recruitmentOpportunityDetailsRepository
								.getRecruitmentDetailsByRecruitmentId(recruitProfileLinkDetails.getRecruitment_id());

//					if(null!=oppRecruitDetails) {
//						OpportunityDetails opportunityDetails = opportunityDetailsRepository
//								.getOpportunityDetailsByOpportunityId(oppRecruitDetails.getOpportunity_id());
//						if (null != opportunityDetails) {
//							recruitProfileLinkDetails.setCustomerId(opportunityDetails.getCustomerId());
//						}
//					}

						if (null != oppRecruitDetails.getEnd_date()) {
							candidateDetails
									.setAvailableDate(Utility.getDateAfterEndDate(((oppRecruitDetails.getEnd_date()))));
							candidateDetailsRepository.save(candidateDetails);
						}

						if (onBoardedNo >= oppRecruitDetails.getNumber()) {
							oppRecruitDetails.setCloseInd(true);
							recruitmentOpportunityDetailsRepository.save(oppRecruitDetails);
						}

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				recruitProfileDetailsRepository.save(recruitProfileLinkDetails);

				/*
				 * String subject = "Onboarding";
				 * System.out.println("emailId>>"+candidateDetails.getEmailId());
				 * if(!StringUtils.isEmpty(candidateDetails.getEmailId())){ String fromEmail =
				 * "support@innoverenit.com"; String to = candidateDetails.getEmailId(); String
				 * message = "<div style=' display: block; margin-top: 100px; '>" +
				 * "    <div style='  text-align: center;'> </div>" +
				 * "    <div style='  margin: 0 auto; width: 300px; background-color: #f4f4f4;  height: 250px; border: 1px #ccc solid; padding: 50px;'>"
				 * + "        <div class='box-2' style='  text-align: center;'>" +
				 * "            <h1 style='text-align: center; padding: 10px;'>Congratulation "
				 * + candidateDetails.getFirstName() + "!!! welcome to our Organization </h1>" +
				 * "            <p style='text-align: center;'>Warm Regards<br /><br />InnoverenIT,"
				 * + "               </p><br />" + "</div>" + "  </div>" + " </div>"; String
				 * serverUrl ="https://develop.tekorero.com/kite/email/send"; HttpHeaders
				 * headers = new HttpHeaders();
				 * headers.setContentType(MediaType.MULTIPART_FORM_DATA); MultiValueMap<String,
				 * Object> body= new LinkedMultiValueMap<>(); body.add("fromEmail", fromEmail);
				 * body.add("message", message); body.add("subject",subject);
				 * body.add("toEmail", to); HttpEntity<MultiValueMap<String, Object>>
				 * requestEntity= new HttpEntity<>(body, headers); RestTemplate restTemplate =
				 * new RestTemplate(); ResponseEntity<String> response = restTemplate
				 * .postForEntity(serverUrl, requestEntity, String.class);
				 * 
				 * }
				 */

				recruitmentOpportunityMapper1 = getProfileDetails(recruitmentOpportunityMapper.getProfileId());
				return recruitmentOpportunityMapper1;
			}
		}
		return recruitmentOpportunityMapper1;
	}

	private String getOnboardingEmailContent(CandidateDetails candidateDetails) throws IOException, TemplateException {
		StringWriter stringWriter = new StringWriter();
		Map<String, Object> model = new HashMap<>();
		model.put("candidateDetails", candidateDetails);
		configuration.getTemplate("onboarding.ftlh").process(model, stringWriter);
		return stringWriter.getBuffer().toString();
	}

	@Override
	public RecruitmentOpportunityMapper getRecruitmentOpportunityMapperByProfileId(String profileId) {

		RecruitProfileLinkDetails recruitProfileLinkDetails = recruitProfileDetailsRepository
				.getprofiledetails(profileId);
		RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
		if (null != recruitProfileLinkDetails) {

			recruitmentOpportunityMapper.setOnboardInd(recruitProfileLinkDetails.isOnboard_ind());
			recruitmentOpportunityMapper
					.setOnboardDate(Utility.getISOFromDate(recruitProfileLinkDetails.getOnboard_date()));
			recruitmentOpportunityMapper.setProfileId(profileId);
		}
		return recruitmentOpportunityMapper;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getDashBoardOpenRecruitmentByRecruiterId(String userId) {
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		// int profileSize =0;
		List<RecruitmentRecruiterLink> oprecruiterList = recruitmentRecruiterLinkRepository
				.getrecruiterListByRecruiterId(userId);
		System.out.println("RequirementLink=" + oprecruiterList.size());
		System.out.println("recruiterlink>>>" + oprecruiterList.toString());
		if (null != oprecruiterList && !oprecruiterList.isEmpty()) {
			// for (RecruitmentRecruiterLink recruitmentRecruiterLink : oprecruiterList) {
			return oprecruiterList.stream().map(recruitmentRecruiterLink -> {
				List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
						.getProfileDetailByRecruitmentIdAndonBoardInd(recruitmentRecruiterLink.getRecruitmentId());
				System.out.println("profileList>>>" + profileList.toString());
				RecruitmentOpportunityMapper recruitmentOpportunityMapper = null;
				if (null != oprecruiterList && !oprecruiterList.isEmpty()) {
					// oprecruiterList.stream().map(li -> {
					System.out.println("start::::::::::::1");
					System.out.println("RequirementId::::" + recruitmentRecruiterLink.getRecruitmentId());
					OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
							.getRecruitmentDetailsByRecruitId(recruitmentRecruiterLink.getRecruitmentId());
					if (opportunityRecruitDetails.isCloseInd() == false) {
						System.out.println("start::::::::::::2");
						int profileSize = profileList.size();
						int positionSize = (int) opportunityRecruitDetails.getNumber();
						if (profileSize < positionSize) {
							recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
							recruitmentOpportunityMapper
									.setRecruitmentId(opportunityRecruitDetails.getRecruitment_id());

							// recruitmentOpportunityMapper.setBilling(opportunityRecruitDetails.getBilling());
							recruitmentOpportunityMapper.setRequirementName(opportunityRecruitDetails.getName());
							recruitmentOpportunityMapper.setNumber(opportunityRecruitDetails.getNumber());
							// recruitmentOpportunityMapper.setAvilableDate(Utility.getISOFromDate(opportunityRecruitDetails.getAvailable_date()));
							recruitmentOpportunityMapper.setCreationDate(
									Utility.getISOFromDate(opportunityRecruitDetails.getCreationDate()));
							recruitmentOpportunityMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
							recruitmentOpportunityMapper.setExperience(opportunityRecruitDetails.getExperience());
							recruitmentOpportunityMapper.setLocation(opportunityRecruitDetails.getLocation());

							String role = "";
							if (!StringUtils.isEmpty(opportunityRecruitDetails.getRole())) {
								role = roleTypeRepository.findByRoleTypeId(opportunityRecruitDetails.getRole())
										.getRoleType();
							}
							recruitmentOpportunityMapper.setRole(role);

							// recruitmentOpportunityMapper.setStageName(getStageName(li.getStage_id()));m
							// recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcess(li.getRecruitment_profile_details_id()));
							// recruitmentOpportunityMapper.setSt
							recruitmentOpportunityMapper.setProcessName(
									getProcessName(opportunityRecruitDetails.getRecruitment_process_id()));
							if (!StringUtils.isEmpty(opportunityRecruitDetails.getSponser_id())) {

								ContactDetails contactDetails = contactRepository
										.getContactDetailsById(opportunityRecruitDetails.getSponser_id());
								String middleName = " ";
								String lastName = " ";
								if (null != contactDetails.getMiddle_name()) {

									middleName = contactDetails.getMiddle_name();
								}
								if (null != contactDetails.getLast_name()) {

									lastName = contactDetails.getLast_name();
								}
								recruitmentOpportunityMapper.setSponserName(
										contactDetails.getFirst_name() + " " + middleName + " " + lastName);
							}

							OpportunityDetails opportunityDetails = opportunityDetailsRepository
									.getopportunityDetailsById(opportunityRecruitDetails.getOpportunity_id());
							if (opportunityDetails != null) {
								if (!StringUtils.isEmpty(opportunityDetails.getCustomerId())) {
									Customer customer = customerRepository
											.getCustomerDetailsByCustomerId(opportunityDetails.getCustomerId());
									recruitmentOpportunityMapper.setCustomerName(customer.getName());
								}
							}

							List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
									.getCandidateList(opportunityRecruitDetails.getRecruitment_id());
							if (null != recruitmentCandidateLink) {
								recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
							}
							System.out.println("offer:::" + recruitmentCandidateLink.size());
							List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
									.getRecruitProfileStatusLinkByRecruitmentId(
											opportunityRecruitDetails.getRecruitment_id());
							if (null != recruitProfileStatusLink) {
								recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
							}
							System.out.println("closePosition::::::::" + recruitProfileStatusLink.size());
							List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
									.getProfileDetailByRecruitmentIdAndonBoardInd(
											opportunityRecruitDetails.getRecruitment_id());
							if (null != profileLinkDetailsList) {
								recruitmentOpportunityMapper.setOnBoardNo(profileLinkDetailsList.size());
							}
							List<RecruitProfileStatusLink> dropList = recruitmentProfileStatusLinkRepository
									.getRecruitDropListByRecruitmentId(opportunityRecruitDetails.getRecruitment_id());
							if (null != dropList) {
								recruitmentOpportunityMapper.setRejected(dropList.size());
							}
							recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcessWithCandidate(
									opportunityRecruitDetails.getRecruitment_process_id(),
									opportunityRecruitDetails.getRecruitment_id()));
							mapperList.add(recruitmentOpportunityMapper);
							System.out.println("____________________________");
						}
					}
					// return mapperList;
					// }).collect(Collectors.toList());
				}
				return recruitmentOpportunityMapper;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public boolean requirementExistsByJobOrder(String jobOrder) {
		OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
				.getByJobOrderDetails(jobOrder);
		if (opportunityRecruitDetails != null) {

			return true;
		}
		return false;
	}

	@Override
	public String saveWebsite(WebsiteMapper websiteMapper) {
		// String Id = null;
		List<Website> dbWebsite = websiteRepository.findByOrgId(websiteMapper.getOrgId());
		if (null != dbWebsite && dbWebsite.size() != 0) {
			String Id = null;
			dbWebsite.get(0).setUrl(websiteMapper.getUrl());
			dbWebsite.get(0).setIp(websiteMapper.getIp());
			dbWebsite.get(0).setAssignToUserId(websiteMapper.getAssignToUserId());
			dbWebsite.get(0).setUser_id(websiteMapper.getUserId());
			dbWebsite.get(0).setLastUpdatedOn(new Date());
			Id = websiteRepository.save(dbWebsite.get(0)).getWebsiteId();

			return Id;
		} else {
			String Ids = null;
			Website newWebsite = new Website();
			newWebsite.setWebsiteId(websiteMapper.getWebsiteId());
			newWebsite.setUrl(websiteMapper.getUrl());
			newWebsite.setIp(websiteMapper.getIp());
			newWebsite.setOrgId(websiteMapper.getOrgId());
			newWebsite.setAssignToUserId(websiteMapper.getAssignToUserId());
			newWebsite.setUser_id(websiteMapper.getUserId());
			newWebsite.setLastUpdatedOn(new Date());
			Ids = websiteRepository.save(newWebsite).getWebsiteId();
			return Ids;
		}

	}

	@Override
	public List<WebsiteMapper> getWebsiteListByOrgId(String orgId) {
		List<Website> websiteList = websiteRepository.findByOrgId(orgId);
		List<WebsiteMapper> mapperList = new ArrayList<>();
		if (null != websiteList && !websiteList.isEmpty()) {

			websiteList.stream().map(li -> {

				WebsiteMapper websiteMapper = new WebsiteMapper();

				websiteMapper.setWebsiteId(li.getWebsiteId());
				websiteMapper.setUrl(li.getUrl());
				websiteMapper.setIp(li.getIp());
				websiteMapper.setOrgId(li.getOrgId());
				websiteMapper.setUserId(li.getUser_id());
				websiteMapper.setAssignToUserId(li.getAssignToUserId());
				websiteMapper.setLastUpdatedOn(Utility.getISOFromDate(li.getLastUpdatedOn()));

				EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(li.getUser_id());

				if (employeeDetails != null) {

					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						websiteMapper.setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						websiteMapper.setName(employeeDetails.getFirstName() + " " + lastName);
					}

				}
				mapperList.add(websiteMapper);

				return mapperList;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getTagCandidateListByRecriutmentId(String recriutmentId) {
		List<RecruitmentOpportunityMapper> resultrList = new ArrayList<>();
		System.out.println("recId::::::::" + recriutmentId);
		List<RecruitProfileLinkDetails> profileLinkList = recruitmentProfileDetailsRepository
				.getProfileDetailByRecruitmentId(recriutmentId);
		System.out.println("profileLinkList::::::::::::::::::" + profileLinkList.size());
		if (null != profileLinkList && !profileLinkList.isEmpty()) {
			System.out.println("ppppppppppppppppppppppppppppppppppp");
			profileLinkList.stream().map(li -> {

				RecruitmentOpportunityMapper mapper = new RecruitmentOpportunityMapper();
				System.out.println("profileId::::::::::" + li.getProfile_id());
				RecruitmentCandidateLink candidateLink = recruitmentCandidateLinkRepository
						.getCandidateProfile(li.getProfile_id());

				mapper.setProfileId(li.getProfile_id());
				mapper.setStageId(li.getStage_id());
				mapper.setStageName(getStageName(li.getStage_id()));
				if (null != candidateLink) {
					System.out.println("recid====" + candidateLink.getRecruitment_id());
					System.out.println("profileid===" + candidateLink.getProfileId());
					System.out.println("candidate Id =====-=----inner0-----=--///" + candidateLink.getCandidate_id());
					if (null != candidateLink) {
						System.out
								.println("candidate Id =====-=----inner1-----=--///" + candidateLink.getCandidate_id());
						CandidateDetails candidate = candidateDetailsRepository
								.getcandidateDetailsById(candidateLink.getCandidate_id());
						if (null != candidate) {
							String middleName = " ";
							String lastName = " ";
							if (null != candidate.getMiddleName()) {

								middleName = candidate.getMiddleName();
							}
							if (null != candidate.getLastName()) {

								lastName = candidate.getLastName();
							}
							mapper.setCandidateName(candidate.getFirstName() + " " + middleName + " " + lastName);
							mapper.setCandidateBilling(candidate.getBilling());
							mapper.setCandidateId(candidate.getCandidateId());
							mapper.setCurrency(candidate.getCurrency());
							mapper.setTagUserId(candidate.getUserId());
							mapper.setAvilableDate(Utility.getISOFromDate(candidate.getAvailableDate()));

						}
//					System.out.println("candidateLink==="+candidateLink.getCandidate_id());
//					List<CandidateDocumentLink> candidateDocumentLinkList = candidateDocumentLinkRepository
//							.getDocumentByCandidateId(candidateLink.getCandidate_id());
//					//List<CandidateDocumentLink> candidateDocumentLink = candidateDocumentLinkRepository.getDocumentByCandidateId(candidateLink.getCandidate_id());
//					System.out.println("candidateDocumentLink =====-=----inner1-----=--///"+candidateDocumentLinkList.size());

					}
				}
				RecruitProfileStatusLink recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
						.getRecruitProfileStatusLinkByProfileId(li.getProfile_id());
				if (null != recruitProfileStatusLink) {
					mapper.setApproveInd(recruitProfileStatusLink.isApproveInd());
					mapper.setRejectInd(recruitProfileStatusLink.isRejectInd());
				}
				mapper.setRecruitmentProcessId(li.getProcess_id());
				mapper.setRecruitmentId(li.getRecruitment_id());
				mapper.setOpportunityId(li.getOpp_id());
				mapper.setStageList(getActiveStagesOfProcess(li.getProcess_id()));
				mapper.setOnboardInd(li.isOnboard_ind());
				mapper.setOnboardDate(Utility.getISOFromDate(li.getOnboard_date()));

				if (!StringUtils.isEmpty(li.getRecruitOwner())) {
					EmployeeDetails employeeDetails = employeeRepository
							.getEmployeeDetailsByEmployeeId(li.getRecruitOwner());
					String middleName = " ";
					String lastName = "";
					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {
						lastName = employeeDetails.getLastName();
					}
					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {
						middleName = employeeDetails.getMiddleName();
						mapper.setRecruitOwner(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {
						mapper.setRecruitOwner(employeeDetails.getFirstName() + " " + lastName);
					}
				}

				if (null != candidateLink) {
					List<ProcessDocumentLink> processDocumentLink = processDocumentLinkRepository
							.getProcessDocumentListByProcessId(li.getProcess_id());
					Set<String> documentSet = new HashSet<String>();
					if (null != processDocumentLink && !processDocumentLink.isEmpty()) {
						for (ProcessDocumentLink processDocumentLink1 : processDocumentLink) {
							System.out.println(
									"candidate Id =====-=----inner3-----=--///" + candidateLink.getCandidate_id());
							if (null != processDocumentLink1.getDocumentTypeId()) {

								List<CandidateDocumentLink> candidateDocumentLink = candidateDocumentLinkRepository
										.findByCandidateId(candidateLink.getCandidate_id());
								System.out.println("candidateDocumentLink =====-=----inner3-----=--///"
										+ candidateDocumentLink.size());

								if (null != candidateDocumentLink && !candidateDocumentLink.isEmpty()) {
									for (CandidateDocumentLink candidateDocumentLink1 : candidateDocumentLink) {
										DocumentDetails documentDetails = documentDetailsRepository
												.getDocumentDetailsById(candidateDocumentLink1.getDocumentId());
										if (null != documentDetails) {
											if (null != documentDetails.getDocument_type()) {
												if (!documentDetails.getDocument_type()
														.equalsIgnoreCase(processDocumentLink1.getDocumentTypeId())) {
													DocumentType DocumentType = documentTypeRepository
															.getTypeDetails(processDocumentLink1.getDocumentTypeId());
													if (null != DocumentType) {
														documentSet.add(DocumentType.getDocumentTypeName());
													}
												}
											}
										}
									}
								} else {
									DocumentType DocumentType = documentTypeRepository
											.getTypeDetails(processDocumentLink1.getDocumentTypeId());
									if (null != DocumentType) {
										documentSet.add(DocumentType.getDocumentTypeName());
									}
								}
							}
						}

						mapper.setDocumentSetList(documentSet);
					}
				}
				mapper.setEmailInd(li.isEmailInd());
				mapper.setIntrestInd(li.isIntrestInd());

				resultrList.add(mapper);

				return resultrList;
			}).collect(Collectors.toList());
		}
		return resultrList;
	}

	@Override
	public RecruitmentOpportunityMapper getRecriutmentUpdateResponse(String recruitmentId, String orgId)
			throws Exception {

		OpportunityRecruitDetails recruitDetails = recruitmentOpportunityDetailsRepository
				.getRecruitmentDetailsByRecruitId(recruitmentId);
		RecruitmentOpportunityMapper resultMapper = new RecruitmentOpportunityMapper();
		if (null != recruitDetails) {
			resultMapper.setRecruitmentId(recruitDetails.getRecruitment_id());
			resultMapper.setOpportunityId(recruitDetails.getOpportunity_id());
			resultMapper.setRequirementName(recruitDetails.getName());
			resultMapper.setSponserId(recruitDetails.getSponser_id());
			resultMapper.setDescription(recruitDetails.getDescription());
			resultMapper.setNumber(recruitDetails.getNumber());
			resultMapper.setBilling(recruitDetails.getBilling());
			resultMapper.setCurrency(recruitDetails.getCurrency());
			resultMapper.setType(recruitDetails.getType());
			resultMapper.setAvilableDate(Utility.getISOFromDate(recruitDetails.getAvailable_date()));
			resultMapper.setEndDate(Utility.getISOFromDate(recruitDetails.getEnd_date()));
			resultMapper.setStageList(getActiveStagesOfProcess(recruitDetails.getRecruitment_process_id()));
			resultMapper.setCreationDate(Utility.getISOFromDate(recruitDetails.getCreationDate()));
			resultMapper.setJobOrder(recruitDetails.getJob_order());
			resultMapper.setExperience(recruitDetails.getExperience());
			resultMapper.setLocation(recruitDetails.getLocation());
			resultMapper.setCategory(recruitDetails.getCategory());
			resultMapper.setCountry(recruitDetails.getCountry());
			resultMapper.setDepartment(recruitDetails.getDepartment());
			resultMapper.setRole(recruitDetails.getRole());
			resultMapper.setRecruitmentProcessId(recruitDetails.getRecruitment_process_id());
			if (!StringUtils.isEmpty(recruitDetails.getUserId())) {
				EmployeeDetails employee = employeeRepository
						.getEmployeeDetailsByEmployeeId(recruitDetails.getUserId());
				resultMapper.setRecruitOwner(employee.getFullName());
			}

			if (!StringUtils.isEmpty(recruitDetails.getRecruiter_id())) {
				EmployeeDetails employeeDetails = employeeRepository
						.getEmployeeDetailsByEmployeeId(recruitDetails.getRecruiter_id());
				String middleName = " ";
				String lastName = " ";
				if (employeeDetails.getMiddleName() != null) {

					middleName = employeeDetails.getMiddleName();
				}
				if (employeeDetails.getLastName() != null) {

					lastName = employeeDetails.getLastName();
				}

				resultMapper.setRecruiterName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
			}

			/* Add skill set */
			resultMapper.setSkillSetList(candidateService.getSkillSetOfSkillLibery(orgId));
			// resultMapper.setSkillSetList(candidateService.getSkillSetOfCandidatesOfUser(orgId));
			List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
					.getRecruitmentSkillSetLinkByRecruitmentId(recruitDetails.getRecruitment_id());
			if (recruitmentSkillsetLink.size() != 0) {
				resultMapper.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

			}

			List<RecruitmentCandidateLink> recruitmentCandidateLink = candidateLinkRepository
					.getCandidateList(recruitDetails.getRecruitment_id());
			System.out.println("recruitmentCandidateLink::::::" + recruitmentCandidateLink.toString());
			if (null != recruitmentCandidateLink && !recruitmentCandidateLink.isEmpty()) {
				System.out.println("size:::::::::" + recruitmentCandidateLink.size());

				List<CandidateMapper> resultList = new ArrayList<>();
				int count = 0;
				for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
					CandidateMapper mapper = new CandidateMapper();
					CandidateDetails candidate = candidateDetailsRepository
							.getcandidateDetailsById(candidateLink.getCandidate_id());
					if (null != candidate) {
						String middleName = " ";
						String lastName = " ";
						if (null != candidate.getMiddleName()) {

							middleName = candidate.getMiddleName();
						}
						if (null != candidate.getLastName()) {

							lastName = candidate.getLastName();
						}
						mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
						mapper.setImageId(candidate.getImageId());
					}
					resultList.add(mapper);
					count++;
					if (count == 2) {
						break;
					}
				}
				resultMapper.setCandidatetList(resultList);
			}
			resultMapper.setCandidateNo(candidateNo(recruitmentCandidateLink.size()));

			/*
			 * List<RecruitmentRecruiterLink> recruiterList =
			 * recruitmentRecruiterLinkRepository
			 * .getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id()); if (null
			 * != recruiterList && !recruiterList.isEmpty()) { List<String> resultList = new
			 * ArrayList<>(); for (RecruitmentRecruiterLink recruiter : recruiterList) {
			 * //EmployeeMapper mapper = new EmployeeMapper(); String recruiters = null;
			 * EmployeeDetails emp =
			 * employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
			 * System.out.println("name....." + emp.getFullName()); recruiters
			 * =emp.getFullName(); recruiters=emp.getId(); resultList.add(recruiters); }
			 * recruitmentOpportunityMapper.setRecruiterNames(resultList); }
			 */

			List<RecruitmentRecruiterLink> recruiterList = recruitmentRecruiterLinkRepository
					.getRecruitmentRecruiterLinkByRecruitmentId(recruitDetails.getRecruitment_id());
			if (null != recruiterList && !recruiterList.isEmpty()) {
				List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
				for (RecruitmentRecruiterLink recruiter : recruiterList) {
					EmployeeMapper mapper = new EmployeeMapper();
					EmployeeDetails emp = employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
					// System.out.println("name....." + emp.getFullName());
					mapper.setFullName(emp.getFullName());
					mapper.setEmployeeId(recruiter.getRecruiterId());

					resultList.add(mapper);
				}
				resultMapper.setRecruiterList(resultList);
			}

			List<RecruitmentAddressLink> recruitmentAddressLink = recruitmentAddressLinkRepository
					.getAddressListByRecruitmentId(recruitDetails.getRecruitment_id());
			List<AddressMapper> addressList = new ArrayList<AddressMapper>();
			if (null != recruitmentAddressLink && !recruitmentAddressLink.isEmpty()) {

				for (RecruitmentAddressLink recruitmentAddressLink1 : recruitmentAddressLink) {
					AddressDetails addressDetails = addressRepository
							.getAddressDetailsByAddressId(recruitmentAddressLink1.getAddressId());

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
			resultMapper.setAddress(addressList);

			/*
			 * List<RecruitmentPartnerLink> recruitmentPartnerLinkList =
			 * recruitmentPartnerLinkRepository.
			 * findByRecruitmentIdAndLiveInd(recruitmentOpportunityMapper.getRecruitment_id(
			 * ),true); if (null != recruitmentPartnerLinkList &&
			 * !recruitmentPartnerLinkList.isEmpty()) { List<PartnerMapper>
			 * partnerResultList = new ArrayList<PartnerMapper>(); for
			 * (RecruitmentPartnerLink partner : recruitmentPartnerLinkList) { PartnerMapper
			 * mapper = new PartnerMapper(); PartnerDetails dbPartner =
			 * partnerDetailsRepository.getPartnerDetailsById(partner.getPartnerId());
			 * mapper.setPartnerName(dbPartner.getPartnerName());
			 * mapper.setPartnerId(dbPartner.getPartnerId());
			 * 
			 * partnerResultList.add(mapper); }
			 * resultMapper.setPartnerList(partnerResultList); } return
			 * resultMapper.add(resultMapper);
			 */
		}
		return resultMapper;
	}

	@Override
	public Object getRecruitDashBoardRecordByuserIdAndDateRange1(String recruitmentId, String startMonth,
			String endMonth) {
		/*
		 * int numOfAllCandidate = 0; int numOfOfferCandidate = 0; Date end_month =
		 * null; Date start_month = null;
		 * 
		 * try { end_month =
		 * Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(
		 * endMonth))); start_month =
		 * Utility.removeTime(Utility.getDateFromISOString(startMonth)); } catch
		 * (Exception e) { e.printStackTrace(); }
		 * 
		 * List<RecruitProfileStatusLink> recruterList =
		 * recruitmentProfileStatusLinkRepository
		 * .getRecruitmentRecruiterLinkByRecruitmentIdAndDateRange(recruitmentId,
		 * start_month, end_month); // List<RecruitmentRecruiterLink> recruterList //
		 * =recruitmentRecruiterLinkRepository. //
		 * getrecruiterListByRecruiterId(recruiterId);
		 * System.out.println("recruterList....." + recruterList.size());
		 * 
		 * if (null != recruterList) { for (RecruitProfileStatusLink
		 * recruitProfileStatusLink : recruterList) { List<RecruitmentCandidateLink>
		 * candidateList = recruitmentCandidateLinkRepository
		 * .getCandidateList(recruitProfileStatusLink.getRecruitmentId());
		 * numOfAllCandidate += candidateList.size();
		 * System.out.println("candidateList......" + candidateList.size()); } } if
		 * (null != recruterList) { for (RecruitProfileStatusLink
		 * recruitProfileStatusLink : recruterList) { List<RecruitProfileStatusLink>
		 * offerCandidateList = recruitmentProfileStatusLinkRepository
		 * .getRecruitProfileStatusLinkByRecruitmentId(recruitProfileStatusLink.
		 * getRecruitmentId()); numOfOfferCandidate += offerCandidateList.size(); } }
		 * HashMap map = new HashMap(); map.put("requirementopen", recruterList.size());
		 * map.put("profileTagg", numOfAllCandidate); map.put("positionFilled",
		 * numOfOfferCandidate);
		 * 
		 * return map;
		 */
		return null;
	}

	public void updatefeedback(RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			String recruitmentStageNoteId) {
		System.out.println("mapper........" + recruitmentStageNoteId);
		RecruitStageNoteLink recruitStageNoteLink = recruitStageNotelinkRepository
				.findByRecruitmentStageNoteId(recruitmentStageNoteId);
		System.out.println("object.........." + recruitStageNoteLink.toString());
		if (null != recruitStageNoteLink) {
			// recruitStageNoteLink.setRecruit_id(recruitmentOpportunityMapper.getRecruitmentId());
			recruitStageNoteLink.setNote(recruitmentOpportunityMapper.getNote());
			recruitStageNoteLink.setStage_id(recruitmentOpportunityMapper.getStageId());
			// recruitStageNoteLink.setOpp_id(recruitmentOpportunityMapper.getOpportunityId());
			recruitStageNoteLink.setProfile_id(recruitmentOpportunityMapper.getProfileId());
			if (!StringUtils.isEmpty(recruitmentOpportunityMapper.getReviewer())) {

				recruitStageNoteLink.setReviewer(recruitmentOpportunityMapper.getReviewer());
			}
			recruitStageNoteLink.setUpdatedOn(new Date());
			recruitStageNotelinkRepository.save(recruitStageNoteLink);
		}
	}

	@Override
	public RecruitmentOpportunityMapper getFeedbackByRecruitmentStageNoteId(String recruitmentStageNoteId) {
		RecruitStageNoteLink recruitStageNoteLink = recruitStageNotelinkRepository
				.findByRecruitmentStageNoteId(recruitmentStageNoteId);
		RecruitmentOpportunityMapper newMapper = new RecruitmentOpportunityMapper();
		if (null != recruitStageNoteLink) {
			newMapper.setRecruitmentId(recruitStageNoteLink.getRecruit_id());
			newMapper.setNote(recruitStageNoteLink.getNote());
			newMapper.setStageId(recruitStageNoteLink.getStage_id());
			newMapper.setOpportunityId(recruitStageNoteLink.getOpp_id());
			newMapper.setProfileId(recruitStageNoteLink.getProfile_id());
			newMapper.setReviewer(recruitStageNoteLink.getReviewer());
			newMapper.setUpdatedOn(Utility.getISOFromDate(recruitStageNoteLink.getUpdatedOn()));
			newMapper.setRecruitmentStageNoteId(recruitStageNoteLink.getRecruitmentStageNoteId());
			newMapper.setStageName(getStageName(recruitStageNoteLink.getStage_id()));
		}
		return newMapper;
	}

	@Override
	public RecruitmentOpportunityMapper getProfileDetails(String profileId) {
		RecruitmentOpportunityMapper mapper = new RecruitmentOpportunityMapper();

		RecruitProfileLinkDetails li = recruitmentProfileDetailsRepository.getprofiledetails(profileId);
		if (null != li) {

			System.out.println("profileId::::::::::" + li.getProfile_id());
			RecruitmentCandidateLink candidateLink = recruitmentCandidateLinkRepository
					.getCandidateProfile(li.getProfile_id());

			mapper.setProfileId(li.getProfile_id());
			mapper.setStageId(li.getStage_id());
			System.out.println("oppId:::" + li.getOpp_id());
			mapper.setStageName(getStageName(li.getStage_id()));

			if (null != candidateLink) {
				CandidateDetails candidate = candidateDetailsRepository
						.getcandidateDetailsById(candidateLink.getCandidate_id());
				if (null != candidate) {
					String middleName = " ";
					String lastName = " ";
					if (null != candidate.getMiddleName()) {

						middleName = candidate.getMiddleName();
					}
					if (null != candidate.getLastName()) {

						lastName = candidate.getLastName();
					}
					mapper.setCandidateName(candidate.getFirstName() + " " + middleName + " " + lastName);
					mapper.setEmailId(candidate.getEmailId());
					mapper.setCandidateBilling(candidate.getBilling());
					mapper.setCandidateId(candidate.getCandidateId());
					mapper.setCurrency(candidate.getCurrency());
					mapper.setAvilableDate(Utility.getISOFromDate(candidate.getAvailableDate()));

				}
			}
			RecruitProfileStatusLink recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
					.getRecruitProfileStatusLinkByProfileId(li.getProfile_id());
			if (null != recruitProfileStatusLink) {
				mapper.setApproveInd(recruitProfileStatusLink.isApproveInd());
				mapper.setRejectInd(recruitProfileStatusLink.isRejectInd());
			}
			mapper.setRecruitmentProcessId(li.getProcess_id());
			mapper.setRecruitmentId(li.getRecruitment_id());
			mapper.setOpportunityId(li.getOpp_id());
			mapper.setStageList(getActiveStagesOfProcess(li.getProcess_id()));
			mapper.setOnboardInd(li.isOnboard_ind());
			mapper.setOnboardDate(Utility.getISOFromDate(li.getOnboard_date()));
			mapper.setActualEndDate(Utility.getISOFromDate(li.getActualEndDate()));
			mapper.setFinalBilling(li.getFinalBilling());
			mapper.setOnboardCurrency(li.getOnboardCurrency());
			mapper.setProjectName(li.getProjectName());
			mapper.setBillableHour(li.getBillableHour());

			if (!StringUtils.isEmpty(li.getRecruitOwner())) {
				EmployeeDetails employeeDetails = employeeRepository
						.getEmployeeDetailsByEmployeeId(li.getRecruitOwner());
				String middleName = " ";
				String lastName = " ";
				if (employeeDetails.getMiddleName() != null) {

					middleName = employeeDetails.getMiddleName();
				}
				if (employeeDetails.getLastName() != null) {

					lastName = employeeDetails.getLastName();
				}

				mapper.setRecruitOwner(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
			}

			OpportunityDetails oppDetails = opportunityDetailsRepository
					.getOpportunityDetailsByOpportunityId(li.getOpp_id());
			String customerName = "";
			if (!StringUtils.isEmpty(oppDetails.getCustomerId())) {
				customerName = customerRepository.getCustomerDetailsByCustomerId(oppDetails.getCustomerId()).getName();
			}
			mapper.setCustomerName(customerName);

			String OrgName = organizationRepository.getOrganizationDetailsById(oppDetails.getOrgId()).getName();
			mapper.setOrgName(OrgName);
		}
		return mapper;
	}

	@Override
	public void updateRecruitmentCandidate(RecruitmentOpportunityMapper recruitmentOpportunityMapper,
			String profileId) {
		System.out.println("mapper........" + profileId);
		RecruitmentCandidateLink recruitmentCandidateLink = recruitmentCandidateLinkRepository
				.findByProfileId(profileId);
		System.out.println("object.........." + recruitmentCandidateLink.toString());
		if (null != recruitmentCandidateLink) {
			recruitmentCandidateLink.setOpportunity_id(recruitmentOpportunityMapper.getOpportunityId());
			recruitmentCandidateLink.setCandidate_id(recruitmentOpportunityMapper.getCandidateId());
			recruitmentCandidateLink.setProfileId(recruitmentOpportunityMapper.getProfileId());
			recruitmentCandidateLink.setRecruitment_process_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
			recruitmentCandidateLink.setStage_id(recruitmentOpportunityMapper.getStageId());
			recruitmentCandidateLink.setRecruitment_id(recruitmentOpportunityMapper.getRecruitmentId());
			recruitmentCandidateLink.setUser_id(recruitmentOpportunityMapper.getTagUserId());

			/*
			 * if (!StringUtils.isEmpty(recruitmentOpportunityMapper.getReviewer())) {
			 * 
			 * recruitStageNoteLink.setReviewer(recruitmentOpportunityMapper.getReviewer());
			 * 
			 * } recruitStageNoteLink.setUpdatedOn(new Date());
			 */
			recruitmentCandidateLinkRepository.save(recruitmentCandidateLink);
		}
	}

	@Override
	public RecruitmentOpportunityMapper getRecruitmentCandidateProfileId(String profileId) {
		RecruitmentCandidateLink recruitmentCandidateLink = recruitmentCandidateLinkRepository
				.findByProfileId(profileId);
		RecruitmentOpportunityMapper newMapper = new RecruitmentOpportunityMapper();
		if (null != recruitmentCandidateLink) {
			newMapper.setOpportunityId(recruitmentCandidateLink.getOpportunity_id());
			newMapper.setCandidateId(recruitmentCandidateLink.getCandidate_id());
			newMapper.setProfileId(recruitmentCandidateLink.getProfileId());
			newMapper.setRecruitmentProcessId(recruitmentCandidateLink.getRecruitment_process_id());
			newMapper.setStageId(recruitmentCandidateLink.getStage_id());
			newMapper.setRecruitmentId(recruitmentCandidateLink.getRecruitment_id());
			newMapper.setTagUserId(recruitmentCandidateLink.getUser_id());
		}
		return newMapper;
	}

	@Override
	public boolean ipAddressExists(String url) {
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			System.out.println("web>:>:::>::::::>>" + web.toString());
			return true;
		}
		return false;
	}

	public List<RecruitmentOpportunityMapper> getRecruitmentProgressDetailsListsByRecruiterId(String userId) {
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		List<RecruitmentRecruiterLink> oprecruiterList = recruitmentRecruiterLinkRepository
				.getrecruiterListByRecruiterId(userId);
		if (null != oprecruiterList && !oprecruiterList.isEmpty()) {
			// for (OpportunitySalesUserLink opportunitySalesUserLink : salesUserList) {
			return oprecruiterList.stream().map(recruitmentRecruiterLink -> {
				List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
						.getRecruitmentDetailsListByRecruitmentId(recruitmentRecruiterLink.getRecruitmentId());

				if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {
					recruitDetailsList.stream().map(li -> {
						RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
						recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
						recruitmentOpportunityMapper.setRequirementName(li.getName());
						recruitmentOpportunityMapper.setNumber(li.getNumber());
						recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
						recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
						recruitmentOpportunityMapper.setExperience(li.getExperience());
						recruitmentOpportunityMapper.setLocation(li.getLocation());

//                      List<RecruitProfileLinkDetails> onbordingList = recruitmentProfileDetailsRepository.
//                              getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());
//                      if(null != onbordingList) {
//                          recruitmentOpportunityMapper.setOnBoardNo(onbordingList.size());
//                      }

						if (!StringUtils.isEmpty(li.getSponser_id())) {

							ContactDetails contactDetails = contactRepository.getContactDetailsById(li.getSponser_id());
							String middleName = " ";
							String lastName = " ";
							if (null != contactDetails.getMiddle_name()) {

								middleName = contactDetails.getMiddle_name();
							}
							if (null != contactDetails.getLast_name()) {

								lastName = contactDetails.getLast_name();
							}
							recruitmentOpportunityMapper
									.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
						}
						OpportunityDetails opportunityDetails = opportunityDetailsRepository
								.getopportunityDetailsById(li.getOpportunity_id());
						if (opportunityDetails != null) {
							if (!StringUtils.isEmpty(opportunityDetails.getCustomerId())) {
								Customer customer = customerRepository
										.getCustomerDetailsByCustomerId(opportunityDetails.getCustomerId());
								recruitmentOpportunityMapper.setCustomerName(customer.getName());
							}
						}
						List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
								.getCandidateList(li.getRecruiter_id());
						if (null != recruitmentCandidateLink) {
							recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
						}

						List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
								.getRecruitProfileStatusLinkByRecruitmentId(li.getRecruitment_id());
						if (null != recruitProfileStatusLink) {
							recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
						}

						List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());
						if (null != profileLinkDetailsList) {
							recruitmentOpportunityMapper.setOnBoardNo(profileLinkDetailsList.size());
						}
						List<RecruitProfileStatusLink> dropList = recruitmentProfileStatusLinkRepository
								.getRecruitDropListByRecruitmentId(li.getRecruitment_id());
						if (null != dropList) {
							recruitmentOpportunityMapper.setRejected(dropList.size());
						}
						recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcessWithCandidate(
								li.getRecruitment_process_id(), li.getRecruitment_id()));
						return mapperList.add(recruitmentOpportunityMapper);

					}).collect(Collectors.toList());
				}
				return mapperList;
			}).flatMap(l -> l.stream()).collect(Collectors.toList());

		}
		return mapperList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getRecruitmentProgressDetailsListsByUserId(String userId) {
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		List<OpportunitySalesUserLink> salesUserList = opportunitySalesUserRepository.getSalesUserLinkByUserId(userId);
		if (null != salesUserList && !salesUserList.isEmpty()) {
			return salesUserList.stream().map(opportunitySalesUserLink -> {
				// for (OpportunitySalesUserLink opportunitySalesUserLink : salesUserList) {
				List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
						.getRecriutmentsByOpportunityId(opportunitySalesUserLink.getOpportunity_id());

				if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {
					recruitDetailsList.stream().map(li -> {
						RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
						recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
						recruitmentOpportunityMapper.setRequirementName(li.getName());
						recruitmentOpportunityMapper.setNumber(li.getNumber());
						recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
						recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
						recruitmentOpportunityMapper.setExperience(li.getExperience());
						recruitmentOpportunityMapper.setLocation(li.getLocation());

						if (!StringUtils.isEmpty(li.getSponser_id())) {

							ContactDetails contactDetails = contactRepository.getContactDetailsById(li.getSponser_id());
							String middleName = " ";
							String lastName = " ";
							if (null != contactDetails.getMiddle_name()) {

								middleName = contactDetails.getMiddle_name();
							}
							if (null != contactDetails.getLast_name()) {

								lastName = contactDetails.getLast_name();
							}
							recruitmentOpportunityMapper
									.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
						}

						OpportunityDetails opportunityDetails = opportunityDetailsRepository
								.getopportunityDetailsById(li.getOpportunity_id());
						if (opportunityDetails != null) {
							if (!StringUtils.isEmpty(opportunityDetails.getCustomerId())) {
								Customer customer = customerRepository
										.getCustomerDetailsByCustomerId(opportunityDetails.getCustomerId());
								recruitmentOpportunityMapper.setCustomerName(customer.getName());
							}
						}
						List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
								.getCandidateList(li.getRecruiter_id());
						if (null != recruitmentCandidateLink) {
							recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
						}

						List<RecruitProfileLinkDetails> onbordingList = recruitmentProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());
						if (null != onbordingList) {
							recruitmentOpportunityMapper.setOnBoardNo(onbordingList.size());
						}

						List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
								.getRecruitProfileStatusLinkByRecruitmentId(li.getRecruiter_id());
						if (null != recruitProfileStatusLink) {
							recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
						}

						List<RecruitProfileStatusLink> dropList = recruitmentProfileStatusLinkRepository
								.getRecruitDropListByRecruitmentId(li.getRecruitment_id());
						if (null != dropList) {
							recruitmentOpportunityMapper.setRejected(dropList.size());
						}
						recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcessWithCandidate(
								li.getRecruitment_process_id(), li.getRecruitment_id()));

						return mapperList.add(recruitmentOpportunityMapper);

					}).collect(Collectors.toList());
				}
				return mapperList;
			}).flatMap(l -> l.stream()).collect(Collectors.toList());

		}
		return mapperList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getDashBoardOpenRecruitmentByEmployeeId(String userId) {
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		List<OpportunitySalesUserLink> salesList = opportunitySalesUserRepository.getemployeeListByEmployeeId(userId);
		if (null != salesList && !salesList.isEmpty()) {
			System.out.println("salesList=" + salesList.size());
			System.out.println("salesList>>>" + salesList.toString());
			System.out.println("###############################################################");
			// return salesList.stream().map(opportunitySalesUserLink-> {
			for (OpportunitySalesUserLink opportunitySalesUserLink : salesList) {
				List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
						.getRecriutmentsByOpportunityId(opportunitySalesUserLink.getOpportunity_id());
				// int profileSize =0;

				for (OpportunityRecruitDetails opportunityRecruitDetails : recruitDetailsList) {

					System.out.println("recruitDetailsList>>>" + recruitDetailsList.toString());
					if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {
						// recruitDetailsList.stream().map(opportunityRecruitDetails -> {
						System.out.println("opportunityName=" + opportunityRecruitDetails.getName());
						List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(
										opportunityRecruitDetails.getRecruitment_id());
						System.out.println("start::::::::::::1");
						// System.out.println("RequirementId::::"+recruitmentRecruiterLink.getRecruitmentId());
						// OpportunityRecruitDetails opportunityRecruitDetails =
						// recruitmentOpportunityDetailsRepository
						// .getRecruitmentDetailsByRecruitId(recruitmentRecruiterLink.getRecruitmentId());
						if (opportunityRecruitDetails.isCloseInd() == false) {
							System.out.println("start::::::::::::2");
							int profileSize = profileList.size();
							int positionSize = (int) opportunityRecruitDetails.getNumber();
							if (profileSize < positionSize) {
								RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
								recruitmentOpportunityMapper
										.setRecruitmentId(opportunityRecruitDetails.getRecruitment_id());

								// recruitmentOpportunityMapper.setBilling(opportunityRecruitDetails.getBilling());
								recruitmentOpportunityMapper.setRequirementName(opportunityRecruitDetails.getName());
								recruitmentOpportunityMapper.setNumber(opportunityRecruitDetails.getNumber());
								// recruitmentOpportunityMapper.setAvilableDate(Utility.getISOFromDate(opportunityRecruitDetails.getAvailable_date()));
								recruitmentOpportunityMapper.setCreationDate(
										Utility.getISOFromDate(opportunityRecruitDetails.getCreationDate()));
								recruitmentOpportunityMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
								recruitmentOpportunityMapper.setExperience(opportunityRecruitDetails.getExperience());
								recruitmentOpportunityMapper.setLocation(opportunityRecruitDetails.getLocation());

								String role = "";
								if (!StringUtils.isEmpty(opportunityRecruitDetails.getRole())) {
									role = roleTypeRepository.findByRoleTypeId(opportunityRecruitDetails.getRole())
											.getRoleType();
								}
								recruitmentOpportunityMapper.setRole(role);

								// recruitmentOpportunityMapper.setStageName(getStageName(li.getStage_id()));
								// recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcess(li.getRecruitment_profile_details_id()));
								// recruitmentOpportunityMapper.setSt
								recruitmentOpportunityMapper.setProcessName(
										getProcessName(opportunityRecruitDetails.getRecruitment_process_id()));
								if (!StringUtils.isEmpty(opportunityRecruitDetails.getSponser_id())) {

									ContactDetails contactDetails = contactRepository
											.getContactDetailsById(opportunityRecruitDetails.getSponser_id());
									String middleName = " ";
									String lastName = " ";
									if (null != contactDetails.getMiddle_name()) {

										middleName = contactDetails.getMiddle_name();
									}
									if (null != contactDetails.getLast_name()) {

										lastName = contactDetails.getLast_name();
									}
									recruitmentOpportunityMapper.setSponserName(
											contactDetails.getFirst_name() + " " + middleName + " " + lastName);
								}

								OpportunityDetails opportunityDetails = opportunityDetailsRepository
										.getopportunityDetailsById(opportunityRecruitDetails.getOpportunity_id());
								if (opportunityDetails != null) {
									if (!StringUtils.isEmpty(opportunityDetails.getCustomerId())) {
										Customer customer = customerRepository
												.getCustomerDetailsByCustomerId(opportunityDetails.getCustomerId());
										if (null != customer) {
											recruitmentOpportunityMapper.setCustomerName(customer.getName());
										}
									}
								}

								List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
										.getCandidateList(opportunityRecruitDetails.getRecruitment_id());
								if (null != recruitmentCandidateLink) {
									recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
								}
								System.out.println("offer:::" + recruitmentCandidateLink.size());
								List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
										.getRecruitProfileStatusLinkByRecruitmentId(
												opportunityRecruitDetails.getRecruitment_id());
								if (null != recruitProfileStatusLink) {
									recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
								}
								System.out.println("closePosition::::::::" + recruitProfileStatusLink.size());
								List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
										.getProfileDetailByRecruitmentIdAndonBoardInd(
												opportunityRecruitDetails.getRecruitment_id());
								if (null != profileLinkDetailsList) {
									recruitmentOpportunityMapper.setOnBoardNo(profileLinkDetailsList.size());
								}
								List<RecruitProfileStatusLink> dropList = recruitmentProfileStatusLinkRepository
										.getRecruitDropListByRecruitmentId(
												opportunityRecruitDetails.getRecruitment_id());
								if (null != dropList) {
									recruitmentOpportunityMapper.setRejected(dropList.size());
								}
								recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcessWithCandidate(
										opportunityRecruitDetails.getRecruitment_process_id(),
										opportunityRecruitDetails.getRecruitment_id()));
								mapperList.add(recruitmentOpportunityMapper);
								System.out.println("____________________________");
							}
						}
					}
					// return mapperList;
					// }).collect(Collectors.toList());
				}
			}

//				return mapperList;
//			}).flatMap(l -> l.stream()).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public HashMap getOnboardCountByOpportunityId(String oppId) {
		List<RecruitProfileLinkDetails> recruitProfileList = recruitmentProfileDetailsRepository
				.getByOppIdAndOnboardIndAndLiveInd(oppId);
		HashMap map = new HashMap();
		map.put("recruitProfileLinkDetails", recruitProfileList.size());

		return map;
	}

	@Override

	public List<RecruitmentOpportunityMapper> getSalesOpenRecruitmentByuserIdAndDateRange(String userId,
			String startDate, String endDate) {

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		List<OpportunitySalesUserLink> salesList = opportunitySalesUserRepository
				.getemployeeListByEmployeeIdAndDateRange(userId, start_date, end_date);
		if (null != salesList && !salesList.isEmpty()) {
			for (OpportunitySalesUserLink opportunitySalesUserLink : salesList) {
				List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
						.getRecriutmentsByOpportunityId(opportunitySalesUserLink.getOpportunity_id());

				if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {
					recruitDetailsList.stream().map(li -> {
						List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());
						int profileSize = profileList.size();
						int positionSize = (int) li.getNumber();
						if (profileSize < positionSize) {
							RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
							recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
							recruitmentOpportunityMapper.setOpportunityId(li.getOpportunity_id());

							recruitmentOpportunityMapper.setLocation(li.getLocation());
							recruitmentOpportunityMapper.setRequirementName(li.getName());
							recruitmentOpportunityMapper.setSponserId(li.getSponser_id());
							recruitmentOpportunityMapper.setDescription(li.getDescription());
							recruitmentOpportunityMapper.setNumber(li.getNumber());
							recruitmentOpportunityMapper.setBilling(li.getBilling());

							EmployeeDetails employeeDetails = employeeRepository.getEmployeesByuserId(li.getUserId());
							recruitmentOpportunityMapper.setRecruitOwner(employeeDetails.getFullName());

							recruitmentOpportunityMapper.setCurrency(li.getCurrency());
							recruitmentOpportunityMapper.setType(li.getType());
							recruitmentOpportunityMapper
									.setAvilableDate(Utility.getISOFromDate(li.getAvailable_date()));
							recruitmentOpportunityMapper.setEndDate(Utility.getISOFromDate(li.getEnd_date()));
							recruitmentOpportunityMapper
									.setStageList(getActiveStagesOfProcess(li.getRecruitment_process_id()));
							recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
							recruitmentOpportunityMapper.setJobOrder(li.getJob_order());

							// recruitmentOpportunityMapper.setStageName(getStageName()));

							if (!StringUtils.isEmpty(li.getSponser_id())) {

								ContactDetails contactDetails = contactRepository
										.getContactDetailsById(li.getSponser_id());
								String middleName = " ";
								String lastName = " ";
								if (null != contactDetails.getMiddle_name()) {

									middleName = contactDetails.getMiddle_name();
								}
								if (null != contactDetails.getLast_name()) {

									lastName = contactDetails.getLast_name();
								}
								recruitmentOpportunityMapper.setSponserName(
										contactDetails.getFirst_name() + " " + middleName + " " + lastName);
							}

							List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
									.getCandidateList(li.getRecruiter_id());
							if (null != recruitmentCandidateLink) {
								recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
							}

							List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
									.getRecruitProfileStatusLinkByRecruitmentId(li.getRecruiter_id());
							if (null != recruitProfileStatusLink) {
								recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
							}

							List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
									.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruiter_id());
							if (null != profileLinkDetailsList) {
								recruitmentOpportunityMapper.setOnBoardNo(profileLinkDetailsList.size());
							}

							/*
							 * RecruitmentPublishDetails recruitmentPublishDetails =
							 * recruitmentPublishRepository
							 * .getRecruitmentPublishDetails(li.getRecruitment_id()); if (null !=
							 * recruitmentPublishDetails) {
							 * 
							 * recruitmentOpportunityMapper.setPublishInd(true); } else {
							 * 
							 * recruitmentOpportunityMapper.setPublishInd(false); }
							 * 
							 * if (!StringUtils.isEmpty(li.getRecruiter_id())) { EmployeeDetails
							 * employeeDetails = employeeRepository
							 * .getEmployeeDetailsByEmployeeId(li.getRecruiter_id()); String middleName =
							 * " "; String lastName = " "; if (employeeDetails.getMiddleName() != null) {
							 * 
							 * middleName = employeeDetails.getMiddleName(); } if
							 * (employeeDetails.getLastName() != null) {
							 * 
							 * lastName = employeeDetails.getLastName(); }
							 * 
							 * recruitmentOpportunityMapper .setRecruiterName(employeeDetails.getFirstName()
							 * + " " + middleName + " " + lastName); }
							 */

							/* Add skill set */
							recruitmentOpportunityMapper
									.setSkillSetList(candidateService.getSkillSetOfSkillLibery(li.getOpportunity_id()));
							List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
									.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
							if (recruitmentSkillsetLink.size() != 0) {
								recruitmentOpportunityMapper
										.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

							}

							List<RecruitmentRecruiterLink> recruiterList = recruitmentRecruiterLinkRepository
									.getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id());
							if (null != recruiterList && !recruiterList.isEmpty()) {
								List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
								for (RecruitmentRecruiterLink recruiter : recruiterList) {
									EmployeeMapper mapper = new EmployeeMapper();
									EmployeeDetails emp = employeeRepository
											.getEmployeesByuserId(recruiter.getRecruiterId());
									// System.out.println("name....." + emp.getFullName());
									mapper.setFullName(emp.getFullName());
									mapper.setEmployeeId(recruiter.getRecruiterId());

									resultList.add(mapper);
								}
								recruitmentOpportunityMapper.setRecruiterList(resultList);
							}
							return mapperList.add(recruitmentOpportunityMapper);
						}
						return mapperList;
					}).collect(Collectors.toList());
				}
			}
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentStageMapper> getActiveStagesOfProcessWithCandidate(String processId, String recruitmentId) {

		List<GlobalProcessStageLink> stageList = recruitmentProcessStageLinkRepository
				.getRecruitmentProcessStageLinkByProcessId(processId);
		List<RecruitmentStageMapper> mapperList = new ArrayList<>();
		if (null != stageList && !stageList.isEmpty()) {

			stageList.stream().map(li -> {

				RecruitmentProcessStageDetails recruitmentProcessStageDetails = recruitmentStageDetailsRepository
						.getPublishedStageDetailsOfProcess(li.getRecruitmentStageId());
				if (null != recruitmentProcessStageDetails) {
					if (!recruitmentProcessStageDetails.getStage_name().equalsIgnoreCase("Drop")) {
						if (!recruitmentProcessStageDetails.getStage_name().equalsIgnoreCase("Selected")) {
							RecruitmentStageMapper recruitmentStageMapper = new RecruitmentStageMapper();
							recruitmentStageMapper.setStageId(recruitmentProcessStageDetails.getRecruitmentStageId());
							recruitmentStageMapper.setStageName(recruitmentProcessStageDetails.getStage_name());
							recruitmentStageMapper.setProbability(recruitmentProcessStageDetails.getProbability());
							System.out.println("11111111111111111111111111111111111111111");
							List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
									.getProfileDetailByStageIdAndRecruitmentId(
											recruitmentProcessStageDetails.getRecruitmentStageId(), recruitmentId);
							int dropAndSelect = 0;
							if (null != profileLinkDetailsList) {
								for (RecruitProfileLinkDetails recruitProfileLinkDetails : profileLinkDetailsList) {
									System.out.println("profileid:" + recruitProfileLinkDetails.getProfile_id());
									List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
											.findByProfileIdAndRejectIndOrApproveInd(
													recruitProfileLinkDetails.getProfile_id(), true, true);
									System.out.println("recruitProfileStatusLink::" + recruitProfileStatusLink.size());
									System.out.println(
											"recruitProfileStatusLink::" + recruitProfileStatusLink.toString());
									if (null != recruitProfileStatusLink) {
										dropAndSelect++;
									}
								}
								recruitmentStageMapper.setCandidateNo(profileLinkDetailsList.size() - dropAndSelect);
							}
							System.out.println("222222222222222222222222222222222222222222222222222222222222");
							mapperList.add(recruitmentStageMapper);

							if (null != mapperList && !mapperList.isEmpty()) {

								Collections.sort(mapperList,
										(RecruitmentStageMapper c1, RecruitmentStageMapper c2) -> Double
												.compare(c1.getProbability(), c2.getProbability()));
							}
						}
					}
				}
				return mapperList;

			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public HashMap getAllUserRecordByuserIdAndDateRange(String userId, String startDate, String endDate) {
		HashMap map = new HashMap();
		int openRequirement = 0;
		int numOfAllCandidate = 0;
		int numOfOfferCandidate = 0;
		int numOfOnBoardCandidate = 0;
		int openPosition = 0;

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<OpportunitySalesUserLink> saleList = opportunitySalesUserRepository.getSalesUserLinkByUserId(userId);
		System.out.println("saleList....." + saleList.size());

		if (null != saleList && !saleList.isEmpty()) {
			for (OpportunitySalesUserLink opportunitySalesUserLink : saleList) {

				List<OpportunityRecruitDetails> openRecruitmentList = recruitmentOpportunityDetailsRepository
						.getOpenRecruitmentListByOppId(opportunitySalesUserLink.getOpportunity_id());
				for (OpportunityRecruitDetails openRecruitmentListNo : openRecruitmentList) {
					// openRequirement += openRecruitmentListNo.getNumber();
					if ((Utility.removeTime(openRecruitmentListNo.getCreationDate())
							.equals(Utility.removeTime(start_date)))
							|| (Utility.removeTime(openRecruitmentListNo.getCreationDate())
									.equals(Utility.removeTime(end_date)))
							|| (Utility.removeTime(openRecruitmentListNo.getCreationDate())
									.after(Utility.removeTime(start_date))
									&& Utility.removeTime(openRecruitmentListNo.getCreationDate())
											.before(Utility.removeTime(end_date)))) {

						List<RecruitProfileLinkDetails> onbordedCandidate = recruitmentProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(
										openRecruitmentListNo.getRecruitment_id());
						numOfOnBoardCandidate += onbordedCandidate.size();

						if (openRecruitmentListNo.isCloseInd() == false) {
							int positionSize = (int) openRecruitmentListNo.getNumber();
							if (numOfOnBoardCandidate < positionSize) {
								openRequirement++;
								openPosition += openRecruitmentListNo.getNumber();
							}
						}
					}
					List<RecruitProfileLinkDetails> onbordedCandidate = recruitmentProfileDetailsRepository
							.getProfileDetailByRecruitmentIdAndonBoardIndAndDateRange(
									openRecruitmentListNo.getRecruitment_id(), start_date, end_date);
					numOfOnBoardCandidate += onbordedCandidate.size();

					/*
					 * List<RecruitProfileLinkDetails> allCandidate =
					 * recruitmentProfileDetailsRepository
					 * .getProfileDetailByDateRange(openRecruitmentListNo.getRecruitment_id(),
					 * start_date,end_date); numOfAllCandidate += allCandidate.size();
					 */

					List<RecruitProfileStatusLink> selectedCandidate = recruitmentProfileStatusLinkRepository
							.getRecruitProfileStatusLinkByRecruitmentIdOnDateRange(
									openRecruitmentListNo.getRecruitment_id(), start_date, end_date);
					numOfOfferCandidate += selectedCandidate.size();

				}
			}
		}
		System.out.println("openPosition>>>>>>>>>>" + openPosition);
		map.put("openRequirement", openRequirement);
		// map.put("taggedProfile", numOfAllCandidate);
		map.put("selectted", numOfOfferCandidate);
		map.put("onboarded", numOfOnBoardCandidate);
		map.put("openPosition", openPosition);

		return map;
	}

	@Override
	public RecruitmentOpportunityMapper getRecriutmentByOppIdAndRecruitId(String opportunityId, String recruitId,
			String orgId) throws Exception {
		RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();

		OpportunityRecruitDetails li = recruitmentOpportunityDetailsRepository
				.getRecriutmentsByOpportunitysId(opportunityId, recruitId);
		if (null != li) {

			recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
			recruitmentOpportunityMapper.setOpportunityId(li.getOpportunity_id());
			/*
			 * recruitmentOpportunityMapper.setStageId(li.getStage_id());
			 * recruitmentOpportunityMapper.setRecruitmentProcessId(li.getProcess_id());
			 * recruitmentOpportunityMapper.setProcessName(getProcessName(li.getProcess_id()
			 * ));
			 * recruitmentOpportunityMapper.setStageName(getStageName(li.getStage_id()));
			 * recruitmentOpportunityMapper.setProfileId(li.getProfile_id());
			 */
			recruitmentOpportunityMapper.setLocation(li.getLocation());
			recruitmentOpportunityMapper.setRequirementName(li.getName());
			recruitmentOpportunityMapper.setSponserId(li.getSponser_id());
			recruitmentOpportunityMapper.setDescription(li.getDescription());
			recruitmentOpportunityMapper.setNumber(li.getNumber());
			recruitmentOpportunityMapper.setBilling(li.getBilling());
			recruitmentOpportunityMapper.setCurrency(li.getCurrency());
			recruitmentOpportunityMapper.setType(li.getType());
			recruitmentOpportunityMapper.setAvilableDate(Utility.getISOFromDate(li.getAvailable_date()));
			recruitmentOpportunityMapper.setEndDate(Utility.getISOFromDate(li.getEnd_date()));
			recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcess(li.getRecruitment_process_id()));
			recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
			recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
			recruitmentOpportunityMapper.setExperience(li.getExperience());
			recruitmentOpportunityMapper.setLocation(li.getLocation());
			recruitmentOpportunityMapper.setCategory(li.getCategory());
			recruitmentOpportunityMapper.setCountry(li.getCountry());
			recruitmentOpportunityMapper.setDepartment(li.getDepartment());
			recruitmentOpportunityMapper.setRole(li.getRole());
			recruitmentOpportunityMapper.setRecruitmentProcessId(li.getRecruitment_process_id());
			recruitmentOpportunityMapper.setWorkPreference(li.getWorkPreferance());
			if (!StringUtils.isEmpty(li.getUserId())) {
				EmployeeDetails employee = employeeRepository.getEmployeeDetailsByEmployeeId(li.getUserId());
				recruitmentOpportunityMapper.setRecruitOwner(employee.getFullName());
			}

			RecruitmentPublishDetails recruitmentPublishDetails = recruitmentPublishRepository
					.getRecruitmentPublishDetails(li.getRecruitment_id());
			if (null != recruitmentPublishDetails) {

				recruitmentOpportunityMapper.setPublishInd(true);
			} else {

				recruitmentOpportunityMapper.setPublishInd(false);
			}

			if (!StringUtils.isEmpty(li.getRecruiter_id())) {
				EmployeeDetails employeeDetails = employeeRepository
						.getEmployeeDetailsByEmployeeId(li.getRecruiter_id());
				String middleName = " ";
				String lastName = " ";
				if (employeeDetails.getMiddleName() != null) {

					middleName = employeeDetails.getMiddleName();
				}
				if (employeeDetails.getLastName() != null) {

					lastName = employeeDetails.getLastName();
				}

				recruitmentOpportunityMapper
						.setRecruiterName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
			}

			/* Add skill set */
			recruitmentOpportunityMapper.setSkillSetList(candidateService.getSkillSetOfSkillLibery(orgId));
			List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
					.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
			if (recruitmentSkillsetLink.size() != 0) {
				recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

			}

			List<RecruitmentCandidateLink> recruitmentCandidateLink = candidateLinkRepository
					.getCandidateList(li.getRecruitment_id());
			System.out.println("recruitmentCandidateLink::::::" + recruitmentCandidateLink.toString());
			if (null != recruitmentCandidateLink && !recruitmentCandidateLink.isEmpty()) {
				System.out.println("size:::::::::" + recruitmentCandidateLink.size());

				List<CandidateMapper> resultList = new ArrayList<>();
				int count = 0;
				for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
					CandidateMapper mapper = new CandidateMapper();
					CandidateDetails candidate = candidateDetailsRepository
							.getcandidateDetailsById(candidateLink.getCandidate_id());
					if (null != candidate) {
						String middleName = " ";
						String lastName = " ";
						if (null != candidate.getMiddleName()) {

							middleName = candidate.getMiddleName();
						}
						if (null != candidate.getLastName()) {

							lastName = candidate.getLastName();
						}
						mapper.setCandidateId(candidate.getCandidateId());
						mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
						mapper.setImageId(candidate.getImageId());
					}
					resultList.add(mapper);
					count++;
					if (count == 2) {
						break;
					}
				}
				recruitmentOpportunityMapper.setCandidatetList(resultList);

				List<CandidateMapper> resultList2 = new ArrayList<>();
				for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
					CandidateMapper mapper = new CandidateMapper();
					CandidateDetails candidate = candidateDetailsRepository
							.getcandidateDetailsById(candidateLink.getCandidate_id());
					if (null != candidate) {
						String middleName = " ";
						String lastName = " ";
						if (null != candidate.getMiddleName()) {

							middleName = candidate.getMiddleName();
						}
						if (null != candidate.getLastName()) {

							lastName = candidate.getLastName();
						}
						mapper.setCandidateId(candidate.getCandidateId());
						mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
						mapper.setImageId(candidate.getImageId());
					}
					resultList2.add(mapper);
				}
				recruitmentOpportunityMapper.setFiltercandidatetList(resultList2);
			}
			recruitmentOpportunityMapper.setCandidateNo(candidateNo(recruitmentCandidateLink.size()));
			/*
			 * RecruitProfileStatusLink recruitProfileStatusLink =
			 * recruitmentProfileStatusLinkRepository
			 * .getRecruitProfileStatusLinkByProfileId(li.getProfile_id()); if (null !=
			 * recruitProfileStatusLink) {
			 * recruitmentOpportunityMapper.setApproveInd(recruitProfileStatusLink.
			 * isApproveInd());
			 * recruitmentOpportunityMapper.setRejectInd(recruitProfileStatusLink.
			 * isRejectInd()); }
			 */

			/*
			 * List<RecruitmentRecruiterLink> recruiterList =
			 * recruitmentRecruiterLinkRepository
			 * .getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id()); if (null
			 * != recruiterList && !recruiterList.isEmpty()) { List<String> resultList = new
			 * ArrayList<>(); for (RecruitmentRecruiterLink recruiter : recruiterList) {
			 * //EmployeeMapper mapper = new EmployeeMapper(); String recruiters = null;
			 * EmployeeDetails emp =
			 * employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
			 * System.out.println("name....." + emp.getFullName()); recruiters
			 * =emp.getFullName(); recruiters=emp.getId(); resultList.add(recruiters); }
			 * recruitmentOpportunityMapper.setRecruiterNames(resultList); }
			 */

			List<RecruitmentRecruiterLink> recruiterList = recruitmentRecruiterLinkRepository
					.getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id());
			if (null != recruiterList && !recruiterList.isEmpty()) {
				List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
				for (RecruitmentRecruiterLink recruiter : recruiterList) {
					EmployeeMapper mapper = new EmployeeMapper();
					EmployeeDetails emp = employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
					// System.out.println("name....." + emp.getFullName());
					mapper.setFullName(emp.getFullName());
					mapper.setEmployeeId(recruiter.getRecruiterId());

					resultList.add(mapper);
				}
				recruitmentOpportunityMapper.setRecruiterList(resultList);
			}
			List<RecruitmentPartnerLink> recruitmentPartnerLinkList = recruitmentPartnerLinkRepository
					.findByRecruitmentIdAndLiveInd(li.getRecruitment_id(), true);
			if (null != recruitmentPartnerLinkList && !recruitmentPartnerLinkList.isEmpty()) {
				List<PartnerMapper> partnerResultList = new ArrayList<PartnerMapper>();
				for (RecruitmentPartnerLink partner : recruitmentPartnerLinkList) {
					PartnerMapper mapper = new PartnerMapper();
					System.out.println("partner::::::::::" + partner.getPartnerId());
					System.out.println("recID:::::::::::" + partner.getRecruitmentId());
					PartnerDetails dbPartner = partnerDetailsRepository.getPartnerDetailsById(partner.getPartnerId());
					mapper.setPartnerName(dbPartner.getPartnerName());
					mapper.setPartnerId(dbPartner.getPartnerId());

					partnerResultList.add(mapper);
				}
				recruitmentOpportunityMapper.setPartnerList(partnerResultList);
			}
			// return mapperList.add(recruitmentOpportunityMapper);
		}
		return recruitmentOpportunityMapper;
	}

	@Override
	public String closeRecruitment(String recruitmentId) {
		OpportunityRecruitDetails recruitDetails = recruitmentOpportunityDetailsRepository
				.getRecruitmentDetailsByRecruitId(recruitmentId);
		if (null != recruitDetails) {
			recruitDetails.setCloseInd(true);
			recruitmentOpportunityDetailsRepository.save(recruitDetails);
			return " Requirement Closed Successfully";
		}
		return "Sorry Requirement Not Found";
	}

	@Override
	public List<RecruitmentOpportunityMapper> getCloseRecruitmentOfOpportunity(String opportunityId, String orgId,
			String userId) throws Exception {

		List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
				.getClosedRecriutmentsByOpportunityId(opportunityId);
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {
			recruitDetailsList.stream().map(li -> {

				RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
				recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
				recruitmentOpportunityMapper.setOpportunityId(li.getOpportunity_id());
				/*
				 * recruitmentOpportunityMapper.setStageId(li.getStage_id());
				 * recruitmentOpportunityMapper.setRecruitmentProcessId(li.getProcess_id());
				 * recruitmentOpportunityMapper.setProcessName(getProcessName(li.getProcess_id()
				 * ));
				 * recruitmentOpportunityMapper.setStageName(getStageName(li.getStage_id()));
				 * recruitmentOpportunityMapper.setProfileId(li.getProfile_id());
				 */
				recruitmentOpportunityMapper.setLocation(li.getLocation());
				recruitmentOpportunityMapper.setRequirementName(li.getName());
				recruitmentOpportunityMapper.setSponserId(li.getSponser_id());
				recruitmentOpportunityMapper.setDescription(li.getDescription());
				recruitmentOpportunityMapper.setNumber(li.getNumber());
				recruitmentOpportunityMapper.setBilling(li.getBilling());
				recruitmentOpportunityMapper.setCurrency(li.getCurrency());
				recruitmentOpportunityMapper.setType(li.getType());
				recruitmentOpportunityMapper.setAvilableDate(Utility.getISOFromDate(li.getAvailable_date()));
				recruitmentOpportunityMapper.setEndDate(Utility.getISOFromDate(li.getEnd_date()));
				recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcess(li.getRecruitment_process_id()));
				recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
				recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
				recruitmentOpportunityMapper.setExperience(li.getExperience());
				recruitmentOpportunityMapper.setLocation(li.getLocation());
				recruitmentOpportunityMapper.setCategory(li.getCategory());
				recruitmentOpportunityMapper.setCountry(li.getCountry());
				recruitmentOpportunityMapper.setDepartment(li.getDepartment());
				recruitmentOpportunityMapper.setRole(li.getRole());
				recruitmentOpportunityMapper.setRecruitmentProcessId(li.getRecruitment_process_id());
				recruitmentOpportunityMapper.setWorkPreference(li.getWorkPreferance());
				recruitmentOpportunityMapper.setCloseInd(li.isCloseInd());
				if (!StringUtils.isEmpty(li.getUserId())) {
					EmployeeDetails employee = employeeRepository.getEmployeeDetailsByEmployeeId(li.getUserId());
					recruitmentOpportunityMapper.setRecruitOwner(employee.getFullName());
				}

				RecruitmentPublishDetails recruitmentPublishDetails = recruitmentPublishRepository
						.getRecruitmentPublishDetails(li.getRecruitment_id());
				if (null != recruitmentPublishDetails) {

					recruitmentOpportunityMapper.setPublishInd(true);
				} else {

					recruitmentOpportunityMapper.setPublishInd(false);
				}

				if (!StringUtils.isEmpty(li.getRecruiter_id())) {
					EmployeeDetails employeeDetails = employeeRepository
							.getEmployeeDetailsByEmployeeId(li.getRecruiter_id());
					String middleName = " ";
					String lastName = " ";
					if (employeeDetails.getMiddleName() != null) {

						middleName = employeeDetails.getMiddleName();
					}
					if (employeeDetails.getLastName() != null) {

						lastName = employeeDetails.getLastName();
					}

					recruitmentOpportunityMapper
							.setRecruiterName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				}

				/* Add skill set */
				recruitmentOpportunityMapper.setSkillSetList(candidateService.getSkillSetOfSkillLibery(orgId));
				List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
						.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
				if (recruitmentSkillsetLink.size() != 0) {
					recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

				}

				List<RecruitmentCandidateLink> recruitmentCandidateLink = candidateLinkRepository
						.getCandidateList(li.getRecruitment_id());
				System.out.println("recruitmentCandidateLink::::::" + recruitmentCandidateLink.toString());
				if (null != recruitmentCandidateLink && !recruitmentCandidateLink.isEmpty()) {
					System.out.println("size:::::::::" + recruitmentCandidateLink.size());

					List<CandidateMapper> resultList = new ArrayList<>();
					int count = 0;
					for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
						CandidateMapper mapper = new CandidateMapper();
						CandidateDetails candidate = candidateDetailsRepository
								.getcandidateDetailsById(candidateLink.getCandidate_id());
						if (null != candidate) {
							String middleName = " ";
							String lastName = " ";
							if (null != candidate.getMiddleName()) {

								middleName = candidate.getMiddleName();
							}
							if (null != candidate.getLastName()) {

								lastName = candidate.getLastName();
							}
							mapper.setCandidateId(candidate.getCandidateId());
							mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
							mapper.setImageId(candidate.getImageId());
						}
						resultList.add(mapper);
						count++;
						if (count == 2) {
							break;
						}
					}
					recruitmentOpportunityMapper.setCandidatetList(resultList);

					List<CandidateMapper> resultList2 = new ArrayList<>();
					for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
						CandidateMapper mapper = new CandidateMapper();
						CandidateDetails candidate = candidateDetailsRepository
								.getcandidateDetailsById(candidateLink.getCandidate_id());
						if (null != candidate) {
							String middleName = " ";
							String lastName = " ";
							if (null != candidate.getMiddleName()) {

								middleName = candidate.getMiddleName();
							}
							if (null != candidate.getLastName()) {

								lastName = candidate.getLastName();
							}
							mapper.setCandidateId(candidate.getCandidateId());
							mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
							mapper.setImageId(candidate.getImageId());
						}
						resultList2.add(mapper);
					}
					recruitmentOpportunityMapper.setFiltercandidatetList(resultList2);
				}
				recruitmentOpportunityMapper.setCandidateNo(candidateNo(recruitmentCandidateLink.size()));
				/*
				 * RecruitProfileStatusLink recruitProfileStatusLink =
				 * recruitmentProfileStatusLinkRepository
				 * .getRecruitProfileStatusLinkByProfileId(li.getProfile_id()); if (null !=
				 * recruitProfileStatusLink) {
				 * recruitmentOpportunityMapper.setApproveInd(recruitProfileStatusLink.
				 * isApproveInd());
				 * recruitmentOpportunityMapper.setRejectInd(recruitProfileStatusLink.
				 * isRejectInd()); }
				 */

				/*
				 * List<RecruitmentRecruiterLink> recruiterList =
				 * recruitmentRecruiterLinkRepository
				 * .getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id()); if (null
				 * != recruiterList && !recruiterList.isEmpty()) { List<String> resultList = new
				 * ArrayList<>(); for (RecruitmentRecruiterLink recruiter : recruiterList) {
				 * //EmployeeMapper mapper = new EmployeeMapper(); String recruiters = null;
				 * EmployeeDetails emp =
				 * employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
				 * System.out.println("name....." + emp.getFullName()); recruiters
				 * =emp.getFullName(); recruiters=emp.getId(); resultList.add(recruiters); }
				 * recruitmentOpportunityMapper.setRecruiterNames(resultList); }
				 */

				List<RecruitmentRecruiterLink> recruiterList = recruitmentRecruiterLinkRepository
						.getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id());
				if (null != recruiterList && !recruiterList.isEmpty()) {
					List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
					for (RecruitmentRecruiterLink recruiter : recruiterList) {
						EmployeeMapper mapper = new EmployeeMapper();
						EmployeeDetails emp = employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
						// System.out.println("name....." + emp.getFullName());
						mapper.setFullName(emp.getFullName());
						mapper.setEmployeeId(recruiter.getRecruiterId());

						resultList.add(mapper);
					}
					recruitmentOpportunityMapper.setRecruiterList(resultList);
				}

				List<RecruitmentPartnerLink> recruitmentPartnerLinkList = recruitmentPartnerLinkRepository
						.findByRecruitmentIdAndLiveInd(li.getRecruitment_id(), true);
				if (null != recruitmentPartnerLinkList && !recruitmentPartnerLinkList.isEmpty()) {
					List<PartnerMapper> partnerResultList = new ArrayList<PartnerMapper>();
					for (RecruitmentPartnerLink partner : recruitmentPartnerLinkList) {
						PartnerMapper mapper = new PartnerMapper();
						System.out.println("partner::::::::::" + partner.getPartnerId());
						System.out.println("recID:::::::::::" + partner.getRecruitmentId());
						PartnerDetails dbPartner = partnerDetailsRepository
								.getPartnerDetailsById(partner.getPartnerId());
						mapper.setPartnerName(dbPartner.getPartnerName());
						mapper.setPartnerId(dbPartner.getPartnerId());

						partnerResultList.add(mapper);
					}
					recruitmentOpportunityMapper.setPartnerList(partnerResultList);
				}
				return mapperList.add(recruitmentOpportunityMapper);

			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public String OpenRecruitment(String recruitmentId) {
		OpportunityRecruitDetails recruitDetails = recruitmentOpportunityDetailsRepository
				.getRecruitmentDetailsByRecruitId(recruitmentId);
		if (null != recruitDetails) {
			recruitDetails.setCloseInd(false);
			recruitmentOpportunityDetailsRepository.save(recruitDetails);
			return "Requirement Opened Successfully";
		}
		return "Sorry Requirement Not Found";
	}

	@Override
	public String publishWebsiteRequirementPing(String recruitmentId, PingMapper pingMapper) {
		RecruitmentPublishDetails recruitmentPublishPing = recruitmentPublishRepository
				.getRecruitmentPulishPing(recruitmentId);

		if (null != recruitmentPublishPing) {

			if (pingMapper.isPingInd()) {
				recruitmentPublishPing.setPingInd(true);
				recruitmentPublishRepository.save(recruitmentPublishPing);
				return "Requirement Pinged into Top";
			} else {
				recruitmentPublishPing.setPingInd(pingMapper.isPingInd());
				recruitmentPublishRepository.save(recruitmentPublishPing);
				return "Requirement Pinged into Top Cancelled";
			}
		}
		return "Sorry Requirement Not Found";
	}

	@Override
	public String saveUpwork(UpworkMapper upworkMapper) {
		String Id = null;
		Upwork upwork = upworkRepository.findByOrgId(upworkMapper.getOrgId());
		if (null != upwork) {
			upwork.setUpworkInd(upworkMapper.isUpworkInd());
			upwork.setOrgId(upworkMapper.getOrgId());
			upwork.setUserId(upworkMapper.getUserId());
			upwork.setLastUpdatedOn(new Date());
			Id = upworkRepository.save(upwork).getUpworkId();

		} else {
			// String Ids = null;
			Upwork newUpwork = new Upwork();
			// newUpwork.setUpworkId(upworkMapper.getUpworkId());
			newUpwork.setUpworkInd(upworkMapper.isUpworkInd());
			newUpwork.setOrgId(upworkMapper.getOrgId());
			newUpwork.setUserId(upworkMapper.getUserId());
			newUpwork.setLastUpdatedOn(new Date());
			Id = upworkRepository.save(newUpwork).getUpworkId();
		}
		return Id;
	}

	@Override
	public UpworkMapper getUpworkByOrgId(String orgId) {
		UpworkMapper mapper = new UpworkMapper();
		Upwork pem = upworkRepository.findByOrgId(orgId);
		if (pem != null) {
			mapper.setUpworkInd(pem.isUpworkInd());
			mapper.setUserId(pem.getUserId());
			mapper.setLastUpdatedOn(Utility.getISOFromDate(pem.getLastUpdatedOn()));

			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(pem.getUserId());

			if (employeeDetails != null) {

				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					mapper.setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					mapper.setName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		return mapper;
	}

	public List<RecruitmentOpportunityMapper> getSalesOpenRecruitmentByOrgId(String orgId) {
		/*
		 * Date end_date = null; Date start_date = null; try { end_date =
		 * Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(
		 * endDate))); start_date =
		 * Utility.removeTime(Utility.getDateFromISOString(startDate)); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */

		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		List<OpportunitySalesUserLink> salesList = opportunitySalesUserRepository.getOpportunitySalesListByOrgId(orgId);
		if (null != salesList && !salesList.isEmpty()) {
			return salesList.stream().map(opportunitySalesUserLink -> {
				List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
						.getRecriutmentsByOpportunityId(opportunitySalesUserLink.getOpportunity_id());

				if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {
					recruitDetailsList.stream().map(li -> {
						List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());
						int profileSize = profileList.size();
						int positionSize = (int) li.getNumber();
						if (profileSize < positionSize) {
							RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
							recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
							recruitmentOpportunityMapper.setOpportunityId(li.getOpportunity_id());

							recruitmentOpportunityMapper.setLocation(li.getLocation());
							recruitmentOpportunityMapper.setRequirementName(li.getName());
							recruitmentOpportunityMapper.setSponserId(li.getSponser_id());
							recruitmentOpportunityMapper.setDescription(li.getDescription());
							recruitmentOpportunityMapper.setNumber(li.getNumber());
							recruitmentOpportunityMapper.setBilling(li.getBilling());
							recruitmentOpportunityMapper.setCurrency(li.getCurrency());
							recruitmentOpportunityMapper.setType(li.getType());
							recruitmentOpportunityMapper
									.setAvilableDate(Utility.getISOFromDate(li.getAvailable_date()));
							recruitmentOpportunityMapper.setEndDate(Utility.getISOFromDate(li.getEnd_date()));
							recruitmentOpportunityMapper
									.setStageList(getActiveStagesOfProcess(li.getRecruitment_process_id()));
							recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
							recruitmentOpportunityMapper.setJobOrder(li.getJob_order());

							// recruitmentOpportunityMapper.setStageName(getStageName()));

							if (!StringUtils.isEmpty(li.getSponser_id())) {

								ContactDetails contactDetails = contactRepository
										.getContactDetailsById(li.getSponser_id());
								String middleName = " ";
								String lastName = " ";
								if (null != contactDetails.getMiddle_name()) {

									middleName = contactDetails.getMiddle_name();
								}
								if (null != contactDetails.getLast_name()) {

									lastName = contactDetails.getLast_name();
								}
								recruitmentOpportunityMapper.setSponserName(
										contactDetails.getFirst_name() + " " + middleName + " " + lastName);
							}

							List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
									.getCandidateList(li.getRecruiter_id());
							if (null != recruitmentCandidateLink) {
								recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
							}

							List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
									.getRecruitProfileStatusLinkByRecruitmentId(li.getRecruiter_id());
							if (null != recruitProfileStatusLink) {
								recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
							}

							List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
									.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruiter_id());
							if (null != profileLinkDetailsList) {
								recruitmentOpportunityMapper.setOnBoardNo(profileLinkDetailsList.size());
							}

							/*
							 * RecruitmentPublishDetails recruitmentPublishDetails =
							 * recruitmentPublishRepository
							 * .getRecruitmentPublishDetails(li.getRecruitment_id()); if (null !=
							 * recruitmentPublishDetails) {
							 * 
							 * recruitmentOpportunityMapper.setPublishInd(true); } else {
							 * 
							 * recruitmentOpportunityMapper.setPublishInd(false); }
							 * 
							 * if (!StringUtils.isEmpty(li.getRecruiter_id())) { EmployeeDetails
							 * employeeDetails = employeeRepository
							 * .getEmployeeDetailsByEmployeeId(li.getRecruiter_id()); String middleName =
							 * " "; String lastName = " "; if (employeeDetails.getMiddleName() != null) {
							 * 
							 * middleName = employeeDetails.getMiddleName(); } if
							 * (employeeDetails.getLastName() != null) {
							 * 
							 * lastName = employeeDetails.getLastName(); }
							 * 
							 * recruitmentOpportunityMapper .setRecruiterName(employeeDetails.getFirstName()
							 * + " " + middleName + " " + lastName); }
							 */

							/* Add skill set */
							recruitmentOpportunityMapper
									.setSkillSetList(candidateService.getSkillSetOfSkillLibery(li.getOpportunity_id()));
							List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
									.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
							if (recruitmentSkillsetLink.size() != 0) {
								recruitmentOpportunityMapper
										.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

							}

							List<RecruitmentRecruiterLink> recruiterList = recruitmentRecruiterLinkRepository
									.getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id());
							if (null != recruiterList && !recruiterList.isEmpty()) {
								List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
								for (RecruitmentRecruiterLink recruiter : recruiterList) {
									EmployeeMapper mapper = new EmployeeMapper();
									EmployeeDetails emp = employeeRepository
											.getEmployeesByuserId(recruiter.getRecruiterId());
									// System.out.println("name....." + emp.getFullName());
									mapper.setFullName(emp.getFullName());
									mapper.setEmployeeId(recruiter.getRecruiterId());

									resultList.add(mapper);
								}
								recruitmentOpportunityMapper.setRecruiterList(resultList);
							}
							return mapperList.add(recruitmentOpportunityMapper);
						}
						return mapperList;
					}).collect(Collectors.toList());
				}
				return mapperList;
			}).flatMap(l -> l.stream()).collect(Collectors.toList());

		}
		return mapperList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getOfferedCandidateByOrgId(String orgId) {
		/*
		 * Date end_date = null; Date start_date = null; try { end_date =
		 * Utility.getDateFromISOString(endDate); start_date =
		 * Utility.getDateFromISOString(startDate); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		List<OpportunitySalesUserLink> salesList = opportunitySalesUserRepository.getOpportunitySalesListByOrgId(orgId);
		if (null != salesList) {
			return salesList.stream().map(opportunitySalesUserLink -> {
				List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
						.getRecriutmentsByOpportunityId(opportunitySalesUserLink.getOpportunity_id());

				if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {
					recruitDetailsList.stream().map(li -> {

						List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());

						RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
						recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
						recruitmentOpportunityMapper.setRequirementName(li.getName());
						recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
						recruitmentOpportunityMapper.setNumber(li.getNumber());
						recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
						recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
						recruitmentOpportunityMapper.setExperience(li.getExperience());
						recruitmentOpportunityMapper.setLocation(li.getLocation());
						recruitmentOpportunityMapper.setAvilableDate(Utility.getISOFromDate(li.getAvailable_date()));
						recruitmentOpportunityMapper.setBilling(li.getBilling());
						recruitmentOpportunityMapper
								.setStageList(getActiveStagesOfProcess(li.getRecruitment_process_id()));

						if (!StringUtils.isEmpty(li.getSponser_id())) {

							ContactDetails contactDetails = contactRepository.getContactDetailsById(li.getSponser_id());
							String middleName = " ";
							String lastName = " ";
							if (null != contactDetails.getMiddle_name()) {

								middleName = contactDetails.getMiddle_name();
							}
							if (null != contactDetails.getLast_name()) {

								lastName = contactDetails.getLast_name();
							}
							recruitmentOpportunityMapper
									.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
						}
						List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
								.getCandidateList(li.getRecruitment_id());
						if (null != recruitmentCandidateLink) {
							recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
						}

						List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
								.getRecruitProfileStatusLinkByRecruitmentId(li.getRecruitment_id());
						if (null != recruitProfileStatusLink) {
							recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
						}

						List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());
						if (null != profileLinkDetailsList) {
							recruitmentOpportunityMapper.setOnBoardNo(profileLinkDetailsList.size());
						}
						List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
								.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
						if (recruitmentSkillsetLink.size() != 0) {
							recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

						}

						mapperList.add(recruitmentOpportunityMapper);
						return mapperList;

					}).collect(Collectors.toList());
				}
				return mapperList;
			}).flatMap(l -> l.stream()).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<CustomerRecruitmentMapper> getCustomerListByUserId(String userId) {

		List<CustomerRecruitmentMapper> resultList = new ArrayList<CustomerRecruitmentMapper>();
		List<OpportunitySalesUserLink> list = opportunitySalesUserRepository.getCustomerListByUserId(userId);
//              System.out.println("list>>>>"+list.toString());
		HashMap<String, List<OpportunityDetails>> map = new HashMap<>();

		if (null != list && !list.isEmpty()) {
			for (OpportunitySalesUserLink opportunitySalesUserLink : list) {

				OpportunityDetails opportunity = opportunityDetailsRepository
						.getopportunityDetailsById(opportunitySalesUserLink.getOpportunity_id());
//                       System.out.println("opportunityId:"+opportunitySalesUserLink.getOpportunity_id());
				// opportunity.getCustomerId().trim();
				if (null != opportunity) {
					if (opportunity.getCustomerId() == null || opportunity.getCustomerId() == ""
							|| opportunity.getCustomerId().length() == 0) {

					} else {

						if (map.keySet().contains(opportunity.getCustomerId())) {
							map.get(opportunity.getCustomerId()).add(opportunity);

						} else {
							List<OpportunityDetails> ll = new ArrayList<>();
							ll.add(opportunity);
							map.put(opportunity.getCustomerId(), ll);
						}
					}
				}
			}
		}
		// CustomerRecruitmentMapper mapper = new CustomerRecruitmentMapper();
//              System.out.println(map.keySet());

		// for (String key : map.keySet()) {

		map.keySet().stream().map(li -> {

			CustomerRecruitmentMapper mapper = new CustomerRecruitmentMapper();

			System.out.println("The Key Is " + li);
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(li);
			if (null != customer) {
				mapper.setCustomerName(customer.getName());
			}
			long recruitmentNo = 0;
			int onBordedNo = 0;
			int selectedNo = 0;
			int position = 0;
			for (OpportunityDetails opp : map.get(li)) {
				System.out.println("the opp. id is : " + opp.getOpportunityId());
				List<OpportunityRecruitDetails> openRecruitmentList = recruitmentOpportunityDetailsRepository
						.getOpenRecruitmentListByOppId(opp.getOpportunityId());

				for (OpportunityRecruitDetails openRecruitmentListNo : openRecruitmentList) {
					recruitmentNo += openRecruitmentListNo.getNumber();
					List<RecruitProfileLinkDetails> onbordedCandidate = recruitmentProfileDetailsRepository
							.getProfileDetailByRecruitmentIdAndonBoardInd(openRecruitmentListNo.getRecruitment_id());
					onBordedNo += onbordedCandidate.size();

					List<RecruitProfileStatusLink> selectedCandidate = recruitmentProfileStatusLinkRepository
							.getRecruitProfileStatusLinkByRecruitmentId(openRecruitmentListNo.getRecruitment_id());
					selectedNo += selectedCandidate.size();
				}
			}
			System.out.println("recruitmentNo=" + recruitmentNo);
			System.out.println("onBordedNo=" + onBordedNo);
			System.out.println("selectedNo=" + selectedNo);
			position = (int) recruitmentNo - onBordedNo;
			System.out.println("position=" + position);
			mapper.setOpenRequirmentNo((int) recruitmentNo);
			mapper.setOnboardedNo(onBordedNo);
			mapper.setSelectedNo(selectedNo);
			mapper.setPosition(position);
			resultList.add(mapper);
			return resultList;
		}).collect(Collectors.toList());

		return resultList.stream().filter(o -> o.getPosition() > 0).collect(Collectors.toList());
	}

	@Override
	public Object getAllRecordByorgIdAndDateRange(String orgId, String startDate, String endDate) {
		int openRequirement = 0;
		int numOfAllCandidate = 0;
		int numOfOfferCandidate = 0;
		int numOfOnBoardCandidate = 0;
		int openPosition = 0;
		// int todayOnboardNo = 0;

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Date currentDate = new Date();

		List<OpportunityDetails> opportunityList = opportunityDetailsRepository.getopportunityDetailsByOrgId(orgId);
		/*
		 * List<OpportunityDetails> sortedOpportunityList = opportunityList.stream()
		 * .filter(list ->
		 * Utility.removeTime(list.getCreationDate()).compareTo(Utility.removeTime(
		 * currentDate)) == 0) .collect(Collectors.toList());
		 */
		// openRequirement = sortedOpportunityList.size();
		// System.out.println("sortedNo="+sortedOpportunityList.size());
		for (OpportunityDetails opportunityDetails : opportunityList) {
			List<OpportunityRecruitDetails> recruitOppList = recruitmentOpportunityDetailsRepository
					.getRecriutmentsByOpportunityId(opportunityDetails.getOpportunityId());

			for (OpportunityRecruitDetails opportunityRecruitDetails : recruitOppList) {
				System.out.println("RECID=" + opportunityRecruitDetails.getRecruitment_id());

				if ((Utility.removeTime(opportunityRecruitDetails.getCreationDate())
						.equals(Utility.removeTime(start_date)))
						|| (Utility.removeTime(opportunityRecruitDetails.getCreationDate())
								.equals(Utility.removeTime(end_date)))
						|| (Utility.removeTime(opportunityRecruitDetails.getCreationDate())
								.after(Utility.removeTime(start_date))
								&& Utility.removeTime(opportunityRecruitDetails.getCreationDate())
										.before(Utility.removeTime(end_date)))) {

					if (opportunityRecruitDetails.isCloseInd() == false) {
						System.out.println("oppId::::::::::::" + opportunityDetails.getOpportunityId());
						List<RecruitProfileLinkDetails> todayOnbordedList = recruitProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(
										opportunityRecruitDetails.getRecruitment_id());
						// todayOnboardNo += todayOnbordedList.size();
						int profileSize = todayOnbordedList.size();
						int positionSize = (int) opportunityRecruitDetails.getNumber();
						System.out.println("profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
						if (profileSize < positionSize) {

							openRequirement++;
							openPosition += opportunityRecruitDetails.getNumber();
						}
					}
				}
				/*
				 * List<RecruitProfileLinkDetails> allCandidate =
				 * recruitmentProfileDetailsRepository
				 * .getProfileDetailByDateRange(opportunityRecruitDetails.getRecruitment_id(),
				 * start_date, end_date); numOfAllCandidate += allCandidate.size();
				 */
				List<RecruitProfileStatusLink> selectedCandidate = recruitmentProfileStatusLinkRepository
						.getRecruitProfileStatusLinkByRecruitmentIdOnDateRange(
								opportunityRecruitDetails.getRecruitment_id(), start_date, end_date);
				numOfOfferCandidate += selectedCandidate.size();

				List<RecruitProfileLinkDetails> onbordedCandidateList = recruitProfileDetailsRepository
						.getProfileDetailByRecruitmentIdAndonBoardIndAndDateRange(
								opportunityRecruitDetails.getRecruitment_id(), start_date, end_date);
				numOfOnBoardCandidate += onbordedCandidateList.size();

			}
		}
		/*
		 * openPosition = openRequirement; if (numOfOfferCandidate > todayOnboardNo) {
		 * numOfOfferCandidate = numOfOfferCandidate - todayOnboardNo; } else {
		 * numOfOfferCandidate = 0; }
		 */

		HashMap map = new HashMap();
		map.put("openRequirement", openRequirement);
		// map.put("taggedProfile", numOfAllCandidate);
		map.put("selectted", numOfOfferCandidate);
		map.put("onboarded", numOfOnBoardCandidate);
		map.put("openPosition", openPosition);

		return map;
	}

	@Override
	public String saveWebsiteForPartner(WebsitePartnerLinkMapper websitePartnerLinkMapper) {
		List<WebsitePartnerLink> dbWebsite = websitePartnerLinkRepository
				.findByOrgId(websitePartnerLinkMapper.getOrgId());
		if (null != dbWebsite && dbWebsite.size() != 0) {
			String Id = null;
			dbWebsite.get(0).setUrl(websitePartnerLinkMapper.getUrl());
			dbWebsite.get(0).setIp(websitePartnerLinkMapper.getIp());
			dbWebsite.get(0).setUser_id(websitePartnerLinkMapper.getUserId());
			dbWebsite.get(0).setAssignToUserId(websitePartnerLinkMapper.getAssignToUserId());
			dbWebsite.get(0).setLastUpdatedOn(new Date());
			Id = websitePartnerLinkRepository.save(dbWebsite.get(0)).getWebsitePartnerLinkId();

			return Id;
		} else {
			String Ids = null;
			WebsitePartnerLink newWebsite = new WebsitePartnerLink();
			newWebsite.setWebsitePartnerLinkId(websitePartnerLinkMapper.getWebsitePartnerLinkId());
			newWebsite.setUrl(websitePartnerLinkMapper.getUrl());
			newWebsite.setIp(websitePartnerLinkMapper.getIp());
			newWebsite.setOrgId(websitePartnerLinkMapper.getOrgId());
			newWebsite.setUser_id(websitePartnerLinkMapper.getUserId());
			newWebsite.setAssignToUserId(websitePartnerLinkMapper.getAssignToUserId());
			newWebsite.setLastUpdatedOn(new Date());
			Ids = websitePartnerLinkRepository.save(newWebsite).getWebsitePartnerLinkId();
			return Ids;
		}
	}

	@Override
	public List<WebsitePartnerLinkMapper> getWebsitePartnerListByOrgId(String orgId) {
		List<WebsitePartnerLink> websiteList = websitePartnerLinkRepository.findByOrgId(orgId);
		List<WebsitePartnerLinkMapper> mapperList = new ArrayList<>();
		if (null != websiteList && !websiteList.isEmpty()) {
			return websiteList.stream().map(websitePartnerLink -> {
				WebsitePartnerLinkMapper mapper = new WebsitePartnerLinkMapper();

				mapper.setWebsitePartnerLinkId(websitePartnerLink.getWebsitePartnerLinkId());
				mapper.setUserId(websitePartnerLink.getUser_id());
				mapper.setAssignToUserId(websitePartnerLink.getAssignToUserId());
				mapper.setIp(websitePartnerLink.getIp());
				mapper.setOrgId(websitePartnerLink.getOrgId());
				mapper.setUrl(websitePartnerLink.getUrl());
				mapper.setLastUpdatedOn(Utility.getISOFromDate(websitePartnerLink.getLastUpdatedOn()));

				EmployeeDetails employeeDetails = employeeRepository
						.getEmployeeByUserId(websitePartnerLink.getUser_id());

				if (employeeDetails != null) {

					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						mapper.setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						mapper.setName(employeeDetails.getFirstName() + " " + lastName);
					}
				}
				mapperList.add(mapper);

				return mapper;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<CustomerRecruitmentMapper> getRecruiterCloserByUserId(String userId, String type, String startDate,
			String endDate) {
		List<CustomerRecruitmentMapper> resultMapper = new ArrayList<>();
		int totalRequirement = 0;
		int onBordedNo = 0;
		float closerRatio = 0;
		int selectedNo = 0;

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String name = "";
		name = employeeRepository.getEmployeesByuserId(userId).getFullName();

		CustomerRecruitmentMapper mapper = new CustomerRecruitmentMapper();
		List<RecruitmentRecruiterLink> recruiterLinksList = recruitmentRecruiterLinkRepository
				.getRecruitmentRecruiterLinkByRecruitmentIdAndDateRange(userId, start_date, end_date);
		if (null != recruiterLinksList && !recruiterLinksList.isEmpty()) {
			for (RecruitmentRecruiterLink recruitmentRecruiterLink : recruiterLinksList) {

				OpportunityRecruitDetails recruitDetails = recruitmentOpportunityDetailsRepository
						.getRecruitmentDetailsByRecruitId(recruitmentRecruiterLink.getRecruitmentId());
				if (null != recruitDetails) {
					System.out.println("recruitDetails======" + recruitDetails.getRecruitment_id());
					totalRequirement += recruitDetails.getNumber();

					List<RecruitProfileLinkDetails> onbordedCandidate = recruitmentProfileDetailsRepository
							.getProfileDetailByRecruitmentIdAndonBoardInd(recruitDetails.getRecruitment_id());
					onBordedNo += onbordedCandidate.size();
					List<RecruitProfileStatusLink> selectedCandidate = recruitmentProfileStatusLinkRepository
							.getRecruitProfileStatusLinkByRecruitmentId(recruitDetails.getRecruitment_id());
					selectedNo += selectedCandidate.size();
				}
			}
			mapper.setName(name);
			mapper.setOpenRequirmentNo(totalRequirement - onBordedNo);
			mapper.setSelectedNo(selectedNo);
			mapper.setOnboardedNo(onBordedNo);
			resultMapper.add(mapper);
		}
		return resultMapper;
	}

	@Override
	public List<CustomerRecruitmentMapper> getSalesCloserByUserId(String userId, String startDate, String endDate) {
		List<CustomerRecruitmentMapper> resultMapper = new ArrayList<>();
		List<OpportunitySalesUserLink> salesUserList = null;
		int totalRequirement = 0;
		int onBordedNo = 0;
		int selectedNo = 0;
		float closerRatio = 0;

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		salesUserList = opportunitySalesUserRepository.getemployeeListByEmployeeIdAndDateRange(userId, start_date,
				end_date);
		String name = "";
		name = employeeRepository.getEmployeesByuserId(userId).getFullName();

		if (null != salesUserList && !salesUserList.isEmpty()) {
			CustomerRecruitmentMapper mapper = new CustomerRecruitmentMapper();
			for (OpportunitySalesUserLink opportunitySalesUserLink : salesUserList) {

				List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
						.getRecriutmentsByOpportunityId(opportunitySalesUserLink.getOpportunity_id());

				if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {
					List<OpportunityRecruitDetails> recruitDetails = recruitmentOpportunityDetailsRepository
							.getRecriutmentByOpportunityIdAndLiveInd(opportunitySalesUserLink.getOpportunity_id());
					for (OpportunityRecruitDetails OpportunityRecruitDetails : recruitDetails) {

						totalRequirement += OpportunityRecruitDetails.getNumber();
						List<RecruitProfileLinkDetails> onbordedCandidate = recruitmentProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(
										OpportunityRecruitDetails.getRecruitment_id());
						onBordedNo += onbordedCandidate.size();
						List<RecruitProfileStatusLink> selectedCandidate = recruitmentProfileStatusLinkRepository
								.getRecruitProfileStatusLinkByRecruitmentId(
										OpportunityRecruitDetails.getRecruitment_id());
						selectedNo += selectedCandidate.size();

					}
				}
			}
			mapper.setName(name);
			mapper.setOpenRequirmentNo(totalRequirement - onBordedNo);
			mapper.setSelectedNo(selectedNo);
			mapper.setOnboardedNo(onBordedNo);
			resultMapper.add(mapper);
		}
		return resultMapper;
	}

	@Override
	public List<CustomerRecruitmentMapper> getUsersCloserByOrgId(String organisationId, String startDate,
			String endDate) {
		List<CustomerRecruitmentMapper> resultMapper = new ArrayList<>();
		Department department = departmentRepository.findByName("Recruiter");
		List<EmployeeDetails> empList = employeeRepository.getEmployeesByOrgId(organisationId);
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("empList============" + empList.toString());
		for (EmployeeDetails employeeDetails : empList) {
			int totalRequirement = 0;
			int onBordedNo = 0;
			int selectedNo = 0;
			CustomerRecruitmentMapper mapper = new CustomerRecruitmentMapper();
			System.out.println("userId=" + employeeDetails.getUserId());
			if (null != employeeDetails.getDepartment() && employeeDetails.getDepartment() != ""
					&& employeeDetails.getDepartment().equalsIgnoreCase(department.getDepartment_id())) {
				List<RecruitmentRecruiterLink> recruiterLinksList = recruitmentRecruiterLinkRepository
						.getRecruitmentRecruiterLinkByRecruitmentIdAndDateRange(employeeDetails.getUserId(), start_date,
								end_date);
				if (null != recruiterLinksList && !recruiterLinksList.isEmpty()) {
					for (RecruitmentRecruiterLink recruitmentRecruiterLink : recruiterLinksList) {

						OpportunityRecruitDetails recruitDetails = recruitmentOpportunityDetailsRepository
								.getRecruitmentDetailsByRecruitId(recruitmentRecruiterLink.getRecruitmentId());
						if (null != recruitDetails) {
							System.out.println("recruitDetails======" + recruitDetails.getRecruitment_id());
							totalRequirement += recruitDetails.getNumber();

							List<RecruitProfileLinkDetails> onbordedCandidate = recruitmentProfileDetailsRepository
									.getProfileDetailByRecruitmentIdAndonBoardInd(recruitDetails.getRecruitment_id());
							onBordedNo += onbordedCandidate.size();
							List<RecruitProfileStatusLink> selectedCandidate = recruitmentProfileStatusLinkRepository
									.getRecruitProfileStatusLinkByRecruitmentId(recruitDetails.getRecruitment_id());
							selectedNo += selectedCandidate.size();
						}
					}
					mapper.setName(employeeDetails.getFullName());
					mapper.setOpenRequirmentNo(totalRequirement - onBordedNo);
					mapper.setSelectedNo(selectedNo);
					mapper.setOnboardedNo(onBordedNo);
					resultMapper.add(mapper);
					System.out.println("saving recruiter");
				}
			} else {
				List<OpportunitySalesUserLink> salesUserList = opportunitySalesUserRepository
						.getemployeeListByEmployeeIdAndDateRange(employeeDetails.getUserId(), start_date, end_date);

				if (null != salesUserList && !salesUserList.isEmpty()) {
					for (OpportunitySalesUserLink opportunitySalesUserLink : salesUserList) {

						List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
								.getRecriutmentsByOpportunityId(opportunitySalesUserLink.getOpportunity_id());

						if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {
							List<OpportunityRecruitDetails> recruitDetails = recruitmentOpportunityDetailsRepository
									.getRecriutmentByOpportunityIdAndLiveInd(
											opportunitySalesUserLink.getOpportunity_id());

							for (OpportunityRecruitDetails OpportunityRecruitDetails : recruitDetails) {

								totalRequirement += OpportunityRecruitDetails.getNumber();
								List<RecruitProfileLinkDetails> onbordedCandidate = recruitmentProfileDetailsRepository
										.getProfileDetailByRecruitmentIdAndonBoardInd(
												OpportunityRecruitDetails.getRecruitment_id());
								onBordedNo += onbordedCandidate.size();
								List<RecruitProfileStatusLink> selectedCandidate = recruitmentProfileStatusLinkRepository
										.getRecruitProfileStatusLinkByRecruitmentId(
												OpportunityRecruitDetails.getRecruitment_id());
								selectedNo += selectedCandidate.size();

							}
						}
					}
					mapper.setName(employeeDetails.getFullName());
					mapper.setOpenRequirmentNo(totalRequirement - onBordedNo);
					mapper.setSelectedNo(selectedNo);
					mapper.setOnboardedNo(onBordedNo);
					resultMapper.add(mapper);
					System.out.println("saving sales");
				}
			}
		}
		return resultMapper;
	}

	@Override
	public String getSelectedCandidateEmailContent(RecruitmentOpportunityMapper recruitmentOpportunityMappernew)
			throws IOException, TemplateException {
		StringWriter stringWriter = new StringWriter();
		Map<String, Object> model = new HashMap<>();
		model.put("recruitmentOpportunityMapper", recruitmentOpportunityMappernew);
		configuration.getTemplate("selected.ftlh").process(model, stringWriter);
		return stringWriter.getBuffer().toString();
	}

	@Override
	public String getUpdateStageCandidateEmailContent(RecruitmentOpportunityMapper recruitmentOpportunityMappernew)
			throws IOException, TemplateException {
		StringWriter stringWriter = new StringWriter();
		Map<String, Object> model = new HashMap<>();
		model.put("recruitmentOpportunityMapper", recruitmentOpportunityMappernew);
		configuration.getTemplate("stageUpdate.ftlh").process(model, stringWriter);
		return stringWriter.getBuffer().toString();
	}

	@Override
	public String getDropCandidateEmailContent(RecruitmentOpportunityMapper recruitmentOpportunityMappernew)
			throws IOException, TemplateException {
		StringWriter stringWriter = new StringWriter();
		Map<String, Object> model = new HashMap<>();
		model.put("recruitmentOpportunityMapper", recruitmentOpportunityMappernew);
		configuration.getTemplate("drop.ftlh").process(model, stringWriter);
		return stringWriter.getBuffer().toString();
	}

	@Override
	public List<CustomerRecruitmentMapper> getCustomerRequirementByOrgId(String organizationId, String startDate,
			String endDate) {
		List<CustomerRecruitmentMapper> resultList = new ArrayList<CustomerRecruitmentMapper>();
		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getopportunityDetailsByOrgId(organizationId);
		// System.out.println("list>>>>"+list.toString());

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		HashMap<String, List<OpportunityDetails>> map = new HashMap<>();

		if (null != opportunityList && !opportunityList.isEmpty()) {
			for (OpportunityDetails opportunityDetails : opportunityList) {

//               System.out.println("opportunityId:"+opportunitySalesUserLink.getOpportunity_id());
				// opportunity.getCustomerId().trim();

				if (opportunityDetails.getCustomerId() == null || opportunityDetails.getCustomerId() == ""
						|| opportunityDetails.getCustomerId().length() == 0) {

				} else {

					if (map.keySet().contains(opportunityDetails.getCustomerId())) {
						map.get(opportunityDetails.getCustomerId()).add(opportunityDetails);

					} else {
						List<OpportunityDetails> ll = new ArrayList<>();
						ll.add(opportunityDetails);
						map.put(opportunityDetails.getCustomerId(), ll);
					}
				}
			}
		}
		// CustomerRecruitmentMapper mapper = new CustomerRecruitmentMapper();
//      System.out.println(map.keySet());
		for (String key : map.keySet()) {
			CustomerRecruitmentMapper mapper = new CustomerRecruitmentMapper();

			System.out.println("The Key Is " + key);
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(key);
			if (null != customer) {
				mapper.setCustomerName(customer.getName());
			} else {
				mapper.setCustomerName("Customer not available");
			}
			long recruitmentNo = 0;
			int onBordedNo = 0;
			int selectedNo = 0;
			int position = 0;
			for (OpportunityDetails opp : map.get(key)) {
				System.out.println("the opp. id is : " + opp.getOpportunityId());
				List<OpportunityRecruitDetails> openRecruitmentList = recruitmentOpportunityDetailsRepository
						.getRecruitmentListByOppIdAndDateRange(opp.getOpportunityId(), start_date, end_date);

				for (OpportunityRecruitDetails openRecruitmentListNo : openRecruitmentList) {
					recruitmentNo += openRecruitmentListNo.getNumber();
					List<RecruitProfileLinkDetails> onbordedCandidate = recruitmentProfileDetailsRepository
							.getProfileDetailByRecruitmentIdAndonBoardInd(openRecruitmentListNo.getRecruitment_id());
					onBordedNo += onbordedCandidate.size();

					List<RecruitProfileStatusLink> selectedCandidate = recruitmentProfileStatusLinkRepository
							.getRecruitProfileStatusLinkByRecruitmentId(openRecruitmentListNo.getRecruitment_id());
					selectedNo += selectedCandidate.size();
				}
			}
			System.out.println("recruitmentNo=" + recruitmentNo);
			System.out.println("onBordedNo=" + onBordedNo);
			System.out.println("selectedNo=" + selectedNo);
			position = (int) recruitmentNo - onBordedNo;
			System.out.println("position=" + position);
			mapper.setOpenRequirmentNo((int) recruitmentNo);
			mapper.setOnboardedNo(onBordedNo);
			mapper.setSelectedNo(selectedNo);
			mapper.setPosition(position);
			resultList.add(mapper);
			System.out.println("------------------------");
		}

		if (resultList != null && !resultList.isEmpty()) {

			Collections.sort(resultList, (CustomerRecruitmentMapper c1, CustomerRecruitmentMapper c2) -> Integer
					.compare(c2.getOnboardedNo(), c1.getOnboardedNo()));
		}
		return resultList.stream().filter(o -> o.getPosition() > 0).collect(Collectors.toList());

	}

	// @Scheduled(cron = "0 0/15 * * * *")
	@Override
	public String updateCustomerLatestRequirement() throws Exception {

		List<OpportunityDetails> opportunityDetailslist = opportunityDetailsRepository.findByLiveInd(true);
		System.out.println("size@@1==" + opportunityDetailslist.size());
		System.out.println("list==" + opportunityDetailslist.toString());
		for (OpportunityDetails opportunityDetails : opportunityDetailslist) {
			if (opportunityDetails.getCustomerId() != null && opportunityDetails.getCustomerId() != "") {
				List<OpportunityRecruitDetails> opportunityRecruitDetailsList = recruitmentOpportunityDetailsRepository
						.getRecriutmentsByOpportunityId(opportunityDetails.getOpportunityId());
				if (opportunityRecruitDetailsList != null && !opportunityRecruitDetailsList.isEmpty()) {
					Collections.sort(opportunityRecruitDetailsList,
							(m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				}
				System.out.println("size=" + opportunityRecruitDetailsList.size());

				if (opportunityRecruitDetailsList.size() != 0) {
					System.out.println("222222222222222222222222222");
					OpportunityRecruitDetails latestRecruitDetails = opportunityRecruitDetailsList.get(0);
					System.out.println("3333333333333333333333");
					CustomerRecruitUpdate dbCustomerRecruitUpdate = customerRecruitUpdateRepository
							.findByCustomerId(latestRecruitDetails.getCustomerId());
					if (dbCustomerRecruitUpdate != null) {
						dbCustomerRecruitUpdate.setCustomerId(latestRecruitDetails.getCustomerId());
						dbCustomerRecruitUpdate.setRecruitmentId(latestRecruitDetails.getRecruitment_id());
						dbCustomerRecruitUpdate.setUpdatedDate(new Date());
						customerRecruitUpdateRepository.save(dbCustomerRecruitUpdate);
						System.out.println(
								"udate Id =" + customerRecruitUpdateRepository.save(dbCustomerRecruitUpdate).getId());
					} else {
						CustomerRecruitUpdate cusRecruitUpdate = new CustomerRecruitUpdate();
						cusRecruitUpdate.setCustomerId(latestRecruitDetails.getCustomerId());
						cusRecruitUpdate.setRecruitmentId(latestRecruitDetails.getRecruitment_id());
						cusRecruitUpdate.setUpdatedDate(new Date());
						customerRecruitUpdateRepository.save(cusRecruitUpdate);
						System.out.println("new Id =" + customerRecruitUpdateRepository.save(cusRecruitUpdate).getId());
					}
				}
			}
		}
		return null;
	}

	@Override
	public void linkCandidateToRecruitment(CandidateWebsiteMapper candidateWebsiteMapper) {
		List<RecruitmentCandidateLink> recruitmentCandidateLinkList = candidateLinkRepository
				.getCandidateList(candidateWebsiteMapper.getRecruitmentId());

		List<String> candiList = candidateWebsiteMapper.getCandidateIds();
		if (null != recruitmentCandidateLinkList && !recruitmentCandidateLinkList.isEmpty()) {
			if (null != candiList && !candiList.isEmpty()) {
				for (String candidateId : candiList) {

					boolean flag = false;
					for (RecruitmentCandidateLink recruitmentCandidateLink : recruitmentCandidateLinkList) {
						if (recruitmentCandidateLink.getCandidate_id().equals(candidateId)) {

							flag = true;
							break;
						}
					}
					if (flag == false) {
						RecruitmentProfileInfo recruitmentProfileInfo = new RecruitmentProfileInfo();
						recruitmentProfileInfo.setCreation_date(new Date());
						String profileId = recruitmentProfileInfoRepository.save(recruitmentProfileInfo)
								.getRecruitment_profile_info_id();

						RecruitProfileLinkDetails recruitProfileLinkDetails = new RecruitProfileLinkDetails();

						List<RecruitmentStageMapper> list = getStagesOfProcess(
								candidateWebsiteMapper.getRecruitmentProcessId());
						System.out.println("stageList>>>>" + list.toString());
						String stageIdd = list.get(1).getStageId();
						recruitProfileLinkDetails.setStage_id(stageIdd);
						recruitProfileLinkDetails.setProfile_id(profileId);
						recruitProfileLinkDetails.setOpp_id(candidateWebsiteMapper.getOpportunityId());
						recruitProfileLinkDetails.setRecruitment_id(candidateWebsiteMapper.getRecruitmentId());
						recruitProfileLinkDetails.setProcess_id(candidateWebsiteMapper.getRecruitmentProcessId());
						recruitProfileLinkDetails.setCreation_date(new Date());
						recruitProfileLinkDetails.setLive_ind(true);
						recruitProfileLinkDetails.setIntrestInd(candidateWebsiteMapper.isIntrestInd());
						recruitProfileLinkDetails.setRecruitOwner(candidateWebsiteMapper.getTagUserId());

						recruitProfileDetailsRepository.save(recruitProfileLinkDetails);
						System.out.println("profileId=1::::::::::" + profileId);
						RecruitmentCandidateLink recruitmentCandidateLink1 = new RecruitmentCandidateLink();

						recruitmentCandidateLink1.setCandidate_id(candidateId);
						recruitmentCandidateLink1.setOpportunity_id(candidateWebsiteMapper.getOpportunityId());
						recruitmentCandidateLink1
								.setRecruitment_process_id(candidateWebsiteMapper.getRecruitmentProcessId());
						recruitmentCandidateLink1.setProfileId(profileId);
						recruitmentCandidateLink1.setRecruitment_id(candidateWebsiteMapper.getRecruitmentId());
						recruitmentCandidateLink1.setCreation_date(new Date());
						recruitmentCandidateLink1.setLive_ind(true);
						recruitmentCandidateLink1.setUser_id(candidateWebsiteMapper.getTagUserId());
						candidateLinkRepository.save(recruitmentCandidateLink1);
						System.out.println(
								"profileId=" + candidateLinkRepository.save(recruitmentCandidateLink1).getProfileId()
										+ " " + "candidateId="
										+ candidateLinkRepository.save(recruitmentCandidateLink1).getCandidate_id());
						System.out.println("object:::::::::::"
								+ candidateLinkRepository.save(recruitmentCandidateLink1).toString());
					}
				}
			}

		} else {
			if (null != candiList && !candiList.isEmpty()) {
				for (String candidateId : candiList) {
					RecruitmentProfileInfo recruitmentProfileInfo = new RecruitmentProfileInfo();
					recruitmentProfileInfo.setCreation_date(new Date());
					String profileId = recruitmentProfileInfoRepository.save(recruitmentProfileInfo)
							.getRecruitment_profile_info_id();

					RecruitProfileLinkDetails recruitProfileLinkDetails = new RecruitProfileLinkDetails();

					List<RecruitmentStageMapper> list = getStagesOfProcess(
							candidateWebsiteMapper.getRecruitmentProcessId());
					System.out.println("stageList>>>>" + list.toString());
					String stageIdd = list.get(1).getStageId();
					recruitProfileLinkDetails.setStage_id(stageIdd);
					recruitProfileLinkDetails.setProfile_id(profileId);
					recruitProfileLinkDetails.setOpp_id(candidateWebsiteMapper.getOpportunityId());
					recruitProfileLinkDetails.setRecruitment_id(candidateWebsiteMapper.getRecruitmentId());
					recruitProfileLinkDetails.setProcess_id(candidateWebsiteMapper.getRecruitmentProcessId());
					recruitProfileLinkDetails.setCreation_date(new Date());
					recruitProfileLinkDetails.setLive_ind(true);
					recruitProfileLinkDetails.setIntrestInd(candidateWebsiteMapper.isIntrestInd());
					recruitProfileLinkDetails.setRecruitOwner(candidateWebsiteMapper.getTagUserId());

					recruitProfileDetailsRepository.save(recruitProfileLinkDetails);
					System.out.println("profileId=1::::::::::" + profileId);
					RecruitmentCandidateLink recruitmentCandidateLink1 = new RecruitmentCandidateLink();

					recruitmentCandidateLink1.setCandidate_id(candidateId);
					recruitmentCandidateLink1.setOpportunity_id(candidateWebsiteMapper.getOpportunityId());
					recruitmentCandidateLink1
							.setRecruitment_process_id(candidateWebsiteMapper.getRecruitmentProcessId());
					recruitmentCandidateLink1.setProfileId(profileId);
					recruitmentCandidateLink1.setRecruitment_id(candidateWebsiteMapper.getRecruitmentId());
					recruitmentCandidateLink1.setCreation_date(new Date());
					recruitmentCandidateLink1.setLive_ind(true);
					recruitmentCandidateLink1.setUser_id(candidateWebsiteMapper.getTagUserId());
					candidateLinkRepository.save(recruitmentCandidateLink1);
					System.out.println(
							"profileId=" + candidateLinkRepository.save(recruitmentCandidateLink1).getProfileId() + " "
									+ "candidateId="
									+ candidateLinkRepository.save(recruitmentCandidateLink1).getCandidate_id());
					System.out.println(
							"object:::::::::::" + candidateLinkRepository.save(recruitmentCandidateLink1).toString());
				}
			}
		}

	}

	@Override
	public HashMap getCandidateExternalAndInternalCountListByRecruitmentId(String recruitmentId) {
		int internalRecord = 0;
		int externalRecord = 0;
		HashMap map = new HashMap();
		List<RecruitmentCandidateLink> candidatelist = recruitmentCandidateLinkRepository
				.getCandidateLinkByRecruitmentId(recruitmentId);

		if (null != candidatelist && !candidatelist.isEmpty()) {

			for (RecruitmentCandidateLink recruitmentCandidateLink : candidatelist) {
				CandidateDetails candidateDetails = candidateDetailsRepository
						.getcandidateDetailsById(recruitmentCandidateLink.getCandidate_id());
				if (candidateDetails.getChannel().equalsIgnoreCase("self")) {

					internalRecord++;
				}
				if (candidateDetails.getChannel().equalsIgnoreCase("Website")) {
					externalRecord++;
				}
			}
			map.put("internalRecord", internalRecord);
			map.put("externalRecord", externalRecord);
		}
		return map;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getSalesOpenByuserIdAndDateRange(String userId) {

		LocalDate localDate = Utility.getLocalDateByDate(new Date());
		Date startDate1 = Utility.getUtilDateByLocalDate(localDate.minusDays(7));
		Date endDate = Utility.getUtilDateByLocalDate(localDate);

		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		List<OpportunitySalesUserLink> salesList = opportunitySalesUserRepository.getemployeeListByEmployeeId(userId);

		List<OpportunityRecruitDetails> recruitmentList = null;
		if (null != salesList && !salesList.isEmpty()) {
			for (OpportunitySalesUserLink opportunitySalesUserLink : salesList) {
				recruitmentList = recruitmentOpportunityDetailsRepository
						.getRecriutmentsByOpportunityId(opportunitySalesUserLink.getOpportunity_id());

			}
			if (null != recruitmentList && !recruitmentList.isEmpty()) {

				recruitmentList.stream().map(li -> {
					List<RecruitProfileStatusLink> recruterList = recruitmentProfileStatusLinkRepository
							.getRecruitProfileStatusLinkByRecruitmentId(li.getRecruitment_id());
					for (RecruitProfileStatusLink recruitProfileStatusLink : recruterList) {
						RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
						recruitmentOpportunityMapper.setProfileId(recruitProfileStatusLink.getProfileId());
						CandidateDetails candidateDetails = candidateDetailsRepository
								.getcandidateDetailsById(recruitProfileStatusLink.getCandidateId());
						recruitmentOpportunityMapper.setEmailId(candidateDetails.getEmailId());
						recruitmentOpportunityMapper.setCandidateName(candidateDetails.getFullName());

						mapperList.add(recruitmentOpportunityMapper);
					}

					return mapperList;

				}).collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentRecruitOwnerMapper> getTagCandidateRecruitOwnerListByRecriutmentId(String recriutmentId) {
		List<RecruitmentRecruitOwnerMapper> resultrList = new ArrayList<>();
		List<String> list = new ArrayList<>();
		// System.out.println("recId::::::::"+recriutmentId);
		List<RecruitProfileLinkDetails> profileLinkList = recruitmentProfileDetailsRepository
				.getProfileDetailByRecruitmentId(recriutmentId);
		// System.out.println("profileLinkList::::::::::::::::::"+profileLinkList.size());
		if (null != profileLinkList && !profileLinkList.isEmpty()) {

			for (RecruitProfileLinkDetails recruitProfileLinkDetails : profileLinkList) {
				// System.out.println("recruitProfileLinkDetails++++++++++"+recruitProfileLinkDetails.toString());
				if (!StringUtils.isEmpty(recruitProfileLinkDetails.getRecruitOwner())) {
					if (!list.contains(recruitProfileLinkDetails.getRecruitOwner())) {
						list.add(recruitProfileLinkDetails.getRecruitOwner());
						// System.out.println("list++++++++++"+list.toString());
					}
				}
			}
			for (String userId : list) {
				// System.out.println("userid++++++++++++++"+userId);
				RecruitmentRecruitOwnerMapper mapper = new RecruitmentRecruitOwnerMapper();
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userId);

				String middleName = " ";
				String lastName = " ";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {
					lastName = employeeDetails.getLastName();
				}
				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {
					middleName = employeeDetails.getMiddleName();
					mapper.setRecruitOwner(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					mapper.setRecruitOwnerId(employeeDetails.getEmployeeId());
				} else {
					mapper.setRecruitOwner(employeeDetails.getFirstName() + " " + lastName);
					mapper.setRecruitOwnerId(employeeDetails.getEmployeeId());
				}
				resultrList.add(mapper);
			}
		}
		return resultrList;
	}

	@Override
	@Scheduled(cron = "0 0 0 * * *")
	public void averageFeedBack() {
		/*
		 * int score = 0; float avg = 0; int count = 0;
		 * 
		 * List<RecruitmentCandidateLink> candidateList =
		 * recruitmentCandidateLinkRepository.getAllCandidateList();
		 * 
		 * if (null != candidateList && !candidateList.isEmpty()) { for
		 * (RecruitmentCandidateLink recruitmentCandidateLink : candidateList) {
		 * 
		 * List<RecruitStageNoteLink> recruitStageNoteList =
		 * recruitStageNotelinkRepository
		 * .getStageNoteLink(recruitmentCandidateLink.getProfileId());
		 * 
		 * for (RecruitStageNoteLink recruitStageNoteLink : recruitStageNoteList) {
		 * 
		 * 
		 * score += recruitStageNoteLink.getScore(); count++;
		 * System.out.println("count=" + count); } } System.out.println("TotalCount=" +
		 * count + "||" + count); System.out.println("score=" + score); } try { avg =
		 * (float) score / count; System.out.println("avg=" + avg + "||" + score /
		 * count); } catch (ArithmeticException e) { avg = 0; }
		 * 
		 * RecruitmentAverageFeedback recruitmentAverageFeedback =
		 * recruitmentAvgFeedbackRepository.
		 * findByCandidateId(recruitmentOpportunityMapper.getCandidateId()); if(null !=
		 * recruitmentAverageFeedback) { recruitmentAverageFeedback.setAverage(avg);
		 * recruitmentAvgFeedbackRepository.save(recruitmentAverageFeedback); }
		 * RecruitmentAverageFeedback newRecruitmentAverageFeedback = new
		 * RecruitmentAverageFeedback(); newRecruitmentAverageFeedback.setAverage(avg);
		 * recruitmentAvgFeedbackRepository.save(newRecruitmentAverageFeedback);
		 */
	}

	public HashMap getCandidateCountCameFromWebsitePerMonth(String startDate, String endDate) {

		int externalCandApplied = 0;
		int externalCandRegistered = 0;
		HashMap map = new HashMap();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<String> list = new ArrayList<>();

		List<RecruitmentCandidateLink> candidatelist = recruitmentCandidateLinkRepository
				.getCandidateLinkByDateRange(start_date, end_date);
		System.out.println("candidatelist++++++++++++++++++++" + candidatelist.size());
		if (null != candidatelist && !candidatelist.isEmpty()) {

			for (RecruitmentCandidateLink recruitmentCandidateLink : candidatelist) {

				if (!list.contains(recruitmentCandidateLink.getCandidate_id())) {
					list.add(recruitmentCandidateLink.getCandidate_id());
				}
			}
			for (String userId : list) {
				CandidateDetails candidateDetails = candidateDetailsRepository.getcandidateDetailsById(userId);

				if (candidateDetails.getChannel().equalsIgnoreCase("Website")) {
					System.out.println("channel++++++++++++++++++++" + candidateDetails.getChannel());
					externalCandApplied++;
					System.out.println("externalCandApplied++++++++++++++++++++" + externalCandApplied);
				}
			}
			map.put("externalCandApplied", externalCandApplied);
		}
		List<CandidateDetails> candidateDetails = candidateDetailsRepository.getCandidateListByDateRange(start_date,
				end_date);
		if (null != candidateDetails && !candidateDetails.isEmpty()) {
			for (CandidateDetails candidateDetails2 : candidateDetails) {
				CandidateDetails candidateDetails3 = candidateDetailsRepository
						.getcandidateDetailsById(candidateDetails2.getCandidateId());

				if (candidateDetails3.getChannel().equalsIgnoreCase("Website")) {
					externalCandRegistered++;
				}
			}
			map.put("externalCandRegistered", externalCandRegistered);
		}
		return map;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getDeletedRecriutmentListByOppId(String opportunityId, String orgId,
			String userId) {
		List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
				.getDeletedOpenRecriutmentsByOpportunityId(opportunityId);
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {
			recruitDetailsList.stream().map(li -> {

				RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
				recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
				recruitmentOpportunityMapper.setOpportunityId(li.getOpportunity_id());
				/*
				 * recruitmentOpportunityMapper.setStageId(li.getStage_id());
				 * recruitmentOpportunityMapper.setRecruitmentProcessId(li.getProcess_id());
				 * recruitmentOpportunityMapper.setProcessName(getProcessName(li.getProcess_id()
				 * ));
				 * recruitmentOpportunityMapper.setStageName(getStageName(li.getStage_id()));
				 * recruitmentOpportunityMapper.setProfileId(li.getProfile_id());
				 */
				recruitmentOpportunityMapper.setLocation(li.getLocation());
				recruitmentOpportunityMapper.setRequirementName(li.getName());
				recruitmentOpportunityMapper.setSponserId(li.getSponser_id());
				recruitmentOpportunityMapper.setDescription(li.getDescription());
				recruitmentOpportunityMapper.setNumber(li.getNumber());
				recruitmentOpportunityMapper.setBilling(li.getBilling());
				recruitmentOpportunityMapper.setCurrency(li.getCurrency());
				recruitmentOpportunityMapper.setType(li.getType());
				recruitmentOpportunityMapper.setAvilableDate(Utility.getISOFromDate(li.getAvailable_date()));
				recruitmentOpportunityMapper.setEndDate(Utility.getISOFromDate(li.getEnd_date()));
				recruitmentOpportunityMapper.setCloseByDate(Utility.getISOFromDate(li.getCloseByDate()));
				recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));

				recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcess(li.getRecruitment_process_id()));

				recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
				recruitmentOpportunityMapper.setExperience(li.getExperience());
				recruitmentOpportunityMapper.setLocation(li.getLocation());
				recruitmentOpportunityMapper.setCategory(li.getCategory());
				recruitmentOpportunityMapper.setCountry(li.getCountry());
				recruitmentOpportunityMapper.setDepartment(li.getDepartment());
				recruitmentOpportunityMapper.setRole(li.getRole());
				recruitmentOpportunityMapper.setRecruitmentProcessId(li.getRecruitment_process_id());
				recruitmentOpportunityMapper.setWorkPreference(li.getWorkPreferance());
				recruitmentOpportunityMapper.setCloseInd(li.isCloseInd());
				if (!StringUtils.isEmpty(li.getUserId())) {
					EmployeeDetails employee = employeeRepository.getEmployeeDetailsByEmployeeId(li.getUserId());
					recruitmentOpportunityMapper.setRecruitOwner(employee.getFullName());
				}
				List<RecruitProfileLinkDetails> onbordingList = recruitmentProfileDetailsRepository
						.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());
				if (null != onbordingList) {
					recruitmentOpportunityMapper.setOnBoardNo(onbordingList.size());
				}
				System.out.println("recruitId" + li.getRecruitment_id());
				List<RecruitmentPublishDetails> recruitmentPublishDetails = recruitmentPublishRepository
						.getDeletedRecruitmentPublishDetails(li.getRecruitment_id());
				if (null != recruitmentPublishDetails) {

					recruitmentOpportunityMapper.setPublishInd(true);
				} else {

					recruitmentOpportunityMapper.setPublishInd(false);
				}

				if (!StringUtils.isEmpty(li.getRecruiter_id())) {
					EmployeeDetails employeeDetails = employeeRepository
							.getEmployeeDetailsByEmployeeId(li.getRecruiter_id());
					String middleName = " ";
					String lastName = " ";
					if (employeeDetails.getMiddleName() != null) {

						middleName = employeeDetails.getMiddleName();
					}
					if (employeeDetails.getLastName() != null) {

						lastName = employeeDetails.getLastName();
					}

					recruitmentOpportunityMapper
							.setRecruiterName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				}

				if (!StringUtils.isEmpty(li.getSponser_id())) {

					ContactDetails contactDetails = contactRepository.getContactDetailsById(li.getSponser_id());
					String middleName = " ";
					String lastName = " ";
					if (null != contactDetails.getMiddle_name()) {

						middleName = contactDetails.getMiddle_name();
					}
					if (null != contactDetails.getLast_name()) {

						lastName = contactDetails.getLast_name();
					}
					recruitmentOpportunityMapper
							.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
				}

				/* Add skill set */
				recruitmentOpportunityMapper.setSkillSetList(candidateService.getSkillSetOfSkillLibery(orgId));
				List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
						.getDeletedRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
				if (recruitmentSkillsetLink.size() != 0) {
					recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

				}

				List<RecruitmentCandidateLink> recruitmentCandidateLink = candidateLinkRepository
						.getDeletedCandidateList(li.getRecruitment_id());
				System.out.println("recruitmentCandidateLink::::::" + recruitmentCandidateLink.toString());
				if (null != recruitmentCandidateLink && !recruitmentCandidateLink.isEmpty()) {
					System.out.println("size:::::::::" + recruitmentCandidateLink.size());

					List<CandidateMapper> resultList = new ArrayList<>();
					int count = 0;
					for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
						CandidateMapper mapper = new CandidateMapper();
						CandidateDetails candidate = candidateDetailsRepository
								.getcandidateDetailsById(candidateLink.getCandidate_id());
						if (null != candidate) {
							String middleName = " ";
							String lastName = " ";
							if (null != candidate.getMiddleName()) {

								middleName = candidate.getMiddleName();
							}
							if (null != candidate.getLastName()) {

								lastName = candidate.getLastName();
							}
							mapper.setCandidateId(candidate.getCandidateId());
							mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
							mapper.setImageId(candidate.getImageId());
						}
						resultList.add(mapper);
						count++;
						if (count == 2) {
							break;
						}
					}
					recruitmentOpportunityMapper.setCandidatetList(resultList);

					List<CandidateMapper> resultList2 = new ArrayList<>();
					for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
						CandidateMapper mapper = new CandidateMapper();
						CandidateDetails candidate = candidateDetailsRepository
								.getcandidateDetailsById(candidateLink.getCandidate_id());
						if (null != candidate) {
							String middleName = " ";
							String lastName = " ";
							if (null != candidate.getMiddleName()) {

								middleName = candidate.getMiddleName();
							}
							if (null != candidate.getLastName()) {

								lastName = candidate.getLastName();
							}
							mapper.setCandidateId(candidate.getCandidateId());
							mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
							mapper.setImageId(candidate.getImageId());
						}
						resultList2.add(mapper);
					}
					recruitmentOpportunityMapper.setFiltercandidatetList(resultList2);
				}
				recruitmentOpportunityMapper.setCandidateNo(candidateNo(recruitmentCandidateLink.size()));
				/*
				 * RecruitProfileStatusLink recruitProfileStatusLink =
				 * recruitmentProfileStatusLinkRepository
				 * .getRecruitProfileStatusLinkByProfileId(li.getProfile_id()); if (null !=
				 * recruitProfileStatusLink) {
				 * recruitmentOpportunityMapper.setApproveInd(recruitProfileStatusLink.
				 * isApproveInd());
				 * recruitmentOpportunityMapper.setRejectInd(recruitProfileStatusLink.
				 * isRejectInd()); }
				 */

				/*
				 * List<RecruitmentRecruiterLink> recruiterList =
				 * recruitmentRecruiterLinkRepository
				 * .getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id()); if (null
				 * != recruiterList && !recruiterList.isEmpty()) { List<String> resultList = new
				 * ArrayList<>(); for (RecruitmentRecruiterLink recruiter : recruiterList) {
				 * //EmployeeMapper mapper = new EmployeeMapper(); String recruiters = null;
				 * EmployeeDetails emp =
				 * employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
				 * System.out.println("name....." + emp.getFullName()); recruiters
				 * =emp.getFullName(); recruiters=emp.getId(); resultList.add(recruiters); }
				 * recruitmentOpportunityMapper.setRecruiterNames(resultList); }
				 */

				List<RecruitmentRecruiterLink> recruiterList = recruitmentRecruiterLinkRepository
						.getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id());
				if (null != recruiterList && !recruiterList.isEmpty()) {
					List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
					for (RecruitmentRecruiterLink recruiter : recruiterList) {
						EmployeeMapper mapper = new EmployeeMapper();
						EmployeeDetails emp = employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
						// System.out.println("name....." + emp.getFullName());
						mapper.setFullName(emp.getFullName());
						mapper.setEmployeeId(recruiter.getRecruiterId());

						resultList.add(mapper);
					}
					recruitmentOpportunityMapper.setRecruiterList(resultList);
				}

				List<RecruitmentAddressLink> recruitmentAddressLink = recruitmentAddressLinkRepository
						.getAddressListByRecruitmentId(li.getRecruitment_id());
				List<AddressMapper> addressList = new ArrayList<AddressMapper>();
				if (null != recruitmentAddressLink && !recruitmentAddressLink.isEmpty()) {

					for (RecruitmentAddressLink recruitmentAddressLink1 : recruitmentAddressLink) {
						AddressDetails addressDetails = addressRepository
								.getAddressDetailsByAddressId(recruitmentAddressLink1.getAddressId());

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
				recruitmentOpportunityMapper.setAddress(addressList);

				List<RecruitmentPartnerLink> recruitmentPartnerLinkList = recruitmentPartnerLinkRepository
						.findByRecruitmentIdAndLiveInd(li.getRecruitment_id(), true);
				if (null != recruitmentPartnerLinkList && !recruitmentPartnerLinkList.isEmpty()) {
					List<PartnerMapper> partnerResultList = new ArrayList<PartnerMapper>();
					for (RecruitmentPartnerLink partner : recruitmentPartnerLinkList) {
						PartnerMapper mapper = new PartnerMapper();
						System.out.println("partner::::::::::" + partner.getPartnerId());
						System.out.println("recID:::::::::::" + partner.getRecruitmentId());
						PartnerDetails dbPartner = partnerDetailsRepository
								.getPartnerDetailsById(partner.getPartnerId());
						if (null != dbPartner) {
							mapper.setPartnerName(dbPartner.getPartnerName());
							mapper.setPartnerId(dbPartner.getPartnerId());

							partnerResultList.add(mapper);
						}
					}
					recruitmentOpportunityMapper.setPartnerList(partnerResultList);
				}
				return mapperList.add(recruitmentOpportunityMapper);

			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getOpenRecuitmentByCustomerId(String customerId, String orgId) {
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();

		List<OpportunityDetails> oppCustomersList = opportunityDetailsRepository
				.getOpportunityListByCustomerIdAndLiveInd(customerId);
		if (null != oppCustomersList && !oppCustomersList.isEmpty()) {
			for (OpportunityDetails opportunityDetails : oppCustomersList) {
				List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
						.getRecriutmentsByOpportunityId(opportunityDetails.getOpportunityId());
				if (null != recruitList && !recruitList.isEmpty()) {
					for (OpportunityRecruitDetails opportunityRecruitDetails : recruitList) {
						System.out.println("recruitmentId=" + opportunityRecruitDetails.getRecruitment_id());
						List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(
										opportunityRecruitDetails.getRecruitment_id());
						System.out.println("profile Lits2@@@@@@@@@" + profileList.size());

						if (opportunityRecruitDetails.isCloseInd() == false) {
							System.out.println("start::::::::::::2");
							int profileSize = profileList.size();
							int positionSize = (int) opportunityRecruitDetails.getNumber();
							System.out.println("profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
							if (profileSize < positionSize) {
								System.out.println("start::::::::::::3");
								RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
								recruitmentOpportunityMapper
										.setRecruitmentId(opportunityRecruitDetails.getRecruitment_id());

								recruitmentOpportunityMapper.setBilling(opportunityRecruitDetails.getBilling());
								recruitmentOpportunityMapper.setRequirementName(opportunityRecruitDetails.getName());
								recruitmentOpportunityMapper.setNumber(opportunityRecruitDetails.getNumber());
								recruitmentOpportunityMapper.setAvilableDate(
										Utility.getISOFromDate(opportunityRecruitDetails.getAvailable_date()));
								recruitmentOpportunityMapper.setCreationDate(
										Utility.getISOFromDate(opportunityRecruitDetails.getCreationDate()));
								recruitmentOpportunityMapper.setCloseByDate(
										Utility.getISOFromDate(opportunityRecruitDetails.getCloseByDate()));
								recruitmentOpportunityMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
								recruitmentOpportunityMapper.setExperience(opportunityRecruitDetails.getExperience());
								recruitmentOpportunityMapper.setLocation(opportunityRecruitDetails.getLocation());
								// recruitmentOpportunityMapper.setStageName(getStageName(li.getStage_id()));
								// recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcess(li.getRecruitment_profile_details_id()));
								// recruitmentOpportunityMapper.setSt
								System.out.println("start::::::::::::4");
								if (!StringUtils.isEmpty(opportunityRecruitDetails.getSponser_id())) {

									ContactDetails contactDetails = contactRepository
											.getContactDetailsById(opportunityRecruitDetails.getSponser_id());
									String middleName = " ";
									String lastName = " ";
									if (null != contactDetails.getMiddle_name()) {

										middleName = contactDetails.getMiddle_name();
									}
									if (null != contactDetails.getLast_name()) {

										lastName = contactDetails.getLast_name();
									}
									recruitmentOpportunityMapper.setSponserName(
											contactDetails.getFirst_name() + " " + middleName + " " + lastName);
								}

								recruitmentOpportunityMapper.setOpprtunityName(opportunityDetails.getOpportunityName());
								if (opportunityDetails != null) {
									if (!StringUtils.isEmpty(opportunityDetails.getCustomerId())) {
										Customer customer = customerRepository
												.getCustomerDetailsByCustomerId(opportunityDetails.getCustomerId());
										recruitmentOpportunityMapper.setCustomerName(customer.getName());
									}
								}

								String skills = null;
								List<String> skillLibery = candidateService.getSkillSetOfSkillLibery(orgId);
								System.out.println("skillLibery============" + skillLibery + orgId);
								ArrayList<String> requiredSkills = new ArrayList<>();
								String description = opportunityRecruitDetails.getDescription().replace(",", " ");
								if (!StringUtils.isEmpty(description)) {
									// List<String> descriptionList = Arrays.asList(description.split("\\s*,\\s*"));

									List<String> descriptionList = Arrays.asList(description.split(" "));
									for (String word : skillLibery) {
										for (String description1 : descriptionList) {
											if (!requiredSkills.contains(word)) {
												if (description1.equalsIgnoreCase(word)) {
													requiredSkills.add(word);
												}
											}
										}
									}
									skills = requiredSkills.stream().map(n -> String.valueOf(n))
											.collect(Collectors.joining(","));
								}
								recruitmentOpportunityMapper.setSkillName(skills);

								if (!StringUtils.isEmpty(opportunityRecruitDetails.getUserId())) {
									EmployeeDetails employee = employeeRepository
											.getEmployeeDetailsByEmployeeId(opportunityRecruitDetails.getUserId());
									String middleName = " ";
									String lastName = "";

									if (!StringUtils.isEmpty(employee.getLastName())) {

										lastName = employee.getLastName();
									}
									if (employee.getMiddleName() != null && employee.getMiddleName().length() > 0) {
										middleName = employee.getMiddleName();
										recruitmentOpportunityMapper.setRecruitOwner(
												employee.getFirstName() + " " + middleName + " " + lastName);
									} else {
										recruitmentOpportunityMapper
												.setRecruitOwner(employee.getFirstName() + " " + lastName);
									}
								}

								List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
										.getCandidateList(opportunityRecruitDetails.getRecruitment_id());
								if (null != recruitmentCandidateLink) {
									recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
								}
								System.out.println("offer:::" + recruitmentCandidateLink.size());
								List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
										.getRecruitProfileStatusLinkByRecruitmentId(
												opportunityRecruitDetails.getRecruitment_id());
								if (null != recruitProfileStatusLink) {
									recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
								}
								System.out.println("closePosition::::::::" + recruitProfileStatusLink.size());
								List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
										.getProfileDetailByRecruitmentIdAndonBoardInd(
												opportunityRecruitDetails.getRecruitment_id());
								if (null != profileLinkDetailsList) {
									recruitmentOpportunityMapper.setOnBoardNo(profileLinkDetailsList.size());
								}
								List<RecruitProfileStatusLink> dropList = recruitmentProfileStatusLinkRepository
										.getRecruitDropListByRecruitmentId(
												opportunityRecruitDetails.getRecruitment_id());
								if (null != dropList) {
									recruitmentOpportunityMapper.setRejected(dropList.size());
								}
								System.out.println("start::::::::::::6");
								recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcessWithCandidate(
										opportunityRecruitDetails.getRecruitment_process_id(),
										opportunityRecruitDetails.getRecruitment_id()));
								mapperList.add(recruitmentOpportunityMapper);
								System.out.println("____________________________");
							}
						}
					}
				}
			}
		}
		return mapperList;
	}

	@Override
	public List<FunnelMapper> getFunelRecordByOrganizationId(String orgId) {
		List<FunnelMapper> resultMapper2 = new ArrayList<>();
		int openRequirement = 0;
		int numOfAllCandidate = 0;
		int numOfOfferCandidate = 0;
		int numOfOnBoardCandidate = 0;
		int position = 0;

		Date startDate = new Date();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(startDate));
			start_date = Utility.removeTime(startDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<OpportunityDetails> opportunityList = opportunityDetailsRepository.getopportunityDetailsByOrgId(orgId);

		for (OpportunityDetails opportunityDetails : opportunityList) {
			List<OpportunityRecruitDetails> recruitOppList = recruitmentOpportunityDetailsRepository
					.getRecriutmentsByOpportunityId(opportunityDetails.getOpportunityId());

			for (OpportunityRecruitDetails opportunityRecruitDetails : recruitOppList) {
				List<RecruitProfileLinkDetails> onbordedCandidateList = recruitProfileDetailsRepository
						.getProfileDetailByRecruitmentIdAndonBoardIndAndDateRange(
								opportunityRecruitDetails.getRecruitment_id(), start_date, end_date);
				if (opportunityRecruitDetails.isCloseInd() == false) {
					System.out.println("oppId::::::::::::" + opportunityDetails.getOpportunityId());
					int profileSize = onbordedCandidateList.size();
					int positionSize = (int) opportunityRecruitDetails.getNumber();
					System.out.println("profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
					if (profileSize < positionSize) {

						openRequirement += opportunityRecruitDetails.getNumber();

						numOfOnBoardCandidate += onbordedCandidateList.size();

						List<RecruitProfileLinkDetails> allCandidate = recruitmentProfileDetailsRepository
								.getProfileDetailByDateRange(opportunityRecruitDetails.getRecruitment_id(), start_date,
										end_date);
						numOfAllCandidate += allCandidate.size();
						List<RecruitProfileStatusLink> selectedCandidate = recruitmentProfileStatusLinkRepository
								.getRecruitProfileStatusLinkByRecruitmentIdOnDateRange(
										opportunityRecruitDetails.getRecruitment_id(), start_date, end_date);
						numOfOfferCandidate += selectedCandidate.size();

					}
				}

			}
		}
		position = openRequirement - numOfOnBoardCandidate;
		if (numOfOfferCandidate > numOfOnBoardCandidate) {
			numOfOfferCandidate = numOfOfferCandidate - numOfOnBoardCandidate;
		} else {
			numOfOfferCandidate = 0;
		}

		/*
		 * JSONArray funnelArray = new JSONArray();
		 * 
		 * JSONObject positionObjet = new JSONObject(); positionObjet.put("stage",
		 * "Positions"); positionObjet.put("value", position);
		 * 
		 * JSONObject onboardObjet = new JSONObject(); positionObjet.put("stage",
		 * "Submitted"); positionObjet.put("value", numOfAllCandidate);
		 * 
		 * JSONObject submittedObjet = new JSONObject(); positionObjet.put("stage",
		 * "Selected"); positionObjet.put("value", numOfOfferCandidate);
		 * 
		 * JSONObject selectedObjet = new JSONObject(); positionObjet.put("stage",
		 * "OnBoarded"); positionObjet.put("value", numOfOnBoardCandidate);
		 * 
		 * funnelArray.put(positionObjet); funnelArray.put(onboardObjet);
		 * funnelArray.put(submittedObjet); funnelArray.put(selectedObjet);
		 */

		FunnelMapper positionmapper = new FunnelMapper();
		positionmapper.setStage("Positions");
		positionmapper.setNumber(position);
		resultMapper2.add(positionmapper);

		FunnelMapper onboardmapper = new FunnelMapper();
		onboardmapper.setStage("Submitted");
		onboardmapper.setNumber(numOfAllCandidate);
		resultMapper2.add(onboardmapper);

		FunnelMapper submitedmapper = new FunnelMapper();
		submitedmapper.setStage("Selected");
		submitedmapper.setNumber(numOfOfferCandidate);
		resultMapper2.add(submitedmapper);

		FunnelMapper onboardedmapper = new FunnelMapper();
		onboardedmapper.setStage("OnBoarded");
		onboardedmapper.setNumber(numOfOnBoardCandidate);
		resultMapper2.add(onboardedmapper);

		/*
		 * map.put("Positions", position); map.put("Submitted", numOfAllCandidate);
		 * map.put("Selected", numOfOfferCandidate); map.put("OnBoarded",
		 * numOfOnBoardCandidate);
		 */
		// map.put("Open Requirements", openRequirement);

		return resultMapper2;
	}

	@Override
	public HashMap getSeedoMeterRecordByOrganizationId(String orgId) {
		HashMap map = new HashMap();
		int openRequirement = 0;
		int numOfOnBoardCandidate = 0;
		float onBoardedPer = 0;

		Date startDate = new Date();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(startDate));
			start_date = Utility.removeTime(startDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<OpportunityDetails> opportunityList = opportunityDetailsRepository.getopportunityDetailsByOrgId(orgId);

		for (OpportunityDetails opportunityDetails : opportunityList) {
			List<OpportunityRecruitDetails> recruitOppList = recruitmentOpportunityDetailsRepository
					.getRecriutmentsByOpportunityId(opportunityDetails.getOpportunityId());

			for (OpportunityRecruitDetails opportunityRecruitDetails : recruitOppList) {
				List<RecruitProfileLinkDetails> onbordedCandidateList = recruitProfileDetailsRepository
						.getProfileDetailByRecruitmentIdAndonBoardIndAndDateRange(
								opportunityRecruitDetails.getRecruitment_id(), start_date, end_date);
				if (opportunityRecruitDetails.isCloseInd() == false) {
					// System.out.println("oppId::::::::::::"+opportunityDetails.getOpportunityId());
					int profileSize = onbordedCandidateList.size();
					int positionSize = (int) opportunityRecruitDetails.getNumber();
					// System.out.println("profileSize="+profileSize+"||"+"positionSize="+positionSize);
					if (profileSize < positionSize) {

						openRequirement += opportunityRecruitDetails.getNumber();

						numOfOnBoardCandidate += onbordedCandidateList.size();
					}
				}
			}
		}

		onBoardedPer = (float) numOfOnBoardCandidate / openRequirement;
		float f = (float) (Math.round(onBoardedPer * 100) / 100.0);
		System.out.println("onBoardedPer##2=" + f);
		map.put("onBoardedPer", f);

		return map;
	}

	@Override
	public boolean stageExistsByWeightage(double probability, String recruitmentProcessId) {

		List<GlobalProcessStageLink> list = recruitmentProcessStageLinkRepository
				.getByRecruitmentProcessId(recruitmentProcessId);
		if (null != list && !list.isEmpty()) {
			for (GlobalProcessStageLink globalProcessStageLink : list) {
				RecruitmentProcessStageDetails recruitmentProcessStageDetails = recruitmentStageDetailsRepository
						.getByProbabilityAndRecruitmentStageId(probability,
								globalProcessStageLink.getRecruitmentStageId());
				if (recruitmentProcessStageDetails != null) {

					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<CustomerNetflixMapper> getCustomersPositionUserId(String userId) {
		List<CustomerNetflixMapper> mapperList = new ArrayList<>();
		List<Customer> customerList = customerRepository.findByUserIdandLiveInd(userId);
		customerList.stream().map(li -> {
			CustomerNetflixMapper mapper = new CustomerNetflixMapper();
			int requirement = 0;
			int selecttedNo = 0;
			int onBoarded = 0;

			String customerName = "";
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(li.getCustomerId());
			if (null != customer) {
				customerName = customer.getName();
			}

			List<OpportunityDetails> oppCustomersList = opportunityDetailsRepository
					.getOpportunityListByCustomerIdAndLiveInd(li.getCustomerId());
			if (null != oppCustomersList && !oppCustomersList.isEmpty()) {
				mapper.setOppNo(oppCustomersList.size());
				for (OpportunityDetails opportunityDetails : oppCustomersList) {
					List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
							.getRecriutmentsByOpportunityId(opportunityDetails.getOpportunityId());
					if (null != recruitList && !recruitList.isEmpty()) {
						for (OpportunityRecruitDetails opportunityRecruitDetails : recruitList) {
							System.out.println("recruitmentId=" + opportunityRecruitDetails.getRecruitment_id());

							if (opportunityRecruitDetails.isCloseInd() == false) {
								System.out.println("start::::::::::::2");
								List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
										.getProfileDetailByRecruitmentIdAndonBoardInd(
												opportunityRecruitDetails.getRecruitment_id());
								int profileSize = profileList.size();
								int positionSize = (int) opportunityRecruitDetails.getNumber();
								System.out
										.println("profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
								if (profileSize < positionSize) {
									requirement += opportunityRecruitDetails.getNumber();
									/*
									 * List<RecruitProfileStatusLink> selectedList =
									 * recruitmentProfileStatusLinkRepository.
									 * getRecruitProfileStatusLinkByRecruitmentId(userId); selecttedNo +=
									 * selectedList.size();
									 */
									onBoarded += profileSize;
								}
							}
						}
					}
				}
			}

			List<AddressMapper> addressList = new ArrayList<AddressMapper>();
			List<CustomerAddressLink> customerAddressList = customerAddressLinkRepository
					.getAddressListByCustomerId(li.getCustomerId());

			/* fetch customer address & set to customer mapper */
			if (null != customerAddressList && !customerAddressList.isEmpty()) {

				for (CustomerAddressLink customerAddressLink : customerAddressList) {
					AddressDetails addressDetails = addressRepository
							.getAddressDetailsByAddressId(customerAddressLink.getAddressId());

					AddressMapper addressMapper = new AddressMapper();
					if (null != addressDetails) {

						addressMapper.setAddress1(addressDetails.getAddressLine1());
						addressMapper.setAddress2(addressDetails.getAddressLine2());
						// addressMapper.setAddressType(addressDetails.getAddress_type());
						addressMapper.setPostalCode(addressDetails.getPostalCode());

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
						Country country1 = countryRepository
								.getCountryDetailsByCountryNameAndOrgId(addressDetails.getCountry(),li.getOrganizationId());
						if (null != country1) {
							addressMapper.setCountry_alpha2_code(country1.getCountry_alpha2_code());
							addressMapper.setCountry_alpha3_code(country1.getCountry_alpha3_code());
						}
						addressList.add(addressMapper);
					}
				}

				System.out.println("addressList.......... " + addressList);
			}
			mapper.setAddress(addressList);

			mapper.setCustomerId(li.getCustomerId());
			mapper.setName(customerName);
			mapper.setUserId(userId);
			mapper.setPosition(requirement);
			mapper.setOnBoarded(onBoarded);
			// mapper.setSelected(selecttedNo);
			mapperList.add(mapper);
			return mapperList;
		}).collect(Collectors.toList());

		return mapperList.stream().sorted(Comparator.comparingInt(CustomerNetflixMapper::getPosition).reversed())
				.limit(5).collect(Collectors.toList());
	}

	@Override
	public List<CustomerNetflixMapper> getAllCustomerCloserByUserIdAndDateRange(String userId, String startDate,
			String endDate) {

		List<CustomerNetflixMapper> mapperList = new ArrayList<>();
		List<Customer> customerList = customerRepository.findByUserIdandLiveInd(userId);
		customerList.stream().map(li -> {
			CustomerNetflixMapper mapper = new CustomerNetflixMapper();
			int requirement = 0;
			int onboarded = 0;
			int totalOnboarded = 0;
			String customerName = "";
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(li.getCustomerId());
			if (null != customer) {
				customerName = customer.getName();
			}

			List<OpportunityDetails> oppCustomersList = opportunityDetailsRepository
					.getOpportunityListByCustomerIdAndLiveInd(li.getCustomerId());
			if (null != oppCustomersList && !oppCustomersList.isEmpty()) {
				for (OpportunityDetails opportunityDetails : oppCustomersList) {
					List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
							.getRecriutmentsByOpportunityId(opportunityDetails.getOpportunityId());
					if (null != recruitList && !recruitList.isEmpty()) {
						for (OpportunityRecruitDetails opportunityRecruitDetails : recruitList) {
							if (opportunityRecruitDetails.isCloseInd() == false) {
								Date end_date = null;
								Date start_date = null;
								try {
									end_date = Utility.getDateAfterEndDate(
											Utility.removeTime(Utility.getDateFromISOString(endDate)));
									start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
								} catch (Exception e) {
									e.printStackTrace();
								}

								List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
										.getProfileDetailByRecruitmentIdAndonBoardInd(
												opportunityRecruitDetails.getRecruitment_id());
								System.out.println("start::::::::::::2");
								int profileSize = profileList.size();
								totalOnboarded += profileSize;
								int positionSize = (int) opportunityRecruitDetails.getNumber();
								System.out
										.println("profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
								if (profileSize < positionSize) {
									requirement += opportunityRecruitDetails.getNumber();

									for (RecruitProfileLinkDetails recruitProfileLinkDetails : profileList) {
										if (null != recruitProfileLinkDetails.getOnboard_date()) {
											if ((Utility.removeTime(recruitProfileLinkDetails.getOnboard_date())
													.equals(start_date))
													&& (Utility.removeTime(recruitProfileLinkDetails.getOnboard_date())
															.before(end_date))) {
												onboarded++;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			mapper.setCustomerId(li.getCustomerId());
			mapper.setName(customerName);
			mapper.setUserId(userId);
			mapper.setPosition(requirement - totalOnboarded);
			mapper.setOnBoarded(onboarded);
			mapperList.add(mapper);
			return mapperList;
		}).collect(Collectors.toList());

		return mapperList.stream().sorted(Comparator.comparingInt(CustomerNetflixMapper::getOnBoarded).reversed())
				.collect(Collectors.toList());
	}

	@Override
	public List<CustomerNetflixMapper> getAllCustomersPositonByUserId(String userId) {

		List<CustomerNetflixMapper> mapperList = new ArrayList<>();
		List<Customer> customerList = customerRepository.findByUserIdandLiveInd(userId);
		customerList.stream().map(li -> {
			CustomerNetflixMapper mapper = new CustomerNetflixMapper();
			int requirement = 0;
			int selecttedNo = 0;
			int onBoarded = 0;
			String customerName = "";
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(li.getCustomerId());
			if (null != customer) {
				customerName = customer.getName();
			}

			List<OpportunityDetails> oppCustomersList = opportunityDetailsRepository
					.getOpportunityListByCustomerIdAndLiveInd(li.getCustomerId());
			if (null != oppCustomersList && !oppCustomersList.isEmpty()) {
				for (OpportunityDetails opportunityDetails : oppCustomersList) {
					List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
							.getRecriutmentsByOpportunityId(opportunityDetails.getOpportunityId());
					if (null != recruitList && !recruitList.isEmpty()) {
						for (OpportunityRecruitDetails opportunityRecruitDetails : recruitList) {
							System.out.println("recruitmentId=" + opportunityRecruitDetails.getRecruitment_id());

							if (opportunityRecruitDetails.isCloseInd() == false) {
								List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
										.getProfileDetailByRecruitmentIdAndonBoardInd(
												opportunityRecruitDetails.getRecruitment_id());
								System.out.println("start::::::::::::2");
								int profileSize = profileList.size();
								int positionSize = (int) opportunityRecruitDetails.getNumber();
								System.out
										.println("profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
								if (profileSize < positionSize) {
									requirement += opportunityRecruitDetails.getNumber();
									/*
									 * List<RecruitProfileStatusLink> selectedList =
									 * recruitmentProfileStatusLinkRepository.
									 * getRecruitProfileStatusLinkByRecruitmentId(userId); selecttedNo +=
									 * selectedList.size();
									 */
									onBoarded += profileSize;
								}
							}
						}
					}
				}
			}
			mapper.setCustomerId(li.getCustomerId());
			mapper.setName(customerName);
			mapper.setUserId(userId);
			mapper.setPosition(requirement);
			// mapper.setSelected(selecttedNo);
			mapper.setOnBoarded(onBoarded);
			mapperList.add(mapper);
			return mapperList;
		}).collect(Collectors.toList());

		return mapperList;
	}

	@Override
	public List<CustomerNetflixMapper> getCustomerCloserByUserIdAndDateRange(String userId, String startDate,
			String endDate) {

		List<CustomerNetflixMapper> mapperList = new ArrayList<>();
		List<Customer> customerList = customerRepository.findByUserIdandLiveInd(userId);
		customerList.stream().map(li -> {
			CustomerNetflixMapper mapper = new CustomerNetflixMapper();
			int onboarded = 0;
			int requirement = 0;
			int totalOnboarded = 0;
			String customerName = "";
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(li.getCustomerId());
			if (null != customer) {
				customerName = customer.getName();
			}

			List<OpportunityDetails> oppCustomersList = opportunityDetailsRepository
					.getOpportunityListByCustomerIdAndLiveInd(li.getCustomerId());
			if (null != oppCustomersList && !oppCustomersList.isEmpty()) {
				mapper.setOppNo(oppCustomersList.size());
				for (OpportunityDetails opportunityDetails : oppCustomersList) {
					List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
							.getRecriutmentsByOpportunityId(opportunityDetails.getOpportunityId());
					if (null != recruitList && !recruitList.isEmpty()) {
						for (OpportunityRecruitDetails opportunityRecruitDetails : recruitList) {
							if (opportunityRecruitDetails.isCloseInd() == false) {
								List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
										.getProfileDetailByRecruitmentIdAndonBoardInd(
												opportunityRecruitDetails.getRecruitment_id());

								System.out.println("start::::::::::::2");
								int profileSize = profileList.size();
								totalOnboarded += profileSize;
								int positionSize = (int) opportunityRecruitDetails.getNumber();
								System.out
										.println("profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
								if (profileSize < positionSize) {
									requirement += opportunityRecruitDetails.getNumber();
									Date end_date = null;
									Date start_date = null;
									try {
										end_date = Utility.getDateAfterEndDate(
												Utility.removeTime(Utility.getDateFromISOString(endDate)));
										start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
									} catch (Exception e) {
										e.printStackTrace();
									}

									for (RecruitProfileLinkDetails recruitProfileLinkDetails : profileList) {
										if ((Utility.removeTime(recruitProfileLinkDetails.getOnboard_date())
												.equals(start_date))
												&& (Utility.removeTime(recruitProfileLinkDetails.getOnboard_date())
														.before(end_date))) {
											onboarded++;
										}
									}

								}
							}
						}
					}
				}
			}

			List<AddressMapper> addressList = new ArrayList<AddressMapper>();
			List<CustomerAddressLink> customerAddressList = customerAddressLinkRepository
					.getAddressListByCustomerId(li.getCustomerId());

			/* fetch customer address & set to customer mapper */
			if (null != customerAddressList && !customerAddressList.isEmpty()) {

				for (CustomerAddressLink customerAddressLink : customerAddressList) {
					AddressDetails addressDetails = addressRepository
							.getAddressDetailsByAddressId(customerAddressLink.getAddressId());

					AddressMapper addressMapper = new AddressMapper();
					if (null != addressDetails) {

						addressMapper.setAddress1(addressDetails.getAddressLine1());
						addressMapper.setAddress2(addressDetails.getAddressLine2());
						// addressMapper.setAddressType(addressDetails.getAddress_type());
						addressMapper.setPostalCode(addressDetails.getPostalCode());

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
						Country country1 = countryRepository
								.getCountryDetailsByCountryNameAndOrgId(addressDetails.getCountry(),li.getOrganizationId());
						if (null != country1) {
							addressMapper.setCountry_alpha2_code(country1.getCountry_alpha2_code());
							addressMapper.setCountry_alpha3_code(country1.getCountry_alpha3_code());
						}
						addressList.add(addressMapper);
					}
				}

				System.out.println("addressList.......... " + addressList);
			}
			mapper.setAddress(addressList);

			mapper.setCustomerId(li.getCustomerId());
			mapper.setName(customerName);
			mapper.setUserId(userId);
			mapper.setOnBoarded(onboarded);
			mapper.setPosition(requirement - totalOnboarded);
			mapperList.add(mapper);
			return mapperList;
		}).collect(Collectors.toList());

		return mapperList.stream().sorted(Comparator.comparingInt(CustomerNetflixMapper::getOnBoarded).reversed())
				.limit(5).collect(Collectors.toList());

	}

	@Override
	public RecruitmentCloseRuleMapper updateRecruitmentCloseRule(
			RecruitmentCloseRuleMapper recruitmentCloseRuleMapper) {
		RecruitmentCloseRuleMapper resultMapper = new RecruitmentCloseRuleMapper();

		RecruitmentCloseRule recruitmentCloseRule = recruitmentCloseRuleRepository
				.findByOrgId(recruitmentCloseRuleMapper.getOrgId());

		if (null != recruitmentCloseRule) {
			System.out.println(recruitmentCloseRule.getRecruitmentCloseRuleId());
			recruitmentCloseRule.setOrgId(recruitmentCloseRuleMapper.getOrgId());
			recruitmentCloseRule.setTimePeriod(recruitmentCloseRuleMapper.getTimePeriod());
			recruitmentCloseRule.setOppTimePeriod(recruitmentCloseRuleMapper.getOppTimePeriod());
			recruitmentCloseRule.setOrderTimePeriod(recruitmentCloseRuleMapper.getOrderTimePeriod());
			recruitmentCloseRule.setUserId(recruitmentCloseRuleMapper.getUserId());
			recruitmentCloseRule.setCreationDate(new Date());
			recruitmentCloseRule.setInspectionRequiredInd(recruitmentCloseRuleMapper.isInspectionRequiredInd());
			recruitmentCloseRule.setJobAniEmailInd(recruitmentCloseRuleMapper.isJobAniEmailInd());
			recruitmentCloseRule.setBirthdayEmailInd(recruitmentCloseRuleMapper.isBirthdayEmailInd());
//			recruitmentCloseRule.setRepairInd(recruitmentCloseRuleMapper.isRepairInd());
//			recruitmentCloseRule.setProductionInd(recruitmentCloseRuleMapper.isProductionInd());
//			recruitmentCloseRule.setMakeToInd(recruitmentCloseRuleMapper.isMakeToInd());
			recruitmentCloseRule.setIndependentInd(recruitmentCloseRuleMapper.isIndependentInd());
			recruitmentCloseRule.setPartNoInd(recruitmentCloseRuleMapper.isPartNoInd());
			recruitmentCloseRule.setTrnsfrEvthngToErpInd(recruitmentCloseRuleMapper.isTrnsfrEvthngToErpInd());
			recruitmentCloseRule.setTrnsfrToErpQtionWinInd(recruitmentCloseRuleMapper.isTrnsfrToErpQtionWinInd());
			recruitmentCloseRule.setProcessInd(recruitmentCloseRuleMapper.isProcessInd());
			recruitmentCloseRule.setTypeInd(recruitmentCloseRuleMapper.isTypeInd());
			recruitmentCloseRule.setFifoInd(recruitmentCloseRuleMapper.isFifoInd());
			recruitmentCloseRule.setProInd(recruitmentCloseRuleMapper.isProInd());
			recruitmentCloseRule.setRepairOrdInd(recruitmentCloseRuleMapper.isRepairOrdInd());
			recruitmentCloseRule.setRepairProcessInd(recruitmentCloseRuleMapper.isRepairProcessInd());
			recruitmentCloseRule.setQcInd(recruitmentCloseRuleMapper.isQcInd());
			recruitmentCloseRuleRepository.save(recruitmentCloseRule);
		} else {
			RecruitmentCloseRule newRecruitmentCloseRule = new RecruitmentCloseRule();

			System.out.println(newRecruitmentCloseRule.getRecruitmentCloseRuleId());
			newRecruitmentCloseRule.setOrgId(recruitmentCloseRuleMapper.getOrgId());
			newRecruitmentCloseRule.setOppTimePeriod(recruitmentCloseRuleMapper.getOppTimePeriod());
			newRecruitmentCloseRule.setTimePeriod(recruitmentCloseRuleMapper.getTimePeriod());
			newRecruitmentCloseRule.setOrderTimePeriod(recruitmentCloseRuleMapper.getOrderTimePeriod());
			newRecruitmentCloseRule.setUserId(recruitmentCloseRuleMapper.getUserId());
			newRecruitmentCloseRule.setCreationDate(new Date());
			newRecruitmentCloseRule.setInspectionRequiredInd(recruitmentCloseRuleMapper.isInspectionRequiredInd());
			newRecruitmentCloseRule.setJobAniEmailInd(recruitmentCloseRuleMapper.isJobAniEmailInd());
			newRecruitmentCloseRule.setBirthdayEmailInd(recruitmentCloseRuleMapper.isBirthdayEmailInd());
//			newRecruitmentCloseRule.setRepairInd(recruitmentCloseRuleMapper.isRepairInd());
//			newRecruitmentCloseRule.setProductionInd(recruitmentCloseRuleMapper.isProductionInd());
//			newRecruitmentCloseRule.setMakeToInd(recruitmentCloseRuleMapper.isMakeToInd());
			newRecruitmentCloseRule.setIndependentInd(recruitmentCloseRuleMapper.isIndependentInd());
			newRecruitmentCloseRule.setPartNoInd(recruitmentCloseRuleMapper.isPartNoInd());
			newRecruitmentCloseRule.setTrnsfrEvthngToErpInd(recruitmentCloseRuleMapper.isTrnsfrEvthngToErpInd());
			newRecruitmentCloseRule.setTrnsfrToErpQtionWinInd(recruitmentCloseRuleMapper.isTrnsfrToErpQtionWinInd());
			newRecruitmentCloseRule.setProcessInd(recruitmentCloseRuleMapper.isProcessInd());
			newRecruitmentCloseRule.setTypeInd(recruitmentCloseRuleMapper.isTypeInd());
			newRecruitmentCloseRule.setFifoInd(recruitmentCloseRuleMapper.isFifoInd());
			newRecruitmentCloseRule.setProInd(recruitmentCloseRuleMapper.isProInd());
			newRecruitmentCloseRule.setRepairOrdInd(recruitmentCloseRuleMapper.isRepairOrdInd());
			newRecruitmentCloseRule.setRepairProcessInd(recruitmentCloseRuleMapper.isRepairProcessInd());
			newRecruitmentCloseRule.setQcInd(recruitmentCloseRuleMapper.isQcInd());
			
			recruitmentCloseRuleRepository.save(newRecruitmentCloseRule);

		}
		resultMapper = getRecruitmentCloseRuleByOrgId(recruitmentCloseRuleMapper.getOrgId());
		return resultMapper;
	}

	@Override
	public RecruitmentCloseRuleMapper getRecruitmentCloseRuleByOrgId(String orgId) {
		RecruitmentCloseRuleMapper resultMapper = new RecruitmentCloseRuleMapper();

		RecruitmentCloseRule recruitmentCloseRule = recruitmentCloseRuleRepository.findByOrgId(orgId);

		if (null != recruitmentCloseRule) {
			System.out.println(recruitmentCloseRule.getRecruitmentCloseRuleId());
			resultMapper.setOrgId(recruitmentCloseRule.getOrgId());
			resultMapper.setTimePeriod(recruitmentCloseRule.getTimePeriod());
			resultMapper.setOppTimePeriod(recruitmentCloseRule.getOppTimePeriod());
			resultMapper.setUserId(recruitmentCloseRule.getUserId());
			resultMapper.setCreationDate(Utility.getISOFromDate(recruitmentCloseRule.getCreationDate()));
			resultMapper.setInspectionRequiredInd(recruitmentCloseRule.isInspectionRequiredInd());
			resultMapper.setJobAniEmailInd(recruitmentCloseRule.isJobAniEmailInd());
			resultMapper.setBirthdayEmailInd(recruitmentCloseRule.isBirthdayEmailInd());
//			resultMapper.setRepairInd(recruitmentCloseRule.isRepairInd());
//			resultMapper.setProductionInd(recruitmentCloseRule.isProductionInd());
//			resultMapper.setMakeToInd(recruitmentCloseRule.isMakeToInd());
			resultMapper.setIndependentInd(recruitmentCloseRule.isIndependentInd());
			resultMapper.setPartNoInd(recruitmentCloseRule.isPartNoInd());
			resultMapper.setTrnsfrEvthngToErpInd(recruitmentCloseRule.isTrnsfrEvthngToErpInd());
			resultMapper.setTrnsfrToErpQtionWinInd(recruitmentCloseRule.isTrnsfrToErpQtionWinInd());
			resultMapper.setProcessInd(recruitmentCloseRule.isProcessInd());			
			resultMapper.setTypeInd(recruitmentCloseRule.isTypeInd());
			resultMapper.setFifoInd(recruitmentCloseRule.isFifoInd());
			resultMapper.setProInd(recruitmentCloseRule.isProInd());
			resultMapper.setRepairOrdInd(recruitmentCloseRule.isRepairOrdInd());
			resultMapper.setQcInd(recruitmentCloseRule.isQcInd());
			resultMapper.setRepairProcessInd(recruitmentCloseRule.isRepairProcessInd());
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeDetailsByEmployeeId(recruitmentCloseRule.getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					resultMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					resultMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		return resultMapper;
	}

	@Override
	public List<CustomerRecruitmentMapper> getCustomerRecruitSummaryListByOrgId(String organizationId) {

		List<CustomerRecruitmentMapper> resultList = new ArrayList<CustomerRecruitmentMapper>();
		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getopportunityDetailsByOrgId(organizationId);

		HashMap<String, List<OpportunityDetails>> map = new HashMap<>();

		if (null != opportunityList && !opportunityList.isEmpty()) {
			for (OpportunityDetails opportunityDetails : opportunityList) {

//               System.out.println("opportunityId:"+opportunitySalesUserLink.getOpportunity_id());
				// opportunity.getCustomerId().trim();

				if (opportunityDetails.getCustomerId() == null || opportunityDetails.getCustomerId() == ""
						|| opportunityDetails.getCustomerId().length() == 0) {

				} else {

					if (map.keySet().contains(opportunityDetails.getCustomerId())) {
						map.get(opportunityDetails.getCustomerId()).add(opportunityDetails);

					} else {
						List<OpportunityDetails> ll = new ArrayList<>();
						ll.add(opportunityDetails);
						map.put(opportunityDetails.getCustomerId(), ll);
					}
				}
			}
		}
		// CustomerRecruitmentMapper mapper = new CustomerRecruitmentMapper();
		System.out.println("CUSTOMERIDS=" + map.keySet());
		map.keySet().stream().map(li -> {

			CustomerRecruitmentMapper mapper = new CustomerRecruitmentMapper();

			System.out.println("The Key Is " + li);
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(li);
			System.out.println("The customer Id>>>>>>>>>> " + li);
			if (null != customer) {
				mapper.setCustomerName(customer.getName());
			} else {
				mapper.setCustomerName("Customer not available.");
			}
			long recruitmentNo = 0;
			int onBordedNo = 0;
			int selectedNo = 0;
			int position = 0;
			System.out.println("start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			for (OpportunityDetails opp : map.get(li)) {
				System.out.println("the opp. id is : " + opp.getOpportunityId());
				List<OpportunityRecruitDetails> openRecruitmentList = recruitmentOpportunityDetailsRepository
						.getOpenRecruitmentListByOppId(opp.getOpportunityId());

				for (OpportunityRecruitDetails openRecruitmentListNo : openRecruitmentList) {

					recruitmentNo += openRecruitmentListNo.getNumber();
					List<RecruitProfileLinkDetails> onbordedCandidate = recruitmentProfileDetailsRepository
							.getProfileDetailByRecruitmentIdAndonBoardInd(openRecruitmentListNo.getRecruitment_id());
					onBordedNo += onbordedCandidate.size();

					List<RecruitProfileStatusLink> selectedCandidate = recruitmentProfileStatusLinkRepository
							.getRecruitProfileStatusLinkByRecruitmentId(openRecruitmentListNo.getRecruitment_id());
					selectedNo += selectedCandidate.size();

					System.out.println("recruitmentNo=" + recruitmentNo);
					System.out.println("onBordedNo=" + onBordedNo);
					System.out.println("selectedNo=" + selectedNo);
				}
			}
			System.out.println("recruitmentNo=" + recruitmentNo);
			System.out.println("onBordedNo=" + onBordedNo);
			System.out.println("selectedNo=" + selectedNo);
			position = (int) recruitmentNo - onBordedNo;
			System.out.println("position=" + position);
			mapper.setOpenRequirmentNo((int) recruitmentNo);
			mapper.setOnboardedNo(onBordedNo);
			mapper.setSelectedNo(selectedNo);
			mapper.setPosition(position);
			resultList.add(mapper);
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END");
			return resultList;
		}).collect(Collectors.toList());

		return resultList.stream().filter(o -> o.getPosition() > 0).collect(Collectors.toList());
	}

	@Override
	public List<CustomerNetflixMapper> getAllCustomerAlphabetByUserId(String userId) {
		List<CustomerNetflixMapper> resultList = new ArrayList<>();
		List<Customer> customerList = customerRepository.findByUserIdandLiveInd(userId);
		List<Customer> customerList5 = customerList.stream()
				.sorted((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName())).collect(Collectors.toList());
		System.out.println("customer=" + customerList5.size());
		if (null != customerList5 && !customerList5.isEmpty()) {
			customerList5.stream().map(li -> {
				CustomerNetflixMapper mapper = new CustomerNetflixMapper();
				int requirement = 0;
				int selecttedNo = 0;
				int onBoarded = 0;

				List<OpportunityDetails> oppCustomersList = opportunityDetailsRepository
						.getOpportunityListByCustomerIdAndLiveInd(li.getCustomerId());
				if (null != oppCustomersList && !oppCustomersList.isEmpty()) {
					for (OpportunityDetails opportunityDetails : oppCustomersList) {
						List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
								.getRecriutmentsByOpportunityId(opportunityDetails.getOpportunityId());
						if (null != recruitList && !recruitList.isEmpty()) {
							for (OpportunityRecruitDetails opportunityRecruitDetails : recruitList) {
								System.out.println("recruitmentId=" + opportunityRecruitDetails.getRecruitment_id());

								if (opportunityRecruitDetails.isCloseInd() == false) {
									System.out.println("start::::::::::::2");
									List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
											.getProfileDetailByRecruitmentIdAndonBoardInd(
													opportunityRecruitDetails.getRecruitment_id());
									int profileSize = profileList.size();
									int positionSize = (int) opportunityRecruitDetails.getNumber();
									System.out.println(
											"profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
									if (profileSize < positionSize) {
										requirement += opportunityRecruitDetails.getNumber();
										/*
										 * List<RecruitProfileStatusLink> selectedList =
										 * recruitmentProfileStatusLinkRepository.
										 * getRecruitProfileStatusLinkByRecruitmentId(userId); selecttedNo +=
										 * selectedList.size();
										 */
										onBoarded += profileSize;
									}
								}
							}
						}
					}
				}
				mapper.setCustomerId(li.getCustomerId());
				mapper.setName(li.getName());
				mapper.setUserId(li.getUserId());
				mapper.setPosition(requirement);
				// mapper.setSelected(selecttedNo);
				mapper.setOnBoarded(onBoarded);
				resultList.add(mapper);
				return resultList;
			}).collect(Collectors.toList());
		}
		return resultList;
	}

	@Override
	public List<CustomerNetflixMapper> getAlphabetOrderCustomersByUserId(String userId) {
		List<CustomerNetflixMapper> resultList = new ArrayList<>();
		List<Customer> customerList = customerRepository.findByUserIdandLiveInd(userId);
		if (null != customerList && !customerList.isEmpty()) {
			List<Customer> customerList5 = customerList.stream()
//					.sorted(Comparator.comparing(o -> o != null ? o.getName() : null)).limit(5)
				    .sorted(Comparator.comparing(o -> o.getName(), Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))).limit(5)
					.collect(Collectors.toList());
			System.out.println("customer=" + customerList5.size());
			if (null != customerList5 && !customerList5.isEmpty()) {
				customerList5.stream().map(li -> {

					CustomerNetflixMapper mapper = new CustomerNetflixMapper();
					int requirement = 0;
					int selecttedNo = 0;
					int onBoarded = 0;

					List<OpportunityDetails> oppCustomersList = opportunityDetailsRepository
							.getOpportunityListByCustomerIdAndLiveInd(li.getCustomerId());
					if (null != oppCustomersList && !oppCustomersList.isEmpty()) {
						mapper.setOppNo(oppCustomersList.size());
						for (OpportunityDetails opportunityDetails : oppCustomersList) {
							List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
									.getRecriutmentsByOpportunityId(opportunityDetails.getOpportunityId());
							if (null != recruitList && !recruitList.isEmpty()) {
								for (OpportunityRecruitDetails opportunityRecruitDetails : recruitList) {
									System.out
											.println("recruitmentId=" + opportunityRecruitDetails.getRecruitment_id());

									if (opportunityRecruitDetails.isCloseInd() == false) {
										System.out.println("start::::::::::::2");
										List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
												.getProfileDetailByRecruitmentIdAndonBoardInd(
														opportunityRecruitDetails.getRecruitment_id());
										int profileSize = profileList.size();
										int positionSize = (int) opportunityRecruitDetails.getNumber();
										System.out.println(
												"profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
										if (profileSize < positionSize) {
											requirement += opportunityRecruitDetails.getNumber();
											/*
											 * List<RecruitProfileStatusLink> selectedList =
											 * recruitmentProfileStatusLinkRepository.
											 * getRecruitProfileStatusLinkByRecruitmentId(userId); selecttedNo +=
											 * selectedList.size();
											 */
											onBoarded += profileSize;
										}
									}
								}
							}
						}
					}

					List<AddressMapper> addressList = new ArrayList<AddressMapper>();
					List<CustomerAddressLink> customerAddressList = customerAddressLinkRepository
							.getAddressListByCustomerId(li.getCustomerId());

					/* fetch customer address & set to customer mapper */
					if (null != customerAddressList && !customerAddressList.isEmpty()) {

						for (CustomerAddressLink customerAddressLink : customerAddressList) {
							AddressDetails addressDetails = addressRepository
									.getAddressDetailsByAddressId(customerAddressLink.getAddressId());

							AddressMapper addressMapper = new AddressMapper();
							if (null != addressDetails) {

								addressMapper.setAddress1(addressDetails.getAddressLine1());
								addressMapper.setAddress2(addressDetails.getAddressLine2());
								// addressMapper.setAddressType(addressDetails.getAddress_type());
								addressMapper.setPostalCode(addressDetails.getPostalCode());

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
								Country country1 = countryRepository
										.getCountryDetailsByCountryNameAndOrgId(addressDetails.getCountry(),li.getOrganizationId());
								if (null != country1) {
									addressMapper.setCountry_alpha2_code(country1.getCountry_alpha2_code());
									addressMapper.setCountry_alpha3_code(country1.getCountry_alpha3_code());
								}
								addressList.add(addressMapper);
							}
						}

						System.out.println("addressList.......... " + addressList);
					}
					mapper.setAddress(addressList);

					mapper.setCustomerId(li.getCustomerId());
					mapper.setName(li.getName());
					mapper.setUserId(li.getUserId());
					mapper.setPosition(requirement - onBoarded);
					mapper.setOnBoarded(onBoarded);
					resultList.add(mapper);
					return resultList;
				}).collect(Collectors.toList());
			}
		}
		return resultList;
	}

	@Override
	public RecruitmentStageMapper updateReruitmentStage(RecruitmentStageMapper recruitmentStageMapper) {
		RecruitmentStageMapper resultMapper = new RecruitmentStageMapper();

		RecruitmentProcessStageDetails recruitmentProcessStageDetails = recruitmentStageDetailsRepository
				.findByRecruitmentStageId(recruitmentStageMapper.getStageId());
		if (null != recruitmentProcessStageDetails) {
			if (null != recruitmentStageMapper.getResponsible()) {

				recruitmentProcessStageDetails.setResponsible(recruitmentStageMapper.getResponsible());
			}
		}
		resultMapper = getStageByStageId(recruitmentStageMapper.getStageId());
		return resultMapper;
	}

	private RecruitmentStageMapper getStageByStageId(String stageId) {
		RecruitmentStageMapper recruitmentStageMapper = new RecruitmentStageMapper();

		RecruitmentProcessStageDetails recruitmentProcessStageDetails = recruitmentStageDetailsRepository
				.findByRecruitmentStageId(stageId);
		if (null != recruitmentProcessStageDetails) {
			recruitmentStageMapper.setStageId(recruitmentProcessStageDetails.getRecruitmentStageId());
			recruitmentStageMapper.setStageName(recruitmentProcessStageDetails.getStage_name());
			recruitmentStageMapper.setProbability(recruitmentProcessStageDetails.getProbability());
			recruitmentStageMapper.setDays(recruitmentProcessStageDetails.getDays());
			recruitmentStageMapper.setPublishInd(recruitmentProcessStageDetails.isPublishInd());
			recruitmentStageMapper.setResponsible(recruitmentProcessStageDetails.getResponsible());
		}
		return recruitmentStageMapper;
	}

	@Scheduled(cron = "0 0 0 * * *")
	private void closeOpenrquirement() throws Exception {

		List<RecruitmentCloseRule> ruleList = recruitmentCloseRuleRepository.findAll();
		for (RecruitmentCloseRule recruitmentCloseRule : ruleList) {
			List<OpportunityDetails> oppList = opportunityDetailsRepository
					.findByOrgIdAndReinstateIndAndLiveIndAndCloseIndAndLostIndAndWonInd(recruitmentCloseRule.getOrgId(),
							true, true, false, false, false);

			for (OpportunityDetails opportunityDetails : oppList) {
				List<OpportunityRecruitDetails> openRequirementList = recruitmentOpportunityDetailsRepository
						.getOpenRecriutmentsByOpportunityIdAndCloseInd(opportunityDetails.getOpportunityId());

				for (OpportunityRecruitDetails OpportunityRecruitDetails : openRequirementList) {
					if (recruitmentCloseRule.getTimePeriod() != 0) {
						Date todayDate = Utility.removeTime(new Date());
						if (null != OpportunityRecruitDetails.getEnd_date()) {
							Date closeDate = Utility.removeTime(Utility.getPlusMonth(
									OpportunityRecruitDetails.getEnd_date(), recruitmentCloseRule.getTimePeriod()));

							System.out.println(closeDate + "||" + todayDate);
							System.out.println("recid===================================="
									+ OpportunityRecruitDetails.getRecruitment_id());
							if (closeDate.compareTo(todayDate) == 0 || closeDate.compareTo(todayDate) < 0) {
								System.out.println("closeing.....................................ooo");
								OpportunityRecruitDetails.setCloseInd(true);
								recruitmentOpportunityDetailsRepository.save(OpportunityRecruitDetails);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<RecruitmentOpportunityMapper> getOrgDashBoardOpenRecruitmentByOrgId(String orgId) {
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();

		List<OpportunityDetails> opportunityList = opportunityDetailsRepository.getopportunityDetailsByOrgId(orgId);
		return opportunityList.stream().map(opportunityDetails -> {
			List<OpportunityRecruitDetails> recruitOppList = recruitmentOpportunityDetailsRepository
					.getRecriutmentsByOpportunityId(opportunityDetails.getOpportunityId());

			recruitOppList.stream().map(opportunityRecruitDetails -> {

				if (opportunityRecruitDetails.isCloseInd() == false) {
					System.out.println("oppId::::::::::::" + opportunityDetails.getOpportunityId());
					List<RecruitProfileLinkDetails> todayOnbordedList = recruitProfileDetailsRepository
							.getProfileDetailByRecruitmentIdAndonBoardInd(
									opportunityRecruitDetails.getRecruitment_id());
					int profileSize = todayOnbordedList.size();
					int positionSize = (int) opportunityRecruitDetails.getNumber();
					System.out.println("profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
					if (profileSize < positionSize) {
						RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
						recruitmentOpportunityMapper.setRecruitmentId(opportunityRecruitDetails.getRecruitment_id());

						// recruitmentOpportunityMapper.setBilling(opportunityRecruitDetails.getBilling());
						recruitmentOpportunityMapper.setRequirementName(opportunityRecruitDetails.getName());
						recruitmentOpportunityMapper.setNumber(opportunityRecruitDetails.getNumber());
						// recruitmentOpportunityMapper.setAvilableDate(Utility.getISOFromDate(opportunityRecruitDetails.getAvailable_date()));
						recruitmentOpportunityMapper
								.setCreationDate(Utility.getISOFromDate(opportunityRecruitDetails.getCreationDate()));
						recruitmentOpportunityMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
						recruitmentOpportunityMapper.setExperience(opportunityRecruitDetails.getExperience());
						recruitmentOpportunityMapper.setLocation(opportunityRecruitDetails.getLocation());
						recruitmentOpportunityMapper
								.setProcessName(getProcessName(opportunityRecruitDetails.getRecruitment_process_id()));
						// recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcess(li.getRecruitment_profile_details_id()));
						// recruitmentOpportunityMapper.setSt

						if (!StringUtils.isEmpty(opportunityRecruitDetails.getSponser_id())) {

							ContactDetails contactDetails = contactRepository
									.getContactDetailsById(opportunityRecruitDetails.getSponser_id());
							String middleName = " ";
							String lastName = " ";
							if (null != contactDetails.getMiddle_name()) {

								middleName = contactDetails.getMiddle_name();
							}
							if (null != contactDetails.getLast_name()) {

								lastName = contactDetails.getLast_name();
							}
							recruitmentOpportunityMapper
									.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
						}

						if (!StringUtils.isEmpty(opportunityDetails.getCustomerId())) {
							Customer customer = customerRepository
									.getCustomerDetailsByCustomerId(opportunityDetails.getCustomerId());
							if (null != customer) {
								recruitmentOpportunityMapper.setCustomerName(customer.getName());
							}
						}

						List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
								.getCandidateList(opportunityRecruitDetails.getRecruitment_id());
						if (null != recruitmentCandidateLink) {
							recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
						}
						System.out.println("offer:::" + recruitmentCandidateLink.size());
						List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
								.getRecruitProfileStatusLinkByRecruitmentId(
										opportunityRecruitDetails.getRecruitment_id());
						if (null != recruitProfileStatusLink) {
							recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
						}
						System.out.println("closePosition::::::::" + recruitProfileStatusLink.size());
						List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(
										opportunityRecruitDetails.getRecruitment_id());
						if (null != profileLinkDetailsList) {
							recruitmentOpportunityMapper.setOnBoardNo(profileLinkDetailsList.size());
						}
						List<RecruitProfileStatusLink> dropList = recruitmentProfileStatusLinkRepository
								.getRecruitDropListByRecruitmentId(opportunityRecruitDetails.getRecruitment_id());
						if (null != dropList) {
							recruitmentOpportunityMapper.setRejected(dropList.size());
						}
						recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcessWithCandidate(
								opportunityRecruitDetails.getRecruitment_process_id(),
								opportunityRecruitDetails.getRecruitment_id()));
						mapperList.add(recruitmentOpportunityMapper);
						System.out.println("____________________________");
					}
				}
				return mapperList;
			}).collect(Collectors.toList());

			return mapperList;
		}).flatMap(l -> l.stream()).collect(Collectors.toList());
	}

	@Override
	public List<FunnelMapper> getRecruiterFunnelRecordByUserId(String userId) {
		List<FunnelMapper> resultMapper2 = new ArrayList<>();
		int openRequirement = 0;
		int numOfAllCandidate = 0;
		int numOfOfferCandidate = 0;
		int numOfOnBoardCandidate = 0;
		int position = 0;

		Date startDate = new Date();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(startDate));
			start_date = Utility.removeTime(startDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<RecruitmentRecruiterLink> oprecruiterList = recruitmentRecruiterLinkRepository
				.getrecruiterListByRecruiterId(userId);
		if (null != oprecruiterList && !oprecruiterList.isEmpty()) {
			for (RecruitmentRecruiterLink recruitmentRecruiterLink : oprecruiterList) {

				List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
						.getProfileDetailByRecruitmentIdAndonBoardInd(recruitmentRecruiterLink.getRecruitmentId());
				System.out.println("profileList>>>" + profileList.toString());
				if (null != oprecruiterList && !oprecruiterList.isEmpty()) {
					System.out.println("start::::::::::::1");
					System.out.println("RequirementId::::" + recruitmentRecruiterLink.getRecruitmentId());
					OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
							.getRecruitmentDetailsByRecruitId(recruitmentRecruiterLink.getRecruitmentId());
					System.out.println("dates::::::" + start_date + end_date);
					if (null != opportunityRecruitDetails) {
						List<RecruitProfileLinkDetails> onbordedCandidateList = recruitProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardIndAndDateRange(
										opportunityRecruitDetails.getRecruitment_id(), start_date, end_date);
						System.out.println("rcid:::::::::::::" + opportunityRecruitDetails.getRecruitment_id() + "||"
								+ opportunityRecruitDetails.getNumber());
						openRequirement += opportunityRecruitDetails.getNumber();

						numOfOnBoardCandidate += onbordedCandidateList.size();

						List<RecruitProfileLinkDetails> allCandidate = recruitmentProfileDetailsRepository
								.getProfileDetailByDateRange(opportunityRecruitDetails.getRecruitment_id(), start_date,
										end_date);
						numOfAllCandidate += allCandidate.size();
						List<RecruitProfileStatusLink> selectedCandidate = recruitmentProfileStatusLinkRepository
								.getRecruitProfileStatusLinkByRecruitmentIdOnDateRange(
										opportunityRecruitDetails.getRecruitment_id(), start_date, end_date);
						numOfOfferCandidate += selectedCandidate.size();
					}
				}
			}
		}

		position = openRequirement - numOfOnBoardCandidate;
		if (numOfOfferCandidate > numOfOnBoardCandidate) {
			numOfOfferCandidate = numOfOfferCandidate - numOfOnBoardCandidate;
		} else {
			numOfOfferCandidate = 0;
		}

		FunnelMapper positionmapper = new FunnelMapper();
		positionmapper.setStage("Positions");
		positionmapper.setNumber(position);
		resultMapper2.add(positionmapper);

		FunnelMapper onboardmapper = new FunnelMapper();
		onboardmapper.setStage("Submitted");
		onboardmapper.setNumber(numOfAllCandidate);
		resultMapper2.add(onboardmapper);

		FunnelMapper submitedmapper = new FunnelMapper();
		submitedmapper.setStage("Selected");
		submitedmapper.setNumber(numOfOfferCandidate);
		resultMapper2.add(submitedmapper);

		FunnelMapper onboardedmapper = new FunnelMapper();
		onboardedmapper.setStage("OnBoarded");
		onboardedmapper.setNumber(numOfOnBoardCandidate);
		resultMapper2.add(onboardedmapper);

		return resultMapper2;
	}

	@Override
	public List<FunnelMapper> getSalesFunnelRecordByUserId(String userId) {
		List<FunnelMapper> resultMapper2 = new ArrayList<>();
		int openRequirement = 0;
		int numOfAllCandidate = 0;
		int numOfOfferCandidate = 0;
		int numOfOnBoardCandidate = 0;
		int position = 0;
		int totalOnborded = 0;

		Date startDate = new Date();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(startDate));
			start_date = Utility.removeTime(startDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<OpportunitySalesUserLink> salesList = opportunitySalesUserRepository.getemployeeListByEmployeeId(userId);
		if (null != salesList && !salesList.isEmpty()) {

			for (OpportunitySalesUserLink opportunitySalesUserLink : salesList) {
				List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
						.getRecriutmentsByOpportunityId(opportunitySalesUserLink.getOpportunity_id());

				if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {
					// recruitDetailsList.stream().map(opportunityRecruitDetails -> {
					for (OpportunityRecruitDetails opportunityRecruitDetails : recruitDetailsList) {

						if (opportunityRecruitDetails.isCloseInd() == false) {
							List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
									.getProfileDetailByRecruitmentIdAndonBoardInd(
											opportunityRecruitDetails.getRecruitment_id());
							System.out.println("start::::::::::::2");
							int profileSize = profileList.size();
							int positionSize = (int) opportunityRecruitDetails.getNumber();
							if (profileSize < positionSize) {
								List<RecruitProfileLinkDetails> profileList2 = recruitProfileDetailsRepository
										.getProfileDetailByRecruitmentIdAndonBoardInd(
												opportunityRecruitDetails.getRecruitment_id());
								totalOnborded += profileList2.size();
								openRequirement += opportunityRecruitDetails.getNumber();

								List<RecruitProfileLinkDetails> allCandidate = recruitmentProfileDetailsRepository
										.getProfileDetailByDateRange(opportunityRecruitDetails.getRecruitment_id(),
												start_date, end_date);
								numOfAllCandidate += allCandidate.size();

								List<RecruitProfileStatusLink> selectedCandidate = recruitmentProfileStatusLinkRepository
										.getRecruitProfileStatusLinkByRecruitmentIdOnDateRange(
												opportunityRecruitDetails.getRecruitment_id(), start_date, end_date);
								numOfOfferCandidate += selectedCandidate.size();

								List<RecruitProfileLinkDetails> onbordedCandidateList = recruitProfileDetailsRepository
										.getProfileDetailByRecruitmentIdAndonBoardIndAndDateRange(
												opportunityRecruitDetails.getRecruitment_id(), start_date, end_date);
								numOfOnBoardCandidate += onbordedCandidateList.size();

							}
						}
					}
					// }).collect(Collectors.toList());
				}
			}

			position = openRequirement - totalOnborded;
			if (numOfOfferCandidate > numOfOnBoardCandidate) {
				numOfOfferCandidate = numOfOfferCandidate - numOfOnBoardCandidate;
			} else {
				numOfOfferCandidate = 0;
			}

			FunnelMapper positionmapper = new FunnelMapper();
			positionmapper.setStage("Positions");
			positionmapper.setNumber(position);
			resultMapper2.add(positionmapper);

			FunnelMapper onboardmapper = new FunnelMapper();
			onboardmapper.setStage("Submitted");
			onboardmapper.setNumber(numOfAllCandidate);
			resultMapper2.add(onboardmapper);

			FunnelMapper submitedmapper = new FunnelMapper();
			submitedmapper.setStage("Selected");
			submitedmapper.setNumber(numOfOfferCandidate);
			resultMapper2.add(submitedmapper);

			FunnelMapper onboardedmapper = new FunnelMapper();
			onboardedmapper.setStage("OnBoarded");
			onboardedmapper.setNumber(numOfOnBoardCandidate);
			resultMapper2.add(onboardedmapper);
		}
		return resultMapper2;
	}

	@Override
	public HashMap getWorkFlowDeleteHistoryCountList(String orgId) {
		List<GlobalProcessDetails> globalProcessDetailsList = processDetailsRepository.getByLiveIndAndOrgId(orgId);
		HashMap map = new HashMap();
		map.put("GlobalProcessDetails", globalProcessDetailsList.size());

		return map;
	}

	@Override
	public void deleteWorkflowByProcessId(String processId) {
		if (null != processId) {
			GlobalProcessDetails global = processDetailsRepository.getGlobalProcessDetailsById(processId);

			global.setLive_ind(false);
			processDetailsRepository.save(global);
		}
	}

	@Override
	public List<RecruitmentOpportunityMapper> getOpenRecuitmentByContactId(String contactId, String orgId) {
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();

		List<OpportunityDetails> oppCustomersList = opportunityDetailsRepository
				.getOpportunityListByContactIdAndLiveInd(contactId);
		if (null != oppCustomersList && !oppCustomersList.isEmpty()) {
			for (OpportunityDetails opportunityDetails : oppCustomersList) {
				List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
						.getRecriutmentsByOpportunityId(opportunityDetails.getOpportunityId());
				if (null != recruitList && !recruitList.isEmpty()) {
					for (OpportunityRecruitDetails opportunityRecruitDetails : recruitList) {
						System.out.println("recruitmentId=" + opportunityRecruitDetails.getRecruitment_id());
						List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(
										opportunityRecruitDetails.getRecruitment_id());
						System.out.println("profile Lits2@@@@@@@@@" + profileList.size());

						if (opportunityRecruitDetails.isCloseInd() == false) {
							System.out.println("start::::::::::::2");
							int profileSize = profileList.size();
							int positionSize = (int) opportunityRecruitDetails.getNumber();
							System.out.println("profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
							if (profileSize < positionSize) {
								System.out.println("start::::::::::::3");
								RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
								recruitmentOpportunityMapper
										.setRecruitmentId(opportunityRecruitDetails.getRecruitment_id());

								recruitmentOpportunityMapper.setBilling(opportunityRecruitDetails.getBilling());
								recruitmentOpportunityMapper.setRequirementName(opportunityRecruitDetails.getName());
								recruitmentOpportunityMapper.setNumber(opportunityRecruitDetails.getNumber());
								recruitmentOpportunityMapper.setAvilableDate(
										Utility.getISOFromDate(opportunityRecruitDetails.getAvailable_date()));
								recruitmentOpportunityMapper.setCreationDate(
										Utility.getISOFromDate(opportunityRecruitDetails.getCreationDate()));
								recruitmentOpportunityMapper.setCloseByDate(
										Utility.getISOFromDate(opportunityRecruitDetails.getCloseByDate()));
								recruitmentOpportunityMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
								recruitmentOpportunityMapper.setExperience(opportunityRecruitDetails.getExperience());
								recruitmentOpportunityMapper.setLocation(opportunityRecruitDetails.getLocation());
								// recruitmentOpportunityMapper.setStageName(getStageName(li.getStage_id()));
								// recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcess(li.getRecruitment_profile_details_id()));
								// recruitmentOpportunityMapper.setSt
								System.out.println("start::::::::::::4");
								if (!StringUtils.isEmpty(opportunityRecruitDetails.getSponser_id())) {

									ContactDetails contactDetails = contactRepository
											.getContactDetailsById(opportunityRecruitDetails.getSponser_id());
									String middleName = " ";
									String lastName = " ";
									if (null != contactDetails.getMiddle_name()) {

										middleName = contactDetails.getMiddle_name();
									}
									if (null != contactDetails.getLast_name()) {

										lastName = contactDetails.getLast_name();
									}
									recruitmentOpportunityMapper.setSponserName(
											contactDetails.getFirst_name() + " " + middleName + " " + lastName);
								}

								recruitmentOpportunityMapper.setOpprtunityName(opportunityDetails.getOpportunityName());
								if (opportunityDetails != null) {
									if (!StringUtils.isEmpty(opportunityDetails.getCustomerId())) {
										Customer customer = customerRepository
												.getCustomerDetailsByCustomerId(opportunityDetails.getCustomerId());
										recruitmentOpportunityMapper.setCustomerName(customer.getName());
									}
								}

								String skills = null;
								List<String> skillLibery = candidateService.getSkillSetOfSkillLibery(orgId);
								System.out.println("skillLibery============" + skillLibery + orgId);
								ArrayList<String> requiredSkills = new ArrayList<>();
								String description = opportunityRecruitDetails.getDescription().replace(",", " ");
								if (!StringUtils.isEmpty(description)) {
									// List<String> descriptionList = Arrays.asList(description.split("\\s*,\\s*"));

									List<String> descriptionList = Arrays.asList(description.split(" "));
									for (String word : skillLibery) {
										for (String description1 : descriptionList) {
											if (!requiredSkills.contains(word)) {
												if (description1.equalsIgnoreCase(word)) {
													requiredSkills.add(word);
												}
											}
										}
									}
									skills = requiredSkills.stream().map(n -> String.valueOf(n))
											.collect(Collectors.joining(","));
								}
								recruitmentOpportunityMapper.setSkillName(skills);

								if (!StringUtils.isEmpty(opportunityRecruitDetails.getUserId())) {
									EmployeeDetails employee = employeeRepository
											.getEmployeeDetailsByEmployeeId(opportunityRecruitDetails.getUserId());
									String middleName = " ";
									String lastName = "";

									if (!StringUtils.isEmpty(employee.getLastName())) {

										lastName = employee.getLastName();
									}
									if (employee.getMiddleName() != null && employee.getMiddleName().length() > 0) {
										middleName = employee.getMiddleName();
										recruitmentOpportunityMapper.setRecruitOwner(
												employee.getFirstName() + " " + middleName + " " + lastName);
									} else {
										recruitmentOpportunityMapper
												.setRecruitOwner(employee.getFirstName() + " " + lastName);
									}
								}

								List<RecruitmentCandidateLink> recruitmentCandidateLink = recruitmentCandidateLinkRepository
										.getCandidateList(opportunityRecruitDetails.getRecruitment_id());
								if (null != recruitmentCandidateLink) {
									recruitmentOpportunityMapper.setOffered(recruitmentCandidateLink.size());
								}
								System.out.println("offer:::" + recruitmentCandidateLink.size());
								List<RecruitProfileStatusLink> recruitProfileStatusLink = recruitmentProfileStatusLinkRepository
										.getRecruitProfileStatusLinkByRecruitmentId(
												opportunityRecruitDetails.getRecruitment_id());
								if (null != recruitProfileStatusLink) {
									recruitmentOpportunityMapper.setClosedPosition(recruitProfileStatusLink.size());
								}
								System.out.println("closePosition::::::::" + recruitProfileStatusLink.size());
								List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
										.getProfileDetailByRecruitmentIdAndonBoardInd(
												opportunityRecruitDetails.getRecruitment_id());
								if (null != profileLinkDetailsList) {
									recruitmentOpportunityMapper.setOnBoardNo(profileLinkDetailsList.size());
								}
								List<RecruitProfileStatusLink> dropList = recruitmentProfileStatusLinkRepository
										.getRecruitDropListByRecruitmentId(
												opportunityRecruitDetails.getRecruitment_id());
								if (null != dropList) {
									recruitmentOpportunityMapper.setRejected(dropList.size());
								}
								System.out.println("start::::::::::::6");
								recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcessWithCandidate(
										opportunityRecruitDetails.getRecruitment_process_id(),
										opportunityRecruitDetails.getRecruitment_id()));
								mapperList.add(recruitmentOpportunityMapper);
								System.out.println("____________________________");
							}
						}
					}
				}
			}
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentProcessMapper> getAllWorkFlowDeleteHistoryList(String orgId) {
		List<GlobalProcessDetails> global = processDetailsRepository.getByLiveIndAndOrgId(orgId);
		List<RecruitmentProcessMapper> resultList = new ArrayList<RecruitmentProcessMapper>();
		if (null != global && !global.isEmpty()) {

			for (GlobalProcessDetails globalProcessDetails : global) {
				RecruitmentProcessMapper processMapper = new RecruitmentProcessMapper();

				processMapper.setRecruitmentProcessName(globalProcessDetails.getProcess_name());
				processMapper.setRecruitmentProcessId(globalProcessDetails.getProcess_id());
				processMapper.setPublishInd(globalProcessDetails.isPublishInd());
				resultList.add(processMapper);
			}

		}
		return resultList;
	}

	@Override
	public List<RecruitmentActionMapper> getRecruiterRecruitmentActionByUserId(String userId) {
		List<RecruitmentActionMapper> mapperList = new ArrayList<>();
		// int profileSize =0;
		List<RecruitmentRecruiterLink> oprecruiterList = recruitmentRecruiterLinkRepository
				.getrecruiterListByRecruiterId(userId);
		System.out.println("RequirementLink=" + oprecruiterList.size());
		System.out.println("recruiterlink>>>" + oprecruiterList.toString());
		if (null != oprecruiterList && !oprecruiterList.isEmpty()) {
			for (RecruitmentRecruiterLink recruitmentRecruiterLink : oprecruiterList) {
				List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
						.getProfileDetailByRecruitmentIdAndonBoardInd(recruitmentRecruiterLink.getRecruitmentId());
				System.out.println("profileList>>>" + profileList.toString());
				if (null != oprecruiterList && !oprecruiterList.isEmpty()) {
					// oprecruiterList.stream().map(li -> {
					System.out.println("start::::::::::::1");
					System.out.println("RequirementId::::" + recruitmentRecruiterLink.getRecruitmentId());
					OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
							.getRecruitmentDetailsByRecruitId(recruitmentRecruiterLink.getRecruitmentId());
					if (null != opportunityRecruitDetails) {
						if (opportunityRecruitDetails.isCloseInd() == false) {
							System.out.println("start::::::::::::2");
							int profileSize = profileList.size();
							int positionSize = (int) opportunityRecruitDetails.getNumber();
							if (profileSize < positionSize) {
								System.out.println("start::::::::::::3");
								List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
										.getProfileDetailByRecruitmentId(recruitmentRecruiterLink.getRecruitmentId());
								for (RecruitProfileLinkDetails recruitProfileLinkDetails : profileLinkDetailsList) {

									RecruitmentProcessStageDetails recruitmentProcessStageDetails = recruitmentStageDetailsRepository
											.getRecruitmentStageDetailsByStageId(
													recruitProfileLinkDetails.getStage_id());
									// Date creation =recruitmentProcessStageDetails.getCreation_date();
									Date currentDate = Utility.removeTime(new Date());
									Date validDate = Utility
											.removeTime(Utility.getPlusDate(opportunityRecruitDetails.getCreationDate(),
													recruitmentProcessStageDetails.getDays()));

									System.out.println(currentDate + "||" + validDate);
									if (validDate.before(currentDate) || validDate.equals(currentDate)) {
										RecruitmentActionMapper actionMapper = new RecruitmentActionMapper();

										String msg = "";
										String sponsorName = "";
										if (!StringUtils.isEmpty(opportunityRecruitDetails.getSponser_id())) {

											ContactDetails contactDetails = contactRepository
													.getContactDetailsById(opportunityRecruitDetails.getSponser_id());
											String middleName = " ";
											String lastName = " ";
											if (null != contactDetails.getMiddle_name()) {

												middleName = contactDetails.getMiddle_name();
											}
											if (null != contactDetails.getLast_name()) {

												lastName = contactDetails.getLast_name();
											}

											sponsorName = contactDetails.getFirst_name() + " " + middleName + " "
													+ lastName;
										}
										String stageName = recruitmentProcessStageDetails.getStage_name();
										String jobId = opportunityRecruitDetails.getJob_order();

										RecruitmentCandidateLink recruitmentCandidateLink = candidateLinkRepository
												.getCandidateProfile(recruitProfileLinkDetails.getProfile_id());
										String candidateName = cadndidateDetails(
												recruitmentCandidateLink.getCandidate_id());

										msg = candidateName + " still in to stage {" + stageName + "}" + " for " + jobId
												+ "/" + sponsorName;
										actionMapper.setMessage(msg);
										actionMapper.setMessage(recruitProfileLinkDetails.getProfile_id());
										actionMapper.setStageId(recruitProfileLinkDetails.getStage_id());
										actionMapper.setOpportunityId(recruitProfileLinkDetails.getOpp_id());
										actionMapper.setRecruitmentId(recruitProfileLinkDetails.getRecruitment_id());
										actionMapper.setJobOrder(jobId);
										actionMapper.setRecruitOwner(recruitProfileLinkDetails.getRecruitOwner());
										mapperList.add(actionMapper);
									}
								}
								System.out.println("____________________________");
							}
						}
					}
					// return mapperList;
					// }).collect(Collectors.toList());
				}
			}
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentActionMapper> getSalesRecruitmentActionByUserId(String userId) {
		List<RecruitmentActionMapper> mapperList = new ArrayList<>();
		List<OpportunitySalesUserLink> salesList = opportunitySalesUserRepository.getemployeeListByEmployeeId(userId);
		if (null != salesList && !salesList.isEmpty()) {
			System.out.println("salesList=" + salesList.size());
			System.out.println("salesList>>>" + salesList.toString());
			System.out.println("###############################################################");
			return salesList.stream().map(opportunitySalesUserLink -> {
				List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
						.getRecriutmentsByOpportunityId(opportunitySalesUserLink.getOpportunity_id());

				System.out.println("recruitDetailsList>>>" + recruitDetailsList.toString());
				if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {

					return recruitDetailsList.stream().map(opportunityRecruitDetails -> {
						System.out.println("opportunityName=" + opportunityRecruitDetails.getName());
						List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(
										opportunityRecruitDetails.getRecruitment_id());

						RecruitmentActionMapper actionMapper = null;
						if (opportunityRecruitDetails.isCloseInd() == false) {
							System.out.println("start::::::::::::2");
							int profileSize = profileList.size();
							int positionSize = (int) opportunityRecruitDetails.getNumber();
							if (profileSize < positionSize) {

								List<RecruitProfileLinkDetails> profileLinkDetailsList = recruitmentProfileDetailsRepository
										.getProfileDetailByRecruitmentId(opportunityRecruitDetails.getRecruitment_id());
								for (RecruitProfileLinkDetails recruitProfileLinkDetails : profileLinkDetailsList) {

									RecruitmentProcessStageDetails recruitmentProcessStageDetails = recruitmentStageDetailsRepository
											.getRecruitmentStageDetailsByStageId(
													recruitProfileLinkDetails.getStage_id());
									if (null != recruitmentProcessStageDetails) {
										System.out.println(
												"xxxxxxxxx==" + recruitmentProcessStageDetails.getRecruitmentStageId());
										// Date creation =recruitmentProcessStageDetails.getCreation_date();
										Date currentDate = Utility.removeTime(new Date());
										System.out.println(
												"RECIDDDD====" + opportunityRecruitDetails.getRecruitment_id());
										System.out.println("STAGEID==="
												+ recruitmentProcessStageDetails.getRecruitment_stage_details_id());
										Date validDate = Utility.removeTime(
												Utility.getPlusDate(opportunityRecruitDetails.getCreationDate(),
														recruitmentProcessStageDetails.getDays()));
										System.out.println(currentDate + "||" + validDate);
										if (validDate.before(currentDate) || validDate.equals(currentDate)) {
											actionMapper = new RecruitmentActionMapper();
											System.out.println("start::::::::::::3");

											String msg = "";
											String sponsorName = "";
											if (!StringUtils.isEmpty(opportunityRecruitDetails.getSponser_id())) {

												ContactDetails contactDetails = contactRepository.getContactDetailsById(
														opportunityRecruitDetails.getSponser_id());
												String middleName = " ";
												String lastName = " ";
												if (null != contactDetails.getMiddle_name()) {

													middleName = contactDetails.getMiddle_name();
												}
												if (null != contactDetails.getLast_name()) {

													lastName = contactDetails.getLast_name();
												}

												sponsorName = contactDetails.getFirst_name() + " " + middleName + " "
														+ lastName;
											}
											String stageName = recruitmentProcessStageDetails.getStage_name();
											String jobId = opportunityRecruitDetails.getJob_order();

											RecruitmentCandidateLink recruitmentCandidateLink = candidateLinkRepository
													.getCandidateProfile(recruitProfileLinkDetails.getProfile_id());
											String candidateName = "";

											if (null != recruitmentCandidateLink) {
												System.out.println("requirementId="
														+ recruitmentCandidateLink.getRecruitment_id());
												System.out.println(
														"candidateId=" + recruitmentCandidateLink.getCandidate_id());

//												 if(!StringUtils.isEmpty(cadndidateDetails(recruitmentCandidateLink.getCandidate_id()))) {
												candidateName = cadndidateDetails(
														recruitmentCandidateLink.getCandidate_id());
//												 }

											}
											msg = candidateName + " still in to stage {" + stageName + "}" + " for "
													+ jobId + "/" + sponsorName;
											actionMapper.setMessage(msg);
											actionMapper.setDate(validDate);
											actionMapper.setProfileId(recruitProfileLinkDetails.getProfile_id());
											actionMapper.setStageId(recruitProfileLinkDetails.getStage_id());
											actionMapper.setOpportunityId(recruitProfileLinkDetails.getOpp_id());
											actionMapper
													.setRecruitmentId(recruitProfileLinkDetails.getRecruitment_id());
											actionMapper.setRecruitOwner(recruitProfileLinkDetails.getRecruitOwner());
											actionMapper.setJobOrder(jobId);
											mapperList.add(actionMapper);
											System.out
													.println("_______________________________________________________");
										}
									}
								}
							}
						}
						return actionMapper;
					}).filter(Objects::nonNull).collect(Collectors.toList());
				}
				return mapperList;
			}).flatMap(l -> l.stream()).collect(Collectors.toList());
		}
		Collections.sort(mapperList,
				(RecruitmentActionMapper m1, RecruitmentActionMapper m2) -> m2.getDate().compareTo(m1.getDate()));
		return mapperList;
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void closeOpportunity() {
		List<OpportunityDetails> opportunityList = opportunityDetailsRepository.findByLiveInd(true);
		if (null != opportunityList && !opportunityList.isEmpty()) {
			for (OpportunityDetails opportunityDetails : opportunityList) {

				List<OpportunityRecruitDetails> recruitDetailsList1 = recruitmentOpportunityDetailsRepository
						.getRecriutmentByOpportunityIdAndLiveInd(opportunityDetails.getOpportunityId());

				if (recruitDetailsList1.size() > 0) {

					List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
							.getOpenRecriutmentsByOpportunityIdAndCloseInd(opportunityDetails.getOpportunityId());

					if (recruitDetailsList.size() == 0) {

						opportunityDetails.setCloseInd(true);
						opportunityDetailsRepository.save(opportunityDetails);
					}
				}
			}
		}
	}

	@Override
	public TaskViewMapper approveAction(String profileId, RecruitmentActionMapper recruitmentActionMapper) {
		String type = recruitmentActionMapper.getActionType();
		if (type.equalsIgnoreCase("email") || type.equalsIgnoreCase("call")) {
			RecruitProfileLinkDetails profileLinkDetails = recruitmentProfileDetailsRepository
					.getprofiledetails(profileId);
			OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
					.getRecruitmentDetailsByRecruitId(profileLinkDetails.getRecruitment_id());
			if (opportunityRecruitDetails.getSponser_id() != null) {
				ContactDetails contact = contactRepository
						.getContactDetailsById(opportunityRecruitDetails.getSponser_id());
				if (contact != null) {
					String contactEmailId = contact.getEmail_id();
				}

			}
		} else if (type.equalsIgnoreCase("select") || type.equalsIgnoreCase("drop")) {
			RecruitProfileLinkDetails profileLinkDetails = recruitmentProfileDetailsRepository
					.getprofiledetails(profileId);
			String candidateId = recruitmentCandidateLinkRepository.getCandidateProfile(profileId).getCandidate_id();
			RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
			recruitmentOpportunityMapper.setRecruitmentId(profileLinkDetails.getRecruitment_id());
			recruitmentOpportunityMapper.setProfileId(profileId);
			if (type.equalsIgnoreCase("select")) {
				recruitmentOpportunityMapper.setApproveInd(true);
			} else {
				recruitmentOpportunityMapper.setApproveInd(false);
			}
			recruitmentOpportunityMapper.setCandidateId(candidateId);
			recruitmentOpportunityMapper.setTagUserId(recruitmentActionMapper.getUserId());
			String id = updateStatusOfRecrutment(recruitmentOpportunityMapper);

		}
		ActionHistory actionHistory = new ActionHistory();
		actionHistory.setActionId(type);
		actionHistory.setOpportunityId(recruitmentActionMapper.getOpportunityId());
		actionHistory.setUserId(recruitmentActionMapper.getUserId());
		actionHistory.setCreationDate(new Date());
		actionHistoryRepository.save(actionHistory);
		return null;
	}

	@Override
	public void deleteRecuitmentByStageId(String stageId) {
		if (null != stageId) {
			RecruitmentProcessStageDetails recruitmentProcessStageDetails = recruitmentStageDetailsRepository
					.getRecruitmentStageDetailsByStageId(stageId);

			RecruitmentProcessStageDetailsDelete recruitmentStageDelete = recruitmentProcessStageDetailsDeleteRepository
					.getRecruitmentStageDetailsByStageId(stageId);
			if (null != recruitmentStageDelete) {
				recruitmentStageDelete.setUpdationDate(new Date());
				recruitmentProcessStageDetailsDeleteRepository.save(recruitmentStageDelete);
			}
			recruitmentProcessStageDetails.setLiveInd(false);
			recruitmentStageDetailsRepository.save(recruitmentProcessStageDetails);
		}
	}

	@Override
	public RecruitmentOpportunityMapper deleteCandidateOnboarding(String profileId) {
		RecruitmentOpportunityMapper recruitmentOpportunityMapper1 = null;

		RecruitProfileLinkDetails recruitProfileLinkDetails = recruitProfileDetailsRepository
				.getprofiledetails(profileId);
		if (null != recruitProfileLinkDetails) {
			recruitProfileLinkDetails.setOnboard_ind(false);
			recruitProfileDetailsRepository.save(recruitProfileLinkDetails);
		}
		recruitmentOpportunityMapper1 = getProfileDetails(profileId);
		return recruitmentOpportunityMapper1;
	}

	@Override
	public RecruitmentOpportunityMapper changeCandidateAnotherStage(String profileId, String stageId) {
		RecruitmentOpportunityMapper recruitmentOpportunityMapper1 = null;
		RecruitProfileLinkDetails recruitProfileLinkDetails = recruitProfileDetailsRepository
				.getprofiledetails(profileId);
		if (null != recruitProfileLinkDetails) {
			recruitProfileLinkDetails.setLive_ind(false);
			recruitProfileDetailsRepository.save(recruitProfileLinkDetails);

			RecruitProfileLinkDetails recruitProfileLinkDetails1 = new RecruitProfileLinkDetails();

			recruitProfileLinkDetails1.setRecruitment_id(recruitProfileLinkDetails.getRecruitment_id());
			recruitProfileLinkDetails1.setCandidateId(recruitProfileLinkDetails.getCandidateId());
			recruitProfileLinkDetails1.setProfile_id(recruitProfileLinkDetails.getProfile_id());
			recruitProfileLinkDetails1.setProcess_id(recruitProfileLinkDetails.getProcess_id());
			recruitProfileLinkDetails1.setStage_id(stageId);
			recruitProfileLinkDetails1.setOpp_id(recruitProfileLinkDetails.getOpp_id());
			recruitProfileLinkDetails1.setModification_date(new Date());
			recruitProfileLinkDetails1.setCreation_date(recruitProfileLinkDetails.getCreation_date());
			recruitProfileLinkDetails1.setLive_ind(true);
			recruitProfileLinkDetails1.setRecruitOwner(recruitProfileLinkDetails.getRecruitOwner());
			recruitProfileDetailsRepository.save(recruitProfileLinkDetails1);
			recruitmentOpportunityMapper1 = getProfileDetails(profileId);

		}
		return recruitmentOpportunityMapper1;
	}
//
//	@Override
//	public List<ProcessDocumentLinkMapper> saveProcessDocumentLink(List<ProcessDocumentLinkMapper> processDocumentLinkMapper) {
//
//			List<ProcessDocumentLink> processDocumentLink = processDocumentLinkRepository.getProcessDocumentListByProcessId(processDocumentLinkMapper.get(0).getProcessId());
//			if (null != processDocumentLink && !processDocumentLink.isEmpty()) {
//				for (ProcessDocumentLink processDocumentLink2 : processDocumentLink) {
//					processDocumentLinkRepository.delete(processDocumentLink2);
//				}
//				 }
//			
//			for (ProcessDocumentLinkMapper processDocumentLinkMapper1 : processDocumentLinkMapper) {
//				if(null!=processDocumentLinkMapper1) {
//				ProcessDocumentLink newProcessDocumentLink = new ProcessDocumentLink();
//			
//				newProcessDocumentLink.setUserId(processDocumentLinkMapper1.getUserId());
//				newProcessDocumentLink.setOrgId(processDocumentLinkMapper1.getOrgId());
//				newProcessDocumentLink.setCreationDate(new Date());
//				newProcessDocumentLink.setProcessId(processDocumentLinkMapper1.getProcessId());
//				newProcessDocumentLink.setDocumentTypeId(processDocumentLinkMapper1.getDocumentTypeId());
//				newProcessDocumentLink.setMandatoryInd(false);
//
//				processDocumentLinkRepository.save(newProcessDocumentLink);
//
//				}
//			}
//					 
//			List<ProcessDocumentLinkMapper> processDocumentLinkMapper2 = getProcessDocumentLinkByProcessId(
//					processDocumentLinkMapper.get(0).getProcessId());
//			
//				return processDocumentLinkMapper2;
//		}
//
//	@Override
//	public List<ProcessDocumentLinkMapper> getProcessDocumentLinkByProcessId(String processId) {
//		List<ProcessDocumentLink> processDocumentLink = processDocumentLinkRepository.getProcessDocumentListByProcessId(processId);
//		List<ProcessDocumentLinkMapper> list = new ArrayList<>();
//		if (null != processDocumentLink && !processDocumentLink.isEmpty()) {
//			for (ProcessDocumentLink processLink : processDocumentLink) {
//				ProcessDocumentLinkMapper processDocumentLinkMapper = new ProcessDocumentLinkMapper();
//        		
//				processDocumentLinkMapper.setProcessDocumentLinkId(processLink.getProcessDocumentLinkId());
//				processDocumentLinkMapper.setOrgId(processLink.getOrgId());
//				processDocumentLinkMapper.setUserId(processLink.getUserId());
//				processDocumentLinkMapper.setCreationDate(Utility.getISOFromDate(processLink.getCreationDate()));
//				processDocumentLinkMapper.setProcessId(processLink.getProcessId());
//				processDocumentLinkMapper.setDocumentTypeId(processLink.getDocumentTypeId());
//				processDocumentLinkMapper.setMandatoryInd(processLink.isMandatoryInd());
//				
//
//				DocumentType DocumentType = documentTypeRepository.getTypeDetails(processLink.getDocumentTypeId());
//				if(null!=DocumentType) {
//					processDocumentLinkMapper.setDocumentTypeName(DocumentType.getDocumentTypeName());
//				}
//				
//				list.add(processDocumentLinkMapper);
//			}
//		}
//
//		return list;
//	}

	@Override
	public ProcessDocumentLinkMapper convertDocumentToMandatory(ProcessDocumentLinkMapper processDocumentLinkMapper) {
		ProcessDocumentLinkMapper processDocumentLinkMapper1 = null;
		if (null != processDocumentLinkMapper) {
			ProcessDocumentLink processDocumentLink1 = processDocumentLinkRepository.findByDocumentTypeIdAndProcessId(
					processDocumentLinkMapper.getDocumentTypeId(), processDocumentLinkMapper.getProcessId());
			if (null != processDocumentLink1) {
				processDocumentLink1.setMandatoryInd(processDocumentLinkMapper.isMandatoryInd());
				processDocumentLink1.setUserId(processDocumentLinkMapper.getUserId());
				processDocumentLink1.setOrgId(processDocumentLinkMapper.getOrgId());
				processDocumentLink1.setCreationDate(new Date());
				processDocumentLink1.setUpdationDate(new Date());
				processDocumentLink1.setProcessId(processDocumentLinkMapper.getProcessId());
				processDocumentLink1.setDocumentTypeId(processDocumentLinkMapper.getDocumentTypeId());
				processDocumentLinkRepository.save(processDocumentLink1);
			} else {
				ProcessDocumentLink processDocumentLink = new ProcessDocumentLink();
				processDocumentLink.setMandatoryInd(processDocumentLinkMapper.isMandatoryInd());
				processDocumentLink.setUserId(processDocumentLinkMapper.getUserId());
				processDocumentLink.setOrgId(processDocumentLinkMapper.getOrgId());
				processDocumentLink.setCreationDate(new Date());
				processDocumentLink.setUpdationDate(new Date());
				processDocumentLink.setProcessId(processDocumentLinkMapper.getProcessId());
				processDocumentLink.setDocumentTypeId(processDocumentLinkMapper.getDocumentTypeId());
				processDocumentLinkRepository.save(processDocumentLink);
			}

			processDocumentLinkMapper1 = getProcessDocumentLinkByDocumentTypeIdAndProcessId(
					processDocumentLinkMapper.getDocumentTypeId(), processDocumentLinkMapper.getProcessId());
		}
		return processDocumentLinkMapper1;
	}

	private ProcessDocumentLinkMapper getProcessDocumentLinkByDocumentTypeIdAndProcessId(String documentTypeId,
			String processId) {
		ProcessDocumentLinkMapper processDocumentLinkMapper = new ProcessDocumentLinkMapper();
		ProcessDocumentLink processDocumentLink = processDocumentLinkRepository
				.findByDocumentTypeIdAndProcessId(documentTypeId, processId);
		if (null != processDocumentLink) {
			processDocumentLinkMapper.setProcessDocumentLinkId(processDocumentLink.getProcessDocumentLinkId());
			processDocumentLinkMapper.setOrgId(processDocumentLink.getOrgId());
			processDocumentLinkMapper.setUserId(processDocumentLink.getUserId());
			processDocumentLinkMapper.setCreationDate(Utility.getISOFromDate(processDocumentLink.getCreationDate()));
			processDocumentLinkMapper.setUpdationDate(Utility.getISOFromDate(processDocumentLink.getUpdationDate()));
			processDocumentLinkMapper.setProcessId(processDocumentLink.getProcessId());
			processDocumentLinkMapper.setDocumentTypeId(processDocumentLink.getDocumentTypeId());
			processDocumentLinkMapper.setMandatoryInd(processDocumentLink.isMandatoryInd());
			DocumentType DocumentType = documentTypeRepository.getTypeDetails(processDocumentLink.getDocumentTypeId());
			if (null != DocumentType) {
				processDocumentLinkMapper.setDocumentTypeName(DocumentType.getDocumentTypeName());
			}

			EmployeeDetails employee = employeeRepository
					.getEmployeeDetailsByEmployeeId(processDocumentLink.getUserId());
			if (null != employee) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employee.getLastName())) {

					lastName = employee.getLastName();
				}
				if (employee.getMiddleName() != null && employee.getMiddleName().length() > 0) {
					middleName = employee.getMiddleName();
					processDocumentLinkMapper.setName(employee.getFirstName() + " " + middleName + " " + lastName);
				} else {
					processDocumentLinkMapper.setName(employee.getFirstName() + " " + lastName);
				}
			}
		}
		return processDocumentLinkMapper;
	}

	@Override
	public List<ActionHistoryMapper> getRecruiterActionHistoryByUserId(String userId) {
		List<RecruitmentRecruiterLink> recruterList = recruitmentRecruiterLinkRepository
				.getRecruitmentRecruiterLinkByRecruitmentId(userId);
		List<ActionHistoryMapper> mapperList = new ArrayList<>();

		if (null != recruterList && !recruterList.isEmpty()) {
			return recruterList.stream().map(recruitmentRecruiterLink -> {

				OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
						.getRecriutmentByOpportunityId(recruitmentRecruiterLink.getRecruitmentId());
				List<ActionHistory> historyList = actionHistoryRepository
						.findByOpportunityId(opportunityRecruitDetails.getOpportunity_id());

				if (null != historyList && !historyList.isEmpty()) {
					historyList.stream().sorted((l1, l2) -> l1.getCreationDate().compareTo(l2.getCreationDate()))
							.map(li -> {
								ActionHistoryMapper mapper = new ActionHistoryMapper();
								mapper.setTitle(li.getActionId());
								mapper.setDescription(
										"On " + Utility.getISOFromDate(li.getCreationDate()) + " " + li.getActionId());

								mapperList.add(mapper);
								return mapperList;
							}).collect(Collectors.toList());
				}
				return mapperList;
			}).flatMap(l -> l.stream()).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<ActionHistoryMapper> getSalesActionHistoryByUserId(String userId) {
		List<OpportunitySalesUserLink> salesUserList = opportunitySalesUserRepository.getSalesUserLinkByUserId(userId);
		List<ActionHistoryMapper> mapperList = new ArrayList<>();
		if (null != salesUserList && !salesUserList.isEmpty()) {
			return salesUserList.stream().map(opportunitySalesUserLink -> {
				List<ActionHistory> historyList = actionHistoryRepository
						.findByOpportunityId(opportunitySalesUserLink.getOpportunity_id());

				if (null != historyList && !historyList.isEmpty()) {
					historyList.stream().sorted((l1, l2) -> l1.getCreationDate().compareTo(l2.getCreationDate()))
							.map(li -> {
								ActionHistoryMapper mapper = new ActionHistoryMapper();
								mapper.setTitle(li.getActionId());
								mapper.setDescription(
										"On " + Utility.getISOFromDate(li.getCreationDate()) + " " + li.getActionId());

								mapperList.add(mapper);
								return mapperList;
							}).collect(Collectors.toList());
				}
				return mapperList;
			}).flatMap(l -> l.stream()).collect(Collectors.toList());

		}
		return mapperList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getOpenRequirementListByOrgId(String orgId) {
		List<OpportunityRecruitDetails> opportunityRecruitDetailsList = recruitmentOpportunityDetailsRepository
				.getOpenRecruitmentsListByOrgIdAndCloseInd(orgId);
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();

		if (null != opportunityRecruitDetailsList && !opportunityRecruitDetailsList.isEmpty()) {
			opportunityRecruitDetailsList.stream().map(li -> {

				RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
				recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
				System.out.println("getRecruitment_id---------------" + li.getRecruitment_id());
				recruitmentOpportunityMapper.setLocation(li.getLocation());
				recruitmentOpportunityMapper.setRequirementName(li.getName());
				recruitmentOpportunityMapper.setSponserId(li.getSponser_id());
				recruitmentOpportunityMapper.setDescription(li.getDescription());
				recruitmentOpportunityMapper.setNumber(li.getNumber());
				recruitmentOpportunityMapper.setBilling(li.getBilling());
				recruitmentOpportunityMapper.setCurrency(li.getCurrency());
				recruitmentOpportunityMapper.setType(li.getType());
				recruitmentOpportunityMapper.setAvilableDate(Utility.getISOFromDate(li.getAvailable_date()));
				recruitmentOpportunityMapper.setEndDate(Utility.getISOFromDate(li.getEnd_date()));
				recruitmentOpportunityMapper.setCloseByDate(Utility.getISOFromDate(li.getCloseByDate()));
				recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));

				recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcess(li.getRecruitment_process_id()));

				recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
				recruitmentOpportunityMapper.setExperience(li.getExperience());
				recruitmentOpportunityMapper.setLocation(li.getLocation());
				recruitmentOpportunityMapper.setCategory(li.getCategory());
				recruitmentOpportunityMapper.setCountry(li.getCountry());
				recruitmentOpportunityMapper.setDepartment(li.getDepartment());
				recruitmentOpportunityMapper.setRole(li.getRole());
				recruitmentOpportunityMapper.setRecruitmentProcessId(li.getRecruitment_process_id());
				recruitmentOpportunityMapper.setWorkPreference(li.getWorkPreferance());
				recruitmentOpportunityMapper.setCloseInd(li.isCloseInd());
				if (!StringUtils.isEmpty(li.getUserId())) {
					EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(li.getUserId());
					if (null != employeeDetails) {
						String middleName = " ";
						String lastName = " ";

						if (null != employeeDetails.getLastName()) {

							lastName = employeeDetails.getLastName();
						}
						if (null != employeeDetails.getMiddleName()) {

							middleName = employeeDetails.getMiddleName();
							recruitmentOpportunityMapper
									.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
						} else {
							recruitmentOpportunityMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
						}
					}
				}
				List<RecruitProfileLinkDetails> onbordingList = recruitmentProfileDetailsRepository
						.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());
				if (null != onbordingList) {
					recruitmentOpportunityMapper.setOnBoardNo(onbordingList.size());
				}

				RecruitmentPublishDetails recruitmentPublishDetails = recruitmentPublishRepository
						.getRecruitmentPublishDetails(li.getRecruitment_id());
				if (null != recruitmentPublishDetails) {

					recruitmentOpportunityMapper.setPublishInd(true);
				} else {

					recruitmentOpportunityMapper.setPublishInd(false);
				}

				if (!StringUtils.isEmpty(li.getRecruiter_id())) {
					EmployeeDetails employeeDetails = employeeRepository
							.getEmployeeDetailsByEmployeeId(li.getRecruiter_id());
					String middleName = " ";
					String lastName = " ";
					if (employeeDetails.getMiddleName() != null) {

						middleName = employeeDetails.getMiddleName();
					}
					if (employeeDetails.getLastName() != null) {

						lastName = employeeDetails.getLastName();
					}

					recruitmentOpportunityMapper
							.setRecruiterName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				}

				if (!StringUtils.isEmpty(li.getSponser_id())) {

					ContactDetails contactDetails = contactRepository.getContactDetailsById(li.getSponser_id());
					String middleName = " ";
					String lastName = " ";
					if (null != contactDetails.getMiddle_name()) {

						middleName = contactDetails.getMiddle_name();
					}
					if (null != contactDetails.getLast_name()) {

						lastName = contactDetails.getLast_name();
					}
					recruitmentOpportunityMapper
							.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
				}

				/* Add skill set */
				recruitmentOpportunityMapper.setSkillSetList(candidateService.getSkillSetOfSkillLibery(orgId));
				List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
						.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
				if (recruitmentSkillsetLink.size() != 0) {
					recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

				}

				List<RecruitmentCandidateLink> recruitmentCandidateLink = candidateLinkRepository
						.getCandidateList(li.getRecruitment_id());
				System.out.println("recruitmentCandidateLink::::::" + recruitmentCandidateLink.toString());
				if (null != recruitmentCandidateLink && !recruitmentCandidateLink.isEmpty()) {
					System.out.println("size:::::::::" + recruitmentCandidateLink.size());

					List<CandidateMapper> resultList = new ArrayList<>();
					int count = 0;
					for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
						CandidateMapper mapper = new CandidateMapper();
						CandidateDetails candidate = candidateDetailsRepository
								.getcandidateDetailsById(candidateLink.getCandidate_id());
						if (null != candidate) {
							String middleName = " ";
							String lastName = " ";
							if (null != candidate.getMiddleName()) {

								middleName = candidate.getMiddleName();
							}
							if (null != candidate.getLastName()) {

								lastName = candidate.getLastName();
							}
							mapper.setCandidateId(candidate.getCandidateId());
							mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
							mapper.setImageId(candidate.getImageId());
						}
						resultList.add(mapper);
						count++;
						if (count == 2) {
							break;
						}
					}
					recruitmentOpportunityMapper.setCandidatetList(resultList);

					List<CandidateMapper> resultList2 = new ArrayList<>();
					for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
						CandidateMapper mapper = new CandidateMapper();
						CandidateDetails candidate = candidateDetailsRepository
								.getcandidateDetailsById(candidateLink.getCandidate_id());
						if (null != candidate) {
							String middleName = " ";
							String lastName = " ";
							if (null != candidate.getMiddleName()) {

								middleName = candidate.getMiddleName();
							}
							if (null != candidate.getLastName()) {

								lastName = candidate.getLastName();
							}
							mapper.setCandidateId(candidate.getCandidateId());
							mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
							mapper.setImageId(candidate.getImageId());
						}
						resultList2.add(mapper);
					}
					recruitmentOpportunityMapper.setFiltercandidatetList(resultList2);
				}
				recruitmentOpportunityMapper.setCandidateNo(candidateNo(recruitmentCandidateLink.size()));

				List<RecruitmentRecruiterLink> recruiterList = recruitmentRecruiterLinkRepository
						.getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id());
				if (null != recruiterList && !recruiterList.isEmpty()) {
					List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
					for (RecruitmentRecruiterLink recruiter : recruiterList) {
						EmployeeMapper mapper = new EmployeeMapper();
						EmployeeDetails emp = employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
						// System.out.println("name....." + emp.getFullName());
						mapper.setFullName(emp.getFullName());
						mapper.setEmployeeId(recruiter.getRecruiterId());

						resultList.add(mapper);
					}
					recruitmentOpportunityMapper.setRecruiterList(resultList);
				}

				List<RecruitmentAddressLink> recruitmentAddressLink = recruitmentAddressLinkRepository
						.getAddressListByRecruitmentId(li.getRecruitment_id());
				List<AddressMapper> addressList = new ArrayList<AddressMapper>();
				if (null != recruitmentAddressLink && !recruitmentAddressLink.isEmpty()) {

					for (RecruitmentAddressLink recruitmentAddressLink1 : recruitmentAddressLink) {
						AddressDetails addressDetails = addressRepository
								.getAddressDetailsByAddressId(recruitmentAddressLink1.getAddressId());

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
							addressDetails.setHouseNo(addressMapper.getHouseNo());
							addressList.add(addressMapper);
						}
					}

					System.out.println("addressList.......... " + addressList);
				}
				recruitmentOpportunityMapper.setAddress(addressList);

				List<RecruitmentPartnerLink> recruitmentPartnerLinkList = recruitmentPartnerLinkRepository
						.findByRecruitmentIdAndLiveInd(li.getRecruitment_id(), true);
				if (null != recruitmentPartnerLinkList && !recruitmentPartnerLinkList.isEmpty()) {
					List<PartnerMapper> partnerResultList = new ArrayList<PartnerMapper>();
					for (RecruitmentPartnerLink partner : recruitmentPartnerLinkList) {
						PartnerMapper mapper = new PartnerMapper();
						System.out.println("partner::::::::::" + partner.getPartnerId());
						System.out.println("recID:::::::::::" + partner.getRecruitmentId());
						PartnerDetails dbPartner = partnerDetailsRepository
								.getPartnerDetailsById(partner.getPartnerId());
						if (null != dbPartner) {
							mapper.setPartnerName(dbPartner.getPartnerName());
							mapper.setPartnerId(dbPartner.getPartnerId());

							partnerResultList.add(mapper);
						}
					}
					recruitmentOpportunityMapper.setPartnerList(partnerResultList);
				}
				return mapperList.add(recruitmentOpportunityMapper);

			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<JobVacancyMapper> getJobVacancyListForWebsite() {
		List<JobVacancyMapper> mapperList = new ArrayList<>();
		List<RecruitmentPublishDetails> list = recruitmentPublishRepository.getRecruitmentDetails();
		System.out.println("publishList>>>>>>>>>>>" + list.toString());
		if (null != list && !list.isEmpty()) {
			String orgId = list.get(0).getOrg_id();
			list.stream().map(li -> {
				OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
						.getRecruitmentDetailsByRecruitId(li.getRecruiter_id());
				String skills = null;
				JobVacancyMapper recruitmentOpportunityMapper = new JobVacancyMapper();
				System.out.println("requirementName>>>>>>>>>>" + opportunityRecruitDetails.getRecruitment_id());
				recruitmentOpportunityMapper.setRequirementName(opportunityRecruitDetails.getName());
				recruitmentOpportunityMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
				recruitmentOpportunityMapper.setDescription(opportunityRecruitDetails.getDescription());
				recruitmentOpportunityMapper.setLocation(opportunityRecruitDetails.getLocation());
				recruitmentOpportunityMapper.setRecruitmentId(opportunityRecruitDetails.getRecruitment_id());
//				recruitmentOpportunityMapper
//						.setCreationDate(Utility.getISOFromDate(opportunityRecruitDetails.getCreationDate()));
				recruitmentOpportunityMapper
						.setAvilableDate(Utility.getISOFromDate(opportunityRecruitDetails.getAvailable_date()));
//				recruitmentOpportunityMapper
//						.setEndDate(Utility.getISOFromDate(opportunityRecruitDetails.getEnd_date()));
				recruitmentOpportunityMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
				recruitmentOpportunityMapper.setLocation(opportunityRecruitDetails.getLocation());
				recruitmentOpportunityMapper.setOpportunityId(opportunityRecruitDetails.getOpportunity_id());
				recruitmentOpportunityMapper.setWorkPreference(opportunityRecruitDetails.getWorkPreferance());
				recruitmentOpportunityMapper
						.setRecruitmentProcessId(opportunityRecruitDetails.getRecruitment_process_id());

				// recruitmentOpportunityMapper.setOrgId(null)

//				recruitmentOpportunityMapper
//						.setCreationDate(Utility.getISOFromDate(opportunityRecruitDetails.getCreationDate()));

				List<String> skillLibery = candidateService.getSkillSetOfSkillLibery(orgId);
				System.out.println("skillLibery============" + skillLibery + orgId);
				ArrayList<String> requiredSkills = new ArrayList<>();
				String description = opportunityRecruitDetails.getDescription().replace(",", " ");
				if (!StringUtils.isEmpty(description)) {
					// List<String> descriptionList = Arrays.asList(description.split("\\s*,\\s*"));

					List<String> descriptionList = Arrays.asList(description.split(" "));
					for (String word : skillLibery) {
						for (String description1 : descriptionList) {
							if (!requiredSkills.contains(word)) {
								if (description1.equalsIgnoreCase(word)) {
									requiredSkills.add(word);
								}
							}
						}
					}
					skills = requiredSkills.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(","));
				}
				recruitmentOpportunityMapper.setSkillName(skills);

				List<RecruitmentAddressLink> recruitmentAddressLink = recruitmentAddressLinkRepository
						.getAddressListByRecruitmentId(opportunityRecruitDetails.getRecruitment_id());
				List<JobPublishAddressMapper> addressList = new ArrayList<JobPublishAddressMapper>();
				if (null != recruitmentAddressLink && !recruitmentAddressLink.isEmpty()) {

					for (RecruitmentAddressLink recruitmentAddressLink1 : recruitmentAddressLink) {
						AddressDetails addressDetails = addressRepository
								.getAddressDetailsByAddressId(recruitmentAddressLink1.getAddressId());

						JobPublishAddressMapper addressMapper = new JobPublishAddressMapper();
						if (null != addressDetails) {

							addressMapper.setAddress1(addressDetails.getAddressLine1());
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
				recruitmentOpportunityMapper.setAddress(addressList);

				return mapperList.add(recruitmentOpportunityMapper);

			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public RecruitmentOpportunityMapper updateCandidateReOnboarding(
			RecruitmentOpportunityMapper recruitmentOpportunityMapper) {

		RecruitmentOpportunityMapper recruitmentOpportunityMapper1 = null;
		RecruitProfileLinkDetails recruitProfileDetails = new RecruitProfileLinkDetails();
		recruitProfileDetails.setOnboard_ind(false);
		recruitProfileDetailsRepository.save(recruitProfileDetails);

		RecruitProfileLinkDetails recruitProfileLinkDetails = recruitProfileDetailsRepository
				.getprofiledetails(recruitmentOpportunityMapper.getProfileId());
		CandidateDetails candidateDetails = candidateDetailsRepository
				.getcandidateDetailsById(recruitmentOpportunityMapper.getCandidateId());

		if (null != recruitProfileLinkDetails) {

			List<RecruitProfileLinkDetails> onboard = recruitProfileDetailsRepository
					.getProfileDetailByRecruitmentIdAndonBoardInd(recruitProfileLinkDetails.getRecruitment_id());
			OpportunityRecruitDetails recruitmentNo = recruitmentOpportunityDetailsRepository
					.getRecruitmentDetailsByRecruitId(recruitProfileLinkDetails.getRecruitment_id());
			if (onboard.size() > recruitmentNo.getNumber()) {
				recruitProfileLinkDetails.setOnboard_ind(recruitmentOpportunityMapper.isOnboardInd());
				recruitProfileLinkDetails.setFinalBilling(recruitmentOpportunityMapper.getFinalBilling());
				recruitProfileLinkDetails.setOnboardCurrency(recruitmentOpportunityMapper.getOnboardCurrency());
				recruitProfileLinkDetails.setProjectName(recruitmentOpportunityMapper.getProjectName());
				recruitProfileLinkDetails.setUserId(recruitmentOpportunityMapper.getUserId());
				recruitProfileLinkDetails.setOrgId(recruitmentOpportunityMapper.getOrgId());

				try {
					recruitProfileLinkDetails.setOnboard_date(
							Utility.getDateFromISOString(recruitmentOpportunityMapper.getOnboardDate()));
					recruitProfileLinkDetails.setActualEndDate(
							Utility.getDateFromISOString(recruitmentOpportunityMapper.getActualEndDate()));
					if (recruitmentOpportunityMapper.isOnboardInd() == true) {
						List<RecruitProfileLinkDetails> onbordedCandidate = recruitmentProfileDetailsRepository
								.getProfileDetailByRecruitmentIdAndonBoardInd(
										recruitProfileLinkDetails.getRecruitment_id());
						int onBoardedNo = onbordedCandidate.size() + 1;
						OpportunityRecruitDetails oppRecruitDetails = recruitmentOpportunityDetailsRepository
								.getRecruitmentDetailsByRecruitmentId(recruitProfileLinkDetails.getRecruitment_id());

						if (null != oppRecruitDetails.getEnd_date()) {
							candidateDetails
									.setAvailableDate(Utility.getDateAfterEndDate(((oppRecruitDetails.getEnd_date()))));
							candidateDetailsRepository.save(candidateDetails);
						}

						if (onBoardedNo >= oppRecruitDetails.getNumber()) {
							oppRecruitDetails.setCloseInd(true);
							recruitmentOpportunityDetailsRepository.save(oppRecruitDetails);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				recruitProfileDetailsRepository.save(recruitProfileLinkDetails);

				recruitmentOpportunityMapper1 = getProfileDetails(recruitmentOpportunityMapper.getProfileId());
				return recruitmentOpportunityMapper1;
			}
		}
		return recruitmentOpportunityMapper1;
	}

	@Override
	public void linkCandidateToRecruitmentForWebsite(WebSiteRecruitmentOpportunityMapper recruitmentOpportunityMapper) {
		List<RecruitmentCandidateLink> recruitmentCandidateLinkList = candidateLinkRepository
				.getCandidateList(recruitmentOpportunityMapper.getRecruitmentId());

		List<String> candiList = recruitmentOpportunityMapper.getCandidateIds();
		if (null != recruitmentCandidateLinkList && !recruitmentCandidateLinkList.isEmpty()) {
			if (null != candiList && !candiList.isEmpty()) {
				for (String candidateId : candiList) {

					boolean flag = false;
					for (RecruitmentCandidateLink recruitmentCandidateLink : recruitmentCandidateLinkList) {
						if (recruitmentCandidateLink.getCandidate_id().equals(candidateId)) {

							flag = true;
							break;

						}
					}
					if (flag == false) {
						RecruitmentProfileInfo recruitmentProfileInfo = new RecruitmentProfileInfo();
						recruitmentProfileInfo.setCreation_date(new Date());
						String profileId = recruitmentProfileInfoRepository.save(recruitmentProfileInfo)
								.getRecruitment_profile_info_id();

						RecruitProfileLinkDetails recruitProfileLinkDetails = new RecruitProfileLinkDetails();

						List<RecruitmentStageMapper> list = getStagesOfProcess(
								recruitmentOpportunityMapper.getRecruitmentProcessId());
						Collections.sort(list, (RecruitmentStageMapper c1, RecruitmentStageMapper c2) -> Double
								.compare(c1.getProbability(), c2.getProbability()));
						System.out.println("stageList>>>>" + list.toString());
						if (list.size() != 0) {
							String stageIdd = list.get(1).getStageId();
							recruitProfileLinkDetails.setStage_id(stageIdd);
						}
						recruitProfileLinkDetails.setProfile_id(profileId);
						recruitProfileLinkDetails.setOpp_id(recruitmentOpportunityMapper.getOpportunityId());
						recruitProfileLinkDetails.setRecruitment_id(recruitmentOpportunityMapper.getRecruitmentId());
						recruitProfileLinkDetails.setProcess_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
						recruitProfileLinkDetails.setCreation_date(new Date());
						recruitProfileLinkDetails.setLive_ind(true);
						recruitProfileLinkDetails.setIntrestInd(recruitmentOpportunityMapper.isIntrestInd());
						recruitProfileLinkDetails.setCandidateId(candidateId);
						recruitProfileLinkDetails.setRecruitOwner(recruitmentOpportunityMapper.getTagUserId());

						CandidateDetails candidateDetail = candidateDetailsRepository
								.getcandidateDetailsById(candidateId);
						if (null != candidateDetail) {
							candidateDetail.setCandiProcessInd(true);
						}

						recruitProfileDetailsRepository.save(recruitProfileLinkDetails);
						System.out.println("profileId=1::::::::::" + profileId);
						RecruitmentCandidateLink recruitmentCandidateLink1 = new RecruitmentCandidateLink();

						recruitmentCandidateLink1.setCandidate_id(candidateId);
						recruitmentCandidateLink1.setOpportunity_id(recruitmentOpportunityMapper.getOpportunityId());
						recruitmentCandidateLink1
								.setRecruitment_process_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
						recruitmentCandidateLink1.setProfileId(profileId);
						recruitmentCandidateLink1.setRecruitment_id(recruitmentOpportunityMapper.getRecruitmentId());
						recruitmentCandidateLink1.setCreation_date(new Date());
						recruitmentCandidateLink1.setLive_ind(true);
						recruitmentCandidateLink1.setUser_id(recruitmentOpportunityMapper.getTagUserId());
						candidateLinkRepository.save(recruitmentCandidateLink1);
						System.out.println(
								"profileId=" + candidateLinkRepository.save(recruitmentCandidateLink1).getProfileId()
										+ " " + "candidateId="
										+ candidateLinkRepository.save(recruitmentCandidateLink1).getCandidate_id());
						System.out.println("object:::::::::::"
								+ candidateLinkRepository.save(recruitmentCandidateLink1).toString());
					}
				}
			}

		} else {
			if (null != candiList && !candiList.isEmpty()) {
				for (String candidateId : candiList) {
					RecruitmentProfileInfo recruitmentProfileInfo = new RecruitmentProfileInfo();
					recruitmentProfileInfo.setCreation_date(new Date());
					String profileId = recruitmentProfileInfoRepository.save(recruitmentProfileInfo)
							.getRecruitment_profile_info_id();

					RecruitProfileLinkDetails recruitProfileLinkDetails = new RecruitProfileLinkDetails();

					List<RecruitmentStageMapper> list = getStagesOfProcess(
							recruitmentOpportunityMapper.getRecruitmentProcessId());
					Collections.sort(list, (RecruitmentStageMapper c1, RecruitmentStageMapper c2) -> Double
							.compare(c1.getProbability(), c2.getProbability()));
					System.out.println("stageList>>>>" + list.toString());
					if (list.size() != 0) {
						String stageIdd = list.get(1).getStageId();
						recruitProfileLinkDetails.setStage_id(stageIdd);
					}
					recruitProfileLinkDetails.setProfile_id(profileId);
					recruitProfileLinkDetails.setOpp_id(recruitmentOpportunityMapper.getOpportunityId());
					recruitProfileLinkDetails.setRecruitment_id(recruitmentOpportunityMapper.getRecruitmentId());
					recruitProfileLinkDetails.setProcess_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
					recruitProfileLinkDetails.setCreation_date(new Date());
					recruitProfileLinkDetails.setLive_ind(true);
					recruitProfileLinkDetails.setCandidateId(candidateId);
					recruitProfileLinkDetails.setIntrestInd(recruitmentOpportunityMapper.isIntrestInd());
					recruitProfileLinkDetails.setRecruitOwner(recruitmentOpportunityMapper.getTagUserId());

					recruitProfileDetailsRepository.save(recruitProfileLinkDetails);
					System.out.println("profileId=1::::::::::" + profileId);
					RecruitmentCandidateLink recruitmentCandidateLink1 = new RecruitmentCandidateLink();

					recruitmentCandidateLink1.setCandidate_id(candidateId);
					recruitmentCandidateLink1.setOpportunity_id(recruitmentOpportunityMapper.getOpportunityId());
					recruitmentCandidateLink1
							.setRecruitment_process_id(recruitmentOpportunityMapper.getRecruitmentProcessId());
					recruitmentCandidateLink1.setProfileId(profileId);
					recruitmentCandidateLink1.setRecruitment_id(recruitmentOpportunityMapper.getRecruitmentId());
					recruitmentCandidateLink1.setCreation_date(new Date());
					recruitmentCandidateLink1.setLive_ind(true);
					recruitmentCandidateLink1.setUser_id(recruitmentOpportunityMapper.getTagUserId());
					candidateLinkRepository.save(recruitmentCandidateLink1);
					System.out.println(
							"profileId=" + candidateLinkRepository.save(recruitmentCandidateLink1).getProfileId() + " "
									+ "candidateId="
									+ candidateLinkRepository.save(recruitmentCandidateLink1).getCandidate_id());
					System.out.println(
							"object:::::::::::" + candidateLinkRepository.save(recruitmentCandidateLink1).toString());
				}
			}
		}
	}

	@Override
	public List<CandidateProjectMapper> getListOfOpenProjectByCandidateId(String candidateId) {
		List<CandidateProjectMapper> CandidateProjectMapper = new ArrayList<CandidateProjectMapper>();

		Date todayDate = new Date();

		List<RecruitProfileLinkDetails> ProfileLinkDetails = recruitProfileDetailsRepository
				.getProfileDetailsByCandidateId(candidateId);
		if (null != ProfileLinkDetails && !ProfileLinkDetails.isEmpty()) {
			for (RecruitProfileLinkDetails recruitProfileLinkDetails : ProfileLinkDetails) {
				if (!StringUtils.isEmpty(recruitProfileLinkDetails.getProjectName())) {
					if (null != recruitProfileLinkDetails.getActualEndDate()) {

						if (Utility.removeTime(todayDate).getTime() <= Utility
								.removeTime(recruitProfileLinkDetails.getActualEndDate()).getTime()) {
							CandidateProjectMapper mapper = new CandidateProjectMapper();

							mapper.setActualEndDate(
									Utility.getISOFromDate(recruitProfileLinkDetails.getActualEndDate()));
							mapper.setBillableHour(recruitProfileLinkDetails.getBillableHour());
							mapper.setCandidateId(candidateId);
							mapper.setCreationDate(
									Utility.getISOFromDate(recruitProfileLinkDetails.getCreation_date()));
							mapper.setProjectId(recruitProfileLinkDetails.getProjectName());
							if (!StringUtils.isEmpty(recruitProfileLinkDetails.getProjectName())) {
								ProjectDetails projectDetails = projectRepository
										.getById(recruitProfileLinkDetails.getProjectName());
								if (null != projectDetails) {
									mapper.setProjectName(projectDetails.getProjectName());
								}
							}
							mapper.setUserId(recruitProfileLinkDetails.getUserId());
							mapper.setCustomerId(recruitProfileLinkDetails.getCustomerId());

							Customer customer = customerRepository
									.getCustomerDetailsByCustomerId(recruitProfileLinkDetails.getCustomerId());
							if (null != customer) {
								mapper.setCustomerName(customer.getName());
							}

							mapper.setOnboardDate(Utility.getISOFromDate(recruitProfileLinkDetails.getOnboard_date()));
							CandidateProjectMapper.add(mapper);
						}
					}
				}
			}
		}

		return CandidateProjectMapper;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getSuggestedRecquirementToCandidate(String candidateId, String orgId) {
		CandidateDetails candidateDetails = candidateDetailsRepository.getcandidateDetailsById(candidateId);
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		if (null != candidateDetails) {
			List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
					.getRequirementByWorkPreferenceAndbilling(candidateDetails.getWorkPreferance(),
							(Double.valueOf(candidateDetails.getBilling())));

			List<CandidateAddressLink> candidateAddressLinkList = candidateAddressLinkRepository
					.getAddressListByCandidateId(candidateId);
			AddressDetails addressDetails = null;
			if (candidateAddressLinkList.size() != 0) {
				addressDetails = addressRepository
						.getAddressDetailsByAddressId(candidateAddressLinkList.get(0).getAddressId());
				// System.out.println("candiAddId=" + addressDetails.getAddressId());
			}
			System.out.println("Candidate location==" + candidateDetails.getWorkLocation());
			System.out.println("addressDetails==" + addressDetails.getAddressId());
			if (null != addressDetails) {
				for (OpportunityRecruitDetails opportunityRecruitDetails : recruitDetailsList) {
					System.out.println("rECADDRESSID==" + opportunityRecruitDetails.getRecruitment_id());
					System.out.println("Candidate preference==" + candidateDetails.getWorkPreferance() + "||"
							+ "Recruit Preference==" + opportunityRecruitDetails.getWorkPreferance());
					List<RecruitmentAddressLink> recruitmentAddressLink = recruitmentAddressLinkRepository
							.getAddressListByRecruitmentId(opportunityRecruitDetails.getRecruitment_id());
					AddressDetails recruitAddress = null;
					if (recruitmentAddressLink.size() != 0) {
						recruitAddress = addressRepository
								.getAddressDetailsByAddressId(recruitmentAddressLink.get(0).getAddressId());
						System.out.println("recruitAddId=" + recruitAddress.getAddressId());
						System.out.println("candiLatitude=" + addressDetails.getLatitude() + ",candiLongitude"
								+ addressDetails.getLongitude() + "||jobLatitude" + recruitAddress.getLatitude()
								+ "jobLongitude" + recruitAddress.getLongitude());
					}

					int distance = 0;
					double candiLatitude = 0;
					double candiLongitude = 0;
					double jobLatitude;
					double jobLongitude;
					final double pi = Math.PI;

					if (null != addressDetails && addressDetails.getLatitude() != null
							&& !addressDetails.getLatitude().equals("") && addressDetails.getLongitude() != null
							&& !addressDetails.getLongitude().equals("") && null != recruitAddress
							&& recruitAddress.getLatitude() != null && !recruitAddress.getLatitude().equals("")
							&& recruitAddress.getLongitude() != null && !recruitAddress.getLongitude().equals("")) {
						System.out.println(
								"addressId%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + addressDetails.getAddressId());

//				if (!StringUtils.isEmpty(addressDetails.getLatitude())
//						&& !StringUtils.isEmpty(addressDetails.getLongitude())) {
						candiLatitude = Double.parseDouble(addressDetails.getLatitude());
						candiLongitude = Double.parseDouble(addressDetails.getLongitude());
						jobLatitude = Double.parseDouble(recruitAddress.getLatitude());
						jobLongitude = Double.parseDouble(recruitAddress.getLongitude());
						System.out.println("candiLatitude=" + candiLatitude + ",candiLongitude" + candiLongitude
								+ "||jobLatitude" + jobLatitude + "jobLongitude" + jobLongitude);
						distance = (int) (((Math
								.acos(Math.sin(candiLatitude * pi / 180) * Math.sin(jobLatitude * pi / 180)
										+ Math.cos(candiLatitude * pi / 180) * Math.cos(jobLatitude * pi / 180)
												* Math.cos((candiLongitude - jobLongitude) * pi / 180)))
								* 180 / pi) * 60 * 1.1515 * 1.609344);
						System.out.println("Distance==" + distance);
						if (distance <= 30) {
							List<SkillSetDetails> skillList = skillSetRepository.getSkillSetById(candidateId);
							// List<String> skills;
							int count = 0;
							for (SkillSetDetails skillSetDetails : skillList) {
								if (null != skillSetDetails) {
									DefinationDetails definationDetails1 = definationRepository
											.findByDefinationId(skillSetDetails.getSkillName());
									if (null != definationDetails1) {
										System.out.println("RecName==" + opportunityRecruitDetails.getName());
										System.out.println("deskill==" + opportunityRecruitDetails.getDescription()
												+ "||" + "libery==" + definationDetails1.getName());
										System.out.println(opportunityRecruitDetails.getDescription()
												.contains(definationDetails1.getName()));
										// if(opportunityRecruitDetails.getDescription().contains(definationDetails1.getName()))
										// {
										if (count < 1) {
											if (StringUtils.containsIgnoreCase(
													opportunityRecruitDetails.getDescription(),
													definationDetails1.getName())) {
												count++;
												System.out.println(
														"KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK===" + candidateId);
												System.out.println("skillName===" + definationDetails1.getName());
												RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
												recruitmentOpportunityMapper
														.setJobOrder(opportunityRecruitDetails.getJob_order());
												recruitmentOpportunityMapper.setRecruitmentId(
														opportunityRecruitDetails.getRecruitment_id());
												recruitmentOpportunityMapper.setOpportunityId(
														opportunityRecruitDetails.getOpportunity_id());
												recruitmentOpportunityMapper.setRecruitmentProcessId(
														opportunityRecruitDetails.getRecruitment_process_id());
												recruitmentOpportunityMapper
														.setLocation(opportunityRecruitDetails.getLocation());
												recruitmentOpportunityMapper
														.setRequirementName(opportunityRecruitDetails.getName());
												recruitmentOpportunityMapper
														.setDescription(opportunityRecruitDetails.getDescription());
												recruitmentOpportunityMapper
														.setNumber(opportunityRecruitDetails.getNumber());
												recruitmentOpportunityMapper
														.setBilling(opportunityRecruitDetails.getBilling());
												recruitmentOpportunityMapper
														.setCurrency(opportunityRecruitDetails.getCurrency());
												recruitmentOpportunityMapper.setLocation(recruitAddress.getCity());
												recruitmentOpportunityMapper
														.setType(opportunityRecruitDetails.getType());
												recruitmentOpportunityMapper.setAvilableDate(Utility
														.getISOFromDate(opportunityRecruitDetails.getAvailable_date()));
												recruitmentOpportunityMapper.setEndDate(Utility
														.getISOFromDate(opportunityRecruitDetails.getEnd_date()));
												recruitmentOpportunityMapper.setCloseByDate(Utility
														.getISOFromDate(opportunityRecruitDetails.getCloseByDate()));
												recruitmentOpportunityMapper.setCreationDate(Utility
														.getISOFromDate(opportunityRecruitDetails.getCreationDate()));
												recruitmentOpportunityMapper
														.setWorkType(opportunityRecruitDetails.getWorkType());
												recruitmentOpportunityMapper.setWorkPreference(
														opportunityRecruitDetails.getWorkPreferance());

												List<String> skillLibery = candidateService
														.getSkillSetOfSkillLibery(orgId);
												ArrayList<String> requiredSkills = new ArrayList<>();
												String skills = null;
												for (String liberySkill : skillLibery) {
													if (StringUtils.containsIgnoreCase(
															opportunityRecruitDetails.getDescription(), liberySkill)) {
														requiredSkills.add(liberySkill);
													}

												}
												skills = requiredSkills.stream().map(n -> String.valueOf(n))
														.collect(Collectors.joining(","));

												recruitmentOpportunityMapper.setSkillName(skills);

												mapperList.add(recruitmentOpportunityMapper);
											}
										}
									}
								}
							}
						}

					}
				}

			}
		}
		return mapperList;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getDashBoardRecruitmentByCandidateId(String candidateId) {
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
				.getActiveProfileDetailsByCandidateId(candidateId);
		// System.out.println("profileList="+profileList.toString());
		return profileList.stream().map(li -> {
			RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
			;
			// for (RecruitProfileLinkDetails recruitProfileLinkDetails : profileList) {
			OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
					.getRecruitmentDetailsByRecruitId(li.getRecruitment_id());
			if (opportunityRecruitDetails != null) {

				recruitmentOpportunityMapper.setRecruitmentId(opportunityRecruitDetails.getRecruitment_id());

				// recruitmentOpportunityMapper.setBilling(opportunityRecruitDetails.getBilling());
				recruitmentOpportunityMapper.setRequirementName(opportunityRecruitDetails.getName());
				recruitmentOpportunityMapper.setNumber(opportunityRecruitDetails.getNumber());
				recruitmentOpportunityMapper
						.setAvilableDate(Utility.getISOFromDate(opportunityRecruitDetails.getAvailable_date()));
				recruitmentOpportunityMapper
						.setEndDate(Utility.getISOFromDate(opportunityRecruitDetails.getEnd_date()));
				recruitmentOpportunityMapper
						.setCreationDate(Utility.getISOFromDate(opportunityRecruitDetails.getCreationDate()));
				recruitmentOpportunityMapper.setJobOrder(opportunityRecruitDetails.getJob_order());
				recruitmentOpportunityMapper.setExperience(opportunityRecruitDetails.getExperience());
				recruitmentOpportunityMapper.setLocation(opportunityRecruitDetails.getLocation());
				// System.out.println("idddd=="+li.getProcess_id());
				recruitmentOpportunityMapper.setStageName(getStageName(li.getStage_id()));
				// recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcess(li.getRecruitment_profile_details_id()));
				recruitmentOpportunityMapper.setStageId(li.getStage_id());
				recruitmentOpportunityMapper
						.setStageList(getActiveStagesOfProcess(opportunityRecruitDetails.getRecruitment_process_id()));
				recruitmentOpportunityMapper
						.setProcessName(getProcessName(opportunityRecruitDetails.getRecruitment_process_id()));

				if (li.isOnboard_ind() == true) {
					recruitmentOpportunityMapper.setResult("OnBoarded");
				} else {
					RecruitProfileStatusLink status = recruitmentProfileStatusLinkRepository
							.getRecruitProfileStatusLinkByProfileId(li.getProfile_id());
					if (null != status) {
						if (status.isApproveInd() == true) {
							recruitmentOpportunityMapper.setResult("Selected");
						} else {
							recruitmentOpportunityMapper.setResult("Rejected");
						}
					}
				}

				if (!StringUtils.isEmpty(opportunityRecruitDetails.getSponser_id())) {

					ContactDetails contactDetails = contactRepository
							.getContactDetailsById(opportunityRecruitDetails.getSponser_id());
					String middleName = " ";
					String lastName = " ";
					if (null != contactDetails.getMiddle_name()) {

						middleName = contactDetails.getMiddle_name();
					}
					if (null != contactDetails.getLast_name()) {

						lastName = contactDetails.getLast_name();
					}
					recruitmentOpportunityMapper
							.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
				}

				OpportunityDetails opportunityDetails = opportunityDetailsRepository
						.getopportunityDetailsById(opportunityRecruitDetails.getOpportunity_id());
				if (opportunityDetails != null) {
					if (!StringUtils.isEmpty(opportunityDetails.getCustomerId())) {
						Customer customer = customerRepository
								.getCustomerDetailsByCustomerId(opportunityDetails.getCustomerId());
						recruitmentOpportunityMapper.setCustomerName(customer.getName());
					}
					recruitmentOpportunityMapper.setOpprtunityName(opportunityDetails.getOpportunityName());
				}

				mapperList.add(recruitmentOpportunityMapper);
			}
			System.out.println("____________________________");
			// }
			return recruitmentOpportunityMapper;
		}).collect(Collectors.toList());
	}

	@Override
	public RecruitmentOpportunityMapper getRecruitmentDetailsByRecruitmentId(String recruitmentId) {

		RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
		OpportunityRecruitDetails li = recruitmentOpportunityDetailsRepository
				.getRecruitmentDetailsByRecruitId(recruitmentId);
		if (null != li) {

			recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
			System.out.println("getRecruitment_id---------------" + li.getRecruitment_id());
			recruitmentOpportunityMapper.setOpportunityId(li.getOpportunity_id());
			System.out.println("li.getOpportunity_id()----/-------/----" + li.getOpportunity_id());
			/*
			 * recruitmentOpportunityMapper.setStageId(li.getStage_id());
			 * recruitmentOpportunityMapper.setRecruitmentProcessId(li.getProcess_id());
			 * recruitmentOpportunityMapper.setProcessName(getProcessName(li.getProcess_id()
			 * ));
			 * recruitmentOpportunityMapper.setStageName(getStageName(li.getStage_id()));
			 * recruitmentOpportunityMapper.setProfileId(li.getProfile_id());
			 */
			recruitmentOpportunityMapper.setLocation(li.getLocation());
			recruitmentOpportunityMapper.setRequirementName(li.getName());
			recruitmentOpportunityMapper.setSponserId(li.getSponser_id());
			recruitmentOpportunityMapper.setDescription(li.getDescription());
			recruitmentOpportunityMapper.setNumber(li.getNumber());
			recruitmentOpportunityMapper.setBilling(li.getBilling());
			recruitmentOpportunityMapper.setCurrency(li.getCurrency());
			recruitmentOpportunityMapper.setType(li.getType());
			recruitmentOpportunityMapper.setAvilableDate(Utility.getISOFromDate(li.getAvailable_date()));
			recruitmentOpportunityMapper.setEndDate(Utility.getISOFromDate(li.getEnd_date()));
			recruitmentOpportunityMapper.setCloseByDate(Utility.getISOFromDate(li.getCloseByDate()));
			recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
			recruitmentOpportunityMapper.setWorkType(li.getWorkType());
			recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcess(li.getRecruitment_process_id()));

			recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
			recruitmentOpportunityMapper.setExperience(li.getExperience());
			recruitmentOpportunityMapper.setLocation(li.getLocation());
			recruitmentOpportunityMapper.setCategory(li.getCategory());
			recruitmentOpportunityMapper.setCountry(li.getCountry());
			recruitmentOpportunityMapper.setDepartment(li.getDepartment());
			recruitmentOpportunityMapper.setRole(li.getRole());
			recruitmentOpportunityMapper.setRecruitmentProcessId(li.getRecruitment_process_id());
			recruitmentOpportunityMapper.setWorkPreference(li.getWorkPreferance());
			recruitmentOpportunityMapper.setCloseInd(li.isCloseInd());
			if (!StringUtils.isEmpty(li.getUserId())) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(li.getUserId());
				if (null != employeeDetails) {
					String middleName = " ";
					String lastName = " ";

					if (null != employeeDetails.getLastName()) {

						lastName = employeeDetails.getLastName();
					}
					if (null != employeeDetails.getMiddleName()) {

						middleName = employeeDetails.getMiddleName();
						recruitmentOpportunityMapper
								.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {
						recruitmentOpportunityMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
					}
				}
			}
			List<RecruitProfileLinkDetails> onbordingList = recruitmentProfileDetailsRepository
					.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());
			if (null != onbordingList) {
				recruitmentOpportunityMapper.setOnBoardNo(onbordingList.size());
			}

			RecruitmentPublishDetails recruitmentPublishDetails = recruitmentPublishRepository
					.getRecruitmentPublishDetails(li.getRecruitment_id());
			if (null != recruitmentPublishDetails) {

				recruitmentOpportunityMapper.setPublishInd(true);
			} else {

				recruitmentOpportunityMapper.setPublishInd(false);
			}

			if (!StringUtils.isEmpty(li.getRecruiter_id())) {
				EmployeeDetails employeeDetails = employeeRepository
						.getEmployeeDetailsByEmployeeId(li.getRecruiter_id());
				String middleName = " ";
				String lastName = " ";
				if (employeeDetails.getMiddleName() != null) {

					middleName = employeeDetails.getMiddleName();
				}
				if (employeeDetails.getLastName() != null) {

					lastName = employeeDetails.getLastName();
				}

				recruitmentOpportunityMapper
						.setRecruiterName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
			}

			if (!StringUtils.isEmpty(li.getSponser_id())) {

				ContactDetails contactDetails = contactRepository.getContactDetailsById(li.getSponser_id());
				String middleName = " ";
				String lastName = " ";
				if (null != contactDetails.getMiddle_name()) {

					middleName = contactDetails.getMiddle_name();
				}
				if (null != contactDetails.getLast_name()) {

					lastName = contactDetails.getLast_name();
				}
				recruitmentOpportunityMapper
						.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
			}

			/* Add skill set */
			recruitmentOpportunityMapper.setSkillSetList(candidateService.getSkillSetOfSkillLibery(li.getOrgId()));
			List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
					.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
			if (recruitmentSkillsetLink.size() != 0) {
				recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

			}

			List<RecruitmentCandidateLink> recruitmentCandidateLink = candidateLinkRepository
					.getCandidateList(li.getRecruitment_id());
			System.out.println("recruitmentCandidateLink::::::" + recruitmentCandidateLink.toString());
			if (null != recruitmentCandidateLink && !recruitmentCandidateLink.isEmpty()) {
				System.out.println("size:::::::::" + recruitmentCandidateLink.size());

				List<CandidateMapper> resultList = new ArrayList<>();
				int count = 0;
				for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
					CandidateMapper mapper = new CandidateMapper();
					CandidateDetails candidate = candidateDetailsRepository
							.getcandidateDetailsById(candidateLink.getCandidate_id());
					if (null != candidate) {
						String middleName = " ";
						String lastName = " ";
						if (null != candidate.getMiddleName()) {

							middleName = candidate.getMiddleName();
						}
						if (null != candidate.getLastName()) {

							lastName = candidate.getLastName();
						}
						mapper.setCandidateId(candidate.getCandidateId());
						mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
						mapper.setImageId(candidate.getImageId());
					}
					resultList.add(mapper);
					count++;
					if (count == 2) {
						break;
					}
				}
				recruitmentOpportunityMapper.setCandidatetList(resultList);

				List<CandidateMapper> resultList2 = new ArrayList<>();
				for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
					CandidateMapper mapper = new CandidateMapper();
					CandidateDetails candidate = candidateDetailsRepository
							.getcandidateDetailsById(candidateLink.getCandidate_id());
					if (null != candidate) {
						String middleName = " ";
						String lastName = " ";
						if (null != candidate.getMiddleName()) {

							middleName = candidate.getMiddleName();
						}
						if (null != candidate.getLastName()) {

							lastName = candidate.getLastName();
						}
						mapper.setCandidateId(candidate.getCandidateId());
						mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
						mapper.setImageId(candidate.getImageId());
					}
					resultList2.add(mapper);
				}
				recruitmentOpportunityMapper.setFiltercandidatetList(resultList2);
			}
			recruitmentOpportunityMapper.setCandidateNo(candidateNo(recruitmentCandidateLink.size()));
			/*
			 * RecruitProfileStatusLink recruitProfileStatusLink =
			 * recruitmentProfileStatusLinkRepository
			 * .getRecruitProfileStatusLinkByProfileId(li.getProfile_id()); if (null !=
			 * recruitProfileStatusLink) {
			 * recruitmentOpportunityMapper.setApproveInd(recruitProfileStatusLink.
			 * isApproveInd());
			 * recruitmentOpportunityMapper.setRejectInd(recruitProfileStatusLink.
			 * isRejectInd()); }
			 */

			/*
			 * List<RecruitmentRecruiterLink> recruiterList =
			 * recruitmentRecruiterLinkRepository
			 * .getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id()); if (null
			 * != recruiterList && !recruiterList.isEmpty()) { List<String> resultList = new
			 * ArrayList<>(); for (RecruitmentRecruiterLink recruiter : recruiterList) {
			 * //EmployeeMapper mapper = new EmployeeMapper(); String recruiters = null;
			 * EmployeeDetails emp =
			 * employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
			 * System.out.println("name....." + emp.getFullName()); recruiters
			 * =emp.getFullName(); recruiters=emp.getId(); resultList.add(recruiters); }
			 * recruitmentOpportunityMapper.setRecruiterNames(resultList); }
			 */

			List<RecruitmentRecruiterLink> recruiterList = recruitmentRecruiterLinkRepository
					.getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id());
			if (null != recruiterList && !recruiterList.isEmpty()) {
				List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
				for (RecruitmentRecruiterLink recruiter : recruiterList) {
					EmployeeMapper mapper = new EmployeeMapper();
					EmployeeDetails emp = employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
					// System.out.println("name....." + emp.getFullName());
					mapper.setFullName(emp.getFullName());
					mapper.setEmployeeId(recruiter.getRecruiterId());

					resultList.add(mapper);
				}
				recruitmentOpportunityMapper.setRecruiterList(resultList);
			}

			List<RecruitmentAddressLink> recruitmentAddressLink = recruitmentAddressLinkRepository
					.getAddressListByRecruitmentId(li.getRecruitment_id());
			List<AddressMapper> addressList = new ArrayList<AddressMapper>();
			if (null != recruitmentAddressLink && !recruitmentAddressLink.isEmpty()) {

				for (RecruitmentAddressLink recruitmentAddressLink1 : recruitmentAddressLink) {
					AddressDetails addressDetails = addressRepository
							.getAddressDetailsByAddressId(recruitmentAddressLink1.getAddressId());

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
						addressList.add(addressMapper);
					}
				}

				System.out.println("addressList.......... " + addressList);
			}
			recruitmentOpportunityMapper.setAddress(addressList);

			List<RecruitmentPartnerLink> recruitmentPartnerLinkList = recruitmentPartnerLinkRepository
					.findByRecruitmentIdAndLiveInd(li.getRecruitment_id(), true);
			if (null != recruitmentPartnerLinkList && !recruitmentPartnerLinkList.isEmpty()) {
				List<PartnerMapper> partnerResultList = new ArrayList<PartnerMapper>();
				for (RecruitmentPartnerLink partner : recruitmentPartnerLinkList) {
					PartnerMapper mapper = new PartnerMapper();
					System.out.println("partner::::::::::" + partner.getPartnerId());
					System.out.println("recID:::::::::::" + partner.getRecruitmentId());
					PartnerDetails dbPartner = partnerDetailsRepository.getPartnerDetailsById(partner.getPartnerId());
					if (null != dbPartner) {
						mapper.setPartnerName(dbPartner.getPartnerName());
						mapper.setPartnerId(dbPartner.getPartnerId());

						partnerResultList.add(mapper);
					}
				}
				recruitmentOpportunityMapper.setPartnerList(partnerResultList);
			}
		}
		return recruitmentOpportunityMapper;
	}

	@Override
	public List<CandidateProjectMapper> getListOfCustomerNameFromOpenProjectByCandidateIdForWeb(String candidateId) {
		List<CandidateProjectMapper> CandidateProjectMapper = new ArrayList<CandidateProjectMapper>();

		Date todayDate = new Date();

		List<RecruitProfileLinkDetails> ProfileLinkDetails = recruitProfileDetailsRepository
				.getProfileDetailsByCandidateId(candidateId);
		if (null != ProfileLinkDetails && !ProfileLinkDetails.isEmpty()) {
			for (RecruitProfileLinkDetails recruitProfileLinkDetails : ProfileLinkDetails) {
				if (!StringUtils.isEmpty(recruitProfileLinkDetails.getCustomerId())) {
					if (null != recruitProfileLinkDetails.getActualEndDate()) {

						if (Utility.removeTime(todayDate).getTime() <= Utility
								.removeTime(recruitProfileLinkDetails.getActualEndDate()).getTime()) {
							CandidateProjectMapper mapper = new CandidateProjectMapper();

							mapper.setActualEndDate(
									Utility.getISOFromDate(recruitProfileLinkDetails.getActualEndDate()));
							mapper.setBillableHour(recruitProfileLinkDetails.getBillableHour());
							mapper.setCandidateId(candidateId);
							mapper.setCreationDate(
									Utility.getISOFromDate(recruitProfileLinkDetails.getCreation_date()));

							mapper.setProjectId(recruitProfileLinkDetails.getProjectName());
							if (!StringUtils.isEmpty(recruitProfileLinkDetails.getProjectName())) {
								ProjectDetails projectDetails = projectRepository
										.getById(recruitProfileLinkDetails.getProjectName());
								if (null != projectDetails) {
									mapper.setProjectName(projectDetails.getProjectName());
								}
							}
							mapper.setUserId(recruitProfileLinkDetails.getUserId());
							mapper.setCustomerId(recruitProfileLinkDetails.getCustomerId());

							Customer customer = customerRepository
									.getCustomerDetailsByCustomerId(recruitProfileLinkDetails.getCustomerId());
							if (null != customer) {
								mapper.setCustomerName(customer.getName());
							}

							mapper.setOnboardDate(Utility.getISOFromDate(recruitProfileLinkDetails.getOnboard_date()));
							CandidateProjectMapper.add(mapper);
						}
					}
				}
			}
		}
		List<CandidateProjectMapper> CandidateProjectMapper1 = new ArrayList<CandidateProjectMapper>();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (CandidateProjectMapper CandidateProjectMapper2 : CandidateProjectMapper) {
			if (!map.containsKey(CandidateProjectMapper2.getCustomerId())) {
				map.put(CandidateProjectMapper2.getCustomerId(), 1);
				CandidateProjectMapper1.add(CandidateProjectMapper2);
			}
		}
		return CandidateProjectMapper1;
	}

	@Override
	public List<CandidateProjectMapper> getListOfProjectNameByCustomerId(String customerId) {
		List<CandidateProjectMapper> candidateProjectMapper = new ArrayList<CandidateProjectMapper>();

		List<RecruitProfileLinkDetails> ProfileLinkDetails = recruitProfileDetailsRepository
				.getProfileDetailsByCustomerId(customerId);
		if (null != ProfileLinkDetails && !ProfileLinkDetails.isEmpty()) {

			for (RecruitProfileLinkDetails recruitProfileLinkDetails : ProfileLinkDetails) {

				CandidateProjectMapper mapper = new CandidateProjectMapper();
				// mapper.setProjectName(recruitProfileLinkDetails.getProjectName());
				mapper.setCustomerId(customerId);
				mapper.setActualEndDate(Utility.getISOFromDate(recruitProfileLinkDetails.getActualEndDate()));
				mapper.setBillableHour(recruitProfileLinkDetails.getBillableHour());
				mapper.setCandidateId(recruitProfileLinkDetails.getCandidateId());
				mapper.setCreationDate(Utility.getISOFromDate(recruitProfileLinkDetails.getCreation_date()));

				mapper.setProjectId(recruitProfileLinkDetails.getProjectName());
				if (!StringUtils.isEmpty(recruitProfileLinkDetails.getProjectName())) {
					ProjectDetails projectDetails = projectRepository
							.getById(recruitProfileLinkDetails.getProjectName());
					if (null != projectDetails) {
						mapper.setProjectName(projectDetails.getProjectName());
					}
				}
				mapper.setUserId(recruitProfileLinkDetails.getUserId());
				mapper.setOnboardDate(Utility.getISOFromDate(recruitProfileLinkDetails.getOnboard_date()));
				Customer customer = customerRepository
						.getCustomerDetailsByCustomerId(recruitProfileLinkDetails.getCustomerId());
				if (null != customer) {
					mapper.setCustomerName(customer.getName());
				}
				candidateProjectMapper.add(mapper);
			}
		}

		List<CandidateProjectMapper> candidateProjectMapper1 = new ArrayList<CandidateProjectMapper>();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (CandidateProjectMapper CandidateProjectMapper2 : candidateProjectMapper) {

			if (!map.containsKey(CandidateProjectMapper2.getProjectId())) {
				System.out.println(
						"CandidateProjectMapper2.getProjectId()=========" + CandidateProjectMapper2.getProjectId());
				map.put(CandidateProjectMapper2.getProjectId(), 1);
				candidateProjectMapper1.add(CandidateProjectMapper2);
			}
		}

		return candidateProjectMapper1;

	}

	@Override
	public List<CandidateProjectMapper> getListOfCandidateByProjectName(String projectName) {
		List<CandidateProjectMapper> candidateProjectMapper = new ArrayList<CandidateProjectMapper>();

		List<RecruitProfileLinkDetails> ProfileLinkDetails = recruitProfileDetailsRepository
				.getCandidateByProjectName(projectName);
		if (null != ProfileLinkDetails && !ProfileLinkDetails.isEmpty()) {

			for (RecruitProfileLinkDetails recruitProfileLinkDetails : ProfileLinkDetails) {
				CandidateProjectMapper mapper = new CandidateProjectMapper();
				// mapper.setProjectName(recruitProfileLinkDetails.getProjectName());
				mapper.setCustomerId(recruitProfileLinkDetails.getCustomerId());
				Customer customer = customerRepository
						.getCustomerDetailsByCustomerId(recruitProfileLinkDetails.getCustomerId());
				if (null != customer) {
					mapper.setCustomerName(customer.getName());
				}
				mapper.setActualEndDate(Utility.getISOFromDate(recruitProfileLinkDetails.getActualEndDate()));
				mapper.setBillableHour(recruitProfileLinkDetails.getBillableHour());
				mapper.setCandidateId(recruitProfileLinkDetails.getCandidateId());
				mapper.setCreationDate(Utility.getISOFromDate(recruitProfileLinkDetails.getCreation_date()));

				mapper.setProjectId(recruitProfileLinkDetails.getProjectName());
				if (!StringUtils.isEmpty(recruitProfileLinkDetails.getProjectName())) {
					ProjectDetails projectDetails = projectRepository
							.getById(recruitProfileLinkDetails.getProjectName());
					if (null != projectDetails) {
						mapper.setProjectName(projectDetails.getProjectName());
					}
				}
				mapper.setUserId(recruitProfileLinkDetails.getUserId());
				mapper.setOnboardDate(Utility.getISOFromDate(recruitProfileLinkDetails.getOnboard_date()));

				CandidateDetails candidateDetails = candidateDetailsRepository
						.getcandidateDetailsById(recruitProfileLinkDetails.getCandidateId());
				if (null != candidateDetails) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

						lastName = candidateDetails.getLastName();
					}

					if (candidateDetails.getMiddleName() != null && candidateDetails.getMiddleName().length() > 0) {

						middleName = candidateDetails.getMiddleName();
						mapper.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						mapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
					}
				}

				candidateProjectMapper.add(mapper);
			}
		}
		return candidateProjectMapper;
	}

	@Override
	public List<CandidateProjectMapper> getListOfOpenProjectByOrgId(String orgId) {
		List<CandidateProjectMapper> CandidateProjectMapper = new ArrayList<CandidateProjectMapper>();

		Date todayDate = new Date();

		List<RecruitProfileLinkDetails> ProfileLinkDetails = recruitProfileDetailsRepository
				.getProfileDetailsByOrgId(orgId);
		if (null != ProfileLinkDetails && !ProfileLinkDetails.isEmpty()) {
			for (RecruitProfileLinkDetails recruitProfileLinkDetails : ProfileLinkDetails) {
				if (!StringUtils.isEmpty(recruitProfileLinkDetails.getProjectName())) {
					if (null != recruitProfileLinkDetails.getActualEndDate()) {

						if (Utility.removeTime(todayDate).getTime() <= Utility
								.removeTime(recruitProfileLinkDetails.getActualEndDate()).getTime()) {
							CandidateProjectMapper mapper = new CandidateProjectMapper();

							mapper.setActualEndDate(
									Utility.getISOFromDate(recruitProfileLinkDetails.getActualEndDate()));
							mapper.setBillableHour(recruitProfileLinkDetails.getBillableHour());
							mapper.setCandidateId(recruitProfileLinkDetails.getCandidateId());
							mapper.setCreationDate(
									Utility.getISOFromDate(recruitProfileLinkDetails.getCreation_date()));

							mapper.setProjectId(recruitProfileLinkDetails.getProjectName());
							if (!StringUtils.isEmpty(recruitProfileLinkDetails.getProjectName())) {
								ProjectDetails projectDetails = projectRepository
										.getById(recruitProfileLinkDetails.getProjectName());
								if (null != projectDetails) {
									mapper.setProjectName(projectDetails.getProjectName());
								}
							}
							mapper.setUserId(recruitProfileLinkDetails.getUserId());
							mapper.setCustomerId(recruitProfileLinkDetails.getCustomerId());

							Customer customer = customerRepository
									.getCustomerDetailsByCustomerId(recruitProfileLinkDetails.getCustomerId());
							if (null != customer) {
								mapper.setCustomerName(customer.getName());
							}

							mapper.setOnboardDate(Utility.getISOFromDate(recruitProfileLinkDetails.getOnboard_date()));
							CandidateProjectMapper.add(mapper);
						}
					}
				}
			}
		}

		return CandidateProjectMapper;
	}

	@Override
	public List<CandidateProjectMapper> getListOfOpenProjectCandidateListByOrgId(String orgId) {
		List<CandidateProjectMapper> CandidateProjectMapper = new ArrayList<CandidateProjectMapper>();

		Date todayDate = new Date();

		List<RecruitProfileLinkDetails> ProfileLinkDetails = recruitProfileDetailsRepository
				.getProfileDetailsByOrgId(orgId);
		if (null != ProfileLinkDetails && !ProfileLinkDetails.isEmpty()) {
			for (RecruitProfileLinkDetails recruitProfileLinkDetails : ProfileLinkDetails) {
				if (!StringUtils.isEmpty(recruitProfileLinkDetails.getCandidateId())) {
					if (null != recruitProfileLinkDetails.getActualEndDate()) {

						if (Utility.removeTime(todayDate).getTime() <= Utility
								.removeTime(recruitProfileLinkDetails.getActualEndDate()).getTime()) {
							CandidateProjectMapper mapper = new CandidateProjectMapper();

							mapper.setActualEndDate(
									Utility.getISOFromDate(recruitProfileLinkDetails.getActualEndDate()));
							mapper.setBillableHour(recruitProfileLinkDetails.getBillableHour());
							mapper.setCandidateId(recruitProfileLinkDetails.getCandidateId());

							CandidateDetails candidateDetails = candidateDetailsRepository
									.getcandidateDetailsById(recruitProfileLinkDetails.getCandidateId());
							if (null != candidateDetails) {
								String middleName = " ";
								String lastName = "";

								if (!StringUtils.isEmpty(candidateDetails.getLastName())) {

									lastName = candidateDetails.getLastName();
								}

								if (candidateDetails.getMiddleName() != null
										&& candidateDetails.getMiddleName().length() > 0) {

									middleName = candidateDetails.getMiddleName();
									mapper.setCandidateName(
											candidateDetails.getFirstName() + " " + middleName + " " + lastName);
								} else {

									mapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
								}
							}

							mapper.setCreationDate(
									Utility.getISOFromDate(recruitProfileLinkDetails.getCreation_date()));

							mapper.setProjectId(recruitProfileLinkDetails.getProjectName());
							if (!StringUtils.isEmpty(recruitProfileLinkDetails.getProjectName())) {
								ProjectDetails projectDetails = projectRepository
										.getById(recruitProfileLinkDetails.getProjectName());
								if (null != projectDetails) {
									mapper.setProjectName(projectDetails.getProjectName());
								}
							}
							mapper.setUserId(recruitProfileLinkDetails.getUserId());
							mapper.setCustomerId(recruitProfileLinkDetails.getCustomerId());

							Customer customer = customerRepository
									.getCustomerDetailsByCustomerId(recruitProfileLinkDetails.getCustomerId());
							if (null != customer) {
								mapper.setCustomerName(customer.getName());
							}

							mapper.setOnboardDate(Utility.getISOFromDate(recruitProfileLinkDetails.getOnboard_date()));
							CandidateProjectMapper.add(mapper);
						}
					}
				}
			}
		}

		return CandidateProjectMapper;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getAllRecruitmentsByOrgId(String orgId, String userId) {
		List<OpportunityRecruitDetails> recruitDetailsList = recruitmentOpportunityDetailsRepository
				.getAllRecriutmentsByOrgIdAndLiveInd(orgId);
		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();
		if (null != recruitDetailsList && !recruitDetailsList.isEmpty()) {
			recruitDetailsList.stream().map(li -> {

				RecruitmentOpportunityMapper recruitmentOpportunityMapper = new RecruitmentOpportunityMapper();
				recruitmentOpportunityMapper.setRecruitmentId(li.getRecruitment_id());
				recruitmentOpportunityMapper.setOpportunityId(li.getOpportunity_id());
				/*
				 * recruitmentOpportunityMapper.setStageId(li.getStage_id());
				 * recruitmentOpportunityMapper.setRecruitmentProcessId(li.getProcess_id());
				 * recruitmentOpportunityMapper.setProcessName(getProcessName(li.getProcess_id()
				 * ));
				 * recruitmentOpportunityMapper.setStageName(getStageName(li.getStage_id()));
				 * recruitmentOpportunityMapper.setProfileId(li.getProfile_id());
				 */
				recruitmentOpportunityMapper.setLocation(li.getLocation());
				recruitmentOpportunityMapper.setRequirementName(li.getName());
				recruitmentOpportunityMapper.setSponserId(li.getSponser_id());
				recruitmentOpportunityMapper.setDescription(li.getDescription());
				recruitmentOpportunityMapper.setNumber(li.getNumber());
				recruitmentOpportunityMapper.setBilling(li.getBilling());
				recruitmentOpportunityMapper.setCurrency(li.getCurrency());
				recruitmentOpportunityMapper.setType(li.getType());
				recruitmentOpportunityMapper.setAvilableDate(Utility.getISOFromDate(li.getAvailable_date()));
				recruitmentOpportunityMapper.setEndDate(Utility.getISOFromDate(li.getEnd_date()));
				recruitmentOpportunityMapper.setCloseByDate(Utility.getISOFromDate(li.getCloseByDate()));
				recruitmentOpportunityMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));

				recruitmentOpportunityMapper.setStageList(getActiveStagesOfProcess(li.getRecruitment_process_id()));
				recruitmentOpportunityMapper.setJobOrder(li.getJob_order());
				recruitmentOpportunityMapper.setExperience(li.getExperience());
				recruitmentOpportunityMapper.setLocation(li.getLocation());
				recruitmentOpportunityMapper.setCategory(li.getCategory());
				recruitmentOpportunityMapper.setCountry(li.getCountry());
				recruitmentOpportunityMapper.setDepartment(li.getDepartment());
				recruitmentOpportunityMapper.setRole(li.getRole());
				recruitmentOpportunityMapper.setRecruitmentProcessId(li.getRecruitment_process_id());
				recruitmentOpportunityMapper.setWorkPreference(li.getWorkPreferance());
				recruitmentOpportunityMapper.setCloseInd(li.isCloseInd());
				if (!StringUtils.isEmpty(li.getUserId())) {
					EmployeeDetails employee = employeeRepository.getEmployeeDetailsByEmployeeId(li.getUserId());
					recruitmentOpportunityMapper.setRecruitOwner(employee.getFullName());
				}
				List<RecruitProfileLinkDetails> onbordingList = recruitmentProfileDetailsRepository
						.getProfileDetailByRecruitmentIdAndonBoardInd(li.getRecruitment_id());
				if (null != onbordingList) {
					recruitmentOpportunityMapper.setOnBoardNo(onbordingList.size());
				}

				RecruitmentPublishDetails recruitmentPublishDetails = recruitmentPublishRepository
						.getRecruitmentPublishDetails(li.getRecruitment_id());
				if (null != recruitmentPublishDetails) {

					recruitmentOpportunityMapper.setPublishInd(true);
				} else {

					recruitmentOpportunityMapper.setPublishInd(false);
				}

				if (!StringUtils.isEmpty(li.getOpportunity_id())) {
					OpportunityDetails opportunityDetails = opportunityDetailsRepository
							.findByOppId(li.getOpportunity_id());
					if (null != opportunityDetails) {
						if (!StringUtils.isEmpty(opportunityDetails.getCustomerId())) {
							recruitmentOpportunityMapper.setCustomerId(opportunityDetails.getCustomerId());
							Customer customer = customerRepository
									.getCustomerByIdAndLiveInd(opportunityDetails.getCustomerId());
							if (null != customer) {
								if (!StringUtils.isEmpty(customer.getName())) {
									recruitmentOpportunityMapper.setCustomerName(customer.getName());
								}
							}
						}
					}
				}

				if (!StringUtils.isEmpty(li.getRecruiter_id())) {
					EmployeeDetails employeeDetails = employeeRepository
							.getEmployeeDetailsByEmployeeId(li.getRecruiter_id());
					String middleName = " ";
					String lastName = " ";
					if (employeeDetails.getMiddleName() != null) {

						middleName = employeeDetails.getMiddleName();
					}
					if (employeeDetails.getLastName() != null) {

						lastName = employeeDetails.getLastName();
					}

					recruitmentOpportunityMapper
							.setRecruiterName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				}

				if (!StringUtils.isEmpty(li.getSponser_id())) {

					ContactDetails contactDetails = contactRepository.getContactDetailsById(li.getSponser_id());
					String middleName = " ";
					String lastName = " ";
					if (null != contactDetails.getMiddle_name()) {

						middleName = contactDetails.getMiddle_name();
					}
					if (null != contactDetails.getLast_name()) {

						lastName = contactDetails.getLast_name();
					}
					recruitmentOpportunityMapper
							.setSponserName(contactDetails.getFirst_name() + " " + middleName + " " + lastName);
				}

				/* Add skill set */
				recruitmentOpportunityMapper.setSkillSetList(candidateService.getSkillSetOfSkillLibery(orgId));
				List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
						.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
				if (recruitmentSkillsetLink.size() != 0) {
					recruitmentOpportunityMapper.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

				}

				List<RecruitmentCandidateLink> recruitmentCandidateLink = candidateLinkRepository
						.getCandidateList(li.getRecruitment_id());
				System.out.println("recruitmentCandidateLink::::::" + recruitmentCandidateLink.toString());
				if (null != recruitmentCandidateLink && !recruitmentCandidateLink.isEmpty()) {
					System.out.println("size:::::::::" + recruitmentCandidateLink.size());

					List<CandidateMapper> resultList = new ArrayList<>();
					int count = 0;
					for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
						CandidateMapper mapper = new CandidateMapper();
						CandidateDetails candidate = candidateDetailsRepository
								.getcandidateDetailsById(candidateLink.getCandidate_id());
						if (null != candidate) {
							String middleName = " ";
							String lastName = " ";
							if (null != candidate.getMiddleName()) {

								middleName = candidate.getMiddleName();
							}
							if (null != candidate.getLastName()) {

								lastName = candidate.getLastName();
							}
							mapper.setCandidateId(candidate.getCandidateId());
							mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
							mapper.setImageId(candidate.getImageId());
						}
						resultList.add(mapper);
						count++;
						if (count == 2) {
							break;
						}
					}
					recruitmentOpportunityMapper.setCandidatetList(resultList);

					List<CandidateMapper> resultList2 = new ArrayList<>();
					for (RecruitmentCandidateLink candidateLink : recruitmentCandidateLink) {
						CandidateMapper mapper = new CandidateMapper();
						CandidateDetails candidate = candidateDetailsRepository
								.getcandidateDetailsById(candidateLink.getCandidate_id());
						if (null != candidate) {
							String middleName = " ";
							String lastName = " ";
							if (null != candidate.getMiddleName()) {

								middleName = candidate.getMiddleName();
							}
							if (null != candidate.getLastName()) {

								lastName = candidate.getLastName();
							}
							mapper.setCandidateId(candidate.getCandidateId());
							mapper.setFullName(candidate.getFirstName() + " " + middleName + " " + lastName);
							mapper.setImageId(candidate.getImageId());
						}
						resultList2.add(mapper);
					}
					recruitmentOpportunityMapper.setFiltercandidatetList(resultList2);
				}
				recruitmentOpportunityMapper.setCandidateNo(candidateNo(recruitmentCandidateLink.size()));
				/*
				 * RecruitProfileStatusLink recruitProfileStatusLink =
				 * recruitmentProfileStatusLinkRepository
				 * .getRecruitProfileStatusLinkByProfileId(li.getProfile_id()); if (null !=
				 * recruitProfileStatusLink) {
				 * recruitmentOpportunityMapper.setApproveInd(recruitProfileStatusLink.
				 * isApproveInd());
				 * recruitmentOpportunityMapper.setRejectInd(recruitProfileStatusLink.
				 * isRejectInd()); }
				 */

				/*
				 * List<RecruitmentRecruiterLink> recruiterList =
				 * recruitmentRecruiterLinkRepository
				 * .getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id()); if (null
				 * != recruiterList && !recruiterList.isEmpty()) { List<String> resultList = new
				 * ArrayList<>(); for (RecruitmentRecruiterLink recruiter : recruiterList) {
				 * //EmployeeMapper mapper = new EmployeeMapper(); String recruiters = null;
				 * EmployeeDetails emp =
				 * employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
				 * System.out.println("name....." + emp.getFullName()); recruiters
				 * =emp.getFullName(); recruiters=emp.getId(); resultList.add(recruiters); }
				 * recruitmentOpportunityMapper.setRecruiterNames(resultList); }
				 */

				List<RecruitmentRecruiterLink> recruiterList = recruitmentRecruiterLinkRepository
						.getRecruitmentRecruiterLinkByRecruitmentId(li.getRecruitment_id());
				if (null != recruiterList && !recruiterList.isEmpty()) {
					List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
					for (RecruitmentRecruiterLink recruiter : recruiterList) {
						EmployeeMapper mapper = new EmployeeMapper();
						EmployeeDetails emp = employeeRepository.getEmployeesByuserId(recruiter.getRecruiterId());
						// System.out.println("name....." + emp.getFullName());
						mapper.setFullName(emp.getFullName());
						mapper.setEmployeeId(recruiter.getRecruiterId());

						resultList.add(mapper);
					}
					recruitmentOpportunityMapper.setRecruiterList(resultList);
				}

				List<RecruitmentAddressLink> recruitmentAddressLink = recruitmentAddressLinkRepository
						.getAddressListByRecruitmentId(li.getRecruitment_id());
				List<AddressMapper> addressList = new ArrayList<AddressMapper>();
				if (null != recruitmentAddressLink && !recruitmentAddressLink.isEmpty()) {

					for (RecruitmentAddressLink recruitmentAddressLink1 : recruitmentAddressLink) {
						AddressDetails addressDetails = addressRepository
								.getAddressDetailsByAddressId(recruitmentAddressLink1.getAddressId());

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
				recruitmentOpportunityMapper.setAddress(addressList);

				List<RecruitmentPartnerLink> recruitmentPartnerLinkList = recruitmentPartnerLinkRepository
						.findByRecruitmentIdAndLiveInd(li.getRecruitment_id(), true);
				if (null != recruitmentPartnerLinkList && !recruitmentPartnerLinkList.isEmpty()) {
					List<PartnerMapper> partnerResultList = new ArrayList<PartnerMapper>();
					for (RecruitmentPartnerLink partner : recruitmentPartnerLinkList) {
						PartnerMapper mapper = new PartnerMapper();
						System.out.println("partner::::::::::" + partner.getPartnerId());
						System.out.println("recID:::::::::::" + partner.getRecruitmentId());
						PartnerDetails dbPartner = partnerDetailsRepository
								.getPartnerDetailsById(partner.getPartnerId());
						if (null != dbPartner) {
							mapper.setPartnerName(dbPartner.getPartnerName());
							mapper.setPartnerId(dbPartner.getPartnerId());

							partnerResultList.add(mapper);
						}
					}
					recruitmentOpportunityMapper.setPartnerList(partnerResultList);
				}
				return mapperList.add(recruitmentOpportunityMapper);

			}).collect(Collectors.toList());
		}

		return mapperList;
	}

}
