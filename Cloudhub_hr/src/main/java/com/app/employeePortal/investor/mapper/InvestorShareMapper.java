package com.app.employeePortal.investor.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvestorShareMapper {
    @JsonProperty("investorsShareId")
    private String investorsShareId;

    @JsonProperty("quantityOfShare")
    private double quantityOfShare;
    
    @JsonProperty("allTotalQuantityOfShare")
    private double allTotalQuantityOfShare;

    @JsonProperty("amountPerShare")
    private double amountPerShare;
    
    @JsonProperty("totalAmountOfShare")
    private double totalAmountOfShare;
    
    @JsonProperty("allTotalAmountOfShare")
    private double allTotalAmountOfShare;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("buyingDate")
    private String buyingDate;

    @JsonProperty("creationDate")
    private String creationDate;

    @JsonProperty("investorId")
    private String investorId;

    @JsonProperty("userId")
    private String userId;
    
    @JsonProperty("orgId")
    private String orgId;

    @JsonProperty("documentId")
    private String documentId;

}
