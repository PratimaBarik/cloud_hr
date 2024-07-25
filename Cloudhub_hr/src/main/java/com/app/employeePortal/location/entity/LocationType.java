//package com.app.employeePortal.location.entity;
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
//@Table(name="location_type")
//public class LocationType {
//	@Id
//    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.location.generator.LocationTypeGenerator")
//    @GeneratedValue(generator = "id")
//
//    @Column(name = "location_type_id")
//    private String locationtypeId;
//
//    @Column(name = "location_type")
//    private String locationType;
//   	
//    @Column(name = "org_id")
//    private String orgId;
//
//    @Column(name = "user_id")
//    private String userId;
//    
//    @Column(name = "active_ind")
//    private boolean activeInd;
//  
//}
