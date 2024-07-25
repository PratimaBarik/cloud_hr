package com.app.employeePortal.monster.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.app.employeePortal.address.entity.AddressDetails;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.monster.entity.MonsterBoard;
import com.app.employeePortal.monster.entity.MonsterCategory;
import com.app.employeePortal.monster.entity.MonsterCredentials;
import com.app.employeePortal.monster.entity.MonsterIndustry;
import com.app.employeePortal.monster.entity.MonsterOccupation;
import com.app.employeePortal.monster.entity.MonsterPublish;
import com.app.employeePortal.monster.mapper.MonsterBoardMapper;
import com.app.employeePortal.monster.mapper.MonsterCategoryMapper;
import com.app.employeePortal.monster.mapper.MonsterCredentialsMapper;
import com.app.employeePortal.monster.mapper.MonsterIndustryMapper;
import com.app.employeePortal.monster.mapper.MonsterOccupationMapper;
import com.app.employeePortal.monster.mapper.MonsterPublishMapper;
import com.app.employeePortal.monster.repository.MonsterBoardRepository;
import com.app.employeePortal.monster.repository.MonsterCategoryRepository;
import com.app.employeePortal.monster.repository.MonsterCredentialsRepository;
import com.app.employeePortal.monster.repository.MonsterIndustryRepository;
import com.app.employeePortal.monster.repository.MonsterOccupationRepository;
import com.app.employeePortal.monster.repository.MonsterPublishRepository;
import com.app.employeePortal.organization.entity.OrganizationAddressLink;
import com.app.employeePortal.organization.entity.OrganizationDetails;
import com.app.employeePortal.organization.repository.OrganizationAddressLinkRepository;
import com.app.employeePortal.organization.repository.OrganizationRepository;
import com.app.employeePortal.recruitment.entity.OpportunityRecruitDetails;
import com.app.employeePortal.recruitment.entity.RecruitmentAddressLink;
import com.app.employeePortal.recruitment.repository.RecruitmentAddressLinkRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentOpportunityDetailsRepository;
import com.app.employeePortal.registration.entity.Country;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.app.employeePortal.util.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@Builder
public class MonsterServiceImpl implements MonsterService {
	
	@Autowired
	MonsterCredentialsRepository monsterCredentialsRepository;
	@Autowired
	MonsterBoardRepository monsterBoardRepository;
	@Autowired
	MonsterCategoryRepository monsterCategoryRepository;
	@Autowired
	MonsterOccupationRepository monsterOccupationRepository;
	@Autowired
	MonsterIndustryRepository monsterIndustryRepository;
	@Autowired
	MonsterPublishRepository monsterPublishRepository;
	@Autowired
	RecruitmentOpportunityDetailsRepository recruitmentOpportunityDetailsRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	OrganizationRepository organizationRepository;
	@Autowired
	RecruitmentAddressLinkRepository recruitmentAddressLinkRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	OrganizationAddressLinkRepository organizationAddressLinkRepository;
	@Autowired
	CountryRepository countryRepository;
	
	@Override
	public String saveMonsterCredentials(MonsterCredentialsMapper monsterCredentialsMapper) {
		
		String Id = null;
		   MonsterCredentials dbMonsterCredentials = monsterCredentialsRepository.findByOrgId(monsterCredentialsMapper.getOrgId());
			
	        if (null != dbMonsterCredentials) {
	        	dbMonsterCredentials.setUserName(monsterCredentialsMapper.getUserName());
	        	dbMonsterCredentials.setPassword(monsterCredentialsMapper.getPassword());
	        	dbMonsterCredentials.setMonsterInd(monsterCredentialsMapper.isMonsterInd());
	        	dbMonsterCredentials.setLastUpdatedOn(new Date());
	    
	            Id = monsterCredentialsRepository.save(dbMonsterCredentials).getMonsterCredentialsId();
	        	}else { 
		
	    MonsterCredentials newMonsterCredentials = new MonsterCredentials();
	    newMonsterCredentials.setUserId(monsterCredentialsMapper.getUserId());
	    newMonsterCredentials.setOrgId(monsterCredentialsMapper.getOrgId());
	    newMonsterCredentials.setUserName(monsterCredentialsMapper.getUserName());
	    newMonsterCredentials.setPassword(monsterCredentialsMapper.getPassword());
	    newMonsterCredentials.setMonsterInd(monsterCredentialsMapper.isMonsterInd());
	    newMonsterCredentials.setLastUpdatedOn(new Date());
        
        Id = monsterCredentialsRepository.save(newMonsterCredentials).getMonsterCredentialsId();
	        	}
        
        return Id;
		
		
    }

