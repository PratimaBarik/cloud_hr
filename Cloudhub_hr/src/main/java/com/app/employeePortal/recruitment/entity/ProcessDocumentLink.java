package com.app.employeePortal.recruitment.entity;
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
@Table(name="process_document_link")
public class ProcessDocumentLink {
	@Id
    @GenericGenerator(name = "process_document_link_id", strategy = "com.app.employeePortal.recruitment.generator.ProcessDocumentLinkGenerator")
    @GeneratedValue(generator = "process_document_link_id")

    @Column(name = "process_document_link_id")
    private String processDocumentLinkId;

	@Column(name = "user_id")
	private String userId;

    @Column(name = "org_id")
    private String orgId;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "process_id")
    private String processId;

    @Column(name = "document_type_id")
    private String documentTypeId;
    
    @Column(name = "mandatory_ind")
	private boolean mandatoryInd;
    
    @Column(name = "updation_date")
    private Date updationDate;
}
