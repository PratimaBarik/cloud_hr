package com.app.employeePortal.template.controller;


import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.template.mapper.NotificationTemplateMapper;
import com.app.employeePortal.template.mapper.SignatureMapper;
import com.app.employeePortal.template.mapper.TemplateMapper;
import com.app.employeePortal.template.service.TemplateService;


@Service
@Transactional
@RestController
@CrossOrigin(maxAge = 3600)
public class TemplateController {


	@Autowired
	private TokenProvider jwtTokenUtil;
	@Autowired
	TemplateService templateService;
	
	@PostMapping("/api/v1/email/template")

	public  ResponseEntity<?> createTemplate(@RequestBody TemplateMapper templateMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		
		 if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
	        	String authToken = authorization.replace(TOKEN_PREFIX, "");				
			templateMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			templateMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));			
			TemplateMapper resultMapper = templateService.SaveEmailTemplate(templateMapper);
			return new ResponseEntity<>(resultMapper, HttpStatus.OK);
			
		}

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/email/templates")

	public ResponseEntity<?> fetchTemplates(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");	
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);	
			List<TemplateMapper> list = templateService.getEmailTemplateByOrgId(orgId);
			return new ResponseEntity<>(list, HttpStatus.OK);
			
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@PutMapping("/api/v1/email/template")

	public ResponseEntity<?> updateTemplate(@RequestBody TemplateMapper templateMapper,@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
				
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			templateMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			templateMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			TemplateMapper resultMapper =  templateService.updateEmailTemplate(templateMapper);
			return new ResponseEntity<>(resultMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@PutMapping("/api/v1/user/signature")

	public ResponseEntity<?> updateUserSignature(@RequestBody SignatureMapper signatureMapper,
			                                    @RequestHeader("Authorization") String authorization,
			                                    HttpServletRequest request) {
				
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			signatureMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			signatureMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			signatureMapper.setType("User");
			SignatureMapper resultMapper =  templateService.updateSignatureTemplate(signatureMapper);
			return new ResponseEntity<>(resultMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/user/signature")

	public ResponseEntity<?> getUserSignature(
			                                    @RequestHeader("Authorization") String authorization,
			                                    HttpServletRequest request) {
				
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);	
			SignatureMapper resultMapper =  templateService.getUserSignature(userId);
			return new ResponseEntity<>(resultMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@PutMapping("/api/v1/organization/signature")

	public ResponseEntity<?> updateOrgSignature(@RequestBody  SignatureMapper signatureMapper, 
			                                    @RequestHeader("Authorization") String authorization,
			                                    HttpServletRequest request) {
				
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			signatureMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			signatureMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			signatureMapper.setType("Admin");
			SignatureMapper resultMapper =  templateService.updateSignatureTemplate(signatureMapper);
			return new ResponseEntity<>(resultMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/api/v1/organization/signature")

	public ResponseEntity<?> getAdminSignature(
			                                    @RequestHeader("Authorization") String authorization,
			                                    HttpServletRequest request) {
				
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);	
			SignatureMapper resultMapper =  templateService.getAdminSignature(orgId);
			return new ResponseEntity<>(resultMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
	
	 @GetMapping("/api/v1/check/signature")

	    public SignatureMapper checkSignature(
	            @RequestHeader("Authorization") String authorization,
	            HttpServletRequest request) throws IOException {

	        SignatureMapper signatureMapper = null;

	        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

	            String authToken = authorization.replace(TOKEN_PREFIX, "");


	            //signatureMapper = templateService.checkSignatureExists(jwtTokenUtil.getOrgIdFromToken(authToken), jwtTokenUtil.getUserIdFromToken(authToken));


	        }

	        return signatureMapper;

	    }
	 
	 
	 @PostMapping("/api/v1/notifcation")
	    public ResponseEntity<?>  createNotification(@RequestBody NotificationTemplateMapper notificationTemplateMapper, @RequestHeader("Authorization") String authorization,
	                                     HttpServletRequest request) throws IOException {

	        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

	            String authToken = authorization.replace(TOKEN_PREFIX, "");


	            notificationTemplateMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
	            notificationTemplateMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
	            NotificationTemplateMapper resultMapper = templateService.insertToNotificationTemplate(notificationTemplateMapper);
				return new ResponseEntity<>(resultMapper, HttpStatus.OK);
				
			}

	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
	 
	 
	 @GetMapping("/api/v1/notificationTemplate")
	    public List<NotificationTemplateMapper> fetchAllTemplate(@RequestHeader("Authorization") String authorization,
	                                                             HttpServletRequest request) throws IOException {

	        List<NotificationTemplateMapper> list = new ArrayList<>();

	        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

	            String authToken = authorization.replace(TOKEN_PREFIX, "");


	            String userId = jwtTokenUtil.getUserIdFromToken(authToken);
	            String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);


	            list = templateService.fetchNotificationTemplateDetails(jwtTokenUtil.getOrgIdFromToken(authToken));

	        /*    if (null != list && !list.isEmpty()) {

	                Collections.sort(list, (NotificationTemplateMapper m1, NotificationTemplateMapper m2) -> m2
	                        .getCreationDate().compareTo(m1.getCreationDate()));

	            }*/
	        }

	        return list;

	    }
	 
	 
	 @PutMapping("/api/v1/update/notification")
	    public NotificationTemplateMapper updateNotification(@RequestBody NotificationTemplateMapper notificationTemplateMapper, @RequestHeader("Authorization") String authorization,
	                                                         HttpServletRequest request) throws IOException {

	        NotificationTemplateMapper templateMapper2 = null;

	        if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

	            String authToken = authorization.replace(TOKEN_PREFIX, "");


	            String userId = jwtTokenUtil.getUserIdFromToken(authToken);
	            String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);

	            String templateId = templateService.updateNotificationTemplate(notificationTemplateMapper);

	            if (null != templateId) {

	                templateMapper2 = templateService.getNotificationTemplate(notificationTemplateMapper.getNotificationTemplateId());

	                templateMapper2.setNotificationTemplateId(templateId);
	            }


	        }

	        return templateMapper2;

	    }
	 
	 
	
	
}
