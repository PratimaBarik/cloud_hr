package com.app.employeePortal.recruitment.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Entity
@Getter
@Setter
@Table(name="recruitment_recruiter_link")
public class RecruitmentRecruiterLink {
	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentRecruiterLinkGenerator")
	@GeneratedValue(generator = "id")
	
	@Column(name = "recruitment_recruiter_link_id")
	private String id;
	
	@Column(name = "recruiter_id")
	private String recruiterId;
	
	@Column(name = "recruitment_id")
	private String recruitmentId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;

}
