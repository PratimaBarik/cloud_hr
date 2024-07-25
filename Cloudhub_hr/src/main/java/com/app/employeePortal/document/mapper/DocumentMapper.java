package com.app.employeePortal.document.mapper;

import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonPropertyOrder({"documentId","documentName", "documetContentType","documentDescription", "documentSize", "documentData", "employeeId", "organizationId","creationDate","stageId","documentTitle","association"})
public class DocumentMapper {
	
	@JsonProperty("documentId")
	private String documentId;
	
	@JsonProperty("documentName")
	private String documentName;
	
	@JsonProperty("documentContentType")
	private String documentContentType;
	
	@JsonProperty("documentDescription")
	private String documentDescription;
	
	@JsonProperty("documentSize")
	private long documentSize;
	
	@JsonProperty("contactId")
	private String contactId;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("candidateId")
	private String candidateId;
	
	@JsonProperty("employeeId")
	private String employeeId;
	
	@JsonProperty("opportunityId")
	private String opportunityId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	
	@JsonProperty("documentTitle")
	private String documentTitle;
	
	@JsonProperty("partnerId")
	private String partnerId;
	
	
	@JsonProperty("documentTypeId")
	private String documentTypeId;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("uploadedBy")
	private String uploadedBy;
	
	@JsonProperty("customerInvoiceId")
	private String customerInvoiceId;

	@JsonProperty("shareInd")
    private boolean shareInd;
	
	@JsonProperty("latestInd")
    private boolean latestInd;
	
	@JsonProperty("leadsId")
	private String leadsId;

	@JsonProperty("taskId")
	private String taskId;

	@JsonProperty("investorLeadsId")
	private String investorLeadsId;

	@JsonProperty("invOpportunityId")
	private String invOpportunityId;
	
	@JsonProperty("investorId")
	private String investorId;
	
	@JsonProperty("contractInd")
	private boolean contractInd;
	
	@JsonProperty("fileName")
	private String fileName;
	
	@JsonProperty("included")
	private Set<String> included;

	@JsonProperty("roomId")
	private String roomId;

}
