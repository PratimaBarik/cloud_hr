package com.app.employeePortal.source.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.source.mapper.SourceMapper;

public interface SourceService {

	SourceMapper saveSource(SourceMapper sourceMapper);

	List<SourceMapper> getSourceByOrgId(String orgId);

	SourceMapper getSourceBySourceId(String sourceId);

	public SourceMapper updateSource(String sourceId, SourceMapper sourceMapper);

	public void deleteSource(String sourceId, String userId);

	List<SourceMapper> getSourceByNameAndOrgId(String name, String orgId);

	HashMap getSourceCountByOrgId(String orgId);

	ByteArrayInputStream exportSourceListToExcel(List<SourceMapper> list);

	boolean checkSourceByNameByOrgLevel(String name, String orgId);


	

}
