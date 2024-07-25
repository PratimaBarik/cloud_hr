package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.ItemTaskMapper;

public interface ItemTaskService {

	public ItemTaskMapper saveItemTask(ItemTaskMapper itemTaskMapper);

	public ItemTaskMapper getItemTaskById(String itemTaskId);

	public List<ItemTaskMapper> getItemTaskMapperByOrgId(String orgId);

	public ItemTaskMapper updateItemTask(String itemTaskId, ItemTaskMapper itemTaskMapper);

	public List<ItemTaskMapper> getItemTaskByNameByOrgLevel(String name, String orgId);

	public void deleteItemTask(String itemTaskId, String userId);

	public HashMap getItemTaskCountByOrgId(String orgId);

	public ByteArrayInputStream exportItemTaskListToExcel(List<ItemTaskMapper> list);

	public boolean checkNameInItemTaskByOrgLevel(String name, String orgId);


}
