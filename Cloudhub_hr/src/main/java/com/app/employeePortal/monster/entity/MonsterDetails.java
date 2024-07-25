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
@Table(name = "monster_details")

public class MonsterDetails {
	
	@Id
	@GenericGenerator(name = "monster_details_id", strategy = "com.app.employeePortal.monster.generator.MonsterDetailsGenerator")
	@GeneratedValue(generator = "monster_details_id")
	
	
	@Column(name = "monster_details_id")
	private String monsterDetailsId;

	@Column(name = "monster_id")
	private String monsterId;

	@Column(name = "job_duration")
	private String jobDuration;

	@Column(name = "job_category")
	private String jobCategory;

	@Column(name = "job_occupation")
	private String jobOccupation;

	@Column(name = "job_board_name")
	private int jobBoardName;

	@Column(name = "display_template")
	private int displayTemplate;

	@Column(name ="vedio")
	private int vedio;

	
	
	
}
