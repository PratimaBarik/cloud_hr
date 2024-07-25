package com.app.employeePortal.investor.entity;

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
@Table(name="investor_document_link")
public class InvestorDocumentLink {
    @Id
    @GenericGenerator(name = "investor_document_link_id", strategy ="com.app.employeePortal.investor.generator.InvestorDocumentLinkGenerator")
    @GeneratedValue(generator = "investor_document_link_id")

    @Column(name="investor_document_link_id")
    private String id;

    @Column(name="investor_id")
    private String investorId;

    @Column(name="documentId")
    private String documentId;
    
    @Column(name="share_ind", nullable =false)
    private boolean shareInd = false;
	
	@Column(name="shared_user")
	private String sharedUser;
	
	@Column(name="contract_Ind", nullable = false)
	private boolean contractInd =false;
}
