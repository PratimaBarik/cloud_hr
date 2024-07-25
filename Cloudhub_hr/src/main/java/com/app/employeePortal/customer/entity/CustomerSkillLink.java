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
@Table(name="customer_skill_link")
public class CustomerSkillLink {
	@Id
	@GenericGenerator(name = "customer_skill_link_id", strategy = "com.app.employeePortal.customer.generator.CustomerSkillLinkGenerator")
	@GeneratedValue(generator = "customer_skill_link_id")
	
	@Column(name = "customer_skill_link_id")
	private String customerSkillLinkId;
	
	@Column(name = "skill_name")
	private String skillName;
	
	@Column(name="customer_id")
	private String customerId;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name="edit_ind")
	private boolean editInd;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
    private String orgId;
}
