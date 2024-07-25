package com.app.employeePortal.leave.entity;

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
@Table(name="organization_leave_rule")
public class OrganizationLeaveRule {
	@Id
	@GenericGenerator(name = "organization_leave_rule_id", strategy = "com.app.employeePortal.leave.generator.OrganizationLeaveRuleGenerator")
	@GeneratedValue(generator = "organization_leave_rule_id")
	
	@Column(name = "organization_leave_rule_id")
	private String organization_leave_rule_id;
	
	@Column(name="org_id")
	private String org_id;
	
	@Column(name="maximum_leaves")
	private int maximum_leaves;
	
	@Column(name="carry_forward")
	private double carry_forward;
	
	@Column(name="leaves_capped_times_annualy")
	private double leavesCappedTimesAnnualy;

	@Column(name = "creation_date")
	private Date creation_date;

	@Column(name = "live_ind")
	private boolean live_ind;

	@Column(name="country")
	private String country;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name = "maximumLeavesEffectiveDate")
	private Date maximumLeavesEffectiveDate;
	
	@Column(name = "carryForwardEffectiveDate")
	private Date carryForwardEffectiveDate;
	
	@Column(name = "mileageRateEffectiveDate")
	private Date mileageRateEffectiveDate;
	
	@Column(name = "leavesCappedTimesAnnualyEffectiveDate")
	private Date leavesCappedTimesAnnualyEffectiveDate;
	
	@Column(name="maxOpsnlHoliday")
	private int maxOpsnlHoliday;
}
