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

import org.apache.commons.lang.StringUtils;
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

import com.app.employeePortal.category.entity.DepartmentPerformanceMgmtLink;
import com.app.employeePortal.category.entity.PerformanceManagement;
import com.app.employeePortal.category.entity.RoleType;
import com.app.employeePortal.category.mapper.PerformanceMgmntDeptLinkMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmntDeptLinkRespMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmtDropDownMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmtReqMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmtRespMapper;
import com.app.employeePortal.category.repository.DepartmentPerformanceMgmtlinkRepository;
import com.app.employeePortal.category.repository.PerformanceManagementRepository;
import com.app.employeePortal.category.repository.RoleTypeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class PerformanceManagementServiceImpl implements PerformanceManagementService {

	@Autowired
	PerformanceManagementRepository performanceManagementRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] headings = { "Name" };
	@Autowired
	DepartmentPerformanceMgmtlinkRepository departmentPerformanceMgmtlinkRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	RoleTypeRepository roleTypeRepository;

	@Override
	public PerformanceMgmtRespMapper savePerformanceManagement(PerformanceMgmtReqMapper requestMapper) {

		String PerformanceManagementId = null;
		if (requestMapper != null) {
			PerformanceManagement performanceManagement = new PerformanceManagement();
			performanceManagement.setCreationDate(new Date());
			performanceManagement.setLiveInd(true);
			performanceManagement.setCurrencyInd(false);
			performanceManagement.setKpi(requestMapper.getKpi());
			performanceManagement.setOrgId(requestMapper.getOrgId());
			performanceManagement.setUpdatedBy(requestMapper.getUserId());
			performanceManagement.setUpdationDate(new Date());
			performanceManagement.setUserId(requestMapper.getUserId());

			PerformanceManagementId = performanceManagementRepository.save(performanceManagement)
					.getPerformanceManagementId();

		}
		PerformanceMgmtRespMapper resultMapper = getPerformanceManagementById(PerformanceManagementId);
		return resultMapper;
	}

	@Override
	public PerformanceMgmtRespMapper getPerformanceManagementById(String performanceManagementId) {

		PerformanceManagement performanceManagement = performanceManagementRepository
				.findByPerformanceManagementId(performanceManagementId);
		PerformanceMgmtRespMapper resultMapper = new PerformanceMgmtRespMapper();

		if (null != performanceManagement) {

			resultMapper.setCreationDate(Utility.getISOFromDate(performanceManagement.getCreationDate()));
			resultMapper.setLiveInd(performanceManagement.isLiveInd());
			resultMapper.setCurrencyInd(performanceManagement.isCurrencyInd());
			resultMapper.setKpi(performanceManagement.getKpi());
			resultMapper.setOrgId(performanceManagement.getOrgId());
			resultMapper.setUpdatedBy(employeeService.getEmployeeFullName(performanceManagement.getUserId()));
			resultMapper.setUpdationDate(Utility.getISOFromDate(performanceManagement.getUpdationDate()));
			resultMapper.setUserId(performanceManagement.getUserId());
			resultMapper.setPerformanceManagementId(performanceManagement.getPerformanceManagementId());
		}

		return resultMapper;
	}

	@Override
	public List<PerformanceMgmtRespMapper> getPerformanceManagementByOrgId(String orgId) {

		List<PerformanceMgmtRespMapper> resultMapper = new ArrayList<>();
		List<PerformanceManagement> list = performanceManagementRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getPerformanceManagementById(li.getPerformanceManagementId()))
					.collect(Collectors.toList());
		}

		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<PerformanceManagement> list1 = performanceManagementRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public PerformanceMgmtRespMapper updatePerformanceManagement(String performanceManagementId,
			PerformanceMgmtReqMapper requestMapper) {
		PerformanceManagement performanceManagement = performanceManagementRepository
				.findByPerformanceManagementId(performanceManagementId);
		if (null != performanceManagement) {
			performanceManagement.setLiveInd(true);
			performanceManagement.setKpi(requestMapper.getKpi());
			performanceManagement.setOrgId(requestMapper.getOrgId());
			performanceManagement.setUpdatedBy(requestMapper.getUserId());
			performanceManagement.setUpdationDate(new Date());
			performanceManagement.setUserId(requestMapper.getUserId());
			performanceManagementRepository.save(performanceManagement);
		}
		PerformanceMgmtRespMapper resultMapper = getPerformanceManagementById(performanceManagementId);
		return resultMapper;
	}

	@Override
	public PerformanceMgmtRespMapper updatePerformanceManagementForCurrencyInd(String performanceManagementId,
			PerformanceMgmtReqMapper requestMapper) {
		PerformanceManagement performanceManagement = performanceManagementRepository
				.findByPerformanceManagementId(performanceManagementId);
		if (null != performanceManagement) {

			performanceManagement.setCurrencyInd(requestMapper.isCurrencyInd());
			performanceManagement.setOrgId(requestMapper.getOrgId());
			performanceManagement.setUpdatedBy(requestMapper.getUserId());
			performanceManagement.setUpdationDate(new Date());

			performanceManagementRepository.save(performanceManagement);
		}
		PerformanceMgmtRespMapper resultMapper = getPerformanceManagementById(performanceManagementId);
		return resultMapper;
	}

	@Override
	public void deletePerformanceManagement(String PerformanceManagementId, String userId) {

		if (null != PerformanceManagementId) {
			PerformanceManagement PerformanceManagement = performanceManagementRepository
					.findByPerformanceManagementId(PerformanceManagementId);
			if (null != PerformanceManagement) {
				PerformanceManagement.setUpdationDate(new Date());
				PerformanceManagement.setUpdatedBy(userId);
				PerformanceManagement.setLiveInd(false);
				performanceManagementRepository.save(PerformanceManagement);
			}
		}
	}

	@Override
	public List<PerformanceMgmtRespMapper> getPerformanceManagementByKpiByOrgLevel(String name, String orgId) {
		List<PerformanceManagement> list = performanceManagementRepository.findByKpiContainingAndLiveIndAndOrgId(name,
				true, orgId);
		List<PerformanceMgmtRespMapper> resultList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(PerformanceManagement -> {
				PerformanceMgmtRespMapper mapper = getPerformanceManagementById(
						PerformanceManagement.getPerformanceManagementId());
				if (null != mapper) {
					resultList.add(mapper);
				}
				return resultList;
			}).collect(Collectors.toList());
		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<PerformanceManagement> list1 = performanceManagementRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultList.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultList.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultList;
	}

