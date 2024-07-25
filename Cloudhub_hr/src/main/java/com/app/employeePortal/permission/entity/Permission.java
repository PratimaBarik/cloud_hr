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
@Table(name="permission")
public class Permission {
	@Override
	public String toString() {
		return "Permission [id=" + id + ", userId=" + userId + ", candidateShareInd=" + candidateShareInd
				+ ", plannerShareInd=" + plannerShareInd + ", opportunityInd=" + opportunityInd + ", contactInd="
				+ contactInd + ", customerInd=" + customerInd + ", partnerContactInd=" + partnerContactInd + "]";
	}

	@Id
	@GenericGenerator(name = "permission_id", strategy = "com.app.employeePortal.permission.generator.PermissionGenerator")
	@GeneratedValue(generator = "permission_id")
	
	@Column(name="permission_id")
	private String id;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="candidate_share_Ind")
	private boolean candidateShareInd;
	
	@Column(name="planner_share_Ind")
	private boolean plannerShareInd;
	
	@Column(name="oppotunity_Ind")
	private boolean opportunityInd;
	
	@Column(name="contact_Ind")
	private boolean contactInd;
	
	@Column(name="customer_Ind")
	private boolean customerInd;
	
	@Column(name="partner_contact_Ind")
	private boolean partnerContactInd;
	
	@Column(name="partnerInd")
	private boolean partnerInd;
	
	@Column(name="monitizeInd")
	private boolean monitizeInd;
	
	@Column(name="plannerInd")
	private boolean plannerInd;
	
	@Column(name="call_Ind")
	private boolean callInd;
	
	@Column(name="event_Ind")
	private boolean eventInd;
	
	@Column(name="task_Ind")
	private boolean taskInd;

	@Column(name="candidate_emp_share_ind")
	private boolean candiEmpShareInd;
	
	@Column(name="candidate_emp_srch_ind")
	private boolean candiEmpSrchInd;
	
	@Column(name="candidate_cont_share_ind")
	private boolean candiContShareInd;
	
	@Column(name="candidate_cont_srch_ind")
	private boolean candiContSrchInd;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name ="last_updated_on")
	private Date lastUpdatedOn;
}
