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
@Table(name="skill_candidate_no")
public class SkillCandidateNo {
	
	@Id
	@GenericGenerator(name = "skill_candidate_no_id", strategy = "com.app.employeePortal.candidate.generator.SkillCandidateNoGenerator")
	@GeneratedValue(generator = "skill_candidate_no_id")
	
	@Column(name = "skill_candidate_no_id")
	private String skillCandidateNoId;
	
	@Column(name="skill")
	private String skill;
	
	@Column(name = "number")
	private int number;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="organization_id")
	private String organizationId;

	@Column(name = "creation_date")
	private Date creationDate;
	
	

}
