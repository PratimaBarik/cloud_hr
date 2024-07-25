package com.app.employeePortal.registration.entity;

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
@Table(name = "user_role")
public class UserRoleLink {


	@Id
	@GenericGenerator(name = "user_role_id", strategy = "com.app.employeePortal.registration.generator.UserRoleGenerator")
	@GeneratedValue(generator = "user_role_id")
	
	@Column(name="user_role_id")
	private String user_role_id;

    @Column(name = "role_id")
    private Integer role_id;
    
    @Column(name = "role_name")
    private String role_name;
    
    @Column(name="user_id")
	private String user_id;

	
    
    
	
}
