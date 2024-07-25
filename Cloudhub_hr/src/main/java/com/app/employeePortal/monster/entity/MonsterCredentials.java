package com.app.employeePortal.monster.entity;

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
@Table(name = "monster_credentials")

public class MonsterCredentials {
	
	@Id
	@GenericGenerator(name = "monster_credentials_id", strategy = "com.app.employeePortal.monster.generator.MonsterCredentialsGenerator")
	@GeneratedValue(generator = "monster_credentials_id")
	
	
	@Column(name = "monster_credentials_id")
	private String monsterCredentialsId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "user_name")
	private String userName;

	@Column(name="password")
	private String password;

	@Column(name = "monster_ind")
	private boolean monsterInd;
	
	@Column(name ="last_updated_on")
	private Date lastUpdatedOn;

	
}



