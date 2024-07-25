package com.app.employeePortal.document.service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.app.employeePortal.Opportunity.entity.OpportunityDocumentLink;
import com.app.employeePortal.Opportunity.repository.OpportunityDocumentLinkRepository;
import com.app.employeePortal.candidate.entity.CandidateDocumentLink;
import com.app.employeePortal.candidate.repository.CandidateDocumentLinkRepository;
import com.app.employeePortal.contact.entity.ContactDocumentLink;
import com.app.employeePortal.contact.repository.ContactDocumentLinkRepository;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.entity.CustomerDocumentLink;
import com.app.employeePortal.customer.repository.CustomerDocumentLinkRepository;
import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.entity.DocumentInfo;
import com.app.employeePortal.document.entity.DocumentType;
import com.app.employeePortal.document.entity.DocumentTypeDelete;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.document.mapper.DocumentTypeMapper;
import com.app.employeePortal.document.repository.DocumentDetailsRepository;
import com.app.employeePortal.document.repository.DocumentInfoRepository;
import com.app.employeePortal.document.repository.DocumentTypeDeleteRepository;
import com.app.employeePortal.document.repository.DocumentTypeRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.EmployeeDocumentLink;
import com.app.employeePortal.employee.repository.EmployeeDocumentLinkRepository;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.investor.entity.InvestorDocumentLink;
import com.app.employeePortal.investor.entity.InvestorOppDocsLink;
import com.app.employeePortal.investor.repository.InvestorDocumentLinkRepo;
import com.app.employeePortal.investor.repository.InvestorOppDocsLinkRepo;
import com.app.employeePortal.investorleads.entity.InvestorLeadsDocumentLink;
import com.app.employeePortal.investorleads.repository.InvestorLeadsDocumentLinkRepository;
import com.app.employeePortal.leads.entity.LeadsDocumentLink;
import com.app.employeePortal.leads.repository.LeadsDocumentLinkRepository;
import com.app.employeePortal.partner.entity.PartnerDocumentLink;
import com.app.employeePortal.partner.repository.PartnerDocumentLinkRepository;
import com.app.employeePortal.recruitment.entity.ProcessDocumentLink;
import com.app.employeePortal.recruitment.repository.ProcessDocumentLinkRepository;
import com.app.employeePortal.room.entity.RoomDocumentLink;
import com.app.employeePortal.room.repository.RoomDocumentRepository;
import com.app.employeePortal.task.entity.TaskDocumentLink;
import com.app.employeePortal.task.repository.TaskDocumentLinkRepository;
import com.app.employeePortal.util.Utility;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.BlobStorageException;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	DocumentInfoRepository documentInfoRepository;

	@Autowired
	DocumentDetailsRepository doucumenDetailsRepository;

	@Autowired
	DocumentTypeRepository documentTypeRepository;
	
	@Autowired
	DocumentTypeDeleteRepository documentTypeDeleteRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	ProcessDocumentLinkRepository processDocumentLinkRepository;

	@Autowired
	EmployeeService employeeService;
	@Autowired DocumentDetailsRepository documentDetailsRepository;
	@Autowired CandidateDocumentLinkRepository candidateDocumentLinkRepository;
	@Autowired ContactDocumentLinkRepository contactDocumentLinkRepository;
	@Autowired CustomerDocumentLinkRepository customerDocumentLinkRepository;
	@Autowired EmployeeDocumentLinkRepository employeeDocumentLinkRepository;
	@Autowired InvestorDocumentLinkRepo investorDocumentLinkRepo;
	@Autowired InvestorOppDocsLinkRepo investorOppDocsLinkRepo;
	@Autowired
	InvestorLeadsDocumentLinkRepository investorLeadsDocumentLinkRepository;
	@Autowired LeadsDocumentLinkRepository leadsDocumentLinkRepository;
	@Autowired OpportunityDocumentLinkRepository opportunityDocumentLinkRepository;
	@Autowired PartnerDocumentLinkRepository partnerDocumentLinkRepository;
	@Autowired TaskDocumentLinkRepository taskDocumentLinkRepository;
	@Autowired ContactService contactService;
	@Autowired RoomDocumentRepository roomDocumentRepository;
	
	@Autowired
	private AmazonS3 amazonS3;

	@Value("${bucketName}")
	private String s3bucket;
	
	private String[] documentType_headings = {"Name"};

	 @Value("${azure.storage.account-name}")
	    private String accountName;

	    @Value("${azure.storage.account-key}")
	    private String accountKey;

	    @Value("${azure.storage.blob-endpoint}")
	    private String blobEndpoint;
	    
	    private final String containerName = "docsupload";

	    /*aws s3 upload code*/
	public String saveUploadedFiles(List<MultipartFile> files, String employeeId, String organizationId) {
		String documentId = null;

		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}
			try {

				if (file.getOriginalFilename().trim() != null) {
					
					String fileName = file.getOriginalFilename();
					File actualfile = convertMultiPartFileToFile(file);
					String path = organizationId + "/" + employeeId + "/" + fileName;
					uploadFile(s3bucket, path, actualfile);
					
					DocumentInfo documentInfo = new DocumentInfo();
					documentInfo.setCreator_id(employeeId);
					documentInfo.setCreation_date(new Date());

					DocumentInfo documentInfoo = documentInfoRepository.save(documentInfo);
					documentId = documentInfoo.getDocument_id();

					if (null != documentId) {

						DocumentDetails documentDetails = new DocumentDetails();
						documentDetails.setDocument_id(documentId);
						documentDetails.setCreation_date(new Date());
						documentDetails.setCreator_id(employeeId);
						documentDetails.setDocument_size(file.getSize());
						documentDetails.setDocument_name(fileName);
						documentDetails.setExtensionType(file.getContentType());
//						documentDetails.setDocument_data(file.getBytes());
						documentDetails.setDocument_path(path);
						documentDetails.setEmployee_id(employeeId);
						documentDetails.setOrg_id(organizationId);
						documentDetails.setLive_ind(true);
						documentDetails.setFileName(fileName);
						doucumenDetailsRepository.save(documentDetails);
						actualfile.delete();
					}
				}

			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		return documentId;

	}
	
	
