package com.app.employeePortal.processApproval.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@Table(name = "process_approval")
public class ProcessApproval {
	
	@Id
    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.processApproval.generator.ProcessApprovalGenerator")
    @GeneratedValue(generator = "id")

    @Column(name = "process_id")
    private String id;
	
	
	@Column(name = "process_name")
	 private String processName;

}
