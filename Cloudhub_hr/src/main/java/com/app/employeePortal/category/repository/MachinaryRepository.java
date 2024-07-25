package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Machinary;

@Repository
public interface MachinaryRepository extends JpaRepository<Machinary, String> {

	Machinary findByMachinaryIdAndLiveInd(String Machinary, boolean b);

	List<Machinary> findByOrgIdAndLiveInd(String orgId, boolean b);

	List<Machinary> findByNameAndLiveIndAndOrgId(String name, boolean b, String orgId);
	
	List<Machinary> findByNameContainingAndLiveIndAndOrgId(String name, boolean b, String orgId);

    List<Machinary> findByNameContainingAndMachinaryIdNotAndLiveIndAndOrgId(String name,String MachinaryId, boolean b, String orgId);
}
