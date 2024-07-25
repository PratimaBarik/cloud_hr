package com.app.employeePortal.opportunityWorkflow.service;

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
import com.app.employeePortal.opportunityWorkflow.entity.OpportunityStages;
import com.app.employeePortal.opportunityWorkflow.entity.OpportunityStagesDelete;
import com.app.employeePortal.opportunityWorkflow.entity.OpportunityWorkflowDetails;
import com.app.employeePortal.opportunityWorkflow.entity.OpportunityWorkflowDetailsDelete;
import com.app.employeePortal.opportunityWorkflow.mapper.OpportunityStagesMapper;
import com.app.employeePortal.opportunityWorkflow.mapper.OpportunityWorkflowMapper;
import com.app.employeePortal.opportunityWorkflow.repository.OpportunityStagesDeleteRepository;
import com.app.employeePortal.opportunityWorkflow.repository.OpportunityStagesRepository;
import com.app.employeePortal.opportunityWorkflow.repository.OpportunityWorkflowDetailsDeleteRepository;
import com.app.employeePortal.opportunityWorkflow.repository.OpportunityWorkflowDetailsRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class OpportunityWorkflowServiceImpl implements OpportunityWorkflowService {

	@Autowired
	OpportunityWorkflowDetailsRepository opportunityWorkflowDetailsRepository;
	@Autowired
	OpportunityStagesRepository opportunityStagesRepository;

	@Autowired
	OpportunityWorkflowDetailsDeleteRepository opportunityWorkflowDetailsDeleteRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	OpportunityStagesDeleteRepository opportunityStagesDeleteRepository;
	@Autowired 
	EmployeeService employeeService;

	@Override
	public OpportunityWorkflowMapper saveOpportunityWorkflow(OpportunityWorkflowMapper opportunityWorkflowMapper) {

		ArrayList<String> names = new ArrayList<String>(Arrays.asList("Won", "Lost"));

		String Ids = null;
		OpportunityWorkflowDetails newOpportunityWorkflowDetails = new OpportunityWorkflowDetails();
		newOpportunityWorkflowDetails
				.setOpportunityWorkflowDetailsId(opportunityWorkflowMapper.getOpportunityWorkflowDetailsId());
		newOpportunityWorkflowDetails.setWorkflowName(opportunityWorkflowMapper.getWorkflowName());
		newOpportunityWorkflowDetails.setLiveInd(true);
		newOpportunityWorkflowDetails.setOrgId(opportunityWorkflowMapper.getOrgId());
		newOpportunityWorkflowDetails.setPublishInd(opportunityWorkflowMapper.isPublishInd());
		newOpportunityWorkflowDetails.setUser_id(opportunityWorkflowMapper.getUserId());
		newOpportunityWorkflowDetails.setCreationDate(new Date());
		Ids = opportunityWorkflowDetailsRepository.save(newOpportunityWorkflowDetails)
				.getOpportunityWorkflowDetailsId();

		OpportunityWorkflowDetailsDelete opportunityWorkflowDetailsDelete = new OpportunityWorkflowDetailsDelete();
		opportunityWorkflowDetailsDelete.setOrgId(opportunityWorkflowMapper.getOrgId());
		opportunityWorkflowDetailsDelete.setOpportunityWorkflowDetailsId(Ids);
		opportunityWorkflowDetailsDelete.setUpdationDate(new Date());
		opportunityWorkflowDetailsDelete.setUserId(opportunityWorkflowMapper.getUserId());
		opportunityWorkflowDetailsDeleteRepository.save(opportunityWorkflowDetailsDelete);
		for (int i = 0; i < names.size(); i++) {

			OpportunityStages newOpportunityStages = new OpportunityStages();

			newOpportunityStages.setStageName(names.get(i));
			newOpportunityStages.setLiveInd(true);
			newOpportunityStages.setUserId(opportunityWorkflowMapper.getUserId());
			newOpportunityStages.setOrgId(opportunityWorkflowMapper.getOrgId());
			newOpportunityStages.setPublishInd(false);
			newOpportunityStages.setCreationDate(new Date());
			newOpportunityStages.setOpportunityWorkflowDetailsId(Ids);

			if (names.get(i) == "Won") {

				newOpportunityStages.setProbability(100);
			} else {

				newOpportunityStages.setProbability(0);
			}
			opportunityStagesRepository.save(newOpportunityStages).getOpportunityStagesId();
		}
		OpportunityWorkflowMapper resultMapper = getOpportunityWorkflowDetails(Ids);
		return resultMapper;
	}

	@Override
	public List<OpportunityWorkflowMapper> getWorkflowListByOrgId(String orgId) {
		List<OpportunityWorkflowDetails> opportunityWorkflowDetailsList = opportunityWorkflowDetailsRepository
				.findByOrgIdAndLiveInd(orgId, true);
		List<OpportunityWorkflowMapper> mapperList = new ArrayList<>();
		if (null != opportunityWorkflowDetailsList && !opportunityWorkflowDetailsList.isEmpty()) {

			opportunityWorkflowDetailsList.stream().map(li -> {

				OpportunityWorkflowMapper opportunityWorkflowMapper = new OpportunityWorkflowMapper();

				opportunityWorkflowMapper.setOpportunityWorkflowDetailsId(li.getOpportunityWorkflowDetailsId());
				opportunityWorkflowMapper.setWorkflowName(li.getWorkflowName());
				opportunityWorkflowMapper.setLiveInd(li.isLiveInd());
				opportunityWorkflowMapper.setPublishInd(li.isPublishInd());
				opportunityWorkflowMapper.setOrgId(li.getOrgId());
				opportunityWorkflowMapper.setUserId(li.getUser_id());
				opportunityWorkflowMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));

				mapperList.add(opportunityWorkflowMapper);

				return mapperList;
			}).collect(Collectors.toList());

		}

		List<OpportunityWorkflowDetailsDelete> opportunityWorkflowDetailsDelete = opportunityWorkflowDetailsDeleteRepository
				.findByOrgId(orgId);
		if (null != opportunityWorkflowDetailsDelete && !opportunityWorkflowDetailsDelete.isEmpty()) {
			Collections.sort(opportunityWorkflowDetailsDelete,
					(p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			mapperList.get(0)
					.setUpdationDate(Utility.getISOFromDate(opportunityWorkflowDetailsDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeByUserId(opportunityWorkflowDetailsDelete.get(0).getUserId());
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
	public OpportunityWorkflowMapper updateOpportunityWorkflowDetails(String opportunityWorkflowDetailsId,
			OpportunityWorkflowMapper opportunityWorkflowMapper) {
		OpportunityWorkflowDetails opportunityWorkflowDetails = opportunityWorkflowDetailsRepository
				.getOpportunityWorkflowDetailsByOpportunityWorkflowDetailsId(opportunityWorkflowDetailsId);
		if (null != opportunityWorkflowDetails) {

			if (null != opportunityWorkflowMapper.getWorkflowName()) {
				opportunityWorkflowDetails.setWorkflowName(opportunityWorkflowMapper.getWorkflowName());
			}
			if (null != opportunityWorkflowMapper.getUserId()) {
				opportunityWorkflowDetails.setUser_id(opportunityWorkflowMapper.getUserId());
			}
			if (null != opportunityWorkflowMapper.getOrgId()) {
				opportunityWorkflowDetails.setOrgId(opportunityWorkflowMapper.getOrgId());
			}

			opportunityWorkflowDetailsRepository.save(opportunityWorkflowDetails);

			OpportunityWorkflowDetailsDelete opportunityWorkflowDetailsDelete = opportunityWorkflowDetailsDeleteRepository
					.findByOpportunityWorkflowDetailsId(opportunityWorkflowDetailsId);
			if (null != opportunityWorkflowDetailsDelete) {
				if (null != opportunityWorkflowMapper.getUserId()) {
					opportunityWorkflowDetailsDelete.setUserId(opportunityWorkflowMapper.getUserId());
				}
				if (null != opportunityWorkflowMapper.getOrgId()) {
					opportunityWorkflowDetailsDelete.setOrgId(opportunityWorkflowMapper.getOrgId());
				}
				opportunityWorkflowDetailsDelete.setUpdationDate(new Date());
				opportunityWorkflowDetailsDeleteRepository.save(opportunityWorkflowDetailsDelete);
			}
		}
		OpportunityWorkflowMapper opportunityWorkflowMapperr = getOpportunityWorkflowDetails(
				opportunityWorkflowDetails.getOpportunityWorkflowDetailsId());
		return opportunityWorkflowMapperr;
	}

	private OpportunityWorkflowMapper getOpportunityWorkflowDetails(String opportunityWorkflowDetailsId) {
		OpportunityWorkflowDetails opportunityWorkflowDetails = opportunityWorkflowDetailsRepository
				.getOpportunityWorkflowDetailsByOpportunityWorkflowDetailsId(opportunityWorkflowDetailsId);
		OpportunityWorkflowMapper opportunityWorkflowMapper = new OpportunityWorkflowMapper();
		if (null != opportunityWorkflowDetails) {
			opportunityWorkflowMapper
					.setOpportunityWorkflowDetailsId(opportunityWorkflowDetails.getOpportunityWorkflowDetailsId());
			opportunityWorkflowMapper.setWorkflowName(opportunityWorkflowDetails.getWorkflowName());
			opportunityWorkflowMapper.setLiveInd(opportunityWorkflowDetails.isLiveInd());
			opportunityWorkflowMapper.setPublishInd(opportunityWorkflowDetails.isPublishInd());
			opportunityWorkflowMapper.setOrgId(opportunityWorkflowDetails.getOrgId());
			opportunityWorkflowMapper.setUserId(opportunityWorkflowDetails.getUser_id());
			opportunityWorkflowMapper
					.setCreationDate(Utility.getISOFromDate(opportunityWorkflowDetails.getCreationDate()));
			OpportunityWorkflowDetailsDelete opportunityWorkflowDelete=opportunityWorkflowDetailsDeleteRepository.findByOpportunityWorkflowDetailsId(opportunityWorkflowDetailsId);
			if(null != opportunityWorkflowDelete) {
				opportunityWorkflowMapper.setUpdationDate(Utility.getISOFromDate(opportunityWorkflowDelete.getUpdationDate()));
				opportunityWorkflowMapper.setName(employeeService.getEmployeeFullName(opportunityWorkflowDelete.getUserId()));

			}
		}
		return opportunityWorkflowMapper;
	}

	@Override
	public void deleteOpportunityWorkflowById(String opportunityWorkflowDetailsId) {
		if (null != opportunityWorkflowDetailsId) {
			OpportunityWorkflowDetails opportunityWorkflowDetails = opportunityWorkflowDetailsRepository
					.getOpportunityWorkflowDetailsByOpportunityWorkflowDetailsId(opportunityWorkflowDetailsId);

			if (null != opportunityWorkflowDetails) {

				OpportunityWorkflowDetailsDelete opportunityWorkflowDetailsDelete = opportunityWorkflowDetailsDeleteRepository
						.findByOpportunityWorkflowDetailsId(opportunityWorkflowDetailsId);
				if (null != opportunityWorkflowDetailsDelete) {
					opportunityWorkflowDetailsDelete.setUpdationDate(new Date());
					opportunityWorkflowDetailsDeleteRepository.save(opportunityWorkflowDetailsDelete);
				}


				// opportunityWorkflowDetails.setLiveInd(false);
				//opportunityWorkflowDetailsRepository.delete(opportunityWorkflowDetails);

				
				opportunityWorkflowDetails.setLiveInd(false);
				opportunityWorkflowDetailsRepository.save(opportunityWorkflowDetails);

			}

		}

	}

	@Override
	public OpportunityStagesMapper saveOpportunityStages(OpportunityStagesMapper opportunityStagesMapper) {
		String Ids = null;
		OpportunityStages newOpportunityStages = new OpportunityStages();
		newOpportunityStages.setOpportunityStagesId(opportunityStagesMapper.getOpportunityStagesId());
		newOpportunityStages.setStageName(opportunityStagesMapper.getStageName());
		newOpportunityStages.setLiveInd(true);
		newOpportunityStages.setUserId(opportunityStagesMapper.getUserId());
		newOpportunityStages.setOrgId(opportunityStagesMapper.getOrgId());
		newOpportunityStages.setPublishInd(opportunityStagesMapper.isPublishInd());
		newOpportunityStages.setProbability(opportunityStagesMapper.getProbability());
		newOpportunityStages.setDays(opportunityStagesMapper.getDays());
		// newOpportunityStages.setResponsible(opportunityStagesMapper.getResponsible());
		newOpportunityStages.setCreationDate(new Date());
		newOpportunityStages.setOpportunityWorkflowDetailsId(opportunityStagesMapper.getOpportunityWorkflowDetailsId());
		Ids = opportunityStagesRepository.save(newOpportunityStages).getOpportunityStagesId();

		OpportunityStagesDelete opportunityStagesDelete = new OpportunityStagesDelete();
		opportunityStagesDelete.setOrgId(opportunityStagesMapper.getOrgId());
		opportunityStagesDelete.setOpportunityStagesId(Ids);
		opportunityStagesDelete.setUpdationDate(new Date());
		opportunityStagesDelete.setUserId(opportunityStagesMapper.getUserId());

		opportunityStagesDeleteRepository.save(opportunityStagesDelete);
		OpportunityStagesMapper resultMapper= getOpportunityStagesDetails(Ids);
		return resultMapper;

	}

	@Override
	public List<OpportunityStagesMapper> getStagesByOppworkFlowId(String oppworkFlowId) {
		List<OpportunityStages> opportunityStagesList = opportunityStagesRepository
				.findByOpportunityWorkflowDetailsIdAndLiveInd(oppworkFlowId, true);
		List<OpportunityStagesMapper> mapperList = new ArrayList<>();
		if (null != opportunityStagesList && !opportunityStagesList.isEmpty()) {

			opportunityStagesList.stream().map(li -> {
				
				if(!li.getStageName().equalsIgnoreCase("Lost")) {
					if(!li.getStageName().equalsIgnoreCase("Won")) {

				OpportunityStagesMapper opportunityStagesMapper = new OpportunityStagesMapper();

				opportunityStagesMapper.setOpportunityStagesId(li.getOpportunityStagesId());
				opportunityStagesMapper.setStageName(li.getStageName());
				opportunityStagesMapper.setLiveInd(li.isLiveInd());
				opportunityStagesMapper.setPublishInd(li.isPublishInd());
				opportunityStagesMapper.setOrgId(li.getOrgId());
				opportunityStagesMapper.setUserId(li.getUserId());
				opportunityStagesMapper.setProbability(li.getProbability());
				opportunityStagesMapper.setDays(li.getDays());
				// opportunityStagesMapper.setResponsible(li.getResponsible());
				opportunityStagesMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
				opportunityStagesMapper.setOpportunityWorkflowDetailsId(li.getOpportunityWorkflowDetailsId());

				mapperList.add(opportunityStagesMapper);

				if (null != mapperList && !mapperList.isEmpty()) {

					Collections.sort(mapperList, (OpportunityStagesMapper c1, OpportunityStagesMapper c2) -> Double
							.compare(c1.getProbability(), c2.getProbability()));

				}

					}
				}

				return mapperList;
			}).collect(Collectors.toList());

		}
		
		List<OpportunityStagesDelete> opportunityStagesDelete = opportunityStagesDeleteRepository.findByOpportunityWorkflowDetailsId(oppworkFlowId);
		if (null != opportunityStagesDelete && !opportunityStagesDelete.isEmpty()) {
			Collections.sort(opportunityStagesDelete,
					( p1,p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			
			mapperList.get(0).setUpdationDate(Utility.getISOFromDate(opportunityStagesDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(opportunityStagesDelete.get(0).getUserId());
			if(null!=employeeDetails) {
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
	public OpportunityStagesMapper updateOpportunityStages(String opportunityStagesId,
			OpportunityStagesMapper opportunityStagesMapper) {
		OpportunityStages opportunityStages = opportunityStagesRepository
				.getOpportunityStagesByOpportunityStagesId(opportunityStagesId);
		if (null != opportunityStages) {

			if (null != opportunityStagesMapper.getStageName()) {
				opportunityStages.setStageName(opportunityStagesMapper.getStageName());
			}
			if (null != opportunityStagesMapper.getOrgId()) {
				opportunityStages.setOrgId(opportunityStagesMapper.getOrgId());
			}

			opportunityStages.setProbability(opportunityStagesMapper.getProbability());
			opportunityStages.setDays(opportunityStagesMapper.getDays());

			opportunityStagesRepository.save(opportunityStages);

			OpportunityStagesDelete opportunityStagesDelete = opportunityStagesDeleteRepository
					.findByOpportunityStagesId(opportunityStagesId);

			if (null != opportunityStagesMapper.getOrgId()) {
				opportunityStages.setOrgId(opportunityStagesMapper.getOrgId());
			}
			opportunityStagesDelete.setUpdationDate(new Date());
			opportunityStagesDeleteRepository.save(opportunityStagesDelete);

		}
		OpportunityStagesMapper opportunityStagesMapperr = getOpportunityStagesDetails(
				opportunityStages.getOpportunityStagesId());
		return opportunityStagesMapperr;
	}

	private OpportunityStagesMapper getOpportunityStagesDetails(String opportunityStagesId) {
		OpportunityStages opportunityStages = opportunityStagesRepository
				.getOpportunityStagesByOpportunityStagesId(opportunityStagesId);
		OpportunityStagesMapper opportunityStagesMapper = new OpportunityStagesMapper();
		if (null != opportunityStages) {
			opportunityStagesMapper.setOpportunityStagesId(opportunityStages.getOpportunityStagesId());
			opportunityStagesMapper.setStageName(opportunityStages.getStageName());
			opportunityStagesMapper.setLiveInd(opportunityStages.isLiveInd());
			opportunityStagesMapper.setPublishInd(opportunityStages.isPublishInd());
			opportunityStagesMapper.setOrgId(opportunityStages.getOrgId());
			opportunityStagesMapper.setProbability(opportunityStages.getProbability());
			// opportunityStagesMapper.setResponsible(opportunityStages.getResponsible());
			opportunityStagesMapper.setDays(opportunityStages.getDays());
			opportunityStagesMapper.setCreationDate(Utility.getISOFromDate(opportunityStages.getCreationDate()));
			OpportunityStagesDelete opportunityStagesDelete=opportunityStagesDeleteRepository.findByOpportunityStagesId(opportunityStagesId);
			if (null != opportunityStagesDelete) {
				opportunityStagesMapper.setName(employeeService.getEmployeeFullName(opportunityStagesDelete.getUserId()));
				opportunityStagesMapper.setUpdationDate(Utility.getISOFromDate(opportunityStagesDelete.getUpdationDate()));
			}
		}
		return opportunityStagesMapper;
	}

	@Override
	public void deleteOpportunityStagesById(String opportunityStagesId) {
		if (null != opportunityStagesId) {
			OpportunityStages opportunityStages = opportunityStagesRepository
					.getOpportunityStagesByOpportunityStagesId(opportunityStagesId);

			if (null != opportunityStages) {
				OpportunityStagesDelete opportunityStagesDelete = opportunityStagesDeleteRepository
						.findByOpportunityStagesId(opportunityStagesId);
				if (null != opportunityStagesDelete) {
					opportunityStagesDelete.setUpdationDate(new Date());
					opportunityStagesDeleteRepository.save(opportunityStagesDelete);
				}

				// opportunityStages.setLiveInd(false);
				opportunityStagesRepository.delete(opportunityStages);

				opportunityStages.setLiveInd(false);
				opportunityStagesRepository.save(opportunityStages);

			}

		}
	}

	@Override
	public List<OpportunityStagesMapper> getStagesByOrgId(String orgId) {
		List<OpportunityStages> opportunityStagesList = opportunityStagesRepository.findByOrgIdAndLiveInd(orgId, true);
		List<OpportunityStagesMapper> mapperList = new ArrayList<>();
		System.out.println("opportunityStagesList+++++++++++++++++=========" + opportunityStagesList.toString());
		if (null != opportunityStagesList && !opportunityStagesList.isEmpty()) {

			opportunityStagesList.stream().map(li -> {

				System.out.println("getOrgId+++++++++++++++++=========" + li.getOrgId());

				if(!li.getStageName().equalsIgnoreCase("Lost")) {
					if(!li.getStageName().equalsIgnoreCase("Won")) {
				
				System.out.println("getOrgId+++++++++++++++++========="+li.getOrgId());

				OpportunityStagesMapper opportunityStagesMapper = new OpportunityStagesMapper();

				opportunityStagesMapper.setOpportunityStagesId(li.getOpportunityStagesId());
				opportunityStagesMapper.setStageName(li.getStageName());
				opportunityStagesMapper.setLiveInd(li.isLiveInd());
				opportunityStagesMapper.setPublishInd(li.isPublishInd());
				opportunityStagesMapper.setOrgId(li.getOrgId());
				opportunityStagesMapper.setUserId(li.getUserId());
				opportunityStagesMapper.setProbability(li.getProbability());
				opportunityStagesMapper.setDays(li.getDays());
				// opportunityStagesMapper.setResponsible(li.getResponsible());
				opportunityStagesMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
				opportunityStagesMapper.setOpportunityWorkflowDetailsId(li.getOpportunityWorkflowDetailsId());

				mapperList.add(opportunityStagesMapper);

					}
				}

				return mapperList;
				
			}).collect(Collectors.toList());

		}

		List<OpportunityStagesDelete> opportunityStagesDelete = opportunityStagesDeleteRepository.findByOrgId(orgId);
		if (null != opportunityStagesDelete && !opportunityStagesDelete.isEmpty()) {
			Collections.sort(opportunityStagesDelete, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			mapperList.get(0).setUpdationDate(Utility.getISOFromDate(opportunityStagesDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeByUserId(opportunityStagesDelete.get(0).getUserId());
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
	public boolean stageExistsByWeightage(double probability, String opportunityWorkflowDetailsId) {
		List<OpportunityStages> list = opportunityStagesRepository
				.findByOpportunityWorkflowDetailsIdAndLiveInd(opportunityWorkflowDetailsId, true);
		if (null != list && !list.isEmpty()) {
			for (OpportunityStages opportunityStages : list) {
				// OpportunityStages OpportunityStages1 =
				// opportunityStagesRepository.getByProbabilityAndOpportunityStageId();

				if (opportunityStages.getProbability() == probability) {

					return true;
				}
			}
		}

		return false;
	}

	@Override
	public OpportunityStagesMapper updateOpportunityStagesPubliahInd(OpportunityStagesMapper opportunityStagesMapper) {

		OpportunityStagesMapper resultMapper = null;
		
		if (null != opportunityStagesMapper.getOpportunityStagesId()) {
		OpportunityStages opportunityStages = opportunityStagesRepository.getOpportunityStagesByOpportunityStagesId(opportunityStagesMapper.getOpportunityStagesId());

		if (null != opportunityStages.getOpportunityStagesId()) {
			
			opportunityStages.setPublishInd(opportunityStagesMapper.isPublishInd());
			opportunityStagesRepository.save(opportunityStages);
			
		}
		resultMapper = getOpportunityStagesDetails(opportunityStagesMapper.getOpportunityStagesId());
	}
	return resultMapper;
}

	@Override
	public OpportunityWorkflowMapper updateOpportunityWorkflowDetailsPublishInd(OpportunityWorkflowMapper opportunityWorkflowMapper) {

		OpportunityWorkflowMapper resultMapper = null;
		
		if (null != opportunityWorkflowMapper.getOpportunityWorkflowDetailsId()) {
			OpportunityWorkflowDetails opportunityWorkflowDetails = opportunityWorkflowDetailsRepository.getOpportunityWorkflowDetailsByOpportunityWorkflowDetailsId(opportunityWorkflowMapper.getOpportunityWorkflowDetailsId());


		if (null != opportunityWorkflowDetails) {
			
			opportunityWorkflowDetails.setPublishInd(opportunityWorkflowMapper.isPublishInd());
			opportunityWorkflowDetailsRepository.save(opportunityWorkflowDetails);
			
		}
		resultMapper = getOpportunityWorkflowDetails(opportunityWorkflowMapper.getOpportunityWorkflowDetailsId());
	}
	return resultMapper;
}

	@Override
	public List<OpportunityWorkflowMapper> getWorkflowListByOrgIdForDropdown(String orgId) {
		List<OpportunityWorkflowDetails> opportunityWorkflowDetailsList = opportunityWorkflowDetailsRepository
				.findByOrgIdAndLiveIndAndPublishInd(orgId, true,true);
		List<OpportunityWorkflowMapper> mapperList = new ArrayList<>();
		if (null != opportunityWorkflowDetailsList && !opportunityWorkflowDetailsList.isEmpty()) {

			opportunityWorkflowDetailsList.stream().map(li -> {

				OpportunityWorkflowMapper opportunityWorkflowMapper = new OpportunityWorkflowMapper();

				opportunityWorkflowMapper.setOpportunityWorkflowDetailsId(li.getOpportunityWorkflowDetailsId());
				opportunityWorkflowMapper.setWorkflowName(li.getWorkflowName());
				opportunityWorkflowMapper.setLiveInd(li.isLiveInd());
				opportunityWorkflowMapper.setPublishInd(li.isPublishInd());
				opportunityWorkflowMapper.setOrgId(li.getOrgId());
				opportunityWorkflowMapper.setUserId(li.getUser_id());
				opportunityWorkflowMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));

				mapperList.add(opportunityWorkflowMapper);

				return mapperList;
			}).collect(Collectors.toList());

		}
		return mapperList;
	}

	@Override
	public List<OpportunityStagesMapper> getStagesByOrgIdForDropdown(String orgId) {
		List<OpportunityStages> opportunityStagesList = opportunityStagesRepository.findByOrgIdAndLiveIndAndPublishInd(orgId, true,true);
		List<OpportunityStagesMapper> mapperList = new ArrayList<>();
		System.out.println("opportunityStagesList+++++++++++++++++=========" + opportunityStagesList.toString());
		if (null != opportunityStagesList && !opportunityStagesList.isEmpty()) {

			opportunityStagesList.stream().map(li -> {

				System.out.println("getOrgId+++++++++++++++++=========" + li.getOrgId());

				if(!li.getStageName().equalsIgnoreCase("Lost")) {
					if(!li.getStageName().equalsIgnoreCase("Won")) {
				
				System.out.println("getOrgId+++++++++++++++++========="+li.getOrgId());

				OpportunityStagesMapper opportunityStagesMapper = new OpportunityStagesMapper();

				opportunityStagesMapper.setOpportunityStagesId(li.getOpportunityStagesId());
				opportunityStagesMapper.setStageName(li.getStageName());
				opportunityStagesMapper.setLiveInd(li.isLiveInd());
				opportunityStagesMapper.setPublishInd(li.isPublishInd());
				opportunityStagesMapper.setOrgId(li.getOrgId());
				opportunityStagesMapper.setUserId(li.getUserId());
				opportunityStagesMapper.setProbability(li.getProbability());
				opportunityStagesMapper.setDays(li.getDays());
				// opportunityStagesMapper.setResponsible(li.getResponsible());
				opportunityStagesMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
				opportunityStagesMapper.setOpportunityWorkflowDetailsId(li.getOpportunityWorkflowDetailsId());

				mapperList.add(opportunityStagesMapper);

					}
				}

				return mapperList;
				
			}).collect(Collectors.toList());

		}
		return mapperList;
	}
}
