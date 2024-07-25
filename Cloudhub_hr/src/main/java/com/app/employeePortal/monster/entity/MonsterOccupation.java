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
@Table(name="monster_occupation")

public class MonsterOccupation {
	
	@Id
	@GenericGenerator(name = "monster_occupation_id", strategy = "com.app.employeePortal.monster.generator.MonsterOccupationGenerator")
	@GeneratedValue(generator = "monster_occupation_id")
	
	
	@Column(name="monster_occupation_id")
	private String monsterOccupationId;
	
	
	@Column(name="occupation_id")
	private String occupationId;
	
	
	@Column(name="occupation_alias")
	private String occupationAlias;

}


