package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.ItemTask;

@Repository
public interface ItemTaskRepository extends JpaRepository<ItemTask, String> {

	public List<ItemTask> findByOrgIdAndLiveInd(String orgId, boolean b);
	
	public ItemTask findByItemTaskId(String itemTaskId);

	public List<ItemTask> findByNameAndOrgIdAndLiveInd(String name, String orgId, boolean b);

	public List<ItemTask> findByNameContainingAndLiveIndAndOrgId(String name, boolean b, String orgId);

}
