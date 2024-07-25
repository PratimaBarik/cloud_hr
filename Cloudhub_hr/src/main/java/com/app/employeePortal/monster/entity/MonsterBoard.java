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
@Table(name="monster_board")


public class MonsterBoard {

	@Id
	@GenericGenerator(name = "monster_board_id", strategy = "com.app.employeePortal.monster.generator.MonsterBoardGenerator")
	@GeneratedValue(generator = "monster_board_id")
	
	
	@Column(name="monster_board_id")
	private String monsterBoardId;
	
	
	@Column(name="job_board_name")
	private String jobBoardName;
	
	
	@Column(name="test_board_id")
	private String testBoardId;
	
}
