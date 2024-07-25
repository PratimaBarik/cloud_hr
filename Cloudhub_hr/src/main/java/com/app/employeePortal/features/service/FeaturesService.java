package com.app.employeePortal.features.service;

import java.util.List;

import com.app.employeePortal.features.mapper.FeaturesMapper;

public interface FeaturesService {

	String saveAdvanceFeature(FeaturesMapper featuresMapper);

	List<FeaturesMapper> getAdavnceFeatureByOrgId(String orgId);

	FeaturesMapper updateAdavnceFeature(FeaturesMapper featuresMapper)throws Exception;
	
	public String addJobPublish(FeaturesMapper featuresMapper);
	
	public FeaturesMapper getJobpublishByOrgId(String orgId);
	
	

}
