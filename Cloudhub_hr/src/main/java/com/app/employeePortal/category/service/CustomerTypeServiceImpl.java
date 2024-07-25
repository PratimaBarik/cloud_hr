package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

import com.app.employeePortal.category.entity.CustomerType;
import com.app.employeePortal.category.mapper.CustomerTypeMapper;
import com.app.employeePortal.category.repository.CustomerTypeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class CustomerTypeServiceImpl implements CustomerTypeService {

	@Autowired
	CustomerTypeRepository customerTypeRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] headings = { "Name" };

	@Override
	public CustomerTypeMapper saveCustomerType(CustomerTypeMapper customerTypeMapper) {
		String customerTypeId = null;
		if (customerTypeMapper != null) {
			CustomerType customerType = new CustomerType();
			customerType.setCreationDate(new Date());
			customerType.setLiveInd(true);
			customerType.setEditInd(customerTypeMapper.isEditInd());
			customerType.setName(customerTypeMapper.getName());
			customerType.setOrgId(customerTypeMapper.getOrgId());
			customerType.setUpdatedBy(customerTypeMapper.getUserId());
			customerType.setUpdationDate(new Date());
			customerType.setUserId(customerTypeMapper.getUserId());

			customerTypeId = customerTypeRepository.save(customerType).getCustomerTypeId();

		}
		CustomerTypeMapper resultMapper = getCustomerTypeByCustomerTypeId(customerTypeId);
		return resultMapper;
	}

	@Override
	public CustomerTypeMapper getCustomerTypeByCustomerTypeId(String customerTypeId) {

		CustomerType customerType = customerTypeRepository.findByCustomerTypeId(customerTypeId);
		CustomerTypeMapper customerTypeMapper = new CustomerTypeMapper();

		if (null != customerType) {

			customerTypeMapper.setCreationDate(Utility.getISOFromDate(customerType.getCreationDate()));
			customerTypeMapper.setLiveInd(true);
			customerTypeMapper.setName(customerType.getName());
			customerTypeMapper.setOrgId(customerType.getOrgId());
			customerTypeMapper.setUpdatedBy(employeeService.getEmployeeFullName(customerType.getUserId()));
			customerTypeMapper.setUpdationDate(Utility.getISOFromDate(customerType.getUpdationDate()));
			customerTypeMapper.setUserId(customerType.getUserId());
			customerTypeMapper.setCustomerTypeId(customerType.getCustomerTypeId());
			customerTypeMapper.setEditInd(customerType.isEditInd());
		}

		return customerTypeMapper;
	}

	@Override
	public List<CustomerTypeMapper> getCustomerTypeByOrgId(String orgId) {

		List<CustomerTypeMapper> resultMapper = new ArrayList<>();
		List<CustomerType> list = customerTypeRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getCustomerTypeByCustomerTypeId(li.getCustomerTypeId()))
					.collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<CustomerType> list1 = customerTypeRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public CustomerTypeMapper updateCustomerType(String customerTypeId, CustomerTypeMapper customerTypeMapper) {

		CustomerType customerType = customerTypeRepository.findByCustomerTypeId(customerTypeId);
		if (null != customerType) {

			customerType.setCreationDate(new Date());
			customerType.setLiveInd(true);
			customerType.setName(customerTypeMapper.getName());
			customerType.setOrgId(customerTypeMapper.getOrgId());
			customerType.setUpdatedBy(customerTypeMapper.getUserId());
			customerType.setUpdationDate(new Date());
			customerType.setUserId(customerTypeMapper.getUserId());
			customerType.setEditInd(customerTypeMapper.isEditInd());
			customerTypeRepository.save(customerType);
		}
		CustomerTypeMapper resultMapper = getCustomerTypeByCustomerTypeId(customerTypeId);
		return resultMapper;
	}

	@Override
	public void deleteCustomerType(String customerTypeId, String userId) {

		if (null != customerTypeId) {
			CustomerType customerType = customerTypeRepository.findByCustomerTypeId(customerTypeId);

			customerType.setUpdationDate(new Date());
			customerType.setUpdatedBy(userId);
			customerType.setLiveInd(false);
			customerTypeRepository.save(customerType);
		}
	}

	@Override
	public List<CustomerTypeMapper> getCustomerTypeByNameAndOrgId(String name, String orgId) {
		List<CustomerType> list = customerTypeRepository.findByNameContainingAndOrgId(name,orgId);
		List<CustomerTypeMapper> resultList = new ArrayList<CustomerTypeMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(customerType -> {
				CustomerTypeMapper mapper = getCustomerTypeByCustomerTypeId(customerType.getCustomerTypeId());
				if (null != mapper) {
					resultList.add(mapper);
				}
				return resultList;
			}).collect(Collectors.toList());
		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<CustomerType> list1 = customerTypeRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultList.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultList.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultList;
	}

	@Override
	public HashMap getCustomerTypeCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<CustomerType> list = customerTypeRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("CustomerTypeCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportCustomerTypeListToExcel(List<CustomerTypeMapper> list) {
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
			for (CustomerTypeMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getName());
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
	public boolean checkNameInCustomerTypeByOrgLevel(String name, String orgId) {
		List<CustomerType> list = customerTypeRepository.findByNameAndOrgId(name, orgId);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

}