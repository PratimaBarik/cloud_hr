package com.app.employeePortal.category.service;

import com.app.employeePortal.category.mapper.BaseFormMapper;

public interface BaseFormService {

	BaseFormMapper updateBaseForm(BaseFormMapper mapper);

	BaseFormMapper getBaseFormByOrgIdAndFormTypeAndBaseFormType(String orgId, String formType, String baseFormType);



}
