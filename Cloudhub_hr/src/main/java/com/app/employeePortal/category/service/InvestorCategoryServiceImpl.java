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

import com.app.employeePortal.category.entity.InvestorCategory;
import com.app.employeePortal.category.entity.ServiceLine;
import com.app.employeePortal.category.entity.UserSalaryBreakout;
import com.app.employeePortal.category.mapper.InvestorCategoryMapper;
import com.app.employeePortal.category.repository.InvestorCategoryRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.mapper.EventMapper;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class InvestorCategoryServiceImpl implements InvestorCategoryService {

	@Autowired
	InvestorCategoryRepository investorCategoryRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] headings = { "Name" };

	@Override
	public InvestorCategoryMapper saveInvestorCategory(InvestorCategoryMapper investorCategoryMapper) {
		String investorCategoryId = null;
		if (investorCategoryMapper != null) {
			InvestorCategory investorCategory = new InvestorCategory();
			investorCategory.setCreationDate(new Date());
			investorCategory.setLiveInd(true);
			investorCategory.setEditInd(investorCategoryMapper.isEditInd());
			investorCategory.setName(investorCategoryMapper.getName());
			investorCategory.setOrgId(investorCategoryMapper.getOrgId());
			investorCategory.setUpdatedBy(investorCategoryMapper.getUserId());
			investorCategory.setUpdationDate(new Date());
			investorCategory.setUserId(investorCategoryMapper.getUserId());

			investorCategoryId = investorCategoryRepository.save(investorCategory).getInvestorCategoryId();

		}
		InvestorCategoryMapper resultMapper = getInvestorCategoryById(investorCategoryId);
		return resultMapper;
	}

	@Override
	public InvestorCategoryMapper getInvestorCategoryById(String investorCategoryId) {

		InvestorCategory investorCategory = investorCategoryRepository.findByInvestorCategoryId(investorCategoryId);
		InvestorCategoryMapper mapper = new InvestorCategoryMapper();

		if (null != investorCategory) {

			mapper.setCreationDate(Utility.getISOFromDate(investorCategory.getCreationDate()));
			mapper.setLiveInd(investorCategory.isLiveInd());
			mapper.setName(investorCategory.getName());
			mapper.setOrgId(investorCategory.getOrgId());
			mapper.setUpdatedBy(employeeService.getEmployeeFullName(investorCategory.getUserId()));
			mapper.setUpdationDate(Utility.getISOFromDate(investorCategory.getUpdationDate()));
			mapper.setUserId(investorCategory.getUserId());
			mapper.setInvestorCategoryId(investorCategoryId);
			mapper.setEditInd(investorCategory.isEditInd());
		}

		return mapper;
	}

	@Override
	public List<InvestorCategoryMapper> getInvestorCategoryByOrgId(String orgId) {

		List<InvestorCategoryMapper> resultMapper = new ArrayList<>();
		List<InvestorCategory> list = investorCategoryRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getInvestorCategoryById(li.getInvestorCategoryId()))
					.collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<InvestorCategory> list1 = investorCategoryRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public InvestorCategoryMapper updateInvestorCategory(String investorCategoryId,
			InvestorCategoryMapper investorCategoryMapper) {

		InvestorCategory investorCategory = investorCategoryRepository.findByInvestorCategoryId(investorCategoryId);
		if (null != investorCategory) {

			investorCategory.setCreationDate(new Date());
			investorCategory.setLiveInd(true);
			investorCategory.setName(investorCategoryMapper.getName());
			investorCategory.setOrgId(investorCategoryMapper.getOrgId());
			investorCategory.setUpdatedBy(investorCategoryMapper.getUserId());
			investorCategory.setUpdationDate(new Date());
			investorCategory.setUserId(investorCategoryMapper.getUserId());
			investorCategory.setEditInd(investorCategoryMapper.isEditInd());
			investorCategoryRepository.save(investorCategory);
		}
		InvestorCategoryMapper resultMapper = getInvestorCategoryById(investorCategoryId);
		return resultMapper;
	}

	@Override
	public void deleteInvestorCategory(String investorCategoryId, String userId) {
		if (null != investorCategoryId) {
			InvestorCategory investorCategory = investorCategoryRepository.findByInvestorCategoryId(investorCategoryId);

			investorCategory.setUpdationDate(new Date());
			investorCategory.setUpdatedBy(userId);
			investorCategory.setLiveInd(false);
			investorCategoryRepository.save(investorCategory);
		}
	}

	@Override
	public List<InvestorCategoryMapper> getInvestorCategoryByName(String name) {
		List<InvestorCategory> list = investorCategoryRepository.findByNameContaining(name);
		List<InvestorCategoryMapper> resultMapper = new ArrayList<InvestorCategoryMapper>();
		if (null != list && !list.isEmpty()) {

			list.stream().map(investorCategory -> {
				InvestorCategoryMapper mapper = getInvestorCategoryById(investorCategory.getInvestorCategoryId());
				if (null != mapper) {
					resultMapper.add(mapper);

				}
				return resultMapper;
			}).collect(Collectors.toList());

		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<InvestorCategory> list1 = investorCategoryRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public HashMap getInvestorCategoryCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<InvestorCategory> list = investorCategoryRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("InvestorCategoryCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportInvestorCategoryListToExcel(List<InvestorCategoryMapper> list) {
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
			for (InvestorCategoryMapper mapper : list) {
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
	public boolean checkNameInInvestorCategoryByOrgLevel(String name, String orgId) {
		List<InvestorCategory> list = investorCategoryRepository.findByNameAndOrgIdAndLiveInd(name, orgId, true);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
}