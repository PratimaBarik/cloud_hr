package com.app.employeePortal.category.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.category.mapper.*;

public interface CategoryService {

	RoleMapper CreateRoleType(RoleMapper roleMapper);

	RoleMapper updateRoleType(String roleTypeId, RoleMapper roleMapper);

	List<RoleMapper> getRoleListByOrgId(String orgId);

	RoleMapper CreateLibraryType(RoleMapper roleMapper);

	RoleMapper updateLibraryType(String libraryTypeId, RoleMapper roleMapper);

	List<RoleMapper> getAllLibraryType(String orgId);

	RoleMapper getAllLibrarySkills();

	boolean ipAddressExists(String url);

	List<RoleMapper> getAllRoleTypeToWebsite(String url);

	List<RoleMapper> getRoleDetailsByNameByOrgLevel(String name, String orgId);

	List<RoleMapper> getLibraryDetailsByNameByOrgLevel(String name, String orgId);

	public boolean checkRoleNameInRoleTypeByorgLevel(String roleType, String departmentId, String orgId);

	boolean checkLibraryNameInLibraryTypeInOrgLevel(String libraryType, String orgId);

	public void deleteRoleTypeDetailsById(String roleTypeId);

	UnitMapper saveUnit(UnitMapper unitMapper);

	List<UnitMapper> getUnitListByOrgId(String orgId);

	UnitMapper updateUnit(String unitId, UnitMapper unitMapper);

	void deleteUnitById(String unitId);

	UnitMapper getUnitDetailsById(String unitId);

	String saveTaskChecklist(TaskChecklistMapper taskChecklistMapper);

	List<TaskChecklistMapper> getAllTaskChecklistByOrgId(String orgId);

	TaskChecklistMapper updateTaskChecklist(String taskChecklistId, TaskChecklistMapper taskChecklistMapper);

	TaskChecklistMapper getTaskChecklistDetailsById(String taskChecklistId);

	void deleteTaskChecklist(String taskChecklistId, String userId);

	String saveTaskChecklistStageLink(TaskChecklistStageLinkMapper taskChecklistStageLinkMapper);

	List<TaskChecklistStageLinkMapper> getAllTaskChecklistStageLinkByOrgId(String orgId);

	TaskChecklistStageLinkMapper updateTaskChecklistStageLink(String taskChecklistStagelinkId,
			TaskChecklistStageLinkMapper taskChecklistStageLinkMapper);

	TaskChecklistStageLinkMapper getTaskChecklistStageLinkDetailsById(String taskChecklistStagelinkId);

	void deleteTaskChecklistStageLink(String taskChecklistStagelinkId, String userId);

	List<TaskChecklistStageLinkMapper> getAllTaskChecklistStageLinkByTaskChecklistId(String taskChecklistId);

	List<UnitMapper> getUnitDetailsByName(String name);

	List<RoleMapper> getAllRoleTypeByDepartmentId(String departmentId);

	List<TaskChecklistMapper> getAllTaskChecklistByTaskType(String taskTypeId);

    boolean checkUnitNameExist(String unitName);

	SkillLevelLinkMapper saveSkillLevel(SkillLevelLinkMapper skillLevelLinkMapper);

	List<SkillLevelLinkMapper> getSkillLevel();

	boolean TicketsTypeNameInTicketsType(String TicketsType);

	TicketTypeResponseMapper CreateTicketType(TicketTypeRequestMapper ticketRequest);

	TicketTypeResponseMapper getByTicketTypeId(String ticketsTypeId);

	List<TicketTypeResponseMapper> getAllTicketsTypelist(String orgId);

	TicketTypeResponseMapper updateTicketsType(TicketTypeRequestMapper ticketRequestMapper);

	void deleteTicketsType(String ticketsTypeId,String userId);

	List<TicketTypeResponseMapper> getTicketsTypeByName(String name);

	ModuleResponseMapper updateModule(ModuleRequestMapper moduleRequestMapper);

	ModuleResponseMapper getModuleByOrgId(String orgId);

	List<SkillLevelLinkMapper> getSkillLevelByCountry(String countryId,String orgId);

	String setAdminSecreteKey(AdminSecreteKeyMapper adminSecreteKeyMapper);

	AdminSecreteKeyMapper getAdminSecreteKeyByOrgId(String userId);

    MinimumActivityRespMapper getMinimumActivity(String orgId);

	MinimumActivityRespMapper getMinimumActivityById(String minimumActivityId);

	MinimumActivityRespMapper createMinimumActivity(MinimumActivityReqMapper requestMapper);

	HashMap getRoleTypeCountByOrgId(String orgId);

	HashMap getLibreryTypeCountByOrgId(String orgId);

	ByteArrayInputStream exportRoleTypeListToExcel(List<RoleMapper> list);

	ByteArrayInputStream exportLibraryTypeListToExcel(List<RoleMapper> list);

	boolean checkNameInCategory(String name, String orgId);

	CategoryMapper saveCategory(CategoryMapper categoryMapper);

	List<CategoryMapper> getCategoryMapperByOrgId(String orgId);

	CategoryMapper updateCategory(String categoryId, CategoryMapper categoryMapper);

	List<CategoryMapper> getCategoryByNameByOrgLevel(String name, String orgId);

	void deleteCategory(String categoryId, String userId);

	HashMap getCategoryCountByOrgId(String orgId);


    RatingTypeMapper saveRatingType(RatingTypeMapper mapper);

	List<RatingTypeMapper> getRatingTypes(String orgId);

	InvoiceCategoryMapper updateInvoiceCategory(InvoiceCategoryMapper invoiceCategoryMapper);

	InvoiceCategoryMapper getInvoiceCategoryByOrgId(String orgId);
}
