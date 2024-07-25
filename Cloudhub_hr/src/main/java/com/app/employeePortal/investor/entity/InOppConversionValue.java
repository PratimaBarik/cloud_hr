package com.app.employeePortal.investor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name="in_opp_conversion_value")
public class InOppConversionValue {
    @Id
    @GenericGenerator(name = "in_opp_conversion_value_id", strategy = "com.app.employeePortal.investor.generator.InOppConversionValueGenerator")
    @GeneratedValue(generator = "in_opp_conversion_value_id")

    @Column(name="in_opp_conversion_value_id")
    private String inOppConversionValueId;

    @Column(name="user_conversion_value")
    private double userConversionValue;
    
    @Column(name="user_conversion_weighted_value")
    private double userConversionWeightedValue;
    
    @Column(name="user_conversion_currency")
    private String userConversionCurrency;
    
    @Column(name="org_conversion_currency")
    private String orgConversionCurrency;
    
    @Column(name="org_conversion_value")
    private double orgConversionValue;
    
    @Column(name="org_conversion_weighted_value")
    private double orgConversionWeightedValue;

    private String investorId;

    @Column(name="user_id")
    private String userId;

    @Column(name="org_id")
    private String orgId;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "live_ind")
    private boolean liveInd;

}
