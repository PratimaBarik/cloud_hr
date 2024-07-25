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
@Table(name = "role_type")
public class RoleType {

	@Id
    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.category.generator.RoleTypeGenerator")
    @GeneratedValue(generator = "id")

	@Column(name="role_type_id")
	private String roleTypeId;
	
	@Column(name="role_type")
	private String roleType;
	
	@Column(name = "org_id")
    private String orgId;

	@Column(name = "user_id")
    private String userId;
	
	@Column(name="department_id")
	private String departmentId;
	
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name="edit_ind")
	private boolean editInd;
	
	@Column(name = "live_ind")
	private boolean liveInd;
	
}
