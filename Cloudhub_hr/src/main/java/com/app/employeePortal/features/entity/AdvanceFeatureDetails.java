package com.app.employeePortal.features.entity;

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
@Table(name = "advance_feature_details")
public class AdvanceFeatureDetails {
	
	@Id
	@GenericGenerator(name = "advance_feature_details_id", strategy = "com.app.employeePortal.features.generator.AdvanceFeatureGenerator")
	@GeneratedValue(generator = "advance_feature_details_id")

	@Column(name = "advance_feature_details_id")
	private String id;
	
	@Column(name = "org_id")
	private String orgId;

	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "inactive_date")
	private Date inactiveDate;

	@Column(name = "live_ind")
	private boolean liveInd;

}
