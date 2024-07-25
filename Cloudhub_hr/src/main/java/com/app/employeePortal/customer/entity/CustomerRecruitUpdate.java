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
@Table(name = "customer_recruit_update")
public class CustomerRecruitUpdate {

	@Id
	@GenericGenerator(name = "customer_recruit_update_id", strategy = "com.app.employeePortal.customer.generator.CustomerRecruitGenerator")
    @GeneratedValue(generator = "customer_recruit_update_id")
	
	@Column(name="id")
	private String id;
	
	@Column(name="customer_id")
	private String customerId;
	
	@Column(name="recruitment_id")
	private String recruitmentId;
	
	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name="contact_id")
	private String contactId;
	
	@Column(name = "contact_updated_on")
	private Date contactUpdatedOn;
}
