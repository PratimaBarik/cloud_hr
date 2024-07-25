package com.app.employeePortal.processApproval.mapper;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProcessApprovalMapper {
	
	private String approvalLevelId;
	 
	private String reportingTo;
	 
	private String reportingTo2;
	
	private String reportingTo3;
		
	private String reportingTo4;
		
	private String reportingTo5;
		
	private int levelCount;
	 
	 private String approvalType;
	 
	 private String userId;
	 
	private boolean approvalIndicator;

	private String departmentId;

	private String roleTypeId;

	private int jobLevel;
	
//	private String processId;
	
	private String subProcessName;
	
	private boolean approvalInd;

//	private int approveInd;

//	private String level1FunctionId;
//		 
//	private String level2FunctionId;
//	
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
