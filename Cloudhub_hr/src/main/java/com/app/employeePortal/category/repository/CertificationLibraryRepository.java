package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.CertificationLibrary;

@Repository
public interface CertificationLibraryRepository extends JpaRepository<CertificationLibrary, String> {

	public CertificationLibrary findByCertificationId(@Param(value = "certificationId") String certificationId);

	@Query(value = "select a  from CertificationLibrary a  where a.certificationId=:certificationId And a.liveInd = true")
	public CertificationLibrary getCertificationLibraryByCertificationId(
			@Param(value = "certificationId") String certificationId);

	@Query(value = "select a  from CertificationLibrary a  where a.orgId=:orgId And a.liveInd = true")
	public List<CertificationLibrary> getCertificationLibraryByOrgId(@Param(value = "orgId") String orgId);

	@Query(value = "select a  from CertificationLibrary a  where a.name=:candidateCertificationName and a.liveInd = true")
	public CertificationLibrary getByCandidateCertificationNameAndLiveInd(@Param(value = "candidateCertificationName") String candidateCertificationName);

	@Query(value = "select a  from CertificationLibrary a  where a.name=:employeeCertificationName and a.liveInd = true")
	public CertificationLibrary getByEmployeeCertificationNameAndLiveInd(@Param(value = "employeeCertificationName") String employeeCertificationName);

//	List<CertificationLibrary> findByNameContainingAndCertificationIdNotAndLiveInd(String name, String certificationId, boolean b);

	public List<CertificationLibrary> findByNameAndLiveIndAndOrgId(String name, boolean b, String orgId);

	public List<CertificationLibrary> findByNameContainingAndOrgId(String name, String orgId);

	public List<CertificationLibrary> findByNameContaining(String employeeCertificationName);

	public List<CertificationLibrary> findByNameContainingAndCertificationIdAndLiveIndAndOrgId(String name,
			String certificationId, boolean b, String orgId);
}
