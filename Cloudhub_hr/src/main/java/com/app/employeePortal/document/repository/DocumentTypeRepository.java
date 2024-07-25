package com.app.employeePortal.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.document.entity.DocumentType;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, String> {

	@Query(value = "select a from DocumentType a where a.orgId=:orgId")
	public List<DocumentType> getDocumentTypesListByOrgId(@Param(value = "orgId") String orgId);

	@Query(value = "select a  from DocumentType a  where a.document_type_id=:document_type_id")
	public DocumentType getTypeDetails(@Param(value = "document_type_id") String document_type_id);

	@Query(value = "select a  from DocumentType a  where a.documentTypeName=:document_type_name")
	public DocumentType getDocumentTypeByName(@Param(value = "document_type_name") String document_type_name);

//	public List<DocumentType> findByDocumentTypeNameLike(String name);

	public List<DocumentType> findByDocumentTypeName(String documentTypeName);

	@Query(value = "select a from DocumentType a where a.orgId=:orgId and a.live_ind=:liveInd")
	public List<DocumentType> getDocumentTypesListByOrgIdAndLiveInd(@Param(value = "orgId") String orgId,
			@Param(value = "liveInd") boolean liveInd);
	
	@Query(value = "select a from DocumentType a where a.orgId=:orgId and a.userType=:userType and a.live_ind=true")
	public List<DocumentType> getDocumentTypesListByOrgIdAndUserTypeAndLiveInd(@Param(value = "orgId") String orgId,
			@Param(value = "userType") String userType);

	@Query(value = "select a  from DocumentType a  where a.documentTypeName=:documentTypeName and a.orgId=:orgId and a.live_ind=true")
	List<DocumentType> getDocumentTypeByNameAndLiveIndAndOrgId(@Param("documentTypeName") String documentTypeName,
			@Param(value = "orgId") String orgId);

	@Query(value = "select a from DocumentType a where a.orgId=:OrgId and a.userType=:userType and a.live_ind=true")
	public List<DocumentType> getDocumentTypeListByOrgIdAndUserTypeWithLiveInd(@Param(value = "OrgId") String OrgId,
			@Param(value = "userType") String userType);

	@Query(value = "select a from DocumentType a where a.orgId=:OrgId and a.userType=:userType and a.live_ind=true and a.mandatoryInd=true")
	public List<DocumentType> getDocumentTypeListByOrgIdAndUserTypeWithLiveIndAndMandatoryInd(
			@Param(value = "OrgId") String OrgId, @Param(value = "userType") String userType);

	public List<DocumentType> findByDocumentTypeNameContainingAndOrgId(String name, String orgId);

//	public List<DocumentType> findByDocumentTypeNameLikeAndOrgId(String name, String orgId);

	// public List<DocumentType> findByOrgIdAndLiveInd(String orgId, boolean b);

}
