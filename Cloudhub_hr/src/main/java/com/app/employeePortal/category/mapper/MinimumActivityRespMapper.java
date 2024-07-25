package com.app.employeePortal.category.mapper;

import lombok.Data;
@Data
public class MinimumActivityRespMapper {
	
    private String minimumActivityId;
    private double callActivity;
    private double eventActivity;
    private double taskActivity;
    private String creationDate;
    private String updationDate;
    private String updatedBy;
    private String orgId;
    private String userId;
    
}
