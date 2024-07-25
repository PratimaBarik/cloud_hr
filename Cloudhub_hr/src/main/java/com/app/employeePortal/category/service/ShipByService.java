package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.ShipByMapper;

public interface ShipByService {

	public ShipByMapper saveShipBy(ShipByMapper shipByMapper);

	public ShipByMapper getShipByByShipById(String shipById);

	public List<ShipByMapper> getShipByByOrgId(String orgId);

	public ShipByMapper updateShipBy(String shipById, ShipByMapper shipByMapper);

	public void deleteShipBy(String shipById, String userId);

	public List<ShipByMapper> getShipByByNameByOrgLevel(String name, String orgId);

	public HashMap getShipByCountByOrgId(String orgId);

	public ByteArrayInputStream exportShipByListToExcel(List<ShipByMapper> list);

	public boolean checkNameInShipByByOrgLevel(String name, String orgId);



}
