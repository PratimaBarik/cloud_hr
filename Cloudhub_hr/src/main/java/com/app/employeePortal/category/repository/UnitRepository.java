package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Unit;
@Repository
public interface UnitRepository extends JpaRepository<Unit, String>{

	List<Unit> findByOrgId(String orgId);

	Unit findByUnitId(String unitId);

	List<Unit> findByOrgIdAndLiveInd(String orgId, boolean b);
	
	List<Unit> findByUnitNameContainingAndLiveInd(String name, boolean b);


    List<Unit> findByUnitNameAndLiveInd(String unitName, boolean b);
}
