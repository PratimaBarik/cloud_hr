package com.app.employeePortal.sector.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.sector.mapper.SectorMapper;

public interface SectorService {

	List<SectorMapper> saveToSectorDetails(SectorMapper sectorMapper);

	SectorMapper getsectorDetailsById(String sectorId);

	// SectorMapper updateSector(SectorMapper sectorMapper);

	List<SectorMapper> getSectorTypeByOrgId(String orgIdFromToken);

	List<SectorMapper> getSectorDetailsByuserId(String userId);

	List<SectorMapper> updateSector(String sectorId, SectorMapper sectorMapper);

	boolean ipAddressExists(String url);

	List<SectorMapper> getSectorTypeByUrl(String url);

	public void deleteSectorDetailsById(String sectorId);

	HashMap getSectorCountByOrgId(String orgId);

	ByteArrayInputStream exportSectorListToExcel(List<SectorMapper> list);

	boolean checkSectorNameInSectorDetailsByOrgLevel(String sectorName, String orgId);

	List<SectorMapper> getSectorByNameByOrgLevel(String name, String orgId);

}
