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
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@Table(name = "admin_secrete_key")
public class AdminSecreteKey {

	@Id
	@GenericGenerator(name = "admin_secrete_key_id", strategy = "com.app.employeePortal.category.generator.AdminSecreteKeyGenerator")
	@GeneratedValue(generator = "admin_secrete_key_id")

	@Column(name = "admin_secrete_key_id")
	private String adminSecreteKeyId;

	@Column(name = "admin_secrete_key")
	private String adminSecreteKey;
	
	@Column(name = "orgid")
	private String orgId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "live_ind")
	private boolean liveInd;
	
}
