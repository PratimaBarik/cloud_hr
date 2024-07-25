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
@Table(name = "team_member_link")

public class TeamMemberLink {
	@Id
	@GenericGenerator(name = "team_member_link_id", strategy = "com.app.employeePortal.Team.generator.TeamMemberLinkGenerator")
    @GeneratedValue(generator = "team_member_link_id")
	
	@Column(name="team_member_link_id")
	private String team_member_link_id;
	
	@Column(name="team_member_id")
	private String teamMemberId;
	
	@Column(name="team_id")
	private String teamId;
	
	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="live_ind",nullable = false)
	private boolean liveInd;
	
}

