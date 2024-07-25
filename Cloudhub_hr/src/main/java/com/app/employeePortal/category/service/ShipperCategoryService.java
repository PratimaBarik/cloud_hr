package com.app.employeePortal.category.service;

import com.app.employeePortal.category.mapper.ShipperCategoryMapper;

import java.util.HashMap;
import java.util.List;

public interface ShipperCategoryService {

	ShipperCategoryMapper saveShipperCategory(ShipperCategoryMapper mapper);

	boolean checkNameInShipperCategory(String name, String orgIdFromToken);

	List<ShipperCategoryMapper> getShipperCategoryByOrgId(String orgIdFromToken);

	ShipperCategoryMapper updateShipperCategory(String equipmentId, ShipperCategoryMapper mapper);

	void deleteShipperCategory(String equipmentId, String userIdFromToken);

	List<ShipperCategoryMapper> getShipperCategoryByName(String name, String orgIdFromToken);

	HashMap getShipperCategoryCountByOrgId(String orgIdFromToken);

	boolean checkNameInShipperCategoryInUpdate(String name, String orgIdFromToken,String equipmentId);
	
}
