package com.app.employeePortal.organization.service;

import java.io.IOException;
import java.util.List;

import com.app.employeePortal.organization.mapper.*;
import com.app.employeePortal.registration.mapper.NewAdminRegisterMapper;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public interface OrganizationService {
	public OrganizationMapper saveToOrganizationProcess(OrganizationMapper organizationMapper);
	
	public OrganizationMapper getOrganizationDetails(String organizationId);
	
	public OrganizationMapper updateOrganizationDetails(OrganizationMapper organizationMapper);
	
	public FiscalMapper getFiscalMapperByOrgId(String organizationId);

	public OrganizationMapper upateOrganizationByOrgId(String orgId, OrganizationMapper organizationMapper);

	public OrganizationDocumentLinkMapper SaveOrgDocument(OrganizationDocumentLinkMapper organizationDocumentLinkMapper);
	
	public OrganizationDocumentLinkMapper getOrganizationDocumentLinkDetailsById(String organizationDocumentLinkId);

	public List<OrganizationDocumentLinkMapper> getOrganizationDocumentByOrgId(String orgId);

	public void deleteDocumentsById(String organizationDocumentLinkId);

//	public OrganizationDocumentLinkMapper upatePublishInd(
//			OrganizationDocumentLinkMapper organizationDocumentLinkMapper);
//
//	public OrganizationDocumentLinkMapper upatePublicInd(OrganizationDocumentLinkMapper organizationDocumentLinkMapper);

	public OrganizationCurrencyLinkMapper SaveOrgCurrency(OrganizationCurrencyLinkMapper organizationCurrencyLinkMapper);

	public OrganizationCurrencyLinkMapper getOrganizationCurrencyLinkDetailsById(String orgCurrencyLinkId);

	public List<OrganizationCurrencyLinkMapper> getOrganizationCurrencyByOrgId(String orgId);

	public void deleteOrgCurrencyById(String orgCurrencyLinkId);

	public OrganizationCurrencyLinkMapper upateOrgCurrency(String orgCurrencyLinkId,
			OrganizationCurrencyLinkMapper organizationCurrencyLinkMapper);

	String saveToOrganizationProcess(NewAdminRegisterMapper organizationMapper);

	public List<OrganizationMapper> getListOfOrganization();

	public OrganizationMapper upateOrganizationDetailsByOrgId(String orgId, OrganizationMapper organizationMapper);

	public String deleteOrganizationDetailsByOrgId(String orgId, OrganizationMapper organizationMapper);

	public List<OrganizationDocumentLinkMapper> getOrganizationDocumentByUserId(String userId);

	public OrganizationDocumentLinkMapper upateOrganisationDocument(
			OrganizationDocumentLinkMapper organizationDocumentLinkMapper, String organizationDocumentLinkId);

	public OrgIndustryMapper saveAndUpdateOrganizationIndustry(OrgIndustryMapper mapper, String userId, String orgId);

	public OrgIndustryMapper getOrganizationIndustryByOrgId(String orgId);

	public OrganizationSubscriptionMapper upateOrganizationSubscriptionByOrgId(
			OrganizationSubscriptionMapper subscription);

	public OrganizationSubscriptionMapper getOrganizationSubscriptionByOrgId(String orgId);

    InvoiceToUserMapper getInvoiceToUserByOrgId(String orgId);

	InvoiceToUserMapper createInvoiceToUser(InvoiceToUserMapper mapper);

	public OrganizationSubscriptionMapper getUserSubscriptionByOrgId(String userId) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, TemplateException, IOException;

	public OrganizationPaymentMapper createOrgPaymentByOrgIdAndPaymentGateway(OrganizationPaymentMapper payment);

	List<OrganizationPaymentMapper> getPaymentGatewayListByOrgId(String orgId);
}
