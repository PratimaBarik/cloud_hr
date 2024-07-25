package com.app.employeePortal.investor.mapper;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.app.employeePortal.Opportunity.mapper.OpportunityForecastLinkMapper;
import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import javax.persistence.Column;

@Data
public class InvestorOpportunityMapper {
    @JsonProperty("invOpportunityId")
    private String invOpportunityId;

    @JsonProperty("opportunityName")
    private String opportunityName;

    @JsonProperty("proposalAmount")
    private String proposalAmount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("contactId")
    private String contactId;

    @JsonProperty("investorId")
    private String investorId;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("orgId")
    private String orgId;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("investor")
    private String investor;

    @JsonProperty("recruiterId")
    private List<String> recruiterId;

    @JsonProperty("recruiterName")
    private String recruiterName;

    @JsonProperty("salesUserIds")
    //private List<String> salesUserIds;
    private String salesUserIds;

    @JsonProperty("description")
    private String description;

    @JsonProperty("opportunityIds")
    private List<String> opportunityIds;

//    @JsonProperty("opportunitySkill")
//    private List<OpportunitySkillLinkMapper> opportunitySkill;

//    @JsonProperty("oppInnitiative")
//    private String oppInnitiative;

    @JsonProperty("wonInd")
    private boolean wonInd;

    @JsonProperty("lostInd")
    private boolean lostInd;

    @JsonProperty("closeInd")
    private boolean closeInd;

    @JsonProperty("opportunityForecast")
    private List<OpportunityForecastLinkMapper> opportunityForecast;

    @JsonProperty("oppWorkflow")
    private String oppWorkflow;
    
    @JsonProperty("oppWorkflowId")
    private String oppWorkflowId;

    @JsonProperty("oppStage")
    private String oppStage;

    @JsonProperty("investorLeadsId")
    private String investorLeadsId;

    @JsonProperty("creationDate")
    private String creationDate;

    @JsonProperty("liveInd")
    private boolean liveInd;

    @JsonProperty("ownerName")
    private String ownerName;

    @JsonProperty("ownerImageId")
    private String ownerImageId;

    @JsonProperty("contactName")
    private String contactName;

    @JsonProperty("assignedTo")
    private String assignedTo;

    @JsonProperty("openRecruitment")
    private int openRecruitment;

    @JsonProperty("openPosition")
    private int openPosition;

    @JsonProperty("probability")
    private double probability;

    @JsonProperty("invOpportunityStagesId")
    private String invOpportunityStagesId;

    @JsonProperty("stageList")
    private List<InvestorOppStagesMapper> stageList;
    
    @JsonProperty("pageCount")
   	private int pageCount;
   	
   	@JsonProperty("dataCount")
   	private int dataCount;
   	
   	@JsonProperty("listCount")
   	private long listCount;
   	
   	@JsonProperty("included")
	private Set<String> included;
   	
   	@JsonProperty("include")
	private List<EmployeeShortMapper> include;
   	
	@JsonProperty("collectedAmount")
   	private float collectedAmount;
	
	@JsonProperty("source")
	private String source;
	
	@JsonProperty("sourceName")
	private String sourceName;
	
	@JsonProperty("newDealNo")
	private String newDealNo;

    @JsonProperty("paymentReceived")
    private double paymentReceived;

    @JsonProperty("paymentReceivedDate")
    private String paymentReceivedDate;

    @JsonProperty("wonDate")
    private String wonDate;

    @JsonProperty("lostDate")
    private String lostDate;

    @JsonProperty("closeDate")
    private String closeDate;
}