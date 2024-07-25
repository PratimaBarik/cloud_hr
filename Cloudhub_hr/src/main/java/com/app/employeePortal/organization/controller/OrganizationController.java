package com.app.employeePortal.organization.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.app.employeePortal.organization.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.organization.service.OrganizationService;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(maxAge = 3600)
public class OrganizationController {

	@Autowired
	OrganizationService organizationService;
	@Autowired
	private TokenProvider jwtTokenUtil;
	private OrganizationCurrencyLinkMapper organizationDocumentLinkMapper;

	@GetMapping("/api/v1/organization")
	public ResponseEntity<?> getOrganizationDetailsById(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			OrganizationMapper organizationMapper = organizationService.getOrganizationDetails(orgId);
			return new ResponseEntity<OrganizationMapper>(organizationMapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/organization")

	public ResponseEntity<?> updateOrganization(@RequestBody OrganizationMapper organizationMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			organizationMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));

			OrganizationMapper organizationMapperNew = organizationService
					.updateOrganizationDetails(organizationMapper);
			return new ResponseEntity<OrganizationMapper>(organizationMapperNew, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/organization/{orgId}")
	public ResponseEntity<?> upateOrganizationByOrgId(@PathVariable("orgId") String orgId,
			@RequestBody OrganizationMapper organizationMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			OrganizationMapper mapper = organizationService.upateOrganizationByOrgId(orgId, organizationMapper);

			return new ResponseEntity<OrganizationMapper>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/organization/document")
	public ResponseEntity<?> CreateOrgDocument(@RequestHeader("Authorization") String authorization,
			@RequestBody OrganizationDocumentLinkMapper organizationDocumentLinkMapper) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			organizationDocumentLinkMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			organizationDocumentLinkMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			OrganizationDocumentLinkMapper orgDocMapper = organizationService
					.SaveOrgDocument(organizationDocumentLinkMapper);
			return new ResponseEntity<>(orgDocMapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/organization/document/{organizationDocumentLinkId}")
	public ResponseEntity<?> getOrganizationDocumentLinkDetailsById(
			@PathVariable("organizationDocumentLinkId") String organizationDocumentLinkId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			OrganizationDocumentLinkMapper mapper = organizationService
					.getOrganizationDocumentLinkDetailsById(organizationDocumentLinkId);

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/organization/document/user/{userId}")
	public ResponseEntity<?> getOrganizationDocumentByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<OrganizationDocumentLinkMapper> organizationDocumentLinkMapper = organizationService
					.getOrganizationDocumentByUserId(userId);

			return new ResponseEntity<>(organizationDocumentLinkMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/organization/document/organization/{orgId}")
	public ResponseEntity<?> getOrganizationDocumentByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<OrganizationDocumentLinkMapper> organizationDocumentLinkMapper = organizationService
					.getOrganizationDocumentByOrgId(orgId);

			return new ResponseEntity<>(organizationDocumentLinkMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/organization/document/{organizationDocumentLinkId}")
	public ResponseEntity<?> deleteOrganizationDocument(
			@PathVariable("organizationDocumentLinkId") String organizationDocumentLinkId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			organizationService.deleteDocumentsById(organizationDocumentLinkId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

//	@PutMapping("/api/v1/organization/publishInd")
//	public ResponseEntity<?> upatePublishInd(@RequestBody OrganizationDocumentLinkMapper organizationDocumentLinkMapper,
//			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			OrganizationDocumentLinkMapper mapper = organizationService.upatePublishInd(organizationDocumentLinkMapper);
//
//			return new ResponseEntity<OrganizationDocumentLinkMapper>(mapper, HttpStatus.OK);
//		}
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//	}
//
//	@PutMapping("/api/v1/organization/publicInd")
//	public ResponseEntity<?> upatePublicInd(@RequestBody OrganizationDocumentLinkMapper organizationDocumentLinkMapper,
//			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
//
//		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//
//			OrganizationDocumentLinkMapper mapper = organizationService.upatePublicInd(organizationDocumentLinkMapper);
//
//			return new ResponseEntity<OrganizationDocumentLinkMapper>(mapper, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//	}

	@PostMapping("/api/v1/organization/currency")
	public ResponseEntity<?> CreateOrgCurrency(@RequestHeader("Authorization") String authorization,
			@RequestBody OrganizationCurrencyLinkMapper organizationCurrencyLinkMapper) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			organizationCurrencyLinkMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			organizationCurrencyLinkMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			OrganizationCurrencyLinkMapper orgDocMapper = organizationService
					.SaveOrgCurrency(organizationCurrencyLinkMapper);
			return new ResponseEntity<>(orgDocMapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/eorganization/currency/{orgCurrencyLinkId}")
	public ResponseEntity<?> getOrganizationCurrencyLinkDetailsById(
			@PathVariable("orgCurrencyLinkId") String orgCurrencyLinkId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			OrganizationCurrencyLinkMapper mapper = organizationService
					.getOrganizationCurrencyLinkDetailsById(orgCurrencyLinkId);

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/organization/currency/organization/{orgId}")
	public ResponseEntity<?> getOrganizationCurrencyByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<OrganizationCurrencyLinkMapper> organizationCurrencyLinkMapper = organizationService
					.getOrganizationCurrencyByOrgId(orgId);

			return new ResponseEntity<>(organizationCurrencyLinkMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/organization/currency/{orgCurrencyLinkId}")
	public ResponseEntity<?> deleteOrganizationCurrency(@PathVariable("orgCurrencyLinkId") String orgCurrencyLinkId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			organizationService.deleteOrgCurrencyById(orgCurrencyLinkId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/organization/currency/update/{orgCurrencyLinkId}")
	public ResponseEntity<?> upateOrgCurrency(@PathVariable("orgCurrencyLinkId") String orgCurrencyLinkId,
			@RequestBody OrganizationCurrencyLinkMapper organizationCurrencyLinkMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			organizationCurrencyLinkMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			organizationCurrencyLinkMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			OrganizationCurrencyLinkMapper mapper = organizationService.upateOrgCurrency(orgCurrencyLinkId,
					organizationCurrencyLinkMapper);

			return new ResponseEntity<OrganizationCurrencyLinkMapper>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/organization/save")
	public ResponseEntity<?> saveOrganization(@RequestBody OrganizationMapper organizationMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			organizationMapper.setCreatorId(jwtTokenUtil.getUserIdFromToken(authToken));
			OrganizationMapper organizationMapperNew = organizationService
					.saveToOrganizationProcess(organizationMapper);
			return new ResponseEntity<OrganizationMapper>(organizationMapperNew, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/organization/get/all/organization")
	public ResponseEntity<?> getListOfOrganization(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<OrganizationMapper> organizationMapper = organizationService.getListOfOrganization();
			if (null != organizationMapper && !organizationMapper.isEmpty()) {
				organizationMapper.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(organizationMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(organizationMapper, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/organization/update/{orgId}")
	public ResponseEntity<?> upateOrganizationDetailsByOrgId(@PathVariable("orgId") String orgId,
			@RequestBody OrganizationMapper organizationMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			OrganizationMapper mapper = organizationService.upateOrganizationDetailsByOrgId(orgId, organizationMapper);

			return new ResponseEntity<OrganizationMapper>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/organization/delete/{orgId}")
	public ResponseEntity<?> deleteOrganizationDetailsByOrgId(@PathVariable("orgId") String orgId,
			@RequestBody OrganizationMapper organizationMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String mapper = organizationService.deleteOrganizationDetailsByOrgId(orgId, organizationMapper);

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/organization/get/{orgId}")
	public ResponseEntity<?> getOrganizationByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			OrganizationMapper organizationMapper = organizationService.getOrganizationDetails(orgId);

			return new ResponseEntity<>(organizationMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/organization/document/{organizationDocumentLinkId}")
	public ResponseEntity<?> upateOrganisationDocument(
			@PathVariable("organizationDocumentLinkId") String organizationDocumentLinkId,
			@RequestBody OrganizationDocumentLinkMapper organizationDocumentLinkMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			organizationDocumentLinkMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			organizationDocumentLinkMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			OrganizationDocumentLinkMapper mapper = organizationService
					.upateOrganisationDocument(organizationDocumentLinkMapper, organizationDocumentLinkId);

			return new ResponseEntity<OrganizationDocumentLinkMapper>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/organization/industry")
	public ResponseEntity<?> saveAndUpdateOrganizationIndustry(@RequestBody OrgIndustryMapper mapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			OrgIndustryMapper result = organizationService.saveAndUpdateOrganizationIndustry(mapper, userId, orgId);
			return new ResponseEntity<OrgIndustryMapper>(result, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/organization/industry/{orgId}")
	public ResponseEntity<?> getOrganizationIndustryByOrgId(@PathVariable String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			OrgIndustryMapper response = organizationService.getOrganizationIndustryByOrgId(orgId);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/organization/subscription")
	public ResponseEntity<?> upateOrganizationSubscriptionByOrgId(
			@RequestBody OrganizationSubscriptionMapper subscription,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			// String authToken = authorization.replace(TOKEN_PREFIX, "");
			OrganizationSubscriptionMapper mapper = organizationService
					.upateOrganizationSubscriptionByOrgId(subscription);

			return new ResponseEntity<OrganizationSubscriptionMapper>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/organization/subscription/{orgId}")
	public ResponseEntity<?> getOrganizationSubscriptionByOrgId(@PathVariable String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			OrganizationSubscriptionMapper response = organizationService.getOrganizationSubscriptionByOrgId(orgId);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/organization/invoiceToUser/{orgId}")
	public ResponseEntity<?> getInvoiceToUserByOrgId(@PathVariable String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			InvoiceToUserMapper response = organizationService.getInvoiceToUserByOrgId(orgId);
			if (null != response) {
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invoice to user not found for org");
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/organization/create/invoiceToUser")
	public ResponseEntity<?> createInvoiceToUser(@PathVariable String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request,
			@RequestBody InvoiceToUserMapper mapper) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			InvoiceToUserMapper response = organizationService.createInvoiceToUser(mapper);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/organization/payment")
	public ResponseEntity<?> createOrgPaymentByOrgIdAndPaymentGateway(
			@RequestBody OrganizationPaymentMapper payment,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			OrganizationPaymentMapper mapper = organizationService
					.createOrgPaymentByOrgIdAndPaymentGateway(payment);

			return new ResponseEntity<OrganizationPaymentMapper>(mapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/organization/payment/{orgId}")
	public ResponseEntity<?> getOrgPaymentByOrgId(@PathVariable String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<OrganizationPaymentMapper> response = organizationService.getPaymentGatewayListByOrgId(orgId);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
