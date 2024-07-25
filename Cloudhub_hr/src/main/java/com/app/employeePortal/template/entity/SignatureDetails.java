package com.app.employeePortal.template.entity;

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
@Table(name="signature_details")
public class SignatureDetails {
	
	@Id
	@GenericGenerator(name = "signature_id", strategy = "com.app.employeePortal.template.generator.SignatureGenerator")
	@GeneratedValue(generator = "signature_id")

	@Column(name="signature_id")
	private String signature_id;
	
	@Column(name="user_id")
	private String user_id;
	
	@Column(name="org_id")
	private String org_id;
	
	
	@Column(name="signature")
	private String signature;
	
	@Column(name="type")
	private String type;
		
	@Column(name="creation_date")
	private Date creation_date;
		
	@Column(name="live_ind")
	private boolean live_ind;

	
	
	

}
