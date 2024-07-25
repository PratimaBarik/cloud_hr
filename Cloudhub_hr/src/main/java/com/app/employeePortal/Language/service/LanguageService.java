package com.app.employeePortal.Language.service;

import java.util.List;

import com.app.employeePortal.Language.mapper.LanguageDTO;

public interface LanguageService {

	List<LanguageDTO> all();

	List<LanguageDTO> selected();

	LanguageDTO updateMandatory(LanguageDTO language);

	LanguageDTO updateBaseLanguage(LanguageDTO language);

}
