package com.app.employeePortal.partner.entity;

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
@Table(name="partner_candidate_link")

public class PartnerCandidateLink {
	
	@Id
	@GenericGenerator(name = "partner_candidate_link_id", strategy = "com.app.employeePortal.partner.generator.PartnerCandidateLinkGenerator")
    @GeneratedValue(generator = "partner_candidate_link_id")
	
	@Column(name="partner_candidate_link_id")
	private String id;

	@Column(name="partner_id")
	private String partnerId;
	
	@Column(name="candidate_id")
	private String candidateId;
	
	@Column(name="creation_date")
	private Date creationDate;

	

}
