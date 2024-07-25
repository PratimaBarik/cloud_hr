package com.app.employeePortal.leads.entity;

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
@Table(name="leads_document_link")
public class LeadsDocumentLink {
	@Id
	@GenericGenerator(name = "leads_document_link_id", strategy ="com.app.employeePortal.leads.generator.LeadsDocumentLinkGenerator")
	@GeneratedValue(generator = "leads_document_link_id")
	
	@Column(name="leads_document_link_id")
	private String id;
	
	@Column(name="leadsId")
	private String leadsId;

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
