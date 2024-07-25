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
@Table(name="monster_category")

public class MonsterCategory {
	@Id
	@GenericGenerator(name = "monster_category_id", strategy = "com.app.employeePortal.monster.generator.MonsterCategoryGenerator")
	@GeneratedValue(generator = "monster_category_id")
	
	
	@Column(name="monster_category_id")
	private String monsterCategoryId;
	
	
	@Column(name="job_category_id")
	private String jobCategoryId;
	
	
	@Column(name="job_category_alias")
	private String jobCategoryAlias;

}
