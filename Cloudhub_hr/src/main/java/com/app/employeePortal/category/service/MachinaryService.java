package com.app.employeePortal.category.service;

import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.MachinaryLocationCellMapper;
import com.app.employeePortal.category.mapper.MachinaryLocationMapper;
import com.app.employeePortal.category.mapper.MachinaryMapper;

public interface MachinaryService {

	public MachinaryMapper saveMachinary(MachinaryMapper mapper);

	public boolean checkNameInMachinary(String name, String orgIdFromToken);

	public List<MachinaryMapper> getMachinaryByOrgId(String orgIdFromToken);

	public MachinaryMapper updateMachinary(String machinaryId, MachinaryMapper mapper);

	public void deleteMachinary(String MachinaryId, String userIdFromToken);

	public List<MachinaryMapper> getMachinaryByName(String name, String orgIdFromToken);

	public HashMap getMachinaryCountByOrgId(String orgIdFromToken);

	public boolean checkNameInMachinaryInUpdate(String name, String orgIdFromToken,String machinaryId);

	public MachinaryLocationMapper saveMachinaryLocation(MachinaryLocationMapper mapper);

	public List<MachinaryLocationMapper> getMachinaryLocationByLocationId(String locationId);

	public MachinaryLocationMapper saveMachinaryLocationCell(MachinaryLocationCellMapper mapper);
}
