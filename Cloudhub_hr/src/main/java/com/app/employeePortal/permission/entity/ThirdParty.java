package com.app.employeePortal.permission.entity;

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
@Table(name = "third_party")


public class ThirdParty {
	
	@Id
	@GenericGenerator(name = "third_party_id", strategy = "com.app.employeePortal.permission.generator.ThirdPartyGenerator")
    @GeneratedValue(generator = "third_party_id")
	
	@Column(name="third_party_id")
	private String thirdPartyId;
	
	@Column(name="customer_contact_ind")
	private boolean customerContactInd;
	
	@Column(name="partner_contact_ind")
	private boolean partnerContactInd;
	
	@Column(name="monitize_ind")
	private boolean monitizeInd;
	
	@Column(name="customer_ai_ind", nullable = false)
	private boolean customerAiInd=false;
	
	@Column(name="partner_ai_ind", nullable = false)
	private boolean partnerAiInd=false;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="last_updated_on")
	private Date lastUpdatedOn;

	@Column(name="allow_prf_with_vendor_ind")
	private boolean allowPrfWithVendorInd;
	
	@Column(name="enable_hiring_team_ind")
	private boolean enableHiringTeamInd;
	
}
