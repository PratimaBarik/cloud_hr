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
@Table(name = "role_type_delete")
public class RoleTypeDelete {

	@Id
    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.category.generator.RoleTypeDeleteGenerator")
    @GeneratedValue(generator = "id")

	@Column(name="role_type_delete_id")
	private String roleTypeDeleteId;
	
	@Column(name="role_type_id")
	private String roleTypeId;
	
	@Column(name = "org_id")
    private String orgId;

	@Column(name = "user_id")
    private String userId;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
}
