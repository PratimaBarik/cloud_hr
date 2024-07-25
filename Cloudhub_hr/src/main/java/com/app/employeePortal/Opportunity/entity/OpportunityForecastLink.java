package com.app.employeePortal.Opportunity.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="opportunity_forecast_link")
public class OpportunityForecastLink{
    @Id
    @GenericGenerator(name = "opportunity_forecast_link_id", strategy = "com.app.employeePortal.Opportunity.generator.OpportunityForecastLinkGenerator")
    @GeneratedValue(generator = "opportunity_forecast_link_id")
    
    @Column(name="opportunity_forecast_link_id")
    private String opportunityForecastLinkId;
    
    @Column(name="opportunity_id")
    private String opportunityId;

    @Column(name="skill")
    private String skill;

    @Column(name="no_of_position")
    private String noOfPosition;
    
    @Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="month")
	private String month;
	
	@Column(name="year")
	private String year;

}
