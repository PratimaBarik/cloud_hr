//package com.app.employeePortal.location.mapper;
//import com.app.employeePortal.location.entity.Shift;
//import com.app.employeePortal.util.Utility;
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class ShiftDTO {
//
//	@JsonProperty("shiftId")
//	private String shiftId;
//	
//	@JsonProperty("shiftName")
//	private String shiftName;
//	 
//	@JsonProperty("startTime")
//	private String startTime;
//
//	@JsonProperty("endTime")
//    private String endTime;
//  
//	@JsonProperty("userId")
//	private String userId;
//	
//	@JsonProperty("locationDetailsId")
//	private String locationDetailsId;
//	
//	@JsonProperty("startDate")
//    private String startDate;
//    
//    @JsonProperty("endDate")
//    private String endDate;
//    
//	@JsonProperty("creationDate")
//	private String creationDate;
//
//	@JsonProperty("createdBy")
//	private String createdBy;
//	
//	@JsonProperty("createdByName")
//	private String createdByName;
//
//    @JsonProperty("locationDetailsName")
//    private String locationDetailsName;
//    
//	 public ShiftDTO(String shiftId,String shiftName,String startTime,String endTime,String locationDetailsId,
//			 String startDate,String endDate,String creationDate,String createdBy) {
//	        this.shiftId = shiftId;
//	        this.shiftName = shiftName;
//	        this.startTime = startTime;
//	        this.endTime = endTime;
//	        //this.userId = userId;
//	        this.locationDetailsId=locationDetailsId;
//	        this.startDate = startDate;
//	        this.endDate = endDate;
//	        this.creationDate=creationDate;
//	        this.createdBy=createdBy;
//	       
//	 } 
//	 public static ShiftDTO from(Shift shift) {
//		 ShiftDTO viewDTO;
//	        if (shift == null) {
//	            viewDTO = null;
//	        } else {
//	        	
//	        	String startDate= null;
//	        	if(null!=shift.getStartDate()) {
//	        		
//	        		startDate = Utility.getISOFromDate(shift.getStartDate());
//	        	}
//	        	String endDate= null;
//	        	if(null!=shift.getEndDate()) {
//	        		
//	        		endDate = Utility.getISOFromDate(shift.getEndDate());
//	        	}
//	        	String creationDate= null;
//	        	if(null!=shift.getCreationDate()) {
//	        		
//	        		creationDate = Utility.getISOFromDate(shift.getCreationDate());
//	        	}
//	            viewDTO = new ShiftDTO(shift.getId(),shift.getShiftName(),shift.getStartTime(), shift.getEndTime(),
//	            		shift.getLocationDetails().getId(),startDate,endDate,creationDate,shift.getCreatedBy());
//	        }
//	        return viewDTO;
//	    }
//}
