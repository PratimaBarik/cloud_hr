package com.app.employeePortal.holiday.entity;

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
@Table(name="user_optional_link")
public class UserOptionalHolidayLink {
	
	@Id
	@GenericGenerator(name = "user_optional_link_id", strategy = "com.app.employeePortal.holiday.generator.UserOptionalLinkGenerator")
	@GeneratedValue(generator = "user_optional_link_id")
	
	@Column(name="userOptionalLinkId")
	private String userOptionalLinkId;
	
	@Column(name="countryId")
	private String countryId;
	
	@Column(name="holidayId")
	private String holidayId;
	
	@Column(name="userId")
	private String userId;
	
	@Column(name="orgId")
	private String orgId;
	
	@Column(name="year")
	private int year;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="updation_date")
	private Date updationDate;

	@Column(name="updatedBy")
	private String updatedBy;
}
