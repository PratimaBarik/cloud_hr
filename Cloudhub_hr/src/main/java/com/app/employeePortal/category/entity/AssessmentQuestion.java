package com.app.employeePortal.category.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@ToString
@Entity
@Getter
@Setter
@Table(name = "assessment_question")
public class AssessmentQuestion {

	@Id
	@GenericGenerator(name = "assmnt_qstn_id", strategy = "com.app.employeePortal.category.generator.AssessmentQuestionsGenerator")
	@GeneratedValue(generator = "assmnt_qstn_id")

	@Column(name = "assmnt_qstn_id")
	private String assmntQstnId;

	@Column(name = "question")
	private String question;

	@Column(name = "department_id")
	private String departmentId;

	@Column(name = "roleType_id")
	private String roleTypeId;

	@Column(name = "orgId")
	private String orgId;

	@Column(name = "userId")
	private String userId;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "liveInd")
	private boolean liveInd;
	
	@Column(name = "updationDate")
	private Date updationDate;
	
	@Column(name = "updatedBy")
	private String updatedBy;
	
}
