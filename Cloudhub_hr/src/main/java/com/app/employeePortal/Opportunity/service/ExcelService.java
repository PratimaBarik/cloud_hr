
package com.app.employeePortal.Opportunity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.employeePortal.Opportunity.entity.Phone;
import com.app.employeePortal.Opportunity.mapper.FieldDetailsDTO;
import com.app.employeePortal.Opportunity.mapper.PhoneDetailsDTO;
import com.app.employeePortal.Opportunity.repository.PhoneRepository;
import com.app.employeePortal.address.repository.AddressInfoRepository;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.email.service.EmailService;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.immport.entity.ExcelImport;
import com.app.employeePortal.immport.repository.ExcelImportRepository;
import com.app.employeePortal.location.repository.LocationDetailsRepository;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class ExcelService {

	@Autowired
	ExcelImportRepository excelimportRepository;
	
	@Autowired
	Phoneservice phoneService;
		
	@Autowired
	EmployeeRepository userRepository;
	
	@Autowired
	LocationDetailsRepository locationDetailsRepository;
	
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	AddressInfoRepository addressInfoRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	EmailService emailService;
	@Autowired
	PhoneRepository phoneRepository;
	
	public String getJsonMessageFromDb(String excelId) {
		String excelJson = null;
		if (null != excelId) {

			ExcelImport excelImport = excelimportRepository.findById(excelId).orElseThrow(
					(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Excel not found " + excelId)));

			excelJson = excelImport.getExcelJson();
		}

		return excelJson;
	}
	
	public List<String> getAllKeys(String jsonstring) {

		JsonParser parser = new JsonParser();
		JsonObject jObj = (JsonObject) parser.parse(jsonstring);

		List<String> keys = new ArrayList<String>();
		for (Entry<String, JsonElement> e : jObj.entrySet()) {

			keys.add(e.getKey());
		}
		return keys;

	}
	
	public String insertPhoneDetails(FieldDetailsDTO fieldDetailsDTO) throws Exception {
		List<FieldDetailsDTO> list = getAllMappingEntityFields3();
		mapPhoneDetails(list, fieldDetailsDTO);
	
		return fieldDetailsDTO.getExcelId();
	}

	public List<FieldDetailsDTO> getAllMappingEntityFields3() {

		ArrayList<FieldDetailsDTO> list = new ArrayList<FieldDetailsDTO>();

		FieldDetailsDTO company = new FieldDetailsDTO("Company", "company");
		FieldDetailsDTO model = new FieldDetailsDTO("Model", "model");
		FieldDetailsDTO IMEI = new FieldDetailsDTO("IMEI", "IMEI");
		FieldDetailsDTO task1 = new FieldDetailsDTO("Task1", "task1");
		FieldDetailsDTO task2 = new FieldDetailsDTO("Task2", "task2");
		FieldDetailsDTO task3 = new FieldDetailsDTO("Task3", "task3");
		FieldDetailsDTO OS = new FieldDetailsDTO("OS", "OS");
		FieldDetailsDTO GB = new FieldDetailsDTO("GB", "GB");
		FieldDetailsDTO color = new FieldDetailsDTO("Color", "color");
		FieldDetailsDTO condition = new FieldDetailsDTO("Condition", "condition");

		list.add(company);
		list.add(model);
		list.add(IMEI);
		list.add(task1);
		list.add(task2);
		list.add(task3);
		list.add(OS);
		list.add(GB);
		list.add(color);
		list.add(condition);

		return list;
	}

	public int mapPhoneDetails(List<FieldDetailsDTO> mappingList, FieldDetailsDTO fieldDetailsDTO) throws Exception {

		List<PhoneDetailsDTO> mapperList = new ArrayList<>();

		String excelJson = getJsonMessageFromDb(fieldDetailsDTO.getExcelId());
		int count = 0;
		if (null != excelJson) {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(excelJson);

			List<String> keys = getAllKeys(jsonObj.toString());
			JsonElement jsonElement = jsonObj.get(keys.get(0));

			for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {

				PhoneDetailsDTO phoneDetailsDTO = new PhoneDetailsDTO();

				for (FieldDetailsDTO excelMapping : mappingList) {
					String header = excelMapping.getFieldViewName();

					String mappingField = excelMapping.getFieldKey();

					JsonElement value = jsonElement.getAsJsonArray().get(i).getAsJsonObject().get(header);

					if (mappingField.equals("company")) {

						phoneDetailsDTO.setCompany(value.getAsString());

					}
					if (mappingField.equals("model")) {

						phoneDetailsDTO.setModel(value.getAsString());

					}
					if (mappingField.equals("IMEI")) {

						phoneDetailsDTO.setIMEI(value.getAsString());

					}
					if (mappingField.equals("task1")) {

						phoneDetailsDTO.setTask1(value.getAsString());

					}
					if (mappingField.equals("task2")) {

						phoneDetailsDTO.setTask2(value.getAsString());

					}
					if (mappingField.equals("task3")) {

						phoneDetailsDTO.setTask3(value.getAsString());

					}
					if (mappingField.equals("OS")) {

						phoneDetailsDTO.setOS(value.getAsString());

					}
					if (mappingField.equals("GB")) {

						phoneDetailsDTO.setGB(value.getAsString());

					}
					if (mappingField.equals("color")) {

						phoneDetailsDTO.setColor(value.getAsString());

					}
					if (mappingField.equals("condition")) {

						phoneDetailsDTO.setConditions(value.getAsString());

					}
				}

				mapperList.add(phoneDetailsDTO);
			}
			if (null != mapperList && !mapperList.isEmpty()) {
				if (fieldDetailsDTO.isXlUpdateInd() == true) {
					List<Phone> plist = phoneRepository.findByOpportunityId(fieldDetailsDTO.getOpportunityId());
					if (null != plist && !plist.isEmpty()) {
						phoneRepository.deleteAll(plist);
					}
				}
				float phcount=0;
				for (PhoneDetailsDTO phoneDetailsDTO1 : mapperList) {

					// carDetailsDTO1.setOrgId(fieldDetailsDTO.getOrgId());
					phoneDetailsDTO1.setOpportunityId(fieldDetailsDTO.getOpportunityId());
					phoneDetailsDTO1.setExcelId(fieldDetailsDTO.getExcelId());
					phoneDetailsDTO1.setUserId(fieldDetailsDTO.getUserId());
					phoneDetailsDTO1.setCustomerId(fieldDetailsDTO.getCustomerId());
					String phn1 = phoneService.savePhoneDetails(phoneDetailsDTO1);
					
					phcount++;
//
//					if (phoneDetailsDTO1.getTask1() != null && !phoneDetailsDTO1.getTask1().isEmpty()) {
//						RepairTask repairTask = new RepairTask();
//						repairTask.setTaskName(phoneDetailsDTO1.getTask1());
//						repairTask.setPhoneId(phn1.getPhoneId());
//						repairTaskRepository.save(repairTask);
//					}
//					if (phoneDetailsDTO1.getTask2() != null && !phoneDetailsDTO1.getTask2().isEmpty()) {
//						RepairTask repairTask2 = new RepairTask();
//						repairTask2.setTaskName(phoneDetailsDTO1.getTask2());
//						repairTask2.setPhoneId(phn1.getPhoneId());
//						repairTaskRepository.save(repairTask2);
//					}
//					if (phoneDetailsDTO1.getTask3() != null && !phoneDetailsDTO1.getTask3().isEmpty()) {
//						RepairTask repairTask3 = new RepairTask();
//						repairTask3.setTaskName(phoneDetailsDTO1.getTask3());
//						repairTask3.setPhoneId(phn1.getPhoneId());
//						repairTaskRepository.save(repairTask3);
//					}

				}

//				Calendar calendar = Calendar.getInstance();
//				String month = Integer.toString(calendar.get(Calendar.MONTH));
//				MonthlyRepairOrder monthlyOrder = monthlyRepairOrderRepository.findByMonth(month);
//				if (monthlyOrder != null) {
//					monthlyOrder.setOrderCount(monthlyOrder.getOrderCount() + 1);
//					monthlyOrder.setPhnCount(monthlyOrder.getPhnCount()+phcount);
//					monthlyRepairOrderRepository.save(monthlyOrder);
//				} else {
//					MonthlyRepairOrder monthlyOrder1 = new MonthlyRepairOrder();
//					monthlyOrder1.setOrderCount(1);
//					monthlyOrder1.setMonth(month);
//					monthlyOrder1.setPhnCount(phcount);
//					monthlyOrder1.setCreateAt(new Date());
//					monthlyRepairOrderRepository.save(monthlyOrder1);
//				}
//				
////				OrgRepairMonthlyOutstanding orgRepairMonthlyOutstanding = orgRepairMonthlyOutstandingRepository
////						.findByOrgIdAndMonth(orderDetailsDTO.getOrgId(), month);
////
////				if (orgRepairMonthlyOutstanding != null) {
////
////						orgRepairMonthlyOutstanding.setPhnCount(phcount);
////						orgRepairMonthlyOutstandingRepository.save(orgRepairMonthlyOutstanding);
////					}
//
//				String year = Integer.toString(calendar.get(Calendar.YEAR));
//				YearlyRepairOrder yearlyOrder = yearlyRepairOrderRepository.findByYear(year);
//				if (yearlyOrder != null) {
//					yearlyOrder.setOrderCount(yearlyOrder.getOrderCount() + 1);
//					yearlyRepairOrderRepository.save(yearlyOrder);
//				} else {
//					YearlyRepairOrder yearlyOrder1 = new YearlyRepairOrder();
//					yearlyOrder1.setOrderCount(1);
//					yearlyOrder1.setYear(year);
//					yearlyOrder1.setCreateAt(new Date());
//					yearlyRepairOrderRepository.save(yearlyOrder1);
//				}
//
//				TodayRepairOrder ordrerToday = todayRepairOrderRepository
//						.findByCreateAt(UtilService.removeTime(new Date()));
//				if (ordrerToday != null) {
//					ordrerToday.setOrderCount(ordrerToday.getOrderCount() + 1);
//					todayRepairOrderRepository.save(ordrerToday);
//				} else {
//					TodayRepairOrder ordrerToday1 = new TodayRepairOrder();
//					ordrerToday1.setOrderCount(1);
//					ordrerToday1.setCreateAt(UtilService.removeTime(new Date()));
//					todayRepairOrderRepository.save(ordrerToday1);
//				}
				
				
				

				// OrderTaskTypeLinkDTO orderTaskTypeLinkDTO=new OrderTaskTypeLinkDTO();
				/*
				 * List<OrderTaskTypeLinkDTO> list = fieldDetailsDTO.getTaskList(); if (null !=
				 * list && !list.isEmpty()) { for (OrderTaskTypeLinkDTO taskTypeLinkDTO : list)
				 * { OrderDetailsTaskTypeLink orderDetailsTaskTypeLink=new
				 * OrderDetailsTaskTypeLink();
				 * orderDetailsTaskTypeLink.setTaskTypeId(taskTypeLinkDTO.getTaskTypeId());
				 * orderDetailsTaskTypeLink.setOrderPhoneId(fieldDetailsDTO.getOrderPhoneId());
				 * orderDetailsTaskTypeLinkRepository.save(orderDetailsTaskTypeLink); } }
				 */
			}

		}
		return count;
	}

}
