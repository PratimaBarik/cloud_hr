
package com.app.employeePortal.employee.service;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.mapper.BankDetailsMapper;
import com.app.employeePortal.employee.mapper.EducationalDetailsMapper;
import com.app.employeePortal.employee.mapper.EmployeeAdminUpdateRequestMapper;
import com.app.employeePortal.employee.mapper.EmployeeAdminUpdateResponseMapper;
import com.app.employeePortal.employee.mapper.EmployeeContractMapper;
import com.app.employeePortal.employee.mapper.EmployeeEmailLinkMapper;
import com.app.employeePortal.employee.mapper.EmployeeIDMapper;
import com.app.employeePortal.employee.mapper.EmployeeMapper;
import com.app.employeePortal.employee.mapper.EmployeePreferedLanguageMapper;
import com.app.employeePortal.employee.mapper.EmployeeRoleLinkMapper;
import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.app.employeePortal.employee.mapper.EmployeeTableMapper;
import com.app.employeePortal.employee.mapper.EmployeeTreeMapper;
import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.app.employeePortal.employee.mapper.EmployeeWorkflowAndStageResponseMapper;
import com.app.employeePortal.employee.mapper.EmployeeWorkflowReqestMapper;
import com.app.employeePortal.employee.mapper.EmploymentHistoryMapper;
import com.app.employeePortal.employee.mapper.KeyskillsMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.mapper.PersonalDetailsMapper;
import com.app.employeePortal.employee.mapper.SalaryDetailsMapper;
import com.app.employeePortal.employee.mapper.TrainingDetailsMapper;
import com.app.employeePortal.employee.mapper.UserEquipmentMapper;
import com.app.employeePortal.employee.mapper.UserKpiLobMapper;
import com.app.employeePortal.employee.mapper.UserKpiRequestForAssignedValueMapper;
import com.app.employeePortal.employee.mapper.UserKpiRequestForCompletedValueMapper;
import com.app.employeePortal.employee.mapper.UserKpiRequestMapper;
import com.app.employeePortal.employee.mapper.UserKpiResponseMapper;
import com.app.employeePortal.employee.mapper.UserSalaryBreakoutMapper;
import com.app.employeePortal.employee.mapper.VisaMapper;
import com.app.employeePortal.registration.mapper.DepartmentMapper;
import com.app.employeePortal.registration.mapper.NewAdminRegisterMapper;

public interface EmployeeService {

	public EmployeeTableMapper saveToEmployeeProcess(EmployeeMapper employeeMapper, String orgId);

	public EmployeeViewMapper getEmployeeDetails(EmployeeDetails employeeDetails);

	public List<EmployeeTableMapper> getEmployeesByOrgId(String organizationId);

	public List<PersonalDetailsMapper> getEmployeePersonalDetails(String employeeId);

	public List<BankDetailsMapper> getEmployeeBankDetails(String employeeId);

	public List<KeyskillsMapper> getEmployeeKeyskillsDetails(String employeeId);

	public List<EducationalDetailsMapper> getEmployeeEducationDetails(String employeeId);

	public List<TrainingDetailsMapper> getEmployeeTrainingDetails(String employeeId);

	public List<EmploymentHistoryMapper> getEmploymentHistoryDetails(String employeeId);

	public String saveEmploymentHistory(EmploymentHistoryMapper employmentHistoryMapper);

	public String saveTrainingDetails(TrainingDetailsMapper trainingDetailsMapper);

	public String saveEducationDetails(EducationalDetailsMapper educationalDetailsMapper);

	public String saveKeyskills(KeyskillsMapper keyskillsMapper);

	public String saveBankDetails(BankDetailsMapper bankDetailsMapper);

	public String savePersonalDetails(PersonalDetailsMapper personalDetailsMapper);

	public String saveNotes(NotesMapper notesMapper);

	public KeyskillsMapper updateKeyskills(KeyskillsMapper keyskillsMapper);

	public EmployeePreferedLanguageMapper updateEmployeePreferedLanguage(EmployeePreferedLanguageMapper employeeMapper);

	public PersonalDetailsMapper updatePersonalDetails(PersonalDetailsMapper personalDetailsMapper);

