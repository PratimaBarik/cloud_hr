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
import lombok.ToString;
@ToString
@Entity
@Getter
@Setter
@Table(name="opportunity_included_link")
public class OpportunityIncludedLink {
	
	@Id
	@GenericGenerator(name = "opportunity_included_link_id",strategy="com.app.employeePortal.Opportunity.generator.OpportunityIncludedGenerator")
	@GeneratedValue(generator = "opportunity_included_link_id")
	
	@Column(name="opportunity_included_link_id")
	private String opportunityIncludedLinkId;
	
	@Column(name="opportunity_id")
	private String opportunityId ;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;

	
	
	

}
