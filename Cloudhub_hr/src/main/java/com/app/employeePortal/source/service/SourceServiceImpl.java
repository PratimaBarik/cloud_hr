package com.app.employeePortal.source.service;

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

import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.source.entity.Source;
import com.app.employeePortal.source.mapper.SourceMapper;
import com.app.employeePortal.source.repository.SourceRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class SourceServiceImpl implements SourceService {

	@Autowired
	SourceRepository sourceRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] source_headings = { "Name" };

	@Override
	public SourceMapper saveSource(SourceMapper sourceMapper) {
		String sourceId = null;
		if (sourceMapper != null) {
			Source source = new Source();
			source.setCreationDate(new Date());
			source.setLiveInd(true);
			source.setEditInd(sourceMapper.isEditInd());
			source.setName(sourceMapper.getName());
			source.setOrgId(sourceMapper.getOrgId());
			source.setUpdatedBy(sourceMapper.getUserId());
			source.setUpdationDate(new Date());
			source.setUserId(sourceMapper.getUserId());
			source.setLink(sourceMapper.getLink());

			sourceId = sourceRepository.save(source).getSourceId();

		}
		SourceMapper resultMapper = getSourceBySourceId(sourceId);
		return resultMapper;
	}

	@Override
	public SourceMapper getSourceBySourceId(String sourceId) {

		Source source = sourceRepository.findBySourceId(sourceId);
		SourceMapper sourceMapper = new SourceMapper();

		if (null != source) {

			sourceMapper.setCreationDate(Utility.getISOFromDate(source.getCreationDate()));
			sourceMapper.setLiveInd(source.isLiveInd());
			sourceMapper.setName(source.getName());
			sourceMapper.setOrgId(source.getOrgId());
			sourceMapper.setUpdatedBy(employeeService.getEmployeeFullName(source.getUserId()));
			sourceMapper.setUpdationDate(Utility.getISOFromDate(source.getUpdationDate()));
			sourceMapper.setUserId(source.getUserId());
			sourceMapper.setSourceId(source.getSourceId());
			sourceMapper.setEditInd(source.isEditInd());
			sourceMapper.setLink(source.getLink());
		}

		return sourceMapper;
	}

	@Override
	public List<SourceMapper> getSourceByOrgId(String orgId) {

		List<SourceMapper> resultMapper = new ArrayList<>();
		List<Source> list = sourceRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getSourceBySourceId(li.getSourceId())).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public SourceMapper updateSource(String sourceId, SourceMapper sourceMapper) {

		Source source = sourceRepository.findBySourceId(sourceId);
		if (null != source) {

			source.setCreationDate(new Date());
			source.setLiveInd(sourceMapper.isLiveInd());
			source.setName(sourceMapper.getName());
			source.setOrgId(sourceMapper.getOrgId());
			source.setUpdatedBy(sourceMapper.getUserId());
			source.setUpdationDate(new Date());
			source.setUserId(sourceMapper.getUserId());
			source.setEditInd(sourceMapper.isEditInd());
			source.setLink(sourceMapper.getLink());
			sourceRepository.save(source);
		}
		SourceMapper resultMapper = getSourceBySourceId(sourceId);
		return resultMapper;
	}

	@Override
	public void deleteSource(String sourceId, String userId) {

		if (null != sourceId) {
			Source source = sourceRepository.findBySourceId(sourceId);

			source.setUpdationDate(new Date());
			source.setUpdatedBy(userId);
			source.setLiveInd(false);
			sourceRepository.save(source);
		}
	}

	@Override
	public	List<SourceMapper> getSourceByNameAndOrgId(String name, String orgId) {
		List<Source> list = sourceRepository.findByNameAndOrgIdAndLiveInd(name,orgId,true);
		List<SourceMapper> resultList = new ArrayList<SourceMapper>();
		if (null != list && !list.isEmpty()) {

			list.stream().map(source -> {
				SourceMapper sourceMapper = getSourceBySourceId(source.getSourceId());
				if (null != sourceMapper) {
					resultList.add(sourceMapper);

				}
				return resultList;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public HashMap getSourceCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<Source> list = sourceRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("SourceCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportSourceListToExcel(List<SourceMapper> list) {
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
		for (int i = 0; i < source_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(source_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != list && !list.isEmpty()) {
			for (SourceMapper source : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(source.getName());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < source_headings.length; i++) {
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
	public boolean checkSourceByNameByOrgLevel(String name, String orgId) {
		List<Source> list = sourceRepository.findByNameAndOrgIdAndLiveInd(name, orgId, true);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
}