	public EducationalDetailsMapper updateEducationalDetails(EducationalDetailsMapper educationalDetailsMapper);

	public TrainingDetailsMapper updateTrainingDetails(TrainingDetailsMapper trainingDetailsMapper);

	public BankDetailsMapper updateBankDetails(BankDetailsMapper bankDetailsMapper);

	public EmploymentHistoryMapper updateEmploymentHistory(EmploymentHistoryMapper employmentHistoryMapper);

	public NotesMapper updateNotes(NotesMapper notesMapper);

	public PersonalDetailsMapper getPersonalDetails(String id);

	public BankDetailsMapper getBankDetails(String id);

	public KeyskillsMapper getKeyskillsDetails(String id);

	public EducationalDetailsMapper getEducationDetails(String id);

	public TrainingDetailsMapper getTrainingDetails(String id);

	public EmploymentHistoryMapper getHistoryDetails(String id);

	public NotesMapper getNotes(String id);

	public SalaryDetailsMapper getSalaryDetails(String id);

	public void deleteKeySkillsById(String employeeId, String keySkillId);

	public String saveToEmployeeIdProcess(EmployeeIDMapper employeeIDMapper);

	public EmployeeIDMapper getEmployeeIdDetails(String id);

	public EmployeeIDMapper updateEmployeeIdDetails(EmployeeIDMapper employeeIDMapper);

	public List<EmployeeIDMapper> getEmployeeIDDetails(String employeeId);

	public List<EmployeeMapper> getEmployeeListByUserIdWithDateRange(String userId, String startDate, String endDate);

	public List<EmployeeMapper> getEmployeeListByOrgIdWithDateRange(String orgId, String startDate, String endDate);

	public String getHREmployeeId(String orgId);

	public String getAdminIdByOrgId(String orgId);

	public void deleteEmployementHistoryById(String id);

	public void deleteTrainingDetailsById(String id);

	public void deleteEducationDetailsById(String id);

	public void deleteBankDetailsById(String id);

	public void deletePersonalDetailsById(String id);

	public void deleteEmployeeIDDetailsById(String id);

	public List<NotesMapper> getNotesByEmployeeId(String employeeId);

	public String saveSalaryDetails(SalaryDetailsMapper salaryDetailsMapper);

	public List<SalaryDetailsMapper> getSalaryDetailsByEmployeeID(String employeeId);

	public SalaryDetailsMapper updateSalaryDetails(SalaryDetailsMapper salaryDetailsMapper);

	public EmployeeViewMapper updateEmployee(String employeeId, EmployeeMapper employeeMapper);

	public EmployeeViewMapper getEmployeeDetailsByUserId(String userId);

	public EmployeeViewMapper getEmployeeDetailsByEmployeeId(String employeeId);
	
	public EmployeeTableMapper getEmployeeDetailByEmployeeId(String employeeId);

	public EmployeeTableMapper suspendAndUnSuspendUser(String employeeId, boolean suspendImd);

	public List<EmployeeTableMapper> getEmployeeDetailsByName(String name);

	public List<EmployeeViewMapper> getEmployeeDetailsBySkill(String skill);

	public String saveEmployeeContract(EmployeeContractMapper employeeContractMapper);

	public List<EmployeeContractMapper> getEmployeeContract(String employeeId);

	public EmployeeContractMapper getEmployeeContracts(String id);

	public EmployeeContractMapper updateEmployeeContract(EmployeeContractMapper employeeContractMapper);

	public ByteArrayInputStream exportEmployeeListToExcel(List<EmployeeViewMapper> employeeList);

	public List<EmployeeViewMapper> getEmployeeListByUserId(String userId);

	public List<DocumentMapper> getEmployeeDocumentListByEmployeeId(String employeeId);

	public List<EmployeeViewMapper> getAllrecruiterList(String orgId);

	public List<EmployeeViewMapper> getAllSalesEmployeeList(String orgIdFromToken);

	public boolean getEmployeeDetailsByEmailId(String username);

	public boolean ActiveUser(String employeeId);

