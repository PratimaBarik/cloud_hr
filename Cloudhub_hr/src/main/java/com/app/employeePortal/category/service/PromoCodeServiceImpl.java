package com.app.employeePortal.category.service;

import com.app.employeePortal.category.entity.PromoCodeDetails;
import com.app.employeePortal.category.mapper.PromoCodeRequestMapper;
import com.app.employeePortal.category.repository.PromoCodeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class PromoCodeServiceImpl implements PromoCodeRequestService{
    
    @Autowired
    PromoCodeRepository promoCodeRepository;
    @Autowired
    EmployeeService employeeService;
    
    @Override
    public PromoCodeRequestMapper savePromoCode(PromoCodeRequestMapper promoCodeRequestMapper) {
        PromoCodeDetails promoCodeDetails=new PromoCodeDetails();
        setPropertyOnInput(promoCodeRequestMapper,promoCodeDetails);
        return getPromoCodeDetailsById(promoCodeRepository.save(promoCodeDetails).getPromoCodeId());
    }

    private PromoCodeRequestMapper getPromoCodeDetailsById(String promoCodeId) {
        PromoCodeRequestMapper requestMapper=new PromoCodeRequestMapper();
        PromoCodeDetails details=promoCodeRepository.findById(promoCodeId).orElse(null);
        if(null!=details){
            requestMapper.setPromoCodeId(details.getPromoCodeId());
            requestMapper.setPromoCodeName(details.getPromoCodeName());
            requestMapper.setPromoCode(details.getPromoCode());
            requestMapper.setProductInd(details.isProductInd());
            requestMapper.setMaterialInd(details.isMaterialInd());
            requestMapper.setSupplierInventoryInd(details.isSupplInvtryInd());
            requestMapper.setDiscountValue(details.getDiscountValue());
            requestMapper.setStartDate(Utility.getISOFromDate(details.getStartDate()));
            requestMapper.setEndDate(Utility.getISOFromDate(details.getEndDate()));
            requestMapper.setCreationDate(Utility.getISOFromDate(details.getCreationDate()));
            requestMapper.setCreatedBy(employeeService.getEmployeeFullName(details.getUserId()));
            requestMapper.setUpdationDate(Utility.getISOFromDate(details.getUpdationDate()));
            requestMapper.setUpdatedBy(employeeService.getEmployeeFullName(details.getUpdatedBy()));
            requestMapper.setOrgId(details.getOrgId());
            requestMapper.setUserId(details.getUserId());
        }
        return requestMapper;
    }

    private void setPropertyOnInput(PromoCodeRequestMapper promoCodeRequestMapper, PromoCodeDetails promoCodeDetails) {
        promoCodeDetails.setPromoCodeName(promoCodeRequestMapper.getPromoCodeName());
        promoCodeDetails.setPromoCode(promoCodeRequestMapper.getPromoCode());
        promoCodeDetails.setLiveInd(true);
        promoCodeDetails.setProductInd(promoCodeRequestMapper.isProductInd());
        promoCodeDetails.setMaterialInd(promoCodeRequestMapper.isMaterialInd());
        promoCodeDetails.setSupplInvtryInd(promoCodeRequestMapper.isSupplierInventoryInd());
        promoCodeDetails.setDiscountValue(promoCodeRequestMapper.getDiscountValue());
        try {
            promoCodeDetails.setStartDate(Utility.getDateFromISOString(promoCodeRequestMapper.getStartDate()));
            promoCodeDetails.setEndDate(Utility.getDateFromISOString(promoCodeRequestMapper.getEndDate()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        promoCodeDetails.setOrgId(promoCodeRequestMapper.getOrgId());
        promoCodeDetails.setUserId(promoCodeRequestMapper.getUserId());
        promoCodeDetails.setCreationDate(new Date());
    }

    @Override
    public List<PromoCodeRequestMapper> getAllPromoCode() {
        return promoCodeRepository.findByLiveInd(true).stream().map(p->getPromoCodeDetailsById(p.getPromoCodeId())).collect(Collectors.toList());
    }
}
