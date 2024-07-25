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
@Getter
@Setter
@Entity
@Table(name = "recruitment_profile_status")
public class RecruitProfileStatusLink {
	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.recruitment.generator.RecruitProfileStatusLinkGeneretor")
	@GeneratedValue(generator = "id")
	
	@Column(name = "recruitment_profile_status_id")
	private String id;
	
	@Column(name = "profile_id")
	private String profileId;
	
	@Column(name = "recruitment_id")
	private String recruitmentId;
	
	@Column(name = "candidate_id")
	private String candidateId;
	
	@Column(name="approve_ind")
	private boolean approveInd;
	
	@Column(name = "offer_date")
	private Date offerDate;
	
	@Column(name="reject_ind")
	private boolean rejectInd;
	
	@Column(name = "reject_date")
	private Date rejectDate;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name = "user_id")
	private String userId;
}
