package com.app.employeePortal.category.service;

import com.app.employeePortal.category.mapper.SecreteKeyMapper;
import com.app.employeePortal.category.mapper.ServiceLineRespMapper;

public interface SecreteKeyService {

	public SecreteKeyMapper createSecreteKey(SecreteKeyMapper mapper);

	public SecreteKeyMapper updateSecreteKey(String secretekeyId, SecreteKeyMapper requestMapper);
}
