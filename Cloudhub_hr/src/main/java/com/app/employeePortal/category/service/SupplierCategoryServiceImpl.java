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

import com.app.employeePortal.category.entity.SupplierCategory;
import com.app.employeePortal.category.mapper.SupplierCategoryMapper;
import com.app.employeePortal.category.repository.SupplierCategoryRepo;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class SupplierCategoryServiceImpl implements SupplierCategoryService {

	@Autowired
	SupplierCategoryRepo supplierCategoryRepo;
	@Autowired
	EmployeeService employeeService;
	private String[] headings = { "Name" };

	@Override
	public SupplierCategoryMapper saveSupplierCategory(SupplierCategoryMapper mapper) {
		String supplierCategoryId = null;
		if (mapper != null) {
			SupplierCategory supplierCategory = new SupplierCategory();
			supplierCategory.setCreationDate(new Date());
			supplierCategory.setLiveInd(true);
			supplierCategory.setName(mapper.getSupplierCatName());
			supplierCategory.setOrgId(mapper.getOrgId());
			supplierCategory.setUpdatedBy(mapper.getUserId());
			supplierCategory.setUpdationDate(new Date());
			supplierCategory.setUserId(mapper.getUserId());
			supplierCategoryId = supplierCategoryRepo.save(supplierCategory).getSupplierCategoryId();

		}
		SupplierCategoryMapper resultMapper = getSupplierCategoryBySupplierCategoryId(supplierCategoryId);
		return resultMapper;
	}

	public SupplierCategoryMapper getSupplierCategoryBySupplierCategoryId(String supplierCategoryId) {

		SupplierCategory supplierCategory = supplierCategoryRepo.findBySupplierCategoryIdAndLiveInd(supplierCategoryId,
				true);
		SupplierCategoryMapper supplierCategoryMapper = new SupplierCategoryMapper();

		if (null != supplierCategory) {

			supplierCategoryMapper.setCreationDate(Utility.getISOFromDate(supplierCategory.getCreationDate()));
			supplierCategoryMapper.setLiveInd(true);
			supplierCategoryMapper.setSupplierCatName(supplierCategory.getName());
			supplierCategoryMapper.setOrgId(supplierCategory.getOrgId());
			supplierCategoryMapper.setUpdatedBy(employeeService.getEmployeeFullName(supplierCategory.getUserId()));
			supplierCategoryMapper.setUpdationDate(Utility.getISOFromDate(supplierCategory.getUpdationDate()));
			supplierCategoryMapper.setUserId(supplierCategory.getUserId());
			supplierCategoryMapper.setSupplierCategoryId(supplierCategory.getSupplierCategoryId());
		}

		return supplierCategoryMapper;
	}

	@Override
	public List<SupplierCategoryMapper> getSupplierCategoryByOrgId(String orgId) {

		List<SupplierCategoryMapper> resultMapper = new ArrayList<>();
		List<SupplierCategory> list = supplierCategoryRepo.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getSupplierCategoryBySupplierCategoryId(li.getSupplierCategoryId()))
					.collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<SupplierCategory> list1 = supplierCategoryRepo.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public SupplierCategoryMapper updateSupplierCategory(String supplierCategoryId, SupplierCategoryMapper mapper) {

		SupplierCategory supplierCategory = supplierCategoryRepo.findBySupplierCategoryIdAndLiveInd(supplierCategoryId,
				true);
		if (null != supplierCategory) {

			supplierCategory.setLiveInd(true);
			supplierCategory.setName(mapper.getSupplierCatName());
			supplierCategory.setOrgId(mapper.getOrgId());
			supplierCategory.setUpdatedBy(mapper.getUserId());
			supplierCategory.setUpdationDate(new Date());
			supplierCategory.setUserId(mapper.getUserId());
			supplierCategoryRepo.save(supplierCategory);
		}
		SupplierCategoryMapper resultMapper = getSupplierCategoryBySupplierCategoryId(supplierCategoryId);
		return resultMapper;
	}

	@Override
	public void deleteSupplierCategory(String supplierCategoryId, String userId) {

		if (null != supplierCategoryId) {
			SupplierCategory supplierCategory = supplierCategoryRepo
					.findBySupplierCategoryIdAndLiveInd(supplierCategoryId, true);

			supplierCategory.setUpdationDate(new Date());
			supplierCategory.setUpdatedBy(userId);
			supplierCategory.setLiveInd(false);
			supplierCategoryRepo.save(supplierCategory);
		}
	}

	@Override
	public List<SupplierCategoryMapper> getSupplierCategoryByName(String name, String orgId) {
		List<SupplierCategory> list = supplierCategoryRepo.findByNameContainingAndLiveIndAndOrgId(name, true, orgId);
		List<SupplierCategoryMapper> resultList = new ArrayList<SupplierCategoryMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(Quality -> {
				SupplierCategoryMapper mapper = getSupplierCategoryBySupplierCategoryId(
						Quality.getSupplierCategoryId());
				if (null != mapper) {
					resultList.add(mapper);
				}
				return resultList;
			}).collect(Collectors.toList());
		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<SupplierCategory> list1 = supplierCategoryRepo.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultList.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultList.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultList;
	}

	@Override
	public HashMap getSupplierCategoryCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<SupplierCategory> list = supplierCategoryRepo.findByOrgIdAndLiveInd(orgId, true);
		map.put("supplierCategoryCount", list.size());
		return map;
	}

	@Override
	public boolean checkNameInSupplierCategory(String name, String orgId) {
		List<SupplierCategory> supplierCategory = supplierCategoryRepo.findByNameAndLiveIndAndOrgId(name, true, orgId);
		if (supplierCategory.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkNameInSupplierCategoryInUpdate(String name, String orgId, String supplierCategoryId) {
		List<SupplierCategory> supplierCategory = supplierCategoryRepo
				.findByNameContainingAndSupplierCategoryIdNotAndLiveIndAndOrgId(name, supplierCategoryId, true, orgId);
		if (supplierCategory.size() > 0) {
			return true;
		}
		return false;
	}
}