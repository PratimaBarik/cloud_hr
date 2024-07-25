package com.app.employeePortal.monster.entity;

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
@Table(name = "monster_publish")

public class MonsterPublish {
	
	@Id
	@GenericGenerator(name = "monster_publish_id", strategy = "com.app.employeePortal.monster.generator.MonsterPublishGenerator")
	@GeneratedValue(generator = "monster_publish_id")
	
	@Column(name = "monster_publish_id")
	private String monsterPublishId; 
	
	@Column(name = "recruitment_id")
	private String recruitmentId;
	
	@Column(name = "monster_id")
	private String monsterId;

	@Column(name = "job_duration")
	private String jobDuration;

	@Column(name = "job_category")
	private String jobCategory;

	@Column(name = "job_occupation")
	private String jobOccupation;

	@Column(name = "job_board_name")
	private String jobBoardName;

	@Column(name = "display_template")
	private String displayTemplate;
	
	@Column(name = "industry")
	private String industry;

	@Column(name = "organization_id")
	private String organizationId;
	
	@Column(name = "user_id")
	private String userId;
	

}
