package com.app.employeePortal.customer.entity;

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
@Table(name="customer_initiative_link")

public class CustomerInitiativeLink {
	
	@Id
	@GenericGenerator(name = "customer_initiative_link_id", strategy ="com.app.employeePortal.customer.generator.CustomerInitiativeLinkGenerator")	
	@GeneratedValue(generator = "customer_initiative_link_id")
	
	@Column(name = "customer_initiative_link_id")
	private String customerInitiativeLinkId;	
	
	@Column(name = "skil_id")
	private String skilId;

	@Column(name = "initiative_details_id")
	private String initiativeDetailsId;
	
	@Column(name = "user_id")
    private String userId;

	@Column(name = "org_id")
    private String orgId;
	
	@Column(name = "live_ind")
    private boolean liveInd;

}
