package com.app.employeePortal.category.entity;

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
@Table(name = "cell_details")
public class CellDetails extends Auditable {
	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.category.generator.CellProductionLinkGenerator")
	@GeneratedValue(generator = "id")

	@Column(name = "cell_id")
	private String id;
	
	@Column(name = "cell")
	private String cell;
	
	@Column(name = "cell_unit")
	private int cellUnit;
	
	@Column(name = "user_id")
	private String userId;

	@Column(name = "location_details_id")
	private String locationDetailsId;
	
	@Column(name = "org_id")
	private String orgId;
	
	@Column(name = "description")
	private String description;
}
