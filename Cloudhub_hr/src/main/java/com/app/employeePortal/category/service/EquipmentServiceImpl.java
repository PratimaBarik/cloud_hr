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

import com.app.employeePortal.category.entity.Equipment;
import com.app.employeePortal.category.mapper.EquipmentMapper;
import com.app.employeePortal.category.repository.EquipmentRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    EquipmentRepository equipmentRepository;
    @Autowired
    EmployeeService employeeService;
    private String[] headings = {"Name"};

    @Override
    public EquipmentMapper saveEquipment(EquipmentMapper mapper) {
        String equipmentId = null;
        if (mapper != null) {
            Equipment equipment = new Equipment();
            equipment.setCreationDate(new Date());
            equipment.setLiveInd(true);
            equipment.setName(mapper.getName());
            equipment.setOrgId(mapper.getOrgId());
            equipment.setUpdatedBy(mapper.getUserId());
            equipment.setUpdationDate(new Date());
            equipment.setUserId(mapper.getUserId());
            equipment.setQuantity(mapper.getQuantity());
            equipment.setDescription(mapper.getDescription());

            equipmentId = equipmentRepository.save(equipment).getEquipmentId();

        }
        EquipmentMapper resultMapper = getEquipmentByEquipmentId(equipmentId);
        return resultMapper;
    }

    public EquipmentMapper getEquipmentByEquipmentId(String equipmentId) {

        Equipment equipment = equipmentRepository.findByEquipmentIdAndLiveInd(equipmentId, true);
        EquipmentMapper equipmentMapper = new EquipmentMapper();

        if (null != equipment) {

            equipmentMapper.setCreationDate(Utility.getISOFromDate(equipment.getCreationDate()));
            equipmentMapper.setLiveInd(true);
            equipmentMapper.setName(equipment.getName());
            equipmentMapper.setOrgId(equipment.getOrgId());
            equipmentMapper.setUpdatedBy(employeeService.getEmployeeFullName(equipment.getUserId()));
            equipmentMapper.setUpdationDate(Utility.getISOFromDate(equipment.getUpdationDate()));
            equipmentMapper.setUserId(equipment.getUserId());
            equipmentMapper.setEquipmentId(equipment.getEquipmentId());
            equipmentMapper.setDescription(equipment.getDescription());
            equipmentMapper.setQuantity(equipment.getQuantity());

        }

        return equipmentMapper;
    }

    @Override
    public List<EquipmentMapper> getEquipmentByOrgId(String orgId) {

        List<EquipmentMapper> resultMapper = new ArrayList<>();
        List<Equipment> list = equipmentRepository.findByOrgIdAndLiveInd(orgId, true);
        if (null != list) {
            resultMapper = list.stream().map(li -> getEquipmentByEquipmentId(li.getEquipmentId()))
                    .collect(Collectors.toList());
        }
        Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

        List<Equipment> list1 = equipmentRepository.findAll();
        if (null != list1 && !list1.isEmpty()) {
            Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

            resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
            resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
        }
        return resultMapper;
    }

    @Override
    public EquipmentMapper updateEquipment(String equipmentId, EquipmentMapper mapper) {

        Equipment equipment = equipmentRepository.findByEquipmentIdAndLiveInd(equipmentId, true);
        if (null != equipment) {

            equipment.setLiveInd(true);
            equipment.setName(mapper.getName());
            equipment.setOrgId(mapper.getOrgId());
            equipment.setUpdatedBy(mapper.getUserId());
            equipment.setUpdationDate(new Date());
            equipment.setUserId(mapper.getUserId());
            equipment.setDescription(mapper.getDescription());
            equipment.setQuantity(mapper.getQuantity());
            equipmentRepository.save(equipment);
        }
        EquipmentMapper resultMapper = getEquipmentByEquipmentId(equipmentId);
        return resultMapper;
    }

    @Override
    public void deleteEquipment(String equipmentId, String userId) {

        if (null != equipmentId) {
            Equipment equipment = equipmentRepository.findByEquipmentIdAndLiveInd(equipmentId, true);

            equipment.setUpdationDate(new Date());
            equipment.setUpdatedBy(userId);
            equipment.setLiveInd(false);
            equipmentRepository.save(equipment);
        }
    }

    @Override
    public List<EquipmentMapper> getEquipmentByName(String name, String orgId) {
        List<Equipment> list = equipmentRepository.findByNameContainingAndLiveIndAndOrgId(name, true, orgId);
        List<EquipmentMapper> resultList = new ArrayList<EquipmentMapper>();
        if (null != list && !list.isEmpty()) {
            list.stream().map(Quality -> {
                EquipmentMapper mapper = getEquipmentByEquipmentId(Quality.getEquipmentId());
                if (null != mapper) {
                    resultList.add(mapper);
                }
                return resultList;
            }).collect(Collectors.toList());
        }
        Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

        List<Equipment> list1 = equipmentRepository.findAll();
        if (null != list1 && !list1.isEmpty()) {
            Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

            resultList.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
            resultList.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
        }
        return resultList;
    }

    @Override
    public HashMap getEquipmentCountByOrgId(String orgId) {
        HashMap map = new HashMap();
        List<Equipment> list = equipmentRepository.findByOrgIdAndLiveInd(orgId, true);
        map.put("EquipmentCount", list.size());
        return map;
    }


    //	@Override
    public ByteArrayInputStream exportQualityListToExcel(List<EquipmentMapper> list) {
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
            for (EquipmentMapper mapper : list) {
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
    public boolean checkNameInEquipment(String name, String orgId) {
        List<Equipment> equipment = equipmentRepository.findByNameAndLiveIndAndOrgId(name, true,
                orgId);
        if (equipment.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkNameInEquipmentInUpdate(String name, String orgId, String equipmentId) {
        List<Equipment> equipment = equipmentRepository.findByNameContainingAndEquipmentIdNotAndLiveIndAndOrgId(name,equipmentId, true,
                orgId);
        if (equipment.size() > 0) {
            return true;
        }
        return false;
    }
}