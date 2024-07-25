package com.app.employeePortal.Opportunity.mapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhoneDetailsDTO {

	private String phoneId;

	private String company;

	private String model;

	private String orderPhoneId;

	private String excelId;

	private String userId;

	private String distributorId;

	private String IMEI;

	private String qcStatus;

	private String repairStatus;

	private String task1;

	private boolean task1Ind;

	private boolean task1CompleteInd;

	private String task2;

	private boolean task2Ind;

	private boolean task2CompleteInd;

	private String task3;

	private boolean task3Ind;

	private boolean task3CompleteInd;

	private boolean receivePhoneInd;

	private String OS;

	private String GB;

	private String color;

	private String conditions;

	private String receiveCompany;

	private String receiveModel;

	private String receiveIMEI;

	private String receiveOS;

	private String receiveGB;

	private String receiveColor;

	private String receiveCondition;

	private boolean mismatchInd;

	private boolean dispatchPhoneInd;

	private String dispatchCompany;

	private String dispatchModel;

	private String dispatchIMEI;

	private String dispatchOS;

	private String dispatchGB;

	private String dispatchColor;

	private String dispatchCondition;

	private boolean dispatchMismatchInd;

	private String receivePhoneUser;

	private String receivePhoneDate;

	private String repairTechnicianId;

	private String dispatchPhoneUser;

	private String dispatchPhoneDate;

	private int qcInspectionInd;

	private String productionDispatchId;
	
	private int repairInspectionInd;
	
	private String productionRepairDispatchId;
	
	private boolean repairPauseInd;
	
	private boolean repairResumeInd;
	
	private String customerId;
	
	private String opportunityId;
}
