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

import com.app.employeePortal.category.entity.ServiceLine;
import com.app.employeePortal.category.entity.ServiceLineDepartmentLink;
import com.app.employeePortal.category.mapper.LobMapper;
import com.app.employeePortal.category.mapper.ServiceLineReqMapper;
import com.app.employeePortal.category.mapper.ServiceLineRespMapper;
import com.app.employeePortal.category.repository.ServiceLineDepartmentLinkRepository;
import com.app.employeePortal.category.repository.ServiceLineRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.mapper.DepartmentMapper;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.registration.service.DepartmentService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class ServiceLineServiceImpl implements ServiceLineService {

	@Autowired
	ServiceLineRepository serviceLineRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	ServiceLineDepartmentLinkRepository serviceLineDepartmentLinkRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	DepartmentService departmentService;
	private String[] headings = {"Name"};
	@Override
	public ServiceLineRespMapper saveServiceLine(ServiceLineReqMapper requestMapper) {
		String serviceLineId = null;
		if (requestMapper != null) {
			ServiceLine db = new ServiceLine();
			db.setCreationDate(new Date());
			db.setLiveInd(true);
			db.setServiceLineName(requestMapper.getServiceLineName());
			db.setOrgId(requestMapper.getOrgId());
			db.setUpdatedBy(requestMapper.getUserId());
			db.setUpdationDate(new Date());
			db.setUserId(requestMapper.getUserId());

			serviceLineId = serviceLineRepository.save(db).getServiceLineId();

		}
		ServiceLineRespMapper resultMapper = getServiceLineById(serviceLineId);
		return resultMapper;
	}

	public ServiceLineRespMapper getServiceLineById(String serviceLineId) {
		ServiceLine db = serviceLineRepository.findByServiceLineId(serviceLineId);
		ServiceLineRespMapper resultMapper = new ServiceLineRespMapper();
		if (null != db) {
			resultMapper.setCreationDate(Utility.getISOFromDate(db.getCreationDate()));
			resultMapper.setLiveInd(db.isLiveInd());
			resultMapper.setServiceLineName(db.getServiceLineName());
			resultMapper.setOrgId(db.getOrgId());
			resultMapper.setUpdatedBy(employeeService.getEmployeeFullName(db.getUserId()));
			resultMapper.setUpdationDate(Utility.getISOFromDate(db.getUpdationDate()));
			resultMapper.setUserId(db.getUserId());
			resultMapper.setServiceLineId(serviceLineId);
		}
		return resultMapper;
	}

	@Override
	public List<ServiceLineRespMapper> getServiceLineByOrgId(String orgId) {

		List<ServiceLineRespMapper> resultMapper = new ArrayList<>();
		List<ServiceLine> resultList = serviceLineRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != resultList && !resultList.isEmpty()) {
			resultMapper = resultList.stream().map(li -> getServiceLineById(li.getServiceLineId()))
					.collect(Collectors.toList());
		}
		if (null != resultMapper && !resultMapper.isEmpty()) {
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<ServiceLine> list1 = serviceLineRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		}
		return resultMapper;
	}

	@Override
	public ServiceLineRespMapper updateServiceLine(String serviceLineId, ServiceLineReqMapper requestMapper) {
		ServiceLine db = serviceLineRepository.findByServiceLineId(serviceLineId);
		if (null != db) {
			db.setCreationDate(new Date());
			db.setServiceLineName(requestMapper.getServiceLineName());
			db.setOrgId(requestMapper.getOrgId());
			db.setUpdatedBy(requestMapper.getUserId());
			db.setUpdationDate(new Date());
			db.setUserId(requestMapper.getUserId());
			serviceLineRepository.save(db);
		}
		ServiceLineRespMapper resultMapper = getServiceLineById(serviceLineId);
		return resultMapper;
	}

	@Override
	public void deleteServiceLine(String serviceLineId, String userId) {
		if (null != serviceLineId) {
			ServiceLine db = serviceLineRepository.findByServiceLineId(serviceLineId);

			db.setUpdationDate(new Date());
			db.setUpdatedBy(userId);
			db.setLiveInd(false);
			serviceLineRepository.save(db);
		}
	}

	@Override
	public List<ServiceLineRespMapper> getServiceLineByNameByOrgLevel(String name, String orgId) {
		List<ServiceLine> list = serviceLineRepository.findByServiceLineNameContainingAndLiveIndAndOrgId(name,true,orgId);
		List<ServiceLineRespMapper> resultMapper = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(serviceLine -> {
				ServiceLineRespMapper mapper = getServiceLineById(serviceLine.getServiceLineId());
				if (null != mapper) {
					resultMapper.add(mapper);
				}
				return resultMapper;
			}).collect(Collectors.toList());
		}
		if (null != resultMapper && !resultMapper.isEmpty()) {
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<ServiceLine> list1 = serviceLineRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		}
		return resultMapper;
	}

	@Override
	public DepartmentMapper updateServiceLineDepartment(String orgId,String departmentId, boolean liveInd) {
		
		if (liveInd == true) {
			
			Department dbDepartment = departmentRepository.getDepartmentDetails(departmentId);
			if (null != dbDepartment.getDepartment_id()) {
				dbDepartment.setServiceLineInd(liveInd);
				departmentRepository.save(dbDepartment);
			}
			
			List<ServiceLine> serviceLine = serviceLineRepository.findByOrgIdAndLiveInd(orgId, true);
			if (null != serviceLine && !serviceLine.isEmpty()) {
				for (ServiceLine serviceLine2 : serviceLine) {
					ServiceLineDepartmentLink db=new ServiceLineDepartmentLink();
					db.setDepartmentId(departmentId);
					db.setCreationDate(new Date());
					db.setLiveInd(true);
					db.setServiceLineId(serviceLine2.getServiceLineId());
					
					serviceLineDepartmentLinkRepository.save(db);
				}
			}
			
		}else {
			Department dbDepartment = departmentRepository.getDepartmentDetails(departmentId);
			if (null != dbDepartment.getDepartment_id()) {
				dbDepartment.setServiceLineInd(liveInd);
				departmentRepository.save(dbDepartment);
			}
			List<ServiceLineDepartmentLink> db=serviceLineDepartmentLinkRepository.findByDepartmentIdAndLiveInd(departmentId,true);
			if(null != db && !db.isEmpty()) {
				serviceLineDepartmentLinkRepository.deleteAll(db);
			}
		}
		
		return departmentService.getDepartmentTypeById(departmentId);
	}

	@Override
	public List<ServiceLineRespMapper> getServiceLineByDepartmentId(String departmentId) {
		List<ServiceLineDepartmentLink> list=serviceLineDepartmentLinkRepository.findByDepartmentIdAndLiveInd(departmentId,true);
		List<ServiceLineRespMapper> resultMapper=new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			for (ServiceLineDepartmentLink serviceLineDepartmentLink : list) {
				ServiceLine serviceLine = serviceLineRepository.findByServiceLineId(serviceLineDepartmentLink.getServiceLineId());
				if (null != serviceLine) {
					ServiceLineRespMapper mapper=getServiceLineById(serviceLineDepartmentLink.getServiceLineId());
					resultMapper.add(mapper);
				}
			}
			
		}
		return resultMapper;
	}

	@Override
	public HashMap getServiceLineCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<ServiceLine> list = serviceLineRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("ServiceLineCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportServiceLineListToExcel(List<ServiceLineRespMapper> list) {
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
			for (ServiceLineRespMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getServiceLineName());
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
	public boolean checkServiceLineNameInServiceLineByOrgLevel(String name, String orgId) {
		List<ServiceLine> list = serviceLineRepository.findByServiceLineNameAndLiveIndAndOrgId(name,true,orgId);
		if(list.size()>0) {
			return true;
		}
		return false;
	}

}