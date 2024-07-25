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
import com.app.employeePortal.category.mapper.PaymentMapper;
import com.app.employeePortal.category.repository.PaymentRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.mapper.EventMapper;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] headings = {"Name"};

	@Override
	public PaymentMapper savePayment(PaymentMapper paymentMapper) {
		String paymentId = null;
		if (paymentMapper != null) {
			PaymentCategory payment = new PaymentCategory();
			payment.setCreationDate(new Date());
			payment.setLiveInd(true);
			payment.setName(paymentMapper.getName());
			payment.setOrganizationId(paymentMapper.getOrganizationId());
			payment.setUserId(paymentMapper.getUserId());
			payment.setUpdatedBy(paymentMapper.getUserId());
			payment.setUpdationDate(new Date());
			paymentId = paymentRepository.save(payment).getPaymentCatagoryId();

		}
		PaymentMapper resultMapper = getPaymentCatagoryById(paymentId);
		return resultMapper;
	}

	@Override
	public PaymentMapper getPaymentCatagoryById(String paymentCatagoryId) {

		PaymentCategory payment = paymentRepository.findByPaymentCatagoryId(paymentCatagoryId);
		PaymentMapper paymentMapper = new PaymentMapper();

		if (null != payment) {

			paymentMapper.setCreationDate(Utility.getISOFromDate(payment.getCreationDate()));
			paymentMapper.setLiveInd(payment.isLiveInd());
			paymentMapper.setName(payment.getName());
			paymentMapper.setOrganizationId(payment.getOrganizationId());
			paymentMapper.setUserId(payment.getUserId());
			paymentMapper.setPaymentCatagoryId(paymentCatagoryId);
			paymentMapper.setUpdatedBy(employeeService.getEmployeeFullName(payment.getUserId()));
			paymentMapper.setUpdationDate(Utility.getISOFromDate(payment.getCreationDate()));
		}

		return paymentMapper;
	}

	@Override
	public List<PaymentMapper> getPaymentCategoryByOrgId(String orgId) {

		List<PaymentMapper> resultMapper = new ArrayList<>();
		List<PaymentCategory> list = paymentRepository.findByOrganizationIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getPaymentCatagoryById(li.getPaymentCatagoryId()))
					.collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<PaymentCategory> list1 = paymentRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public PaymentMapper updatePaymentCategory(String paymentCatagoryId, PaymentMapper paymentMapper) {

		PaymentCategory payment = paymentRepository.findByPaymentCatagoryId(paymentCatagoryId);
		if (null != payment) {

			payment.setCreationDate(new Date());
			payment.setLiveInd(true);
			payment.setName(paymentMapper.getName());
			payment.setOrganizationId(paymentMapper.getOrganizationId());
			payment.setUserId(paymentMapper.getUserId());
			payment.setUpdatedBy(paymentMapper.getUserId());
			payment.setUpdationDate(new Date());
			paymentRepository.save(payment);
		}
		PaymentMapper resultMapper = getPaymentCatagoryById(paymentCatagoryId);
		return resultMapper;
	}

	@Override
	public void deletePaymentCatagory(String paymentCatagoryId, String userId) {

		if (null != paymentCatagoryId) {
			PaymentCategory payment = paymentRepository.findByPaymentCatagoryId(paymentCatagoryId);
			payment.setLiveInd(false);
			payment.setUpdatedBy(userId);
			payment.setUpdationDate(new Date());
			paymentRepository.save(payment);
		}
	}

	@Override
	public List<PaymentMapper> getPaymentByNameByOrgLevel(String name, String orgId) {
		List<PaymentCategory> list = paymentRepository.findByNameContainingAndLiveIndAndOrganizationId(name,true,orgId);
		List<PaymentMapper> resultMapper = new ArrayList<PaymentMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(payment -> {
				PaymentMapper mapper = getPaymentCatagoryById(payment.getPaymentCatagoryId());
				if (null != mapper) {
					resultMapper.add(mapper);
				}
				return resultMapper;
			}).collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<PaymentCategory> list1 = paymentRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public HashMap getPaymentCategoryCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<PaymentCategory> list = paymentRepository.findByOrganizationIdAndLiveInd(orgId, true);
		map.put("ServiceLineCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportPaymentListToExcel(List<PaymentMapper> list) {
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
			for (PaymentMapper mapper : list) {
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
	public boolean checkNameInPaymentByOrgLevel(String name, String orgId) {
		List<PaymentCategory> list = paymentRepository.findByNameAndOrganizationIdAndLiveInd(name,orgId, true);
		if(list.size()>0) {
			return true;
		}
		return false;
	}

}