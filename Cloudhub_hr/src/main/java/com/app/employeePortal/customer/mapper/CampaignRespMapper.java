package com.app.employeePortal.customer.mapper;

import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CampaignRespMapper {

    @JsonProperty("campaignId")
    private String campaignId;

    @JsonProperty("budgetValue")
    private double budgetValue;

    @JsonProperty("eventId")
    private String eventId;

    @JsonProperty("eventType")
    private String eventType;

    @JsonProperty("eventSubject")
    private String eventSubject;

    @JsonProperty("eventDescription")
    private String eventDescription;

    @JsonProperty("status")
    private String status;

    @JsonProperty("timeZone")
    private String timeZone;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("startTime")
    private long startTime;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("endTime")
    private long endTime;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("organizationId")
    private String organizationId;

    @JsonProperty("assignedTo")
    private String assignedTo;

    @JsonProperty("assignedToName")
    private String assignedToName;

    @JsonProperty("included")
    private List<EmployeeShortMapper> included;

    @JsonProperty("woner")
    private String woner;
    
    @JsonProperty("updateBy")
    private String updateBy;

//	@JsonProperty("ownerIds")
//	private List<String> ownerIds;

    @JsonProperty("creationDate")
    private String creationDate;

    @JsonProperty("eventTypeId")
    private String eventTypeId;

    @JsonProperty("EditInd")
    private boolean EditInd;

    @JsonProperty("completionInd")
    private boolean completionInd;

    @JsonProperty("rating")
    private float rating;

    @JsonProperty("updateDate")
    private String updateDate;



    @JsonProperty("liveInd")
    private boolean liveInd;
}
