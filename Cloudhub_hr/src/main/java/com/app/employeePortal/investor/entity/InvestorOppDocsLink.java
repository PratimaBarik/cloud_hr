package com.app.employeePortal.investor.entity;

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
@Table(name="investor_opp_document_link")
public class InvestorOppDocsLink {
    @Id
    @GenericGenerator(name = "investor_opp_document_link_id",strategy="com.app.employeePortal.investor.generator.InvestorOpportunityDocumentLinkGenerator")
    @GeneratedValue(generator = "investor_opp_document_link_id")
    @Column(name="investor_opp_document_link_id")
    private String invOppDocsLinkId;

    @Column(name="investor_opportunity_id")
    private String invOpportunityId ;

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
