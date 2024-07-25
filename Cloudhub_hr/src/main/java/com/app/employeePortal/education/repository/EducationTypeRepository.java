package com.app.employeePortal.education.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.education.entity.EducationType;

@Repository
public interface EducationTypeRepository extends JpaRepository<EducationType, String>{

	public EducationType findByEducationTypeId(String educationTypeId);

	//public List<EducationType> findByorgId(String orgId);

    public List<EducationType> findByEducationTypeContainingAndOrgId(String name,String orgId);

    public List<EducationType> findByEducationType(String educationType);

	public List<EducationType> findByOrgIdAndLiveInd(String orgId, boolean liveInd);


	public List<EducationType> findByEducationTypeAndLiveIndAndOrgId(String educationType, boolean b, String orgId);

}
