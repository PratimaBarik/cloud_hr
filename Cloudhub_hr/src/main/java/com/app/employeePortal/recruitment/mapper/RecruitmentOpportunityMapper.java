package com.app.employeePortal.recruitment.mapper;

import java.util.List;
import java.util.Set;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.candidate.mapper.CandidateMapper;
import com.app.employeePortal.employee.mapper.EmployeeMapper;
import com.app.employeePortal.partner.mapper.PartnerMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class RecruitmentOpportunityMapper {
	
	@JsonProperty("recruitmentProcessId")
	private String recruitmentProcessId;
	
	@JsonProperty("processName")
	private String processName;
	
	@JsonProperty("stageName")
	private String stageName;
		
	@JsonProperty("stageId")
	private String stageId;
	
	@JsonProperty("profileId")
	private String profileId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("opportunityId")
	private String opportunityId;
	
	@JsonProperty("sponserId")
	private String sponserId;
	
	@JsonProperty("number")
	private long number;
	
	@JsonProperty("sponserName")
	private String sponserName;
	
	@JsonProperty("description")
	private String description;
		
	@JsonProperty("recruitmentId")
	private String recruitmentId;  
			
	@JsonProperty("requirementName")
	private String requirementName;
	
	@JsonProperty("contactId")
	private String contactId;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("customerName")
	private String customerName;
	
	@JsonProperty("candidateId")
	private String candidateId;
	
	@JsonProperty("candidateOfferDate")
	private String candidateOfferDate;
	
	@JsonProperty("candidateOfferAccept")
	private String candidateOfferAccept;
	
	@JsonProperty("sponserOfferDate")
	private String sponserOfferDate;
	
	@JsonProperty("sponserOfferAccept")
	private String sponserOfferAccept;
	
	@JsonProperty("candidateName")
	private String candidateName;
	
	@JsonProperty("categoryList")
	private List<String> categoryList;
	
	@JsonProperty("skillSetList")
	private List<String> skillSetList;
	
	 @JsonProperty("candidatetList")
	 private List<CandidateMapper> candidatetList;
	
	@JsonProperty("skillName")
	private String skillName;
	
	@JsonProperty("type")
	private String type;

	
	@JsonProperty("stageList")
	private List<RecruitmentStageMapper> stageList;

	@JsonProperty("category")
	private String category;
	
	/*
	 * @JsonProperty("conatactList") private List<ContactMapper> conatactList;
	 */
	
	@JsonProperty("approveInd")
	private boolean approveInd;
	
	@JsonProperty("stageInd")
	private boolean stageInd;
	
	
	@JsonProperty("rejectInd")
	private boolean rejectInd;
	
	
	@JsonProperty("avilableDate")
	private String avilableDate;
	
	@JsonProperty("billing")
	private double billing;
	
	@JsonProperty("tagUserId")
	private String tagUserId;
	
	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("reviewer")
	private String reviewer;
	
	/*
	 * @JsonProperty("stageData") private List<StageData> stageData;
	 */
	
	@JsonProperty("note")
	private String note;
	
	@JsonProperty("offered")
	private int offered;
	
	@JsonProperty("rejected")
	private int rejected;
	
	@JsonProperty("candidateInd")
	private boolean candidateInd;
	
	
	@JsonProperty("sponserInd")
	private boolean sponserInd;
	
	@JsonProperty("openedPosition")
	private int openedPosition;
	
	@JsonProperty("closedPosition")
	private int closedPosition;

	
	@JsonProperty("accountName")
	private String accountName;
	
	@JsonProperty("opprtunityName")
	private String opprtunityName;
	
	@JsonProperty("recruiterId")
	private String recruiterId;
	
	@JsonProperty("recruiterName")
	private String recruiterName;
	
	@JsonProperty("endDate")
	private String endDate;
	
	@JsonProperty("publishInd")
	private boolean publishInd;
	
	@JsonProperty("openInd")
	private boolean openInd;

	@JsonProperty("candidateBilling")
	private String candidateBilling;
	
	@JsonProperty("ownerName")
	private String ownerName;

	@JsonProperty("jobOrder")
	private String jobOrder;
	
	@JsonProperty("experience")
	private String experience;
	
	@JsonProperty("location")
	private String location;
	
	@JsonProperty("partnerId")
	private List<String> partnerId;
	
	@JsonProperty("recruitersId")
	private List<String> recruitersId;
	
	@JsonProperty("recruiterList")
	private List<EmployeeMapper> recruiterList;
	
	@JsonProperty("recruitOwner")
	private String recruitOwner;
	
	@JsonProperty("recruiterNames")
	private List<String> recruiterNames;
	
	@JsonProperty("partnerNames")
	private List<String> partnerNames;

	@JsonProperty("partnerList")
	private List<PartnerMapper> partnerList;
	
	@JsonProperty("onboardInd")
	private boolean onboardInd;
	
	@JsonProperty("onboardDate")
	private String onboardDate;

	@JsonProperty("country")
	private String country;
	
	@JsonProperty("department")
	private String department;
	
	@JsonProperty("role")
	private String role;

	@JsonProperty("candidateIds")
	private List<String> candidateIds;
	
	@JsonProperty("candidateNo")
	private String candidateNo;
	
	@JsonProperty("workPreference")
	private String workPreference;
	
	@JsonProperty("recruitment_stage_note_id")
	private String recruitmentStageNoteId;
	
	@JsonProperty("updatedOn")
	private String updatedOn;

	@JsonProperty("onBoardNo")
	private int onBoardNo;

	@JsonProperty("filtercandidatetList")
	private List<CandidateMapper> filtercandidatetList;
	 
	@JsonProperty("emailInd")
	private boolean emailInd;
	 
	@JsonProperty("intrestInd")
	private boolean intrestInd;

	@JsonProperty("pingInd")
	private boolean pingInd;
	
	@JsonProperty("closeInd")
	private boolean closeInd;
	
	@JsonProperty("address")
	private List<AddressMapper> address;
	
	@JsonProperty("closeByDate")
	private String closeByDate;
	
	@JsonProperty("score")
	private int score;
	
	@JsonProperty("emailId")
	private String emailId;
	
	@JsonProperty("orgName")
	private String orgName;

	@JsonProperty("internalCandiNo")
	private int internalCandiNo;
	
	@JsonProperty("websiteCandiNo")
	private int websiteCandiNo;

	@JsonProperty("result")
	private String result;
	
	@JsonProperty("feedBackScore")
	private int feedBackScore;
	
	@JsonProperty("actualEndDate")
	private String actualEndDate;
	
	@JsonProperty("finalBilling")
	private float finalBilling;
	
	@JsonProperty("billableHour")
	private float billableHour;
	
	@JsonProperty("onboardCurrency")
	private String onboardCurrency;
	
	@JsonProperty("projectName")
	private String projectName;
	
	@JsonProperty("documentSetList")
	private Set<String> documentSetList;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("workType")
	private String workType;
	
	@JsonProperty("workDays")
	private String workDays;
}
