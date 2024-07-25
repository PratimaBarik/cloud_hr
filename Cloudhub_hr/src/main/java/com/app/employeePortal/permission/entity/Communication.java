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

@Entity
@Getter
@Setter
@Table(name = "communication")

public class Communication {
	
	@Id
	@GenericGenerator(name = "communication_id", strategy = "com.app.employeePortal.permission.generator.CommunicationGenerator")
    @GeneratedValue(generator = "communication_id")
	
	
	@Column(name="communication_id")
	private String communicationId;
	
	@Column(name="email_customer_ind")
	private boolean emailCustomerInd;
	
	@Column(name="email_job_des_ind")
	private boolean emailJobDesInd;
	
	@Column(name="whatsapp_customer_ind")
	private boolean whatsappCustomerInd;
	
	@Column(name="whatsapp_job_des_ind")
	private boolean whatsappJobDesInd;
	
	@Column(name="candidate_event_update_ind")
	private boolean candidateEventUpdateInd;
	
	@Column(name="candi_workflow_enabled_inst_ind")
	private boolean candiWorkflowEnabledInstInd;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name ="last_updated_on")
	private Date lastUpdatedOn;
	
	@Column(name="user_id")
	private String userId;

	@Column(name="candi_pipeline_email_ind",nullable =false)
	private boolean candiPipelineEmailInd=false;
}
