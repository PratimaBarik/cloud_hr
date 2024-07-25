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
@Table(name="opportunity_order_link")
public class OpportunityOrderLink {
	@Id
	@GenericGenerator(name = "opportunity_order_link_id", strategy = "com.app.employeePortal.Opportunity.generator.OpportunityOrderLinkGenerator")
	@GeneratedValue(generator = "opportunity_order_link_id")
	
	@Column(name="opportunity_order_link_id")
	private String opportunityOrderLinkId;
	
	@Column(name="opportunityId")
	private String opportunityId;
	
	@Column(name="orderId")
	private String orderId;
	
	@Column(name="creationDate")
	private Date creationDate;

	@Column(name="userId")
	private String userId;
	
	@Column(name="orgId")
	private String orgId;
	
	@Column(name="liveInd")
	private boolean liveInd;

	@Column(name="updationDate")
	private Date updationDate;
	
	@Column(name="updatedBy")
	private String updatedBy;

}
