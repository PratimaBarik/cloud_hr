package com.app.employeePortal.category.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.category.entity.WorkflowCategory;
import com.app.employeePortal.category.mapper.WorkflowCategoryMapper;
import com.app.employeePortal.category.repository.WorkflowCategoryRepo;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class WorkflowCategoryServiceImpl implements WorkflowCategoryService {

	@Autowired
	WorkflowCategoryRepo workflowCategoryRepo;
	@Autowired
	EmployeeService employeeService;

	@Override
	public WorkflowCategoryMapper saveWorkflowCategory(WorkflowCategoryMapper mapper) {
		String workflowCategoryId = null;
		if (mapper != null) {
			WorkflowCategory workflowCategory = new WorkflowCategory();
			workflowCategory.setCreationDate(new Date());
			workflowCategory.setLiveInd(true);
			workflowCategory.setName(mapper.getName());
			workflowCategory.setOrgId(mapper.getOrgId());
			workflowCategory.setUpdatedBy(mapper.getUserId());
			workflowCategory.setUpdationDate(new Date());
			workflowCategory.setUserId(mapper.getUserId());
			workflowCategoryId = workflowCategoryRepo.save(workflowCategory).getWorkflowCategoryId();

		}
		WorkflowCategoryMapper resultMapper = getWorkflowCategoryByWorkflowCategoryId(workflowCategoryId);
		return resultMapper;
	}

	public WorkflowCategoryMapper getWorkflowCategoryByWorkflowCategoryId(String workflowCategoryId) {

		WorkflowCategory workflowCategory = workflowCategoryRepo.findByWorkflowCategoryIdAndLiveInd(workflowCategoryId,
				true);
		WorkflowCategoryMapper workflowCategoryMapper = new WorkflowCategoryMapper();

		if (null != workflowCategory) {

			workflowCategoryMapper.setCreationDate(Utility.getISOFromDate(workflowCategory.getCreationDate()));
			workflowCategoryMapper.setLiveInd(true);
			workflowCategoryMapper.setName(workflowCategory.getName());
			workflowCategoryMapper.setOrgId(workflowCategory.getOrgId());
			workflowCategoryMapper.setUpdatedBy(employeeService.getEmployeeFullName(workflowCategory.getUserId()));
			workflowCategoryMapper.setUpdationDate(Utility.getISOFromDate(workflowCategory.getUpdationDate()));
			workflowCategoryMapper.setUserId(workflowCategory.getUserId());
			workflowCategoryMapper.setWorkflowCategoryId(workflowCategory.getWorkflowCategoryId());
		}

		return workflowCategoryMapper;
	}

	@Override
	public List<WorkflowCategoryMapper> getWorkflowCategoryByOrgId(String orgId) {

		List<WorkflowCategoryMapper> resultMapper = new ArrayList<>();
		List<WorkflowCategory> list = workflowCategoryRepo.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getWorkflowCategoryByWorkflowCategoryId(li.getWorkflowCategoryId()))
					.collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<WorkflowCategory> list1 = workflowCategoryRepo.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public WorkflowCategoryMapper updateWorkflowCategory(String workflowCategoryId, WorkflowCategoryMapper mapper) {

		WorkflowCategory workflowCategory = workflowCategoryRepo.findByWorkflowCategoryIdAndLiveInd(workflowCategoryId,
				true);
		if (null != workflowCategory) {

			workflowCategory.setLiveInd(true);
			workflowCategory.setName(mapper.getName());
			workflowCategory.setOrgId(mapper.getOrgId());
			workflowCategory.setUpdatedBy(mapper.getUserId());
			workflowCategory.setUpdationDate(new Date());
			workflowCategory.setUserId(mapper.getUserId());
			workflowCategoryRepo.save(workflowCategory);
		}
		WorkflowCategoryMapper resultMapper = getWorkflowCategoryByWorkflowCategoryId(workflowCategoryId);
		return resultMapper;
	}

	@Override
	public void deleteWorkflowCategory(String workflowCategoryId, String userId) {

		if (null != workflowCategoryId) {
			WorkflowCategory workflowCategory = workflowCategoryRepo
					.findByWorkflowCategoryIdAndLiveInd(workflowCategoryId, true);

			workflowCategory.setUpdationDate(new Date());
			workflowCategory.setUpdatedBy(userId);
			workflowCategory.setLiveInd(false);
			workflowCategoryRepo.save(workflowCategory);
		}
	}

	@Override
	public List<WorkflowCategoryMapper> getWorkflowCategoryByName(String name, String orgId) {
		List<WorkflowCategory> list = workflowCategoryRepo.findByNameContainingAndLiveIndAndOrgId(name, true, orgId);
		List<WorkflowCategoryMapper> resultList = new ArrayList<WorkflowCategoryMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(Quality -> {
				WorkflowCategoryMapper mapper = getWorkflowCategoryByWorkflowCategoryId(
						Quality.getWorkflowCategoryId());
				if (null != mapper) {
					resultList.add(mapper);
				}
				return resultList;
			}).collect(Collectors.toList());
		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<WorkflowCategory> list1 = workflowCategoryRepo.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultList.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultList.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultList;
	}

	@Override
	public HashMap getWorkflowCategoryCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<WorkflowCategory> list = workflowCategoryRepo.findByOrgIdAndLiveInd(orgId, true);
		map.put("workflowCategoryCount", list.size());
		return map;
	}

	@Override
	public boolean checkNameInWorkflowCategory(String name, String orgId) {
		List<WorkflowCategory> workflowCategory = workflowCategoryRepo.findByNameAndLiveIndAndOrgId(name, true, orgId);
		if (workflowCategory.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkNameInWorkflowCategoryInUpdate(String name, String orgId, String workflowCategoryId) {
		List<WorkflowCategory> workflowCategory = workflowCategoryRepo
				.findByNameContainingAndWorkflowCategoryIdNotAndLiveIndAndOrgId(name, workflowCategoryId, true, orgId);
		if (workflowCategory.size() > 0) {
			return true;
		}
		return false;
	}
}