package com.app.employeePortal.employee.entity;

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
@Table(name="user_equipment_link")
public class UserEquipmentLink {
	
	@Id
	@GenericGenerator(name = "user_kpi_link_id", strategy = "com.app.employeePortal.employee.generator.UserEquipmentLinkGenerator")
	@GeneratedValue(generator = "user_kpi_link_id")
	
	@Column(name="user_equipment_link_id")
	private String userEquipmentLinkId;
	
	@Column(name="equipment_id")
	private String equipmentId;
	
	@Column(name="value")
	private String value;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
}
