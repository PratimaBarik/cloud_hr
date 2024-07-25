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

import com.app.employeePortal.category.entity.IdProofType;
import com.app.employeePortal.category.entity.IdProofTypeDelete;
import com.app.employeePortal.category.entity.UserSalaryBreakout;
import com.app.employeePortal.category.mapper.IdProofTypeMapper;
import com.app.employeePortal.category.repository.IdProofTypeDeleteRepository;
import com.app.employeePortal.category.repository.IdProofTypeRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.mapper.EventMapper;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class IdProofServiceImpl implements IdProofService {

	@Autowired
	IdProofTypeRepository idProofTypeRepository;

	@Autowired
	WebsiteRepository websiteRepository;

	@Autowired
	IdProofTypeDeleteRepository idProofTypeDeleteRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] headings = { "Name" };

	@Override
	public IdProofTypeMapper saveIdProofType(IdProofTypeMapper idProofTypeMapper) {

		String idProofTypeId = null;

		if (idProofTypeMapper != null) {
			IdProofType idProofType = new IdProofType();
			idProofType.setIdProofType(idProofTypeMapper.getIdProofType());
			idProofType.setCreationDate(new Date());
			idProofType.setUserId(idProofTypeMapper.getUserId());
			idProofType.setOrgId(idProofTypeMapper.getOrganizationId());
			idProofType.setEditInd(idProofTypeMapper.isEditInd());
			idProofType.setLiveInd(true);
			IdProofType dbIdProofType = idProofTypeRepository.save(idProofType);
			idProofTypeId = dbIdProofType.getIdProofTypeId();

			IdProofTypeDelete idProofTypeDelete = new IdProofTypeDelete();
			idProofTypeDelete.setIdProofTypeId(idProofTypeId);
			idProofTypeDelete.setUserId(idProofTypeMapper.getUserId());
			idProofTypeDelete.setOrgId(idProofTypeMapper.getOrganizationId());
			idProofTypeDelete.setUpdationDate(new Date());
			idProofTypeDeleteRepository.save(idProofTypeDelete);

		}
		IdProofTypeMapper resultMapper = getIdProofTypeById(idProofTypeId);
		return resultMapper;
	}

	@Override
	public IdProofTypeMapper getIdProofTypeById(String idProofTypeId) {

		IdProofType idProofType = idProofTypeRepository.findByIdProofTypeId(idProofTypeId);
		IdProofTypeMapper idProofTypeMapper = new IdProofTypeMapper();

		if (null != idProofType) {
			idProofTypeMapper.setIdProofTypeId(idProofType.getIdProofTypeId());

			idProofTypeMapper.setIdProofType(idProofType.getIdProofType());
			idProofTypeMapper.setOrganizationId(idProofType.getOrgId());
			idProofTypeMapper.setUserId(idProofType.getUserId());
			idProofTypeMapper.setEditInd(idProofType.isEditInd());
			idProofTypeMapper.setLiveInd(idProofType.isLiveInd());
			idProofTypeMapper.setCreationDate(Utility.getISOFromDate(idProofType.getCreationDate()));

			List<IdProofTypeDelete> list = idProofTypeDeleteRepository.findByOrgId(idProofType.getOrgId());
			if (null != list && !list.isEmpty()) {
				Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
				idProofTypeMapper.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
				idProofTypeMapper.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));

			}
		}
		return idProofTypeMapper;
	}

	@Override
	public IdProofTypeMapper updateIdProofType(String idProofTypeId, IdProofTypeMapper idProofTypeMapper) {

		IdProofTypeMapper resultMapper = null;

		if (null != idProofTypeMapper.getIdProofTypeId()) {

			IdProofType idProofType = idProofTypeRepository.findByIdProofTypeId(idProofTypeMapper.getIdProofTypeId());

			if (null != idProofTypeMapper.getIdProofType()) {
				idProofType.setIdProofType(idProofTypeMapper.getIdProofType());
				idProofType.setEditInd(idProofTypeMapper.isEditInd());
				idProofTypeRepository.save(idProofType);

			}
			resultMapper = getIdProofTypeById(idProofTypeMapper.getIdProofTypeId());
			IdProofTypeDelete idProofTypeDelete = idProofTypeDeleteRepository.findByIdProofTypeId(idProofTypeId);
			if (null != idProofTypeDelete) {
				idProofTypeDelete.setUpdationDate(new Date());
				idProofTypeDelete.setUpdatedBy(idProofTypeMapper.getUserId());

				idProofTypeDeleteRepository.save(idProofTypeDelete);
			} else {
				IdProofTypeDelete idProofTypeDelete1 = new IdProofTypeDelete();
				idProofTypeDelete1.setIdProofTypeId(idProofTypeId);
				idProofTypeDelete1.setUserId(idProofTypeMapper.getUserId());
				idProofTypeDelete1.setOrgId(idProofTypeMapper.getOrganizationId());
				idProofTypeDelete1.setUpdationDate(new Date());
				idProofTypeDelete1.setUpdatedBy(idProofTypeMapper.getUserId());
				idProofTypeDeleteRepository.save(idProofTypeDelete1);

			}
		}
		return resultMapper;
	}

	@Override
	public List<IdProofTypeMapper> getIdProofTypesByOrgId(String orgId) {
		List<IdProofTypeMapper> resultList = new ArrayList<IdProofTypeMapper>();
		List<IdProofType> idProofTypeList = idProofTypeRepository.findByOrgIdAndLiveInd(orgId, true);

		if (null != idProofTypeList && !idProofTypeList.isEmpty()) {
			idProofTypeList.stream().map(idProofType -> {
				IdProofTypeMapper idProofTypeMapper = new IdProofTypeMapper();

				idProofTypeMapper.setIdProofTypeId(idProofType.getIdProofTypeId());

				idProofTypeMapper.setIdProofType(idProofType.getIdProofType());
				idProofTypeMapper.setOrganizationId(idProofType.getOrgId());
				idProofTypeMapper.setUserId(idProofType.getUserId());
				idProofTypeMapper.setCreationDate(Utility.getISOFromDate(idProofType.getCreationDate()));
				idProofTypeMapper.setEditInd(idProofType.isEditInd());
				idProofTypeMapper.setLiveInd(idProofType.isLiveInd());
				resultList.add(idProofTypeMapper);

				return resultList;
			}).collect(Collectors.toList());

		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		List<IdProofTypeDelete> idProofTypeDelete = idProofTypeDeleteRepository.findByOrgId(orgId);
		if (null != idProofTypeDelete && !idProofTypeDelete.isEmpty()) {
			Collections.sort(idProofTypeDelete, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultList.get(0).setUpdationDate(Utility.getISOFromDate(idProofTypeDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeByUserId(idProofTypeDelete.get(0).getUserId());
			if (null != employeeDetails) {
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
	public boolean ipAddressExists(String url) {
		Website web = websiteRepository.getByUrl(url);
		if (null != web) {
			System.out.println("web>:>:::>::::::>>" + web.toString());
			return true;
		}
		return false;
	}

	@Override
	public List<IdProofTypeMapper> getIdProofTypeByUrl(String url) {
		Website web = websiteRepository.getByUrl(url);
		List<IdProofTypeMapper> resultList = new ArrayList<IdProofTypeMapper>();
		List<IdProofType> idProofTypeList = idProofTypeRepository.findByorgId(web.getOrgId());

		if (null != idProofTypeList && !idProofTypeList.isEmpty()) {
			idProofTypeList.stream().map(idProofType -> {
				IdProofTypeMapper idProofTypeMapper = new IdProofTypeMapper();

				idProofTypeMapper.setIdProofTypeId(idProofType.getIdProofTypeId());

				idProofTypeMapper.setIdProofType(idProofType.getIdProofType());
				idProofTypeMapper.setOrganizationId(idProofType.getOrgId());
				idProofTypeMapper.setUserId(idProofType.getUserId());
				idProofTypeMapper.setCreationDate(Utility.getISOFromDate(idProofType.getCreationDate()));
				idProofTypeMapper.setEditInd(idProofType.isEditInd());
				idProofTypeMapper.setLiveInd(idProofType.isLiveInd());
				resultList.add(idProofTypeMapper);

				return resultList;
			}).collect(Collectors.toList());

		}

		return resultList;
	}

	@Override
	public List<IdProofTypeMapper> getIdProofDetailsByNameByOrgLevel(String name, String orgId) {
		List<IdProofType> list = idProofTypeRepository.findByIdProofTypeContainingAndLiveIndAndOrgId(name, true,orgId);
		List<IdProofTypeMapper> resultList = new ArrayList<IdProofTypeMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(idProofType -> {
				System.out.println("idProofTyoeById=========" + idProofType.getIdProofTypeId());
				IdProofTypeMapper idProofTypeMapper = getIdProofTypeById(idProofType.getIdProofTypeId());
				if (null != idProofTypeMapper) {
					resultList.add(idProofTypeMapper);

				}
				return resultList;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public boolean checkIdProofNameInIdProofTypebyOrgLevel(String idProofType, String orgId) {
		List<IdProofType> idProofTypes = idProofTypeRepository.findByIdProofTypeAndLiveIndAndOrgId(idProofType, true,
				orgId);
		if (idProofTypes.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteIdProofTypeById(String idProofTypeId) {
		if (null != idProofTypeId) {
			IdProofType idProofType = idProofTypeRepository.findByIdProofTypeId(idProofTypeId);

			IdProofTypeDelete idProofTypeDelete = idProofTypeDeleteRepository.findByIdProofTypeId(idProofTypeId);
			if (null != idProofTypeDelete) {
				idProofTypeDelete.setUpdationDate(new Date());
				idProofTypeDelete.setUpdatedBy(idProofType.getUserId());
				idProofTypeDeleteRepository.save(idProofTypeDelete);
			}
			idProofType.setLiveInd(false);
			idProofTypeRepository.save(idProofType);
		}

	}

	@Override
	public HashMap getIdProofTypeCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<IdProofType> list = idProofTypeRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("IdProofTypeCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportIdProofTypeListToExcel(List<IdProofTypeMapper> list) {
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
			for (IdProofTypeMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getIdProofType());
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