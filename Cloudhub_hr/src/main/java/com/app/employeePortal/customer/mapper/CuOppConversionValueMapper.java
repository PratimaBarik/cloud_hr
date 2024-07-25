package com.app.employeePortal.customer.mapper;

import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CuOppConversionValueMapper {
    @JsonProperty("cuOppConversionValueId")
    private String cuOppConversionValueId;

    @JsonProperty("orgConversionCurrency")
    private String orgConversionCurrency;

    @JsonProperty("orgConversionValue")
    private String orgConversionValue;

    @JsonProperty("userConversionCurrency")
    private String userConversionCurrency;

    @JsonProperty("userConversionValue")
    private String userConversionValue;

    @JsonProperty("creationDate")
    private String creationDate;

    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("orgId")
    private String orgId;
    
    @JsonProperty("liveInd")
    private boolean liveInd;

}