	List<DepartmentMapper> getAllSalesAndManagementList(String orgIdFromToken);

	public List<EmployeeViewMapper> getEmployeeListWhichEmployeeTypeIsEmployee(String employeeType);

	public List<EmployeeViewMapper> getEmployeeListWhichIsNotEmployee(String employeeType);

	public EmployeeViewMapper updateUserTypeByEmployeeId(String employeeId, EmployeeViewMapper employeeViewMapper);

	public List<EmployeeViewMapper> getEmployeeListByOrgIdForCustomerCreate(String organizationId);

	public List<EmployeeViewMapper> getEmployeeListByOrgIdForOpportunityCreate(String organizationId);

	public List<EmployeeViewMapper> getEmployeeListByOrgIdForPartnerCreate(String organizationId);

	public EmployeeViewMapper updateEmployeeRoleUserToAdminByEmployeeId(String employeeId,
			EmployeeRoleLinkMapper employeeRoleLinkMapper);

	public List<EmployeeViewMapper> getEmployeeListByOrgIdForRecruitmentCreate(String organizationId);

	public HashMap getActiveEmployeeListByUserIdAndLiveInd(String userId);
	
	public HashMap getAllEmployeeListByUserIdAndLiveInd(String userId);

	public List<EmployeeViewMapper> getEmployeeListWhoseDesignationManagerByOrgId(String orgId);

	String getEmployeeFullName(String employeeId);

    boolean checkSkillInEmployeeSkillSet(String keySkillsName, String employeeId);

	public KeyskillsMapper updateEmployeeSkill(String keySkillId, KeyskillsMapper skillSetMapper);

	public KeyskillsMapper updateEmployeeSkillRoleBySkillSetDetailsId(String keySkillId,
			KeyskillsMapper skillSetMapper);

	public KeyskillsMapper pauseAndUnpauseEmployeeSkillExperience(String keySkillId, KeyskillsMapper skillSetMapper);

	public EmployeeTreeMapper getEmployeeTreeByEmployeeId(String employeeId);

    List<EmployeeViewMapper> getEmployeeListByOrgIdForLeadsCreate(String organizationId);

	List<EmployeeViewMapper> getEmployeeListByOrgIdForContactCreate(String organizationId);

	List<Map<String,List<EmployeeViewMapper>>> getEmployeesByDepartmentWiseJoiningDate(String orgId, String startDate, String endDate);

	EmployeeShortMapper getEmployeeFullNameAndEmployeeId(String employeeId);

	List<Map> getEmployeesCountByDepartmentWiseJoiningDate(String orgId, String startDate, String endDate);

	public List<EmployeeShortMapper> getEmployeeListByOrgIdForDropDown(String orgId);

	public VisaMapper saveVisa(VisaMapper visaMapper);

	public VisaMapper getVisaDetailsVisaId(String visaId);

	public List<VisaMapper> getVisaByUserId(String userId);

	public VisaMapper updateVisa(String visaId, VisaMapper visaMapper);

	public void deleteVisa(String visaId);

	public Map getPendingDocListByUserId(String userId, String orgId);

	public List<EmployeeShortMapper> getEmployeeListWhoseErpIndTrueForDropDown(String orgId);

	public List<EmployeeShortMapper> getEmployeeListWhoseCrmIndTrueForDropDown(String orgId);

	public EmployeeTableMapper updateEmployeeByEmployeeId(EmployeeMapper employeeMapper, String employeeId);

	public List<EmployeeShortMapper> getEmployeeListByLocationIdWhoseDepartmentProductionForDropDown(
			String departmentId, String locationId, String userId);

	public List<EmployeeTableMapper> getEmployeeListByLocationId(String locationId);

	public List<EmployeeShortMapper> getEmployeeListWhoseImIndTrueForDropDown(String orgId);

	public void deleteEmployeeNotesById(String notesId);

	EmployeeTableMapper getEmployeeDetail(EmployeeDetails employeeDetails);

	public List<EmployeeShortMapper> getEmployeeListByDepartmentIdForDropDown(String departmentId);

	EmployeeTableMapper saveToEmployeeProcess(NewAdminRegisterMapper employeeMapper, String orgId);

