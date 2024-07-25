package com.app.employeePortal.processApproval.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProcessApprovalViewMapper {
	private String approvalLevelId;

	private String subProcessApprovalId;
	
	private String subProcessName;

	private String approvalType;

	private String roleTypeId;

	private String roleType;

	private String departmentId;

	private String departmentName;

	private boolean approvalIndicator;

	private int approveInd;

	private String level1;

	private String level2;

	private String level3;

	private String level4;

	private String level5;
	
	private float threshold1;
	 
	 private float threshold2;
	 
	 private float threshold3;
	 
	 private float threshold4;
	 
	 private float threshold5;
	 
	 private List<ApprovalLevelMapper> level;

}
