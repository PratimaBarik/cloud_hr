package com.app.employeePortal.category.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.category.mapper.BaseFormMapper;
import com.app.employeePortal.category.service.BaseFormService;

import freemarker.template.TemplateException;

@RestController
@CrossOrigin(maxAge = 3600)

public class BaseFormController {
	@Autowired
	BaseFormService baseFormService;
	@Autowired
	private TokenProvider jwtTokenUtil;

	@PutMapping("/api/v1/baseForm/update/form")
	public ResponseEntity<?> updateBaseForm(
			@RequestBody BaseFormMapper mapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws IOException, TemplateException {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			mapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			mapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<BaseFormMapper>(baseFormService.updateBaseForm(mapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/baseForm/get/{orgId}/{formType}/{baseFormType}")
	public ResponseEntity<?> getBaseFormByOrgIdAndFormTypeAndBaseFormType(@PathVariable("orgId") String orgId,
			@PathVariable("formType") String formType,@PathVariable("baseFormType") String baseFormType,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(baseFormService.getBaseFormByOrgIdAndFormTypeAndBaseFormType(orgId, formType, baseFormType));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
