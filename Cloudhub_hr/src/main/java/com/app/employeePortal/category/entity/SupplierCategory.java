package com.app.employeePortal.category.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "supplier_category")

public class SupplierCategory {
	@Id
	@GenericGenerator(name = "supplier_category_id", strategy = "com.app.employeePortal.category.generator.SupplierCategoryGenerator")
    @GeneratedValue(generator = "supplier_category_id")
	
	@Column(name="supplier_category_id")
	private String supplierCategoryId;
	
	@Column(name="supplier_cat_name")
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

