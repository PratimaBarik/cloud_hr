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
@Table(name = "recruitment_stage_note")
public class RecruitStageNoteLink {
	
	@Id
	@GenericGenerator(name = "recruitment_stage_note_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitStageNoteLinkGenerator")
	@GeneratedValue(generator = "recruitment_stage_note_id")
	
	@Column(name = "recruitment_stage_note_id")
	private String recruitmentStageNoteId;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "reviewer")
	private String reviewer;
	
	@Column(name = "stage_id")
	private String stage_id;
	
	@Column(name = "recruit_id")
	private String recruit_id;
	
	@Column(name = "opp_id")
	private String opp_id;
	
	@Column(name = "profile_id")
	private String profile_id;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "live_ind")
	private boolean live_ind;

	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "updated_On")
	private Date updatedOn;
	
	@Column(name = "score")
	private int score;
	
}
