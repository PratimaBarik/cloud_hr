package com.app.employeePortal.Opportunity.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.app.employeePortal.investor.entity.Investor;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.app.employeePortal.Opportunity.entity.OpportunityContactLink;
import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
import com.app.employeePortal.Opportunity.entity.OpportunityDocumentLink;
import com.app.employeePortal.Opportunity.entity.OpportunityForecastLink;
import com.app.employeePortal.Opportunity.entity.OpportunityIncludedLink;
import com.app.employeePortal.Opportunity.entity.OpportunityInfo;
import com.app.employeePortal.Opportunity.entity.OpportunityNotesLink;
import com.app.employeePortal.Opportunity.entity.OpportunityOrderLink;
import com.app.employeePortal.Opportunity.entity.OpportunityProductLink;
import com.app.employeePortal.Opportunity.entity.OpportunitySalesUserLink;
import com.app.employeePortal.Opportunity.entity.OpportunitySkillLink;
import com.app.employeePortal.Opportunity.entity.Order;
import com.app.employeePortal.Opportunity.entity.OrderDetails;
import com.app.employeePortal.Opportunity.mapper.ConversionValueMapper;
import com.app.employeePortal.Opportunity.mapper.FieldDetailsDTO;
import com.app.employeePortal.Opportunity.mapper.OpportunityDropdownMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityForecastLinkMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityOrderMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityOrderViewMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityProductMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityReportMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunitySkillLinkMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.Opportunity.mapper.RecruiterMapper;
import com.app.employeePortal.Opportunity.repository.OpportunityContactLinkRepository;
import com.app.employeePortal.Opportunity.repository.OpportunityDetailsRepository;
import com.app.employeePortal.Opportunity.repository.OpportunityDocumentLinkRepository;
import com.app.employeePortal.Opportunity.repository.OpportunityForecastLinkRepository;
import com.app.employeePortal.Opportunity.repository.OpportunityIncludedRepository;
import com.app.employeePortal.Opportunity.repository.OpportunityInfoRepository;
import com.app.employeePortal.Opportunity.repository.OpportunityNotesLinkRepository;
import com.app.employeePortal.Opportunity.repository.OpportunityOrderLinkRepository;
import com.app.employeePortal.Opportunity.repository.OpportunityProductRepository;
import com.app.employeePortal.Opportunity.repository.OpportunitySalesUserRepository;
import com.app.employeePortal.Opportunity.repository.OpportunitySkillLinkRepository;
import com.app.employeePortal.Opportunity.repository.OrderDetailsRepository;
import com.app.employeePortal.Opportunity.repository.OrderRepository;
import com.app.employeePortal.Team.entity.Team;
import com.app.employeePortal.Team.entity.TeamMemberLink;
import com.app.employeePortal.Team.repository.TeamMemberLinkRepo;
import com.app.employeePortal.Team.repository.TeamRepository;
import com.app.employeePortal.address.entity.AddressDetails;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.candidate.entity.CandidateDetails;
import com.app.employeePortal.candidate.entity.DefinationDetails;
import com.app.employeePortal.candidate.mapper.CandidateMapper;
import com.app.employeePortal.candidate.repository.CandidateDetailsRepository;
import com.app.employeePortal.candidate.repository.DefinationRepository;
import com.app.employeePortal.category.entity.CurrencyConversion;
import com.app.employeePortal.category.repository.CurrencyConversionRepository;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.entity.CuOppConversionValue;
import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.entity.CustomerAddressLink;
import com.app.employeePortal.customer.entity.CustomerRecruitUpdate;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.customer.repository.CuOppConversionValueRepo;
import com.app.employeePortal.customer.repository.CustomerAddressLinkRepository;
import com.app.employeePortal.customer.repository.CustomerRecruitUpdateRepository;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.customer.service.CustomerService;
import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.document.repository.DocumentDetailsRepository;
import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.EmployeeEmailLink;
import com.app.employeePortal.employee.entity.Notes;
import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.EmployeeEmailLinkRepository;
import com.app.employeePortal.employee.repository.EmployeeInfoRepository;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.NotesRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.investor.entity.InvestorOppIncludedLink;
import com.app.employeePortal.investor.repository.InvestorOppIncludedLinkRepo;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.opportunityWorkflow.entity.OpportunityStages;
import com.app.employeePortal.opportunityWorkflow.entity.OpportunityWorkflowDetails;
import com.app.employeePortal.opportunityWorkflow.repository.OpportunityStagesRepository;
import com.app.employeePortal.opportunityWorkflow.repository.OpportunityWorkflowDetailsRepository;
import com.app.employeePortal.opportunityWorkflow.service.OpportunityWorkflowService;
import com.app.employeePortal.organization.entity.OrganizationDetails;
import com.app.employeePortal.organization.mapper.OrganizationValueMapper;
import com.app.employeePortal.organization.repository.OrganizationRepository;
import com.app.employeePortal.permission.entity.Permission;
import com.app.employeePortal.permission.repository.PermissionRepository;
import com.app.employeePortal.product.entity.ProductAttributeDetails;
import com.app.employeePortal.product.entity.ProductCategoryDetails;
import com.app.employeePortal.product.entity.ProductSubAtrributeDetails;
import com.app.employeePortal.product.entity.ProductSubCategoryDetails;
import com.app.employeePortal.product.repository.ProductAttributeDetailsRepository;
import com.app.employeePortal.product.repository.ProductCategoryDetailsRepository;
import com.app.employeePortal.product.repository.ProductSubAtrributeDetailsRepository;
import com.app.employeePortal.product.repository.ProductSubCategoryDetailsRepository;
import com.app.employeePortal.recruitment.entity.OpportunityRecruitDetails;
import com.app.employeePortal.recruitment.entity.OpportunityRecruiterLink;
import com.app.employeePortal.recruitment.entity.RecruitProfileLinkDetails;
import com.app.employeePortal.recruitment.entity.RecruitmentCandidateLink;
import com.app.employeePortal.recruitment.entity.RecruitmentCloseRule;
import com.app.employeePortal.recruitment.entity.RecruitmentSkillsetLink;
import com.app.employeePortal.recruitment.mapper.RecruitmentOpportunityMapper;
import com.app.employeePortal.recruitment.repository.OpportunityRecruiterLinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentCandidateLinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentCloseRuleRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentOpportunityDetailsRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProfileDetailsRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentSkillsetLinkRepository;
import com.app.employeePortal.recruitment.service.RecruitmentService;
import com.app.employeePortal.registration.entity.Currency;
import com.app.employeePortal.registration.repository.CurrencyRepository;
import com.app.employeePortal.support.entity.Product;
import com.app.employeePortal.support.repository.ProductRepository;
import com.app.employeePortal.task.entity.TaskIncludedLink;
import com.app.employeePortal.task.repository.TaskIncludedLinkRepository;
import com.app.employeePortal.util.Utility;

import freemarker.template.TemplateException;

@Service
@Transactional
public class OpportunityServiceImpl implements OpportunityService {

	private static final String opportunityId = null;

	@Autowired
	OpportunityDetailsRepository opportunityDetailsRepository;
	@Autowired
	OpportunityContactLinkRepository opportunityContactLinkRepository;
	@Autowired
	OpportunityInfoRepository opportunityInfoRepository;
	@Autowired
	OpportunityNotesLinkRepository opportunityNotesLinkRepository;
	@Autowired
	OpportunitySkillLinkRepository opportunitySkillLinkRepository;
	@Autowired
	NotesRepository notesRepository;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	DocumentDetailsRepository documentDetailsRepository;
	@Autowired
	OpportunityDocumentLinkRepository opportunityDocumentLinkRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EmployeeInfoRepository employeeInfoRepository;
	@Autowired
	CustomerService customerService;
	@Autowired
	RecruitmentOpportunityDetailsRepository recruitmentOpportunityDetailsRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	OpportunityRecruiterLinkRepository opportunityRecruiterLinkRepository;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	OpportunitySalesUserRepository opportunitySalesUserRepository;
	@Autowired
	PermissionRepository permissionRepository;
	@Autowired
	RecruitmentProfileDetailsRepository recruitProfileDetailsRepository;
	@Autowired
	RecruitmentCandidateLinkRepository candidateLinkRepository;
	@Autowired
	CandidateDetailsRepository candidateDetailsRepository;
	@Autowired
	RecruitmentSkillsetLinkRepository recruitmentSkillsetLinkRepository;
	@Autowired
	CustomerRecruitUpdateRepository customerRecruitUpdateRepository;
	@Autowired
	DefinationRepository definationRepository;
	@Autowired
	OpportunityForecastLinkRepository opportunityForecastLinkRepository;
	@Autowired
	OpportunityWorkflowDetailsRepository opportunityWorkflowDetailsRepository;
	@Autowired
	CuOppConversionValueRepo cuOppConversionValueRepo;

	private String[] opportunity_headings = { "Opportunity Id", "Name", "Proposal Amount", "Currency", "Customer Name",
			"Workflow" };
	@Autowired
	ContactService contactService;
	@Autowired
	OpportunityService OpportunityService;
	@Autowired
	RecruitmentService recruitmentService;
	@Autowired
	OpportunityStagesRepository opportunityStagesRepository;
	@Autowired
	RecruitmentCloseRuleRepository recruitmentCloseRuleRepository;
	@Autowired
	OpportunityWorkflowService opportunityWorkflowService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	TeamMemberLinkRepo teamMemberLinkRepo;
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	OpportunityOrderLinkRepository opportunityOrderRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderDetailsRepository orderDetailsRepository;
	@Autowired
	OpportunityIncludedRepository opportunityIncludedRepository;
	@Autowired
	InvestorOppIncludedLinkRepo investorOppIncludedRepository;
	@Autowired
	TaskIncludedLinkRepository taskIncludedRepository;
	@Autowired
	CurrencyConversionRepository currencyConversionRepository;
	@Autowired
	OrganizationRepository organizationRepository;
	@Autowired
	CurrencyRepository currencyRepository;
	@Autowired
	NotificationService notificationService;
	@Autowired
	ExcelService excelService;
	@Autowired
	CustomerAddressLinkRepository customerAddressLinkRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	DocumentService documentService;
	@Autowired
	EmployeeEmailLinkRepository employeeEmailLinkRepository;
	@Autowired OpportunityProductRepository opportunityProductRepository;
	@Autowired ProductRepository productRepository;
	@Autowired
	ProductCategoryDetailsRepository productCategoryDetailsRepository;
	@Autowired
	ProductSubCategoryDetailsRepository productSubCategoryDetailsRepository;
	@Autowired ProductAttributeDetailsRepository productAttributeDetailsRepository;
	@Autowired ProductSubAtrributeDetailsRepository productSubAttributeDetailsRepository;
	
	@Override
	public OpportunityViewMapper saveOpportunity(OpportunityMapper opportunityMapper) {

		String opportunityId = null;

		OpportunityViewMapper resultMapper = null;
		OpportunityInfo opportunityInfo = new OpportunityInfo();

		opportunityInfo.setCreationDate(new Date());

		OpportunityInfo opportunityy = opportunityInfoRepository.save(opportunityInfo);
		opportunityId = opportunityy.getId();
		System.out.println("oppId;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;" + opportunityId);
		OpportunityDetails opportunityDetails = new OpportunityDetails();

		setPropertyOnInput(opportunityMapper, opportunityDetails, opportunityId);
		opportunityDetailsRepository.save(opportunityDetails);

		resultMapper = getOpportunityDetails(opportunityId);
		return resultMapper;
	}

	private void setPropertyOnInput(OpportunityMapper opportunityMapper, OpportunityDetails opportunity,
			String opportunityId) {

		opportunity.setOpportunityName(opportunityMapper.getOpportunityName());
		opportunity.setProposalAmount(opportunityMapper.getProposalAmount());
		opportunity.setCustomerId(opportunityMapper.getCustomerId());
		// opportunity.setCustomerName(opportunityMapper.getCustomerId());
		opportunity.setUserId(opportunityMapper.getUserId());
		opportunity.setContactId(opportunityMapper.getContactId());
		opportunity.setOrgId(opportunityMapper.getOrgId());
		opportunity.setOppInnitiative(opportunityMapper.getOppInnitiative());
		try {
			opportunity
					.setStartDate(Utility.removeTime(Utility.getDateFromISOString(opportunityMapper.getStartDate())));
			opportunity.setEndDate(Utility.removeTime(Utility.getDateFromISOString(opportunityMapper.getEndDate())));
		} catch (Exception e) {
			e.printStackTrace();
		}

		opportunity.setCurrency(opportunityMapper.getCurrency());
		opportunity.setDescription(opportunityMapper.getDescription());
		opportunity.setCreationDate(new Date());
		opportunity.setLiveInd(true);
		opportunity.setReinstateInd(true);
		opportunity.setWonInd(false);
		opportunity.setCloseInd(false);
		opportunity.setLostInd(false);
		opportunity.setOpportunityId(opportunityId);
		opportunity.setOppStage(opportunityMapper.getOppStage());
		opportunity.setOppWorkflow(opportunityMapper.getOppWorkflow());
		
		int countNumber = opportunityDetailsRepository.countByCreationDate(Utility.removeTime(new Date()));

		String counts = "";
		if (countNumber < 10) {
			counts += "000" + countNumber;
		} else if (countNumber >= 10 && countNumber < 100) {
			counts += "00" + countNumber;
		} else if (countNumber >= 100 && countNumber < 1000) {
			counts += "0" + countNumber;
		} else {
			counts += "" + countNumber;
		}

		Calendar calendar = Calendar.getInstance();
		String year = Integer.toString(calendar.get(Calendar.YEAR));
		int month = calendar.get(Calendar.MONTH) + 1;
		String month1 = "";
		if (month < 10) {
			month1 += "0" + month;
		} else {
			month1 += "" + month;
		}

		int date = calendar.get(Calendar.DATE);

		String date1 = "";
		if (date < 10) {
			date1 += "0" + date;
		} else {
			date1 += "" + date;
		}
		opportunity.setNewOppId(counts + date1 + month1 + year);
		
		/*
		 * if(null !=opportunityMapper.getRecruiterId() &&
		 * !opportunityMapper.getRecruiterId().isEmpty()) { for(String recruiterId :
		 * opportunityMapper.getRecruiterId()) { OpportunityRecruiterLink
		 * opportunityRecruiterLink = new OpportunityRecruiterLink();
		 * opportunityRecruiterLink.setOpportunity_id(opportunityId);
		 * opportunityRecruiterLink.setRecruiter_id(recruiterId);
		 * opportunityRecruiterLink.setCreation_date(new Date());
		 * opportunityRecruiterLink.setLive_ind(true);
		 * 
		 * opportunityRecruiterLinkRepository.save(opportunityRecruiterLink); } }
		 */
		/* insert to Opportunity SalesUserLink */
		/*
		 * List<String> salesIds = new ArrayList<>();
		 * salesIds.add(opportunityMapper.getUserId());
		 * 
		 * if (null != opportunityMapper.getSalesUserIds() &&
		 * !opportunityMapper.getSalesUserIds().isEmpty()) { if
		 * (!StringUtils.isEmpty(opportunityMapper.getSalesUserIds()) &&
		 * !opportunityMapper.getUserId().equals(opportunityMapper.getSalesUserIds())) {
		 * salesIds.add(opportunityMapper.getSalesUserIds()); } for (String salesUserId
		 * : salesIds) {
		 * 
		 * OpportunitySalesUserLink opportunitySalesLink = new
		 * OpportunitySalesUserLink();
		 * 
		 * opportunitySalesLink.setOpportunity_id(opportunityId);
		 * opportunitySalesLink.setEmployee_id(salesUserId);
		 * opportunitySalesLink.setCreationDate(new Date());
		 * opportunitySalesLink.setLive_ind(true);
		 * opportunitySalesLink.setOrgId(opportunityMapper.getOrgId());
		 * opportunitySalesUserRepository.save(opportunitySalesLink); } }
		 */
		opportunity.setAssignedTo(opportunityMapper.getSalesUserIds());
		opportunity.setAssignedBy(opportunityMapper.getUserId());

		/* insert to opportunity-included-link */
		if (opportunityMapper.getIncluded() != null && !opportunityMapper.getIncluded().isEmpty()) {
			for (String id : opportunityMapper.getIncluded()) {
				OpportunityIncludedLink opportunityIncludedLink = new OpportunityIncludedLink();
				opportunityIncludedLink.setOpportunityId(opportunityId);
				opportunityIncludedLink.setEmployeeId(id);
				opportunityIncludedLink.setCreationDate(new Date());
				opportunityIncludedLink.setLiveInd(true);
				opportunityIncludedLink.setOrgId(opportunityMapper.getOrgId());

				opportunityIncludedRepository.save(opportunityIncludedLink);
			}
		}

		/* insert to opportunity-contact-link */
		if (opportunityMapper.getContactId() != null && opportunityMapper.getContactId().trim().length() > 0) {
			OpportunityContactLink opportunityContactLink = new OpportunityContactLink();
			opportunityContactLink.setContactId(opportunityMapper.getContactId());
			opportunityContactLink.setOpportunityId(opportunityId);
			opportunityContactLink.setCreationDate(new Date());

			opportunityContactLinkRepository.save(opportunityContactLink);
		}

		String id = null;

		if (null != opportunityMapper.getExcelId()) {
			FieldDetailsDTO dto = new FieldDetailsDTO();
			dto.setCustomerId(opportunityMapper.getCustomerId());
			dto.setOpportunityId(opportunityId);
			dto.setExcelId(opportunityMapper.getExcelId());
			dto.setXlUpdateInd(opportunityMapper.isXlUpdateInd());
			dto.setUserId(opportunityMapper.getUserId());

			try {
				id = excelService.insertPhoneDetails(dto);
				System.out.println("excelId" + id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (null != id) {
				opportunity.setExcelId(id);
			}
		}

		/* insert to opportunity-Skill-link */
//			if(opportunityMapper.getOpportunitySkill().size() > 0)  {
//			    for(OpportunitySkillLinkMapper opportunitySkillLinkMapper : opportunityMapper.getOpportunitySkill()) {
//			    	
//			    OpportunitySkillLink opportunitySkillLink =new OpportunitySkillLink();
//			    opportunitySkillLink.setOpportunityId(opportunityId);
//			    opportunitySkillLink.setNoOfPosition(opportunitySkillLinkMapper.getNoOfPosition());
//			    opportunitySkillLink.setSkill(opportunitySkillLinkMapper.getSkill());
//			    opportunitySkillLink.setOppInnitiative(opportunitySkillLinkMapper.getOppInnitiative());
//                opportunitySkillLinkRepository.save(opportunitySkillLink);
//			    }
//			 }

		/* insert to Notification Table */
//		Notificationparam noti = new Notificationparam();
//		String customerName = customerRepository.getCustomerByIdAndLiveInd(opportunityMapper.getCustomerId()).getName();
//		noti.setAdminMsg("You have created an opportunity " + opportunityMapper.getOpportunityName() + "with "
//				+ customerName + " " + opportunity.getCreationDate());
//		noti.setUserId(opportunityMapper.getUserId());
//		notificationService.createNotificationForDynamicUsers(noti);

		/* insert to Notes Table */
		Notes notes = new Notes();
		notes.setCreation_date(new Date());
		notes.setNotes(opportunityMapper.getDescription());
		notes.setLiveInd(true);
		Notes note = notesRepository.save(notes);

		String notesId = note.getNotes_id();
		/* insert to CallNoteLink table */

		OpportunityNotesLink callNote = new OpportunityNotesLink();
		callNote.setOpportunity_id(opportunityId);
		callNote.setNotesId(notesId);
		callNote.setCreation_date(new Date());
		opportunityNotesLinkRepository.save(callNote);

		String userCurrency = null;
		String orgCurrency = null;
		double probability = 0;
		EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
		if (null != emp1) {
			Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
			if (null != currency) {
				userCurrency = currency.getCurrencyName();
			}
		}
		OrganizationDetails organizationDetails = organizationRepository
				.getOrganizationDetailsById(opportunityMapper.getOrgId());
		if (null != organizationDetails.getTrade_currency()) {
			orgCurrency = organizationDetails.getTrade_currency();
		}
		OpportunityStages oppStages = opportunityStagesRepository
				.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
		if (null != oppStages) {
			probability = oppStages.getProbability();
		}

		ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
				opportunityMapper.getProposalAmount(), userCurrency, orgCurrency, opportunityMapper.getCurrency(),
				probability);
		if (null != mapper) {

			CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
					.findByCustomerId(opportunityMapper.getCustomerId());
			if (null != cuOppConversionValue) {
				double userpValue = (cuOppConversionValue.getUserConversionValue() + mapper.getUserConversionPValue());
				cuOppConversionValue.setUserConversionValue(userpValue);
				double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
						+ mapper.getUserConversionWValue());
				cuOppConversionValue.setUserConversionWeightedValue(userwValue);
				double orgpValue = (cuOppConversionValue.getOrgConversionValue() + mapper.getOrgConversionPValue());
				cuOppConversionValue.setOrgConversionValue(orgpValue);
				double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
						+ mapper.getOrgConversionWValue());
				cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
				cuOppConversionValueRepo.save(cuOppConversionValue);
			} else {
				CuOppConversionValue cuOppConversionValue1 = new CuOppConversionValue();
				cuOppConversionValue1.setCreationDate(new Date());
				cuOppConversionValue1.setCustomerId(opportunityMapper.getCustomerId());
				cuOppConversionValue1.setLiveInd(true);
				cuOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
				cuOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
				cuOppConversionValue1.setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
				cuOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
				cuOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
				cuOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
				cuOppConversionValue1.setUserConversionWeightedValue(mapper.getUserConversionWValue());
				cuOppConversionValue1.setUserId(opportunityMapper.getUserId());

				cuOppConversionValueRepo.save(cuOppConversionValue1);

			}
		}

	}

	public OpportunityViewMapper getOpportunityDetailsByOppId(String opportunityId) {

		OpportunityViewMapper opportunityViewMapper = new OpportunityViewMapper();
		if (null != opportunityId && opportunityId.trim().length() > 0) {

			OpportunityDetails opportunity = opportunityDetailsRepository
					.getOpenOpportunityDetailsByOpportunityId(opportunityId);

			System.out.println("Opportunity@@@@@@@@@&" + opportunity);
			if (null != opportunity) {
				opportunityViewMapper = getOpportunityRelatedDetails(opportunity);
			}
		}
		return opportunityViewMapper;

	}

	@Override
	public List<OpportunityViewMapper> getOpportunityDetailsListPageWiseByUserId(String userId, int pageNo,
			int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "creationDate"));
		List<OpportunityViewMapper> opportunities = new ArrayList<>();

		Page<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getOpportunityListByUserIdAndLiveIndAndReinstateIndAndCloseIndAndLostIndPageWise(userId, paging);
