package com.app.employeePortal.investorleads.entity;

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
@Table(name="investor_leads_document_link")
public class InvestorLeadsDocumentLink {
	@Id
	@GenericGenerator(name = "leads_document_link_id", strategy ="com.app.employeePortal.investorleads.generator.InvestorLeadsDocumentLinkGenerator")
	@GeneratedValue(generator = "leads_document_link_id")
	
	@Column(name="investor_leads_document_link_id")
	private String id;
	
	@Column(name="investorleadsId")
	private String investorleadsId;

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
