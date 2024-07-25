package com.app.employeePortal.Team.entity;

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
@Table(name = "team")

public class Team {
	@Id
	@GenericGenerator(name = "team_id", strategy = "com.app.employeePortal.Team.generator.TeamGenerator")
    @GeneratedValue(generator = "team_id")
	
	@Column(name="team_id")
	private String teamId;
	
	@Column(name="teamName")
	private String teamName;
	
	@Column(name="teamLead")
	private String teamLead;
	
	@Column(name="teamlogo")
	private String teamlogo;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="creation_date")
	private Date creationDate;
	
}

