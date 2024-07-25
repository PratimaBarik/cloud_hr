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
@Table(name = "club")

public class Club {
	@Id
	@GenericGenerator(name = "equipment_id", strategy = "com.app.employeePortal.category.generator.ClubGenerator")
    @GeneratedValue(generator = "equipment_id")
	
	@Column(name="club_id")
	private String clubId;
	
	@Column(name="clubName")
	private String clubName;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="updation_date")
	private Date updationDate;
	
	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="no_of_share",nullable = false)
	private double noOfShare;

	@Column(name="discount")
	private float discount;
	
	@Column(name="inv_to_cus_Ind", nullable =false)
	private boolean invToCusInd =false;

}

