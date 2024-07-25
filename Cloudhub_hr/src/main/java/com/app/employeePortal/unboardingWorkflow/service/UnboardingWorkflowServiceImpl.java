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
import com.app.employeePortal.unboardingWorkflow.entity.UnboardingStages;
import com.app.employeePortal.unboardingWorkflow.entity.UnboardingWorkflowDetails;
import com.app.employeePortal.unboardingWorkflow.mapper.UnboardingStagesRequestMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.UnboardingStagesResponseMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.UnboardingWfReqMapper;
import com.app.employeePortal.unboardingWorkflow.mapper.UnboardingWfRespMapper;
import com.app.employeePortal.unboardingWorkflow.repository.UnboardingStagesRepository;
import com.app.employeePortal.unboardingWorkflow.repository.UnboardingWorkflowDetailsRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class UnboardingWorkflowServiceImpl implements UnboardingWorkflowService {

	@Autowired
	UnboardingWorkflowDetailsRepository unboardingWorkflowDetailsRepository;
	@Autowired
	UnboardingStagesRepository unboardingStagesRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EmployeeService employeeService;

	@Override
	public UnboardingWfRespMapper saveUnboardingWorkflow(UnboardingWfReqMapper requestMapper) {
		ArrayList<String> names = new ArrayList<String>(Arrays.asList("Won", "Lost"));
		String Ids = null;
		UnboardingWorkflowDetails db = new UnboardingWorkflowDetails();
		db.setUnboardingWorkflowDetailsId(requestMapper.getUnboardingWorkflowDetailsId());
		db.setWorkflowName(requestMapper.getWorkflowName());
		db.setLiveInd(true);
		db.setOrgId(requestMapper.getOrgId());
		db.setPublishInd(requestMapper.isPublishInd());
		db.setUserId(requestMapper.getUserId());
		db.setCreationDate(new Date());
		db.setUpdationDate(new Date());
		Ids = unboardingWorkflowDetailsRepository.save(db).getUnboardingWorkflowDetailsId();
		for (int i = 0; i < names.size(); i++) {
			UnboardingStages unboardingStages = new UnboardingStages();
			unboardingStages.setStageName(names.get(i));
			unboardingStages.setLiveInd(true);
			unboardingStages.setUserId(requestMapper.getUserId());
			unboardingStages.setOrgId(requestMapper.getOrgId());
			unboardingStages.setPublishInd(false);
			unboardingStages.setCreationDate(new Date());
			unboardingStages.setUnboardingWorkflowDetailsId(Ids);
			if (names.get(i) == "Won") {
				unboardingStages.setProbability(100);
			} else {
				unboardingStages.setProbability(0);
			}
			unboardingStagesRepository.save(unboardingStages).getUnboardingStagesId();
		}
		UnboardingWfRespMapper resultMapper=getUnboardingWorkflowDetails(Ids);
		return resultMapper;
	}

	@Override
	public List<UnboardingWfRespMapper> getUnboardingWorkflowListByOrgId(String orgId) {
		List<UnboardingWorkflowDetails> list = unboardingWorkflowDetailsRepository.findByOrgIdAndLiveInd(orgId, true);
		List<UnboardingWfRespMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				UnboardingWfRespMapper responseMapper = new UnboardingWfRespMapper();
				responseMapper.setUnboardingWorkflowDetailsId(li.getUnboardingWorkflowDetailsId());
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
	public UnboardingWfRespMapper updateUnboardingWorkflowDetails(String unboardingWorkflowDetailsId,
			UnboardingWfReqMapper requestMapper) {
		UnboardingWorkflowDetails db = unboardingWorkflowDetailsRepository
				.getUnboardingWorkflowDetailsByUnboardingWorkflowDetailsId(unboardingWorkflowDetailsId);
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
			unboardingWorkflowDetailsRepository.save(db);
		}
		UnboardingWfRespMapper responseMapper = getUnboardingWorkflowDetails(db.getUnboardingWorkflowDetailsId());
		return responseMapper;
	}

	private UnboardingWfRespMapper getUnboardingWorkflowDetails(String unboardingWorkflowDetailsId) {
		UnboardingWorkflowDetails db = unboardingWorkflowDetailsRepository
				.getUnboardingWorkflowDetailsByUnboardingWorkflowDetailsId(unboardingWorkflowDetailsId);
		UnboardingWfRespMapper responseMapper = new UnboardingWfRespMapper();
		if (null != db) {
			responseMapper.setUnboardingWorkflowDetailsId(unboardingWorkflowDetailsId);
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
	public void deleteUnboardingWorkflow(String unboardingWorkflowDetailsId) {
		if (null != unboardingWorkflowDetailsId) {
			UnboardingWorkflowDetails db = unboardingWorkflowDetailsRepository
					.getUnboardingWorkflowDetailsByUnboardingWorkflowDetailsId(unboardingWorkflowDetailsId);
			if (null != db) {
				db.setUpdationDate(new Date());
				db.setLiveInd(false);
				unboardingWorkflowDetailsRepository.save(db);
			}
		}
	}

	@Override
	public boolean stageExistsByWeightage(double probability, String unboardingWorkflowDetailsId) {
		List<UnboardingStages> list = unboardingStagesRepository
				.findByUnboardingWorkflowDetailsIdAndLiveInd(unboardingWorkflowDetailsId, true);
		if (null != list && !list.isEmpty()) {
			for (UnboardingStages unboardingStages : list) {
				if (unboardingStages.getProbability() == probability) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public UnboardingStagesResponseMapper saveOpportunityStages(UnboardingStagesRequestMapper requestMapper) {
		String Ids = null;
		UnboardingStages db = new UnboardingStages();
		db.setUnboardingStagesId(requestMapper.getUnboardingStagesId());
		db.setStageName(requestMapper.getStageName());
		db.setLiveInd(true);
		db.setUserId(requestMapper.getUserId());
		db.setOrgId(requestMapper.getOrgId());
		db.setPublishInd(requestMapper.isPublishInd());
		db.setProbability(requestMapper.getProbability());
		db.setDays(requestMapper.getDays());
		// newOpportunityStages.setResponsible(opportunityStagesMapper.getResponsible());
		db.setCreationDate(new Date());
		db.setUnboardingWorkflowDetailsId(requestMapper.getUnboardingWorkflowDetailsId());
		db.setUpdationDate(new Date());
		db.setUpdatedBy(requestMapper.getUserId());
		db.setUserId(requestMapper.getUserId());
		Ids = unboardingStagesRepository.save(db).getUnboardingStagesId();
		UnboardingStagesResponseMapper resultMapper=getUnboardingStagesDetails(Ids);
		return resultMapper;
	}

	@Override
	public List<UnboardingStagesResponseMapper> getStagesByUnboardingWorkflowId(String unboardingWorkflowId) {
		List<UnboardingStages> list = unboardingStagesRepository
				.findByUnboardingWorkflowDetailsIdAndLiveInd(unboardingWorkflowId, true);
		List<UnboardingStagesResponseMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				if (!li.getStageName().equalsIgnoreCase("Lost")) {
					if (!li.getStageName().equalsIgnoreCase("Won")) {
						UnboardingStagesResponseMapper responseMapper = new UnboardingStagesResponseMapper();
						responseMapper.setUnboardingStagesId(li.getUnboardingStagesId());
						responseMapper.setStageName(li.getStageName());
						responseMapper.setLiveInd(li.isLiveInd());
						responseMapper.setPublishInd(li.isPublishInd());
						responseMapper.setOrgId(li.getOrgId());
						responseMapper.setUserId(li.getUserId());
						responseMapper.setProbability(li.getProbability());
						responseMapper.setDays(li.getDays());
						// opportunityStagesMapper.setResponsible(li.getResponsible());
						responseMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
						responseMapper.setUnboardingWorkflowDetailsId(li.getUnboardingWorkflowDetailsId());
						responseMapper.setUpdationDate(Utility.getISOFromDate(li.getUpdationDate()));
						mapperList.add(responseMapper);
						
					}
				}
				if (null != mapperList && !mapperList.isEmpty()) {

					Collections.sort(mapperList,
							(UnboardingStagesResponseMapper c1, UnboardingStagesResponseMapper c2) -> Double
									.compare(c1.getProbability(), c2.getProbability()));
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
	public UnboardingStagesResponseMapper updateUnboardingStages(String unboardingStagesId,
			UnboardingStagesRequestMapper requestMapper) {
		UnboardingStages db = unboardingStagesRepository.getUnboardingStagesByUnboardingStagesId(unboardingStagesId);
		if (null != db) {

			if (null != requestMapper.getStageName()) {
				db.setStageName(requestMapper.getStageName());
			}
			if (null != requestMapper.getOrgId()) {
				db.setOrgId(requestMapper.getOrgId());
			}
			if (0 != requestMapper.getProbability()) {
			db.setProbability(requestMapper.getProbability());
			}
			if (0 != requestMapper.getDays()) {
			db.setDays(requestMapper.getDays());
			}
			
			db.setUpdationDate(new Date());
			unboardingStagesRepository.save(db);
		}
		//UnboardingStagesResponseMapper responseMapper = getUnboardingStagesDetails(unboardingStagesId);
		return getUnboardingStagesDetails(unboardingStagesId);
	}

	private UnboardingStagesResponseMapper getUnboardingStagesDetails(String unboardingStagesId) {
		UnboardingStages unboardingStages = unboardingStagesRepository
				.getUnboardingStagesByUnboardingStagesId(unboardingStagesId);
		UnboardingStagesResponseMapper responseMapper = new UnboardingStagesResponseMapper();
		if (null != unboardingStages) {
			responseMapper.setUnboardingStagesId(unboardingStages.getUnboardingStagesId());
			responseMapper.setStageName(unboardingStages.getStageName());
			responseMapper.setLiveInd(unboardingStages.isLiveInd());
			responseMapper.setPublishInd(unboardingStages.isPublishInd());
			responseMapper.setOrgId(unboardingStages.getOrgId());
			responseMapper.setProbability(unboardingStages.getProbability());
			// opportunityStagesMapper.setResponsible(opportunityStages.getResponsible());
			responseMapper.setDays(unboardingStages.getDays());
			responseMapper.setCreationDate(Utility.getISOFromDate(unboardingStages.getCreationDate()));
			responseMapper.setUserId(unboardingStages.getUserId());
			responseMapper.setUnboardingWorkflowDetailsId(unboardingStages.getUnboardingWorkflowDetailsId());
			responseMapper.setUpdationDate(Utility.getISOFromDate(unboardingStages.getUpdationDate()));
			responseMapper.setName(employeeService.getEmployeeFullName(unboardingStages.getUpdatedBy()));
		}
		return responseMapper;
	}

	@Override
	public void deleteUnboardingStages(String unboardingStagesId) {
		if (null != unboardingStagesId) {
			UnboardingStages unboardingStages = unboardingStagesRepository
					.getUnboardingStagesByUnboardingStagesId(unboardingStagesId);
			if (null != unboardingStages) {
				unboardingStages.setUpdationDate(new Date());
				unboardingStages.setLiveInd(false);
				unboardingStagesRepository.save(unboardingStages);
			}
		}
	}

	@Override
	public List<UnboardingStagesResponseMapper> getStagesByOrgId(String orgId) {
		List<UnboardingStages> list = unboardingStagesRepository.findByOrgIdAndLiveInd(orgId, true);
		List<UnboardingStagesResponseMapper> mapperList = new ArrayList<>();
		System.out.println("opportunityStagesList+++++++++++++++++=========" + list.toString());
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				System.out.println("getOrgId+++++++++++++++++=========" + li.getOrgId());
				if (!li.getStageName().equalsIgnoreCase("Lost")) {
					if (!li.getStageName().equalsIgnoreCase("Won")) {
						System.out.println("getOrgId+++++++++++++++++=========" + li.getOrgId());
						UnboardingStagesResponseMapper opportunityStagesMapper = getUnboardingStagesDetails(
								li.getUnboardingStagesId());
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
	public UnboardingStagesResponseMapper updateUnboardingStagesPubliahInd(
			UnboardingStagesRequestMapper requestMapper) {
		UnboardingStagesResponseMapper resultMapper = null;
		if (null != requestMapper.getUnboardingStagesId()) {
			UnboardingStages db = unboardingStagesRepository
					.getUnboardingStagesByUnboardingStagesId(requestMapper.getUnboardingStagesId());
			if (null != db.getUnboardingStagesId()) {
				db.setPublishInd(requestMapper.isPublishInd());
				unboardingStagesRepository.save(db);
			}
			resultMapper = getUnboardingStagesDetails(requestMapper.getUnboardingStagesId());
		}
		return resultMapper;
	}

	@Override
	public UnboardingWfRespMapper updateUnboardingWorkflowDetailsPublishInd(UnboardingWfReqMapper requestMapper) {
		UnboardingWfRespMapper resultMapper = null;
		if (null != requestMapper.getUnboardingWorkflowDetailsId()) {
			UnboardingWorkflowDetails unboardingWorkflowDetails = unboardingWorkflowDetailsRepository
					.getUnboardingWorkflowDetailsByUnboardingWorkflowDetailsId(
							requestMapper.getUnboardingWorkflowDetailsId());
			if (null != unboardingWorkflowDetails) {
				unboardingWorkflowDetails.setPublishInd(requestMapper.isPublishInd());
				unboardingWorkflowDetailsRepository.save(unboardingWorkflowDetails);
			}
			resultMapper = getUnboardingWorkflowDetails(requestMapper.getUnboardingWorkflowDetailsId());
		}
		return resultMapper;
	}

	@Override
	public List<UnboardingWfRespMapper> getUnboardingWorkflowListByOrgIdForDropdown(String orgId) {
		List<UnboardingWorkflowDetails> list = unboardingWorkflowDetailsRepository
				.findByOrgIdAndLiveIndAndPublishInd(orgId, true, true);
		List<UnboardingWfRespMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				UnboardingWfRespMapper mapper = getUnboardingWorkflowDetails(li.getUnboardingWorkflowDetailsId());
				mapperList.add(mapper);

				return mapperList;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<UnboardingStagesResponseMapper> getStagesByOrgIdForDropdown(String orgId) {
		List<UnboardingStages> list = unboardingStagesRepository.findByOrgIdAndLiveIndAndPublishInd(orgId, true, true);
		List<UnboardingStagesResponseMapper> mapperList = new ArrayList<>();
		System.out.println("opportunityStagesList+++++++++++++++++=========" + list.toString());
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				System.out.println("getOrgId+++++++++++++++++=========" + li.getOrgId());
				if (!li.getStageName().equalsIgnoreCase("Lost")) {
					if (!li.getStageName().equalsIgnoreCase("Won")) {
						System.out.println("getOrgId+++++++++++++++++=========" + li.getOrgId());
						UnboardingStagesResponseMapper resultMapper = getUnboardingStagesDetails(
								li.getUnboardingStagesId());
						mapperList.add(resultMapper);
					}
				}
				return mapperList;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

}
