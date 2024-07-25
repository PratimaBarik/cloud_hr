package com.app.employeePortal.registration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.registration.entity.Designation;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, String> {

	public List<Designation> findByorgId(String orgId);

	public Designation findByDesignationTypeId(String designationTypeId);

	// public List<Designation> findByDesignationType(String designationType);

	public Designation findByDesignationType(String designationType);

	public List<Designation> findByOrgIdAndLiveInd(String orgId, boolean liveInd);

//	public Designation findByDesignationTypeIdAndLiveInd(String designationId,boolean liveInd);

	public Designation findByOrgIdAndDesignationTypeContainingAndLiveInd(String orgId, String designation,boolean liveInd);

	public List<Designation> findByDesignationTypeContainingAndOrgId(String name, String orgId);

	public Designation findByDesignationTypeIdAndLiveIndAndOrgId(String designationType, boolean b, String orgId);

}
