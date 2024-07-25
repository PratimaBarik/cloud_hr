package com.app.employeePortal.category.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "hour")
public class Hour{

	@Id
    @GenericGenerator(name = "hour_id", strategy = "com.app.employeePortal.category.generator.HourGenerator")
    @GeneratedValue(generator = "hour_id")

	@Column(name="hour_id")
	private String hourId;
	
	@Column(name = "org_id")
    private String orgId;

	@Column(name = "user_id")
    private String userId;	
	
	@Column(name = "project_name")
	private String projectName;
	
	@Column(name = "start_time")
	private long startTime;

	@Column(name = "end_time")
	private long endTime;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "candidate_id")
    private String candidateId;
	
	@Column(name = "hour")
    private float hour;	
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "customer_id")
    private String customerId;
	
	@Column(name = "task_id")
    private String taskId;
	
	@Column(name = "project_manager")
    private String projectManager;
	
	@Column(name = "aprove_ind")
    private boolean aproveInd;
	
	@Column(name = "complete_unit")
    private String completeUnit;
	
	@Lob
	@Column(name = "note")
    private String note;
	
	@Column(name = "remark")
    private String remark;
	
	@Column(name = "aprove_unit")
    private String aproveUnit;
	
	@Column(name="image_id")
	private String imageId;
	
	@Column(name="document_id")
	private String documentId;
	
	@Column(name = "planner_start_date")
	private Date plannerStartDate;
	
}
