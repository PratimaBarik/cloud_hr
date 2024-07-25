package com.app.employeePortal.leads.entity;

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
@Table(name="leads_innitiative_link")
public class LeadsInnitiativeLink {
	@Id
	@GenericGenerator(name = "leads_innitiative_link_id", strategy ="com.app.employeePortal.leads.generator.LeadsInnitiativeLinkGenerator")
	@GeneratedValue(generator = "leads_innitiative_link_id")
	
	@Column(name="leads_innitiative_link_id")
	private String id;
	
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
