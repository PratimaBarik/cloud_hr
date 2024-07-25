//package com.app.employeePortal.location.mapper;
//
//
//
//import com.app.employeePortal.location.entity.LocationType;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class LocationTypeDTO {
//
//	private String locationtypeId;
//
//	private String locationType;
//
//	private String orgId;
//
//	private String userId;
//
//	private boolean activeInd;
//
//	public LocationTypeDTO(String locationtypeId, String locationType, String orgId, String userId,boolean activeInd) {
//		super();
//		this.locationtypeId = locationtypeId;
//		this.locationType = locationType;
//		this.orgId = orgId;
//		this.userId = userId;
//		this.activeInd=activeInd;
//	}
//
//	public static LocationTypeDTO from(LocationType locationType) {
//		LocationTypeDTO viewDTO;
//		if (locationType == null) {
//			viewDTO = null;
//		} else {
//			viewDTO = new LocationTypeDTO(locationType.getLocationtypeId(), locationType.getLocationType(),
//					locationType.getOrgId(), locationType.getUserId(),locationType.isActiveInd());
//		}
//		return viewDTO;
//
//	}
//
//}
