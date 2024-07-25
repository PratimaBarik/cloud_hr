package com.app.employeePortal.features.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.email.service.EmailService;
import com.app.employeePortal.features.entity.AdvanceFeatureDetails;
import com.app.employeePortal.features.entity.JobPublishDetails;
import com.app.employeePortal.features.mapper.FeaturesMapper;
import com.app.employeePortal.features.repository.AdvanceFeatureRepository;
import com.app.employeePortal.features.repository.JobPublishRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class FeaturesServiceImpl implements FeaturesService {

	@Autowired
	AdvanceFeatureRepository advanceFeatureRepository;
	@Autowired EmailService emailService;
	@Autowired JobPublishRepository jobPublishRepository;
	
	@Override
	public String saveAdvanceFeature(FeaturesMapper featuresMapper) {
		String id= null;
		if(null !=featuresMapper) {
		AdvanceFeatureDetails dbAdvanceFeatureDetails = new AdvanceFeatureDetails();
		dbAdvanceFeatureDetails.setOrgId(featuresMapper.getOrganizationId());
		dbAdvanceFeatureDetails.setUserId(featuresMapper.getUserId());
		dbAdvanceFeatureDetails.setType(featuresMapper.getType());
		dbAdvanceFeatureDetails.setCreationDate(new Date());
		//dbAdvanceFeatureDetails.setInactiveDate(new);
		dbAdvanceFeatureDetails.setLiveInd(true);
		 id = advanceFeatureRepository.save(dbAdvanceFeatureDetails).getId();
		 
		 if(id!=null) {
			 
			 emailService.sendMailToOurOrganizationDuringFeatureAdded(featuresMapper.getOrganizationId(),"send mail during feature added"); 
			 
		 }
		 
		 
		}
		return id;
	}

	@Override
	public List<FeaturesMapper> getAdavnceFeatureByOrgId(String orgId) {
		List<AdvanceFeatureDetails> featureList = advanceFeatureRepository.getAdavnceFeatureByOrgId(orgId);
		List<FeaturesMapper> mapperList = new ArrayList<>();
		if(null !=featureList && !featureList.isEmpty()) {
			
			mapperList = featureList.stream().map(li->getAdvanceFeatureById(li.getId())).collect(Collectors.toList());
				
				
		}
		
		
		return mapperList;
	}

	@Override
	public FeaturesMapper updateAdavnceFeature(FeaturesMapper featuresMapper) throws Exception {
		
		
		AdvanceFeatureDetails advanceFeatureDetails = advanceFeatureRepository.getAdavnceFeatureById(featuresMapper.getAdvanceFeatureId());
		if (!StringUtils.isEmpty(featuresMapper.getType())) {
			advanceFeatureDetails.setType(featuresMapper.getType());
		}if (!StringUtils.isEmpty(featuresMapper.getInactiveDate())) {
			advanceFeatureDetails.setInactiveDate(Utility.getDateFromISOString(featuresMapper.getInactiveDate()));
		}
		
		advanceFeatureRepository.save(advanceFeatureDetails);
		
		if (!StringUtils.isEmpty(featuresMapper.getInactiveDate())) {
			
			 emailService.sendMailToOurOrganizationDuringFeatureAdded(featuresMapper.getOrganizationId(),"send mail during feature removed"); 

		}
		
		return getAdvanceFeatureById(featuresMapper.getAdvanceFeatureId());
		
		
		
		
	}
	
	public FeaturesMapper getAdvanceFeatureById(String id) {
		AdvanceFeatureDetails advanceFeatureDetails = advanceFeatureRepository.getAdavnceFeatureById(id);
		FeaturesMapper featuresMapper =  new FeaturesMapper();

		if (null != featuresMapper) {
			featuresMapper.setUserId(advanceFeatureDetails.getUserId());
			featuresMapper.setOrganizationId(advanceFeatureDetails.getOrgId());
			featuresMapper.setType(advanceFeatureDetails.getType());
			featuresMapper.setCreationDate(Utility.getISOFromDate(advanceFeatureDetails.getCreationDate()));
			if(null !=advanceFeatureDetails.getInactiveDate()) {
			featuresMapper.setInactiveDate(Utility.getISOFromDate(advanceFeatureDetails.getInactiveDate()));
			}
		}
		return featuresMapper;
	}

	@Override
	public String addJobPublish(FeaturesMapper featuresMapper) {
		
		JobPublishDetails jobPublishDetails = new JobPublishDetails();
		jobPublishDetails.setWebsite(featuresMapper.getWebsite());
		jobPublishDetails.setOrgId(featuresMapper.getOrganizationId());
		jobPublishDetails.setCreationDate(new Date());
		jobPublishDetails.setLiveInd(true);
		
		String id = jobPublishRepository.save(jobPublishDetails).getId();
		

		
		return id;
	}

	@Override
	public FeaturesMapper getJobpublishByOrgId(String orgId) {
		FeaturesMapper featuresMapper = new FeaturesMapper();
		JobPublishDetails jobPublishDetails = jobPublishRepository.getDetailsByOrgId(orgId);
		
		
		if(null !=jobPublishDetails) {
			featuresMapper.setWebsite(jobPublishDetails.getWebsite());
			featuresMapper.setOrganizationId(orgId);
			featuresMapper.setAdvanceFeatureId(jobPublishDetails.getId());
			
		}
		
		
		return featuresMapper;
	}

	
	
}
