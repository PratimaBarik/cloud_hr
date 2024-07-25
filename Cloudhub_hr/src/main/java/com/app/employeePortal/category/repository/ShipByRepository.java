package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.ShipBy;
@Repository
public interface ShipByRepository extends JpaRepository<ShipBy, String>{

	public ShipBy findByShipById(String shipById);

	public List<ShipBy> findByOrgIdAndLiveInd(String orgId, boolean b);

	public List<ShipBy> findByNameAndOrgId(String name, String orgId);

	public List<ShipBy> findByNameContainingAndOrgId(String name, String orgId);

	
	
}
