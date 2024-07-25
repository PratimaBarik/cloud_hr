package com.app.employeePortal.employee.entity;

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
@Table(name="user_kpi_link")
public class UserKpiLink {
	
	@Id
	@GenericGenerator(name = "user_kpi_link_id", strategy = "com.app.employeePortal.employee.generator.UserkpiLinkGenerator")
	@GeneratedValue(generator = "user_kpi_link_id")
	
	@Column(name="user_kpi_link_id")
	private String userKpiLinkId;
	
	@Column(name="performance_management_id")
	private String performanceManagementId;
	
	@Column(name="lob_details_id")
	private String lobDetailsId;
	
	@Column(name="assigned_value")
	private double assignedValue;
	
	@Column(name="month1_assigned_value")
	private double month1AssignedValue;
	
	@Column(name="month2_assigned_value")
	private double month2AssignedValue;
	
	@Column(name="month3_assigned_value")
	private double month3AssignedValue;
	
	@Column(name="completed_value")
	private double completedValue;
	
	@Column(name="month1_completed_value")
	private double month1CompletedValue;
	
	@Column(name="month2_completed_value")
	private double month2CompletedValue;
	
	@Column(name="month3_completed_value")
	private double month3CompletedValue;
	
	@Column(name = "weitage_value")
	private double weitageValue;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="cmplt_valu_add_date")
	private Date cmpltValueAddDate;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name = "year")
	private double year;

	@Column(name = "quarter")
	private String quarter;
	
	@Column(name="month1_actual_completed_value")
	private double month1ActualCompletedValue;
	
	@Column(name="month2_actual_completed_value")
	private double month2ActualCompletedValue;
	
	@Column(name="month3_actual_completed_value")
	private double month3ActualCompletedValue;
	
	@Column(name="actual_completed_value")
	private double actualCompletedValue;
	
	@Column(name="actual_cmplt_valu_add_date")
	private Date actualCmpltValueAddDate;
	
	@Column(name="actual_cmplt_valu_added_by")
	private String actualCmpltValueAddedBy;

}
