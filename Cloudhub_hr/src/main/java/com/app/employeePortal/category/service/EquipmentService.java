package com.app.employeePortal.category.service;

import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.EquipmentMapper;

public interface EquipmentService {

	EquipmentMapper saveEquipment(EquipmentMapper mapper);

	boolean checkNameInEquipment(String name, String orgIdFromToken);

	List<EquipmentMapper> getEquipmentByOrgId(String orgIdFromToken);

	EquipmentMapper updateEquipment(String equipmentId, EquipmentMapper mapper);

	void deleteEquipment(String equipmentId, String userIdFromToken);

	List<EquipmentMapper> getEquipmentByName(String name, String orgIdFromToken);

	HashMap getEquipmentCountByOrgId(String orgIdFromToken);

	boolean checkNameInEquipmentInUpdate(String name, String orgIdFromToken,String equipmentId);
}
