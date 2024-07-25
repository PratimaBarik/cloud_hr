package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

import com.app.employeePortal.category.entity.Nav;
import com.app.employeePortal.category.entity.UserSalaryBreakout;
import com.app.employeePortal.category.mapper.NavRequestMapper;
import com.app.employeePortal.category.mapper.NavResponseMapper;
import com.app.employeePortal.category.repository.NavRepository;
import com.app.employeePortal.event.mapper.EventMapper;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class NavServiceImpl implements NavService {

	@Autowired
	NavRepository navRepository;
	private String[] headings = { "Name" };

	@Override
	public NavResponseMapper createNav(NavRequestMapper requestMapper) {
		int id = 0;
		if (requestMapper != null) {
			Nav nav = new Nav();
			nav.setCreationDate(new Date());
			nav.setLiveInd(true);
			nav.setNavName(requestMapper.getNavName());
			nav.setNavId(requestMapper.getNavName());
			nav.setOrgId(requestMapper.getOrgId());
			nav.setUserId(requestMapper.getUserId());

			id = navRepository.save(nav).getNavDetailId();

		}
		NavResponseMapper resultMapper = getNavById(id);
		return resultMapper;
	}

	@Override
	public NavResponseMapper getNavById(int navDetailId) {

		Nav nav = navRepository.findByNavDetailId(navDetailId);
		NavResponseMapper responseMapper = new NavResponseMapper();

		if (null != nav) {

			responseMapper.setCreationDate(Utility.getISOFromDate(nav.getCreationDate()));
			responseMapper.setLiveInd(nav.isLiveInd());
			responseMapper.setNavName(nav.getNavName());
			responseMapper.setNavId(nav.getNavId());
			responseMapper.setOrgId(nav.getOrgId());
			responseMapper.setUserId(nav.getUserId());
			responseMapper.setNavDetailId(navDetailId);

		}

		return responseMapper;
	}

	@Override
	public List<NavResponseMapper> getNavByOrgId(String orgId) {

		List<NavResponseMapper> resultMapper = new ArrayList<>();
		List<Nav> list = navRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getNavById(li.getNavDetailId())).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public NavResponseMapper updateNav(int navDetailId, NavRequestMapper requestMapper) {

		Nav nav = navRepository.findByNavDetailId(navDetailId);
		if (null != nav) {

			nav.setCreationDate(new Date());
			nav.setLiveInd(true);
			nav.setNavName(requestMapper.getNavName());
			nav.setNavId(requestMapper.getNavName());
			nav.setOrgId(requestMapper.getOrgId());
			nav.setUserId(requestMapper.getUserId());

			navRepository.save(nav);
		}
		NavResponseMapper resultMapper = getNavById(navDetailId);
		return resultMapper;
	}

	@Override
	public void deleteNav(int navDetailId) {

		if (0 != navDetailId) {
			Nav nav = navRepository.findByNavDetailId(navDetailId);
			nav.setLiveInd(false);
			navRepository.save(nav);
		}
	}

	@Override
	public HashMap getNavCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<Nav> list = navRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("NavCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportNavListToExcel(List<NavResponseMapper> list) {
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
			for (NavResponseMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getNavName());
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
	public boolean checkNavNameInNavDetailsByOrgLevel(String navName, String orgId) {
		List<Nav> list = navRepository.findByNavNameAndOrgIdAndLiveInd(navName, orgId, true);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

}