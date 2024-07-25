
package com.app.employeePortal.employee.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import com.app.employeePortal.registration.entity.*;
import com.app.employeePortal.registration.entity.Currency;
import com.app.employeePortal.registration.repository.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.app.employeePortal.Opportunity.entity.OpportunityNotesLink;
import com.app.employeePortal.Opportunity.repository.OpportunityNotesLinkRepository;
import com.app.employeePortal.Team.entity.Team;
import com.app.employeePortal.Team.repository.TeamRepository;
import com.app.employeePortal.address.entity.AddressDetails;
import com.app.employeePortal.address.entity.AddressInfo;
import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.address.repository.AddressInfoRepository;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.address.service.AddressService;
import com.app.employeePortal.call.entity.CallNotesLink;
import com.app.employeePortal.call.repository.CallNotesLinkRepository;
import com.app.employeePortal.candidate.entity.DefinationDetails;
import com.app.employeePortal.candidate.entity.DefinationDetailsDelete;
import com.app.employeePortal.candidate.entity.DefinationInfo;
import com.app.employeePortal.candidate.repository.DefinationDeleteRepository;
import com.app.employeePortal.candidate.repository.DefinationInfoRepository;
import com.app.employeePortal.candidate.repository.DefinationRepository;
import com.app.employeePortal.category.entity.Equipment;
import com.app.employeePortal.category.entity.LobDetails;
import com.app.employeePortal.category.entity.Module;
import com.app.employeePortal.category.entity.PerformanceManagement;
import com.app.employeePortal.category.entity.RoleType;
import com.app.employeePortal.category.entity.ServiceLine;
import com.app.employeePortal.category.entity.UserSalaryBreakout;
import com.app.employeePortal.category.mapper.ModuleDepartmentResponseMapper;
import com.app.employeePortal.category.repository.EquipmentRepository;
import com.app.employeePortal.category.repository.LobDetailsRepository;
import com.app.employeePortal.category.repository.ModuleRepository;
import com.app.employeePortal.category.repository.PerformanceManagementRepository;
import com.app.employeePortal.category.repository.RoleTypeRepository;
import com.app.employeePortal.category.repository.ServiceLineRepository;
import com.app.employeePortal.category.repository.UserSalaryBreakoutRepository;
import com.app.employeePortal.config.AesEncryptor;
import com.app.employeePortal.contact.entity.ContactNotesLink;
import com.app.employeePortal.contact.repository.ContactNotesLinkRepository;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.entity.CustomerNotesLink;
import com.app.employeePortal.customer.repository.CustomerNotesLinkRepository;
import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.entity.DocumentType;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.document.repository.DocumentDetailsRepository;
import com.app.employeePortal.document.repository.DocumentTypeRepository;
import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.education.entity.EducationType;
import com.app.employeePortal.education.repository.EducationTypeRepository;
import com.app.employeePortal.email.service.EmailService;
import com.app.employeePortal.employee.entity.BankDetails;
import com.app.employeePortal.employee.entity.CellChamberLink;
import com.app.employeePortal.employee.entity.CellChamberUserLink;
import com.app.employeePortal.employee.entity.EducationalDetails;
import com.app.employeePortal.employee.entity.EmployeeAddressLink;
import com.app.employeePortal.employee.entity.EmployeeAdminUpdate;
import com.app.employeePortal.employee.entity.EmployeeContactAddressLink;
import com.app.employeePortal.employee.entity.EmployeeContract;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.EmployeeDocumentLink;
import com.app.employeePortal.employee.entity.EmployeeEmailLink;
import com.app.employeePortal.employee.entity.EmployeeIDDetails;
import com.app.employeePortal.employee.entity.EmployeeInfo;
import com.app.employeePortal.employee.entity.EmployeeNotesLink;
import com.app.employeePortal.employee.entity.EmployeeRoleLink;
import com.app.employeePortal.employee.entity.EmployeeWorkflowLink;
import com.app.employeePortal.employee.entity.EmployementHistory;
import com.app.employeePortal.employee.entity.KeySkillDetails;
import com.app.employeePortal.employee.entity.Notes;
import com.app.employeePortal.employee.entity.PersonalDetails;
import com.app.employeePortal.employee.entity.RoomNotesLink;
import com.app.employeePortal.employee.entity.SalaryDetails;
import com.app.employeePortal.employee.entity.SalaryInfo;
import com.app.employeePortal.employee.entity.TrainingDetails;
import com.app.employeePortal.employee.entity.UserEquipmentLink;
import com.app.employeePortal.employee.entity.UserKpiLink;
import com.app.employeePortal.employee.entity.Visa;
import com.app.employeePortal.employee.mapper.BankDetailsMapper;
import com.app.employeePortal.employee.mapper.EducationalDetailsMapper;
import com.app.employeePortal.employee.mapper.EmployeeAdminUpdateRequestMapper;
import com.app.employeePortal.employee.mapper.EmployeeAdminUpdateResponseMapper;
import com.app.employeePortal.employee.mapper.EmployeeCommonMapper;
import com.app.employeePortal.employee.mapper.EmployeeContractMapper;
import com.app.employeePortal.employee.mapper.EmployeeEducationDetailsTreeMapper;
import com.app.employeePortal.employee.mapper.EmployeeEmailLinkMapper;
import com.app.employeePortal.employee.mapper.EmployeeIDMapper;
import com.app.employeePortal.employee.mapper.EmployeeMapper;
import com.app.employeePortal.employee.mapper.EmployeePreferedLanguageMapper;
import com.app.employeePortal.employee.mapper.EmployeeRoleLinkMapper;
import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.app.employeePortal.employee.mapper.EmployeeTableMapper;
import com.app.employeePortal.employee.mapper.EmployeeTreeMapper;
import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.app.employeePortal.employee.mapper.EmployeeWorkflowAndStageResponseMapper;
import com.app.employeePortal.employee.mapper.EmployeeWorkflowReqestMapper;
import com.app.employeePortal.employee.mapper.EmploymentHistoryMapper;
import com.app.employeePortal.employee.mapper.KeyskillsMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.mapper.PersonalDetailsMapper;
import com.app.employeePortal.employee.mapper.SalaryDetailsMapper;
import com.app.employeePortal.employee.mapper.TrainingDetailsMapper;
import com.app.employeePortal.employee.mapper.UserEquipmentMapper;
import com.app.employeePortal.employee.mapper.UserKpiLobMapper;
import com.app.employeePortal.employee.mapper.UserKpiRequestForAssignedValueMapper;
import com.app.employeePortal.employee.mapper.UserKpiRequestForCompletedValueMapper;
import com.app.employeePortal.employee.mapper.UserKpiRequestMapper;
import com.app.employeePortal.employee.mapper.UserKpiResponseMapper;
import com.app.employeePortal.employee.mapper.UserSalaryBreakoutMapper;
import com.app.employeePortal.employee.mapper.VisaMapper;
import com.app.employeePortal.employee.repository.BankRepository;
import com.app.employeePortal.employee.repository.CellChamberLinkRepository;
import com.app.employeePortal.employee.repository.CellChamberUserLinkRepository;
import com.app.employeePortal.employee.repository.EducationalRepository;
import com.app.employeePortal.employee.repository.EmployeeAddressLinkRepository;
import com.app.employeePortal.employee.repository.EmployeeAdminUpdateRepository;
import com.app.employeePortal.employee.repository.EmployeeContactAddressRepository;
import com.app.employeePortal.employee.repository.EmployeeContractRepository;
import com.app.employeePortal.employee.repository.EmployeeDocumentLinkRepository;
import com.app.employeePortal.employee.repository.EmployeeEmailLinkRepository;
import com.app.employeePortal.employee.repository.EmployeeIDRepository;
import com.app.employeePortal.employee.repository.EmployeeInfoRepository;
import com.app.employeePortal.employee.repository.EmployeeNotesLinkRepository;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.EmployeeRoleLinkRepository;
import com.app.employeePortal.employee.repository.EmployeeWorkflowLinkRepository;
import com.app.employeePortal.employee.repository.EmployementHistoryRepository;
import com.app.employeePortal.employee.repository.KeySkillRepository;
import com.app.employeePortal.employee.repository.NotesRepository;
import com.app.employeePortal.employee.repository.PersonalRepository;
import com.app.employeePortal.employee.repository.RoomNotesLinkRepository;
import com.app.employeePortal.employee.repository.SalaryInfoRepository;
import com.app.employeePortal.employee.repository.SalaryRepository;
import com.app.employeePortal.employee.repository.TrainingRepository;
import com.app.employeePortal.employee.repository.UserEquipmentLinkRepository;
import com.app.employeePortal.employee.repository.UserKpiLinkRepository;
import com.app.employeePortal.employee.repository.VisaRepository;
import com.app.employeePortal.event.entity.EventNotesLink;
import com.app.employeePortal.event.repository.EventNotesLinkRepository;
import com.app.employeePortal.expense.entity.ExpenseNotesLink;
import com.app.employeePortal.expense.repository.ExpenseNotesLinkRepository;
import com.app.employeePortal.investor.entity.InvestorNoteLink;
import com.app.employeePortal.investor.repository.InvestorNotesLinkRepo;
import com.app.employeePortal.investorleads.entity.InvestorLeadsNotesLink;
import com.app.employeePortal.investorleads.repository.InvestorLeadsNotesLinkRepository;
import com.app.employeePortal.leads.entity.LeadsNotesLink;
import com.app.employeePortal.leads.repository.LeadsNotesLinkRepository;
import com.app.employeePortal.leave.entity.LeaveNotesLink;
import com.app.employeePortal.leave.repository.LeaveNotesLinkRepository;
import com.app.employeePortal.location.entity.LocationDetails;
import com.app.employeePortal.location.repository.LocationDetailsRepository;
import com.app.employeePortal.mileage.entity.MileageNotesLink;
import com.app.employeePortal.mileage.repository.MileageNotesLinkRepository;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.organization.entity.OrgIndustry;
import com.app.employeePortal.organization.entity.OrganizationDetails;
import com.app.employeePortal.organization.entity.OrganizationSubsriptionDetails;
import com.app.employeePortal.organization.mapper.FiscalMapper;
import com.app.employeePortal.organization.repository.OrgIndustryRepository;
import com.app.employeePortal.organization.repository.OrganizationRepository;
import com.app.employeePortal.organization.repository.OrganizationSubsriptionDetailsRepository;
import com.app.employeePortal.organization.service.OrganizationService;
import com.app.employeePortal.partner.entity.PartnerNotesLink;
import com.app.employeePortal.partner.repository.PartnerNotesLinkRepository;
import com.app.employeePortal.permission.entity.Communication;
import com.app.employeePortal.permission.entity.Compliance;
import com.app.employeePortal.permission.entity.DepartmentPermission;
import com.app.employeePortal.permission.entity.Permission;
import com.app.employeePortal.permission.entity.ThirdParty;
import com.app.employeePortal.permission.repository.CommunicationRepository;
import com.app.employeePortal.permission.repository.ComplianceRepository;
import com.app.employeePortal.permission.repository.DepartmentPermissionRepository;
import com.app.employeePortal.permission.repository.PermissionRepository;
import com.app.employeePortal.permission.repository.ThirdPartyRepository;
import com.app.employeePortal.recruitment.entity.RecruitmentCloseRule;
import com.app.employeePortal.recruitment.repository.RecruitmentCloseRuleRepository;
import com.app.employeePortal.registration.mapper.DepartmentMapper;
import com.app.employeePortal.registration.mapper.NewAdminRegisterMapper;
import com.app.employeePortal.sequence.entity.Sequence;
import com.app.employeePortal.sequence.repository.SequenceRepository;
import com.app.employeePortal.task.entity.TaskNotesLink;
import com.app.employeePortal.task.repository.TaskNotesLinkRepository;
import com.app.employeePortal.unboardingWorkflow.entity.UnboardingStages;
import com.app.employeePortal.unboardingWorkflow.entity.UnboardingWorkflowDetails;
import com.app.employeePortal.unboardingWorkflow.repository.UnboardingStagesRepository;
import com.app.employeePortal.unboardingWorkflow.repository.UnboardingWorkflowDetailsRepository;
import com.app.employeePortal.unboardingWorkflow.service.UnboardingWorkflowService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    DefinationInfoRepository definationInfoRepository;

    @Autowired
    DefinationDeleteRepository definationDeleteRepository;
    @Autowired
    DefinationRepository definationRepository;
    @Autowired
    EmployeeAddressLinkRepository employeeAddressLinkRepository;
    @Autowired
    EmployeeInfoRepository employeeInfoRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    AddressInfoRepository addressInfoRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DocumentDetailsRepository documentDetailsRepository;
    @Autowired
    UserSettingsRepository userSettingsRepository;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    EducationalRepository educationalRepository;
    @Autowired
    EmployeeContactAddressRepository employeeContactAddressRepository;
    @Autowired
    EmployementHistoryRepository employementHistoryRepository;
    @Autowired
    KeySkillRepository keySkillRepository;
    @Autowired
    PersonalRepository personalRepository;
    @Autowired
    TrainingRepository trainingRepository;
    @Autowired
    ContactService contactService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    AddressService addressService;
    @Autowired
    EmailService emailService;
    @Autowired
    EmployeeIDRepository employeeIdRepository;
    @Autowired
    OrganizationService organizationService;
    @Autowired
    NotesRepository notesRepository;
    @Autowired
    SalaryRepository salaryRepository;
    @Autowired
    SalaryInfoRepository salaryInfoRepository;
    @Autowired
    EmployeeNotesLinkRepository employeeNotesLinkRepository;
    @Autowired
    EmployeeDocumentLinkRepository employeeDocumentLinkRepository;
    @Autowired
    EmployeeContractRepository employeeContractRepository;
    @Autowired
    DocumentTypeRepository documentTypeRepository;
    @Autowired
    DesignationRepository designationRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EducationTypeRepository educationTypeRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    DepartmentPermissionRepository departmentPermissionRepository;
    @Autowired
    CommunicationRepository communicationRepository;
    @Autowired
    ComplianceRepository complianceRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    EmployeeRoleLinkRepository employeeRoleLinkRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    SequenceRepository sequenceRepository;
    @Autowired
    OrganizationSubsriptionDetailsRepository organizationSubsriptionDetailsRepository;
    @Autowired
    RoleTypeRepository roleTypeRepository;
    @Autowired
    LocationDetailsRepository locationDetailsRepository;
    @Autowired
    VisaRepository visaRepository;
    @Autowired
    DefaultDepartmentRepository defaultDepartmentRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    RecruitmentCloseRuleRepository recruitmentCloseRuleRepository;
    @Autowired
    EmployeeAdminUpdateRepository employeeAdminUpdateRepository;
    @Autowired
    NotificationService notificationService;
    @Autowired
    UserKpiLinkRepository userKpiLinkRepository;
    @Autowired
    PerformanceManagementRepository performanceManagementRepository;
    @Autowired
    EmployeeWorkflowLinkRepository employeeWorkflowLinkRepository;
    @Autowired
    UnboardingWorkflowDetailsRepository unboardingWorkflowDetailsRepository;
    @Autowired
    UnboardingStagesRepository unboardingStagesRepository;
    @Autowired
    UnboardingWorkflowService unboardingWorkflowService;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    TimezoneRepository timezoneRepository;
    @Autowired
    ServiceLineRepository serviceLineRepository;
    @Autowired
    UserSalaryBreakoutRepository userSalaryBreakoutRepository;
    @Autowired
    LobDetailsRepository lobRepository;
    @Autowired
    DocumentService documentService;
    @Autowired
    UserEquipmentLinkRepository userEquipmentLinkRepository;
    @Autowired
    EquipmentRepository equipmentRepository;
    @Autowired
    CellChamberUserLinkRepository cellChamberUserLinkRepository;
    @Autowired
    CellChamberLinkRepository cellChamberLinkRepository;
    @Autowired
    EmployeeEmailLinkRepository employeeEmailLinkRepository;
    @Autowired
    CallNotesLinkRepository callNotesLinkRepository;
    @Autowired
    ContactNotesLinkRepository contactNotesLinkRepository;
    @Autowired
    OpportunityNotesLinkRepository opportunityNotesLinkRepository;
    @Autowired
    CustomerNotesLinkRepository customerNotesLinkRepository;
    @Autowired
    EventNotesLinkRepository eventNotesLinkRepository;
    @Autowired
    ExpenseNotesLinkRepository expenseNotesLinkRepository;
    @Autowired
    InvestorNotesLinkRepo investorNotesLinkRepo;
    @Autowired
    InvestorLeadsNotesLinkRepository investorLeadsNotesLinkRepository;
    @Autowired
    LeadsNotesLinkRepository leadsNotesLinkRepository;
    @Autowired
    LeaveNotesLinkRepository leaveNotesLinkRepository;
    @Autowired
    MileageNotesLinkRepository mileageNotesLinkRepository;
    @Autowired
    PartnerNotesLinkRepository partnerNotesLinkRepository;
    @Autowired
    TaskNotesLinkRepository taskNotesLinkRepository;
    @Autowired
    OrgIndustryRepository orgIndustryRepository;
    @Autowired
    RoomNotesLinkRepository roomNotesLinkRepository;
    @Autowired
    AesEncryptor aesEncryptor;
    @Autowired
    CountryRepository countryRepository;

    private String[] employee_headings = {"candidate Id", "First Name", "Middle Name", "Last Name", "Mobile#",
            "phone#"};

    @Override
    public EmployeeTableMapper saveToEmployeeProcess(EmployeeMapper employeeMapper, String orgId) {

        EmployeeTableMapper resultMapper = null;

        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setCreationDate(new Date());
        employeeInfo.setCreatorId(employeeMapper.getCreatorId());

        EmployeeInfo employeeInfoo = employeeInfoRepository.save(employeeInfo);
        String employeeId = employeeInfoo.getId();

        if (null != employeeId) {

            EmployeeDetails employeeDetails = new EmployeeDetails();
            employeeDetails.setEmployeeId(employeeId);
            employeeDetails.setFirstName(employeeMapper.getFirstName());
            employeeDetails.setMiddleName(employeeMapper.getMiddleName());
            employeeDetails.setLastName(employeeMapper.getLastName());
            // employeeDetails.setFullName(employeeMapper.getFirstName()+"
            // "+employeeMapper.getMiddleName()+" "+employeeMapper.getLastName());

            String middleName = " ";
            String lastName = "";
            String satutation = "";

            if (!StringUtils.isEmpty(employeeMapper.getLastName())) {

                lastName = employeeMapper.getLastName();
            }
            if (employeeMapper.getSalutation() != null && employeeMapper.getSalutation().length() > 0) {
                satutation = employeeMapper.getSalutation();
            }

            if (employeeMapper.getMiddleName() != null && employeeMapper.getMiddleName().length() > 0) {

                middleName = employeeMapper.getMiddleName();
                employeeDetails.setFullName(
                        satutation + " " + employeeMapper.getFirstName() + " " + middleName + " " + lastName);
            } else {

                employeeDetails.setFullName(satutation + " " + employeeMapper.getFirstName() + " " + lastName);
            }
            employeeDetails.setGender(employeeMapper.getGender());
            employeeDetails.setSalutation(employeeMapper.getSalutation());
            employeeDetails.setEmailId(employeeMapper.getEmailId().toLowerCase());
            employeeDetails.setSecondaryEmailId(employeeMapper.getSecondaryEmailId());
            employeeDetails.setMobileNo(employeeMapper.getMobileNo());
            employeeDetails.setPhoneNo(employeeMapper.getPhoneNo());
            employeeDetails.setSkypeId(employeeMapper.getSkypeId());
            employeeDetails.setFax(employeeMapper.getFax());
            employeeDetails.setLanguage(employeeMapper.getPreferedLanguage());
            employeeDetails.setTimeZone(employeeMapper.getTimeZone());
            employeeDetails.setSuspendInd(false);
            employeeDetails.setMultyOrgAccessInd(false);
            if (employeeMapper.isType()) {
                employeeDetails.setUserType("External");
            } else {
                employeeDetails.setUserType("Internal");
            }
            employeeDetails.setBilledInd(employeeMapper.isBilledInd());

            System.out.println("getReportingManager............ " + employeeMapper.getReportingManager());

            if (employeeMapper.getUserType().equalsIgnoreCase("ADMIN"))
                employeeDetails.setReportingManager(employeeId);
            else
                employeeDetails.setReportingManager(employeeMapper.getReportingManager());

            try {

                if (null != employeeMapper.getDob()) {
                    employeeDetails.setDob(Utility.getDateFromISOString(employeeMapper.getDob()));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null != employeeMapper.getDateOfJoining()) {
                try {
                    employeeDetails.setDateOfJoining(Utility.getDateFromISOString(employeeMapper.getDateOfJoining()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            employeeDetails.setStatus(employeeMapper.getStatus());
            employeeDetails.setLinkedinPublicUrl(employeeMapper.getLinkedinPublicUrl());
            employeeDetails.setDesignation(employeeMapper.getDesignationTypeId());
            employeeDetails.setCreatorId(employeeId);
            employeeDetails.setImageId(employeeMapper.getImageId());
            employeeDetails.setOrgId(orgId);
            employeeDetails.setRole(employeeMapper.getRole());
            employeeDetails.setCountry(employeeMapper.getCountry());
            employeeDetails.setFacebook(employeeMapper.getFacebook());
            employeeDetails.setTwitter(employeeMapper.getTwitter());
            employeeDetails.setDepartment(employeeMapper.getDepartmentId());
            employeeDetails.setReportingManagerDept(employeeMapper.getReportingManagerDeptId());
            employeeDetails.setRoleType(employeeMapper.getRoleType());
            employeeDetails.setCreationDate(new Date());
            employeeDetails.setCountryDialCode(employeeMapper.getCountryDialCode());
            employeeDetails.setCountryDialCode1(employeeMapper.getCountryDialCode1());
            employeeDetails.setWorkplace(employeeMapper.getWorkplace());
            employeeDetails.setCurrency(employeeMapper.getCurrency());
            employeeDetails.setLabel(employeeMapper.getLabel());
            employeeDetails.setJobType(employeeMapper.getJobType());
            employeeDetails.setEmployeeType(employeeMapper.getEmployeeType());
            employeeDetails.setLiveInd(true);
            employeeDetails.setUserId(employeeId);
            employeeDetails.setBloodGroup(employeeMapper.getBloodGroup());
            employeeDetails.setLocation(employeeMapper.getLocation());
            employeeDetails.setSalary(employeeMapper.getSalary());
            employeeDetails.setServiceLine(employeeMapper.getServiceLineId());
            employeeDetails.setSecondaryReptManagerDept(employeeMapper.getSecondaryReptManagerDept());
            employeeDetails.setSecondaryReptManager(employeeMapper.getSecondaryReptManager());

            EmployeeDetails employeeDetailss = employeeRepository.save(employeeDetails);

            if (employeeMapper.getAddress() != null) {

                /* insert to address table */

                List<AddressMapper> list = employeeMapper.getAddress();

                String addressId = null;
                if (null != list && !list.isEmpty()) {

                    for (AddressMapper addressMapper : list) {

                        if (null != addressMapper.getCountry() && !addressMapper.getCountry().isEmpty()) {
                            AddressInfo addressInfo = new AddressInfo();
                            addressInfo.setCreationDate(new Date());
                            addressInfo.setCreatorId(employeeId);

                            AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

                            addressId = addressInfoo.getId();

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
                                addressDetails.setXlAddress(addressMapper.getXlAddress());
                                addressRepository.save(addressDetails);

                                EmployeeAddressLink employeeAddressLink = new EmployeeAddressLink();
                                employeeAddressLink.setEmployeeId(employeeId);
                                employeeAddressLink.setAddressId(addressId);
                                employeeAddressLink.setCreationDate(new Date());
                                employeeAddressLink.setLiveInd(true);

                                employeeAddressLinkRepository.save(employeeAddressLink);

                            }
                        }

                    }

                }

            }


            /* insert to user settings */
            UserSettings userSettings = new UserSettings();
            userSettings.setCreationDate(new Date());
            userSettings.setEmail(employeeMapper.getEmailId().toLowerCase());
            userSettings.setUserId(employeeId);
            userSettings.setUserType(employeeMapper.getUserType());
            userSettings.setDeviceValInd(false);
            userSettings.setUserActiveInd(false);
            userSettings.setEmailValInd(false);
            userSettings.setPasswordActiveInd(false);
            userSettings.setLiveInd(true);
            userSettingsRepository.save(userSettings);

            /* send email */
//        if (null != orgId && null != employeeId) {
//
//            OrganizationDetails organizationDetails = organizationRepository.getOrganizationDetailsById(orgId);
//
//            String orgName = organizationDetails.getName();
//            String from = "engage@tekorero.com";
//            String to = employeeMapper.getEmailId();
//            String name = employeeMapper.getFirstName();
//            String subject = "Welcome to Korero HRMS";
//            String message = emailService.prepareEmailValidationLink(to, employeeId, orgId, name, orgName);
//
//            String serverUrl = "https://develop.tekorero.com/kite/email/send";
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            body.add("fromEmail", from);
//            body.add("message", message);
//            body.add("subject", subject);
//            body.add("toEmail", to);
//            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);

//        }

            resultMapper = getEmployeeDetail(employeeDetailss);
        }
        return resultMapper;
    }


    @Override
    public EmployeeTableMapper saveToEmployeeProcess(NewAdminRegisterMapper employeeMapper, String orgId) {

        EmployeeTableMapper resultMapper = null;

        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setCreationDate(new Date());
        employeeInfo.setCreatorId("");

        EmployeeInfo employeeInfoo = employeeInfoRepository.save(employeeInfo);
        String employeeId = employeeInfoo.getId();

        if (null != employeeId) {

            EmployeeDetails employeeDetails = new EmployeeDetails();
            employeeDetails.setEmployeeId(employeeId);
            employeeDetails.setFirstName(employeeMapper.getFirstName());
            employeeDetails.setMiddleName(employeeMapper.getMiddleName());
            employeeDetails.setLastName(employeeMapper.getLastName());
            // employeeDetails.setFullName(employeeMapper.getFirstName()+"
            // "+employeeMapper.getMiddleName()+" "+employeeMapper.getLastName());

            String middleName = " ";
            String lastName = "";
            String satutation = "";

            if (!StringUtils.isEmpty(employeeMapper.getLastName())) {

                lastName = employeeMapper.getLastName();
            }
//            if (employeeMapper.getSalutation() != null && employeeMapper.getSalutation().length() > 0) {
//                satutation = employeeMapper.getSalutation();
//            }

            if (employeeMapper.getMiddleName() != null && employeeMapper.getMiddleName().length() > 0) {

                middleName = employeeMapper.getMiddleName();
                employeeDetails.setFullName(
                        satutation + " " + employeeMapper.getFirstName() + " " + middleName + " " + lastName);
            } else {

                employeeDetails.setFullName(satutation + " " + employeeMapper.getFirstName() + " " + lastName);
            }
//            employeeDetails.setGender(employeeMapper.getGender());
//            employeeDetails.setSalutation(employeeMapper.getSalutation());
            employeeDetails.setEmailId(employeeMapper.getEmailId());
//            employeeDetails.setSecondaryEmailId(employeeMapper.getSecondaryEmailId());
//            employeeDetails.setMobileNo(employeeMapper.getMobileNo());
//            employeeDetails.setPhoneNo(employeeMapper.getPhoneNo());
//            employeeDetails.setSkypeId(employeeMapper.getSkypeId());
//            employeeDetails.setFax(employeeMapper.getFax());
//            employeeDetails.setCurrency(employeeMapper.getCurrency());
//            employeeDetails.setLanguage(employeeMapper.getPreferedLanguage());
//            employeeDetails.setTimeZone(ZoneId.systemDefault().getId());
            employeeDetails.setSuspendInd(false);
            employeeDetails.setMultyOrgAccessInd(false);
//            if (employeeMapper.isType()) {
            if (true) {
                employeeDetails.setUserType("External");
            } else {
                employeeDetails.setUserType("Internal");
            }
//            employeeDetails.setBilledInd(employeeMapper.isBilledInd());

//            System.out.println("getReportingManager............ " + employeeMapper.getReportingManager());

            if (employeeMapper.getUserType().equalsIgnoreCase("ADMIN"))
                employeeDetails.setReportingManager(employeeId);
            else
                employeeDetails.setReportingManager(employeeId);

//            try {
//
//                if (null != employeeMapper.getDob()) {
//                    employeeDetails.setDob(Utility.getDateFromISOString(employeeMapper.getDob()));
//
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (null != employeeMapper.getDateOfJoining()) {
//                try {
//                    employeeDetails.setDateOfJoining(Utility.getDateFromISOString(employeeMapper.getDateOfJoining()));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//            employeeDetails.setStatus(employeeMapper.getStatus());
//            employeeDetails.setLinkedinPublicUrl(employeeMapper.getLinkedinPublicUrl());
//            employeeDetails.setDesignation(employeeMapper.getDesignationTypeId());
            employeeDetails.setCreatorId(employeeId);
//            employeeDetails.setImageId(employeeMapper.getImageId());
            employeeDetails.setOrgId(orgId);
            employeeDetails.setRole(employeeMapper.getRole());
//            employeeDetails.setCountry(employeeMapper.getCountry());
//            employeeDetails.setFacebook(employeeMapper.getFacebook());
//            employeeDetails.setTwitter(employeeMapper.getTwitter());
//            employeeDetails.setDepartment(employeeMapper.getDepartmentId());
//            employeeDetails.setRoleType(employeeMapper.getRoleType());
            employeeDetails.setCreationDate(new Date());
//            employeeDetails.setCountryDialCode(employeeMapper.getCountryDialCode());
//            employeeDetails.setCountryDialCode1(employeeMapper.getCountryDialCode1());
//            employeeDetails.setWorkplace(employeeMapper.getWorkplace());
//            employeeDetails.setCurrency(employeeMapper.getCurrency());
//            employeeDetails.setLabel(employeeMapper.getLabel());
//            employeeDetails.setJobType(employeeMapper.getJobType());
//            employeeDetails.setEmployeeType(employeeMapper.getEmployeeType());
            employeeDetails.setLiveInd(true);
            employeeDetails.setUserId(employeeId);
//            employeeDetails.setBloodGroup(employeeMapper.getBloodGroup());
//            employeeDetails.setLocation(employeeMapper.getLocation());
            EmployeeDetails employeeDetailss = employeeRepository.save(employeeDetails);

//            if (employeeMapper.getAddress() != null) {
//
//                /* insert to address table */
//
//                List<AddressMapper> list = employeeMapper.getAddress();
//
//                String addressId = null;
//                if (null != list && !list.isEmpty()) {
//
//                    for (AddressMapper addressMapper : list) {
//
//                        if (null != addressMapper.getCountry() && !addressMapper.getCountry().isEmpty()) {
//                            AddressInfo addressInfo = new AddressInfo();
//                            addressInfo.setCreationDate(new Date());
//                            addressInfo.setCreatorId(employeeId);
//
//                            AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);
//
//                            addressId = addressInfoo.getId();
//
//                            if (null != addressId) {
//
//                                AddressDetails addressDetails = new AddressDetails();
//                                addressDetails.setAddressId(addressId);
//                                addressDetails.setAddressLine1(addressMapper.getAddress1());
//                                addressDetails.setAddressLine2(addressMapper.getAddress2());
//                                addressDetails.setAddressType(addressMapper.getAddressType());
//                                addressDetails.setCountry(addressMapper.getCountry());
//                                addressDetails.setCreationDate(new Date());
//                                addressDetails.setStreet(addressMapper.getStreet());
//                                addressDetails.setCity(addressMapper.getCity());
//                                addressDetails.setPostalCode(addressMapper.getPostalCode());
//                                addressDetails.setTown(addressMapper.getTown());
//                                addressDetails.setState(addressMapper.getState());
//                                addressDetails.setLatitude(addressMapper.getLatitude());
//                                addressDetails.setLongitude(addressMapper.getLongitude());
//                                addressDetails.setLiveInd(true);
//                                addressDetails.setHouseNo(addressMapper.getHouseNo());
//                                addressRepository.save(addressDetails);
//
//                                EmployeeAddressLink employeeAddressLink = new EmployeeAddressLink();
//                                employeeAddressLink.setEmployeeId(employeeId);
//                                employeeAddressLink.setAddressId(addressId);
//                                employeeAddressLink.setCreationDate(new Date());
//                                employeeAddressLink.setLiveInd(true);
//
//                                employeeAddressLinkRepository.save(employeeAddressLink);
//
//                            }
//                        }
//
//                    }
//
//                }
//
//            }

        }

        /* insert to user settings */
        UserSettings userSettings = new UserSettings();
        userSettings.setCreationDate(new Date());
        userSettings.setEmail(employeeMapper.getEmailId());
        userSettings.setUserId(employeeId);
        userSettings.setUserType(employeeMapper.getUserType());
        userSettings.setDeviceValInd(false);
        userSettings.setUserActiveInd(true);
        userSettings.setEmailValInd(true);
        userSettings.setPasswordActiveInd(true);
        userSettings.setLiveInd(true);
        userSettings.setPassword(new BCryptPasswordEncoder().encode(employeeMapper.getPassword()));
        userSettingsRepository.save(userSettings);

        /* send email */
//        if (null != orgId && null != employeeId) {
//
//            OrganizationDetails organizationDetails = organizationRepository.getOrganizationDetailsById(orgId);
//
//            String orgName = organizationDetails.getName();
//            String from = "engage@tekorero.com";
//            String to = employeeMapper.getEmailId();
//            String name = employeeMapper.getFirstName();
//            String subject = "Welcome to Korero HRMS";
//            String message = emailService.prepareEmailValidationLink(to, employeeId, orgId, name, orgName);
//
//            String serverUrl = "https://develop.tekorero.com/kite/email/send";
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            body.add("fromEmail", from);
//            body.add("message", message);
//            body.add("subject", subject);
//            body.add("toEmail", to);
//            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
//
//        }
        resultMapper = getEmployeeDetailByEmployeeId(employeeId);

        return resultMapper;
    }

    @Override
    public EmployeeViewMapper getEmployeeDetails(EmployeeDetails employeeDetails) {

        EmployeeViewMapper employeeMapper = new EmployeeViewMapper();

        if (null != employeeDetails) {

            employeeMapper.setEmployeeId(employeeDetails.getEmployeeId());
            employeeMapper.setFirstName(employeeDetails.getFirstName());
            employeeMapper.setMiddleName(employeeDetails.getMiddleName());
            employeeMapper.setLastName(employeeDetails.getLastName());
            employeeMapper.setCreationDate(Utility.getISOFromDate((employeeDetails.getCreationDate())));
            employeeMapper.setCreatorId(employeeDetails.getCreatorId());
            employeeMapper.setDateOfJoining(Utility.getISOFromDate(employeeDetails.getDateOfJoining()));
            employeeMapper.setReportingManager(employeeDetails.getReportingManager());
            employeeMapper.setReportingManagerName(getEmployeeFullName(employeeDetails.getReportingManager()));

            if (null != employeeDetails.getDob()) {
                employeeMapper.setDob(Utility.getISOFromDate(employeeDetails.getDob()));

            } else {
                employeeMapper.setDob("");
            }

            if (null != employeeDetails.getDateOfJoining()) {
                employeeMapper.setDateOfJoining(Utility.getISOFromDate(employeeDetails.getDateOfJoining()));
            } else {
                employeeMapper.setDateOfJoining("");

            }
            employeeMapper.setEmailId(employeeDetails.getEmailId());
            employeeMapper.setSecondaryEmailId(employeeDetails.getSecondaryEmailId());
            employeeMapper.setFax(employeeDetails.getFax());
            // employeeMapper.setFullName(employeeDetails.getFullName());
            employeeMapper.setGender(employeeDetails.getGender());
            employeeMapper.setImageId(employeeDetails.getImageId());
            employeeMapper.setLinkedinPublicUrl(employeeDetails.getLinkedinPublicUrl());
            employeeMapper.setMobileNo(employeeDetails.getMobileNo());
            employeeMapper.setPhoneNo(employeeDetails.getPhoneNo());
            employeeMapper.setSkypeId(employeeDetails.getSkypeId());
            employeeMapper.setSalutation(employeeDetails.getSalutation());
            employeeMapper.setStatus(employeeDetails.getStatus());
            employeeMapper.setPreferedLanguage(employeeDetails.getLanguage());
            //employeeMapper.setTimeZone(employeeDetails.getTimeZone());
            employeeMapper.setSuspendInd(employeeDetails.isSuspendInd());
            employeeMapper.setEmployeeType(employeeDetails.getEmployeeType());
            employeeMapper.setWorkplace(employeeDetails.getWorkplace());
            employeeMapper.setLabel(employeeDetails.getLabel());
            // employeeMapper.setType(employeeDetails.getUserType());
            employeeMapper.setBilledInd(employeeDetails.isBilledInd());
            employeeMapper.setSalary(employeeDetails.getSalary());
            employeeMapper.setMultyOrgLinkInd(employeeDetails.isMultyOrgLinkInd());
            employeeMapper.setMultyOrgAccessInd(employeeDetails.isMultyOrgAccessInd());

            ServiceLine db = serviceLineRepository.findByServiceLineId(employeeDetails.getServiceLine());
            if (null != db) {
                employeeMapper.setServiceLine(db.getServiceLineName());
                employeeMapper.setServiceLineId(db.getServiceLineId());
            }
            System.out.println("EMPLOYEEID=" + employeeDetails.getEmployeeId());
            if (employeeDetails.getUserType().equalsIgnoreCase("External")) {
                employeeMapper.setType(true);
            } else {
                employeeMapper.setType(false);
            }
            System.out.println("FullName===="+employeeDetails.getFullName());
            String middleName = " ";
            String lastName = "";

            if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                lastName = employeeDetails.getLastName();
            }

            if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

                middleName = employeeDetails.getMiddleName();
                employeeMapper.setFullName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
            } else {

                employeeMapper.setFullName(employeeDetails.getFirstName() + " " + lastName);
            }
            System.out.println("FullName===="+employeeMapper.getFullName());
            if (!StringUtils.isEmpty(employeeDetails.getDesignation())) {
                Designation designationn = designationRepository
                        .findByDesignationTypeId(employeeDetails.getDesignation());
                if (null != designationn) {
                    employeeMapper.setDesignationTypeId(designationn.getDesignationTypeId());
                    employeeMapper.setDesignation(designationn.getDesignationType());
                }
            }

            Timezone timeZone = timezoneRepository.findByTimezoneId(employeeDetails.getTimeZone());
            if (null != timeZone) {
                employeeMapper.setTimeZone(timeZone.getZoneName());
            }

            Module module = moduleRepository.findByOrgId(employeeDetails.getOrgId());
            ModuleDepartmentResponseMapper resultMapper = new ModuleDepartmentResponseMapper();

            if (null != module) {
                resultMapper.setCrmInd(module.isCrmInd());
                resultMapper.setErpInd(module.isErpInd());
                resultMapper.setImInd(module.isImInd());
                resultMapper.setInventoryInd(module.isInventoryInd());
                resultMapper.setLogisticsInd(module.isLogisticsInd());
                resultMapper.setOrderManagementInd(module.isOrderManagementInd());
                resultMapper.setProcurementInd(module.isProcurementInd());
                resultMapper.setProductionInd(module.isProductionInd());
                resultMapper.setRecruitProInd(module.isRecruitProInd());
                resultMapper.setRepairInd(module.isRepairInd());
                resultMapper.setELearningInd(module.isELearningInd());
                resultMapper.setFinanceInd(module.isFinanceInd());
                resultMapper.setEcomModInd(module.isEcomModInd());
                resultMapper.setProjectModInd(module.isProjectModInd());
                resultMapper.setTradingInd(module.isTradingInd());
            }

            employeeMapper.setModuleMapper(resultMapper);

            if (!StringUtils.isEmpty(employeeDetails.getDepartment())) {
                // employeeMapper.setDepartment(employeeDetails.getDepartment());
                // System.out.println("department
                // Name@@@@@@@@@@@@"+employeeDetails.getDepartment());

                Department department = departmentRepository.getDepartmentDetails(employeeDetails.getDepartment());
                if (null != department) {
                    employeeMapper.setDepartmentId(department.getDepartment_id());
                    employeeMapper.setDepartment(department.getDepartmentName());
                    employeeMapper.setReportingManagerDept(department.getDepartmentName());
                    employeeMapper.setReportingManagerDept(department.getDepartmentName());
                    employeeMapper.setCrmInd(department.isCrmInd());
                    employeeMapper.setErpInd(department.isErpInd());
                    employeeMapper.setImInd(department.isImInd());
                    employeeMapper.setAccountInd(department.isAccountInd());
                    employeeMapper.setRecruitOppsInd(department.isRecruitOppsInd());
                    employeeMapper.setHrInd(department.isHrInd());
                    employeeMapper.setInventoryInd(department.isInventoryInd());
                    employeeMapper.setLogisticsInd(department.isLogisticsInd());
                    employeeMapper.setOrderManagementInd(department.isOrderManagementInd());
                    employeeMapper.setProcurementInd(department.isProcurementInd());
                    employeeMapper.setProductionInd(department.isProductionInd());
                    employeeMapper.setRecruitProInd(department.isRecruitProInd());
                    employeeMapper.setRepairInd(department.isRepairInd());
                    employeeMapper.setELearningInd(department.isELearningInd());
                    employeeMapper.setFinanceInd(department.isFinanceInd());
                }

                Department department1 = departmentRepository.getDepartmentDetails(employeeDetails.getReportingManagerDept());
                if (null != department1) {
                    employeeMapper.setReportingManagerDeptId(department1.getDepartment_id());
                    employeeMapper.setReportingManagerDept(department1.getDepartmentName());

                }

                Department department2 = departmentRepository.getDepartmentDetails(employeeDetails.getSecondaryReptManagerDept());
                if (null != department2) {
                    employeeMapper.setSecondaryReptManagerDeptId(department2.getDepartment_id());
                    employeeMapper.setSecondaryReptManagerDeptName(department2.getDepartmentName());

                }
                EmployeeShortMapper employee = getEmployeeFullNameAndEmployeeId(employeeDetails.getSecondaryReptManager());
                if (null != employee) {
                    employeeMapper.setSecondaryReptManagerId(employee.getEmployeeId());
                    employeeMapper.setSecondaryReptManagerName(employee.getEmpName());

                }
                RecruitmentCloseRule recruitmentCloseRule = recruitmentCloseRuleRepository.findByOrgId(employeeDetails.getOrgId());

                if (null != recruitmentCloseRule) {
//        			employeeMapper.setOrderCreatProductionInd(recruitmentCloseRule.isProductionInd());
//        			employeeMapper.setOrderCreatRepairInd(recruitmentCloseRule.isRepairInd());
//        			employeeMapper.setMakeToInd(recruitmentCloseRule.isMakeToInd());
                    employeeMapper.setIndependentInd(recruitmentCloseRule.isIndependentInd());
                    employeeMapper.setPartNoInd(recruitmentCloseRule.isPartNoInd());
                    employeeMapper.setTrnsfrEvthngToErpInd(recruitmentCloseRule.isTrnsfrEvthngToErpInd());
                    employeeMapper.setTrnsfrToErpQtionWinInd(recruitmentCloseRule.isTrnsfrToErpQtionWinInd());
                    employeeMapper.setProcessInd(recruitmentCloseRule.isProcessInd());
                    employeeMapper.setTypeInd(recruitmentCloseRule.isTypeInd());
                    employeeMapper.setFifoInd(recruitmentCloseRule.isFifoInd());
                    employeeMapper.setRepairOrdInd(recruitmentCloseRule.isRepairOrdInd());
                    employeeMapper.setProInd(recruitmentCloseRule.isProInd());
                }

                RoleType role = roleTypeRepository.findByRoleTypeId(employeeDetails.getRoleType());
                if (null != role) {
                    employeeMapper.setRoleType(role.getRoleType());

                    DepartmentPermission dep = departmentPermissionRepository
                            .getDepartmentPermission(role.getRoleTypeId(), employeeDetails.getOrgId());
                    if (null != dep) {
                        employeeMapper.setVendorAccessInd(dep.isVendorAccessInd());
                        employeeMapper.setVendorCreateInd(dep.isVendorCreateInd());
                        employeeMapper.setVendorUpdateInd(dep.isVendorUpdateInd());
                        employeeMapper.setVendorDeleteInd(dep.isVendorDeleteInd());
                        employeeMapper.setVendorFullListInd(dep.isVendorFullListInd());

                        employeeMapper.setCustomerAccessInd(dep.isCustomerAccessInd());
                        employeeMapper.setCustomerCreateInd(dep.isCustomerUpdateInd());
                        employeeMapper.setCustomerUpdateInd(dep.isCustomerUpdateInd());
                        employeeMapper.setCustomerDeleteInd(dep.isCustomerDeleteInd());
                        employeeMapper.setCustomerFullListInd(dep.isCustomerFullListInd());                       

                        employeeMapper.setOpportunityAccessInd(dep.isOpportunityAccessInd());
                        employeeMapper.setOpportunityCreateInd(dep.isOpportunityCreateInd());
                        employeeMapper.setOpportunityUpdateInd(dep.isOpportunityUpdateInd());
                        employeeMapper.setOpportunityDeleteInd(dep.isOpportunityDeleteInd());
                        employeeMapper.setOpportunityFullListInd(dep.isOpportunityFullListInd());

                        employeeMapper.setTalentAccessInd(dep.isTalentAccessInd());
                        employeeMapper.setTalentCreateInd(dep.isTalentCreateInd());
                        employeeMapper.setTalentUpdateInd(dep.isTalentUpdateInd());
                        employeeMapper.setTalentDeleteInd(dep.isTalentDeleteInd());
                        employeeMapper.setTalentFullListInd(dep.isTalentFullListInd());

                        employeeMapper.setPublishAccessInd(dep.isPublishAccessInd());
                        employeeMapper.setPublishCreateInd(dep.isPublishCreateInd());
                        employeeMapper.setPublishUpdateInd(dep.isPublishUpdateInd());
                        employeeMapper.setPublishDeleteInd(dep.isPublishDeleteInd());
                        employeeMapper.setPublishFullListInd(dep.isPublishFullListInd());

                        employeeMapper.setContactAccessInd(dep.isContactAccessInd());
                        employeeMapper.setContactCreateInd(dep.isContactCreateInd());
                        employeeMapper.setContactUpdateInd(dep.isContactUpdateInd());
                        employeeMapper.setContactDeleteInd(dep.isContactDeleteInd());
                        employeeMapper.setContactFullListInd(dep.isContactFullListInd());

                        employeeMapper.setRequirementAccessInd(dep.isRequirementAccessInd());
                        employeeMapper.setRequirementCreateInd(dep.isRequirementCreateInd());
                        employeeMapper.setRequirementUpdateInd(dep.isRequirementUpdateInd());
                        employeeMapper.setRequirementDeleteInd(dep.isRequirementDeleteInd());
                        employeeMapper.setRequirementFullListInd(dep.isRequirementFullListInd());

                        employeeMapper.setPulseAccessInd(dep.isPulseAccessInd());
                        employeeMapper.setPulseCreateInd(dep.isPulseCreateInd());
                        employeeMapper.setPulseUpdateInd(dep.isPulseUpdateInd());
                        employeeMapper.setPulseDeleteInd(dep.isPulseDeleteInd());
                        employeeMapper.setPulseFullListInd(dep.isPulseFullListInd());

                        employeeMapper.setAssessmentAccessInd(dep.isAssessmentAccessInd());
                        employeeMapper.setAssessmentCreateInd(dep.isAssessmentCreateInd());
                        employeeMapper.setAssessmentUpdateInd(dep.isAssessmentUpdateInd());
                        employeeMapper.setAssessmentDeleteInd(dep.isAssessmentDeleteInd());
                        employeeMapper.setAssessmentFullListInd(dep.isAssessmentFullListInd());

                        employeeMapper.setLeadsAccessInd(dep.isLeadsAccessInd());
                        employeeMapper.setLeadsCreateInd(dep.isLeadsCreateInd());
                        employeeMapper.setLeadsUpdateInd(dep.isLeadsUpdateInd());
                        employeeMapper.setLeadsDeleteInd(dep.isLeadsDeleteInd());
                        employeeMapper.setLeadsFullListInd(dep.isLeadsFullListInd());

                        employeeMapper.setTestAccessInd(dep.isTestAccessInd());
                        employeeMapper.setTestCreateInd(dep.isTestCreateInd());
                        employeeMapper.setTestUpdateInd(dep.isTestUpdateInd());
                        employeeMapper.setTestDeleteInd(dep.isTestDeleteInd());
                        employeeMapper.setTestFullListInd(dep.isTestFullListInd());

                        employeeMapper.setProgramAccessInd(dep.isProgramAccessInd());
                        employeeMapper.setProgramCreateInd(dep.isProgramCreateInd());
                        employeeMapper.setProgramUpdateInd(dep.isProgramUpdateInd());
                        employeeMapper.setProgramDeleteInd(dep.isProgramDeleteInd());
                        employeeMapper.setProgramFullListInd(dep.isProgramFullListInd());

                        employeeMapper.setCourseAccessInd(dep.isCourseAccessInd());
                        employeeMapper.setCourseCreateInd(dep.isCourseCreateInd());
                        employeeMapper.setCourseUpdateInd(dep.isCourseUpdateInd());
                        employeeMapper.setCourseDeleteInd(dep.isCourseDeleteInd());
                        employeeMapper.setCourseFullListInd(dep.isCourseFullListInd());

                        employeeMapper.setHoursAccessInd(dep.isHoursAccessInd());
                        employeeMapper.setHoursCreateInd(dep.isHoursCreateInd());
                        employeeMapper.setHoursUpdateInd(dep.isHoursUpdateInd());
                        employeeMapper.setHoursDeleteInd(dep.isHoursDeleteInd());
                        employeeMapper.setHoursFullListInd(dep.isHoursFullListInd());

						/*employeeMapper.setTaskAccessInd(dep.isTaskAccessInd());
						employeeMapper.setTaskCreateInd(dep.isTaskCreateInd());
						employeeMapper.setTaskUpdateInd(dep.isTaskUpdateInd());
						employeeMapper.setTaskDeleteInd(dep.isTaskDeleteInd());*/
                        employeeMapper.setTaskFullListInd(dep.isTaskFullListInd());

                        employeeMapper.setComercialAccessInd(dep.isComercialAccessInd());
                        employeeMapper.setComercialCreateInd(dep.isComercialCreateInd());
                        employeeMapper.setComercialUpdateInd(dep.isComercialUpdateInd());
                        employeeMapper.setComercialDeleteInd(dep.isComercialDeleteInd());
                        employeeMapper.setComercialFullListInd(dep.isComercialFullListInd());

                        employeeMapper.setLocationAccessInd(dep.isLocationAccessInd());
                        employeeMapper.setLocationCreateInd(dep.isLocationCreateInd());
                        employeeMapper.setLocationDeleteInd(dep.isLocationDeleteInd());
                        employeeMapper.setLocationFullListInd(dep.isLocationFullListInd());
                        employeeMapper.setLocationUpdateInd(dep.isLocationUpdateInd());

                        employeeMapper.setLeaveAccessInd(dep.isLeaveAccessInd());
                        employeeMapper.setLeaveFullListInd(dep.isLeaveFullListInd());

                        employeeMapper.setExpenseAccessInd(dep.isExpenseAccessInd());
                        employeeMapper.setExpenseFullListInd(dep.isExpenseFullListInd());

                        employeeMapper.setMileageAccessInd(dep.isMileageAccessInd());
                        employeeMapper.setMileageFullListInd(dep.isMileageFullListInd());

                        employeeMapper.setUserAccessInd(dep.isUserAccessInd());
                        employeeMapper.setUserCreateInd(dep.isUserCreateInd());
                        employeeMapper.setUserUpdateInd(dep.isUserUpdateInd());
                        employeeMapper.setUserDeleteInd(dep.isUserDeleteInd());
                        employeeMapper.setUserAccessPlusInd(dep.isUserAccessPlusInd());

                        employeeMapper.setAccountAccessInd(dep.isAccountAccessInd());
                        employeeMapper.setAccountCreateInd(dep.isAccountCreateInd());
                        employeeMapper.setAccountDeleteInd(dep.isAccountUpdateInd());
                        employeeMapper.setAccountUpdateInd(dep.isAccountUpdateInd());
                        employeeMapper.setAccountFullListInd(dep.isAccountFullListInd());
                        employeeMapper.setAccountInfoInd(dep.isAccountInfoInd());

                        employeeMapper.setInventoryAccessInd(dep.isInventoryAccessInd());
                        employeeMapper.setInventoryCreateInd(dep.isInventoryCreateInd());
                        employeeMapper.setInventoryDeleteInd(dep.isInventoryDeleteInd());
                        employeeMapper.setInventoryUpdateInd(dep.isInventoryUpdateInd());
                        employeeMapper.setInventoryFullListInd(dep.isInventoryFullListInd());

                        employeeMapper.setMaterialAccessInd(dep.isMaterialAccessInd());
                        employeeMapper.setMaterialCreateInd(dep.isMaterialCreateInd());
                        employeeMapper.setMaterialDeleteInd(dep.isMaterialDeleteInd());
                        employeeMapper.setMaterialUpdateInd(dep.isMaterialUpdateInd());

                        employeeMapper.setOrderAccessInd(dep.isOrderAccessInd());
                        employeeMapper.setOrderCreateInd(dep.isOrderCreateInd());
                        employeeMapper.setOrderDeleteInd(dep.isOrderDeleteInd());
                        employeeMapper.setOrderUpdateInd(dep.isOrderUpdateInd());
                        employeeMapper.setOrderFullListInd(dep.isOrderFullListInd());

                        employeeMapper.setRefurbishAdminAssignInd(dep.isRefurbishAdminAssignInd());
                        employeeMapper.setRefurbishAdminviewInd(dep.isRefurbishAdminviewInd());
                        employeeMapper.setRefurbishWorkshopInd(dep.isRefurbishWorkshopInd());

                        employeeMapper.setSupplierAccessInd(dep.isSupplierAccessInd());
                        employeeMapper.setSupplierCreateInd(dep.isSupplierCreateInd());
                        employeeMapper.setSupplierDeleteInd(dep.isSupplierDeleteInd());
                        employeeMapper.setSupplierUpdateInd(dep.isSupplierUpdateInd());
                        employeeMapper.setSupplierFullListInd(dep.isSupplierFullListInd());
                        employeeMapper.setSupplierBlockInd(dep.isSupplierBlockInd());
                        employeeMapper.setSupplierInventoryInd(dep.isSupplierInventoryInd());

                        employeeMapper.setDashboardFullListInd(dep.isDashboardFullListInd());
                        employeeMapper.setDashboardAccessInd(dep.isDashboardAccessInd());
                        employeeMapper.setDashboardRegionalInd(dep.isDashboardRegionalInd());

                        employeeMapper.setSettingsAccessInd(dep.isSettingsAccessInd());

                        employeeMapper.setJunkAccessInd(dep.isJunkAccessInd());
                        employeeMapper.setJunkTransferInd(dep.isJunkTransferInd());

                        employeeMapper.setInvestorAccessInd(dep.isInvestorAccessInd());
                        employeeMapper.setInvestorCreateInd(dep.isInvestorCreateInd());
                        employeeMapper.setInvestorDeleteInd(dep.isInvestorDeleteInd());
                        employeeMapper.setInvestorUpdateInd(dep.isInvestorUpdateInd());
                        employeeMapper.setInvestorFullListInd(dep.isInvestorFullListInd());

                        employeeMapper.setInvestorContactAccessInd(dep.isInvestorContactAccessInd());
                        employeeMapper.setInvestorContactCreateInd(dep.isInvestorContactCreateInd());
                        employeeMapper.setInvestorContactDeleteInd(dep.isInvestorContactDeleteInd());
                        employeeMapper.setInvestorContactUpdateInd(dep.isInvestorContactUpdateInd());
                        employeeMapper.setInvestorContactFullListInd(dep.isInvestorContactFullListInd());

                        employeeMapper.setPitchAccessInd(dep.isPitchAccessInd());
                        employeeMapper.setPitchCreateInd(dep.isPitchCreateInd());
                        employeeMapper.setPitchDeleteInd(dep.isPitchDeleteInd());
                        employeeMapper.setPitchUpdateInd(dep.isPitchUpdateInd());
                        employeeMapper.setPitchFullListInd(dep.isPitchFullListInd());

                        employeeMapper.setDealAccessInd(dep.isDealAccessInd());
                        employeeMapper.setDealCreateInd(dep.isDealCreateInd());
                        employeeMapper.setDealDeleteInd(dep.isDealDeleteInd());
                        employeeMapper.setDealUpdateInd(dep.isDealUpdateInd());
                        employeeMapper.setDealFullListInd(dep.isDealFullListInd());

                        employeeMapper.setRepositoryCreateInd(dep.isRepositoryCreateInd());

                        employeeMapper.setShipperAccessInd(dep.isShipperAccessInd());
                        employeeMapper.setShipperCreateInd(dep.isShipperCreateInd());
                        employeeMapper.setShipperDeleteInd(dep.isShipperDeleteInd());
                        employeeMapper.setShipperUpdateInd(dep.isShipperUpdateInd());
                        employeeMapper.setShipperFullListInd(dep.isShipperFullListInd());

                        employeeMapper.setPlantAccessInd(dep.isPlantAccessInd());
                        employeeMapper.setPlantCreateInd(dep.isPlantCreateInd());
                        employeeMapper.setPlantDeleteInd(dep.isPlantDeleteInd());
                        employeeMapper.setPlantUpdateInd(dep.isPlantUpdateInd());
                        employeeMapper.setPlantFullListInd(dep.isPlantFullListInd());

                        employeeMapper.setTeamsAccessInd(dep.isTeamsAccessInd());
                        employeeMapper.setTeamsCreateInd(dep.isTeamsCreateInd());
                        employeeMapper.setTeamsDeleteInd(dep.isTeamsDeleteInd());
                        employeeMapper.setTeamsUpdateInd(dep.isTeamsUpdateInd());
                        employeeMapper.setTeamsFullListInd(dep.isTeamsFullListInd());

                        employeeMapper.setBasicAccessInd(dep.isBasicAccessInd());

                        employeeMapper.setCatalogAccessInd(dep.isCatalogAccessInd());
                        employeeMapper.setCatalogCreateInd(dep.isCatalogCreateInd());
                        employeeMapper.setCatalogDeleteInd(dep.isCatalogDeleteInd());
                        employeeMapper.setCatalogUpdateInd(dep.isCatalogUpdateInd());

                        employeeMapper.setPaymentAccessInd(dep.isPaymentAccessInd());

                        employeeMapper.setCollectionAccessInd(dep.isCollectionAccessInd());
                        employeeMapper.setCollectionApproveInd(dep.isCollectionApproveInd());

                        employeeMapper.setHolidayAccessInd(dep.isHolidayAccessInd());

                        employeeMapper.setTopicAccessInd(dep.isTopicAccessInd());
                        employeeMapper.setTopicCreateInd(dep.isTopicCreateInd());
                        employeeMapper.setTopicDeleteInd(dep.isTopicDeleteInd());
                        employeeMapper.setTopicFullListInd(dep.isTopicFullListInd());
                        employeeMapper.setTopicUpdateInd(dep.isTopicUpdateInd());

                        employeeMapper.setProcurementAccessInd(dep.isProcurementAccessInd());
                        employeeMapper.setProcurementCreateInd(dep.isProcurementCreateInd());
                        employeeMapper.setProcurementDeleteInd(dep.isProcurementDeleteInd());
                        employeeMapper.setProcurementFullListInd(dep.isProcurementFullListInd());
                        employeeMapper.setProcurementUpdateInd(dep.isProcurementUpdateInd());

                        employeeMapper.setSubscriptionAccessInd(dep.isSubscriptionAccessInd());
                        employeeMapper.setSubscriptionCreateInd(dep.isSubscriptionCreateInd());
                        employeeMapper.setSubscriptionDeleteInd(dep.isSubscriptionDeleteInd());
                        employeeMapper.setSubscriptionUpdateInd(dep.isSubscriptionUpdateInd());

                        employeeMapper.setProductionAccessInd(dep.isProductionAccessInd());
                        employeeMapper.setProductionCreateInd(dep.isProductionCreateInd());
                        employeeMapper.setProductionDeleteInd(dep.isProductionDeleteInd());
                        employeeMapper.setProductionUpdateInd(dep.isProductionUpdateInd());

                        employeeMapper.setReportFullListInd(dep.isReportFullListInd());

                        employeeMapper.setDataRoomAccessInd(dep.isDataRoomAccessInd());
                        employeeMapper.setDataRoomCreateInd(dep.isDataRoomCreateInd());
                        employeeMapper.setDataRoomDeleteInd(dep.isDataRoomDeleteInd());
                        employeeMapper.setDataRoomFullListInd(dep.isDataRoomFullListInd());
                        employeeMapper.setDataRoomUpdateInd(dep.isDataRoomUpdateInd());

                        employeeMapper.setScannerAccessInd(dep.isScannerAccessInd());
                        
                        employeeMapper.setQualityAccessInd(dep.isQualityAccessInd());
                        employeeMapper.setQualityCreateInd(dep.isQualityCreateInd());
                        employeeMapper.setQualityDeleteInd(dep.isQualityDeleteInd());
                        employeeMapper.setQualityFullListInd(dep.isQualityFullListInd());
                        employeeMapper.setQualityUpdateInd(dep.isQualityUpdateInd());
                        
                        employeeMapper.setClubAccessInd(dep.isClubAccessInd());
                        employeeMapper.setClubCreateInd(dep.isClubCreateInd());
                        employeeMapper.setClubDeleteInd(dep.isClubDeleteInd());
                        employeeMapper.setClubFullListInd(dep.isClubFullListInd());
                        employeeMapper.setClubUpdateInd(dep.isClubUpdateInd());
                        
                        employeeMapper.setCalenderManageInd(dep.isCalenderManageInd());
                        employeeMapper.setCalenderViewInd(dep.isCalenderViewInd());
                    }
                }
            }

            Permission permission = permissionRepository.findByOrgId(employeeDetails.getOrgId());
            if (null != permission) {
                employeeMapper.setCandiEmpShareInd(permission.isCandiEmpShareInd());
                employeeMapper.setCandiContShareInd(permission.isCandiContShareInd());
                employeeMapper.setCandiContSrchInd(permission.isCandiContSrchInd());
                employeeMapper.setCandiContSrchInd(permission.isCandiEmpSrchInd());
            }

            Communication pem = communicationRepository.findByOrgId(employeeDetails.getOrgId());
            if (null != pem) {
                employeeMapper.setEmailCustomerInd(pem.isEmailCustomerInd());
                employeeMapper.setEmailJobDesInd(pem.isEmailJobDesInd());
                employeeMapper.setCandidateEventUpdateInd(pem.isCandidateEventUpdateInd());
                employeeMapper.setWhatsappCustomerInd(pem.isWhatsappCustomerInd());
                employeeMapper.setWhatsappJobDesInd(pem.isWhatsappJobDesInd());
                employeeMapper.setCandiWorkflowEnabledInstInd(pem.isCandiWorkflowEnabledInstInd());
                employeeMapper.setCandiPipelineEmailInd(pem.isCandiPipelineEmailInd());
            }

            RecruitmentCloseRule recruitmentCloseRule = recruitmentCloseRuleRepository.findByOrgId(employeeDetails.getOrgId());
            if (null != recruitmentCloseRule) {
                employeeMapper.setInspectionRequiredInd(recruitmentCloseRule.isInspectionRequiredInd());
            }

            List<Sequence> pem2 = sequenceRepository.getSequenceByOrgId(employeeDetails.getOrgId());
            if (null != pem2 && !pem2.isEmpty()) {
                employeeMapper.setSequenceAvailableInd(true);
            }

            ThirdParty pem1 = thirdPartyRepository.findByOrgId(employeeDetails.getOrgId());
            if (null != pem1) {
                employeeMapper.setPartnerContactInd(pem1.isPartnerContactInd());
                employeeMapper.setCustomerContactInd(pem1.isCustomerContactInd());
            }

            List<KeyskillsMapper> skillList = new ArrayList<KeyskillsMapper>();
            List<KeySkillDetails> list = keySkillRepository.getSkillByEmployeeId(employeeDetails.getEmployeeId());
            if (null != list && !list.isEmpty()) {
                for (KeySkillDetails skill : list) {
                    KeySkillDetails list2 = keySkillRepository.getById(skill.getId());

                    KeyskillsMapper mapper = new KeyskillsMapper();
                    if (null != list2) {

                        mapper.setKeySkillsName(list2.getSkillName());
                        mapper.setEmployeeId(list2.getEmployeeId());
                        mapper.setKeySkillsId(list2.getId());
                        skillList.add(mapper);
                    }
                }
                employeeMapper.setSkill(skillList);
            }

            employeeMapper.setRole(employeeDetails.getRole());

            if (!StringUtils.isEmpty(employeeDetails.getCountry())) {
                employeeMapper.setCountry(employeeDetails.getCountry());
                Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(employeeDetails.getCountry(), employeeDetails.getOrgId());
                if (null != country) {
                    employeeMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
                    employeeMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
                }
            } else {
                List<EmployeeAddressLink> employeeAddressList = employeeAddressLinkRepository
                        .getAddressListByEmpId(employeeDetails.getEmployeeId());
                if (null != employeeAddressList && !employeeAddressList.isEmpty()) {
                    for (EmployeeAddressLink employeeAddressLink : employeeAddressList) {
                        AddressDetails addressDetails = addressRepository
                                .getAddressDetailsByAddressId(employeeAddressLink.getAddressId());
                        if (null != addressDetails) {
                            if (!StringUtils.isEmpty(addressDetails.getCountry())) {
                                employeeMapper.setCountry(addressDetails.getCountry());
                                Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(addressDetails.getCountry(), employeeDetails.getOrgId());
                                if (null != country) {
                                    employeeMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
                                    employeeMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
                                }
                            }
                        }
                    }
                }
            }

            employeeMapper.setTwitter(employeeDetails.getTwitter());
            employeeMapper.setFacebook(employeeDetails.getFacebook());
            // employeeMapper.setCurrency(employeeDetails.getCurrency());
            employeeMapper.setCountryDialCode(employeeDetails.getCountryDialCode());
            employeeMapper.setCountryDialCode1(employeeDetails.getCountryDialCode1());

            employeeMapper.setOrganizationId(employeeDetails.getOrgId());
            if(!StringUtils.isEmpty(employeeDetails.getOrgId())){
                OrganizationDetails organizationDetails=organizationRepository.findByOrgId(employeeDetails.getOrgId());
                System.out.println("org "+organizationDetails.getName()+"  "+organizationDetails.getType());
                if(null!=organizationDetails){
                    employeeMapper.setPrimaryOrgType(organizationDetails.getType());
                }
            }

            employeeMapper.setUserId(employeeDetails.getUserId());
            employeeMapper.setBloodGroup(employeeDetails.getBloodGroup());
            Currency currency = currencyRepository.getByCurrencyId(employeeDetails.getCurrency());
            if (null != currency) {
                employeeMapper.setCurrency(currency.getCurrencyName());
            }

            LocationDetails location = locationDetailsRepository.findByLocationDetailsIdAndActiveInd(employeeDetails.getLocation(), true);
            if (null != location) {
                employeeMapper.setLocation(location.getLocationName());
                employeeMapper.setLocationId(location.getLocationDetailsId());
            }

//            List<EmployeeAddressLink> employeeAddressList = employeeAddressLinkRepository
//                    .getAddressListByEmpId(employeeDetails.getEmployeeId());
//            List<AddressMapper> addressList = new ArrayList<AddressMapper>();
//            if (null != employeeAddressList && !employeeAddressList.isEmpty()) {
//
//                for (EmployeeAddressLink employeeAddressLink : employeeAddressList) {
//                    AddressDetails addressDetails = addressRepository
//                            .getAddressDetailsByAddressId(employeeAddressLink.getAddressId());
//
//                    AddressMapper addressMapper = new AddressMapper();
//                    if (null != addressDetails) {
//                        addressMapper.setAddress1(addressDetails.getAddressLine1());
//                        addressMapper.setAddress2(addressDetails.getAddressLine2());
//                        addressMapper.setAddressType(addressDetails.getAddressType());
//                        addressMapper.setPostalCode(addressDetails.getPostalCode());
//                        addressMapper.setStreet(addressDetails.getStreet());
//                        addressMapper.setCity(addressDetails.getCity());
//                        addressMapper.setTown(addressDetails.getTown());
//                        addressMapper.setCountry(addressDetails.getCountry());
//                        addressMapper.setLatitude(addressDetails.getLatitude());
//                        addressMapper.setLongitude(addressDetails.getLongitude());
//                        addressMapper.setState(addressDetails.getState());
//                        addressMapper.setAddressId(addressDetails.getAddressId());
//                        addressMapper.setHouseNo(addressDetails.getHouseNo());
//                        addressList.add(addressMapper);
//
//                    }
//
//                }
//            }
//            employeeMapper.setAddress(addressList);

            List<DocumentType> documentType = documentTypeRepository.getDocumentTypeListByOrgIdAndUserTypeWithLiveIndAndMandatoryInd(employeeDetails.getOrgId(), "User");
            List<String> documentList = new ArrayList<>();
            if (null != documentType && !documentType.isEmpty()) {
                for (DocumentType documentType2 : documentType) {
                    documentList.add(documentType2.getDocumentTypeName());
                }
                for (DocumentType documentType1 : documentType) {
                    System.out.println("User Id =====-=----inner3-----=--///" + employeeDetails.getEmployeeId());
                    if (null != documentType1.getDocument_type_id()) {
                        List<EmployeeDocumentLink> employeeDocumentLink = employeeDocumentLinkRepository.
                                getDocumentByEmployeeId(employeeDetails.getEmployeeId());
                        if (null != employeeDocumentLink && !employeeDocumentLink.isEmpty()) {
                            for (EmployeeDocumentLink employeeDocumentLink1 : employeeDocumentLink) {
                                DocumentDetails documentDetails = documentDetailsRepository.getDocumentDetailsById(employeeDocumentLink1.getDocumentId());
                                if (null != documentDetails) {
                                    if (null != documentDetails.getDocument_type()) {
                                        if (documentDetails.getDocument_type().equalsIgnoreCase(documentType1.getDocument_type_id())) {
                                            documentList.remove(documentType1.getDocumentTypeName());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            employeeMapper.setListOfDocPending(documentList);
            employeeMapper.setNoOfDocPending(documentList.size());
        }

        System.out.println("empId$$$$$$=" + employeeDetails.getEmployeeId());
        System.out.println("orgid=" + employeeDetails.getOrgId());
        Compliance compliance = complianceRepository.findByOrgId(employeeDetails.getOrgId());
        if (null != compliance) {
            employeeMapper.setGdprApplicableInd(compliance.isGdprApplicableInd());
        }
        System.out.println("emailId=" + employeeDetails.getEmailId());
        UserSettings userSettings = userSettingsRepository.getUserSettingsByEmail(employeeDetails.getEmailId(), true);
        if (null != userSettings) {
            employeeMapper.setEmailValidationInd(userSettings.isEmailValInd());
        }
        Map map = new HashMap();
        if (null != employeeDetails.getReportingManager()) {
            EmployeeDetails reportingManagerDetails = employeeRepository
                    .getEmployeeDetailsByEmployeeId(employeeDetails.getReportingManager(), true);
            if (null != reportingManagerDetails) {
                if (!StringUtils.isEmpty(reportingManagerDetails.getFirstName()))
                    map.put("firstName", reportingManagerDetails.getFirstName());
                if (!StringUtils.isEmpty(reportingManagerDetails.getMiddleName()))
                    map.put("middleName", reportingManagerDetails.getMiddleName());
                if (!StringUtils.isEmpty(reportingManagerDetails.getLastName()))
                    map.put("lastName", reportingManagerDetails.getLastName());
                if (!StringUtils.isEmpty(reportingManagerDetails.getEmailId()))
                    map.put("email", reportingManagerDetails.getEmailId());

                OrganizationDetails organizationDetails = organizationRepository
                        .getOrganizationDetailsById(employeeDetails.getOrgId());
                map.put("orgImageId", organizationDetails.getImage_id());
//				Communication communication = communicationRepository.findByOrgId(employeeDetails.getOrgId());
//				if (null != communication) {
//					map.put("emailCustomerInd", communication.isEmailCustomerInd());
//					map.put("emailJobDesInd", communication.isEmailJobDesInd());
//					map.put("whatsappCustomerInd", communication.isWhatsappCustomerInd());
//					map.put("whatsappJobDesInd", communication.isWhatsappJobDesInd());
//				}

            }
        }
        OrganizationSubsriptionDetails organizationSubsriptionDetails = organizationSubsriptionDetailsRepository
                .findByOrganizationId(employeeDetails.getOrgId());
        if (null != organizationSubsriptionDetails) {
            map.put("subscriptionType", organizationSubsriptionDetails.getSubscriptionType());
            map.put("subscriptionEndDate", organizationSubsriptionDetails.getSubscription_end_date());

            map.put("crmInd", organizationSubsriptionDetails.isCrmInd());
            map.put("atsInd", organizationSubsriptionDetails.isAtsInd());
            map.put("billingInd", organizationSubsriptionDetails.isBillingInd());
            map.put("lmsInd", organizationSubsriptionDetails.isLmsInd());
            map.put("vmsInd", organizationSubsriptionDetails.isVmsInd());

        }
        employeeMapper.setMetaData(map);
        FiscalMapper fiscalMapper = organizationService.getFiscalMapperByOrgId(employeeDetails.getOrgId());
        employeeMapper.setFiscalMapper(fiscalMapper);

        OrgIndustry dbOrgIndustry = orgIndustryRepository.findByOrgIdAndLiveInd(employeeDetails.getOrgId(), true);
        if (null != dbOrgIndustry) {
            employeeMapper.setOrgType(dbOrgIndustry.getType());
        }
        return employeeMapper;

    }

    @Override
    public List<EmployeeTableMapper> getEmployeesByOrgId(String organizationId) {
        List<EmployeeDetails> empList = employeeRepository.getEmployeesByOrgIdAndLiveInd(organizationId);
        List<EmployeeTableMapper> resultList = new ArrayList<>();
        System.out.println("@@@@@@@@" + empList);
        if (null != empList && !empList.isEmpty()) {
            return empList.stream().map(employeeDetails -> {
//            	System.out.println("EMPLOYEE ID=="+employeeDetails.getEmployeeId());
//            	System.out.println("CREATION DATE ID=="+employeeDetails.getEmployeeId());
//            	EmployeeTableMapper employeeMapper = getEmployeeDetailByEmployeeId(employeeDetails.getEmployeeId());
                EmployeeTableMapper employeeMapper = getEmployeeDetail(employeeDetails);
                employeeMapper.setSuspendInd(employeeDetails.isSuspendInd());
                return employeeMapper;
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public List<EmployeeTableMapper> getAllActiveEmployees(String organizationId) {
        List<EmployeeDetails> empList = employeeRepository.getEmployeesByOrgId(organizationId);
        List<EmployeeTableMapper> resultList = new ArrayList<>();
        System.out.println("@@@@@@@@" + empList);
        if (null != empList && !empList.isEmpty()) {
            return empList.stream().map(employeeDetails -> {
//            	System.out.println("EMPLOYEE ID=="+employeeDetails.getEmployeeId());
//            	System.out.println("CREATION DATE ID=="+employeeDetails.getEmployeeId());
//            	EmployeeTableMapper employeeMapper = getEmployeeDetailByEmployeeId(employeeDetails.getEmployeeId());
                EmployeeTableMapper employeeMapper = getEmployeeDetail(employeeDetails);
                employeeMapper.setSuspendInd(employeeDetails.isSuspendInd());
                return employeeMapper;
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public PersonalDetailsMapper getPersonalDetails(String id) {

        PersonalDetails personalDetails = personalRepository.getById(id);
        PersonalDetailsMapper personalDetailsMapper = new PersonalDetailsMapper();
        List<AddressMapper> addressList = new ArrayList<AddressMapper>();
        if (null != personalDetails) {

            personalDetailsMapper.setId(personalDetails.getId());
            personalDetailsMapper.setEmployeeId(personalDetails.getEmployeeId());
            personalDetailsMapper.setBloodGroup(personalDetails.getBloodGroup());
            personalDetailsMapper.setContactFirstName(personalDetails.getContactFirstName());
            personalDetailsMapper.setContactMiddleName(personalDetails.getContactMiddleName());
            personalDetailsMapper.setContactLastName(personalDetails.getContactLastName());
            personalDetailsMapper.setContactSalutation(personalDetails.getSalutation());
            personalDetailsMapper.setCountryDialCode(personalDetails.getCountryDialCode());
            personalDetailsMapper.setCountryDialCode1(personalDetails.getCountryDialCode1());
            personalDetailsMapper.setCreationDate(Utility.getISOFromDate(personalDetails.getCreationDate()));
            if (null != personalDetails.getDateOfBirth()) {
                personalDetailsMapper.setDob(Utility.getISOFromDate(personalDetails.getDateOfBirth()));

            } else {
                personalDetailsMapper.setDob("");
            }
            personalDetailsMapper.setPhoneNo(personalDetails.getPhone());
            personalDetailsMapper.setMobileNo(personalDetails.getMobile());

            List<EmployeeContactAddressLink> list = employeeContactAddressRepository
                    .getEmployeeContactAddressById(personalDetails.getEmployeeId(), personalDetails.getId());

            if (null != list && !list.isEmpty()) {
                list.stream().map(employeeContactAddressLink -> {
                    AddressMapper addressMapper = addressService
                            .getAddressDetails(employeeContactAddressLink.getContactAddressId());
                    addressList.add(addressMapper);
                    return addressList;

                }).flatMap(l -> l.stream()).collect(Collectors.toList());

                personalDetailsMapper.setAddress(addressList);

            }
        }

        return personalDetailsMapper;

    }

    @Override
    public BankDetailsMapper getBankDetails(String id) {

        BankDetails bankDetails = bankRepository.getById(id);
        BankDetailsMapper bankDetailsMapper = new BankDetailsMapper();

        if (null != bankDetails) {
            bankDetailsMapper.setId(bankDetails.getId());
            bankDetailsMapper.setBankName(bankDetails.getBankName());
            bankDetailsMapper.setAccountHolderName(bankDetails.getAccountHolderName());
            bankDetailsMapper.setBranchName(bankDetails.getBranchName());
            bankDetailsMapper.setEmployeeId(bankDetails.getEmployeeId());
            bankDetailsMapper.setIfscCode(bankDetails.getIfscCode());
            bankDetailsMapper.setAccountNo(bankDetails.getAccountNo());
            bankDetailsMapper.setDefaultInd(bankDetails.isDefaultInd());
            bankDetailsMapper.setCreationDate(Utility.getISOFromDate(bankDetails.getCreationDate()));

        }

        return bankDetailsMapper;
    }

    @Override
    public KeyskillsMapper getKeyskillsDetails(String id) {

        KeySkillDetails keySkillDetails = keySkillRepository.getById(id);
        KeyskillsMapper keyskillsMapper = new KeyskillsMapper();

        if (null != keySkillDetails) {
            keyskillsMapper.setEmployeeId(keySkillDetails.getEmployeeId());
            keyskillsMapper.setKeySkillsId(keySkillDetails.getId());
            if (null != keySkillDetails.getSkillName() && !keySkillDetails.getSkillName().isEmpty()) {
                DefinationDetails definationDetails = definationRepository
                        .findByDefinationId(keySkillDetails.getSkillName());
                if (null != definationDetails) {
                    keyskillsMapper.setKeySkillsName(definationDetails.getName());
                }
            }

            if (keySkillDetails.isPauseInd() == true) {
                keyskillsMapper.setExperience(keySkillDetails.getPauseExperience());
            } else {
                if (null != keySkillDetails.getUnpauseDate()) {
                    keyskillsMapper.setExperience(
                            getExprience(keySkillDetails.getUnpauseDate()) + keySkillDetails.getPauseExperience());
                } else {
                    keyskillsMapper.setExperience(
                            getExprience(keySkillDetails.getCreationDate()) + keySkillDetails.getExperience());
                }
            }
            keyskillsMapper.setSkillRole(keySkillDetails.getSkillRole());
            keyskillsMapper.setPauseInd(keySkillDetails.isPauseInd());
            keyskillsMapper.setCreationDate(Utility.getISOFromDate(keySkillDetails.getCreationDate()));
        }
        return keyskillsMapper;
    }

    private float getExprience(Date endDate) {
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
    public EducationalDetailsMapper getEducationDetails(String id) {

        EducationalDetails educationalDetails = educationalRepository.getById(id);
        EducationalDetailsMapper educationalDetailsMapper = new EducationalDetailsMapper();
        if (null != educationalDetails) {
            educationalDetailsMapper.setId(educationalDetails.getId());
            educationalDetailsMapper.setEmployeeId(educationalDetails.getEmployeeId());
            educationalDetailsMapper.setCourseName(educationalDetails.getCourseName());
            educationalDetailsMapper.setCourseType(educationalDetails.getCourseType());
            // educationalDetailsMapper.setEducationType(educationalDetails.getEducationType());
            if (!StringUtils.isEmpty(educationalDetails.getEducationType())) {
                EducationType educationType = educationTypeRepository
                        .findByEducationTypeId(educationalDetails.getEducationType());
                if (null != educationType) {
                    educationalDetailsMapper.setEducationTypeId(educationType.getEducationTypeId());
                    educationalDetailsMapper.setEducationType(educationType.getEducationType());
                }
            }
            if (!StringUtils.isEmpty(educationalDetails.getDocumentType())) {
                DocumentType documentType = documentTypeRepository.getTypeDetails(educationalDetails.getDocumentType());
                if (null != documentType) {
                    educationalDetailsMapper.setDocumentType(documentType.getDocumentTypeName());
                }
            }
            educationalDetailsMapper.setMarksSecured(educationalDetails.getMarksSecured());
            educationalDetailsMapper.setSpecialization(educationalDetails.getSpecialization());
            educationalDetailsMapper.setUniversity(educationalDetails.getUniversity());
            educationalDetailsMapper.setYearOfPassing(educationalDetails.getYearOfPassing());
            educationalDetailsMapper.setMarksType(educationalDetails.getMarksType());
            educationalDetailsMapper.setCreationDate(Utility.getISOFromDate(educationalDetails.getCreationDate()));
            educationalDetailsMapper.setDocumentId(educationalDetails.getDocumentId());
        }

        return educationalDetailsMapper;
    }

    @Override
    public TrainingDetailsMapper getTrainingDetails(String id) {
        TrainingDetails trainingDetails = trainingRepository.getById(id);
        TrainingDetailsMapper trainingDetailsMapper = new TrainingDetailsMapper();
        if (null != trainingDetails) {

            if (trainingDetails.getDocument_type_name() != null
                    && trainingDetails.getDocument_type_name().trim().length() > 0) {
                // SectorDetails sector=
                // sectorDetailsRepository.getSectorDetailsBySectorId(customer.getSector());
                DocumentType document = documentTypeRepository.getTypeDetails(trainingDetails.getDocument_type_name());
                // System.out.println("get sectordetails by id
                // returns........."+customer.getSector());
                // System.out.println("sector object is......."+sector);
                if (null != document) {
                    trainingDetailsMapper.setDocumentTypeName(document.getDocumentTypeName());
                    trainingDetailsMapper.setDocumentTypeId(document.getDocument_type_id());
                }
            } else {
                trainingDetailsMapper.setDocumentTypeName("");
                trainingDetailsMapper.setDocumentTypeId("");
            }

            trainingDetailsMapper.setId(trainingDetails.getId());
            trainingDetailsMapper.setEmployeeId(trainingDetails.getEmployeeId());
            trainingDetailsMapper.setCourseName(trainingDetails.getCourseName());
            trainingDetailsMapper.setGrade(trainingDetails.getGrade());
            trainingDetailsMapper.setOrganization(trainingDetails.getOrganization());
            trainingDetailsMapper.setCreationDate(Utility.getISOFromDate(trainingDetails.getCreationDate()));
            trainingDetailsMapper.setDocumentId(trainingDetails.getDocumentId());

            if (null != trainingDetails.getStartDate()) {
                trainingDetailsMapper.setStartDate(Utility.getISOFromDate(trainingDetails.getStartDate()));

            } else {
                trainingDetailsMapper.setStartDate("");
            }

            if (null != trainingDetails.getEndDate()) {
                trainingDetailsMapper.setEndDate(Utility.getISOFromDate(trainingDetails.getEndDate()));

            } else {
                trainingDetailsMapper.setEndDate("");
            }
        }

        return trainingDetailsMapper;
    }

    @Override
    public EmploymentHistoryMapper getHistoryDetails(String id) {
        EmployementHistory employementHistory = employementHistoryRepository.getById(id);
        EmploymentHistoryMapper employmentHistoryMapper = new EmploymentHistoryMapper();
        if (null != employementHistory) {
            employmentHistoryMapper.setId(employementHistory.getId());
            employmentHistoryMapper.setEmployeeId(employementHistory.getEmployeeId());
            employmentHistoryMapper.setCompanyName(employementHistory.getCompanyName());
            employmentHistoryMapper.setDescription(employementHistory.getDescription());
            employmentHistoryMapper.setCreationDate(Utility.getISOFromDate(employementHistory.getCreationDate()));
            employmentHistoryMapper.setDocumentId(employementHistory.getDocumentId());

            if (null != employementHistory.getEndDate()) {
                employmentHistoryMapper.setStartDate(Utility.getISOFromDate(employementHistory.getEndDate()));

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
            }
        }
        employmentHistoryMapper.setCurrency(employementHistory.getCurrency());
        if (!StringUtils.isEmpty(employementHistory.getDocumentType())) {
            DocumentType documentType = documentTypeRepository.getTypeDetails(employementHistory.getDocumentType());
            if (null != documentType) {
                employmentHistoryMapper.setDocumentType(documentType.getDocumentTypeName());
            }
        }
        return employmentHistoryMapper;
    }

    @Override
    public List<PersonalDetailsMapper> getEmployeePersonalDetails(String employeeId) {
        List<PersonalDetails> personalDetailsList = personalRepository.getPersonalDetailsById(employeeId);
        if (null != personalDetailsList && !personalDetailsList.isEmpty()) {
            return personalDetailsList.stream().map(personalDetails -> {
                PersonalDetailsMapper personalDetailsMapper = getPersonalDetails(personalDetails.getId());
                if (null != personalDetailsMapper) {
                    return personalDetailsMapper;
                }
                return null;
            }).collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public List<BankDetailsMapper> getEmployeeBankDetails(String employeeId) {
        List<BankDetails> bankDetailsList = bankRepository.getBankDetailsById(employeeId);
        if (null != bankDetailsList && !bankDetailsList.isEmpty()) {
            return bankDetailsList.stream().map(bankDetails -> {
                BankDetailsMapper bankDetailsMapper = getBankDetails(bankDetails.getId());
                if (null != bankDetailsMapper) {
                    return bankDetailsMapper;
                }
                return null;
            }).collect(Collectors.toList());

        }
        return null;
    }

    @Override
    public List<KeyskillsMapper> getEmployeeKeyskillsDetails(String employeeId) {
        List<KeyskillsMapper> keyskillsMappers = new ArrayList<>();
        List<KeySkillDetails> skillsList = keySkillRepository.getKeyskillsByEmployeeId(employeeId);
        if (null != skillsList && !skillsList.isEmpty()) {
            return skillsList.stream().map(keySkillDetails -> {
                KeyskillsMapper keyskillsMapper = getKeyskillsDetails(keySkillDetails.getId());
                if (null != keyskillsMapper) {
                    return keyskillsMapper;
                }
                return null;
            }).collect(Collectors.toList());
        }
        return keyskillsMappers;
    }

    @Override
    public List<EducationalDetailsMapper> getEmployeeEducationDetails(String employeeId) {
        List<EducationalDetails> educationalList = educationalRepository.getEducationDetailsById(employeeId);
        if (null != educationalList && !educationalList.isEmpty()) {
            return educationalList.stream().map(educationalDetails -> {
                EducationalDetailsMapper educationalDetailsMapper = getEducationDetails(educationalDetails.getId());
                if (null != educationalDetailsMapper) {
                    return educationalDetailsMapper;
                }
                return null;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<TrainingDetailsMapper> getEmployeeTrainingDetails(String employeeId) {
        List<TrainingDetails> trainingList = trainingRepository.getEmployeeTrainingDetailsByEmployeeId(employeeId);
        if (null != trainingList && !trainingList.isEmpty()) {
            return trainingList.stream().map(trainingDetails -> {
                TrainingDetailsMapper trainingDetailsMapper = getTrainingDetails(trainingDetails.getId());
                if (null != trainingDetailsMapper) {
                    return trainingDetailsMapper;
                }
                return null;
            }).collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public List<EmploymentHistoryMapper> getEmploymentHistoryDetails(String employeeId) {
        List<EmployementHistory> employementHistoryList = employementHistoryRepository
                .getEmploymentHistoryByEmployeeId(employeeId);
        List<EmploymentHistoryMapper> resultList = new ArrayList<EmploymentHistoryMapper>();

        if (null != employementHistoryList && !employementHistoryList.isEmpty()) {
            employementHistoryList.stream().map(employementHistory -> {
                EmploymentHistoryMapper employmentHistoryMapper = getHistoryDetails(employementHistory.getId());
                if (null != employmentHistoryMapper)
                    resultList.add(employmentHistoryMapper);
                return resultList;
            }).collect(Collectors.toList());

        }

        return resultList;
    }

    @Override
    public String saveEmploymentHistory(EmploymentHistoryMapper employmentHistoryMapper) {

        String id = null;
        if (null != employmentHistoryMapper.getEmployeeId()) {
            EmployementHistory employementHistory = new EmployementHistory();
            employementHistory.setEmployeeId(employmentHistoryMapper.getEmployeeId());
            employementHistory.setCompanyName(employmentHistoryMapper.getCompanyName());
            employementHistory.setCreationDate(new Date());
            employementHistory.setDescription(employmentHistoryMapper.getDescription());
            employementHistory.setDesignation(employmentHistoryMapper.getDesignationTypeId());
            // employementHistory.setTenure(employmentHistoryMapper.getTenure());
            try {
                if (null != employmentHistoryMapper.getStartDate()) {
                    employementHistory
                            .setStartDate(Utility.getDateFromISOString(employmentHistoryMapper.getStartDate()));

                }
                if (null != employmentHistoryMapper.getEndDate()) {
                    employementHistory.setEndDate(Utility.getDateFromISOString(employmentHistoryMapper.getEndDate()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            employementHistory.setCurrency(employmentHistoryMapper.getCurrency());
            employementHistory.setSalary(employmentHistoryMapper.getSalary());
            employementHistory.setSalaryType(employmentHistoryMapper.getSalaryType());
            employementHistory.setLiveInd(true);
            employementHistory.setDocumentType(employmentHistoryMapper.getDocumentType());
            if (!StringUtils.isEmpty(employmentHistoryMapper.getDocumentId())) {

                String documentId = employmentHistoryMapper.getDocumentId();

                DocumentDetails documentDetails = documentDetailsRepository.getDocumentDetailsById(documentId);

                //documentDetails.setDocument_title(employmentHistoryMapper.getDocumentName());
                documentDetails.setDocument_type(employmentHistoryMapper.getDocumentType());
                documentDetails.setDoc_description(employmentHistoryMapper.getDescription());
                documentDetails.setDocument_id(documentDetails.getDocument_id());
                documentDetails.setCreation_date(new Date());
                // documentDetails.setDocumentType(documentMapper.getDocumentTypeId());
                documentDetailsRepository.save(documentDetails);

                employementHistory.setDocumentId(employmentHistoryMapper.getDocumentId());

                EmployeeDocumentLink employeeDocumentLink = new EmployeeDocumentLink();
                employeeDocumentLink.setDocumentId(employmentHistoryMapper.getDocumentId());
                employeeDocumentLink.setEmployeeId(employmentHistoryMapper.getEmployeeId());
                employeeDocumentLink.setCreationDate(new Date());
                employeeDocumentLinkRepository.save(employeeDocumentLink);

            }
            EmployementHistory history = employementHistoryRepository.save(employementHistory);

            id = history.getId();

            System.out.println("id........ " + id);

        }
        return id;
    }

    @Override
    public String saveTrainingDetails(TrainingDetailsMapper trainingDetailsMapper) {
        String id = null;
        if (null != trainingDetailsMapper.getEmployeeId()) {
            TrainingDetails trainingDetails = new TrainingDetails();
            trainingDetails.setEmployeeId(trainingDetailsMapper.getEmployeeId());
            trainingDetails.setCourseName(trainingDetailsMapper.getCourseName());
            trainingDetails.setGrade(trainingDetailsMapper.getGrade());
            trainingDetails.setOrganization(trainingDetailsMapper.getOrganization());
            trainingDetails.setDocument_type_name(trainingDetailsMapper.getDocumentTypeName());
            trainingDetails.setCreationDate(new Date());
            trainingDetails.setLiveInd(true);
            if (!StringUtils.isEmpty(trainingDetailsMapper.getDocumentId())) {

                String documentId = trainingDetailsMapper.getDocumentId();

                DocumentDetails documentDetails = documentDetailsRepository.getDocumentDetailsById(documentId);

                //documentDetails.setDocument_title(employmentHistoryMapper.getDocumentName());
                documentDetails.setDocument_type(trainingDetailsMapper.getDocumentTypeName());
                //documentDetails.setDoc_description(trainingDetailsMapper.getDescription());
                documentDetails.setDocument_id(documentDetails.getDocument_id());
                documentDetails.setCreation_date(new Date());
                // documentDetails.setDocumentType(documentMapper.getDocumentTypeId());
                documentDetailsRepository.save(documentDetails);

                trainingDetails.setDocumentId(trainingDetailsMapper.getDocumentId());

                EmployeeDocumentLink employeeDocumentLink = new EmployeeDocumentLink();
                employeeDocumentLink.setDocumentId(trainingDetailsMapper.getDocumentId());
                employeeDocumentLink.setEmployeeId(trainingDetailsMapper.getEmployeeId());
                employeeDocumentLink.setCreationDate(new Date());
                employeeDocumentLinkRepository.save(employeeDocumentLink);

            }
            try {
                if (null != trainingDetailsMapper.getStartDate()) {
                    trainingDetails.setStartDate(Utility.getDateFromISOString(trainingDetailsMapper.getStartDate()));
                }

                if (null != trainingDetailsMapper.getEndDate()) {

                    trainingDetails.setEndDate(Utility.getDateFromISOString(trainingDetailsMapper.getEndDate()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            TrainingDetails training = trainingRepository.save(trainingDetails);
            id = training.getId();
        }
        return id;
    }

    @Override
    public String saveEducationDetails(EducationalDetailsMapper educationalDetailsMapper) {
        String id = null;
        if (null != educationalDetailsMapper.getEmployeeId()) {

            EducationalDetails educationDetails = new EducationalDetails();
            educationDetails.setCourseName(educationalDetailsMapper.getCourseName());
            educationDetails.setEmployeeId(educationalDetailsMapper.getEmployeeId());
            educationDetails.setCourseType(educationalDetailsMapper.getCourseType());
            educationDetails.setCreationDate(new Date());
            educationDetails.setEducationType(educationalDetailsMapper.getEducationTypeId());
            educationDetails.setMarksSecured(educationalDetailsMapper.getMarksSecured());
            educationDetails.setMarksType(educationalDetailsMapper.getMarksType());
            educationDetails.setSpecialization(educationalDetailsMapper.getSpecialization());
            educationDetails.setUniversity(educationalDetailsMapper.getUniversity());
            educationDetails.setYearOfPassing(educationalDetailsMapper.getYearOfPassing());
            educationDetails.setDocumentType(educationalDetailsMapper.getDocumentType());
            educationDetails.setLiveInd(true);
            if (!StringUtils.isEmpty(educationalDetailsMapper.getDocumentId())) {

                String documentId = educationalDetailsMapper.getDocumentId();

                DocumentDetails documentDetails = documentDetailsRepository.getDocumentDetailsById(documentId);

                //documentDetails.setDocument_title(employmentHistoryMapper.getDocumentName());
                documentDetails.setDocument_type(educationalDetailsMapper.getDocumentType());
                //documentDetails.setDoc_description(trainingDetailsMapper.getDescription());
                documentDetails.setDocument_id(documentDetails.getDocument_id());
                documentDetails.setCreation_date(new Date());
                // documentDetails.setDocumentType(documentMapper.getDocumentTypeId());
                documentDetailsRepository.save(documentDetails);

                educationDetails.setDocumentId(educationalDetailsMapper.getDocumentId());

                EmployeeDocumentLink employeeDocumentLink = new EmployeeDocumentLink();
                employeeDocumentLink.setDocumentId(educationalDetailsMapper.getDocumentId());
                employeeDocumentLink.setEmployeeId(educationalDetailsMapper.getEmployeeId());
                employeeDocumentLink.setCreationDate(new Date());
                employeeDocumentLinkRepository.save(employeeDocumentLink);

            }
            EducationalDetails education = educationalRepository.save(educationDetails);
            id = education.getId();

        }
        return id;
    }

    @Override
    public String saveKeyskills(KeyskillsMapper keyskillsMapper) {
        DefinationDetails definationDetails = definationRepository
                .getBySkillNameAndLiveInd(keyskillsMapper.getKeySkillsName());
        if (null != definationDetails) {
            KeySkillDetails keySkillDetails = new KeySkillDetails();
            keySkillDetails.setEmployeeId(keyskillsMapper.getEmployeeId());
            keySkillDetails.setSkillName(definationDetails.getDefinationId());
            keySkillDetails.setExperience(keyskillsMapper.getExperience());
            keySkillDetails.setCreationDate(new Date());
            keySkillDetails.setLiveInd(true);
            keySkillRepository.save(keySkillDetails);
        } else {
            DefinationInfo definationInfo = new DefinationInfo();
            definationInfo.setCreation_date(new Date());
            String id = definationInfoRepository.save(definationInfo).getDefination_info_id();

            DefinationDetails newDefinationDetails = new DefinationDetails();
            newDefinationDetails.setDefinationId(id);
            newDefinationDetails.setName(keyskillsMapper.getKeySkillsName());
            newDefinationDetails.setOrg_id(keyskillsMapper.getOrganizationId());
            newDefinationDetails.setUser_id(keyskillsMapper.getUserId());
            newDefinationDetails.setCreation_date(new Date());
            newDefinationDetails.setLiveInd(true);
            newDefinationDetails.setEditInd(true);
            DefinationDetails definationDetails1 = definationRepository.save(newDefinationDetails);

            KeySkillDetails keySkillDetails = new KeySkillDetails();
            keySkillDetails.setEmployeeId(keyskillsMapper.getEmployeeId());
            keySkillDetails.setSkillName(id);
            keySkillDetails.setExperience(keyskillsMapper.getExperience());
            keySkillDetails.setCreationDate(new Date());
            keySkillDetails.setLiveInd(true);
            KeySkillDetails keySkillDetails1 = keySkillRepository.save(keySkillDetails);

            DefinationDetailsDelete definationDetailsDelete = new DefinationDetailsDelete();
            definationDetailsDelete.setOrgId(keyskillsMapper.getOrganizationId());
            definationDetailsDelete.setUserId(keyskillsMapper.getUserId());
            definationDetailsDelete.setUpdationDate(new Date());
            definationDeleteRepository.save(definationDetailsDelete);
        }
        return keyskillsMapper.getKeySkillsName();
    }

    @Override
    public String saveBankDetails(BankDetailsMapper bankDetailsMapper) {
        String id = null;
        if (null != bankDetailsMapper.getEmployeeId()) {

            BankDetails bankDetails1 = bankRepository.getByCandidateIdAndDefaultInd(bankDetailsMapper.getEmployeeId());
            if (null != bankDetails1) {
                bankDetails1.setDefaultInd(false);
                bankRepository.save(bankDetails1);
            }

            BankDetails bankDetails = new BankDetails();
            bankDetails.setEmployeeId(bankDetailsMapper.getEmployeeId());
            bankDetails.setBankName(bankDetailsMapper.getBankName());
            bankDetails.setBranchName(bankDetailsMapper.getBranchName());
            bankDetails.setAccountNo(bankDetailsMapper.getAccountNo());
            bankDetails.setIfscCode(bankDetailsMapper.getIfscCode());
            bankDetails.setCreationDate(new Date());
            bankDetails.setLiveInd(true);
            bankDetails.setDefaultInd(true);
            bankDetails.setAccountHolderName(bankDetailsMapper.getAccountHolderName());
            BankDetails bank = bankRepository.save(bankDetails);
            id = bank.getId();

        }
        return id;

    }

    @Override
    public String savePersonalDetails(PersonalDetailsMapper personalDetailsMapper) {

        String id = null;
        if (null != personalDetailsMapper.getEmployeeId()) {

            PersonalDetails personalDetails = new PersonalDetails();
            personalDetails.setEmployeeId(personalDetailsMapper.getEmployeeId());
            personalDetails.setBloodGroup(personalDetailsMapper.getBloodGroup());
            personalDetails.setContactFirstName(personalDetailsMapper.getContactFirstName());
            personalDetails.setContactMiddleName(personalDetailsMapper.getContactMiddleName());
            personalDetails.setContactLastName(personalDetailsMapper.getContactLastName());
            personalDetails.setCountryDialCode(personalDetailsMapper.getCountryDialCode());
            personalDetails.setCountryDialCode1(personalDetailsMapper.getCountryDialCode1());
            personalDetails.setSalutation(personalDetailsMapper.getContactSalutation());
            try {
                if (null != personalDetailsMapper.getDob()) {
                    personalDetails.setDateOfBirth(Utility.getDateFromISOString(personalDetailsMapper.getDob()));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            personalDetails.setMobile(personalDetailsMapper.getMobileNo());
            personalDetails.setPhone(personalDetailsMapper.getPhoneNo());
            personalDetails.setCreationDate(new Date());
            personalDetails.setLiveInd(true);

            PersonalDetails person = personalRepository.save(personalDetails);
            id = person.getId();
            if (personalDetailsMapper.getAddress().size() > 0) {
                for (AddressMapper addressMapper : personalDetailsMapper.getAddress()) {
                    AddressInfo addressInfo = new AddressInfo();
                    addressInfo.setCreationDate(new Date());
                    addressInfo.setCreatorId(personalDetailsMapper.getEmployeeId());

                    AddressInfo info = addressInfoRepository.save(addressInfo);

                    String addressId = info.getId();

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

                        EmployeeContactAddressLink employeeAddressLink = new EmployeeContactAddressLink();
                        employeeAddressLink.setEmployeeId(personalDetailsMapper.getEmployeeId());
                        employeeAddressLink.setContactAddressId(addressId);
                        employeeAddressLink.setCreationDate(new Date());
                        employeeAddressLink.setLiveInd(true);
                        employeeAddressLink.setContactPersonId(id);
                        employeeContactAddressRepository.save(employeeAddressLink);

                    }

                }
            }

        }

        return id;
    }

    @Override
    public KeyskillsMapper updateKeyskills(KeyskillsMapper keyskillsMapper) {
        KeyskillsMapper resultMapper = null;

        if (null != keyskillsMapper.getKeySkillsId()) {

            KeySkillDetails keySkillDetails = keySkillRepository.getById(keyskillsMapper.getKeySkillsId());

            if (null != keyskillsMapper.getKeySkillsName()) {
                keySkillDetails.setSkillName(keyskillsMapper.getKeySkillsName());
                keySkillRepository.save(keySkillDetails);

            }
            resultMapper = getKeyskillsDetails(keyskillsMapper.getKeySkillsId());
        }
        return resultMapper;
    }

    @Override
    public EmployeePreferedLanguageMapper updateEmployeePreferedLanguage(EmployeePreferedLanguageMapper employeeMapper) {
        String id = null;
        EmployeeDetails employeeDetails = employeeRepository
                .getEmployeeDetailsByEmployeeId(employeeMapper.getEmployeeId(), true);
        System.out.println("get employeedetails in employeedetails able" + employeeDetails);

        if (null != employeeDetails) {

            if (null != employeeMapper.getPreferedLanguage()) {

                employeeDetails.setLanguage(employeeMapper.getPreferedLanguage());
            } else {
                employeeDetails.setLanguage(employeeDetails.getLanguage());
            }

            id = employeeRepository.save(employeeDetails).getEmployeeId();
        }

        return getPreferedLanguageByEmployeeId(id);
    }

    private EmployeePreferedLanguageMapper getPreferedLanguageByEmployeeId(String id) {
        EmployeePreferedLanguageMapper resultMapper = new EmployeePreferedLanguageMapper();
        EmployeeDetails employeeDetails = employeeRepository
                .getEmployeeDetailsByEmployeeId(id, true);

        if (null != employeeDetails) {
            resultMapper.setEmployeeId(employeeDetails.getEmployeeId());
            resultMapper.setPreferedLanguage(employeeDetails.getLanguage());
        }
        return resultMapper;
    }


    @Override
    public PersonalDetailsMapper updatePersonalDetails(PersonalDetailsMapper personalDetailsMapper) {
        PersonalDetailsMapper resultMapper = null;
        PersonalDetails personalDetails = personalRepository.getById(personalDetailsMapper.getId());

        if (null != personalDetailsMapper.getBloodGroup())
            personalDetails.setBloodGroup(personalDetailsMapper.getBloodGroup());
        if (null != personalDetailsMapper.getContactFirstName())
            personalDetails.setContactFirstName(personalDetailsMapper.getContactFirstName());
        if (null != personalDetailsMapper.getContactMiddleName())
            personalDetails.setContactMiddleName(personalDetailsMapper.getContactMiddleName());
        if (null != personalDetailsMapper.getContactLastName())
            personalDetails.setContactLastName(personalDetailsMapper.getContactLastName());
        if (null != personalDetailsMapper.getDob())
            try {
                personalDetails.setDateOfBirth(Utility.getDateFromISOString(personalDetailsMapper.getDob()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        if (null != personalDetailsMapper.getMobileNo())
            personalDetails.setMobile(personalDetailsMapper.getMobileNo());
        if (null != personalDetailsMapper.getContactSalutation())
            personalDetails.setSalutation(personalDetailsMapper.getContactSalutation());
        if (null != personalDetailsMapper.getPhoneNo())
            personalDetails.setPhone(personalDetailsMapper.getPhoneNo());
        if (null != personalDetailsMapper.getCountryDialCode())

            personalDetails.setCountryDialCode(personalDetailsMapper.getCountryDialCode());
        if (null != personalDetailsMapper.getCountryDialCode1())

            personalDetails.setCountryDialCode1(personalDetailsMapper.getCountryDialCode1());

        personalRepository.save(personalDetails);

        resultMapper = getPersonalDetails(personalDetailsMapper.getId());

        return resultMapper;
    }

    @Override
    public EducationalDetailsMapper updateEducationalDetails(EducationalDetailsMapper educationalDetailsMapper) {
        EducationalDetailsMapper resultMapper = null;
        EducationalDetails educationalDetails = educationalRepository.getById(educationalDetailsMapper.getId());

        if (0 != educationalDetailsMapper.getMarksSecured())
            educationalDetails.setMarksSecured(educationalDetailsMapper.getMarksSecured());
        if (0 != educationalDetailsMapper.getYearOfPassing())
            educationalDetails.setYearOfPassing(educationalDetailsMapper.getYearOfPassing());
        if (null != educationalDetailsMapper.getCourseName())
            educationalDetails.setCourseName(educationalDetailsMapper.getCourseName());
        if (null != educationalDetailsMapper.getCourseType())
            educationalDetails.setCourseType(educationalDetailsMapper.getCourseType());
        if (null != educationalDetailsMapper.getEducationType())
            educationalDetails.setEducationType(educationalDetailsMapper.getEducationTypeId());
        if (null != educationalDetailsMapper.getDocumentType())
            educationalDetails.setDocumentType(educationalDetailsMapper.getDocumentType());
        if (null != educationalDetailsMapper.getSpecialization())
            educationalDetails.setSpecialization(educationalDetailsMapper.getSpecialization());
        if (null != educationalDetailsMapper.getUniversity())
            educationalDetails.setUniversity(educationalDetailsMapper.getUniversity());
        if (null != educationalDetailsMapper.getMarksType())
            educationalDetails.setMarksType(educationalDetailsMapper.getMarksType());

        educationalRepository.save(educationalDetails);

        resultMapper = getEducationDetails(educationalDetailsMapper.getId());

        return resultMapper;
    }

    @Override
    public TrainingDetailsMapper updateTrainingDetails(TrainingDetailsMapper trainingDetailsMapper) {

        TrainingDetailsMapper resultMapper = null;
        TrainingDetails trainingDetails = trainingRepository.getById(trainingDetailsMapper.getId());

        if (null != trainingDetailsMapper.getCourseName())
            trainingDetails.setCourseName(trainingDetailsMapper.getCourseName());
        if (null != trainingDetailsMapper.getGrade())
            trainingDetails.setGrade(trainingDetailsMapper.getGrade());
        if (null != trainingDetailsMapper.getOrganization())
            trainingDetails.setOrganization(trainingDetailsMapper.getOrganization());

        try {
            if (null != trainingDetailsMapper.getStartDate()) {
                trainingDetails.setStartDate(Utility.getDateFromISOString(trainingDetailsMapper.getStartDate()));
            }

            if (null != trainingDetailsMapper.getEndDate()) {

                trainingDetails.setEndDate(Utility.getDateFromISOString(trainingDetailsMapper.getEndDate()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != trainingDetailsMapper.getDocumentTypeName())
            trainingDetails.setDocument_type_name(trainingDetailsMapper.getDocumentTypeName());
        trainingRepository.save(trainingDetails);

        resultMapper = getTrainingDetails(trainingDetailsMapper.getId());

        return resultMapper;

    }

    @Override
    public BankDetailsMapper updateBankDetails(BankDetailsMapper bankDetailsMapper) {
        BankDetails bankDetails = bankRepository.getById(bankDetailsMapper.getId());

        if (null != bankDetailsMapper.getBankName())
            bankDetails.setBankName(bankDetailsMapper.getBankName());
        if (null != bankDetailsMapper.getBranchName())
            bankDetails.setBranchName(bankDetailsMapper.getBranchName());
        if (null != bankDetailsMapper.getIfscCode())
            bankDetails.setIfscCode(bankDetailsMapper.getIfscCode());
        if (null != bankDetailsMapper.getAccountNo())
            bankDetails.setAccountNo(bankDetailsMapper.getAccountNo());
        if (false != bankDetailsMapper.isDefaultInd()) {
            BankDetails bankDetails1 = bankRepository.getByCandidateIdAndDefaultInd(bankDetails.getEmployeeId());
            if (null != bankDetails1) {
                bankDetails1.setDefaultInd(false);
                bankRepository.save(bankDetails1);
            }
            bankDetails.setDefaultInd(bankDetailsMapper.isDefaultInd());
        }
        bankRepository.save(bankDetails);

        BankDetailsMapper resultMapper = getBankDetails(bankDetailsMapper.getId());

        return resultMapper;

    }

    @Override
    public EmploymentHistoryMapper updateEmploymentHistory(EmploymentHistoryMapper employmentHistoryMapper) {

        EmployementHistory employementHistory = employementHistoryRepository.getById(employmentHistoryMapper.getId());

        if (null != employmentHistoryMapper.getCompanyName())
            employementHistory.setCompanyName(employmentHistoryMapper.getCompanyName());
        if (null != employmentHistoryMapper.getDescription())
            employementHistory.setDescription(employmentHistoryMapper.getDescription());
        if (null != employmentHistoryMapper.getStartDate())
            try {
                employementHistory.setStartDate(Utility.getDateFromISOString(employmentHistoryMapper.getStartDate()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        if (null != employmentHistoryMapper.getEndDate())
            try {
                employementHistory.setEndDate(Utility.getDateFromISOString(employmentHistoryMapper.getEndDate()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        if (null != employmentHistoryMapper.getCurrency())

            employementHistory.setCurrency(employmentHistoryMapper.getCurrency());
        if (employmentHistoryMapper.getSalary() != 0)

            employementHistory.setSalary(employmentHistoryMapper.getSalary());

        if (null != employmentHistoryMapper.getSalaryType())
            employementHistory.setSalaryType(employmentHistoryMapper.getSalaryType());

        if (null != employmentHistoryMapper.getDesignationTypeId())
            employementHistory.setDesignation(employmentHistoryMapper.getDesignationTypeId());
        if (null != employmentHistoryMapper.getDocumentType())
            employementHistory.setDocumentType(employmentHistoryMapper.getDocumentType());

        employementHistoryRepository.save(employementHistory);
        EmploymentHistoryMapper resultMapper = getHistoryDetails(employmentHistoryMapper.getId());
        return resultMapper;
    }

    @Override
    public void deleteKeySkillsById(String employeeId, String keySkillId) {
        KeySkillDetails keySkillDetails = keySkillRepository.getKeySkillDetailsByEmpIdAndKeySkillId(employeeId,
                keySkillId);

        if (null != keySkillDetails) {
            keySkillRepository.delete(keySkillDetails);
        }
    }

    @Override
    public String saveToEmployeeIdProcess(EmployeeIDMapper employeeIDMapper) {
        String id = null;
        if (null != employeeIDMapper.getEmployeeId()) {
            /* insert to EmployeeId Details */
            EmployeeIDDetails employeeIdDetails = new EmployeeIDDetails();
            employeeIdDetails.setCreationDate(new Date());
            employeeIdDetails.setEmployeeId(employeeIDMapper.getEmployeeId());
            employeeIdDetails.setIdNo(employeeIDMapper.getIdNo());
            employeeIdDetails.setIdType(employeeIDMapper.getIdType());
            employeeIdDetails.setLiveInd(true);
            employeeIdDetails.setDocumentName(employeeIDMapper.getDocumentName());
            employeeIdDetails.setDescription(employeeIDMapper.getDescription());
            employeeIdDetails.setDocumentType(employeeIDMapper.getDocumentType());
            if (!StringUtils.isEmpty(employeeIDMapper.getDocumentId())) {

                String documentId = employeeIDMapper.getDocumentId();

                DocumentDetails documentDetails = documentDetailsRepository.getDocumentDetailsById(documentId);

                documentDetails.setDocument_title(employeeIDMapper.getDocumentName());
                documentDetails.setDocument_type(employeeIDMapper.getDocumentType());
                documentDetails.setDoc_description(employeeIDMapper.getDescription());
                documentDetails.setDocument_id(documentDetails.getDocument_id());
                documentDetails.setCreation_date(new Date());
                // documentDetails.setDocumentType(documentMapper.getDocumentTypeId());
                documentDetailsRepository.save(documentDetails);


                employeeIdDetails.setDocumentId(employeeIDMapper.getDocumentId());

                EmployeeDocumentLink employeeDocumentLink = new EmployeeDocumentLink();
                employeeDocumentLink.setDocumentId(employeeIDMapper.getDocumentId());
                employeeDocumentLink.setEmployeeId(employeeIDMapper.getEmployeeId());
                employeeDocumentLink.setCreationDate(new Date());
                employeeDocumentLinkRepository.save(employeeDocumentLink);

            }
            EmployeeIDDetails employeeIdDetailss = employeeIdRepository.save(employeeIdDetails);
            id = employeeIdDetailss.getId();

        }
        return id;
    }

    @Override
    public EmployeeIDMapper getEmployeeIdDetails(String id) {
        EmployeeIDDetails employeeIdDetails = employeeIdRepository.getById(id);
        EmployeeIDMapper employeeIDMapper = new EmployeeIDMapper();
        if (null != employeeIdDetails) {
            employeeIDMapper.setEmployeeId(employeeIdDetails.getEmployeeId());
            employeeIDMapper.setIdNo(employeeIdDetails.getIdNo());
            employeeIDMapper.setIdType(employeeIdDetails.getIdType());
            employeeIDMapper.setId(id);
            employeeIDMapper.setDocumentId(employeeIdDetails.getDocumentId());
            employeeIDMapper.setDocumentName(employeeIdDetails.getDocumentName());
            employeeIDMapper.setDescription(employeeIdDetails.getDescription());
            employeeIDMapper.setCreationDate(Utility.getISOFromDate(employeeIdDetails.getCreationDate()));
            if (!StringUtils.isEmpty(employeeIdDetails.getDocumentType())) {
                DocumentType documentType = documentTypeRepository.getTypeDetails(employeeIdDetails.getDocumentType());
                if (null != documentType) {
                    employeeIDMapper.setDocumentType(documentType.getDocumentTypeName());
                }
            }
        }

        return employeeIDMapper;
    }

    @Override
    public EmployeeIDMapper updateEmployeeIdDetails(EmployeeIDMapper employeeIDMapper) {
        EmployeeIDMapper resultMapper = new EmployeeIDMapper();
        EmployeeIDDetails employeeIdDetails = employeeIdRepository.getById(employeeIDMapper.getId());
        if (null != employeeIdDetails) {
            if (null != employeeIDMapper.getIdNo())
                employeeIdDetails.setIdNo(employeeIDMapper.getIdNo());
            if (null != employeeIDMapper.getIdType())
                employeeIdDetails.setIdType(employeeIDMapper.getIdType());

            if (null != employeeIDMapper.getDescription())
                employeeIdDetails.setDescription(employeeIDMapper.getDescription());

            if (null != employeeIDMapper.getDocumentName())
                employeeIdDetails.setDocumentName(employeeIDMapper.getDocumentName());
            if (null != employeeIDMapper.getDocumentType())
                employeeIdDetails.setDocumentType(employeeIDMapper.getDocumentType());
        }
        employeeIdRepository.save(employeeIdDetails);
        resultMapper = getEmployeeIdDetails(employeeIDMapper.getId());

        return resultMapper;
    }

    @Override
    public List<EmployeeIDMapper> getEmployeeIDDetails(String employeeId) {
        List<EmployeeIDDetails> employeeIdDetailsList = employeeIdRepository
                .getEmployeeIdDetailsListByEmployeeId(employeeId);
        if (null != employeeIdDetailsList && !employeeIdDetailsList.isEmpty()) {
            return employeeIdDetailsList.stream().map(employeeIdDetails -> {
                EmployeeIDMapper employeeIDMapper = getEmployeeIdDetails(employeeIdDetails.getId());
                if (null != employeeIDMapper) {
                    return employeeIDMapper;
                }
                return null;
            }).collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public List<EmployeeMapper> getEmployeeListByUserIdWithDateRange(String userId, String startDate, String endDate) {
        Date end_date = null;
        Date start_date = null;
        // List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
        try {
            end_date = Utility.getDateAfterEndDate(Utility.getDateFromISOString(endDate));
            start_date = Utility.getDateFromISOString(startDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<EmployeeDetails> empList = employeeRepository.getEmployeesByIdWithDateRange(userId, start_date, end_date);
        if (null != empList && !empList.isEmpty()) {
            return empList.stream().map(employeeDetails -> {
                EmployeeMapper employeeMapper = new EmployeeMapper();
                employeeMapper.setEmailId(employeeDetails.getEmailId());
                employeeMapper.setDepartment(employeeDetails.getDepartment());
                employeeMapper.setCreationDate(Utility.getISOFromDate(employeeDetails.getCreationDate()));
                employeeMapper.setDateOfJoining(Utility.getISOFromDate(employeeDetails.getDateOfJoining()));
                employeeMapper.setDesignation(employeeDetails.getDesignation());
                employeeMapper.setMobileNo(employeeDetails.getMobileNo());
                employeeMapper.setCountryDialCode(employeeDetails.getCountryDialCode());
                employeeMapper.setFirstName(employeeDetails.getFirstName());
                employeeMapper.setEmployeeId(employeeDetails.getEmployeeId());
                employeeMapper.setFirstName(employeeDetails.getFirstName());
                employeeMapper.setMiddleName(employeeDetails.getMiddleName());
                employeeMapper.setLastName(employeeDetails.getLastName());
                return employeeMapper;
            }).collect(Collectors.toList());

        }
        return null;
    }

    @Override
    public List<EmployeeMapper> getEmployeeListByOrgIdWithDateRange(String orgId, String startDate, String endDate) {

        Date end_date = null;
        Date start_date = null;
        // List<EmployeeMapper> resultList = new ArrayList<EmployeeMapper>();
        try {
            end_date = Utility.getDateAfterEndDate(Utility.getDateFromISOString(endDate));
            start_date = Utility.getDateFromISOString(startDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<EmployeeDetails> empList = employeeRepository.getEmployeesByOrgIdWithDateRange(orgId, start_date,
                end_date);

        if (null != empList && !empList.isEmpty()) {
            return empList.stream().map(employeeDetails -> {
                EmployeeMapper employeeMapper = new EmployeeMapper();
                employeeMapper.setEmailId(employeeDetails.getEmailId());
                employeeMapper.setDepartment(employeeDetails.getDepartment());
                employeeMapper.setCreationDate(Utility.getISOFromDate(employeeDetails.getCreationDate()));
                employeeMapper.setDateOfJoining(Utility.getISOFromDate(employeeDetails.getDateOfJoining()));
                employeeMapper.setDesignation(employeeDetails.getDesignation());
                employeeMapper.setMobileNo(employeeDetails.getMobileNo());
                employeeMapper.setCountryDialCode(employeeDetails.getCountryDialCode());
                employeeMapper.setFirstName(employeeDetails.getFirstName());
                employeeMapper.setEmployeeId(employeeDetails.getEmployeeId());
                employeeMapper.setFirstName(employeeDetails.getFirstName());
                employeeMapper.setMiddleName(employeeDetails.getMiddleName());
                employeeMapper.setLastName(employeeDetails.getLastName());
                return employeeMapper;
            }).collect(Collectors.toList());

        }
        return null;

    }

    @Override
    public String getHREmployeeId(String orgId) {

        List<EmployeeDetails> empList = employeeRepository.getEmployeesByOrgId(orgId);
        String empId = null;
        if (null != empList && !empList.isEmpty()) {
            for (EmployeeDetails employeeDetails : empList) {

                if (!StringUtils.isEmpty(employeeDetails.getDepartment())) {
                    if (employeeDetails.getDepartment().equalsIgnoreCase("HR")) {
                        empId = employeeDetails.getEmployeeId();
                    }
                }
            }

        }

        return empId;
    }

    @Override
    public String getAdminIdByOrgId(String orgId) {
        List<EmployeeDetails> empList = employeeRepository.getEmployeesByOrgId(orgId);
        String empId = null;
        if (null != empList && !empList.isEmpty()) {
            for (EmployeeDetails employeeDetails : empList) {
                if (!StringUtils.isEmpty(employeeDetails.getRole())) {
                    if (employeeDetails.getRole().equalsIgnoreCase("ADMIN")) {
                        empId = employeeDetails.getEmployeeId();
                    }
                }
            }

        }

        return empId;
    }

    @Override
    public void deleteEmployementHistoryById(String id) {
        EmployementHistory employementHistory = employementHistoryRepository.getById(id);
        if (null != employementHistory) {
            employementHistoryRepository.delete(employementHistory);

        }

    }

    @Override
    public void deleteTrainingDetailsById(String id) {
        TrainingDetails trainingDetails = trainingRepository.getById(id);
        if (null != trainingDetails) {
            trainingRepository.delete(trainingDetails);

        }
    }

    @Override
    public void deleteEducationDetailsById(String id) {
        EducationalDetails educationalDetails = educationalRepository.getById(id);
        if (null != educationalDetails) {
            educationalRepository.delete(educationalDetails);

        }
    }

    @Override
    public void deleteBankDetailsById(String id) {
        BankDetails bankDetails = bankRepository.getById(id);
        if (null != bankDetails) {
            bankRepository.delete(bankDetails);

        }
    }

    @Override
    public void deletePersonalDetailsById(String id) {
        PersonalDetails personalDetails = personalRepository.getById(id);
        if (null != personalDetails) {
            personalRepository.delete(personalDetails);

        }
    }

    @Override
    public void deleteEmployeeIDDetailsById(String id) {

        EmployeeIDDetails employeeIdDetails = employeeIdRepository.getById(id);
        if (null != employeeIdDetails) {
            employeeIdRepository.delete(employeeIdDetails);

        }

    }

    @Override
    public String saveNotes(NotesMapper notesMapper) {
        String notesId = null;
        if (null != notesMapper) {
            Notes notes = new Notes();

            notes.setNotes(notesMapper.getNotes());
            notes.setCreation_date(new Date());
            notes.setLiveInd(true);
            notes.setUserId(notesMapper.getUserId());   
            Notes note = notesRepository.save(notes);
            notesId = note.getNotes_id();
        }
        if (notesMapper.getType().equalsIgnoreCase("employee")) {
            EmployeeNotesLink link = new EmployeeNotesLink();
            link.setCreationDate(new Date());
            link.setEmployeeId(notesMapper.getId());
            link.setLiveInd(true);
            link.setNotesId(notesId);
            employeeNotesLinkRepository.save(link);
        }

        /* insert to customer-notes-link */
        if (notesMapper.getType().equalsIgnoreCase("room")) {
            RoomNotesLink link = new RoomNotesLink();
            link.setCreationDate(new Date());
            link.setRoomId(notesMapper.getId());
            link.setLiveInd(true);
            link.setNotesId(notesId);
            roomNotesLinkRepository.save(link);
        }
        
        if (notesMapper.getType().equalsIgnoreCase("call")) {
            CallNotesLink callNotesLink = new CallNotesLink();
            callNotesLink.setNotesId(notesId);
            callNotesLink.setCallId(notesMapper.getId());
            callNotesLink.setLiveInd(true);
            callNotesLinkRepository.save(callNotesLink);
        }
        
        if (notesMapper.getType().equalsIgnoreCase("contact")) {
            ContactNotesLink contactNotesLink = new ContactNotesLink();
            contactNotesLink.setNotesId(notesId);
            contactNotesLink.setContact_id(notesMapper.getId());
            contactNotesLink.setLiveInd(true);
            contactNotesLinkRepository.save(contactNotesLink);
        }
        if (notesMapper.getType().equalsIgnoreCase("opportunity")) {
            OpportunityNotesLink opportunityNotesLink = new OpportunityNotesLink();
            opportunityNotesLink.setNotesId(notesId);
            opportunityNotesLink.setOpportunity_id(notesMapper.getId());
            opportunityNotesLink.setLiveInd(true);
            opportunityNotesLinkRepository.save(opportunityNotesLink);
        }
        if (notesMapper.getType().equalsIgnoreCase("customer")) {
            CustomerNotesLink customerNotesLink = new CustomerNotesLink();
            customerNotesLink.setNotesId(notesId);
            customerNotesLink.setCustomerId(notesMapper.getId());
            customerNotesLink.setLiveInd(true);
            customerNotesLinkRepository.save(customerNotesLink);
        }

        if (notesMapper.getType().equalsIgnoreCase("event")) {
            EventNotesLink eventNotesLink = new EventNotesLink();
            eventNotesLink.setNotesId(notesId);
            eventNotesLink.setEventId(notesMapper.getId());
            eventNotesLink.setLiveInd(true);
            eventNotesLinkRepository.save(eventNotesLink);
        }

        if (notesMapper.getType().equalsIgnoreCase("expense")) {
            ExpenseNotesLink leaveNotesLink = new ExpenseNotesLink();
            leaveNotesLink.setNoteId(notesId);
            leaveNotesLink.setLiveInd(true);
            leaveNotesLink.setExpenseId(notesMapper.getId());
            expenseNotesLinkRepository.save(leaveNotesLink);
        }

        if (notesMapper.getType().equalsIgnoreCase("investor")) {
            InvestorNoteLink investorNoteLink = new InvestorNoteLink();
            investorNoteLink.setNoteId(notesId);
            investorNoteLink.setLiveInd(true);
            investorNoteLink.setInvestorId(notesMapper.getId());
            investorNotesLinkRepo.save(investorNoteLink);
        }

        if (notesMapper.getType().equalsIgnoreCase("investorLead")) {
            InvestorLeadsNotesLink investorLeadsNotesLink = new InvestorLeadsNotesLink();
            investorLeadsNotesLink.setNoteId(notesId);
            investorLeadsNotesLink.setLiveInd(true);
            investorLeadsNotesLink.setInvestorLeadsId(notesMapper.getId());
            investorLeadsNotesLinkRepository.save(investorLeadsNotesLink);
        }

        if (notesMapper.getType().equalsIgnoreCase("lead")) {
            LeadsNotesLink leadsNotesLink = new LeadsNotesLink();
            leadsNotesLink.setNotesId(notesId);
            leadsNotesLink.setLiveInd(true);
            leadsNotesLink.setLeadsId(notesMapper.getId());
            leadsNotesLinkRepository.save(leadsNotesLink);
        }

        if (notesMapper.getType().equalsIgnoreCase("leave")) {
            LeaveNotesLink leaveNotesLink = new LeaveNotesLink();
            leaveNotesLink.setNoteId(notesId);
            leaveNotesLink.setLiveInd(true);
            leaveNotesLink.setLeaveId(notesMapper.getId());
            leaveNotesLinkRepository.save(leaveNotesLink);
        }

        if (notesMapper.getType().equalsIgnoreCase("mileage")) {
            MileageNotesLink mileageNotesLink = new MileageNotesLink();
            mileageNotesLink.setNoteId(notesId);
            mileageNotesLink.setLiveInd(true);
            mileageNotesLink.setMileageId(notesMapper.getId());
            mileageNotesLinkRepository.save(mileageNotesLink);
        }

        if (notesMapper.getType().equalsIgnoreCase("partner")) {
            PartnerNotesLink partnerNotesLink = new PartnerNotesLink();
            partnerNotesLink.setNoteId(notesId);
            partnerNotesLink.setLiveInd(true);
            partnerNotesLink.setPartnerId(notesMapper.getId());
            partnerNotesLinkRepository.save(partnerNotesLink);
        }

        if (notesMapper.getType().equalsIgnoreCase("task")) {
            TaskNotesLink taskNotesLink = new TaskNotesLink();
            taskNotesLink.setNotesId(notesId);
            taskNotesLink.setLiveInd(true);
            taskNotesLink.setCreationDate(new Date());
            taskNotesLink.setTaskId(notesMapper.getId());
            taskNotesLinkRepository.save(taskNotesLink);
        }


        return notesId;
    }

    @Override
    public NotesMapper updateNotes(NotesMapper notesMapper) {
        NotesMapper note = new NotesMapper();
        if (null != notesMapper.getNotes()) {
            Notes notes = notesRepository.getById(notesMapper.getNotesId());
            if (null != notes) {
                notes.setNotes(notesMapper.getNotes());
                note = getNotes(notesRepository.save(notes).getNotes_id());
            }
        }
        return note;
    }

    @Override
    public List<NotesMapper> getNotesByEmployeeId(String employeeId) {

        List<EmployeeNotesLink> empNotesLinkList = employeeNotesLinkRepository.getNotesIdByEmployeeId(employeeId);
        if (empNotesLinkList != null && !empNotesLinkList.isEmpty()) {
            return empNotesLinkList.stream().map(employeeNotesLink -> {
                NotesMapper notesMapper = getNotes(employeeNotesLink.getNotesId());
                return notesMapper;
            }).collect(Collectors.toList());

        }

        return null;
    }

    @Override
    public String saveSalaryDetails(SalaryDetailsMapper salaryDetailsMapper) {

        String id1 = null;

        SalaryInfo salaryInfo = new SalaryInfo();
        salaryInfo.setCreation_date(new Date());
        salaryInfo.setLive_ind(true);

        SalaryInfo info = salaryInfoRepository.save(salaryInfo);
        String id = info.getSalary_id();

        // String salaryDetailsId = null;
        if (null != salaryDetailsMapper) {

            SalaryDetails salaryDetails = new SalaryDetails();
            salaryDetails.setEmployeeId(salaryDetailsMapper.getEmployeeId());
            salaryDetails.setGrossMonthlySalary(salaryDetailsMapper.getGrossMonthlySalary());
            salaryDetails.setNetSalary(salaryDetailsMapper.getNetSalary());
            salaryDetails.setCurrency(salaryDetailsMapper.getCurrency());
            salaryDetails.setType(salaryDetailsMapper.getType());
            salaryDetails.setBasic(salaryDetailsMapper.getBasic());
            salaryDetails.setHra(salaryDetailsMapper.getHra());
            salaryDetails.setLta(salaryDetailsMapper.getLta());
            salaryDetails.setHolidayPay(salaryDetailsMapper.getHolidayPay());
            salaryDetails.setSalary_id(id);
            salaryDetails.setLiveInd(true);
            try {
                salaryDetails.setStartingDate(Utility.getDateFromISOString(salaryDetailsMapper.getStartingDate()));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                salaryDetails.setEndDate(Utility.getDateFromISOString(salaryDetailsMapper.getEndDate()));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            SalaryDetails salary = salaryRepository.save(salaryDetails);
            id1 = salary.getId();
        }

        return id1;
    }

    @Override
    public List<SalaryDetailsMapper> getSalaryDetailsByEmployeeID(String employeeId) {
        List<SalaryDetails> salaryDetailsList = salaryRepository.getSalaryDetailsById(employeeId);
        if (null != salaryDetailsList && !salaryDetailsList.isEmpty()) {
            return salaryDetailsList.stream().map(salaryDetails -> {
                SalaryDetailsMapper salaryDetailsMapper = getSalaryDetails(salaryDetails.getId());
                if (null != salaryDetailsMapper) {
                    return salaryDetailsMapper;
                }
                return null;
            }).collect(Collectors.toList());

        }
        return null;
    }

    @Override
    public NotesMapper getNotes(String id) {
        Notes notes = notesRepository.findByNoteId(id);
        NotesMapper notesMapper = new NotesMapper();
        if (null != notes) {
            notesMapper.setNotesId(notes.getNotes_id());
            notesMapper.setNotes(notes.getNotes());
            notesMapper.setLiveInd(notes.isLiveInd());
            // notesMapper.setCreationDate(notes.getCreation_date());
            notesMapper.setCreationDate(Utility.getISOFromDate(notes.getCreation_date()));
            System.out.println("date" + notes.getCreation_date());
        }
        return notesMapper;
    }

    @Override
    public SalaryDetailsMapper getSalaryDetails(String id) {
        SalaryDetails salaryDetails = salaryRepository.getById(id);

        SalaryDetailsMapper salaryDetailsMapper = new SalaryDetailsMapper();
        salaryDetailsMapper.setSalaryDetailsId(salaryDetails.getId());
        salaryDetailsMapper.setEmployeeId(salaryDetails.getEmployeeId());
        salaryDetailsMapper.setGrossMonthlySalary(salaryDetails.getGrossMonthlySalary());
        salaryDetailsMapper.setNetSalary(salaryDetails.getNetSalary());
        salaryDetailsMapper.setCurrency(salaryDetails.getCurrency());
        salaryDetailsMapper.setType(salaryDetails.getType());
        salaryDetailsMapper.setBasic(salaryDetails.getBasic());
        salaryDetailsMapper.setHra(salaryDetails.getHra());
        salaryDetailsMapper.setLta(salaryDetails.getLta());
        salaryDetailsMapper.setHolidayPay(salaryDetails.getHolidayPay());
        salaryDetailsMapper.setStartingDate(Utility.getISOFromDate(salaryDetails.getStartingDate()));
        if (salaryDetails.getEndDate() != null) {
            salaryDetailsMapper.setEndDate(Utility.getISOFromDate(salaryDetails.getEndDate()));
        } else {
            salaryDetailsMapper.setEndDate("Till Now");
        }
        return salaryDetailsMapper;
    }

    @Override
    public SalaryDetailsMapper updateSalaryDetails(SalaryDetailsMapper salaryDetailsMapper) {
        SalaryDetails salaryDetails = salaryRepository.getById(salaryDetailsMapper.getSalaryDetailsId());

        /*
         * try {
         * salaryDetails.setEndDate(Utility.getDateFromISOString(salaryDetailsMapper.
         * getEndDate())); } catch (Exception e1) { // TODO Auto-generated catch block
         * e1.printStackTrace(); }
         */
        if (null != salaryDetails) {
            salaryDetails.setLiveInd(false);
            salaryRepository.save(salaryDetails);

            SalaryDetails salDetails = new SalaryDetails();
            salDetails.setSalary_id(salaryDetails.getSalary_id());
            salDetails.setLiveInd(true);
            if (null != salaryDetailsMapper.getGrossMonthlySalary())
                salDetails.setGrossMonthlySalary(salaryDetailsMapper.getGrossMonthlySalary());
            if (null != salaryDetailsMapper.getNetSalary())
                salDetails.setNetSalary(salaryDetailsMapper.getNetSalary());
            if (null != salaryDetailsMapper.getCurrency())
                salDetails.setCurrency(salaryDetailsMapper.getCurrency());
            if (null != salaryDetailsMapper.getEmployeeId()) {
                salDetails.setEmployeeId(salaryDetailsMapper.getEmployeeId());
            } else {
                salDetails.setEmployeeId(salaryDetails.getEmployeeId());
            }
            if (null != salaryDetailsMapper.getStartingDate())
                try {
                    salDetails.setStartingDate(Utility.getDateFromISOString(salaryDetailsMapper.getStartingDate()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            SalaryDetailsMapper salaryDetailsMapper1 = getSalaryDetails(salaryRepository.save(salDetails).getId());

            return salaryDetailsMapper1;
        }
        return null;
    }

    @Override
    public EmployeeViewMapper updateEmployee(String employeeId, EmployeeMapper employeeMapper) {
        EmployeeViewMapper resultMapper = null;
        EmployeeDetails employee = employeeRepository.getEmployeeDetailsByEmployeeId(employeeId, true);
        // System.out.println("customer details@@@"+customer);
        if (null != employee) {

            /*
             * employee.setLiveInd(false); employee.setSuspendInd(false);
             * employeeRepository.save(employee); } EmployeeDetails newemployee=new
             * EmployeeDetails();
             *
             *
             * newemployee.setEmployeeId(employeeId);
             */

            if (null != employeeMapper.getOrganizationId()) {
                employee.setOrgId(employeeMapper.getOrganizationId());
            }

            if (null != employeeId) {
                employee.setUserId(employeeId);
            }

            if (null != employeeMapper.getSalutation()) {
                employee.setSalutation(employeeMapper.getSalutation());
            }

            if (null != employeeMapper.getFirstName()) {
                employee.setFirstName(employeeMapper.getFirstName());
            }

            if (null != employeeMapper.getMiddleName()) {
                employee.setMiddleName(employeeMapper.getMiddleName());
            }

            if (null != employeeMapper.getLastName()) {
                employee.setLastName(employeeMapper.getLastName());
            }

            if (null != employeeMapper.getStatus()) {
                employee.setStatus(employeeMapper.getStatus());
            }

            if (null != employeeMapper.getImageId()) {
                employee.setImageId(employeeMapper.getImageId());
            }

            if (null != employeeMapper.getGender()) {
                employee.setGender(employeeMapper.getGender());
            }
            if (null != employeeMapper.getCountryDialCode()) {
                employee.setCountryDialCode(employeeMapper.getCountryDialCode());
            }

            if (null != employeeMapper.getCountryDialCode1()) {
                employee.setCountryDialCode1(employeeMapper.getCountryDialCode1());
            }

            if (null != employeeMapper.getPhoneNo()) {
                employee.setPhoneNo(employeeMapper.getPhoneNo());
            }
            if (null != employeeMapper.getMobileNo()) {
                employee.setMobileNo(employeeMapper.getMobileNo());
            }
            if (null != employeeMapper.getEmailId()) {
                employee.setEmailId(employeeMapper.getEmailId());
            }
            if (null != employeeMapper.getSecondaryEmailId()) {
                employee.setSecondaryEmailId(employeeMapper.getSecondaryEmailId());
            }
            if (null != employeeMapper.getSkypeId()) {
                employee.setSkypeId(employeeMapper.getSkypeId());
            }
            if (null != employeeMapper.getFax()) {
                employee.setFax(employeeMapper.getFax());
            }
            if (null != employeeMapper.getLinkedinPublicUrl()) {
                employee.setLinkedinPublicUrl(employeeMapper.getLinkedinPublicUrl());
            }
            if (null != employeeMapper.getTwitter()) {
                employee.setTwitter(employeeMapper.getTwitter());
            }

            if (null != employeeMapper.getTimeZone()) {
                employee.setTimeZone(employeeMapper.getTimeZone());
            }

            if (null != employeeMapper.getServiceLineId()) {
                employee.setServiceLine(employeeMapper.getServiceLineId());
            }

            if (0 != employeeMapper.getSalary()) {
                employee.setSalary(employeeMapper.getSalary());
            }

            if (null != employeeMapper.getDateOfJoining()) {
                try {
                    employee.setDateOfJoining(Utility.getDateFromISOString(employeeMapper.getDateOfJoining()));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (null != employeeMapper.getDob()) {
                try {
                    employee.setDob(Utility.getDateFromISOString(employeeMapper.getDob()));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (null != employeeMapper.getDesignationTypeId()) {
                employee.setDesignation(employeeMapper.getDesignationTypeId());
            }

            if (null != employeeMapper.getBloodGroup()) {
                employee.setBloodGroup(employeeMapper.getBloodGroup());
            }
            if (null != employeeMapper.getEmployeeType()) {
                employee.setEmployeeType(employeeMapper.getEmployeeType());
            }
            if (null != employeeMapper.getPreferedLanguage()) {
                employee.setLanguage(employeeMapper.getPreferedLanguage());
            }

            if (null != employeeMapper.getFirstName()) {
                String middleName = " ";
                String lastName = "";

                if (!StringUtils.isEmpty(employeeMapper.getLastName())) {

                    lastName = employeeMapper.getLastName();
                }
                if (employeeMapper.getMiddleName() != null && employeeMapper.getMiddleName().length() > 0) {

                    middleName = employeeMapper.getMiddleName();
                    employee.setFullName(employeeMapper.getFirstName() + " " + middleName + " " + lastName);
                } else {

                    employee.setFullName(employeeMapper.getFirstName() + " " + lastName);
                }
            } else {
                employee.setFullName(employeeMapper.getFullName());
            }
            EmployeeDetails details = employeeRepository.save(employee);

            resultMapper = getEmployeeDetails(details);
        }
//        String msg = employeeMapper.getFirstName() + " employee updated";
//		notificationService.createNotificationForAll(employeeMapper.getOrganizationId(), employeeMapper.getUserId(),"user update", msg,"user","update");
        return resultMapper;
    }

    @Override
    public EmployeeViewMapper getEmployeeDetailsByUserId(String userId) {
        EmployeeDetails details = employeeRepository.getEmployeeDetailsByEmployeeId(userId, true);
        EmployeeViewMapper resultMapper = getEmployeeDetails(details);

        List<Team> team = teamRepository.getByTeamLead(userId);
        if (null != team && !team.isEmpty()) {
            resultMapper.setTeamAccessInd(true);
        }
        return resultMapper;

    }

    @Override
    public EmployeeViewMapper getEmployeeDetailsByEmployeeId(String employeeId) {
        EmployeeViewMapper resultMapper = null;
        EmployeeDetails details = employeeRepository.getEmployeeDetailsByEmployeeId(employeeId, true);
        if (null != details) {
            resultMapper = getEmployeeDetails(details);
        }
        return resultMapper;
    }

    @Override
    public EmployeeTableMapper getEmployeeDetailByEmployeeId(String employeeId) {
        EmployeeTableMapper employeeMapper = new EmployeeTableMapper();
        EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(employeeId, true);
        if (null != employeeDetails) {
            employeeMapper.setEmployeeId(employeeDetails.getEmployeeId());
            employeeMapper.setFirstName(employeeDetails.getFirstName());
            employeeMapper.setMiddleName(employeeDetails.getMiddleName());
            employeeMapper.setLastName(employeeDetails.getLastName());
            employeeMapper.setCreationDate(Utility.getISOFromDate((employeeDetails.getCreationDate())));
            employeeMapper.setCreatorId(employeeDetails.getCreatorId());
            employeeMapper.setDateOfJoining(Utility.getISOFromDate(employeeDetails.getDateOfJoining()));
            employeeMapper.setReportingManager(employeeDetails.getReportingManager());

            if (null != employeeDetails.getDob()) {
                employeeMapper.setDob(Utility.getISOFromDate(employeeDetails.getDob()));

            } else {
                employeeMapper.setDob("");
            }

            if (null != employeeDetails.getDateOfJoining()) {
                employeeMapper.setDateOfJoining(Utility.getISOFromDate(employeeDetails.getDateOfJoining()));
            } else {
                employeeMapper.setDateOfJoining("");

            }
            employeeMapper.setEmailId(employeeDetails.getEmailId());
            employeeMapper.setSecondaryEmailId(employeeDetails.getSecondaryEmailId());
            employeeMapper.setFax(employeeDetails.getFax());
            // employeeMapper.setFullName(employeeDetails.getFullName());
            employeeMapper.setGender(employeeDetails.getGender());
            employeeMapper.setImageId(employeeDetails.getImageId());
            employeeMapper.setLinkedinPublicUrl(employeeDetails.getLinkedinPublicUrl());
            employeeMapper.setMobileNo(employeeDetails.getMobileNo());
            employeeMapper.setPhoneNo(employeeDetails.getPhoneNo());
            employeeMapper.setSkypeId(employeeDetails.getSkypeId());
            employeeMapper.setSalutation(employeeDetails.getSalutation());
            employeeMapper.setStatus(employeeDetails.getStatus());
            employeeMapper.setPreferedLanguage(employeeDetails.getLanguage());
            employeeMapper.setTimeZone(employeeDetails.getTimeZone());
            employeeMapper.setSuspendInd(employeeDetails.isSuspendInd());
            employeeMapper.setEmployeeType(employeeDetails.getEmployeeType());
            employeeMapper.setWorkplace(employeeDetails.getWorkplace());
            employeeMapper.setLabel(employeeDetails.getLabel());
            // employeeMapper.setType(employeeDetails.getUserType());
            employeeMapper.setBilledInd(employeeDetails.isBilledInd());
            employeeMapper.setSalary(employeeDetails.getSalary());
            employeeMapper.setMultyOrgAccessInd(employeeDetails.isMultyOrgAccessInd());

            System.out.println("EMPLOYEEID=" + employeeDetails.getEmployeeId());
            if (employeeDetails.getUserType().equalsIgnoreCase("External")) {
                employeeMapper.setType(true);
            } else {
                employeeMapper.setType(false);
            }
            ServiceLine db = serviceLineRepository.findByServiceLineId(employeeDetails.getServiceLine());
            if (null != db) {
                employeeMapper.setServiceLine(db.getServiceLineName());
                employeeMapper.setServiceLineId(db.getServiceLineId());
            }
            String middleName = " ";
            String lastName = "";

            if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                lastName = employeeDetails.getLastName();
            }

            if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

                middleName = employeeDetails.getMiddleName();
                employeeMapper.setFullName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
            } else {

                employeeMapper.setFullName(employeeDetails.getFirstName() + " " + lastName);
            }

            if (!StringUtils.isEmpty(employeeDetails.getDesignation())) {
                Designation designationn = designationRepository
                        .findByDesignationTypeId(employeeDetails.getDesignation());
                if (null != designationn) {
                    employeeMapper.setDesignationTypeId(designationn.getDesignationTypeId());
                    employeeMapper.setDesignation(designationn.getDesignationType());
                }
            }

            if (!StringUtils.isEmpty(employeeDetails.getDepartment())) {
                // employeeMapper.setDepartment(employeeDetails.getDepartment());
                // System.out.println("department
                // Name@@@@@@@@@@@@"+employeeDetails.getDepartment());

                Department department = departmentRepository.getDepartmentDetails(employeeDetails.getDepartment());
                if (null != department) {
                    employeeMapper.setDepartmentId(department.getDepartment_id());
                    employeeMapper.setDepartment(department.getDepartmentName());
//                     employeeMapper.setCrmInd(department.isCrmInd());
//                     employeeMapper.setErpInd(department.isErpInd());
//                     employeeMapper.setImInd(department.isImInd());
                }

                RoleType role = roleTypeRepository.findByRoleTypeId(employeeDetails.getRoleType());
                if (null != role) {
                    employeeMapper.setRoleType(role.getRoleType());
                }
            }

            List<KeyskillsMapper> skillList = new ArrayList<KeyskillsMapper>();
            List<KeySkillDetails> list = keySkillRepository.getSkillByEmployeeId(employeeDetails.getEmployeeId());
            if (null != list && !list.isEmpty()) {
                for (KeySkillDetails skill : list) {
                    KeySkillDetails list2 = keySkillRepository.getById(skill.getId());

                    KeyskillsMapper mapper = new KeyskillsMapper();
                    if (null != list2) {

                        mapper.setKeySkillsName(list2.getSkillName());
                        mapper.setEmployeeId(list2.getEmployeeId());
                        mapper.setKeySkillsId(list2.getId());
                        skillList.add(mapper);
                    }
                }
                employeeMapper.setSkill(skillList);
            }

            employeeMapper.setRole(employeeDetails.getRole());

            if (!StringUtils.isEmpty(employeeDetails.getCountry())) {
                employeeMapper.setCountry(employeeDetails.getCountry());
                Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(employeeDetails.getCountry(), employeeDetails.getOrgId());
                if (null != country) {
                    employeeMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
                    employeeMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
                }
            } else {
                List<EmployeeAddressLink> employeeAddressList = employeeAddressLinkRepository
                        .getAddressListByEmpId(employeeDetails.getEmployeeId());
                if (null != employeeAddressList && !employeeAddressList.isEmpty()) {
                    for (EmployeeAddressLink employeeAddressLink : employeeAddressList) {
                        AddressDetails addressDetails = addressRepository
                                .getAddressDetailsByAddressId(employeeAddressLink.getAddressId());
                        if (null != addressDetails) {
                            if (!StringUtils.isEmpty(addressDetails.getCountry())) {
                                employeeMapper.setCountry(addressDetails.getCountry());
                                Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(addressDetails.getCountry(), employeeDetails.getOrgId());
                                if (null != country) {
                                    employeeMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
                                    employeeMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
                                }
                            }
                        }
                    }
                }
            }

            employeeMapper.setTwitter(employeeDetails.getTwitter());
            employeeMapper.setFacebook(employeeDetails.getFacebook());
            employeeMapper.setCurrency(employeeDetails.getCurrency());
            employeeMapper.setCountryDialCode(employeeDetails.getCountryDialCode());
            employeeMapper.setCountryDialCode1(employeeDetails.getCountryDialCode1());
            employeeMapper.setOrganizationId(employeeDetails.getOrgId());
            employeeMapper.setUserId(employeeDetails.getUserId());
            employeeMapper.setBloodGroup(employeeDetails.getBloodGroup());

            LocationDetails location = locationDetailsRepository.findByLocationDetailsIdAndActiveInd(employeeDetails.getLocation(), true);
            if (null != location) {
                employeeMapper.setLocation(location.getLocationName());
            }

//             List<EmployeeAddressLink> employeeAddressList = employeeAddressLinkRepository
//                     .getAddressListByEmpId(employeeDetails.getEmployeeId());
//             List<AddressMapper> addressList = new ArrayList<AddressMapper>();
//             if (null != employeeAddressList && !employeeAddressList.isEmpty()) {
//
//                 for (EmployeeAddressLink employeeAddressLink : employeeAddressList) {
//                     AddressDetails addressDetails = addressRepository
//                             .getAddressDetailsByAddressId(employeeAddressLink.getAddressId());
//
//                     AddressMapper addressMapper = new AddressMapper();
//                     if (null != addressDetails) {
//                         addressMapper.setAddress1(addressDetails.getAddressLine1());
//                         addressMapper.setAddress2(addressDetails.getAddressLine2());
//                         addressMapper.setAddressType(addressDetails.getAddressType());
//                         addressMapper.setPostalCode(addressDetails.getPostalCode());
//                         addressMapper.setStreet(addressDetails.getStreet());
//                         addressMapper.setCity(addressDetails.getCity());
//                         addressMapper.setTown(addressDetails.getTown());
//                         addressMapper.setCountry(addressDetails.getCountry());
//                         addressMapper.setLatitude(addressDetails.getLatitude());
//                         addressMapper.setLongitude(addressDetails.getLongitude());
//                         addressMapper.setState(addressDetails.getState());
//                         addressMapper.setAddressId(addressDetails.getAddressId());
//                         addressMapper.setHouseNo(addressDetails.getHouseNo());
//                         addressList.add(addressMapper);
//
//                     }
//
//                 }
//             }
//             employeeMapper.setAddress(addressList);

            List<DocumentType> documentType = documentTypeRepository.getDocumentTypeListByOrgIdAndUserTypeWithLiveIndAndMandatoryInd(employeeDetails.getOrgId(), "User");
            List<String> documentList = new ArrayList<>();
            if (null != documentType && !documentType.isEmpty()) {
                for (DocumentType documentType2 : documentType) {
                    documentList.add(documentType2.getDocumentTypeName());
                }
                for (DocumentType documentType1 : documentType) {
                    System.out.println("User Id =====-=----inner3-----=--///" + employeeDetails.getEmployeeId());
                    if (null != documentType1.getDocument_type_id()) {
                        List<EmployeeDocumentLink> employeeDocumentLink = employeeDocumentLinkRepository.
                                getDocumentByEmployeeId(employeeDetails.getEmployeeId());
                        if (null != employeeDocumentLink && !employeeDocumentLink.isEmpty()) {
                            for (EmployeeDocumentLink employeeDocumentLink1 : employeeDocumentLink) {
                                DocumentDetails documentDetails = documentDetailsRepository.getDocumentDetailsById(employeeDocumentLink1.getDocumentId());
                                if (null != documentDetails) {
                                    if (null != documentDetails.getDocument_type()) {
                                        if (documentDetails.getDocument_type().equalsIgnoreCase(documentType1.getDocument_type_id())) {
                                            documentList.remove(documentType1.getDocumentTypeName());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            employeeMapper.setListOfDocPending(documentList);
            employeeMapper.setNoOfDocPending(documentList.size());
            employeeMapper.setReportingManagerName(getEmployeeFullName(employeeDetails.getReportingManager()));
        }
        return employeeMapper;
    }

    @Override
    public EmployeeTableMapper suspendAndUnSuspendUser(String employeeId, boolean suspendImd) {
        EmployeeDetails details = employeeRepository.getEmployeeDetailsByEmployeeIdAndLiveInd(employeeId);
        if (null != details) {

            details.setSuspendInd(suspendImd);
            employeeRepository.save(details);
            //  EmployeeTableMapper employeeMapper = getEmployeeDetail(employeeDetails);
        }
        return getEmployeeDetail(details);
    }

    @Override
    public List<EmployeeTableMapper> getEmployeeDetailsByName(String name) {
        List<EmployeeDetails> detailsList = employeeRepository.findByLiveInd(true);
        List<EmployeeDetails> filterList = detailsList.parallelStream()
                .filter(detail -> {
                    return detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim());
                })
                .collect(Collectors.toList());
        List<EmployeeTableMapper> mapperList = new ArrayList<EmployeeTableMapper>();
        if (null != filterList && !filterList.isEmpty()) {

            mapperList = filterList.parallelStream().map(li -> getEmployeeDetailByEmployeeId(li.getEmployeeId()))
                    .collect(Collectors.toList());
        }
        return mapperList;
    }

    @Override
    public List<EmployeeViewMapper> getEmployeeDetailsBySkill(String skill) {
        List<KeySkillDetails> keySkillDetailsList = keySkillRepository.findBySkillNameContaining(skill);
        if (null != keySkillDetailsList && !keySkillDetailsList.isEmpty()) {
            return keySkillDetailsList.stream().map(keySkillDetails -> {
                EmployeeViewMapper employeeMapper = getEmployeeDetailsByEmployeeId(keySkillDetails.getEmployeeId());
                if (null != employeeMapper) {
                    return employeeMapper;
                }
                return null;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public String saveEmployeeContract(EmployeeContractMapper employeeContractMapper) {

        String id = null;
        if (null != employeeContractMapper.getEmployeeId()) {

            EmployeeContract employeeContract = new EmployeeContract();
            employeeContract.setEmployeeId(employeeContractMapper.getEmployeeId());
            employeeContract.setLiveInd(true);
            if (null != employeeContractMapper.getPreviousStartDate()) {
                try {
                    employeeContract.setPreviousStartDate(
                            Utility.getDateFromISOString(employeeContractMapper.getPreviousStartDate()));
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            if (null != employeeContractMapper.getPreviousEndDate()) {
                try {
                    employeeContract.setPreviousEndDate(
                            Utility.getDateFromISOString(employeeContractMapper.getPreviousEndDate()));
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            /*
             * if(null!=employeeContractMapper.getPresentStartDate()) { try {
             * employeeContract.setPresentStartDate(Utility.getDateFromISOString(
             * employeeContractMapper.getPresentStartDate()));
             *
             * } catch (Exception e1) { // TODO Auto-generated catch block
             * e1.printStackTrace(); } }
             */

            /*
             * if(null!=employeeContractMapper.getPresentEndDate()) { try {
             * employeeContract.setPresentEndDate(Utility.getDateFromISOString(
             * employeeContractMapper.getPresentEndDate())); } catch (Exception e) { // TODO
             * Auto-generated catch block e.printStackTrace(); } }
             */
            if (null != employeeContractMapper.getRenwalDate()) {
                try {
                    employeeContract
                            .setRenwalDate(Utility.getDateFromISOString(employeeContractMapper.getRenwalDate()));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            employeeContract.setContractType(employeeContractMapper.getContractType());
            employeeContract.setNotes(employeeContractMapper.getNotes());
            EmployeeContract contract = employeeContractRepository.save(employeeContract);
            id = contract.getId();
        }
        return id;

    }

    @Override
    public List<EmployeeContractMapper> getEmployeeContract(String employeeId) {
        List<EmployeeContract> contractlList = employeeContractRepository.getEmployeeContractListById(employeeId);
        if (null != contractlList && !contractlList.isEmpty()) {
            return contractlList.stream().map(contract -> {
                EmployeeContractMapper employeeContractMapper = getEmployeeContracts(contract.getId());
                if (null != employeeContractMapper) {
                    return employeeContractMapper;
                }
                return null;
            }).collect(Collectors.toList());

        }

        return null;
    }

    @Override
    public EmployeeContractMapper getEmployeeContracts(String id) {

        EmployeeContract employeeContract = employeeContractRepository.getEmployeeContractById(id);
        EmployeeContractMapper employeeContractMapper = new EmployeeContractMapper();

        if (null != employeeContract) {
            employeeContractMapper.setId(employeeContract.getId());
            employeeContractMapper.setEmployeeId(employeeContract.getEmployeeId());
            employeeContractMapper.setRenwalDate(Utility.getISOFromDate(employeeContract.getRenwalDate()));
            // employeeContractMapper.getPreviousStartDate();
            employeeContractMapper
                    .setPreviousStartDate(Utility.getISOFromDate(employeeContract.getPreviousStartDate()));
            employeeContractMapper.setPreviousEndDate(Utility.getISOFromDate(employeeContract.getPreviousEndDate()));
            // employeeContractMapper.setPresentStartDate(Utility.getISOFromDate(employeeContract.getPresentStartDate()));
            // employeeContractMapper.setPresentEndDate(Utility.getISOFromDate(employeeContract.getPresentEndDate()));
            employeeContractMapper.setContractType(employeeContract.getContractType());
            employeeContractMapper.setNotes(employeeContract.getNotes());

        }
        return employeeContractMapper;

    }

    @Override
    public EmployeeContractMapper updateEmployeeContract(EmployeeContractMapper employeeContractMapper) {
        EmployeeContractMapper resultMapper = null;
        EmployeeContract employeeContracts = employeeContractRepository
                .getEmployeeContractById(employeeContractMapper.getId());
        EmployeeContract newEmployeeContract = new EmployeeContract();
        if (null != employeeContracts) {
            employeeContracts.setLiveInd(false);
            employeeContractRepository.save(employeeContracts);
        }

        if (null != employeeContractMapper.getRenwalDate())
            try {
                newEmployeeContract.setRenwalDate(Utility.getDateFromISOString(employeeContractMapper.getRenwalDate()));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        if (null != employeeContractMapper.getPreviousStartDate())
            try {
                newEmployeeContract.setPreviousStartDate(
                        Utility.getDateFromISOString(employeeContractMapper.getPreviousStartDate()));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        if (null != employeeContractMapper.getPreviousEndDate())
            try {
                newEmployeeContract
                        .setPreviousEndDate(Utility.getDateFromISOString(employeeContractMapper.getPreviousEndDate()));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        /*
         * if(null != employeeContractMapper.getPresentStartDate()) try {
         * newEmployeeContract.setPresentStartDate(Utility.getDateFromISOString(
         * employeeContractMapper.getPresentStartDate())); } catch (Exception e) { //
         * TODO Auto-generated catch block e.printStackTrace(); }
         */

        /*
         * if(null != employeeContractMapper.getPresentEndDate()) try {
         * newEmployeeContract.setPresentEndDate(Utility.getDateFromISOString(
         * employeeContractMapper.getPresentEndDate())); } catch (Exception e) { // TODO
         * Auto-generated catch block e.printStackTrace(); }
         */

        if (null != employeeContractMapper.getContractType()) {
            newEmployeeContract.setContractType(employeeContractMapper.getContractType());
        } else {
            newEmployeeContract.setContractType(employeeContractMapper.getContractType());
        }
        if (null != employeeContractMapper.getNotes()) {
            newEmployeeContract.setNotes(employeeContractMapper.getNotes());
        } else {
            newEmployeeContract.setNotes(employeeContractMapper.getNotes());
        }

        newEmployeeContract.setEmployeeId(employeeContracts.getEmployeeId());
        newEmployeeContract.setLiveInd(true);
        employeeContractRepository.save(newEmployeeContract);

        resultMapper = getEmployeeContracts(newEmployeeContract.getId());

        return resultMapper;
    }

    @Override
    public ByteArrayInputStream exportEmployeeListToExcel(List<EmployeeViewMapper> employeeList) {
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
        for (int i = 0; i < employee_headings.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(employee_headings[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        if (null != employeeList && !employeeList.isEmpty()) {
            for (EmployeeViewMapper employee : employeeList) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(employee.getEmployeeId());

                row.createCell(1).setCellValue(employee.getFirstName());
                row.createCell(2).setCellValue(employee.getMiddleName());
                row.createCell(3).setCellValue(employee.getLastName());
                row.createCell(4).setCellValue(employee.getMobileNo());
                row.createCell(5).setCellValue(employee.getPhoneNo());

            }
        }
        // Resize all columns to fit the content size
        for (int i = 0; i < employee_headings.length; i++) {
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
    public List<EmployeeViewMapper> getEmployeeListByUserId(String userId) {
        List<EmployeeDetails> employeeList = employeeRepository.getEmployeeListByUserId(userId);
        System.out.println("###########" + employeeList);
        if (null != employeeList && !employeeList.isEmpty()) {
            return employeeList.stream().map(employee -> {
                EmployeeViewMapper employeeMapper = getEmployeeDetailsByEmployeeId(employee.getEmployeeId());
                String middleName = " ";
                String lastName = "";

                if (!StringUtils.isEmpty(employee.getLastName())) {

                    lastName = employee.getLastName();
                }

                if (employee.getMiddleName() != null && employee.getMiddleName().length() > 0) {

                    middleName = employee.getMiddleName();
                    employeeMapper.setFullName(employee.getFirstName() + " " + middleName + " " + lastName);
                } else {

                    employeeMapper.setFullName(employee.getFirstName() + " " + lastName);
                }
                return employeeMapper;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<DocumentMapper> getEmployeeDocumentListByEmployeeId(String employeeId) {
        List<DocumentMapper> documentMapperList = new ArrayList<>();
        List<EmployeeDocumentLink> employeeDocumentLinkList = employeeDocumentLinkRepository.getDocumentByEmployeeId(employeeId);
        Set<String> documentIds = employeeDocumentLinkList.stream().map(EmployeeDocumentLink::getDocumentId).collect(Collectors.toSet());
        if (null != documentIds && !documentIds.isEmpty()) {
            return documentIds.stream().map(documentId -> {
                DocumentMapper documentMapper = documentService.getDocument(documentId);
                return documentMapper;
            }).collect(Collectors.toList());
        }

        return documentMapperList;
    }

    @Override

    /*
     * public List<EmployeeViewMapper> getAllrecruiterList() {
     * List<EmployeeViewMapper> resultMapper = new ArrayList<EmployeeViewMapper>();
     *
     * List<EmployeeDetails> employee =
     * employeeRepository.getRecruiterList("Recruiter");
     * System.out.println(" user$$$$$$$$$$$$=="+employee.toString());
     *
     * if (null != employee && !employee.isEmpty()) {
     *
     * for(EmployeeDetails employees : employee) { //List<ContactViewMapper> mp
     * =contactService.getCandidateListByUserId(permissionn.getUserId());
     * List<EmployeeViewMapper> mp
     * =employeeService.getEmployeeListByUserId(employees.getUserId());
     *
     * //System.out.println(" userId$$$$$$$$$$$$=="+permissionn.getUserId());
     *
     * resultMapper.addAll(mp); } }
     *
     * return resultMapper; }
     */
    public List<EmployeeViewMapper> getAllrecruiterList(String orgId) {

        List<EmployeeDetails> list = employeeRepository.getEmployeesByOrgId(orgId);
        List<EmployeeViewMapper> mappList = new ArrayList();
        if (null != list && !list.isEmpty()) {

            List<String> salesIds = list.stream()
                    .map(li -> departmentRepository.getDepartmentDetails(li.getDepartment())).filter(li1 -> li1 != null)
                    .filter(li -> li.getDepartmentName().equalsIgnoreCase("Recruiter")).map(li -> li.getDepartment_id())
                    .collect(Collectors.toList());

            if (null != salesIds && !salesIds.isEmpty()) {

                Set<String> mapperSet = salesIds.stream()
                        .map(li -> employeeRepository.getEmployeeListByDepartmentId(li))
                        .flatMap(li -> li.stream().map(id -> id.getEmployeeId())).collect(Collectors.toSet());

                List<String> idList = new ArrayList<>(mapperSet);
                mappList = idList.stream().map(li -> getEmployeeDetailsByEmployeeId(li)).collect(Collectors.toList());
            }

        }

        return mappList;

    }

    @Override
    public List<EmployeeViewMapper> getAllSalesEmployeeList(String orgIdFromToken) {
        List<EmployeeDetails> list = employeeRepository.getEmployeesByOrgId(orgIdFromToken);
        List<EmployeeViewMapper> mappList = new ArrayList();
        if (null != list && !list.isEmpty()) {

            List<String> salesIds = list.stream().filter(li -> (!StringUtils.isEmpty(li.getDepartment())))
                    .map(li -> departmentRepository.getDepartmentDetails(li.getDepartment())).filter(li1 -> li1 != null)
                    .filter(li -> li.getDepartmentName().equalsIgnoreCase("Sales")).map(li -> li.getDepartment_id())
                    .collect(Collectors.toList());

            if (null != salesIds && !salesIds.isEmpty()) {

                Set<String> mapperSet = salesIds.stream()
                        .map(li -> employeeRepository.getEmployeeListByDepartmentId(li))
                        .flatMap(li -> li.stream().map(id -> id.getEmployeeId())).collect(Collectors.toSet());

                List<String> idList = new ArrayList<>(mapperSet);
                mappList = idList.stream().map(li -> getEmployeeDetailsByEmployeeId(li)).collect(Collectors.toList());
            }

        }

        return mappList;
    }

    @Override
    public boolean getEmployeeDetailsByEmailId(String emailId) {
    	String email = emailId.toLowerCase();
    	System.out.println("email ==LowerCase== "+email);
        EmployeeDetails employeeDetails = employeeRepository.getEmailByMailId(email);
        if (null != employeeDetails) {
            System.out.println("found Email from DB" + employeeDetails.getEmailId() + "of Suspended user");
            return true;
        } else {
            System.out.println("Unsuspended user located");
            return false;
        }

    }

    @Override
    public boolean ActiveUser(String employeeId) {
        EmployeeDetails details = employeeRepository.getEmployeeDetailsByEmployeeId(employeeId);
        if (null != details) {
            details.setSuspendInd(false);
            employeeRepository.save(details);
        }
        return false;
    }

    @Override
    public List<DepartmentMapper> getAllSalesAndManagementList(String orgIdFromToken) {

        List<Department> list = departmentRepository.getAllSalesAndManagementList(orgIdFromToken, "Management",
                "Sales");
        if (null != list && !list.isEmpty()) {
            return list.stream().map(department -> {
                List<EmployeeDetails> list1 = employeeRepository
                        .findByDepartmentAndSuspendIndAndLiveInd(department.getDepartment_id(), false, true);
                return list1.stream().map(employeeDetails -> {
                    DepartmentMapper departmentMappers = new DepartmentMapper();

                    String middleName = " ";
                    String lastName = "";

                    if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                        lastName = employeeDetails.getLastName();
                    }

                    if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

                        middleName = employeeDetails.getMiddleName();
                        departmentMappers
                                .setFullName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
                    } else {

                        departmentMappers.setFullName(employeeDetails.getFirstName() + " " + lastName);
                    }

                    departmentMappers.setEmployeeId(employeeDetails.getEmployeeId());
                    return departmentMappers;
                }).collect(Collectors.toList());
            }).flatMap(l -> l.stream()).collect(Collectors.toList());
        }
        return null;

    }

    @Override
    public List<EmployeeViewMapper> getEmployeeListWhichEmployeeTypeIsEmployee(String employeeType) {
        List<EmployeeDetails> empList = employeeRepository.findByEmployeeType(employeeType);
        System.out.println("@@@@@@@@" + empList);
        if (null != empList && !empList.isEmpty()) {
            return empList.stream().map(employeeDetails -> {
                EmployeeViewMapper employeeMapper = getEmployeeDetails(employeeDetails);
                return employeeMapper;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<EmployeeViewMapper> getEmployeeListWhichIsNotEmployee(String employeeType) {
        List<EmployeeDetails> empList = employeeRepository.findByEmployeeType(employeeType);
        System.out.println("@@@@@@@@" + empList);
        if (null != empList && !empList.isEmpty()) {
            return empList.stream().map(employeeDetails -> {
                EmployeeViewMapper employeeMapper = getEmployeeDetails(employeeDetails);
                return employeeMapper;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public EmployeeViewMapper updateUserTypeByEmployeeId(String employeeId, EmployeeViewMapper employeeViewMapper) {
        EmployeeDetails employeeDetails = employeeRepository.getEmployeesByuserId(employeeId);
        System.out.println("employeeDetails=======" + employeeDetails.toString());

        EmployeeViewMapper mapper = new EmployeeViewMapper();
        if (employeeDetails != null) {
            if (false != employeeViewMapper.isType()) {
                System.out.println("UserType======" + employeeViewMapper.isType());
                employeeDetails.setUserType("External");
                employeeRepository.save(employeeDetails);

            } else {
                System.out.println("UserType======" + employeeViewMapper.isType());
                employeeDetails.setUserType("Internal");
                employeeRepository.save(employeeDetails);
            }
            mapper = employeeService.getEmployeeDetailsByEmployeeId(employeeDetails.getEmployeeId());
        }
        return mapper;
    }

    @Override
    public List<EmployeeViewMapper> getEmployeeListByOrgIdForCustomerCreate(String organizationId) {
        List<EmployeeViewMapper> employeeViewMappers = new ArrayList<>();
        List<DepartmentPermission> departmentPermission = departmentPermissionRepository
                .getDepartmentPermissionByOrganizationId(organizationId);
        if (!departmentPermission.isEmpty()) {
            return departmentPermission.stream().map(departmentPermission2 -> {
                if (null != departmentPermission2.getRoleTypeId() && !departmentPermission2.getRoleTypeId().isEmpty()) {
                    List<EmployeeDetails> employeeDetails = employeeRepository
                            .getActiveEmployeesByOrgIdAndRoleType(organizationId, departmentPermission2.getRoleTypeId());

                    if (null != employeeDetails && !employeeDetails.isEmpty()) {
                        return employeeDetails.stream().map(EmployeeDetails1 -> {
                            EmployeeViewMapper employeeViewMapper = null;
                            employeeViewMapper = getEmployeeDetailsByEmployeeId(EmployeeDetails1.getEmployeeId());
                            employeeViewMappers.add(employeeViewMapper);
                            return employeeViewMapper;
                        }).collect(Collectors.toList());

                    }
                }
//				System.out.println( "department  role type null or Empty "+departmentPermission2.getId());
                return null;
            }).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
        }
//		System.out.println( "department Empty ");
        return employeeViewMappers;
    }

    @Override
    public List<EmployeeViewMapper> getEmployeeListByOrgIdForOpportunityCreate(String organizationId) {
        List<EmployeeViewMapper> employeeViewMappers = new ArrayList<>();
        List<DepartmentPermission> departmentPermission = departmentPermissionRepository
                .getDepartmentPermissionssByOrganizationId(organizationId);
        if (null != departmentPermission && !departmentPermission.isEmpty()) {
            return departmentPermission.stream().map(departmentPermission2 -> {
                if (null != departmentPermission2.getRoleTypeId() && !departmentPermission2.getRoleTypeId().isEmpty()) {
                    List<EmployeeDetails> employeeDetails = employeeRepository
                            .getActiveEmployeesByOrgIdAndRoleType(organizationId, departmentPermission2.getRoleTypeId());

                    if (null != employeeDetails && !employeeDetails.isEmpty()) {
                        return employeeDetails.stream().map(EmployeeDetails1 -> {
                            EmployeeViewMapper employeeViewMapper = null;
                            employeeViewMapper = getEmployeeDetailsByEmployeeId(EmployeeDetails1.getEmployeeId());
                            employeeViewMappers.add(employeeViewMapper);
                            return employeeViewMapper;
                        }).collect(Collectors.toList());

                    }
                }
                return null;
            }).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
        }
        return employeeViewMappers;
    }

    @Override
    public List<EmployeeViewMapper> getEmployeeListByOrgIdForPartnerCreate(String organizationId) {
        List<DepartmentPermission> departmentPermission = departmentPermissionRepository
                .getDepartmentPermissionsByOrganizationId(organizationId);
        if (null != departmentPermission && !departmentPermission.isEmpty()) {
            return departmentPermission.stream().map(departmentPermission2 -> {
                List<EmployeeDetails> employeeDetails = employeeRepository
                        .getEmployeesByDepartmentId(departmentPermission2.getDepartmentId());
                if (null != employeeDetails && !employeeDetails.isEmpty()) {
                    return employeeDetails.stream().map(EmployeeDetails1 -> {
                        EmployeeViewMapper employeeViewMapper = null;
                        employeeViewMapper = getEmployeeDetailsByEmployeeId(EmployeeDetails1.getEmployeeId());
                        return employeeViewMapper;
                    }).collect(Collectors.toList());

                }
                return null;
            }).filter(l -> l != null).flatMap(l -> l.stream()).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public EmployeeViewMapper updateEmployeeRoleUserToAdminByEmployeeId(String employeeId,
                                                                        EmployeeRoleLinkMapper employeeRoleLinkMapper) {

        EmployeeRoleLink employeeRoleLink = employeeRoleLinkRepository.findByEmployeeIdAndLiveInd(employeeId, true);
        if (employeeRoleLink != null) {
            employeeRoleLink.setLiveInd(false);
        } else {
            EmployeeRoleLink EmployeeRoleLink1 = new EmployeeRoleLink();

            EmployeeRoleLink1.setEmployeeId(employeeId);
            if (null != employeeRoleLinkMapper.getProvideDate()) {
                try {
                    EmployeeRoleLink1.setProvideDate(
                            Utility.removeTime(Utility.getDateFromISOString(employeeRoleLinkMapper.getProvideDate())));
                    System.out.println("getDateFromISOString........." + employeeRoleLinkMapper.getProvideDate());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                EmployeeRoleLink1.setProvideDate(Utility.removeTime(Utility.getPlusDate(new Date(), 30)));
            }
            EmployeeRoleLink1.setUpdationDate(new Date());
            EmployeeRoleLink1.setLiveInd(true);
            EmployeeRoleLink1.setOrgId(employeeRoleLinkMapper.getOrgId());
            EmployeeRoleLink1.setRole(employeeRoleLinkMapper.getRole());
            EmployeeRoleLink1.setUserId(employeeRoleLinkMapper.getUserId());
            employeeRoleLinkRepository.save(EmployeeRoleLink1);
        }

        EmployeeDetails employeeDetails = employeeRepository.getEmployeesByuserId(employeeId);
        System.out.println("employeeDetails=======" + employeeDetails.toString());

        EmployeeViewMapper mapper = new EmployeeViewMapper();
        if (employeeDetails != null) {
            if (null != employeeRoleLinkMapper.getRole() && !employeeRoleLinkMapper.getRole().isEmpty()) {
                System.out.println("Role======" + employeeRoleLinkMapper.getRole());
                employeeDetails.setRole(employeeRoleLinkMapper.getRole());
                employeeRepository.save(employeeDetails);

            }

            UserSettings UserSettings = userSettingsRepository.getUserSettingsByEmail(employeeDetails.getEmailId(),
                    true);
            if (UserSettings != null) {
                System.out.println("UserType======" + employeeRoleLinkMapper.getRole());
                UserSettings.setUserType(employeeRoleLinkMapper.getRole());
                userSettingsRepository.save(UserSettings);
            }
            mapper = employeeService.getEmployeeDetailsByEmployeeId(employeeDetails.getEmployeeId());
        }
        return mapper;
    }

    @Override
    public List<EmployeeViewMapper> getEmployeeListByOrgIdForRecruitmentCreate(String organizationId) {
        List<DepartmentPermission> departmentPermission = departmentPermissionRepository
                .getDepartmentPermissionnByOrganizationId(organizationId);
        if (null != departmentPermission && !departmentPermission.isEmpty()) {
            return departmentPermission.stream().map(departmentPermission2 -> {
                List<EmployeeDetails> employeeDetails = employeeRepository
                        .getEmployeesByDepartmentId(departmentPermission2.getDepartmentId());
                if (null != employeeDetails && !employeeDetails.isEmpty()) {
                    return employeeDetails.stream().map(EmployeeDetails1 -> {
                        EmployeeViewMapper employeeViewMapper = null;
                        employeeViewMapper = getEmployeeDetailsByEmployeeId(EmployeeDetails1.getEmployeeId());
                        return employeeViewMapper;
                    }).collect(Collectors.toList());

                }
                return null;
            }).flatMap(l -> l.stream()).collect(Collectors.toList());
        }
        return null;
    }

    @Scheduled(cron = "0 41 0 * * *")
    public void convertTempAdminToUser() {
        System.out.println("skb schedular started");
        List<EmployeeRoleLink> employeeRoleLink = employeeRoleLinkRepository.findByLiveInd(true);
        System.out.println("list..............." + employeeRoleLink.toString());
        if (null != employeeRoleLink && !employeeRoleLink.isEmpty()) {
            for (EmployeeRoleLink employeeRoleLink2 : employeeRoleLink) {
                Date empDate = Utility.removeTime(employeeRoleLink2.getProvideDate());
                Date currentDate = Utility.removeTime(new Date());
                System.out.println("foreachloop,,,,,,,,,,,,,,,,,,,,,," + employeeRoleLink2.getEmployeeId());
                System.out.println(empDate + "|1|" + currentDate);
                if (empDate.equals(currentDate)) {
                    System.out.println(empDate + "|2|" + currentDate);
                    employeeRoleLink2.setLiveInd(false);
                    employeeRoleLinkRepository.save(employeeRoleLink2);

                    EmployeeDetails employeeDetails = employeeRepository
                            .getEmployeesByuserId(employeeRoleLink2.getEmployeeId());
                    if (employeeDetails != null) {
                        employeeDetails.setRole("USER");
                        employeeRepository.save(employeeDetails);
                    }

                    UserSettings UserSettings = userSettingsRepository
                            .getUserSettingsByEmail(employeeDetails.getEmailId(), true);
                    System.out.println("UserSettings//////////////////" + UserSettings.toString());
                    if (UserSettings != null) {
                        UserSettings.setUserType("USER");
                        userSettingsRepository.save(UserSettings);
                    }
                }
            }
        }
    }

    @Override
    public HashMap getActiveEmployeeListByUserIdAndLiveInd(String OrgId) {
        List<EmployeeDetails> employeeDetailsList = employeeRepository.getEmployeesByOrgId(OrgId);
        HashMap map = new HashMap();
        map.put("EmployeeListByLiveInd", employeeDetailsList.size());

        return map;
    }

    @Override
    public HashMap getAllEmployeeListByUserIdAndLiveInd(String OrgId) {
        List<EmployeeDetails> employeeDetailsList = employeeRepository.getEmployeesByOrgIdAndLiveInd(OrgId);
        HashMap map = new HashMap();
        map.put("EmployeeListByLiveInd", employeeDetailsList.size());

        return map;
    }

    @Override
    public List<EmployeeViewMapper> getEmployeeListWhoseDesignationManagerByOrgId(String orgId) {
        String designation = "Manager";

        Designation list = designationRepository.findByOrgIdAndDesignationTypeContainingAndLiveInd(orgId, designation,
                true);

        if (null != list) {
            List<EmployeeDetails> employeeDetails = employeeRepository
                    .getEmployeesByOrgIdAndDesignation(list.getDesignationTypeId(), orgId);
            if (null != employeeDetails && !employeeDetails.isEmpty()) {
                return employeeDetails.stream().map(employeeDetails2 -> {
                    EmployeeViewMapper employeeViewMapper1 = getEmployeeDetailsByEmployeeId(
                            employeeDetails2.getEmployeeId());
                    if (null != employeeViewMapper1) {
                        return employeeViewMapper1;
                    }
                    return null;
                }).collect(Collectors.toList());
            }

        }

        return null;
    }

    @Override
    public String getEmployeeFullName(String employeeId) {
        String fullName = "";

        EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(employeeId, true);
        if (null != employeeDetails) {
            String middleName = " ";
            String lastName = "";

            if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                lastName = employeeDetails.getLastName();
            }

            if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

                middleName = employeeDetails.getMiddleName();
                fullName = (employeeDetails.getFirstName() + " " + middleName + " " + lastName);
            } else {

                fullName = (employeeDetails.getFirstName() + " " + lastName);
            }
        }
        return fullName;
    }

    @Override
    public String getEmployeeFullNameByObject(EmployeeDetails employeeDetails) {
        String fullName = "";
        if (null != employeeDetails) {
            String middleName = " ";
            String lastName = "";

            if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                lastName = employeeDetails.getLastName();
            }

            if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

                middleName = employeeDetails.getMiddleName();
                fullName = (employeeDetails.getFirstName() + " " + middleName + " " + lastName);
            } else {

                fullName = (employeeDetails.getFirstName() + " " + lastName);
            }
        }
        return fullName;
    }

    @Override
    public boolean checkSkillInEmployeeSkillSet(String keySkillsName, String employeeId) {
        List<DefinationDetails> definationDetails = definationRepository.findByNameContaining(keySkillsName);
        for (DefinationDetails definationDetails1 : definationDetails) {
            if (null != definationDetails1) {
                List<KeySkillDetails> skillsets = keySkillRepository
                        .findBySkillNameAndEmployeeId(definationDetails1.getDefinationId(), employeeId);
                if (!skillsets.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public KeyskillsMapper updateEmployeeSkill(String keySkillId, KeyskillsMapper skillSetMapper) {
        KeySkillDetails skill = keySkillRepository.getById(keySkillId);
        // CandidateMapper candidateMapper1 = new CandidateMapper();
        if (null != skill) {
            // skill.setSkillName(skillSetMapper.getSkillName());
            skill.setExperience(skillSetMapper.getExperience());
            keySkillRepository.save(skill);
        }
        KeyskillsMapper result = getKeyskillsDetails(keySkillId);
        return result;
    }

    @Override
    public KeyskillsMapper updateEmployeeSkillRoleBySkillSetDetailsId(String keySkillId,
                                                                      KeyskillsMapper skillSetMapper) {
        KeySkillDetails skillSetDetails = keySkillRepository.getEmployeeSkillByKeySkillId(keySkillId);

        KeyskillsMapper skillSetMapperr = new KeyskillsMapper();
        if (skillSetDetails != null) {
            if (null != skillSetMapper.getSkillRole() && !skillSetMapper.getSkillRole().isEmpty()) {
                System.out.println("SkillRole======" + skillSetMapper.getSkillRole());
                skillSetDetails.setSkillRole(skillSetMapper.getSkillRole());
                keySkillRepository.save(skillSetDetails);
            }
            skillSetMapperr = getKeyskillsDetails(skillSetDetails.getId());
        }
        return skillSetMapperr;
    }

    @Override
    public KeyskillsMapper pauseAndUnpauseEmployeeSkillExperience(String keySkillId, KeyskillsMapper skillSetMapper) {
        KeyskillsMapper result = new KeyskillsMapper();
        KeySkillDetails skill = keySkillRepository.getById(keySkillId);
        if (null != skill) {
            if (skillSetMapper.isPauseInd() == true) {
                skill.setPauseInd(skillSetMapper.isPauseInd());
                skill.setPauseDate(new Date());
                skill.setPauseExperience(getExprience(skill.getCreationDate()) + skill.getExperience());
                keySkillRepository.save(skill);
            } else {
                skill.setPauseInd(skillSetMapper.isPauseInd());
                skill.setUnpauseDate(new Date());
                keySkillRepository.save(skill);
            }
            result = getKeyskillsDetails(keySkillId);
        }

        return result;
    }

    @Override
    public EmployeeTreeMapper getEmployeeTreeByEmployeeId(String employeeId) {
        EmployeeTreeMapper parentmapper = new EmployeeTreeMapper();
        List<EmployeeEducationDetailsTreeMapper> childList = new ArrayList<>();
        List<EmployeeCommonMapper> commonMapper = new ArrayList<>();
        List<EmployeeCommonMapper> commonMapper2 = new ArrayList<>();
        List<EmployeeCommonMapper> commonMapper3 = new ArrayList<>();
        List<EmployeeCommonMapper> commonMapper4 = new ArrayList<>();
        //  List<EmployeeCommonMapper> commonMapper5 = new ArrayList<>();

        EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(employeeId);
        if (null != employeeDetails) {
            String middleName = " ";
            String lastName = "";
            String satutation = "";

            if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                lastName = employeeDetails.getLastName();
            }
            if (employeeDetails.getSalutation() != null && employeeDetails.getSalutation().length() > 0) {
                satutation = employeeDetails.getSalutation();
            }

            if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

                middleName = employeeDetails.getMiddleName();
                parentmapper.setName(satutation + " " + employeeDetails.getFirstName() + " " + middleName + " " + lastName);
            } else {

                parentmapper.setName(satutation + " " + employeeDetails.getFirstName() + " " + lastName);
            }

            List<EducationalDetails> educationList = educationalRepository.getEducationDetailsById(employeeId);
            if (null != educationList && !educationList.isEmpty()) {
                EmployeeEducationDetailsTreeMapper mainChildren = new EmployeeEducationDetailsTreeMapper();
                mainChildren.setName("Education");
                for (EducationalDetails educationDetails : educationList) {
                    EmployeeCommonMapper mapper = new EmployeeCommonMapper();
                    mapper.setName(educationDetails.getCourseName());
                    commonMapper.add(mapper);
                }
                mainChildren.setChildren(commonMapper);
                childList.add(mainChildren);
            }

            List<TrainingDetails> trainingList = trainingRepository.getEmployeeTrainingDetailsByEmployeeId(employeeId);
            if (null != trainingList && !trainingList.isEmpty()) {
                EmployeeEducationDetailsTreeMapper mainChildren = new EmployeeEducationDetailsTreeMapper();
                mainChildren.setName("Training");
                for (TrainingDetails trainingDetails : trainingList) {
                    EmployeeCommonMapper mapper = new EmployeeCommonMapper();
                    mapper.setName(trainingDetails.getCourseName());
                    commonMapper2.add(mapper);
                }
                mainChildren.setChildren(commonMapper2);
                childList.add(mainChildren);
            }

            List<EmployementHistory> employementHistory = employementHistoryRepository.getEmploymentHistoryByEmployeeId(employeeId);
            if (null != employementHistory && !employementHistory.isEmpty()) {
                EmployeeEducationDetailsTreeMapper mainChildren = new EmployeeEducationDetailsTreeMapper();
                mainChildren.setName("EmployeeHistory");
                for (EmployementHistory employementHistoryDetails : employementHistory) {
                    EmployeeCommonMapper mapper = new EmployeeCommonMapper();
                    Designation dDesignation = designationRepository
                            .findByDesignationTypeId(employementHistoryDetails.getDesignation());

                    if (null != dDesignation) {
                        mapper.setName(dDesignation.getDesignationType());
                    }
                    commonMapper3.add(mapper);
                }
                mainChildren.setChildren(commonMapper3);
                childList.add(mainChildren);
            }

            List<BankDetails> bankDetails = bankRepository.getBankDetailsById(employeeId);
            if (null != bankDetails && !bankDetails.isEmpty()) {
                EmployeeEducationDetailsTreeMapper mainChildren = new EmployeeEducationDetailsTreeMapper();
                mainChildren.setName("BankDetails");
                for (BankDetails bankDetail : bankDetails) {
                    EmployeeCommonMapper mapper = new EmployeeCommonMapper();
                    mapper.setName(bankDetail.getBankName());
                    commonMapper4.add(mapper);
                }
                mainChildren.setChildren(commonMapper4);
                childList.add(mainChildren);
            }
            parentmapper.setChildren(childList);
        }
        return parentmapper;
    }

    @Override
    public List<EmployeeViewMapper> getEmployeeListByOrgIdForLeadsCreate(String organizationId) {
        List<EmployeeViewMapper> employeeViewMappers = new ArrayList<>();
        List<DepartmentPermission> departmentPermission = departmentPermissionRepository
                .getByOrganizationIdAndLeadsCreateInd(organizationId);
        if (null != departmentPermission && !departmentPermission.isEmpty()) {
            return departmentPermission.stream().map(departmentPermission2 -> {
                if (null != departmentPermission2.getRoleTypeId() && !departmentPermission2.getRoleTypeId().isEmpty()) {
                    List<EmployeeDetails> employeeDetails = employeeRepository
                            .getActiveEmployeesByOrgIdAndRoleType(organizationId, departmentPermission2.getRoleTypeId());

                    if (null != employeeDetails && !employeeDetails.isEmpty()) {
                        return employeeDetails.stream().map(EmployeeDetails1 -> {
                            EmployeeViewMapper employeeViewMapper = null;
                            employeeViewMapper = getEmployeeDetailsByEmployeeId(EmployeeDetails1.getEmployeeId());
                            employeeViewMappers.add(employeeViewMapper);
                            return employeeViewMapper;
                        }).collect(Collectors.toList());

                    }
                }
                return null;
            }).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
        }
        return employeeViewMappers;
    }

    @Override
    public List<EmployeeViewMapper> getEmployeeListByOrgIdForContactCreate(String organizationId) {
        List<EmployeeViewMapper> employeeViewMappers = new ArrayList<>();
        List<DepartmentPermission> departmentPermission = departmentPermissionRepository
                .getDepartmentPermissionsByOrganizationIdAndContactCreateInd(organizationId);
        if (null != departmentPermission && !departmentPermission.isEmpty()) {
            return departmentPermission.stream().map(departmentPermission2 -> {
                if (null != departmentPermission2.getRoleTypeId() && !departmentPermission2.getRoleTypeId().isEmpty()) {
                    List<EmployeeDetails> employeeDetails = employeeRepository
                            .getActiveEmployeesByOrgIdAndRoleType(organizationId, departmentPermission2.getRoleTypeId());

                    if (null != employeeDetails && !employeeDetails.isEmpty()) {
                        return employeeDetails.stream().map(EmployeeDetails1 -> {
                            EmployeeViewMapper employeeViewMapper = null;
                            employeeViewMapper = getEmployeeDetailsByEmployeeId(EmployeeDetails1.getEmployeeId());
                            employeeViewMappers.add(employeeViewMapper);
                            return employeeViewMapper;
                        }).collect(Collectors.toList());

                    }
                }
                return null;
            }).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
        }
        return employeeViewMappers;
    }

    @Override
    public List<Map<String, List<EmployeeViewMapper>>> getEmployeesByDepartmentWiseJoiningDate(String orgId, String startDate, String endDate) {
        List<Map<String, List<EmployeeViewMapper>>> mapList = new ArrayList<>();
        Date start_date = null;
        Date end_date = null;
        try {
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Department> departmentList = departmentRepository.getDepartmentListByOrgId(orgId);
        for (Department department : departmentList) {
            Map<String, List<EmployeeViewMapper>> map = new HashMap<>();
            List<EmployeeViewMapper> list = new ArrayList<>();
            List<EmployeeDetails> employeeDetails = employeeRepository.findByDepartmentAndSuspendIndAndLiveIndAndJoiningDate(department.getDepartment_id(), start_date, end_date);
            for (EmployeeDetails details : employeeDetails) {
                EmployeeViewMapper employeeMapper = employeeService.getEmployeeDetails(details);
                list.add(employeeMapper);
            }
            map.put(department.getDepartmentName(), list);
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public EmployeeShortMapper getEmployeeFullNameAndEmployeeId(String employeeId) {
        EmployeeShortMapper employeeShortMapper = new EmployeeShortMapper();
        //String fullName = "";

        EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(employeeId, true);
        if (null != employeeDetails) {
            String middleName = " ";
            String lastName = "";

            if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                lastName = employeeDetails.getLastName();
            }

            if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

                middleName = employeeDetails.getMiddleName();
                employeeShortMapper.setEmpName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
            } else {

                employeeShortMapper.setEmpName(employeeDetails.getFirstName() + " " + lastName);
            }
            employeeShortMapper.setEmployeeId(employeeDetails.getEmployeeId());
        }
        return employeeShortMapper;
    }

    @Override
    public List<Map> getEmployeesCountByDepartmentWiseJoiningDate(String orgId, String startDate, String endDate) {
        List<Map> mapList = new ArrayList<>();
        Date start_date = null;
        Date end_date = null;
        try {
            start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
            end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Department> departmentList = departmentRepository.getDepartmentListByOrgId(orgId);
        for (Department department : departmentList) {
            Map map = new HashMap<>();
            List<EmployeeDetails> employeeDetails = employeeRepository.findByDepartmentAndSuspendIndAndLiveIndAndJoiningDate(department.getDepartment_id(), start_date, end_date);
            map.put("department", department.getDepartmentName());
            map.put("empCount", employeeDetails.size());
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public List<EmployeeShortMapper> getEmployeeListByOrgIdForDropDown(String orgId) {
        List<EmployeeShortMapper> list = new ArrayList<>();
        List<EmployeeDetails> employeeList = employeeRepository.getEmployeesByOrgId(orgId);
        System.out.println("###########" + employeeList.toString());
        if (null != employeeList && !employeeList.isEmpty()) {
            return employeeList.stream().map(employee -> {
                EmployeeShortMapper employeeMapper = getEmployeeFullNameAndEmployeeId(employee.getEmployeeId());
                return employeeMapper;
            }).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public VisaMapper saveVisa(VisaMapper visaMapper) {
        String visaId = null;
        if (visaMapper != null) {
            Visa visa = new Visa();
            visa.setCreationDate(new Date());
            visa.setCountry(visaMapper.getCountry());
            visa.setDocumentId(visaMapper.getDocumentId());
            visa.setLiveInd(true);
            visa.setMultipleEntryInd(visaMapper.isMultipleEntryInd());
            visa.setOrgId(visaMapper.getOrgId());
            visa.setType(visaMapper.getType());
            visa.setUserId(visaMapper.getUserId());
            visa.setDocumentType(visaMapper.getDocumentType());
            try {
                visa.setStartDate(Utility.getDateFromISOString(visaMapper.getStartDate()));
                visa.setEndDate(Utility.getDateFromISOString(visaMapper.getEndDate()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            visaId = visaRepository.save(visa).getVisaId();

        }
        VisaMapper resultMapper = getVisaDetailsVisaId(visaId);
        return resultMapper;
    }

    @Override
    public VisaMapper getVisaDetailsVisaId(String visaId) {

        Visa visa = visaRepository.findByVisaId(visaId);
        VisaMapper resultMapper = new VisaMapper();

        if (null != visa) {
            resultMapper.setVisaId(visaId);
            resultMapper.setCreationDate(Utility.getISOFromDate(visa.getCreationDate()));
            resultMapper.setCountry(visa.getCountry());
            resultMapper.setDocumentId(visa.getDocumentId());
            resultMapper.setLiveInd(visa.isLiveInd());
            resultMapper.setMultipleEntryInd(visa.isMultipleEntryInd());
            resultMapper.setOrgId(visa.getOrgId());
            resultMapper.setType(visa.getType());
            resultMapper.setUserId(visa.getUserId());
            resultMapper.setEndDate(Utility.getISOFromDate(visa.getEndDate()));
            resultMapper.setStartDate(Utility.getISOFromDate(visa.getStartDate()));
            if (!StringUtils.isEmpty(visa.getDocumentType())) {
                DocumentType documentType = documentTypeRepository.getTypeDetails(visa.getDocumentType());
                if (null != documentType) {
                    resultMapper.setDocumentType(documentType.getDocumentTypeName());
                }
            }
        }

        return resultMapper;
    }

    @Override
    public List<VisaMapper> getVisaByUserId(String userId) {

        List<VisaMapper> resultMapper = new ArrayList<>();
        List<Visa> list = visaRepository.findByUserIdAndLiveInd(userId, true);
        if (null != list) {
            resultMapper = list.stream().map(li -> getVisaDetailsVisaId(li.getVisaId()))
                    .collect(Collectors.toList());
        }
        return resultMapper;
    }

    @Override
    public VisaMapper updateVisa(String visaId, VisaMapper visaMapper) {
        Visa visa = visaRepository.getById(visaId);
        if (visa != null) {

            visa.setCreationDate(new Date());
            visa.setCountry(visaMapper.getCountry());
            visa.setDocumentId(visaMapper.getDocumentId());
            visa.setLiveInd(true);
            visa.setMultipleEntryInd(visaMapper.isMultipleEntryInd());
            visa.setOrgId(visaMapper.getOrgId());
            visa.setType(visaMapper.getType());
            visa.setUserId(visaMapper.getUserId());
            try {
                visa.setStartDate(Utility.getDateFromISOString(visaMapper.getStartDate()));
                visa.setEndDate(Utility.getDateFromISOString(visaMapper.getEndDate()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null != visaMapper.getDocumentType())
                visa.setDocumentType(visaMapper.getDocumentType());
            visaId = visaRepository.save(visa).getVisaId();

        }
        VisaMapper resultMapper = getVisaDetailsVisaId(visaId);
        return resultMapper;
    }

    @Override
    public void deleteVisa(String visaId) {

        if (null != visaId) {
            Visa visa = visaRepository.findByVisaId(visaId);
            visa.setLiveInd(false);
            visaRepository.save(visa);
        }
    }

    @Override
    public Map getPendingDocListByUserId(String userId, String orgId) {
        Map map = new HashMap<>();

        List<DocumentType> documentType = documentTypeRepository.getDocumentTypeListByOrgIdAndUserTypeWithLiveIndAndMandatoryInd(orgId, "User");
        List<String> documentList = new ArrayList<>();
        if (null != documentType && !documentType.isEmpty()) {
            for (DocumentType documentType2 : documentType) {
                documentList.add(documentType2.getDocumentTypeName());
            }
            for (DocumentType documentType1 : documentType) {
                System.out.println("User Id =====-=----inner3-----=--///" + userId);
                if (null != documentType1.getDocument_type_id()) {
                    List<EmployeeDocumentLink> employeeDocumentLink = employeeDocumentLinkRepository.
                            getDocumentByEmployeeId(userId);
                    if (null != employeeDocumentLink && !employeeDocumentLink.isEmpty()) {
                        for (EmployeeDocumentLink employeeDocumentLink1 : employeeDocumentLink) {
                            DocumentDetails documentDetails = documentDetailsRepository.getDocumentDetailsById(employeeDocumentLink1.getDocumentId());
                            if (null != documentDetails) {
                                if (null != documentDetails.getDocument_type()) {
                                    if (documentDetails.getDocument_type().equalsIgnoreCase(documentType1.getDocument_type_id())) {
                                        documentList.remove(documentType1.getDocumentTypeName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        map.put("noOfDocPending", documentList.size());
        map.put("ListOfDocPending", documentList);

        return map;
    }

    @Override
    public List<EmployeeShortMapper> getEmployeeListWhoseErpIndTrueForDropDown(String orgId) {

        List<EmployeeShortMapper> resultList = new ArrayList<>();

        List<Department> departmentList = departmentRepository.getDepartmentListByOrgIdAndErpInd(orgId, true);
        if (null != departmentList && !departmentList.isEmpty()) {
            for (Department department : departmentList) {
                List<EmployeeDetails> employeeList = employeeRepository.getEmployeeListByDepartmentId(department.getDepartment_id());
                System.out.println("###########" + employeeList.toString());
                if (null != employeeList && !employeeList.isEmpty()) {
                    employeeList.stream().map(employee -> {
                        EmployeeShortMapper employeeMapper = getEmployeeFullNameAndEmployeeId(employee.getEmployeeId());
                        resultList.add(employeeMapper);
                        return employeeMapper;
                    }).collect(Collectors.toList());
                }
            }
        }
        return resultList;
    }

    @Override
    public List<EmployeeShortMapper> getEmployeeListWhoseCrmIndTrueForDropDown(String orgId) {
        List<EmployeeShortMapper> resultList = new ArrayList<>();

        List<Department> departmentList = departmentRepository.getDepartmentListByOrgIdAndCrmInd(orgId, true);
        if (null != departmentList && !departmentList.isEmpty()) {
            for (Department department : departmentList) {
                List<EmployeeDetails> employeeList = employeeRepository.getEmployeeListByDepartmentId(department.getDepartment_id());
                System.out.println("###########" + employeeList.toString());
                if (null != employeeList && !employeeList.isEmpty()) {
                    employeeList.stream().map(employee -> {
                        EmployeeShortMapper employeeMapper = getEmployeeFullNameAndEmployeeId(employee.getEmployeeId());
                        resultList.add(employeeMapper);
                        return employeeMapper;
                    }).collect(Collectors.toList());
                }
            }
        }
        return resultList;
    }

    @Override
    public EmployeeTableMapper updateEmployeeByEmployeeId(EmployeeMapper employeeMapper, String employeeId) {
        EmployeeTableMapper resultMapper = new EmployeeTableMapper();
        EmployeeDetails employeeDetails = employeeRepository
                .getEmployeeDetailsByEmployeeId(employeeId, true);
        System.out.println("get employeedetails in employeedetails able" + employeeDetails);

        if (null != employeeDetails) {

            if (null != employeeMapper.getCountry())
                employeeDetails.setCountry(employeeMapper.getCountry());

            if (null != employeeMapper.getCountryDialCode())
                employeeDetails.setCountryDialCode(employeeMapper.getCountryDialCode());

            if (null != employeeMapper.getCountryDialCode1())
                employeeDetails.setCountryDialCode1(employeeMapper.getCountryDialCode1());

            if (null != employeeMapper.getCurrency())
                employeeDetails.setCurrency(employeeMapper.getCurrency());

            if (null != employeeMapper.getDateOfJoining())
                try {
                    employeeDetails.setDateOfJoining(Utility.getDateFromISOString(employeeMapper.getDateOfJoining()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            if (null != employeeMapper.getDepartmentId())
                employeeDetails.setDepartment(employeeMapper.getDepartmentId());

            if (null != employeeMapper.getDesignationTypeId())
                employeeDetails.setDesignation(employeeMapper.getDesignationTypeId());

            if (null != employeeMapper.getDob())
                try {
                    employeeDetails.setDob(Utility.getDateFromISOString(employeeMapper.getDob()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            if (null != employeeMapper.getEmailId()) {
                employeeDetails.setEmailId(employeeMapper.getEmailId().toLowerCase());

                UserSettings userSeting = userSettingsRepository.getUserSettingsByUserId(employeeId);
                if (null != userSeting) {
                    userSeting.setEmail(employeeMapper.getEmailId().toLowerCase());
                }

            }

            if (null != employeeMapper.getEmployeeType())
                employeeDetails.setEmployeeType(employeeMapper.getEmployeeType());

            if (null != employeeMapper.getFacebook())
                employeeDetails.setFacebook(employeeMapper.getFacebook());

            if (null != employeeMapper.getFax()) {
                employeeDetails.setFax(employeeMapper.getFax());
            }

            if (null != employeeMapper.getSkypeId()) {
                employeeDetails.setSkypeId(employeeMapper.getSkypeId());
            }

            if (null != employeeMapper.getLinkedinPublicUrl()) {
                employeeDetails.setLinkedinPublicUrl(employeeMapper.getLinkedinPublicUrl());
            }

            if (null != employeeMapper.getOrganizationId()) {
                employeeDetails.setOrgId(employeeMapper.getOrganizationId());
            }

            if (null != employeeMapper.getUserId()) {
                employeeDetails.setUserId(employeeMapper.getUserId());
            }

            if (null != employeeMapper.getSalutation()) {
                employeeDetails.setSalutation(employeeMapper.getSalutation());
            }

            if (null != employeeMapper.getFirstName()) {
                employeeDetails.setFirstName(employeeMapper.getFirstName());
            }

            if (null != employeeMapper.getLastName()) {
                employeeDetails.setLastName(employeeMapper.getLastName());
            }

            if (null != employeeMapper.getMiddleName()) {
                employeeDetails.setMiddleName(employeeMapper.getMiddleName());
            }

            if (null != employeeMapper.getJobType())
                employeeDetails.setJobType(employeeMapper.getJobType());

            if (null != employeeMapper.getLabel())
                employeeDetails.setLabel(employeeMapper.getLabel());

            if (null != employeeMapper.getPreferedLanguage())
                employeeDetails.setLanguage(employeeMapper.getPreferedLanguage());

            if (null != employeeMapper.getMobileNo()) {
                employeeDetails.setMobileNo(employeeMapper.getMobileNo());
            }

            if (null != employeeMapper.getPhoneNo()) {
                employeeDetails.setPhoneNo(employeeMapper.getPhoneNo());
            }

            if (null != employeeMapper.getStatus())
                employeeDetails.setStatus(employeeMapper.getStatus());

            if (null != employeeMapper.getTimeZone()) {
                employeeDetails.setTimeZone(employeeMapper.getTimeZone());
            }

            if (null != employeeMapper.getLocation()) {
                employeeDetails.setLocation(employeeMapper.getLocation());
            }

            if (!employeeMapper.getLocation().equalsIgnoreCase(employeeDetails.getLocation())) {
                CellChamberUserLink ccu = cellChamberUserLinkRepository
                        .findByUserAndActive(employeeId, true);
                if (null != ccu) {

                    ccu.setActive(false);
                    ccu.setModifiedAt(new Date());
                    ccu.setModifiedBy(employeeId);
                    cellChamberUserLinkRepository.save(ccu);
                }
            }

            if (null != employeeMapper.getImageId())
                employeeDetails.setImageId(employeeMapper.getImageId());

            if (null != employeeMapper.getBloodGroup())
                employeeDetails.setBloodGroup(employeeMapper.getBloodGroup());

            if (null != employeeMapper.getWorkplace())
                employeeDetails.setWorkplace(employeeMapper.getWorkplace());

            if (null != employeeMapper.getEmployeeType())
                employeeDetails.setEmployeeType(employeeMapper.getEmployeeType());

            if (null != employeeMapper.getRoleType())
                employeeDetails.setRoleType(employeeMapper.getRoleType());

            if (null != employeeMapper.getUserType())
                employeeDetails.setUserType(employeeMapper.getUserType());

            if (null != employeeMapper.getJobType())
                employeeDetails.setJobType(employeeMapper.getJobType());

            if (null != employeeMapper.getTwitter())
                employeeDetails.setTwitter(employeeMapper.getTwitter());

            if (null != employeeMapper.getRole())
                employeeDetails.setRole(employeeMapper.getRole());

            if (null != employeeMapper.getGender())
                employeeDetails.setGender(employeeMapper.getGender());

            if (null != employeeMapper.getReportingManager())
                employeeDetails.setReportingManager(employeeMapper.getReportingManager());

            if (null != employeeMapper.getReportingManagerDeptId())
                employeeDetails.setReportingManagerDept(employeeMapper.getReportingManagerDeptId());

            if (0 != employeeMapper.getSalary())
                employeeDetails.setSalary(employeeMapper.getSalary());

            if (null != employeeMapper.getServiceLineId())
                employeeDetails.setServiceLine(employeeMapper.getServiceLineId());

            if (null != employeeMapper.getSecondaryReptManagerDept())
                employeeDetails.setSecondaryReptManagerDept(employeeMapper.getSecondaryReptManagerDept());

            if (null != employeeMapper.getSecondaryReptManager())
                employeeDetails.setSecondaryReptManager(employeeMapper.getSecondaryReptManager());

            if (null != employeeMapper.getFirstName()) {
                String middleName = "";
                String lastName = "";

                if (employeeMapper.getLastName() != null) {

                    lastName = employeeMapper.getLastName();
                }
                if (employeeMapper.getMiddleName() != null && employeeMapper.getMiddleName().length() > 0) {

                    middleName = employeeMapper.getMiddleName();
                    employeeDetails.setFullName(employeeMapper.getFirstName() + " " + middleName + " " + lastName);
                } else {

                    employeeDetails.setFullName(employeeMapper.getFirstName() + " " + lastName);
                }
            } else {
                employeeDetails.setFullName(employeeDetails.getFullName());
            }

            if (null != employeeMapper.getAddress()) {
                List<AddressMapper> addressList = employeeMapper.getAddress();

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
                    } else {

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
                            System.out.println("AddressId::" + addressRepository.save(newAddressDetailss).getAddressId());

                            EmployeeAddressLink employeeAddressLink = new EmployeeAddressLink();
                            employeeAddressLink.setEmployeeId(employeeId);
                            employeeAddressLink.setAddressId(addressId);
                            employeeAddressLink.setCreationDate(new Date());
                            employeeAddressLink.setLiveInd(true);

                            employeeAddressLinkRepository.save(employeeAddressLink);
                        }
                    }
                }
            }

            employeeDetails.setModificationDate(new Date());
            employeeDetails.setModificationBy(employeeMapper.getUserId());
            EmployeeDetails details = employeeRepository.save(employeeDetails);

//            String msg = employeeMapper.getFirstName() + " employee updated";
//    		notificationService.createNotificationForAll(employeeMapper.getOrganizationId(), employeeMapper.getUserId(),"user update", msg,"user","update");
            resultMapper = getEmployeeDetail(details);
            System.out.println("get employee details" + resultMapper);
        }

        return resultMapper;
    }

    @Override
    public List<EmployeeShortMapper> getEmployeeListByLocationIdWhoseDepartmentProductionForDropDown(String departmentId, String locationId, String userId) {
        List<EmployeeShortMapper> resultList = new ArrayList<>();

        DefaultDepartment defaultDepartment = defaultDepartmentRepository.findByUserId(userId);
        if (null != defaultDepartment) {
            if (defaultDepartment.getDepartmentId().equalsIgnoreCase(departmentId)) {
                List<EmployeeDetails> employeeList = employeeRepository.getEmployeeListByDepartmentIdAndLocationId(defaultDepartment.getDepartmentId(), locationId);
                System.out.println("###########" + employeeList.toString());
                if (null != employeeList && !employeeList.isEmpty()) {
                    employeeList.stream().map(employee -> {
                        EmployeeShortMapper employeeMapper = getEmployeeFullNameAndEmployeeId(employee.getEmployeeId());
                        resultList.add(employeeMapper);
                        return employeeMapper;
                    }).collect(Collectors.toList());
                }
            } else {
                defaultDepartment.setDepartmentId(departmentId);
                defaultDepartment.setCreationDate(new Date());
                defaultDepartment.setUserId(userId);
                defaultDepartment.setLiveInd(true);
                defaultDepartmentRepository.save(defaultDepartment);

                List<EmployeeDetails> employeeList1 = employeeRepository.getEmployeeListByDepartmentIdAndLocationId(defaultDepartment.getDepartmentId(), locationId);
                System.out.println("###########" + employeeList1.toString());
                if (null != employeeList1 && !employeeList1.isEmpty()) {
                    employeeList1.stream().map(employee -> {
                        EmployeeShortMapper employeeMapper = getEmployeeFullNameAndEmployeeId(employee.getEmployeeId());
                        resultList.add(employeeMapper);
                        return employeeMapper;
                    }).collect(Collectors.toList());
                }
            }
        } else {
            DefaultDepartment defaultDepartment1 = new DefaultDepartment();
            defaultDepartment1.setCreationDate(new Date());
            defaultDepartment1.setDepartmentId(departmentId);
            defaultDepartment1.setUserId(userId);
            defaultDepartment1.setLiveInd(true);
            defaultDepartmentRepository.save(defaultDepartment1);

            List<EmployeeDetails> employeeList = employeeRepository.getEmployeeListByDepartmentIdAndLocationId(departmentId, locationId);
            System.out.println("###########" + employeeList.toString());
            if (null != employeeList && !employeeList.isEmpty()) {
                employeeList.stream().map(employee -> {
                    EmployeeShortMapper employeeMapper = getEmployeeFullNameAndEmployeeId(employee.getEmployeeId());
                    resultList.add(employeeMapper);
                    return employeeMapper;
                }).collect(Collectors.toList());
            }
        }

        return resultList;
    }

    @Override
    public List<EmployeeTableMapper> getEmployeeListByLocationId(String locationId) {
        List<EmployeeTableMapper> resultList = new ArrayList<>();
        List<EmployeeDetails> empList = employeeRepository.getEmployeesByLocationId(locationId);
        System.out.println("@@@@@@@@Id" + locationId);
        System.out.println("@@@@@@@@" + empList);
        if (null != empList && !empList.isEmpty()) {
            return empList.stream().map(employeeDetails -> {
                EmployeeTableMapper employeeMapper = getEmployeeDetailByEmployeeId(employeeDetails.getEmployeeId());
                if (null != employeeMapper) {
                    employeeMapper.setSuspendInd(employeeDetails.isSuspendInd());
                    resultList.add(employeeMapper);
                }
                return employeeMapper;
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public List<EmployeeShortMapper> getEmployeeListWhoseImIndTrueForDropDown(String orgId) {
        List<EmployeeShortMapper> resultList = new ArrayList<>();

        List<Department> departmentList = departmentRepository.getDepartmentListByOrgIdAndImInd(orgId, true);
        if (null != departmentList && !departmentList.isEmpty()) {
            for (Department department : departmentList) {
                List<EmployeeDetails> employeeList = employeeRepository.getEmployeeListByDepartmentId(department.getDepartment_id());
                System.out.println("###########" + employeeList.toString());
                if (null != employeeList && !employeeList.isEmpty()) {
                    employeeList.stream().map(employee -> {
                        EmployeeShortMapper employeeMapper = getEmployeeFullNameAndEmployeeId(employee.getEmployeeId());
                        resultList.add(employeeMapper);
                        return employeeMapper;
                    }).collect(Collectors.toList());
                }
            }
        }
        return resultList;
    }

    @Override
    public void deleteEmployeeNotesById(String notesId) {
        EmployeeNotesLink notesList = employeeNotesLinkRepository.findByNotesId(notesId);
        if (null != notesList) {
            notesList.setLiveInd(false);
            employeeNotesLinkRepository.save(notesList);
            Notes notes = notesRepository.findByNoteId(notesId);
            if (null != notes) {
                notes.setLiveInd(false);
                notesRepository.save(notes);
            }
        }
    }


    @Override
    public EmployeeTableMapper getEmployeeDetail(EmployeeDetails employeeDetails) {
        EmployeeTableMapper employeeMapper = new EmployeeTableMapper();
//	        EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(employeeId, true);
        if (null != employeeDetails) {
            employeeMapper.setReportingManagerName(getEmployeeFullName(employeeDetails.getReportingManager()));
            employeeMapper.setEmployeeId(employeeDetails.getEmployeeId());
            employeeMapper.setFirstName(employeeDetails.getFirstName());
            employeeMapper.setMiddleName(employeeDetails.getMiddleName());
            employeeMapper.setLastName(employeeDetails.getLastName());
            employeeMapper.setCreationDate(Utility.getISOFromDate((employeeDetails.getCreationDate())));
            employeeMapper.setCreatorId(employeeDetails.getCreatorId());
            employeeMapper.setDateOfJoining(Utility.getISOFromDate(employeeDetails.getDateOfJoining()));
            employeeMapper.setReportingManager(employeeDetails.getReportingManager());

            if (null != employeeDetails.getDob()) {
                employeeMapper.setDob(Utility.getISOFromDate(employeeDetails.getDob()));
            } else {
                employeeMapper.setDob("");
            }

            if (null != employeeDetails.getDateOfJoining()) {
                employeeMapper.setDateOfJoining(Utility.getISOFromDate(employeeDetails.getDateOfJoining()));
            } else {
                employeeMapper.setDateOfJoining("");
            }
            employeeMapper.setEmailId(employeeDetails.getEmailId());
            employeeMapper.setSecondaryEmailId(employeeDetails.getSecondaryEmailId());
            employeeMapper.setFax(employeeDetails.getFax());
            // employeeMapper.setFullName(employeeDetails.getFullName());
            employeeMapper.setGender(employeeDetails.getGender());
            employeeMapper.setImageId(employeeDetails.getImageId());
            employeeMapper.setLinkedinPublicUrl(employeeDetails.getLinkedinPublicUrl());
            employeeMapper.setMobileNo(employeeDetails.getMobileNo());
            employeeMapper.setPhoneNo(employeeDetails.getPhoneNo());
            employeeMapper.setSkypeId(employeeDetails.getSkypeId());
            employeeMapper.setSalutation(employeeDetails.getSalutation());
            employeeMapper.setStatus(employeeDetails.getStatus());
            employeeMapper.setPreferedLanguage(employeeDetails.getLanguage());
            // employeeMapper.setTimeZone(employeeDetails.getTimeZone());
            employeeMapper.setSuspendInd(employeeDetails.isSuspendInd());
            employeeMapper.setEmployeeType(employeeDetails.getEmployeeType());
            employeeDetails.setJobType(employeeMapper.getJobType());
            employeeMapper.setWorkplace(employeeDetails.getWorkplace());
            // employeeMapper.setType(employeeDetails.getUserType());
            employeeMapper.setBilledInd(employeeDetails.isBilledInd());
            employeeMapper.setSalary(employeeDetails.getSalary());
            employeeMapper.setMultyOrgAccessInd(employeeDetails.isMultyOrgAccessInd());
            employeeMapper.setMultyOrgLinkInd(employeeDetails.isMultyOrgLinkInd());
            ServiceLine db = serviceLineRepository.findByServiceLineId(employeeDetails.getServiceLine());
            if (null != db) {
                employeeMapper.setServiceLine(db.getServiceLineName());
                employeeMapper.setServiceLineId(db.getServiceLineId());
            }

            System.out.println("EMPLOYEEID=" + employeeDetails.getEmployeeId());
            if (employeeDetails.getUserType().equalsIgnoreCase("External")) {
                employeeMapper.setType(true);
            } else {
                employeeMapper.setType(false);
            }

            CellChamberUserLink ccu = cellChamberUserLinkRepository
                    .findByUserAndActive(employeeDetails.getEmployeeId(), true);
            if (null != ccu) {
                CellChamberLink camber = cellChamberLinkRepository.findByIdAndActive(ccu.getCellChamberLinkId(), true);
                if (null != camber) {
                    employeeMapper.setCellChamber(camber.getCellChamber());
                }
            }

//	             if (employeeDetails.getRole().equalsIgnoreCase("ADMIN")) {
//	                 employeeMapper.setAdminInd(true);
//	             } else {
//	                 employeeMapper.setAdminInd(false);
//	             }

            String middleName = " ";
            String lastName = "";

            if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                lastName = employeeDetails.getLastName();
            }

            if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

                middleName = employeeDetails.getMiddleName();
                employeeMapper.setFullName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
            } else {

                employeeMapper.setFullName(employeeDetails.getFirstName() + " " + lastName);
            }

            if (!StringUtils.isEmpty(employeeDetails.getDesignation())) {
                Designation designationn = designationRepository
                        .findByDesignationTypeId(employeeDetails.getDesignation());
                if (null != designationn) {
                    employeeMapper.setDesignationTypeId(designationn.getDesignationTypeId());
                    employeeMapper.setDesignation(designationn.getDesignationType());
                }
            }

            Timezone timeZone = timezoneRepository.findByTimezoneId(employeeDetails.getTimeZone());
            if (null != timeZone) {
                employeeMapper.setTimeZone(timeZone.getZoneName());
            }

            if (!StringUtils.isEmpty(employeeDetails.getDepartment())) {
                // employeeMapper.setDepartment(employeeDetails.getDepartment());
                // System.out.println("department
                // Name@@@@@@@@@@@@"+employeeDetails.getDepartment());

                Department department = departmentRepository.getDepartmentDetails(employeeDetails.getDepartment());
                if (null != department) {
                    employeeMapper.setDepartmentId(department.getDepartment_id());
                    employeeMapper.setDepartment(department.getDepartmentName());
//	                     employeeMapper.setCrmInd(department.isCrmInd());
//	                     employeeMapper.setErpInd(department.isErpInd());
//	                     employeeMapper.setImInd(department.isImInd());
                }

                Department department1 = departmentRepository.getDepartmentDetails(employeeDetails.getReportingManagerDept());
                if (null != department1) {
                    employeeMapper.setReportingManagerDeptId(department1.getDepartment_id());
                    employeeMapper.setReportingManagerDept(department1.getDepartmentName());
                }

                Department department2 = departmentRepository.getDepartmentDetails(employeeDetails.getSecondaryReptManagerDept());
                if (null != department2) {
                    employeeMapper.setSecondaryReptManagerDeptId(department2.getDepartment_id());
                    employeeMapper.setSecondaryReptManagerDept(department2.getDepartmentName());

                }

                Department department3 = departmentRepository.getDepartmentDetails(employeeDetails.getSecondaryReptManager());
                if (null != department3) {
                    employeeMapper.setSecondaryReptManagerId(department3.getDepartment_id());
                    employeeMapper.setSecondaryReptManager(department3.getDepartmentName());

                }
                RoleType role = roleTypeRepository.findByRoleTypeId(employeeDetails.getRoleType());
                if (null != role) {
                    employeeMapper.setRoleType(role.getRoleTypeId());
                    employeeMapper.setRoleTypeName(role.getRoleType());
                }
            }

            List<KeyskillsMapper> skillList = new ArrayList<KeyskillsMapper>();
            List<KeySkillDetails> list = keySkillRepository.getSkillByEmployeeId(employeeDetails.getEmployeeId());
            if (null != list && !list.isEmpty()) {
                for (KeySkillDetails skill : list) {
                    KeySkillDetails list2 = keySkillRepository.getById(skill.getId());

                    KeyskillsMapper mapper = new KeyskillsMapper();
                    if (null != list2) {

                        mapper.setKeySkillsName(list2.getSkillName());
                        mapper.setEmployeeId(list2.getEmployeeId());
                        mapper.setKeySkillsId(list2.getId());
                        skillList.add(mapper);
                    }
                }
                employeeMapper.setSkill(skillList);
            }

            employeeMapper.setRole(employeeDetails.getRole());

            if (!StringUtils.isEmpty(employeeDetails.getCountry())) {
                employeeMapper.setCountry(employeeDetails.getCountry());
                Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(employeeDetails.getCountry(), employeeDetails.getOrgId());
                if (null != country) {
                    employeeMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
                    employeeMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
                }
            } else {
                List<EmployeeAddressLink> employeeAddressList = employeeAddressLinkRepository
                        .getAddressListByEmpId(employeeDetails.getEmployeeId());
                if (null != employeeAddressList && !employeeAddressList.isEmpty()) {
                    for (EmployeeAddressLink employeeAddressLink : employeeAddressList) {
                        AddressDetails addressDetails = addressRepository
                                .getAddressDetailsByAddressId(employeeAddressLink.getAddressId());
                        if (null != addressDetails) {
                            if (!StringUtils.isEmpty(addressDetails.getCountry())) {
                                employeeMapper.setCountry(addressDetails.getCountry());
                                Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(addressDetails.getCountry(), employeeDetails.getOrgId());
                                if (null != country) {
                                    employeeMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
                                    employeeMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
                                }
                            }
                        }
                    }
                }
            }


            employeeMapper.setTwitter(employeeDetails.getTwitter());
            employeeMapper.setFacebook(employeeDetails.getFacebook());
            Currency currency = currencyRepository.getByCurrencyId(employeeDetails.getCurrency());
            if (null != currency) {
                employeeMapper.setCurrency(currency.getCurrencyName());
            }
            employeeMapper.setCountryDialCode(employeeDetails.getCountryDialCode());
            employeeMapper.setCountryDialCode1(employeeDetails.getCountryDialCode1());
            employeeMapper.setOrganizationId(employeeDetails.getOrgId());
            employeeMapper.setUserId(employeeDetails.getUserId());
            employeeMapper.setBloodGroup(employeeDetails.getBloodGroup());

            LocationDetails location = locationDetailsRepository.findByLocationDetailsIdAndActiveInd(employeeDetails.getLocation(), true);
            if (null != location) {
                employeeMapper.setLocation(location.getLocationDetailsId());
                employeeMapper.setLocationName(location.getLocationName());
            }

//	             List<EmployeeAddressLink> employeeAddressList = employeeAddressLinkRepository
//	                     .getAddressListByEmpId(employeeDetails.getEmployeeId());
//	             List<AddressMapper> addressList = new ArrayList<AddressMapper>();
//	             if (null != employeeAddressList && !employeeAddressList.isEmpty()) {
//
//	                 for (EmployeeAddressLink employeeAddressLink : employeeAddressList) {
//	                     AddressDetails addressDetails = addressRepository
//	                             .getAddressDetailsByAddressId(employeeAddressLink.getAddressId());
//
//	                     AddressMapper addressMapper = new AddressMapper();
//	                     if (null != addressDetails) {
//	                         addressMapper.setAddress1(addressDetails.getAddressLine1());
//	                         addressMapper.setAddress2(addressDetails.getAddressLine2());
//	                         addressMapper.setAddressType(addressDetails.getAddressType());
//	                         addressMapper.setPostalCode(addressDetails.getPostalCode());
//	                         addressMapper.setStreet(addressDetails.getStreet());
//	                         addressMapper.setCity(addressDetails.getCity());
//	                         addressMapper.setTown(addressDetails.getTown());
//	                         addressMapper.setCountry(addressDetails.getCountry());
//	                         addressMapper.setLatitude(addressDetails.getLatitude());
//	                         addressMapper.setLongitude(addressDetails.getLongitude());
//	                         addressMapper.setState(addressDetails.getState());
//	                         addressMapper.setAddressId(addressDetails.getAddressId());
//	                         addressMapper.setHouseNo(addressDetails.getHouseNo());
//	                         addressList.add(addressMapper);
//
//	                     }
//
//	                 }
//	             }
//	             employeeMapper.setAddress(addressList);

            List<DocumentType> documentType = documentTypeRepository.getDocumentTypeListByOrgIdAndUserTypeWithLiveIndAndMandatoryInd(employeeDetails.getOrgId(), "User");
            List<String> documentList = new ArrayList<>();
            if (null != documentType && !documentType.isEmpty()) {
                for (DocumentType documentType2 : documentType) {
                    documentList.add(documentType2.getDocumentTypeName());
                }
                for (DocumentType documentType1 : documentType) {
                    System.out.println("User Id =====-=----inner3-----=--///" + employeeDetails.getEmployeeId());
                    if (null != documentType1.getDocument_type_id()) {
                        List<EmployeeDocumentLink> employeeDocumentLink = employeeDocumentLinkRepository.
                                getDocumentByEmployeeId(employeeDetails.getEmployeeId());
                        if (null != employeeDocumentLink && !employeeDocumentLink.isEmpty()) {
                            for (EmployeeDocumentLink employeeDocumentLink1 : employeeDocumentLink) {
                                DocumentDetails documentDetails = documentDetailsRepository.getDocumentDetailsById(employeeDocumentLink1.getDocumentId());
                                if (null != documentDetails) {
                                    if (null != documentDetails.getDocument_type()) {
                                        if (documentDetails.getDocument_type().equalsIgnoreCase(documentType1.getDocument_type_id())) {
                                            documentList.remove(documentType1.getDocumentTypeName());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            employeeMapper.setListOfDocPending(documentList);
            employeeMapper.setNoOfDocPending(documentList.size());
        }
        return employeeMapper;
    }

    @Override
    public List<EmployeeShortMapper> getEmployeeListByDepartmentIdForDropDown(String departmentId) {
        List<EmployeeShortMapper> resultList = new ArrayList<>();
        List<EmployeeDetails> employeeList = employeeRepository.getEmployeeListByDepartmentId(departmentId);
        System.out.println("###########" + employeeList.toString());
        if (null != employeeList && !employeeList.isEmpty()) {
            employeeList.stream().map(employee -> {
                EmployeeShortMapper employeeMapper = getEmployeeFullNameAndEmployeeId(employee.getEmployeeId());
                resultList.add(employeeMapper);
                return employeeMapper;
            }).collect(Collectors.toList());
        }
        return resultList;
    }


    @Override
    public HashMap getEmployeeListByLocationIdAndLiveInd(String locationId) {
        List<EmployeeDetails> empList = employeeRepository.getEmployeesByLocationId(locationId);
        HashMap map = new HashMap();
        map.put("EmployeeListByLocationId", empList.size());

        return map;
    }


    @Override
    public EmployeeTableMapper updateEmployeeUserToAdminByEmployeeId(
            EmployeeAdminUpdateRequestMapper employeeAdminUpdateRequestMapper) {
        EmployeeTableMapper mapper = new EmployeeTableMapper();
//		Date end_date =null;
        Date start_date = null;
        Date todayDate = Utility.removeTime(new Date());
        try {
//			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
            start_date = Utility.removeTime(Utility.getDateFromISOString(employeeAdminUpdateRequestMapper.getStartDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        EmployeeAdminUpdate employeeAdminUpdate1 = employeeAdminUpdateRepository.findByEmployeeId(employeeAdminUpdateRequestMapper.getEmployeeId());
        if (null != employeeAdminUpdate1) {
            employeeAdminUpdate1.setCreationDate(new Date());
            employeeAdminUpdate1.setEmployeeId(employeeAdminUpdateRequestMapper.getEmployeeId());
            employeeAdminUpdate1.setOrgId(employeeAdminUpdateRequestMapper.getOrgId());
            employeeAdminUpdate1.setUserId(employeeAdminUpdateRequestMapper.getUserId());
            try {
                employeeAdminUpdate1.setStartDate(Utility.getDateFromISOString(employeeAdminUpdateRequestMapper.getStartDate()));
                employeeAdminUpdate1.setEndDate(Utility.getDateFromISOString(employeeAdminUpdateRequestMapper.getEndDate()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (employeeAdminUpdateRequestMapper.isAdminInd()) {
                employeeAdminUpdate1.setAdminInd(employeeAdminUpdateRequestMapper.isAdminInd());
                if (start_date.compareTo(todayDate) == 0) {
                    EmployeeDetails emp = employeeRepository.getEmployeeDetailsByEmployeeId(employeeAdminUpdateRequestMapper.getEmployeeId());
                    if (null != emp) {
                        emp.setRole("ADMIN");
                        employeeRepository.save(emp);
                        mapper = getEmployeeDetail(emp);
                    }
                } else {
                    EmployeeDetails emp = employeeRepository.getEmployeeDetailsByEmployeeId(employeeAdminUpdateRequestMapper.getEmployeeId());
                    if (null != emp) {
                        mapper = getEmployeeDetail(emp);
                    }
                }
            } else {
                employeeAdminUpdate1.setAdminInd(employeeAdminUpdateRequestMapper.isAdminInd());
                EmployeeDetails emp = employeeRepository.getEmployeeDetailsByEmployeeId(employeeAdminUpdateRequestMapper.getEmployeeId());
                if (null != emp) {
                    emp.setRole("USER");
                    employeeRepository.save(emp);
                    mapper = getEmployeeDetail(emp);
                }
            }
            employeeAdminUpdateRepository.save(employeeAdminUpdate1);

        } else {
            EmployeeAdminUpdate employeeAdminUpdate = new EmployeeAdminUpdate();
            employeeAdminUpdate.setCreationDate(new Date());
            employeeAdminUpdate.setEmployeeId(employeeAdminUpdateRequestMapper.getEmployeeId());
            employeeAdminUpdate.setOrgId(employeeAdminUpdateRequestMapper.getOrgId());
            employeeAdminUpdate.setUserId(employeeAdminUpdateRequestMapper.getUserId());
            try {
                employeeAdminUpdate.setStartDate(Utility.getDateFromISOString(employeeAdminUpdateRequestMapper.getStartDate()));
                employeeAdminUpdate.setEndDate(Utility.getDateFromISOString(employeeAdminUpdateRequestMapper.getEndDate()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (employeeAdminUpdateRequestMapper.isAdminInd()) {
                employeeAdminUpdate.setAdminInd(employeeAdminUpdateRequestMapper.isAdminInd());
                if (start_date.compareTo(todayDate) == 0) {
                    EmployeeDetails emp = employeeRepository.getEmployeeDetailsByEmployeeId(employeeAdminUpdateRequestMapper.getEmployeeId());
                    if (null != emp) {
                        emp.setRole("ADMIN");
                        employeeRepository.save(emp);
                        mapper = getEmployeeDetail(emp);
                    }
                } else {
                    EmployeeDetails emp = employeeRepository.getEmployeeDetailsByEmployeeId(employeeAdminUpdateRequestMapper.getEmployeeId());
                    if (null != emp) {
                        mapper = getEmployeeDetail(emp);
                    }
                }
            } else {
                employeeAdminUpdate.setAdminInd(employeeAdminUpdateRequestMapper.isAdminInd());
                EmployeeDetails emp = employeeRepository.getEmployeeDetailsByEmployeeId(employeeAdminUpdateRequestMapper.getEmployeeId());
                if (null != emp) {
                    mapper = getEmployeeDetail(emp);
                }
            }
            employeeAdminUpdateRepository.save(employeeAdminUpdate);
        }
        return mapper;
    }


    @Override
    public EmployeeAdminUpdateResponseMapper getEmployeeUserToAdminByEmployeeId(String employeeId) {
        EmployeeAdminUpdateResponseMapper mapper = new EmployeeAdminUpdateResponseMapper();
        EmployeeAdminUpdate employeeAdminUpdate1 = employeeAdminUpdateRepository.findByEmployeeId(employeeId);
        if (null != employeeAdminUpdate1) {
            mapper.setAdminInd(employeeAdminUpdate1.isAdminInd());
            mapper.setStartDate(Utility.getISOFromDate(employeeAdminUpdate1.getStartDate()));
            mapper.setEndDate(Utility.getISOFromDate(employeeAdminUpdate1.getEndDate()));
            mapper.setUpdatedOn(Utility.getISOFromDate(employeeAdminUpdate1.getCreationDate()));
            mapper.setUpdatedBy(employeeService.getEmployeeFullName(employeeAdminUpdate1.getUserId()));
        }
        return mapper;
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void updateRoleAdminAndUser() throws Exception {

        Date todayDate = Utility.removeTime(new Date());

        List<EmployeeDetails> employeeList = employeeRepository.getEmployeesByLiveIndAndSuspendInd();
        if (null != employeeList && !employeeList.isEmpty()) {
            for (EmployeeDetails employee : employeeList) {
                EmployeeAdminUpdate employeeAdminUpdate1 = employeeAdminUpdateRepository.findByEmployeeId(employee.getEmployeeId());
                if (null != employeeAdminUpdate1) {
                    Date startDate = Utility.removeTime(employeeAdminUpdate1.getStartDate());
                    Date endDate = Utility.getDateAfterEndDate(Utility.removeTime(employeeAdminUpdate1.getEndDate()));
                    if (employeeAdminUpdate1.isAdminInd()) {
                        if (startDate.compareTo(todayDate) == 0) {
                            EmployeeDetails emp = employeeRepository.getEmployeeDetailsByEmployeeId(employee.getEmployeeId());
                            if (null != emp) {
                                emp.setRole("ADMIN");
                                employeeRepository.save(emp);
                            }
                        }
                        if (endDate.compareTo(todayDate) == 0) {
                            EmployeeDetails emp = employeeRepository.getEmployeeDetailsByEmployeeId(employee.getEmployeeId());
                            if (null != emp) {
                                emp.setRole("USER");
                                employeeRepository.save(emp);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public EmployeeTableMapper getTableMapperEmployeeDetails(EmployeeDetails employeeDetails) {

        EmployeeTableMapper employeeMapper = new EmployeeTableMapper();

        if (null != employeeDetails) {

            employeeMapper.setEmployeeId(employeeDetails.getEmployeeId());
            employeeMapper.setFirstName(employeeDetails.getFirstName());
            employeeMapper.setMiddleName(employeeDetails.getMiddleName());
            employeeMapper.setLastName(employeeDetails.getLastName());
            employeeMapper.setCreationDate(Utility.getISOFromDate((employeeDetails.getCreationDate())));
            employeeMapper.setCreatorId(employeeDetails.getCreatorId());
            employeeMapper.setDateOfJoining(Utility.getISOFromDate(employeeDetails.getDateOfJoining()));
            employeeMapper.setReportingManager(employeeDetails.getReportingManager());
            employeeMapper.setReportingManagerName(getEmployeeFullName(employeeDetails.getReportingManager()));

            if (null != employeeDetails.getDob()) {
                employeeMapper.setDob(Utility.getISOFromDate(employeeDetails.getDob()));

            } else {
                employeeMapper.setDob("");
            }

            if (null != employeeDetails.getDateOfJoining()) {
                employeeMapper.setDateOfJoining(Utility.getISOFromDate(employeeDetails.getDateOfJoining()));
            } else {
                employeeMapper.setDateOfJoining("");
            }
            employeeMapper.setEmailId(employeeDetails.getEmailId());
            employeeMapper.setSecondaryEmailId(employeeDetails.getSecondaryEmailId());
            employeeMapper.setFax(employeeDetails.getFax());
            // employeeMapper.setFullName(employeeDetails.getFullName());
            employeeMapper.setGender(employeeDetails.getGender());
            employeeMapper.setImageId(employeeDetails.getImageId());
            employeeMapper.setLinkedinPublicUrl(employeeDetails.getLinkedinPublicUrl());
            employeeMapper.setMobileNo(employeeDetails.getMobileNo());
            employeeMapper.setPhoneNo(employeeDetails.getPhoneNo());
            employeeMapper.setSkypeId(employeeDetails.getSkypeId());
            employeeMapper.setSalutation(employeeDetails.getSalutation());
            employeeMapper.setStatus(employeeDetails.getStatus());
            employeeMapper.setPreferedLanguage(employeeDetails.getLanguage());
            employeeMapper.setTimeZone(employeeDetails.getTimeZone());
            employeeMapper.setSuspendInd(employeeDetails.isSuspendInd());
            employeeMapper.setEmployeeType(employeeDetails.getEmployeeType());
            employeeMapper.setWorkplace(employeeDetails.getWorkplace());
            // employeeMapper.setType(employeeDetails.getUserType());
            employeeMapper.setBilledInd(employeeDetails.isBilledInd());
            employeeMapper.setSalary(employeeDetails.getSalary());
            employeeMapper.setServiceLine(employeeDetails.getServiceLine());
            System.out.println("EMPLOYEEID=" + employeeDetails.getEmployeeId());
            if (employeeDetails.getUserType().equalsIgnoreCase("External")) {
                employeeMapper.setType(true);
            } else {
                employeeMapper.setType(false);
            }

            String middleName = " ";
            String lastName = "";

            if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                lastName = employeeDetails.getLastName();
            }

            if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

                middleName = employeeDetails.getMiddleName();
                employeeMapper.setFullName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
            } else {

                employeeMapper.setFullName(employeeDetails.getFirstName() + " " + lastName);
            }

            if (!StringUtils.isEmpty(employeeDetails.getDesignation())) {
                Designation designationn = designationRepository
                        .findByDesignationTypeId(employeeDetails.getDesignation());
                if (null != designationn) {
                    employeeMapper.setDesignationTypeId(designationn.getDesignationTypeId());
                    employeeMapper.setDesignation(designationn.getDesignationType());
                }
            }

            if (!StringUtils.isEmpty(employeeDetails.getDepartment())) {
                // employeeMapper.setDepartment(employeeDetails.getDepartment());
                // System.out.println("department
                // Name@@@@@@@@@@@@"+employeeDetails.getDepartment());

                Department department = departmentRepository.getDepartmentDetails(employeeDetails.getDepartment());
                if (null != department) {
                    employeeMapper.setDepartmentId(department.getDepartment_id());
                    employeeMapper.setDepartment(department.getDepartmentName());
//                    employeeMapper.setReportingManagerDept(department.getDepartmentName());
//                    employeeMapper.setReportingManagerDept(department.getDepartmentName());                    
                }

                Department department1 = departmentRepository.getDepartmentDetails(employeeDetails.getReportingManagerDept());
                if (null != department1) {
                    employeeMapper.setReportingManagerDeptId(department1.getDepartment_id());
                    employeeMapper.setReportingManagerDept(department1.getDepartmentName());

                }

                Department department2 = departmentRepository.getDepartmentDetails(employeeDetails.getSecondaryReptManagerDept());
                if (null != department2) {
                    employeeMapper.setSecondaryReptManagerDeptId(department2.getDepartment_id());
                    employeeMapper.setSecondaryReptManagerDept(department2.getDepartmentName());

                }

                Department department3 = departmentRepository.getDepartmentDetails(employeeDetails.getSecondaryReptManager());
                if (null != department3) {
                    employeeMapper.setSecondaryReptManagerId(department3.getDepartment_id());
                    employeeMapper.setSecondaryReptManager(department3.getDepartmentName());

                }

                employeeMapper.setRole(employeeDetails.getRole());

                if (!StringUtils.isEmpty(employeeDetails.getCountry())) {
                    employeeMapper.setCountry(employeeDetails.getCountry());
                    Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(employeeDetails.getCountry(), employeeDetails.getOrgId());
                    if (null != country) {
                        employeeMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
                        employeeMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
                    }
                } else {
                    List<EmployeeAddressLink> employeeAddressList = employeeAddressLinkRepository
                            .getAddressListByEmpId(employeeDetails.getEmployeeId());
                    if (null != employeeAddressList && !employeeAddressList.isEmpty()) {
                        for (EmployeeAddressLink employeeAddressLink : employeeAddressList) {
                            AddressDetails addressDetails = addressRepository
                                    .getAddressDetailsByAddressId(employeeAddressLink.getAddressId());
                            if (null != addressDetails) {
                                if (!StringUtils.isEmpty(addressDetails.getCountry())) {
                                    employeeMapper.setCountry(addressDetails.getCountry());
                                    Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(addressDetails.getCountry(), employeeDetails.getOrgId());
                                    if (null != country) {
                                        employeeMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
                                        employeeMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
                                    }
                                }
                            }
                        }
                    }
                }

                employeeMapper.setTwitter(employeeDetails.getTwitter());
                employeeMapper.setFacebook(employeeDetails.getFacebook());
                employeeMapper.setCurrency(employeeDetails.getCurrency());
                employeeMapper.setCountryDialCode(employeeDetails.getCountryDialCode());
                employeeMapper.setCountryDialCode1(employeeDetails.getCountryDialCode1());
                employeeMapper.setOrganizationId(employeeDetails.getOrgId());
                employeeMapper.setUserId(employeeDetails.getUserId());
                employeeMapper.setBloodGroup(employeeDetails.getBloodGroup());

                LocationDetails location = locationDetailsRepository.findByLocationDetailsIdAndActiveInd(employeeDetails.getLocation(), true);
                if (null != location) {
                    employeeMapper.setLocation(location.getLocationName());
                }

//            List<EmployeeAddressLink> employeeAddressList = employeeAddressLinkRepository
//                    .getAddressListByEmpId(employeeDetails.getEmployeeId());
//            List<AddressMapper> addressList = new ArrayList<AddressMapper>();
//            if (null != employeeAddressList && !employeeAddressList.isEmpty()) {
//
//                for (EmployeeAddressLink employeeAddressLink : employeeAddressList) {
//                    AddressDetails addressDetails = addressRepository
//                            .getAddressDetailsByAddressId(employeeAddressLink.getAddressId());
//
//                    AddressMapper addressMapper = new AddressMapper();
//                    if (null != addressDetails) {
//                        addressMapper.setAddress1(addressDetails.getAddressLine1());
//                        addressMapper.setAddress2(addressDetails.getAddressLine2());
//                        addressMapper.setAddressType(addressDetails.getAddressType());
//                        addressMapper.setPostalCode(addressDetails.getPostalCode());
//                        addressMapper.setStreet(addressDetails.getStreet());
//                        addressMapper.setCity(addressDetails.getCity());
//                        addressMapper.setTown(addressDetails.getTown());
//                        addressMapper.setCountry(addressDetails.getCountry());
//                        addressMapper.setLatitude(addressDetails.getLatitude());
//                        addressMapper.setLongitude(addressDetails.getLongitude());
//                        addressMapper.setState(addressDetails.getState());
//                        addressMapper.setAddressId(addressDetails.getAddressId());
//                        addressMapper.setHouseNo(addressDetails.getHouseNo());
//                        addressList.add(addressMapper);
//
//                    }
//
//                }
//            }
//            employeeMapper.setAddress(addressList);

                List<DocumentType> documentType = documentTypeRepository.getDocumentTypeListByOrgIdAndUserTypeWithLiveIndAndMandatoryInd(employeeDetails.getOrgId(), "User");
                List<String> documentList = new ArrayList<>();
                if (null != documentType && !documentType.isEmpty()) {
                    for (DocumentType documentType2 : documentType) {
                        documentList.add(documentType2.getDocumentTypeName());
                    }
                    for (DocumentType documentType1 : documentType) {
                        System.out.println("User Id =====-=----inner3-----=--///" + employeeDetails.getEmployeeId());
                        if (null != documentType1.getDocument_type_id()) {
                            List<EmployeeDocumentLink> employeeDocumentLink = employeeDocumentLinkRepository.
                                    getDocumentByEmployeeId(employeeDetails.getEmployeeId());
                            if (null != employeeDocumentLink && !employeeDocumentLink.isEmpty()) {
                                for (EmployeeDocumentLink employeeDocumentLink1 : employeeDocumentLink) {
                                    DocumentDetails documentDetails = documentDetailsRepository.getDocumentDetailsById(employeeDocumentLink1.getDocumentId());
                                    if (null != documentDetails) {
                                        if (null != documentDetails.getDocument_type()) {
                                            if (documentDetails.getDocument_type().equalsIgnoreCase(documentType1.getDocument_type_id())) {
                                                documentList.remove(documentType1.getDocumentTypeName());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                employeeMapper.setListOfDocPending(documentList);
                employeeMapper.setNoOfDocPending(documentList.size());
            }

//          if (null != employeeDetails.getReportingManager()) {
//            EmployeeDetails reportingManagerDetails = employeeRepository
//                    .getEmployeeDetailsByEmployeeId(employeeDetails.getReportingManager(), true);
//            if (null != reportingManagerDetails) {
//               
//
//            }
//        }

        }
        return employeeMapper;
    }


    @Override
    public List<EmployeeTableMapper> getUserListByReportingMangerId(String reptMngrId) {
        List<EmployeeTableMapper> resultList = new ArrayList<>();
        List<EmployeeDetails> employeeList = employeeRepository.getEmployeeDetailsByReportingManagerId(reptMngrId);
        if (null != employeeList && !employeeList.isEmpty()) {
            employeeList.stream().map(employee -> {
                EmployeeTableMapper employeeMapper = getEmployeeDetail(employee);
                if (null != employeeMapper) {
                    resultList.add(employeeMapper);
                }
                return employeeMapper;
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public HashMap getUserCountByReportingMangerId(String reptMngrId) {
        List<EmployeeDetails> employeeList = employeeRepository.getEmployeeDetailsByReportingManagerId(reptMngrId);
        HashMap map = new HashMap();
        map.put("EmployeeListByReptMngrId", employeeList.size());
        return map;
    }

    @Override
    public UserKpiResponseMapper addKpiWithEmployee(UserKpiRequestMapper userKpiRequestMapper) {
        UserKpiResponseMapper resultList = new UserKpiResponseMapper();
        if (null != userKpiRequestMapper) {
            UserKpiLink userKpiLink = new UserKpiLink();
            userKpiLink.setLiveInd(true);
            userKpiLink.setCreationDate(new Date());
            userKpiLink.setPerformanceManagementId(userKpiRequestMapper.getPerformanceManagementId());
            userKpiLink.setWeitageValue(userKpiRequestMapper.getWeitageValue());
            userKpiLink.setEmployeeId(userKpiRequestMapper.getEmployeeId());
            userKpiLink.setOrgId(userKpiRequestMapper.getOrgId());
            userKpiLink.setUserId(userKpiRequestMapper.getUserId());
            userKpiLink.setYear(userKpiRequestMapper.getYear());
            userKpiLink.setQuarter(userKpiRequestMapper.getQuarter());
            userKpiLink.setLobDetailsId(userKpiRequestMapper.getLobDetailsId());
            userKpiLink.setMonth1AssignedValue(userKpiRequestMapper.getMonth1AssignedValue());
            userKpiLink.setMonth2AssignedValue(userKpiRequestMapper.getMonth2AssignedValue());
            userKpiLink.setMonth3AssignedValue(userKpiRequestMapper.getMonth3AssignedValue());
            userKpiLink.setEmployeeId(userKpiRequestMapper.getEmployeeId());

            double assignedValue = (userKpiRequestMapper.getMonth1AssignedValue() +
                    userKpiRequestMapper.getMonth2AssignedValue() +
                    userKpiRequestMapper.getMonth3AssignedValue());
            userKpiLink.setAssignedValue(assignedValue);
            resultList = getKpiByUserKpiLinkId(userKpiLinkRepository.save(userKpiLink).getUserKpiLinkId());
        }
        return resultList;
    }

    private UserKpiResponseMapper getKpiByUserKpiLinkId(String userKpiLinkId) {
        UserKpiResponseMapper mapper = new UserKpiResponseMapper();
        UserKpiLink userKpiLink = userKpiLinkRepository.getById(userKpiLinkId);
        if (null != userKpiLink) {
            mapper.setEmployeeId(userKpiLink.getEmployeeId());
            mapper.setOrgId(userKpiLink.getOrgId());
            mapper.setUserId(userKpiLink.getUserId());
            mapper.setMonth1AssignedValue(userKpiLink.getMonth1AssignedValue());
            mapper.setMonth2AssignedValue(userKpiLink.getMonth2AssignedValue());
            mapper.setMonth3AssignedValue(userKpiLink.getMonth3AssignedValue());
            mapper.setAssignedValue(userKpiLink.getAssignedValue());
            mapper.setWeitageValue(userKpiLink.getWeitageValue());
            mapper.setMonth1CompletedValue(userKpiLink.getMonth1CompletedValue());
            mapper.setMonth2CompletedValue(userKpiLink.getMonth2CompletedValue());
            mapper.setMonth3CompletedValue(userKpiLink.getMonth3CompletedValue());
            mapper.setCompletedValue(userKpiLink.getCompletedValue());
            mapper.setUserKpiLinkId(userKpiLink.getUserKpiLinkId());
            mapper.setCreationDate(Utility.getISOFromDate(userKpiLink.getCreationDate()));
            mapper.setCmpltValueAddDate(Utility.getISOFromDate(userKpiLink.getCmpltValueAddDate()));
            mapper.setActualCmpltValueAddDate(Utility.getISOFromDate(userKpiLink.getActualCmpltValueAddDate()));
            mapper.setMonth1ActualCompletedValue(userKpiLink.getMonth1ActualCompletedValue());
            mapper.setMonth2ActualCompletedValue(userKpiLink.getMonth2ActualCompletedValue());
            mapper.setMonth3ActualCompletedValue(userKpiLink.getMonth3ActualCompletedValue());
            mapper.setActualCompletedValue(userKpiLink.getActualCompletedValue());
            mapper.setActualCmpltValueAddedBy(getEmployeeFullName(userKpiLink.getActualCmpltValueAddedBy()));
            mapper.setYear(userKpiLink.getYear());
            mapper.setQuarter(userKpiLink.getQuarter());
            PerformanceManagement performanceManagement = performanceManagementRepository
                    .findByPerformanceManagementId(userKpiLink.getPerformanceManagementId());
            if (null != performanceManagement) {
                mapper.setKpiId(performanceManagement.getPerformanceManagementId());
                mapper.setKpiName(performanceManagement.getKpi());
                mapper.setCurrencyInd(performanceManagement.isCurrencyInd());
            }
            EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(userKpiLink.getEmployeeId(), true);
            if (null != employeeDetails) {
                Currency currency = currencyRepository.getByCurrencyId(employeeDetails.getCurrency());
                if (null != currency) {
                    mapper.setUserCurrency(currency.getCurrencyName());
                }
            }

            LobDetails lob = lobRepository.findByLobDetailsId(userKpiLink.getLobDetailsId());
            if (null != lob) {
                mapper.setLobDetailsId(lob.getLobDetailsId());
                mapper.setLobName(lob.getName());
            }

        }
        return mapper;
    }


    @Override
    public List<UserKpiResponseMapper> getKpiListByEmployeeId(String employeeId, double year, String quarter) {
        List<UserKpiResponseMapper> resultList = new ArrayList<>();
        List<UserKpiLink> userKpiLink = userKpiLinkRepository.findByYearAndQuarterAndEmployeeIdAndLiveInd(year, quarter, employeeId, true);
        if (null != userKpiLink && !userKpiLink.isEmpty()) {
            userKpiLink.stream().map(employee -> {
                UserKpiResponseMapper mapper = getKpiByUserKpiLinkId(employee.getUserKpiLinkId());
                if (null != mapper) {
                    resultList.add(mapper);
                }
                return mapper;
            }).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public String employeeOnboarding(String employeeId, String loggedInUserId, String loggedInUserOrgId) {
        OrganizationDetails organizationDetails = organizationRepository.getOrganizationDetailsById(loggedInUserOrgId);
        String orgName = organizationDetails.getName();
        EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(employeeId, true);
        if (null != employeeDetails) {
            String to = employeeDetails.getEmailId();
            String name = getEmployeeFullNameByObject(employeeDetails);
            String subject = "Welcome to Korero HRMS";
            String message = emailService.prepareEmailValidationLink(to, employeeId, loggedInUserOrgId, name, orgName);
            try {
                emailService.sendMail("support@innoverenit.com", to, subject, message);
            } catch (MessagingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//			String msg = name + " created as new employee";
//			notificationService.createNotificationForAll(loggedInUserOrgId, loggedInUserId, "user create", msg, "user",
//					"Create");
            return "Employee Successfully onboarded..";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id :" + employeeId);
    }

    @Override
    public UserKpiResponseMapper addKpiAssignedValueByEmployeeId(
            UserKpiRequestForAssignedValueMapper userKpiRequestMapper) {
        UserKpiResponseMapper resultList = new UserKpiResponseMapper();
        UserKpiLink userKpiLink = userKpiLinkRepository.getById(userKpiRequestMapper.getUserKpiLinkId());
        if (null != userKpiLink) {
            userKpiLink.setWeitageValue(userKpiRequestMapper.getWeitageValue());
            userKpiLink.setLobDetailsId(userKpiRequestMapper.getLobDetailsId());
            userKpiLink.setMonth1AssignedValue(userKpiRequestMapper.getMonth1AssignedValue());
            userKpiLink.setMonth2AssignedValue(userKpiRequestMapper.getMonth2AssignedValue());
            userKpiLink.setMonth3AssignedValue(userKpiRequestMapper.getMonth3AssignedValue());
            userKpiLink.setEmployeeId(userKpiRequestMapper.getEmployeeId());

            double assignedValue = (userKpiRequestMapper.getMonth1AssignedValue() +
                    userKpiRequestMapper.getMonth2AssignedValue() +
                    userKpiRequestMapper.getMonth3AssignedValue());
            userKpiLink.setAssignedValue(assignedValue);

            resultList = getKpiByUserKpiLinkId(userKpiLinkRepository.save(userKpiLink).getUserKpiLinkId());
//				/* insert to Notification Table */
//		        Notificationparam param = new Notificationparam();
//				EmployeeDetails emp = employeeRepository.getEmployeesByuserId(userKpiLink.getUserId());
//		        String name = employeeService.getEmployeeFullNameByObject(emp);
//		        param.setEmployeeDetails(emp);
//		        param.setAdminMsg("Prospect "+"'"+customerMapperr.getName() + "' created by "+name);
//		        param.setOwnMsg("Prospect "+customerMapperr.getName() +" created.");
//		        param.setNotificationType("Prospect Creation"); 
//		        param.setProcessNmae("Prospect");
//		        param.setType("create");
//		        param.setEmailSubject("Korero alert- Prospect created");
//		        param.setCompanyName(companyName);
//		        param.setUserId(userKpiLink.getUserId());   
//		        
//		        if(!customerMapperr.getUserId().equals(customerMapperr.getAssignedTo())) {
//		        	List<String> assignToUserIds = new ArrayList<>();
//		        	assignToUserIds.add(customerMapperr.getAssignedTo());
//		        	param.setAssignToUserIds(assignToUserIds); 
//		        param.setAssignToMsg("Prospect "+"'"+customerMapperr.getName() + "'"+ " assigned to "+employeeService.getEmployeeFullName(customerMapperr.getAssignedTo())+" by "+name);
//		        }
//		        notificationService.createNotificationForDynamicUsers(param);

            String assignedTo = userKpiLink.getEmployeeId();
            String name = getEmployeeFullName(userKpiLink.getUserId());
            String msg = "Kpi Assigned Value Changed By" + name;

            notificationService.addToAllNotiTable(userKpiLink.getUserId(), "Kpi Assigned Value Changed ", assignedTo, userKpiLink.getOrgId(), msg);
        }
        return resultList;
    }

    @Override
    public UserKpiResponseMapper addKpiCompletedValueByEmployeeId(UserKpiRequestForCompletedValueMapper userKpiRequestMapper) {
        UserKpiResponseMapper resultList = new UserKpiResponseMapper();
        UserKpiLink userKpiLink = userKpiLinkRepository.getById(userKpiRequestMapper.getUserKpiLinkId());
        if (null != userKpiLink) {
            userKpiLink.setCmpltValueAddDate(new Date());
            userKpiLink.setMonth1CompletedValue(userKpiRequestMapper.getMonth1CompletedValue());
            userKpiLink.setMonth2CompletedValue(userKpiRequestMapper.getMonth2CompletedValue());
            userKpiLink.setMonth3CompletedValue(userKpiRequestMapper.getMonth3CompletedValue());
            userKpiLink.setEmployeeId(userKpiRequestMapper.getEmployeeId());

            double completedValue = (userKpiRequestMapper.getMonth1CompletedValue() +
                    userKpiRequestMapper.getMonth2CompletedValue() +
                    userKpiRequestMapper.getMonth3CompletedValue());
            userKpiLink.setCompletedValue(completedValue);

            resultList = getKpiByUserKpiLinkId(userKpiLinkRepository.save(userKpiLink).getUserKpiLinkId());

            String assignedTo = userKpiLink.getUserId();
            String name = getEmployeeFullName(userKpiLink.getEmployeeId());
            String msg = "Kpi Completed Value Changed By" + name;

            notificationService.addToAllNotiTable(userKpiLink.getEmployeeId(), "Kpi Completed Value Changed ", assignedTo, userKpiLink.getOrgId(), msg);
        }
        return resultList;
    }

    @Override
    public UserKpiResponseMapper addKpiaActualCompletedValueByEmployeeId(
            UserKpiRequestForCompletedValueMapper userKpiRequestMapper) {
        UserKpiResponseMapper resultList = new UserKpiResponseMapper();
        UserKpiLink userKpiLink = userKpiLinkRepository.getById(userKpiRequestMapper.getUserKpiLinkId());
        if (null != userKpiLink) {
            userKpiLink.setActualCmpltValueAddDate(new Date());
            userKpiLink.setMonth1ActualCompletedValue(userKpiRequestMapper.getMonth1ActualCompletedValue());
            userKpiLink.setMonth2ActualCompletedValue(userKpiRequestMapper.getMonth2ActualCompletedValue());
            userKpiLink.setMonth3ActualCompletedValue(userKpiRequestMapper.getMonth3ActualCompletedValue());

            double actualCompletedValue = (userKpiRequestMapper.getMonth1ActualCompletedValue() +
                    userKpiRequestMapper.getMonth2ActualCompletedValue() +
                    userKpiRequestMapper.getMonth3ActualCompletedValue());

            userKpiLink.setActualCompletedValue(actualCompletedValue);
            userKpiLink.setActualCmpltValueAddedBy(userKpiRequestMapper.getActualCmpltValueAddedBy());

            resultList = getKpiByUserKpiLinkId(userKpiLinkRepository.save(userKpiLink).getUserKpiLinkId());

            String assignedTo = userKpiLink.getEmployeeId();
            String name = getEmployeeFullName(userKpiLink.getActualCmpltValueAddedBy());
            String msg = "Kpi Actual Completed Value Changed By" + name;

            notificationService.addToAllNotiTable(userKpiLink.getActualCmpltValueAddedBy(), "Kpi Actual Completed Value Changed ", assignedTo, userKpiLink.getOrgId(), msg);
        }
        return resultList;
    }

    @Override
    public String hardDeleteEmployeeByUserId(String userId) {
        String msg = null;
        if (null != userId) {
            EmployeeDetails emp = employeeRepository.getSuspendEmployeeDetailsByEmployeeId(userId);
            if (null != emp) {
                employeeRepository.delete(emp);
                UserSettings userSettings = userSettingsRepository.getUserSettingsByUserId(userId);
                if (null != userSettings) {
                    userSettingsRepository.delete(userSettings);
                }
                msg = "This Employee Deleted Successfully";
            }else {
           	 msg = "This Employee Not Deleted ";
            }
        }else {
        	 msg = "Please provide userId ";
        }
        return msg;
    }

    @Override
    public void deleteKpiByEmployeeKpiLinkId(String employeeKpiLinkId) {
        UserKpiLink userKpiLink = userKpiLinkRepository.getById(employeeKpiLinkId);
        if (null != userKpiLink) {
            userKpiLink.setLiveInd(false);
            userKpiLinkRepository.save(userKpiLink);
        }
    }


    @Override
    public List<String> addFullName(String orgIdFromToken) {
        List<String> list = new ArrayList<>();

        List<EmployeeDetails> employeeDetails = employeeRepository.getEmployeesByOrgIdAndLiveInd(orgIdFromToken);
        if (null != employeeDetails && !employeeDetails.isEmpty()) {
            for (EmployeeDetails li : employeeDetails) {
                if (null == li.getFullName() || li.getFullName().isEmpty()) {
                    String name = getEmployeeFullName(li.getEmployeeId());
                    li.setFullName(name);
                    employeeRepository.save(li);
                    list.add(name);
                }
            }

        }

        return list;
    }

    @Override
    public List<EmployeeWorkflowAndStageResponseMapper> addWorkflowWithEmployee(
            EmployeeWorkflowReqestMapper employeeWorkflowReqestMapper) {
        List<EmployeeWorkflowAndStageResponseMapper> resultMapper = new ArrayList<>();
        EmployeeWorkflowLink employeeWorkflowLink1 = employeeWorkflowLinkRepository.findByEmployeeIdAndLiveInd(employeeWorkflowReqestMapper.getEmployeeId(), true);
        if (null != employeeWorkflowLink1) {
            employeeWorkflowLink1.setLiveInd(false);
            employeeWorkflowLinkRepository.save(employeeWorkflowLink1);

            EmployeeWorkflowLink employeeWorkflowLink = new EmployeeWorkflowLink();
            employeeWorkflowLink.setCreationDate(new Date());
            employeeWorkflowLink.setUpdationDate(new Date());
            employeeWorkflowLink.setLiveInd(true);
            employeeWorkflowLink.setEmployeeId(employeeWorkflowReqestMapper.getEmployeeId());
            employeeWorkflowLink.setOrgId(employeeWorkflowReqestMapper.getOrgId());
            employeeWorkflowLink.setUserId(employeeWorkflowReqestMapper.getUserId());
            employeeWorkflowLink.setUnboardingWorkflowDetailsId(employeeWorkflowReqestMapper.getUnboardingWorkflowDetailsId());

            List<UnboardingStages> unboardingStages = unboardingStagesRepository
                    .findByUnboardingWorkflowDetailsIdAndLiveInd(employeeWorkflowReqestMapper.getUnboardingWorkflowDetailsId(), true);
            if (null != unboardingStages && !unboardingStages.isEmpty()) {
                List<UnboardingStages> list = unboardingStages.stream()
                        .sorted(Comparator.comparingDouble(UnboardingStages::getProbability))
                        .collect(Collectors.toList());
                if (null != list && !list.isEmpty()) {
                    employeeWorkflowLink.setUnboardingWorkflowStageId(list.get(0).getUnboardingStagesId());
                }
            }

            resultMapper = getWorkflowStagetByEmployeeId(employeeWorkflowLinkRepository.save(employeeWorkflowLink).getEmployeeId());
        } else {
            EmployeeWorkflowLink employeeWorkflowLink = new EmployeeWorkflowLink();
            employeeWorkflowLink.setCreationDate(new Date());
            employeeWorkflowLink.setUpdationDate(new Date());
            employeeWorkflowLink.setLiveInd(true);
            employeeWorkflowLink.setEmployeeId(employeeWorkflowReqestMapper.getEmployeeId());
            employeeWorkflowLink.setOrgId(employeeWorkflowReqestMapper.getOrgId());
            employeeWorkflowLink.setUserId(employeeWorkflowReqestMapper.getUserId());
            employeeWorkflowLink.setUnboardingWorkflowDetailsId(employeeWorkflowReqestMapper.getUnboardingWorkflowDetailsId());

            List<UnboardingStages> unboardingStages = unboardingStagesRepository
                    .findByUnboardingWorkflowDetailsIdAndLiveInd(employeeWorkflowReqestMapper.getUnboardingWorkflowDetailsId(), true);
            if (null != unboardingStages && !unboardingStages.isEmpty()) {
                List<UnboardingStages> list = unboardingStages.stream()
                        .sorted(Comparator.comparingDouble(UnboardingStages::getProbability))
                        .collect(Collectors.toList());
                if (null != list && !list.isEmpty()) {
                    employeeWorkflowLink.setUnboardingWorkflowStageId(list.get(0).getUnboardingStagesId());
                }
            }

            resultMapper = getWorkflowStagetByEmployeeId(employeeWorkflowLinkRepository.save(employeeWorkflowLink).getEmployeeId());
        }

        return resultMapper;
    }

    @Override
    public EmployeeWorkflowAndStageResponseMapper changeStageWithEmployee(
            EmployeeWorkflowReqestMapper employeeWorkflowReqestMapper) {
        EmployeeWorkflowAndStageResponseMapper resultMapper = new EmployeeWorkflowAndStageResponseMapper();
        EmployeeWorkflowLink employeeWorkflowLink1 = employeeWorkflowLinkRepository.
                findByEmployeeIdAndUnboardingWorkflowDetailsIdAndLiveInd(employeeWorkflowReqestMapper.getEmployeeId(),
                        employeeWorkflowReqestMapper.getUnboardingWorkflowDetailsId(), true);
        if (null != employeeWorkflowLink1) {
            employeeWorkflowLink1.setUnboardingWorkflowStageId(employeeWorkflowReqestMapper.getUnboardingStagesId());
            employeeWorkflowLink1.setUpdationDate(new Date());
            employeeWorkflowLink1.setOrgId(employeeWorkflowReqestMapper.getOrgId());
            employeeWorkflowLink1.setUserId(employeeWorkflowReqestMapper.getUserId());

            resultMapper = getWorkflowStageByEmployeeWorkflowLinkId(employeeWorkflowLinkRepository.save(employeeWorkflowLink1).getEmployeeWorkflowLinkId());
        }
        return resultMapper;
    }

    @Override
    public EmployeeWorkflowAndStageResponseMapper getWorkflowStageByEmployeeWorkflowLinkId(String employeeWorkflowLinkId) {
        EmployeeWorkflowAndStageResponseMapper responseMapper = new EmployeeWorkflowAndStageResponseMapper();
        EmployeeWorkflowLink employeeWorkflowLink = employeeWorkflowLinkRepository.findByEmployeeWorkflowLinkIdAndLiveInd(employeeWorkflowLinkId, true);
        if (null != employeeWorkflowLink) {
            UnboardingStages li = unboardingStagesRepository
                    .getUnboardingStagesByUnboardingStagesId(employeeWorkflowLink.getUnboardingWorkflowStageId());
            if (null != li) {

                responseMapper.setUnboardingStagesId(li.getUnboardingStagesId());
                responseMapper.setStageName(li.getStageName());
                responseMapper.setPublishInd(li.isPublishInd());
                responseMapper.setProbability(li.getProbability());
                responseMapper.setDays(li.getDays());
                responseMapper.setCreationDate(Utility.getISOFromDate(employeeWorkflowLink.getCreationDate()));

                responseMapper.setUserId(employeeWorkflowLink.getUserId());
                responseMapper.setUpdationDate(Utility.getISOFromDate(employeeWorkflowLink.getUpdationDate()));
                responseMapper.setUpdatedBy(getEmployeeFullName(employeeWorkflowLink.getUserId()));
                responseMapper.setOrgId(employeeWorkflowLink.getOrgId());
                responseMapper.setLiveInd(employeeWorkflowLink.isLiveInd());

                UnboardingWorkflowDetails db = unboardingWorkflowDetailsRepository
                        .getUnboardingWorkflowDetailsByUnboardingWorkflowDetailsId(employeeWorkflowLink.getUnboardingWorkflowDetailsId());
                if (null != db) {
                    responseMapper.setUnboardingWorkflowDetailsName(db.getWorkflowName());
                    responseMapper.setUnboardingWorkflowDetailsId(db.getUnboardingWorkflowDetailsId());
                }

                responseMapper.setOnboardingEmpId(employeeWorkflowLink.getEmployeeId());
                responseMapper.setOnboardingEmpName(getEmployeeFullName(employeeWorkflowLink.getEmployeeId()));


//					if (null != resultMapper && !resultMapper.isEmpty()) {
//
//						Collections.sort(resultMapper,
//								( c1,  c2) -> Double.compare(c1.getProbability(), c2.getProbability()));
//					}
            }

        }

        return responseMapper;
    }

    @Override
    public List<EmployeeWorkflowAndStageResponseMapper> getWorkflowStagetByEmployeeId(String employeeId) {
        List<EmployeeWorkflowAndStageResponseMapper> resultMapper = new ArrayList<>();
        EmployeeWorkflowLink employeeWorkflowLink = employeeWorkflowLinkRepository.findByEmployeeIdAndLiveInd(employeeId, true);
        if (null != employeeWorkflowLink) {
            List<UnboardingStages> list = unboardingStagesRepository
                    .findByUnboardingWorkflowDetailsIdAndLiveInd(employeeWorkflowLink.getUnboardingWorkflowDetailsId(), true);
            if (null != list && !list.isEmpty()) {
                list.stream().map(li -> {
                    if (!li.getStageName().equalsIgnoreCase("Lost")) {
                        if (!li.getStageName().equalsIgnoreCase("Won")) {
                            EmployeeWorkflowAndStageResponseMapper responseMapper = new EmployeeWorkflowAndStageResponseMapper();
                            responseMapper.setUnboardingStagesId(li.getUnboardingStagesId());
                            responseMapper.setStageName(li.getStageName());
                            responseMapper.setPublishInd(li.isPublishInd());
                            responseMapper.setProbability(li.getProbability());
                            responseMapper.setDays(li.getDays());
                            responseMapper.setCreationDate(Utility.getISOFromDate(employeeWorkflowLink.getCreationDate()));

                            responseMapper.setUserId(employeeWorkflowLink.getUserId());
                            responseMapper.setUpdationDate(Utility.getISOFromDate(employeeWorkflowLink.getUpdationDate()));
                            responseMapper.setUpdatedBy(getEmployeeFullName(employeeWorkflowLink.getUserId()));
                            responseMapper.setOrgId(employeeWorkflowLink.getOrgId());
                            responseMapper.setLiveInd(employeeWorkflowLink.isLiveInd());

                            UnboardingWorkflowDetails db = unboardingWorkflowDetailsRepository
                                    .getUnboardingWorkflowDetailsByUnboardingWorkflowDetailsId(employeeWorkflowLink.getUnboardingWorkflowDetailsId());
                            if (null != db) {
                                responseMapper.setUnboardingWorkflowDetailsName(db.getWorkflowName());
                                responseMapper.setUnboardingWorkflowDetailsId(db.getUnboardingWorkflowDetailsId());
                            }


                            if (li.getUnboardingStagesId().equalsIgnoreCase(employeeWorkflowLink.getUnboardingWorkflowStageId())) {
                                responseMapper.setOnboardingEmpId(employeeWorkflowLink.getEmployeeId());
                                responseMapper.setOnboardingEmpName(getEmployeeFullName(employeeWorkflowLink.getEmployeeId()));
                            }


                            resultMapper.add(responseMapper);

                        }
                    }
                    if (null != resultMapper && !resultMapper.isEmpty()) {

                        Collections.sort(resultMapper,
                                (c1, c2) -> Double.compare(c1.getProbability(), c2.getProbability()));
                    }
                    return resultMapper;
                }).collect(Collectors.toList());
            }

        }

        return resultMapper;
    }

    @Override
    public boolean emailExistsInEmployeeByEmployeeId(String emailId, String employeeId) {
        EmployeeDetails emp = employeeRepository.getEmployeeByMailIdAndEmployeeId(emailId.toLowerCase(), employeeId);
        if (null != emp) {
            return true;
        }
        return false;
    }


    @Override
    public String emailExistsInEmployee(String emailId) {
        EmployeeDetails emp = employeeRepository.getEmailByEmailId(emailId);
        if (null != emp) {
            return emp.getFullName();
        }
        return null;
    }


    @Override
    public UserSalaryBreakoutMapper getSalaryBreckOutByEmployeeId(String employeeId) {
        UserSalaryBreakoutMapper mapper = new UserSalaryBreakoutMapper();
        double salary = 0;
        double basic = 0;
        double transport = 0;
        double house = 0;
        double others = 0;

        EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(employeeId, true);
        if (null != employeeDetails) {
            UserSalaryBreakout userSalaryBreakout1 = userSalaryBreakoutRepository
                    .findByDepartmentIdAndRoleTypeId(employeeDetails.getDepartment(), employeeDetails.getRoleType());
            if (null != userSalaryBreakout1) {
                salary = employeeDetails.getSalary();
                basic = ((userSalaryBreakout1.getBasic() / 100) * (salary));
                transport = ((userSalaryBreakout1.getTransportation() / 100) * (salary));
                house = ((userSalaryBreakout1.getHousing() / 100) * (salary));
                others = ((userSalaryBreakout1.getOthers() / 100) * (salary));

                mapper.setTotalSalary(employeeDetails.getSalary());
                mapper.setBasic(basic);
                mapper.setTransportation(transport);
                mapper.setHousing(house);
                mapper.setOthers(others);
                mapper.setUserId(employeeId);
                Currency currency = currencyRepository.getByCurrencyId(employeeDetails.getCurrency());
                if (null != currency) {
                    mapper.setCurrency(currency.getCurrencyName());
                }

            }
        }
        return mapper;
    }


    @Override
    public List<EmployeeShortMapper> getEmployeeListByLocationIdAndDepartmentIdForDropDown(String locationId,
                                                                                           String departmentId) {
        List<EmployeeShortMapper> resultList = new ArrayList<>();

        List<EmployeeDetails> employeeList = employeeRepository.getEmployeeListByDepartmentIdAndLocationId(departmentId, locationId);
        System.out.println("###########" + employeeList.toString());
        if (null != employeeList && !employeeList.isEmpty()) {
            employeeList.stream().map(employee -> {
                EmployeeShortMapper employeeMapper = getEmployeeFullNameAndEmployeeId(employee.getEmployeeId());
                resultList.add(employeeMapper);
                return employeeMapper;
            }).collect(Collectors.toList());
        }
        return resultList;
    }


    @Override
    public List<UserKpiLobMapper> getKpiTargetByLobByEmployeeIdMonthWise(String employeeId, String orgId) {
        List<UserKpiLobMapper> resultList = new ArrayList<>();

//		List<LobDetails> list = lobRepository.findByOrgIdAndLiveInd(orgId, true);
//		if (null != list && !list.isEmpty()) {
//			list.stream().map(li -> {
//				UserKpiLobMapper mapper = new UserKpiLobMapper();
//				
//				mapper.setLobId(li.getLobDetailsId());)
//			mapper.setLobName(li.get);
//				
//				resultList.add(mapper);
//				return mapper;
//		}).collect(Collectors.toList());
//		}

        return resultList;
    }

    @Override
    public List<UserEquipmentMapper> createUserEquipment(List<UserEquipmentMapper> requestMapper) {
        List<UserEquipmentLink> db = userEquipmentLinkRepository
                .findByUserIdAndLiveInd(requestMapper.get(0).getUserId(), true);
        List<UserEquipmentMapper> resultmapper = new ArrayList<>();
        if (null != db) {
            userEquipmentLinkRepository.deleteAll(db);
        }
        if (null != requestMapper && !requestMapper.isEmpty()) {
            for (UserEquipmentMapper mapper : requestMapper) {

                UserEquipmentLink newdb = new UserEquipmentLink();
                newdb.setCreationDate(new Date());
                newdb.setEquipmentId(mapper.getEquipmentName());
                newdb.setOrgId(mapper.getOrgId());
                newdb.setUserId(mapper.getUserId());
                newdb.setValue(mapper.getValue());
                newdb.setLiveInd(true);
                userEquipmentLinkRepository.save(newdb);
            }
            resultmapper = getEmployeeEquipmentByUserId(requestMapper.get(0).getUserId());

        }
        return resultmapper;

    }

    private UserEquipmentMapper getUserEquipmentMapperById(String id) {
        UserEquipmentLink db = userEquipmentLinkRepository.findByUserEquipmentLinkId(id);
        UserEquipmentMapper resultMapper = new UserEquipmentMapper();
        if (null != db) {
            resultMapper.setCreationDate(Utility.getISOFromDate(db.getCreationDate()));
            resultMapper.setLiveInd(db.isLiveInd());
            resultMapper.setOrgId(db.getOrgId());
            resultMapper.setUserEquipmentLinkId(id);
            resultMapper.setUserId(db.getUserId());
            resultMapper.setValue(db.getValue());
            if (!StringUtils.isEmpty(db.getEquipmentId())) {
                Equipment equipment = equipmentRepository.findByEquipmentIdAndLiveInd(db.getEquipmentId(), true);
                if (null != equipment) {
                    resultMapper.setEquipmentId(equipment.getEquipmentId());
                    resultMapper.setEquipmentName(equipment.getName());
                }
            }
        }
        return resultMapper;
    }


    @Override
    public List<UserEquipmentMapper> getEmployeeEquipmentByUserId(String userId) {

        List<UserEquipmentMapper> resultMapper = new ArrayList<>();
        List<UserEquipmentLink> list = userEquipmentLinkRepository.findByUserIdAndLiveInd(userId, true);
        if (null != list) {
            resultMapper = list.stream().map(li -> getUserEquipmentMapperById(li.getUserEquipmentLinkId()))
                    .collect(Collectors.toList());
        }
        return resultMapper;
    }


    @Override
    public HashMap linkEmailInUser(EmployeeEmailLinkMapper requestMapper) {
        HashMap map = new HashMap();
        UserSettings list = userSettingsRepository.getUserSettingsByEmail(requestMapper.getPrimaryEmailId(), true);
        if (null != list) {
            UserSettings list1 = userSettingsRepository.getUserSettingsByEmail(requestMapper.getSecondaryEmailId(), true);
            if (null != list1) {
                List<EmployeeEmailLink> employeeEmailLink = employeeEmailLinkRepository.findByEmail(requestMapper.getPrimaryEmailId());
                if (null == employeeEmailLink || employeeEmailLink.isEmpty()) {
                    List<EmployeeEmailLink> employeeEmailLink1 = employeeEmailLinkRepository.findByEmail(requestMapper.getSecondaryEmailId());
                    if (null == employeeEmailLink1 || employeeEmailLink1.isEmpty()) {
                        EmployeeEmailLink employeeEmailLink2 = new EmployeeEmailLink();
                        employeeEmailLink2.setCreationDate(new Date());
                        employeeEmailLink2.setEmployeeId(requestMapper.getEmployeeId());
                        employeeEmailLink2.setLiveInd(true);
                        employeeEmailLink2.setOrgId(requestMapper.getOrgId());
                        employeeEmailLink2.setPrimaryEmailId(requestMapper.getPrimaryEmailId());
                        employeeEmailLink2.setSecondaryEmailId(requestMapper.getSecondaryEmailId());
                        employeeEmailLink2.setUserId(requestMapper.getUserId());
                        employeeEmailLinkRepository.save(employeeEmailLink2);

                        EmployeeDetails employeeList = employeeRepository.getEmployeeByMailId(requestMapper.getPrimaryEmailId());
                        if (null != employeeList) {
                            employeeList.setMultyOrgLinkInd(true);
                        }
                        EmployeeDetails employeeList1 = employeeRepository.getEmployeeByMailId(requestMapper.getSecondaryEmailId());
                        if (null != employeeList1) {
                            employeeList.setMultyOrgLinkInd(true);
                        }
                        map.put("multyOrgLinkInd", true);
                        map.put("message", "Email linked Successfully");

                    } else {
                        map.put("multyOrgLinkInd", true);
                        map.put("message", "Email" + requestMapper.getSecondaryEmailId() + "has already linked");
                    }
                } else {
                    map.put("multyOrgLinkInd", true);
                    map.put("message", "Email" + requestMapper.getPrimaryEmailId() + "has already linked");
                }
            } else {
                map.put("multyOrgLinkInd", false);
                map.put("message", "Email" + requestMapper.getSecondaryEmailId() + "has not present in our database");
            }
        } else {
            map.put("multyOrgLinkInd", false);
            map.put("message", "Email" + requestMapper.getPrimaryEmailId() + "has not present in our database");
        }

        return map;
    }

    @Override
    public String deleteNotes(NotesMapper notesMapper) {
        Notes notes = notesRepository.findByNoteId(notesMapper.getNotesId());
        String msg = "Something went Worng !!!";
        if (null != notes) {
            msg = "Note deleted successfully";
            notes.setLiveInd(false);
            notesRepository.save(notes);

            if (notesMapper.getType().equalsIgnoreCase("call")) {
                CallNotesLink callNotesLink = callNotesLinkRepository.findByNotesId(notesMapper.getNotesId());
                callNotesLink.setLiveInd(false);
                callNotesLinkRepository.save(callNotesLink);
            }
            if (notesMapper.getType().equalsIgnoreCase("contact")) {
                ContactNotesLink contactNotesLink = contactNotesLinkRepository
                        .findByNotesId(notesMapper.getNotesId());
                contactNotesLink.setLiveInd(false);
                contactNotesLinkRepository.save(contactNotesLink);
            }
            if (notesMapper.getType().equalsIgnoreCase("opportunity")) {
                OpportunityNotesLink opportunityNotesLink = opportunityNotesLinkRepository
                        .findByNotesId(notesMapper.getNotesId());
                opportunityNotesLink.setLiveInd(false);
                opportunityNotesLinkRepository.save(opportunityNotesLink);
            }
            if (notesMapper.getType().equalsIgnoreCase("customer")) {
                CustomerNotesLink customerNotesLink = customerNotesLinkRepository
                        .findByNotesId(notesMapper.getNotesId());
                customerNotesLink.setLiveInd(false);
                customerNotesLinkRepository.save(customerNotesLink);
            }
            if (notesMapper.getType().equalsIgnoreCase("employee")) {
                EmployeeNotesLink link = employeeNotesLinkRepository.findByNotesId(notesMapper.getNotesId());
                link.setLiveInd(false);
                employeeNotesLinkRepository.save(link);
            }

            if (notesMapper.getType().equalsIgnoreCase("event")) {
                EventNotesLink eventNotesLink = eventNotesLinkRepository.findByNotesId(notesMapper.getNotesId());
                eventNotesLink.setLiveInd(false);
                eventNotesLinkRepository.save(eventNotesLink);
            }

            if (notesMapper.getType().equalsIgnoreCase("expense")) {
                ExpenseNotesLink leaveNotesLink = expenseNotesLinkRepository.findByNoteId(notesMapper.getNotesId());
                leaveNotesLink.setLiveInd(false);
                expenseNotesLinkRepository.save(leaveNotesLink);
            }

            if (notesMapper.getType().equalsIgnoreCase("investor")) {
                InvestorNoteLink customerNotesLink = investorNotesLinkRepo.findByNoteId(notesMapper.getNotesId());
                customerNotesLink.setLiveInd(false);
                investorNotesLinkRepo.save(customerNotesLink);
            }

            if (notesMapper.getType().equalsIgnoreCase("investorLead")) {
                InvestorLeadsNotesLink investorLeadsNotesLink = investorLeadsNotesLinkRepository
                        .findByNoteId(notesMapper.getNotesId());
                investorLeadsNotesLink.setLiveInd(false);
                investorLeadsNotesLinkRepository.save(investorLeadsNotesLink);
            }

            if (notesMapper.getType().equalsIgnoreCase("lead")) {
                LeadsNotesLink leadsNotesLink = leadsNotesLinkRepository.findByNotesId(notesMapper.getNotesId());
                leadsNotesLink.setLiveInd(false);
                leadsNotesLinkRepository.save(leadsNotesLink);
            }

            if (notesMapper.getType().equalsIgnoreCase("leave")) {
                LeaveNotesLink leaveNotesLink = leaveNotesLinkRepository.findByNoteId(notesMapper.getNotesId());
                leaveNotesLink.setLiveInd(false);
                leaveNotesLinkRepository.save(leaveNotesLink);
            }

            if (notesMapper.getType().equalsIgnoreCase("mileage")) {
                MileageNotesLink mileageNotesLink = mileageNotesLinkRepository.findByNoteId(notesMapper.getNotesId());
                mileageNotesLink.setLiveInd(false);
                mileageNotesLinkRepository.save(mileageNotesLink);
            }

            if (notesMapper.getType().equalsIgnoreCase("partner")) {
                PartnerNotesLink partnerNotesLink = partnerNotesLinkRepository.findByNoteId(notesMapper.getNotesId());
                partnerNotesLink.setLiveInd(false);
                partnerNotesLinkRepository.save(partnerNotesLink);
            }

            if (notesMapper.getType().equalsIgnoreCase("task")) {
                TaskNotesLink taskNotesLink = taskNotesLinkRepository.findByNotesId(notesMapper.getNotesId());
                taskNotesLink.setLiveInd(false);
                taskNotesLinkRepository.save(taskNotesLink);
            }

        }
        return msg;
    }


    @Override
    public EmployeeTableMapper UpdateMultyOrgAccessInd(String employeeId, boolean multyOrgAccessInd) {
        EmployeeTableMapper mapper = new EmployeeTableMapper();
        EmployeeDetails details = employeeRepository.getEmployeeDetailsByEmployeeIdAndLiveInd(employeeId);
        if (null != details) {

            details.setMultyOrgAccessInd(multyOrgAccessInd);
            employeeRepository.save(details);
            mapper = getEmployeeDetail(details);
        }
        return mapper;

    }


    @Override
    public List<EmployeeShortMapper> getEmployeeListByOrgIdAndTypeForDropDown(String orgId, String type) {
        List<EmployeeShortMapper> list = new ArrayList<>();
        List<EmployeeDetails> employeeList = employeeRepository.getEmployeesByOrgIdAndType(orgId, type);
        System.out.println("###########" + employeeList.toString());
        if (null != employeeList && !employeeList.isEmpty()) {
            return employeeList.stream().map(employee -> {
                EmployeeShortMapper employeeMapper = getEmployeeFullNameAndEmployeeId(employee.getEmployeeId());
                return employeeMapper;
            }).collect(Collectors.toList());
        }
        return list;
    }


	@Override
	public List<EmployeeTableMapper> getAllEmployeeDetailsByName(String name, String orgId) {
		List<EmployeeDetails> detailsList = employeeRepository.getEmployeesByOrgIdAndLiveInd(orgId);
        List<EmployeeDetails> filterList = detailsList.parallelStream()
                .filter(detail -> {
                    return detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim());
                })
                .collect(Collectors.toList());
        List<EmployeeTableMapper> mapperList = new ArrayList<EmployeeTableMapper>();
        if (null != filterList && !filterList.isEmpty()) {

            mapperList = filterList.parallelStream().map(li -> getEmployeeDetailByEmployeeId(li.getEmployeeId()))
                    .collect(Collectors.toList());
        }
        return mapperList;
	}

	@Override
	public List<EmployeeTableMapper> getActiveEmployeeDetailsByName(String name, String orgId) {
		List<EmployeeDetails> detailsList = employeeRepository.getEmployeesByOrgId(orgId);
        List<EmployeeDetails> filterList = detailsList.parallelStream()
                .filter(detail -> {
                    return detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim());
                })
                .collect(Collectors.toList());
        List<EmployeeTableMapper> mapperList = new ArrayList<EmployeeTableMapper>();
        if (null != filterList && !filterList.isEmpty()) {

            mapperList = filterList.parallelStream().map(li -> getEmployeeDetailByEmployeeId(li.getEmployeeId()))
                    .collect(Collectors.toList());
        }
        return mapperList;
	}


	@Override
	public List<NotesMapper> getNotesByTypeAndUniqueId(String type, String uniqueId) {
		List<NotesMapper> response = new ArrayList<>();
		if (type.equalsIgnoreCase("call")) {
             List<CallNotesLink> linkList = callNotesLinkRepository.getNoteByCallId(uniqueId);
            
             response = linkList.stream().map(link -> {
                 NotesMapper notesMapper = getNotes(link.getNotesId());
                 return notesMapper;
             }).collect(Collectors.toList());
         }
         if (type.equalsIgnoreCase("contact")) {
        	 List<ContactNotesLink> linkList = contactNotesLinkRepository.getNoteListByContactId(uniqueId);
            		 response = linkList.stream().map(link -> {
                         NotesMapper notesMapper = getNotes(link.getNotesId());
                         return notesMapper;
                     }).collect(Collectors.toList());
         }
         if (type.equalsIgnoreCase("opportunity")) {
             List<OpportunityNotesLink> linkList = opportunityNotesLinkRepository.getNoteListByOpportunityId(uniqueId);
             response = linkList.stream().map(link -> {
                         NotesMapper notesMapper = getNotes(link.getNotesId());
                         return notesMapper;
                     }).collect(Collectors.toList());
         }
         if (type.equalsIgnoreCase("customer")) {
        	 List<CustomerNotesLink> linkList = customerNotesLinkRepository.getNotesIdByCustomerId(uniqueId);
        	 response = linkList.stream().map(link -> {
                         NotesMapper notesMapper = getNotes(link.getNotesId());
                         return notesMapper;
                     }).collect(Collectors.toList());
         }
         if (type.equalsIgnoreCase("employee")) {
        	 List<EmployeeNotesLink> linkList = employeeNotesLinkRepository.getNotesIdByEmployeeId(uniqueId);
             response = linkList.stream().map(link -> {
                 NotesMapper notesMapper = getNotes(link.getNotesId());
                 return notesMapper;
             }).collect(Collectors.toList());
         }

         if (type.equalsIgnoreCase("event")) {
        	 List<EventNotesLink> linkList = eventNotesLinkRepository.getNoteByEventId(uniqueId);
             response = linkList.stream().map(link -> {
                 NotesMapper notesMapper = getNotes(link.getNotesId());
                 return notesMapper;
             }).collect(Collectors.toList());
         }

         if (type.equalsIgnoreCase("expense")) {
        	 List<ExpenseNotesLink> linkList = expenseNotesLinkRepository.getNotesIdByExpenseId(uniqueId);
             response = linkList.stream().map(link -> {
                 NotesMapper notesMapper = getNotes(link.getNoteId());
                 return notesMapper;
             }).collect(Collectors.toList());
         }

         if (type.equalsIgnoreCase("investor")) {
        	 List<InvestorNoteLink> linkList = investorNotesLinkRepo.getNotesIdByInvestorId(uniqueId);
             response = linkList.stream().map(link -> {
                 NotesMapper notesMapper = getNotes(link.getNoteId());
                 return notesMapper;
             }).collect(Collectors.toList());
         }

         if (type.equalsIgnoreCase("investorLead")) {
        	 List<InvestorLeadsNotesLink> linkList = investorLeadsNotesLinkRepository.getNotesIdByInvestorLeadsId(uniqueId);
        	 response = linkList.stream().map(link -> {
                         NotesMapper notesMapper = getNotes(link.getNoteId());
                         return notesMapper;
                     }).collect(Collectors.toList());
         }

         if (type.equalsIgnoreCase("lead")) {
        	 List<LeadsNotesLink> linkList = leadsNotesLinkRepository.getNotesIdByLeadsId(uniqueId);
             response = linkList.stream().map(link -> {
                 NotesMapper notesMapper = getNotes(link.getNotesId());
                 return notesMapper;
             }).collect(Collectors.toList());
         }

         if (type.equalsIgnoreCase("leave")) {
        	 List<LeaveNotesLink> linkList = leaveNotesLinkRepository.getNotesIdByLeaveId(uniqueId);
             response = linkList.stream().map(link -> {
                 NotesMapper notesMapper = getNotes(link.getNoteId());
                 return notesMapper;
             }).collect(Collectors.toList());
         }

         if (type.equalsIgnoreCase("mileage")) {
        	 List<MileageNotesLink> linkList = mileageNotesLinkRepository.getNotesIdByMileageId(uniqueId);
             response = linkList.stream().map(link -> {
                 NotesMapper notesMapper = getNotes(link.getNoteId());
                 return notesMapper;
             }).collect(Collectors.toList());
         }

         if (type.equalsIgnoreCase("partner")) {
        	 List<PartnerNotesLink> linkList = partnerNotesLinkRepository.getNotesByPartnerId(uniqueId);
             response = linkList.stream().map(link -> {
                 NotesMapper notesMapper = getNotes(link.getNoteId());
                 return notesMapper;
             }).collect(Collectors.toList());
         }

         if (type.equalsIgnoreCase("task")) {
        	 List<TaskNotesLink> linkList = taskNotesLinkRepository.getNotesIdByTaskId(uniqueId);
             response = linkList.stream().map(link -> {
                 NotesMapper notesMapper = getNotes(link.getNotesId());
                 return notesMapper;
             }).collect(Collectors.toList());
         }
		return response;

     }


	@Override
	public List<EmployeeShortMapper> getEmployeeListByDepartmentIdAndRoletypeId(String departmentId,
			String roletypetypeId) {
        List<EmployeeShortMapper> list = new ArrayList<>();
        List<EmployeeDetails> employeeList = employeeRepository.findByDepartmentAndRoleTypeAndLiveInd(departmentId, roletypetypeId,true);
        if (null != employeeList && !employeeList.isEmpty()) {
            return employeeList.stream().map(employee -> {
                EmployeeShortMapper employeeMapper = getEmployeeFullNameAndEmployeeId(employee.getEmployeeId());
                return employeeMapper;
            }).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public AddressMapper getLocationAddress(String userId) {
        AddressMapper mapper=new AddressMapper();
//        return addressRepository.findById(locationDetailsRepository.findById(employeeRepository.findbyEmployeeId(userId).getLocation()).get().getAddressId()).map(a->addressService.getAddressDetails(a.getAddressId()));
        EmployeeDetails employeeDetails= employeeRepository.findByEmployeeId(userId);
        if(null!=employeeDetails&&!StringUtils.isEmpty(employeeDetails.getLocation())){
            LocationDetails locationDetails=locationDetailsRepository.findById(employeeDetails.getLocation()).orElse(null);
            if(null!=locationDetails&&!StringUtils.isEmpty(locationDetails.getAddressId())){
                AddressDetails addressDetails=addressRepository.getById(locationDetails.getAddressId());
                if(null!=addressDetails){
                    mapper=addressService.getAddressDetails(addressDetails.getAddressId());
                }
            }
        }
        return mapper;
    }


}
