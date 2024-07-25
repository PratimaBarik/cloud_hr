package com.app.employeePortal.category.entity;

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
@Table(name = "Department_Sector_Link")
public class DepartmentSectorLink {
	
	@Id
    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.category.generator.DepartmentSectorLinkGenerator")
    @GeneratedValue(generator = "id")

	@Column(name="Department_Sector_Link_id")
	private String DepartmentSectorLinkId;
	
	@Column(name="Department_id")
	private String DepartmentId;
	
	@Column(name="Sector_id")
	private String SectorId;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name="edit_ind")
	private boolean editInd;
}
