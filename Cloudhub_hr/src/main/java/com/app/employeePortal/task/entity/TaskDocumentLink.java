package com.app.employeePortal.task.entity;

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
@Table(name="task_document_link")
public class TaskDocumentLink {
	@Id
	@GenericGenerator(name = "task_document_link_id", strategy ="com.app.employeePortal.task.generator.TaskDocumentLinkGenerator")
	@GeneratedValue(generator = "task_document_link_id")
	
	@Column(name="task_document_link_id")
	private String id;
	
	@Column(name="taskId")
	private String taskId;

	@Column(name="documentId")
	private String documentId;

	@Column(name="creationDate")
	private Date creationDate;
	
	@Column(name="share_ind", nullable =false)
    private boolean shareInd = false;
	
	@Column(name="shared_user")
	private String sharedUser;

	@Column(name="contract_Ind", nullable = false)
	private boolean contractInd =false;
}
