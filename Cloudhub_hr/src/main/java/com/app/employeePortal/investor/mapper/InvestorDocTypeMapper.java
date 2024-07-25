package com.app.employeePortal.investor.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestorDocTypeMapper {

    private String documentTypeId;

    private String investorId;

    private String orgId;

    private String userId;

    private boolean availableInd;
}
