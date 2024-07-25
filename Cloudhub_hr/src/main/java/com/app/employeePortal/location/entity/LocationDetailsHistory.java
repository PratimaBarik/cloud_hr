//package com.app.employeePortal.location.entity;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.GenericGenerator;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "location_details_history")
//public class LocationDetailsHistory{
//
//	@Id
//	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.location.generator.LocationDetailsHistoryGenerator")
//    @GeneratedValue(generator = "id")
//	
//	@Column(name="location_history_id")
//	private String id;
//	
//	@Column(name="location_details_id")
//	private String locationDetailsId;
//	
//	@Column(name="name")
//	private String name;
//	
//	@Column(name="start_date")
//	 private Date startDate;
//	
//	@Column(name="end_date")
//	private Date endDate;
//	 
//	@Column(name = "creation_date")
//	private Date creationDate;
//	
//	@Column(name = "updated_by")
//	private String updatedBy;
//	 
//	@Column(name = "updation_date")
//	private Date updationDate;
//	
//}
