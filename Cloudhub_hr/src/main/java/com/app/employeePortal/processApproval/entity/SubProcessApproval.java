package com.app.employeePortal.processApproval.entity;

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
@Table(name = "sub_process_approval")
public class SubProcessApproval {
	@Id
    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.processApproval.generator.SubProcessApprovalGenerator")
    @GeneratedValue(generator = "id")

	@Column(name="sub_process_approval_id")
	private String id;
	
	@Column(name="sub_process_name")
	private String subProcessName;
	
	@Column(name="process_id")
	private String processId;

}
