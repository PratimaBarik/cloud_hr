package com.app.employeePortal.category.service;

import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.CertificationLibraryMapper;

public interface CertificationLibraryService {

	CertificationLibraryMapper saveCertificationLibrary(CertificationLibraryMapper certificationLibraryMapper);

	CertificationLibraryMapper getCertificationLibraryByCertificationId(String certificationId);

	CertificationLibraryMapper updateCertificationLibrary(String certificationId,
			CertificationLibraryMapper certificationLibraryMapper);

	boolean deleteCertificationLibrary(String certificationId);

	List<CertificationLibraryMapper> getCertificationLibraryByOrgId(String orgId);

    boolean checkCertificationNameExistByOrgLevel(String name,String orgId);

	public HashMap getCertificationsLibraryCountByOrgId(String orgId);

	List<CertificationLibraryMapper> getCertificationsLibraryByNameByOrgLevel(String name, String orgId);

	boolean checkCertificationNameExistForUpdateByOrgLevel(String certificationId, String name, String orgId);
}
