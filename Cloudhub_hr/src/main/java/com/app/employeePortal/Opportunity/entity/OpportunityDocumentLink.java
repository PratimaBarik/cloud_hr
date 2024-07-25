package com.app.employeePortal.Opportunity.entity;

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
@Table(name="opportunity_document_link")
public class OpportunityDocumentLink {
	
	@Id
	@GenericGenerator(name = "opportunity_document_link_id",strategy="com.app.employeePortal.Opportunity.generator.OpportunityDocumentLinkGenerator")
	@GeneratedValue(generator = "opportunity_document_link_id")
	
	@Column(name="opportunity_document_link_id")
	private String opportunity_document_link_id;
	
	@Column(name="opportunity_id")
	private String opportunity_id ;
	
	@Column(name="document_id")
	private String document_id;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="contract_Ind", nullable = false)
	private boolean contractInd =false;

	@Column(name="share_ind", nullable =false)
    private boolean shareInd = false;
	
	@Column(name="shared_user")
	private String sharedUser;

}
