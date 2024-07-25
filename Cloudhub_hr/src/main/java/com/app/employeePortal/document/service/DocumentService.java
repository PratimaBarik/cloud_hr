package com.app.employeePortal.document.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.document.mapper.DocumentTypeMapper;

public interface DocumentService {
	public String saveUploadedFiles(List<MultipartFile> files, String employeeId, String organizationId);

	public DocumentDetails getDocumentDetailsByDocumentId(String documentId);

	public File convertMultiPartFileToFile(MultipartFile multipartFile);

	public DocumentTypeMapper addDocumentType(DocumentTypeMapper documentTypeMapper);

	public List<DocumentTypeMapper> getDocumentTypesByOrgId(String orgIdFromToken);

	//public boolean deleteDocumentType(String documentTypeId);

	public DocumentTypeMapper updatedocumentType(DocumentTypeMapper documentTypeMapper);

    DocumentTypeMapper getdocumentTypes(String documentTypeId);

    public List<DocumentTypeMapper> getDocumentDetailsByNameByOrgLevel(String name, String orgId);

	 public void deleteDocumentTypeById(String documentTypeId);

	public List<DocumentTypeMapper> getAllDocumentTypeByOrgIdAndProcessId(String orgIdFromToken, String processId);

	public String uploadFileThroughWebsite(List<MultipartFile> asList);

	public DocumentTypeMapper updatedocumentTypeByDocumentTypeId(DocumentTypeMapper documentTypeMapper);

	public DocumentTypeMapper updateUserTypeByDocumentTypeId(DocumentTypeMapper documentTypeMapper);

	public List<DocumentTypeMapper> getUserTypeListByOrgId(String OrgId);

	public List<DocumentTypeMapper> getCustomerTypeListByOrgId(String OrgId);

	void uploadFile(String bucketName, String filePath, File file);

	String uploadFolder(MultipartFile folder);

	void downloadDocumentFromCloud(DocumentDetails doc, HttpServletResponse response);

	public HashMap getDocumentTypeCountByOrgId(String orgId);

	void uploadFileForAzureBlob(String filePath, File file);

	public ByteArrayInputStream exportDocumentListToExcel(List<DocumentTypeMapper> list);

	public DocumentMapper saveDocument(DocumentMapper documentMapper);

	DocumentMapper getDocument(String documentId);

	public boolean checkDocumentNameInDocumentTypeByOrgLevel(String documentTypeName, String orgId);


	List<DocumentTypeMapper> getDocumentTypeListByOrgIdAndUserType(String orgId, String userType);

	public List<DocumentTypeMapper> getAllDocumentTypeByOrgIdAndUsertype(String orgId, String userType);
}
