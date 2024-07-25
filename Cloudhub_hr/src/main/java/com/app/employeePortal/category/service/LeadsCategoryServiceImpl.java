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

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.category.entity.ItemTask;
import com.app.employeePortal.category.entity.LeadsCatagory;
import com.app.employeePortal.category.entity.UserSalaryBreakout;
import com.app.employeePortal.category.mapper.LeadsCategoryMapper;
import com.app.employeePortal.category.repository.LeadsCatagoryRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.mapper.EventMapper;
import com.app.employeePortal.util.Utility;

import io.opencensus.common.ServerStatsFieldEnums.Size;

@Service
@Transactional

public class LeadsCategoryServiceImpl implements LeadsCategoryService {
	@Autowired
	LeadsCatagoryRepository leadsCatagoryRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] headings = {"Cold","Hot","Worm"};

	@Override
	public LeadsCategoryMapper CreateLeadsCategory(LeadsCategoryMapper leadsCatagoryMapper) {
		LeadsCategoryMapper resultMapper= new LeadsCategoryMapper();
		
		LeadsCatagory leadsCatagory=leadsCatagoryRepository.findByOrgId(leadsCatagoryMapper.getOrgId());
		if(null!=leadsCatagory){
			leadsCatagory.setCreationDate(new Date());
			leadsCatagory.setLiveInd(leadsCatagoryMapper.isLiveInd());
			leadsCatagory.setOrgId(leadsCatagoryMapper.getOrgId());
			leadsCatagory.setUserId(leadsCatagoryMapper.getUserId());
			leadsCatagory.setUpdationBy(leadsCatagoryMapper.getUserId());
			leadsCatagory.setUpdationDate(new Date());
			leadsCatagory.setCold(leadsCatagoryMapper.getCold());
			leadsCatagory.setHot(leadsCatagoryMapper.getHot());
			leadsCatagory.setWorm(leadsCatagoryMapper.getWorm());
			leadsCatagory.setNotDefined(leadsCatagoryMapper.getNotDefined());
			
			resultMapper=getLeadsCatagoryById(leadsCatagoryRepository.save(leadsCatagory).getLeadsCatagoryId());
		}else {
			LeadsCatagory leadsCatagory1=new LeadsCatagory();
			
			leadsCatagory1.setCreationDate(new Date());
			leadsCatagory1.setLiveInd(leadsCatagoryMapper.isLiveInd());
			leadsCatagory1.setOrgId(leadsCatagoryMapper.getOrgId());
			leadsCatagory1.setUserId(leadsCatagoryMapper.getUserId());
			leadsCatagory1.setUpdationBy(leadsCatagoryMapper.getUserId());
			leadsCatagory1.setUpdationDate(new Date());
			leadsCatagory1.setCold(leadsCatagoryMapper.getCold());
			leadsCatagory1.setHot(leadsCatagoryMapper.getHot());
			leadsCatagory1.setWorm(leadsCatagoryMapper.getWorm());
			leadsCatagory1.setNotDefined(leadsCatagoryMapper.getNotDefined());
			
			resultMapper=getLeadsCatagoryById(leadsCatagoryRepository.save(leadsCatagory1).getLeadsCatagoryId());
		
		}
		return resultMapper;
	}

	@Override
	public LeadsCategoryMapper getLeadsCatagoryById(String leadsCatagoryId) {

		LeadsCategoryMapper resultMapper=new LeadsCategoryMapper();
		LeadsCatagory leadsCatagory=leadsCatagoryRepository.findByLeadsCatagoryId(leadsCatagoryId);
		
		if(null!=leadsCatagory) {
			resultMapper.setLeadsCatagoryId(leadsCatagoryId);
			resultMapper.setCold(leadsCatagory.getCold());
			resultMapper.setCreationDate(Utility.getISOFromDate(leadsCatagory.getCreationDate()));
			resultMapper.setHot(leadsCatagory.getHot());
			resultMapper.setLiveInd(leadsCatagory.isLiveInd());
			resultMapper.setName(employeeService.getEmployeeFullName(leadsCatagory.getUpdationBy()));
			resultMapper.setNotDefined(leadsCatagory.getNotDefined());
			resultMapper.setOrgId(leadsCatagory.getOrgId());
			resultMapper.setUpdationDate(Utility.getISOFromDate(leadsCatagory.getUpdationDate()));
			resultMapper.setUserId(leadsCatagory.getUserId());
			resultMapper.setWorm(leadsCatagory.getWorm());
		}
		return resultMapper;
	}

	@Override
	public List<LeadsCategoryMapper> getLeadsCategoryListByOrgId(String orgId) {
		List<LeadsCatagory> list=leadsCatagoryRepository.findByOrgIdAndLiveInd(orgId,true);
		List<LeadsCategoryMapper> resultMapper=new ArrayList<>();
		
		if (null != list && !list.isEmpty()) {
			resultMapper = list.stream().map(li->getLeadsCatagoryById(li.getLeadsCatagoryId()))
					.sorted((p1,p2)->p1.getCreationDate().compareTo(p2.getCreationDate()))
					.collect(Collectors.toList());

		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<LeadsCatagory> list1 = leadsCatagoryRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setName(employeeService.getEmployeeFullName(list1.get(0).getUpdationBy()));
		}
		return resultMapper;
	}

	@Override
	public HashMap getLeadsCategoryCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<LeadsCatagory> list = leadsCatagoryRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("LeadsCatagoryCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportleadsCategoryListToExcel(List<LeadsCategoryMapper> list) {
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
			for (LeadsCategoryMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getCold());				
				row.createCell(1).setCellValue(mapper.getHot());
				row.createCell(2).setCellValue(mapper.getWorm());
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
	
}