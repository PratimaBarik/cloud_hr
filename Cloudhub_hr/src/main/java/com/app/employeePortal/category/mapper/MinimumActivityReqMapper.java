package com.app.employeePortal.category.mapper;

import lombok.Data;

import java.util.Date;
@Data
public class MinimumActivityReqMapper {
    private String minimumActivityId;
    private double callActivity;
    private double eventActivity;
    private double taskActivity;
    private Date creationDate;
    private Date updationDate;
    private String orgId;
    private String userId;
}
