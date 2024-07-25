package com.app.employeePortal.document.entity;

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
@Table(name="document_info")
public class DocumentInfo {
	

	@Id
	@GenericGenerator(name = "document_id", strategy = "com.app.employeePortal.document.generator.DocumentInfoGenerator")
	@GeneratedValue(generator = "document_id")
	
	@Column(name="document_id")
	private String document_id;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="creator_id")
	private String creator_id;

	

	
}
