package com.app.employeePortal.sector.entity;

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
@Table(name="sector_details")
public class SectorDetails {
	
	@Id
	@GenericGenerator(name = "sector_id", strategy = "com.app.employeePortal.sector.generator.SectorDetailsGenerator")
    @GeneratedValue(generator = "sector_id")
	
	
	
	@Column(name="sector_id")
	private String sectorId;
	
	@Column(name="sector_name")
	private String sectorName;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "live_ind")
	private boolean liveInd;
	
	@Column(name="edit_ind")
	private boolean editInd;

}
