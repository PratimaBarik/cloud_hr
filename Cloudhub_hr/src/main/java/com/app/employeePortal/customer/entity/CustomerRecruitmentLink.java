package com.app.employeePortal.customer.entity;

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
@Table(name="customer_recruitment_link")
public class CustomerRecruitmentLink {
	
	@Id
	@GenericGenerator(name = "customer_recruitment_link_id", strategy ="com.app.employeePortal.customer.generator.CustomerRecruitmentLinkGenerator")	
	@GeneratedValue(generator = "customer_recruitment_link_id")
	
	@Column(name = "customer_recruitment_link_id")
	private String id;	
	
	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "recruitment_id")
	private String recruitmentId;
	
	@Column(name = "recruiter_id")
	private String recruiterId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;

}
