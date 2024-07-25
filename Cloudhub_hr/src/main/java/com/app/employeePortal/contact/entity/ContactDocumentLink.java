package com.app.employeePortal.contact.entity;

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
@Table(name="contact_document_link")
public class ContactDocumentLink {
	@Id
	@GenericGenerator(name = "contact_document_link_id",strategy="com.app.employeePortal.contact.generator.ContactDocumentLinkGenerator")
	@GeneratedValue(generator = "contact_document_link_id")
	
	@Column(name="contact_document_link_id")
	private String contact_document_link_id;
	
	@Column(name="contact_id")
	private String contact_id ;
	
	@Column(name="document_id")
	private String document_id;
	
	@Column(name="creation_date")
	private Date creation_date;

	@Column(name="share_ind", nullable =false)
    private boolean shareInd = false;
	
	@Column(name="shared_user")
	private String sharedUser;
	
	@Column(name="contract_Ind", nullable = false)
	private boolean contractInd =false;
}
