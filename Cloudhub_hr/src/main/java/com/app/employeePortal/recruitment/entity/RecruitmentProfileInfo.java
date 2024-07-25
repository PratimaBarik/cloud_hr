package com.app.employeePortal.recruitment.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "recruitment_profile_info")
public class RecruitmentProfileInfo {
	
	@Id
	@GenericGenerator(name = "recruitment_profile_info_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentProfileInfoGenerator")
	@GeneratedValue(generator = "recruitment_profile_info_id")
	
	@Column(name = "recruitment_profile_info_id")
	private String recruitment_profile_info_id;	
	
	@Column(name="creation_date")
	private Date creation_date;
	
	

	public String getRecruitment_profile_info_id() {
		return recruitment_profile_info_id;
	}

	public void setRecruitment_profile_info_id(String recruitment_profile_info_id) {
		this.recruitment_profile_info_id = recruitment_profile_info_id;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

}
