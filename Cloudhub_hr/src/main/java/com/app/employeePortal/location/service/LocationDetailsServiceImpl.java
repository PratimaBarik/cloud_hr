package com.app.employeePortal.location.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.address.entity.AddressDetails;
import com.app.employeePortal.address.entity.AddressInfo;
import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.address.repository.AddressInfoRepository;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.address.service.AddressService;
import com.app.employeePortal.category.entity.Regions;
import com.app.employeePortal.category.entity.RoleType;
import com.app.employeePortal.category.repository.RegionsRepository;
import com.app.employeePortal.category.repository.RoleTypeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.location.entity.LocationDetails;
import com.app.employeePortal.location.mapper.LocationAllIndcatorsRequestMapper;
import com.app.employeePortal.location.mapper.LocationDetailsDTO;
import com.app.employeePortal.location.mapper.LocationDetailsViewDTO;
import com.app.employeePortal.location.repository.LocationDetailsRepository;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.registration.entity.Country;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.entity.Timezone;
import com.app.employeePortal.registration.entity.UserSettings;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.registration.repository.TimezoneRepository;
import com.app.employeePortal.registration.repository.UserSettingsRepository;
import com.app.employeePortal.util.Utility;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class LocationDetailsServiceImpl implements LocationDetailsService {
	@Autowired
	LocationDetailsRepository locationDetailsRepository;
	@Autowired
	UserSettingsRepository userSettingsRepository;
	@Autowired
	AddressService addressService;
	@Autowired
	AddressInfoRepository addressInfoRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	NotificationService notificationService;
	@Autowired
	TimezoneRepository timezoneRepository;
	@Autowired
	RegionsRepository regionsRepository;
	@Autowired
	RoleTypeRepository roleTypeRepository;
	@Autowired
    DepartmentRepository departmentRepository;

	private String[] location_headings = {  "Location Id", "Location Name", "Country Name" };
	
	@Override
	public LocationDetailsViewDTO saveLocationDetails(LocationDetailsDTO locationDetailsDTO) {
		LocationDetails locationDetails = new LocationDetails();
		setPropertyOnInput(locationDetailsDTO, locationDetails);
		LocationDetails dbLocationDetails = locationDetailsRepository.save(locationDetails);
		String msg = "A new location"+ dbLocationDetails.getLocationName() + " created";
		notificationService.createNotificationForAll(locationDetailsDTO.getOrgId(), locationDetailsDTO.getUserId(),"location create", msg,"location","create");
		
		return getLocationDetails(dbLocationDetails.getLocationDetailsId());
	}

	private void setPropertyOnInput(LocationDetailsDTO locationDetailsDTO, LocationDetails locationDetails) {
		locationDetails.setLocationName(locationDetailsDTO.getLocationName());
		locationDetails.setTimeZone(locationDetailsDTO.getTimeZone());
		locationDetails.setProductionInd(locationDetailsDTO.isProductionInd());
		locationDetails.setInventoryInd(locationDetailsDTO.isInventoryInd());
		locationDetails.setBillingInd(locationDetailsDTO.isBillingInd());
		locationDetails.setCorporateInd(locationDetailsDTO.isCorporateInd());
		locationDetails.setProjectInd(locationDetailsDTO.isProjectInd());
		locationDetails.setRetailInd(locationDetailsDTO.isRetailInd());
		locationDetails.setUpdatedBy(locationDetailsDTO.getUserId());
		locationDetails.setUserId(locationDetailsDTO.getUserId());
		locationDetails.setOrgId(locationDetailsDTO.getOrgId());
		locationDetails.setUpdationDate(new Date());
		locationDetails.setCreationDate(new Date());
		locationDetails.setActiveInd(true);
		locationDetails.setDefaultBillToLocInd(false);
		locationDetails.setDefaultLocInd(false);
		locationDetails.setProdManufactureInd(locationDetailsDTO.isProdManufactureInd());
		locationDetails.setRegionsId(locationDetailsDTO.getRegionsId());
		locationDetails.setThirdPartyContact(locationDetailsDTO.getThirdPartyContact());
		locationDetails.setThirdPartyContactDpt(locationDetailsDTO.getThirdPartyContactDpt());
		locationDetails.setThirdPartyInd(locationDetailsDTO.isThirdPartyInd());

		if (locationDetailsDTO.getAddress().size() > 0) {
			for (AddressMapper addressMapper : locationDetailsDTO.getAddress()) {

				/* insert to address info & address deatils & customeraddressLink */

				AddressInfo addressInfo = new AddressInfo();
				addressInfo.setCreationDate(new Date());
				AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

				String addressId = addressInfoo.getId();

				if (null != addressId) {
					AddressDetails addressDetails = new AddressDetails();
					addressDetails.setAddressId(addressId);
					addressDetails.setAddressLine1(addressMapper.getAddress1());
					addressDetails.setAddressLine2(addressMapper.getAddress2());
					addressDetails.setAddressType(addressMapper.getAddressType());
					addressDetails.setCountry(addressMapper.getCountry());
					addressDetails.setCreationDate(new Date());
					addressDetails.setStreet(addressMapper.getStreet());
					addressDetails.setCity(addressMapper.getCity());
					addressDetails.setPostalCode(addressMapper.getPostalCode());
					addressDetails.setTown(addressMapper.getTown());
					addressDetails.setState(addressMapper.getState());
					addressDetails.setLatitude(addressMapper.getLatitude());
					addressDetails.setLongitude(addressMapper.getLongitude());
					addressDetails.setLiveInd(true);
					addressDetails.setHouseNo(addressMapper.getHouseNo());
					addressRepository.save(addressDetails);

					locationDetails.setAddressId(addressId);
					locationDetails.setCountry(addressMapper.getCountry());

				}
			}
		}

		locationDetailsRepository.save(locationDetails);

	}

	@Override
	public LocationDetailsViewDTO getLocationDetails(String locationDetailsId) {

		LocationDetailsViewDTO locationDetailsViewDTO = new LocationDetailsViewDTO();

		LocationDetails locationDetails = locationDetailsRepository
				.findByLocationDetailsIdAndActiveInd(locationDetailsId, true);
		if (null != locationDetails) {
			locationDetailsViewDTO = LocationDetailsViewDTO.from(locationDetails);

			List<AddressMapper> addressList = new ArrayList<AddressMapper>();
			AddressDetails addressDetails = addressRepository
					.getAddressDetailsByAddressId(locationDetails.getAddressId());

			AddressMapper addressMapper = new AddressMapper();
			if (null != addressDetails) {
				addressMapper.setAddress1(addressDetails.getAddressLine1());
				addressMapper.setAddress2(addressDetails.getAddressLine2());
				addressMapper.setAddressType(addressDetails.getAddressType());
				addressMapper.setPostalCode(addressDetails.getPostalCode());
				addressMapper.setStreet(addressDetails.getStreet());
				addressMapper.setCity(addressDetails.getCity());
				addressMapper.setTown(addressDetails.getTown());
				addressMapper.setCountry(addressDetails.getCountry());
				addressMapper.setLatitude(addressDetails.getLatitude());
				addressMapper.setLongitude(addressDetails.getLongitude());
				addressMapper.setState(addressDetails.getState());
				addressMapper.setAddressId(addressDetails.getAddressId());
				addressMapper.setHouseNo(addressDetails.getHouseNo());
				
				Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(addressDetails.getCountry(),locationDetails.getOrgId());
				if(null!=country1) {
					addressMapper.setCountry_alpha2_code(country1.getCountry_alpha2_code());
					addressMapper.setCountry_alpha3_code(country1.getCountry_alpha3_code());
				}
				
				addressList.add(addressMapper);

			}
			locationDetailsViewDTO.setAddress(addressList);

			Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(locationDetailsViewDTO.getCountry_name(),locationDetails.getOrgId());
			if(null!=country1) {
				locationDetailsViewDTO.setCountry_alpha2_code(country1.getCountry_alpha2_code());
				locationDetailsViewDTO.setCountry_alpha3_code(country1.getCountry_alpha3_code());
				locationDetailsViewDTO.setCountry_id(country1.getCountry_id());
			}
			
			 Department department2 = departmentRepository.getDepartmentDetails(locationDetails.getThirdPartyContactDpt());
             if (null != department2) {
            	 locationDetailsViewDTO.setThirdPartyContactDpt(department2.getDepartmentName());
             }
             
             locationDetailsViewDTO.setThirdPartyContact(employeeService.getEmployeeFullName(locationDetails.getThirdPartyContact()));
			
			Timezone timeZone = timezoneRepository.findByTimezoneId(locationDetails.getTimeZone());
            if(null!=timeZone) {
            	locationDetailsViewDTO.setTimeZone(timeZone.getZoneName());
            }
            Regions regions = regionsRepository.findByRegionsId(locationDetails.getRegionsId());
    		if (null != regions) {
    			locationDetailsViewDTO.setRegions(regions.getRegions());
    			locationDetailsViewDTO.setRegionsId(regions.getRegionsId());
    		}
			
//			List<LocationDetails> list = locationDetailsRepository.findByOrgIdAndActiveInd(locationDetails.getOrgId(),true);
//			if (null != list && !list.isEmpty()) {
//				Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
//	
//				locationDetailsViewDTO.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
//				locationDetailsViewDTO.setUpdatedBy(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
//			}
		}
		return locationDetailsViewDTO;
	}

	@Override
	public List<LocationDetailsViewDTO> getLocationDetailsList(String orgId) {
		List<LocationDetailsViewDTO> locationDetailsViewDTO = new ArrayList<>();
		List<LocationDetails> list = locationDetailsRepository.findByOrgIdAndActiveInd(orgId, true);
		if (null != list && !list.isEmpty()) {
			locationDetailsViewDTO = list.stream().map(li -> getLocationDetails(li.getLocationDetailsId()))
//					.sorted(Comparator.comparing(LocationDetailsViewDTO::getCreationDate).reversed())
					.collect(Collectors.toList());

		}

//		List<LocationDetails> list1 = locationDetailsRepository.findByOrgIdAndActiveInd(orgId, true);
		if (null != list && !list.isEmpty()) {
			list.sort((p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			locationDetailsViewDTO.get(0).setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
			locationDetailsViewDTO.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
		}

		return locationDetailsViewDTO;
	}

	@Override
	public LocationDetailsViewDTO updateLocationDetails(String locationDetailsId,
			LocationDetailsDTO locationDetailsDTO) {
		LocationDetailsViewDTO locationDetailsViewDTO = new LocationDetailsViewDTO();

		LocationDetails locationDetails = locationDetailsRepository
				.findByLocationDetailsIdAndActiveInd(locationDetailsId, true);
		if (null != locationDetails) {
			if (null != locationDetailsDTO.getLocationName() && !locationDetailsDTO.getLocationName().isEmpty()) {
				locationDetails.setLocationName(locationDetailsDTO.getLocationName());
			}
			if (null != locationDetailsDTO.getTimeZone() && !locationDetailsDTO.getTimeZone().isEmpty()) {
				locationDetails.setTimeZone(locationDetailsDTO.getTimeZone());
			}
			
			if (null != locationDetailsDTO.getRegionsId() && !locationDetailsDTO.getRegionsId().isEmpty()) {
				locationDetails.setRegionsId(locationDetailsDTO.getRegionsId());
			}

			locationDetails.setProductionInd(locationDetailsDTO.isProductionInd());
			locationDetails.setInventoryInd(locationDetailsDTO.isInventoryInd());
			locationDetails.setBillingInd(locationDetailsDTO.isBillingInd());
			locationDetails.setCorporateInd(locationDetailsDTO.isCorporateInd());
			locationDetails.setProjectInd(locationDetailsDTO.isProjectInd());
			locationDetails.setRetailInd(locationDetailsDTO.isRetailInd());
			locationDetails.setDefaultBillToLocInd(locationDetailsDTO.isDefaultBillToLocInd());
			locationDetails.setDefaultLocInd(locationDetailsDTO.isDefaultLocInd());
			locationDetails.setProdManufactureInd(locationDetailsDTO.isProdManufactureInd());
			locationDetails.setThirdPartyContact(locationDetailsDTO.getThirdPartyContact());
			locationDetails.setThirdPartyContactDpt(locationDetailsDTO.getThirdPartyContactDpt());
			locationDetails.setThirdPartyInd(locationDetailsDTO.isThirdPartyInd());
			
			if (null != locationDetailsDTO.getUserId() && !locationDetailsDTO.getUserId().isEmpty()) {
				locationDetails.setUpdatedBy(locationDetailsDTO.getUserId());
			}
			locationDetails.setUpdationDate(new Date());

			if (null != locationDetailsDTO.getAddress() && !locationDetailsDTO.getAddress().isEmpty()) {
				List<AddressMapper> addressMappers = locationDetailsDTO.getAddress();
				for (AddressMapper addressMapper : addressMappers) {
					locationDetails.setCountry(addressMapper.getCountry());
					String addressId = addressMapper.getAddressId();
					if (null != addressId) {
						AddressDetails addressDetails = addressRepository.getAddressDetailsByAddressId(addressId);
						if (null != addressDetails) {
							addressDetails.setAddressLine1(addressMapper.getAddress1());
							addressDetails.setAddressLine2(addressMapper.getAddress2());
							addressDetails.setAddressType(addressMapper.getAddressType());
							addressDetails.setCountry(addressMapper.getCountry());
							addressDetails.setCreationDate(new Date());
							addressDetails.setStreet(addressMapper.getStreet());
							addressDetails.setCity(addressMapper.getCity());
							addressDetails.setPostalCode(addressMapper.getPostalCode());
							addressDetails.setTown(addressMapper.getTown());
							addressDetails.setState(addressMapper.getState());
							addressDetails.setLatitude(addressMapper.getLatitude());
							addressDetails.setLongitude(addressMapper.getLongitude());
							addressDetails.setLiveInd(true);
							addressDetails.setHouseNo(addressMapper.getHouseNo());
							addressRepository.save(addressDetails);
						}
					} else {
						AddressInfo addressInfo = new AddressInfo();
						addressInfo.setCreationDate(new Date());
						AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

						String addressId1 = addressInfoo.getId();

						if (null != addressId1) {
							AddressDetails addressDetails = new AddressDetails();
							addressDetails.setAddressId(addressId1);
							addressDetails.setAddressLine1(addressMapper.getAddress1());
							addressDetails.setAddressLine2(addressMapper.getAddress2());
							addressDetails.setAddressType(addressMapper.getAddressType());
							addressDetails.setCountry(addressMapper.getCountry());
							addressDetails.setCreationDate(new Date());
							addressDetails.setStreet(addressMapper.getStreet());
							addressDetails.setCity(addressMapper.getCity());
							addressDetails.setPostalCode(addressMapper.getPostalCode());
							addressDetails.setTown(addressMapper.getTown());
							addressDetails.setState(addressMapper.getState());
							addressDetails.setLatitude(addressMapper.getLatitude());
							addressDetails.setLongitude(addressMapper.getLongitude());
							addressDetails.setLiveInd(true);
							addressDetails.setHouseNo(addressMapper.getHouseNo());
							addressRepository.save(addressDetails);
							locationDetails.setAddressId(addressId1);
							locationDetails.setCountry(addressMapper.getCountry());
						}
					}
				}
			}
			LocationDetails details = locationDetailsRepository.save(locationDetails);
			
			String msg = details.getLocationName() + " location updated.";
			notificationService.createNotificationForAll(locationDetailsDTO.getOrgId(), locationDetailsDTO.getUserId(),"location update", msg,"location","update");
			locationDetailsViewDTO = getLocationDetails(details.getLocationDetailsId());
		}
		return locationDetailsViewDTO;
	}

	@Override
	public ByteArrayInputStream exportLocationListToExcel(List<LocationDetailsViewDTO> locationList) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("location");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < location_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(location_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != locationList && !locationList.isEmpty()) {
			for (LocationDetailsViewDTO location : locationList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(location.getLocationDetailsId());
				row.createCell(1).setCellValue(location.getLocationName());
				row.createCell(2).setCellValue(location.getCountry_name());

			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < location_headings.length; i++) {
			sheet.autoSizeColumn(i);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(outputStream.toByteArray());

	}

	@Override
	public List<LocationDetailsViewDTO> getLocationDetailsListByUserId(String userId) {
		List<LocationDetailsViewDTO> locationDetailsViewDTO = new ArrayList<>();
		List<LocationDetails> list = locationDetailsRepository.findByUserIdAndActiveInd(userId, true);
		if (null != list && !list.isEmpty()) {
			locationDetailsViewDTO = list.stream().map(li -> getLocationDetails(li.getLocationDetailsId()))
					.sorted(Comparator.comparing(LocationDetailsViewDTO::getCreationDate).reversed())
					.collect(Collectors.toList());

		}
		return locationDetailsViewDTO;
	}

	@Override
	public Map<String, Long> getAllLocationCount(String orgId) {
		Map<String,Long> map=new HashMap<>();
		Long count=locationDetailsRepository.countByOrgIdAndActiveInd(orgId, true);
		map.put("locCount",count);
		return map;
	}

	@Override
	public void deleteLocationDetails(String locationDetailsId, String orgId,String userId) {		
		LocationDetails locationDetails = locationDetailsRepository
				.findByLocationDetailsIdAndActiveInd(locationDetailsId, true);
		if (null != locationDetails) {
			locationDetails.setActiveInd(false);
			locationDetailsRepository.save(locationDetails);
			AddressDetails addressDetails = addressRepository
					.getAddressDetailsByAddressId(locationDetails.getAddressId());
			if (null != addressDetails) {
				addressDetails.setLiveInd(false);
			addressRepository.save(addressDetails);
			}
			String msg = locationDetails.getLocationName() + " location deleted";
			notificationService.createNotificationForAll(orgId, userId,"location delete", msg,"location","delete");
			}
	}

	@Override
	public LocationDetailsViewDTO updateLocationIndicators(LocationAllIndcatorsRequestMapper requestMapper) {
		LocationDetailsViewDTO id=new LocationDetailsViewDTO();
		LocationDetails locationDetails = locationDetailsRepository
				.findByLocationDetailsIdAndActiveInd(requestMapper.getLocationId(), true);
		if (null != locationDetails) {
			
			if(requestMapper.getType().equalsIgnoreCase("billing")) {
				if(requestMapper.isValue()) {
					locationDetails.setBillingInd(true);
				}else {
					locationDetails.setBillingInd(false);
					
				}
			}
			
			if(requestMapper.getType().equalsIgnoreCase("corporate")) {
				if(requestMapper.isValue()) {
					locationDetails.setCorporateInd(true);;
				}else {
					locationDetails.setCorporateInd(false);
					
				}
			}
			
			if(requestMapper.getType().equalsIgnoreCase("defaultBillToLoc")) {
				if(requestMapper.isValue()) {
					locationDetails.setDefaultBillToLocInd(true);
				}else {
					locationDetails.setDefaultBillToLocInd(false);
					
				}
			}
			
			if(requestMapper.getType().equalsIgnoreCase("DefaultLoc")) {
				if(requestMapper.isValue()) {
					locationDetails.setDefaultLocInd(true);
				}else {
					locationDetails.setDefaultLocInd(false);
					
				}
			}
			
			if(requestMapper.getType().equalsIgnoreCase("inventory")) {
				if(requestMapper.isValue()) {
					locationDetails.setInventoryInd(true);
				}else {
					locationDetails.setInventoryInd(false);
					
				}
			}
			
			if(requestMapper.getType().equalsIgnoreCase("prodManufacture")) {
				if(requestMapper.isValue()) {
					locationDetails.setProdManufactureInd(true);
				}else {
					locationDetails.setProdManufactureInd(false);
					
				}
			}
			
			if(requestMapper.getType().equalsIgnoreCase("production")) {
				if(requestMapper.isValue()) {
					locationDetails.setProductionInd(true);
				}else {
					locationDetails.setProductionInd(false);	
				}
			}
			
			if(requestMapper.getType().equalsIgnoreCase("Project")) {
				if(requestMapper.isValue()) {
					locationDetails.setProjectInd(true);
				}else {
					locationDetails.setProjectInd(false);	
				}
			}
			
			if(requestMapper.getType().equalsIgnoreCase("retail")) {
				if(requestMapper.isValue()) {
					locationDetails.setRetailInd(true);
				}else {
					locationDetails.setRetailInd(false);	
				}
			}
			
				id=getLocationDetails(locationDetailsRepository.save(locationDetails).getLocationDetailsId());
			}

		return id;
	}

	@Override
	public boolean locationNameExist(String locationName, String regionsId, String orgId) {
		List<LocationDetails> list = locationDetailsRepository.findByLocationNameAndRegionsIdAndOrgIdAndActiveInd(locationName, regionsId, orgId, true);
		if (null != list && !list.isEmpty()) {

				return true;
			
		}
		return false;	
	}

	@Override
	public List<LocationDetailsViewDTO> getAllDeteleLocationList(String orgId) {
		List<LocationDetailsViewDTO> locationDetailsViewDTO = new ArrayList<>();
		
			locationDetailsViewDTO = locationDetailsRepository.findByOrgIdAndActiveInd(orgId, false).stream()
            .map(this::getLocationDetailsById)
            .collect(Collectors.toList());
		
		return locationDetailsViewDTO;
	}

	@Override
	public LocationDetailsViewDTO updateLocationReInState(String locationDetailsId,
			LocationDetailsDTO locationDetailsDTO) {
		LocationDetailsViewDTO locationDetailsViewDTO = new LocationDetailsViewDTO();

		LocationDetails locationDetails = locationDetailsRepository
				.findByLocationDetailsIdAndActiveInd(locationDetailsId, false);
		if (null != locationDetails) {
			locationDetails.setActiveInd(true);	
			
			if (null != locationDetailsDTO.getUserId() && !locationDetailsDTO.getUserId().isEmpty()) {
				locationDetails.setUpdatedBy(locationDetailsDTO.getUserId());
			}
			locationDetails.setUpdationDate(new Date());
			locationDetailsRepository.save(locationDetails);
			AddressDetails addressDetails = addressRepository
					.getAddressDetailsByAddressId(locationDetails.getAddressId());
			if (null != addressDetails) {
				addressDetails.setLiveInd(true);
			addressRepository.save(addressDetails);
			}
		locationDetailsViewDTO = getLocationDetails(locationDetailsId);
		}
	return locationDetailsViewDTO;
	}

	@Override
	public Map getDeleteLocationCountList(String orgId) {
		Map map=new HashMap<>();
		List<LocationDetails> list = locationDetailsRepository.findByOrgIdAndActiveInd(orgId, false);
		map.put("locCount",list.size());
		return map;
	}
	
	private LocationDetailsViewDTO getLocationDetailsById(LocationDetails locationDetails) {

		LocationDetailsViewDTO locationDetailsViewDTO = new LocationDetailsViewDTO();

		if (null != locationDetails) {
			locationDetailsViewDTO = LocationDetailsViewDTO.from(locationDetails);

			List<AddressMapper> addressList = new ArrayList<AddressMapper>();
			AddressDetails addressDetails = addressRepository
					.getAddressDetailsByAddressId(locationDetails.getAddressId());

			AddressMapper addressMapper = new AddressMapper();
			if (null != addressDetails) {
				addressMapper.setAddress1(addressDetails.getAddressLine1());
				addressMapper.setAddress2(addressDetails.getAddressLine2());
				addressMapper.setAddressType(addressDetails.getAddressType());
				addressMapper.setPostalCode(addressDetails.getPostalCode());
				addressMapper.setStreet(addressDetails.getStreet());
				addressMapper.setCity(addressDetails.getCity());
				addressMapper.setTown(addressDetails.getTown());
				addressMapper.setCountry(addressDetails.getCountry());
				addressMapper.setLatitude(addressDetails.getLatitude());
				addressMapper.setLongitude(addressDetails.getLongitude());
				addressMapper.setState(addressDetails.getState());
				addressMapper.setAddressId(addressDetails.getAddressId());
				addressMapper.setHouseNo(addressDetails.getHouseNo());
				
				Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(addressDetails.getCountry(),locationDetails.getOrgId());
				if(null!=country1) {
					addressMapper.setCountry_alpha2_code(country1.getCountry_alpha2_code());
					addressMapper.setCountry_alpha3_code(country1.getCountry_alpha3_code());
				}
				
				addressList.add(addressMapper);

			}
			locationDetailsViewDTO.setAddress(addressList);

			Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(locationDetailsViewDTO.getCountry_name(),locationDetails.getOrgId());
			if(null!=country1) {
				locationDetailsViewDTO.setCountry_alpha2_code(country1.getCountry_alpha2_code());
				locationDetailsViewDTO.setCountry_alpha3_code(country1.getCountry_alpha3_code());
				locationDetailsViewDTO.setCountry_id(country1.getCountry_id());
			}
			
			Timezone timeZone = timezoneRepository.findByTimezoneId(locationDetails.getTimeZone());
            if(null!=timeZone) {
            	locationDetailsViewDTO.setTimeZone(timeZone.getZoneName());
            }
            Regions regions = regionsRepository.findByRegionsId(locationDetails.getRegionsId());
    		if (null != regions) {
    			locationDetailsViewDTO.setRegions(regions.getRegions());
    			locationDetailsViewDTO.setRegionsId(regions.getRegionsId());
    		}
			
		}
		return locationDetailsViewDTO;
	}
	
	@Override
	public List<LocationDetailsViewDTO> getLocationDetailsListByOrgIdWhoseProductionIndAndInventoryIndTrue(String orgId) {
		List<LocationDetailsViewDTO> locationDetailsViewDTO = new ArrayList<>();
		List<LocationDetails> list = locationDetailsRepository.findByOrgIdAndActiveIndAndProductionIndAndInventoryInd(orgId);
		if (null != list && !list.isEmpty()) {
			locationDetailsViewDTO = list.stream().map(li -> getLocationDetails(li.getLocationDetailsId()))
//					.sorted(Comparator.comparing(LocationDetailsViewDTO::getCreationDate).reversed())
					.collect(Collectors.toList());

		}

//		List<LocationDetails> list1 = locationDetailsRepository.findByOrgIdAndActiveInd(orgId, true);
		if (null != list && !list.isEmpty()) {
			list.sort((p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			locationDetailsViewDTO.get(0).setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
			locationDetailsViewDTO.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
		}

		return locationDetailsViewDTO;
	}
	
}