//	public String saveUploadedFiles(List<MultipartFile> files, String employeeId, String organizationId) {
//	String documentId = null;
//
//	for (MultipartFile file : files) {
//		if (file.isEmpty()) {
//			continue;
//		}
//		try {
//
//			if (file.getOriginalFilename().trim() != null) {
//				
//				String fileName = file.getOriginalFilename();
//				File actualfile = convertMultiPartFileToFile(file);
//				String path = organizationId + "/" + employeeId + "/" + fileName;
//				
////				 String blobName = folderPath + "/" + fileName;
//			        BlobClient blobClient = new BlobClientBuilder()
//			                .connectionString("DefaultEndpointsProtocol=https;AccountName=" + accountName + ";AccountKey=" + accountKey + ";EndpointSuffix=core.windows.net")
//			                .containerName(containerName)
//			                .blobName(path)
//			                .buildClient();
//
////			        blobClient.uploadFromFile(actualfile.getAbsolutePath());
//			        try {
//			            blobClient.uploadFromFile(actualfile.getAbsolutePath());
////			            System.out.println("trueeeeeeeeeeee");
//			        } catch (BlobStorageException e) {
////			        	System.out.println("false.............");
//			            e.printStackTrace();
//			        }
//			    actualfile.delete();
//				DocumentInfo documentInfo = new DocumentInfo();
//				documentInfo.setCreator_id(employeeId);
//				documentInfo.setCreation_date(new Date());
//
//				DocumentInfo documentInfoo = documentInfoRepository.save(documentInfo);
//				documentId = documentInfoo.getDocument_id();
//
//				if (null != documentId) {
//
//					DocumentDetails documentDetails = new DocumentDetails();
//					documentDetails.setDocument_id(documentId);
//					documentDetails.setCreation_date(new Date());
//					documentDetails.setCreator_id(employeeId);
//					documentDetails.setDocument_size(file.getSize());
//					documentDetails.setDocument_name(fileName);
//					documentDetails.setExtensionType(file.getContentType());
////					documentDetails.setDocument_data(file.getBytes());
//					documentDetails.setDocument_path(path);
//					documentDetails.setEmployee_id(employeeId);
//					documentDetails.setOrg_id(organizationId);
//					documentDetails.setLive_ind(true);
//					documentDetails.setFileName(fileName);
//					doucumenDetailsRepository.save(documentDetails);						
//				}
//			}
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//
//	}
//	return documentId;
//
//}

	@Override
	public DocumentDetails getDocumentDetailsByDocumentId(String documentId) {

		DocumentDetails documentDetails = doucumenDetailsRepository.getDocumentDetailsById(documentId);
		return documentDetails;
	}
	
