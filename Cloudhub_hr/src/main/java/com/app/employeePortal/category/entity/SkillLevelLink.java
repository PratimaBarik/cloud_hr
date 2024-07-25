package com.app.employeePortal.category.entity;

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
@Table(name = "skill_level_link")

public class SkillLevelLink {
	@Id
	@GenericGenerator(name = "skill_level_link_id", strategy = "com.app.employeePortal.category.generator.SkillLevelLinkGenerator")
    @GeneratedValue(generator = "skill_level_link_id")
	
	@Column(name="skill_level_link_id")
	private String id;
	
	@Column(name="skill_defination_id")
	private String skillDefinationId;
	
	@Column(name="skill")
	private String skill;
	
	@Column(name="level1")
	private float level1;
	
	@Column(name="level2")
	private float level2;
	
	@Column(name="level3")
	private float level3;
	
	@Column(name="level4")
	private float level4;
	
	@Column(name="level5")
	private float level5;
	
	@Column(name="country_id")
	private String countryId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	
}
