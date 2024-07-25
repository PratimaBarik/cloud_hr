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
@Table(name="opportunity_sales_user_link")
public class OpportunitySalesUserLink {
	
	@Id
	@GenericGenerator(name = "opportunity_sales_user_link_id",strategy="com.app.employeePortal.Opportunity.generator.OpportunitySalesUserLinkGenerator")
	@GeneratedValue(generator = "opportunity_sales_user_link_id")
	
	@Column(name="opportunity_sales_user_link_id")
	private String opportunity_sales_user_link_id;
	
	@Column(name="opportunity_id")
	private String opportunity_id ;
	
	@Column(name="employee_id")
	private String employee_id;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean live_ind;

	
	
	

}
