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
@Table(name="sector_details_delete")
public class SectorDetailsDelete {
	
	@Id
	@GenericGenerator(name = "sector_delete_id", strategy = "com.app.employeePortal.sector.generator.SectorDetailsDeleteGenerator")
    @GeneratedValue(generator = "sector_delete_id")
	
	@Column(name="sector_delete_id")
	private String sectorDeleteId;
	
	@Column(name="sector_id")
	private String sectorId;
	
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name = "updated_by")
	private String updatedBy;

}
