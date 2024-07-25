package com.app.employeePortal.education.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.education.mapper.EducationTypeMapper;

public interface EducationService {

	EducationTypeMapper saveEducationType(EducationTypeMapper educationTypeMapper);

	EducationTypeMapper getEducationTypeById(String educationTypeId);

	EducationTypeMapper updateEducationType(String educationTypeId, EducationTypeMapper educationTypeMapper);

	List<EducationTypeMapper> getEducationTypesByOrgId(String orgIdFromToken);

	public void deleteEducationTypeById(String educationTypeId);

	HashMap getEducationTypeCountByOrgId(String orgId);

	ByteArrayInputStream exportEducationListToExcel(List<EducationTypeMapper> list);

	boolean checkEducationNameInEducationTypeByOrgLevel(String educationType, String orgIdFromToken);

	List<EducationTypeMapper> getEducationTypeByNameByOrgLevel(String name, String orgId);

}
