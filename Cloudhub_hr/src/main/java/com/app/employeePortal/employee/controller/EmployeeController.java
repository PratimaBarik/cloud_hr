
package com.app.employeePortal.employee.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.address.mapper.AddressViewMapper;
import com.app.employeePortal.location.mapper.LocationDetailsViewDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.Opportunity.service.OpportunityService;
import com.app.employeePortal.attendance.service.AttendanceService;
import com.app.employeePortal.authentication.config.TokenProvider;
import com.app.employeePortal.candidate.mapper.CandidateViewMapper;
import com.app.employeePortal.candidate.service.CandidateService;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.mapper.CustomerResponseMapper;
import com.app.employeePortal.customer.service.CustomerService;
import com.app.employeePortal.document.mapper.DocumentMapper;
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
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.investor.mapper.InvestorViewMapper;
import com.app.employeePortal.partner.mapper.PartnerMapper;
import com.app.employeePortal.partner.service.PartnerService;
import com.app.employeePortal.registration.mapper.DepartmentMapper;
import com.app.employeePortal.registration.service.RegistrationService;

@RestController
@CrossOrigin(maxAge = 3600)
public class EmployeeController {

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	EmployeeService employeeService;
	@Autowired
	CandidateService candidateService;
	@Autowired
	OpportunityService opportunityService;
	@Autowired
	ContactService contactService;
	@Autowired
	CustomerService customerService;
	@Autowired
	PartnerService partnerService;

