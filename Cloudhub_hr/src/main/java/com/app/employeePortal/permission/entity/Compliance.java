package com.app.employeePortal.permission.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="compliance")
public class Compliance {
	
	@Id
	@GenericGenerator(name = "compliance_id", strategy = "com.app.employeePortal.permission.generator.ComplianceGenerator")
	@GeneratedValue(generator = "compliance_id")
	
	@Column(name="compliance_id")
	private String complianceId;
	
	/*@Column(name="candidate_id")
	private String candidateId;*/
	
	@Column(name="gdpr_applicable_Ind")
	private boolean gdprApplicableInd;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="last_updated_on")
	private Date lastUpdatedOn;
	
	@Column(name="user_id")
	private String userId;
	

}
