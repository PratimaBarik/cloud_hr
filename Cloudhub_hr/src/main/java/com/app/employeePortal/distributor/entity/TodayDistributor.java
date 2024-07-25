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
@Table(name = "today_distributor")
public class TodayDistributor extends Auditable {

	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.distributor.generator.TodayDistributorGenerator")
	@GeneratedValue(generator = "id")

	@Column(name = "today_distributor_id")
	private String id;

	@Column(name = "distributor_count")
	private int distributorCount;

	@Column(name = "delete_distributor_count")
	private int deletedistributorCount;
	
	@Column(name = "pending_distributor")
	private int pendingDistributor=0;
	
	@Column(name = "complete_distributor")
	private int completeDistributor=0;
}
