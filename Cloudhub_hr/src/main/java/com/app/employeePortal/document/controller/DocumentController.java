package com.app.employeePortal.document.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.document.mapper.DocumentTypeMapper;
import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(maxAge = 3600)
public class DocumentController {

	@Autowired
	private DocumentService documentService;

	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	WebsiteRepository websiteRepository;

	@Autowired
	private AmazonS3 amazonS3;

	@Value("${bucketName}")
	private String s3bucket;

	@PostMapping("/api/v1/document/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile,
			// @RequestBody MultipartFile uploadfile,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			if (uploadfile.isEmpty()) {
				return new ResponseEntity<String>("please select a file!", HttpStatus.OK);
			} else if (null != uploadfile && uploadfile.getSize() > 0) {

				try {
					// File will get saved to file system and database

					// File file = documentService.convertMultiPartFileToFile(uploadfile);
					long fileSize = uploadfile.getSize();

					if (fileSize <= 10485760) {
						String documentId = documentService.saveUploadedFiles(Arrays.asList(uploadfile),
								jwtTokenUtil.getUserIdFromToken(authToken), jwtTokenUtil.getOrgIdFromToken(authToken));
						return new ResponseEntity<String>(documentId, HttpStatus.OK);

					} else {
						return new ResponseEntity<String>("File size exceeds 10MB", HttpStatus.OK);

					}

				} catch (Exception e) {

					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	/* view document by documentId */
//	@GetMapping("/api/v1/document/{documentId}")
//	public void downloadFile3(@PathVariable("documentId") String documentId, HttpServletResponse response)
//			throws IOException {
//
//		DocumentDetails doc = documentService.getDocumentDetailsByDocumentId(documentId);
//		String folderPath = doc.getDocument_path();
//	  	String fileName = doc.getDocument_name();
//	  	S3Object s3object = amazonS3.getObject(s3bucket, folderPath);
//		S3ObjectInputStream inputStream = s3object.getObjectContent();
//		try {
//			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
//			response.setContentType(doc.getDocument_type());
//			if (null != doc) {
////				byte[] buffer = doc.getDocument_data();
//				IOUtils.copy(inputStream, response.getOutputStream());
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/* view document by documentId */
	@GetMapping("/api/v1/document/{documentId}")
	public void downloadFile3(@PathVariable("documentId") String documentId, HttpServletResponse response)
			throws IOException {
		DocumentDetails doc = documentService.getDocumentDetailsByDocumentId(documentId);
		if (doc != null) {
			documentService.downloadDocumentFromCloud(doc, response);
		} else {
			// Handle document not found
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	/*
	 * DocumentDetails doc =
	 * documentService.getDocumentDetailsByDocumentId(documentId);
	 * 
	 * String folderPath = doc.getDocument_path(); String fileName =
	 * doc.getDocument_name();
	 * 
	 * response.setHeader("Content-Disposition", "inline;filename=\"" + fileName +
	 * "\""); response.setContentType("application/octet-stream");
	 * 
	 * S3Object s3object = amazonS3.getObject(s3bucket, folderPath);
	 * S3ObjectInputStream inputStream = s3object.getObjectContent(); try {
	 * IOUtils.copy(inputStream, response.getOutputStream()); } catch (IOException
	 * e) { e.printStackTrace(); } }
	 */

	@PostMapping("/api/v1/document")
	public ResponseEntity<?> addDocumentType(@RequestBody DocumentTypeMapper documentTypeMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getUserIdFromToken(authToken);
			if (!StringUtils.isEmpty(documentTypeMapper.getDocumentTypeName())) {
				boolean b = documentService.checkDocumentNameInDocumentTypeByOrgLevel(documentTypeMapper.getDocumentTypeName(),orgId);
				if (b == true) {
					map.put("docTypeInd", b);
					map.put("message", "DocumentName can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					
					documentTypeMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
					documentTypeMapper.setCreatorId(jwtTokenUtil.getUserIdFromToken(authToken));

					DocumentTypeMapper documentid = documentService.addDocumentType(documentTypeMapper);

					return new ResponseEntity<>(documentid, HttpStatus.OK);
				}
			} else {
				map.put("message", "Please Provide DocumentName !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/document")
	public ResponseEntity<?> getAllDocumentType(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<DocumentTypeMapper> typeList = documentService
					.getDocumentTypesByOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			if (null != typeList && !typeList.isEmpty()) {
				Collections.sort(typeList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			}
			return new ResponseEntity<>(typeList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/document/get-all/{orgId}/{userType}")
	public ResponseEntity<?> getAllDocumentTypeByOrgIdAndUsertype(@PathVariable("orgId") String orgId,
			@PathVariable("userType") String userType,@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<DocumentTypeMapper> typeList = documentService
					.getAllDocumentTypeByOrgIdAndUsertype(orgId, userType);
			if (null != typeList && !typeList.isEmpty()) {
				Collections.sort(typeList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			} 
				return new ResponseEntity<>(typeList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/document")

	public ResponseEntity<?> updatedocumentType(@RequestBody DocumentTypeMapper documentTypeMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			documentTypeMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			documentTypeMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			if (!StringUtils.isEmpty(documentTypeMapper.getDocumentTypeName())) {
				boolean b = documentService.checkDocumentNameInDocumentTypeByOrgLevel(documentTypeMapper.getDocumentTypeName(),orgId);
				if (b == true) {
					Map map = new HashMap<>();
					map.put("docTypeInd", true);
					map.put("message", "DocumentName can not be updated as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}

			DocumentTypeMapper documentTypeMapperr = documentService.updatedocumentType(documentTypeMapper);

			return new ResponseEntity<DocumentTypeMapper>(documentTypeMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	/*
	 * @DeleteMapping("/api/v1/document")
	 * 
	 * public ResponseEntity<?> deleteDocumentType(@PathVariable("documentTypeId")
	 * String documentTypeId,
	 * 
	 * @RequestHeader("Authorization") String authorization, HttpServletRequest
	 * request) {
	 * 
	 * if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	 * 
	 * boolean b = documentService.deleteDocumentType(documentTypeId); return new
	 * ResponseEntity<>(b, HttpStatus.OK);
	 * 
	 * } return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); }
	 */
	@GetMapping("/api/v1/document/search/{name}")
	public ResponseEntity<?> getDocumentDetailsByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request)
			throws JsonGenerationException, IOException {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<DocumentTypeMapper> documentTypeMapper = documentService.getDocumentDetailsByNameByOrgLevel(name,orgId);
			if (null != documentTypeMapper && !documentTypeMapper.isEmpty()) {
				return new ResponseEntity<>(documentTypeMapper, HttpStatus.OK);
			} else {
				map.put("message", " No Records Found !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
			// return new ResponseEntity<>(documentTypeMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/documentType/{documentTypeId}")

	public ResponseEntity<?> deleteDocumentTypes(@PathVariable("documentTypeId") String documentTypeId,

			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			documentService.deleteDocumentTypeById(documentTypeId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/document/get/documentType/{processId}")
	public ResponseEntity<?> getAllDocumentTypeByOrgIdAndProcessId(@RequestHeader("Authorization") String authorization,
			@PathVariable("processId") String processId, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			List<DocumentTypeMapper> typeList = documentService
					.getAllDocumentTypeByOrgIdAndProcessId(jwtTokenUtil.getOrgIdFromToken(authToken), processId);

			if (null != typeList && !typeList.isEmpty()) {
				Collections.sort(typeList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(typeList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(typeList, HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/document/website/upload")
	public ResponseEntity<?> uploadFileThroughWebsite(@RequestParam("file") MultipartFile uploadfile,
			@RequestParam(value = "url", required = true) String url) {

		Map map = new HashMap();
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {

			if (uploadfile.isEmpty()) {
				return new ResponseEntity<String>("please select a file!", HttpStatus.OK);
			} else if (null != uploadfile && uploadfile.getSize() > 0) {

				try {
					// File will get saved to file system and database

					// File file = documentService.convertMultiPartFileToFile(uploadfile);
					long fileSize = uploadfile.getSize();

					if (fileSize <= 10485760) {
						String documentId = documentService.saveUploadedFiles(Arrays.asList(uploadfile),
								"throughWebsite", "docs");
						return new ResponseEntity<String>(documentId, HttpStatus.OK);

					} else {
						return new ResponseEntity<String>("File size exceeds 10MB", HttpStatus.OK);

					}

				} catch (Exception e) {

					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
		} else {
			map.put("website", true);
			map.put("message", " website not Present !!!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return null;

	}

	@PutMapping("/api/v1/document/update/document-type")

	public ResponseEntity<?> updatedocumentTypeByDocumentTypeId(@RequestBody DocumentTypeMapper documentTypeMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			documentTypeMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			documentTypeMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));

			DocumentTypeMapper documentTypeMapperr = documentService
					.updatedocumentTypeByDocumentTypeId(documentTypeMapper);

			return new ResponseEntity<DocumentTypeMapper>(documentTypeMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/document/update/user-type")
	public ResponseEntity<?> updateUserTypeByDocumentTypeId(@RequestBody DocumentTypeMapper documentTypeMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			documentTypeMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			documentTypeMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));

			DocumentTypeMapper documentTypeMapperr = documentService.updateUserTypeByDocumentTypeId(documentTypeMapper);

			return new ResponseEntity<DocumentTypeMapper>(documentTypeMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/document/user-type/user/list/{OrgId}")
	public ResponseEntity<?> getUserTypeListByOrgId(@PathVariable("OrgId") String OrgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<DocumentTypeMapper> userTypeList = documentService.getUserTypeListByOrgId(OrgId);
			return new ResponseEntity<>(userTypeList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/document/user-type/customer/list/{OrgId}")
	public ResponseEntity<?> getCustomerTypeListByOrgId(@PathVariable("OrgId") String OrgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<DocumentTypeMapper> userTypeList = documentService.getCustomerTypeListByOrgId(OrgId);
			return new ResponseEntity<>(userTypeList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/document/aws/upload-folder")
	@ApiOperation(value = "Upload a folder")
	public ResponseEntity<?> handleFolderUpload(@RequestParam("folder") MultipartFile folder,
			@RequestHeader("Authorization") String authorization) {
		// Process the folder and upload files to S3
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return new ResponseEntity<>(documentService.uploadFolder(folder), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/documentType/count/{orgId}")
	public ResponseEntity<?> getDocumentTypeCountByOrgId(@RequestHeader("Authorization") String authorization,
			@PathVariable("orgId") String orgId) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			return ResponseEntity.ok(documentService.getDocumentTypeCountByOrgId(orgId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/document/save")
	public ResponseEntity<?> saveDocument(@RequestBody DocumentMapper documentMapper,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			documentMapper.setUploadedBy(jwtTokenUtil.getUserIdFromToken(authToken));
			// notesMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			DocumentMapper id = documentService.saveDocument(documentMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/document/documentType/list/{orgId}/{userType}")
	public ResponseEntity<?> getDocumentTypeListByOrgIdAndUserType(@PathVariable("orgId") String orgId,
			@PathVariable("userType") String userType,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<DocumentTypeMapper> userTypeList = documentService.getDocumentTypeListByOrgIdAndUserType(orgId,userType);
			return new ResponseEntity<>(userTypeList, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
}
