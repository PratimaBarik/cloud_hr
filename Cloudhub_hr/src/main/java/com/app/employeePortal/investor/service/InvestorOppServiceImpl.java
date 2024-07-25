package com.app.employeePortal.investor.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
import com.app.employeePortal.Opportunity.mapper.ConversionValueMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.Opportunity.service.OpportunityService;
import com.app.employeePortal.Team.entity.Team;
import com.app.employeePortal.Team.entity.TeamMemberLink;
import com.app.employeePortal.Team.repository.TeamMemberLinkRepo;
import com.app.employeePortal.Team.repository.TeamRepository;
import com.app.employeePortal.address.entity.AddressDetails;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.category.entity.CurrencyConversion;
import com.app.employeePortal.category.repository.CurrencyConversionRepository;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.document.repository.DocumentDetailsRepository;
import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.Notes;
import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.NotesRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.investor.entity.FundContactLink;
import com.app.employeePortal.investor.entity.InOppConversionValue;
import com.app.employeePortal.investor.entity.Investor;
import com.app.employeePortal.investor.entity.InvestorAddressLink;
import com.app.employeePortal.investor.entity.InvestorOppContactLink;
import com.app.employeePortal.investor.entity.InvestorOppDocsLink;
import com.app.employeePortal.investor.entity.InvestorOppIncludedLink;
import com.app.employeePortal.investor.entity.InvestorOppNotesLink;
import com.app.employeePortal.investor.entity.InvestorOppSalesUserLink;
import com.app.employeePortal.investor.entity.InvestorOppStages;
import com.app.employeePortal.investor.entity.InvestorOppWorkflow;
import com.app.employeePortal.investor.entity.InvestorOpportunity;
import com.app.employeePortal.investor.mapper.InvestorOppFundRequest;
import com.app.employeePortal.investor.mapper.InvestorOppFundResponse;
import com.app.employeePortal.investor.mapper.InvestorOppReportMapper;
import com.app.employeePortal.investor.mapper.InvestorOpportunityMapper;
import com.app.employeePortal.investor.repository.InOppConversionValueRepo;
import com.app.employeePortal.investor.repository.InvestorAddressLinkRepo;
import com.app.employeePortal.investor.repository.InvestorOpFundRepo;
import com.app.employeePortal.investor.repository.InvestorOppContactLinkRepo;
import com.app.employeePortal.investor.repository.InvestorOppDocsLinkRepo;
import com.app.employeePortal.investor.repository.InvestorOppIncludedLinkRepo;
import com.app.employeePortal.investor.repository.InvestorOppNoteLinkRepo;
import com.app.employeePortal.investor.repository.InvestorOppSalesLinkRepo;
import com.app.employeePortal.investor.repository.InvestorOppStagesRepo;
import com.app.employeePortal.investor.repository.InvestorOppWorkflowRepo;
import com.app.employeePortal.investor.repository.InvestorOpportunityRepo;
import com.app.employeePortal.investor.repository.InvestorRepository;
import com.app.employeePortal.notification.entity.NotificationDetails;
import com.app.employeePortal.notification.entity.Notificationparam;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.organization.entity.OrganizationDetails;
import com.app.employeePortal.organization.repository.OrganizationRepository;
import com.app.employeePortal.permission.entity.Permission;
import com.app.employeePortal.permission.repository.PermissionRepository;
import com.app.employeePortal.registration.entity.Currency;
import com.app.employeePortal.registration.repository.CurrencyRepository;
import com.app.employeePortal.source.entity.Source;
import com.app.employeePortal.source.repository.SourceRepository;
import com.app.employeePortal.util.Utility;

import freemarker.template.TemplateException;

@Service
@Transactional
public class InvestorOppServiceImpl implements InvestorOppService {
    @Autowired
    InvestorOppWorkflowService investorOppWorkflowService;
    @Autowired
    InvestorOppStagesRepo investorOppStagesRepo;
    @Autowired
    ContactService contactService;
    @Autowired
    InvestorOppDocsLinkRepo investorOppDocsLinkRepo;
    @Autowired
    DocumentDetailsRepository documentDetailsRepository;
    @Autowired
    NotesRepository notesRepository;
    @Autowired
    InvestorOppNoteLinkRepo investorOppNoteLinkRepo;
    @Autowired
    InvestorOppService investorOppService;
    @Autowired
    InvestorOpportunityRepo investorOpportunityRepo;
    @Autowired
    InvestorOppSalesLinkRepo investorOppSalesLinkRepo;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    InvestorOppContactLinkRepo investorOppContactLinkRepo;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    InvestorRepository investorRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    InvestorOppWorkflowRepo investorOppWorkflowRepo;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    TeamMemberLinkRepo teamMemberLinkRepo;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    InvestorOppIncludedLinkRepo investorOppIncludedRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    OpportunityService oppertunityService;
    @Autowired
    InOppConversionValueRepo inOppConversionValueRepo;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    NotificationService notificationService;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    InvestorAddressLinkRepo investorAddressLinkRepo;
    @Autowired
    SourceRepository sourceRepository;

    @Value("${companyName}")
    private String companyName;
    @Autowired
    DocumentService documentService;
    @Autowired
    CurrencyConversionRepository currencyConversionRepository;
    @Autowired
    InvestorOpFundRepo investorOpFundRepo;

    @Override
    public InvestorOpportunityMapper saveInvestorOpportunity(InvestorOpportunityMapper opportunityMapper) throws TemplateException, IOException {
        InvestorOpportunityMapper resultMapper = null;
        InvestorOpportunity opportunity = new InvestorOpportunity();

        setPropertyOnInput(opportunityMapper, opportunity);
        InvestorOpportunity investorOpportunity = investorOpportunityRepo.save(opportunity);

        /*insert to Notification table*/
        Notificationparam param = new Notificationparam();
        EmployeeDetails emp = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
        String name = employeeService.getEmployeeFullNameByObject(emp);
        param.setEmployeeDetails(emp);
        param.setAdminMsg("Deal " + "'" + opportunityMapper.getOpportunityName() + "' created by " + name);
        param.setOwnMsg("Deal " + opportunityMapper.getOpportunityName() + " created.");
        param.setNotificationType("Deal Creation");
        param.setProcessNmae("Deal");
        param.setType("create");
        param.setCompanyName(companyName);
        param.setEmailSubject("Korero alert- Deal created");
        param.setCompanyName("Korero");
        param.setUserId(opportunityMapper.getUserId());

        if (!opportunityMapper.getUserId().equals(opportunityMapper.getAssignedTo())) {
            List<String> assignToUserIds = new ArrayList<>();
            assignToUserIds.add(opportunityMapper.getAssignedTo());
            param.setAssignToUserIds(assignToUserIds);
            param.setAssignToMsg("Deal " + "'" + opportunityMapper.getOpportunityName() + "'" + " assigned to " + employeeService.getEmployeeFullName(opportunityMapper.getAssignedTo()) + " by " + name);
        }
        notificationService.createNotificationForDynamicUsers(param);
        resultMapper = getOpportunityDetails(investorOpportunity.getInvOpportunityId());
        return resultMapper;
    }