//		Page<OpportunitySalesUserLink> opportunitySalesLink = opportunitySalesUserRepository
//				.getSalesUserLinkByUserIdWithPagination(userId, paging);
		if (null != opportunityList && !opportunityList.isEmpty()) {
			opportunityList.stream().filter(li -> (li != null)).map(li -> {
				OpportunityDetails opportunity = opportunityDetailsRepository
						.getOpenOpportunityDetailsByOpportunityId(li.getOpportunityId());
				if (null != opportunity) {
					OpportunityViewMapper mapper = getOpportunityRelatedDetails(opportunity);
					if (null != mapper) {
						mapper.setPageCount(opportunityList.getTotalPages());
						mapper.setDataCount(opportunityList.getSize());
						mapper.setListCount(opportunityList.getTotalElements());
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
	public List<OpportunityViewMapper> getOpportunityDetailsListByContactId(String contactId) {
		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getOpportunityListByContactIdAndLiveInd(contactId, true);

		List<OpportunityViewMapper> mapperList = new ArrayList<OpportunityViewMapper>();
		opportunityList.stream().map(opportunityDetails -> {

			OpportunityViewMapper opportunityMapper = getOpportunityRelatedDetails(opportunityDetails);
			opportunityMapper.setContactId(contactId);

			mapperList.add(opportunityMapper);
			return mapperList;
		}).collect(Collectors.toList());

		return mapperList;
	}

	@Override
	public OpportunityViewMapper getOpportunityRelatedDetails(OpportunityDetails opportunityDetails) {
		int openRecruitment = 0;
		int openPosition = 0;
		OpportunityViewMapper oppertunityMapper = new OpportunityViewMapper();

		if (null != opportunityDetails.getOpportunityId()) {

			oppertunityMapper.setOpportunityId(opportunityDetails.getOpportunityId());
			oppertunityMapper.setOpportunityName(opportunityDetails.getOpportunityName());
			oppertunityMapper.setNewOppId(opportunityDetails.getNewOppId());
			oppertunityMapper.setProposalAmount(opportunityDetails.getProposalAmount());
			oppertunityMapper.setCurrency(opportunityDetails.getCurrency());
			oppertunityMapper.setUserId(opportunityDetails.getUserId());
			oppertunityMapper.setOrgId(opportunityDetails.getOrgId());
			oppertunityMapper.setStartDate(Utility.getISOFromDate(opportunityDetails.getStartDate()));
			oppertunityMapper.setEndDate(Utility.getISOFromDate(opportunityDetails.getEndDate()));
			oppertunityMapper.setDescription(opportunityDetails.getDescription());
			oppertunityMapper.setOppInnitiative(opportunityDetails.getOppInnitiative());
			oppertunityMapper.setWonInd(opportunityDetails.isWonInd());
			oppertunityMapper.setLostInd(opportunityDetails.isLostInd());
			oppertunityMapper.setCloseInd(opportunityDetails.isCloseInd());
			oppertunityMapper.setAssignedBy(employeeService.getEmployeeFullName(opportunityDetails.getAssignedBy()));
			if (null != opportunityDetails.getCreationDate()) {

				oppertunityMapper.setCreationDate(Utility.getISOFromDate(opportunityDetails.getCreationDate()));

			}

			List<OpportunityRecruiterLink> list = opportunityRecruiterLinkRepository
					.getRecruiterLinkByOppId(opportunityDetails.getOpportunityId());
			if (null != list && !list.isEmpty()) {
				List<RecruiterMapper> recruiterList = new ArrayList<RecruiterMapper>();
				for (OpportunityRecruiterLink opportunityRecruiterLink : list) {

					RecruiterMapper recriuterMapper = new RecruiterMapper();
					EmployeeDetails employeeDetails = employeeRepository
							.getEmployeeDetailsByEmployeeId(opportunityRecruiterLink.getRecruiter_id());
					if (null != employeeDetails) {
						String middleName = " ";
						String lastName = "";

						if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

							lastName = employeeDetails.getLastName();
						}
						if (!StringUtils.isEmpty(employeeDetails.getMiddleName())) {
							middleName = employeeDetails.getMiddleName();

						}
						recriuterMapper.setFullName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
						recriuterMapper.setEmployeeId(employeeDetails.getEmployeeId());
						recriuterMapper.setImageId(employeeDetails.getImageId());
					}
					recruiterList.add(recriuterMapper);
				}
				oppertunityMapper.setRecruiterDetails(recruiterList);

			}
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeDetailsByEmployeeId(opportunityDetails.getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}
				if (!StringUtils.isEmpty(employeeDetails.getMiddleName())) {
					middleName = employeeDetails.getMiddleName();

				}
				oppertunityMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				oppertunityMapper.setOwnerImageId(employeeDetails.getImageId());
			}

			System.out.println("till employeee detail set");
			if (opportunityDetails.getCustomerId() != null && opportunityDetails.getCustomerId().trim().length() > 0) {
				Customer customer = customerRepository.getCustomerByIdAndLiveInd(opportunityDetails.getCustomerId());
				if (null != customer) {
					oppertunityMapper.setCustomerId(customer.getCustomerId());
					oppertunityMapper.setCustomer(customer.getName());
				}
			} else {
				oppertunityMapper.setCustomerId("");

			}
			if (opportunityDetails.getContactId() != null && opportunityDetails.getContactId().trim().length() > 0) {
				ContactDetails contact = contactRepository.getContactDetailsById(opportunityDetails.getContactId());
				if (null != contact) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(contact.getLast_name())) {

						lastName = contact.getLast_name();
					}
					if (!StringUtils.isEmpty(contact.getMiddle_name())) {
						middleName = contact.getMiddle_name();

					}
					oppertunityMapper.setContactId(contact.getContactId());
					oppertunityMapper.setContactName(contact.getFirst_name() + " " + middleName + " " + lastName);

				}
			}

			if (opportunityDetails.getOpportunityId() != null
					&& opportunityDetails.getOpportunityId().trim().length() > 0) {
				List<String> includedIds = opportunityIncludedRepository
						.findByOpportunityId(opportunityDetails.getOpportunityId()).stream()
						.map(OpportunityIncludedLink::getEmployeeId).collect(Collectors.toList());
				List<EmployeeShortMapper> included = new ArrayList<>();
				if (null != includedIds && !includedIds.isEmpty()) {
					for (String includedId : includedIds) {
						EmployeeShortMapper employeeMapper = employeeService
								.getEmployeeFullNameAndEmployeeId(includedId);
						included.add(employeeMapper);
					}
					oppertunityMapper.setIncluded(included);
				}

//				List<String> opp = opportunitySalesUserRepository
//						.getSalesUsersByOppId(opportunityDetails.getOpportunityId()).stream()
//						.map(OpportunitySalesUserLink::getEmployee_id).collect(Collectors.toList());
//
//				System.out.println("opp.size()===================" + opp.size());
//				if (null != opp && !opp.isEmpty()) {
//					opp.remove(opportunityDetails.getUserId());
//					if (opp.size() > 0) {
//						for (String empId : opp) {
////							if (opportunitySalesUserLink.getEmployee_id().equals(opportunityDetails.getUserId())) {
////								break;
////							}
//
//							EmployeeDetails employeeDetailss = employeeRepository.getEmployeeDetailsByEmployeeId(empId);
//							if (null != employeeDetailss) {
//								oppertunityMapper.setSalesUserIds(empId);
//								String middleName = " ";
//								String lastName = "";
//
//								if (!StringUtils.isEmpty(employeeDetailss.getLastName())) {
//
//									lastName = employeeDetailss.getLastName();
//								}
//								if (!StringUtils.isEmpty(employeeDetailss.getMiddleName())) {
//									middleName = employeeDetailss.getMiddleName();
//
//								}
//								oppertunityMapper.setAssignedTo(
//										employeeDetailss.getFirstName() + " " + middleName + " " + lastName);
//							}
//						}
//					} else {
//						EmployeeDetails employeeDetailss = employeeRepository
//								.getEmployeeDetailsByEmployeeId(opportunityDetails.getUserId());
//						if (null != employeeDetailss) {
//							oppertunityMapper.setSalesUserIds(opportunityDetails.getUserId());
//							String middleName = " ";
//							String lastName = "";
//
//							if (!StringUtils.isEmpty(employeeDetailss.getLastName())) {
//
//								lastName = employeeDetailss.getLastName();
//							}
//							if (!StringUtils.isEmpty(employeeDetailss.getMiddleName())) {
//								middleName = employeeDetailss.getMiddleName();
//
//							}
//							oppertunityMapper
//									.setAssignedTo(employeeDetailss.getFirstName() + " " + middleName + " " + lastName);
//						}
//					}
//				}

				EmployeeDetails employeeDetail = employeeRepository
						.getEmployeeDetailsByEmployeeId(opportunityDetails.getAssignedTo());
				if (null != employeeDetail) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(employeeDetail.getLastName())) {

						lastName = employeeDetail.getLastName();
					}

					if (employeeDetail.getMiddleName() != null && employeeDetail.getMiddleName().length() > 0) {

						middleName = employeeDetail.getMiddleName();
						oppertunityMapper
								.setAssignedTo(employeeDetail.getFirstName() + " " + middleName + " " + lastName);

					} else {

						oppertunityMapper.setAssignedTo(employeeDetail.getFirstName() + " " + lastName);

					}
				}
			}

			OpportunityWorkflowDetails workflowDetails = opportunityWorkflowDetailsRepository
					.getOpportunityWorkflowDetailsByOpportunityWorkflowDetailsId(opportunityDetails.getOppWorkflow());
			if (null != workflowDetails) {
				oppertunityMapper.setOppWorkflow(workflowDetails.getWorkflowName());
			}

			OpportunityStages oppStages = opportunityStagesRepository
					.getOpportunityStagesByOpportunityStagesId(opportunityDetails.getOppStage());
			if (null != oppStages) {
				oppertunityMapper.setOppStage(oppStages.getStageName());
				oppertunityMapper.setProbability(oppStages.getProbability());
				oppertunityMapper.setOpportunityStagesId(oppStages.getOpportunityStagesId());
			}

			oppertunityMapper.setStageList(
					opportunityWorkflowService.getStagesByOppworkFlowId(opportunityDetails.getOppWorkflow()));

			List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
					.getOpenRecriutmentsByOpportunityIdAndCloseInd(opportunityDetails.getOpportunityId());
			if (null != recruitList && !recruitList.isEmpty()) {
				for (OpportunityRecruitDetails opportunityRecruitDetails : recruitList) {

					System.out.println("recruitmentId=" + opportunityRecruitDetails.getRecruitment_id());
					List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
							.getProfileDetailByRecruitmentIdAndonBoardInd(
									opportunityRecruitDetails.getRecruitment_id());
					if (opportunityRecruitDetails.isCloseInd() == false) {
						System.out.println("start::::::::::::2");
						int profileSize = profileList.size();
						int positionSize = (int) opportunityRecruitDetails.getNumber();
						System.out.println("profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
						if (profileSize < positionSize) {
							openRecruitment++;
							openPosition = positionSize - profileSize;
							System.out.println("openPosition=============inner===" + openPosition);
						}
						if (recruitList.size() > 1) {
							openPosition += openPosition;
							System.out.println("openPosition=============outer===" + openPosition);
						}
					}
				}
				System.out.println("openRecruitment=============outer===" + openRecruitment);

			}
			oppertunityMapper.setOpenRecruitment(openRecruitment);
			oppertunityMapper.setOpenPosition(openPosition);

//			List<OpportunitySkillLink> opportunitySkillLink = opportunitySkillLinkRepository
//	                .getSkillListByOpportunityId(opportunityDetails.getOpportunityId());
//	        List<OpportunitySkillLinkMapper> skillList = new ArrayList<OpportunitySkillLinkMapper>();
//	        
//	        if(null!=opportunitySkillLink && !opportunitySkillLink.isEmpty()) {
//	          for(OpportunitySkillLink opportunityDetailss:opportunitySkillLink)  {
//	             // OpportunitySkillLink opportunitySkillLink1=opportunitySkillLinkRepository. getOpportunitySkillLinkId( );
//	              OpportunitySkillLinkMapper opportunitySkillLinkMapper =new OpportunitySkillLinkMapper();
//	              opportunitySkillLinkMapper.setNoOfPosition(opportunityDetailss.getNoOfPosition());
//	              opportunitySkillLinkMapper.setOppInnitiative(opportunityDetailss.getOppInnitiative());
//	              opportunitySkillLinkMapper.setSkill(opportunityDetailss.getSkill());
//	              opportunitySkillLinkMapper.setOpportunityId(opportunityDetailss.getOpportunityId());
//	              opportunitySkillLinkMapper.setOpportunitySkillLinkId(opportunityDetailss.getOpportunitySkillLinkId());
//	              skillList.add(opportunitySkillLinkMapper);
//	              
//	          }  
//	          }
//	        oppertunityMapper.setOpportunitySkill(skillList);
		}
		return oppertunityMapper;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityListByCustomerId(String customerId) {

		// List<OpportunityDetails> opportunityList =
		// opportunityDetailsRepository.getOpportunityListByCustomerId(customerId);

		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getOpportunityListByCustomerIdAndLiveInd(customerId);

		List<OpportunityViewMapper> mapperList = new ArrayList<OpportunityViewMapper>();
		opportunityList.stream().map(opportunityDetails -> {

			OpportunityViewMapper opportunityMapper = getOpportunityRelatedDetails(opportunityDetails);
			mapperList.add(opportunityMapper);
			return mapperList;
		}).collect(Collectors.toList());

		return mapperList;
	}

	@Override
	public OpportunityViewMapper updateOpportunityDetails(String opportunityId, OpportunityMapper opportunityMapper) {

		OpportunityDetails opportunity = opportunityDetailsRepository
				.getOpportunityDetailsByOpportunityId(opportunityId);
		if (null != opportunity) {
			opportunity.setLiveInd(false);
			opportunity.setReinstateInd(false);
			opportunityDetailsRepository.save(opportunity);
		}
		OpportunityDetails opportunityDetails = new OpportunityDetails();
		opportunityDetails.setOpportunityId(opportunityId);
		if (null != opportunityMapper.getOpportunityName()) {
			opportunityDetails.setOpportunityName(opportunityMapper.getOpportunityName());
		} else {
			opportunityDetails.setOpportunityName(opportunity.getOpportunityName());
		}
		if (null != opportunityMapper.getProposalAmount()) {
			opportunityDetails.setProposalAmount(opportunityMapper.getProposalAmount());
		} else {
			opportunityDetails.setProposalAmount(opportunity.getProposalAmount());
		}
		if (null != opportunityMapper.getCurrency()) {
			opportunityDetails.setCurrency(opportunityMapper.getCurrency());
		} else {
			opportunityDetails.setCurrency(opportunity.getCurrency());
		}

		if (null != opportunityMapper.getCustomerId()) {
			opportunityDetails.setCustomerId(opportunityMapper.getCustomerId());
		} else {
			opportunityDetails.setCustomerId(opportunity.getCustomerId());
		}
		if (null != opportunityMapper.getOppInnitiative()) {
			opportunityDetails.setOppInnitiative(opportunityMapper.getOppInnitiative());
		} else {
			opportunityDetails.setOppInnitiative(opportunity.getOppInnitiative());
		}

		if (null != opportunityMapper.getContactId()) {
			opportunityDetails.setContactId(opportunityMapper.getContactId());
			if (!StringUtils.isEmpty(opportunity.getContactId())) {
				OpportunityContactLink opportunityContactLink = opportunityContactLinkRepository
						.findByContactIdAndOpportunityId(opportunity.getContactId(), opportunityId);
				if (null != opportunityContactLink) {
					opportunityContactLink.setContactId(opportunityMapper.getContactId());
					opportunityContactLinkRepository.save(opportunityContactLink);
				}
			} else {
				OpportunityContactLink newOpportunityContactLink = new OpportunityContactLink();
				newOpportunityContactLink.setContactId(opportunityMapper.getContactId());
				newOpportunityContactLink.setOpportunityId(opportunityId);
				newOpportunityContactLink.setCreationDate(new Date());
			}

		} else {
			opportunityDetails.setContactId(opportunity.getContactId());
		}

		if (null != opportunityMapper.getUserId()) {
			opportunityDetails.setUserId(opportunityMapper.getUserId());
		} else {
			opportunityDetails.setUserId(opportunity.getUserId());
		}

		if (null != opportunityMapper.getOrgId()) {
			opportunityDetails.setOrgId(opportunityMapper.getOrgId());
		} else {
			opportunityDetails.setOrgId(opportunity.getOrgId());
		}
		if (null != opportunityMapper.getDescription()) {
			List<OpportunityNotesLink> list = opportunityNotesLinkRepository.getNoteListByOpportunityId(opportunityId);
			if (null != list && !list.isEmpty()) {
				list.sort((m1, m2) -> m2.getCreation_date().compareTo(m1.getCreation_date()));
				Notes notes = notesRepository.findByNoteId(list.get(0).getNotesId());
				if (null != notes) {
					notes.setNotes(opportunityMapper.getDescription());
					notesRepository.save(notes);
				}
			}
		}
		if (null != opportunityMapper.getOppStage()) {
			opportunityDetails.setOppStage(opportunityMapper.getOppStage());
		} else {
			opportunityDetails.setOppStage(opportunity.getOppStage());
		}
		if (null != opportunityMapper.getOppWorkflow()) {
			opportunityDetails.setOppWorkflow(opportunityMapper.getOppWorkflow());
		} else {
			opportunityDetails.setOppWorkflow(opportunity.getOppWorkflow());
		}

		try {

			if (null != opportunityMapper.getStartDate()) {
				opportunityDetails.setStartDate(
						Utility.removeTime(Utility.getDateFromISOString(opportunityMapper.getStartDate())));
			} else {
				opportunityDetails.setStartDate(opportunity.getStartDate());
			}
			if (null != opportunityMapper.getEndDate()) {
				opportunityDetails
						.setEndDate(Utility.removeTime(Utility.getDateFromISOString(opportunityMapper.getEndDate())));
			} else {
				opportunityDetails.setEndDate(opportunity.getEndDate());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		opportunityDetails.setCreationDate(opportunity.getCreationDate());
		opportunityDetails.setLiveInd(true);
		opportunityDetails.setReinstateInd(true);
		opportunityDetails.setCreationDate(opportunity.getCreationDate());

		CustomerRecruitUpdate dbCustomerRecruitUpdate = customerRecruitUpdateRepository
				.findByCustomerId(opportunityMapper.getCustomerId());
		if (dbCustomerRecruitUpdate != null) {
			dbCustomerRecruitUpdate.setCustomerId(opportunityMapper.getCustomerId());
			dbCustomerRecruitUpdate.setContactId(opportunityMapper.getContactId());
			dbCustomerRecruitUpdate.setUpdatedDate(new Date());
			customerRecruitUpdateRepository.save(dbCustomerRecruitUpdate);
		} else {
			CustomerRecruitUpdate cusRecruitUpdate = new CustomerRecruitUpdate();
			cusRecruitUpdate.setCustomerId(opportunityMapper.getCustomerId());
			cusRecruitUpdate.setContactId(opportunityMapper.getContactId());
			cusRecruitUpdate.setUpdatedDate(new Date());
			customerRecruitUpdateRepository.save(cusRecruitUpdate);
		}

		if (null != opportunityMapper.getExcelId()) {
			String id = null;
			if (opportunityMapper.getExcelId().equalsIgnoreCase(opportunity.getExcelId())) {
				FieldDetailsDTO dto = new FieldDetailsDTO();
				dto.setCustomerId(opportunityMapper.getCustomerId());
				dto.setOpportunityId(opportunityId);
				dto.setExcelId(opportunityMapper.getExcelId());
				dto.setXlUpdateInd(opportunityMapper.isXlUpdateInd());
				dto.setUserId(opportunityMapper.getUserId());

				try {
					id = excelService.insertPhoneDetails(dto);
					System.out.println("excelId" + id);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (null != id) {
					opportunityDetails.setExcelId(id);
				}
			}
		}
		if (null != opportunityMapper.getSalesUserIds()) {
			opportunityDetails.setAssignedTo(opportunityMapper.getSalesUserIds());
		}

		if (null != opportunityMapper.getSalesUserIds()
				&& !opportunityDetails.getAssignedBy().equals(opportunityMapper.getSalesUserIds())) {
			opportunityDetails.setAssignedBy(opportunityMapper.getUserId());
		}
		OpportunityDetails updateOpportunity = opportunityDetailsRepository.save(opportunityDetails);

		// Edit AssignedTo
//		List<OpportunitySalesUserLink> opportunitySalesUserLink = opportunitySalesUserRepository
//				.getSalesUsersByOppId(opportunityId);
//		if (null != opportunitySalesUserLink && !opportunitySalesUserLink.isEmpty()) {
//			for (OpportunitySalesUserLink opportunitySalesUserLink2 : opportunitySalesUserLink) {
//				opportunitySalesUserRepository.delete(opportunitySalesUserLink2);
//			}
//		}
//		List<String> salesIds = new ArrayList<>();
//		salesIds.add(opportunityMapper.getUserId());
//		if (null != opportunityMapper.getSalesUserIds() && !opportunityMapper.getSalesUserIds().isEmpty()) {
//			if (!opportunityMapper.getSalesUserIds().equals(opportunityMapper.getUserId())) {
//				salesIds.add(opportunityMapper.getSalesUserIds());
//			}
//			for (String salesUserId : salesIds) {
//
//				OpportunitySalesUserLink opportunitySalesLink = new OpportunitySalesUserLink();
//
//				opportunitySalesLink.setOpportunity_id(opportunityId);
//				opportunitySalesLink.setEmployee_id(salesUserId);
//				opportunitySalesLink.setCreationDate(new Date());
//				opportunitySalesLink.setLive_ind(true);
//				opportunitySalesUserRepository.save(opportunitySalesLink);
//			}
//		}

		// Edit Included
		List<OpportunityIncludedLink> opportunityIncludedLink = opportunityIncludedRepository
				.findByOpportunityId(opportunityId);
		if (null != opportunityIncludedLink && !opportunityIncludedLink.isEmpty()) {
			for (OpportunityIncludedLink opportunityIncludedLink1 : opportunityIncludedLink) {
				opportunityIncludedRepository.delete(opportunityIncludedLink1);
			}
		}

		if (opportunityMapper.getIncluded() != null && !opportunityMapper.getContactId().isEmpty()) {
			for (String id : opportunityMapper.getIncluded()) {
				OpportunityIncludedLink opportunityIncludedLink2 = new OpportunityIncludedLink();
				opportunityIncludedLink2.setOpportunityId(opportunityId);
				opportunityIncludedLink2.setEmployeeId(id);
				opportunityIncludedLink2.setCreationDate(new Date());
				opportunityIncludedLink2.setLiveInd(true);
				opportunityIncludedLink2.setOrgId(opportunityMapper.getOrgId());

				opportunityIncludedRepository.save(opportunityIncludedLink2);
			}
		}

		if (opportunityMapper.getCustomerId().equalsIgnoreCase(opportunity.getCustomerId())) {
			if (opportunityMapper.getProposalAmount().equalsIgnoreCase(opportunity.getProposalAmount())) {
				if (opportunityMapper.getOppStage().equalsIgnoreCase(opportunity.getOppStage())) {
					if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunity.getCurrency())) {

					} else {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);
						ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunity.getCurrency(), probability);

						if (null != previous) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- previous.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- previous.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- previous.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- previous.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);

								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
					}
				} else {
					if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunity.getCurrency())) {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						double previousProbability1 = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						OpportunityStages oppStages1 = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunity.getOppStage());
						if (null != oppStages1) {
							previousProbability1 = oppStages1.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);
						ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), previousProbability1);

						if (null != previous) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- previous.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- previous.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- previous.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- previous.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
					} else {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						double previousProbability1 = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						OpportunityStages oppStages1 = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunity.getOppStage());
						if (null != oppStages1) {
							previousProbability1 = oppStages1.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);
						ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunity.getCurrency(), previousProbability1);

						if (null != previous) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- previous.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- previous.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- previous.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- previous.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
					}
				}
			} else {
				if (opportunityMapper.getOppStage().equalsIgnoreCase(opportunity.getOppStage())) {
					if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunity.getCurrency())) {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);
						ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
								opportunity.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);

						if (null != previous) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- previous.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- previous.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- previous.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- previous.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
					} else {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);
						ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
								opportunity.getProposalAmount(), userCurrency, orgCurrency, opportunity.getCurrency(),
								probability);

						if (null != previous) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- previous.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- previous.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- previous.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- previous.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
					}
				} else {
					if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunity.getCurrency())) {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						double previousProbability1 = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						OpportunityStages oppStages1 = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunity.getOppStage());
						if (null != oppStages1) {
							previousProbability1 = oppStages1.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);
						ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
								opportunity.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), previousProbability1);

						if (null != previous) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- previous.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- previous.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- previous.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- previous.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
					} else {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						double previousProbability1 = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						OpportunityStages oppStages1 = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunity.getOppStage());
						if (null != oppStages1) {
							previousProbability1 = oppStages1.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);
						ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
								opportunity.getProposalAmount(), userCurrency, orgCurrency, opportunity.getCurrency(),
								previousProbability1);

						if (null != previous) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- previous.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- previous.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- previous.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- previous.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
					}
				}
			}
		} else {
			if (opportunityMapper.getProposalAmount().equalsIgnoreCase(opportunity.getProposalAmount())) {
				if (opportunityMapper.getOppStage().equalsIgnoreCase(opportunity.getOppStage())) {
					if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunity.getCurrency())) {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);

						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunity.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							} else {
								CuOppConversionValue cuOppConversionValue1 = new CuOppConversionValue();
								cuOppConversionValue1.setCreationDate(new Date());
								cuOppConversionValue1.setCustomerId(opportunityMapper.getCustomerId());
								cuOppConversionValue1.setLiveInd(true);
								cuOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
								cuOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
								cuOppConversionValue1.setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
								cuOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
								cuOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
								cuOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
								cuOppConversionValue1.setUserConversionWeightedValue(mapper.getUserConversionWValue());
								cuOppConversionValue1.setUserId(opportunityMapper.getUserId());

								cuOppConversionValueRepo.save(cuOppConversionValue1);

							}
						}
					} else {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);
						ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunity.getCurrency(), probability);

						if (null != previous) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunity.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- previous.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- previous.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- previous.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- previous.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							} else {
								CuOppConversionValue cuOppConversionValue1 = new CuOppConversionValue();
								cuOppConversionValue1.setCreationDate(new Date());
								cuOppConversionValue1.setCustomerId(opportunityMapper.getCustomerId());
								cuOppConversionValue1.setLiveInd(true);
								cuOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
								cuOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
								cuOppConversionValue1.setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
								cuOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
								cuOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
								cuOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
								cuOppConversionValue1.setUserConversionWeightedValue(mapper.getUserConversionWValue());
								cuOppConversionValue1.setUserId(opportunityMapper.getUserId());

								cuOppConversionValueRepo.save(cuOppConversionValue1);

							}
						}
					}
				} else {
					if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunity.getCurrency())) {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						double previousProbability1 = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						OpportunityStages oppStages1 = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunity.getOppStage());
						if (null != oppStages1) {
							previousProbability1 = oppStages1.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);
						ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), previousProbability1);

						if (null != previous) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunity.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- previous.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- previous.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- previous.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- previous.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							} else {
								CuOppConversionValue cuOppConversionValue1 = new CuOppConversionValue();
								cuOppConversionValue1.setCreationDate(new Date());
								cuOppConversionValue1.setCustomerId(opportunityMapper.getCustomerId());
								cuOppConversionValue1.setLiveInd(true);
								cuOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
								cuOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
								cuOppConversionValue1.setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
								cuOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
								cuOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
								cuOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
								cuOppConversionValue1.setUserConversionWeightedValue(mapper.getUserConversionWValue());
								cuOppConversionValue1.setUserId(opportunityMapper.getUserId());

								cuOppConversionValueRepo.save(cuOppConversionValue1);

							}
						}
					} else {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						double previousProbability1 = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						OpportunityStages oppStages1 = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunity.getOppStage());
						if (null != oppStages1) {
							previousProbability1 = oppStages1.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);
						ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunity.getCurrency(), previousProbability1);

						if (null != previous) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunity.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- previous.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- previous.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- previous.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- previous.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							} else {
								CuOppConversionValue cuOppConversionValue1 = new CuOppConversionValue();
								cuOppConversionValue1.setCreationDate(new Date());
								cuOppConversionValue1.setCustomerId(opportunityMapper.getCustomerId());
								cuOppConversionValue1.setLiveInd(true);
								cuOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
								cuOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
								cuOppConversionValue1.setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
								cuOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
								cuOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
								cuOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
								cuOppConversionValue1.setUserConversionWeightedValue(mapper.getUserConversionWValue());
								cuOppConversionValue1.setUserId(opportunityMapper.getUserId());

								cuOppConversionValueRepo.save(cuOppConversionValue1);

							}
						}
					}
				}
			} else {
				if (opportunityMapper.getOppStage().equalsIgnoreCase(opportunity.getOppStage())) {
					if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunity.getCurrency())) {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);
						ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
								opportunity.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);

						if (null != previous) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunity.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- previous.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- previous.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- previous.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- previous.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							} else {
								CuOppConversionValue cuOppConversionValue1 = new CuOppConversionValue();
								cuOppConversionValue1.setCreationDate(new Date());
								cuOppConversionValue1.setCustomerId(opportunityMapper.getCustomerId());
								cuOppConversionValue1.setLiveInd(true);
								cuOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
								cuOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
								cuOppConversionValue1.setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
								cuOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
								cuOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
								cuOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
								cuOppConversionValue1.setUserConversionWeightedValue(mapper.getUserConversionWValue());
								cuOppConversionValue1.setUserId(opportunityMapper.getUserId());

								cuOppConversionValueRepo.save(cuOppConversionValue1);

							}
						}
					} else {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);
						ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
								opportunity.getProposalAmount(), userCurrency, orgCurrency, opportunity.getCurrency(),
								probability);

						if (null != previous) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunity.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- previous.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- previous.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- previous.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- previous.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							} else {
								CuOppConversionValue cuOppConversionValue1 = new CuOppConversionValue();
								cuOppConversionValue1.setCreationDate(new Date());
								cuOppConversionValue1.setCustomerId(opportunityMapper.getCustomerId());
								cuOppConversionValue1.setLiveInd(true);
								cuOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
								cuOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
								cuOppConversionValue1.setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
								cuOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
								cuOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
								cuOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
								cuOppConversionValue1.setUserConversionWeightedValue(mapper.getUserConversionWValue());
								cuOppConversionValue1.setUserId(opportunityMapper.getUserId());

								cuOppConversionValueRepo.save(cuOppConversionValue1);

							}
						}
					}
				} else {
					if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunity.getCurrency())) {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						double previousProbability1 = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						OpportunityStages oppStages1 = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunity.getOppStage());
						if (null != oppStages1) {
							previousProbability1 = oppStages1.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);
						ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
								opportunity.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), previousProbability1);

						if (null != previous) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunity.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- previous.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- previous.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- previous.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- previous.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							} else {
								CuOppConversionValue cuOppConversionValue1 = new CuOppConversionValue();
								cuOppConversionValue1.setCreationDate(new Date());
								cuOppConversionValue1.setCustomerId(opportunityMapper.getCustomerId());
								cuOppConversionValue1.setLiveInd(true);
								cuOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
								cuOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
								cuOppConversionValue1.setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
								cuOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
								cuOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
								cuOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
								cuOppConversionValue1.setUserConversionWeightedValue(mapper.getUserConversionWValue());
								cuOppConversionValue1.setUserId(opportunityMapper.getUserId());

								cuOppConversionValueRepo.save(cuOppConversionValue1);

							}
						}
					} else {
						String userCurrency = null;
						String orgCurrency = null;
						double probability = 0;
						double previousProbability1 = 0;
						EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
						if (null != emp1) {
							Currency currency = currencyRepository.getByCurrencyId(emp1.getCurrency());
							if (null != currency) {
								userCurrency = currency.getCurrencyName();
							}
						}
						OrganizationDetails organizationDetails = organizationRepository
								.getOrganizationDetailsById(opportunityMapper.getOrgId());
						if (null != organizationDetails.getTrade_currency()) {
							orgCurrency = organizationDetails.getTrade_currency();
						}
						OpportunityStages oppStages = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
						if (null != oppStages) {
							probability = oppStages.getProbability();
						}

						OpportunityStages oppStages1 = opportunityStagesRepository
								.getOpportunityStagesByOpportunityStagesId(opportunity.getOppStage());
						if (null != oppStages1) {
							previousProbability1 = oppStages1.getProbability();
						}

						ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
								opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
								opportunityMapper.getCurrency(), probability);
						ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
								opportunity.getProposalAmount(), userCurrency, orgCurrency, opportunity.getCurrency(),
								previousProbability1);

						if (null != previous) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunity.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										- previous.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										- previous.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										- previous.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										- previous.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							}
						}
						if (null != mapper) {
							CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
									.findByCustomerId(opportunityMapper.getCustomerId());
							if (null != cuOppConversionValue) {
								double userpValue = (cuOppConversionValue.getUserConversionValue()
										+ mapper.getUserConversionPValue());
								cuOppConversionValue.setUserConversionValue(userpValue);
								double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
										+ mapper.getUserConversionWValue());
								cuOppConversionValue.setUserConversionWeightedValue(userwValue);
								double orgpValue = (cuOppConversionValue.getOrgConversionValue()
										+ mapper.getOrgConversionPValue());
								cuOppConversionValue.setOrgConversionValue(orgpValue);
								double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
										+ mapper.getOrgConversionWValue());
								cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
								cuOppConversionValue.setUserConversionCurrency(userCurrency);
								cuOppConversionValue.setOrgConversionCurrency(orgCurrency);
								cuOppConversionValueRepo.save(cuOppConversionValue);
							} else {
								CuOppConversionValue cuOppConversionValue1 = new CuOppConversionValue();
								cuOppConversionValue1.setCreationDate(new Date());
								cuOppConversionValue1.setCustomerId(opportunityMapper.getCustomerId());
								cuOppConversionValue1.setLiveInd(true);
								cuOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
								cuOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
								cuOppConversionValue1.setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
								cuOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
								cuOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
								cuOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
								cuOppConversionValue1.setUserConversionWeightedValue(mapper.getUserConversionWValue());
								cuOppConversionValue1.setUserId(opportunityMapper.getUserId());

								cuOppConversionValueRepo.save(cuOppConversionValue1);

							}
						}
					}
				}
			}
		}