	@Override
	public MonsterCredentialsMapper getMonsterCredentialsByOrgId(String orgId) {
		 
		MonsterCredentialsMapper mapper = new MonsterCredentialsMapper();
		MonsterCredentials pem = monsterCredentialsRepository.findByOrgId(orgId);
	 		if (pem != null) {
	 			mapper.setMonsterCredentialsId(pem.getMonsterCredentialsId());
	 			mapper.setUserId(pem.getUserId());
	 			mapper.setOrgId(pem.getOrgId());
	 			mapper.setUserName(pem.getUserName());
	 			mapper.setPassword(pem.getPassword());
	 			mapper.setMonsterInd(pem.isMonsterInd());
	 			mapper.setLastUpdatedOn(Utility.getISOFromDate(pem.getLastUpdatedOn()));
	 			
	 			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(pem.getUserId());
				  
				if(employeeDetails !=null) {
					
					String middleName =" ";
					String lastName ="";
					
					if(!StringUtils.isEmpty(employeeDetails.getLastName())) {
						 
						 lastName = employeeDetails.getLastName();
					 }
					 

					 if(employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length()>0) {

					 
						 middleName = employeeDetails.getMiddleName();
						 mapper.setName(employeeDetails.getFirstName()+" "+middleName+" "+lastName);
					 }else {
						 
						 mapper.setName(employeeDetails.getFirstName()+" "+lastName);
					 }
				
				}
	 			
	 		}
	 		return mapper;
	 	
	 	}

	@Override
	public List<MonsterBoardMapper> jobBoardList() {
		
		List<MonsterBoardMapper> mapper = new ArrayList<MonsterBoardMapper>();
		List<MonsterBoard> pem = monsterBoardRepository.findAll();
	 		if (pem != null) {
	 			//for(MonsterBoard monsterBoard :pem) {
				pem.stream().map(monsterBoard -> {
	 			MonsterBoardMapper	monsterBoardMapper=new MonsterBoardMapper();
	 			monsterBoardMapper.setMonsterBoardId(monsterBoard.getMonsterBoardId());
	 			monsterBoardMapper.setJobBoardName(monsterBoard.getJobBoardName());
	 			monsterBoardMapper.setTestBoardId(monsterBoard.getTestBoardId());
	 			mapper.add(monsterBoardMapper);
				 return mapper;
	 			}	).collect(Collectors.toList());
	 		}
	 		return mapper;
	 	
	 	}

	@Override
	public List<MonsterCategoryMapper> jobCategoryList() {
		List<MonsterCategoryMapper> mapper = new ArrayList<MonsterCategoryMapper>();
		List<MonsterCategory> pem = monsterCategoryRepository.findAll();
	 		if (pem != null) {
	 			//for(MonsterCategory monsterCategory :pem) {
				pem.stream().map(monsterCategory -> {
				MonsterCategoryMapper monsterCategoryMapper=new MonsterCategoryMapper();
	 			monsterCategoryMapper.setMonsterCategoryId(monsterCategory.getMonsterCategoryId());
	 			monsterCategoryMapper.setJobCategoryId(monsterCategory.getJobCategoryId());
	 			monsterCategoryMapper.setJobCategoryAlias(monsterCategory.getJobCategoryAlias());
	 			mapper.add(monsterCategoryMapper);
					return mapper;
				}	).collect(Collectors.toList());
	 		}
	 		return mapper;
	 	
	 	}

