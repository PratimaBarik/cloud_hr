package com.app.employeePortal.category.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.category.entity.BaseForm;
import com.app.employeePortal.category.mapper.BaseFormMapper;
import com.app.employeePortal.category.repository.BaseFormRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class BaseFormServiceImpl implements BaseFormService {

    @Autowired
    BaseFormRepository baseFormRepository;
    @Autowired
    EmployeeService employeeService;
    
    
	@Override
	public BaseFormMapper updateBaseForm(BaseFormMapper mapper) {
		BaseForm baseForm = baseFormRepository.findByOrgIdAndFormTypeAndBaseFormTypeAndLiveInd
				(mapper.getOrgId(), mapper.getFormType(), mapper.getBaseFormType(), true);
		BaseFormMapper baseFormMapper = new BaseFormMapper();

      if (null != baseForm) {
    	  
    	  baseForm.setBaseFormType(mapper.getBaseFormType());
    	  baseForm.setFormType(mapper.getFormType());
    	  baseForm.setOrgId(mapper.getOrgId());
    	  baseForm.setUpdatedBy(mapper.getUserId());
    	  baseForm.setUpdationDate(new Date());
    	  baseForm.setUserId(mapper.getUserId());
    	 
    	  baseForm.setAddressInd(mapper.isAddressInd());
    	  baseForm.setAssignedToInd(mapper.isAssignedToInd());
    	  baseForm.setBusinessRegInd(mapper.isBusinessRegInd());
    	  baseForm.setDailCodeInd(mapper.isDailCodeInd());
    	  baseForm.setNameInd(mapper.isNameInd());
    	  baseForm.setNoteInd(mapper.isNoteInd());
    	  baseForm.setPhoneNoInd(mapper.isPhoneNoInd());
    	  baseForm.setPotentialCurrencyInd(mapper.isPotentialCurrencyInd());
    	  baseForm.setPotentialInd(mapper.isPotentialInd());
    	  baseForm.setSectorInd(mapper.isSectorInd());
    	  baseForm.setSourceInd(mapper.isSourceInd());
    	  baseForm.setTypeInd(mapper.isTypeInd());
    	  baseForm.setVatNoInd(mapper.isVatNoInd());
    	  baseForm.setFirstNameInd(mapper.isFirstNameInd());
    	  baseForm.setMiddleNameInd(mapper.isMiddleNameInd());
    	  baseForm.setLastNameInd(mapper.isLastNameInd());
    	  baseForm.setImageUploadInd(mapper.isImageUploadInd());
    	  baseForm.setUrlInd(mapper.isUrlInd());
    	  baseForm.setLobInd(mapper.isLobInd());
    	  baseForm.setShipByInd(mapper.isShipByInd());
    	  baseForm.setApiInd(mapper.isApiInd());
    	  baseForm.setApproveInd(mapper.isApproveInd());
    	  
    	  
    	  baseFormMapper = getBaseFormByOrgIdAndFormTypeAndBaseFormType(baseFormRepository.save(baseForm).getOrgId(),
    			  baseForm.getFormType(), baseForm.getBaseFormType());
      }else {
    	  BaseForm baseForm1 = new BaseForm();
    	  baseForm1.setCreationDate(new Date());
    	  baseForm1.setLiveInd(true);
    	  baseForm1.setBaseFormType(mapper.getBaseFormType());
    	  baseForm1.setFormType(mapper.getFormType());
    	  baseForm1.setOrgId(mapper.getOrgId());
    	  baseForm1.setUpdatedBy(mapper.getUserId());
    	  baseForm1.setUpdationDate(new Date());
    	  baseForm1.setUserId(mapper.getUserId());
    	 
    	  baseForm1.setAddressInd(mapper.isAddressInd());
    	  baseForm1.setAssignedToInd(mapper.isAssignedToInd());
    	  baseForm1.setBusinessRegInd(mapper.isBusinessRegInd());
    	  baseForm1.setDailCodeInd(mapper.isDailCodeInd());
    	  baseForm1.setNameInd(mapper.isNameInd());
    	  baseForm1.setNoteInd(mapper.isNoteInd());
    	  baseForm1.setPhoneNoInd(mapper.isPhoneNoInd());
    	  baseForm1.setPotentialCurrencyInd(mapper.isPotentialCurrencyInd());
    	  baseForm1.setPotentialInd(mapper.isPotentialInd());
    	  baseForm1.setSectorInd(mapper.isSectorInd());
    	  baseForm1.setSourceInd(mapper.isSourceInd());
    	  baseForm1.setTypeInd(mapper.isTypeInd());
    	  baseForm1.setVatNoInd(mapper.isVatNoInd());
    	  baseForm1.setFirstNameInd(mapper.isFirstNameInd());
    	  baseForm1.setMiddleNameInd(mapper.isMiddleNameInd());
    	  baseForm1.setLastNameInd(mapper.isLastNameInd());
    	  baseForm1.setImageUploadInd(mapper.isImageUploadInd());
    	  baseForm1.setUrlInd(mapper.isUrlInd());
    	  baseForm1.setLobInd(mapper.isLobInd());
    	  baseForm1.setShipByInd(mapper.isShipByInd());
    	  baseForm1.setApiInd(mapper.isApiInd());
    	  baseForm1.setApproveInd(mapper.isApproveInd());
    	  
    	  baseFormMapper = getBaseFormByOrgIdAndFormTypeAndBaseFormType(baseFormRepository.save(baseForm1).getOrgId(),
    			  mapper.getFormType(), mapper.getBaseFormType());
      }

      return baseFormMapper;
	}

	@Override
	public BaseFormMapper getBaseFormByOrgIdAndFormTypeAndBaseFormType(String orgId, String formType,
			String baseFormType) {
		BaseFormMapper baseFormMapper = new BaseFormMapper();
		
		BaseForm baseForm = baseFormRepository.findByOrgIdAndFormTypeAndBaseFormTypeAndLiveInd
				(orgId, formType, baseFormType, true);
		 if (null != baseForm) {
			 baseFormMapper.setCreationDate(Utility.getISOFromDate(baseForm.getCreationDate()));
			 baseFormMapper.setLiveInd(baseForm.isLiveInd());
			 baseFormMapper.setBaseFormType(baseForm.getBaseFormType());
			 baseFormMapper.setFormType(baseForm.getFormType());
			 baseFormMapper.setOrgId(baseForm.getOrgId());
			 baseFormMapper.setUpdatedBy(employeeService.getEmployeeFullName(baseForm.getUpdatedBy()));
			 baseFormMapper.setUpdationDate(Utility.getISOFromDate(baseForm.getUpdationDate()));
			 baseFormMapper.setUserId(baseForm.getUserId());
	    	 
			 baseFormMapper.setAddressInd(baseForm.isAddressInd());
			 baseFormMapper.setAssignedToInd(baseForm.isAssignedToInd());
			 baseFormMapper.setBusinessRegInd(baseForm.isBusinessRegInd());
			 baseFormMapper.setDailCodeInd(baseForm.isDailCodeInd());
			 baseFormMapper.setNameInd(baseForm.isNameInd());
			 baseFormMapper.setNoteInd(baseForm.isNoteInd());
			 baseFormMapper.setPhoneNoInd(baseForm.isPhoneNoInd());
			 baseFormMapper.setPotentialCurrencyInd(baseForm.isPotentialCurrencyInd());
			 baseFormMapper.setPotentialInd(baseForm.isPotentialInd());
			 baseFormMapper.setSectorInd(baseForm.isSectorInd());
			 baseFormMapper.setSourceInd(baseForm.isSourceInd());
			 baseFormMapper.setTypeInd(baseForm.isTypeInd());
			 baseFormMapper.setVatNoInd(baseForm.isVatNoInd());
			 baseFormMapper.setFirstNameInd(baseForm.isFirstNameInd());
			 baseFormMapper.setMiddleNameInd(baseForm.isMiddleNameInd());
			 baseFormMapper.setLastNameInd(baseForm.isLastNameInd());
			 baseFormMapper.setImageUploadInd(baseForm.isImageUploadInd());
			 baseFormMapper.setUrlInd(baseForm.isUrlInd());
			 baseFormMapper.setLobInd(baseForm.isLobInd());
			 baseFormMapper.setShipByInd(baseForm.isShipByInd());
			 baseFormMapper.setApiInd(baseForm.isApiInd());
			 baseFormMapper.setApproveInd(baseForm.isApproveInd());
	    	  
		 }
		
		return baseFormMapper;
	}
   
}