    private void setPropertyOnInput(InvestorOpportunityMapper opportunityMapper, InvestorOpportunity opportunity) {
        opportunity.setOpportunityName(opportunityMapper.getOpportunityName());
        opportunity.setProposalAmount(opportunityMapper.getProposalAmount());
        opportunity.setInvestorId(opportunityMapper.getInvestorId());
        // opportunity.setCustomerName(opportunityMapper.getCustomerId());
        opportunity.setUserId(opportunityMapper.getUserId());
        opportunity.setContactId(opportunityMapper.getContactId());
        opportunity.setOrgId(opportunityMapper.getOrgId());
//        opportunity.setOppInnitiative(opportunityMapper.getOppInnitiative());
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
//        opportunity.setOpportunityId(opportunityId);
        opportunity.setOppStage(opportunityMapper.getOppStage());
        opportunity.setOppWorkflow(opportunityMapper.getOppWorkflow());
        opportunity.setAssignedTo(opportunityMapper.getSalesUserIds());
        opportunity.setAssignedBy(opportunityMapper.getUserId());
        opportunity.setSource(opportunityMapper.getSource());

        int countNumber = investorOpportunityRepo.countByCreationDate(Utility.removeTime(new Date()));

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
        opportunity.setNewDealNo(counts + date1 + month1 + year);

        InvestorOpportunity investorOpportunity = investorOpportunityRepo.save(opportunity);
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

//		List<String> salesIds = new ArrayList<>();
//		salesIds.add(opportunityMapper.getUserId());
//		if (null != opportunityMapper.getSalesUserIds() && !opportunityMapper.getSalesUserIds().isEmpty()) {
//			if (!opportunityMapper.getSalesUserIds().equals(opportunityMapper.getUserId())) {
//				salesIds.add(opportunityMapper.getSalesUserIds());
//			}
//			for (String salesUserId : salesIds) {
//
//				InvestorOppSalesUserLink opportunitySalesLink = new InvestorOppSalesUserLink();
//
//				opportunitySalesLink.setInvOpportunityId(investorOpportunity.getInvOpportunityId());
//				opportunitySalesLink.setEmployeeId(salesUserId);
//				opportunitySalesLink.setCreationDate(new Date());
//				opportunitySalesLink.setLiveInd(true);
//				investorOppSalesLinkRepo.save(opportunitySalesLink);
//			}
//		}

        /* insert to opportunity-contact-link */
        if (opportunityMapper.getContactId() != null && !opportunityMapper.getContactId().isEmpty()) {
            InvestorOppContactLink opportunityContactLink = new InvestorOppContactLink();
            opportunityContactLink.setContactId(opportunityMapper.getContactId());
            opportunityContactLink.setInvOpportunityId(investorOpportunity.getInvOpportunityId());
            opportunityContactLink.setCreationDate(new Date());

            investorOppContactLinkRepo.save(opportunityContactLink);
        }

        /* insert to investorOpp-included-link */
        if (opportunityMapper.getIncluded() != null && !opportunityMapper.getIncluded().isEmpty()) {
            for (String id : opportunityMapper.getIncluded()) {
                InvestorOppIncludedLink opportunityIncludedLink = new InvestorOppIncludedLink();
                opportunityIncludedLink.setInvestorOppId(investorOpportunity.getInvOpportunityId());
                opportunityIncludedLink.setEmployeeId(id);
                opportunityIncludedLink.setCreationDate(new Date());
                opportunityIncludedLink.setLiveInd(true);
                opportunityIncludedLink.setOrgId(opportunityMapper.getOrgId());

                investorOppIncludedRepository.save(opportunityIncludedLink);
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

        NotificationDetails notification = new NotificationDetails();
        notification.setNotificationType("Opportunity");
        notification.setOrg_id(opportunityMapper.getOrgId());
        notification.setUser_id(opportunityMapper.getUserId());
        EmployeeDetails emp = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());

        String middleName2 = " ";
        String lastName2 = "";
        String salutation1 = "";

        if (!StringUtils.isEmpty(emp.getLastName())) {

            lastName2 = emp.getLastName();
        }
        if (emp.getSalutation() != null && !emp.getSalutation().isEmpty()) {
            salutation1 = emp.getSalutation();
        }

        if (emp.getMiddleName() != null && !emp.getMiddleName().isEmpty()) {

            middleName2 = emp.getMiddleName();
            notification.setAssignedBy(salutation1 + " " + emp.getFirstName() + " " + middleName2 + " " + lastName2);
        } else {

            notification.setAssignedBy(salutation1 + " " + emp.getFirstName() + " " + lastName2);
        }

        String middleName1 = " ";
        String lastName1 = "";
        String salutation = "";

        if (!StringUtils.isEmpty(emp.getLastName())) {

            lastName1 = emp.getLastName();
        }
        if (emp.getSalutation() != null && !emp.getSalutation().isEmpty()) {
            salutation = emp.getSalutation();
        }

        if (emp.getMiddleName() != null && !emp.getMiddleName().isEmpty()) {

            middleName1 = emp.getMiddleName();
            notification.setMessage("An Investor_Opportunity is created By " + salutation + " " + emp.getFirstName()
                    + " " + middleName1 + " " + lastName1);
        } else {

            notification.setMessage(
                    "An Investor_Opportunity is created By " + salutation + " " + emp.getFirstName() + " " + lastName1);
        }

        notification.setAssignedTo(emp.getReportingManager());
        notification.setNotificationDate(new Date());
        notification.setMessageReadInd(false);
        notificationRepository.save(notification);

        Notes notes = new Notes();
        notes.setCreation_date(new Date());
        notes.setNotes(opportunityMapper.getDescription());
        notes.setLiveInd(true);
        Notes note = notesRepository.save(notes);

        String notesId = note.getNotes_id();

        /* insert to InvestorOppNotesLink table */
        InvestorOppNotesLink investor = new InvestorOppNotesLink();
        investor.setInvOpportunityId(investorOpportunity.getInvOpportunityId());
        investor.setNoteId(notesId);
        investor.setCreationDate(new Date());
        investorOppNoteLinkRepo.save(investor);

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

        InvestorOppStages oppStages = investorOppStagesRepo.getById(opportunityMapper.getOppStage());
        if (null != oppStages) {
            probability = oppStages.getProbability();
        }

        ConversionValueMapper mapper = oppertunityService.ConvertOppertunityProposalValueAndWeightedValue(
                opportunityMapper.getProposalAmount(), userCurrency, orgCurrency, opportunityMapper.getCurrency(),
                probability);
        if (null != mapper) {
            InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                    .findByInvestorId(opportunityMapper.getInvestorId());
            if (null != inOppConversionValue) {
                double userpValue = (inOppConversionValue.getUserConversionValue() + mapper.getUserConversionPValue());
                inOppConversionValue.setUserConversionValue(userpValue);
                double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                        + mapper.getUserConversionWValue());
                inOppConversionValue.setUserConversionWeightedValue(userwValue);
                double orgpValue = (inOppConversionValue.getOrgConversionValue() + mapper.getOrgConversionPValue());
                inOppConversionValue.setOrgConversionValue(orgpValue);
                double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                        + mapper.getOrgConversionWValue());
                inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                inOppConversionValue.setUserConversionCurrency(userCurrency);
                inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                inOppConversionValueRepo.save(inOppConversionValue);
            } else {
                InOppConversionValue inOppConversionValue1 = new InOppConversionValue();
                inOppConversionValue1.setCreationDate(new Date());
                inOppConversionValue1.setInvestorId(opportunityMapper.getInvestorId());
                inOppConversionValue1.setLiveInd(true);
                inOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
                inOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
                inOppConversionValue1.setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
                inOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
                inOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
                inOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
                inOppConversionValue1.setUserConversionWeightedValue(mapper.getUserConversionWValue());
                inOppConversionValue1.setUserId(opportunityMapper.getUserId());

                inOppConversionValueRepo.save(inOppConversionValue1);

            }
        }

    }

    @Override
    public InvestorOpportunityMapper getOpportunityDetails(String invOpportunityId) {
        InvestorOpportunityMapper opportunityViewMapper = new InvestorOpportunityMapper();
        if (null != invOpportunityId && !invOpportunityId.trim().isEmpty()) {

            InvestorOpportunity opportunity = investorOpportunityRepo
                    .getOpportunityDetailsByOpportunityId(invOpportunityId);

            System.out.println("Opportunity@@@@@@@@@&" + opportunity);
            if (null != opportunity) {
                opportunityViewMapper = getOpportunityRelatedDetails(opportunity);
            }
        }
        return opportunityViewMapper;

    }

    @Override
    public List<InvestorOpportunityMapper> getAllOpportunityList(int pageNo, int pageSize) {
        List<InvestorOpportunityMapper> resultMapper = new ArrayList<InvestorOpportunityMapper>();

        List<Permission> permission = permissionRepository.getUserListForOpportunity();
        System.out.println(" user$$$$$$$$$$$$==" + permission.toString());

        if (null != permission && !permission.isEmpty()) {
            permission.stream().map(permissionn -> {

                List<InvestorOpportunityMapper> mp = investorOppService
                        .getOpportunityDetailsListPageWiseByUserId(permissionn.getUserId(), pageNo, pageSize);

                System.out.println(" userId$$$$$$$$$$$$==" + permissionn.getUserId());

                resultMapper.addAll(mp);
                return resultMapper;
            }).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());

        }
        return resultMapper;

    }

    @Override
    public List<InvestorOpportunityMapper> getOpportunityDetailsListPageWiseByUserId(String userId, int pageNo,
                                                                                     int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
        List<InvestorOpportunityMapper> opportunities = new ArrayList<>();
        Page<InvestorOpportunity> investorOppSalesUserLink = investorOpportunityRepo
                .getOpenInvOpportunityDetailsListByUserId(userId, paging);
        if (null != investorOppSalesUserLink && !investorOppSalesUserLink.isEmpty()) {

            investorOppSalesUserLink.stream().filter(li -> (li != null)).map(li -> {
                InvestorOpportunity opportunityDetails = investorOpportunityRepo
                        .getOpenInvOpportunityDetailsByOpportunityId(li.getInvOpportunityId());
                InvestorOpportunityMapper mapper = null;
                if (null != opportunityDetails) {
                    mapper = getOpportunityRelatedDetails(opportunityDetails);
                    if (null != mapper) {
                        mapper.setPageCount(investorOppSalesUserLink.getTotalPages());
                        mapper.setDataCount(investorOppSalesUserLink.getSize());
                        mapper.setListCount(investorOppSalesUserLink.getTotalElements());
                        opportunities.add(mapper);
                    }
                    mapper.setCollectedAmount(getAmountSumByInvOpportunityId(li.getInvOpportunityId()));
                }
                return mapper;
            }).collect(Collectors.toList());
        }
        return opportunities;
    }

    private float getAmountSumByInvOpportunityId(String invOpportunityId) {
        List<FundContactLink> fundContactLinks = investorOpFundRepo.findByInvOpportunityId(invOpportunityId);
        return fundContactLinks.stream()
                .map(FundContactLink::getAmount)
                .reduce(0f, Float::sum);
    }

    private InvestorOpportunityMapper getOpportunityRelatedDetails(InvestorOpportunity opportunityDetails) {
        int openRecruitment = 0;
        int openPosition = 0;
        InvestorOpportunityMapper oppertunityMapper = new InvestorOpportunityMapper();

        if (null != opportunityDetails.getInvOpportunityId()) {

            oppertunityMapper.setInvOpportunityId(opportunityDetails.getInvOpportunityId());
            oppertunityMapper.setOpportunityName(opportunityDetails.getOpportunityName());

            oppertunityMapper.setProposalAmount(opportunityDetails.getProposalAmount());
            oppertunityMapper.setCurrency(opportunityDetails.getCurrency());
            oppertunityMapper.setUserId(opportunityDetails.getUserId());
            oppertunityMapper.setOrgId(opportunityDetails.getOrgId());
            oppertunityMapper.setStartDate(Utility.getISOFromDate(opportunityDetails.getStartDate()));
            oppertunityMapper.setEndDate(Utility.getISOFromDate(opportunityDetails.getEndDate()));
            oppertunityMapper.setDescription(opportunityDetails.getDescription());
//            oppertunityMapper.setOppInnitiative(opportunityDetails.getOppInnitiative());
            oppertunityMapper.setWonInd(opportunityDetails.isWonInd());
            oppertunityMapper.setLostInd(opportunityDetails.isLostInd());
            oppertunityMapper.setCloseInd(opportunityDetails.isCloseInd());
            oppertunityMapper.setNewDealNo(opportunityDetails.getNewDealNo());
            if (null != opportunityDetails.getCreationDate()) {

                oppertunityMapper.setCreationDate(Utility.getISOFromDate(opportunityDetails.getCreationDate()));

            }
            oppertunityMapper.setPaymentReceived(opportunityDetails.getPaymentReceived());
            if (null != opportunityDetails.getPaymentReceivedDate()) {
                oppertunityMapper.setPaymentReceivedDate(Utility.getISOFromDate(opportunityDetails.getPaymentReceivedDate()));
            }

            if (null != opportunityDetails.getWonDate()) {
                oppertunityMapper.setWonDate(Utility.getISOFromDate(opportunityDetails.getWonDate()));
            }

            if (null != opportunityDetails.getLostDate()) {
                oppertunityMapper.setLostDate(Utility.getISOFromDate(opportunityDetails.getLostDate()));
            }

            if (null != opportunityDetails.getCloseDate()) {
                oppertunityMapper.setCloseDate(Utility.getISOFromDate(opportunityDetails.getCloseDate()));
            }

//            List<OpportunityRecruiterLink> list = opportunityRecruiterLinkRepository
//                    .getRecruiterLinkByOppId(opportunityDetails.getOpportunityId());
//            if (null != list && !list.isEmpty()) {
//                List<RecruiterMapper> recruiterList = new ArrayList<RecruiterMapper>();
//                for (OpportunityRecruiterLink opportunityRecruiterLink : list) {
//
//                    RecruiterMapper recriuterMapper = new RecruiterMapper();
//                    EmployeeDetails employeeDetails = employeeRepository
//                            .getEmployeeDetailsByEmployeeId(opportunityRecruiterLink.getRecruiter_id());
//                    if (null != employeeDetails) {
//                        String middleName = " ";
//                        String lastName = "";
//
//                        if (!StringUtils.isEmpty(employeeDetails.getLastName())) {
//
//                            lastName = employeeDetails.getLastName();
//                        }
//                        if (!StringUtils.isEmpty(employeeDetails.getMiddleName())) {
//                            middleName = employeeDetails.getMiddleName();
//
//                        }
//                        recriuterMapper.setFullName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
//                        recriuterMapper.setEmployeeId(employeeDetails.getEmployeeId());
//                        recriuterMapper.setImageId(employeeDetails.getImageId());
//                    }
//                    recruiterList.add(recriuterMapper);
//                }
//                oppertunityMapper.setRecruiterDetails(recruiterList);
//
//            }
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
            if (opportunityDetails.getInvestorId() != null && !opportunityDetails.getInvestorId().isEmpty()) {
                Investor investor = investorRepository.getById(opportunityDetails.getInvestorId());
                if (null != investor) {
                    oppertunityMapper.setInvestorId(investor.getInvestorId());
                    oppertunityMapper.setInvestor(investor.getName());
                }
            } else {
                oppertunityMapper.setInvestorId("");

            }
            if (opportunityDetails.getContactId() != null && !opportunityDetails.getContactId().trim().isEmpty()) {
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

            if (opportunityDetails.getInvOpportunityId() != null
                    && !opportunityDetails.getInvOpportunityId().trim().isEmpty()) {
                List<String> includedIds = investorOppIncludedRepository
                        .findByInvestorOppId(opportunityDetails.getInvOpportunityId()).stream()
                        .map(InvestorOppIncludedLink::getEmployeeId).collect(Collectors.toList());
                List<EmployeeShortMapper> included = new ArrayList<>();
                if (null != includedIds && !includedIds.isEmpty()) {
                    for (String includedId : includedIds) {
                        EmployeeShortMapper employeeMapper = employeeService
                                .getEmployeeFullNameAndEmployeeId(includedId);
                        included.add(employeeMapper);
                    }
                    oppertunityMapper.setInclude(included);
                }

//				List<String> opp = investorOppSalesLinkRepo
//						.getSalesUsersByOppId(opportunityDetails.getInvOpportunityId()).stream()
//						.map(InvestorOppSalesUserLink::getEmployeeId).collect(Collectors.toList());
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

            InvestorOppWorkflow workflowDetails = investorOppWorkflowRepo.getById(opportunityDetails.getOppWorkflow());
            if (null != workflowDetails) {
                oppertunityMapper.setOppWorkflow(workflowDetails.getWorkflowName());
                oppertunityMapper.setOppWorkflowId(workflowDetails.getInvestorOppWorkflowId());
            }

            InvestorOppStages oppStages = investorOppStagesRepo.getById(opportunityDetails.getOppStage());
            if (null != oppStages) {
                oppertunityMapper.setOppStage(oppStages.getStageName());
                oppertunityMapper.setProbability(oppStages.getProbability());
                oppertunityMapper.setInvOpportunityStagesId(oppStages.getInvestorOppStagesId());
            }

            Source source = sourceRepository.findBySourceId(opportunityDetails.getSource());
            if (null != source) {
                oppertunityMapper.setSourceName(source.getName());
                oppertunityMapper.setSource(source.getSourceId());
            }

            oppertunityMapper.setStageList(
                    investorOppWorkflowService.getStagesByInvOppworkFlowId(opportunityDetails.getOppWorkflow()));
//
//            List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
//                    .getOpenRecriutmentsByOpportunityIdAndCloseInd(opportunityDetails.getOpportunityId());
//            if (null != recruitList && !recruitList.isEmpty()) {
//                for (OpportunityRecruitDetails opportunityRecruitDetails : recruitList) {
//
//                    System.out.println("recruitmentId=" + opportunityRecruitDetails.getRecruitment_id());
//                    List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
//                            .getProfileDetailByRecruitmentIdAndonBoardInd(
//                                    opportunityRecruitDetails.getRecruitment_id());
//                    if (opportunityRecruitDetails.isCloseInd() == false) {
//                        System.out.println("start::::::::::::2");
//                        int profileSize = profileList.size();
//                        int positionSize = (int) opportunityRecruitDetails.getNumber();
//                        System.out.println("profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
//                        if (profileSize < positionSize) {
//                            openRecruitment++;
//                            openPosition = positionSize - profileSize;
//                            System.out.println("openPosition=============inner===" + openPosition);
//                        }
//                        if (recruitList.size() > 1) {
//                            openPosition += openPosition;
//                            System.out.println("openPosition=============outer===" + openPosition);
//                        }
//                    }
//                }
//                System.out.println("openRecruitment=============outer===" + openRecruitment);
//
//            }
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
    public InvestorOpportunityMapper updateInvOpportunityDetails(String invOpportunityId,
                                                                 InvestorOpportunityMapper opportunityMapper) throws TemplateException, IOException {
        InvestorOpportunity opportunityDetails = investorOpportunityRepo
                .getOpportunityDetailsByOpportunityId(invOpportunityId);
        InvestorOpportunityMapper opportunityViewMapper = null;
        if (null != opportunityDetails) {
            if (null != opportunityMapper.getOpportunityName()) {
                opportunityDetails.setOpportunityName(opportunityMapper.getOpportunityName());
            }
//        } else {
//            opportunityDetails.setOpportunityName(opportunity.getOpportunityName());
//        }
            if (null != opportunityMapper.getProposalAmount()) {
                opportunityDetails.setProposalAmount(opportunityMapper.getProposalAmount());
            }
            if (null != opportunityMapper.getSource()) {
                opportunityDetails.setSource(opportunityMapper.getSource());
            }
//        } else {
//            opportunityDetails.setProposalAmount(opportunity.getProposalAmount());
//        }
            if (null != opportunityMapper.getCurrency()) {
                opportunityDetails.setCurrency(opportunityMapper.getCurrency());
            }
//        } else {
//            opportunityDetails.setCurrency(opportunity.getCurrency());
//        }

            if (null != opportunityMapper.getInvestorId()) {
                opportunityDetails.setInvestorId(opportunityMapper.getInvestorId());
            }
//        } else {
//            opportunityDetails.setCustomerId(opportunity.getCustomerId());
//        }
//        if (null != opportunityMapper.getOppInnitiative()) {
//            opportunityDetails.setOppInnitiative(opportunityMapper.getOppInnitiative());
//        } else {
//            opportunityDetails.setOppInnitiative(opportunity.getOppInnitiative());
//        }

            if (null != opportunityMapper.getContactId()) {
                opportunityDetails.setContactId(opportunityMapper.getContactId());
                if (!StringUtils.isEmpty(opportunityMapper.getContactId())) {
                    InvestorOppContactLink opportunityContactLink = investorOppContactLinkRepo
                            .findByContactIdAndInvOpportunityId(opportunityDetails.getContactId(), invOpportunityId);
                    if (null != opportunityContactLink) {
                        opportunityContactLink.setContactId(opportunityMapper.getContactId());
                        investorOppContactLinkRepo.save(opportunityContactLink);
                    }
                } else {
                    InvestorOppContactLink newOpportunityContactLink = new InvestorOppContactLink();
                    newOpportunityContactLink.setContactId(opportunityMapper.getContactId());
                    newOpportunityContactLink.setInvOpportunityId(invOpportunityId);
                    newOpportunityContactLink.setCreationDate(new Date());
                }
            }
//        } else {
//            opportunityDetails.setContactId(opportunity.getContactId());
//        }

            if (null != opportunityMapper.getUserId()) {
                opportunityDetails.setUserId(opportunityMapper.getUserId());
            }
//        else {
//            opportunityDetails.setUserId(opportunity.getUserId());
//        }

            if (null != opportunityMapper.getOrgId()) {
                opportunityDetails.setOrgId(opportunityMapper.getOrgId());
            }
//                else {
//                    opportunityDetails.setOrgId(opportunity.getOrgId());
//                }
            if (null != opportunityMapper.getDescription()) {

                List<InvestorOppNotesLink> list = investorOppNoteLinkRepo
                        .getNoteListByInvOpportunityId(invOpportunityId);
                if (null != list && !list.isEmpty()) {
                    list.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
                    Notes notes = notesRepository.findByNoteId(list.get(0).getNoteId());
                    if (null != notes) {
                        notes.setNotes(opportunityMapper.getDescription());
                        notesRepository.save(notes);
                    }
                }

            }
//                else {
//                    opportunityDetails.setDescription(opportunity.getDescription());
//                }
            if (null != opportunityMapper.getOppStage()) {
                opportunityDetails.setOppStage(opportunityMapper.getOppStage());
            }
//                else {
//                    opportunityDetails.setOppStage(opportunity.getOppStage());
//                }
            if (null != opportunityMapper.getOppWorkflow()) {
                opportunityDetails.setOppWorkflow(opportunityMapper.getOppWorkflow());
            }
//                else {
//                    opportunityDetails.setOppWorkflow(opportunity.getOppWorkflow());
//                }

            try {

                if (null != opportunityMapper.getStartDate()) {
                    opportunityDetails.setStartDate(
                            Utility.removeTime(Utility.getDateFromISOString(opportunityMapper.getStartDate())));
                }
//                    else {
//                        opportunityDetails.setStartDate(opportunity.getStartDate());
//                    }
                if (null != opportunityMapper.getEndDate()) {
                    opportunityDetails.setEndDate(
                            Utility.removeTime(Utility.getDateFromISOString(opportunityMapper.getEndDate())));
                }
//                    else {
//                        opportunityDetails.setEndDate(opportunity.getEndDate());
//                    }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//                opportunityDetails.setCreationDate(opportunity.getCreationDate());
//                opportunityDetails.setLiveInd(true);
            opportunityDetails.setReinstateInd(true);
//                opportunityDetails.setCreationDate(opportunity.getCreationDate());

//                CustomerRecruitUpdate dbCustomerRecruitUpdate = customerRecruitUpdateRepository
//                        .findByCustomerId(opportunityMapper.getCustomerId());
//                if (dbCustomerRecruitUpdate != null) {
//                    dbCustomerRecruitUpdate.setCustomerId(opportunityMapper.getCustomerId());
//                    dbCustomerRecruitUpdate.setContactId(opportunityMapper.getContactId());
//                    dbCustomerRecruitUpdate.setUpdatedDate(new Date());
//                    customerRecruitUpdateRepository.save(dbCustomerRecruitUpdate);
//                } else {
//                    CustomerRecruitUpdate cusRecruitUpdate = new CustomerRecruitUpdate();
//                    cusRecruitUpdate.setCustomerId(opportunityMapper.getCustomerId());
//                    cusRecruitUpdate.setContactId(opportunityMapper.getContactId());
//                    cusRecruitUpdate.setUpdatedDate(new Date());
//                    customerRecruitUpdateRepository.save(cusRecruitUpdate);
//                }
            // Edit AssignedTo
            /*
             * List<InvestorOppSalesUserLink> opportunitySalesUserLink =
             * investorOppSalesLinkRepo .getSalesUsersByOppId(invOpportunityId); if (null !=
             * opportunitySalesUserLink && !opportunitySalesUserLink.isEmpty()) { for
             * (InvestorOppSalesUserLink opportunitySalesUserLink2 :
             * opportunitySalesUserLink) {
             * investorOppSalesLinkRepo.delete(opportunitySalesUserLink2); } } List<String>
             * salesIds = new ArrayList<>(); salesIds.add(opportunityMapper.getUserId()); if
             * (null != opportunityMapper.getSalesUserIds() &&
             * !opportunityMapper.getSalesUserIds().isEmpty()) { if
             * (!opportunityMapper.getSalesUserIds().equals(opportunityMapper.getUserId()))
             * { salesIds.add(opportunityMapper.getSalesUserIds()); } for (String
             * salesUserId : salesIds) {
             *
             * InvestorOppSalesUserLink opportunitySalesLink = new
             * InvestorOppSalesUserLink();
             *
             * opportunitySalesLink.setInvOpportunityId(invOpportunityId);
             * opportunitySalesLink.setEmployeeId(salesUserId);
             * opportunitySalesLink.setCreationDate(new Date());
             * opportunitySalesLink.setLiveInd(true);
             * investorOppSalesLinkRepo.save(opportunitySalesLink); } }
             */
            if (null != opportunityMapper.getSalesUserIds()) {
                opportunityDetails.setAssignedTo(opportunityMapper.getSalesUserIds());
            }

            if (null != opportunityMapper.getSalesUserIds()) {
                opportunityDetails.setAssignedBy(opportunityMapper.getUserId());
            }
            // Edit Include
            List<InvestorOppIncludedLink> investoroppIncludedLink = investorOppIncludedRepository
                    .findByInvestorOppId(invOpportunityId);
            if (null != investoroppIncludedLink && !investoroppIncludedLink.isEmpty()) {
                for (InvestorOppIncludedLink investorOppIncludedLink : investoroppIncludedLink) {
                    investorOppIncludedRepository.delete(investorOppIncludedLink);
                }
            }

            if (opportunityMapper.getIncluded() != null && !opportunityMapper.getContactId().isEmpty()) {
                for (String id : opportunityMapper.getIncluded()) {
                    InvestorOppIncludedLink opportunityIncludedLink2 = new InvestorOppIncludedLink();
                    opportunityIncludedLink2.setInvestorOppId(invOpportunityId);
                    opportunityIncludedLink2.setEmployeeId(id);
                    opportunityIncludedLink2.setCreationDate(new Date());
                    opportunityIncludedLink2.setLiveInd(true);
                    opportunityIncludedLink2.setOrgId(opportunityMapper.getOrgId());

                    investorOppIncludedRepository.save(opportunityIncludedLink2);
                }
            }

            if (opportunityMapper.getInvestorId().equalsIgnoreCase(opportunityDetails.getInvestorId())) {
                if (opportunityMapper.getProposalAmount().equalsIgnoreCase(opportunityDetails.getProposalAmount())) {
                    if (opportunityMapper.getOppStage().equalsIgnoreCase(opportunityDetails.getOppStage())) {
                        if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunityDetails.getCurrency())) {

                        } else {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);
                            ConversionValueMapper previous = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityDetails.getCurrency(), probability);

                            if (null != previous) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - previous.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - previous.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - previous.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - previous.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                        }
                    } else {
                        if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunityDetails.getCurrency())) {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            double previousProbability1 = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            InvestorOppStages oppStages1 = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages1) {
                                previousProbability1 = oppStages1.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);
                            ConversionValueMapper previous = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), previousProbability1);

                            if (null != previous) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - previous.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - previous.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - previous.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - previous.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                        } else {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            double previousProbability1 = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            InvestorOppStages oppStages1 = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages1) {
                                previousProbability1 = oppStages1.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);
                            ConversionValueMapper previous = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityDetails.getCurrency(), previousProbability1);

                            if (null != previous) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - previous.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - previous.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - previous.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - previous.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                        }
                    }
                } else {
                    if (opportunityMapper.getOppStage().equalsIgnoreCase(opportunityDetails.getOppStage())) {
                        if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunityDetails.getCurrency())) {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);
                            ConversionValueMapper previous = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityDetails.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);

                            if (null != previous) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - previous.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - previous.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - previous.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - previous.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                        } else {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);
                            ConversionValueMapper previous = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityDetails.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityDetails.getCurrency(), probability);

                            if (null != previous) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - previous.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - previous.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - previous.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - previous.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                        }
                    } else {
                        if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunityDetails.getCurrency())) {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            double previousProbability1 = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            InvestorOppStages oppStages1 = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages1) {
                                previousProbability1 = oppStages1.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);
                            ConversionValueMapper previous = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityDetails.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), previousProbability1);

                            if (null != previous) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - previous.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - previous.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - previous.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - previous.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                        } else {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            double previousProbability1 = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            InvestorOppStages oppStages1 = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages1) {
                                previousProbability1 = oppStages1.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);
                            ConversionValueMapper previous = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityDetails.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityDetails.getCurrency(), previousProbability1);

                            if (null != previous) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - previous.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - previous.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - previous.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - previous.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                        }
                    }
                }
            } else {
                if (opportunityMapper.getProposalAmount().equalsIgnoreCase(opportunityDetails.getProposalAmount())) {
                    if (opportunityMapper.getOppStage().equalsIgnoreCase(opportunityDetails.getOppStage())) {
                        if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunityDetails.getCurrency())) {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);

                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityDetails.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                } else {
                                    InOppConversionValue inOppConversionValue1 = new InOppConversionValue();
                                    inOppConversionValue1.setCreationDate(new Date());
                                    inOppConversionValue1.setInvestorId(opportunityMapper.getInvestorId());
                                    inOppConversionValue1.setLiveInd(true);
                                    inOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
                                    inOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
                                    inOppConversionValue1
                                            .setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
                                    inOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
                                    inOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
                                    inOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
                                    inOppConversionValue1
                                            .setUserConversionWeightedValue(mapper.getUserConversionWValue());
                                    inOppConversionValue1.setUserId(opportunityMapper.getUserId());

                                    inOppConversionValueRepo.save(inOppConversionValue1);

                                }
                            }
                        } else {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);
                            ConversionValueMapper previous = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityDetails.getCurrency(), probability);

                            if (null != previous) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityDetails.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - previous.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - previous.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - previous.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - previous.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                } else {
                                    InOppConversionValue inOppConversionValue1 = new InOppConversionValue();
                                    inOppConversionValue1.setCreationDate(new Date());
                                    inOppConversionValue1.setInvestorId(opportunityMapper.getInvestorId());
                                    inOppConversionValue1.setLiveInd(true);
                                    inOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
                                    inOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
                                    inOppConversionValue1
                                            .setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
                                    inOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
                                    inOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
                                    inOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
                                    inOppConversionValue1
                                            .setUserConversionWeightedValue(mapper.getUserConversionWValue());
                                    inOppConversionValue1.setUserId(opportunityMapper.getUserId());

                                    inOppConversionValueRepo.save(inOppConversionValue1);

                                }
                            }
                        }
                    } else {
                        if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunityDetails.getCurrency())) {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            double previousProbability1 = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            InvestorOppStages oppStages1 = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages1) {
                                previousProbability1 = oppStages1.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);
                            ConversionValueMapper previous = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), previousProbability1);

                            if (null != previous) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityDetails.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - previous.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - previous.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - previous.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - previous.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                } else {
                                    InOppConversionValue inOppConversionValue1 = new InOppConversionValue();
                                    inOppConversionValue1.setCreationDate(new Date());
                                    inOppConversionValue1.setInvestorId(opportunityMapper.getInvestorId());
                                    inOppConversionValue1.setLiveInd(true);
                                    inOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
                                    inOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
                                    inOppConversionValue1
                                            .setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
                                    inOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
                                    inOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
                                    inOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
                                    inOppConversionValue1
                                            .setUserConversionWeightedValue(mapper.getUserConversionWValue());
                                    inOppConversionValue1.setUserId(opportunityMapper.getUserId());

                                    inOppConversionValueRepo.save(inOppConversionValue1);

                                }
                            }
                        } else {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            double previousProbability1 = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            InvestorOppStages oppStages1 = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages1) {
                                previousProbability1 = oppStages1.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);
                            ConversionValueMapper previous = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityDetails.getCurrency(), previousProbability1);

                            if (null != previous) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityDetails.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - previous.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - previous.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - previous.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - previous.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                } else {
                                    InOppConversionValue inOppConversionValue1 = new InOppConversionValue();
                                    inOppConversionValue1.setCreationDate(new Date());
                                    inOppConversionValue1.setInvestorId(opportunityMapper.getInvestorId());
                                    inOppConversionValue1.setLiveInd(true);
                                    inOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
                                    inOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
                                    inOppConversionValue1
                                            .setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
                                    inOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
                                    inOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
                                    inOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
                                    inOppConversionValue1
                                            .setUserConversionWeightedValue(mapper.getUserConversionWValue());
                                    inOppConversionValue1.setUserId(opportunityMapper.getUserId());

                                    inOppConversionValueRepo.save(inOppConversionValue1);

                                }
                            }
                        }
                    }
                } else {
                    if (opportunityMapper.getOppStage().equalsIgnoreCase(opportunityDetails.getOppStage())) {
                        if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunityDetails.getCurrency())) {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);
                            ConversionValueMapper previous = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityDetails.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);

                            if (null != previous) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityDetails.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - previous.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - previous.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - previous.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - previous.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                } else {
                                    InOppConversionValue inOppConversionValue1 = new InOppConversionValue();
                                    inOppConversionValue1.setCreationDate(new Date());
                                    inOppConversionValue1.setInvestorId(opportunityMapper.getInvestorId());
                                    inOppConversionValue1.setLiveInd(true);
                                    inOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
                                    inOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
                                    inOppConversionValue1
                                            .setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
                                    inOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
                                    inOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
                                    inOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
                                    inOppConversionValue1
                                            .setUserConversionWeightedValue(mapper.getUserConversionWValue());
                                    inOppConversionValue1.setUserId(opportunityMapper.getUserId());

                                    inOppConversionValueRepo.save(inOppConversionValue1);

                                }
                            }
                        } else {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);
                            ConversionValueMapper previous = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityDetails.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityDetails.getCurrency(), probability);

                            if (null != previous) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityDetails.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - previous.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - previous.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - previous.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - previous.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                } else {
                                    InOppConversionValue inOppConversionValue1 = new InOppConversionValue();
                                    inOppConversionValue1.setCreationDate(new Date());
                                    inOppConversionValue1.setInvestorId(opportunityMapper.getInvestorId());
                                    inOppConversionValue1.setLiveInd(true);
                                    inOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
                                    inOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
                                    inOppConversionValue1
                                            .setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
                                    inOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
                                    inOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
                                    inOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
                                    inOppConversionValue1
                                            .setUserConversionWeightedValue(mapper.getUserConversionWValue());
                                    inOppConversionValue1.setUserId(opportunityMapper.getUserId());

                                    inOppConversionValueRepo.save(inOppConversionValue1);

                                }
                            }
                        }
                    } else {
                        if (opportunityMapper.getCurrency().equalsIgnoreCase(opportunityDetails.getCurrency())) {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            double previousProbability1 = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            InvestorOppStages oppStages1 = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages1) {
                                previousProbability1 = oppStages1.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);
                            ConversionValueMapper previous = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityDetails.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), previousProbability1);

                            if (null != previous) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityDetails.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - previous.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - previous.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - previous.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - previous.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                } else {
                                    InOppConversionValue inOppConversionValue1 = new InOppConversionValue();
                                    inOppConversionValue1.setCreationDate(new Date());
                                    inOppConversionValue1.setInvestorId(opportunityMapper.getInvestorId());
                                    inOppConversionValue1.setLiveInd(true);
                                    inOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
                                    inOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
                                    inOppConversionValue1
                                            .setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
                                    inOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
                                    inOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
                                    inOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
                                    inOppConversionValue1
                                            .setUserConversionWeightedValue(mapper.getUserConversionWValue());
                                    inOppConversionValue1.setUserId(opportunityMapper.getUserId());

                                    inOppConversionValueRepo.save(inOppConversionValue1);

                                }
                            }
                        } else {
                            String userCurrency = null;
                            String orgCurrency = null;
                            double probability = 0;
                            double previousProbability1 = 0;
                            EmployeeDetails emp1 = employeeRepository
                                    .getEmployeesByuserId(opportunityMapper.getUserId());
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
                            InvestorOppStages oppStages = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages) {
                                probability = oppStages.getProbability();
                            }

                            InvestorOppStages oppStages1 = investorOppStagesRepo
                                    .getById(opportunityMapper.getOppStage());
                            if (null != oppStages1) {
                                previousProbability1 = oppStages1.getProbability();
                            }

                            ConversionValueMapper mapper = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityMapper.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityMapper.getCurrency(), probability);
                            ConversionValueMapper previous = oppertunityService
                                    .ConvertOppertunityProposalValueAndWeightedValue(
                                            opportunityDetails.getProposalAmount(), userCurrency, orgCurrency,
                                            opportunityDetails.getCurrency(), previousProbability1);

                            if (null != previous) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityDetails.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            - previous.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            - previous.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            - previous.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            - previous.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                }
                            }
                            if (null != mapper) {
                                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                                        .findByInvestorId(opportunityMapper.getInvestorId());
                                if (null != inOppConversionValue) {
                                    double userpValue = (inOppConversionValue.getUserConversionValue()
                                            + mapper.getUserConversionPValue());
                                    inOppConversionValue.setUserConversionValue(userpValue);
                                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                                            + mapper.getUserConversionWValue());
                                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                                            + mapper.getOrgConversionPValue());
                                    inOppConversionValue.setOrgConversionValue(orgpValue);
                                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                                            + mapper.getOrgConversionWValue());
                                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                                    inOppConversionValue.setUserConversionCurrency(userCurrency);
                                    inOppConversionValue.setOrgConversionCurrency(orgCurrency);
                                    inOppConversionValueRepo.save(inOppConversionValue);
                                } else {
                                    InOppConversionValue inOppConversionValue1 = new InOppConversionValue();
                                    inOppConversionValue1.setCreationDate(new Date());
                                    inOppConversionValue1.setInvestorId(opportunityMapper.getInvestorId());
                                    inOppConversionValue1.setLiveInd(true);
                                    inOppConversionValue1.setOrgConversionCurrency(mapper.getOrgConversionCurrency());
                                    inOppConversionValue1.setOrgConversionValue(mapper.getOrgConversionPValue());
                                    inOppConversionValue1
                                            .setOrgConversionWeightedValue(mapper.getOrgConversionWValue());
                                    inOppConversionValue1.setOrgId(opportunityMapper.getOrgId());
                                    inOppConversionValue1.setUserConversionCurrency(mapper.getUserConversionCurrency());
                                    inOppConversionValue1.setUserConversionValue(mapper.getUserConversionPValue());
                                    inOppConversionValue1
                                            .setUserConversionWeightedValue(mapper.getUserConversionWValue());
                                    inOppConversionValue1.setUserId(opportunityMapper.getUserId());

                                    inOppConversionValueRepo.save(inOppConversionValue1);

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
            InvestorOpportunity updateOpportunity = investorOpportunityRepo.save(opportunityDetails);

            /*insert to Notification table*/
            Notificationparam param = new Notificationparam();
            EmployeeDetails emp = employeeRepository.getEmployeesByuserId(opportunityMapper.getUserId());
            String name = employeeService.getEmployeeFullNameByObject(emp);
            param.setEmployeeDetails(emp);
            param.setAdminMsg("Deal " + "'" + opportunityMapper.getOpportunityName() + "' updated by " + name);
            param.setOwnMsg("Deal " + opportunityMapper.getOpportunityName() + " updated.");
            param.setNotificationType("Deal updated.");
            param.setProcessNmae("Deal");
            param.setType("update");
            param.setCompanyName(companyName);
            param.setEmailSubject("Korero alert- Deal updated.");
            param.setCompanyName("Korero");
            param.setUserId(opportunityMapper.getUserId());

            if (!opportunityMapper.getUserId().equals(opportunityMapper.getAssignedTo())) {
                List<String> assignToUserIds = new ArrayList<>();
                assignToUserIds.add(opportunityMapper.getAssignedTo());
                param.setAssignToUserIds(assignToUserIds);
                param.setAssignToMsg("Deal " + "'" + opportunityMapper.getOpportunityName() + "'" + " updated by " + name);
            } else {
                List<String> assignToUserIds = new ArrayList<>();
                assignToUserIds.add(opportunityMapper.getAssignedTo());
                param.setAssignToUserIds(assignToUserIds);
                param.setAssignToMsg("Deal " + "'" + opportunityMapper.getOpportunityName() + "'" + " assigned to " + employeeService.getEmployeeFullName(opportunityMapper.getAssignedTo()) + " by " + name);
            }
            notificationService.createNotificationForDynamicUsers(param);

            opportunityViewMapper = getOpportunityRelatedDetails(updateOpportunity);
        }
        return opportunityViewMapper;
    }

    @Override
    public String saveInvOpportunityNotes(NotesMapper notesMapper) {
        String notesId = null;
        if (null != notesMapper) {
            Notes notes = new Notes();
            notes.setNotes(notesMapper.getNotes());
            notes.setCreation_date(new Date());
            notes.setUserId(notesMapper.getUserId());
            notes.setLiveInd(true);
            Notes note = notesRepository.save(notes);
            notesId = note.getNotes_id();

            /* insert to customer-notes-link */

            InvestorOppNotesLink opportunityNotesLink = new InvestorOppNotesLink();

            opportunityNotesLink.setInvOpportunityId(notesMapper.getInvOpportunityId());
            opportunityNotesLink.setNoteId(notesId);
            opportunityNotesLink.setCreationDate(new Date());

            investorOppNoteLinkRepo.save(opportunityNotesLink);

        }
        return null;
    }

    @Override
    public List<NotesMapper> getNoteListByInvOpportunityId(String invOpportunityId) {
        List<InvestorOppNotesLink> opportunityNotesLinkList = investorOppNoteLinkRepo
                .getNoteListByInvOpportunityId(invOpportunityId);
        List<NotesMapper> resultList = new ArrayList<NotesMapper>();

        if (opportunityNotesLinkList != null && !opportunityNotesLinkList.isEmpty()) {
            opportunityNotesLinkList.stream().map(opportunityNotesLink -> {

                NotesMapper notesMapper = getNotes(opportunityNotesLink.getNoteId());
                resultList.add(notesMapper);
                return resultList;
            }).collect(Collectors.toList());

        }

        return resultList;
    }

    @Override
    public NotesMapper getNotes(String id) {
        Notes notes = notesRepository.findByNoteId(id);
        NotesMapper notesMapper = new NotesMapper();
        if (null != notes) {
            notesMapper.setNotesId(notes.getNotes_id());
            notesMapper.setNotes(notes.getNotes());
            notesMapper.setCreationDate(Utility.getISOFromDate(notes.getCreation_date()));
            notesMapper.setOwnerName(employeeService.getEmployeeFullName(notes.getUserId()));

            System.out.println("opportunity notes.........." + notesMapper.toString());
        }
        return notesMapper;
    }

    @Override
    public List<InvestorOpportunityMapper> getInvOpportunityDetailsListByContactId(String contactId) {
        List<InvestorOpportunity> opportunityList = investorOpportunityRepo
                .getOpportunityListByContactIdAndLiveInd(contactId);

        List<InvestorOpportunityMapper> mapperList = new ArrayList<>();
        opportunityList.stream().map(opportunityDetails -> {

            InvestorOpportunityMapper opportunityMapper = getOpportunityRelatedDetails(opportunityDetails);
            opportunityMapper.setContactId(contactId);

            mapperList.add(opportunityMapper);
            return mapperList;
        }).collect(Collectors.toList());

        return mapperList;
    }

    @Override
    public List<DocumentMapper> getDocumentDetailsListByinvOpportunityId(String invOpportunityId) {
        List<InvestorOppDocsLink> opportunityDocumentLinkList = investorOppDocsLinkRepo
                .getByInvOpportunityId(invOpportunityId);
        List<DocumentMapper> resultList = new ArrayList<DocumentMapper>();
        Set<String> documentIds = opportunityDocumentLinkList.stream().map(InvestorOppDocsLink::getDocumentId).collect(Collectors.toSet());
        if (documentIds != null && !documentIds.isEmpty()) {
            documentIds.stream().map(documentId -> {
                DocumentMapper documentMapper = documentService.getDocument(documentId);
                resultList.add(documentMapper);
                return resultList;
            }).collect(Collectors.toList());

        }

        return resultList;
    }

    @Override
    public List<InvestorOpportunityMapper> getInvOpportunityDetailsByName(String opportunityName) {
        List<InvestorOpportunity> list = investorOpportunityRepo
                .findByOpportunityNameContainingAndLiveInd(opportunityName, true);

        List<InvestorOpportunityMapper> resultList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            list.stream().map(opportunityDetails -> {
                InvestorOpportunityMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
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
    public ContactViewMapper saveInvOpportunityContact(ContactMapper contactMapper) throws IOException, TemplateException {

        return contactService.saveContact(contactMapper);
    }

    @Override
    public List<ContactViewMapper> getContactListByInvOpportunityId(String invOpportunityId) {
        List<InvestorOppContactLink> opportunityContactLinkList = investorOppContactLinkRepo
                .getByInvOpportunityId(invOpportunityId);
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
    public List<InvestorOpportunityMapper> getInvOpportunityOfASalesUser(String userId) {
        List<InvestorOppSalesUserLink> list = investorOppSalesLinkRepo.getSalesUserLinkByUserId(userId);
        System.out.println("list@@@@@@@@@" + list);
        List<InvestorOpportunityMapper> mapperList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {

            mapperList = list.stream().map(li -> getOpportunityDetails(li.getInvOpportunityId()))
                    .collect(Collectors.toList());
        }
        return mapperList;
    }

    @Override
    public HashMap getInvestorOpportunityCountByUserId(String userId) {
        List<InvestorOpportunity> opportunityDetailsList = investorOpportunityRepo
                .getOpportunityListByUserIdAndLiveIndAndReinstateIndAndCloseIndAndLostInd(userId);
        HashMap map = new HashMap();
        map.put("opportunityDetails", opportunityDetailsList.size());
        return map;
    }

    @Override
    public HashMap getInvestorOpportunityCountByOrgId(String orgId) {
        List<InvestorOpportunity> opportunityDetailsList = investorOpportunityRepo
                .getOpenInvOpportunityDetailsListByOrgIdAndLiveInd(orgId);
        HashMap map = new HashMap();
        map.put("opportunityDetails", opportunityDetailsList.size());
        return map;
    }

    @Override
    public List<InvestorOpportunityMapper> getInvOpportunityDetailsListByUserId(String userId) {
        List<InvestorOpportunityMapper> opportunities = new ArrayList<>();
        List<InvestorOpportunity> opportunityList = investorOpportunityRepo
                .getOpportunityListByUserIdAndLiveIndAndReinstateIndAndCloseIndAndLostInd(userId);

        if (null != opportunityList && !opportunityList.isEmpty()) {

            opportunities = opportunityList.stream().filter(Objects::nonNull).map(this::getOpportunityRelatedDetails)
                    .collect(Collectors.toList());
        }

        return opportunities;
    }

    @Override
    public HashMap getCloseOpportunityCountByUserIdAndDateRange(String userId, String startDate, String endDate) {
        Date end_date = null;
        Date start_date = null;
        try {
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<InvestorOpportunity> opportunityDetailsList = investorOpportunityRepo
                .getClosedInvOpportunityDetailsUserIdWithDateRange(userId, start_date, end_date);
        HashMap map = new HashMap();
        map.put("closedOpportunity", opportunityDetailsList.size());

        return map;
    }

    @Override
    public HashMap getAddedOpportunityCountByUserIdAndDateRange(String userId, String startDate, String endDate) {
        Date end_date = null;
        Date start_date = null;
        try {
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<InvestorOpportunity> opportunityDetailsList = investorOpportunityRepo
                .getAddedInvOpportunityDetailsUserIdWithDateRange(userId, start_date, end_date);
        HashMap map = new HashMap();
        map.put("opportunityAdded", opportunityDetailsList.size());

        return map;
    }

    @Override
    public List<InvestorOpportunityMapper> getOpenOpportunitiesByUserIdDateRange(String userId, String startDate,
                                                                                 String endDate) {
        Date end_date = null;
        Date start_date = null;
        try {
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<InvestorOpportunity> opportunityDetailsList = investorOpportunityRepo
                .getAddedInvOpportunityDetailsUserIdWithDateRange(userId, start_date, end_date);
        return opportunityDetailsList.stream().map(this::getOpportunityRelatedDetails).collect(Collectors.toList());
    }

    @Override
    public List<InvestorOpportunityMapper> getClosedOpportunitiesByUserIdDateRange(String userId, String startDate,
                                                                                   String endDate) {
        Date end_date = null;
        Date start_date = null;
        try {
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<InvestorOpportunity> opportunityDetailsList = investorOpportunityRepo
                .getClosedInvOpportunityDetailsUserIdWithDateRange(userId, start_date, end_date);

        return opportunityDetailsList.stream().map(this::getOpportunityRelatedDetails).collect(Collectors.toList());
    }

    @Override
    public void deleteOpportunityDetailsById(String id) throws TemplateException, IOException {
        if (null != id) {
            InvestorOpportunity opportunityDetails = investorOpportunityRepo.getById(id);
            if (null != opportunityDetails) {
                // opportunityDetails.setLiveInd(false);
                opportunityDetails.setReinstateInd(false);
                InvestorOpportunity investorOpportunity = investorOpportunityRepo.save(opportunityDetails);

                /*insert to Notification table*/
                Notificationparam param = new Notificationparam();
                EmployeeDetails emp = employeeRepository.getEmployeesByuserId(investorOpportunity.getUserId());
                String name = employeeService.getEmployeeFullNameByObject(emp);
                param.setEmployeeDetails(emp);
                param.setAdminMsg("Deal " + "'" + investorOpportunity.getOpportunityName() + "' deleted by " + name);
                param.setOwnMsg("Deal " + investorOpportunity.getOpportunityName() + " deleted.");
                param.setNotificationType("Deal deleted");
                param.setProcessNmae("Deal");
                param.setType("deleted");
                param.setCompanyName(companyName);
                param.setEmailSubject("Korero alert- Deal deleted");
                param.setCompanyName("Korero");
                param.setUserId(investorOpportunity.getUserId());

                if (!investorOpportunity.getUserId().equals(investorOpportunity.getAssignedTo())) {
                    List<String> assignToUserIds = new ArrayList<>();
                    assignToUserIds.add(investorOpportunity.getAssignedTo());
                    param.setAssignToUserIds(assignToUserIds);
                    param.setAssignToMsg("Deal " + "'" + investorOpportunity.getOpportunityName() + "' deleted by " + name);
                }
                notificationService.createNotificationForDynamicUsers(param);

            }
        }
    }

    @Override
    public void updateOpportunityWonIndByInvOpportunityId(String invOpportunityId,
                                                          InvestorOpportunityMapper opportunityMapper) throws Exception {
        InvestorOpportunity opportunityDetails = investorOpportunityRepo
                .getOpportunityDetailsByOpportunityId(invOpportunityId);
        if (opportunityDetails != null) {
            opportunityDetails.setWonInd(opportunityMapper.isWonInd());
            opportunityDetails.setModifiedDate(new Date());
            opportunityDetails.setPaymentReceived(opportunityMapper.getPaymentReceived());
            if (null != opportunityMapper.getPaymentReceivedDate()) {
                opportunityDetails.setPaymentReceivedDate(Utility.getDateFromISOString(opportunityMapper.getPaymentReceivedDate()));
            } else {
                opportunityDetails.setPaymentReceivedDate(new Date());
            }
            opportunityDetails.setWonDate(new Date());
            investorOpportunityRepo.save(opportunityDetails);
        }
    }

    @Override
    public void updateOpportunityLostIndByInOpportunityId(String inOpportunityId,
                                                          InvestorOpportunityMapper opportunityMapper) {
        InvestorOpportunity opportunityDetails = investorOpportunityRepo
                .getOpportunityDetailsByOpportunityId(inOpportunityId);
        if (opportunityDetails != null) {
            opportunityDetails.setLostInd(opportunityMapper.isLostInd());
            opportunityDetails.setLostDate(new Date());
            opportunityDetails.setModifiedDate(new Date());
            investorOpportunityRepo.save(opportunityDetails);
        }
    }

    @Override
    public void updateOpportunityCloseIndByInOpportunityId(String inOpportunityId,
                                                           InvestorOpportunityMapper opportunityMapper) {
        InvestorOpportunity opportunityDetails = investorOpportunityRepo
                .getOpportunityDetailsByOpportunityId(inOpportunityId);
        if (opportunityDetails != null) {
            opportunityDetails.setCloseInd(opportunityMapper.isCloseInd());
            opportunityDetails.setCloseDate(new Date());
            opportunityDetails.setModifiedDate(new Date());
            investorOpportunityRepo.save(opportunityDetails);
        }
    }

    @Override
    public void reinstateOpportunityByOppId(String inOpportunityId) {
        InvestorOpportunity opportunityDetails = investorOpportunityRepo.getById(inOpportunityId);
        if (null != opportunityDetails) {
            opportunityDetails.setReinstateInd(true);
            investorOpportunityRepo.save(opportunityDetails);
        }
    }

    @Override
    public Map getDeleteCountList(String userId) {
        List<InvestorOpportunity> opportunityDetailsList = investorOpportunityRepo
                .getByUserIdAndReinstateIndAndLiveInd(userId);
        HashMap map = new HashMap();
        map.put("invOpportunity", opportunityDetailsList.size());
        return map;
    }

    @Override
    public List<InvestorOpportunityMapper> getOpportunityDetailByUserIdAndCloseInd(String userId, int pageNo,
                                                                                   int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("modifiedDate").descending());
        Page<InvestorOpportunity> opportunityDetails = investorOpportunityRepo
                .getOpportunityDetailsPaseWiseUserIdAndCloseIndAndLiveInd(userId, paging);
        if (null != opportunityDetails && !opportunityDetails.isEmpty()) {
            opportunityDetails.stream().sorted((p1, p2) -> p1.getModifiedDate().compareTo(p2.getModifiedDate()));
        }
        List<InvestorOpportunityMapper> resultMapper = new ArrayList<>();
        if (opportunityDetails != null && !opportunityDetails.isEmpty()) {
            resultMapper = opportunityDetails.stream().map(opportunity -> {
                InvestorOpportunityMapper mapper;
                if (null != opportunity) {
                    mapper = getOpportunityRelatedDetails(opportunity);
                    mapper.setPageCount(opportunityDetails.getTotalPages());
                    mapper.setDataCount(opportunityDetails.getSize());
                    mapper.setListCount(opportunityDetails.getTotalElements());
                    return mapper;
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        return resultMapper;
    }

    @Override
    public Map getOpportunityListByCloseInd(String userId) {
        List<InvestorOpportunity> opportunityDetails = investorOpportunityRepo.getByUserIdAndCloseIndAndLiveInd(userId);
        Map map = new HashMap();
        map.put("OpportunityDetailsByCloseInd", opportunityDetails.size());
        return map;
    }

    @Override
    public Map getOpportunityListByLostInd(String userId) {
        List<InvestorOpportunity> opportunityDetails = investorOpportunityRepo.getByUserIdAndLostIndAndLiveInd(userId);
        Map map = new HashMap();
        map.put("OpportunityDetailsByLostInd", opportunityDetails.size());
        return map;
    }

    @Override
    public List<InvestorOpportunityMapper> getOpportunityDetailByUserIdAndLostInd(String userId, int pageNo,
                                                                                  int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("modifiedDate").descending());
        Page<InvestorOpportunity> opportunityDetails = investorOpportunityRepo
                .getByUserIdAndLostIndAndLiveIndPagination(userId, paging);
        if (null != opportunityDetails && !opportunityDetails.isEmpty()) {
            opportunityDetails.stream().sorted((p1, p2) -> p1.getModifiedDate().compareTo(p2.getModifiedDate()));
        }
        List<InvestorOpportunityMapper> resultMapper = new ArrayList<>();
        if (opportunityDetails != null && !opportunityDetails.isEmpty()) {
            resultMapper = opportunityDetails.stream().map(opportunity -> {
                InvestorOpportunityMapper mapper;
                if (null != opportunity) {
                    mapper = getOpportunityRelatedDetails(opportunity);
                    mapper.setPageCount(opportunityDetails.getTotalPages());
                    mapper.setDataCount(opportunityDetails.getSize());
                    mapper.setListCount(opportunityDetails.getTotalElements());
                    return mapper;
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        return resultMapper;
    }

    @Override
    public List<InvestorOpportunityMapper> getOpportunityDetailByUserIdAndWonInd(String userId, int pageNo,
                                                                                 int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("modifiedDate").descending());
        Page<InvestorOpportunity> opportunityDetails = investorOpportunityRepo
                .getByUserIdAndWonIndAndLiveIndPagination(userId, paging);
        if (null != opportunityDetails && !opportunityDetails.isEmpty()) {
            opportunityDetails.stream().sorted((p1, p2) -> p1.getModifiedDate().compareTo(p2.getModifiedDate()));
        }
        List<InvestorOpportunityMapper> resultMapper = new ArrayList<>();
        if (opportunityDetails != null && !opportunityDetails.isEmpty()) {
            resultMapper = opportunityDetails.stream().map(opportunity -> {
                InvestorOpportunityMapper mapper;
                if (null != opportunity) {
                    mapper = getOpportunityRelatedDetails(opportunity);
                    mapper.setPageCount(opportunityDetails.getTotalPages());
                    mapper.setDataCount(opportunityDetails.getSize());
                    mapper.setListCount(opportunityDetails.getTotalElements());
                    return mapper;
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        return resultMapper;
    }

    @Override
    public Map getOpportunityListByWonInd(String userId) {
        List<InvestorOpportunity> opportunityDetails = investorOpportunityRepo.getByUserIdAndWonIndAndLiveInd(userId);
        Map map = new HashMap();
        map.put("OpportunityDetailsByWonInd", opportunityDetails.size());
        return map;
    }

    @Override
    public List<InvestorOpportunityMapper> getOpportunityDetailByUserIdAndDeleteInd(String userId, int pageNo,
                                                                                    int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("modifiedDate").descending());
        Page<InvestorOpportunity> opportunityDetails = investorOpportunityRepo
                .getByUserIdAndReinstateIndIndAndLiveIndPagination(userId, paging);
        if (opportunityDetails != null && !opportunityDetails.isEmpty()) {
            opportunityDetails.stream().sorted((p1, p2) -> p1.getModifiedDate().compareTo(p2.getModifiedDate()));
        }
        List<InvestorOpportunityMapper> resultMapper = new ArrayList<>();
        if (opportunityDetails != null && !opportunityDetails.isEmpty()) {
            resultMapper = opportunityDetails.stream().map(opportunity -> {
                InvestorOpportunityMapper mapper;
                if (null != opportunity) {
                    mapper = getOpportunityRelatedDetails(opportunity);
                    mapper.setPageCount(opportunityDetails.getTotalPages());
                    mapper.setDataCount(opportunityDetails.getSize());
                    mapper.setListCount(opportunityDetails.getTotalElements());
                    return mapper;
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        return resultMapper;
    }

    @Override
    public InvestorOpportunityMapper updateStage(InvestorOpportunityMapper opportunityMapper) {

        InvestorOpportunityMapper resultMapper = new InvestorOpportunityMapper();
        System.out.println(
                "opportunityMapper.getOpportunityId()================" + opportunityMapper.getInvOpportunityId());
        System.out.println(
                "opportunityMapper.getOppStage()================" + opportunityMapper.getInvOpportunityStagesId());
        InvestorOpportunity opportunityDetails = investorOpportunityRepo
                .getOpportunityDetailsByOpportunityId(opportunityMapper.getInvOpportunityId());
        System.out.println("opportunityDetails================" + opportunityDetails.toString());
        if (null != opportunityDetails) {
            System.out.println("opportunityMapper.getOpportunityId()--1================"
                    + opportunityMapper.getInvOpportunityId());
            System.out.println("opportunityMapper.getOppStage()--1================"
                    + opportunityMapper.getInvOpportunityStagesId());
            opportunityDetails.setOppStage(opportunityMapper.getInvOpportunityStagesId());

            investorOpportunityRepo.save(opportunityDetails);

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
            InvestorOppStages oppStages = investorOppStagesRepo.getById(opportunityMapper.getInvOpportunityStagesId());
            if (null != oppStages) {
                probability = oppStages.getProbability();
            }

            InvestorOppStages oppStages1 = investorOppStagesRepo.getById(opportunityDetails.getOppStage());
            if (null != oppStages1) {
                previousProbability1 = oppStages1.getProbability();
            }

            ConversionValueMapper mapper = oppertunityService.ConvertOppertunityProposalValueAndWeightedValue(
                    opportunityDetails.getProposalAmount(), userCurrency, orgCurrency, opportunityDetails.getCurrency(),
                    probability);
            ConversionValueMapper previous = oppertunityService.ConvertOppertunityProposalValueAndWeightedValue(
                    opportunityDetails.getProposalAmount(), userCurrency, orgCurrency, opportunityDetails.getCurrency(),
                    previousProbability1);

            if (null != previous) {
                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                        .findByInvestorId(opportunityMapper.getInvestorId());
                if (null != inOppConversionValue) {
                    double userpValue = (inOppConversionValue.getUserConversionValue()
                            - previous.getUserConversionPValue());
                    inOppConversionValue.setUserConversionValue(userpValue);
                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                            - previous.getUserConversionWValue());
                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                    double orgpValue = (inOppConversionValue.getOrgConversionValue()
                            - previous.getOrgConversionPValue());
                    inOppConversionValue.setOrgConversionValue(orgpValue);
                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                            - previous.getOrgConversionWValue());
                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                    inOppConversionValueRepo.save(inOppConversionValue);
                }
            }
            if (null != mapper) {
                InOppConversionValue inOppConversionValue = inOppConversionValueRepo
                        .findByInvestorId(opportunityMapper.getInvestorId());
                if (null != inOppConversionValue) {
                    double userpValue = (inOppConversionValue.getUserConversionValue()
                            + mapper.getUserConversionPValue());
                    inOppConversionValue.setUserConversionValue(userpValue);
                    double userwValue = (inOppConversionValue.getUserConversionWeightedValue()
                            + mapper.getUserConversionWValue());
                    inOppConversionValue.setUserConversionWeightedValue(userwValue);
                    double orgpValue = (inOppConversionValue.getOrgConversionValue() + mapper.getOrgConversionPValue());
                    inOppConversionValue.setOrgConversionValue(orgpValue);
                    double orgwValue = (inOppConversionValue.getOrgConversionWeightedValue()
                            + mapper.getOrgConversionWValue());
                    inOppConversionValue.setOrgConversionWeightedValue(orgwValue);
                    inOppConversionValueRepo.save(inOppConversionValue);
                }
            }

        }

        resultMapper = getOpportunityDetails(opportunityMapper.getInvOpportunityId());

        return resultMapper;
    }

//	@Override
//	public NotesMapper updateNoteDetails(NotesMapper notesMapper) {
//
//		NotesMapper resultMapper = employeeService.updateNotes(notesMapper);
//
//		return resultMapper;
//	}

    @Override
    public void deleteInvestorOpportunityNotesById(String notesId) {
        InvestorOppNotesLink notesList = investorOppNoteLinkRepo.findByNoteId(notesId);
        if (null != notesList) {

            Notes notes = notesRepository.findByNoteId(notesId);
            if (null != notes) {
                notes.setLiveInd(false);
                notesRepository.save(notes);
            }
        }
    }

    @Override
    public List<InvestorOpportunityMapper> getOpportunityDetailsListPageWiseByOrgId(String orgId, int pageNo,
                                                                                    int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
        List<InvestorOpportunityMapper> opportunities = new ArrayList<>();
        Page<InvestorOpportunity> listLink = investorOpportunityRepo.getOpenInvOpportunityDetailsListByOrgId(orgId,
                paging);
        if (null != listLink && !listLink.isEmpty()) {

            listLink.stream().filter(li -> (li != null)).map(li -> {
                InvestorOpportunityMapper mapper = getOpportunityRelatedDetails(li);
                mapper.setPageCount(listLink.getTotalPages());
                mapper.setDataCount(listLink.getSize());
                mapper.setListCount(listLink.getTotalElements());
                opportunities.add(mapper);
                mapper.setCollectedAmount(getAmountSumByInvOpportunityId(li.getInvOpportunityId()));

                return mapper;
            }).collect(Collectors.toList());
        }
        return opportunities;
    }

    @Override
    public List<InvestorOpportunityMapper> getInvestorOpportunitByInvOppWorkFlowId(String investorOppWorkflowId) {
        List<InvestorOpportunity> opportunityList = investorOpportunityRepo
                .getOpportunityListByInvestorOppWorkflowIdAndLiveInd(investorOppWorkflowId);

        List<InvestorOpportunityMapper> mapperList = new ArrayList<>();
        opportunityList.stream().map(opportunityDetails -> {

            InvestorOpportunityMapper opportunityMapper = getOpportunityDetails(
                    opportunityDetails.getInvOpportunityId());
            mapperList.add(opportunityMapper);
            return mapperList;
        }).collect(Collectors.toList());

        return mapperList;
    }

    @Override
    public Set<InvestorOpportunityMapper> getTeamInnvestorOppDetailsByUserId(String userId, int pageNo, int pageSize,
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
        Page<InvestorOpportunity> leadsPage = investorOpportunityRepo.getTeamInvestorsOppListByUserIdsPaginated(userIds,
                paging);

        Set<InvestorOpportunityMapper> mapperSet = new HashSet<>();

        if (leadsPage != null && !leadsPage.isEmpty()) {
            mapperSet = leadsPage.getContent().stream().map(li -> {
                InvestorOpportunityMapper mapper = getOpportunityRelatedDetails(li);
                mapper.setPageCount(leadsPage.getTotalPages());
                mapper.setDataCount(leadsPage.getSize());
                mapper.setListCount(leadsPage.getTotalElements());
                return mapper;
            }).collect(Collectors.toSet());
        }
        return mapperSet;
    }

    @Override
    public HashMap getTeamInvestorOppContactCountByUserId(String userId) {
        HashMap map = new HashMap();
        Team team = teamRepository.findByTeamLeadAndLiveInd(userId, true);

        List<String> userIds = teamMemberLinkRepo.findByTeamId(team.getTeamId()).stream()
                .map(TeamMemberLink::getTeamMemberId).collect(Collectors.toList());
        List<InvestorOpportunity> list = investorOpportunityRepo.getTeamInvestorOppListByUserIds(userIds);
        map.put("InvestorOpportunityTeam", list.size());

        return map;
    }

    @Override
    public List<String> updateTransferOneUserToAnother(String userId, TransferMapper transferMapper) {

        List<String> opportunityList = transferMapper.getOpportunityIds();

        System.out.println("opportunityList::::::::::::::::::::::::::::::::::::::::::::::::::::" + opportunityList);
        if (null != opportunityList && !opportunityList.isEmpty()) {
            for (String opportunityId : opportunityList) {
                System.out.println("the opportunity id is : " + opportunityId);
                InvestorOpportunity opportunity = investorOpportunityRepo
                        .getOpportunityDetailsByOpportunityId(opportunityId);
                System.out.println(
                        "opportunityDetails::::::::::::::::::::::::::::::::::::::::::::::::::::" + opportunity);
                if (null != opportunity) {
                    opportunity.setUserId(userId);
                    investorOpportunityRepo.save(opportunity);
                }
            }

        }
        return opportunityList;
    }

    @Override
    public List<InvestorOpportunityMapper> getInvestorOppByinvestorId(String investorId) {
        List<InvestorOpportunity> list = investorOpportunityRepo.getByInvestorId(investorId);

        List<InvestorOpportunityMapper> mapperList = new ArrayList<>();
        list.stream().map(opportunityDetails -> {

            InvestorOpportunityMapper opportunityMapper = getOpportunityDetails(
                    opportunityDetails.getInvOpportunityId());
            mapperList.add(opportunityMapper);
            return mapperList;
        }).collect(Collectors.toList());

        return mapperList;
    }

    @Override
    public List<InvestorOpportunityMapper> getInvestorOppDetailsListPageWiseByIncludedUserId(String userId, int pageNo,
                                                                                             int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "creationDate"));
        List<InvestorOpportunityMapper> investorOpportunities = new ArrayList<>();
        Page<InvestorOppIncludedLink> investorOppIncludedLink = investorOppIncludedRepository
                .getInvestorOppIncludedLinkByUserIdWithPagination(userId, paging);
        System.out.println("InvestorOppIncludedLink ==" + investorOppIncludedLink.getSize());
        if (null != investorOppIncludedLink && !investorOppIncludedLink.isEmpty()) {
            investorOppIncludedLink.stream().filter(li -> (li != null)).map(li -> {
                InvestorOpportunity investorOpportunity = investorOpportunityRepo
                        .getOpportunityDetailsByOpportunityId(li.getInvestorOppId());
                if (null != investorOpportunity) {
                    InvestorOpportunityMapper mapper = getOpportunityRelatedDetails(investorOpportunity);
                    if (null != mapper) {
                        mapper.setPageCount(investorOppIncludedLink.getTotalPages());
                        mapper.setDataCount(investorOppIncludedLink.getSize());
                        mapper.setListCount(investorOppIncludedLink.getTotalElements());
                        investorOpportunities.add(mapper);
                        return mapper;
                    }
                }
                return null;
            }).collect(Collectors.toList());
        }
        return investorOpportunities;
    }

    @Override
    public HashMap getCountListByIncludedUserId(String userId) {
        List<InvestorOppIncludedLink> investorOppIncludedLinkList = investorOppIncludedRepository
                .getInvestorOppIncludedLinkByUserId(userId);
        HashMap map = new HashMap();
        map.put("InvestorOppCount", investorOppIncludedLinkList.size());
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
    public List<InvestorOppReportMapper> getClosedOpportunitiesByOrgIdDateRange(String orgId, String startDate,
                                                                                String endDate) {
        Date end_date = null;
        Date start_date = null;
        try {
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<InvestorOpportunity> opportunityDetailsList = investorOpportunityRepo
                .getClosedInvOpportunityDetailsOrgIdWithDateRange(orgId, start_date, end_date);

        return opportunityDetailsList.stream().map(this::getOpportunityDetail).collect(Collectors.toList());
    }

    @Override
    public List<InvestorOppReportMapper> getOpenOpportunitiesByOrgIdDateRange(String orgId, String startDate,
                                                                              String endDate) {
        Date end_date = null;
        Date start_date = null;
        try {
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<InvestorOpportunity> opportunityDetailsList = investorOpportunityRepo
                .getOpenInvOpportunityDetailsUserIdWithDateRange(orgId, start_date, end_date);
        return opportunityDetailsList.stream().map(this::getOpportunityDetail).collect(Collectors.toList());
    }

    public InvestorOppReportMapper getOpportunityDetail(InvestorOpportunity opportunityDetails) {
        int openRecruitment = 0;
        int openPosition = 0;
        InvestorOppReportMapper oppertunityMapper = new InvestorOppReportMapper();

        if (null != opportunityDetails.getInvOpportunityId()) {

            oppertunityMapper.setInvOpportunityId(opportunityDetails.getInvOpportunityId());
            oppertunityMapper.setOpportunityName(opportunityDetails.getOpportunityName());

            oppertunityMapper.setProposalAmount(opportunityDetails.getProposalAmount());
            oppertunityMapper.setCurrency(opportunityDetails.getCurrency());
            oppertunityMapper.setUserId(opportunityDetails.getUserId());
            oppertunityMapper.setOrgId(opportunityDetails.getOrgId());
            oppertunityMapper.setStartDate(Utility.getISOFromDate(opportunityDetails.getStartDate()));
            oppertunityMapper.setEndDate(Utility.getISOFromDate(opportunityDetails.getEndDate()));
            oppertunityMapper.setDescription(opportunityDetails.getDescription());
//            oppertunityMapper.setOppInnitiative(opportunityDetails.getOppInnitiative());
            oppertunityMapper.setWonInd(opportunityDetails.isWonInd());
            oppertunityMapper.setLostInd(opportunityDetails.isLostInd());
            oppertunityMapper.setCloseInd(opportunityDetails.isCloseInd());
            if (null != opportunityDetails.getCreationDate()) {

                oppertunityMapper.setCreationDate(Utility.getISOFromDate(opportunityDetails.getCreationDate()));

            }

//            List<OpportunityRecruiterLink> list = opportunityRecruiterLinkRepository
//                    .getRecruiterLinkByOppId(opportunityDetails.getOpportunityId());
//            if (null != list && !list.isEmpty()) {
//                List<RecruiterMapper> recruiterList = new ArrayList<RecruiterMapper>();
//                for (OpportunityRecruiterLink opportunityRecruiterLink : list) {
//
//                    RecruiterMapper recriuterMapper = new RecruiterMapper();
//                    EmployeeDetails employeeDetails = employeeRepository
//                            .getEmployeeDetailsByEmployeeId(opportunityRecruiterLink.getRecruiter_id());
//                    if (null != employeeDetails) {
//                        String middleName = " ";
//                        String lastName = "";
//
//                        if (!StringUtils.isEmpty(employeeDetails.getLastName())) {
//
//                            lastName = employeeDetails.getLastName();
//                        }
//                        if (!StringUtils.isEmpty(employeeDetails.getMiddleName())) {
//                            middleName = employeeDetails.getMiddleName();
//
//                        }
//                        recriuterMapper.setFullName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
//                        recriuterMapper.setEmployeeId(employeeDetails.getEmployeeId());
//                        recriuterMapper.setImageId(employeeDetails.getImageId());
//                    }
//                    recruiterList.add(recriuterMapper);
//                }
//                oppertunityMapper.setRecruiterDetails(recruiterList);
//
//            }
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
            if (opportunityDetails.getInvestorId() != null && !opportunityDetails.getInvestorId().isEmpty()) {
                Investor investor = investorRepository.getById(opportunityDetails.getInvestorId());
                if (null != investor) {
                    oppertunityMapper.setInvestorId(investor.getInvestorId());
                    oppertunityMapper.setInvestor(investor.getName());
                }
            } else {
                oppertunityMapper.setInvestorId("");

            }
            if (opportunityDetails.getContactId() != null && !opportunityDetails.getContactId().trim().isEmpty()) {
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

            if (opportunityDetails.getInvOpportunityId() != null
                    && !opportunityDetails.getInvOpportunityId().trim().isEmpty()) {
                List<String> includedIds = investorOppIncludedRepository
                        .findByInvestorOppId(opportunityDetails.getInvOpportunityId()).stream()
                        .map(InvestorOppIncludedLink::getEmployeeId).collect(Collectors.toList());
                List<EmployeeShortMapper> included = new ArrayList<>();
                if (null != includedIds && !includedIds.isEmpty()) {
                    for (String includedId : includedIds) {
                        EmployeeShortMapper employeeMapper = employeeService
                                .getEmployeeFullNameAndEmployeeId(includedId);
                        included.add(employeeMapper);
                    }
                    oppertunityMapper.setInclude(included);
                }

//				List<String> opp = investorOppSalesLinkRepo
//						.getSalesUsersByOppId(opportunityDetails.getInvOpportunityId()).stream()
//						.map(InvestorOppSalesUserLink::getEmployeeId).collect(Collectors.toList());
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

            InvestorOppWorkflow workflowDetails = investorOppWorkflowRepo.getById(opportunityDetails.getOppWorkflow());
            if (null != workflowDetails) {
                oppertunityMapper.setOppWorkflow(workflowDetails.getWorkflowName());
                oppertunityMapper.setOppWorkflowId(workflowDetails.getInvestorOppWorkflowId());
            }

            InvestorOppStages oppStages = investorOppStagesRepo.getById(opportunityDetails.getOppStage());
            if (null != oppStages) {
                oppertunityMapper.setOppStage(oppStages.getStageName());
                oppertunityMapper.setProbability(oppStages.getProbability());
                oppertunityMapper.setInvOpportunityStagesId(oppStages.getInvestorOppStagesId());
            }

            oppertunityMapper.setStageList(
                    investorOppWorkflowService.getStagesByInvOppworkFlowId(opportunityDetails.getOppWorkflow()));
//
//            List<OpportunityRecruitDetails> recruitList = recruitmentOpportunityDetailsRepository
//                    .getOpenRecriutmentsByOpportunityIdAndCloseInd(opportunityDetails.getOpportunityId());
//            if (null != recruitList && !recruitList.isEmpty()) {
//                for (OpportunityRecruitDetails opportunityRecruitDetails : recruitList) {
//
//                    System.out.println("recruitmentId=" + opportunityRecruitDetails.getRecruitment_id());
//                    List<RecruitProfileLinkDetails> profileList = recruitProfileDetailsRepository
//                            .getProfileDetailByRecruitmentIdAndonBoardInd(
//                                    opportunityRecruitDetails.getRecruitment_id());
//                    if (opportunityRecruitDetails.isCloseInd() == false) {
//                        System.out.println("start::::::::::::2");
//                        int profileSize = profileList.size();
//                        int positionSize = (int) opportunityRecruitDetails.getNumber();
//                        System.out.println("profileSize=" + profileSize + "||" + "positionSize=" + positionSize);
//                        if (profileSize < positionSize) {
//                            openRecruitment++;
//                            openPosition = positionSize - profileSize;
//                            System.out.println("openPosition=============inner===" + openPosition);
//                        }
//                        if (recruitList.size() > 1) {
//                            openPosition += openPosition;
//                            System.out.println("openPosition=============outer===" + openPosition);
//                        }
//                    }
//                }
//                System.out.println("openRecruitment=============outer===" + openRecruitment);
//
//            }
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
    public List<InvestorOppReportMapper> getOpportunitiesByOrgIdDateRange(String orgId, String startDate,
                                                                          String endDate) {
        Date end_date = null;
        Date start_date = null;
        try {
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<InvestorOpportunity> opportunityDetailsList = investorOpportunityRepo
                .getInvOpportunityDetailsByOrgIdWithDateRange(orgId, start_date, end_date);
        System.out.println("opportunityDetailsList=====" + opportunityDetailsList.size());
        List<InvestorOppReportMapper> mapperList = new ArrayList<>();
        if (null != opportunityDetailsList && !opportunityDetailsList.isEmpty()) {
            opportunityDetailsList.stream().map(li -> {
                InvestorOppReportMapper mapper = getOpportunityDetail(li);
                System.out.println("getInvestorLeadsId=====" + mapper.getInvOpportunityId());

                if (null != mapper.getInvOpportunityId()) {
                    mapperList.add(mapper);
                }

                return mapperList;
            }).collect(Collectors.toList());

        }
        return mapperList;
    }

    @Override
    public List<InvestorOppReportMapper> getOpportunitiesByUserIdDateRange(String userId, String startDate,
                                                                           String endDate) {
        Date end_date = null;
        Date start_date = null;
        try {
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<InvestorOpportunity> opportunityDetailsList = investorOpportunityRepo
                .getInvOpportunityDetailsByUserIdWithDateRange(userId, start_date, end_date);
        System.out.println("opportunityDetailsList=====" + opportunityDetailsList.size());
        List<InvestorOppReportMapper> mapperList = new ArrayList<>();
        if (null != opportunityDetailsList && !opportunityDetailsList.isEmpty()) {
            opportunityDetailsList.stream().map(li -> {
                InvestorOppReportMapper mapper = getOpportunityDetail(li);
                System.out.println("getInvestorLeadsId=====" + mapper.getInvOpportunityId());

                if (null != mapper.getInvOpportunityId()) {
                    mapperList.add(mapper);
                }

                return mapperList;
            }).collect(Collectors.toList());

        }
        return mapperList;
    }

    @Override
    public Set<InvestorOpportunityMapper> getTeamInnvestorOppDetailsByUnderAUserId(String userId, int pageNo,
                                                                                   int pageSize) {
        Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
                .map(EmployeeDetails::getUserId).collect(Collectors.toSet());
        userIds.add(userId);

        List<String> userIdss = new ArrayList<>(userIds);

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());


        Page<InvestorOpportunity> leadsPage = investorOpportunityRepo.getTeamInvestorsOppListByUserIdsPaginated(userIdss,
                paging);

        Set<InvestorOpportunityMapper> mapperSet = new HashSet<>();

        if (leadsPage != null && !leadsPage.isEmpty()) {
            mapperSet = leadsPage.getContent().stream().map(li -> {
                InvestorOpportunityMapper mapper = getOpportunityRelatedDetails(li);
                mapper.setPageCount(leadsPage.getTotalPages());
                mapper.setDataCount(leadsPage.getSize());
                mapper.setListCount(leadsPage.getTotalElements());
                return mapper;
            }).collect(Collectors.toSet());
        }
        return mapperSet;
    }

    @Override
    public HashMap getTeamInvestorOppContactCountByUnderAUserId(String userId) {
        HashMap map = new HashMap();

        Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
                .map(EmployeeDetails::getUserId).collect(Collectors.toSet());
        userIds.add(userId);

        List<String> userIdss = new ArrayList<>(userIds);

        List<InvestorOpportunity> list = investorOpportunityRepo.getTeamInvestorOppListByUserIds(userIdss);
        map.put("investorOpportunityTeam", list.size());
        return map;
    }

    @Override
    public HashMap getOpenInvOppCountByContactId(String contactId) {
        List<InvestorOpportunity> opportunityList = investorOpportunityRepo
                .getOpenInvOpportunityListByContactId(contactId);
        HashMap map = new HashMap();

        map.put("openInvestorOpportunity", opportunityList.size());
        return map;

    }

    @Override
    public List<InvestorOpportunityMapper> getOpenInvOppListBycontactId(String contactId) {
        List<InvestorOpportunity> opportunityList = investorOpportunityRepo
                .getOpenInvOpportunityListByContactId(contactId);
        List<InvestorOpportunityMapper> resultmapper = new ArrayList<>();
        opportunityList.stream().map(opp -> {
            InvestorOpportunityMapper mapper = getOpportunityRelatedDetails(opp);
            mapper.setContactId(contactId);

            resultmapper.add(mapper);
            return resultmapper;
        }).collect(Collectors.toList());

        return resultmapper;

    }

    @Override
    public HashMap getWonInvOppCountBycontactId(String contactId) {
        List<InvestorOpportunity> opportunityList = investorOpportunityRepo
                .getWonInvOpportunityListByContactId(contactId);
        HashMap map = new HashMap();

        map.put("wonInvestorOpportunity", opportunityList.size());
        return map;

    }

    @Override
    public List<InvestorOpportunityMapper> getWonInvOppListBycontactId(String contactId) {
        List<InvestorOpportunity> opportunityList = investorOpportunityRepo
                .getWonInvOpportunityListByContactId(contactId);
        List<InvestorOpportunityMapper> resultMapper = new ArrayList<>();
        opportunityList.stream().map(opp -> {
            InvestorOpportunityMapper mapper = getOpportunityRelatedDetails(opp);

            resultMapper.add(mapper);
            return resultMapper;
        }).collect(Collectors.toList());
        return resultMapper;
    }

    @Override
    public HashMap getInvestorOppProposalValueCountByContactId(String contactId, String userId, String orgId) {
        HashMap map = new HashMap();
        int count = 0;
        int conversionAmount = 0;
        ContactDetails contact = contactRepository.getcontactDetailsById(contactId);
        if (null != contact) {
            if (contact.getUser_id().equalsIgnoreCase(userId)) {

                List<InvestorOpportunity> opportunityList = investorOpportunityRepo
                        .getOpportunityListByContactIdAndLiveInd(contactId);
                if (null != opportunityList && !opportunityList.isEmpty()) {
                    for (InvestorOpportunity opportunityDetails : opportunityList) {
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

                List<InvestorOpportunity> opportunityList = investorOpportunityRepo
                        .getOpportunityListByContactIdAndLiveInd(contactId);
                if (null != opportunityList && !opportunityList.isEmpty()) {
                    for (InvestorOpportunity opportunityDetails : opportunityList) {
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
                    map.put("message", "InvestorOpportunity  not available for this contact");
                }
            }
        } else {
            map.put("message", "Contact not available");
        }

        map.put("pipeLineValue", count);

        return map;

    }

    @Override
    public HashMap getInvestorOppWeigthedValueCountByContactId(String contactId, String userId, String orgId) {
        HashMap map = new HashMap();
        double count = 0;
        int conversionAmount = 0;
        ContactDetails contact = contactRepository.getcontactDetailsById(contactId);
        if (null != contact) {
            if (contact.getUser_id().equalsIgnoreCase(userId)) {

                List<InvestorOpportunity> opportunityList = investorOpportunityRepo
                        .getOpportunityListByContactIdAndLiveInd(contactId);
                if (null != opportunityList && !opportunityList.isEmpty()) {
                    for (InvestorOpportunity opportunityDetails : opportunityList) {
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
                                            InvestorOppStages oppStages = investorOppStagesRepo
                                                    .getInvestorOppStagesByInvestorOppStagesId(
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
                                                InvestorOppStages oppStages = investorOppStagesRepo
                                                        .getInvestorOppStagesByInvestorOppStagesId(
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
                                                    InvestorOppStages oppStages = investorOppStagesRepo
                                                            .getInvestorOppStagesByInvestorOppStagesId(
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
                List<InvestorOpportunity> opportunityList = investorOpportunityRepo
                        .getOpportunityListByContactIdAndLiveInd(contactId);
                if (null != opportunityList && !opportunityList.isEmpty()) {
                    for (InvestorOpportunity opportunityDetails : opportunityList) {
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
                                        InvestorOppStages oppStages = investorOppStagesRepo
                                                .getInvestorOppStagesByInvestorOppStagesId(
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
                                            InvestorOppStages oppStages = investorOppStagesRepo
                                                    .getInvestorOppStagesByInvestorOppStagesId(
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
                                                InvestorOppStages oppStages = investorOppStagesRepo
                                                        .getInvestorOppStagesByInvestorOppStagesId(
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
                    map.put("message", "InvestorOpportunity not available for this Contact");
                }
            }
        } else {
            map.put("message", "Contact not available");
        }

        map.put("weightedValue", count);

        return map;
    }

    @Override
    public HashMap getInvestorOppCountByCountry(String country) {
        List<AddressDetails> addressDetails = addressRepository.getAddressDetailsByCountry(country);
        HashMap map = new HashMap();
        int count = 0;
        if (null != addressDetails && !addressDetails.isEmpty()) {
            for (AddressDetails address : addressDetails) {
                List<InvestorAddressLink> investorAddressLinkList = investorAddressLinkRepo
                        .getByAddressId(address.getAddressId());
                if (null != investorAddressLinkList && !investorAddressLinkList.isEmpty()) {
                    for (InvestorAddressLink investorAddressLink : investorAddressLinkList) {
                        List<InvestorOpportunity> opportunityList = investorOpportunityRepo
                                .getOpportunityListByInvestorId(investorAddressLink.getInvestorId());
                        if (null != opportunityList) {
                            count = count + opportunityList.size();
                        }
                    }
                }
            }
        }

        map.put("InvestorOppCountByCountry", count);
        return map;
    }

    @Override
    public List<InvestorOpportunityMapper> getInvestorOppListByCountry(String country) {
        List<AddressDetails> addressDetails = addressRepository.getAddressDetailsByCountry(country);
        List<InvestorOpportunityMapper> resultList = new ArrayList<>();
        if (null != addressDetails && !addressDetails.isEmpty()) {
            for (AddressDetails address : addressDetails) {
                List<InvestorAddressLink> investorAddressLinkList = investorAddressLinkRepo
                        .getByAddressId(address.getAddressId());
                if (null != investorAddressLinkList && !investorAddressLinkList.isEmpty()) {
                    for (InvestorAddressLink investorAddressLink : investorAddressLinkList) {
                        List<InvestorOpportunity> opportunityList = investorOpportunityRepo
                                .getOpportunityListByInvestorIdAndLiveInd(investorAddressLink.getInvestorId());
                        if (null != opportunityList && !opportunityList.isEmpty()) {
                            for (InvestorOpportunity opportunity : opportunityList) {
                                InvestorOpportunityMapper mapper = getOpportunityRelatedDetails(opportunity);
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
    public HashMap getOpenInvestorOppCountByCountry(String country) {
        List<AddressDetails> addressDetails = addressRepository.getAddressDetailsByCountry(country);
        HashMap map = new HashMap();
        int count = 0;
        if (null != addressDetails && !addressDetails.isEmpty()) {
            for (AddressDetails address : addressDetails) {
                List<InvestorAddressLink> investorAddressLinkList = investorAddressLinkRepo
                        .getByAddressId(address.getAddressId());
                if (null != investorAddressLinkList && !investorAddressLinkList.isEmpty()) {
                    for (InvestorAddressLink investorAddressLink : investorAddressLinkList) {
                        List<InvestorOpportunity> opportunityList = investorOpportunityRepo
                                .getOpenInvestorOppByInvestorId(investorAddressLink.getInvestorId());
                        if (null != opportunityList) {
                            count = count + opportunityList.size();
                        }
                    }
                }
            }
        }

        map.put("openInvestorOppCountByCountry", count);
        return map;
    }

    @Override
    public List<InvestorOpportunityMapper> getOpenInvestorOppListByCountry(String country) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap getInvestorOppYearlyCountByCountry(String country) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<InvestorOpportunityMapper> getInvestorOppYearlyListByCountry(String country) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<InvestorOppFundResponse> getInvestorOppFundByInvOpportunityId(String invOpportunityId, String userId) {
        List<InvestorOppFundResponse> result = new ArrayList<>();
        List<ContactDetails> contacts = contactRepository.findByUserIdAndContactType(userId, "Investor");
        List<String> contactIds = contacts.stream().map(ContactDetails::getContactId).collect(Collectors.toList());

        List<FundContactLink> fundContactLinks = investorOpFundRepo.findByContactIdInAndInvOpportunityId(contactIds, invOpportunityId);

        Map<String, FundContactLink> fundContactLinkMap = fundContactLinks.stream()
                .collect(Collectors.toMap(FundContactLink::getContactId, fundContactLink -> fundContactLink));

        result = contacts.stream().map(contact -> {
            InvestorOppFundResponse dto = new InvestorOppFundResponse();
            dto.setContactId(contact.getContactId());
            if (null != contact) {
                String middleName = " ";
                String lastName = "";

                if (!StringUtils.isEmpty(contact.getLast_name())) {

                    lastName = contact.getLast_name();
                }
                if (!StringUtils.isEmpty(contact.getMiddle_name())) {
                    middleName = contact.getMiddle_name();

                }
                dto.setName(contact.getFirst_name() + " " + middleName + " " + lastName);
            }

            FundContactLink fundContactLink = fundContactLinkMap.get(contact.getContactId());
            if (fundContactLink != null) {
                dto.setBorrowInd(fundContactLink.isBorrowInd());
                dto.setBorrowDate(Utility.getISOFromDate(fundContactLink.getBorrowDate()));
                ;
                dto.setRepayMonth(fundContactLink.getRepayMonth());
                dto.setAmount(fundContactLink.getAmount());
                dto.setInterest(fundContactLink.getInterest());
                Currency currency = currencyRepository.getByCurrencyId(fundContactLink.getCurrency());
                if (null != currency) {
                    dto.setCurrency(currency.getCurrencyName());
                }

            }
            return dto;
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public InvestorOppFundResponse updateInvestorOppFund(InvestorOppFundRequest requestBody) {
        FundContactLink responsefundContactLink;
        FundContactLink fundContactLink = investorOpFundRepo.findByContactIdAndInvOpportunityId(requestBody.getContactId(), requestBody.getInvOpportunityId());
        if (fundContactLink != null) {
            try {
                fundContactLink.setBorrowDate(Utility.getDateFromISOString(requestBody.getBorrowDate()));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ;
            fundContactLink.setRepayMonth(requestBody.getRepayMonth());
            fundContactLink.setAmount(requestBody.getAmount());
            fundContactLink.setInterest(requestBody.getInterest());
            fundContactLink.setContactId(requestBody.getContactId());
            fundContactLink.setInvOpportunityId(requestBody.getInvOpportunityId());
            fundContactLink.setCurrency(requestBody.getCurrency());
            responsefundContactLink = investorOpFundRepo.save(fundContactLink);
        } else {
            FundContactLink newFundContactLink = new FundContactLink();
            try {
                newFundContactLink.setBorrowDate(Utility.getDateFromISOString(requestBody.getBorrowDate()));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ;
            newFundContactLink.setCurrency(requestBody.getCurrency());
            newFundContactLink.setRepayMonth(requestBody.getRepayMonth());
            newFundContactLink.setAmount(requestBody.getAmount());
            newFundContactLink.setInterest(requestBody.getInterest());
            newFundContactLink.setContactId(requestBody.getContactId());
            newFundContactLink.setInvOpportunityId(requestBody.getInvOpportunityId());
            responsefundContactLink = investorOpFundRepo.save(newFundContactLink);
        }
        return getFundResponse(responsefundContactLink);
    }

    @Override
    public InvestorOppFundResponse updateInvestorOppFundToggle(InvestorOppFundRequest requestBody) {
        FundContactLink responsefundContactLink;
        FundContactLink fundContactLink = investorOpFundRepo.findByContactIdAndInvOpportunityId(requestBody.getContactId(), requestBody.getInvOpportunityId());
        if (fundContactLink != null) {
            fundContactLink.setBorrowInd(requestBody.isBorrowInd());
            fundContactLink.setContactId(requestBody.getContactId());
            fundContactLink.setInvOpportunityId(requestBody.getInvOpportunityId());
            fundContactLink.setCreationDate(new Date());
            responsefundContactLink = investorOpFundRepo.save(fundContactLink);
        } else {
            FundContactLink newFundContactLink = new FundContactLink();
            newFundContactLink.setBorrowInd(requestBody.isBorrowInd());
            newFundContactLink.setContactId(requestBody.getContactId());
            newFundContactLink.setInvOpportunityId(requestBody.getInvOpportunityId());
            newFundContactLink.setCreationDate(new Date());
            responsefundContactLink = investorOpFundRepo.save(newFundContactLink);
        }
        return getFundResponse(responsefundContactLink);
    }


    private InvestorOppFundResponse getFundResponse(FundContactLink responsefundContactLink) {
        InvestorOppFundResponse dto = new InvestorOppFundResponse();
        ContactDetails contact = contactRepository.getcontactDetailsById(responsefundContactLink.getContactId());
        if (contact != null) {
            String middleName = " ";
            String lastName = "";

            if (!StringUtils.isEmpty(contact.getLast_name())) {

                lastName = contact.getLast_name();
            }
            if (!StringUtils.isEmpty(contact.getMiddle_name())) {
                middleName = contact.getMiddle_name();

            }
            dto.setName(contact.getFirst_name() + " " + middleName + " " + lastName);
            dto.setContactId(contact.getContactId());
            dto.setBorrowInd(responsefundContactLink.isBorrowInd());
            dto.setBorrowDate(Utility.getISOFromDate(responsefundContactLink.getBorrowDate()));
            ;
            dto.setRepayMonth(responsefundContactLink.getRepayMonth());
            dto.setAmount(responsefundContactLink.getAmount());
            dto.setInvOpportunityId(responsefundContactLink.getContactId());
            dto.setInterest(responsefundContactLink.getInterest());
            Currency currency = currencyRepository.getByCurrencyId(responsefundContactLink.getCurrency());
            if (null != currency) {
                dto.setCurrency(currency.getCurrencyName());
            }
        }

        return dto;
    }

    @Override
    public List<InvestorOpportunityMapper> getDeletedInnOpportunityDetails(String loggeduserId, int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
//		List<OpportunityDetails> opportunity = opportunityDetailsRepository.findByReinstateIndAndLiveIndAndUserId(false,
//				true, loggeduserId,paging);
        List<InvestorOpportunityMapper> resultList = new ArrayList<InvestorOpportunityMapper>();
        Page<InvestorOpportunity> opportunity = investorOpportunityRepo.findByReinstateIndAndLiveIndAndUserId(false,
                true, loggeduserId, paging);
        ;
        if (null != opportunity && !opportunity.isEmpty()) {
            opportunity.stream().map(opportunityDetails -> {
                InvestorOpportunityMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
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

    @Override
    public List<InvestorOpportunityMapper> getInvestorOppByNameByOrgLevel(String name, String orgId) {
        List<InvestorOpportunity> list = investorOpportunityRepo.findByOpportunityNameContainingAndOrgId(orgId);
        List<InvestorOpportunity> filterList = list.parallelStream().filter(detail -> {
            return detail.getOpportunityName() != null && Utility.containsIgnoreCase(detail.getOpportunityName(), name.trim());
        }).collect(Collectors.toList());

        List<InvestorOpportunityMapper> resultList = new ArrayList<>();
        if (null != filterList && !filterList.isEmpty()) {
            filterList.stream().map(opportunityDetails -> {
                InvestorOpportunityMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
                resultList.add(opportunityViewMapper);
                return resultList;
            }).collect(Collectors.toList());

        }
        return resultList;
    }

    @Override
    public List<InvestorOpportunityMapper> getInvestorOppBByNameForTeam(String name, String userId) {

        Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
                .map(EmployeeDetails::getUserId).collect(Collectors.toSet());
        userIds.add(userId);

        List<String> userIdss = new ArrayList<>(userIds);

        System.out.println(userIdss.stream().toArray());

        List<InvestorOpportunity> list = investorOpportunityRepo.findByOpportunityNameContainingAndUserIds(userIdss);
        List<InvestorOpportunity> filterList = list.parallelStream().filter(detail -> {
            return detail.getOpportunityName() != null && Utility.containsIgnoreCase(detail.getOpportunityName(), name.trim());
        }).collect(Collectors.toList());
        List<InvestorOpportunityMapper> resultList = new ArrayList<>();
        if (null != filterList && !filterList.isEmpty()) {
            filterList.stream().map(opportunityDetails -> {
                InvestorOpportunityMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
                resultList.add(opportunityViewMapper);
                return resultList;
            }).collect(Collectors.toList());

        }
        return resultList;
    }

    @Override
    public List<InvestorOpportunityMapper> getInvestorOppBByNameByUserIdl(String name, String userId) {
        List<InvestorOpportunity> list = investorOpportunityRepo.findByOpportunityNameContainingAndUserId(userId);

        List<InvestorOpportunity> filterList = list.parallelStream().filter(detail -> {
            return detail.getOpportunityName() != null && Utility.containsIgnoreCase(detail.getOpportunityName(), name.trim());
        }).collect(Collectors.toList());
        List<InvestorOpportunityMapper> resultList = new ArrayList<>();
        if (null != filterList && !filterList.isEmpty()) {
            filterList.stream().map(opportunityDetails -> {
                InvestorOpportunityMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
                resultList.add(opportunityViewMapper);
                return resultList;
            }).collect(Collectors.toList());

        }
        return resultList;
    }

    @Override
    public List<InvestorOpportunityMapper> getOpportunityDetailsByNewOppIdAndTypeOrgLevel(String name, String orgId) {
        List<InvestorOpportunity> list = investorOpportunityRepo.findByNewDealNoContainingAndOrgId(name,orgId);

        List<InvestorOpportunityMapper> resultList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            list.stream().map(opportunityDetails -> {
                InvestorOpportunityMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
                resultList.add(opportunityViewMapper);
                return resultList;
            }).collect(Collectors.toList());

        }
        return resultList;
    }

    @Override
    public List<InvestorOpportunityMapper> getOpportunityDetailsByNewOppIdAndTypeForTeam(String name, String userId) {
        Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
                .map(EmployeeDetails::getUserId).collect(Collectors.toSet());
        userIds.add(userId);

        List<String> userIdss = new ArrayList<>(userIds);

        List<InvestorOpportunity> list = investorOpportunityRepo.findByNewDealNoContainingAndUserIds(name,userIdss);

        List<InvestorOpportunityMapper> resultList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            list.stream().map(opportunityDetails -> {
                InvestorOpportunityMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
                resultList.add(opportunityViewMapper);
                return resultList;
            }).collect(Collectors.toList());

        }
        return resultList;
    }

    @Override
    public List<InvestorOpportunityMapper> getOpportunityDetailsByNewOppIdAndTypeAndUserId(String name, String userId) {
        List<InvestorOpportunity> list = investorOpportunityRepo.findByNewDealNoContainingAndUserId(name,userId);

        List<InvestorOpportunityMapper> resultList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            list.stream().map(opportunityDetails -> {
                InvestorOpportunityMapper opportunityViewMapper = getOpportunityRelatedDetails(opportunityDetails);
                resultList.add(opportunityViewMapper);
                return resultList;
            }).collect(Collectors.toList());

        }
        return resultList;
    }
}