	@Autowired
	RegistrationService registrationService;
	@Autowired
	AttendanceService attendanceService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/api/v1/employee")
	public ResponseEntity<?> createEmployee(@RequestBody EmployeeMapper employeeMapper,
			@RequestHeader("Authorization") String authorization) {
		HashMap map = new HashMap();

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInUserOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			if (!StringUtils.isEmpty(employeeMapper.getEmailId())) {
				if (registrationService.emailExist(employeeMapper.getEmailId())) {
					map.put("emailInd", true);
					map.put("message", "Employee with same mail already exists.");
					return new ResponseEntity<>(map, HttpStatus.OK);

				} else {

					employeeMapper.setOrganizationId(loggedInUserOrgId);
					employeeMapper.setUserId(loggedInUserId);
					employeeMapper.setRole("USER");
					employeeMapper.setUserType("USER");
					EmployeeTableMapper empId = employeeService.saveToEmployeeProcess(employeeMapper,
							loggedInUserOrgId);
					return new ResponseEntity<>(empId, HttpStatus.OK);
				}
			} else {
				map.put("message", " please provide EmailId !!!");
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/{employeeId}")

	public ResponseEntity<?> getEmployeeDetailsById(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			EmployeeViewMapper employeeMapper = employeeService.getEmployeeDetailsByEmployeeId(employeeId);

			return new ResponseEntity<EmployeeViewMapper>(employeeMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	/* retrieve all employees of an organization */
	@GetMapping("/api/v1/employee/employees/filter/{filter}/{type}")

	public ResponseEntity<?> getAllEmployees(@PathVariable("filter") String filter, HttpServletRequest request,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String organizationId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<EmployeeTableMapper> empList = new ArrayList<>();
			if (type.equalsIgnoreCase("active")) {
				empList = employeeService.getAllActiveEmployees(organizationId);
			} else if (type.equalsIgnoreCase("all")) {
				empList = employeeService.getEmployeesByOrgId(organizationId);
			}

			if (null != empList && !empList.isEmpty()) {
				if (filter.equalsIgnoreCase("cretiondate")) {
					Collections.sort(empList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
					return new ResponseEntity<>(empList, HttpStatus.OK);
				} else if (filter.equalsIgnoreCase("Atoz")) {
					Collections.sort(empList, (m1, m2) -> m1.getFullName().compareTo(m2.getFullName()));
					return new ResponseEntity<>(empList, HttpStatus.OK);
				} else if (filter.equalsIgnoreCase("Ztoa")) {
					Collections.sort(empList, (m1, m2) -> m2.getFullName().compareTo(m1.getFullName()));
					return new ResponseEntity<>(empList, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(empList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/employee/personal-details")
	public ResponseEntity<?> saveEmployeePersonalDetails(@RequestBody PersonalDetailsMapper personalDetailsMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String id = employeeService.savePersonalDetails(personalDetailsMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/personal-details/{employeeId}")

	public ResponseEntity<?> getEmployeePersonalDetailsById(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<PersonalDetailsMapper> personalDetailsMapper = employeeService.getEmployeePersonalDetails(employeeId);
			if (null != personalDetailsMapper && !personalDetailsMapper.isEmpty()) {
				Collections.sort(personalDetailsMapper, (PersonalDetailsMapper m1, PersonalDetailsMapper m2) -> m2
						.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(personalDetailsMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(personalDetailsMapper, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/employee/bank-details")
	public ResponseEntity<?> saveEmployeeBankDetails(@RequestBody BankDetailsMapper bankDetailsMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String id = employeeService.saveBankDetails(bankDetailsMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/bank-details/{employeeId}")

	public ResponseEntity<?> getBankDetailsById(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<BankDetailsMapper> bankDetailsMapper = employeeService.getEmployeeBankDetails(employeeId);
			if (null != bankDetailsMapper && !bankDetailsMapper.isEmpty()) {
				Collections.sort(bankDetailsMapper, (BankDetailsMapper m1, BankDetailsMapper m2) -> m2.getCreationDate()
						.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(bankDetailsMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(bankDetailsMapper, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/employee/key-skills")
	public ResponseEntity<?> saveEmployeeKeyskills(@RequestBody KeyskillsMapper keyskillsMapper,
			@RequestHeader("Authorization") String authorization) {

		Map map = new HashMap<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (!StringUtils.isEmpty(keyskillsMapper.getKeySkillsName())) {
				boolean b = employeeService.checkSkillInEmployeeSkillSet(keyskillsMapper.getKeySkillsName(),
						keyskillsMapper.getEmployeeId());
				if (b) {
					map.put("skillInd", true);
					map.put("message", "Skill name can not be created as same name already exists!!!");
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			keyskillsMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			keyskillsMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			String id = employeeService.saveKeyskills(keyskillsMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/key-skills/{employeeId}")

	public ResponseEntity<?> getKeySkillsById(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<KeyskillsMapper> keyskillsMapper = employeeService.getEmployeeKeyskillsDetails(employeeId);
			if (null != keyskillsMapper && !keyskillsMapper.isEmpty()) {
				keyskillsMapper.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(keyskillsMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(keyskillsMapper, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/employee/education-details")
	public ResponseEntity<?> saveEducationDetails(@RequestBody EducationalDetailsMapper educationalDetailsMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String id = employeeService.saveEducationDetails(educationalDetailsMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/education-details/{employeeId}")

	public ResponseEntity<?> getEducationDetailsById(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<EducationalDetailsMapper> educationalDetailsMapper = employeeService
					.getEmployeeEducationDetails(employeeId);
			if (null != educationalDetailsMapper && !educationalDetailsMapper.isEmpty()) {
				Collections.sort(educationalDetailsMapper, (EducationalDetailsMapper m1,
						EducationalDetailsMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(educationalDetailsMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(educationalDetailsMapper, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/employee/training-details")
	public ResponseEntity<?> saveTrainingDetails(@RequestBody TrainingDetailsMapper trainingDetailsMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String id = employeeService.saveTrainingDetails(trainingDetailsMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/training-details/{employeeId}")

	public ResponseEntity<?> getTrainingDetailsById(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<TrainingDetailsMapper> trainingDetailsMapper = employeeService.getEmployeeTrainingDetails(employeeId);
			if (null != trainingDetailsMapper && !trainingDetailsMapper.isEmpty()) {
				Collections.sort(trainingDetailsMapper, (TrainingDetailsMapper m1, TrainingDetailsMapper m2) -> m2
						.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(trainingDetailsMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(trainingDetailsMapper, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/employee/employment-history")
	public ResponseEntity<?> saveEmploymentHistory(@RequestBody EmploymentHistoryMapper employmentHistoryMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String id = employeeService.saveEmploymentHistory(employmentHistoryMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/employment-history/{employeeId}")

	public ResponseEntity<?> getEmploymentHistoryById(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<EmploymentHistoryMapper> employmentHistoryMapper = employeeService
					.getEmploymentHistoryDetails(employeeId);
			Collections.sort(employmentHistoryMapper, (EmploymentHistoryMapper m1, EmploymentHistoryMapper m2) -> m2
					.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(employmentHistoryMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/profile")
	public ResponseEntity<?> getEmploymentProfileById(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			EmployeeViewMapper employeeMapper = employeeService.getEmployeeDetailsByUserId(userId);
			employeeMapper.setStartInd(attendanceService.getStartIndByUserId(userId));
			return new ResponseEntity<EmployeeViewMapper>(employeeMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/key-skills")

	public ResponseEntity<?> updateKeySkills(@RequestBody KeyskillsMapper keyskillsMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			KeyskillsMapper newKeyskillsMapper = employeeService.updateKeyskills(keyskillsMapper);

			return new ResponseEntity<KeyskillsMapper>(newKeyskillsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/update/preferedLanguage")

	public ResponseEntity<?> updateEmployeePreferedLanguage(@RequestBody EmployeePreferedLanguageMapper employeeMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			EmployeePreferedLanguageMapper newEmployeeMapper = employeeService
					.updateEmployeePreferedLanguage(employeeMapper);

			return new ResponseEntity<>(newEmployeeMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/personal-details")

	public ResponseEntity<?> updatePersonal(@RequestBody PersonalDetailsMapper personalDetailsMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			PersonalDetailsMapper newpersonalDetailsMapper = employeeService
					.updatePersonalDetails(personalDetailsMapper);

			return new ResponseEntity<PersonalDetailsMapper>(newpersonalDetailsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/education-details")

	public ResponseEntity<?> updateEducational(@RequestBody EducationalDetailsMapper educationalDetailsMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			EducationalDetailsMapper neweducationalDetailsMapper = employeeService
					.updateEducationalDetails(educationalDetailsMapper);

			return new ResponseEntity<EducationalDetailsMapper>(neweducationalDetailsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/training-details")

	public ResponseEntity<?> updateTraining(@RequestBody TrainingDetailsMapper trainingDetailsMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			TrainingDetailsMapper newTrainingDetailsMapper = employeeService
					.updateTrainingDetails(trainingDetailsMapper);

			return new ResponseEntity<TrainingDetailsMapper>(newTrainingDetailsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/bank-details")

	public ResponseEntity<?> updateBankDetails(@RequestBody BankDetailsMapper bankDetailsMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			BankDetailsMapper newBankDetailsMapper = employeeService.updateBankDetails(bankDetailsMapper);

			return new ResponseEntity<BankDetailsMapper>(newBankDetailsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/employment-history")

	public ResponseEntity<?> updateEmploymentHistory(@RequestBody EmploymentHistoryMapper employmentHistoryMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			EmploymentHistoryMapper newEmploymentHistoryMapper = employeeService
					.updateEmploymentHistory(employmentHistoryMapper);

			return new ResponseEntity<EmploymentHistoryMapper>(newEmploymentHistoryMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	/* delete a employee keyskills */
	@DeleteMapping("/api/v1/employee/{employeeId}/key-skills/{keySkillId}")
	public ResponseEntity<?> deleteKeySkills(@PathVariable("employeeId") String employeeId,
			@PathVariable("keySkillId") String keySkillId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			employeeService.deleteKeySkillsById(employeeId, keySkillId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/employee/employee-id-details")
	public ResponseEntity<?> saveEmployeeIdDetails(@RequestBody EmployeeIDMapper employeeIDMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String id = employeeService.saveToEmployeeIdProcess(employeeIDMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	/* retrieve all EmployeeIdDetails by employeeId */
	@GetMapping("/api/v1/employee/employee-id-details/{employeeId}")

	public ResponseEntity<?> getAllEmployeeIDs(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<EmployeeIDMapper> employeeIDMapper = employeeService.getEmployeeIDDetails(employeeId);
			if (null != employeeIDMapper && !employeeIDMapper.isEmpty()) {
				Collections.sort(employeeIDMapper, (EmployeeIDMapper m1, EmployeeIDMapper m2) -> m2.getCreationDate()
						.compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(employeeIDMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(employeeIDMapper, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/employee/employee-id-details")

	public ResponseEntity<?> updateEmployeeID(@RequestBody EmployeeIDMapper employeeIDMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			EmployeeIDMapper newEmployeeIdMapper = employeeService.updateEmployeeIdDetails(employeeIDMapper);

			return new ResponseEntity<EmployeeIDMapper>(newEmployeeIdMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/employee/employee-id-details/{id}")

	public ResponseEntity<?> deleteEmployeeIDDetails(@PathVariable("id") String id,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			employeeService.deleteEmployeeIDDetailsById(id);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/employee/personal-details/{id}")

	public ResponseEntity<?> deleteEmployeePersonalDetails(@PathVariable("id") String id,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			employeeService.deletePersonalDetailsById(id);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/employee/training-details/{id}")

	public ResponseEntity<?> deleteTrainingDetails(@PathVariable("id") String id,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			employeeService.deleteTrainingDetailsById(id);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/employee/employment-history/{id}")

	public ResponseEntity<?> deleteEmploymentHistory(@PathVariable("id") String id,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			employeeService.deleteEmployementHistoryById(id);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/employee/education-details/{id}")

	public ResponseEntity<?> deleteEducationDetails(@PathVariable("id") String id,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			employeeService.deleteEducationDetailsById(id);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/employee/bank-details/{id}")

	public ResponseEntity<?> deleteBankDetails(@PathVariable("id") String id,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			employeeService.deleteBankDetailsById(id);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/employee/notes")
	public ResponseEntity<?> saveNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String id = employeeService.saveNotes(notesMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/notes")

	public ResponseEntity<?> updateNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			NotesMapper notesMapperr = employeeService.updateNotes(notesMapper);

			return new ResponseEntity<NotesMapper>(notesMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/notes/{employeeId}")

	public ResponseEntity<?> getNotesByEmployeeID(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<NotesMapper> notesMapper = employeeService.getNotesByEmployeeId(employeeId);
			if (null != notesMapper && !notesMapper.isEmpty()) {
				Collections.sort(notesMapper,
						(NotesMapper m1, NotesMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(notesMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(notesMapper, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/employee/salaryDetails")
	public ResponseEntity<?> saveSalaryDetails(@RequestBody SalaryDetailsMapper salaryDetailsMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String id = employeeService.saveSalaryDetails(salaryDetailsMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/salaryDetails/{employeeId}")

	public ResponseEntity<?> getSalaryDetailsByEmployeeID(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<SalaryDetailsMapper> salaryDetailsMapper = employeeService.getSalaryDetailsByEmployeeID(employeeId);
			// System.out.println("salry details list"+salaryDetailsMapper.toString());

			return new ResponseEntity<>(salaryDetailsMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/employee/salaryDetails")
	public ResponseEntity<?> updateSalaryDetails(@RequestBody SalaryDetailsMapper salaryDetailsMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			SalaryDetailsMapper salaryDetailsMapper1 = employeeService.updateSalaryDetails(salaryDetailsMapper);

			return new ResponseEntity<>(salaryDetailsMapper1, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/{employeeId}")

	public ResponseEntity<?> updateEmployee(@PathVariable("employeeId") String employeeId,
			@RequestBody EmployeeMapper employeeMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			employeeMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			employeeMapper.setOrganizationId(jwtTokenUtil.getOrgIdFromToken(authToken));
			EmployeeViewMapper newemployeeMapper = employeeService.updateEmployee(employeeId, employeeMapper);

			return new ResponseEntity<EmployeeViewMapper>(newemployeeMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/suspend/employee/{employeeId}/{suspendInd}")
	public ResponseEntity<?> suspendUser(@PathVariable("employeeId") String employeeId,
			@PathVariable("suspendInd") boolean suspendInd, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			EmployeeTableMapper mapper = employeeService.suspendAndUnSuspendUser(employeeId, suspendInd);

			return new ResponseEntity<>(mapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/details/{name}")
	public ResponseEntity<?> getEmployeeDetailsByName(@PathVariable("name") String name,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			// Map map = new HashMap();
			List<EmployeeTableMapper> employeeViewMapper = employeeService.getEmployeeDetailsByName(name);
			if (null != employeeViewMapper && !employeeViewMapper.isEmpty()) {

				return new ResponseEntity<>(employeeViewMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Data not Found", HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/details-skill/{skill}")
	public ResponseEntity<?> getEmployeeDetailsBySkill(@PathVariable("skill") String skill,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<EmployeeViewMapper> employeeViewMapper = employeeService.getEmployeeDetailsBySkill(skill);

			// Collections.sort(candidateMapper, ( m1, m2) -> m2.getCreationDate()
			// .compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(employeeViewMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/employee/employee-contract")
	public ResponseEntity<?> saveEmployeeContract(@RequestBody EmployeeContractMapper employeeContractMapper,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String id = employeeService.saveEmployeeContract(employeeContractMapper);
			return new ResponseEntity<>(id, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/employee-contract/{employeeId}")

	public ResponseEntity<?> getEmployeeContractsById(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<EmployeeContractMapper> employeeContractMapper = employeeService.getEmployeeContract(employeeId);
			/*
			 * Collections.sort(employeeContractMapper, (EducationalDetailsMapper m1,
			 * EducationalDetailsMapper m2) -> m2.getCreationDate()
			 * .compareTo(m1.getCreationDate()));
			 */
			return new ResponseEntity<>(employeeContractMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/employee-contract")

	public ResponseEntity<?> updateEmployeeContract(@RequestBody EmployeeContractMapper employeeContractMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			EmployeeContractMapper neweEmployeeContractMapper = employeeService
					.updateEmployeeContract(employeeContractMapper);

			return new ResponseEntity<EmployeeContractMapper>(neweEmployeeContractMapper, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/user/{userId}")

	public ResponseEntity<List<EmployeeViewMapper>> getEmployeeDetailsByuserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<EmployeeViewMapper> employeeDetailsList = employeeService.getEmployeeListByUserId(userId);
			Collections.sort(employeeDetailsList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(employeeDetailsList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/document/{employeeId}")

	public ResponseEntity<?> getDocumentListByEmployeeId(@PathVariable("employeeId") String employeeId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<DocumentMapper> docList = employeeService.getEmployeeDocumentListByEmployeeId(employeeId);
			// Collections.sort(oppList, (OpportunityMapper m1, OpportunityMapper m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));

			return new ResponseEntity<>(docList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("api/v1/employee/all-recruiter")
	public ResponseEntity<?> getAllRecruiterList(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		List<EmployeeViewMapper> recruiterList = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			recruiterList = employeeService.getAllrecruiterList(jwtTokenUtil.getOrgIdFromToken(authToken));
			Collections.sort(recruiterList, (v1, v2) -> v2.getCreationDate().compareTo(v1.getCreationDate()));

		}
		return new ResponseEntity<>(recruiterList, HttpStatus.OK);

	}

	@GetMapping("api/v1/employee/all-sales")
	public ResponseEntity<?> getAllSalesList(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		List<EmployeeViewMapper> recruiterList = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			recruiterList = employeeService.getAllSalesEmployeeList(jwtTokenUtil.getOrgIdFromToken(authToken));

		}
		return new ResponseEntity<>(recruiterList, HttpStatus.OK);

	}

	@GetMapping("api/v1/permision/all-count/{type}")
	public ResponseEntity<?> countAllcandidate(@PathVariable("type") String type,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		Map map = new HashMap();
		String authToken = authorization.replace(TOKEN_PREFIX, "");

		if (type.equalsIgnoreCase("candidate")) {
			List<CandidateViewMapper> candidateList = candidateService.getAllCandidateListCount();
			map.put("record", candidateList.size());
			return ResponseEntity.ok(map);

		} else if (type.equalsIgnoreCase("customer")) {
			List<CustomerResponseMapper> customerList = customerService.getAllCustomerListCount();
			map.put("record", customerList.size());
			return ResponseEntity.ok(map);

		} else if (type.equalsIgnoreCase("opportunity")) {
			List<OpportunityViewMapper> oppList = opportunityService.getAllOpportunityListCount();
			map.put("record", oppList.size());
			return ResponseEntity.ok(map);

		} else if (type.equalsIgnoreCase("partner")) {
			List<PartnerMapper> partnerList = partnerService.getAllPartnerListCount();
			map.put("record", partnerList.size());
			return ResponseEntity.ok(map);

		} else if (type.equalsIgnoreCase("partnerContact")) {
			List<ContactViewMapper> partnerContactList = contactService.getAllPartnerContatListCount();
			map.put("record", partnerContactList.size());
			return ResponseEntity.ok(map);
		} else if (type.equalsIgnoreCase("customerContact")) {
			List<ContactViewMapper> customerContactList = contactService.getAllCustomerContatListCount();
			map.put("record", customerContactList.size());
			return ResponseEntity.ok(map);
		}
		return null;
	}

	@PutMapping("/api/v1/active/employee/{employeeId}")
	public ResponseEntity<?> ActiveUser(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			employeeService.ActiveUser(employeeId);

			return new ResponseEntity<>(HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("api/v1/employee/all-sales-management")
	public ResponseEntity<?> getAllSalesAndManagementList(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		List<DepartmentMapper> recruiterList = new ArrayList<>();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			recruiterList = employeeService.getAllSalesAndManagementList(jwtTokenUtil.getOrgIdFromToken(authToken));

		}
		return new ResponseEntity<>(recruiterList, HttpStatus.OK);

	}

	@GetMapping("/api/v1/employee/employees/{employeeType}")

	public ResponseEntity<?> getEmployeeListByEmployeeType(@PathVariable("employeeType") String employeeType,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			if (employeeType.equalsIgnoreCase("employee")) {
				List<EmployeeViewMapper> empList = employeeService
						.getEmployeeListWhichEmployeeTypeIsEmployee(employeeType);
				Collections.sort(empList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

				return new ResponseEntity<>(empList, HttpStatus.OK);

			} else if (employeeType.equalsIgnoreCase("contractor")) {
				List<EmployeeViewMapper> empList = employeeService.getEmployeeListWhichIsNotEmployee(employeeType);
				Collections.sort(empList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

				return new ResponseEntity<>(empList, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/update/UserType/{employeeId}")
	public ResponseEntity<?> updateUserTypeByEmployeeId(@RequestBody EmployeeViewMapper employeeViewMapper,
			@PathVariable("employeeId") String employeeId, HttpServletRequest request,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			EmployeeViewMapper employeeViewMapper1 = employeeService.updateUserTypeByEmployeeId(employeeId,
					employeeViewMapper);

			return new ResponseEntity<>(employeeViewMapper1, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/update/role/user-admin/{employeeId}")
	public ResponseEntity<?> updateEmployeeRoleUserToAdminByEmployeeId(
			@RequestBody EmployeeRoleLinkMapper employeeRoleLinkMapper, @PathVariable("employeeId") String employeeId,
			HttpServletRequest request, @RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");
			employeeRoleLinkMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			employeeRoleLinkMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));

			EmployeeViewMapper employeeViewMapper1 = employeeService
					.updateEmployeeRoleUserToAdminByEmployeeId(employeeId, employeeRoleLinkMapper);

			return new ResponseEntity<>(employeeViewMapper1, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/count/{orgId}/{type}")
	public ResponseEntity<?> getEmployeeListByUserIdAndLiveInd(@PathVariable("orgId") String orgId,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (type.equalsIgnoreCase("active")) {
				return ResponseEntity.ok(employeeService.getActiveEmployeeListByUserIdAndLiveInd(orgId));
			} else if (type.equalsIgnoreCase("all")) {
				return ResponseEntity.ok(employeeService.getAllEmployeeListByUserIdAndLiveInd(orgId));
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/list/designation/manager/{orgId}")
	public ResponseEntity<?> getEmployeeListWhoseDesignationManagerByOrgId(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<EmployeeViewMapper> list = employeeService.getEmployeeListWhoseDesignationManagerByOrgId(orgId);
			return new ResponseEntity<>(list, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/keySkill/{keySkillId}")

	public ResponseEntity<?> updateEmployeeExperienceBySkill(@PathVariable("keySkillId") String keySkillId,
			@RequestBody KeyskillsMapper skillSetMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			KeyskillsMapper skillSetMapperNew = employeeService.updateEmployeeSkill(keySkillId, skillSetMapper);

			return new ResponseEntity<KeyskillsMapper>(skillSetMapperNew, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/skill/role/{keySkillId}")
	public ResponseEntity<?> updateEmployeeSkillRoleBySkillSetDetailsId(@PathVariable("keySkillId") String keySkillId,
			@RequestBody KeyskillsMapper skillSetMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			KeyskillsMapper skillSetMapperr = employeeService.updateEmployeeSkillRoleBySkillSetDetailsId(keySkillId,
					skillSetMapper);

			return new ResponseEntity<KeyskillsMapper>(skillSetMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/employee/pause/skill/experience/{keySkillId}")

	public ResponseEntity<?> pauseAndUnpauseEmployeeSkillExperience(@PathVariable("keySkillId") String keySkillId,
			@RequestBody KeyskillsMapper skillSetMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			KeyskillsMapper skillSetMapperNew = employeeService.pauseAndUnpauseEmployeeSkillExperience(keySkillId,
					skillSetMapper);

			return new ResponseEntity<KeyskillsMapper>(skillSetMapperNew, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/tree/{employeeId}")
	public ResponseEntity<?> getEmployeeTreeByEmployeeId(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			EmployeeTreeMapper employeeTreeMapper = employeeService.getEmployeeTreeByEmployeeId(employeeId);

			return new ResponseEntity<EmployeeTreeMapper>(employeeTreeMapper, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/newJoins/departmentWise")
	public ResponseEntity<?> getEmployeesByDepartmentWiseJoiningDate(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			return ResponseEntity
					.ok(employeeService.getEmployeesByDepartmentWiseJoiningDate(orgId, startDate, endDate));

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/Count/newJoins/departmentWise")
	public ResponseEntity<?> getEmployeesCountByDepartmentWiseJoiningDate(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = (jwtTokenUtil.getOrgIdFromToken(authToken));
			return ResponseEntity
					.ok(employeeService.getEmployeesCountByDepartmentWiseJoiningDate(orgId, startDate, endDate));

		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/active/user/drop-down/{orgId}")
	public ResponseEntity<List<?>> getEmployeeListByuserIdForDropDown(@PathVariable("orgId") String orgId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<EmployeeShortMapper> employeeDetailsList = employeeService.getEmployeeListByOrgIdForDropDown(orgId);
			List<EmployeeShortMapper> list = employeeDetailsList.stream()
					.sorted((o1, o2) -> o1.getEmpName().compareToIgnoreCase(o2.getEmpName()))
					.collect(Collectors.toList());
			// Collections.sort(employeeDetailsList, (m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(list, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/employee/visa")
	public ResponseEntity<?> createVisa(@RequestBody VisaMapper visaMapper,
			@RequestHeader("Authorization") String authorization) {

		String sourceId = null;

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");

			visaMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			visaMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			VisaMapper visaId = employeeService.saveVisa(visaMapper);

			return new ResponseEntity<>(visaId, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/visa/detail/{visaId}")
	public ResponseEntity<?> getVisaDetailsVisaId(@PathVariable("visaId") String visaId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			VisaMapper visaMapper = employeeService.getVisaDetailsVisaId(visaId);

			return new ResponseEntity<>(visaMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/visa/user/{userId}")
	public ResponseEntity<?> getVisaByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<VisaMapper> visaMapper = employeeService.getVisaByUserId(userId);

			return new ResponseEntity<>(visaMapper, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/visa/update/{visaId}")

	public ResponseEntity<?> updateVisa(@PathVariable("visaId") String visaId, @RequestBody VisaMapper visaMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			visaMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			visaMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));

			return new ResponseEntity<VisaMapper>(employeeService.updateVisa(visaId, visaMapper), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/employee/visa/delete/{visaId}")
	public ResponseEntity<?> deleteVisa(@PathVariable("visaId") String visaId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			employeeService.deleteVisa(visaId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/user/doc-pending/{userId}")
	public ResponseEntity<?> getPendingDocListByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			Map map = employeeService.getPendingDocListByUserId(userId, orgId);

			return new ResponseEntity<>(map, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/user-list/drop-down/erp")
	public ResponseEntity<?> getEmployeeListWhoseErpIndTrueForDropDown(
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<EmployeeShortMapper> employeeDetailsList = employeeService
					.getEmployeeListWhoseErpIndTrueForDropDown(orgId);
			List<EmployeeShortMapper> list = employeeDetailsList.stream()
					.sorted((o1, o2) -> o1.getEmpName().compareToIgnoreCase(o2.getEmpName()))
					.collect(Collectors.toList());
			// Collections.sort(employeeDetailsList, (m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/user-list/drop-down/crm")
	public ResponseEntity<?> getEmployeeListWhoseCrmIndTrueForDropDown(
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<EmployeeShortMapper> employeeDetailsList = employeeService
					.getEmployeeListWhoseCrmIndTrueForDropDown(orgId);
			List<EmployeeShortMapper> list = employeeDetailsList.stream()
					.sorted((o1, o2) -> o1.getEmpName().compareToIgnoreCase(o2.getEmpName()))
					.collect(Collectors.toList());
			// Collections.sort(employeeDetailsList, (m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/employee/update/{employeeId}")
	public ResponseEntity<?> updateEmployeeByEmployeeId(@PathVariable("employeeId") String employeeId,
			@RequestBody EmployeeMapper employeeMapper, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		Map map = new HashMap();
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			if (!StringUtils.isEmpty(employeeMapper.getEmailId())) {
				boolean c = employeeService.emailExistsInEmployeeByEmployeeId(employeeMapper.getEmailId(), employeeId);
				if (c == true) {
					EmployeeTableMapper employeeTableMapper = employeeService.updateEmployeeByEmployeeId(employeeMapper,
							employeeId);

					return new ResponseEntity<>(employeeTableMapper, HttpStatus.OK);
//					map.put("employeeInd", true);
//					map.put("message", "Candidate with same mail id already exists. ");
//					return new ResponseEntity<>(map, HttpStatus.OK);
				} else {
					String name = employeeService.emailExistsInEmployee(employeeMapper.getEmailId());
					if (null != name) {
						map.put("employeeInd", true);
						map.put("message", "Employee with same mail id already exists, with Name " + name + "!!!");
						return new ResponseEntity<>(map, HttpStatus.OK);
					} else {
						EmployeeTableMapper employeeTableMapper = employeeService
								.updateEmployeeByEmployeeId(employeeMapper, employeeId);

						return new ResponseEntity<>(employeeTableMapper, HttpStatus.OK);
					}

				}
			} else {
				EmployeeTableMapper employeeTableMapper = employeeService.updateEmployeeByEmployeeId(employeeMapper,
						employeeId);

				return new ResponseEntity<>(employeeTableMapper, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/user-list/drop-down/{departmentId}/{locationId}")
	public ResponseEntity<?> getEmployeeListByLocationIdWhoseDepartmentProductionForDropDown(
			@PathVariable("departmentId") String departmentId, @PathVariable("locationId") String locationId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<EmployeeShortMapper> employeeDetailsList = employeeService
					.getEmployeeListByLocationIdWhoseDepartmentProductionForDropDown(departmentId, locationId, userId);
			List<EmployeeShortMapper> list = employeeDetailsList.stream()
					.sorted((o1, o2) -> o1.getEmpName().compareToIgnoreCase(o2.getEmpName()))
					.collect(Collectors.toList());
			// Collections.sort(employeeDetailsList, (m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/user-list/{locationId}")
	public ResponseEntity<?> getEmployeeListByLocationId(@PathVariable("locationId") String locationId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<EmployeeTableMapper> employeeDetailsList = employeeService.getEmployeeListByLocationId(locationId);
			if (null != employeeDetailsList && !employeeDetailsList.isEmpty()) {
				List<EmployeeTableMapper> employeeDetailsList1 = employeeDetailsList.stream()
						.sorted((o1, o2) -> o1.getFullName().compareToIgnoreCase(o2.getFullName()))
						.collect(Collectors.toList());
				// Collections.sort(employeeDetailsList, (m1, m2) ->
				// m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(employeeDetailsList1, HttpStatus.OK);
			}
			return new ResponseEntity<>(employeeDetailsList, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/user-list/drop-down/im")
	public ResponseEntity<?> getEmployeeListWhoseImIndTrueForDropDown(
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<EmployeeShortMapper> employeeDetailsList = employeeService
					.getEmployeeListWhoseImIndTrueForDropDown(orgId);
			List<EmployeeShortMapper> list = employeeDetailsList.stream()
					.sorted((o1, o2) -> o1.getEmpName().compareToIgnoreCase(o2.getEmpName()))
					.collect(Collectors.toList());
//			 Collections.sort(employeeDetailsList, (m1, m2) ->
//			 m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/employee/notes/{notesId}")
	public ResponseEntity<?> deleteEmployeeNote(@PathVariable("notesId") String notesId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			employeeService.deleteEmployeeNotesById(notesId);

			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/list/{departmentId}")
	public ResponseEntity<?> getEmployeeListByDepartmentIdForDropDown(@PathVariable("departmentId") String departmentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<EmployeeShortMapper> employeeDetailsList = employeeService
					.getEmployeeListByDepartmentIdForDropDown(departmentId);
			List<EmployeeShortMapper> list = employeeDetailsList.stream()
					.sorted((o1, o2) -> o1.getEmpName().compareToIgnoreCase(o2.getEmpName()))
					.collect(Collectors.toList());
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/count/location/{locationId}")
	public ResponseEntity<?> getEmployeeListByLocationIdAndLiveInd(@PathVariable("locationId") String locationId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			return ResponseEntity.ok(employeeService.getEmployeeListByLocationIdAndLiveInd(locationId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/employee/update/admin-user/user-admin")
	public ResponseEntity<?> updateEmployeeUserToAdminByEmployeeId(
			@RequestBody EmployeeAdminUpdateRequestMapper employeeAdminUpdateRequestMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			employeeAdminUpdateRequestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			employeeAdminUpdateRequestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			EmployeeTableMapper employeeTableMapper = employeeService
					.updateEmployeeUserToAdminByEmployeeId(employeeAdminUpdateRequestMapper);

			return new ResponseEntity<>(employeeTableMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/adminUpdate/{employeeId}")
	public ResponseEntity<?> getEmployeeUserToAdminByEmployeeId(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			EmployeeAdminUpdateResponseMapper mapper = employeeService.getEmployeeUserToAdminByEmployeeId(employeeId);
			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/user-list/reptMngr/{reptMngrId}")
	public ResponseEntity<?> getUserListByReportingMangerId(@PathVariable("reptMngrId") String reptMngrId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<EmployeeTableMapper> mapper = employeeService.getUserListByReportingMangerId(reptMngrId);
			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/user-count/reptMngr/{reptMngrId}")
	public ResponseEntity<?> getUserCountByReportingMangerId(@PathVariable("reptMngrId") String reptMngrId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			return ResponseEntity.ok(employeeService.getUserCountByReportingMangerId(reptMngrId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/employee/kpi-with-user/save")
	public ResponseEntity<?> addKpiWithEmployee(@RequestHeader("Authorization") String authorization,
			@RequestBody UserKpiRequestMapper userKpiRequestMapper, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			userKpiRequestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			userKpiRequestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			UserKpiResponseMapper listOfKpi = employeeService.addKpiWithEmployee(userKpiRequestMapper);
			return new ResponseEntity<>(listOfKpi, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/kpi-list/{employeeId}/{year}/{quarter}")
	public ResponseEntity<?> getKpiListByEmployeeId(@PathVariable("employeeId") String employeeId,
			@PathVariable("year") double year, @PathVariable("quarter") String quarter,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<UserKpiResponseMapper> mapper = employeeService.getKpiListByEmployeeId(employeeId, year, quarter);
			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/employee/onboarding/{employeeId}")
	public ResponseEntity<?> candidateOnboarding(@PathVariable String employeeId,
			@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authToken);
			String loggedInUserOrgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			String msg = employeeService.employeeOnboarding(employeeId, loggedInUserId, loggedInUserOrgId);
			return new ResponseEntity<>(msg, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/employee/kpi-completed-value/save")
	public ResponseEntity<?> addKpiCompletedValueByEmployeeId(@RequestHeader("Authorization") String authorization,
			@RequestBody UserKpiRequestForCompletedValueMapper userKpiRequestMapper, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			userKpiRequestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
//			userKpiRequestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			UserKpiResponseMapper listOfKpi = employeeService.addKpiCompletedValueByEmployeeId(userKpiRequestMapper);
			return new ResponseEntity<>(listOfKpi, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/employee/kpi-assigned-value/save")
	public ResponseEntity<?> addKpiAssignedValueByEmployeeId(@RequestHeader("Authorization") String authorization,
			@RequestBody UserKpiRequestForAssignedValueMapper userKpiRequestMapper, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
//			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			userKpiRequestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
//			userKpiRequestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			UserKpiResponseMapper listOfKpi = employeeService.addKpiAssignedValueByEmployeeId(userKpiRequestMapper);
			return new ResponseEntity<>(listOfKpi, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/employee/kpi-actual-completed-value/save")
	public ResponseEntity<?> addKpiaActualCompletedValueByEmployeeId(
			@RequestHeader("Authorization") String authorization,
			@RequestBody UserKpiRequestForCompletedValueMapper userKpiRequestMapper, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			userKpiRequestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			userKpiRequestMapper.setActualCmpltValueAddedBy(jwtTokenUtil.getUserIdFromToken(authToken));
			UserKpiResponseMapper listOfKpi = employeeService
					.addKpiaActualCompletedValueByEmployeeId(userKpiRequestMapper);
			return new ResponseEntity<>(listOfKpi, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/api/v1/employee/hard-delete/{userId}")
	public ResponseEntity<?> hardDeleteEmployee(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String msg = employeeService.hardDeleteEmployeeByUserId(userId);
			return new ResponseEntity<>(msg, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@DeleteMapping("/api/v1/employee/delete/kpi/{employeeKpiLinkId}")
	public ResponseEntity<?> deleteKpiByEmployeeKpiLinkId(@PathVariable("employeeKpiLinkId") String employeeKpiLinkId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			employeeService.deleteKpiByEmployeeKpiLinkId(employeeKpiLinkId);
			return new ResponseEntity<>("This is deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PutMapping("/api/v1/employee/add-full-name/save")
	public ResponseEntity<?> addFullName(@RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
//			userKpiRequestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
//			userKpiRequestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			List<String> listFullName = employeeService.addFullName(jwtTokenUtil.getOrgIdFromToken(authToken));
			return new ResponseEntity<>(listFullName, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/employee/add/workflow/save")
	public ResponseEntity<?> addWorkflowWithEmployee(@RequestHeader("Authorization") String authorization,
			@RequestBody EmployeeWorkflowReqestMapper employeeWorkflowReqestMapper, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			employeeWorkflowReqestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			employeeWorkflowReqestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			List<EmployeeWorkflowAndStageResponseMapper> list = employeeService
					.addWorkflowWithEmployee(employeeWorkflowReqestMapper);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/employee/workflow/stage/update")
	public ResponseEntity<?> changeStageWithEmployee(@RequestHeader("Authorization") String authorization,
			@RequestBody EmployeeWorkflowReqestMapper employeeWorkflowReqestMapper, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			employeeWorkflowReqestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			employeeWorkflowReqestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			EmployeeWorkflowAndStageResponseMapper list = employeeService
					.changeStageWithEmployee(employeeWorkflowReqestMapper);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/workflow-stage/{employeeId}")
	public ResponseEntity<?> getWorkflowStageByEmployeeId(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<EmployeeWorkflowAndStageResponseMapper> mapper = employeeService
					.getWorkflowStagetByEmployeeId(employeeId);
			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/salary-breckOut/{employeeId}")
	public ResponseEntity<?> getSalaryBreckOutByEmployeeId(@PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			UserSalaryBreakoutMapper mapper = employeeService.getSalaryBreckOutByEmployeeId(employeeId);
			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/user/list/drop-down/{locationId}/{departmentId}")
	public ResponseEntity<?> getEmployeeListByLocationIdAndDepartmentIdForDropDown(
			@PathVariable("locationId") String locationId, @PathVariable("departmentId") String departmentId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<EmployeeShortMapper> employeeDetailsList = employeeService
					.getEmployeeListByLocationIdAndDepartmentIdForDropDown(locationId, departmentId);

			List<EmployeeShortMapper> list = employeeDetailsList.stream()
					.sorted((o1, o2) -> o1.getEmpName().compareToIgnoreCase(o2.getEmpName()))
					.collect(Collectors.toList());
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/kpi/target/lob/month-wise/{employeeId}/{orgId}")
	public ResponseEntity<?> getKpiTargetByLobByEmployeeIdMonthWise(@PathVariable("employeeId") String employeeId,
			@PathVariable("orgId") String orgId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<UserKpiLobMapper> mapper = employeeService.getKpiTargetByLobByEmployeeIdMonthWise(employeeId, orgId);
			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/employee/equipment")
	public ResponseEntity<?> createUserEquipment(@RequestBody List<UserEquipmentMapper> requestMapper,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<UserEquipmentMapper> responseMapper = employeeService.createUserEquipment(requestMapper);
			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/employee/equipment/{userId}")
	public ResponseEntity<?> getEmployeeEquipmentByUserId(@PathVariable("userId") String userId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<UserEquipmentMapper> mapper = employeeService.getEmployeeEquipmentByUserId(userId);
			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/employee/email/link/save")
	public ResponseEntity<?> linkEmailInUser(@RequestBody EmployeeEmailLinkMapper requestMapper,
			@RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			requestMapper.setOrgId(jwtTokenUtil.getOrgIdFromToken(authToken));
			requestMapper.setUserId(jwtTokenUtil.getUserIdFromToken(authToken));
			HashMap responseMapper = employeeService.linkEmailInUser(requestMapper);
			return new ResponseEntity<>(responseMapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/employee/multy-org-access-ind/{employeeId}/{multyOrgAccessInd}")
	public ResponseEntity<?> UpdateMultyOrgAccseeInd(@PathVariable("employeeId") String employeeId,
			@PathVariable("multyOrgAccessInd") boolean multyOrgAccessInd,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) throws Exception {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			EmployeeTableMapper mapper = employeeService.UpdateMultyOrgAccessInd(employeeId, multyOrgAccessInd);

			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/employee/delete/notes")
	public ResponseEntity<?> deleteNotes(@RequestBody NotesMapper notesMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String notesMapperr = employeeService.deleteNotes(notesMapper);

			return new ResponseEntity<>(notesMapperr, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping("/api/v1/employee/active/user/type/drop-down/{orgId}/{type}")
	public ResponseEntity<List<?>> getEmployeeListByOrgIdAndTypeForDropDown(@PathVariable("orgId") String orgId,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<EmployeeShortMapper> employeeDetailsList = employeeService
					.getEmployeeListByOrgIdAndTypeForDropDown(orgId,type);
			List<EmployeeShortMapper> list = employeeDetailsList.stream()
					.sorted((o1, o2) -> o1.getEmpName().compareToIgnoreCase(o2.getEmpName()))
					.collect(Collectors.toList());
			// Collections.sort(employeeDetailsList, (m1, m2) ->
			// m2.getCreationDate().compareTo(m1.getCreationDate()));
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/employee/search/alltype/{name}/{type}")
	public ResponseEntity<?> getInvestorByNameAndType(@PathVariable("name") String name,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization) {
		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String authToken = authorization.replace(TOKEN_PREFIX, "");
			String userId = jwtTokenUtil.getUserIdFromToken(authToken);
			String orgId = jwtTokenUtil.getOrgIdFromToken(authToken);
			List<EmployeeTableMapper> list = new ArrayList<>();
			if (type.equalsIgnoreCase("All")) {
				list = employeeService.getAllEmployeeDetailsByName(name, orgId);
				if (null != list && !list.isEmpty()) {
					return new ResponseEntity<>(list, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("Data not Found", HttpStatus.OK);
				}
			}else {
				list = employeeService.getActiveEmployeeDetailsByName(name, orgId);
				if (null != list && !list.isEmpty()) {
					return new ResponseEntity<>(list, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("Data not Found", HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/employee/notes/{type}/{uniqueId}")
	public ResponseEntity<?> getNotesByTypeAndUniqueId(@PathVariable String type, @PathVariable String uniqueId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			List<NotesMapper> notesMapper = employeeService.getNotesByTypeAndUniqueId(type,uniqueId);
			if (null != notesMapper && !notesMapper.isEmpty()) {
				Collections.sort(notesMapper,
						(NotesMapper m1, NotesMapper m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
				return new ResponseEntity<>(notesMapper, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(notesMapper, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/api/v1/employee/active/user/{departmentId}/{roletypetypeId}")
	public ResponseEntity<List<?>> getEmployeeListByDepartmentIdAndRoletypeId(@PathVariable("departmentId") String departmentId,
			@PathVariable("roletypetypeId") String roletypetypeId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<EmployeeShortMapper> employeeDetailsList = employeeService
					.getEmployeeListByDepartmentIdAndRoletypeId(departmentId,roletypetypeId);
			List<EmployeeShortMapper> list = employeeDetailsList.stream()
					.sorted((o1, o2) -> o1.getEmpName().compareToIgnoreCase(o2.getEmpName()))
					.collect(Collectors.toList());
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	@GetMapping("/api/v1/employee/getLocation/{userId}")
	public ResponseEntity<?> getLocationDetailsListByUserId(@RequestHeader("Authorization") String authorization,HttpServletRequest request, @PathVariable("userId") String userId) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			AddressMapper resultList = employeeService.getLocationAddress(userId);

			return ResponseEntity.ok(resultList);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
