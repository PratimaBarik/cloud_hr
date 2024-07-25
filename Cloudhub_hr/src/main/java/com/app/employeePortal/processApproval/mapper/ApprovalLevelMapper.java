package com.app.employeePortal.processApproval.mapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApprovalLevelMapper {
	private String level;
	
	private String roleType;
	
	private float threshold;

}
