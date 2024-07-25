package com.app.employeePortal.recruitment.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruitmentCloseRuleMapper {

	@JsonProperty("recruitmentCloseRuleId")
	private String recruitmentCloseRuleId;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("timePeriod")
	private int timePeriod;

	@JsonProperty("oppTimePeriod")
	private int oppTimePeriod;

	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("ownerName")
	private String ownerName;

	@JsonProperty("orderTimePeriod")
	private int orderTimePeriod;

	@JsonProperty("inspectionRequiredInd")
	private boolean inspectionRequiredInd;

	@JsonProperty("jobAniEmailInd")
	private boolean jobAniEmailInd;

	@JsonProperty("birthdayEmailInd")
	private boolean birthdayEmailInd;

//        @JsonProperty("productionInd")
//        private boolean productionInd;

//        @JsonProperty("repairInd")
//        private boolean repairInd;

	@JsonProperty("makeToInd")
	private boolean makeToInd;

	@JsonProperty("independentInd")
	private boolean independentInd;

	@JsonProperty("partNoInd")
	private boolean partNoInd;

	@JsonProperty("trnsfrToErpQtionWinInd")
	private boolean trnsfrToErpQtionWinInd;

	@JsonProperty("trnsfrEvthngToErpInd")
	private boolean trnsfrEvthngToErpInd;

	@JsonProperty("processInd")
	private boolean processInd;

	@JsonProperty("typeInd")
	private boolean typeInd;

	@JsonProperty("fifoInd")
	private boolean fifoInd;

	@JsonProperty("proInd")
	private boolean proInd;

	@JsonProperty("repairOrdInd")
	private boolean repairOrdInd;
	
	@JsonProperty("repairProcessInd")
	private boolean repairProcessInd;
	
	@JsonProperty("qcInd")
	private boolean qcInd;
}
