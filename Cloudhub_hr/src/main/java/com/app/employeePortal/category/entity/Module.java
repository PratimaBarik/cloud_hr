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
@Table(name = "module")

public class Module {
	@Id
	@GenericGenerator(name = "module_id", strategy = "com.app.employeePortal.category.generator.ModuleGenerator")
    @GeneratedValue(generator = "module_id")
	
	@Column(name="module_id")
	private String moduleId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="updation_date")
	private Date updationDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="crm_ind")
	private boolean crmInd;
	
	@Column(name="erp_ind")
	private boolean erpInd;
	
	@Column(name="im_ind")
	private boolean imInd;
	
	@Column(name="hr_ind")
	private boolean hrInd;
	
	@Column(name="recruitProInd")
	private boolean recruitProInd;
	
	@Column(name="production_ind")
	private boolean productionInd;
	
	@Column(name="repair_ind")
	private boolean repairInd;
	
	@Column(name="inventory_ind")
	private boolean inventoryInd;
	
	@Column(name="order_Management_ind")
	private boolean orderManagementInd;
	
	@Column(name="logistics_ind")
	private boolean logisticsInd;
	
	@Column(name="procurement_ind")
	private boolean procurementInd;
	
	@Column(name="eLearningInd",nullable =false)
	private boolean eLearningInd=false;
	
	@Column(name="financeInd",nullable =false)
	private boolean financeInd=false;
	
	@Column(name="project_mod_ind",nullable =false)
	private boolean projectModInd=false;
	
	@Column(name="ecom_mod_ind",nullable =false)
	private boolean ecomModInd=false;
	
	@Column(name="trading_ind",nullable =false)
	private boolean tradingInd=false;
	
	@Column(name="customer_port_ind",nullable =false)
	private boolean customerPortInd=false;
}

