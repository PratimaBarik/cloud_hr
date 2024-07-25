package com.app.employeePortal.Language.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.employeePortal.Language.Entity.Languages;
import com.app.employeePortal.Language.mapper.LanguageDTO;
import com.app.employeePortal.Language.repository.LanguageRepository;

@Service
@Transactional
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	LanguageRepository languageRepo;

	@Override
	public List<LanguageDTO> all() {
		return languageRepo.findAll().stream().map(LanguageDTO::from)
				.collect(Collectors.toList());
	}

	@Override
	public List<LanguageDTO> selected() {
		return languageRepo.findByMandatoryInd(true).stream().map(LanguageDTO::from)
				.collect(Collectors.toList());
	}

	@Override
	public LanguageDTO updateMandatory(LanguageDTO languageDTO) {
		LanguageDTO dto = new LanguageDTO();
		if (languageDTO.getLanguageId() != null && !languageDTO.getLanguageId().isEmpty()) {
			Languages language = languageRepo.findById(Long.valueOf(languageDTO.getLanguageId()))
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"language not found with id " + languageDTO.getLanguageId()));
			if (languageDTO.isMandatoryInd()) {
				language.setMandatoryInd(true);
				Languages language1 = languageRepo.save(language);
				dto = LanguageDTO.from(language1);
			} else {
				language.setMandatoryInd(false);
				Languages language2 = languageRepo.save(language);
				dto = LanguageDTO.from(language2);
			}
		}
		return dto;
	}

	@Override
	public LanguageDTO updateBaseLanguage(LanguageDTO languageDTO) {
		if (languageDTO.getLanguageId() != null && !languageDTO.getLanguageId().isEmpty()) {
			Languages languages = languageRepo.findById(Long.valueOf(languageDTO.getLanguageId()))
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"language not found with id " + languageDTO.getLanguageId()));
			Languages language = languageRepo.findByBaseInd(true);
			if (language != null) {
				language.setBaseInd(false);
				languageRepo.save(language);
			}
			languages.setBaseInd(languageDTO.isBaseInd());
			languages.setMandatoryInd(true);
			Languages languages1 = languageRepo.save(languages);
			return LanguageDTO.from(languages1);
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND,
				"language not found with Id " + languageDTO.getLanguageId());
	}

}
