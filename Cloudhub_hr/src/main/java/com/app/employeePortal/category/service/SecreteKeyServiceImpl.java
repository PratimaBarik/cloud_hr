package com.app.employeePortal.category.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.category.entity.Club;
import com.app.employeePortal.category.entity.SecreteKey;
import com.app.employeePortal.category.mapper.ClubMapper;
import com.app.employeePortal.category.mapper.SecreteKeyMapper;
import com.app.employeePortal.category.mapper.ServiceLineRespMapper;
import com.app.employeePortal.category.repository.SecreteKeyRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class SecreteKeyServiceImpl implements SecreteKeyService {

	@Autowired
	SecreteKeyRepository secreteKeyRepository;
	
	@Override
	public SecreteKeyMapper createSecreteKey(SecreteKeyMapper mapper) {
		String secreteKeyId = null;
		if (mapper != null) {
			SecreteKey secreteKey = new SecreteKey();
			secreteKey.setCreationDate(new Date());
			secreteKey.setEpiKey(mapper.getEpiKey());
			secreteKey.setSecreteKey(mapper.getSecreteKey());
			secreteKey.setType(mapper.getType());

			secreteKeyId = secreteKeyRepository.save(secreteKey).getSecreteKeyId();

		}
		SecreteKeyMapper resultMapper = getSecreteKeyBySecreteKeyId(secreteKeyId);
		return resultMapper;
	}

	private SecreteKeyMapper getSecreteKeyBySecreteKeyId(String secreteKeyId) {

		SecreteKey secreteKey = secreteKeyRepository.getById(secreteKeyId);
		SecreteKeyMapper secreteKeyMapper = new SecreteKeyMapper();

        if (null != secreteKey) {
        	secreteKeyMapper.setCreationDate(Utility.getISOFromDate(secreteKey.getCreationDate()));
        	secreteKeyMapper.setEpiKey(secreteKey.getEpiKey());
        	secreteKeyMapper.setSecreteKey(secreteKey.getSecreteKey());
        	secreteKeyMapper.setType(secreteKey.getType());        	

        }

        return secreteKeyMapper;
    }

	@Override
	public SecreteKeyMapper updateSecreteKey(String secretekeyId, SecreteKeyMapper mapper) {
		SecreteKey secreteKey = secreteKeyRepository.getById(secretekeyId);
		if (null != secreteKey) {
			secreteKey.setEpiKey(mapper.getEpiKey());
			secreteKey.setSecreteKey(mapper.getSecreteKey());
			secreteKey.setType(mapper.getType());        	

        }
		SecreteKeyMapper resultMapper = getSecreteKeyBySecreteKeyId(secretekeyId);
		return resultMapper;
	}

}