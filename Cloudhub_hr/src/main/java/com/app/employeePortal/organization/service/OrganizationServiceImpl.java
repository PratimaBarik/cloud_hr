package com.app.employeePortal.organization.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.app.employeePortal.address.entity.AddressDetails;
import com.app.employeePortal.address.entity.AddressInfo;
import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.address.repository.AddressInfoRepository;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.category.entity.Industry;
import com.app.employeePortal.category.repository.IndustryRepository;
import com.app.employeePortal.config.AesEncryptor;
import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.entity.DocumentType;
import com.app.employeePortal.document.repository.DocumentDetailsRepository;
import com.app.employeePortal.document.repository.DocumentTypeRepository;
import com.app.employeePortal.email.service.EmailService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.organization.entity.InvoiceToUser;
import com.app.employeePortal.organization.entity.OrgIndustry;
import com.app.employeePortal.organization.entity.OrganizationAddressLink;
import com.app.employeePortal.organization.entity.OrganizationCurrencyLink;
import com.app.employeePortal.organization.entity.OrganizationDetails;
import com.app.employeePortal.organization.entity.OrganizationDocumentLink;
import com.app.employeePortal.organization.entity.OrganizationInfo;
import com.app.employeePortal.organization.entity.OrganizationPayments;
import com.app.employeePortal.organization.entity.OrganizationSubsriptionDetails;
import com.app.employeePortal.organization.entity.RepositoryIncludeLink;
import com.app.employeePortal.organization.entity.SubsriptionPrice;
import com.app.employeePortal.organization.mapper.FiscalMapper;
import com.app.employeePortal.organization.mapper.InvoiceToUserMapper;
import com.app.employeePortal.organization.mapper.OrgIndustryMapper;
import com.app.employeePortal.organization.mapper.OrganizationCurrencyLinkMapper;
import com.app.employeePortal.organization.mapper.OrganizationDocumentLinkMapper;
import com.app.employeePortal.organization.mapper.OrganizationMapper;
import com.app.employeePortal.organization.mapper.OrganizationPaymentMapper;
import com.app.employeePortal.organization.mapper.OrganizationSubscriptionMapper;
import com.app.employeePortal.organization.repository.InvoiceToUserRepository;
import com.app.employeePortal.organization.repository.OrgIndustryRepository;
import com.app.employeePortal.organization.repository.OrganizationAddressLinkRepository;
import com.app.employeePortal.organization.repository.OrganizationCurrencyLinkRepository;
import com.app.employeePortal.organization.repository.OrganizationDocumentLinkRepository;
import com.app.employeePortal.organization.repository.OrganizationInfoRepository;
import com.app.employeePortal.organization.repository.OrganizationPaymentRep;
import com.app.employeePortal.organization.repository.OrganizationRepository;
import com.app.employeePortal.organization.repository.OrganizationSubsriptionDetailsRepository;
import com.app.employeePortal.organization.repository.RepositoryIncludeLinkRepository;
import com.app.employeePortal.organization.repository.SubscriptionPriceRepository;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.mapper.NewAdminRegisterMapper;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.util.Utility;

