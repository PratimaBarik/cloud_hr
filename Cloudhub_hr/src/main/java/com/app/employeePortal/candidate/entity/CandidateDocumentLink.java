package com.app.employeePortal.candidate.entity;

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
@Table(name = "candidate_document_link")
public class CandidateDocumentLink {
	
	@Id
	@GenericGenerator(name = "candidate_document_link_id", strategy = "com.app.employeePortal.candidate.generator.CandidateDocumentLinkGenerator")
    @GeneratedValue(generator = "candidate_document_link_id")
	
	@Column(name="candidate_document_link_id")
	private String id;
	
	@Column(name="candidate_id")
	private String candidateId;
	
	@Column(name="document_id")
	private String documentId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="share_ind", nullable =false)
    private boolean shareInd = false;
	
	@Column(name="shared_user")
	private String sharedUser;
	
	@Column(name="contract_Ind", nullable = false)
	private boolean contractInd =false;
}
