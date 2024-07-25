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

import com.app.employeePortal.category.entity.ItemTask;
import com.app.employeePortal.category.entity.LobDetails;
import com.app.employeePortal.category.mapper.ItemTaskMapper;
import com.app.employeePortal.category.mapper.LeadsCategoryMapper;
import com.app.employeePortal.category.mapper.LobMapper;
import com.app.employeePortal.category.repository.LobDetailsRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class LobServiceImpl implements LobService {

	@Autowired
	LobDetailsRepository lobRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] headings = { "Name" };

	@Override
	public LobMapper saveLob(LobMapper mapper) {
		String Id = null;
		if (mapper != null) {
			LobDetails lob = new LobDetails();
			lob.setCreationDate(new Date());
			lob.setLiveInd(true);
			lob.setName(mapper.getName());
			lob.setOrgId(mapper.getOrgId());
			lob.setUserId(mapper.getUserId());
			lob.setUpdatedBy(mapper.getUserId());
			lob.setUpdationDate(new Date());
			Id = lobRepository.save(lob).getLobDetailsId();

		}
		LobMapper resultMapper = getLobDetailsById(Id);
		return resultMapper;
	}

	public LobMapper getLobDetailsById(String lobDetailsId) {

		LobDetails lob = lobRepository.findByLobDetailsId(lobDetailsId);
		LobMapper resultMapper = new LobMapper();

		if (null != lob) {
			resultMapper.setCreationDate(Utility.getISOFromDate(lob.getCreationDate()));
			resultMapper.setLiveInd(lob.isLiveInd());
			resultMapper.setName(lob.getName());
			resultMapper.setOrgId(lob.getOrgId());
			resultMapper.setUserId(lob.getUserId());
			resultMapper.setLobDetsilsId(lobDetailsId);
			resultMapper.setUpdatedBy(employeeService.getEmployeeFullName(lob.getUserId()));
			resultMapper.setUpdationDate(Utility.getISOFromDate(lob.getCreationDate()));
		}

		return resultMapper;
	}

	@Override
	public List<LobMapper> getLobMapperByOrgId(String orgId) {

		List<LobMapper> resultMapper = new ArrayList<>();
		List<LobDetails> list = lobRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getLobDetailsById(li.getLobDetailsId())).collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<LobDetails> list1 = lobRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public LobMapper updateLob(String lobDetailsId, LobMapper mapper) {

		LobDetails lob = lobRepository.findByLobDetailsId(lobDetailsId);
		if (null != lob) {

			lob.setCreationDate(new Date());
			lob.setLiveInd(true);
			lob.setName(mapper.getName());
			lob.setOrgId(mapper.getOrgId());
			lob.setUserId(mapper.getUserId());
			lob.setUpdatedBy(mapper.getUserId());
			lob.setUpdationDate(new Date());
			lobRepository.save(lob);
		}
		LobMapper resultMapper = getLobDetailsById(lobDetailsId);
		return resultMapper;
	}

	@Override
	public void deleteLob(String lobDetailsId, String userId) {

		if (null != lobDetailsId) {
			LobDetails lobDetails = lobRepository.findByLobDetailsId(lobDetailsId);
			lobDetails.setLiveInd(false);
			lobDetails.setUpdatedBy(userId);
			lobDetails.setUpdationDate(new Date());
			lobRepository.save(lobDetails);
		}
	}

	@Override
	public List<LobMapper> getLobByNameByOrgLevel(String name, String orgId) {
		List<LobDetails> list = lobRepository.findByNameContainingAndLiveIndAndOrgId(name, true,orgId);
		List<LobMapper> resultMapper = new ArrayList<LobMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(itemTask -> {
				LobMapper mapper = getLobDetailsById(itemTask.getLobDetailsId());
				if (null != mapper) {
					resultMapper.add(mapper);
				}
				return resultMapper;
			}).collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<LobDetails> list1 = lobRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public HashMap getLobCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<LobDetails> list = lobRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("LobCount", list.size());
		return map;
	}

	
	public ByteArrayInputStream exportItemTaskListToExcel(List<ItemTaskMapper> list) {
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
			for (ItemTaskMapper mapper : list) {
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
	public ByteArrayInputStream exportLobListToExcel(List<LobMapper> list) {
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
			for (LobMapper mapper : list) {
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
	public boolean checkNameInLobByOrgLevel(String name, String orgId) {
//		List<LobDetails> list = lobRepository.findByNameAndLiveIndAndOrgId(name, true,orgId);
//		if(list.size()>0) {
//			return true;
//		}
		return false;
	}

}