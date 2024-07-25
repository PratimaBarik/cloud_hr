package com.app.employeePortal.Opportunity.mapper;

import java.util.List;

import com.app.employeePortal.customer.mapper.CustomerViewMapper;
import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.app.employeePortal.opportunityWorkflow.mapper.OpportunityStagesMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OpportunityReportMapper {
	
	@JsonProperty("opportunityId")
	private String opportunityId;
	
	@JsonProperty("opportunityName")
	private String opportunityName;
	
	@JsonProperty("proposalAmount")
	private String proposalAmount;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("contactId")
	private String contactId;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("startDate")
	private String startDate;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("customer")
	private String customer;
	
	@JsonProperty("recruiterDetails")
	private List<RecruiterMapper> recruiterDetails;
	 	
	@JsonProperty("recruiterName")
	private String recruiterName;
	
	
	@JsonProperty("Customer")
	private CustomerViewMapper customerViewMapper;

	@JsonProperty("ownerName")
	private String ownerName;
	
	@JsonProperty("ownerImageId")
	private String ownerImageId;
	
	@JsonProperty("contactName")
	private String contactName;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("assignedTo")
	private String assignedTo;
	
	@JsonProperty("salesUserIds")
	private String salesUserIds;
	
	@JsonProperty("jobOrder")
	private String jobOrder;
	
//	@JsonProperty("opportunitySkill")
//    private List<OpportunitySkillLinkMapper> opportunitySkill;
    
	@JsonProperty("oppInnitiative")
    private String oppInnitiative;
	
	@JsonProperty("wonInd")
    private boolean wonInd;
	
	@JsonProperty("lostInd")
    private boolean lostInd;
	
	@JsonProperty("closeInd")
    private boolean closeInd;
	
	@JsonProperty("oppWorkflow")
	private String oppWorkflow;
	
	@JsonProperty("oppStage")
	private String oppStage;
	
	@JsonProperty("probability")
	private double probability;
	
	@JsonProperty("stageList")
	private List<OpportunityStagesMapper> stageList;

	@JsonProperty("openRecruitment")
	private int openRecruitment;
	
	@JsonProperty("openPosition")
	private int openPosition;
	
	@JsonProperty("opportunityStagesId")
	private String opportunityStagesId;
	
	@JsonProperty("pageCount")
	private int pageCount;
	
	@JsonProperty("dataCount")
	private int dataCount;
	
	@JsonProperty("listCount")
	private long listCount;
	
	@JsonProperty("included")
	private List<EmployeeShortMapper> included;
}
