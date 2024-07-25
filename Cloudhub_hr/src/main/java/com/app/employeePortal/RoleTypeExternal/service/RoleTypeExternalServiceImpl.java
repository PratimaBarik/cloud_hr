package com.app.employeePortal.RoleTypeExternal.service;

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

import com.app.employeePortal.RoleTypeExternal.entity.RoleTypeExternal;
import com.app.employeePortal.RoleTypeExternal.mapper.RoleTypeExternalMapper;
import com.app.employeePortal.RoleTypeExternal.repository.RoleTypeExternalRepo;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.sector.mapper.SectorMapper;
import com.app.employeePortal.source.entity.Source;
import com.app.employeePortal.util.Utility;

@Service
public class RoleTypeExternalServiceImpl implements RoleTypeExternalService {
	@Autowired
	RoleTypeExternalRepo roleTypeExternalRepo;
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	EmployeeService employeeService;
	
	private String[] roleTypeExternal_headings = {"Name"};

	@Override
	public boolean checkRoleNameInRoleTypeByOrgLevel(String roleType, String orgId) {
		List<RoleTypeExternal> roleTypes = roleTypeExternalRepo.findByRoleTypeAndLiveIndAndOrgId(roleType, true,orgId);
		if (roleTypes.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public RoleTypeExternalMapper createRoleTypeExternal(RoleTypeExternalMapper roleMapper) {
		String roleTypeExternalId = null;
		if (roleMapper != null) {
			RoleTypeExternal roleType = new RoleTypeExternal();
			roleType.setRoleType(roleMapper.getRoleType());
			roleType.setUserId(roleMapper.getUserId());
			roleType.setOrgId(roleMapper.getOrganizationId());
			roleType.setCreationDate(new Date());
			roleType.setUpdatedDate(new Date());
			roleType.setUpdatedBy(roleMapper.getUserId());
			roleType.setLiveInd(true);
			roleType.setEditInd(roleMapper.isEditInd());
			roleTypeExternalId = roleTypeExternalRepo.save(roleType).getRoleTypeExternalId();
		}
		RoleTypeExternalMapper  resultMapper= getRoleTypeById(roleTypeExternalId);
		return resultMapper;
	}

	@Override
	public RoleTypeExternalMapper updateRoleTypeExternal(String roleTypeId, RoleTypeExternalMapper roleMapper) {
		RoleTypeExternalMapper resultMapper = new  RoleTypeExternalMapper();
		if (null != roleTypeId) {
			RoleTypeExternal dbRoleTypeExternal = roleTypeExternalRepo.findByRoleTypeExternalId(roleTypeId);
			if (null != dbRoleTypeExternal.getRoleTypeExternalId()) {
				dbRoleTypeExternal.setRoleType(roleMapper.getRoleType());
				dbRoleTypeExternal.setEditInd(roleMapper.isEditInd());
				dbRoleTypeExternal.setUpdatedBy(roleMapper.getUserId());
				dbRoleTypeExternal.setUpdatedDate(new Date());
				roleTypeExternalRepo.save(dbRoleTypeExternal);
				resultMapper = getRoleTypeById(roleTypeId);
			}
		}
		return resultMapper;
	}

	private RoleTypeExternalMapper getRoleTypeById(String roleTypeId) {
		RoleTypeExternal role = roleTypeExternalRepo.findByRoleTypeExternalId(roleTypeId);
		RoleTypeExternalMapper roleMapper = new RoleTypeExternalMapper();
		if (null != role) {
			roleMapper.setRoleTypeExternalId(role.getRoleTypeExternalId());
			roleMapper.setRoleType(role.getRoleType());
			roleMapper.setOrganizationId(role.getOrgId());
			roleMapper.setUserId(role.getUserId());
			roleMapper.setEditInd(role.isEditInd());
			roleMapper.setCreationDate(Utility.getISOFromDate(role.getCreationDate()));
			List<RoleTypeExternal> externals = roleTypeExternalRepo.findByOrgId(role.getOrgId());
			if (null != externals && !externals.isEmpty()) {
				Collections.sort(externals, (p1, p2) -> p2.getUpdatedDate().compareTo(p1.getUpdatedDate()));
				if (null != role.getUpdatedBy() && !role.getUpdatedBy().isEmpty()) {
					roleMapper.setName(employeeService.getEmployeeFullName(externals.get(0).getUpdatedBy()));
				}
				roleMapper.setUpdationDate(Utility.getISOFromDate(externals.get(0).getUpdatedDate()));
			}
		}
		return roleMapper;
	}

	@Override
	public List<RoleTypeExternalMapper> getRoleListByOrgId(String orgId) {
		List<RoleTypeExternalMapper> resultList = new ArrayList<RoleTypeExternalMapper>();
		List<RoleTypeExternal> roleList = roleTypeExternalRepo.findByOrgIdAndLiveInd(orgId, true);
		if (null != roleList && !roleList.isEmpty()) {
			roleList.stream().map(role -> {
				RoleTypeExternalMapper roleMapper = new RoleTypeExternalMapper();
				roleMapper.setRoleTypeExternalId(role.getRoleTypeExternalId());
				roleMapper.setRoleType(role.getRoleType());
				roleMapper.setEditInd(role.isEditInd());
				roleMapper.setOrganizationId(role.getOrgId());
				roleMapper.setUserId(role.getUserId());
				roleMapper.setCreationDate(Utility.getISOFromDate(role.getCreationDate()));
				resultList.add(roleMapper);
				return resultList;
			}).collect(Collectors.toList());
		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		List<RoleTypeExternal> externals = roleTypeExternalRepo.findByOrgId(orgId);
		if (null != externals && !externals.isEmpty()) {
			Collections.sort(externals, (p1, p2) -> p2.getUpdatedDate().compareTo(p1.getUpdatedDate()));
			resultList.get(0).setName(employeeService.getEmployeeFullName(externals.get(0).getUpdatedBy()));
			resultList.get(0).setUpdationDate(Utility.getISOFromDate(externals.get(0).getUpdatedDate()));
		}
		return resultList;
	}

	@Override
	public void deleteRoleTypeDetailsById(String roleTypeExternalId, RoleTypeExternalMapper externalMapper) {
		if (null != roleTypeExternalId) {
			RoleTypeExternal roleTypes = roleTypeExternalRepo.findByRoleTypeExternalId(roleTypeExternalId);
			roleTypes.setLiveInd(false);
			roleTypes.setUpdatedBy(externalMapper.getUserId());
			roleTypes.setUpdatedDate(new Date());
			roleTypeExternalRepo.save(roleTypes);
		}
	}
	
	@Override
	public List<RoleTypeExternalMapper> getRoleTypeExternalByNameByOrgLevel(String name, String orgId) {
		List<RoleTypeExternal> list = roleTypeExternalRepo.findByRoleTypeContainingAndOrgId(name,orgId);
		List<RoleTypeExternalMapper> resultList = new ArrayList<RoleTypeExternalMapper>();
		if (null != list && !list.isEmpty()) {

			list.stream().map(roleType -> {
				RoleTypeExternalMapper roleMapper = getRoleTypeById(roleType.getRoleTypeExternalId());
				if (null != roleMapper) {
					resultList.add(roleMapper);

				}
				return resultList;
			}).collect(Collectors.toList());

		}
		return resultList;

	}

	@Override
	public HashMap getRoleTypeExternalCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<RoleTypeExternal> list = roleTypeExternalRepo.findByOrgIdAndLiveInd(orgId, true);
		map.put("RoleTypeExternalCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportRoleTypeExternalListToExcel(List<RoleTypeExternalMapper> list) {

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
		for (int i = 0; i < roleTypeExternal_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(roleTypeExternal_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != list && !list.isEmpty()) {
			for (RoleTypeExternalMapper sector : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(sector.getRoleType());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < roleTypeExternal_headings.length; i++) {
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
}