package com.app.employeePortal.category.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "nav_detail")
public class Nav{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int navDetailId;

	@Column(name="navId")
	private String navId;
	
	@Column(name = "org_id")
    private String orgId;

	@Column(name = "user_id")
    private String userId;	
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "liveInd")
	private boolean liveInd;

	@Column(name = "navName")
	private String navName;
}
