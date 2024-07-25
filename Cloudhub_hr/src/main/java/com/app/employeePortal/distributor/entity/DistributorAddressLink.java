package com.app.employeePortal.distributor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.app.employeePortal.attendance.entity.Auditable;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "distributor_address_link")
public class DistributorAddressLink extends Auditable{

	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.distributor.generator.DistributorAddressLinkGenerator")
	@GeneratedValue(generator = "id")

	@Column(name = "distributor_address_id")
	private String id;

	@Column(name = "address_id")
	private String addressId;

	@Column(name = "distributor_id")
	private String distributorId;

	@Column(name="creation_date")
	private Date creationDate;
}
