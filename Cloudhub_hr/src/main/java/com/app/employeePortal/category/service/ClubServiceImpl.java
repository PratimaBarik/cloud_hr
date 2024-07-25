package com.app.employeePortal.category.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.Opportunity.entity.OpportunityContactLink;
import com.app.employeePortal.address.entity.AddressDetails;
import com.app.employeePortal.address.entity.AddressInfo;
import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.address.repository.AddressInfoRepository;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.category.entity.Club;
import com.app.employeePortal.category.mapper.ClubMapper;
import com.app.employeePortal.category.repository.ClubRepository;
import com.app.employeePortal.contact.entity.ContactAddressLink;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.entity.ContactInfo;
import com.app.employeePortal.contact.entity.ContactNotesLink;
import com.app.employeePortal.contact.repository.ContactAddressLinkRepository;
import com.app.employeePortal.contact.repository.ContactInfoRepository;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.customer.entity.CustomerContactLink;
import com.app.employeePortal.distributor.entity.Distributor;
import com.app.employeePortal.distributor.entity.DistributorAddressLink;
import com.app.employeePortal.distributor.entity.DistributorContactPersonLink;
import com.app.employeePortal.distributor.entity.MonthlyDistributor;
import com.app.employeePortal.distributor.entity.TodayDistributor;
import com.app.employeePortal.distributor.entity.YearlyDistributor;
import com.app.employeePortal.distributor.repository.DistributorAddressLinkRepository;
import com.app.employeePortal.distributor.repository.DistributorContactPersonLinkRepository;
import com.app.employeePortal.distributor.repository.DistributorRepository;
import com.app.employeePortal.distributor.repository.MonthlyDistributorRepository;
import com.app.employeePortal.distributor.repository.TodayDistributorRepository;
import com.app.employeePortal.distributor.repository.YearlyDistributorRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.Notes;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.investor.entity.Investor;
import com.app.employeePortal.investor.entity.InvestorAddressLink;
import com.app.employeePortal.investor.entity.InvestorContactLink;
import com.app.employeePortal.investor.entity.InvestorOppContactLink;
import com.app.employeePortal.investor.repository.InvestorAddressLinkRepo;
import com.app.employeePortal.investor.repository.InvestorContactLinkRepo;
import com.app.employeePortal.investor.repository.InvestorRepository;
import com.app.employeePortal.notification.entity.Notificationparam;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.registration.entity.Country;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.app.employeePortal.util.Utility;

import freemarker.template.TemplateException;

@Service
@Transactional

public class ClubServiceImpl implements ClubService {

    @Autowired
    ClubRepository clubRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    InvestorRepository investorRepo;
    @Autowired
    DistributorRepository distributorRepository;
    @Autowired
	CountryRepository countryRepository;
    @Autowired
    InvestorAddressLinkRepo investorAddressLinkRepo;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    AddressInfoRepository addressInfoRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    NotificationService notificationService;
    @Autowired
    YearlyDistributorRepository yearlyDistributorRepository;
    @Autowired
    TodayDistributorRepository todayDistributorRepository;
    @Autowired
    MonthlyDistributorRepository monthlyDistributorRepository;
    @Autowired
    DistributorAddressLinkRepository distributorAddressLinkRepository;
    @Value("${companyName}")
	private String companyName;
    @Autowired
    InvestorContactLinkRepo investorContactLinkRepo;
    @Autowired
	ContactRepository contactRepository;
    @Autowired
	ContactInfoRepository contactInfoRepository;
    @Autowired
    DistributorContactPersonLinkRepository distributorContactPersonLinkRepository;
    @Autowired
	ContactAddressLinkRepository contactAddressLinkRepository;
    

    @Override
    public ClubMapper saveClub(ClubMapper mapper) {
        String ClubId = null;
        if (mapper != null) {
            Club club = new Club();
            club.setCreationDate(new Date());
            club.setLiveInd(true);
            club.setClubName(mapper.getClubName());
            club.setOrgId(mapper.getOrgId());
            club.setUserId(mapper.getUserId());
            club.setDiscount(mapper.getDiscount());
            club.setNoOfShare(mapper.getNoOfShare());
            club.setInvToCusInd(false);
            club.setUpdatedBy(mapper.getUserId());
            club.setUpdationDate(new Date());

            ClubId = clubRepository.save(club).getClubId();

        }
        ClubMapper resultMapper = getClubByClubId(ClubId);
        return resultMapper;
    }

