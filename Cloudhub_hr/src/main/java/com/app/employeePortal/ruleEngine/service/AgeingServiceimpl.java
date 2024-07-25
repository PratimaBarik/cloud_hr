package com.app.employeePortal.ruleEngine.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.ruleEngine.entity.OrganizationColdLeadsAgingRule;
import com.app.employeePortal.ruleEngine.entity.OrganizationHotAgingRule;
import com.app.employeePortal.ruleEngine.entity.OrganizationOtherLeadsAgingRule;
import com.app.employeePortal.ruleEngine.entity.OrganizationWarmLeadsAgingRule;
import com.app.employeePortal.ruleEngine.mapper.OrganizationLeadsAgingRuleMapper;
import com.app.employeePortal.ruleEngine.repository.OrganizationColdAgingRepository;
import com.app.employeePortal.ruleEngine.repository.OrganizationHotAgingRepository;
import com.app.employeePortal.ruleEngine.repository.OrganizationOtherAgingRepository;
import com.app.employeePortal.ruleEngine.repository.OrganizationWarmAgingRepository;

@Service
@Transactional
public class AgeingServiceimpl implements AgeingService {
	
	@Autowired
	OrganizationColdAgingRepository organizationColdAgingRepository;
	
	@Autowired
	OrganizationHotAgingRepository organizationHotAgingRepository;
	
	@Autowired
	OrganizationWarmAgingRepository organizationWarmAgingRepository;
	
	@Autowired
	OrganizationOtherAgingRepository organizationOtherAgingRepository;

	@Override
	public String saveOrganizationColdLeadsAgingRule(
			OrganizationLeadsAgingRuleMapper organizationLeadsAgingRuleMapper) {
		if(null!=organizationLeadsAgingRuleMapper) {
			OrganizationColdLeadsAgingRule organizationColdLeadsAgingRule = new OrganizationColdLeadsAgingRule();
			
			organizationColdLeadsAgingRule.setLeads_category(organizationLeadsAgingRuleMapper.getColdLeads().getCategory());
			organizationColdLeadsAgingRule.setDays(organizationLeadsAgingRuleMapper.getColdLeads().getDays());
			organizationColdLeadsAgingRule.setCreation_date(new Date());
			organizationColdAgingRepository.save(organizationColdLeadsAgingRule);
			
			
		}
		return null;
	}

	@Override
	public String saveOrganizationHotLeadsAgingRule(OrganizationLeadsAgingRuleMapper organizationLeadsAgingRuleMapper) {
		if(null!=organizationLeadsAgingRuleMapper) {
			OrganizationHotAgingRule organizationHotLeadsAgingRule = new OrganizationHotAgingRule();
			organizationHotLeadsAgingRule.setLeads_category(organizationLeadsAgingRuleMapper.getHotLeads().getCategory());
			organizationHotLeadsAgingRule.setDays(organizationLeadsAgingRuleMapper.getHotLeads().getDays());
			organizationHotLeadsAgingRule.setCreation_date(new Date());
			organizationHotLeadsAgingRule.setLive_ind(false);
			
			organizationHotAgingRepository.save(organizationHotLeadsAgingRule);
			
		}
		return null;
	}

	@Override
	public String saveOrganizationWarmLeadsAgingRule(
			OrganizationLeadsAgingRuleMapper organizationLeadsAgingRuleMapper) {
		if(null!=organizationLeadsAgingRuleMapper) {
			
			OrganizationWarmLeadsAgingRule organizationWarmLeadsAgingRule = new OrganizationWarmLeadsAgingRule();
			
			organizationWarmLeadsAgingRule.setLeads_category(organizationLeadsAgingRuleMapper.getWarmLeads().getCategory());
			organizationWarmLeadsAgingRule.setCreation_date(new Date());
			organizationWarmLeadsAgingRule.setLive_ind(false);
			
			organizationWarmAgingRepository.save(organizationWarmLeadsAgingRule);
			
		}
		
		return null;
	}

	@Override
	public String saveOrganizationOtherLeadsAgingRule(
			OrganizationLeadsAgingRuleMapper organizationLeadsAgingRuleMapper) {
		
		if(null!=organizationLeadsAgingRuleMapper) {
			
			OrganizationOtherLeadsAgingRule organizationOtherLeadsAgingRule = new OrganizationOtherLeadsAgingRule();
			organizationOtherLeadsAgingRule.setLeads_category(organizationLeadsAgingRuleMapper.getOtherLeads().getCategory());
			organizationOtherLeadsAgingRule.setCreation_date(new Date());
			organizationOtherLeadsAgingRule.setLive_ind(false);
			
			organizationOtherAgingRepository.save(organizationOtherLeadsAgingRule); 
			
		}
		
		return null;
	}

	@Override
	public OrganizationLeadsAgingRuleMapper saveLeadsAgingRule(
			OrganizationLeadsAgingRuleMapper organizationLeadsAgingRuleMapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrganizationLeadsAgingRuleMapper getLeadsAgingRule(String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

}
