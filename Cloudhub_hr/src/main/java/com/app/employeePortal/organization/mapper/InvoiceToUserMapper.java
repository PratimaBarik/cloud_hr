package com.app.employeePortal.organization.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceToUserMapper {
    private String orgId;
    private String org;
    private String userId;
    private String user;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String updatedBy;
}
