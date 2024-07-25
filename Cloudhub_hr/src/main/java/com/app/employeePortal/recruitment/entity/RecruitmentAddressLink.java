package com.app.employeePortal.recruitment.entity;

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
@Table(name = "Recruitment_address_link")
public class RecruitmentAddressLink {

	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentAddressLinkGenerator")
    @GeneratedValue(generator = "id")
	
	@Column(name="recruitment_address_link_id")
	private String recruitmentAddressLinkId;

	@Column(name = "recruitment_id")
	private String recruitmentId;	
	
	@Column(name="address_id")
	private String addressId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
}
