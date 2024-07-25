package com.app.employeePortal.repairWorkflow.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.repairWorkflow.entity.RepairStages;
import com.app.employeePortal.repairWorkflow.entity.RepairWorkflowDetails;
import com.app.employeePortal.repairWorkflow.mapper.RepairStagesRequestMapper;
import com.app.employeePortal.repairWorkflow.mapper.RepairStagesResponseMapper;
import com.app.employeePortal.repairWorkflow.mapper.RepairWorkflowRequestMapper;
import com.app.employeePortal.repairWorkflow.mapper.RepairWorkflowResponseMapper;
import com.app.employeePortal.repairWorkflow.repository.RepairStagesRepository;
import com.app.employeePortal.repairWorkflow.repository.RepairWorkflowDetailsRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class RepairWorkflowServiceImpl implements RepairWorkflowService {

	@Autowired
	RepairWorkflowDetailsRepository repairWorkflowDetailsRepository;

	@Autowired
	RepairStagesRepository repairStagesRepository;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public RepairWorkflowResponseMapper saveRepairWorkflow(RepairWorkflowRequestMapper requestMapper) {
		ArrayList<String> names = new ArrayList<String>(Arrays.asList("Won", "Lost"));
		String Ids = null;
		RepairWorkflowDetails db = new RepairWorkflowDetails();
		db.setWorkflowName(requestMapper.getWorkflowName());
		db.setLiveInd(true);
		db.setOrgId(requestMapper.getOrgId());
		db.setPublishInd(requestMapper.isPublishInd());
		db.setUserId(requestMapper.getUserId());
		db.setCreationDate(new Date());
		db.setUpdationDate(new Date());
		db.setUpdatedBy(requestMapper.getUserId());
		Ids = repairWorkflowDetailsRepository.save(db).getRepairWorkflowDetailsId();
		for (int i = 0; i < names.size(); i++) {
			RepairStages unboardingStages = new RepairStages();
			unboardingStages.setStageName(names.get(i));
			unboardingStages.setLiveInd(true);
			unboardingStages.setUserId(requestMapper.getUserId());
			unboardingStages.setOrgId(requestMapper.getOrgId());
			unboardingStages.setPublishInd(false);
			unboardingStages.setCreationDate(new Date());
			unboardingStages.setRepairWorkflowDetailsId(Ids);
			unboardingStages.setUpdatedBy(requestMapper.getUserId());
			unboardingStages.setUpdationDate(new Date());
			if (names.get(i) == "Won") {
				unboardingStages.setProbability(100);
			} else {
				unboardingStages.setProbability(0);
			}
			repairStagesRepository.save(unboardingStages).getRepairStagesId();
		}
		RepairWorkflowResponseMapper resultMapper = getRepairWorkflowDetails(Ids);
		return resultMapper;
	}

	private RepairWorkflowResponseMapper getRepairWorkflowDetails(String RepairWorkflowDetailsId) {
		RepairWorkflowDetails db = repairWorkflowDetailsRepository
				.getByRepairWorkflowDetailsId(RepairWorkflowDetailsId);
		RepairWorkflowResponseMapper responseMapper = new RepairWorkflowResponseMapper();
		if (null != db) {
			responseMapper.setRepairWorkflowDetailsId(RepairWorkflowDetailsId);
			responseMapper.setWorkflowName(db.getWorkflowName());
			responseMapper.setLiveInd(db.isLiveInd());
			responseMapper.setPublishInd(db.isPublishInd());
			responseMapper.setOrgId(db.getOrgId());
			responseMapper.setUserId(db.getUserId());
			responseMapper.setCreationDate(Utility.getISOFromDate(db.getCreationDate()));
			responseMapper.setUpdationDate(Utility.getISOFromDate(db.getUpdationDate()));
			responseMapper.setName(employeeService.getEmployeeFullName(db.getUpdatedBy()));
		}
		return responseMapper;
	}

	@Override
	public List<RepairWorkflowResponseMapper> getRepairWorkflowListByOrgId(String orgId) {
		List<RepairWorkflowDetails> list = repairWorkflowDetailsRepository.findByOrgIdAndLiveInd(orgId, true);
		List<RepairWorkflowResponseMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				RepairWorkflowResponseMapper responseMapper = new RepairWorkflowResponseMapper();
				responseMapper.setRepairWorkflowDetailsId(li.getRepairWorkflowDetailsId());
				responseMapper.setWorkflowName(li.getWorkflowName());
				responseMapper.setLiveInd(li.isLiveInd());
				responseMapper.setPublishInd(li.isPublishInd());
				responseMapper.setOrgId(li.getOrgId());
				responseMapper.setUserId(li.getUserId());
				responseMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
				mapperList.add(responseMapper);
				return mapperList;
			}).collect(Collectors.toList());

			Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			mapperList.get(0).setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(list.get(0).getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";
				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}
				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					mapperList.get(0).setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					mapperList.get(0).setName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		return mapperList;
	}

	@Override
	public RepairWorkflowResponseMapper updateRepairWorkflowDetails(String RepairWorkflowDetailsId,
			RepairWorkflowRequestMapper requestMapper) {
		RepairWorkflowDetails db = repairWorkflowDetailsRepository
				.getByRepairWorkflowDetailsId(RepairWorkflowDetailsId);
		if (null != db) {

			if (null != db.getWorkflowName()) {
				db.setWorkflowName(requestMapper.getWorkflowName());
			}
			if (null != requestMapper.getUserId()) {
				db.setUserId(requestMapper.getUserId());
			}
			if (null != requestMapper.getOrgId()) {
				db.setOrgId(requestMapper.getOrgId());
			}
			if (null != requestMapper.getUserId()) {
				db.setUpdatedBy(requestMapper.getUserId());
			}
			db.setUpdationDate(new Date());
			repairWorkflowDetailsRepository.save(db);
		}
		RepairWorkflowResponseMapper responseMapper = getRepairWorkflowDetails(RepairWorkflowDetailsId);
		return responseMapper;
	}

	@Override
	public void deleteRepairWorkflowDetails(String RepairWorkflowDetailsId) {
		RepairWorkflowDetails db = repairWorkflowDetailsRepository
				.getByRepairWorkflowDetailsId(RepairWorkflowDetailsId);
		if (null != db) {
			db.setUpdationDate(new Date());
			db.setLiveInd(false);
			repairWorkflowDetailsRepository.save(db);
		}
	}

	@Override
	public boolean stageExistsByWeightage(double probability, String RepairWorkflowDetailsId) {
		List<RepairStages> list = repairStagesRepository
				.findByRepairWorkflowDetailsIdAndLiveInd(RepairWorkflowDetailsId, true);
		if (null != list && !list.isEmpty()) {
			for (RepairStages RepairStages : list) {
				if (RepairStages.getProbability() == probability) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public RepairStagesResponseMapper saveRepairStages(RepairStagesRequestMapper requestMapper) {
		String Ids = null;
		RepairStages db = new RepairStages();

		db.setStageName(requestMapper.getStageName());
		db.setLiveInd(true);
		db.setUserId(requestMapper.getUserId());
		db.setOrgId(requestMapper.getOrgId());
		db.setPublishInd(requestMapper.isPublishInd());
		db.setProbability(requestMapper.getProbability());
		db.setDays(requestMapper.getDays());
		// newOpportunityStages.setResponsible(opportunityStagesMapper.getResponsible());
		db.setCreationDate(new Date());
		db.setRepairWorkflowDetailsId(requestMapper.getRepairWorkflowDetailsId());
		db.setUpdationDate(new Date());
		db.setUpdatedBy(requestMapper.getUserId());
		db.setUserId(requestMapper.getUserId());
		Ids = repairStagesRepository.save(db).getRepairStagesId();
		RepairStagesResponseMapper resultMapper = getRepairStagesDetails(Ids);
		return resultMapper;
	}

	private RepairStagesResponseMapper getRepairStagesDetails(String RepairStagesId) {
		RepairStages db = repairStagesRepository.getByRepairStagesId(RepairStagesId);
		System.out.println("supplierUnboardingStages ------" + db.getRepairWorkflowDetailsId());
		RepairStagesResponseMapper responseMapper = new RepairStagesResponseMapper();
		if (null != db) {
			System.out.println("supplierUnboardingStages22 ------" + db.getRepairWorkflowDetailsId());
			responseMapper.setRepairStagesId(db.getRepairStagesId());
			responseMapper.setStageName(db.getStageName());
			responseMapper.setLiveInd(db.isLiveInd());
			responseMapper.setPublishInd(db.isPublishInd());
			responseMapper.setOrgId(db.getOrgId());
			responseMapper.setProbability(db.getProbability());
			responseMapper.setDays(db.getDays());
			responseMapper.setCreationDate(Utility.getISOFromDate(db.getCreationDate()));
			responseMapper.setUserId(db.getUserId());
			responseMapper.setRepairWorkflowDetailsId(db.getRepairWorkflowDetailsId());
			responseMapper.setUpdationDate(Utility.getISOFromDate(db.getUpdationDate()));
			responseMapper.setName(employeeService.getEmployeeFullName(db.getUpdatedBy()));
			System.out.println("getEmployeeFullName ------" + employeeService.getEmployeeFullName(db.getUpdatedBy()));
		}
		return responseMapper;
	}

	@Override
	public List<RepairStagesResponseMapper> getStagesByRepairWorkflowDetailsId(
			String RepairWorkflowDetailsId) {
		List<RepairStages> list = repairStagesRepository
				.findByRepairWorkflowDetailsIdAndLiveInd(RepairWorkflowDetailsId, true);
		List<RepairStagesResponseMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				if (!li.getStageName().equalsIgnoreCase("Lost")) {
					if (!li.getStageName().equalsIgnoreCase("Won")) {
						RepairStagesResponseMapper responseMapper = new RepairStagesResponseMapper();
						responseMapper.setRepairStagesId(li.getRepairStagesId());
						responseMapper.setStageName(li.getStageName());
						responseMapper.setLiveInd(li.isLiveInd());
						responseMapper.setPublishInd(li.isPublishInd());
						responseMapper.setOrgId(li.getOrgId());
						responseMapper.setUserId(li.getUserId());
						responseMapper.setProbability(li.getProbability());
						responseMapper.setDays(li.getDays());
						// opportunityStagesMapper.setResponsible(li.getResponsible());
						responseMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
						responseMapper.setRepairWorkflowDetailsId(li.getRepairWorkflowDetailsId());
						responseMapper.setUpdationDate(Utility.getISOFromDate(li.getUpdationDate()));
						mapperList.add(responseMapper);
						if (null != mapperList && !mapperList.isEmpty()) {

							Collections.sort(mapperList,
									(RepairStagesResponseMapper c1, RepairStagesResponseMapper c2) -> Double
											.compare(c1.getProbability(), c2.getProbability()));
						}
					}
				}
				return mapperList;
			}).collect(Collectors.toList());
			Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			if (null != mapperList && !mapperList.isEmpty()) {
				mapperList.get(0).setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(list.get(0).getUserId());
				if (null != employeeDetails) {
					String middleName = " ";
					String lastName = "";
					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}
					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {
						middleName = employeeDetails.getMiddleName();
						mapperList.get(0).setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {
						mapperList.get(0).setName(employeeDetails.getFirstName() + " " + lastName);
					}
				}
			}
		}
		return mapperList;
	}

	@Override
	public RepairStagesResponseMapper updateRepairStagesId(String RepairStagesId,
			RepairStagesRequestMapper requestMapper) {
		RepairStages db = repairStagesRepository.getByRepairStagesId(RepairStagesId);
		if (null != db) {

			if (null != requestMapper.getStageName()) {
				db.setStageName(requestMapper.getStageName());
			}
			if (null != requestMapper.getOrgId()) {
				db.setOrgId(requestMapper.getOrgId());
			}
			db.setProbability(requestMapper.getProbability());
			db.setDays(requestMapper.getDays());
			db.setUpdationDate(new Date());
			repairStagesRepository.save(db);
		}
		RepairStagesResponseMapper responseMapper = getRepairStagesDetails(RepairStagesId);
		return responseMapper;
	}

	@Override
	public void deleteRepairStagesById(String RepairStagesId) {
		RepairStages db = repairStagesRepository.getByRepairStagesId(RepairStagesId);
		if (null != db) {
			db.setUpdationDate(new Date());
			db.setLiveInd(false);
			repairStagesRepository.save(db);
		}
	}

	@Override
	public RepairStagesResponseMapper updateRepairStagesPubliahInd(
			RepairStagesRequestMapper requestMapper) {
		RepairStagesResponseMapper resultMapper = null;
		if (null != requestMapper.getRepairStagesId()) {
			RepairStages db = repairStagesRepository
					.getByRepairStagesId(requestMapper.getRepairStagesId());
			if (null != db.getRepairStagesId()) {
				db.setPublishInd(requestMapper.isPublishInd());
				repairStagesRepository.save(db);
			}
			resultMapper = getRepairStagesDetails(requestMapper.getRepairStagesId());
		}
		return resultMapper;
	}

	@Override
	public RepairWorkflowResponseMapper updateRepairWorkflowDetailsPublishInd(
			RepairStagesRequestMapper requestMapper) {
		RepairWorkflowResponseMapper resultMapper = null;
		if (null != requestMapper.getRepairWorkflowDetailsId()) {
			RepairWorkflowDetails db = repairWorkflowDetailsRepository
					.getByRepairWorkflowDetailsId(requestMapper.getRepairWorkflowDetailsId());
			if (null != db) {
				db.setPublishInd(requestMapper.isPublishInd());
				repairWorkflowDetailsRepository.save(db);
			}
			resultMapper = getRepairWorkflowDetails(requestMapper.getRepairWorkflowDetailsId());
		}
		return resultMapper;
	}

	@Override
	public List<RepairWorkflowResponseMapper> getRepairWorkflowListByOrgIdForDropdown(String orgId) {
		List<RepairWorkflowDetails> list = repairWorkflowDetailsRepository
				.findByOrgIdAndLiveIndAndPublishInd(orgId, true, true);
		List<RepairWorkflowResponseMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				RepairWorkflowResponseMapper mapper = getRepairWorkflowDetails(
						li.getRepairWorkflowDetailsId());
				mapperList.add(mapper);
				return mapperList;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<RepairStagesResponseMapper> getRepairStagesByOrgIdForDropdown(String orgId) {
		List<RepairStages> list = repairStagesRepository.findByOrgIdAndLiveIndAndPublishInd(orgId, true, true);
		List<RepairStagesResponseMapper> mapperList = new ArrayList<>();
		System.out.println("opportunityStagesList+++++++++++++++++=========" + list.toString());
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				System.out.println("getOrgId111+++++++++++++++++=========" + li.getOrgId());
				if (!li.getStageName().equalsIgnoreCase("Lost")) {
					if (!li.getStageName().equalsIgnoreCase("Won")) {
						System.out.println("getOrgId222+++++++++++++++++=========" + li.getOrgId());
						RepairStagesResponseMapper resultMapper = getRepairStagesDetails(
								li.getRepairStagesId());
						mapperList.add(resultMapper);
					}
				}
				return mapperList;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

}
