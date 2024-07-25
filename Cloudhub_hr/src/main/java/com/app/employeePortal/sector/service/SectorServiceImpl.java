package com.app.employeePortal.sector.service;

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

import com.app.employeePortal.candidate.mapper.CandidateViewMapper;
import com.app.employeePortal.category.entity.ShipBy;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.recruitment.entity.Website;
import com.app.employeePortal.recruitment.repository.WebsiteRepository;
import com.app.employeePortal.sector.entity.SectorDetails;
import com.app.employeePortal.sector.entity.SectorDetailsDelete;
import com.app.employeePortal.sector.mapper.SectorMapper;
import com.app.employeePortal.sector.repository.SectorDetailsDeleteRepository;
import com.app.employeePortal.sector.repository.SectorDetailsRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class SectorServiceImpl implements SectorService {

	@Autowired
	SectorDetailsRepository sectorDetailsRepository;
	@Autowired
	WebsiteRepository websiteRepository;
	@Autowired
	SectorDetailsDeleteRepository sectorDetailsDeleteRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] sector_headings = {"Name"};
	@Override
	public List<SectorMapper> saveToSectorDetails(SectorMapper sectorMapper) {
		List<SectorMapper> resultList = new ArrayList<>();
		String sectorId = null;
		if (sectorMapper != null) {
			SectorDetails sectorDetails = new SectorDetails();
			sectorDetails.setSectorName(sectorMapper.getSectorName());
			sectorDetails.setCreationDate(new Date());
			sectorDetails.setUserId(sectorMapper.getUserId());
			sectorDetails.setOrgId(sectorMapper.getOrganizationId());
			sectorDetails.setLiveInd(true);
			sectorDetails.setEditInd(sectorMapper.isEditInd());

			SectorDetails dbSectorDetails = sectorDetailsRepository.save(sectorDetails);
			sectorId = dbSectorDetails.getSectorId();
			
			SectorDetailsDelete sectorDetailsDelete=new SectorDetailsDelete();
			sectorDetailsDelete.setSectorId(sectorId);
			sectorDetailsDelete.setUserId(sectorMapper.getUserId());
			sectorDetailsDelete.setOrgId(sectorMapper.getOrganizationId());
			sectorDetailsDelete.setUpdationDate(new Date());
			sectorDetailsDelete.setUpdatedBy(sectorMapper.getUserId());
			sectorDetailsDeleteRepository.save(sectorDetailsDelete);
		}
		SectorMapper resultMapper=getsectorDetailsById(sectorId);
		resultList.add(resultMapper);
		return resultList;
	}
	@Override
	public SectorMapper getsectorDetailsById(String sectorId) {
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorId(sectorId);
		SectorMapper sectorMapper = new SectorMapper();

		if (null != sectorDetails) {
			sectorMapper.setSectorId(sectorDetails.getSectorId());

			sectorMapper.setSectorName(sectorDetails.getSectorName());

			sectorMapper.setEditInd(sectorDetails.isEditInd());

			sectorMapper.setCreationDate(Utility.getISOFromDate(sectorDetails.getCreationDate()));
		
			List<SectorDetailsDelete>list=sectorDetailsDeleteRepository.findByOrgId(sectorDetails.getOrgId());
			
			if(null!=list&&!list.isEmpty()) {
				Collections.sort(list, (p1, p2)-> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
				
				sectorMapper.setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
				sectorMapper.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
			}
		}
		return sectorMapper;
	}
	/*
	 * @Override public SectorMapper updateSector(SectorMapper sectorMapper) {
	 * //SectorMapper resultMapper =null;
	 * 
	 * SectorDetails sector
	 * =sectorDetailsRepository.findOne(sectorMapper.getSectorId());
	 * 
	 * sector.setSectorName(sectorMapper.getSectorName());
	 * 
	 * SectorDetails sectorr = sectorDetailsRepository.save(sector);
	 * 
	 * if(null!=sectorMapper.getSectorId()) {
	 * 
	 * SectorDetails sector =
	 * sectorDetailsRepository.findOne(sectorMapper.getSectorId());
	 * 
	 * if(null!=sectorMapper.getSectorName()) {
	 * sector.setSectorName(sectorMapper.getSectorName());
	 * sectorDetailsRepository.save(sector);
	 * 
	 * } resultMapper = getsectorDetails(sectorMapper.getSectorId()); } return
	 * sectorMapper;
	 * 
	 * }
	 */
	private SectorMapper getsectorDetails(String sectorId) {
		SectorDetails sectorDetails = sectorDetailsRepository.getById(sectorId);
		SectorMapper sectorMapper = new SectorMapper();

		if (null != sectorDetails) {
			sectorMapper.setSectorId(sectorDetails.getSectorId());
			// sectorMapper.setCreatorId(sectorDetails.getCreator_id());
			sectorMapper.setSectorName(sectorDetails.getSectorName());
			sectorMapper.setCreationDate(Utility.getISOFromDate(sectorDetails.getCreationDate()));
		}
		return sectorMapper;
	}
	@Override
	public List<SectorMapper> getSectorTypeByOrgId(String orgId) {
		List<SectorMapper> resultList = new ArrayList<SectorMapper>();
		List<SectorDetails> sectorTypeList = sectorDetailsRepository.findByOrgIdAndLiveInd(orgId, true);

		if (null != sectorTypeList && !sectorTypeList.isEmpty()) {
			//return sectorTypeList.stream().map(sector -> {
			for(SectorDetails sector :sectorTypeList ) {
				SectorMapper sectorMapper = new SectorMapper();
				sectorMapper.setSectorId(sector.getSectorId());
				sectorMapper.setSectorName(sector.getSectorName());
				sectorMapper.setEditInd(sector.isEditInd());
				sectorMapper.setLiveInd(sector.isLiveInd());
				sectorMapper.setCreationDate(Utility.getISOFromDate(sector.getCreationDate()));

				resultList.add(sectorMapper);
				//return resultList;

			}  //).flatMap(l -> l.stream()).collect(Collectors.toList());
		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
		
		List<SectorDetailsDelete> sectorDetailsDelete = sectorDetailsDeleteRepository.findByOrgId(orgId);
		if (null != sectorDetailsDelete && !sectorDetailsDelete.isEmpty()) {
			Collections.sort(sectorDetailsDelete,
					( p1,p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			
			resultList.get(0).setUpdationDate(Utility.getISOFromDate(sectorDetailsDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(sectorDetailsDelete.get(0).getUserId());
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
	public List<SectorMapper> getSectorDetailsByuserId(String userId) {
		List<SectorDetails> sectorList = sectorDetailsRepository.findByUserIdAndLiveInd(userId, true);
		// System.out.println("###########"+customerList);
		if (null != sectorList && !sectorList.isEmpty()) {
			return sectorList.stream().map(sector -> {

				SectorMapper sectorMapperr = getsectorDetailsById(sector.getSectorId());
				if (null != sectorMapperr.getSectorId()) {

					return sectorMapperr;
				}
				return null;
				}).collect(Collectors.toList());
		}
		return null;
	}
	@Override
	public List<SectorMapper> updateSector(String sectorId, SectorMapper sectorMapper) {
		List<SectorMapper> resultList = new ArrayList<>();

		SectorDetails sector = sectorDetailsRepository.getSectorDetailsBySectorId(sectorId);

		if (null != sector) {

			sector.setLiveInd(false);
			sectorDetailsRepository.save(sector);

			SectorDetails newSector = new SectorDetails();

			newSector.setSectorId(sectorId);

			if (null != sectorMapper.getSectorName()) {
				newSector.setSectorName(sectorMapper.getSectorName());
			}

			// customerMapper.setCreationDate(Utility.getISOFromDate(customer.getCreation_date()))

			newSector.setCreationDate(new Date());
			newSector.setLiveInd(true);
			newSector.setEditInd(sectorMapper.isEditInd());
			newSector.setUserId(sectorMapper.getUserId());
			newSector.setOrgId(sectorMapper.getOrganizationId());

			SectorDetails updatedSector = sectorDetailsRepository.save(newSector);
			
			SectorDetailsDelete sectorDetailsDelete=sectorDetailsDeleteRepository.getSectorDetailsById(sectorId);
			if (null !=sectorDetailsDelete) {
				sectorDetailsDelete.setUpdationDate(new Date());
				sectorDetailsDelete.setUserId(sectorMapper.getUserId());
				sectorDetailsDelete.setOrgId(sectorMapper.getOrganizationId());
				sectorDetailsDelete.setUpdatedBy(sectorMapper.getUserId());
				sectorDetailsDeleteRepository.save(sectorDetailsDelete);
			}else {
				SectorDetailsDelete sectorDetailsDelete1 = new SectorDetailsDelete();
				sectorDetailsDelete1.setSectorId(sectorId);
				sectorDetailsDelete1.setUserId(sectorMapper.getUserId());
				sectorDetailsDelete1.setOrgId(sectorMapper.getOrganizationId());
				sectorDetailsDelete1.setUpdationDate(new Date());
				sectorDetailsDelete1.setUpdatedBy(sectorMapper.getUserId());
				sectorDetailsDeleteRepository.save(sectorDetailsDelete1);
			}
			SectorMapper resultMapper = getsectorDetailsById(updatedSector.getSectorId());
			resultList.add(resultMapper);
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
	public List<SectorMapper> getSectorTypeByUrl(String url) {
		Website web = websiteRepository.getByUrl(url);
		List<SectorDetails> sectorTypeList = sectorDetailsRepository.getSectorTypeByOrgId(web.getOrgId());

		if (null != sectorTypeList && !sectorTypeList.isEmpty()) {
			return sectorTypeList.stream().map(sector -> {

				SectorMapper sectorMapper = new SectorMapper();
				sectorMapper.setSectorId(sector.getSectorId());
				sectorMapper.setSectorName(sector.getSectorName());
				sectorMapper.setEditInd(sector.isEditInd());
				sectorMapper.setCreationDate(Utility.getISOFromDate(sector.getCreationDate()));

				return sectorMapper;
			}).collect(Collectors.toList());
	}
		return null;
	}
	@Override
	public List<SectorMapper> getSectorByNameByOrgLevel(String name, String orgId) {
		List<SectorDetails> list = sectorDetailsRepository.findBySectorNameContainingAndOrgId(name,orgId);
		if (null != list && !list.isEmpty()) {
			return list.stream().map(sectorDetails -> {
				System.out.println("sectorDetailsById=========" + sectorDetails.getSectorId());
				SectorMapper sectorMapper = getsectorDetailsById(sectorDetails.getSectorId());
				if (null != sectorMapper) {

					return sectorMapper;
				}
				return null;
			}).collect(Collectors.toList());
		}
		return null;
	}
	@Override
	public boolean checkSectorNameInSectorDetailsByOrgLevel(String sectorName, String orgId) {
		List<SectorDetails> sectorNames = sectorDetailsRepository.findBySectorNameAndLiveIndAndOrgId(sectorName,true, orgId);
		if (sectorNames.size() > 0) {
			return true;
		}
		return false;
	}
	public void deleteSectorDetailsById(String sectorId) {

		if (null != sectorId) {
			SectorDetails sectorDetailss = sectorDetailsRepository.getSectorDetailsById(sectorId);
			
			SectorDetailsDelete sectorDetailsDelete = sectorDetailsDeleteRepository.getSectorDetailsById(sectorId);
			if(null!=sectorDetailsDelete) {
				sectorDetailsDelete.setUpdationDate(new Date());
				sectorDetailsDelete.setUpdatedBy(sectorDetailsDelete.getUserId());
				sectorDetailsDeleteRepository.save(sectorDetailsDelete);
			}
			sectorDetailss.setLiveInd(false);
			sectorDetailsRepository.save(sectorDetailss);
		}
	}
	@Override
	public HashMap getSectorCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<SectorDetails> list = sectorDetailsRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("SectorCount", list.size());
		return map;
	}
	@Override
	public ByteArrayInputStream exportSectorListToExcel(List<SectorMapper> list) {
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
		for (int i = 0; i < sector_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(sector_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != list && !list.isEmpty()) {
			for (SectorMapper sector : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(sector.getName());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < sector_headings.length; i++) {
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