//	@Override
//	public List<PerformanceMgmtRespMapper> getPerformanceManagementByDepartmentId(String departmentId) {
//
//		List<PerformanceMgmtRespMapper> resultMapper = new ArrayList<>();
//		List<PerformanceManagement> list = performanceManagementRepository.findByDepartmentIdAndLiveInd(departmentId,
//				true);
//		if (null != list) {
//			resultMapper = list.stream().map(li -> getPerformanceManagementById(li.getPerformanceManagementId()))
//					.collect(Collectors.toList());
//		}
//		return resultMapper;
//	}

	@Override
	public HashMap getPerformanceManagementCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<PerformanceManagement> list = performanceManagementRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("PerformanceManagementCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportPerformanceManagementListToExcel(List<PerformanceMgmtRespMapper> list) {
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
			for (PerformanceMgmtRespMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getKpi());
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
	public List<PerformanceMgmntDeptLinkRespMapper> saveDepartmentPerformanceMgmt(
			PerformanceMgmntDeptLinkMapper requestMapper) {
		List<DepartmentPerformanceMgmtLink> db = departmentPerformanceMgmtlinkRepository
				.findByDepartmentIdAndRoleTypeId(requestMapper.getDepartmentId(), requestMapper.getRoleTypeId());
		if (null != db) {
			departmentPerformanceMgmtlinkRepository.deleteAll(db);
		}
		if (null != requestMapper.getKpis() && !requestMapper.getKpis().isEmpty()) {
			for (PerformanceMgmtDropDownMapper kpi : requestMapper.getKpis()) {

				DepartmentPerformanceMgmtLink newdb = new DepartmentPerformanceMgmtLink();
				newdb.setDepartmentId(requestMapper.getDepartmentId());
				newdb.setPerformanceManagementId(kpi.getPerformanceManagementId());
				newdb.setRoleTypeId(requestMapper.getRoleTypeId());
				newdb.setLiveInd(true);
				newdb.setUpdatedBy(requestMapper.getUserId());
				newdb.setCreationDate(new Date());
				newdb.setUpdationDate(new Date());
				newdb.setOrgId(requestMapper.getOrgId());
				newdb.setUserId(requestMapper.getUserId());
				departmentPerformanceMgmtlinkRepository.save(newdb);
			}
		}
		List<PerformanceMgmntDeptLinkRespMapper> resultmapper = getDepartmentPerformanceMgmtByDepartmentIdAndRoleTypeId(
				requestMapper.getDepartmentId(), requestMapper.getRoleTypeId());
		return resultmapper;
	}

	private PerformanceMgmntDeptLinkRespMapper getDepartmentPerformanceMgmtById(
			String departmentPerformanceMgmtLinkId) {
		DepartmentPerformanceMgmtLink db = departmentPerformanceMgmtlinkRepository
				.getById(departmentPerformanceMgmtLinkId);
		PerformanceMgmntDeptLinkRespMapper resultmapper = new PerformanceMgmntDeptLinkRespMapper();
		if (null != db) {
			resultmapper.setCreationDate(Utility.getISOFromDate(db.getCreationDate()));
			if (!StringUtils.isEmpty(db.getDepartmentId())) {
				Department department = departmentRepository.getDepartmentDetailsById(db.getDepartmentId());
				if (null != department) {
					resultmapper.setDepartmentId(department.getDepartment_id());
					resultmapper.setDeparmentName(department.getDepartmentName());
				}
			}

			if (!StringUtils.isEmpty(db.getPerformanceManagementId())) {
				PerformanceManagement pm = performanceManagementRepository
						.findByPerformanceManagementId(db.getPerformanceManagementId());
				if (null != pm) {
					resultmapper.setPerformanceManagementId(pm.getPerformanceManagementId());
					resultmapper.setKpi(pm.getKpi());
				}
			}
			if (!StringUtils.isEmpty(db.getRoleTypeId())) {
				RoleType roleType = roleTypeRepository.findByRoleTypeId(db.getRoleTypeId());
				if (null != roleType) {
					resultmapper.setRoleTypeId(roleType.getRoleTypeId());
					resultmapper.setRoleType(roleType.getRoleType());
				}
			}

			resultmapper.setUserId(db.getUserId());
			resultmapper.setLiveInd(true);
			resultmapper.setOrgId(db.getOrgId());
			resultmapper.setCreationDate(Utility.getISOFromDate(db.getCreationDate()));
		}
		return resultmapper;
	}

	@Override
	public List<PerformanceMgmntDeptLinkRespMapper> getDepartmentPerformanceMgmtByDepartmentIdAndRoleTypeId(
			String departmentId, String roleTypeId) {
		List<PerformanceMgmntDeptLinkRespMapper> resultMapper = new ArrayList<>();
		List<DepartmentPerformanceMgmtLink> list = departmentPerformanceMgmtlinkRepository
				.findByDepartmentIdAndRoleTypeId(departmentId, roleTypeId);
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				PerformanceMgmntDeptLinkRespMapper mapper = getDepartmentPerformanceMgmtById(
						li.getDepartmentPerformanceMgmtLinkId());
				if (null != mapper) {
					resultMapper.add(mapper);
				}

				return mapper;
			}).collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<DepartmentPerformanceMgmtLink> list1 = departmentPerformanceMgmtlinkRepository
				.findByDepartmentIdAndRoleTypeId(departmentId, roleTypeId);
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public List<PerformanceMgmtDropDownMapper> getPerformanceManagementByOrgIdForDropDown(String orgId) {
		List<PerformanceMgmtDropDownMapper> resultMapper = new ArrayList<>();
		List<PerformanceManagement> list = performanceManagementRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				PerformanceMgmtDropDownMapper mapper = new PerformanceMgmtDropDownMapper();
				mapper.setKpi(li.getKpi());
				mapper.setPerformanceManagementId(li.getPerformanceManagementId());
				resultMapper.add(mapper);
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public boolean checkKpiInPerformanceMgmtByOrgLevel(String kpi, String orgId) {
		List<PerformanceManagement> list = performanceManagementRepository.findByKpiAndOrgIdAndLiveInd(kpi, orgId,
				true);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

}