//	if (null != opportunityMapper.getOpportunitySkill()) {
//        List<OpportunitySkillLinkMapper> opportunitySkillLinkList = opportunityMapper.getOpportunitySkill();
//
//        for(OpportunitySkillLinkMapper opportunitySkillLinkMapper :opportunitySkillLinkList) {
//	     
//            String opportunitySkillLinkId=opportunitySkillLinkMapper.getOpportunitySkillLinkId();
//    	     if(null!=opportunitySkillLinkId) {
//    	      
//    	         OpportunitySkillLink opportunitySkillLink=opportunitySkillLinkRepository.getOpportunitySkillLinkId(opportunitySkillLinkId);
//    	         if(null!=opportunitySkillLink) {
//    	             if(null!=opportunitySkillLinkMapper.getNoOfPosition())
//    	                 opportunitySkillLink.setNoOfPosition(opportunitySkillLinkMapper.getNoOfPosition());   
//                     if(null!=opportunitySkillLinkMapper.getOppInnitiative())
//                         opportunitySkillLink.setOppInnitiative(opportunitySkillLinkMapper.getOppInnitiative());   
//                     if(null!=opportunitySkillLinkMapper.getSkill())
//                         opportunitySkillLink.setSkill(opportunitySkillLinkMapper.getSkill()); 
//                     opportunitySkillLinkRepository.save(opportunitySkillLink);
//    	             
//    	         }
//    	     }
//        }
//	}

		OpportunityViewMapper opportunityViewMapper = getOpportunityRelatedDetails(updateOpportunity);

		return opportunityViewMapper;
	}

	/*
	 * public OpportunityMapper getOpportunityMapper(OpportunityDetails opportunity)
	 * { OpportunityMapper resultMapper=new OpportunityMapper();
	 * 
	 * resultMapper.setOpportunityId(opportunity.getOpportunityId());
	 * resultMapper.setOpportunityName(opportunity.getOpportunityName());
	 * resultMapper.setProposalAmount(opportunity.getProposalAmount());
	 * resultMapper.setCurrency(opportunity.getCurrency());
	 * resultMapper.setCustomerId(opportunity.getCustomerId());
	 * resultMapper.setUserId(opportunity.getUserId());
	 * resultMapper.setOrgId(opportunity.getOrgId());
	 * resultMapper.setStartDate(Utility.getISOFromDate(opportunity.getStartDate()))
	 * ; resultMapper.setEndDate(Utility.getISOFromDate(opportunity.getEndDate()));
	 * //resultMapper.setCreationDate(Utility.getISOFromDate(opportunity.
	 * getCreation_date()));
	 * 
	 * return resultMapper; }
	 */

	@Override
	public String saveOpportunityNotes(NotesMapper notesMapper) {
		String notesId = null;
		if (null != notesMapper) {
			Notes notes = new Notes();
			notes.setNotes(notesMapper.getNotes());
			notes.setCreation_date(new Date());
			notes.setUserId(notesMapper.getUserId());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);
			notesId = note.getNotes_id();

			/* insert to opportunity-notes-link */

			OpportunityNotesLink opportunityNotesLink = new OpportunityNotesLink();

			opportunityNotesLink.setOpportunity_id(notesMapper.getOpportunityId());
			opportunityNotesLink.setNotesId(notesId);
			opportunityNotesLink.setCreation_date(new Date());

			opportunityNotesLinkRepository.save(opportunityNotesLink);

		}
		return null;
	}

	@Override
	public List<NotesMapper> getNoteListByOpportunityId(String opportunityId) {
		List<OpportunityNotesLink> opportunityNotesLinkList = opportunityNotesLinkRepository
				.getNoteListByOpportunityId(opportunityId);
		List<NotesMapper> resultList = new ArrayList<NotesMapper>();

		if (opportunityNotesLinkList != null && !opportunityNotesLinkList.isEmpty()) {
			opportunityNotesLinkList.stream().map(opportunityNotesLink -> {

				NotesMapper notesMapper = getNotes(opportunityNotesLink.getNotesId());
				resultList.add(notesMapper);
				return resultList;
			}).collect(Collectors.toList());

		}

		System.out.println("resultList.........." + resultList);
		return resultList;
	}

	@Override
	public NotesMapper getNotes(String id) {
		Notes notes = notesRepository.findByNoteId(id);
		NotesMapper notesMapper = new NotesMapper();
		if (null != notes) {
			notesMapper.setNotesId(notes.getNotes_id());
			notesMapper.setNotes(notes.getNotes());
			notesMapper.setUserId(notes.getUserId());
			notesMapper.setCreationDate(Utility.getISOFromDate(notes.getCreation_date()));
			notesMapper.setOwnerName(employeeService.getEmployeeFullName(notes.getUserId()));

			System.out.println("opportunity notes.........." + notesMapper.toString());
		}
		return notesMapper;
	}

	@Override
	public List<DocumentMapper> getOpportunityDetailsListBydocumentId(String opportunityId) {
		List<OpportunityDocumentLink> opportunityDocumentLinkList = opportunityDocumentLinkRepository
				.getDocumentByOpportunityId(opportunityId);
		List<DocumentMapper> resultList = new ArrayList<DocumentMapper>();
		Set<String> documentIds = opportunityDocumentLinkList.stream().map(OpportunityDocumentLink::getDocument_id)
				.collect(Collectors.toSet());
		if (documentIds != null && !documentIds.isEmpty()) {
			documentIds.stream().map(documentId -> {
				DocumentMapper documentMapper = documentService.getDocument(documentId);

				if (null != documentMapper.getDocumentId()) {
					documentMapper.setContractInd(opportunityDocumentLinkList.get(0).isContractInd());
					resultList.add(documentMapper);
				}
				return documentMapper;
			}).collect(Collectors.toList());

		}

		return resultList;
	}

	@Override
	public DocumentMapper getDocument(String id) {
		DocumentDetails doc = documentDetailsRepository.getById(id);
		DocumentMapper documentMapper = new DocumentMapper();
		documentMapper.setDocumentId(doc.getDocument_id());
		documentMapper.setDocumentName(doc.getDocument_name());
		documentMapper.setDocumentContentType(doc.getDocument_type());

		// documentMapper.setCreationDate(Utility.getISOFromDate(doc.getCreation_date()));

		return documentMapper;
	}

	@Override
	public OpportunityViewMapper getOpportunityDetails(String opportunityId) {

		OpportunityViewMapper opportunityViewMapper = new OpportunityViewMapper();
		if (null != opportunityId && opportunityId.trim().length() > 0) {

			OpportunityDetails opportunity = opportunityDetailsRepository
					.getOpportunityDetailsByOpportunityId(opportunityId);

			System.out.println("Opportunity@@@@@@@@@&" + opportunity);
			if (null != opportunity) {
				opportunityViewMapper = getOpportunityRelatedDetails(opportunity);
			}
		}
		return opportunityViewMapper;

	}

	@Override
	public List<OpportunityViewMapper> getOpportunityDetailsByName(String opportunityName) {
		List<OpportunityDetails> list = opportunityDetailsRepository
				.findByOpportunityNameContainingAndLiveIndAndReinstateIndAndCloseIndAndLostIndAndWonInd(opportunityName,
						true, true, false, false, false);

		List<OpportunityViewMapper> resultList = new ArrayList<OpportunityViewMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(opportunityDetails -> {
				OpportunityViewMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
				if (null != opportunityViewMapper) {
					resultList.add(opportunityViewMapper);
					return resultList;

				}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	public ByteArrayInputStream exportCandidateListToExcel(List<OpportunityViewMapper> opportunityList) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("opportunity");

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
		for (int i = 0; i < opportunity_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(opportunity_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != opportunityList && !opportunityList.isEmpty()) {
			for (OpportunityViewMapper opportunity : opportunityList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(opportunity.getOpportunityId());
				row.createCell(1).setCellValue(opportunity.getOpportunityName());
				row.createCell(2).setCellValue(opportunity.getProposalAmount());
				row.createCell(3).setCellValue(opportunity.getCurrency());
				row.createCell(4).setCellValue(opportunity.getCustomer());
				row.createCell(5).setCellValue(opportunity.getOppWorkflow());

			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < opportunity_headings.length; i++) {
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

	public ContactViewMapper saveOpportunityContact(ContactMapper contactMapper) throws IOException, TemplateException {

		ContactViewMapper resultMapper = contactService.saveContact(contactMapper);

		return resultMapper;
	}

	@Override
	public List<ContactViewMapper> getContactListByOpportunityId(String opportunityId) {
		List<OpportunityContactLink> opportunityContactLinkList = opportunityContactLinkRepository
				.getContactByOpportunityId(opportunityId);
		System.out.println("%%%%%%%%%%%%%" + opportunityContactLinkList);

		List<ContactViewMapper> resultList = new ArrayList<ContactViewMapper>();

		if (opportunityContactLinkList != null && !opportunityContactLinkList.isEmpty()) {
			opportunityContactLinkList.stream().map(opportunityContactLinkk -> {

				ContactViewMapper contactMapper = contactService
						.getContactDetailsById(opportunityContactLinkk.getContactId());
				resultList.add(contactMapper);
				return resultList;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public void deleteOppertunityDetailsById(String id) {
		if (null != id) {
			OpportunityDetails opportunityDetails = opportunityDetailsRepository.findByOppId(id);
			if (null != opportunityDetails) {
				// opportunityDetails.setLiveInd(false);
				opportunityDetails.setReinstateInd(false);
				opportunityDetailsRepository.save(opportunityDetails);
			}
		}
	}

	@Override
	public List<OpportunityViewMapper> getDeleteOpportunityDetails(String loggeduserId, int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
//		List<OpportunityDetails> opportunity = opportunityDetailsRepository.findByReinstateIndAndLiveIndAndUserId(false,
//				true, loggeduserId,paging);
		List<OpportunityViewMapper> resultList = new ArrayList<OpportunityViewMapper>();
		Page<OpportunityDetails> opportunity = opportunityDetailsRepository.findByReinstateIndAndLiveIndAndUserId(false,
				true, loggeduserId, paging);
		;
		if (null != opportunity && !opportunity.isEmpty()) {
			opportunity.stream().map(opportunityDetails -> {
				OpportunityViewMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
				if (null != opportunityViewMapper) {
					resultList.add(opportunityViewMapper);
					opportunityViewMapper.setPageCount(opportunity.getTotalPages());
					opportunityViewMapper.setDataCount(opportunity.getSize());
					opportunityViewMapper.setListCount(opportunity.getTotalElements());
					return resultList;

				}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	/*
	 * @Override public HashMap getCountList() {
	 * 
	 * List<OpportunityDetails> opportunityDetailsList =
	 * opportunityDetailsRepository.findByActive(true); HashMap map = new HashMap();
	 * map.put("opportunityDetails", opportunityDetailsList.size());
	 * 
	 * return map; }
	 */

	@Override
	public HashMap getCountListByuserId(String userId) {
		HashMap map = new HashMap();
		List<OpportunityDetails> opportunityDetails = opportunityDetailsRepository.getOpportunityByUserId(userId);
		map.put("opportunty", opportunityDetails.size());
		return map;

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
	public List<OpportunityViewMapper> getOpprtunityByRecruiterId(String recruiterId) {

		List<OpportunityRecruiterLink> oppList = opportunityRecruiterLinkRepository
				.getRecruiterLinkByRecruiterId(recruiterId);
		List<OpportunityViewMapper> mapperList = new ArrayList<>();
		if (null != oppList && !oppList.isEmpty()) {

			mapperList = oppList.stream()
					.map(li -> getOpportunityRelatedDetails(
							opportunityDetailsRepository.getOpportunityDetailsByOpportunityId(li.getOpportunity_id())))
					.collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<OpportunityViewMapper> getopportunityOfASalesUser(String userId) {

		List<OpportunitySalesUserLink> list = opportunitySalesUserRepository.getSalesUserLinkByUserId(userId);
		System.out.println("list@@@@@@@@@" + list);
		List<OpportunityViewMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {

			mapperList = list.stream().map(li -> getOpportunityDetails(li.getOpportunity_id()))
					.collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public String saveTagContact(ContactMapper contactMapper) {
		String id = null;

		if (null != contactMapper) {
			OpportunityContactLink opportunityContactLink = new OpportunityContactLink();

			opportunityContactLink.setContactId(contactMapper.getContactId());
			opportunityContactLink.setOpportunityId(contactMapper.getOpportunityId());
			opportunityContactLinkRepository.save(opportunityContactLink);

			id = opportunityContactLink.getId();

		}
		return id;
	}

//	@Override
//	public List<OpportunityViewMapper> getAllOpportunityList(int pageNo, int pageSize) {
//		List<OpportunityViewMapper> resultMapper = new ArrayList<OpportunityViewMapper>();
//
//		List<Permission> permission = permissionRepository.getUserListForOpportunity();
//		System.out.println(" user$$$$$$$$$$$$==" + permission.toString());
//
//		if (null != permission && !permission.isEmpty()) {
//			permission.stream().map(permissionn -> {
//
//				List<OpportunityViewMapper> mp = OpportunityService
//						.getOpportunityDetailsListPageWiseByUserId(permissionn.getUserId(), pageNo, pageSize);
//
//				System.out.println(" userId$$$$$$$$$$$$==" + permissionn.getUserId());
//
//				resultMapper.addAll(mp);
//				return resultMapper;
//			}).filter(l -> l != null).flatMap(l -> l.stream()).collect(Collectors.toList());
//
//		}
//		return resultMapper;
//
//	}

	@Override
	public void reinstateOpportunityByOppId(String opportunityId) {
		if (null != opportunityId) {
			OpportunityDetails opportunityDetails = opportunityDetailsRepository
					.getByOpportunityIdAndReinstateIndAndLiveInd(opportunityId, false, true);

			// opportunityDetails.setLiveInd(true);
			opportunityDetails.setReinstateInd(true);
			opportunityDetailsRepository.save(opportunityDetails);

		}
	}

	@Override
	public HashMap getRecruitrtCountByuserId(String userId) {
		List<OpportunityRecruiterLink> opportunityDetailsList = opportunityRecruiterLinkRepository
				.getRecruiterLinkByRecruiterId(userId);
		HashMap map = new HashMap();
		map.put("opportunityDetails", opportunityDetailsList.size());
		return map;
	}

	@Override
	public List<RecruitmentOpportunityMapper> getOpportunityListByJobOrder(String jobOrder) {

		List<RecruitmentOpportunityMapper> mapperList = new ArrayList<>();

		List<OpportunityRecruitDetails> opportunity = recruitmentOpportunityDetailsRepository.getByJobOrder(jobOrder);
		// RecruitProfileLinkDetails profileList =
		// recruitProfileDetailsRepository.getProfileDetailByRecruitmentId(opportunity.getRecruitment_id());
		if (null != opportunity && !opportunity.isEmpty()) {
			opportunity.stream().map(li -> {

				RecruitmentOpportunityMapper opp = new RecruitmentOpportunityMapper();
				opp.setJobOrder(li.getJob_order());

				opp.setRequirementName(li.getName());
				opp.setRecruitmentId(li.getRecruitment_id());
				opp.setRecruitmentProcessId(li.getRecruitment_process_id());
				// opp.setProcessName(getProcessName(opportunity.getRecruitment_process_id()));
				opp.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
				opp.setAvilableDate(Utility.getISOFromDate(li.getAvailable_date()));
				opp.setEndDate(Utility.getISOFromDate(li.getEnd_date()));
				opp.setBilling(li.getBilling());
				if (!StringUtils.isEmpty(li.getUserId())) {
					EmployeeDetails employee = employeeRepository.getEmployeeDetailsByEmployeeId(li.getUserId());
					opp.setRecruitOwner(employee.getFullName());
				}
				opp.setCategory(li.getCategory());

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
					opp.setCandidatetList(resultList);
				}
				opp.setCandidateNo(recruitmentService.candidateNo(recruitmentCandidateLink.size()));
				List<RecruitmentSkillsetLink> recruitmentSkillsetLink = recruitmentSkillsetLinkRepository
						.getRecruitmentSkillSetLinkByRecruitmentId(li.getRecruitment_id());
				if (recruitmentSkillsetLink.size() != 0) {
					opp.setSkillName(recruitmentSkillsetLink.get(0).getSkillName());

				}

				mapperList.add(opp);

				return mapperList;
			}).collect(Collectors.toList());

		}
		// opp.getOpportunityName(opportunity.getName());

		// resultList =
		// opportunityList.stream().map(li->getOpportunityDetailsByJobOrder(li.getJobOrder())).collect(Collectors.toList());

		// if (null != opportunityList && !opportunityList.isEmpty()) {
		// for (OpportunityRecruitDetails opportunity : opportunityList) {

		// OpportunityViewMapper opportunityViewMapper = new OpportunityViewMapper();

		// resultList.add(opportunityViewMapper);
		return mapperList;
	}

	/*
	 * @Override public HashMap getNoOfOpportunityBycreationDate() { int
	 * noOfOpportunity = 0;
	 * 
	 * // Millseconds in a day final long ONE_DAY_MILLI_SECONDS = (24 * 60 * 60 *
	 * 1000)-1;
	 * 
	 * // date format LocalDateTime now = LocalDateTime.now(); Date currDate = new
	 * GregorianCalendar(now.getYear(), now.getMonthValue() - 1,
	 * now.getDayOfMonth()).getTime();
	 * 
	 * long nextDayMilliSeconds = currDate.getTime() + ONE_DAY_MILLI_SECONDS; Date
	 * nextDate = new Date(nextDayMilliSeconds);
	 * 
	 * List<OpportunityDetails> opList =
	 * opportunityDetailsRepository.findByCreationDateBetweenAndLiveInd(currDate,
	 * nextDate,true); System.out.println(opList); HashMap map = new HashMap();
	 * map.put("opportunityList",opList.size()); return map; }
	 */
	@Override
	public String saveTagContactWithOppertunity(OpportunityMapper opportunityMapper) {
		String Id = null;

		if (null != opportunityMapper) {

			/* insert OpportunityContact link table */

			OpportunityContactLink opportunityContactLink = new OpportunityContactLink();
			opportunityContactLink.setOpportunityId(opportunityMapper.getOpportunityId());
			opportunityContactLink.setContactId(opportunityMapper.getContactId());
			opportunityContactLink.setCreationDate(new Date());
			Id = opportunityContactLinkRepository.save(opportunityContactLink).getId();

		}
		return Id;

	}

	@Override
	public List<String> updateTransferOneUserToAnother(String userId, TransferMapper transferMapper) {

		List<String> opportunityList = transferMapper.getOpportunityIds();

		System.out.println("opportunityList::::::::::::::::::::::::::::::::::::::::::::::::::::" + opportunityList);
		if (null != opportunityList && !opportunityList.isEmpty()) {
			for (String opportunityId : opportunityList) {
				System.out.println("the opportunity id is : " + opportunityId);
				OpportunityDetails opportunityDetails = opportunityDetailsRepository
						.getopportunityDetailsById(opportunityId);
				System.out.println(
						"opportunityDetails::::::::::::::::::::::::::::::::::::::::::::::::::::" + opportunityDetails);
				if (null != opportunityDetails) {
					opportunityDetails.setUserId(userId);
					opportunityDetailsRepository.save(opportunityDetails);
				}
			}

		}
		return opportunityList;
	}

	@Override
	public ContactViewMapper updateContactRoleByContactId(String contactId, ContactViewMapper contactViewMapper) {

		ContactDetails contactDetails = contactRepository.getContactDetailsById(contactId);

		ContactViewMapper mapper = new ContactViewMapper();
		if (contactDetails != null) {
			if (null != contactViewMapper.getContactRole() && !contactViewMapper.getContactRole().isEmpty()) {
				System.out.println("contactRole======" + contactViewMapper.getContactRole());
				contactDetails.setContactRole(contactViewMapper.getContactRole());
				contactRepository.save(contactDetails);

			}
			mapper = contactService.getContactDetailsById(contactDetails.getContactId());
		}
		return mapper;
	}

	@Override
	public OpportunityViewMapper updateCustomerByOpportunityId(String opportunityId,
			OpportunityViewMapper opportunityViewMapper) {
		OpportunityDetails opportunityDetails = opportunityDetailsRepository.getopportunityDetailsById(opportunityId);

		OpportunityViewMapper opportunityMapper = new OpportunityViewMapper();

		if (opportunityDetails != null) {
			if (null != opportunityViewMapper.getCustomerId() && !opportunityViewMapper.getCustomerId().isEmpty()) {
				opportunityDetails.setCustomerId(opportunityViewMapper.getCustomerId());
				opportunityDetailsRepository.save(opportunityDetails);
			}
			opportunityMapper = getOpportunityDetails(opportunityDetails.getOpportunityId());
		}

		return opportunityMapper;
	}

	@Override
	public List<OpportunitySkillLinkMapper> saveInnitiativeSkillAndNumber(OpportunityMapper opportunityMapper) {
		if (opportunityMapper.getOpportunitySkill().size() > 0) {

			for (OpportunitySkillLinkMapper opportunitySkillLinkMapper : opportunityMapper.getOpportunitySkill()) {
				OpportunitySkillLink opportunitySkillLink1 = new OpportunitySkillLink();
				opportunitySkillLink1.setNoOfPosition(opportunitySkillLinkMapper.getNoOfPosition());
				opportunitySkillLink1.setOppInnitiative(opportunityMapper.getOppInnitiative());
				opportunitySkillLink1.setOpportunityId(opportunityMapper.getOpportunityId());
				opportunitySkillLink1.setOrgId(opportunityMapper.getOrgId());
				opportunitySkillLink1.setUserId(opportunityMapper.getUserId());
				opportunitySkillLink1.setSkill(opportunitySkillLinkMapper.getSkill());
				opportunitySkillLinkRepository.save(opportunitySkillLink1);
			}
		}
		List<OpportunitySkillLinkMapper> oportunitySkillLinkMapper2 = getSkillAndNumberByOppotunityId(
				opportunityMapper.getOpportunityId());

		return oportunitySkillLinkMapper2;
	}

	@Override
	public List<OpportunitySkillLinkMapper> getSkillAndNumberByOppotunityId(String opportunityId) {
		List<OpportunitySkillLink> opportunitySkillLink = opportunitySkillLinkRepository
				.getSkillListByOpportunityId(opportunityId);
		List<OpportunitySkillLinkMapper> list = new ArrayList<>();
		if (null != opportunitySkillLink && !opportunitySkillLink.isEmpty()) {
			opportunitySkillLink.stream().map(skillLink -> {
				OpportunitySkillLinkMapper opportunitySkillLinkMapper = new OpportunitySkillLinkMapper();

				opportunitySkillLinkMapper.setNoOfPosition(skillLink.getNoOfPosition());
				opportunitySkillLinkMapper.setOppInnitiative(skillLink.getOppInnitiative());
				opportunitySkillLinkMapper.setOpportunityId(skillLink.getOpportunityId());
				opportunitySkillLinkMapper.setOpportunitySkillLinkId(skillLink.getOpportunitySkillLinkId());
				opportunitySkillLinkMapper.setOrgId(skillLink.getOrgId());
				opportunitySkillLinkMapper.setUserId(skillLink.getUserId());
				DefinationDetails definationDetails = definationRepository.findByDefinationId(skillLink.getSkill());
				if (null != definationDetails) {
					opportunitySkillLinkMapper.setSkill(definationDetails.getDefinationId());
					opportunitySkillLinkMapper.setSkillName(definationDetails.getName());
				}

				list.add(opportunitySkillLinkMapper);
				return list;

			}).collect(Collectors.toList());
		}

		return list;
	}

	@Override
	public HashMap getDeleteHistoryCountList(String userId) {
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getByUserIdAndReinstateIndAndLiveInd(userId);
		HashMap map = new HashMap();
		map.put("opportunityDetails", opportunityDetailsList.size());

		return map;
	}

	@Override
	public void updateOpportunityWonIndByOpportunityId(String opportunityId, OpportunityMapper opportunityMapper) {
		OpportunityDetails opportunityDetails = opportunityDetailsRepository.getopportunityDetailsById(opportunityId);

		if (opportunityDetails != null) {

			opportunityDetails.setWonInd(opportunityMapper.isWonInd());
			opportunityDetails.setWonDate(new Date());
			opportunityDetails.setModifiedDate(new Date());
			opportunityDetailsRepository.save(opportunityDetails);

			List<Order> orderList = orderRepository.findByOpportunityId(opportunityId);
			if (null != orderList && !orderList.isEmpty()) {
				for (Order order : orderList) {
					order.setOpportunityInd(false);
					orderRepository.save(order);
				}
			}

			List<OrderDetails> orderDetailList = orderDetailsRepository.findByOpportunityId(opportunityId);
			if (null != orderDetailList && !orderDetailList.isEmpty()) {
				for (OrderDetails order1 : orderDetailList) {
					order1.setOpportunityInd(false);
					orderDetailsRepository.save(order1);
				}
			}

		}

	}

	@Override
	public void updateOpportunityLostIndByOpportunityId(String opportunityId, OpportunityMapper opportunityMapper) {
		OpportunityDetails opportunityDetails = opportunityDetailsRepository.getopportunityDetailsById(opportunityId);

		if (opportunityDetails != null) {

			opportunityDetails.setLostInd(opportunityMapper.isLostInd());
			opportunityDetails.setLostDate(new Date());
			opportunityDetails.setModifiedDate(new Date());
			opportunityDetailsRepository.save(opportunityDetails);

		}
	}

	@Override
	public void updateOpportunityCloseIndByOpportunityId(String opportunityId, OpportunityMapper opportunityMapper) {
		OpportunityDetails opportunityDetails = opportunityDetailsRepository.getopportunityDetailsById(opportunityId);

		if (opportunityDetails != null) {

			opportunityDetails.setCloseInd(opportunityMapper.isCloseInd());
			opportunityDetails.setCloseDate(new Date());
			opportunityDetails.setModifiedDate(new Date());
			opportunityDetailsRepository.save(opportunityDetails);
		}
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityDetailByUserIdAndCloseInd(String userId, int pageNo,
			int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("ModifiedDate").descending());
		Page<OpportunityDetails> list = opportunityDetailsRepository
				.getOpportunityDetailsPaseWiseUserIdAndCloseIndAndLiveInd(userId, paging);
		if (null != list && !list.isEmpty()) {
			list.stream().sorted((p1, p2) -> p1.getModifiedDate().compareTo(p2.getModifiedDate()));
		}
		List<OpportunityViewMapper> resultMapper = new ArrayList<OpportunityViewMapper>();
		if (list != null && !list.isEmpty()) {
			list.stream().map(opportunity -> {
				OpportunityViewMapper mapper = getOpportunityDetails(opportunity.getOpportunityId());
				mapper.setPageCount(list.getTotalPages());
				mapper.setDataCount(list.getSize());
				mapper.setListCount(list.getTotalElements());
				resultMapper.add(mapper);
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;

	}

	@Override
	public List<OpportunityViewMapper> getOpportunityDetailByUserIdAndLostInd(String userId, int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("ModifiedDate").descending());
		Page<OpportunityDetails> list = opportunityDetailsRepository
				.getOpportunityDetailsPaseWiseUserIdAndLostIndAndLiveInd(userId, paging);
		List<OpportunityViewMapper> resultMapper = new ArrayList<OpportunityViewMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().sorted((p1, p2) -> p1.getModifiedDate().compareTo(p2.getModifiedDate()));
		}
		if (list != null && !list.isEmpty()) {
			list.stream().map(opportunity -> {
				OpportunityViewMapper mapper = getOpportunityDetails(opportunity.getOpportunityId());
				mapper.setPageCount(list.getTotalPages());
				mapper.setDataCount(list.getSize());
				mapper.setListCount(list.getTotalElements());
				resultMapper.add(mapper);
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityDetailByUserIdAndWonInd(String userId, int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("ModifiedDate").descending());
//		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "creationDate"));
		Page<OpportunityDetails> list = opportunityDetailsRepository
				.getOpportunityDetailsPaseWiseUserIdAndWonIndAndLiveInd(userId, paging);
//		Page<OpportunitySalesUserLink> list = opportunitySalesUserRepository
//				.getSalesUserLinkByUserIdWithPagination(userId, paging);
		List<OpportunityViewMapper> resultMapper = new ArrayList<OpportunityViewMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().filter(li -> (li != null)).map(li -> {
				OpportunityDetails opportunity = opportunityDetailsRepository
						.getOpportunityDetailsOpportunityIdAndWonIndAndLiveInd(li.getOpportunityId());
				if (null != opportunity) {
					OpportunityViewMapper mapper = getOpportunityDetails(opportunity.getOpportunityId());
					mapper.setPageCount(list.getTotalPages());
					mapper.setDataCount(list.getSize());
					mapper.setListCount(list.getTotalElements());
					resultMapper.add(mapper);

					return mapper;
				}
				return null;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public OpportunityForecastLinkMapper saveForecast(OpportunityForecastLinkMapper opportunityMapper) {

		OpportunityForecastLinkMapper opportunityForecastLinkMapper2 = new OpportunityForecastLinkMapper();

		if (null != opportunityMapper) {
			OpportunityForecastLink opportunityForecastLink1 = new OpportunityForecastLink();

			opportunityForecastLink1.setNoOfPosition(opportunityMapper.getNoOfPosition());
			opportunityForecastLink1.setOpportunityId(opportunityMapper.getOpportunityId());
			opportunityForecastLink1.setOrgId(opportunityMapper.getOrgId());
			opportunityForecastLink1.setUserId(opportunityMapper.getUserId());
			opportunityForecastLink1.setSkill(opportunityMapper.getSkill());
			opportunityForecastLink1.setMonth(opportunityMapper.getMonth());
			opportunityForecastLink1.setYear(opportunityMapper.getYear());
			opportunityForecastLink1.setCreationDate(new Date());

			opportunityForecastLinkRepository.save(opportunityForecastLink1);
			opportunityForecastLinkMapper2 = getForecastSkillAndNumberByForcastId(
					opportunityForecastLink1.getOpportunityForecastLinkId());
		}

		return opportunityForecastLinkMapper2;

	}

	private OpportunityForecastLinkMapper getForecastSkillAndNumberByForcastId(String opportunityForecastLinkId) {
		OpportunityForecastLink opportunityForecastLink = opportunityForecastLinkRepository
				.getById(opportunityForecastLinkId);
		OpportunityForecastLinkMapper opportunityForecastLinkMapper = new OpportunityForecastLinkMapper();
		if (null != opportunityForecastLink) {

			opportunityForecastLinkMapper.setNoOfPosition(opportunityForecastLink.getNoOfPosition());
			opportunityForecastLinkMapper.setOpportunityId(opportunityForecastLink.getOpportunityId());
			opportunityForecastLinkMapper
					.setOpportunityForecastLinkId(opportunityForecastLink.getOpportunityForecastLinkId());
			opportunityForecastLinkMapper.setOrgId(opportunityForecastLink.getOrgId());
			opportunityForecastLinkMapper.setUserId(opportunityForecastLink.getUserId());
			opportunityForecastLinkMapper.setMonth(opportunityForecastLink.getMonth());
			opportunityForecastLinkMapper.setYear(opportunityForecastLink.getYear());
			opportunityForecastLinkMapper
					.setCreationDate(Utility.getISOFromDate(opportunityForecastLink.getCreationDate()));
			DefinationDetails definationDetails = definationRepository
					.findByDefinationId(opportunityForecastLink.getSkill());
			if (null != definationDetails) {
				opportunityForecastLinkMapper.setSkill(definationDetails.getDefinationId());
				opportunityForecastLinkMapper.setSkillName(definationDetails.getName());
			}
		}

		return opportunityForecastLinkMapper;
	}

	@Override
	public List<OpportunityForecastLinkMapper> getForecastSkillAndNumberByOppotunityId(String opportunityId) {
		List<OpportunityForecastLink> opportunityForecastLink = opportunityForecastLinkRepository
				.getSkillListByOpportunityId(opportunityId);
		List<OpportunityForecastLinkMapper> list = new ArrayList<>();
		if (null != opportunityForecastLink && !opportunityForecastLink.isEmpty()) {
			opportunityForecastLink.stream().map(skillLink -> {
				OpportunityForecastLinkMapper opportunityForecastLinkMapper = new OpportunityForecastLinkMapper();

				opportunityForecastLinkMapper.setNoOfPosition(skillLink.getNoOfPosition());
				opportunityForecastLinkMapper.setOpportunityId(skillLink.getOpportunityId());
				opportunityForecastLinkMapper.setOpportunityForecastLinkId(skillLink.getOpportunityForecastLinkId());
				opportunityForecastLinkMapper.setOrgId(skillLink.getOrgId());
				opportunityForecastLinkMapper.setUserId(skillLink.getUserId());
				opportunityForecastLinkMapper.setMonth(skillLink.getMonth());
				opportunityForecastLinkMapper.setYear(skillLink.getYear());

				opportunityForecastLinkMapper.setCreationDate(Utility.getISOFromDate(skillLink.getCreationDate()));

				DefinationDetails definationDetails = definationRepository.findByDefinationId(skillLink.getSkill());
				if (null != definationDetails) {
					opportunityForecastLinkMapper.setSkill(definationDetails.getDefinationId());
					opportunityForecastLinkMapper.setSkillName(definationDetails.getName());
				}

				list.add(opportunityForecastLinkMapper);
				return list;
			}).collect(Collectors.toList());
		}

		return list;
	}

	@Override
	public List<OpportunityForecastLinkMapper> updateOpportunityForecastOpportunityId(String opportunityId,
			OpportunityMapper opportunityMapper) {
		OpportunityForecastLink opportunityForecastLink = opportunityForecastLinkRepository
				.getopportunityDetailsById(opportunityId);

		List<OpportunityForecastLinkMapper> resultMapper = new ArrayList<>();

		if (opportunityForecastLink != null) {
			opportunityForecastLink.setUserId(opportunityMapper.getUserId());
			opportunityForecastLink.setOrgId(opportunityMapper.getOrgId());
			// opportunityForecastLink.setNoOfPosition(opportunityMapper.getNoOfPosition());
			opportunityForecastLink.setOpportunityId(opportunityMapper.getOpportunityId());
			opportunityForecastLinkRepository.save(opportunityForecastLink);
		}
		resultMapper = getForecastSkillAndNumberByOppotunityId(opportunityForecastLink.getOpportunityId());
		return resultMapper;
	}

	@Override
	public HashMap getOpportunityListByCloseInd(String userId) {
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getByUserIdAndCloseIndAndLiveInd(userId);
		HashMap map = new HashMap();
		map.put("OpportunityDetailsByCloseInd", opportunityDetailsList.size());

		return map;

	}

	@Override
	public HashMap getOpportunityListByLostInd(String userId) {
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getByUserIdAndLostIndAndLiveInd(userId);
		HashMap map = new HashMap();
		map.put("OpportunityDetailsbyLostInd", opportunityDetailsList.size());

		return map;

	}

	@Override
	public HashMap getOpportunityListByWonInd(String userId) {
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getByUserIdAndWonIndAndLiveInd(userId);
		HashMap map = new HashMap();
		map.put("OpportunityDetailsbyWonInd", opportunityDetailsList.size());

		return map;

	}

	@Override
	public HashMap getOpenRecruitmentAndOpenPositionCountByOpportunityId(String opportunityId) {
		HashMap map = new HashMap();
		int openRecruitment = 0;
		int openPosition = 0;
		List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
				.getOpenRecriutmentsByOpportunityIdAndCloseInd(opportunityId);
		if (null != recruitList && !recruitList.isEmpty()) {
			for (OpportunityRecruitDetails opportunityRecruitDetails : recruitList) {

				System.out.println("recruitmentId=" + opportunityRecruitDetails.getRecruitment_id());
				List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
						.getProfileDetailByRecruitmentIdAndonBoardInd(opportunityRecruitDetails.getRecruitment_id());
				if (opportunityRecruitDetails.isCloseInd() == false) {
					System.out.println("start::::::::::::2");
					int profileSize = profileList.size();
					int positionSize = (int) opportunityRecruitDetails.getNumber();
					System.out.println("profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
					if (profileSize < positionSize) {
						openRecruitment++;
						openPosition = positionSize - profileSize;
						System.out.println("openPosition=============inner===" + openPosition);
					}
					if (recruitList.size() > 1) {
						openPosition += openPosition;
						System.out.println("openPosition=============outer===" + openPosition);
					}
				}
			}
			System.out.println("openRecruitment=============outer===" + openRecruitment);
			map.put("openRecruitment", openRecruitment);
			map.put("openPosition", openPosition);
		}
		return map;
	}

	@Override
	public OpportunityViewMapper updateOpportunityReinstateIndOnlyTrue(String opportunityId) {

		OpportunityDetails opportunityDetails = opportunityDetailsRepository.getopportunityDetailsById(opportunityId);

		OpportunityViewMapper resultMapper = new OpportunityViewMapper();

		if (opportunityDetails != null) {

			opportunityDetails.setLostInd(false);
			opportunityDetails.setCloseInd(false);
			opportunityDetails.setWonInd(false);
			opportunityDetails.setLiveInd(true);
			opportunityDetails.setReinstateInd(false);
			opportunityDetailsRepository.save(opportunityDetails);

		}
		resultMapper = getOpportunityDetails(opportunityDetails.getOpportunityId());
		return resultMapper;
	}

	@Override
	public OpportunityViewMapper updateOpportunityCloseIndOnlyTrue(String opportunityId) {

		OpportunityDetails opportunityDetails = opportunityDetailsRepository.getopportunityDetailsById(opportunityId);

		OpportunityViewMapper resultMapper = new OpportunityViewMapper();

		if (opportunityDetails != null) {

			opportunityDetails.setCloseInd(true);
			opportunityDetails.setCloseDate(new Date());
			opportunityDetails.setLiveInd(true);
			opportunityDetails.setLostInd(false);
			opportunityDetails.setReinstateInd(true);
			opportunityDetails.setWonInd(false);
			opportunityDetailsRepository.save(opportunityDetails);

		}
		resultMapper = getOpportunityDetails(opportunityDetails.getOpportunityId());
		return resultMapper;
	}

	@Scheduled(cron = "0 0 0 * * *")
	private void closeOpenOpportunity() throws Exception {
		System.out.println("started......closeOpenOpportunity");
		List<RecruitmentCloseRule> ruleList = recruitmentCloseRuleRepository.findAll();
		for (RecruitmentCloseRule opportunityCloseRule : ruleList) {
			List<OpportunityDetails> oppList = opportunityDetailsRepository
					.findByOrgIdAndReinstateIndAndLiveIndAndCloseInd(opportunityCloseRule.getOrgId(), true, true,
							false);

			for (OpportunityDetails opportunityDetails : oppList) {
				if (opportunityCloseRule.getOppTimePeriod() != 0) {
					Date todayDate = new Date();
					if (null != opportunityDetails.getStartDate()) {
						Date closeDate = Utility.getPlusMonth(opportunityDetails.getStartDate(),
								opportunityCloseRule.getOppTimePeriod());
						System.out.println(
								"oppid===============================" + opportunityDetails.getOpportunityId());
						if (closeDate.compareTo(todayDate) == 0 || closeDate.compareTo(todayDate) > 0) {
							opportunityDetails.setCloseInd(true);
							opportunityDetails.setCloseDate(new Date());
							opportunityDetailsRepository.save(opportunityDetails);
						}
					}
				}
			}
		}
	}

	@Override
	public OpportunityViewMapper updateStage(OpportunityViewMapper opportunityMapper) {

		OpportunityViewMapper resultMapper = new OpportunityViewMapper();
		System.out
				.println("opportunityMapper.getOpportunityId()================" + opportunityMapper.getOpportunityId());
		System.out.println(
				"opportunityMapper.getOppStage()================" + opportunityMapper.getOpportunityStagesId());
		OpportunityDetails opportunityDetails = opportunityDetailsRepository
				.getOpportunityDetailsByOpportunityId(opportunityMapper.getOpportunityId());
		System.out.println("opportunityDetails================" + opportunityDetails.toString());
		if (null != opportunityDetails) {
			System.out.println(
					"opportunityMapper.getOpportunityId()--1================" + opportunityMapper.getOpportunityId());
			System.out.println(
					"opportunityMapper.getOppStage()--1================" + opportunityMapper.getOpportunityStagesId());
			opportunityDetails.setOppStage(opportunityMapper.getOpportunityStagesId());

			opportunityDetailsRepository.save(opportunityDetails);

			String userCurrency = null;
			String orgCurrency = null;
			double probability = 0;
			double previousProbability1 = 0;
			EmployeeDetails emp1 = employeeRepository.getEmployeesByuserId(opportunityDetails.getUserId());
			if (null != emp1) {
				userCurrency = emp1.getCurrency();
			}
			OrganizationDetails organizationDetails = organizationRepository
					.getOrganizationDetailsById(opportunityDetails.getOrgId());
			if (null != organizationDetails.getTrade_currency()) {
				orgCurrency = organizationDetails.getTrade_currency();
			}
			OpportunityStages oppStages = opportunityStagesRepository
					.getOpportunityStagesByOpportunityStagesId(opportunityMapper.getOppStage());
			if (null != oppStages) {
				probability = oppStages.getProbability();
			}

			OpportunityStages oppStages1 = opportunityStagesRepository
					.getOpportunityStagesByOpportunityStagesId(opportunityDetails.getOppStage());

			if (null != oppStages1) {
				previousProbability1 = oppStages1.getProbability();
			}

			ConversionValueMapper mapper = ConvertOppertunityProposalValueAndWeightedValue(
					opportunityDetails.getProposalAmount(), userCurrency, orgCurrency, opportunityDetails.getCurrency(),
					probability);
			ConversionValueMapper previous = ConvertOppertunityProposalValueAndWeightedValue(
					opportunityDetails.getProposalAmount(), userCurrency, orgCurrency, opportunityDetails.getCurrency(),
					previousProbability1);

			if (null != previous) {
				System.out.println("previos opportunityDetails.getCustomerId()" + opportunityDetails.getCustomerId());
				CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
						.findByCustomerId(opportunityDetails.getCustomerId());
				if (null != cuOppConversionValue) {
					double userpValue = (cuOppConversionValue.getUserConversionValue()
							- previous.getUserConversionPValue());
					cuOppConversionValue.setUserConversionValue(userpValue);
					double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
							- previous.getUserConversionWValue());
					cuOppConversionValue.setUserConversionWeightedValue(userwValue);
					double orgpValue = (cuOppConversionValue.getOrgConversionValue()
							- previous.getOrgConversionPValue());
					cuOppConversionValue.setOrgConversionValue(orgpValue);
					double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
							- previous.getOrgConversionWValue());
					cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
					cuOppConversionValueRepo.save(cuOppConversionValue);
				}
			}

			if (null != mapper) {
				System.out.println("new opportunityDetails.getCustomerId()" + opportunityDetails.getCustomerId());
				CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo
						.findByCustomerId(opportunityDetails.getCustomerId());
				if (null != cuOppConversionValue) {
					double userpValue = (cuOppConversionValue.getUserConversionValue()
							+ mapper.getUserConversionPValue());

					cuOppConversionValue.setUserConversionValue(userpValue);
					double userwValue = (cuOppConversionValue.getUserConversionWeightedValue()
							+ mapper.getUserConversionWValue());
					cuOppConversionValue.setUserConversionWeightedValue(userwValue);
					double orgpValue = (cuOppConversionValue.getOrgConversionValue() + mapper.getOrgConversionPValue());
					cuOppConversionValue.setOrgConversionValue(orgpValue);
					double orgwValue = (cuOppConversionValue.getOrgConversionWeightedValue()
							+ mapper.getOrgConversionWValue());
					cuOppConversionValue.setOrgConversionWeightedValue(orgwValue);
					cuOppConversionValueRepo.save(cuOppConversionValue);
				}
			}

		}

		resultMapper = getOpportunityDetails(opportunityMapper.getOpportunityId());

		return resultMapper;
	}

	@Override
	public Map<String, List<OpportunityForecastLinkMapper>> getforecastByOrgrIdMonthWise(String orgId) {
		int forecastCount = 0;
		List<OpportunityForecastLinkMapper> list = new ArrayList<>();
		List<OpportunityForecastLink> opportunityForecastLink = opportunityForecastLinkRepository
				.getForecastListByOrgId(orgId);
		if (null != opportunityForecastLink && !opportunityForecastLink.isEmpty()) {
			for (OpportunityForecastLink skillLink : opportunityForecastLink) {

				forecastCount++;
				OpportunityForecastLinkMapper opportunityForecastLinkMapper = new OpportunityForecastLinkMapper();

				opportunityForecastLinkMapper.setNoOfPosition(skillLink.getNoOfPosition());
				opportunityForecastLinkMapper.setOpportunityId(skillLink.getOpportunityId());
				opportunityForecastLinkMapper.setOpportunityForecastLinkId(skillLink.getOpportunityForecastLinkId());
				opportunityForecastLinkMapper.setOrgId(skillLink.getOrgId());
				opportunityForecastLinkMapper.setUserId(skillLink.getUserId());
				opportunityForecastLinkMapper.setMonth(skillLink.getMonth());
				opportunityForecastLinkMapper.setYear(skillLink.getYear());
				opportunityForecastLinkMapper.setCreationDate(Utility.getISOFromDate(skillLink.getCreationDate()));
				DefinationDetails definationDetails = definationRepository.findByDefinationId(skillLink.getSkill());
				if (null != definationDetails) {
					opportunityForecastLinkMapper.setSkill(definationDetails.getDefinationId());
					opportunityForecastLinkMapper.setSkillName(definationDetails.getName());
				}

				list.add(opportunityForecastLinkMapper);

			}
		}

		System.out.println("forecastCount=================" + forecastCount);

		Map<String, List<OpportunityForecastLinkMapper>> forecast = new HashMap<>();

		for (OpportunityForecastLinkMapper p : list) {
			if (!forecast.containsKey(p.getMonth())) {
				forecast.put(p.getMonth(), new ArrayList<>());
				forecast.get(p.getMonth()).add(p);
			} else {
				boolean flag = false;
				List<OpportunityForecastLinkMapper> temp = forecast.get(p.getMonth());
				for (OpportunityForecastLinkMapper t : temp) {
					if (t.getSkillName().equalsIgnoreCase(p.getSkillName())) {
						flag = true;
						int num = Integer.parseInt(t.getNoOfPosition()) + Integer.parseInt(p.getNoOfPosition());
						t.setNoOfPosition(Integer.toString(num));
					}
				}
				if (flag == false) {
					forecast.get(p.getMonth()).add(p);
				}
			}
		}

		return forecast;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityDetailsListByUserId(String userId) {
		// Pageable paging = PageRequest.of(pageNo, pageSize,
		// Sort.by("creationDate").descending());
		List<OpportunityViewMapper> opportunities = new ArrayList<>();
		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getOpportunityListByUserIdAndLiveIndAndReinstateIndAndCloseIndAndLostInd(userId);

		if (null != opportunityList && !opportunityList.isEmpty()) {

			opportunities = opportunityList.stream().filter(li -> (li != null))
					.map(li -> getOpportunityRelatedDetails(li)).collect(Collectors.toList());
		}

		return opportunities;
	}

	@Override
	public List<OpportunityViewMapper> getAllOpportunityListCount() {
		List<OpportunityViewMapper> resultMapper = new ArrayList<OpportunityViewMapper>();

		List<Permission> permission = permissionRepository.getUserListForOpportunity();
		System.out.println(" user$$$$$$$$$$$$==" + permission.toString());

		if (null != permission && !permission.isEmpty()) {
			permission.stream().map(permissionn -> {

				List<OpportunityViewMapper> mp = OpportunityService
						.getOpportunityDetailsListByUserId(permissionn.getUserId());

				System.out.println(" userId$$$$$$$$$$$$==" + permissionn.getUserId());

				resultMapper.addAll(mp);
				return resultMapper;
			}).filter(l -> l != null).flatMap(l -> l.stream()).collect(Collectors.toList());

		}
		return resultMapper;

	}

	@Override
	public HashMap getOpportunityContactCountByCustomerId(String opportunityId) {
		List<OpportunityContactLink> opportunityContactList = opportunityContactLinkRepository
				.getContactByOpportunityId(opportunityId);
		HashMap map = new HashMap();
		int count = 0;
		for (OpportunityContactLink opportunityContact : opportunityContactList) {
			ContactDetails contact = contactRepository.getcontactDetailsById(opportunityContact.getContactId());
			if (null != contact) {
				count++;

			}

		}
		map.put("OpportunityContactDetails", count);

		return map;
	}

	@Override
	public HashMap getOpportunityDocumentCountByCustomerId(String opportunityId) {

		List<OpportunityDocumentLink> opportunityDocumentLinkList = opportunityDocumentLinkRepository
				.getDocumentByOpportunityId(opportunityId);
		Set<String> documentIds = opportunityDocumentLinkList.stream().map(OpportunityDocumentLink::getDocument_id)
				.collect(Collectors.toSet());
		int count = 0;
		HashMap map = new HashMap();
		for (String documentId : documentIds) {
			DocumentDetails doc = documentDetailsRepository.getDocumentDetailsById(documentId);
			if (null != doc) {
				count++;
			}

		}

		map.put("OpportunityDocumentDetails", count);
		return map;

	}

	@Override
	public HashMap getCloseOpportunityListByUserIdAndDateRange(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getClosedOpportunityDetailsUserIdWithDateRange(userId, start_date, end_date);
		HashMap map = new HashMap();
		map.put("closedOpportunity", opportunityDetailsList.size());

		return map;
	}

	@Override
	public HashMap getAddedOpportunityListByUserIdAndDateRange(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getAddedOpportunityDetailsUserIdWithDateRange(userId, start_date, end_date);
		HashMap map = new HashMap();
		map.put("opportunityAdded", opportunityDetailsList.size());

		return map;
	}

	@Override
	public List<OpportunityViewMapper> getClosedOpportunitiesByUserIdDateRange(String userId, String startDate,
			String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getClosedOpportunityDetailsUserIdWithDateRange(userId, start_date, end_date);
		return opportunityDetailsList.stream().map(this::getOpportunityRelatedDetails).collect(Collectors.toList());
	}

	@Override
	public List<OpportunityViewMapper> getAddedOpportunitiesByUserIdDateRange(String userId, String startDate,
			String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getAddedOpportunityDetailsUserIdWithDateRange(userId, start_date, end_date);
		return opportunityDetailsList.stream().map(this::getOpportunityRelatedDetails).collect(Collectors.toList());
	}

	@Override
	public List<OpportunityDropdownMapper> getDropDownOpportunityList(String userId) {
		List<OpportunityDropdownMapper> opportunities = new ArrayList<>();

		List<OpportunitySalesUserLink> opportunitySalesLink = opportunitySalesUserRepository
				.getSalesUserLinkByUserId(userId);
		opportunitySalesLink.sort(Comparator.comparing(OpportunitySalesUserLink::getCreationDate));
		if (null != opportunitySalesLink && !opportunitySalesLink.isEmpty()) {
			opportunitySalesLink.stream().filter(li -> (li != null)).map(li -> {
				OpportunityDetails opportunity = opportunityDetailsRepository
						.getOpenOpportunityDetailsByOpportunityId(li.getOpportunity_id());
				if (null != opportunity) {
					OpportunityDropdownMapper mapper = new OpportunityDropdownMapper();
					mapper.setOpportunityId(opportunity.getOpportunityId());
					mapper.setCustomerId(opportunity.getCustomerId());
					mapper.setOpportunityName(opportunity.getOpportunityName());
					if (null != mapper) {
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
	public NotesMapper updateNoteDetails(NotesMapper notesMapper) {

		NotesMapper resultMapper = employeeService.updateNotes(notesMapper);

		return resultMapper;
	}

	@Override
	public void deleteCustomerNotesById(String notesId) {
		OpportunityNotesLink notesList = opportunityNotesLinkRepository.findByNotesId(notesId);
		if (null != notesList) {

			Notes notes = notesRepository.findByNoteId(notesId);
			if (null != notes) {
				notes.setLiveInd(false);
				notesRepository.save(notes);
			}
		}
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityDetailsListPageWiseByOrgId(String orgId, int pageNo,
			int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		List<OpportunityViewMapper> opportunities = new ArrayList<>();
		Page<OpportunityDetails> listLink = opportunityDetailsRepository.getOpportunityDetailsListByOrgId(orgId,
				paging);
		if (null != listLink && !listLink.isEmpty()) {

			listLink.stream().filter(li -> (li != null)).map(li -> {
				OpportunityViewMapper mapper = getOpportunityRelatedDetails(li);
				mapper.setPageCount(listLink.getTotalPages());
				mapper.setDataCount(listLink.getSize());
				mapper.setListCount(listLink.getTotalElements());
				opportunities.add(mapper);
				return mapper;
			}).collect(Collectors.toList());
		}
		return opportunities;
	}

	@Override
	public HashMap getOpportunityListCountByOrgId(String orgId) {
		List<OpportunityDetails> list = opportunityDetailsRepository.getOpportunityListByOrgId(orgId);
		HashMap map = new HashMap();
		map.put("opportunity", list.size());

		return map;
	}

	@Override
	public Set<OpportunityViewMapper> getTeamOpportunityDetailsByUserId(String userId, int pageNo, int pageSize,
			String filter) {
		Pageable paging = null;
		Team team = teamRepository.findByTeamLeadAndLiveInd(userId, true);

		List<String> userIds = teamMemberLinkRepo.findByTeamId(team.getTeamId()).stream()
				.map(TeamMemberLink::getTeamMemberId).collect(Collectors.toList());

		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
		}
		Page<OpportunityDetails> leadsPage = opportunityDetailsRepository.getTeamOppListByUserIdsPaginated(userIds,
				paging);

		Set<OpportunityViewMapper> mapperSet = new HashSet<>();

		if (leadsPage != null && !leadsPage.isEmpty()) {
			mapperSet = leadsPage.getContent().stream().map(li -> getOpportunityRelatedDetails(li))
					.collect(Collectors.toSet());
		}
		return mapperSet;
	}

	@Override
	public HashMap getTeamOppertunityContactCountByUserId(String userId) {
		HashMap map = new HashMap();
		Team team = teamRepository.findByTeamLeadAndLiveInd(userId, true);

		List<String> userIds = teamMemberLinkRepo.findByTeamId(team.getTeamId()).stream()
				.map(TeamMemberLink::getTeamMemberId).collect(Collectors.toList());
		List<OpportunityDetails> oppList = opportunityDetailsRepository.getTeamOppListByUserIds(userIds);
		map.put("OpportunityTeam", oppList.size());

		return map;
	}

	@Override
	public DocumentMapper updateContractIndForDocument(DocumentMapper documentMapperr) {
		OpportunityDocumentLink opportunityDocumentLink = opportunityDocumentLinkRepository
				.getDocumentByDocumentId(documentMapperr.getDocumentId());
		DocumentMapper documentMapper = new DocumentMapper();
		if (null != opportunityDocumentLink) {
			opportunityDocumentLink.setContractInd(documentMapperr.isContractInd());
			opportunityDocumentLinkRepository.save(opportunityDocumentLink);

			documentMapper = documentService.getDocument(opportunityDocumentLink.getDocument_id());

			if (null != documentMapper.getDocumentId()) {
				documentMapper.setContractInd(opportunityDocumentLink.isContractInd());
			}
		}
		return documentMapper;
	}

	@Override
	public List<OpportunityOrderViewMapper> saveOpportunityOrder(OpportunityOrderMapper opportunityMapper) {
		List<OpportunityOrderViewMapper> resultMapper = new ArrayList<>();
		List<String> orderIds = opportunityMapper.getOrderIds();
		if (orderIds != null && !orderIds.isEmpty()) {
			for (String orderId : orderIds) {
				OpportunityOrderLink opportunityOrderLink = new OpportunityOrderLink();
				opportunityOrderLink.setCreationDate(new Date());
				opportunityOrderLink.setLiveInd(true);
				opportunityOrderLink.setOrgId(opportunityMapper.getOrgId());
				opportunityOrderLink.setUserId(opportunityMapper.getUserId());
				opportunityOrderLink.setOpportunityId(opportunityMapper.getOpportunityId());
				opportunityOrderLink.setOrderId(orderId);
				opportunityOrderLink.setUpdationDate(new Date());
				opportunityOrderLink.setUpdatedBy(opportunityMapper.getUserId());

				opportunityOrderRepository.save(opportunityOrderLink);
				resultMapper.add(getOpportunityOrderById(opportunityOrderLink.getOpportunityOrderLinkId(),
						opportunityOrderLink.getOpportunityId()));
			}
		}
		return resultMapper;

	}

	public OpportunityOrderViewMapper getOpportunityOrderById(String opportunityOrderId, String opportunityId) {
		OpportunityOrderLink db = opportunityOrderRepository.getById(opportunityOrderId);
		OpportunityOrderViewMapper resultMapper = new OpportunityOrderViewMapper();
		if (null != db) {
			resultMapper.setOpportunityId(db.getOpportunityId());
			resultMapper.setCreationDate(Utility.getISOFromDate(db.getCreationDate()));
			resultMapper.setOrderId(db.getOrderId());
			resultMapper.setOrgId(db.getOrgId());
			resultMapper.setUserId(db.getUserId());
			resultMapper.setLiveInd(db.isLiveInd());
			resultMapper.setOpportunityOrderLinkId(db.getOpportunityOrderLinkId());
			resultMapper.setUpdatedBy(employeeService.getEmployeeFullName(db.getUserId()));
			resultMapper.setUpdationDate(Utility.getISOFromDate(db.getUpdationDate()));
		}

		return resultMapper;

	}

	@Override
	public List<OpportunityOrderViewMapper> getOpportunityOrderListByOpportunityId(String opportunityId) {
		List<OpportunityOrderViewMapper> resultList = new ArrayList<>();
		List<OpportunityOrderLink> list = opportunityOrderRepository.findByOpportunityId(opportunityId);
		if (null != list && !list.isEmpty()) {
			list.stream().map(order -> {
				OpportunityOrderViewMapper mapper = getOpportunityOrderById(order.getOpportunityOrderLinkId(),
						order.getOpportunityId());
				if (null != mapper.getOrderId()) {
					if (null != mapper.getOrderId()) {
						resultList.add(mapper);
					}
				}
				return mapper;
			}).collect(Collectors.toList());
		}

		return resultList;
	}

	@Override
	public String deleteOpportunityOrder(String opportunityOrderId) {
		String msg = null;
		OpportunityOrderLink opportunityOrderLink = opportunityOrderRepository.getById(opportunityOrderId);
		if (null != opportunityOrderLink) {
			opportunityOrderLink.setLiveInd(false);
			opportunityOrderLink.setUpdationDate(new Date());
			opportunityOrderRepository.save(opportunityOrderLink);
			msg = "This Is Deleted Successfully";
		}
		return msg;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityDetailsListPageWiseByIncludedUserId(String userId, int pageNo,
			int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "creationDate"));
		List<OpportunityViewMapper> opportunities = new ArrayList<>();
		Page<OpportunityIncludedLink> opportunityIncludedLink = opportunityIncludedRepository
				.getOpportunityIncludedLinkByUserIdWithPagination(userId, paging);
		if (null != opportunityIncludedLink && !opportunityIncludedLink.isEmpty()) {
			opportunityIncludedLink.stream().filter(li -> (li != null)).map(li -> {
				OpportunityDetails opportunity = opportunityDetailsRepository
						.getOpportunityDetailsByOpportunityId(li.getOpportunityId());
				if (null != opportunity) {
					OpportunityViewMapper mapper = getOpportunityRelatedDetails(opportunity);
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
		List<OpportunityIncludedLink> opportunityIncludedLinkList = opportunityIncludedRepository
				.getOpportunityIncludedLinkByUserId(userId);
		HashMap map = new HashMap();
		map.put("OpportunityCount", opportunityIncludedLinkList.size());
		return map;
	}

	@Override
	public HashMap getActionRequiredRecordByToday(String userId) {
		// Millseconds in a day
		final long ONE_DAY_MILLI_SECONDS = (24 * 60 * 60 * 1000) - 1;
		int count = 0;
		// date format
		LocalDateTime now = LocalDateTime.now();
		Date currDate = new GregorianCalendar(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth()).getTime();
		long nextDayMilliSeconds = currDate.getTime() + ONE_DAY_MILLI_SECONDS;
		Date nextDate = new Date(nextDayMilliSeconds);
		HashMap map = new HashMap();

		List<OpportunityIncludedLink> opportunityIncludedLinkList = opportunityIncludedRepository
				.findByEmployeeIdAndCreationDateBetweenAndLiveInd(userId, currDate, nextDate, true);
		System.out.println("Opportunity size" + opportunityIncludedLinkList.size());
		if (null != opportunityIncludedLinkList) {
			count = count + opportunityIncludedLinkList.size();
		}

		List<InvestorOppIncludedLink> investorOppIncludedLinkList = investorOppIncludedRepository
				.findByEmployeeIdAndCreationDateBetweenAndLiveInd(userId, currDate, nextDate, true);
		System.out.println("InvestorOpp  size" + investorOppIncludedLinkList.size());
		if (null != investorOppIncludedLinkList) {
			count = count + investorOppIncludedLinkList.size();
		}

		List<TaskIncludedLink> taskIncludedLinkList = taskIncludedRepository
				.findByEmployeeIdAndCreationDateBetweenAndLiveInd(userId, currDate, nextDate, true);
		System.out.println("Task  size" + taskIncludedLinkList.size());
		if (null != taskIncludedLinkList) {
			count = count + taskIncludedLinkList.size();
		}

		map.put("ActionRecordCount", count);

		return map;
	}

	@Override
	public ConversionValueMapper ConvertOppertunityProposalValueAndWeightedValue(String proposalValue,
			String userCurrency, String orgCurrency, String oppCurrency, double probability) {
		ConversionValueMapper mapper = new ConversionValueMapper();

//		FOR USER LEVEL
		if (null != userCurrency) {
			int pAmount = 0;
			double wValue = 0;
			double totalWValue = 0;
			double totalPValue = 0;
			if (null != oppCurrency) {
				if (userCurrency.equalsIgnoreCase(oppCurrency)) {

					pAmount = Integer.parseInt(proposalValue);
					System.out.println("Opp currency in user 0: " + oppCurrency);
					System.out.println("user currency in user 0: " + userCurrency);
					System.out.println("ProposalAmount in user 0: " + pAmount);

					if (0 != probability) {
						wValue = ((probability) / 100);
						System.out.println("wValue in user 0: " + wValue);
						totalWValue = pAmount * wValue;
						System.out.println("totalPValue in user 0: " + totalPValue);
					}

					totalPValue = pAmount;
					System.out.println("totalPValue in user 0: " + totalPValue);
					mapper.setUserConversionCurrency(userCurrency);
					mapper.setUserConversionPValue(totalPValue);
					mapper.setUserConversionWValue(totalWValue);
					System.out.println("in user currency == opp currency");
				} else {

					CurrencyConversion currencyConversion = currencyConversionRepository
							.findByReportingCurrencyAndConversionCurrencyAndLiveInd(userCurrency, oppCurrency, true);
					if (null != currencyConversion) {
						pAmount = Integer.parseInt(proposalValue);
						System.out.println("Trade currency in user 1: " + userCurrency);
						System.out.println("Conversion currency in user 1: " + oppCurrency);
						System.out.println("ProposalAmount in user 1: " + pAmount);

						if (0 != probability) {
							wValue = ((probability) / 100);
							System.out.println("wValue in user 1: " + wValue);
							double total = pAmount * wValue;
							System.out.println("total in user 1: " + total);
							totalWValue = (int) ((total) / currencyConversion.getConversionFactor());
							System.out.println("totalWValue in user 1: " + totalWValue);
						}

						totalPValue = (int) (pAmount / currencyConversion.getConversionFactor());
						System.out.println("totalPValue in user 1: " + totalPValue);
						mapper.setUserConversionCurrency(userCurrency);
						mapper.setUserConversionPValue(totalPValue);
						mapper.setUserConversionWValue(totalWValue);

						System.out.println("in user 0");
					} else {
						CurrencyConversion currencyConversion1 = currencyConversionRepository
								.findByReportingCurrencyAndConversionCurrencyAndLiveInd(oppCurrency, userCurrency,
										true);
						if (null != currencyConversion1) {
							pAmount = Integer.parseInt(proposalValue);
							System.out.println("Trade currency in user 2: " + oppCurrency);
							System.out.println("Conversion currency in user 2: " + userCurrency);
							System.out.println("ProposalAmount in user 2: " + pAmount);

							if (0 != probability) {
								wValue = ((probability) / 100);
								System.out.println("wValue in user 1: " + wValue);
								double total = pAmount * wValue;
								System.out.println("total in user 1: " + total);
								totalWValue = (int) ((total) / currencyConversion1.getConversionFactor());
								System.out.println("totalWValue in user 1: " + totalWValue);
							}
							totalPValue = (int) (pAmount / currencyConversion1.getConversionFactor());
							System.out.println("totalPValue in user 2: " + totalPValue);
							System.out.println("in user 1");
							mapper.setUserConversionCurrency(userCurrency);
							mapper.setUserConversionPValue(totalPValue);
							mapper.setUserConversionWValue(totalWValue);
						} else {
							System.out.println("in user 2");
							mapper.setMessage("No Conversion Available from" + userCurrency + "to" + oppCurrency);
						}
					}
				}
			}
		}

//		FOR ORGANIZATION LEVEL
		if (null != orgCurrency) {
			int pAmount = 0;
			double wValue = 0;
			double totalWValue = 0;
			double totalPValue = 0;
			if (null != oppCurrency) {
				if (orgCurrency.equalsIgnoreCase(oppCurrency)) {
					pAmount = Integer.parseInt(proposalValue);
					System.out.println("Opp currency in org 0: " + oppCurrency);
					System.out.println("user currency in org 0: " + orgCurrency);
					System.out.println("ProposalAmount in org 0: " + pAmount);

					if (0 != probability) {
						wValue = ((probability) / 100);
						System.out.println("wValue in org 0: " + wValue);
						totalWValue = pAmount * wValue;
						System.out.println("totalPValue in org 0: " + totalPValue);
					}

					totalPValue = pAmount;
					System.out.println("totalPValue in org 0: " + totalPValue);
					mapper.setOrgConversionCurrency(orgCurrency);
					mapper.setOrgConversionPValue(totalPValue);
					mapper.setOrgConversionWValue(totalWValue);
					System.out.println("in org currency == opp currency");
				} else {

					CurrencyConversion currencyConversion = currencyConversionRepository
							.findByReportingCurrencyAndConversionCurrencyAndLiveInd(orgCurrency, oppCurrency, true);
					if (null != currencyConversion) {
						pAmount = Integer.parseInt(proposalValue);
						System.out.println("Trade currency in org 1: " + orgCurrency);
						System.out.println("Conversion currency in org 1: " + oppCurrency);
						System.out.println("ProposalAmount in org 1: " + pAmount);

						if (0 != probability) {
							wValue = ((probability) / 100);
							System.out.println("wValue in org 1: " + wValue);
							double total = pAmount * wValue;
							System.out.println("total in user 1: " + total);
							totalWValue = (int) ((total) / currencyConversion.getConversionFactor());
							System.out.println("totalWValue in org 1: " + totalWValue);
						}

						totalPValue = (int) (pAmount / currencyConversion.getConversionFactor());
						System.out.println("totalPValue in org 1: " + totalPValue);
						mapper.setOrgConversionCurrency(orgCurrency);
						mapper.setOrgConversionPValue(totalPValue);
						mapper.setOrgConversionWValue(totalWValue);

						System.out.println("in org 0");
					} else {
						CurrencyConversion currencyConversion1 = currencyConversionRepository
								.findByReportingCurrencyAndConversionCurrencyAndLiveInd(oppCurrency, orgCurrency, true);
						if (null != currencyConversion1) {
							pAmount = Integer.parseInt(proposalValue);
							System.out.println("Trade currency in org 2: " + oppCurrency);
							System.out.println("Conversion currency in org 2: " + orgCurrency);
							System.out.println("ProposalAmount in org 2: " + pAmount);

							if (0 != probability) {
								wValue = ((probability) / 100);
								System.out.println("wValue in org 1: " + wValue);
								double total = pAmount * wValue;
								System.out.println("total in org 1: " + total);
								totalWValue = (int) ((total) / currencyConversion1.getConversionFactor());
								System.out.println("totalWValue in org 1: " + totalWValue);
							}
							totalPValue = (int) (pAmount / currencyConversion1.getConversionFactor());
							System.out.println("totalPValue in org 2: " + totalPValue);
							System.out.println("in user 1");
							mapper.setOrgConversionCurrency(orgCurrency);
							mapper.setOrgConversionPValue(totalPValue);
							mapper.setOrgConversionWValue(totalWValue);
						} else {
							System.out.println("in org 2");
							mapper.setMessage("No Conversion Available from" + orgCurrency + "to" + oppCurrency);
						}
					}
				}
			}
		}

		return mapper;
	}

	public OpportunityReportMapper getOpportunityDetailsforReport(OpportunityDetails opportunityDetails) {
		int openRecruitment = 0;
		int openPosition = 0;
		OpportunityReportMapper oppertunityMapper = new OpportunityReportMapper();

		if (null != opportunityDetails.getOpportunityId()) {

			oppertunityMapper.setOpportunityId(opportunityDetails.getOpportunityId());
			oppertunityMapper.setOpportunityName(opportunityDetails.getOpportunityName());

			oppertunityMapper.setProposalAmount(opportunityDetails.getProposalAmount());
			oppertunityMapper.setCurrency(opportunityDetails.getCurrency());
			oppertunityMapper.setUserId(opportunityDetails.getUserId());
			oppertunityMapper.setOrgId(opportunityDetails.getOrgId());
			oppertunityMapper.setStartDate(Utility.getISOFromDate(opportunityDetails.getStartDate()));
			oppertunityMapper.setEndDate(Utility.getISOFromDate(opportunityDetails.getEndDate()));
			oppertunityMapper.setDescription(opportunityDetails.getDescription());
			oppertunityMapper.setOppInnitiative(opportunityDetails.getOppInnitiative());
			oppertunityMapper.setWonInd(opportunityDetails.isWonInd());
			oppertunityMapper.setLostInd(opportunityDetails.isLostInd());
			oppertunityMapper.setCloseInd(opportunityDetails.isCloseInd());
			if (null != opportunityDetails.getCreationDate()) {

				oppertunityMapper.setCreationDate(Utility.getISOFromDate(opportunityDetails.getCreationDate()));

			}

			List<OpportunityRecruiterLink> list = opportunityRecruiterLinkRepository
					.getRecruiterLinkByOppId(opportunityDetails.getOpportunityId());
			if (null != list && !list.isEmpty()) {
				List<RecruiterMapper> recruiterList = new ArrayList<RecruiterMapper>();
				for (OpportunityRecruiterLink opportunityRecruiterLink : list) {

					RecruiterMapper recriuterMapper = new RecruiterMapper();
					EmployeeDetails employeeDetails = employeeRepository
							.getEmployeeDetailsByEmployeeId(opportunityRecruiterLink.getRecruiter_id());
					if (null != employeeDetails) {
						String middleName = " ";
						String lastName = "";

						if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

							lastName = employeeDetails.getLastName();
						}
						if (!StringUtils.isEmpty(employeeDetails.getMiddleName())) {
							middleName = employeeDetails.getMiddleName();

						}
						recriuterMapper.setFullName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
						recriuterMapper.setEmployeeId(employeeDetails.getEmployeeId());
						recriuterMapper.setImageId(employeeDetails.getImageId());
					}
					recruiterList.add(recriuterMapper);
				}
				oppertunityMapper.setRecruiterDetails(recruiterList);

			}
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeDetailsByEmployeeId(opportunityDetails.getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}
				if (!StringUtils.isEmpty(employeeDetails.getMiddleName())) {
					middleName = employeeDetails.getMiddleName();

				}
				oppertunityMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				oppertunityMapper.setOwnerImageId(employeeDetails.getImageId());
			}

			System.out.println("till employeee detail set");
			if (opportunityDetails.getCustomerId() != null && opportunityDetails.getCustomerId().trim().length() > 0) {
				Customer customer = customerRepository.getCustomerByIdAndLiveInd(opportunityDetails.getCustomerId());
				if (null != customer) {
					oppertunityMapper.setCustomerId(customer.getCustomerId());
					oppertunityMapper.setCustomer(customer.getName());
				}
			} else {
				oppertunityMapper.setCustomerId("");

			}
			if (opportunityDetails.getContactId() != null && opportunityDetails.getContactId().trim().length() > 0) {
				ContactDetails contact = contactRepository.getContactDetailsById(opportunityDetails.getContactId());
				if (null != contact) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(contact.getLast_name())) {

						lastName = contact.getLast_name();
					}
					if (!StringUtils.isEmpty(contact.getMiddle_name())) {
						middleName = contact.getMiddle_name();

					}
					oppertunityMapper.setContactId(contact.getContactId());
					oppertunityMapper.setContactName(contact.getFirst_name() + " " + middleName + " " + lastName);

				}
			}

			if (opportunityDetails.getOpportunityId() != null
					&& opportunityDetails.getOpportunityId().trim().length() > 0) {
				List<String> includedIds = opportunityIncludedRepository
						.findByOpportunityId(opportunityDetails.getOpportunityId()).stream()
						.map(OpportunityIncludedLink::getEmployeeId).collect(Collectors.toList());
				List<EmployeeShortMapper> included = new ArrayList<>();
				if (null != includedIds && !includedIds.isEmpty()) {
					for (String includedId : includedIds) {
						EmployeeShortMapper employeeMapper = employeeService
								.getEmployeeFullNameAndEmployeeId(includedId);
						included.add(employeeMapper);
					}
					oppertunityMapper.setIncluded(included);
				}

				List<String> opp = opportunitySalesUserRepository
						.getSalesUsersByOppId(opportunityDetails.getOpportunityId()).stream()
						.map(OpportunitySalesUserLink::getEmployee_id).collect(Collectors.toList());

				System.out.println("opp.size()===================" + opp.size());
				if (null != opp && !opp.isEmpty()) {
					opp.remove(opportunityDetails.getUserId());
					if (opp.size() > 0) {
						for (String empId : opp) {
//							if (opportunitySalesUserLink.getEmployee_id().equals(opportunityDetails.getUserId())) {
//								break;
//							}

							EmployeeDetails employeeDetailss = employeeRepository.getEmployeeDetailsByEmployeeId(empId);
							if (null != employeeDetailss) {
								oppertunityMapper.setSalesUserIds(empId);
								String middleName = " ";
								String lastName = "";

								if (!StringUtils.isEmpty(employeeDetailss.getLastName())) {

									lastName = employeeDetailss.getLastName();
								}
								if (!StringUtils.isEmpty(employeeDetailss.getMiddleName())) {
									middleName = employeeDetailss.getMiddleName();

								}
								oppertunityMapper.setAssignedTo(
										employeeDetailss.getFirstName() + " " + middleName + " " + lastName);
							}
						}
					} else {
						EmployeeDetails employeeDetailss = employeeRepository
								.getEmployeeDetailsByEmployeeId(opportunityDetails.getUserId());
						if (null != employeeDetailss) {
							oppertunityMapper.setSalesUserIds(opportunityDetails.getUserId());
							String middleName = " ";
							String lastName = "";

							if (!StringUtils.isEmpty(employeeDetailss.getLastName())) {

								lastName = employeeDetailss.getLastName();
							}
							if (!StringUtils.isEmpty(employeeDetailss.getMiddleName())) {
								middleName = employeeDetailss.getMiddleName();

							}
							oppertunityMapper
									.setAssignedTo(employeeDetailss.getFirstName() + " " + middleName + " " + lastName);
						}
					}
				}
			}

			OpportunityWorkflowDetails workflowDetails = opportunityWorkflowDetailsRepository
					.getOpportunityWorkflowDetailsByOpportunityWorkflowDetailsId(opportunityDetails.getOppWorkflow());
			if (null != workflowDetails) {
				oppertunityMapper.setOppWorkflow(workflowDetails.getWorkflowName());
			}

			OpportunityStages oppStages = opportunityStagesRepository
					.getOpportunityStagesByOpportunityStagesId(opportunityDetails.getOppStage());
			if (null != oppStages) {
				oppertunityMapper.setOppStage(oppStages.getStageName());
				oppertunityMapper.setProbability(oppStages.getProbability());
				oppertunityMapper.setOpportunityStagesId(oppStages.getOpportunityStagesId());
			}

			oppertunityMapper.setStageList(
					opportunityWorkflowService.getStagesByOppworkFlowId(opportunityDetails.getOppWorkflow()));

			List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
					.getOpenRecriutmentsByOpportunityIdAndCloseInd(opportunityDetails.getOpportunityId());
			if (null != recruitList && !recruitList.isEmpty()) {
				for (OpportunityRecruitDetails opportunityRecruitDetails : recruitList) {

					System.out.println("recruitmentId=" + opportunityRecruitDetails.getRecruitment_id());
					List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
							.getProfileDetailByRecruitmentIdAndonBoardInd(
									opportunityRecruitDetails.getRecruitment_id());
					if (opportunityRecruitDetails.isCloseInd() == false) {
						System.out.println("start::::::::::::2");
						int profileSize = profileList.size();
						int positionSize = (int) opportunityRecruitDetails.getNumber();
						System.out.println("profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
						if (profileSize < positionSize) {
							openRecruitment++;
							openPosition = positionSize - profileSize;
							System.out.println("openPosition=============inner===" + openPosition);
						}
						if (recruitList.size() > 1) {
							openPosition += openPosition;
							System.out.println("openPosition=============outer===" + openPosition);
						}
					}
				}
				System.out.println("openRecruitment=============outer===" + openRecruitment);

			}
			oppertunityMapper.setOpenRecruitment(openRecruitment);
			oppertunityMapper.setOpenPosition(openPosition);

//			List<OpportunitySkillLink> opportunitySkillLink = opportunitySkillLinkRepository
//	                .getSkillListByOpportunityId(opportunityDetails.getOpportunityId());
//	        List<OpportunitySkillLinkMapper> skillList = new ArrayList<OpportunitySkillLinkMapper>();
//	        
//	        if(null!=opportunitySkillLink && !opportunitySkillLink.isEmpty()) {
//	          for(OpportunitySkillLink opportunityDetailss:opportunitySkillLink)  {
//	             // OpportunitySkillLink opportunitySkillLink1=opportunitySkillLinkRepository. getOpportunitySkillLinkId( );
//	              OpportunitySkillLinkMapper opportunitySkillLinkMapper =new OpportunitySkillLinkMapper();
//	              opportunitySkillLinkMapper.setNoOfPosition(opportunityDetailss.getNoOfPosition());
//	              opportunitySkillLinkMapper.setOppInnitiative(opportunityDetailss.getOppInnitiative());
//	              opportunitySkillLinkMapper.setSkill(opportunityDetailss.getSkill());
//	              opportunitySkillLinkMapper.setOpportunityId(opportunityDetailss.getOpportunityId());
//	              opportunitySkillLinkMapper.setOpportunitySkillLinkId(opportunityDetailss.getOpportunitySkillLinkId());
//	              skillList.add(opportunitySkillLinkMapper);
//	              
//	          }  
//	          }
//	        oppertunityMapper.setOpportunitySkill(skillList);
		}
		return oppertunityMapper;
	}

	@Override
	public List<OpportunityReportMapper> getClosedOpportunitiesByOrgIdDateRangeforReport(String orgId, String startDate,
			String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getClosedOpportunityDetailsOrgIdWithDateRange(orgId, start_date, end_date);

		return opportunityDetailsList.stream().map(this::getOpportunityDetailsforReport).collect(Collectors.toList());
	}

	@Override
	public List<OpportunityReportMapper> getClosedOpportunitiesByUserIdDateRangeforReport(String userId,
			String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getClosedOpportunityDetailsuserIdWithDateRange(userId, start_date, end_date);
		return opportunityDetailsList.stream().map(this::getOpportunityDetailsforReport).collect(Collectors.toList());
	}

	@Override
	public List<OpportunityReportMapper> getOpenOpportunitiesByOrgIdDateRangeforReport(String orgId, String startDate,
			String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getOpenOpportunityDetailsByOrgIdWithDateRange(orgId, start_date, end_date);
		return opportunityDetailsList.stream().map(this::getOpportunityDetailsforReport).collect(Collectors.toList());
	}

	@Override
	public List<OpportunityReportMapper> getOpenOpportunitiesByUserIdDateRangeforReport(String userId, String startDate,
			String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getOpenOpportunityDetailsByUserIdWithDateRange(userId, start_date, end_date);
		return opportunityDetailsList.stream().map(this::getOpportunityDetailsforReport).collect(Collectors.toList());
	}

	@Override
	public List<OpportunityReportMapper> getOpportunitiesByOrgIdDateRangeforReport(String orgId, String startDate,
			String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getOpportunityDetailsByOrgIdWithDateRange(orgId, start_date, end_date);
		return opportunityDetailsList.stream().map(this::getOpportunityDetailsforReport).collect(Collectors.toList());
	}

	@Override
	public List<OpportunityReportMapper> getOpportunitiesByUserIdDateRangeforReport(String userId, String startDate,
			String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getOpportunityDetailsByUserIdWithDateRange(userId, start_date, end_date);
		return opportunityDetailsList.stream().map(this::getOpportunityDetailsforReport).collect(Collectors.toList());
	}

	@Override
	public HashMap getOpportunityCountByCountry(String country) {
		List<AddressDetails> addressDetails = addressRepository.getAddressDetailsByCountry(country);
		HashMap map = new HashMap();
		int count = 0;
		if (null != addressDetails && !addressDetails.isEmpty()) {
			for (AddressDetails address : addressDetails) {
				List<CustomerAddressLink> customerAddressLinkList = customerAddressLinkRepository
						.getByAddressId(address.getAddressId());
				if (null != customerAddressLinkList && !customerAddressLinkList.isEmpty()) {
					for (CustomerAddressLink customerAddressLink : customerAddressLinkList) {
						List<OpportunityDetails> opportunityList = opportunityDetailsRepository
								.getOpportunityListByCustomerIdAndLiveInd(customerAddressLink.getCustomerId());
						if (null != opportunityList) {
							count = count + opportunityList.size();
						}
					}
				}
			}
		}

		map.put("opportunityCountByCountry", count);
		return map;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityListByCountry(String country) {
		List<OpportunityViewMapper> resultList = new ArrayList<>();
		List<AddressDetails> addressDetails = addressRepository.getAddressDetailsByCountry(country);
		HashMap map = new HashMap();
		int count = 0;
		if (null != addressDetails && !addressDetails.isEmpty()) {
			for (AddressDetails address : addressDetails) {
				List<CustomerAddressLink> customerAddressLinkList = customerAddressLinkRepository
						.getByAddressId(address.getAddressId());
				if (null != customerAddressLinkList && !customerAddressLinkList.isEmpty()) {
					for (CustomerAddressLink customerAddressLink : customerAddressLinkList) {
						List<OpportunityDetails> opportunityList = opportunityDetailsRepository
								.getOpportunityListByCustomerIdAndLiveInd(customerAddressLink.getCustomerId());
						if (null != opportunityList && !opportunityList.isEmpty()) {
							for (OpportunityDetails opportunity : opportunityList) {
								OpportunityViewMapper mapper = getOpportunityDetails(opportunity.getOpportunityId());
								resultList.add(mapper);
							}

						}

					}
				}
			}
		}
		return resultList;
	}

	@Override
	public HashMap getOpenOpportunityCountByCountry(String country) {
		List<AddressDetails> addressDetails = addressRepository.getAddressDetailsByCountry(country);
		HashMap map = new HashMap();
		int count = 0;
		if (null != addressDetails && !addressDetails.isEmpty()) {
			for (AddressDetails address : addressDetails) {
				List<CustomerAddressLink> customerAddressLinkList = customerAddressLinkRepository
						.getByAddressId(address.getAddressId());
				if (null != customerAddressLinkList && !customerAddressLinkList.isEmpty()) {
					for (CustomerAddressLink customerAddressLink : customerAddressLinkList) {
						List<OpportunityDetails> opportunityList = opportunityDetailsRepository
								.getOpenOpportunityByCustomerId(customerAddressLink.getCustomerId());
						if (null != opportunityList) {
							count = count + opportunityList.size();
						}
					}
				}
			}
		}

		map.put("openOpportunityCountByCountry", count);
		return map;
	}

	@Override
	public List<OpportunityViewMapper> getOpenOpportunityListByCountry(String country) {
		List<OpportunityViewMapper> resultList = new ArrayList<>();
		List<AddressDetails> addressDetails = addressRepository.getAddressDetailsByCountry(country);
		HashMap map = new HashMap();
		int count = 0;
		if (null != addressDetails && !addressDetails.isEmpty()) {
			for (AddressDetails address : addressDetails) {
				List<CustomerAddressLink> customerAddressLinkList = customerAddressLinkRepository
						.getByAddressId(address.getAddressId());
				if (null != customerAddressLinkList && !customerAddressLinkList.isEmpty()) {
					for (CustomerAddressLink customerAddressLink : customerAddressLinkList) {
						List<OpportunityDetails> opportunityList = opportunityDetailsRepository
								.getOpenOpportunityByCustomerId(customerAddressLink.getCustomerId());
						if (null != opportunityList && !opportunityList.isEmpty()) {
							for (OpportunityDetails opportunity : opportunityList) {
								OpportunityViewMapper mapper = getOpportunityDetails(opportunity.getOpportunityId());
								resultList.add(mapper);
							}

						}

					}
				}
			}
		}
		return resultList;
	}

	@Override
	public HashMap getOpportunityYearlyCountByCountry(String country) {

		Date end_month = Utility.getDateAfterEndDate(Utility.removeTime((new Date())));
		Date start_month = Utility.removeTime((new Date()));

		List<AddressDetails> addressDetails = addressRepository.getAddressDetailsByCountry(country);
		HashMap map = new HashMap();
		int count = 0;
		if (null != addressDetails && !addressDetails.isEmpty()) {
			for (AddressDetails address : addressDetails) {
				List<CustomerAddressLink> customerAddressLinkList = customerAddressLinkRepository
						.getByAddressId(address.getAddressId());
				if (null != customerAddressLinkList && !customerAddressLinkList.isEmpty()) {
					for (CustomerAddressLink customerAddressLink : customerAddressLinkList) {
						List<OpportunityDetails> opportunityList = opportunityDetailsRepository
								.getOpportunityByCustomerIdWithDateRange(customerAddressLink.getCustomerId(),
										start_month, end_month);
						System.out.println("opportunityList===" + opportunityList.size());
						if (null != opportunityList) {
							count = count + opportunityList.size();
							System.out.println("count==" + count);
						}
					}
				}
			}
		}

		map.put("yearlyOpportunityCountByCountry", count);
		return map;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityYearlyListByCountry(String country) {

		Date end_month = Utility.getDateAfterEndDate(Utility.removeTime((new Date())));
		Date start_month = Utility.removeTime((new Date()));
		System.out.println("end_month-----" + end_month);
		System.out.println("start_month-----" + start_month);
		List<OpportunityViewMapper> resultList = new ArrayList<>();
		List<AddressDetails> addressDetails = addressRepository.getAddressDetailsByCountry(country);
		HashMap map = new HashMap();
		int count = 0;
		if (null != addressDetails && !addressDetails.isEmpty()) {
			for (AddressDetails address : addressDetails) {
				List<CustomerAddressLink> customerAddressLinkList = customerAddressLinkRepository
						.getByAddressId(address.getAddressId());
				if (null != customerAddressLinkList && !customerAddressLinkList.isEmpty()) {
					for (CustomerAddressLink customerAddressLink : customerAddressLinkList) {
						List<OpportunityDetails> opportunityList = opportunityDetailsRepository
								.getOpportunityByCustomerIdWithDateRange(customerAddressLink.getCustomerId(),
										start_month, end_month);
						if (null != opportunityList && !opportunityList.isEmpty()) {
							for (OpportunityDetails opportunity : opportunityList) {
								OpportunityViewMapper mapper = getOpportunityDetails(opportunity.getOpportunityId());
								resultList.add(mapper);
							}

						}

					}
				}
			}
		}
		return resultList;
	}

	@Override
	public Set<OpportunityViewMapper> getTeamOppDetailsByUnderUserId(String userId, int pageNo, int pageSize) {

		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		Set<OpportunityViewMapper> mapperSet = new HashSet<>();
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		Page<OpportunityDetails> oppPage = opportunityDetailsRepository.getTeamOppListByUserIdsPaginated(userIdss,
				paging);

		if (oppPage != null && !oppPage.isEmpty()) {
			mapperSet = oppPage.getContent().stream().map(li -> getOpportunityRelatedDetails(li))
					.collect(Collectors.toSet());
		}
		return mapperSet;
	}

	@Override
	public HashMap getTeamOppertunityCountByUnderUserId(String userId) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);
		List<OpportunityDetails> oppList = opportunityDetailsRepository.getTeamOppListByUserIds(userIdss);
		HashMap map = new HashMap();
		map.put("opportunityTeam", oppList.size());

		return map;
	}

	@Override
	public List<OpportunityViewMapper> getOpenOppListBycontactId(String contactId) {
		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getOpenOpportunityListByContactId(contactId);

		List<OpportunityViewMapper> mapperList = new ArrayList<OpportunityViewMapper>();
		opportunityList.stream().map(opportunityDetails -> {

			OpportunityViewMapper opportunityMapper = getOpportunityRelatedDetails(opportunityDetails);
			opportunityMapper.setContactId(contactId);

			mapperList.add(opportunityMapper);
			return mapperList;
		}).collect(Collectors.toList());

		return mapperList;
	}

	@Override
	public HashMap getOpenOppCountBycontactId(String contactId) {
		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getOpenOpportunityListByContactId(contactId);
		HashMap map = new HashMap();

		map.put("openOpportunity", opportunityList.size());
		return map;

	}

	@Override
	public HashMap getWonOppCountBycontactId(String contactId) {
		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getWonOpportunityListByContactId(contactId);
		HashMap map = new HashMap();

		map.put("wonOpportunity", opportunityList.size());
		return map;

	}

	@Override
	public List<OpportunityViewMapper> getWonOppListBycontactId(String contactId) {
		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getWonOpportunityListByContactId(contactId);

		List<OpportunityViewMapper> mapperList = new ArrayList<OpportunityViewMapper>();
		opportunityList.stream().map(opportunityDetails -> {

			OpportunityViewMapper opportunityMapper = getOpportunityRelatedDetails(opportunityDetails);
			opportunityMapper.setContactId(contactId);

			mapperList.add(opportunityMapper);
			return mapperList;
		}).collect(Collectors.toList());

		return mapperList;
	}

	@Override
	public HashMap getOppProposalValueCountByContactId(String contactId, String userId, String orgId) {
		HashMap map = new HashMap();
		int count = 0;
		int conversionAmount = 0;
		ContactDetails contact = contactRepository.getcontactDetailsById(contactId);
		if (null != contact) {
			if (contact.getUser_id().equalsIgnoreCase(userId)) {

				List<OpportunityDetails> opportunityList = opportunityDetailsRepository
						.getOpportunityListByContactIdAndLiveInd(contactId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (OpportunityDetails opportunityDetails : opportunityList) {
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							EmployeeDetails employeeDetails = employeeRepository
									.getEmployeeDetailsByEmployeeId(contact.getUser_id());
							if (null != employeeDetails.getCurrency()) {
								Currency userCurrency = currencyRepository
										.getByCurrencyId(employeeDetails.getCurrency());
								if (null != userCurrency) {
									if (null != opportunityDetails.getCurrency()) {
										if (userCurrency.getCurrencyName()
												.equalsIgnoreCase(opportunityDetails.getCurrency())) {
											System.out.println(
													"Opp currency in user 0: " + opportunityDetails.getCurrency());
											System.out.println(
													"user currency in user 0: " + userCurrency.getCurrencyName());
											System.out.println("ProposalAmount in user 0: "
													+ opportunityDetails.getProposalAmount());
											count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
											System.out.println("count in user 0: " + count);
											System.out.println("in user currency == opp currency");
										} else {

											CurrencyConversion currencyConversion = currencyConversionRepository
													.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
															userCurrency.getCurrencyName(),
															opportunityDetails.getCurrency(), true);
											if (null != currencyConversion) {
												System.out.println(
														"Trade currency in user 1: " + userCurrency.getCurrencyName());
												System.out.println("Conversion currency in user 1: "
														+ opportunityDetails.getCurrency());
												System.out.println("ProposalAmount in user 1: "
														+ opportunityDetails.getProposalAmount());
												conversionAmount = (int) (Integer
														.parseInt(opportunityDetails.getProposalAmount())
														/ currencyConversion.getConversionFactor());
												System.out.println("conversionAmount in user 1: " + conversionAmount);
												count = count + conversionAmount;
												System.out.println("count in user 1: " + count);
												System.out.println("in user 0");
											} else {
												CurrencyConversion currencyConversion1 = currencyConversionRepository
														.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
																opportunityDetails.getCurrency(),
																userCurrency.getCurrencyName(), true);
												if (null != currencyConversion1) {
													System.out.println("Trade currency in user 2: "
															+ opportunityDetails.getCurrency());
													System.out.println("Conversion currency in user 2: "
															+ userCurrency.getCurrencyName());
													System.out.println("ProposalAmount in user 2: "
															+ opportunityDetails.getProposalAmount());
													conversionAmount = (int) (Integer
															.parseInt(opportunityDetails.getProposalAmount())
															* currencyConversion1.getConversionFactor());
													System.out
															.println("conversionAmount in user 2: " + conversionAmount);
													count = count + conversionAmount;
													System.out.println("count in user 2: " + count);
													System.out.println("in user 1");
												} else {
													System.out.println("in user 2");
													map.put("message",
															"No Conversion Available from "
																	+ userCurrency.getCurrencyName() + "to"
																	+ opportunityDetails.getCurrency());
													break;
												}
											}
										}
									}
								}
							} else {
								System.out.println("in user 3");
								map.put("message", "Currency Not Available for user : "
										+ employeeService.getEmployeeFullName(userId));
								count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
							}
						}
					}
					EmployeeDetails employeeDetails = employeeRepository
							.getEmployeeDetailsByEmployeeId(contact.getUser_id());
					if (null != employeeDetails.getCurrency()) {
						Currency userCurrency = currencyRepository.getByCurrencyId(employeeDetails.getCurrency());
						if (null != userCurrency) {
							System.out.println("in user trade currency : " + userCurrency.getCurrencyName());
							map.put("tradeCurrency", userCurrency.getCurrencyName());
						}
					}
				} else {
					System.out.println("in user 4");
					map.put("message", "Opportunity not available for this contact");
				}

			} else {

				List<OpportunityDetails> opportunityList = opportunityDetailsRepository
						.getOpportunityListByContactIdAndLiveInd(contactId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (OpportunityDetails opportunityDetails : opportunityList) {
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							OrganizationDetails organizationDetails = organizationRepository
									.getOrganizationDetailsById(contact.getOrganization_id());
							if (null != organizationDetails.getTrade_currency()) {
								if (null != opportunityDetails.getCurrency()) {
									if (organizationDetails.getTrade_currency()
											.equalsIgnoreCase(opportunityDetails.getCurrency())) {
										System.out
												.println("Opp currency in org 0: " + opportunityDetails.getCurrency());
										System.out.println(
												"org currency in org 0: " + organizationDetails.getTrade_currency());
										System.out.println(
												"ProposalAmount in org 0: " + opportunityDetails.getProposalAmount());
										count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
										System.out.println("count in org 0: " + count);
										System.out.println("in org currency == opp currency");
									} else {

										CurrencyConversion currencyConversion = currencyConversionRepository
												.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
														organizationDetails.getTrade_currency(),
														opportunityDetails.getCurrency(), true);
										if (null != currencyConversion) {
											System.out.println("Trade currency in org 1: "
													+ organizationDetails.getTrade_currency());
											System.out.println("Conversion currency in org 1: "
													+ opportunityDetails.getCurrency());
											System.out.println("ProposalAmount in org 1: "
													+ opportunityDetails.getProposalAmount());
											conversionAmount = (int) (Integer
													.parseInt(opportunityDetails.getProposalAmount())
													/ currencyConversion.getConversionFactor());
											System.out.println("conversionAmount in org 1: " + conversionAmount);
											count = count + conversionAmount;
											System.out.println("count in org 1: " + count);
											System.out.println("in org 0");
										} else {
											CurrencyConversion currencyConversion1 = currencyConversionRepository
													.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
															opportunityDetails.getCurrency(),
															organizationDetails.getTrade_currency(), true);
											if (null != currencyConversion1) {
												System.out.println(
														"Trade currency in org 2: " + opportunityDetails.getCurrency());
												System.out.println("Conversion currency in org 2: "
														+ organizationDetails.getTrade_currency());
												System.out.println("ProposalAmount in org 2: "
														+ opportunityDetails.getProposalAmount());
												conversionAmount = (int) (Integer
														.parseInt(opportunityDetails.getProposalAmount())
														* currencyConversion1.getConversionFactor());
												System.out.println("conversionAmount in org 2: " + conversionAmount);
												count = count + conversionAmount;
												System.out.println("count in org 2: " + count);
												System.out.println("in org 1");
											} else {
												System.out.println("in org 2");
												map.put("message",
														"No Conversion Available from "
																+ organizationDetails.getTrade_currency() + "to"
																+ opportunityDetails.getCurrency());
												break;
											}
										}
									}
								}
							} else {
								System.out.println("in org 3");
								map.put("message", "Trade Currency Not Available for This Organization");
								count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
							}
						}
					}
					OrganizationDetails organizationDetails = organizationRepository
							.getOrganizationDetailsById(contact.getOrganization_id());
					if (null != organizationDetails.getTrade_currency()) {
						System.out.println("in org trade currency : " + organizationDetails.getTrade_currency());
						map.put("tradeCurrency", organizationDetails.getTrade_currency());
					}
				} else {
					System.out.println("in org 4");
					map.put("message", "Opportunity not available for this contact");
				}
			}
		} else {
			map.put("message", "Contact not available");
		}

		map.put("pipeLineValue", count);

		return map;

	}

	@Override
	public HashMap getOppWeigthedValueCountByContactId(String contactId, String userId, String orgId) {
		HashMap map = new HashMap();
		double count = 0;
		int conversionAmount = 0;
		ContactDetails contact = contactRepository.getcontactDetailsById(contactId);
		if (null != contact) {
			if (contact.getUser_id().equalsIgnoreCase(userId)) {

				List<OpportunityDetails> opportunityList = opportunityDetailsRepository
						.getOpportunityListByContactIdAndLiveInd(contactId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (OpportunityDetails opportunityDetails : opportunityList) {
						int pAmount = 0;
						double wValue = 0;
						double total = 0;
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							EmployeeDetails employeeDetails = employeeRepository
									.getEmployeeDetailsByEmployeeId(contact.getUser_id());
							if (null != employeeDetails.getCurrency()) {
								Currency userCurrency = currencyRepository
										.getByCurrencyId(employeeDetails.getCurrency());
								if (null != userCurrency) {
									if (null != opportunityDetails.getCurrency()) {
										if (userCurrency.getCurrencyName()
												.equalsIgnoreCase(opportunityDetails.getCurrency())) {

											pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
											System.out.println(
													"Opp currency in user 0: " + opportunityDetails.getCurrency());
											System.out.println(
													"user currency in user 0: " + userCurrency.getCurrencyName());
											System.out.println("ProposalAmount in user 0: " + pAmount);
											OpportunityStages oppStages = opportunityStagesRepository
													.getOpportunityStagesByOpportunityStagesId(
															opportunityDetails.getOppStage());
											if (null != oppStages) {
												if (0 != oppStages.getProbability()) {
													wValue = ((oppStages.getProbability()) / 100);
													System.out.println("wValue in user 0: " + wValue);
												}
											}
											total = pAmount * wValue;
											System.out.println("total in user 0: " + total);
											count = count + total;

											// count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
											System.out.println("count in user 0: " + count);
											System.out.println("in user currency == opp currency");
										} else {

											CurrencyConversion currencyConversion = currencyConversionRepository
													.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
															userCurrency.getCurrencyName(),
															opportunityDetails.getCurrency(), true);
											if (null != currencyConversion) {
												pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
												System.out.println(
														"Trade currency in user 1: " + userCurrency.getCurrencyName());
												System.out.println("Conversion currency in user 1: "
														+ opportunityDetails.getCurrency());
												System.out.println("ProposalAmount in user 1: " + pAmount);
												OpportunityStages oppStages = opportunityStagesRepository
														.getOpportunityStagesByOpportunityStagesId(
																opportunityDetails.getOppStage());
												if (null != oppStages) {
													if (0 != oppStages.getProbability()) {
														wValue = ((oppStages.getProbability()) / 100);
														System.out.println("wValue in user 1: " + wValue);
													}
												}
												total = pAmount * wValue;
												System.out.println("total in user 1: " + total);
												conversionAmount = (int) ((total)
														/ currencyConversion.getConversionFactor());
												System.out.println("conversionAmount in user 1: " + conversionAmount);
												count = count + conversionAmount;
												System.out.println("count in user 1: " + count);
												System.out.println("in user 0");
											} else {
												CurrencyConversion currencyConversion1 = currencyConversionRepository
														.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
																opportunityDetails.getCurrency(),
																userCurrency.getCurrencyName(), true);
												if (null != currencyConversion1) {
													pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
													System.out.println("Trade currency in user 2: "
															+ opportunityDetails.getCurrency());
													System.out.println("Conversion currency in user 2: "
															+ userCurrency.getCurrencyName());
													System.out.println("ProposalAmount in user 2: " + pAmount);
													OpportunityStages oppStages = opportunityStagesRepository
															.getOpportunityStagesByOpportunityStagesId(
																	opportunityDetails.getOppStage());
													if (null != oppStages) {
														if (0 != oppStages.getProbability()) {
															wValue = ((oppStages.getProbability()) / 100);
															System.out.println("wValue in user 1: " + wValue);
														}
													}
													total = pAmount * wValue;
													System.out.println("total in user 1: " + total);
													conversionAmount = (int) ((total)
															/ currencyConversion1.getConversionFactor());
													System.out
															.println("conversionAmount in user 2: " + conversionAmount);
													count = count + conversionAmount;
													System.out.println("count in user 2: " + count);
													System.out.println("in user 1");
												} else {
													System.out.println("in user 2");
													map.put("message",
															"No Conversion Available from "
																	+ userCurrency.getCurrencyName() + "to"
																	+ opportunityDetails.getCurrency());
													break;
												}
											}
										}
									}
								}
							} else {
								System.out.println("in user 3");
								map.put("message", "Currency Not Available for user : "
										+ employeeService.getEmployeeFullName(userId));
								count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
							}
						}
					}
					EmployeeDetails employeeDetails = employeeRepository
							.getEmployeeDetailsByEmployeeId(contact.getUser_id());
					if (null != employeeDetails.getCurrency()) {
						Currency userCurrency = currencyRepository.getByCurrencyId(employeeDetails.getCurrency());
						if (null != userCurrency) {
							System.out.println("in user trade currency : " + userCurrency.getCurrencyName());
							map.put("tradeCurrency", userCurrency.getCurrencyName());
						}
					}
				} else {
					System.out.println("in user 4");
					map.put("message", "Opportunity not available for this Contact");
				}

			} else {
				List<OpportunityDetails> opportunityList = opportunityDetailsRepository
						.getOpportunityListByContactIdAndLiveInd(contactId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (OpportunityDetails opportunityDetails : opportunityList) {
						int pAmount = 0;
						double wValue = 0;
						double total = 0;
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							OrganizationDetails organizationDetails = organizationRepository
									.getOrganizationDetailsById(contact.getOrganization_id());
							if (null != organizationDetails.getTrade_currency()) {
								if (null != opportunityDetails.getCurrency()) {
									if (organizationDetails.getTrade_currency()
											.equalsIgnoreCase(opportunityDetails.getCurrency())) {
										pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
										System.out
												.println("Opp currency in org 0: " + opportunityDetails.getCurrency());
										System.out.println(
												"org currency in org 0: " + organizationDetails.getTrade_currency());
										System.out.println("ProposalAmount in org 0: " + pAmount);
										OpportunityStages oppStages = opportunityStagesRepository
												.getOpportunityStagesByOpportunityStagesId(
														opportunityDetails.getOppStage());
										if (null != oppStages) {
											if (0 != oppStages.getProbability()) {
												wValue = ((oppStages.getProbability()) / 100);
												System.out.println("wValue in user 0: " + wValue);
											}
										}
										total = pAmount * wValue;
										System.out.println("total in user 0: " + total);
										count = count + total;
										// count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
										System.out.println("count in org 0: " + count);
										System.out.println("in org currency == opp currency");
									} else {

										CurrencyConversion currencyConversion = currencyConversionRepository
												.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
														organizationDetails.getTrade_currency(),
														opportunityDetails.getCurrency(), true);
										if (null != currencyConversion) {
											pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
											System.out.println("Trade currency in org 1: "
													+ organizationDetails.getTrade_currency());
											System.out.println("Conversion currency in org 1: "
													+ opportunityDetails.getCurrency());
											System.out.println("ProposalAmount in org 1: "
													+ opportunityDetails.getProposalAmount());
											OpportunityStages oppStages = opportunityStagesRepository
													.getOpportunityStagesByOpportunityStagesId(
															opportunityDetails.getOppStage());
											if (null != oppStages) {
												if (0 != oppStages.getProbability()) {
													wValue = ((oppStages.getProbability()) / 100);
													System.out.println("wValue in user 1: " + wValue);
												}
											}
											total = pAmount * wValue;
											System.out.println("total in user 1: " + total);
											conversionAmount = (int) ((total)
													/ currencyConversion.getConversionFactor());
											System.out.println("conversionAmount in org 1: " + conversionAmount);
											count = count + conversionAmount;
											System.out.println("count in org 1: " + count);
											System.out.println("in org 0");
										} else {
											CurrencyConversion currencyConversion1 = currencyConversionRepository
													.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
															opportunityDetails.getCurrency(),
															organizationDetails.getTrade_currency(), true);
											if (null != currencyConversion1) {
												pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
												System.out.println(
														"Trade currency in org 2: " + opportunityDetails.getCurrency());
												System.out.println("Conversion currency in org 2: "
														+ organizationDetails.getTrade_currency());
												System.out.println("ProposalAmount in org 2: " + pAmount);
												OpportunityStages oppStages = opportunityStagesRepository
														.getOpportunityStagesByOpportunityStagesId(
																opportunityDetails.getOppStage());
												if (null != oppStages) {
													if (0 != oppStages.getProbability()) {
														wValue = ((oppStages.getProbability()) / 100);
														System.out.println("wValue in user 2: " + wValue);
													}
												}
												total = pAmount * wValue;
												System.out.println("total in user 2: " + total);
												conversionAmount = (int) ((total)
														/ currencyConversion1.getConversionFactor());
												System.out.println("conversionAmount in org 2: " + conversionAmount);
												count = count + conversionAmount;
												System.out.println("count in org 2: " + count);
												System.out.println("in org 1");
											} else {
												System.out.println("in org 2");
												map.put("message",
														"No Conversion Available from "
																+ organizationDetails.getTrade_currency() + "to"
																+ opportunityDetails.getCurrency());
												break;
											}
										}
									}
								}
							} else {
								System.out.println("in org 3");
								map.put("message", "Trade Currency Not Available for This Organization");
								count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
							}
						}
					}
					OrganizationDetails organizationDetails = organizationRepository
							.getOrganizationDetailsById(contact.getOrganization_id());
					if (null != organizationDetails.getTrade_currency()) {
						System.out.println("in org trade currency : " + organizationDetails.getTrade_currency());
						map.put("tradeCurrency", organizationDetails.getTrade_currency());
					}
				} else {
					System.out.println("in org 4");
					map.put("message", "Opportunity not available for this Contact");
				}
			}
		} else {
			map.put("message", "Contact not available");
		}

		map.put("weightedValue", count);

		return map;
	}

	@Override
	public List<OpportunityDropdownMapper> getDropDownOpportunityListByCustomerId(String customerId) {
		List<OpportunityDropdownMapper> opportunities = new ArrayList<>();

		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getOpportunityListByCustomerIdAndLiveInd(customerId);
		opportunityList.sort(Comparator.comparing(OpportunityDetails::getCreationDate));
		if (null != opportunityList && !opportunityList.isEmpty()) {
			return opportunityList.stream().map(li -> {

				OpportunityDropdownMapper mapper = new OpportunityDropdownMapper();
				mapper.setOpportunityId(li.getOpportunityId());
				mapper.setCustomerId(li.getCustomerId());
				mapper.setOpportunityName(li.getOpportunityName());
				return mapper;

			}).collect(Collectors.toList());
		}
		return opportunities;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityListByUserIdAndQuarterAndYear(String userId, String quarter,
			int year) {

		List<OpportunityViewMapper> opportunities = new ArrayList<>();

		Date end_date = null;
		Date start_date = null;
		try {
			if (quarter.equalsIgnoreCase("Q1")) {
				end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getQuarterEndDate(year, 1)));
				start_date = Utility.removeTime(Utility.getQuarterStartDate(year, 1));
			}
			if (quarter.equalsIgnoreCase("Q2")) {
				end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getQuarterEndDate(year, 2)));
				start_date = Utility.removeTime(Utility.getQuarterStartDate(year, 2));
			}
			if (quarter.equalsIgnoreCase("Q3")) {
				end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getQuarterEndDate(year, 3)));
				start_date = Utility.removeTime(Utility.getQuarterStartDate(year, 3));
			}
			if (quarter.equalsIgnoreCase("Q4")) {
				end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getQuarterEndDate(year, 4)));
				start_date = Utility.removeTime(Utility.getQuarterStartDate(year, 4));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getOpportunityDetailsByUserIdAndQuarter(userId, start_date, end_date);

		if (null != opportunityList && !opportunityList.isEmpty()) {

			opportunities = opportunityList.stream().filter(li -> (li != null))
					.map(li -> getOpportunityRelatedDetails(li)).collect(Collectors.toList());
		}

		return opportunities;
	}

	@Override
	public List<OpportunityReportMapper> getWonOpportunitiesByUserIdDateRangeforReport(String userId, String startDate,
			String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getWonDataBetweenStartDateAndEmployeeId(userId, start_date, end_date);
		return opportunityDetailsList.stream().map(this::getOpportunityDetailsforReport).collect(Collectors.toList());
	}

	@Override
	public List<OpportunityReportMapper> getLostOpportunitiesByUserIdDateRangeforReport(String userId, String startDate,
			String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
				.getLostDataBetweenStartDateAndEmployeeId(userId, start_date, end_date);
		return opportunityDetailsList.stream().map(this::getOpportunityDetailsforReport).collect(Collectors.toList());
	}

	@Override
	public List<OrganizationValueMapper> getMultyOrgWonOppValueByYearAndQuarterForDashBoard(String emailId, int year,
			String quarter, String userId, String orgId) {
		List<OrganizationValueMapper> resultList = new ArrayList<>();
		Date end_date = null;
		Date start_date = null;
		System.out.println("emailId+++++====" + emailId);
		System.out.println("year+++++====" + year);
		System.out.println("quarter+++++====" + quarter);
		try {
			if (quarter.equalsIgnoreCase("Q1")) {
				end_date = Utility.getDateAfterEndDate(
						Utility.removeTime(Utility.getQuarterEndDate(year, 1)));
				start_date = Utility.removeTime(Utility.getQuarterStartDate(year, 1));
			}
			if (quarter.equalsIgnoreCase("Q2")) {
				end_date = Utility.getDateAfterEndDate(
						Utility.removeTime(Utility.getQuarterEndDate(year, 2)));
				start_date = Utility.removeTime(Utility.getQuarterStartDate(year, 2));
			}
			if (quarter.equalsIgnoreCase("Q3")) {
				end_date = Utility.getDateAfterEndDate(
						Utility.removeTime(Utility.getQuarterEndDate(year, 3)));
				start_date = Utility.removeTime(Utility.getQuarterStartDate(year, 3));
			}
			if (quarter.equalsIgnoreCase("Q4")) {
				end_date = Utility.getDateAfterEndDate(
						Utility.removeTime(Utility.getQuarterEndDate(year, 4)));
				start_date = Utility.removeTime(Utility.getQuarterStartDate(year, 4));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("start_date+++++====" + start_date);
		System.out.println("end_date+++++====" + end_date);

		EmployeeEmailLink employeeEmailLink = employeeEmailLinkRepository.findUserLinkByEmail(emailId);
		if (null != employeeEmailLink) {
			System.out
					.println("employeeEmailLink.getPrimaryEmailId()+++++====" + employeeEmailLink.getPrimaryEmailId());
			EmployeeDetails employeeList = employeeRepository
					.getEmployeeByMailId(employeeEmailLink.getPrimaryEmailId());
			if (null != employeeList) {

				System.out.println("employeeList.getOrgId()+++++====" + employeeList.getOrgId());

				OrganizationDetails organizationDetails = organizationRepository
						.getOrganizationDetailsById(employeeList.getOrgId());
				if (null != organizationDetails) {
					System.out.println(
							"organizationDetails.getOrgId()+++++====" + organizationDetails.getOrganization_id());
					OrganizationValueMapper mapper = new OrganizationValueMapper();
					List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
							.getWonOppListByWonDateAndOrgId(organizationDetails.getOrganization_id(), start_date,
									end_date);
					if (null != opportunityDetailsList && !opportunityDetailsList.isEmpty()) {
						double value = 0;
						for (OpportunityDetails opportunityDetails : opportunityDetailsList) {
							System.out
									.println("opportunityDetails.getOrgId()+++++====" + opportunityDetails.getOrgId());
							System.out.println("opportunityDetails.getProposalAmount()+++++===="
									+ opportunityDetails.getProposalAmount());
							value = value + Double.parseDouble(opportunityDetails.getProposalAmount());
						}
						System.out.println("value+++++====" + value);
						System.out.println("organizationDetails.getName()+++++====" + organizationDetails.getName());
						mapper.setOrgValue(value);
						mapper.setOrgId(organizationDetails.getOrganization_id());
						mapper.setOrgName(organizationDetails.getName());

						resultList.add(mapper);
					}
				}
			}

			System.out.println(
					"employeeEmailLink.getSecondaryEmailId()+++++====" + employeeEmailLink.getSecondaryEmailId());
			EmployeeDetails employeeList1 = employeeRepository
					.getEmployeeByMailId(employeeEmailLink.getSecondaryEmailId());
			if (null != employeeList1) {

				System.out.println("employeeList1.getOrgId()+++++====" + employeeList1.getOrgId());

				OrganizationDetails organizationDetails = organizationRepository
						.getOrganizationDetailsById(employeeList1.getOrgId());
				if (null != organizationDetails) {
					System.out.println(
							"organizationDetails1.getOrgId()+++++====" + organizationDetails.getOrganization_id());
					OrganizationValueMapper mapper = new OrganizationValueMapper();
					List<OpportunityDetails> opportunityDetailsList = opportunityDetailsRepository
							.getWonOppListByWonDateAndOrgId(organizationDetails.getOrganization_id(), start_date,
									end_date);
					if (null != opportunityDetailsList && !opportunityDetailsList.isEmpty()) {
						double value = 0;
						for (OpportunityDetails opportunityDetails : opportunityDetailsList) {
							System.out
									.println("opportunityDetails1.getOrgId()+++++====" + opportunityDetails.getOrgId());
							System.out.println("opportunityDetails1.getProposalAmount()+++++===="
									+ opportunityDetails.getProposalAmount());
							value = value + Double.parseDouble(opportunityDetails.getProposalAmount());
						}
						System.out.println("value1+++++====" + value);
						System.out.println("organizationDetails1.getName()+++++====" + organizationDetails.getName());
						mapper.setOrgValue(value);
						mapper.setOrgId(organizationDetails.getOrganization_id());
						mapper.setOrgName(organizationDetails.getName());

						resultList.add(mapper);
					}
				}
			}

		}

		return resultList;
	}

	@Override
	public List<OpportunityViewMapper> getAllWonOppList(String orgId, int year, String quarter) {
		Date end_date = null;
		Date start_date = null;

		try {
			if (quarter.equalsIgnoreCase("Q1")) {
				end_date = Utility.getDateAfterEndDate(
						Utility.removeTime(Utility.getQuarterEndDate(year, 1)));
				start_date = Utility.removeTime(Utility.getQuarterStartDate(year, 1));
			}
			if (quarter.equalsIgnoreCase("Q2")) {
				end_date = Utility.getDateAfterEndDate(
						Utility.removeTime(Utility.getQuarterEndDate(year, 2)));
				start_date = Utility.removeTime(Utility.getQuarterStartDate(year, 2));
			}
			if (quarter.equalsIgnoreCase("Q3")) {
				end_date = Utility.getDateAfterEndDate(
						Utility.removeTime(Utility.getQuarterEndDate(year, 3)));
				start_date = Utility.removeTime(Utility.getQuarterStartDate(year, 3));
			}
			if (quarter.equalsIgnoreCase("Q4")) {
				end_date = Utility.getDateAfterEndDate(
						Utility.removeTime(Utility.getQuarterEndDate(year, 4)));
				start_date = Utility.removeTime(Utility.getQuarterStartDate(year, 4));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<OpportunityViewMapper> mapperList = new ArrayList<OpportunityViewMapper>();
		List<OpportunityDetails> opportunityList = opportunityDetailsRepository.getWonOppListByWonDateAndOrgId(orgId,
				start_date, end_date);
		if(null != opportunityList && !opportunityList.isEmpty()) {
		
		opportunityList.stream().map(opportunityDetails -> {

			OpportunityViewMapper opportunityMapper = getOpportunityRelatedDetails(opportunityDetails);
			if(null != opportunityList ) {
			mapperList.add(opportunityMapper);
			}
			return mapperList;
		}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public String linkOppToProduct(OpportunityProductMapper mapper) {
		if (null != mapper) {
		OpportunityProductLink link = new OpportunityProductLink();
        link.setOpportunityId(mapper.getOpportunityId());
        link.setUserId(mapper.getUserId());
        link.setUnit(mapper.getUnit());
        link.setProductId(mapper.getOpportunityId());
        link.setActive(true);
        link.setCreateAt(new Date());
        String opportunityId = opportunityProductRepository.save(link).getOpportunityId();
        return opportunityId;
		}
		return null;
	}

	@Override
	public List<OpportunityProductMapper> getOpportunityLinkProductListByOpportunityId(String opportunityId) {
		List<OpportunityProductMapper> result = new ArrayList<>();
		List<OpportunityProductLink> list = opportunityProductRepository.findByOpportunityIdAndActive(opportunityId, true);
		list.stream().map(link -> {
			OpportunityProductMapper mapper = new OpportunityProductMapper();
			
			Product product = productRepository.findByIdAndActive(link.getProductId(), true);
			if (product.getCategory() != null) {
				Optional<ProductCategoryDetails> categoryDetails = productCategoryDetailsRepository.findById(product.getCategory());
				if (categoryDetails.isPresent()) {
					mapper.setCategoryName(categoryDetails.get().getCategoryName());
				}
			}
			if (product.getSubCategory() != null) {
				Optional<ProductSubCategoryDetails> subCategoryDetails = productSubCategoryDetailsRepository
						.findById(product.getSubCategory());

				if (subCategoryDetails.isPresent()) {
					mapper.setSubCategoryName(subCategoryDetails.get().getSubCategoryName());
				}
			}
			if (product.getAttribute() != null) {
				Optional<ProductAttributeDetails> attributeDetails = productAttributeDetailsRepository
						.findById(product.getAttribute());
				if (attributeDetails.isPresent()) {
					mapper.setAttributeName(attributeDetails.get().getAttributeName());
				}
			}
			if (product.getSubAttribute() != null) {
				Optional<ProductSubAtrributeDetails> subAtrributeDetails = productSubAttributeDetailsRepository
						.findById(product.getSubAttribute());
				if (subAtrributeDetails.isPresent()) {
					mapper.setSubAttributeName(subAtrributeDetails.get().getSubAttributeName());
				}
			}
			mapper.setUnit(link.getUnit());
			return mapper;
		}).collect(Collectors.toList());
					
		return result;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityDetailsByNameAndTypeOrgLevel(String name,String orgId) {
		List<OpportunityDetails> list = opportunityDetailsRepository
				.findByLiveIndAndReinstateIndAndCloseIndAndLostIndAndWonIndAndOrgId(true, true, false, false, false,orgId);

		List<OpportunityDetails> filterList = list.parallelStream().filter(detail -> {
			return detail.getOpportunityName() != null && Utility.containsIgnoreCase(detail.getOpportunityName(), name.trim());
		}).collect(Collectors.toList());

		List<OpportunityViewMapper> resultList = new ArrayList<OpportunityViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {
			filterList.stream().map(opportunityDetails -> {
				OpportunityViewMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
				if (null != opportunityViewMapper) {
					resultList.add(opportunityViewMapper);
					return resultList;

				}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityDetailsByNameAndTypeForTeam(String name,String userId) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		List<OpportunityDetails> list = opportunityDetailsRepository.getOpportunityByUserIdsAndOppName(userIdss);
		List<OpportunityDetails> filterList = list.parallelStream().filter(detail -> {
			return detail.getOpportunityName() != null && Utility.containsIgnoreCase(detail.getOpportunityName(), name.trim());
		}).collect(Collectors.toList());


		List<OpportunityViewMapper> resultList = new ArrayList<OpportunityViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {
			filterList.stream().map(opportunityDetails -> {
				OpportunityViewMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
				if (null != opportunityViewMapper) {
					resultList.add(opportunityViewMapper);
					return resultList;

				}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityDetailsByNameAndTypeAndUserId(String name,	String userId) {
		List<OpportunityDetails> list = opportunityDetailsRepository
				.getOpportunityByUserIdAndOppName(userId);

		List<OpportunityDetails> filterList = list.parallelStream().filter(detail -> {
			return detail.getOpportunityName() != null && Utility.containsIgnoreCase(detail.getOpportunityName(), name.trim());
		}).collect(Collectors.toList());

		List<OpportunityViewMapper> resultList = new ArrayList<OpportunityViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {
			filterList.stream().map(opportunityDetails -> {
				OpportunityViewMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
				if (null != opportunityViewMapper) {
					resultList.add(opportunityViewMapper);
					return resultList;

				}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityDetailsByNewOppIdAndTypeOrgLevel(String name, String orgId) {
		List<OpportunityDetails> list = opportunityDetailsRepository
				.findByNewOppIdContainingAndLiveIndAndReinstateIndAndCloseIndAndLostIndAndWonIndAndOrgId(name,
						true, true, false, false, false,orgId);

		List<OpportunityViewMapper> resultList = new ArrayList<OpportunityViewMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(opportunityDetails -> {
				OpportunityViewMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
				if (null != opportunityViewMapper) {
					resultList.add(opportunityViewMapper);
					return resultList;

				}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityDetailsByNewOppIdAndTypeForTeam(String name, String userId) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);
		
		List<OpportunityDetails> list = opportunityDetailsRepository
				.getOpportunityByUserIdsAndNewOppId(userIdss,name);

		List<OpportunityViewMapper> resultList = new ArrayList<OpportunityViewMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(opportunityDetails -> {
				OpportunityViewMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
				if (null != opportunityViewMapper) {
					resultList.add(opportunityViewMapper);
					return resultList;

				}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityDetailsByNewOppIdAndTypeAndUserId(String name, String userId) {
		List<OpportunityDetails> list = opportunityDetailsRepository
				.getOpportunityByUserIdAndNewOppId(userId,name);

		List<OpportunityViewMapper> resultList = new ArrayList<OpportunityViewMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(opportunityDetails -> {
				OpportunityViewMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
				if (null != opportunityViewMapper) {
					resultList.add(opportunityViewMapper);
					return resultList;

				}
				return null;
			}).collect(Collectors.toList());

		}
		return resultList;
	}
}