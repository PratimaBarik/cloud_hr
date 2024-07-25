package com.app.employeePortal.registration.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="department")
public class Department {
	
	@Id
	@GenericGenerator(name = "department_id", strategy = "com.app.employeePortal.registration.generator.DepartmentGenerator")
	@GeneratedValue(generator = "department_id")
	
	@JsonProperty("departmentId")
	@Column(name="department_id")
	private String department_id;
	
	@JsonProperty("departmentName")
	@Column(name="department_name")
	private String departmentName;

	@JsonProperty("userId")
	@Column(name="user_id")
	private String user_id;
	
	@JsonProperty("orgId")
	@Column(name="org_id")
	private String orgId;
	
	@JsonProperty("sectorId")
	@Column(name="sector_id")
	private String sector_id;
	
	@JsonProperty("sectorName")
	@Column(name="sectorName")
	private String sectorName;

	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name="edit_ind")
	private boolean editInd;
	
	@Column(name="live_ind")
	private boolean liveInd;

	@Column(name="mandetory_ind",nullable =false)
	private boolean mandetoryInd=false;
	
	@Column(name="crm_ind",nullable =false)
	private boolean crmInd=false;
	
	@Column(name="erp_ind",nullable =false)
	private boolean erpInd;
	
	@Column(name="im_ind",nullable =false)
	private boolean imInd;
	
	@Column(name="accountInd",nullable =false)
	private boolean accountInd=false;
	
	@Column(name="recruitOppsInd",nullable =false)
	private boolean recruitOppsInd=false;
	
	@Column(name="hr_ind",nullable =false)
	private boolean hrInd=false;
	
	@Column(name="recruitProInd",nullable =false)
	private boolean recruitProInd=false;
	
	@Column(name="production_ind",nullable =false)
	private boolean productionInd=false;
	
	@Column(name="repair_ind",nullable =false)
	private boolean repairInd=false;
	
	@Column(name="inventory_ind",nullable =false)
	private boolean inventoryInd=false;
	
	@Column(name="order_Management_ind",nullable =false)
	private boolean orderManagementInd=false;
	
	@Column(name="logistics_ind",nullable =false)
	private boolean logisticsInd=false;
	
	@Column(name="procurement_ind",nullable =false)
	private boolean procurementInd=false;
	
	@Column(name="eLearningInd",nullable =false)
	private boolean eLearningInd=false;
	
	@Column(name="financeInd",nullable =false)
	private boolean financeInd=false;
	
//	@Column(name="project_mod_ind",nullable =false)
//	private boolean projectModInd=false;
//	
//	@Column(name="ecom_mod_ind",nullable =false)
//	private boolean ecomModInd=false;
	
	@Column(name="service_line_ind",nullable =false)
	private boolean serviceLineInd=false;
}
