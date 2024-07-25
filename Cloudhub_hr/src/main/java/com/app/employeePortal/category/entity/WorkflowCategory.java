package com.app.employeePortal.category.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "workflow_category")

public class WorkflowCategory {
	@Id
	@GenericGenerator(name = "workflow_category_id", strategy = "com.app.employeePortal.category.generator.WorkflowCategoryGenerator")
    @GeneratedValue(generator = "workflow_category_id")
	
	@Column(name="workflow_category_id")
	private String workflowCategoryId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="updation_date")
	private Date updationDate;
	
	@Column(name="updated_by")
	private String updatedBy;

}