	@Override
	public List<MonsterOccupationMapper> jobOccupationList() {
		List<MonsterOccupationMapper> mapper = new ArrayList<MonsterOccupationMapper>();
		List<MonsterOccupation> pem = monsterOccupationRepository.findAll();
	 		if (pem != null) {
	 			//for(MonsterOccupation monsterOccupation :pem) {
				pem.stream().map(monsterOccupation -> {
				MonsterOccupationMapper monsterOccupationMapper=new MonsterOccupationMapper();
	 			monsterOccupationMapper.setMonsterOccupationId(monsterOccupation.getMonsterOccupationId());
	 			monsterOccupationMapper.setOccupationId(monsterOccupation.getOccupationId());
	 			monsterOccupationMapper.setOccupationAlias(monsterOccupation.getOccupationAlias());
	 			mapper.add(monsterOccupationMapper);
					return mapper;
				}	).collect(Collectors.toList());
	 		}
	 		return mapper;
	 	
	 	}

	@Override
	public List<MonsterIndustryMapper> jobIndustryList() {
		List<MonsterIndustryMapper> mapper = new ArrayList<MonsterIndustryMapper>();
		List<MonsterIndustry> pem = monsterIndustryRepository.findAll();
	 		if (pem != null) {
	 			//for(MonsterIndustry monsterIndustry :pem) {
				pem.stream().map(monsterIndustry -> {
	 				MonsterIndustryMapper monsterIndustryMapper=new MonsterIndustryMapper();
	 				monsterIndustryMapper.setMonsterIndustryId(monsterIndustry.getMonsterIndustryId());
	 				monsterIndustryMapper.setIndustryId(monsterIndustry.getIndustryId());
	 				monsterIndustryMapper.setIndustryAlias(monsterIndustry.getIndustryAlias());
	 			mapper.add(monsterIndustryMapper);
				return mapper;
			}	).collect(Collectors.toList());
	 		}
	 		return mapper;
	 	
	 	}

