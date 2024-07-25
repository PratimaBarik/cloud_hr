package com.app.employeePortal.commercial.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.commercial.entity.PartnerCommission;
import com.app.employeePortal.commercial.mapper.PartnerCommissionMapper;
import com.app.employeePortal.commercial.repository.PartnerCommissionRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.partner.repository.PartnerDetailsRepository;
import com.app.employeePortal.util.Utility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional

public class PartnerCommissionServiceImpl implements PartnerCommissionService {
	@Autowired
	PartnerCommissionRepository partnerCommissionRepository;
	
	@Autowired
	
	PartnerDetailsRepository partnerDetailsRepository;
	@Autowired
	
	EmployeeRepository employeeRepository;
	@Override
    
	public String savePartnerCommission(PartnerCommissionMapper partnerCommissionMapper) {
		String Id = null;
		PartnerCommission dbPartnerCommission = partnerCommissionRepository.findByPartnerId(partnerCommissionMapper.getPartnerId());
        if (null != dbPartnerCommission) {
        	dbPartnerCommission.setRequirementType(partnerCommissionMapper.getRequirementType());
        	dbPartnerCommission.setCommissionDeal(partnerCommissionMapper.getCommissionDeal());
        	dbPartnerCommission.setPaymentDate(partnerCommissionMapper.getPaymentDate());
        	dbPartnerCommission.setCommissionAmount(partnerCommissionMapper.getCommissionAmount());
        	dbPartnerCommission.setCurrency(partnerCommissionMapper.getCurrency());
        	dbPartnerCommission.setLastUpdatedOn(new Date());
        	dbPartnerCommission.setPartnerId(partnerCommissionMapper.getPartnerId());
        	dbPartnerCommission.setOrgId(partnerCommissionMapper.getOrgId());
        	dbPartnerCommission.setUserId(partnerCommissionMapper.getUserId());
        	Id = partnerCommissionRepository.save(dbPartnerCommission).getPartnerCommissionId();
           
        }else {
        PartnerCommission newPartnerCommission = new PartnerCommission();
        newPartnerCommission.setPartnerId(partnerCommissionMapper.getPartnerId());
        newPartnerCommission.setOrgId(partnerCommissionMapper.getOrgId());
        newPartnerCommission.setRequirementType(partnerCommissionMapper.getRequirementType());
        newPartnerCommission.setCommissionDeal(partnerCommissionMapper.getCommissionDeal());
        newPartnerCommission.setPaymentDate(partnerCommissionMapper.getPaymentDate());
        newPartnerCommission.setCommissionAmount(partnerCommissionMapper.getCommissionAmount());
        newPartnerCommission.setCurrency(partnerCommissionMapper.getCurrency());
        newPartnerCommission.setLastUpdatedOn(new Date());
        newPartnerCommission.setUserId(partnerCommissionMapper.getUserId());    
        Id = partnerCommissionRepository.save(newPartnerCommission).getPartnerCommissionId();
        }
       
        return Id;
    }

	@Override
	public List<PartnerCommissionMapper> getPartnerCommissionListByPartnerId(String partnerId) {
			List<PartnerCommission> partnerCommissionList = partnerCommissionRepository.getByPartnerId(partnerId);
			List<PartnerCommissionMapper> mapperList = new ArrayList<>();
			if (null != partnerCommissionList && !partnerCommissionList.isEmpty()) {

				partnerCommissionList.stream().map(li -> {

					PartnerCommissionMapper partnerCommissionMapper = new PartnerCommissionMapper();

					partnerCommissionMapper.setPartnerCommissionId(li.getPartnerCommissionId());
					partnerCommissionMapper.setPartnerId(li.getPartnerId());
					partnerCommissionMapper.setOrgId(li.getOrgId());
					partnerCommissionMapper.setRequirementType(li.getRequirementType());
					partnerCommissionMapper.setCommissionDeal(li.getCommissionDeal());
					partnerCommissionMapper.setPaymentDate(li.getPaymentDate());
					partnerCommissionMapper.setCommissionAmount(li.getCommissionAmount());
					partnerCommissionMapper.setCurrency(li.getCurrency());
					partnerCommissionMapper.setLastUpdatedOn(Utility.getISOFromDate(li.getLastUpdatedOn()));
					partnerCommissionMapper.setUserId(li.getUserId());
					
					EmployeeDetails employeeDetails = employeeRepository
			                .getEmployeeDetailsByEmployeeId(li.getUserId());
			        if (null != employeeDetails) {
			            String middleName = " ";
			            String lastName = "";

			            if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

			                lastName = employeeDetails.getLastName();
			            }

			            if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

			                middleName = employeeDetails.getMiddleName();
			                partnerCommissionMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
			            } else {

			            	partnerCommissionMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
			            }
			        }
					mapperList.add(partnerCommissionMapper);

					return mapperList;
				}).collect(Collectors.toList());
			}

			return mapperList;
		}
}

	
	


