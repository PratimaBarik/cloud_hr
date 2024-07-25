package com.app.employeePortal.category.entity;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "equipment")

public class Equipment {
	@Id
	@GenericGenerator(name = "equipment_id", strategy = "com.app.employeePortal.category.generator.EquipmentGenerator")
    @GeneratedValue(generator = "equipment_id")
	
	@Column(name="equipment_id")
	private String equipmentId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="updation_date")
	private Date updationDate;
	
	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="quantity",nullable = false)
	private int quantity;

	@Lob
	@Column(name="description")
	private String description;

}

