
package com.app.employeePortal.candidate.entity;

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
@Table(name = "candidate_email_details")
public class CandidateEmailDetails {

	@Id
	@GenericGenerator(name = "candidate_email_details_id", strategy = "com.app.employeePortal.candidate.generator.CandidateEmailDetailsGenerator")
    @GeneratedValue(generator = "candidate_email_details_id")
	
	@Column(name="candidate_email_details_id")
	private String id;
	
	@Column(name="_to")
	private String to;
	
	@Column(name="cc")
	private String cc;
	
	@Column(name="bcc")
	private String bcc;
	
	@Column(name="message")
	private String message;
	
	@Column(name="subject")
	private String subject;
	
	@Column(name="organization_id")
	private String organizationId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="available_date_ind")
	private boolean availableDateInd;
	
	@Column(name="email_ind")
	private boolean emailInd;
	
	@Column(name="mobile_ind")
	private boolean mobileInd;
	
	@Column(name="name_ind")
	private boolean nameInd;
	
	@Column(name="role_ind")
	private boolean roleInd;
	
	@Column(name="skills_ind")
	private boolean skillsInd;
	
	@Column(name="customer1")
	private String customer1;
	
	@Column(name="customer2")
	private String customer2;
	
	@Column(name="customer3")
	private String customer3;
	
	@Column(name="contact1")
	private String contact1;
	
	@Column(name="contact2")
	private String contact2;
	
	@Column(name="contact3")
	private String contact3;
	
}