package com.app.employeePortal.RoleTypeExternal.entity;

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
@Table(name = "role_type_external")
public class RoleTypeExternal {
	@Id
	@GenericGenerator(name = "role_type_external_id", strategy = "com.app.employeePortal.RoleTypeExternal.generator.RoleTypeExternalGenerator")
	@GeneratedValue(generator = "role_type_external_id")

	@Column(name = "role_type_external_id")
	private String roleTypeExternalId;
	
	@Column(name="role_type")
	private String roleType;
	
	@Column(name = "org_id")
    private String orgId;

	@Column(name = "user_id")
    private String userId;	
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name="edit_ind",nullable = false)
	private boolean editInd;
	
	@Column(name = "live_ind",nullable = false)
	private boolean liveInd;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="updated_date")
	private Date updatedDate;

}
