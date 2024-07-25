package com.app.employeePortal.task.entity;

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
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="task_details")
public class TaskDetails {
	
	
	@Id
	@GenericGenerator(name = "task_details_id", strategy = "com.app.employeePortal.task.generator.TaskDetailsGenerator")
	@GeneratedValue(generator = "task_details_id")
	

	@Column(name="task_details_id")
	private String task_details_id;
	
	@Column(name="task_id")
	private String task_id;
	
	@Column(name="task_name")
	private String task_name;
	
	@Column(name="task_type")
	private String task_type;
	
	@Lob
	@Column(name="task_description")
	private String task_description;
	
	@Column(name="priority")
	private String priority;
	
	@Column(name="start_date")
	private Date start_date;
	
	
	@Column(name="start_time")
	private long start_time;
	
	@Column(name="end_date")
	private Date end_date;
	
	@Column(name="end_time")
	private long end_time;
	
	@Column(name="time_zone")
	private String time_zone;
	
	@Column(name="task_status")
	private String task_status;
	
    @Column(name="creation_date")
	private Date creation_date;
    
    @Column(name="live_ind")
	private boolean liveInd;
    
	@Column(name = "organization_id")
	private String organization_id;

	@Column(name = "user_id")
	private String user_id;
	
	@Column(name = "approved_ind")
	private String approved_ind;
	
	@Column(name="approved_date")
	private Date approved_date;

	@Column(name="candidateId")
	private String candidateId;

	@Column(name="assignedTo")
	private String assigned_to;
	
	@Column(name="assigned_by")
	private String assignedBy;
	
	@Column(name = "complition_ind", nullable =false)
	private boolean complitionInd=false;

	@Column(name = "rating")
	private float rating;
	
	@Column(name="update_date")
	private Date updateDate;
	
	 @Column(name = "current_level")
	 private int currentLevel;
	 
	@Column(name="assigned_date")
	private Date assignedDate;
	
	@Column(name="value")
	private String value;
	
	@Column(name="unit")
	private String unit;
	
	@Column(name="project_name")
	private String projectName;
	
	@Column(name="image_id")
	private String imageId;
	
//	@Column(name="document_id")
//	private String documentId;
	
	@Column(name="complexity")
	private String complexity;
	
	@Column(name="customer_id")
	private String customerId;
	
	@Column(name="task_checklist_id")
	private String taskChecklistId;
	
	@Column(name="reapply_count")
	private String reapplyCount;
	
	@Column(name="link")
	private String link;
	
	@Column(name = "oppertunity")
	private String oppertunity;
	
	@Column(name = "contact")
	private String contact;
	
}
