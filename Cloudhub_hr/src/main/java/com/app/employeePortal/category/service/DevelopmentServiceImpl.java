package com.app.employeePortal.category.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.category.entity.Development;
import com.app.employeePortal.category.entity.LibraryType;
import com.app.employeePortal.category.entity.RoleType;
import com.app.employeePortal.category.mapper.DevelopmentMapper;
import com.app.employeePortal.category.mapper.RoleMapper;
import com.app.employeePortal.category.repository.DevelopmentRepository;
import com.app.employeePortal.category.repository.RoleTypeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.task.entity.TaskType;
import com.app.employeePortal.task.repository.TaskTypeRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class DevelopmentServiceImpl implements DevelopmentService {

	@Autowired
	DevelopmentRepository developmentRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	RoleTypeRepository roleTypeRepository;
	@Autowired
	TaskTypeRepository taskTypeRepository;

	@Override
	public DevelopmentMapper createDevelopment(DevelopmentMapper mapper) {
		String Id = null;
		Development dbDevelopment = new Development();
		dbDevelopment.setCreationDate(new Date());
		dbDevelopment.setDepartmentId(mapper.getDepartment());
		dbDevelopment.setLiveInd(true);
		dbDevelopment.setOrgId(mapper.getOrgId());
		dbDevelopment.setRoletypeId(mapper.getRoletype());
		dbDevelopment.setTaskTypeId(mapper.getTaskType());
		dbDevelopment.setUpdatedBy(mapper.getUserId());
		dbDevelopment.setUpdationDate(new Date());
		dbDevelopment.setUserId(mapper.getUserId());
		dbDevelopment.setValue(mapper.getValue());
		dbDevelopment.setDevelopmentType(mapper.getDevelopmentType());

		Id = developmentRepository.save(dbDevelopment).getDevelopmentId();

		DevelopmentMapper resultMapper = getDevelopmentByDevelopmentId(Id);
		return resultMapper;
	}

	public DevelopmentMapper getDevelopmentByDevelopmentId(String developmentId) {

		Development development = developmentRepository.findByDevelopmentIdAndLiveInd(developmentId, true);
		DevelopmentMapper mapper = new DevelopmentMapper();

		if (null != development) {
			mapper.setCreationDate(Utility.getISOFromDate(development.getCreationDate()));
			mapper.setDevelopmentId(developmentId);
			mapper.setLiveInd(development.isLiveInd());
			mapper.setOrgId(development.getOrgId());
			mapper.setUpdatedBy(employeeService.getEmployeeFullName(development.getUserId()));
			mapper.setUpdationDate(Utility.getISOFromDate(development.getUpdationDate()));
			mapper.setUserId(development.getUserId());
			mapper.setValue(development.getValue());
			mapper.setDevelopmentType(development.getDevelopmentType());
			if (!StringUtils.isEmpty(development.getDepartmentId())) {
				Department department = departmentRepository.getDepartmentDetails(development.getDepartmentId());
				if (null != department) {
					mapper.setDepartmentId(department.getDepartment_id());
					mapper.setDepartment(department.getDepartmentName());
				}
			}
			if (!StringUtils.isEmpty(development.getRoletypeId())) {
				RoleType roleType = roleTypeRepository.findByRoleTypeId(development.getRoletypeId());
				if (null != roleType) {
					mapper.setRoletypeId(roleType.getRoleTypeId());
					mapper.setRoletype(roleType.getRoleType());
				}
			}
			if (!StringUtils.isEmpty(development.getTaskTypeId())) {
				TaskType taskType = taskTypeRepository.findByTaskTypeId(development.getTaskTypeId());
				if (null != taskType) {
					mapper.setTaskTypeId(taskType.getTaskTypeId());
					mapper.setTaskType(taskType.getTaskType());
				}
			}

		}

		return mapper;
	}

	@Override
	public List<DevelopmentMapper> getDevelopmentByOrgId(String orgId) {

		List<DevelopmentMapper> resultMapper = new ArrayList<>();
		List<Development> list = developmentRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getDevelopmentByDevelopmentId(li.getDevelopmentId()))
					.collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<Development> list1 = developmentRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public void deleteDevelopment(String developmentId, String userId) {
		if (null != developmentId) {
			Development development = developmentRepository.findByDevelopmentIdAndLiveInd(developmentId, true);

			development.setUpdationDate(new Date());
			development.setUpdatedBy(userId);
			development.setLiveInd(false);
			developmentRepository.save(development);
		}
	}

	@Override
	public HashMap getDevelopmentCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<Development> list = developmentRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("DevelopmentCount", list.size());
		return map;
	}

	@Override
	public DevelopmentMapper updateDevelopment(String developmentId, DevelopmentMapper mapper) {
		Development development = developmentRepository.findByDevelopmentIdAndLiveInd(developmentId, true);
		if (development != null) {

			development.setDepartmentId(mapper.getDepartment());
			development.setRoletypeId(mapper.getRoletype());
			development.setTaskTypeId(mapper.getTaskType());
			development.setValue(mapper.getValue());
			development.setDevelopmentType(mapper.getDevelopmentType());
			development.setLiveInd(true);
			development.setOrgId(mapper.getOrgId());
			development.setUpdatedBy(mapper.getUserId());
			development.setUpdationDate(new Date());
			development.setUserId(mapper.getUserId());
			developmentRepository.save(development);
		}
		DevelopmentMapper resultMapper = getDevelopmentByDevelopmentId(developmentId);
		return resultMapper;
	}

	@Override
	public List<DevelopmentMapper> getDevelopmentByTaskTypeIdAndValue(String taskTypeId, String value, String orgId) {
		List<Development> list = developmentRepository.findByTaskTypeIdAndValueAndOrgIdAndLiveInd(taskTypeId, value,
				orgId, true);
		List<DevelopmentMapper> resultList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(dev -> {
				DevelopmentMapper mapper = getDevelopmentByDevelopmentId(dev.getDevelopmentId());
				if (null != mapper) {
					resultList.add(mapper);

				}
				return resultList;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

}