    public ClubMapper getClubByClubId(String ClubId) {

        Club club = clubRepository.findByClubIdAndLiveInd(ClubId, true);
        ClubMapper clubMapper = new ClubMapper();

        if (null != club) {

        	clubMapper.setCreationDate(Utility.getISOFromDate(club.getCreationDate()));
        	clubMapper.setLiveInd(true);
            clubMapper.setClubName(club.getClubName());
            clubMapper.setOrgId(club.getOrgId());
            clubMapper.setUpdatedBy(employeeService.getEmployeeFullName(club.getUserId()));
            clubMapper.setUpdationDate(Utility.getISOFromDate(club.getUpdationDate()));
            clubMapper.setUserId(club.getUserId());
            clubMapper.setClubId(club.getClubId());
            clubMapper.setDiscount(club.getDiscount());
            clubMapper.setNoOfShare(club.getNoOfShare());
            clubMapper.setInvToCusInd(club.isInvToCusInd());

        }

        return clubMapper;
    }

    @Override
    public List<ClubMapper> getClubByOrgId(String orgId) {

        List<ClubMapper> resultMapper = new ArrayList<>();
        List<Club> list = clubRepository.findByOrgIdAndLiveInd(orgId, true);
        if (null != list) {
            resultMapper = list.stream().map(li -> getClubByClubId(li.getClubId()))
                    .collect(Collectors.toList());
        }
        Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

        List<Club> list1 = clubRepository.findAll();
        if (null != list1 && !list1.isEmpty()) {
            Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

            resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
            resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
        }
        return resultMapper;
    }

    @Override
    public ClubMapper updateClub(String clubId, ClubMapper mapper) {

        Club club = clubRepository.findByClubIdAndLiveInd(clubId, true);
        if (null != club) {
        	club.setClubName(mapper.getClubName());
            club.setOrgId(mapper.getOrgId());
            club.setUpdatedBy(mapper.getUserId());
            club.setUpdationDate(new Date());
            club.setUserId(mapper.getUserId());
            club.setDiscount(mapper.getDiscount());
            club.setNoOfShare(mapper.getNoOfShare());
            clubRepository.save(club);
        }
        ClubMapper resultMapper = getClubByClubId(clubId);
        return resultMapper;
    }

    @Override
    public void deleteClub(String clubId, String userId) {

        if (null != clubId) {
            Club club = clubRepository.findByClubIdAndLiveInd(clubId, true);

            club.setUpdationDate(new Date());
            club.setUpdatedBy(userId);
            club.setLiveInd(false);
            clubRepository.save(club);
        }
    }

    @Override
    public List<ClubMapper> getClubByClubName(String name, String orgId) {
        List<Club> list = clubRepository.findByClubNameContainingAndLiveIndAndOrgId(name, true, orgId);
        List<ClubMapper> resultList = new ArrayList<ClubMapper>();
        if (null != list && !list.isEmpty()) {
            list.stream().map(Quality -> {
                ClubMapper mapper = getClubByClubId(Quality.getClubId());
                if (null != mapper) {
                    resultList.add(mapper);
                }
                return resultList;
            }).collect(Collectors.toList());
        }
        Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

        List<Club> list1 = clubRepository.findAll();
        if (null != list1 && !list1.isEmpty()) {
            Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

            resultList.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
            resultList.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
        }
        return resultList;
    }

    @Override
    public HashMap getClubCountByOrgId(String orgId) {
        HashMap map = new HashMap();
        List<Club> list = clubRepository.findByOrgIdAndLiveInd(orgId, true);
        map.put("ClubCount", list.size());
        return map;
    }

