package com.app.employeePortal.organization.entity;

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
@Table(name="organization_info")
public class OrganizationInfo {
	

	@Id
	@GenericGenerator(name = "organization_id", strategy = "com.app.employeePortal.organization.generator.OrganizationInfoGenerator")
	@GeneratedValue(generator = "organization_id")
	
	@Column(name="organization_id")
	private String organization_id;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="creator_id")
	private String creator_id;




	
}
