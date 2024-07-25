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
@Table(name = "yearly_distributor")
public class YearlyDistributor extends Auditable {
	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.distributor.generator.YearlyDistributorGenerator")
	@GeneratedValue(generator = "id")

	@Column(name = "yearly_distributor_id")
	private String id;

	@Column(name = "distributorCount")
	private int distributorCount;

	@Column(name = "cancelDistributor")
	private int cancelDistributor;

	@Column(name = "pendingDistributor")
	private int pendingDistributor;

	@Column(name = "complete_distributor")
	private int completeDistributor = 0;

	@Column(name = "year")
	private String year;

	@Column(name = "org_id")
	private String orgId;
}