	@Override
	public ClubMapper updateInvToCusIndByClubId(String clubId, ClubMapper mapper) throws IOException, TemplateException {
		ClubMapper resultMapper = new ClubMapper();
		Club club = clubRepository.findByClubIdAndLiveInd(clubId, true);
        if (null != club) {
        	if(mapper.isInvToCusInd()) {
	            club.setUpdatedBy(mapper.getUserId());
	            club.setUpdationDate(new Date());
	            club.setInvToCusInd(mapper.isInvToCusInd());
	            clubRepository.save(club);
	            
	            List<Investor> investorList = investorRepo.findByClubAndLiveInd(clubId, true);
	            if (null != investorList && !investorList.isEmpty()) {
	            	for (Investor investor : investorList) {
	            		Distributor distributor = new Distributor();
            			distributor.setName(investor.getName());
            			//distributor.setImageId(distributorDTO.getImageId());
            			distributor.setUrl(investor.getUrl());
            			distributor.setPhoneNo(investor.getPhoneNumber());
            			distributor.setDialCode(investor.getCountryDialCode());
            			distributor.setDescription(investor.getNotes());
            			distributor.setMobileNo(investor.getPhoneNumber());
            			distributor.setEmailId(investor.getEmail());
            			distributor.setImageURL(investor.getImageURL());
            			distributor.setCountryId(investor.getCountry());
            			if (investor.getCountry() != null) {
            				Country country = countryRepository.getByCountryId(investor.getCountry());
            				distributor.setCountryName(country.getCountryName());
            			}
            			distributor.setAssignTo(investor.getAssignedTo());
            			distributor.setUserId(investor.getUserId());
            			distributor.setOrgId(investor.getOrganizationId());
            			distributor.setCreateAt(new Date());
            			distributor.setInvestor(investor.getInvestorId());
            			distributor.setClub(investor.getClub());
            			
            			distributorRepository.save(distributor);
        

            	            List<InvestorAddressLink> investorAddressList = investorAddressLinkRepo
            	                    .getAddressListByInvestorId(investor.getInvestorId());

            	            /* fetch investor address & set to investor mapper */
            	            if (null != investorAddressList && !investorAddressList.isEmpty()) {

            	                for (InvestorAddressLink investorAddressLink : investorAddressList) {
            	                    AddressDetails addressDetails1 = addressRepository
            	                            .getAddressDetailsByAddressId(investorAddressLink.getAddressId());

            	                    if (null != addressDetails1) {
            						/* insert to address info & address deatils & customeraddressLink */

            						AddressInfo addressInfo = new AddressInfo();
            						addressInfo.setCreationDate(new Date());
            						// addressInfo.setCreatorId(candidateMapperr.getUserId());
            						AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

            						String addressId = addressInfoo.getId();

            						if (null != addressId) {

            							AddressDetails addressDetails = new AddressDetails();
            							addressDetails.setAddressId(addressId);
            							addressDetails.setAddressLine1(addressDetails1.getAddressLine1());
            							addressDetails.setAddressLine2(addressDetails1.getAddressLine2());
            							addressDetails.setAddressType(addressDetails1.getAddressType());
            							addressDetails.setCountry(addressDetails1.getCountry());
            							addressDetails.setCreationDate(new Date());
            							addressDetails.setStreet(addressDetails1.getStreet());
            							addressDetails.setCity(addressDetails1.getCity());
            							addressDetails.setPostalCode(addressDetails1.getPostalCode());
            							addressDetails.setTown(addressDetails1.getTown());
            							addressDetails.setState(addressDetails1.getState());
            							addressDetails.setLatitude(addressDetails1.getLatitude());
            							addressDetails.setLongitude(addressDetails1.getLongitude());
            							addressDetails.setLiveInd(true);
            							addressDetails.setHouseNo(addressDetails1.getHouseNo());
            							addressRepository.save(addressDetails);

            							DistributorAddressLink distributorAddressLink = new DistributorAddressLink();
            							distributorAddressLink.setDistributorId(distributor.getId());
            							distributorAddressLink.setAddressId(addressId);
            							distributorAddressLink.setCreationDate(new Date());

            							distributorAddressLinkRepository.save(distributorAddressLink);

            						}
            					}
            				}
            			}
            	            
            	            
            	       List<InvestorContactLink> investorContactLinkList = investorContactLinkRepo
            	               .getContactIdByInvestorId(investor.getInvestorId());
            	        if (investorContactLinkList != null && !investorContactLinkList.isEmpty()) {  
            	        	for (InvestorContactLink investorContact : investorContactLinkList) {
            	        		ContactDetails contact1 = contactRepository.
            	        				getContactDetailsById(investorContact.getContactId());
            	        		if(null!=contact1) {
            	        			String contactId = null;

            	        			ContactInfo contactInfo = new ContactInfo();
            	        			contactInfo.setCreation_date(new Date());

            	        			ContactInfo contactt = contactInfoRepository.save(contactInfo);

            	        			contactId = contactt.getContact_id();
            	        			System.out.println("contact id.........." + contactId);

            	        			ContactDetails contact = new ContactDetails();

            	        			contact.setContactId(contactId);
            	        			contact.setSalutation(contact1.getSalutation());
            	        			contact.setFirst_name(contact1.getFirst_name());
            	        			contact.setMiddle_name(contact1.getMiddle_name());
            	        			contact.setLast_name(contact1.getLast_name());
            	        			contact.setMobile_number(contact1.getMobile_number());
            	        			contact.setPhone_number(contact1.getPhone_number());
            	        			contact.setEmail_id(contact1.getEmail_id());
            	        			contact.setLinkedin_public_url(contact1.getLinkedin_public_url());
            	        			// contact.setTag_with_company(contactMapperr.getTagWithCompany());
            	        			contact.setDepartment(contact1.getDepartment());
            	        			contact.setDesignation(contact1.getDesignation());
            	        			contact.setSector(contact1.getSector());
            	        			contact.setSalary(contact1.getSalary());
            	        			contact.setNotes(contact1.getNotes());
            	        			contact.setCountry_dialcode(contact1.getCountry_dialcode());
            	        			contact.setCountry_dialcode1(contact1.getCountry_dialcode1());
            	        			contact.setImageId(contact1.getImageId());
            	        			contact.setCreationDate(new Date());
            	        			contact.setContactId(contactId);
            	        			contact.setAccessInd(0);
            	        			contact.setWhatsappNumber(contact1.getWhatsappNumber());
            	        			contact.setSource(contact1.getSource());
            	        			contact.setSourceUserId(contact1.getSourceUserId());
            	        			contact.setBedroom(contact1.getBedroom());
            	        			contact.setPrice(contact1.getPrice());
            	        			contact.setPropertyType(contact1.getPropertyType());
            	        			contact.setLiveInd(true);
            	        			contact.setUser_id(contact1.getUser_id());
            	        			contact.setOrganization_id(contact1.getOrganization_id());

            	        			String middleName3 = " ";
            	        			String lastName3 = "";

            	        			if (!StringUtils.isEmpty(contact1.getLast_name())) {

            	        				lastName3 = contact1.getLast_name();
            	        			}
            	        			if (contact1.getMiddle_name() != null && contact1.getMiddle_name().length() > 0) {

            	        				middleName3 = contact1.getMiddle_name();
            	        				contact.setFullName(contact1.getFirst_name() + " " + middleName3 + " " + lastName3);
            	        			} else {

            	        				contact.setFullName(contact1.getFirst_name() + " " + lastName3);
            	        			}
            	        			
            	        			
            	        			contact.setContactType("Distributor");
    								contact.setTag_with_company(distributor.getId());
    								contactRepository.save(contact);
    								
    								DistributorContactPersonLink distributorContactLink = new DistributorContactPersonLink();
    								distributorContactLink.setContactId(contactId);
    								distributorContactLink.setDistributorId(distributor.getId());
    								distributorContactPersonLinkRepository.save(distributorContactLink);
    								
    						List<ContactAddressLink> contactAddressList = contactAddressLinkRepository.getAddressListByContactId(contactId);	
    						if (null != contactAddressList && !contactAddressList.isEmpty()) {
            	        		for (ContactAddressLink addressMapper : contactAddressList) {
	            	        			 AddressDetails addressDetails1 = addressRepository
	             	                            .getAddressDetailsByAddressId(addressMapper.getAddress_id());
	
	             	                    if (null != addressDetails1) {
	             						/* insert to address info & address deatils & customeraddressLink */
	
	             						AddressInfo addressInfo = new AddressInfo();
	             						addressInfo.setCreationDate(new Date());
	             						// addressInfo.setCreatorId(candidateMapperr.getUserId());
	             						AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);
	
	             						String addressId = addressInfoo.getId();
	
	             						if (null != addressId) {
	
	             							AddressDetails addressDetails = new AddressDetails();
	             							addressDetails.setAddressId(addressId);
	             							addressDetails.setAddressLine1(addressDetails1.getAddressLine1());
	             							addressDetails.setAddressLine2(addressDetails1.getAddressLine2());
	             							addressDetails.setAddressType(addressDetails1.getAddressType());
	             							addressDetails.setCountry(addressDetails1.getCountry());
	             							addressDetails.setCreationDate(new Date());
	             							addressDetails.setStreet(addressDetails1.getStreet());
	             							addressDetails.setCity(addressDetails1.getCity());
	             							addressDetails.setPostalCode(addressDetails1.getPostalCode());
	             							addressDetails.setTown(addressDetails1.getTown());
	             							addressDetails.setState(addressDetails1.getState());
	             							addressDetails.setLatitude(addressDetails1.getLatitude());
	             							addressDetails.setLongitude(addressDetails1.getLongitude());
	             							addressDetails.setLiveInd(true);
	             							addressDetails.setHouseNo(addressDetails1.getHouseNo());
	             							addressRepository.save(addressDetails);
	
	             							ContactAddressLink contactAddressLink = new ContactAddressLink();
	            	        				contactAddressLink.setContact_id(contactId);
	            	        				contactAddressLink.setAddress_id(addressId);
	            	        				contactAddressLink.setCreation_date(new Date());
	            	        				contactAddressLinkRepository.save(contactAddressLink);
	
	             						}
	             					}
            	        		}
    						}

            	        			ContactDetails dbContact = contactRepository.save(contact);

            	        			System.out.println("contactdetails id.........." + dbContact.getContact_details_id());

            	        			/* insert to notification table */
            	        			Notificationparam param = new Notificationparam();
            	        			EmployeeDetails emp = employeeRepository.getEmployeesByuserId(contact1.getUser_id());
            	        			String name = employeeService.getEmployeeFullNameByObject(emp);
            	        			param.setEmployeeDetails(emp);
            	        			param.setAdminMsg("Contact " + "'" + Utility.FullName(contact1.getFirst_name(),
            	        					contact1.getMiddle_name(), contact1.getLast_name()) + "' created by " + name);
            	        			param.setOwnMsg("Contact " + Utility.FullName(contact1.getFirst_name(), contact1.getMiddle_name(),
            	        					contact1.getLast_name()) + " created.");
            	        			param.setNotificationType("Contact creation");
            	        			param.setProcessNmae("Contact");
            	        			param.setType("create");
            	        			param.setEmailSubject("Korero alert- Contact created");
            	        			param.setCompanyName(companyName);
            	        			param.setUserId(contact1.getUser_id());
            	        			notificationService.createNotificationForDynamicUsers(param);
            	        		}
							}
            	        }

            			

            			TodayDistributor distribitorToday = todayDistributorRepository
            					.findByCreateAt(Utility.removeTime(new Date()));
            			if (distribitorToday != null) {
            				distribitorToday.setDistributorCount(distribitorToday.getDistributorCount() + 1);
            				distribitorToday.setPendingDistributor(distribitorToday.getPendingDistributor() + 1);
            				todayDistributorRepository.save(distribitorToday);
            			} else {
            				TodayDistributor distribitorToday1 = new TodayDistributor();
            				distribitorToday1.setDistributorCount(1);
            				distribitorToday1.setPendingDistributor(1);
            				distribitorToday1.setCreateAt(Utility.removeTime(new Date()));
            				todayDistributorRepository.save(distribitorToday1);
            			}

            			Calendar calendar = Calendar.getInstance();
            			String year = Integer.toString(calendar.get(Calendar.YEAR));

            			YearlyDistributor yearlyDistributor = yearlyDistributorRepository.findByYear(year);
            			if (yearlyDistributor != null) {
            				yearlyDistributor.setDistributorCount(yearlyDistributor.getDistributorCount() + 1);
            				yearlyDistributor.setPendingDistributor(yearlyDistributor.getPendingDistributor() + 1);
            				yearlyDistributorRepository.save(yearlyDistributor);
            			} else {
            				YearlyDistributor yearlyDistributor1 = new YearlyDistributor();
            				yearlyDistributor1.setDistributorCount(1);
            				yearlyDistributor1.setPendingDistributor(1);
            				yearlyDistributor1.setYear(year);
            				yearlyDistributor1.setCreateAt(new Date());
            				yearlyDistributorRepository.save(yearlyDistributor1);
            			}
            			String month = Integer.toString(calendar.get(Calendar.MONTH));
            			MonthlyDistributor monthlyDistributor = monthlyDistributorRepository.findByMonth(month);
            			if (monthlyDistributor != null) {
            				monthlyDistributor.setDistributorCount(monthlyDistributor.getDistributorCount() + 1);
            				monthlyDistributor.setPendingDistributor(monthlyDistributor.getPendingDistributor() + 1);
            				monthlyDistributorRepository.save(monthlyDistributor);
            			} else {
            				MonthlyDistributor monthlyDistributor1 = new MonthlyDistributor();
            				monthlyDistributor1.setDistributorCount(1);
            				monthlyDistributor1.setPendingDistributor(1);
            				monthlyDistributor1.setMonth(month);
            				monthlyDistributor1.setCreateAt(new Date());
            				monthlyDistributorRepository.save(monthlyDistributor1);
            			}
            			
            			/* insert to Notification Table */
            			Notificationparam param = new Notificationparam();
            			EmployeeDetails emp = employeeRepository.getEmployeesByuserId(investor.getUserId());
            			String name = employeeService.getEmployeeFullNameByObject(emp);
            			param.setEmployeeDetails(emp);
            			param.setAdminMsg("Customer " + "'" + investor.getName() + "' created by " + name);
            			param.setOwnMsg("Customer " + investor.getName() + " created.");
            			param.setNotificationType("Customer Creation");
            			param.setProcessNmae("Customer");
            			param.setType("create");
            			param.setEmailSubject("Korero alert- Customer created");
            			param.setCompanyName(companyName);
            			param.setUserId(investor.getUserId());

            			if (!investor.getUserId().equals(investor.getAssignedTo())) {
            				List<String> assignToUserIds = new ArrayList<>();
            				assignToUserIds.add(investor.getAssignedTo());
            				param.setAssignToUserIds(assignToUserIds);
            				param.setAssignToMsg("Customer " + "'" + investor.getName() + "'" + " assigned to "
            						+ employeeService.getEmployeeFullName(investor.getAssignedTo()) + " by " + name);
            			}
            			notificationService.createNotificationForDynamicUsers(param);
            			
            			

					}
	            }
	            
	            resultMapper = getClubByClubId(clubId);   
        	}else {
        		club.setUpdatedBy(mapper.getUserId());
                club.setUpdationDate(new Date());
                club.setInvToCusInd(mapper.isInvToCusInd());
                clubRepository.save(club);
                
                List<Investor> investorList = investorRepo.findByClubAndLiveInd(clubId, true);
	            if (null != investorList && !investorList.isEmpty()) {
					for (Investor investor : investorList) {
						Distributor distributor = distributorRepository.
								findByInvestorAndActive(investor.getInvestorId(), true);	
						if(null!=distributor) {
							List<DistributorAddressLink> distributorAddressLink = distributorAddressLinkRepository.
									findByDistributorId(distributor.getId());
							if(null!=distributorAddressLink && !distributorAddressLink.isEmpty()) {
								for (DistributorAddressLink addressLink : distributorAddressLink) {
									 AddressDetails addressDetails1 = addressRepository
	         	                            .getAddressDetailsByAddressId(addressLink.getAddressId());
									 if(null!=addressDetails1) {
										 addressRepository.delete(addressDetails1);
									 }
									 distributorAddressLinkRepository.delete(addressLink);
								}
							}
							
							List<DistributorContactPersonLink> distributorContactLinkList = distributorContactPersonLinkRepository
		            	               .findByDistributorId(distributor.getId());
		            	        if (distributorContactLinkList != null && !distributorContactLinkList.isEmpty()) {  
		            	        	for (DistributorContactPersonLink distributorContact : distributorContactLinkList) {
		            	        		ContactDetails contact1 = contactRepository.
		            	        				getContactDetailsById(distributorContact.getContactId());
		            	        		if(null!=contact1) {
		            	        			List<ContactAddressLink> contactAddressLink = contactAddressLinkRepository.
		            	        					getAddressListByContactId(contact1.getContactId());
		        							if(null!=contactAddressLink && !contactAddressLink.isEmpty()) {
		        								for (ContactAddressLink addressLink : contactAddressLink) {
		        									 AddressDetails addressDetails1 = addressRepository
		        	         	                            .getAddressDetailsByAddressId(addressLink.getAddress_id());
		        									 if(null!=addressDetails1) {
		        										 addressRepository.delete(addressDetails1);
		        									 }
		        									 contactAddressLinkRepository.delete(addressLink);
		        								}
		        							}
		        							contactRepository.delete(contact1);
		            	        		}
		            	        		distributorContactPersonLinkRepository.delete(distributorContact);
		            	        	}
		            	        }
							
							TodayDistributor distribitorToday = todayDistributorRepository
									.findByCreateAt(Utility.removeTime(new Date()));
							if (null != distribitorToday) {
								distribitorToday.setDistributorCount(distribitorToday.getDistributorCount() - 1);
								distribitorToday.setPendingDistributor(distribitorToday.getPendingDistributor() - 1);
								todayDistributorRepository.save(distribitorToday);
							}
							Calendar calendar = Calendar.getInstance();
							String year = Integer.toString(calendar.get(Calendar.YEAR));

							YearlyDistributor yearlyDistributor = yearlyDistributorRepository.findByYear(year);
							if (null != yearlyDistributor) {
								yearlyDistributor.setDistributorCount(yearlyDistributor.getDistributorCount() - 1);
								yearlyDistributor.setPendingDistributor(yearlyDistributor.getPendingDistributor() - 1);
								yearlyDistributorRepository.save(yearlyDistributor);
							}
							String month = Integer.toString(calendar.get(Calendar.MONTH));
							MonthlyDistributor monthlyDistributor = monthlyDistributorRepository.findByMonth(month);
							if (null != monthlyDistributor) {
									monthlyDistributor.setDistributorCount(monthlyDistributor.getDistributorCount() - 1);
									monthlyDistributor.setPendingDistributor(monthlyDistributor.getPendingDistributor() - 1);
									monthlyDistributorRepository.save(monthlyDistributor);
							}
							
							distributorRepository.delete(distributor);

							/* insert to Notification Table */
							Notificationparam param = new Notificationparam();
							EmployeeDetails emp = employeeRepository.getEmployeesByuserId(distributor.getUserId());
							String name = employeeService.getEmployeeFullNameByObject(emp);
							param.setEmployeeDetails(emp);
							param.setAdminMsg("Customer " + "'" + distributor.getName() + "' deleted");
							param.setOwnMsg("Customer " + distributor.getName() + " deleted.");
							param.setNotificationType("Customer delete");
							param.setProcessNmae("Customer");
							param.setType("delete");
							param.setEmailSubject("Korero alert- Customer deleted");
							param.setCompanyName(companyName);
							param.setUserId(distributor.getUserId());

							if (distributor.getUserId().equals(distributor.getAssignTo())) {
								List<String> assignToUserIds = new ArrayList<>();
								assignToUserIds.add(distributor.getAssignTo());
								param.setAssignToUserIds(assignToUserIds);
								param.setAssignToMsg("Customer " + "'" + distributor.getName() + "' deleted");
							}
							notificationService.createNotificationForDynamicUsers(param);
							
							
						}
					}
	            }
                
                resultMapper = getClubByClubId(clubId);
        	}
        }
	        return resultMapper;
	}

	@Override
	public boolean checkNameInClub(String clubName, String orgId) {
        List<Club> club = clubRepository.findByClubNameAndLiveIndAndOrgId(clubName, true,
                orgId);
        if (club.size() > 0) {
            return true;
        }
        return false;
    }

	@Override
	public boolean checkNameInClubInUpdate(String clubName, String orgId, String clubId) {
        List<Club> club = clubRepository.findByClubNameAndClubIdNotAndLiveIndAndOrgId(clubName,clubId, true,
                orgId);
        if (club.size() > 0) {
            return true;
        }
        return false;
    }

}