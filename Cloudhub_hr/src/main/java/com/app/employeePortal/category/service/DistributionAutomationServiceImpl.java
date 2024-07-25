package com.app.employeePortal.category.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.category.entity.DistributionAutomation;
import com.app.employeePortal.category.entity.DistributionAutomationAssignedTo;
import com.app.employeePortal.category.mapper.DistributionAutomationMapper;
import com.app.employeePortal.category.repository.DistributionAutomationAssignedToRepository;
import com.app.employeePortal.category.repository.DistributionAutomationRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class DistributionAutomationServiceImpl implements DistributionAutomationService {
	
	@Autowired
	DistributionAutomationRepository distributionAutomationRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	DistributionAutomationAssignedToRepository distributionAutomationAssignedToRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	
	@Override
	public DistributionAutomationMapper saveDistributionAutomation(DistributionAutomationMapper distributionAutomationMapper) {
		DistributionAutomationMapper mapper = new DistributionAutomationMapper();
		
		DistributionAutomation distributionAutomation = distributionAutomationRepository.getByOrgIdAndType
				(distributionAutomationMapper.getOrgId(),distributionAutomationMapper.getType());
		String id1 = null;
		if(null!=distributionAutomation) {
			distributionAutomation.setSingleMultiInd(distributionAutomationMapper.isSingleMultiInd());
			distributionAutomation.setCreationDate(new Date());
			distributionAutomation.setLiveInd(true);
			distributionAutomation.setOrgId(distributionAutomationMapper.getOrgId());
			distributionAutomation.setType(distributionAutomationMapper.getType());
			distributionAutomation.setUpdatedBy(distributionAutomationMapper.getUserId());
			distributionAutomation.setUpdationDate(new Date());
			distributionAutomation.setUserId(distributionAutomationMapper.getUserId());
			distributionAutomation.setDepartmentId(distributionAutomationMapper.getDepartmentId());

			 id1 = distributionAutomationRepository.save(distributionAutomation).getDistributionAutomationId();
			
			if(distributionAutomationMapper.isSingleMultiInd()) {
				if(null!=distributionAutomationMapper.getMultyAsignedTOId() && !distributionAutomationMapper.getMultyAsignedTOId().isEmpty()) {
					if(distributionAutomationMapper.getMultyAsignedTOId().size() > 1) {
						
						List<DistributionAutomationAssignedTo> list = distributionAutomationAssignedToRepository.
								getByDistributionAutomationIdAndLiveInd(distributionAutomation.getDistributionAutomationId(),true);
						if(null!=list && !list.isEmpty()) {
							for(DistributionAutomationAssignedTo distributionAutomationAssignedTo1 : list) {
								distributionAutomationAssignedTo1.setLiveInd(false);
								distributionAutomationAssignedToRepository.save(distributionAutomationAssignedTo1);
							}
						}
						
						for(String id : distributionAutomationMapper.getMultyAsignedTOId()) {
							DistributionAutomationAssignedTo distributionAutomationAssignedTo = new DistributionAutomationAssignedTo();
							distributionAutomationAssignedTo.setAsignedTO(id);
							distributionAutomationAssignedTo.setCreationDate(new Date());
							distributionAutomationAssignedTo.setDistributionAutomationId(id1);
							distributionAutomationAssignedTo.setLiveInd(true);
							distributionAutomationAssignedTo.setOrgId(distributionAutomationMapper.getOrgId());
							distributionAutomationAssignedTo.setUserId(distributionAutomationMapper.getUserId());
							distributionAutomationAssignedToRepository.save(distributionAutomationAssignedTo);
						}
					}
				}
			}else {
				if(null!=distributionAutomationMapper.getMultyAsignedTOId() && !distributionAutomationMapper.getMultyAsignedTOId().isEmpty()) {
					if(distributionAutomationMapper.getMultyAsignedTOId().size() > 0 && distributionAutomationMapper.getMultyAsignedTOId().size() < 2 ) {
						//distributionAutomation.setAsignedTO(distributionAutomationMapper.getMultyAsignedTO().get(0));
						
						List<DistributionAutomationAssignedTo> list = distributionAutomationAssignedToRepository.
								getByDistributionAutomationIdAndLiveInd(distributionAutomation.getDistributionAutomationId(),true);
						if(null!=list && !list.isEmpty()) {
							for(DistributionAutomationAssignedTo distributionAutomationAssignedTo1 : list) {
								distributionAutomationAssignedTo1.setLiveInd(false);
								distributionAutomationAssignedToRepository.save(distributionAutomationAssignedTo1);
							}
						}
						
						
						DistributionAutomationAssignedTo distributionAutomationAssignedTo = new DistributionAutomationAssignedTo();
						distributionAutomationAssignedTo.setAsignedTO(distributionAutomationMapper.getMultyAsignedTOId().get(0));
						distributionAutomationAssignedTo.setCreationDate(new Date());
						distributionAutomationAssignedTo.setDistributionAutomationId(id1);
						distributionAutomationAssignedTo.setLiveInd(true);
						distributionAutomationAssignedTo.setOrgId(distributionAutomationMapper.getOrgId());
						distributionAutomationAssignedTo.setUserId(distributionAutomationMapper.getUserId());
						distributionAutomationAssignedToRepository.save(distributionAutomationAssignedTo);
					}
				}
			}
			
		}else {
			DistributionAutomation distributionAutomationnew = new DistributionAutomation();
			distributionAutomationnew.setSingleMultiInd(distributionAutomationMapper.isSingleMultiInd());
			//distributionAutomationnew.setAsignedTO(distributionAutomationMapper.getAsignedTO());
			distributionAutomationnew.setCreationDate(new Date());
			distributionAutomationnew.setLiveInd(true);
			distributionAutomationnew.setOrgId(distributionAutomationMapper.getOrgId());
			distributionAutomationnew.setType(distributionAutomationMapper.getType());
			distributionAutomationnew.setUpdatedBy(distributionAutomationMapper.getUserId());
			distributionAutomationnew.setUpdationDate(new Date());
			distributionAutomationnew.setUserId(distributionAutomationMapper.getUserId());
			distributionAutomationnew.setDepartmentId(distributionAutomationMapper.getDepartmentId());
			
			 id1 = distributionAutomationRepository.save(distributionAutomationnew).getDistributionAutomationId();
			
			if(distributionAutomationMapper.isSingleMultiInd()) {
				if(null!=distributionAutomationMapper.getMultyAsignedTOId() && !distributionAutomationMapper.getMultyAsignedTOId().isEmpty()) {
					if(distributionAutomationMapper.getMultyAsignedTOId().size() > 1) {
						for(String id : distributionAutomationMapper.getMultyAsignedTOId()) {
							DistributionAutomationAssignedTo distributionAutomationAssignedTo = new DistributionAutomationAssignedTo();
							distributionAutomationAssignedTo.setAsignedTO(id);
							distributionAutomationAssignedTo.setCreationDate(new Date());
							distributionAutomationAssignedTo.setDistributionAutomationId(id1);
							distributionAutomationAssignedTo.setLiveInd(true);
							distributionAutomationAssignedTo.setOrgId(distributionAutomationMapper.getOrgId());
							distributionAutomationAssignedTo.setUserId(distributionAutomationMapper.getUserId());
							distributionAutomationAssignedToRepository.save(distributionAutomationAssignedTo);
						}
					}
				}
			}else {
				if(null!=distributionAutomationMapper.getMultyAsignedTOId() && !distributionAutomationMapper.getMultyAsignedTOId().isEmpty()) {
					if(distributionAutomationMapper.getMultyAsignedTOId().size() > 0 && distributionAutomationMapper.getMultyAsignedTOId().size() < 2 ) {
						//distributionAutomation.setAsignedTO(distributionAutomationMapper.getMultyAsignedTO().get(0));
						DistributionAutomationAssignedTo distributionAutomationAssignedTo = new DistributionAutomationAssignedTo();
						distributionAutomationAssignedTo.setAsignedTO(distributionAutomationMapper.getMultyAsignedTOId().get(0));
						distributionAutomationAssignedTo.setCreationDate(new Date());
						distributionAutomationAssignedTo.setDistributionAutomationId(id1);
						distributionAutomationAssignedTo.setLiveInd(true);
						distributionAutomationAssignedTo.setOrgId(distributionAutomationMapper.getOrgId());
						distributionAutomationAssignedTo.setUserId(distributionAutomationMapper.getUserId());
						distributionAutomationAssignedToRepository.save(distributionAutomationAssignedTo);
					}
				}
			}
			
		}
		mapper = getDistributionAutomationByDistributionAutomationId(id1);
		return mapper;
	}

	@Override
	public DistributionAutomationMapper getDistributionAutomationByDistributionAutomationId(
			String distributionAutomationId) {
		DistributionAutomationMapper mapper = new DistributionAutomationMapper();
		
		DistributionAutomation distributionAutomation = distributionAutomationRepository.getByDistributionAutomationId(distributionAutomationId);
		if(null!=distributionAutomation) {
			//mapper.setAsignedTO(employeeService.getEmployeeFullName(distributionAutomation.getAsignedTO()));
			mapper.setCreationDate(Utility.getISOFromDate(distributionAutomation.getCreationDate()));
			mapper.setLiveInd(distributionAutomation.isLiveInd());
			mapper.setOrgId(distributionAutomation.getOrgId());
			mapper.setType(distributionAutomation.getType());
			mapper.setUpdatedBy(employeeService.getEmployeeFullName(distributionAutomation.getUserId()));
			mapper.setUpdationDate(Utility.getISOFromDate(distributionAutomation.getUpdationDate()));
			mapper.setUserId(distributionAutomation.getUserId());
			//mapper.setAsignedTOId(distributionAutomation.getAsignedTO());
			mapper.setDistributionAutomationId(distributionAutomation.getDistributionAutomationId());
			mapper.setSingleMultiInd(distributionAutomation.isSingleMultiInd());
			
			if (!StringUtils.isEmpty(distributionAutomation.getDepartmentId())) {
				Department department = departmentRepository.getDepartmentDetails(distributionAutomation.getDepartmentId());
				if (null != department) {
					mapper.setDepartmentId(department.getDepartmentName());
				}
			}
			
			List<DistributionAutomationAssignedTo> list = distributionAutomationAssignedToRepository.
					getByDistributionAutomationIdAndLiveInd(distributionAutomation.getDistributionAutomationId(),true);
			if(null!=list && !list.isEmpty()) {
				List<String> ids = new ArrayList<>();
				List<String> names = new ArrayList<>();
				if(list.size()>1) {
 					for(DistributionAutomationAssignedTo distributionAutomationAssignedTo1 : list) {
 						ids.add(distributionAutomationAssignedTo1.getAsignedTO());
 						names.add(employeeService.getEmployeeFullName(distributionAutomationAssignedTo1.getAsignedTO()));
					}
 					mapper.setMultyAsignedTOId(ids);
 					mapper.setMultyAsignedTO(names);
				}else {
					mapper.setAsignedTOId(list.get(0).getAsignedTO());
					mapper.setAsignedTO(employeeService.getEmployeeFullName(list.get(0).getAsignedTO()));
					
					ids.add(list.get(0).getAsignedTO());
					names.add(employeeService.getEmployeeFullName(list.get(0).getAsignedTO()));
					mapper.setMultyAsignedTOId(ids);
 					mapper.setMultyAsignedTO(names);
					
				}
			}
			
		}
		
		
		return mapper;
	}

	@Override
	public DistributionAutomationMapper getDistributionAutomationByOrgIdAndType(String orgId, String type) {
		DistributionAutomationMapper mapper = new DistributionAutomationMapper();
		DistributionAutomation distributionAutomation = distributionAutomationRepository.getByOrgIdAndType(orgId,type);
		if(null!=distributionAutomation) {
			mapper = getDistributionAutomationByDistributionAutomationId(distributionAutomation.getDistributionAutomationId());
		}
		return mapper;
	}
	
	


	
	
}