	@Override
	public String saveToMonsterPublish(MonsterPublishMapper monsterPublishMapper) {
		
		String monsterPublishId = null;
		if(monsterPublishMapper != null) {
		MonsterPublish monsterPublish = new MonsterPublish();
		monsterPublish.setRecruitmentId(monsterPublishMapper.getRecruitmentId());
		monsterPublish.setMonsterId(monsterPublishMapper.getMonsterId());
		monsterPublish.setJobDuration(monsterPublishMapper.getJobDuration());
		monsterPublish.setJobCategory(monsterPublishMapper.getJobCategory());
		monsterPublish.setJobOccupation(monsterPublishMapper.getJobOccupation());
		monsterPublish.setJobBoardName(monsterPublishMapper.getJobBoardName());
		monsterPublish.setDisplayTemplate(monsterPublishMapper.getDisplayTemplate());
		monsterPublish.setIndustry(monsterPublishMapper.getIndustry());
		monsterPublish.setOrganizationId(monsterPublishMapper.getOrganizationId());
		monsterPublish.setUserId(monsterPublishMapper.getUserId());
		MonsterPublish dbMonsterPublish=monsterPublishRepository.save(monsterPublish);
		monsterPublishId = dbMonsterPublish.getMonsterPublishId();
		}
		
		MonsterCredentials monsterCredentials = monsterCredentialsRepository.findByOrgId(monsterPublishMapper.getOrganizationId());
		if(null!=monsterCredentials) {
			monsterPublishMapper.setUserName(monsterCredentials.getUserName());
			monsterPublishMapper.setPassword(monsterCredentials.getPassword());
		}
		
		OpportunityRecruitDetails opportunityRecruitDetails = recruitmentOpportunityDetailsRepository.
				getRecruitmentDetailsByRecruitmentId(monsterPublishMapper.getRecruitmentId());
		if(null!=opportunityRecruitDetails) {
			
			monsterPublishMapper.setJobRefCode(opportunityRecruitDetails.getJob_order());
			monsterPublishMapper.setJobTitle(opportunityRecruitDetails.getName());
			monsterPublishMapper.setDescription(opportunityRecruitDetails.getDescription());
//			monsterPublishMapper.setJobAction("addOrUpdate");
//			monsterPublishMapper.setHideAll("false");
//			monsterPublishMapper.setHideCompanyName("false");
//			monsterPublishMapper.setHideEmailAddress("true");
//			
			List<RecruitmentAddressLink> recruitmentAddressLink = recruitmentAddressLinkRepository.
					getAddressListByRecruitmentId(opportunityRecruitDetails.getRecruitment_id());
			//List<AddressMapper> addressList = new ArrayList<AddressMapper>();
			if (null != recruitmentAddressLink && !recruitmentAddressLink.isEmpty()) {

				for (RecruitmentAddressLink recruitmentAddressLink1 : recruitmentAddressLink) {
					AddressDetails addressDetails = addressRepository
							.getAddressDetailsByAddressId(recruitmentAddressLink1.getAddressId());

					//AddressMapper addressMapper = new AddressMapper();
					if (null != addressDetails) {
						Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(addressDetails.getCountry(),opportunityRecruitDetails.getOrgId());
						
						
						monsterPublishMapper.setLocationPostalCode(addressDetails.getPostalCode());
						monsterPublishMapper.setLocationCity(addressDetails.getCity());
						//monsterPublishMapper.setCountry(addressDetails.getCountry());
						monsterPublishMapper.setLocationCountryCode(country.getCountry_alpha3_code());
						monsterPublishMapper.setLocationState(addressDetails.getState());
						
						
						//addressList.add(addressMapper);
					}
				}

				//System.out.println("addressList.......... " + addressList);
			}
			//monsterPublishMapper.setLocation(addressList);
			
			
		}
		
		EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(monsterPublishMapper.getUserId());
		if(null!=employeeDetails) {
			monsterPublishMapper.setManagerName(employeeDetails.getFullName());
			monsterPublishMapper.setEmail(employeeDetails.getEmailId());
		}
		
		OrganizationDetails organizationDetails = organizationRepository.getOrganizationDetailsById(monsterPublishMapper.getOrganizationId());
		if(null!=organizationDetails) {
			monsterPublishMapper.setCompanyName(organizationDetails.getName());
			
			List<OrganizationAddressLink> organizationAddressLink = organizationAddressLinkRepository.
					getAddressListByOrgId(organizationDetails.getOrganization_id());
			//List<AddressMapper> addressList1 = new ArrayList<AddressMapper>();
			if (null != organizationAddressLink && !organizationAddressLink.isEmpty()) {

				for (OrganizationAddressLink organizationAddressLink1 : organizationAddressLink) {
					AddressDetails addressDetails1 = addressRepository
							.getAddressDetailsByAddressId(organizationAddressLink1.getAddress_id());

					//AddressMapper addressMapper = new AddressMapper();
					if (null != addressDetails1) {
						
						Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(addressDetails1.getCountry(),organizationAddressLink1.getOrganization_id());
						
						
						monsterPublishMapper.setPostalCode(addressDetails1.getPostalCode());
						monsterPublishMapper.setCity(addressDetails1.getCity());
						//monsterPublishMapper.setCountry(addressDetails.getCountry());
						monsterPublishMapper.setCountryCode(country.getCountry_alpha3_code());
						monsterPublishMapper.setState(addressDetails1.getState());
						//monsterPublishMapper.setAddress1(addressDetails.getAddressLine1());
						//monsterPublishMapper.setAddress2(addressDetails.getAddressLine2());
						monsterPublishMapper.setStreet(addressDetails1.getStreet());
						//addressList1.add(addressMapper);
					}
				}

				//System.out.println("addressList.......... " + addressList1);
			}
			//monsterPublishMapper.setPhysicalAddress(addressList1);
			
			
		}
		
	/*	String url="https://develop.tekorero.com/JobBoard/monster/callMonsterApi";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		//set your entity to send
		HttpEntity entity = new HttpEntity(monsterPublishMapper,headers);
		System.out.println("the response is "+entity.toString());
		// send it!
		ResponseEntity<String> out = restTemplate.exchange(url, HttpMethod.POST, entity
		    , String.class);
		
		System.out.println("the response is "+out); */
	/*	System.out.println("opportunityRecruitDetails.getJob_order()="+opportunityRecruitDetails.getJob_order()+"||"+opportunityRecruitDetails.getRecruitment_id());
		String serverUrl ="https://develop.tekorero.com/JobBoard/monster/callMonsterApi";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	    MultiValueMap<String, Object> body= new LinkedMultiValueMap<>();
	   // Map<String, String> body = new HashMap<>();
	    body.add("userName", monsterCredentials.getUserName());
	    body.add("password", monsterCredentials.getPassword());
	    body.add("jobRefCode","ho425");
	    body.add("jobTitle", "Backend");
	    body.add("companyName", organizationDetails.getName());
	    body.add("email", employeeDetails.getEmailId());
	    body.add("managerName", employeeDetails.getFullName());
	    body.add("street", "1 Main Street");
	    body.add("city", "Maynard");
	    body.add("state", "MA");
	    body.add("countryCode", "US");
	    body.add("postalCode", "01754");
	    body.add("locationCity", "Boston");
	    body.add("locationState", "MA");
	    body.add("locationCountryCode", "US");
	    body.add("locationPostalCode", "02125");
	    body.add("description", "java,mySql,html,css,python,mongodb,nosql,good in spring and spring boot");
	    HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);
	    
	    System.out.println("the response is "+body.toString());
	    
	   RestTemplate restTemplate = new RestTemplate();
	   ResponseEntity<String> response = restTemplate
	     .postForEntity(serverUrl, requestEntity, String.class);*/
		
		String url = "https://develop.tekorero.com/JobBoard/monster/callMonsterApi";
		MonsterPublishMapper mapper = new MonsterPublishMapper();
        mapper.setUserName("xrtpjobsx01");
        mapper.setPassword("rtp987654");
        mapper.setEmail("Albert@tekorero.com");
        mapper.setCompanyName("innoverenIT SB");
        mapper.setManagerName("Peter King");
        mapper.setJobRefCode("job65436");
        mapper.setJobTitle("fullstac");
        mapper.setDescription("need Full stack developer for upcoming project");

        mapper.setStreet("1 Main Street");
        mapper.setCity("Maynard");
        mapper.setState("MA");
        mapper.setCountryCode("US");
        mapper.setPostalCode("01754");

        mapper.setLocationCity("Boston");
        mapper.setLocationState("MA");
        mapper.setLocationCountryCode("US");
        mapper.setLocationPostalCode("02125");

    RestTemplate restTemplate = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = null;
        try {
            entity = new HttpEntity<String>(new ObjectMapper().writeValueAsString(mapper), headers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        System.out.println("response..."+response);
		return monsterPublishId;
		
		
	}

	@Override
	public List<MonsterPublishMapper> getMonsterDetailsById(String recruitmentId) {
		
		List<MonsterPublish> list = monsterPublishRepository.getMonsterDetailsById(recruitmentId);
		List<MonsterPublishMapper> mapperList = new ArrayList<>();
		if(null !=list && !list.isEmpty()) {
			
           list.stream().map(li->{
        	   MonsterPublishMapper monsterPublishMapper = new MonsterPublishMapper();
				
        	   monsterPublishMapper.setJobDuration(li.getJobDuration());
        	   monsterPublishMapper.setJobCategory(li.getJobCategory());
        	   monsterPublishMapper.setJobOccupation(li.getJobOccupation());
        	   monsterPublishMapper.setJobBoardName(li.getJobBoardName());
        	   monsterPublishMapper.setIndustry(li.getIndustry());
				mapperList.add(monsterPublishMapper);
				
				return mapperList;
			}).collect(Collectors.toList());
		}
		
		
		
		return mapperList;
		
	}

	@Override
	public List<MonsterPublish> getMonsterPublishList() {
		List<MonsterPublish> list = monsterPublishRepository.findAll();
			return list;
		}
}