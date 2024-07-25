package com.app.employeePortal.category.repository;

import com.app.employeePortal.category.entity.SupplierCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierCategoryRepo extends JpaRepository<SupplierCategory, String> {

	SupplierCategory findBySupplierCategoryIdAndLiveInd(String SupplierCategory, boolean b);

	List<SupplierCategory> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<SupplierCategory> findByNameAndLiveIndAndOrgId(String name, boolean b, String orgId);
	
	List<SupplierCategory> findByNameContainingAndLiveIndAndOrgId(String name, boolean b, String orgId);

    List<SupplierCategory> findByNameContainingAndSupplierCategoryIdNotAndLiveIndAndOrgId(String name,String SupplierCatId, boolean b, String orgId);
}
