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
import com.app.employeePortal.category.entity.ServiceLine;
import com.app.employeePortal.category.entity.UserSalaryBreakout;
import com.app.employeePortal.category.mapper.ItemTaskMapper;
import com.app.employeePortal.category.repository.ItemTaskRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.mapper.EventMapper;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class ItemTaskServiceImpl implements ItemTaskService {

	@Autowired
	ItemTaskRepository itemTaskRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] headings = { "Name" };

	@Override
	public ItemTaskMapper saveItemTask(ItemTaskMapper itemTaskMapper) {
		String itemTaskId = null;
		if (itemTaskMapper != null) {
			ItemTask itemTask = new ItemTask();
			itemTask.setCreationDate(new Date());
			itemTask.setLiveInd(true);
			itemTask.setName(itemTaskMapper.getName());
			itemTask.setOrgId(itemTaskMapper.getOrgId());
			itemTask.setUserId(itemTaskMapper.getUserId());
			itemTask.setUpdatedBy(itemTaskMapper.getUserId());
			itemTask.setUpdationDate(new Date());
			itemTaskId = itemTaskRepository.save(itemTask).getItemTaskId();

		}
		ItemTaskMapper resultMapper = getItemTaskById(itemTaskId);
		return resultMapper;
	}

	@Override
	public ItemTaskMapper getItemTaskById(String itemTaskId) {

		ItemTask itemTask = itemTaskRepository.findByItemTaskId(itemTaskId);
		ItemTaskMapper itemTaskMapper = new ItemTaskMapper();

		if (null != itemTask) {

			itemTaskMapper.setCreationDate(Utility.getISOFromDate(itemTask.getCreationDate()));
			itemTaskMapper.setLiveInd(itemTask.isLiveInd());
			itemTaskMapper.setName(itemTask.getName());
			itemTaskMapper.setOrgId(itemTask.getOrgId());
			itemTaskMapper.setUserId(itemTask.getUserId());
			itemTaskMapper.setItemTaskId(itemTaskId);
			itemTaskMapper.setUpdatedBy(employeeService.getEmployeeFullName(itemTask.getUserId()));
			itemTaskMapper.setUpdationDate(Utility.getISOFromDate(itemTask.getCreationDate()));
		}

		return itemTaskMapper;
	}

	@Override
	public List<ItemTaskMapper> getItemTaskMapperByOrgId(String orgId) {

		List<ItemTaskMapper> resultMapper = new ArrayList<>();
		List<ItemTask> list = itemTaskRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getItemTaskById(li.getItemTaskId())).collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<ItemTask> list1 = itemTaskRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public ItemTaskMapper updateItemTask(String itemTaskId, ItemTaskMapper itemTaskMapper) {

		ItemTask itemTask = itemTaskRepository.findByItemTaskId(itemTaskId);
		if (null != itemTask) {

			itemTask.setCreationDate(new Date());
			itemTask.setLiveInd(true);
			itemTask.setName(itemTaskMapper.getName());
			itemTask.setOrgId(itemTaskMapper.getOrgId());
			itemTask.setUserId(itemTaskMapper.getUserId());
			itemTask.setUpdatedBy(itemTaskMapper.getUserId());
			itemTask.setUpdationDate(new Date());
			itemTaskRepository.save(itemTask);
		}
		ItemTaskMapper resultMapper = getItemTaskById(itemTaskId);
		return resultMapper;
	}

	@Override
	public void deleteItemTask(String itemTaskId, String userId) {

		if (null != itemTaskId) {
			ItemTask itemTask = itemTaskRepository.findByItemTaskId(itemTaskId);
			itemTask.setLiveInd(false);
			itemTask.setUpdatedBy(userId);
			itemTask.setUpdationDate(new Date());
			itemTaskRepository.save(itemTask);
		}
	}

	@Override
	public List<ItemTaskMapper> getItemTaskByNameByOrgLevel(String name, String orgId) {
		List<ItemTask> list = itemTaskRepository.findByNameContainingAndLiveIndAndOrgId(name, true,orgId);
		List<ItemTaskMapper> resultMapper = new ArrayList<ItemTaskMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(itemTask -> {
				ItemTaskMapper mapper = getItemTaskById(itemTask.getItemTaskId());
				if (null != mapper) {
					resultMapper.add(mapper);
				}
				return resultMapper;
			}).collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<ItemTask> list1 = itemTaskRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public HashMap getItemTaskCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<ItemTask> list = itemTaskRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("ItemTaskCount", list.size());
		return map;
	}

	@Override
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
	public boolean checkNameInItemTaskByOrgLevel(String name, String orgId) {
		List<ItemTask> list = itemTaskRepository.findByNameAndOrgIdAndLiveInd(name, orgId, true);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

}