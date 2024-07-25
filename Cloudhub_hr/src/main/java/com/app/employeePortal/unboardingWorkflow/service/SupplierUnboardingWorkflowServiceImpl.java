package com.app.employeePortal.unboardingWorkflow.service;

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
import com.app.employeePortal.unboardingWorkflow.entity.SupplierUnboardingStages;
import com.app.employeePortal.unboardingWorkflow.entity.SupplierUnboardingWorkflowDetails;
import com.app.employeePortal.unboardingWorkflow.mapper.SupplierUnboardingStagesRequestMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.SupplierUnboardingStagesResponseMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.SupplierUnboardingWfReqMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.SupplierUnboardingWfRespMapper;
import com.app.employeePortal.unboardingWorkflow.repository.SupplierUnboardingStagesRepository;
import com.app.employeePortal.unboardingWorkflow.repository.SupplierUnboardingWorkflowDetailsRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class SupplierUnboardingWorkflowServiceImpl implements SupplierUnboardingWorkflowService {

	@Autowired
	SupplierUnboardingWorkflowDetailsRepository supplierUnboardingWorkflowDetailsRepository;
	@Autowired
	SupplierUnboardingStagesRepository supplierUnboardingStagesRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EmployeeService employeeService;

	@Override
	public SupplierUnboardingWfRespMapper saveSupplierUnboardingWorkflow(SupplierUnboardingWfReqMapper requestMapper) {
		ArrayList<String> names = new ArrayList<String>(Arrays.asList("Won", "Lost"));
		String Ids = null;
		SupplierUnboardingWorkflowDetails db = new SupplierUnboardingWorkflowDetails();
//		db.setSupplierUnboardingWorkflowDetailsId(requestMapper.getSupplierUnboardingWorkflowDetailsId());
		db.setWorkflowName(requestMapper.getWorkflowName());
		db.setLiveInd(true);
		db.setOrgId(requestMapper.getOrgId());
		db.setPublishInd(requestMapper.isPublishInd());
		db.setUserId(requestMapper.getUserId());
		db.setCreationDate(new Date());
		db.setUpdationDate(new Date());
		db.setUpdatedBy(requestMapper.getUserId());
		Ids = supplierUnboardingWorkflowDetailsRepository.save(db).getSupplierUnboardingWorkflowDetailsId();
		for (int i = 0; i < names.size(); i++) {
			SupplierUnboardingStages unboardingStages = new SupplierUnboardingStages();
			unboardingStages.setStageName(names.get(i));
			unboardingStages.setLiveInd(true);
			unboardingStages.setUserId(requestMapper.getUserId());
			unboardingStages.setOrgId(requestMapper.getOrgId());
			unboardingStages.setPublishInd(false);
			unboardingStages.setCreationDate(new Date());
			unboardingStages.setSupplierUnboardingWorkflowDetailsId(Ids);
			unboardingStages.setUpdatedBy(requestMapper.getUserId());
			unboardingStages.setUpdationDate(new Date());
			if (names.get(i) == "Won") {
				unboardingStages.setProbability(100);
			} else {
				unboardingStages.setProbability(0);
			}
			supplierUnboardingStagesRepository.save(unboardingStages).getSupplierUnboardingStagesId();
		}
		SupplierUnboardingWfRespMapper resultMapper=getSupplierUnboardingWorkflowDetails(Ids);
		return resultMapper;
	}

	@Override
	public List<SupplierUnboardingWfRespMapper> getSupplierUnboardingWorkflowListByOrgId(String orgId) {
		List<SupplierUnboardingWorkflowDetails> list = supplierUnboardingWorkflowDetailsRepository
				.findByOrgIdAndLiveInd(orgId, true);
		List<SupplierUnboardingWfRespMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				SupplierUnboardingWfRespMapper responseMapper = new SupplierUnboardingWfRespMapper();
				responseMapper.setSupplierUnboardingWorkflowDetailsId(li.getSupplierUnboardingWorkflowDetailsId());
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
	public SupplierUnboardingWfRespMapper updateSupplierUnboardingWorkflowDetails(
			String SupplierUnboardingWorkflowDetailsId, SupplierUnboardingWfReqMapper requestMapper) {
		SupplierUnboardingWorkflowDetails db = supplierUnboardingWorkflowDetailsRepository
				.getBySupplierUnboardingWorkflowDetailsId(SupplierUnboardingWorkflowDetailsId);
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
			supplierUnboardingWorkflowDetailsRepository.save(db);
		}
		SupplierUnboardingWfRespMapper responseMapper = getSupplierUnboardingWorkflowDetails(
				db.getSupplierUnboardingWorkflowDetailsId());
		return responseMapper;
	}

	private SupplierUnboardingWfRespMapper getSupplierUnboardingWorkflowDetails(
			String SupplierUnboardingWorkflowDetailsId) {
		SupplierUnboardingWorkflowDetails db = supplierUnboardingWorkflowDetailsRepository
				.getBySupplierUnboardingWorkflowDetailsId(SupplierUnboardingWorkflowDetailsId);
		SupplierUnboardingWfRespMapper responseMapper = new SupplierUnboardingWfRespMapper();
		if (null != db) {
			responseMapper.setSupplierUnboardingWorkflowDetailsId(SupplierUnboardingWorkflowDetailsId);
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
	public void deleteSupplierUnboardingWorkflow(String supplierUnboardingWorkflowDetailsId) {
		if (null != supplierUnboardingWorkflowDetailsId) {
			SupplierUnboardingWorkflowDetails db = supplierUnboardingWorkflowDetailsRepository
					.getBySupplierUnboardingWorkflowDetailsId(supplierUnboardingWorkflowDetailsId);
			if (null != db) {
				db.setUpdationDate(new Date());
				db.setLiveInd(false);
				supplierUnboardingWorkflowDetailsRepository.save(db);
			}
		}
	}

	@Override
	public boolean stageExistsByWeightage(double probability, String supplierUnboardingWorkflowDetailsId) {
		List<SupplierUnboardingStages> list = supplierUnboardingStagesRepository
				.findBySupplierUnboardingWorkflowDetailsIdAndLiveInd(supplierUnboardingWorkflowDetailsId, true);
		if (null != list && !list.isEmpty()) {
			for (SupplierUnboardingStages unboardingStages : list) {
				if (unboardingStages.getProbability() == probability) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public SupplierUnboardingStagesResponseMapper saveSupplierUnboardingStages(SupplierUnboardingStagesRequestMapper requestMapper) {
		String Ids = null;
		SupplierUnboardingStages db = new SupplierUnboardingStages();

		db.setStageName(requestMapper.getStageName());
		db.setLiveInd(true);
		db.setUserId(requestMapper.getUserId());
		db.setOrgId(requestMapper.getOrgId());
		db.setPublishInd(requestMapper.isPublishInd());
		db.setProbability(requestMapper.getProbability());
		db.setDays(requestMapper.getDays());
		// newOpportunityStages.setResponsible(opportunityStagesMapper.getResponsible());
		db.setCreationDate(new Date());
		db.setSupplierUnboardingWorkflowDetailsId(requestMapper.getSupplierUnboardingWorkflowDetailsId());
		db.setUpdationDate(new Date());
		db.setUpdatedBy(requestMapper.getUserId());
		db.setUserId(requestMapper.getUserId());
		Ids = supplierUnboardingStagesRepository.save(db).getSupplierUnboardingStagesId();
		SupplierUnboardingStagesResponseMapper resultMapper = getSupplierUnboardingStagesDetails(Ids);
		return resultMapper;
	}

	@Override
	public List<SupplierUnboardingStagesResponseMapper> getStagesBySupplierUnboardingWorkflowId(
			String supplierUnboardingWorkflowId) {
		List<SupplierUnboardingStages> list = supplierUnboardingStagesRepository
				.findBySupplierUnboardingWorkflowDetailsIdAndLiveInd(supplierUnboardingWorkflowId, true);
		List<SupplierUnboardingStagesResponseMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				if (!li.getStageName().equalsIgnoreCase("Lost")) {
					if (!li.getStageName().equalsIgnoreCase("Won")) {
						SupplierUnboardingStagesResponseMapper responseMapper = new SupplierUnboardingStagesResponseMapper();
						responseMapper.setSupplierUnboardingStagesId(li.getSupplierUnboardingStagesId());
						responseMapper.setStageName(li.getStageName());
						responseMapper.setLiveInd(li.isLiveInd());
						responseMapper.setPublishInd(li.isPublishInd());
						responseMapper.setOrgId(li.getOrgId());
						responseMapper.setUserId(li.getUserId());
						responseMapper.setProbability(li.getProbability());
						responseMapper.setDays(li.getDays());
						// opportunityStagesMapper.setResponsible(li.getResponsible());
						responseMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
						responseMapper
								.setSupplierUnboardingWorkflowDetailsId(li.getSupplierUnboardingWorkflowDetailsId());
						responseMapper.setUpdationDate(Utility.getISOFromDate(li.getUpdationDate()));
						mapperList.add(responseMapper);
						if (null != mapperList && !mapperList.isEmpty()) {

							Collections.sort(mapperList,
									(SupplierUnboardingStagesResponseMapper c1,
											SupplierUnboardingStagesResponseMapper c2) -> Double
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
	public SupplierUnboardingStagesResponseMapper updateSupplierUnboardingStages(String supplierUnboardingStagesId,
			SupplierUnboardingStagesRequestMapper requestMapper) {
		SupplierUnboardingStages db = supplierUnboardingStagesRepository
				.getBySupplierUnboardingStagesId(supplierUnboardingStagesId);
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
			supplierUnboardingStagesRepository.save(db);
		}
		SupplierUnboardingStagesResponseMapper responseMapper = getSupplierUnboardingStagesDetails(
				supplierUnboardingStagesId);
		return responseMapper;
	}

	public SupplierUnboardingStagesResponseMapper getSupplierUnboardingStagesDetails(
			String supplierUnboardingStagesId) {
		SupplierUnboardingStages supplierUnboardingStages = supplierUnboardingStagesRepository
				.getBySupplierUnboardingStagesId(supplierUnboardingStagesId);
		System.out.println(
				"supplierUnboardingStages ------" + supplierUnboardingStages.getSupplierUnboardingWorkflowDetailsId());
		SupplierUnboardingStagesResponseMapper responseMapper = new SupplierUnboardingStagesResponseMapper();
		if (null != supplierUnboardingStages) {
			System.out.println("supplierUnboardingStages22 ------"
					+ supplierUnboardingStages.getSupplierUnboardingWorkflowDetailsId());
			responseMapper.setSupplierUnboardingStagesId(supplierUnboardingStages.getSupplierUnboardingStagesId());
			responseMapper.setStageName(supplierUnboardingStages.getStageName());
			responseMapper.setLiveInd(supplierUnboardingStages.isLiveInd());
			responseMapper.setPublishInd(supplierUnboardingStages.isPublishInd());
			responseMapper.setOrgId(supplierUnboardingStages.getOrgId());
			responseMapper.setProbability(supplierUnboardingStages.getProbability());
			responseMapper.setDays(supplierUnboardingStages.getDays());
			responseMapper.setCreationDate(Utility.getISOFromDate(supplierUnboardingStages.getCreationDate()));
			responseMapper.setUserId(supplierUnboardingStages.getUserId());
			responseMapper.setSupplierUnboardingWorkflowDetailsId(
					supplierUnboardingStages.getSupplierUnboardingWorkflowDetailsId());
			responseMapper.setUpdationDate(Utility.getISOFromDate(supplierUnboardingStages.getUpdationDate()));
			responseMapper.setName(employeeService.getEmployeeFullName(supplierUnboardingStages.getUpdatedBy()));
			System.out.println("getEmployeeFullName ------"
					+ employeeService.getEmployeeFullName(supplierUnboardingStages.getUpdatedBy()));

		}
		return responseMapper;
	}

	@Override
	public void deleteSupplierUnboardingStages(String supplierUnboardingStagesId) {
		if (null != supplierUnboardingStagesId) {
			SupplierUnboardingStages supplierUnboardingStages = supplierUnboardingStagesRepository
					.getBySupplierUnboardingStagesId(supplierUnboardingStagesId);
			if (null != supplierUnboardingStages) {
				supplierUnboardingStages.setUpdationDate(new Date());
				supplierUnboardingStages.setLiveInd(false);
				supplierUnboardingStagesRepository.save(supplierUnboardingStages);
			}
		}
	}

	@Override
	public List<SupplierUnboardingStagesResponseMapper> getStagesByOrgId(String orgId) {
		List<SupplierUnboardingStages> list = supplierUnboardingStagesRepository.findByOrgIdAndLiveInd(orgId, true);
		List<SupplierUnboardingStagesResponseMapper> mapperList = new ArrayList<>();
		System.out.println("opportunityStagesList+++++++++++++++++=========" + list.toString());
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				System.out.println("getOrgId111+++++++++++++++++=========" + li.getOrgId());
				if (!li.getStageName().equalsIgnoreCase("Lost")) {
					if (!li.getStageName().equalsIgnoreCase("Won")) {
						System.out.println("getOrgId222+++++++++++++++++=========" + li.getOrgId());
						SupplierUnboardingStagesResponseMapper opportunityStagesMapper = getSupplierUnboardingStagesDetails(
								li.getSupplierUnboardingStagesId());
						mapperList.add(opportunityStagesMapper);
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
	public SupplierUnboardingStagesResponseMapper updateSupplierUnboardingStagesPubliahInd(
			SupplierUnboardingStagesRequestMapper requestMapper) {
		SupplierUnboardingStagesResponseMapper resultMapper = null;
		if (null != requestMapper.getSupplierUnboardingStagesId()) {
			SupplierUnboardingStages db = supplierUnboardingStagesRepository
					.getBySupplierUnboardingStagesId(requestMapper.getSupplierUnboardingStagesId());
			if (null != db.getSupplierUnboardingStagesId()) {
				db.setPublishInd(requestMapper.isPublishInd());
				supplierUnboardingStagesRepository.save(db);
			}
			resultMapper = getSupplierUnboardingStagesDetails(requestMapper.getSupplierUnboardingStagesId());
		}
		return resultMapper;
	}

	@Override
	public SupplierUnboardingWfRespMapper updateSupplierUnboardingWorkflowDetailsPublishInd(
			SupplierUnboardingWfReqMapper requestMapper) {
		SupplierUnboardingWfRespMapper resultMapper = null;
		if (null != requestMapper.getSupplierUnboardingWorkflowDetailsId()) {
			SupplierUnboardingWorkflowDetails db = supplierUnboardingWorkflowDetailsRepository
					.getBySupplierUnboardingWorkflowDetailsId(requestMapper.getSupplierUnboardingWorkflowDetailsId());
			if (null != db) {
				db.setPublishInd(requestMapper.isPublishInd());
				supplierUnboardingWorkflowDetailsRepository.save(db);
			}
			resultMapper = getSupplierUnboardingWorkflowDetails(requestMapper.getSupplierUnboardingWorkflowDetailsId());
		}
		return resultMapper;
	}

	@Override
	public List<SupplierUnboardingWfRespMapper> getSupplierUnboardingWorkflowListByOrgIdForDropdown(String orgId) {
		List<SupplierUnboardingWorkflowDetails> list = supplierUnboardingWorkflowDetailsRepository
				.findByOrgIdAndLiveIndAndPublishInd(orgId, true, true);
		List<SupplierUnboardingWfRespMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				SupplierUnboardingWfRespMapper mapper = getSupplierUnboardingWorkflowDetails(li.getSupplierUnboardingWorkflowDetailsId());
				mapperList.add(mapper);

				return mapperList;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<SupplierUnboardingStagesResponseMapper> getStagesByOrgIdForDropdown(String orgId) {
		List<SupplierUnboardingStages> list = supplierUnboardingStagesRepository.findByOrgIdAndLiveIndAndPublishInd(orgId, true, true);
		List<SupplierUnboardingStagesResponseMapper> mapperList = new ArrayList<>();
		System.out.println("opportunityStagesList+++++++++++++++++=========" + list.toString());
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				System.out.println("getOrgId111+++++++++++++++++=========" + li.getOrgId());
				if (!li.getStageName().equalsIgnoreCase("Lost")) {
					if (!li.getStageName().equalsIgnoreCase("Won")) {
						System.out.println("getOrgId222+++++++++++++++++=========" + li.getOrgId());
						SupplierUnboardingStagesResponseMapper resultMapper = getSupplierUnboardingStagesDetails(
								li.getSupplierUnboardingStagesId());
						mapperList.add(resultMapper);
					}
				}
				return mapperList;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

}