//	/*Getting file from Azure Blob*/
//		@Override
//	    public void downloadDocumentFromCloud(DocumentDetails doc, HttpServletResponse response) {
//	        String folderPath = doc.getDocument_path();
//	        String fileName = doc.getDocument_name();
//
//	        BlobClient blobClient = new BlobClientBuilder()
//	                .connectionString("DefaultEndpointsProtocol=https;AccountName=" + accountName + ";AccountKey=" + accountKey + ";EndpointSuffix=core.windows.net")
//	                .containerName(containerName)
//	                .blobName(folderPath)
//	                .buildClient();
//
//	        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//	            blobClient.download(outputStream);
//	            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
//	            response.setContentType(blobClient.getProperties().getContentType());
//	            StreamUtils.copy(outputStream.toByteArray(), response.getOutputStream());
//	            response.flushBuffer();
//	        } catch (BlobStorageException | IOException e) {
//	            e.printStackTrace();
//	        }
//	    }	    

	/*Getting file from Aws s3*/
		@Override
	    public void downloadDocumentFromCloud(DocumentDetails doc, HttpServletResponse response) {
	        String folderPath = doc.getDocument_path();
	        String fileName = doc.getDocument_name();

		  	S3Object s3object = amazonS3.getObject(s3bucket, folderPath);
			S3ObjectInputStream inputStream = s3object.getObjectContent();
			try {
				response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
				response.setContentType(doc.getDocument_type());
				if (null != doc) {
//					byte[] buffer = doc.getDocument_data();
					IOUtils.copy(inputStream, response.getOutputStream());
				}
	
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	    }	    

	@Override
	public File convertMultiPartFileToFile(MultipartFile multipartFile) {
		File file = new File(multipartFile.getOriginalFilename());
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			outputStream.write(multipartFile.getBytes());
			outputStream.close();
		} catch (IOException ex) {
			System.out.println("Error converting the multi-part file to file= " + ex.getMessage());
		}
		return file;
	}
	
	@Override
	public DocumentTypeMapper addDocumentType(DocumentTypeMapper documentTypeMapper) {

		String documentTypeId = null;

		if (documentTypeMapper != null) {

			DocumentType documentTypeDetails = new DocumentType();

			documentTypeDetails.setDocumentTypeName(documentTypeMapper.getDocumentTypeName());
			documentTypeDetails.setCreator_id(documentTypeMapper.getCreatorId());
			documentTypeDetails.setOrgId(documentTypeMapper.getOrganizationId());
			documentTypeDetails.setCreation_date(new Date());
			documentTypeDetails.setLive_ind(true);
			documentTypeDetails.setEditInd(documentTypeMapper.isEditInd());
			documentTypeDetails.setUserId(documentTypeMapper.getCreatorId());
			documentTypeDetails.setUserType(documentTypeMapper.getUserType());

			DocumentType documentType = documentTypeRepository.save(documentTypeDetails);
			documentTypeId = documentType.getDocument_type_id();
			
			DocumentTypeDelete documentTypeDelete=new DocumentTypeDelete();
			documentTypeDelete.setDocument_type_id(documentTypeId);
			documentTypeDelete.setOrgId(documentTypeMapper.getOrganizationId());
			documentTypeDelete.setUpdationDate(new Date());
			documentTypeDelete.setUserId(documentTypeMapper.getCreatorId());
			documentTypeDelete.setUpdatedBy(documentTypeMapper.getUserId());
			documentTypeDeleteRepository.save(documentTypeDelete);
			
			

		}
		DocumentTypeMapper resultMapper = getdocumentTypes(documentTypeId);
		return resultMapper;
	}

	@Override
	public List<DocumentTypeMapper> getDocumentTypesByOrgId(String orgId) {

		List<DocumentTypeMapper> resultList = new ArrayList<DocumentTypeMapper>();
		List<DocumentType> documentTypetList = documentTypeRepository.getDocumentTypesListByOrgIdAndLiveInd(orgId,true);

		if (null != documentTypetList && !documentTypetList.isEmpty()) {
			documentTypetList.stream().map(document->{
				DocumentTypeMapper documentTypeMapper = new DocumentTypeMapper();
				documentTypeMapper.setDocumentTypeName(document.getDocumentTypeName());
				documentTypeMapper.setDocumentTypeId(document.getDocument_type_id());
				documentTypeMapper.setEditInd(document.isEditInd());
				documentTypeMapper.setMandatoryInd(document.isMandatoryInd());
				documentTypeMapper.setUserType(document.getUserType());
				documentTypeMapper.setCreationDate(Utility.getISOFromDate(document.getCreation_date()));

				resultList.add(documentTypeMapper);
				return resultList;

			}).collect(Collectors.toList());

		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		List<DocumentTypeDelete> documentTypeDelete = documentTypeDeleteRepository.findByOrgId(orgId);
		if (null != documentTypeDelete && !documentTypeDelete.isEmpty()) {
			Collections.sort(documentTypeDelete,
					( p1,p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			
			resultList.get(0).setUpdationDate(Utility.getISOFromDate(documentTypeDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(documentTypeDelete.get(0).getUserId());
			if(null!=employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					resultList.get(0).setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					resultList.get(0).setName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		
		
		return resultList;
	}

	@Override
	public List<DocumentTypeMapper> getAllDocumentTypeByOrgIdAndUsertype(String orgId, String userType) {
		List<DocumentTypeMapper> resultList = new ArrayList<DocumentTypeMapper>();
		List<DocumentType> documentTypetList = documentTypeRepository.
				getDocumentTypesListByOrgIdAndUserTypeAndLiveInd(orgId, userType);

		if (null != documentTypetList && !documentTypetList.isEmpty()) {
			documentTypetList.stream().map(document->{
				DocumentTypeMapper documentTypeMapper = new DocumentTypeMapper();
				documentTypeMapper.setDocumentTypeName(document.getDocumentTypeName());
				documentTypeMapper.setDocumentTypeId(document.getDocument_type_id());
				documentTypeMapper.setEditInd(document.isEditInd());
				documentTypeMapper.setMandatoryInd(document.isMandatoryInd());
				documentTypeMapper.setUserType(document.getUserType());
				documentTypeMapper.setCreationDate(Utility.getISOFromDate(document.getCreation_date()));

				resultList.add(documentTypeMapper);
				return resultList;

			}).collect(Collectors.toList());

		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		List<DocumentTypeDelete> documentTypeDelete = documentTypeDeleteRepository.findByOrgId(orgId);
		if (null != documentTypeDelete && !documentTypeDelete.isEmpty()) {
			Collections.sort(documentTypeDelete,
					( p1,p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			
			resultList.get(0).setUpdationDate(Utility.getISOFromDate(documentTypeDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(documentTypeDelete.get(0).getUserId());
			if(null!=employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					resultList.get(0).setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					resultList.get(0).setName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		
		
		return resultList;
	}

/*
	@Override
	public boolean deleteDocumentType(String documentTypeId) {

		DocumentType documentType = documentTypeRepository.getOne(documentTypeId);

		if (documentType != null) {
			DocumentTypeDelete documentTypeDelete= documentTypeDeleteRepository.getByDocumentTypeId(documentTypeId);
			if (documentTypeDelete != null) {
				documentTypeDelete.setUpdationDate(new Date());
			}
			documentTypeRepository.delete(documentType);
			return true;

		}

		return false;
	}
*/
	@Override
	public DocumentTypeMapper updatedocumentType(DocumentTypeMapper documentTypeMapper) {
		DocumentTypeMapper resultMapper = null;

		if (null != documentTypeMapper.getDocumentTypeId()) {

			DocumentType documentType = documentTypeRepository.getById(documentTypeMapper.getDocumentTypeId());

			if (null != documentTypeMapper.getDocumentTypeName()) {
				documentType.setDocumentTypeName(documentTypeMapper.getDocumentTypeName());
				documentType.setEditInd(documentTypeMapper.isEditInd());
				//documentType.setMandatoryInd(documentTypeMapper.isMandatoryInd());

				documentTypeRepository.save(documentType);
				
				DocumentTypeDelete documentTypeDelete= documentTypeDeleteRepository.getByDocumentTypeId(documentTypeMapper.getDocumentTypeId());
				if (null != documentTypeDelete) {
				documentTypeDelete.setUpdationDate(new Date());
				documentTypeDelete.setUpdatedBy(documentTypeMapper.getUserId());
				documentTypeDeleteRepository.save(documentTypeDelete);
				}else {
					DocumentTypeDelete documentTypeDelete1=new DocumentTypeDelete();
					documentTypeDelete1.setDocument_type_id(documentTypeMapper.getDocumentTypeId());
					documentTypeDelete1.setOrgId(documentTypeMapper.getOrganizationId());
					documentTypeDelete1.setUpdationDate(new Date());
					documentTypeDelete1.setUserId(documentTypeMapper.getUserId());
					documentTypeDelete1.setUpdatedBy(documentTypeMapper.getUserId());
					documentTypeDeleteRepository.save(documentTypeDelete1);
				}
			}
			resultMapper = getdocumentTypes(documentTypeMapper.getDocumentTypeId());
		}
		return resultMapper;
	}
@Override
public DocumentTypeMapper getdocumentTypes(String documentTypeId) {
		DocumentType documentType = documentTypeRepository.getById(documentTypeId);
		DocumentTypeMapper documentTypeMapper = new DocumentTypeMapper();

		if (null != documentType) {
			documentTypeMapper.setDocumentTypeId(documentType.getDocument_type_id());
			documentTypeMapper.setCreatorId(documentType.getCreator_id());
			documentTypeMapper.setEditInd(documentType.isEditInd());
			documentTypeMapper.setDocumentTypeName(documentType.getDocumentTypeName());
			documentTypeMapper.setCreationDate(Utility.getISOFromDate(documentType.getCreation_date()));
			documentTypeMapper.setUserType(documentType.getUserType());
			documentTypeMapper.setMandatoryInd(documentType.isMandatoryInd());
			List<DocumentTypeDelete> list=documentTypeDeleteRepository.findByOrgId(documentType.getOrgId());
			if(null!=list&&!list.isEmpty()) {
				Collections.sort(list,(p1,p2)->p2.getUpdationDate().compareTo(p2.getUpdationDate()));
				documentTypeMapper.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
				documentTypeMapper.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
			}
		}
		return documentTypeMapper;
	}

	@Override
	public List<DocumentTypeMapper> getDocumentDetailsByNameByOrgLevel(String name, String orgId) {
		List<DocumentType> list = documentTypeRepository.findByDocumentTypeNameContainingAndOrgId(name,orgId);
		if (null != list && !list.isEmpty()) {
			return list.stream().map(documentType -> {
				System.out.println("idProofTyoeById=========" + documentType.getDocument_type_id());
				DocumentTypeMapper documentTypeMapper = getdocumentTypes(documentType.getDocument_type_id());
				if (null != documentTypeMapper) {
					return documentTypeMapper;
				}
				return null;
			}).collect(Collectors.toList());

		}
		return null;
	}

	@Override
	public boolean checkDocumentNameInDocumentTypeByOrgLevel(String documentTypeName, String orgId) {
		List<DocumentType> documentTypes = documentTypeRepository.getDocumentTypeByNameAndLiveIndAndOrgId(documentTypeName,orgId);
		if (!documentTypes.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public List<DocumentTypeMapper> getDocumentTypeListByOrgIdAndUserType(String orgId, String userType) {
		List<DocumentType> documentTypes = documentTypeRepository.getDocumentTypeListByOrgIdAndUserTypeWithLiveIndAndMandatoryInd(orgId,userType);
		if(null!=documentTypes){
			return documentTypes.stream().map(d->getdocumentTypes(d.getDocument_type_id())).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Override
	public void deleteDocumentTypeById(String documentTypeId) {
		if (null != documentTypeId) {
			DocumentType documentType = documentTypeRepository.getTypeDetails(documentTypeId);
			if (documentType != null) {
			DocumentTypeDelete documentTypeDelete= documentTypeDeleteRepository.getByDocumentTypeId(documentTypeId);
			if (documentTypeDelete != null) {
				documentTypeDelete.setUpdationDate(new Date());
				documentTypeDelete.setUpdatedBy(documentType.getUserId());
				documentTypeDeleteRepository.save(documentTypeDelete);
			}
			documentType.setLive_ind(false);
			documentTypeRepository.save(documentType);
			}
		}

	}

	@Override
	public List<DocumentTypeMapper> getAllDocumentTypeByOrgIdAndProcessId(String orgId, String processId) {
		
		List<ProcessDocumentLink> processDocumentLink = processDocumentLinkRepository.getProcessDocumentListByProcessId(processId);
		
		List<DocumentTypeMapper> resultList = new ArrayList<DocumentTypeMapper>();
		List<DocumentType> documentTypetList = documentTypeRepository.getDocumentTypesListByOrgIdAndLiveInd(orgId,true);

		if (null != documentTypetList && !documentTypetList.isEmpty()) {
			 documentTypetList.stream().map(document->{

				DocumentTypeMapper documentTypeMapper = new DocumentTypeMapper();
				documentTypeMapper.setDocumentTypeName(document.getDocumentTypeName());
				documentTypeMapper.setDocumentTypeId(document.getDocument_type_id());
				documentTypeMapper.setEditInd(document.isEditInd());
				
				if(null!=processDocumentLink) {
					for (ProcessDocumentLink processDocumentLink1 : processDocumentLink) {
						if(processDocumentLink1.getDocumentTypeId().equalsIgnoreCase(document.getDocument_type_id())){
				documentTypeMapper.setMandatoryInd(processDocumentLink1.isMandatoryInd());
						}

					}
				}else {
					documentTypeMapper.setMandatoryInd(false);
				}
				documentTypeMapper.setProcessId(processId);
				documentTypeMapper.setCreationDate(Utility.getISOFromDate(document.getCreation_date()));

				resultList.add(documentTypeMapper);
				return resultList;

			}).collect(Collectors.toList());

		}
		
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		
		if(null!=processDocumentLink && !processDocumentLink.isEmpty()) {
			
			Collections.sort(processDocumentLink,
					( p1,p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			
			if(null!=resultList && !resultList.isEmpty()) {
			
			resultList.get(0).setUpdationDate(Utility.getISOFromDate(processDocumentLink.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(processDocumentLink.get(0).getUserId());
			if(null!=employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					resultList.get(0).setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					resultList.get(0).setName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		}
		
		return resultList;
	}
	
	@Override
	public String uploadFileThroughWebsite(List<MultipartFile> files) {
		String documentId = null;

		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}
			try {

				if (file.getOriginalFilename().trim() != null) {
					DocumentInfo documentInfo = new DocumentInfo();
					documentInfo.setCreator_id("");
					documentInfo.setCreation_date(new Date());

					DocumentInfo documentInfoo = documentInfoRepository.save(documentInfo);
					documentId = documentInfoo.getDocument_id();

					if (null != documentId) {

						DocumentDetails documentDetails = new DocumentDetails();
						documentDetails.setDocument_id(documentId);
						documentDetails.setCreation_date(new Date());
						documentDetails.setCreator_id("");
						documentDetails.setDocument_size(file.getSize());
						documentDetails.setDocument_name(file.getOriginalFilename());
						documentDetails.setExtensionType(file.getContentType());
//						documentDetails.setDocument_data(file.getBytes());
						documentDetails.setEmployee_id("");
						documentDetails.setOrg_id("");
						documentDetails.setLive_ind(true);
						doucumenDetailsRepository.save(documentDetails);

					}

				}

			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		return documentId;

	}

	@Override
	public DocumentTypeMapper updatedocumentTypeByDocumentTypeId(DocumentTypeMapper documentTypeMapper) {
		DocumentTypeMapper resultMapper = null;

		if (null != documentTypeMapper.getDocumentTypeId()) {

			DocumentType documentType = documentTypeRepository.getById(documentTypeMapper.getDocumentTypeId());

			if (null != documentType) {
				if (documentTypeMapper.isEditInd()) {
				documentType.setEditInd(documentTypeMapper.isEditInd());
				documentType.setMandatoryInd(false);
				documentTypeRepository.save(documentType);
				}else {
					documentType.setEditInd(documentTypeMapper.isEditInd());
					documentType.setMandatoryInd(true);
					documentTypeRepository.save(documentType);
				}
				
				DocumentTypeDelete documentTypeDelete= documentTypeDeleteRepository.getByDocumentTypeId(documentTypeMapper.getDocumentTypeId());
				if (null != documentTypeDelete) {
				documentTypeDelete.setUpdationDate(new Date());
				documentTypeDeleteRepository.save(documentTypeDelete);
				}else {
					DocumentTypeDelete documentTypeDelete1=new DocumentTypeDelete();
					documentTypeDelete1.setDocument_type_id(documentTypeMapper.getDocumentTypeId());
					documentTypeDelete1.setOrgId(documentTypeMapper.getOrganizationId());
					documentTypeDelete1.setUpdationDate(new Date());
					documentTypeDelete1.setUserId(documentTypeMapper.getUserId());
					documentTypeDeleteRepository.save(documentTypeDelete1);
				}
			}
			resultMapper = getdocumentTypes(documentTypeMapper.getDocumentTypeId());
		}
		return resultMapper;
	}

	@Override
	public DocumentTypeMapper updateUserTypeByDocumentTypeId(DocumentTypeMapper documentTypeMapper) {
		DocumentTypeMapper resultMapper = null;

		if (null != documentTypeMapper.getDocumentTypeId()) {

			DocumentType documentType = documentTypeRepository.getById(documentTypeMapper.getDocumentTypeId());

			if (null != documentType) {
				documentType.setUserType(documentTypeMapper.getUserType());
				documentTypeRepository.save(documentType);
				
				DocumentTypeDelete documentTypeDelete= documentTypeDeleteRepository.getByDocumentTypeId(documentTypeMapper.getDocumentTypeId());
				if (null != documentTypeDelete) {
				documentTypeDelete.setUpdationDate(new Date());
				documentTypeDelete.setUpdatedBy(documentTypeMapper.getUserId());
				documentTypeDeleteRepository.save(documentTypeDelete);
				}
			}
			resultMapper = getdocumentTypes(documentTypeMapper.getDocumentTypeId());
		}
		return resultMapper;
	}

	@Override
	public List<DocumentTypeMapper> getUserTypeListByOrgId(String OrgId) {
		List<DocumentTypeMapper> resultList=new ArrayList<>();
		List<DocumentType> documentTypeList=documentTypeRepository.getDocumentTypeListByOrgIdAndUserTypeWithLiveInd(OrgId,"User");
		if(null!=documentTypeList&&!documentTypeList.isEmpty()) {
			documentTypeList.stream().map(documentType->{
				DocumentTypeMapper documentTypeMapper=getdocumentTypes(documentType.getDocument_type_id());
				resultList.add(documentTypeMapper);
				return resultList;
			}).collect(Collectors.toList());
			
		}
		return resultList
				;
	}
	
	@Override
	public List<DocumentTypeMapper> getCustomerTypeListByOrgId(String OrgId) {
		List<DocumentTypeMapper> resultList=new ArrayList<>();
		List<DocumentType> documentTypeList=documentTypeRepository.getDocumentTypeListByOrgIdAndUserTypeWithLiveInd(OrgId,"Customer");
		if(null!=documentTypeList&&!documentTypeList.isEmpty()) {
			documentTypeList.stream().map(documentType->{
				DocumentTypeMapper documentTypeMapper=getdocumentTypes(documentType.getDocument_type_id());
				resultList.add(documentTypeMapper);
				return resultList;
			}).collect(Collectors.toList());
			
		}
		return resultList
				;
	}
	
	@Override
	public void uploadFile(String bucketName, String filePath, File file) {

		amazonS3.putObject(
				new PutObjectRequest(bucketName, filePath, file).withCannedAcl(CannedAccessControlList.PublicRead));

	}
	
	@Override
	public void uploadFileForAzureBlob(String filePath, File file) {
	  BlobClient blobClient = new BlobClientBuilder()
              .connectionString("DefaultEndpointsProtocol=https;AccountName=" + accountName + ";AccountKey=" + accountKey + ";EndpointSuffix=core.windows.net")
              .containerName(containerName)
              .blobName(filePath)
              .buildClient();

//      blobClient.uploadFromFile(actualfile.getAbsolutePath());
      try {
          blobClient.uploadFromFile(file.getAbsolutePath());
//          System.out.println("trueeeeeeeeeeee");
      } catch (BlobStorageException e) {
//      	System.out.println("false.............");
          e.printStackTrace();
      }
	}
	
	@Override
	public String uploadFolder(MultipartFile folder) {
        File tempFolder = new File(UUID.randomUUID().toString());
        tempFolder.mkdirs();

        try {
            folder.transferTo(new File(tempFolder, folder.getOriginalFilename()));

            File[] files = tempFolder.listFiles();
            for (File file : files) {
                amazonS3.putObject(new PutObjectRequest(s3bucket, file.getName(), file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            for (File file : tempFolder.listFiles()) {
                file.delete();
            }
            tempFolder.delete();
        }
		return "Folder uploaded successfully";
    }

	@Override
	public HashMap getDocumentTypeCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<DocumentType> list = documentTypeRepository.getDocumentTypesListByOrgIdAndLiveInd(orgId, true);
		map.put("DocumentTypeCount", list.size());
		return map;
	}


	@Override
	public ByteArrayInputStream exportDocumentListToExcel(List<DocumentTypeMapper> list) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("candidate");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < documentType_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(documentType_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != list && !list.isEmpty()) {
			for (DocumentTypeMapper sector : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(sector.getDocumentTypeName());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < documentType_headings.length; i++) {
			sheet.autoSizeColumn(i);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(outputStream.toByteArray());

	
	}


	@Override
	public DocumentMapper saveDocument(DocumentMapper documentMapper) {
		String documentId = documentMapper.getDocumentId();

		if (null != documentMapper) {
			DocumentDetails documentDetails = documentDetailsRepository.getDocumentDetailsById(documentId);
			documentDetails.setDocument_title(documentMapper.getDocumentTitle());
			documentDetails.setDocument_type(documentMapper.getDocumentTypeId());
			documentDetails.setDoc_description(documentMapper.getDocumentDescription());
			documentDetails.setDocument_id(documentDetails.getDocument_id());
			documentDetails.setCreation_date(new Date());
			// documentDetails.setDocumentType(documentMapper.getDocumentTypeId());
			documentDetailsRepository.save(documentDetails);

			Set<String> included = documentMapper.getIncluded();
			included.add(documentMapper.getUploadedBy());
			
			/* insert candidateDocument link table */
			if (!StringUtils.isEmpty(documentMapper.getCandidateId())) {
			for (String userId : included) {
			CandidateDocumentLink candidateDocumentLink = new CandidateDocumentLink();
			candidateDocumentLink.setCandidateId(documentMapper.getCandidateId());
			candidateDocumentLink.setDocumentId(documentId);
			candidateDocumentLink.setCreationDate(new Date());
			candidateDocumentLink.setSharedUser(userId);
			candidateDocumentLink.setContractInd(documentMapper.isContractInd());
			if (false != documentMapper.isShareInd()) {
				candidateDocumentLink.setShareInd(true);
			} else {
				candidateDocumentLink.setShareInd(false);
			}
			candidateDocumentLinkRepository.save(candidateDocumentLink);
			}
			}
			
			/* insert contactDocument link table */
			if (!StringUtils.isEmpty(documentMapper.getContactId())) {
				for (String userId : included) {
			ContactDocumentLink contactDocumentLink = new ContactDocumentLink();
			contactDocumentLink.setContact_id(documentMapper.getContactId());
			contactDocumentLink.setDocument_id(documentId);
			contactDocumentLink.setCreation_date(new Date());
			contactDocumentLink.setSharedUser(userId);
			contactDocumentLink.setShareInd(documentMapper.isShareInd());
			contactDocumentLink.setContractInd(documentMapper.isContractInd());
			contactDocumentLinkRepository.save(contactDocumentLink);
				}
			}
			
			 /* insert customerDocument link table */
			if (!StringUtils.isEmpty(documentMapper.getCustomerId())) {
				for (String userId : included) {
            CustomerDocumentLink customerDocumentLink = new CustomerDocumentLink();
            customerDocumentLink.setCustomerId(documentMapper.getCustomerId());
            customerDocumentLink.setDocumentId(documentMapper.getDocumentId());
            customerDocumentLink.setContractInd(documentMapper.isContractInd());
            customerDocumentLink.setSharedUser(userId);
            customerDocumentLink.setShareInd(documentMapper.isShareInd());
            customerDocumentLinkRepository.save(customerDocumentLink);
				}
			}
			
			 /* insert employeeDocument link table */
			if (!StringUtils.isEmpty(documentMapper.getEmployeeId())) {
				for (String userId : included) {
            EmployeeDocumentLink employeeDocumentLink = new EmployeeDocumentLink();
            employeeDocumentLink.setEmployeeId(documentMapper.getEmployeeId());
            employeeDocumentLink.setDocumentId(documentId);
            employeeDocumentLink.setCreationDate(new Date());
            employeeDocumentLink.setSharedUser(userId);
            employeeDocumentLink.setShareInd(documentMapper.isShareInd());
            employeeDocumentLink.setContractInd(documentMapper.isContractInd());
            employeeDocumentLinkRepository.save(employeeDocumentLink);
				}
			}
			
			/* insert investorDocument link table */
			if (!StringUtils.isEmpty(documentMapper.getInvestorId())) {
				for (String userId : included) {
			InvestorDocumentLink investorDocumentLink = new InvestorDocumentLink();
			investorDocumentLink.setInvestorId(documentMapper.getInvestorId());
			investorDocumentLink.setDocumentId(documentId);
			investorDocumentLink.setSharedUser(userId);
			investorDocumentLink.setShareInd(documentMapper.isShareInd());
			investorDocumentLink.setContractInd(documentMapper.isContractInd());
			investorDocumentLinkRepo.save(investorDocumentLink);
				}
			}
			
			/* insert investorOpportunityDocumentLink Table */
			if (!StringUtils.isEmpty(documentMapper.getInvOpportunityId())) {
				for (String userId : included) {
			InvestorOppDocsLink opportunityDocumentLink = new InvestorOppDocsLink();
			opportunityDocumentLink.setDocumentId(documentId);
			opportunityDocumentLink.setCreationDate(new Date());
			opportunityDocumentLink.setInvOpportunityId(documentMapper.getInvOpportunityId());
			opportunityDocumentLink.setSharedUser(userId);
			opportunityDocumentLink.setShareInd(documentMapper.isShareInd());
			opportunityDocumentLink.setContractInd(documentMapper.isContractInd());
			investorOppDocsLinkRepo.save(opportunityDocumentLink);
				}
			}
			
			/* insert InvestorLeadsDocument link table */
			if (!StringUtils.isEmpty(documentMapper.getInvestorLeadsId())) {
				for (String userId : included) {
			InvestorLeadsDocumentLink investorLeadsDocumentLink = new InvestorLeadsDocumentLink();
			investorLeadsDocumentLink.setInvestorleadsId(documentMapper.getInvestorLeadsId());
			investorLeadsDocumentLink.setDocumentId(documentMapper.getDocumentId());
			investorLeadsDocumentLink.setCreationDate(new Date());
			investorLeadsDocumentLink.setSharedUser(userId);
			investorLeadsDocumentLink.setShareInd(documentMapper.isShareInd());
			investorLeadsDocumentLink.setContractInd(documentMapper.isContractInd());
			investorLeadsDocumentLinkRepository.save(investorLeadsDocumentLink);
				}
			}
			
			/* insert LeadsDocument link table */
			if (!StringUtils.isEmpty(documentMapper.getLeadsId())) {
				for (String userId : included) {
			LeadsDocumentLink leadsDocumentLink = new LeadsDocumentLink();
			leadsDocumentLink.setLeadsId(documentMapper.getLeadsId());
			leadsDocumentLink.setDocumentId(documentMapper.getDocumentId());
			leadsDocumentLink.setCreationDate(new Date());
			leadsDocumentLink.setSharedUser(userId);
			leadsDocumentLink.setShareInd(documentMapper.isShareInd());
			leadsDocumentLink.setContractInd(documentMapper.isContractInd());
			leadsDocumentLinkRepository.save(leadsDocumentLink);
				}
			}
			
			
			/* insert opportunityDocumentLink Table */
			if (!StringUtils.isEmpty(documentMapper.getOpportunityId())) {
				for (String userId : included) {
			OpportunityDocumentLink opportunityDocumentLink = new OpportunityDocumentLink();
			opportunityDocumentLink.setDocument_id(documentId);
			opportunityDocumentLink.setCreation_date(new Date());
			opportunityDocumentLink.setOpportunity_id(documentMapper.getOpportunityId());
			opportunityDocumentLink.setContractInd(documentMapper.isContractInd());
			opportunityDocumentLink.setSharedUser(userId);
			opportunityDocumentLink.setShareInd(documentMapper.isShareInd());
			opportunityDocumentLinkRepository.save(opportunityDocumentLink);
				}
			}
			
			/* insert partnerDocument link table */
			if (!StringUtils.isEmpty(documentMapper.getPartnerId())) {
				for (String userId : included) {
            PartnerDocumentLink partnerDocumentLink = new PartnerDocumentLink();
            partnerDocumentLink.setPartner_id(documentMapper.getPartnerId());
            partnerDocumentLink.setDocumentId(documentMapper.getDocumentId());
            partnerDocumentLink.setSharedUser(userId);
            partnerDocumentLink.setShareInd(documentMapper.isShareInd());
            partnerDocumentLink.setContractInd(documentMapper.isContractInd());
            partnerDocumentLinkRepository.save(partnerDocumentLink);
				}
			}
			
			/* insert LeadsDocument link table */
			if (!StringUtils.isEmpty(documentMapper.getTaskId())) {
				for (String userId : included) {
			TaskDocumentLink taskDocumentLink = new TaskDocumentLink();
			taskDocumentLink.setTaskId(documentMapper.getTaskId());
			taskDocumentLink.setDocumentId(documentMapper.getDocumentId());
			taskDocumentLink.setCreationDate(new Date());
			taskDocumentLink.setSharedUser(userId);
			taskDocumentLink.setShareInd(documentMapper.isShareInd());
			taskDocumentLink.setContractInd(documentMapper.isContractInd());
			taskDocumentLinkRepository.save(taskDocumentLink);
				}
			}
			 /* insert RoomDocument link table */
			if (!StringUtils.isEmpty(documentMapper.getRoomId())) {
            RoomDocumentLink link = new RoomDocumentLink();
            link.setRoomId(documentMapper.getRoomId());
            link.setDocumentId(documentId);
            link.setCreationDate(new Date());
            link.setSharedUser(documentMapper.getEmployeeId());
            link.setShareInd(documentMapper.isShareInd());
            link.setContractInd(documentMapper.isContractInd());
            roomDocumentRepository.save(link);
			}
		}
		return getDocument(documentId);
	}

	@Override
	public DocumentMapper getDocument(String documentId) {
		System.out.println("What1........" + documentId);
		DocumentDetails doc = documentDetailsRepository.getDocumentDetailsById(documentId);
		DocumentMapper documentMapper = new DocumentMapper();
		if (null != doc) {
			documentMapper.setDocumentId(documentId);
			documentMapper.setDocumentName(doc.getDocument_name());
			documentMapper.setDocumentTitle(doc.getDocument_title());
			// documentMapper.setDocumentContentType(doc.getDocument_type());
			documentMapper.setDocumentDescription(doc.getDoc_description());
			documentMapper.setFileName(doc.getFileName());
			System.out.println("What2........" + doc.getDocument_type());

			DocumentType type = documentTypeRepository.getTypeDetails(doc.getDocument_type());
			if (type != null) {
				documentMapper.setDocumentContentType(type.getDocumentTypeName());

			} else {
				documentMapper.setDocumentContentType("");
			}

			EmployeeDetails details = employeeRepository.getEmployeeDetailsByEmployeeId(doc.getEmployee_id());
			if (null != details) {
				System.out.println("What2........" + doc.getEmployee_id());
				documentMapper.setUploadedBy(details.getFirstName() + " " + details.getLastName());
				System.out.println("What2........" + details.getFirstName() + details.getLastName());
			} else {
				documentMapper.setUploadedBy("Self");
			}
			documentMapper.setCreationDate(Utility.getISOFromDate(doc.getCreation_date()));
		}
		return documentMapper;
	}

}
