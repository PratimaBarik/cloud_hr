package com.app.employeePortal.category.service;

import com.app.employeePortal.category.entity.ShipperCategory;
import com.app.employeePortal.category.mapper.ShipperCategoryMapper;
import com.app.employeePortal.category.repository.ShipperCategoryRepo;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional

public class ShipperCategoryServiceImpl implements ShipperCategoryService {

    @Autowired
    ShipperCategoryRepo shipperCategoryRepo;
    @Autowired
    EmployeeService employeeService;
    private String[] headings = {"Name"};

    @Override
    public ShipperCategoryMapper saveShipperCategory(ShipperCategoryMapper mapper) {
        String shipperCategoryId = null;
        if (mapper != null) {
            ShipperCategory shipperCategory = new ShipperCategory();
            shipperCategory.setCreationDate(new Date());
            shipperCategory.setLiveInd(true);
            shipperCategory.setName(mapper.getShipperCatName());
            shipperCategory.setOrgId(mapper.getOrgId());
            shipperCategory.setUpdatedBy(mapper.getUserId());
            shipperCategory.setUpdationDate(new Date());
            shipperCategory.setUserId(mapper.getUserId());
                       shipperCategoryId = shipperCategoryRepo.save(shipperCategory).getShipperCategoryId();

        }
        ShipperCategoryMapper resultMapper = getShipperCategoryByShipperCategoryId(shipperCategoryId);
        return resultMapper;
    }

    public ShipperCategoryMapper getShipperCategoryByShipperCategoryId(String shipperCategoryId) {

        ShipperCategory shipperCategory = shipperCategoryRepo.findByShipperCategoryIdAndLiveInd(shipperCategoryId, true);
        ShipperCategoryMapper shipperCategoryMapper = new ShipperCategoryMapper();

        if (null != shipperCategory) {

            shipperCategoryMapper.setCreationDate(Utility.getISOFromDate(shipperCategory.getCreationDate()));
            shipperCategoryMapper.setLiveInd(true);
            shipperCategoryMapper.setShipperCatName(shipperCategory.getName());
            shipperCategoryMapper.setOrgId(shipperCategory.getOrgId());
            shipperCategoryMapper.setUpdatedBy(employeeService.getEmployeeFullName(shipperCategory.getUserId()));
            shipperCategoryMapper.setUpdationDate(Utility.getISOFromDate(shipperCategory.getUpdationDate()));
            shipperCategoryMapper.setUserId(shipperCategory.getUserId());
            shipperCategoryMapper.setShipperCategoryId(shipperCategory.getShipperCategoryId());
        }

        return shipperCategoryMapper;
    }

    @Override
    public List<ShipperCategoryMapper> getShipperCategoryByOrgId(String orgId) {

        List<ShipperCategoryMapper> resultMapper = new ArrayList<>();
        List<ShipperCategory> list = shipperCategoryRepo.findByOrgIdAndLiveInd(orgId, true);
        if (null != list) {
            resultMapper = list.stream().map(li -> getShipperCategoryByShipperCategoryId(li.getShipperCategoryId()))
                    .collect(Collectors.toList());
        }
        Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

        List<ShipperCategory> list1 = shipperCategoryRepo.findAll();
        if (null != list1 && !list1.isEmpty()) {
            Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

            resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
            resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
        }
        return resultMapper;
    }

    @Override
    public ShipperCategoryMapper updateShipperCategory(String shipperCategoryId, ShipperCategoryMapper mapper) {

        ShipperCategory shipperCategory = shipperCategoryRepo.findByShipperCategoryIdAndLiveInd(shipperCategoryId, true);
        if (null != shipperCategory) {

            shipperCategory.setLiveInd(true);
            shipperCategory.setName(mapper.getShipperCatName());
            shipperCategory.setOrgId(mapper.getOrgId());
            shipperCategory.setUpdatedBy(mapper.getUserId());
            shipperCategory.setUpdationDate(new Date());
            shipperCategory.setUserId(mapper.getUserId());
            shipperCategoryRepo.save(shipperCategory);
        }
        ShipperCategoryMapper resultMapper = getShipperCategoryByShipperCategoryId(shipperCategoryId);
        return resultMapper;
    }

    @Override
    public void deleteShipperCategory(String shipperCategoryId, String userId) {

        if (null != shipperCategoryId) {
            ShipperCategory shipperCategory = shipperCategoryRepo.findByShipperCategoryIdAndLiveInd(shipperCategoryId, true);

            shipperCategory.setUpdationDate(new Date());
            shipperCategory.setUpdatedBy(userId);
            shipperCategory.setLiveInd(false);
            shipperCategoryRepo.save(shipperCategory);
        }
    }

    @Override
    public List<ShipperCategoryMapper> getShipperCategoryByName(String name, String orgId) {
        List<ShipperCategory> list = shipperCategoryRepo.findByNameContainingAndLiveIndAndOrgId(name, true, orgId);
        List<ShipperCategoryMapper> resultList = new ArrayList<ShipperCategoryMapper>();
        if (null != list && !list.isEmpty()) {
            list.stream().map(Quality -> {
                ShipperCategoryMapper mapper = getShipperCategoryByShipperCategoryId(Quality.getShipperCategoryId());
                if (null != mapper) {
                    resultList.add(mapper);
                }
                return resultList;
            }).collect(Collectors.toList());
        }
        Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

        List<ShipperCategory> list1 = shipperCategoryRepo.findAll();
        if (null != list1 && !list1.isEmpty()) {
            Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

            resultList.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
            resultList.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
        }
        return resultList;
    }

    @Override
    public HashMap getShipperCategoryCountByOrgId(String orgId) {
        HashMap map = new HashMap();
        List<ShipperCategory> list = shipperCategoryRepo.findByOrgIdAndLiveInd(orgId, true);
        map.put("ShipperCategoryCount", list.size());
        return map;
    }


    //	@Override
    public ByteArrayInputStream exportQualityListToExcel(List<ShipperCategoryMapper> list) {
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
            for (ShipperCategoryMapper mapper : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(mapper.getShipperCatName());
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
    public boolean checkNameInShipperCategory(String name, String orgId) {
        List<ShipperCategory> shipperCategory = shipperCategoryRepo.findByNameAndLiveIndAndOrgId(name, true,
                orgId);
        if (shipperCategory.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkNameInShipperCategoryInUpdate(String name, String orgId, String shipperCategoryId) {
        List<ShipperCategory> shipperCategory = shipperCategoryRepo.findByNameContainingAndShipperCategoryIdNotAndLiveIndAndOrgId(name,shipperCategoryId, true,
                orgId);
        if (shipperCategory.size() > 0) {
            return true;
        }
        return false;
    }
}