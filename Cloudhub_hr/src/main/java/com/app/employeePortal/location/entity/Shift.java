//
//package com.app.employeePortal.location.entity;
//
//
//import java.util.Date;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.ForeignKey;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.GenericGenerator;
//
//import com.app.employeePortal.registration.entity.UserSettings;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "shift_details")
//public class Shift {
//
//	@Id
//	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.location.generator.ShiftGenerator")
//    @GeneratedValue(generator = "id")
//	
//	@Column(name="shift_id")
//	private String id;
//	
//	@Column(name="shift_name")
//	private String shiftName;
//	
//	@Column(name="start_time")
//	private String startTime;
//	 
//	@Column(name="end_time")
//	private String endTime;
//	
//	@Column(name="start_date")
//	private Date startDate;
//	 
//	@Column(name="end_date")
//	private Date endDate;
//	
//	@Column(name="creation_date")
//	private Date creationDate;
//	
//	@Column(name="created_by")
//	private String createdBy;
//	 
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private UserSettings user;
//	
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private LocationDetails locationDetails;
//	 
//}