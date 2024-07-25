package com.app.employeePortal.employee.entity;

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
@Table(name="key_skills")
public class KeySkillDetails {
	@Id
	@GenericGenerator(name = "key_skill_id", strategy = "com.app.employeePortal.employee.generator.KeySkillDetailsGenerator")
	@GeneratedValue(generator = "key_skill_id")
	
	
	@Column(name="key_skill_id")
	private String id;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="skill_name")
	private String skillName;
	
	@Column(name="creation_date")
	private Date creationDate;
	

	@Column(name="live_ind",nullable = false)
	private boolean liveInd;

	@Column(name="edit_ind",nullable = false)
	private boolean editInd;

	@Column(name="skill_role")
	private String skillRole;

	@Column(name="pause_ind", nullable =false)
	private boolean pauseInd =false;

	@Column(name = "pause_date")
	private Date pauseDate;

	@Column(name = "unpause_date")
	private Date unpauseDate;

	@Column(name = "pause_experience",nullable = false)
	private float pauseExperience;

	@Column(name = "experience",nullable = false)
	private float experience;
}
