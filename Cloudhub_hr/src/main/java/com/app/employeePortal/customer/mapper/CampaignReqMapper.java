package com.app.employeePortal.customer.mapper;


import lombok.Data;

@Data
public class CampaignReqMapper {

    private String campaignId;
    private String customerId;
    private String eventId;
    private double budgetValue;
    private String orgId;
    private String userId;
}
