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

import com.app.employeePortal.category.entity.PaymentCategory;
import com.app.employeePortal.category.entity.ServiceLine;
import com.app.employeePortal.category.entity.ShipBy;
import com.app.employeePortal.category.mapper.ShipByMapper;
import com.app.employeePortal.category.repository.ShipByRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.mapper.EventMapper;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class ShipByServiceImpl implements ShipByService {

	@Autowired
	ShipByRepository shipByRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] shipBy_headings = { "Name" };

	@Override
	public ShipByMapper saveShipBy(ShipByMapper shipByMapper) {
		String shipById = null;
		if (shipByMapper != null) {
			ShipBy shipBy = new ShipBy();
			shipBy.setCreationDate(new Date());
			shipBy.setLiveInd(true);
			shipBy.setEditInd(shipByMapper.isEditInd());
			shipBy.setName(shipByMapper.getName());
			shipBy.setOrgId(shipByMapper.getOrgId());
			shipBy.setUpdatedBy(shipByMapper.getUserId());
			shipBy.setUpdationDate(new Date());
			shipBy.setUserId(shipByMapper.getUserId());

			shipById = shipByRepository.save(shipBy).getShipById();

		}
		ShipByMapper resultMapper = getShipByByShipById(shipById);
		return resultMapper;
	}

	@Override
	public ShipByMapper getShipByByShipById(String shipById) {

		ShipBy shipBy = shipByRepository.findByShipById(shipById);
		ShipByMapper shipByMapper = new ShipByMapper();

		if (null != shipBy) {

			shipByMapper.setCreationDate(Utility.getISOFromDate(shipBy.getCreationDate()));
			shipByMapper.setLiveInd(shipBy.isLiveInd());
			shipByMapper.setName(shipBy.getName());
			shipByMapper.setOrgId(shipBy.getOrgId());
			shipByMapper.setUpdatedBy(employeeService.getEmployeeFullName(shipBy.getUserId()));
			shipByMapper.setUpdationDate(Utility.getISOFromDate(shipBy.getUpdationDate()));
			shipByMapper.setUserId(shipBy.getUserId());
			shipByMapper.setShipById(shipBy.getShipById());
			shipByMapper.setEditInd(shipBy.isEditInd());
		}

		return shipByMapper;
	}

	@Override
	public List<ShipByMapper> getShipByByOrgId(String orgId) {

		List<ShipByMapper> resultMapper = new ArrayList<>();
		List<ShipBy> list = shipByRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getShipByByShipById(li.getShipById())).collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<ShipBy> list1 = shipByRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public ShipByMapper updateShipBy(String shipById, ShipByMapper shipByMapper) {

		ShipBy shipBy = shipByRepository.findByShipById(shipById);
		if (null != shipBy) {

			shipBy.setCreationDate(new Date());
			shipBy.setLiveInd(true);
			shipBy.setName(shipByMapper.getName());
			shipBy.setOrgId(shipByMapper.getOrgId());
			shipBy.setUpdatedBy(shipByMapper.getUserId());
			shipBy.setUpdationDate(new Date());
			shipBy.setUserId(shipByMapper.getUserId());
			shipBy.setEditInd(shipByMapper.isEditInd());
			shipByRepository.save(shipBy);
		}
		ShipByMapper resultMapper = getShipByByShipById(shipById);
		return resultMapper;
	}

	@Override
	public void deleteShipBy(String shipById, String userId) {

		if (null != shipById) {
			ShipBy shipBy = shipByRepository.findByShipById(shipById);

			shipBy.setUpdationDate(new Date());
			shipBy.setUpdatedBy(userId);
			shipBy.setLiveInd(false);
			shipByRepository.save(shipBy);
		}
	}

	@Override
	public List<ShipByMapper> getShipByByNameByOrgLevel(String name, String orgId) {
		List<ShipBy> list = shipByRepository.findByNameContainingAndOrgId(name,orgId);
		List<ShipByMapper> resultMapper = new ArrayList<ShipByMapper>();
		if (null != list && !list.isEmpty()) {

			list.stream().map(shipBY -> {
				ShipByMapper shibByMapper = getShipByByShipById(shipBY.getShipById());
				if (null != shibByMapper) {
					resultMapper.add(shibByMapper);

				}
				return resultMapper;
			}).collect(Collectors.toList());

		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<ShipBy> list1 = shipByRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public HashMap getShipByCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<ShipBy> list = shipByRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("shipByCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportShipByListToExcel(List<ShipByMapper> list) {
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
		for (int i = 0; i < shipBy_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(shipBy_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != list && !list.isEmpty()) {
			for (ShipByMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getName());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < shipBy_headings.length; i++) {
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
	public boolean checkNameInShipByByOrgLevel(String name, String orgId) {
		List<ShipBy> list = shipByRepository.findByNameAndOrgId(name,orgId);
		if(list.size()>0) {
			return true;
		}
		return false;
	}

}