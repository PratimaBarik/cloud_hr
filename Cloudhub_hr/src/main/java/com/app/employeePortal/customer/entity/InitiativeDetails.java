package com.app.employeePortal.customer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "initiative_details")

public class InitiativeDetails {
	
	@Id
	@GenericGenerator(name = "initiative_details_id", strategy = "com.app.employeePortal.customer.generator.InitiativeDetailsGenerator")
    @GeneratedValue(generator = "initiative_details_id")
	
	@Column(name="initiative_details_id")
	private String initiativeDetailsId;
	
	@Column(name="initiative_name")
	private String initiativeName;
	
	@Column(name="customer_id")
	private String customerId;
	
	@Column(name = "user_id")
    private String userId;

    @Column(name = "org_id")
    private String orgId;

	@Column(name="leadsId")
	private String leadsId;
	
	@Lob
	@Column(name="description")
	private String description;
}