import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    OrganizationInfoRepository organizationInfoRepository;

    @Autowired
    OrganizationAddressLinkRepository organizationAddressLinkRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    AddressInfoRepository addressInfoRepository;

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    OrganizationSubsriptionDetailsRepository organizationSubsriptionDetailsRepository;
    @Autowired
    DocumentDetailsRepository documentDetailsRepository;
    @Autowired
    OrganizationDocumentLinkRepository organizationDocumentLinkRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    DocumentTypeRepository documentTypeRepository;
    @Autowired
    OrganizationCurrencyLinkRepository organizationCurrencyLinkRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    RepositoryIncludeLinkRepository repositoryIncludeLinkRepo;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    IndustryRepository industryRepository;
    @Autowired
    OrgIndustryRepository orgIndustryRepository;
    @Autowired
    InvoiceToUserRepository invoiceToUserRepository;
    @Autowired
    SubscriptionPriceRepository subscriptionPriceRepo;
    @Autowired
    InvoiceToUserRepository invoiceToUserRepo;
    @Autowired
    EmailService emailService;
    @Value("${companyName}")
    private String companyName;
    @Autowired
    NotificationService notificationService;
    @Autowired
    OrganizationPaymentRep organizationPaymentRep;

    @Override
    public OrganizationMapper saveToOrganizationProcess(OrganizationMapper organizationMapper) {
        String organizationId = null;

        OrganizationInfo organizationInfo = new OrganizationInfo();
        organizationInfo.setCreator_id(organizationMapper.getCreatorId());
        organizationInfo.setCreation_date(new Date());
        OrganizationInfo organizationInfoo = organizationInfoRepository.save(organizationInfo);
        organizationId = organizationInfoo.getOrganization_id();

        if (null != organizationId) {

            OrganizationDetails organizationDetails = new OrganizationDetails();
            organizationDetails.setOrganization_id(organizationId);
            organizationDetails.setBase_country(organizationMapper.getBaseCountry());
            organizationDetails.setCompany_size(organizationMapper.getCompanySize());
            organizationDetails.setCreation_date(new Date());
            organizationDetails.setCreator_id(organizationMapper.getCreatorId());
            organizationDetails.setImage_id(organizationMapper.getImageId());
            organizationDetails.setIndustry_type(organizationMapper.getIndustryType());
            organizationDetails.setName(organizationMapper.getOrganizationName());
            organizationDetails.setTrade_currency(organizationMapper.getTradeCurrency());
            organizationDetails.setUrl(organizationMapper.getOrganizationUrl());
            organizationDetails.setFiscal_start_month(organizationMapper.getFiscalStartMonth());
            organizationDetails.setFiscal_start_date(organizationMapper.getFiscalStartDate());
            organizationDetails.setTwitter(organizationMapper.getTwitter());
            organizationDetails.setFacebook(organizationMapper.getFacebook());
            organizationDetails.setRevenue(organizationMapper.getRevenue());
            organizationDetails.setLinkedin_url(organizationMapper.getLinkedinUrl());
            organizationDetails.setLive_ind(true);
            organizationDetails.setPhone_number(organizationMapper.getPhoneNo());
            organizationDetails.setCountry_code(organizationMapper.getCountryDialCode());
            organizationDetails.setCountry_code1(organizationMapper.getCountryDialCode1());
            organizationDetails.setMobile_number(organizationMapper.getMobileNo());
            organizationDetails.setIndustryId(organizationMapper.getIndustry());
            organizationDetails.setBToBInd(organizationMapper.isBToB());

            OrganizationDetails organization = organizationRepository.findByType("Parent");
            if (null != organization) {
                organizationDetails.setType("Child");
            } else {
                organizationDetails.setType("Parent");
            }
            organizationRepository.save(organizationDetails);
            List<AddressMapper> list = organizationMapper.getAddress();

            if (null != list && !list.isEmpty()) {
                for (AddressMapper mapper : list) {
                    // list.forEach(mapper->{
                    /* insert to AddressInfo */

                    AddressInfo addressInfo = new AddressInfo();
                    addressInfo.setCreationDate(new Date());
                    addressInfo.setCreatorId(mapper.getEmployeeId());

                    AddressInfo addressInfooo = addressInfoRepository.save(addressInfo);
                    String addressInfoId = addressInfooo.getId();
                    if (null != addressInfoId) {

                        AddressDetails addressDetails = new AddressDetails();
                        addressDetails.setAddressId(addressInfoId);
                        addressDetails.setAddressLine1(mapper.getAddress1());
                        addressDetails.setAddressLine2(mapper.getAddress2());
                        addressDetails.setAddressType(mapper.getAddressType());
                        addressDetails.setCountry(mapper.getCountry());
                        addressDetails.setCreationDate(new Date());
                        addressDetails.setCreatorId(mapper.getEmployeeId());
                        addressDetails.setStreet(mapper.getStreet());
                        addressDetails.setCity(mapper.getCity());
                        addressDetails.setPostalCode(mapper.getPostalCode());
                        addressDetails.setTown(mapper.getTown());
                        addressDetails.setState(mapper.getState());
                        addressDetails.setLatitude(mapper.getLatitude());
                        addressDetails.setLongitude(mapper.getLongitude());
                        addressDetails.setLiveInd(true);
                        addressDetails.setHouseNo(mapper.getHouseNo());
                        addressRepository.save(addressDetails);

                    }

                    /* insert to organization address link table */

                    OrganizationAddressLink organizationAddressLink = new OrganizationAddressLink();
                    organizationAddressLink.setAddress_id(addressInfoId);
                    organizationAddressLink.setCreation_date(new Date());
                    organizationAddressLink.setOrganization_id(organizationId);
                    organizationAddressLink.setLive_ind(true);

                    organizationAddressLinkRepository.save(organizationAddressLink);
                }

            }
        }
        return getOrganizationDetails(organizationId);

    }

    @Override
    public String saveToOrganizationProcess(NewAdminRegisterMapper organizationMapper) {
        String organizationId = null;

        OrganizationInfo organizationInfo = new OrganizationInfo();
        organizationInfo.setCreator_id("");
        organizationInfo.setCreation_date(new Date());
        OrganizationInfo organizationInfoo = organizationInfoRepository.save(organizationInfo);
        organizationId = organizationInfoo.getOrganization_id();

        if (null != organizationId) {

            OrganizationDetails organizationDetails = new OrganizationDetails();
            organizationDetails.setOrganization_id(organizationId);
//			organizationDetails.setBase_country(organizationMapper.getBaseCountry());
//			organizationDetails.setCompany_size(organizationMapper.getCompanySize());
            organizationDetails.setCreation_date(new Date());
//			organizationDetails.setCreator_id(organizationMapper.getCreatorId());
//			organizationDetails.setImage_id(organizationMapper.getImageId());
//			organizationDetails.setIndustry_type(organizationMapper.getIndustryType());
            organizationDetails.setName(organizationMapper.getOrganizationName());
//			organizationDetails.setTrade_currency(organizationMapper.getTradeCurrency());
//			organizationDetails.setUrl(organizationMapper.getOrganizationUrl());
            organizationDetails.setFiscal_start_month(organizationMapper.getFiscalStartMonth());
            organizationDetails.setFiscal_start_date(organizationMapper.getFiscalStartDate());
//			organizationDetails.setTwitter(organizationMapper.getTwitter());
//			organizationDetails.setFacebook(organizationMapper.getFacebook());
//			organizationDetails.setRevenue(organizationMapper.getRevenue());
//			organizationDetails.setLinkedin_url(organizationMapper.getLinkedinUrl());
            organizationDetails.setLive_ind(true);
//			organizationDetails.setPhone_number(organizationMapper.getPhoneNo());
//			organizationDetails.setCountry_code(organizationMapper.getCountryDialCode());
//			organizationDetails.setCountry_code1(organizationMapper.getCountryDialCode1());
//			organizationDetails.setMobile_number(organizationMapper.getMobileNo());

            organizationRepository.save(organizationDetails);
//			String organizationDetailsId = organizationDetailsss.getOrganization_details_id();

//			List<AddressMapper> list = organizationMapper.getAddress();
//
//			if (null != list && !list.isEmpty()) {
//				for (AddressMapper mapper : list) {
//				//list.forEach(mapper->{
//					/* insert to AddressInfo */
//
//					AddressInfo addressInfo = new AddressInfo();
//					addressInfo.setCreationDate(new Date());
//					addressInfo.setCreatorId(mapper.getEmployeeId());
//
//					AddressInfo addressInfooo = addressInfoRepository.save(addressInfo);
//					String addressInfoId = addressInfooo.getId();
//					if (null != addressInfoId) {
//
//						AddressDetails addressDetails = new AddressDetails();
//						addressDetails.setAddressId(addressInfoId);
//						addressDetails.setAddressLine1(mapper.getAddress1());
//						addressDetails.setAddressLine2(mapper.getAddress2());
//						addressDetails.setAddressType(mapper.getAddressType());
//						addressDetails.setCountry(mapper.getCountry());
//						addressDetails.setCreationDate(new Date());
//						addressDetails.setCreatorId(mapper.getEmployeeId());
//						addressDetails.setStreet(mapper.getStreet());
//						addressDetails.setCity(mapper.getCity());
//						addressDetails.setPostalCode(mapper.getPostalCode());
//						addressDetails.setTown(mapper.getTown());
//						addressDetails.setState(mapper.getState());
//						addressDetails.setLatitude(mapper.getLatitude());
//						addressDetails.setLongitude(mapper.getLongitude());
//						addressDetails.setLiveInd(true);
//						addressDetails.setHouseNo(mapper.getHouseNo());
//						addressRepository.save(addressDetails);
//						
//					}
//
//					/* insert to organization address link table */
//
//					OrganizationAddressLink organizationAddressLink = new OrganizationAddressLink();
//					organizationAddressLink.setAddress_id(addressInfoId);
//					organizationAddressLink.setCreation_date(new Date());
//					organizationAddressLink.setOrganization_id(organizationId);
//					organizationAddressLink.setLive_ind(true);
//
//					OrganizationAddressLink organizationAddressLinkkk = organizationAddressLinkRepository
//							.save(organizationAddressLink);
//					String organizationAddressLinkId = organizationAddressLinkkk.getOrganization_address_link_id();
//
//				}
//
//			}
            OrganizationSubsriptionDetails organizationSubsriptionDetails = new OrganizationSubsriptionDetails();
            organizationSubsriptionDetails.setSubscription_start_date(new Date());
            organizationSubsriptionDetails.setSubscription_end_date(Utility.getPlusDate(new Date(), 30));
            organizationSubsriptionDetailsRepository.save(organizationSubsriptionDetails);
        }
        return organizationId;

    }

    @Override
    public OrganizationMapper getOrganizationDetails(String organizationId) {

        OrganizationDetails organizationDetails = organizationRepository.getOrganizationDetailsById(organizationId);

        OrganizationMapper organizationMapper = new OrganizationMapper();
        organizationMapper.setBaseCountry(organizationDetails.getBase_country());
        organizationMapper.setCompanySize(organizationDetails.getCompany_size());
        organizationMapper.setCreatorId(organizationDetails.getCreator_id());
        organizationMapper.setFacebook(organizationDetails.getFacebook());
        organizationMapper.setFiscalStartDate(organizationDetails.getFiscal_start_date());
        organizationMapper.setFiscalStartMonth(organizationDetails.getFiscal_start_month());
        organizationMapper.setImageId(organizationDetails.getImage_id());
        organizationMapper.setIndustryType(organizationDetails.getIndustry_type());
        organizationMapper.setOrganizationId(organizationId);
        organizationMapper.setOrganizationName(organizationDetails.getName());
        organizationMapper.setOrganizationUrl(organizationDetails.getUrl());
        organizationMapper.setRevenue(organizationDetails.getRevenue());
        organizationMapper.setTradeCurrency(organizationDetails.getTrade_currency());
        organizationMapper.setTwitter(organizationDetails.getTwitter());
        organizationMapper.setLinkedinUrl(organizationDetails.getLinkedin_url());
        organizationMapper.setCountryDialCode(organizationDetails.getCountry_code());
        organizationMapper.setCountryDialCode1(organizationDetails.getCountry_code1());
        organizationMapper.setPhoneNo(organizationDetails.getPhone_number());
        organizationMapper.setMobileNo(organizationDetails.getMobile_number());
        organizationMapper.setVat(organizationDetails.getVat());
        organizationMapper.setCreationDate(Utility.getISOFromDate(organizationDetails.getCreation_date()));
        organizationMapper.setBToB(organizationDetails.isBToBInd());

        if (!StringUtils.isEmpty(organizationDetails.getIndustryId())) {
            Industry industry = industryRepository.findByIndustryIdAndLiveInd(organizationDetails.getImage_id(), true);
            if (null != industry) {
                organizationMapper.setIndustryId(organizationDetails.getIndustryId());
                organizationMapper.setIndustry(industry.getName());
            }
        }

        OrganizationSubsriptionDetails organizationSubsriptionDetails = organizationSubsriptionDetailsRepository
                .findByOrganizationId(organizationId);
        if (null != organizationSubsriptionDetails) {
            organizationMapper
                    .setSubscriptionType(String.valueOf(organizationSubsriptionDetails.getSubscriptionType()));
            organizationMapper.setSubscriptionEndDate(
                    Utility.getISOFromDate(organizationSubsriptionDetails.getSubscription_end_date()));
        }

        /* get org address */

        List<OrganizationAddressLink> organizationAddressLinkList = organizationAddressLinkRepository
                .getAddressListByOrgId(organizationId);

        if (null != organizationAddressLinkList && !organizationAddressLinkList.isEmpty()) {

            List<AddressMapper> addressList = new ArrayList<AddressMapper>();

            // for (OrganizationAddressLink organizationAddressLink :
            // organizationAddressLinkList) {
            organizationAddressLinkList.stream().map(organizationAddressLink -> {
                AddressDetails addressDetails = addressRepository
                        .getAddressDetailsByAddressId(organizationAddressLink.getAddress_id());

                AddressMapper addressMapper = new AddressMapper();
                if (null != addressDetails) {
                    addressMapper.setAddress1(addressDetails.getAddressLine1());
                    addressMapper.setAddress2(addressDetails.getAddressLine2());
                    addressMapper.setAddressType(addressDetails.getAddressType());
                    addressMapper.setPostalCode(addressDetails.getPostalCode());
                    addressMapper.setStreet(addressDetails.getStreet());
                    addressMapper.setTown(addressDetails.getTown());
                    addressMapper.setState(addressDetails.getState());
                    addressMapper.setCountry(addressDetails.getCountry());
                    addressMapper.setLatitude(addressDetails.getLatitude());
                    addressMapper.setLongitude(addressDetails.getLongitude());
                    addressMapper.setAddressId(addressDetails.getAddressId());
                    addressMapper.setHouseNo(addressDetails.getHouseNo());
                    addressList.add(addressMapper);

                    organizationMapper.setAddress(addressList);
                    return addressList;
                    // organizationMapper.setAddress(addressList);

                }
                return null;
            }).collect(Collectors.toList());
        }
        return organizationMapper;

    }

    @Override
    public OrganizationMapper updateOrganizationDetails(OrganizationMapper organizationMapper) {
        OrganizationMapper resultMapper = new OrganizationMapper();

        OrganizationDetails organizationDetails = organizationRepository
                .getOrganizationDetailsById(organizationMapper.getOrganizationId());

        if (null != organizationDetails) {
            if (null != organizationMapper.getIndustryType())
                organizationDetails.setIndustry_type(organizationMapper.getIndustryType());

            if (null != organizationMapper.getOrganizationUrl())
                organizationDetails.setUrl(organizationMapper.getOrganizationUrl());
            if (null != organizationMapper.getFiscalStartDate())
                organizationDetails.setFiscal_start_date(organizationMapper.getFiscalStartDate());

            if (null != organizationMapper.getFiscalStartMonth())
                organizationDetails.setFiscal_start_month(organizationMapper.getFiscalStartMonth());

            if (null != organizationMapper.getOrganizationName())
                organizationDetails.setName(organizationMapper.getOrganizationName());

            if (null != organizationMapper.getImageId())
                organizationDetails.setImage_id(organizationMapper.getImageId());
            if (null != organizationMapper.getBaseCountry())
                organizationDetails.setBase_country(organizationMapper.getBaseCountry());
            if (null != organizationMapper.getTradeCurrency())
                organizationDetails.setTrade_currency(organizationMapper.getTradeCurrency());
            if (null != organizationMapper.getTwitter())
                organizationDetails.setTwitter(organizationMapper.getTwitter());
            if (null != organizationMapper.getLinkedinUrl())
                organizationDetails.setLinkedin_url(organizationMapper.getLinkedinUrl());
            if (null != organizationMapper.getFacebook())
                organizationDetails.setFacebook(organizationMapper.getFacebook());
            if (null != organizationMapper.getCountryDialCode())
                organizationDetails.setCountry_code(organizationMapper.getCountryDialCode());
            if (null != organizationMapper.getCountryDialCode1())
                organizationDetails.setCountry_code1(organizationMapper.getCountryDialCode1());
            if (null != organizationMapper.getPhoneNo())
                organizationDetails.setPhone_number(organizationMapper.getPhoneNo());
            if (null != organizationMapper.getMobileNo())
                organizationDetails.setMobile_number(organizationMapper.getMobileNo());
            if (null != organizationMapper.getCompanySize())
                organizationDetails.setCompany_size(organizationMapper.getCompanySize());
            if (null != organizationMapper.getVat())
                organizationDetails.setVat(organizationMapper.getVat());

            if (null != organizationMapper.getSubscriptionType()) {
                OrganizationSubsriptionDetails organizationSubsriptionDetails = organizationSubsriptionDetailsRepository
                        .findByOrganizationId(organizationMapper.getOrganizationId());

                if (null != organizationSubsriptionDetails) {
                    int currsubscription = organizationSubsriptionDetails.getSubscriptionType();
                    int newSubscription = Integer.valueOf(organizationMapper.getSubscriptionType());

                    if (canUpgradeTo(currsubscription, newSubscription)) {
                        organizationSubsriptionDetails.setSubscriptionType(newSubscription);
                        organizationSubsriptionDetails.setSubscription_start_date(new Date());
                        organizationSubsriptionDetails.setSubscription_end_date(Utility.getPlusDate(new Date(), 30));
                        organizationSubsriptionDetailsRepository.save(organizationSubsriptionDetails);
                    } else if (canDowngradeTo(currsubscription, newSubscription)) {
                        if (new Date().after(organizationSubsriptionDetails.getSubscription_end_date())) {
                            organizationSubsriptionDetails.setSubscriptionType(newSubscription);
                            organizationSubsriptionDetails.setSubscription_start_date(new Date());
                            organizationSubsriptionDetails
                                    .setSubscription_end_date(Utility.getPlusDate(new Date(), 30));
                            organizationSubsriptionDetailsRepository.save(organizationSubsriptionDetails);
                        } else {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                    "Cannot downgrade before the current subscription period.Your subscription ends on"
                                            + Utility.getPlusDate(new Date(), 30) + " .");
                        }
                    }
                }

            }

            organizationRepository.save(organizationDetails);

            System.out.println("organizationDetails......" + organizationDetails.toString());
            resultMapper = getOrganizationDetails(organizationMapper.getOrganizationId());

        }
        return resultMapper;
    }

    @Override
    public FiscalMapper getFiscalMapperByOrgId(String organizationId) {
        FiscalMapper fiscalMapper = new FiscalMapper();

        Date fiscalStartDate = calculateFiscalStartDate(organizationId);
        System.out.println("fiscalStartDate ........... " + fiscalStartDate);

        System.out.println("fiscalStartDate in iso ........... " + Utility.getISOFromDate(fiscalStartDate));
        fiscalMapper.setFiscalStartDate(
                Utility.getISOFromDate(new Date(fiscalStartDate.getTime() + (1000 * 60 * 60 * 24))));
        if (null != fiscalStartDate) {
            LocalDate endDate = Utility.getLocalDateByDate(fiscalStartDate).plusMonths(12);
            if (null != endDate) {
                Date fiscalEndDate = Utility.getUtilDateByLocalDate(endDate);
                System.out.println("fiscalEndDate ........... " + fiscalEndDate);

                fiscalMapper.setFiscalEndDate(
                        Utility.getISOFromDate(new Date(fiscalEndDate.getTime() + (1000 * 60 * 60 * 24))));
            }
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate monthStartDate = Utility.getStartDateOfMonth(currentDate);
        LocalDate monthEndDate = Utility.getEndDateOfMonth(currentDate);
        LocalDate weekStartDate = Utility.getCurrentWeekStartDate(currentDate);
        LocalDate weekEndDate = Utility.getCurrentWeekEndDate(currentDate);

        if (null != monthStartDate) {
            fiscalMapper
                    .setCurrentMonthStartDate(Utility.getISOFromDate(Utility.getUtilDateByLocalDate(monthStartDate)));

        }

        if (null != monthEndDate) {
            fiscalMapper.setCurrentMonthEndDate(Utility.getISOFromDate(Utility.getUtilDateByLocalDate(monthEndDate)));

        }
        if (null != weekStartDate) {
            fiscalMapper.setCurrentWeekStartDate(Utility.getISOFromDate(Utility.getUtilDateByLocalDate(weekStartDate)));

        }

        if (null != weekEndDate) {
            fiscalMapper.setCurrentWeekEndDate(Utility.getISOFromDate(Utility.getUtilDateByLocalDate(weekEndDate)));

        }

        return fiscalMapper;
    }

    public Date calculateFiscalStartDate(String organizationId) {

        OrganizationDetails organizationDetails = organizationRepository.getOrganizationDetailsById(organizationId);

        String fiscalStartMonth = organizationDetails.getFiscal_start_month();
        String fiscalStartDateDb = organizationDetails.getFiscal_start_date();
        Date result = null;

        int currentYear = LocalDate.now().getYear();

        String fiscalStartDateInString = fiscalStartMonth + "-" + fiscalStartDateDb + "-" + currentYear;
        try {
            Date fiscal_date_format = new SimpleDateFormat("MMM-dd-yyyy").parse(fiscalStartDateInString);
            LocalDate fiscalStartDate = Utility.getLocalDateByDate(fiscal_date_format);
            if (fiscalStartDate.isBefore(LocalDate.now()) || fiscalStartDate.equals(LocalDate.now())) {
                result = Utility.getUtilDateByLocalDate(fiscalStartDate);
            } else {
                result = Utility.getUtilDateByLocalDate(fiscalStartDate.minusYears(1));

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;

    }

    public static int getYear(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return year;

    }

    @Override
    public OrganizationMapper upateOrganizationByOrgId(String orgId, OrganizationMapper organizationMapper) {

        OrganizationAddressLink organizationAddress = organizationAddressLinkRepository.getAddressByOrgId(orgId);

        if (null != organizationAddress) {
            List<AddressMapper> addressList = organizationMapper.getAddress();

            for (AddressMapper addressMapper : addressList) {

//				if (null != addressId1) {

                AddressDetails addressDetails = addressRepository
                        .getAddressDetailsByAddressId(organizationAddress.getAddress_id());
                if (null != addressDetails) {

                    addressDetails.setLiveInd(false);
                    addressRepository.save(addressDetails);
                }

                AddressDetails newAddressDetailss = new AddressDetails();

                newAddressDetailss.setAddressId(addressMapper.getAddressId());
                // System.out.println("ADDID@@@@@@@" + addressId);

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

                // System.out.println("AddressId::" +
                // addressRepository.save(newAddressDetailss).getAddressId());
            }
        }
        OrganizationMapper resultMapper = getOrganizationDetails(orgId);
        return resultMapper;

    }

    @Override
    public OrganizationDocumentLinkMapper SaveOrgDocument(
            OrganizationDocumentLinkMapper organizationDocumentLinkMapper) {
        String documentId = organizationDocumentLinkMapper.getDocumentId();
        OrganizationDocumentLinkMapper organizationDocumentLinkMapper1 = new OrganizationDocumentLinkMapper();
        if (null != organizationDocumentLinkMapper) {
            DocumentDetails documentDetails = documentDetailsRepository.getDocumentDetailsById(documentId);
            if (null != documentDetails) {
                // documentDetails.setDocument_title(documentMapper.getDocumentTitle());
                // documentDetails.setDocument_type(documentMapper.getDocumentTypeId());
                documentDetails.setDoc_description(organizationDocumentLinkMapper.getDescription());
                documentDetails.setDocument_id(documentId);
                documentDetails.setCreation_date(new Date());

                documentDetailsRepository.save(documentDetails);

                /* insert OrganizationDocumentLink link table */

                OrganizationDocumentLink organizationDocumentLink = new OrganizationDocumentLink();
                organizationDocumentLink.setCatagory(organizationDocumentLinkMapper.getCatagory());
                organizationDocumentLink.setCreationDate(new Date());
                organizationDocumentLink.setDepartment(organizationDocumentLinkMapper.getDepartment());
                organizationDocumentLink.setDescription(organizationDocumentLinkMapper.getDescription());
                organizationDocumentLink.setDocumentId(documentId);
                organizationDocumentLink.setLiveInd(true);
                organizationDocumentLink.setName(organizationDocumentLinkMapper.getName());
                organizationDocumentLink.setOrgId(organizationDocumentLinkMapper.getOrgId());
                organizationDocumentLink.setShareInd(organizationDocumentLinkMapper.isShareInd());
                organizationDocumentLink.setUserId(organizationDocumentLinkMapper.getUserId());
                organizationDocumentLink.setDocumentType(organizationDocumentLinkMapper.getDocumentType());

                OrganizationDocumentLink OrganizationDocumentLink1 = organizationDocumentLinkRepository
                        .save(organizationDocumentLink);

                if (organizationDocumentLinkMapper.isShareInd() == false) {
                    List<String> empList = organizationDocumentLinkMapper.getIncluded();
                    empList.add(organizationDocumentLinkMapper.getUserId());
                    if (null != empList && !empList.isEmpty()) {
                        empList.forEach(employeeId -> {

                            /* insert RepositoryIncludeLink link table */
                            RepositoryIncludeLink repositoryIncludeLink = new RepositoryIncludeLink();
                            repositoryIncludeLink.setOrganizationDocumentLinkId(
                                    OrganizationDocumentLink1.getOrganizationDocumentLinkId());
                            repositoryIncludeLink.setUserId(employeeId);
                            repositoryIncludeLink.setOrgId(OrganizationDocumentLink1.getOrgId());
                            repositoryIncludeLink.setCreationDate(new Date());
                            repositoryIncludeLink.setLiveInd(true);
                            repositoryIncludeLinkRepo.save(repositoryIncludeLink);

                        });

                    }
                }

                organizationDocumentLinkMapper1 = getOrganizationDocumentLinkDetailsById(
                        OrganizationDocumentLink1.getOrganizationDocumentLinkId());
            }
        }
        return organizationDocumentLinkMapper1;
    }

    @Override
    public OrganizationDocumentLinkMapper getOrganizationDocumentLinkDetailsById(String organizationDocumentLinkId) {
        OrganizationDocumentLink organizationDocumentLink = organizationDocumentLinkRepository
                .getById(organizationDocumentLinkId);
        OrganizationDocumentLinkMapper resultMapper = new OrganizationDocumentLinkMapper();
        if (null != organizationDocumentLink) {
            resultMapper.setCatagory(organizationDocumentLink.getCatagory());
            resultMapper.setCreationDate(Utility.getISOFromDate(organizationDocumentLink.getCreationDate()));
            resultMapper.setDescription(organizationDocumentLink.getDescription());
            resultMapper.setDocumentId(organizationDocumentLink.getDocumentId());
            resultMapper.setLiveInd(organizationDocumentLink.isLiveInd());
            resultMapper.setDocumentType(organizationDocumentLink.getDocumentType());
            resultMapper.setName(organizationDocumentLink.getName());
            resultMapper.setOrganizationDocumentLinkId(organizationDocumentLinkId);
            resultMapper.setOrgId(organizationDocumentLink.getOrgId());
            resultMapper.setShareInd(organizationDocumentLink.isShareInd());
            resultMapper.setUserId(organizationDocumentLink.getUserId());
//			resultMapper.setPublishInd(organizationDocumentLink.isPublishInd());
//			resultMapper.setPublicInd(organizationDocumentLink.isPublicInd());
            Department department = departmentRepository
                    .getDepartmentDetailsById(organizationDocumentLink.getDepartment());
            if (null != department) {
                resultMapper.setDepartment(department.getDepartmentName());
            }
            if (!StringUtils.isEmpty(organizationDocumentLink.getDocumentType())) {
                DocumentType documentType = documentTypeRepository
                        .getTypeDetails(organizationDocumentLink.getDocumentType());
                if (null != documentType) {
                    resultMapper.setDocumentType(documentType.getDocumentTypeName());
                }
            }
            List<RepositoryIncludeLink> repositoryIncludeLink = repositoryIncludeLinkRepo
                    .findByOrganizationDocumentLinkIdAndLiveInd(organizationDocumentLinkId, true);
            List<EmployeeShortMapper> empList = new ArrayList<>();
            if (null != repositoryIncludeLink && !repositoryIncludeLink.isEmpty()) {
                if (repositoryIncludeLink.size() > 0) {
                    for (RepositoryIncludeLink empId : repositoryIncludeLink) {
                        EmployeeShortMapper mapper = employeeService
                                .getEmployeeFullNameAndEmployeeId(empId.getUserId());
                        if (null != mapper) {
                            empList.add(mapper);
                        }
                    }
                    resultMapper.setIncludeds(empList);
                }
            }

        }
        return resultMapper;
    }

    @Override
    public List<OrganizationDocumentLinkMapper> getOrganizationDocumentByUserId(String userId) {
        List<OrganizationDocumentLinkMapper> resultMapper = new ArrayList<>();
        List<RepositoryIncludeLink> repositoryIncludeLink = repositoryIncludeLinkRepo.findByUserIdAndLiveInd(userId,
                true);
        if (null != repositoryIncludeLink && !repositoryIncludeLink.isEmpty()) {
            repositoryIncludeLink.stream().map(li -> {
                OrganizationDocumentLinkMapper mapper = getOrganizationDocumentLinkDetailsById(
                        li.getOrganizationDocumentLinkId());
                if (null != mapper) {
                    resultMapper.add(mapper);
                }
                return mapper;
            }).collect(Collectors.toList());
        }

        List<OrganizationDocumentLink> organizationDocumentLink = organizationDocumentLinkRepository
                .findByShareInd(true);
        if (null != organizationDocumentLink && !organizationDocumentLink.isEmpty()) {
            organizationDocumentLink.stream().map(li -> {
                OrganizationDocumentLinkMapper mapper = getOrganizationDocumentLinkDetailsById(
                        li.getOrganizationDocumentLinkId());
                if (null != mapper) {
                    resultMapper.add(mapper);
                }
                return mapper;
            }).collect(Collectors.toList());
        }
        return resultMapper;
    }

    @Override
    public List<OrganizationDocumentLinkMapper> getOrganizationDocumentByOrgId(String orgId) {
        List<OrganizationDocumentLink> organizationDocumentLink = organizationDocumentLinkRepository
                .findByOrgIdAndLiveInd(orgId, true);
        List<OrganizationDocumentLinkMapper> resultMapper = new ArrayList<>();
        if (null != organizationDocumentLink && !organizationDocumentLink.isEmpty()) {
            resultMapper = organizationDocumentLink.stream()
                    .map(li -> getOrganizationDocumentLinkDetailsById(li.getOrganizationDocumentLinkId()))
                    .collect(Collectors.toList());
        }
        return resultMapper;
    }

    @Override
    public void deleteDocumentsById(String organizationDocumentLinkId) {
        if (null != organizationDocumentLinkId) {

            OrganizationDocumentLink OrganizationDocumentLink = organizationDocumentLinkRepository
                    .getByOrganizationDocumentLinkIdAndLiveInd(organizationDocumentLinkId);
            if (null != OrganizationDocumentLink) {
                OrganizationDocumentLink.setLiveInd(false);
                organizationDocumentLinkRepository.save(OrganizationDocumentLink);

                DocumentDetails document = documentDetailsRepository
                        .getDocumentDetailsById(OrganizationDocumentLink.getDocumentId());
                if (null != document) {
                    document.setLive_ind(false);
                    documentDetailsRepository.save(document);
                }

            }
        }
    }

//	@Override
//	public OrganizationDocumentLinkMapper upatePublishInd(
//			OrganizationDocumentLinkMapper organizationDocumentLinkMapper) {
//
//		OrganizationDocumentLinkMapper resultMapper = null;
//
//		if (null != organizationDocumentLinkMapper.getOrganizationDocumentLinkId()) {
//			OrganizationDocumentLink OrganizationDocumentLink = organizationDocumentLinkRepository
//					.getById(organizationDocumentLinkMapper.getOrganizationDocumentLinkId());
//			if (null != OrganizationDocumentLink) {
//				OrganizationDocumentLink.setPublishInd(organizationDocumentLinkMapper.isPublishInd());
//				organizationDocumentLinkRepository.save(OrganizationDocumentLink);
//
//			}
//			resultMapper = getOrganizationDocumentLinkDetailsById(
//					organizationDocumentLinkMapper.getOrganizationDocumentLinkId());
//		}
//		return resultMapper;
//	}

    // @Override
//	public OrganizationDocumentLinkMapper upatePublicInd(
//			OrganizationDocumentLinkMapper organizationDocumentLinkMapper) {
//
//		OrganizationDocumentLinkMapper resultMapper = null;
//
//		if (null != organizationDocumentLinkMapper.getOrganizationDocumentLinkId()) {
//			OrganizationDocumentLink OrganizationDocumentLink = organizationDocumentLinkRepository
//					.getById(organizationDocumentLinkMapper.getOrganizationDocumentLinkId());
//
//			if (null != OrganizationDocumentLink) {
//				OrganizationDocumentLink.setPublicInd(organizationDocumentLinkMapper.isPublicInd());
//				organizationDocumentLinkRepository.save(OrganizationDocumentLink);
//			}
//
//			resultMapper = getOrganizationDocumentLinkDetailsById(
//					organizationDocumentLinkMapper.getOrganizationDocumentLinkId());
//
//		}
//
//		return resultMapper;
//	}
//
    @Override
    public OrganizationCurrencyLinkMapper SaveOrgCurrency(
            OrganizationCurrencyLinkMapper organizationCurrencyLinkMapper) {
        if (null != organizationCurrencyLinkMapper) {

            OrganizationCurrencyLink organizationCurrencyLink = new OrganizationCurrencyLink();
            organizationCurrencyLink.setCreationDate(new Date());
            organizationCurrencyLink.setCurrencyId(organizationCurrencyLinkMapper.getCurrencyId());
            organizationCurrencyLink.setLiveInd(true);
            organizationCurrencyLink.setOrgId(organizationCurrencyLinkMapper.getOrgId());
            organizationCurrencyLink.setPairCurrencyId(organizationCurrencyLinkMapper.getPairCurrencyId());
            organizationCurrencyLink.setUpdatedBy(organizationCurrencyLinkMapper.getUserId());
            organizationCurrencyLink.setUserId(organizationCurrencyLinkMapper.getUserId());

            OrganizationCurrencyLink organizationCurrencyLink1 = organizationCurrencyLinkRepository
                    .save(organizationCurrencyLink);
            organizationCurrencyLinkMapper = getOrganizationCurrencyLinkDetailsById(
                    organizationCurrencyLink1.getOrgCurrencyLinkId());
        }
        return organizationCurrencyLinkMapper;
    }

    @Override
    public OrganizationCurrencyLinkMapper getOrganizationCurrencyLinkDetailsById(String orgCurrencyLinkId) {
        OrganizationCurrencyLink organizationCurrencyLink = organizationCurrencyLinkRepository
                .getById(orgCurrencyLinkId);
        OrganizationCurrencyLinkMapper resultMapper = new OrganizationCurrencyLinkMapper();
        if (null != organizationCurrencyLink) {
            resultMapper.setCreationDate(Utility.getISOFromDate(organizationCurrencyLink.getCreationDate()));
            resultMapper.setCurrencyId(organizationCurrencyLink.getCurrencyId());
            resultMapper.setLiveInd(organizationCurrencyLink.isLiveInd());
            resultMapper.setOrgId(organizationCurrencyLink.getOrgId());
            resultMapper.setOrgCurrencyLinkId(orgCurrencyLinkId);
            resultMapper.setPairCurrencyId(organizationCurrencyLink.getPairCurrencyId());
            resultMapper.setUpdatedBy(employeeService.getEmployeeFullName(organizationCurrencyLink.getUpdatedBy()));
            resultMapper.setUpdationDate(Utility.getISOFromDate(organizationCurrencyLink.getUpdationDate()));
            resultMapper.setUserId(organizationCurrencyLink.getUserId());

        }
        return resultMapper;
    }

    @Override
    public List<OrganizationCurrencyLinkMapper> getOrganizationCurrencyByOrgId(String orgId) {
        List<OrganizationCurrencyLink> organizationCurrencyLink = organizationCurrencyLinkRepository
                .findByOrgIdAndLiveInd(orgId, true);
        List<OrganizationCurrencyLinkMapper> resultMapper = new ArrayList<>();
        if (null != organizationCurrencyLink && !organizationCurrencyLink.isEmpty()) {
            resultMapper = organizationCurrencyLink.stream()
                    .map(li -> getOrganizationCurrencyLinkDetailsById(li.getOrgCurrencyLinkId()))
                    .collect(Collectors.toList());
        }
        return resultMapper;
    }

    @Override
    public void deleteOrgCurrencyById(String orgCurrencyLinkId) {
        if (null != orgCurrencyLinkId) {
            OrganizationCurrencyLink organizationCurrencyLink = organizationCurrencyLinkRepository
                    .findByOrgCurrencyLinkId(orgCurrencyLinkId);
            if (null != organizationCurrencyLink) {
                organizationCurrencyLink.setLiveInd(false);
                organizationCurrencyLinkRepository.save(organizationCurrencyLink);
            }
        }
    }

    @Override
    public OrganizationCurrencyLinkMapper upateOrgCurrency(String orgCurrencyLinkId,
                                                           OrganizationCurrencyLinkMapper organizationCurrencyLinkMapper) {
        OrganizationCurrencyLink organizationCurrencyLink = organizationCurrencyLinkRepository
                .getById(orgCurrencyLinkId);
        if (null != organizationCurrencyLink) {
            organizationCurrencyLink.setCurrencyId(organizationCurrencyLinkMapper.getCurrencyId());
            organizationCurrencyLink.setLiveInd(true);
            organizationCurrencyLink.setOrgId(organizationCurrencyLinkMapper.getOrgId());
            organizationCurrencyLink.setPairCurrencyId(organizationCurrencyLinkMapper.getPairCurrencyId());
            organizationCurrencyLink.setUpdatedBy(organizationCurrencyLinkMapper.getUserId());
            organizationCurrencyLink.setUserId(organizationCurrencyLinkMapper.getUserId());
            organizationCurrencyLink.setUpdationDate(new Date());
            organizationCurrencyLinkRepository.save(organizationCurrencyLink);
        }
        OrganizationCurrencyLinkMapper resultmapper = getOrganizationCurrencyLinkDetailsById(orgCurrencyLinkId);
        return resultmapper;
    }

    @Override
    public List<OrganizationMapper> getListOfOrganization() {
        List<OrganizationMapper> resultList = new ArrayList<>();
        List<OrganizationDetails> organizationDetails = organizationRepository.getOrganizationDetailsByLiveInd(true);
        if (null != organizationDetails && !organizationDetails.isEmpty()) {
            organizationDetails.stream().map(li -> {
                OrganizationMapper mapper = getOrganizationDetails(li.getOrganization_id());
                if (null != mapper) {
                    resultList.add(mapper);
                }
                return mapper;
            }).collect(Collectors.toList());

        }
        return resultList;
    }

    @Override
    public OrganizationMapper upateOrganizationDetailsByOrgId(String orgId, OrganizationMapper organizationMapper) {
        OrganizationMapper resultMapper = new OrganizationMapper();

        OrganizationDetails organizationDetails = organizationRepository.getOrganizationDetailsById(orgId);

        if (null != organizationDetails) {
            if (null != organizationMapper.getIndustryType())
                organizationDetails.setIndustry_type(organizationMapper.getIndustryType());

            if (null != organizationMapper.getOrganizationUrl())
                organizationDetails.setUrl(organizationMapper.getOrganizationUrl());
            if (null != organizationMapper.getFiscalStartDate())
                organizationDetails.setFiscal_start_date(organizationMapper.getFiscalStartDate());

            if (null != organizationMapper.getFiscalStartMonth())
                organizationDetails.setFiscal_start_month(organizationMapper.getFiscalStartMonth());

            if (null != organizationMapper.getOrganizationName())
                organizationDetails.setName(organizationMapper.getOrganizationName());

            if (null != organizationMapper.getImageId())
                organizationDetails.setImage_id(organizationMapper.getImageId());
            if (null != organizationMapper.getBaseCountry())
                organizationDetails.setBase_country(organizationMapper.getBaseCountry());
            if (null != organizationMapper.getTradeCurrency())
                organizationDetails.setTrade_currency(organizationMapper.getTradeCurrency());
            if (null != organizationMapper.getTwitter())
                organizationDetails.setTwitter(organizationMapper.getTwitter());
            if (null != organizationMapper.getLinkedinUrl())
                organizationDetails.setLinkedin_url(organizationMapper.getLinkedinUrl());
            if (null != organizationMapper.getFacebook())
                organizationDetails.setFacebook(organizationMapper.getFacebook());
            if (null != organizationMapper.getCountryDialCode())
                organizationDetails.setCountry_code(organizationMapper.getCountryDialCode());
            if (null != organizationMapper.getCountryDialCode1())
                organizationDetails.setCountry_code1(organizationMapper.getCountryDialCode1());
            if (null != organizationMapper.getPhoneNo())
                organizationDetails.setPhone_number(organizationMapper.getPhoneNo());
            if (null != organizationMapper.getMobileNo())
                organizationDetails.setMobile_number(organizationMapper.getMobileNo());
            if (null != organizationMapper.getCompanySize())
                organizationDetails.setCompany_size(organizationMapper.getCompanySize());
            if (null != organizationMapper.getVat())
                organizationDetails.setVat(organizationMapper.getVat());
            if (null != organizationMapper.getIndustry())
                organizationDetails.setIndustryId(organizationMapper.getIndustry());
            if (false != organizationMapper.isBToB())
                organizationDetails.setBToBInd(organizationMapper.isBToB());
            if (null != organizationMapper.getSubscriptionType()) {
                OrganizationSubsriptionDetails organizationSubsriptionDetails = organizationSubsriptionDetailsRepository
                        .findByOrganizationId(organizationMapper.getOrganizationId());

                if (null != organizationSubsriptionDetails) {
                    int currsubscription = organizationSubsriptionDetails.getSubscriptionType();
                    int newSubscription = Integer.valueOf(organizationMapper.getSubscriptionType());

                    if (canUpgradeTo(currsubscription, newSubscription)) {
                        organizationSubsriptionDetails.setSubscriptionType(newSubscription);
                        organizationSubsriptionDetails.setSubscription_start_date(new Date());
                        organizationSubsriptionDetails.setSubscription_end_date(Utility.getPlusDate(new Date(), 30));
                        organizationSubsriptionDetailsRepository.save(organizationSubsriptionDetails);
                    } else if (canDowngradeTo(currsubscription, newSubscription)) {
                        if (new Date().after(organizationSubsriptionDetails.getSubscription_end_date())) {
                            organizationSubsriptionDetails.setSubscriptionType(newSubscription);
                            organizationSubsriptionDetails.setSubscription_start_date(new Date());
                            organizationSubsriptionDetails
                                    .setSubscription_end_date(Utility.getPlusDate(new Date(), 30));
                            organizationSubsriptionDetailsRepository.save(organizationSubsriptionDetails);
                        } else {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                    "Cannot downgrade before the current subscription period.Your subscription ends on"
                                            + Utility.getPlusDate(new Date(), 30) + " .");
                        }
                    }
                }
            }

            organizationRepository.save(organizationDetails);
            if (null != organizationMapper.getAddress() && !organizationMapper.getAddress().isEmpty()) {
                OrganizationAddressLink organizationAddress = organizationAddressLinkRepository
                        .getAddressByOrgId(orgId);

                if (null != organizationAddress) {
                    List<AddressMapper> addressList = organizationMapper.getAddress();

                    for (AddressMapper addressMapper : addressList) {

//				if (null != addressId1) {

                        AddressDetails addressDetails = addressRepository
                                .getAddressDetailsByAddressId(organizationAddress.getAddress_id());
                        if (null != addressDetails) {

                            addressDetails.setLiveInd(false);
                            addressRepository.save(addressDetails);
                        }

                        AddressDetails newAddressDetailss = new AddressDetails();

                        newAddressDetailss.setAddressId(addressMapper.getAddressId());
                        // System.out.println("ADDID@@@@@@@" + addressId);

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

                        // System.out.println("AddressId::" +
                        // addressRepository.save(newAddressDetailss).getAddressId());
                    }
                }
            }

            System.out.println("organizationDetails......" + organizationDetails.toString());
            resultMapper = getOrganizationDetails(organizationDetails.getOrganization_id());

        }
        return resultMapper;
    }

    @Override
    public String deleteOrganizationDetailsByOrgId(String orgId, OrganizationMapper organizationMapper) {
        OrganizationDetails organizationDetails = organizationRepository
                .getOrganizationDetailsById(organizationMapper.getOrganizationId());
        String msg = null;
        if (null != organizationDetails) {
            organizationDetails.setLive_ind(false);
            organizationRepository.save(organizationDetails);
            msg = "Deleted Successfully";
        } else {
            msg = "Not Deleted";
        }
        return msg;
    }

    @Override
    public OrganizationDocumentLinkMapper upateOrganisationDocument(OrganizationDocumentLinkMapper requestMapper,
                                                                    String organizationDocumentLinkId) {
        OrganizationDocumentLinkMapper resultMapper = new OrganizationDocumentLinkMapper();
        OrganizationDocumentLink db = organizationDocumentLinkRepository.getById(organizationDocumentLinkId);
        if (null != db) {

            if (null != requestMapper.getDepartment()) {
                db.setDepartment(requestMapper.getDepartment());
            }
            if (null != requestMapper.getDescription()) {
                db.setDescription(requestMapper.getDescription());
            }
            if (null != requestMapper.getDocumentType()) {
                db.setDocumentType(requestMapper.getDocumentType());
            }
            if (null != requestMapper.getCatagory()) {
                db.setCatagory(requestMapper.getCatagory());
            }
            if (null != requestMapper.getName()) {
                db.setName(requestMapper.getName());
            }
            if (null != requestMapper.getDocumentId()) {
                db.setDocumentId(requestMapper.getDocumentId());
            }

            db.setShareInd(requestMapper.isShareInd());
            db.setUserId(requestMapper.getUserId());
            db.setOrgId(requestMapper.getOrgId());
            organizationDocumentLinkRepository.save(db);

            if (requestMapper.isShareInd() == false) {

                List<RepositoryIncludeLink> repositoryIncludeLink = repositoryIncludeLinkRepo
                        .findByOrganizationDocumentLinkId(organizationDocumentLinkId);
                if (null != repositoryIncludeLink && !repositoryIncludeLink.isEmpty()) {
                    for (RepositoryIncludeLink repositoryIncludeLink2 : repositoryIncludeLink) {
                        repositoryIncludeLink2.setLiveInd(false);
                        repositoryIncludeLinkRepo.save(repositoryIncludeLink2);
                    }
                }

                List<String> empList = requestMapper.getIncluded();
                empList.add(requestMapper.getUserId());
                if (null != empList && !empList.isEmpty()) {
                    empList.forEach(employeeId -> {

                        /* insert RepositoryIncludeLink link table */
                        RepositoryIncludeLink repositoryIncludeLink3 = new RepositoryIncludeLink();
                        repositoryIncludeLink3.setOrganizationDocumentLinkId(db.getOrganizationDocumentLinkId());
                        repositoryIncludeLink3.setUserId(employeeId);
                        repositoryIncludeLink3.setOrgId(requestMapper.getOrgId());
                        repositoryIncludeLink3.setCreationDate(new Date());
                        repositoryIncludeLink3.setLiveInd(true);
                        repositoryIncludeLinkRepo.save(repositoryIncludeLink3);

                    });

                }
            }

            resultMapper = getOrganizationDocumentLinkDetailsById(organizationDocumentLinkId);

        }
        return resultMapper;
    }

    @Override
    public OrgIndustryMapper saveAndUpdateOrganizationIndustry(OrgIndustryMapper mapper, String userId, String orgId) {
        OrgIndustryMapper response = new OrgIndustryMapper();
        if (!StringUtils.isEmpty(mapper.getOrgType())) {
            OrgIndustry responseOrgIndustry = null;
            OrgIndustry dbOrgIndustry = orgIndustryRepository.findByOrgIdAndLiveInd(orgId, true);
            if (null != dbOrgIndustry) {
                dbOrgIndustry.setType(mapper.getOrgType());
                dbOrgIndustry.setUpdateDate(new Date());
                dbOrgIndustry.setLiveInd(true);
                dbOrgIndustry.setUserId(userId);
                dbOrgIndustry.setOrgId(orgId);
                responseOrgIndustry = orgIndustryRepository.save(dbOrgIndustry);
            } else {
                OrgIndustry industry = new OrgIndustry();
                industry.setType(mapper.getOrgType());
                industry.setUpdateDate(new Date());
                industry.setLiveInd(true);
                industry.setUserId(userId);
                industry.setOrgId(orgId);
                responseOrgIndustry = orgIndustryRepository.save(industry);
            }
            response.setOrgType(responseOrgIndustry.getType());
        }
        return response;
    }

    @Override
    public OrgIndustryMapper getOrganizationIndustryByOrgId(String orgId) {
        OrgIndustryMapper response = new OrgIndustryMapper();
        OrgIndustry dbOrgIndustry = orgIndustryRepository.findByOrgIdAndLiveInd(orgId, true);
        if (null != dbOrgIndustry) {
            response.setOrgType(dbOrgIndustry.getType());
        }
        return response;
    }

    public boolean canUpgradeTo(int currentSubscription, int newSubscription) {
        return newSubscription > currentSubscription;
    }

    public boolean canDowngradeTo(int currentSubscription, int newSubscription) {
        return newSubscription < currentSubscription;
    }

    @Override
    public OrganizationSubscriptionMapper upateOrganizationSubscriptionByOrgId(
            OrganizationSubscriptionMapper subscription) {
        OrganizationSubsriptionDetails dbSub = null;
        if (null != subscription.getSubscriptionType()) {
            OrganizationSubsriptionDetails organizationSubsriptionDetails = organizationSubsriptionDetailsRepository
                    .findByOrganizationId(subscription.getOrganizationId());

            if (null != organizationSubsriptionDetails) {
                int currsubscription = organizationSubsriptionDetails.getSubscriptionType();
                int newSubscription = Integer.valueOf(subscription.getSubscriptionType());

                if (canUpgradeTo(currsubscription, newSubscription)) {
                    organizationSubsriptionDetails.setSubscriptionType(newSubscription);
                    organizationSubsriptionDetails.setSubscription_start_date(new Date());
                    organizationSubsriptionDetails.setSubscription_end_date(Utility.getPlusDate(new Date(), 30));
                    organizationSubsriptionDetails.setOrganizationId(subscription.getOrganizationId());
                    dbSub = organizationSubsriptionDetailsRepository.save(organizationSubsriptionDetails);
                } else if (canDowngradeTo(currsubscription, newSubscription)) {
                    if (new Date().after(organizationSubsriptionDetails.getSubscription_end_date())) {
                        organizationSubsriptionDetails.setSubscriptionType(newSubscription);
                        organizationSubsriptionDetails.setSubscription_start_date(new Date());
                        organizationSubsriptionDetails.setSubscription_end_date(Utility.getPlusDate(new Date(), 30));
                        organizationSubsriptionDetails.setOrganizationId(subscription.getOrganizationId());
                        dbSub = organizationSubsriptionDetailsRepository.save(organizationSubsriptionDetails);
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Cannot downgrade before the current subscription period.Your subscription ends on "
                                        + Utility.getPlusDate(new Date(), 30) + " .");
                    }
                }
            } else {
                OrganizationSubsriptionDetails sub = new OrganizationSubsriptionDetails();
                sub.setSubscriptionType(Integer.valueOf(subscription.getSubscriptionType()));
                sub.setSubscription_start_date(new Date());
                sub.setSubscription_end_date(Utility.getPlusDate(new Date(), 30));
                sub.setOrganizationId(subscription.getOrganizationId());
                dbSub = organizationSubsriptionDetailsRepository.save(sub);
            }

        }
        OrganizationSubscriptionMapper response = getSubscription(dbSub);
        return response;
    }

    private OrganizationSubscriptionMapper getSubscription(OrganizationSubsriptionDetails dbSub) {
        OrganizationSubscriptionMapper response = new OrganizationSubscriptionMapper();
        response.setSubscriptionType(String.valueOf(dbSub.getSubscriptionType()));
        response.setSubscriptionEndDate(Utility.getISOFromDate(dbSub.getSubscription_end_date()));
        response.setOrganizationId(dbSub.getOrganizationId());
        ;
        return response;

    }

    @Override
    public OrganizationSubscriptionMapper getOrganizationSubscriptionByOrgId(String orgId) {
        OrganizationSubscriptionMapper response = new OrganizationSubscriptionMapper();
        OrganizationSubsriptionDetails organizationSubsriptionDetails = organizationSubsriptionDetailsRepository
                .findByOrganizationId(orgId);
        if (null != organizationSubsriptionDetails) {
            response = getSubscription(organizationSubsriptionDetails);
        }
        return response;
    }

    @Override
    public InvoiceToUserMapper getInvoiceToUserByOrgId(String orgId) {
        InvoiceToUser invoiceToUser = invoiceToUserRepository.findByOrgId(orgId);
        if (null != invoiceToUser) {
            InvoiceToUserMapper invoiceToUserMapper = getInvoiceToUserById(invoiceToUser.getInvoiceToUserId());
            return invoiceToUserMapper;
        }
        return null;
    }

    @Override
    public InvoiceToUserMapper createInvoiceToUser(InvoiceToUserMapper mapper) {
        InvoiceToUser invoiceToUser = invoiceToUserRepository.findByOrgId(mapper.getOrgId());
        InvoiceToUserMapper newMapper = null;
        if (null != invoiceToUser) {
            invoiceToUser.setUserId(mapper.getUserId());
            invoiceToUser.setUpdatedBy(mapper.getCreatedBy());
            invoiceToUser.setUpdatedDate(new Date());
            InvoiceToUser invoice = invoiceToUserRepository.save(invoiceToUser);
            newMapper = getInvoiceToUserById(invoice.getInvoiceToUserId());
        } else {
            InvoiceToUser invoice = new InvoiceToUser();
            invoice.setOrgId(mapper.getOrgId());
            invoice.setUserId(mapper.getUserId());
            invoice.setCreationDate(new Date());
            invoice.setCreatedBy(mapper.getCreatedBy());
            InvoiceToUser invoice1 = invoiceToUserRepository.save(invoice);
            newMapper = getInvoiceToUserById(invoice1.getInvoiceToUserId());
        }
        return newMapper;
    }

    private InvoiceToUserMapper getInvoiceToUserById(String invoiceToUserId) {
        InvoiceToUserMapper invoiceToUserMapper = new InvoiceToUserMapper();
        InvoiceToUser invoice = invoiceToUserRepository.findById(invoiceToUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        " not found invoice to user by id " + invoiceToUserId));
        invoiceToUserMapper.setUserId(invoice.getUserId());
        invoiceToUserMapper.setOrgId(invoice.getOrgId());
        invoiceToUserMapper.setCreatedAt(Utility.getISOFromDate(invoice.getCreationDate()));
        if (null != invoice.getCreatedBy()) {
            invoiceToUserMapper.setCreatedBy(employeeService.getEmployeeFullName(invoice.getCreatedBy()));
        }
        OrganizationDetails organization = organizationRepository.findById(invoice.getOrgId()).orElse(null);
        if (null != organization) {
            invoiceToUserMapper.setOrg(organization.getName());
        }
        if (null != invoice.getUserId()) {
            invoiceToUserMapper.setUser(employeeService.getEmployeeFullName(invoice.getUserId()));
        }

        if (null != invoice.getUpdatedBy()) {
            invoiceToUserMapper.setUpdatedBy(employeeService.getEmployeeFullName(invoice.getUpdatedBy()));
        }
        if (null != invoice.getUpdatedDate()) {
            invoiceToUserMapper.setUpdatedAt(Utility.getISOFromDate(invoice.getUpdatedDate()));
        }

        return invoiceToUserMapper;
    }

    @Override
    public OrganizationSubscriptionMapper getUserSubscriptionByOrgId(String orgId) throws TemplateNotFoundException, MalformedTemplateNameException, freemarker.core.ParseException, TemplateException, IOException {
        List<OrganizationSubsriptionDetails> subcriptionList = organizationSubsriptionDetailsRepository.findAll();
        if (null != subcriptionList && !subcriptionList.isEmpty()) {
            for (OrganizationSubsriptionDetails organizationSubsriptionDetails : subcriptionList) {
                SubsriptionPrice subsriptionPrice = subscriptionPriceRepo
                        .findBySubscriptionType(organizationSubsriptionDetails.getSubscriptionType());
                if (null != subsriptionPrice) {
                    if (subsriptionPrice.getSubscriptionType() > 0) {
                        InvoiceToUser invoiceToUser = invoiceToUserRepo.findByOrgId(orgId);
                        if (null != invoiceToUser) {
                            EmployeeDetails emp = employeeRepository.getEmployeeByUserId(invoiceToUser.getUserId());
                            if (null != emp) {
                                String msg = "";
                                String text = notificationService.getNotificationEmailContent(companyName, employeeService.getEmployeeFullNameByObject(emp), msg);

                                try {
                                    emailService.sendMail("support@innoverenit.com", emp.getEmailId(), "Subscription billing...", text);
                                } catch (MessagingException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public OrganizationPaymentMapper createOrgPaymentByOrgIdAndPaymentGateway(OrganizationPaymentMapper payment) {
        AesEncryptor enp = new AesEncryptor();
        String paymentGatway = enp.convertToDatabaseColumn(payment.getPaymentGateway());
        System.out.println("paymentGatway==" + paymentGatway);
        OrganizationPayments dbPayment = organizationPaymentRep.findByOrgIdAndPaymentGateway(payment.getOrganizationId(), paymentGatway);
        OrganizationPayments dbResponse = null;
        if (null != dbPayment) {
            dbPayment.setCreationDate(new Date());
            dbPayment.setAccessKey(payment.getAccesskey());
            dbPayment.setSecreateKey(payment.getSecreateKey());
            dbResponse = organizationPaymentRep.save(dbPayment);
        } else {
            OrganizationPayments newPayment = new OrganizationPayments();
            newPayment.setCreationDate(new Date());
            newPayment.setAccessKey(payment.getAccesskey());
            newPayment.setSecreateKey(payment.getSecreateKey());
            newPayment.setPaymentGateway(payment.getPaymentGateway());
            dbResponse = organizationPaymentRep.save(newPayment);
        }
        return getPaymentGatewayDetails(dbResponse);
    }

    private OrganizationPaymentMapper getPaymentGatewayDetails(OrganizationPayments payment) {
        OrganizationPaymentMapper response = new OrganizationPaymentMapper();
        if (null != payment) {
            response.setId(payment.getId());
            response.setPaymentGateway(payment.getPaymentGateway());
            response.setAccesskey(payment.getAccessKey());
            response.setSecreateKey(payment.getSecreateKey());
            response.setOrganizationId(payment.getOrgId());
        }
        return response;

    }

    @Override
    public List<OrganizationPaymentMapper> getPaymentGatewayListByOrgId(String orgId) {
        List<OrganizationPayments> detailsList = organizationPaymentRep.findAll();
        return detailsList.stream().map(this::getPaymentGatewayDetails).collect(Collectors.toList());
    }
}
