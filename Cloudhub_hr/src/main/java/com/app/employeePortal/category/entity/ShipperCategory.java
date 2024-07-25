package com.app.employeePortal.category.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "shipper_category")

public class ShipperCategory {
	@Id
	@GenericGenerator(name = "shipper_category_id", strategy = "com.app.employeePortal.category.generator.ShipperCategoryGenerator")
    @GeneratedValue(generator = "shipper_category_id")
	
	@Column(name="shipper_category_id")
	private String shipperCategoryId;
	
	@Column(name="shipper_cat_name")
	private String name;
	
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

}

