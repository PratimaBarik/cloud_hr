package com.app.employeePortal.category.service;

import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.QualityMapper;

public interface QualityService {

	public QualityMapper saveQuality(QualityMapper mapper);

	public List<QualityMapper> getQualityByOrgId(String orgIdFromToken);

	public QualityMapper updateQuality(String qualityId, QualityMapper mapper);

	public void deleteQuality(String qualityId, String userIdFromToken);

//	public List<QualityMapper> getQualityByName(String qualityName, String orgId);

	public HashMap getQualityCountByOrgId(String orgIdFromToken);

//	public boolean checkQualityNameInQuality(String qualityName, String orgId);

}
