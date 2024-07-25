package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Equipment;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {

	Equipment findByEquipmentIdAndLiveInd(String Equipment, boolean b);

	List<Equipment> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<Equipment> findByNameAndLiveIndAndOrgId(String name, boolean b, String orgId);
	
	List<Equipment> findByNameContainingAndLiveIndAndOrgId(String name, boolean b, String orgId);

    List<Equipment> findByNameContainingAndEquipmentIdNotAndLiveIndAndOrgId(String name,String equipmentId, boolean b, String orgId);
}
