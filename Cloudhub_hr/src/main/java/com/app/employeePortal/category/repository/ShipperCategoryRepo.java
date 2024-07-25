package com.app.employeePortal.category.repository;

import com.app.employeePortal.category.entity.ShipperCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipperCategoryRepo extends JpaRepository<ShipperCategory, String> {

	ShipperCategory findByShipperCategoryIdAndLiveInd(String ShipperCategory, boolean b);

	List<ShipperCategory> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<ShipperCategory> findByNameAndLiveIndAndOrgId(String name, boolean b, String orgId);
	
	List<ShipperCategory> findByNameContainingAndLiveIndAndOrgId(String name, boolean b, String orgId);

    List<ShipperCategory> findByNameContainingAndShipperCategoryIdNotAndLiveIndAndOrgId(String name,String shipperCatId, boolean b, String orgId);
}
