package com.app.employeePortal.candidate.entity;

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
@Table(name = "candidate_address_link")
public class CandidateAddressLink {

	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.candidate.generator.CandidateAddressLinkGenerator")
    @GeneratedValue(generator = "id")
	
	@Column(name="candidate_address_link_id")
	private String id;

	@Column(name="candidate_id")
	private String candidateId;
	
	@Column(name="address_id")
	private String addressId;
	
	@Column(name="creation_date")
	private Date creationDate;


}
