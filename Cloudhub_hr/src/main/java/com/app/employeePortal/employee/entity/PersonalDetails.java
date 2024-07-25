package com.app.employeePortal.employee.entity;

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
@Table(name="personal_details")
public class PersonalDetails {

	@Id
	@GenericGenerator(name = "personal_details_id", strategy = "com.app.employeePortal.employee.generator.PersonalDetailsGenerator")
	@GeneratedValue(generator = "personal_details_id")
	
	@Column(name="personal_details_id")
	private String id;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="salutation")
	private String salutation;

	
	@Column(name="contact_first_name")
	private String contactFirstName;
	
	@Column(name="contact_middle_name")
	private String contactMiddleName;
	
	@Column(name="contact_last_name")
	private String contactLastName;
	
	@Column(name="country_dial_code")
	private String countryDialCode;
	
	@Column(name="country_dial_code1")
	private String countryDialCode1;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="blood_group")
	private String bloodGroup;
	
	@Column(name="date_of_birth")
	private Date dateOfBirth;
	
	@Column(name="creation_date")
	private Date creationDate;
	

	@Column(name="live_ind")
	private boolean liveInd;

	@Column(name="document_type_name")
	private String document_type_name;
	
	
	
}
