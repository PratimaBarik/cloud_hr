package com.app.employeePortal.customer.entity;

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
@Table(name="customer_document_link")
public class CustomerDocumentLink {
	@Id
	@GenericGenerator(name = "customer_document_link_id", strategy ="com.app.employeePortal.customer.generator.CustomerDocumentLinkGenerator")
	@GeneratedValue(generator = "customer_document_link_id")
	
	@Column(name="customer_document_link_id")
	private String id;
	
	@Column(name="customerId")
	private String customerId;

	@Column(name="documentId")
	private String documentId;
	
	@Column(name="contract_Ind", nullable = false)
	private boolean contractInd =false;
	
	@Column(name="share_ind", nullable =false)
    private boolean shareInd = false;
	
	@Column(name="shared_user")
	private String sharedUser;

}