	public HashMap getEmployeeListByLocationIdAndLiveInd(String locationId);

	String getEmployeeFullNameByObject(EmployeeDetails employeeDetails);
	
	public EmployeeTableMapper updateEmployeeUserToAdminByEmployeeId(
			EmployeeAdminUpdateRequestMapper employeeAdminUpdateRequestMapper);

	public EmployeeAdminUpdateResponseMapper getEmployeeUserToAdminByEmployeeId(String employeeId);
	
	public EmployeeTableMapper getTableMapperEmployeeDetails(EmployeeDetails employeeDetails);

	public List<EmployeeTableMapper> getUserListByReportingMangerId(String reptMngrId);

	public HashMap getUserCountByReportingMangerId(String reptMngrId);

	public UserKpiResponseMapper addKpiWithEmployee(UserKpiRequestMapper userKpiRequestMapper);

	public List<UserKpiResponseMapper> getKpiListByEmployeeId(String employeeId, double year, String quarter);

	public String employeeOnboarding(String employeeId, String loggedInUserId, String loggedInUserOrgId);

	public UserKpiResponseMapper addKpiCompletedValueByEmployeeId(UserKpiRequestForCompletedValueMapper userKpiRequestMapper);

	public String hardDeleteEmployeeByUserId(String userId);

	public void deleteKpiByEmployeeKpiLinkId(String employeeKpiLinkId);

	public UserKpiResponseMapper addKpiAssignedValueByEmployeeId(
			UserKpiRequestForAssignedValueMapper userKpiRequestMapper);

	public List<String> addFullName(String orgIdFromToken);

	public List<EmployeeWorkflowAndStageResponseMapper> addWorkflowWithEmployee(
			EmployeeWorkflowReqestMapper employeeWorkflowReqestMapper);

	public List<EmployeeWorkflowAndStageResponseMapper> getWorkflowStagetByEmployeeId(String employeeId);

	public boolean emailExistsInEmployeeByEmployeeId(String emailId, String employeeId);

	public String emailExistsInEmployee(String emailId);

	public UserSalaryBreakoutMapper getSalaryBreckOutByEmployeeId(String employeeId);

	public UserKpiResponseMapper addKpiaActualCompletedValueByEmployeeId(
			UserKpiRequestForCompletedValueMapper userKpiRequestMapper);

	public EmployeeWorkflowAndStageResponseMapper changeStageWithEmployee(
			EmployeeWorkflowReqestMapper employeeWorkflowReqestMapper);

	public List<EmployeeShortMapper> getEmployeeListByLocationIdAndDepartmentIdForDropDown(String locationId,
			String departmentId);

	EmployeeWorkflowAndStageResponseMapper getWorkflowStageByEmployeeWorkflowLinkId(String employeeWorkflowLinkId);

	public List<UserKpiLobMapper> getKpiTargetByLobByEmployeeIdMonthWise(String employeeId, String orgId);

	public List<UserEquipmentMapper> getEmployeeEquipmentByUserId(String userId);

	public List<UserEquipmentMapper> createUserEquipment(List<UserEquipmentMapper> requestMapper);

	public List<EmployeeTableMapper> getAllActiveEmployees(String organizationId);

	public HashMap linkEmailInUser(EmployeeEmailLinkMapper requestMapper);

	public String deleteNotes(NotesMapper notesMapper);

	public EmployeeTableMapper UpdateMultyOrgAccessInd(String employeeId, boolean multyOrgAccessInd);

	public List<EmployeeShortMapper> getEmployeeListByOrgIdAndTypeForDropDown(String orgId, String type);

	public List<EmployeeTableMapper> getAllEmployeeDetailsByName(String name, String orgId);

	public List<EmployeeTableMapper> getActiveEmployeeDetailsByName(String name, String orgId);

	public List<NotesMapper> getNotesByTypeAndUniqueId(String type, String uniqueId);

	public List<EmployeeShortMapper> getEmployeeListByDepartmentIdAndRoletypeId(String departmentId,
			String roletypetypeId);


    AddressMapper getLocationAddress(String userId);
}
