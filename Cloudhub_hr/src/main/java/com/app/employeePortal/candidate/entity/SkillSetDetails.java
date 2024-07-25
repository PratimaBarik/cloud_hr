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
@Table(name="skill_set_details")
public class SkillSetDetails {
	@Id
	@GenericGenerator(name = "skill_set_details_id", strategy = "com.app.employeePortal.candidate.generator.SkillSetGenerator")
	@GeneratedValue(generator = "skill_set_details_id")
	
	@Column(name = "skill_set_details_id")
	private String skillSetDetailsId;
	
	@Column(name = "skill_name")
	private String skillName;
	
	@Column(name = "experience")
	private float experience;
	
	@Column(name="candidate_id")
	private String candidateId;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name="edit_ind")
	private boolean editInd;
	
	@Column(name="skill_role")
    private String skillRole;
	
	@Column(name="pause_ind", nullable =false)
	private boolean pauseInd =false;
	
	@Column(name = "pause_date")
	private Date pauseDate;
	
	@Column(name = "unpause_date")
	private Date unpauseDate;
	
	@Column(name = "pause_experience")
	private float pauseExperience;
	
}
