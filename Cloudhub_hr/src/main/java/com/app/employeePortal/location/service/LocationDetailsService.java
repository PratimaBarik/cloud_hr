package com.app.employeePortal.location.service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import com.app.employeePortal.location.mapper.LocationAllIndcatorsRequestMapper;
import com.app.employeePortal.location.mapper.LocationDetailsDTO;
import com.app.employeePortal.location.mapper.LocationDetailsViewDTO;

public interface LocationDetailsService {

	public LocationDetailsViewDTO saveLocationDetails(LocationDetailsDTO locationDetailsDTO);

	public LocationDetailsViewDTO getLocationDetails(String locationDetailsId);

	public List<LocationDetailsViewDTO> getLocationDetailsList(String orgId);

	public LocationDetailsViewDTO updateLocationDetails(String locationDetailsId, LocationDetailsDTO locationDetailsDTO);

	public ByteArrayInputStream exportLocationListToExcel(List<LocationDetailsViewDTO> locationList);

	public List<LocationDetailsViewDTO> getLocationDetailsListByUserId(String userId);

	Map<String,Long> getAllLocationCount(String orgId);

	public void deleteLocationDetails(String locationDetailsId,String orgId,String userId);

	public LocationDetailsViewDTO updateLocationIndicators(LocationAllIndcatorsRequestMapper requestMapper);

	public boolean locationNameExist(String locationName, String regionsId, String orgId);

	public List<LocationDetailsViewDTO> getAllDeteleLocationList(String orgId);

	public LocationDetailsViewDTO updateLocationReInState(String locationDetailsId, LocationDetailsDTO locationDetailsDTO);

//	public Map<String,Long> getLocationCountList();

	public Map getDeleteLocationCountList(String orgId);

	public List<LocationDetailsViewDTO> getLocationDetailsListByOrgIdWhoseProductionIndAndInventoryIndTrue(
			String orgId);
}
