package com.app.employeePortal.distributor.entity;

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
@Table(name = "monthly_distributor")
public class MonthlyDistributor extends Auditable{
	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.distributor.generator.MonthlyDistributorGenerator")
	@GeneratedValue(generator = "id")
	
	@Column(name = "monthly_distributor_id")
	private String id;

	@Column(name = "distributorCount")
	private int distributorCount;
	
	@Column(name = "cancelDistributor")
	private int cancelDistributor;
	
	@Column(name = "pendingDistributor")
	private int pendingDistributor;
	
	@Column(name = "complete_distributor")
	private int completeDistributor=0;
	
	@Column(name = "month")
	private String month;
	
	@Column(name = "org_id")
	private String orgId;
}
