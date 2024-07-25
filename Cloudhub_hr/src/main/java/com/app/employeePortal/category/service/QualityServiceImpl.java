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

import com.app.employeePortal.category.entity.Quality;
import com.app.employeePortal.category.entity.RoleType;
import com.app.employeePortal.category.mapper.QualityMapper;
import com.app.employeePortal.category.mapper.QualityMapper;
import com.app.employeePortal.category.repository.QualityRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class QualityServiceImpl implements QualityService {
	
	@Autowired
	QualityRepository qualityRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] headings = {"Name"};

	@Override
	public QualityMapper saveQuality(QualityMapper mapper) {
		String qualityId=null;
		if(mapper != null) {
			Quality quality = new Quality();
			quality.setCreationDate(new Date());
			quality.setLiveInd(true);
			quality.setOrgId(mapper.getOrgId());
			quality.setUpdatedBy(mapper.getUserId());
			quality.setUpdationDate(new Date());
			quality.setUserId(mapper.getUserId());
			quality.setCode(mapper.getCode());
			quality.setDescription(mapper.getDescription());
			qualityId= qualityRepository.save(quality).getQualityId();
			
		}
		QualityMapper resultMapper=getQualityByQualityId(qualityId);
		return resultMapper; 
	}

	public QualityMapper getQualityByQualityId(String qualityId) {
		
		Quality quality = qualityRepository.findByQualityIdAndLiveInd(qualityId,true);
		QualityMapper qualityMapper =new QualityMapper();

		if (null != quality) {
				
			qualityMapper.setCreationDate(Utility.getISOFromDate(quality.getCreationDate()));
			qualityMapper.setLiveInd(true);
			qualityMapper.setOrgId(quality.getOrgId());
			qualityMapper.setUpdatedBy(employeeService.getEmployeeFullName(quality.getUserId()));
			qualityMapper.setUpdationDate(Utility.getISOFromDate(quality.getUpdationDate()));
			qualityMapper.setUserId(quality.getUserId());
			qualityMapper.setQualityId(quality.getQualityId());
			qualityMapper.setCode(quality.getCode());
			qualityMapper.setDescription(quality.getDescription());
			}
		
		return qualityMapper;
		}
	
	@Override
	public List<QualityMapper> getQualityByOrgId(String orgId) {
		
		List<QualityMapper> resultMapper = new ArrayList<>();
		List<Quality> list = qualityRepository.findByOrgIdAndLiveInd(orgId,true);
		if (null != list) {
			resultMapper = list.stream().map(li->getQualityByQualityId(li.getQualityId()))
					.collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<Quality> list1 = qualityRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
		}

	@Override
	public QualityMapper updateQuality(String QualityId, QualityMapper mapper) {

		Quality quality=qualityRepository.findByQualityIdAndLiveInd(QualityId,true);
		if(null!=quality) {
			
			quality.setLiveInd(true);
			quality.setOrgId(mapper.getOrgId());
			quality.setUpdatedBy(mapper.getUserId());
			quality.setUpdationDate(new Date());
			quality.setUserId(mapper.getUserId());
			quality.setCode(mapper.getCode());
			quality.setDescription(mapper.getDescription());
			qualityRepository.save(quality);
		}
		QualityMapper resultMapper=getQualityByQualityId(QualityId);
		return resultMapper;
	}

	@Override
	public void deleteQuality(String qualityId, String userId) {
		
		if(null!=qualityId) {
			Quality quality=qualityRepository.findByQualityIdAndLiveInd(qualityId,true);
			
			quality.setUpdationDate(new Date());
			quality.setUpdatedBy(userId);
			quality.setLiveInd(false);
			qualityRepository.save(quality);
		}
	}

//	@Override
//	public List<QualityMapper> getQualityByName(String qualityName, String orgId) {
//		List<Quality>list=qualityRepository.findByQualityNameContainingAndOrgId(qualityName,orgId);
//		List<QualityMapper> resultList=new ArrayList<QualityMapper>();
//		if(null!=list&&!list.isEmpty()) {
//			list.stream().map(Quality->{
//				QualityMapper mapper=getQualityByQualityId(Quality.getQualityId());
//				if(null!=mapper) {
//					resultList.add(mapper);
//				}
//				return resultList;
//			}).collect(Collectors.toList());
//		}
//		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
//
//		List<Quality> list1 = qualityRepository.findAll();
//		if (null != list1 && !list1.isEmpty()) {
//			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
//
//			resultList.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
//			resultList.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
//		}
//		return resultList;
//	}

	@Override
	public HashMap getQualityCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<Quality> list = qualityRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("QualityCount", list.size());
		return map;
	}

//	@Override
	public ByteArrayInputStream exportQualityListToExcel(List<QualityMapper> list) {
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
			for (QualityMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getDescription());
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

//	@Override
//	public boolean checkQualityNameInQuality(String qualityName, String orgId) {
//		List<Quality> quality = qualityRepository.findByQualityNameAndLiveIndAndOrgId(qualityName, true,
//				orgId);
//		if (quality.size() > 0) {
//			return true;
//		}
//		return false;
//	}

}