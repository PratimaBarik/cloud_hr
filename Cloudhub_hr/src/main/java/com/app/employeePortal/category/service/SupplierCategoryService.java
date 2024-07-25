package com.app.employeePortal.category.service;

import com.app.employeePortal.category.mapper.SupplierCategoryMapper;

import java.util.HashMap;
import java.util.List;

public interface SupplierCategoryService {

	SupplierCategoryMapper saveSupplierCategory(SupplierCategoryMapper mapper);

	boolean checkNameInSupplierCategory(String name, String orgIdFromToken);

	List<SupplierCategoryMapper> getSupplierCategoryByOrgId(String orgIdFromToken);

	SupplierCategoryMapper updateSupplierCategory(String equipmentId, SupplierCategoryMapper mapper);

	void deleteSupplierCategory(String equipmentId, String userIdFromToken);

	List<SupplierCategoryMapper> getSupplierCategoryByName(String name, String orgIdFromToken);

	HashMap getSupplierCategoryCountByOrgId(String orgIdFromToken);

	boolean checkNameInSupplierCategoryInUpdate(String name, String orgIdFromToken,String equipmentId);
	
}
