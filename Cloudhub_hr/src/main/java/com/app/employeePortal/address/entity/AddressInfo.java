package com.app.employeePortal.address.entity;

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
@Table(name="address_info")
public class AddressInfo {
	
	@Id
	@GenericGenerator(name = "address_id", strategy = "com.app.employeePortal.address.generator.AddressInfoGenerator")
	@GeneratedValue(generator = "address_id")
	
	@Column(name="address_id")
	private String id;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="creator_id")
	private String creatorId;

	
	

}
