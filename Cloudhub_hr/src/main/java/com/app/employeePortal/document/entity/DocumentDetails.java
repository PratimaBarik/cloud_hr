package com.app.employeePortal.document.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="document_details")
public class DocumentDetails {
	

	@Id
	@GenericGenerator(name = "document_details_id", strategy = "com.app.employeePortal.document.generator.DocumentDetailsGenerator")
	@GeneratedValue(generator = "document_details_id")
	
	@Column(name="document_details_id")
	private String document_details_id;
	
	@Column(name="document_id")
	private String document_id;
	
	@Column(name="document_name")
	private String document_name;
	
	
	@Column(name="document_type")
	private String document_type;
	
	@Column(name="document_size")
	private long document_size;
	
	@Column(name="document_path")
	private String document_path;
	
	@Lob
	@Column(name="doc_description")
	private String doc_description;
	
	@Column(name="last_update_date")
	private Date last_update_date;
	
    @Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="creator_id")
	private String creator_id;
	

	@Column(name="live_ind")
	private boolean live_ind;
	
	@Column(name="document_title")
	private String document_title;
	
	@Column(name="employee_id")
	private String employee_id;
	
	@Column(name="org_id")
	private String org_id;
	
	@Column(name="resource")
	private String resource;
	
//	@Lob
//	@Column(name="document_data")
//	private byte[] document_data;

	
	 @Column(name = "extension_type")
	 private String extensionType;
	 
	 @Column(name = "file_name")
	 private String fileName;
	
	
	
}
