package com.app.employeePortal.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.app.employeePortal.attendance.entity.Auditable;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cell_chamber_user_link")
public class CellChamberUserLink extends Auditable{
	@Id
//	@GenericGenerator(name = "cell_chamber_user_link_id", strategy = "com.app.NuboxedErp.generator.CellChamberUserLinkGenerator")
//	@GeneratedValue(generator = "cell_chamber_user_link_id")

	@Column(name = "cell_chamber_user_link_id")
	private String cellChamberUserLinkId;
	
	@Column(name = "cell_chamber_link_id")
	private String cellChamberLinkId;
	
	@Column(name = "cell_id")
	private String cellId;
	
	@Column(name = "user")
	private String user;

	@Column(name = "location_id")
	private String locationId;
	
	@Column(name = "org_id")
	private String orgId;
	
	@Column(name = "department")
	private String department;
}
