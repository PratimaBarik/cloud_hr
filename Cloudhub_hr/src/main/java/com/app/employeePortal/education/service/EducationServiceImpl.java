package com.app.employeePortal.education.service;

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

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.education.entity.EducationType;
import com.app.employeePortal.education.entity.EducationTypeDelete;
import com.app.employeePortal.education.mapper.EducationTypeMapper;
import com.app.employeePortal.education.repository.EducationTypeDeleteRepository;
import com.app.employeePortal.education.repository.EducationTypeRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.expense.entity.ExpenseType;
import com.app.employeePortal.sector.mapper.SectorMapper;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class EducationServiceImpl implements EducationService {

	@Autowired
	EducationTypeRepository educationTypeRepository;
	@Autowired
	EducationTypeDeleteRepository educationTypeDeleteRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] education_headings = {"Name"};

	@Override
	public EducationTypeMapper saveEducationType(EducationTypeMapper educationTypeMapper) {
		String educationTypeId = null;

		if (educationTypeMapper != null) {
			EducationType educationType = new EducationType();
			educationType.setEducationType(educationTypeMapper.getEducationType());
			educationType.setCreationDate(new Date());
			educationType.setUserId(educationTypeMapper.getUserId());
			educationType.setOrgId(educationTypeMapper.getOrganizationId());
			educationType.setEditInd(educationTypeMapper.isEditInd());
			educationType.setLiveInd(true);

			EducationType dbEducationType = educationTypeRepository.save(educationType);
			educationTypeId = dbEducationType.getEducationTypeId();
			
			EducationTypeDelete educationTypeDelete=new EducationTypeDelete();
			educationTypeDelete.setEducationTypeId(educationTypeId);
			educationTypeDelete.setOrgId(educationTypeMapper.getOrganizationId());
			educationTypeDelete.setUserId(educationTypeMapper.getUserId());
			educationTypeDelete.setUpdationDate(new Date());
			educationTypeDelete.setUpdatedBy(educationTypeMapper.getUserId());
			educationTypeDeleteRepository.save(educationTypeDelete);
		}
		EducationTypeMapper resultMapper = getEducationTypeById(educationTypeId);
		return resultMapper;
	}

	@Override
	public EducationTypeMapper getEducationTypeById(String educationTypeId) {
		EducationType educationType = educationTypeRepository.findByEducationTypeId(educationTypeId);
		EducationTypeMapper educationTypeMapper = new EducationTypeMapper();

		if (null != educationType) {
			educationTypeMapper.setEducationTypeId(educationType.getEducationTypeId());

			educationTypeMapper.setEducationType(educationType.getEducationType());
			educationTypeMapper.setOrganizationId(educationType.getOrgId());
			educationTypeMapper.setUserId(educationType.getUserId());
			educationTypeMapper.setEditInd(educationType.isEditInd());
			educationTypeMapper.setCreationDate(Utility.getISOFromDate(educationType.getCreationDate()));
			List<EducationTypeDelete> list=educationTypeDeleteRepository.findByOrgId(educationType.getOrgId());
			if(null!=list&&!list.isEmpty()) {
				Collections.sort(list,(p1,p2)->p2.getUpdationDate().compareTo(p1.getUpdationDate()));
				educationTypeMapper.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
				educationTypeMapper.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
			}
		}
		return educationTypeMapper;
	}

	@Override
	public EducationTypeMapper updateEducationType(String educationTypeId, EducationTypeMapper educationTypeMapper) {
		EducationTypeMapper resultMapper = null;

		if (null != educationTypeMapper.getEducationTypeId()) {

			EducationType educationType = educationTypeRepository
					.findByEducationTypeId(educationTypeMapper.getEducationTypeId());

			if (null != educationTypeMapper.getEducationType()) {
				educationType.setEducationType(educationTypeMapper.getEducationType());
				educationType.setEditInd(educationTypeMapper.isEditInd());
				educationTypeRepository.save(educationType);
				
				EducationTypeDelete educationTypeDelete=educationTypeDeleteRepository.findByEducationTypeId(educationTypeMapper.getEducationTypeId());
				if (null != educationTypeDelete) {
					educationTypeDelete.setUpdationDate(new Date());
					educationTypeDelete.setUpdatedBy(educationTypeMapper.getUserId());
					educationTypeDeleteRepository.save(educationTypeDelete);
				}else {
					EducationTypeDelete educationTypeDelete1 = new EducationTypeDelete();
					educationTypeDelete1.setEducationTypeId(educationTypeId);
					educationTypeDelete1.setUserId(educationTypeMapper.getUserId());
					educationTypeDelete1.setOrgId(educationTypeMapper.getOrganizationId());
					educationTypeDelete1.setUpdationDate(new Date());
					educationTypeDelete1.setUpdatedBy(educationTypeMapper.getUserId());
					educationTypeDeleteRepository.save(educationTypeDelete1);	
				}
			}
			resultMapper = getEducationTypeById(educationTypeMapper.getEducationTypeId());
		}
		return resultMapper;
	}

	@Override
	public List<EducationTypeMapper> getEducationTypesByOrgId(String orgId) {
		List<EducationTypeMapper> resultList = new ArrayList<EducationTypeMapper>();
		List<EducationType> educationTypeList = educationTypeRepository.findByOrgIdAndLiveInd(orgId,true);

		if (null != educationTypeList && !educationTypeList.isEmpty()) {
			for(EducationType educationType : educationTypeList) {
			
				EducationTypeMapper educationTypeMapper = new EducationTypeMapper();
				educationTypeMapper.setEducationTypeId(educationType.getEducationTypeId());
				educationTypeMapper.setEducationType(educationType.getEducationType());
				educationTypeMapper.setEditInd(educationType.isEditInd());

				educationTypeMapper.setCreationDate(Utility.getISOFromDate(educationType.getCreationDate()));
				resultList.add(educationTypeMapper);

			}

		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		List<EducationTypeDelete> educationTypeDelete = educationTypeDeleteRepository.findByOrgId(orgId);
		if (null != educationTypeDelete && !educationTypeDelete.isEmpty()) {
			Collections.sort(educationTypeDelete,
					( p1,p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			
			resultList.get(0).setUpdationDate(Utility.getISOFromDate(educationTypeDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(educationTypeDelete.get(0).getUserId());
			if(null!=employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					resultList.get(0).setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					resultList.get(0).setName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		
		return resultList;
	}

	@Override
	public List<EducationTypeMapper> getEducationTypeByNameByOrgLevel(String name, String orgId) {
		List<EducationType> list = educationTypeRepository.findByEducationTypeContainingAndOrgId(name,orgId);
		if (null != list && !list.isEmpty()) {
			return list.stream().map(educationType -> {
				EducationTypeMapper educationTypeMapper = getEducationTypeById(educationType.getEducationTypeId());
				if (null != educationTypeMapper) {
					return educationTypeMapper;
				}
				return null;
			}).collect(Collectors.toList());

		}

		return null;
	}

	@Override
	public boolean checkEducationNameInEducationTypeByOrgLevel(String educationType, String orgId) {
		List<EducationType> educationTypes = educationTypeRepository.findByEducationTypeAndLiveIndAndOrgId(educationType,true,orgId);
		if (educationTypes.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteEducationTypeById(String educationTypeId) {
		if (null != educationTypeId) {
			EducationType educationType = educationTypeRepository.findByEducationTypeId(educationTypeId);
			
			EducationTypeDelete educationTypeDelete = educationTypeDeleteRepository.findByEducationTypeId(educationTypeId);
			if(null!=educationTypeDelete) {
				educationTypeDelete.setUpdationDate(new Date());
				educationTypeDelete.setUpdatedBy(educationType.getUserId());
				educationTypeDeleteRepository.save(educationTypeDelete);
			}
			
			educationType.setLiveInd(false);
			educationTypeRepository.save(educationType);
		}

	}

	@Override
	public HashMap getEducationTypeCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<EducationType> list = educationTypeRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("EducationTypeCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportEducationListToExcel(List<EducationTypeMapper> list) {
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
		for (int i = 0; i < education_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(education_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != list && !list.isEmpty()) {
			for (EducationTypeMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getEducationType());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < education_headings.length; i++) {
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
