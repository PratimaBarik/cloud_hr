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
@Table(name="monster_industry")

public class MonsterIndustry {
	
	@Id
	@GenericGenerator(name = "monster_industry_id", strategy = "com.app.employeePortal.monster.generator.MonsterOccupationGenerator")
	@GeneratedValue(generator = "monster_industry_id")
	
	
	@Column(name="monster_industry_id")
	private String monsterIndustryId;
	
	
	@Column(name="industry_id")
	private String industryId;
	
	
	@Column(name="industry_alias")
	private String industryAlias;

}


