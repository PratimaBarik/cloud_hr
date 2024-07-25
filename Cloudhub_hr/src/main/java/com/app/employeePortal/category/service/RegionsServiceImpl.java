package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.app.employeePortal.category.entity.LobDetails;
import com.app.employeePortal.category.entity.PerformanceManagement;
import com.app.employeePortal.category.entity.Regions;
import com.app.employeePortal.category.entity.RegionsTarget;
import com.app.employeePortal.category.mapper.LeadsCategoryMapper;
import com.app.employeePortal.category.mapper.RegionsDropDownMapper;
import com.app.employeePortal.category.mapper.RegionsMapper;
import com.app.employeePortal.category.mapper.RegionsTargetDashBoardMapper;
import com.app.employeePortal.category.mapper.RegionsTargetMapper;
import com.app.employeePortal.category.mapper.UserKpiDashBoardResponseMapper;
import com.app.employeePortal.category.repository.LobDetailsRepository;
import com.app.employeePortal.category.repository.PerformanceManagementRepository;
import com.app.employeePortal.category.repository.RegionsRepository;
import com.app.employeePortal.category.repository.RegionsTargetRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.UserKpiLink;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.UserKpiLinkRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.location.entity.LocationDetails;
import com.app.employeePortal.location.repository.LocationDetailsRepository;
import com.app.employeePortal.registration.entity.Currency;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.repository.CurrencyRepository;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class RegionsServiceImpl implements RegionsService {

	@Autowired
	RegionsRepository regionsRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	RegionsTargetRepository regionsTargetRepository;
	@Autowired
	CurrencyRepository currencyRepository;
	@Autowired
	PerformanceManagementRepository performanceManagementRepository;
	@Autowired
	LocationDetailsRepository locationDetailsRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
    EmployeeRepository employeeRepository;
	@Autowired
	UserKpiLinkRepository userKpiLinkRepository;
	@Autowired
	LobDetailsRepository lobRepository;
	private String[] headings = {"Name"};
	
	@Override
	public RegionsMapper createRegions(RegionsMapper requestMapper) {
		String regionsId = null;
		if (requestMapper != null) {
			Regions regions = new Regions();
			regions.setCreationDate(new Date());
			regions.setLiveInd(true);
			regions.setRegions(requestMapper.getRegions());
			regions.setOrgId(requestMapper.getOrgId());
			regions.setUpdatedBy(requestMapper.getUserId());
			regions.setUpdationDate(new Date());
			regions.setUserId(requestMapper.getUserId());

			regionsId = regionsRepository.save(regions).getRegionsId();
					

		}
		RegionsMapper resultMapper = getRegionsByRegionsId(regionsId);
		return resultMapper;
	}



	@Override
	public RegionsMapper getRegionsByRegionsId(String regionsId) {
		Regions regions = regionsRepository.findByRegionsId(regionsId);
		RegionsMapper resultMapper = new RegionsMapper();

		if (null != regions) {

			resultMapper.setCreationDate(Utility.getISOFromDate(regions.getCreationDate()));
			resultMapper.setLiveInd(regions.isLiveInd());
			resultMapper.setRegions(regions.getRegions());
			resultMapper.setOrgId(regions.getOrgId());
			resultMapper.setUpdatedBy(employeeService.getEmployeeFullName(regions.getUpdatedBy()));
			resultMapper.setUpdationDate(Utility.getISOFromDate(regions.getUpdationDate()));
			resultMapper.setUserId(regions.getUserId());
			resultMapper.setRegionsId(regions.getRegionsId());
		}

		return resultMapper;
	}



	@Override
	public List<RegionsMapper> getRegionsByOrgId(String orgId) {
		List<RegionsMapper> resultList = new ArrayList<>();
		List<Regions> list = regionsRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list && !list.isEmpty()) {
			resultList = list.stream().map(li -> getRegionsByRegionsId(li.getRegionsId()))
					.collect(Collectors.toList());
		}
		if (null != resultList && !resultList.isEmpty()) {
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<Regions> list1 = regionsRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultList.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultList.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		}
		return resultList;
	}



	@Override
	public RegionsMapper updateRegions(String regionsId, RegionsMapper requestMapper) {
		Regions regions = regionsRepository.findByRegionsId(regionsId);
		if (null != regions) {

			regions.setCreationDate(new Date());
			regions.setRegions(requestMapper.getRegions());
			regions.setOrgId(requestMapper.getOrgId());
			regions.setUpdatedBy(requestMapper.getUserId());
			regions.setUpdationDate(new Date());
			regions.setUserId(requestMapper.getUserId());
			regionsRepository.save(regions);
		}
		RegionsMapper resultMapper = getRegionsByRegionsId(regionsId);
		return resultMapper;
	}



	@Override
	public void deleteRegionsByRegionsId(String regionsId, String userId) {
		
		Regions regions = regionsRepository.findByRegionsId(regionsId);
		if (null != regions) {
			regions.setUpdationDate(new Date());
			regions.setUpdatedBy(userId);
			regions.setLiveInd(false);
			regionsRepository.save(regions);
		}
		
	}



	@Override
	public List<RegionsMapper> getRegionsByRegionsorgLevel(String regions, String orgId) {
		List<Regions> list = regionsRepository.findByRegionsContainingAndLiveIndAndOrgId(regions,true,orgId);
		List<RegionsMapper> resultList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				RegionsMapper mapper = getRegionsByRegionsId(
						li.getRegionsId());
				if (null != mapper) {
					resultList.add(mapper);
				}
				return resultList;
			}).collect(Collectors.toList());
		}
		if (null != resultList && !resultList.isEmpty()) {
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<Regions> list1 = regionsRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultList.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultList.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		}
		return resultList;
	}



	@Override
	public List<RegionsDropDownMapper> getRegionsByOrgIdForDropDown(String orgId) {
		List<RegionsDropDownMapper> resultMapper = new ArrayList<>();
		List<Regions> list = regionsRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				RegionsDropDownMapper mapper = new RegionsDropDownMapper();
				mapper.setRegions(li.getRegions());
				mapper.setRegionsId(li.getRegionsId());
				resultMapper.add(mapper);
			return mapper;
				
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}



	@Override
	public HashMap getRegionsCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<Regions> list = regionsRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("RegionsCount", list.size());
		return map;
	}



	@Override
	public RegionsTargetMapper createRegionsTarget(RegionsTargetMapper requestMapper) {
		
		RegionsTarget target = regionsTargetRepository.findByRegionsIdAndYearAndQuarter(requestMapper.getRegionsId(),requestMapper.getYear(), requestMapper.getQuarter());
		if(null!=target) {
			target.setCreationDate(new Date());
			target.setFulfilment(requestMapper.getFulfilment());
			target.setInvestment(requestMapper.getInvestment());
			target.setInvestmentCurrency(requestMapper.getInvestmentCurrency());
			target.setLiveInd(true);
			target.setOrgId(requestMapper.getOrgId());
			target.setRegionsId(requestMapper.getRegionsId());
			target.setSales(requestMapper.getSales());
			target.setSalesCurrency(requestMapper.getSalesCurrency());
			target.setUserId(requestMapper.getUserId());
			target.setYear(requestMapper.getYear());
			target.setQuarter(requestMapper.getQuarter());
			target.setKpiFullfillment(requestMapper.getKpiFullfillment());
			target.setKpiInvestment(requestMapper.getKpiInvestment());
			target.setKpiSales(requestMapper.getKpiSales());
			regionsTargetRepository.save(target);
		}else {
			RegionsTarget target1 = new RegionsTarget();
			target1.setCreationDate(new Date());
			target1.setFulfilment(requestMapper.getFulfilment());
			target1.setInvestment(requestMapper.getInvestment());
			target1.setInvestmentCurrency(requestMapper.getInvestmentCurrency());
			target1.setLiveInd(true);
			target1.setOrgId(requestMapper.getOrgId());
			target1.setRegionsId(requestMapper.getRegionsId());
			target1.setSales(requestMapper.getSales());
			target1.setSalesCurrency(requestMapper.getSalesCurrency());
			target1.setUserId(requestMapper.getUserId());
			target1.setYear(requestMapper.getYear());
			target1.setQuarter(requestMapper.getQuarter());
			target1.setKpiFullfillment(requestMapper.getKpiFullfillment());
			target1.setKpiInvestment(requestMapper.getKpiInvestment());
			target1.setKpiSales(requestMapper.getKpiSales());
			regionsTargetRepository.save(target1);
		}
		return getRegionsTargetByRegionsId(requestMapper.getRegionsId(),requestMapper.getYear(), requestMapper.getQuarter());
	}

	@Override
	public RegionsTargetMapper getRegionsTargetByRegionsId(String regionsId, double year, String quarter) {
		RegionsTargetMapper regionsTargetMapper = new RegionsTargetMapper();
		RegionsTarget target = regionsTargetRepository.findByRegionsIdAndYearAndQuarter(regionsId,year,quarter);
		if(null!=target) {
			regionsTargetMapper.setCreationDate(Utility.getISOFromDate(target.getCreationDate()));
			regionsTargetMapper.setFulfilment(target.getFulfilment());
			regionsTargetMapper.setInvestment(target.getInvestment());
			Currency currency = currencyRepository.getByCurrencyId(target.getInvestmentCurrency());
			if (null != currency) {
				regionsTargetMapper.setInvestmentCurrency(currency.getCurrencyName());
			}
			regionsTargetMapper.setLiveInd(true);
			regionsTargetMapper.setOrgId(target.getOrgId());
			regionsTargetMapper.setRegionsId(target.getRegionsId());
			regionsTargetMapper.setSales(target.getSales());
			regionsTargetMapper.setYear(target.getYear());
			
			Currency currency1 = currencyRepository.getByCurrencyId(target.getSalesCurrency());
			if (null != currency1) {
				regionsTargetMapper.setSalesCurrency(currency1.getCurrencyName());
			}
			
			PerformanceManagement PerformanceManagement = performanceManagementRepository
					.findByPerformanceManagementId(target.getKpiFullfillment());
			if (null != PerformanceManagement) {
				regionsTargetMapper.setKpiFullfillment(PerformanceManagement.getPerformanceManagementId());
				regionsTargetMapper.setKpiFullfillmentName(PerformanceManagement.getKpi());
			}
			
			PerformanceManagement PerformanceManagement1 = performanceManagementRepository
					.findByPerformanceManagementId(target.getKpiInvestment());
			if (null != PerformanceManagement1) {
				regionsTargetMapper.setKpiInvestment(PerformanceManagement1.getPerformanceManagementId());
				regionsTargetMapper.setKpiInvestmentName(PerformanceManagement1.getKpi());
			}
			
			PerformanceManagement PerformanceManagement2 = performanceManagementRepository
					.findByPerformanceManagementId(target.getKpiSales());
			if (null != PerformanceManagement2) {
				regionsTargetMapper.setKpiSales(PerformanceManagement2.getPerformanceManagementId());
				regionsTargetMapper.setKpiSalesName(PerformanceManagement2.getKpi());
			}
			
			regionsTargetMapper.setUserId(target.getUserId());
			regionsTargetMapper.setUpdatedBy(employeeService.getEmployeeFullName(target.getUserId()));
			regionsTargetMapper.setUpdationDate(Utility.getISOFromDate(target.getCreationDate()));
		}
		return regionsTargetMapper;
	}



	@Override
	public List<RegionsTargetMapper> getRegionsTargetByYearAndQuarterForDashBoard(double year, String quarter, String orgId) {
		List<RegionsTargetMapper> resultMapper = new ArrayList<>();
		List<Regions> list = regionsRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				
		RegionsTarget target = regionsTargetRepository.findByRegionsIdAndYearAndQuarter(li.getRegionsId(),year,quarter);
		RegionsTargetMapper regionsTargetMapper = new RegionsTargetMapper();
		if(null!=target) {
			regionsTargetMapper.setCreationDate(Utility.getISOFromDate(target.getCreationDate()));
			regionsTargetMapper.setFulfilment(target.getFulfilment());
			regionsTargetMapper.setInvestment(target.getInvestment());
			Currency currency = currencyRepository.getByCurrencyId(target.getInvestmentCurrency());
			if (null != currency) {
				regionsTargetMapper.setInvestmentCurrency(currency.getCurrencyName());
			}
			regionsTargetMapper.setLiveInd(true);
			regionsTargetMapper.setOrgId(target.getOrgId());
			
			regionsTargetMapper.setSales(target.getSales());
			regionsTargetMapper.setYear(target.getYear());
			Currency currency1 = currencyRepository.getByCurrencyId(target.getSalesCurrency());
			if (null != currency1) {
				regionsTargetMapper.setSalesCurrency(currency1.getCurrencyName());
			}
			regionsTargetMapper.setUserId(target.getUserId());
		}
		Regions regions = regionsRepository.findByRegionsId(li.getRegionsId());
		if (null != regions) {
		regionsTargetMapper.setRegionsId(regions.getRegionsId());
		regionsTargetMapper.setRegions(regions.getRegions());
		}
		resultMapper.add(regionsTargetMapper);
		return regionsTargetMapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}



	@Override
	public List<RegionsTargetDashBoardMapper> getRegionsTargetByYearAndQuarterByRegionsIdForDashBoard(int year,
			String quarter, String orgId, String regionsId, String type) {
		List<RegionsTargetDashBoardMapper> resultList = new ArrayList<>();
		System.out.println("quarter)==="+quarter);
		System.out.println("orgId==="+orgId);
		System.out.println("regionsId==="+regionsId);
		System.out.println("type==="+type);
		System.out.println("year==="+year);
		RegionsTarget target = regionsTargetRepository.findByRegionsIdAndYearAndQuarter(regionsId,year,quarter);
		if(null!=target) {
			System.out.println("target.getKpiSales()==="+target.getKpiSales());
			List<LocationDetails> list = locationDetailsRepository.findByRegionsIdAndActiveInd(regionsId, true);
			if (null != list && !list.isEmpty()) {
				Department department = departmentRepository.findByDepartmentName(type);
				if(null!=department) {
					System.out.println("department.getDepartmentId()==="+department.getDepartment_id());
					System.out.println("department.getDepartmentName()==="+department.getDepartmentName());
					for (LocationDetails locationDetails : list) {
						System.out.println("locationDetails.getLocationDetailsId()==="+locationDetails.getLocationDetailsId());
						System.out.println("locationDetails.getLocationName()==="+locationDetails.getLocationName());
						  List<EmployeeDetails> employeeDetails = employeeRepository
					                .getEmployeeDetailsByLocationIdAndDepartmentId(locationDetails.getLocationDetailsId(), department.getDepartment_id());
					        if (null != employeeDetails && !employeeDetails.isEmpty()) {
					        	for (EmployeeDetails emp : employeeDetails) {
					        		System.out.println("emp.getEmployeeId()==="+emp.getEmployeeId());
					        		System.out.println("emp.getEmailId()==="+emp.getEmailId());
					        		List<UserKpiLink> userKpiLinkList = userKpiLinkRepository.findByYearAndQuarterAndEmployeeIdAndPerformanceManagementIdAndLiveInd(year,quarter,emp.getEmployeeId(),target.getKpiSales(),true);
					        		if(null!=userKpiLinkList && !userKpiLinkList.isEmpty()){
					        			RegionsTargetDashBoardMapper mapper = new RegionsTargetDashBoardMapper();
					        			mapper.setCreationDate(Utility.getISOFromDate(target.getCreationDate()));
//					        			mapper.setFulfilment(target.getFulfilment());
//					        			mapper.setInvestment(target.getInvestment());
//					        			Currency currency = currencyRepository.getByCurrencyId(target.getInvestmentCurrency());
//					        			if (null != currency) {
//					        				mapper.setInvestmentCurrency(currency.getCurrencyName());
//					        			}
					        			mapper.setOrgId(target.getOrgId());
					        			mapper.setYear(target.getYear());
					        			mapper.setQuarter(target.getQuarter());
					        			mapper.setSales(target.getSales());
					        			Currency currency1 = currencyRepository.getByCurrencyId(target.getSalesCurrency());
					        			if (null != currency1) {
					        				mapper.setSalesCurrency(currency1.getCurrencyName());
					        			}
					        			Regions regions = regionsRepository.findByRegionsId(regionsId);
					        			if (null != regions) {
					        				mapper.setRegionsId(regions.getRegionsId());
					        				mapper.setRegions(regions.getRegions());
					        			}
//					        			PerformanceManagement PerformanceManagement = performanceManagementRepository
//					        					.findByPerformanceManagementId(target.getKpiFullfillment());
//					        			if (null != PerformanceManagement) {
//					        				mapper.setKpiFullfillment(PerformanceManagement.getPerformanceManagementId());
//					        				mapper.setKpiFullfillmentName(PerformanceManagement.getKpi());
//					        			}
//					        			
//					        			PerformanceManagement PerformanceManagement1 = performanceManagementRepository
//					        					.findByPerformanceManagementId(target.getKpiInvestment());
//					        			if (null != PerformanceManagement1) {
//					        				mapper.setKpiInvestment(PerformanceManagement1.getPerformanceManagementId());
//					        				mapper.setKpiInvestmentName(PerformanceManagement1.getKpi());
//					        			}
					        			
					        			PerformanceManagement PerformanceManagement2 = performanceManagementRepository
					        					.findByPerformanceManagementId(target.getKpiSales());
					        			if (null != PerformanceManagement2) {
					        				mapper.setKpiSales(PerformanceManagement2.getPerformanceManagementId());
					        				mapper.setKpiSalesName(PerformanceManagement2.getKpi());
					        				mapper.setCurrencyInd(PerformanceManagement2.isCurrencyInd());
					        			}
					        			EmployeeDetails employeeDetails1 = employeeRepository.getEmployeeDetailsByEmployeeId(userKpiLinkList.get(0).getEmployeeId(), true);
					        			if (null != employeeDetails1) {
					        				Currency currency2 = currencyRepository.getByCurrencyId(employeeDetails1.getCurrency());
					        				if (null != currency2) {
					        					mapper.setUserCurrency(currency2.getCurrencyName());
					        				}
					        			}
					        			mapper.setEmployeeId(userKpiLinkList.get(0).getEmployeeId());
				        				mapper.setEmployeeName(employeeService.getEmployeeFullName(userKpiLinkList.get(0).getEmployeeId()));
					        			List<UserKpiDashBoardResponseMapper> resurltMapper = new ArrayList<>();
					        			for (UserKpiLink userKpiLink : userKpiLinkList) {
											
						        			System.out.println("userKpiLink.getUserKpiLinkId()==="+userKpiLink.getUserKpiLinkId());
						        			UserKpiDashBoardResponseMapper mapper1 = new UserKpiDashBoardResponseMapper();
						        			
						        			mapper1.setMonth1AssignedValue(userKpiLink.getMonth1AssignedValue());
						        			mapper1.setMonth2AssignedValue(userKpiLink.getMonth2AssignedValue());
						        			mapper1.setMonth3AssignedValue(userKpiLink.getMonth3AssignedValue());
						        			mapper1.setAssignedValue(userKpiLink.getAssignedValue());
						        			mapper1.setWeitageValue(userKpiLink.getWeitageValue());
						        			mapper1.setMonth1CompletedValue(userKpiLink.getMonth1CompletedValue());
						        			mapper1.setMonth2CompletedValue(userKpiLink.getMonth2CompletedValue());
						        			mapper1.setMonth3CompletedValue(userKpiLink.getMonth3CompletedValue());
						        			mapper1.setCmpltValueAddDate(Utility.getISOFromDate(userKpiLink.getCmpltValueAddDate()));
						        			mapper1.setCompletedValue(userKpiLink.getCompletedValue());
						        			mapper1.setActualCmpltValueAddDate(Utility.getISOFromDate(userKpiLink.getActualCmpltValueAddDate()));
						        			mapper1.setMonth1ActualCompletedValue(userKpiLink.getMonth1ActualCompletedValue());
						        			mapper1.setMonth2ActualCompletedValue(userKpiLink.getMonth2ActualCompletedValue());
						        			mapper1.setMonth3ActualCompletedValue(userKpiLink.getMonth3ActualCompletedValue());
						        			mapper1.setActualCompletedValue(userKpiLink.getActualCompletedValue());
						        			mapper1.setActualCmpltValueAddedBy(employeeService.getEmployeeFullName(userKpiLink.getActualCmpltValueAddedBy()));
						        			PerformanceManagement PerformanceManagement3 = performanceManagementRepository
						        					.findByPerformanceManagementId(userKpiLink.getPerformanceManagementId());
						        			if (null != PerformanceManagement3) {
						        			mapper1.setKpiId(PerformanceManagement3.getPerformanceManagementId());
						        			mapper1.setKpiName(PerformanceManagement3.getKpi());
						        			mapper1.setCurrencyInd(PerformanceManagement3.isCurrencyInd());
						        			}
						        			LobDetails lob = lobRepository.findByLobDetailsId(userKpiLink.getLobDetailsId());
						        			if (null != lob) {
						        				mapper1.setLobDetailsId(lob.getLobDetailsId());
						        				mapper1.setLobName(lob.getName());
						        			}
						        			EmployeeDetails employeeDetails2 = employeeRepository.getEmployeeDetailsByEmployeeId(userKpiLink.getEmployeeId(), true);
						        			if (null != employeeDetails2) {
						        				Currency currency3 = currencyRepository.getByCurrencyId(employeeDetails2.getCurrency());
						        				if (null != currency3) {
						        					mapper1.setUserCurrency(currency3.getCurrencyName());
						        				}
						        			}
						        			
						        			resurltMapper.add(mapper1);
						        			
						        			
						        		}
					        			
					        			mapper.setUseKpiList(resurltMapper);
					        			resultList.add(mapper);
					        		}
								}
					        }
					}
				}
			}
			
		}
		
		return resultList;
	}



	@Override
	public ByteArrayInputStream exportRegionsListToExcel(List<RegionsMapper> list) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("candidate");

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
		for (int i = 0; i < headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != list && !list.isEmpty()) {
			for (RegionsMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getRegions());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < headings.length; i++) {
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
	public boolean checkRegionInRegionsByOrgLevel(String regions, String orgId) {
		List<Regions> list = regionsRepository.findByRegionsAndOrgIdAndLiveInd(regions,orgId, true);
		return false;
	}

}