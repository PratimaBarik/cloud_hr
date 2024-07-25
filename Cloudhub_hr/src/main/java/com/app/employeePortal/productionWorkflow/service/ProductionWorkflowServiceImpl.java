package com.app.employeePortal.productionWorkflow.service;

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
import com.app.employeePortal.productionWorkflow.entity.ProductionStages;
import com.app.employeePortal.productionWorkflow.entity.ProductionWorkflowDetails;
import com.app.employeePortal.productionWorkflow.mapper.ProductionStagesRequestMapper;
import com.app.employeePortal.productionWorkflow.mapper.ProductionStagesResponseMapper;
import com.app.employeePortal.productionWorkflow.mapper.ProductionWorkflowRequestMapper;
import com.app.employeePortal.productionWorkflow.mapper.ProductionWorkflowResponseMapper;
import com.app.employeePortal.productionWorkflow.repository.ProductionStagesRepository;
import com.app.employeePortal.productionWorkflow.repository.ProductionWorkflowDetailsRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class ProductionWorkflowServiceImpl implements ProductionWorkflowService {

	@Autowired
	ProductionWorkflowDetailsRepository productionWorkflowDetailsRepository;

	@Autowired
	ProductionStagesRepository productionStagesRepository;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public ProductionWorkflowResponseMapper saveProductionWorkflow(ProductionWorkflowRequestMapper requestMapper) {
		ArrayList<String> names = new ArrayList<String>(Arrays.asList("Won", "Lost"));
		String Ids = null;
		ProductionWorkflowDetails db = new ProductionWorkflowDetails();
		db.setWorkflowName(requestMapper.getWorkflowName());
		db.setLiveInd(true);
		db.setOrgId(requestMapper.getOrgId());
		db.setPublishInd(requestMapper.isPublishInd());
		db.setUserId(requestMapper.getUserId());
		db.setCreationDate(new Date());
		db.setUpdationDate(new Date());
		db.setUpdatedBy(requestMapper.getUserId());
		Ids = productionWorkflowDetailsRepository.save(db).getProductionWorkflowDetailsId();
		for (int i = 0; i < names.size(); i++) {
			ProductionStages unboardingStages = new ProductionStages();
			unboardingStages.setStageName(names.get(i));
			unboardingStages.setLiveInd(true);
			unboardingStages.setUserId(requestMapper.getUserId());
			unboardingStages.setOrgId(requestMapper.getOrgId());
			unboardingStages.setPublishInd(false);
			unboardingStages.setCreationDate(new Date());
			unboardingStages.setProductionWorkflowDetailsId(Ids);
			unboardingStages.setUpdatedBy(requestMapper.getUserId());
			unboardingStages.setUpdationDate(new Date());
			if (names.get(i) == "Won") {
				unboardingStages.setProbability(100);
			} else {
				unboardingStages.setProbability(0);
			}
			productionStagesRepository.save(unboardingStages).getProductionStagesId();
		}
		ProductionWorkflowResponseMapper resultMapper = getProductionWorkflowDetails(Ids);
		return resultMapper;
	}

	private ProductionWorkflowResponseMapper getProductionWorkflowDetails(String productionWorkflowDetailsId) {
		ProductionWorkflowDetails db = productionWorkflowDetailsRepository
				.getByProductionWorkflowDetailsId(productionWorkflowDetailsId);
		ProductionWorkflowResponseMapper responseMapper = new ProductionWorkflowResponseMapper();
		if (null != db) {
			responseMapper.setProductionWorkflowDetailsId(productionWorkflowDetailsId);
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
	public List<ProductionWorkflowResponseMapper> getProductionWorkflowListByOrgId(String orgId) {
		List<ProductionWorkflowDetails> list = productionWorkflowDetailsRepository.findByOrgIdAndLiveInd(orgId, true);
		List<ProductionWorkflowResponseMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				ProductionWorkflowResponseMapper responseMapper = new ProductionWorkflowResponseMapper();
				responseMapper.setProductionWorkflowDetailsId(li.getProductionWorkflowDetailsId());
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
	public ProductionWorkflowResponseMapper updateOpportunityWorkflowDetails(String productionWorkflowDetailsId,
			ProductionWorkflowRequestMapper requestMapper) {
		ProductionWorkflowDetails db = productionWorkflowDetailsRepository
				.getByProductionWorkflowDetailsId(productionWorkflowDetailsId);
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
			productionWorkflowDetailsRepository.save(db);
		}
		ProductionWorkflowResponseMapper responseMapper = getProductionWorkflowDetails(productionWorkflowDetailsId);
		return responseMapper;
	}

	@Override
	public void deleteProductionWorkflowDetails(String productionWorkflowDetailsId) {
		ProductionWorkflowDetails db = productionWorkflowDetailsRepository
				.getByProductionWorkflowDetailsId(productionWorkflowDetailsId);
		if (null != db) {
			db.setUpdationDate(new Date());
			db.setLiveInd(false);
			productionWorkflowDetailsRepository.save(db);
		}
	}

	@Override
	public boolean stageExistsByWeightage(double probability, String productionWorkflowDetailsId) {
		List<ProductionStages> list = productionStagesRepository
				.findByProductionWorkflowDetailsIdAndLiveInd(productionWorkflowDetailsId, true);
		if (null != list && !list.isEmpty()) {
			for (ProductionStages productionStages : list) {
				if (productionStages.getProbability() == probability) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ProductionStagesResponseMapper saveProductionStages(ProductionStagesRequestMapper requestMapper) {
		String Ids = null;
		ProductionStages db = new ProductionStages();

		db.setStageName(requestMapper.getStageName());
		db.setLiveInd(true);
		db.setUserId(requestMapper.getUserId());
		db.setOrgId(requestMapper.getOrgId());
		db.setPublishInd(requestMapper.isPublishInd());
		db.setProbability(requestMapper.getProbability());
		db.setDays(requestMapper.getDays());
		// newOpportunityStages.setResponsible(opportunityStagesMapper.getResponsible());
		db.setCreationDate(new Date());
		db.setProductionWorkflowDetailsId(requestMapper.getProductionWorkflowDetailsId());
		db.setUpdationDate(new Date());
		db.setUpdatedBy(requestMapper.getUserId());
		db.setUserId(requestMapper.getUserId());
		Ids = productionStagesRepository.save(db).getProductionStagesId();
		ProductionStagesResponseMapper resultMapper = getProductionStagesDetails(Ids);
		return resultMapper;
	}

	private ProductionStagesResponseMapper getProductionStagesDetails(String productionStagesId) {
		ProductionStages db = productionStagesRepository.getByProductionStagesId(productionStagesId);
		System.out.println("supplierUnboardingStages ------" + db.getProductionWorkflowDetailsId());
		ProductionStagesResponseMapper responseMapper = new ProductionStagesResponseMapper();
		if (null != db) {
			System.out.println("supplierUnboardingStages22 ------" + db.getProductionWorkflowDetailsId());
			responseMapper.setProductionStagesId(db.getProductionStagesId());
			responseMapper.setStageName(db.getStageName());
			responseMapper.setLiveInd(db.isLiveInd());
			responseMapper.setPublishInd(db.isPublishInd());
			responseMapper.setOrgId(db.getOrgId());
			responseMapper.setProbability(db.getProbability());
			responseMapper.setDays(db.getDays());
			responseMapper.setCreationDate(Utility.getISOFromDate(db.getCreationDate()));
			responseMapper.setUserId(db.getUserId());
			responseMapper.setProductionWorkflowDetailsId(db.getProductionWorkflowDetailsId());
			responseMapper.setUpdationDate(Utility.getISOFromDate(db.getUpdationDate()));
			responseMapper.setName(employeeService.getEmployeeFullName(db.getUpdatedBy()));
			System.out.println("getEmployeeFullName ------" + employeeService.getEmployeeFullName(db.getUpdatedBy()));
		}
		return responseMapper;
	}

	@Override
	public List<ProductionStagesResponseMapper> getStagesByProductionWorkflowDetailsId(
			String productionWorkflowDetailsId) {
		List<ProductionStages> list = productionStagesRepository
				.findByProductionWorkflowDetailsIdAndLiveInd(productionWorkflowDetailsId, true);
		List<ProductionStagesResponseMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				if (!li.getStageName().equalsIgnoreCase("Lost")) {
					if (!li.getStageName().equalsIgnoreCase("Won")) {
						ProductionStagesResponseMapper responseMapper = new ProductionStagesResponseMapper();
						responseMapper.setProductionStagesId(li.getProductionStagesId());
						responseMapper.setStageName(li.getStageName());
						responseMapper.setLiveInd(li.isLiveInd());
						responseMapper.setPublishInd(li.isPublishInd());
						responseMapper.setOrgId(li.getOrgId());
						responseMapper.setUserId(li.getUserId());
						responseMapper.setProbability(li.getProbability());
						responseMapper.setDays(li.getDays());
						// opportunityStagesMapper.setResponsible(li.getResponsible());
						responseMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
						responseMapper.setProductionWorkflowDetailsId(li.getProductionWorkflowDetailsId());
						responseMapper.setUpdationDate(Utility.getISOFromDate(li.getUpdationDate()));
						mapperList.add(responseMapper);
						if (null != mapperList && !mapperList.isEmpty()) {

							Collections.sort(mapperList,
									(ProductionStagesResponseMapper c1, ProductionStagesResponseMapper c2) -> Double
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
	public ProductionStagesResponseMapper updateProductionStagesId(String productionStagesId,
			ProductionStagesRequestMapper requestMapper) {
		ProductionStages db = productionStagesRepository.getByProductionStagesId(productionStagesId);
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
			productionStagesRepository.save(db);
		}
		ProductionStagesResponseMapper responseMapper = getProductionStagesDetails(productionStagesId);
		return responseMapper;
	}

	@Override
	public void deleteProductionStagesById(String productionStagesId) {
		ProductionStages db = productionStagesRepository.getByProductionStagesId(productionStagesId);
		if (null != db) {
			db.setUpdationDate(new Date());
			db.setLiveInd(false);
			productionStagesRepository.save(db);
		}
	}

	@Override
	public ProductionStagesResponseMapper updateProductionStagesPubliahInd(
			ProductionStagesRequestMapper requestMapper) {
		ProductionStagesResponseMapper resultMapper = null;
		if (null != requestMapper.getProductionStagesId()) {
			ProductionStages db = productionStagesRepository
					.getByProductionStagesId(requestMapper.getProductionStagesId());
			if (null != db.getProductionStagesId()) {
				db.setPublishInd(requestMapper.isPublishInd());
				productionStagesRepository.save(db);
			}
			resultMapper = getProductionStagesDetails(requestMapper.getProductionStagesId());
		}
		return resultMapper;
	}

	@Override
	public ProductionWorkflowResponseMapper updateProductionWorkflowDetailsPublishInd(
			ProductionStagesRequestMapper requestMapper) {
		ProductionWorkflowResponseMapper resultMapper = null;
		if (null != requestMapper.getProductionWorkflowDetailsId()) {
			ProductionWorkflowDetails db = productionWorkflowDetailsRepository
					.getByProductionWorkflowDetailsId(requestMapper.getProductionWorkflowDetailsId());
			if (null != db) {
				db.setPublishInd(requestMapper.isPublishInd());
				productionWorkflowDetailsRepository.save(db);
			}
			resultMapper = getProductionWorkflowDetails(requestMapper.getProductionWorkflowDetailsId());
		}
		return resultMapper;
	}

	@Override
	public List<ProductionWorkflowResponseMapper> getProductionWorkflowListByOrgIdForDropdown(String orgId) {
		List<ProductionWorkflowDetails> list = productionWorkflowDetailsRepository
				.findByOrgIdAndLiveIndAndPublishInd(orgId, true, true);
		List<ProductionWorkflowResponseMapper> mapperList = new ArrayList<>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				ProductionWorkflowResponseMapper mapper = getProductionWorkflowDetails(
						li.getProductionWorkflowDetailsId());
				mapperList.add(mapper);
				return mapperList;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<ProductionStagesResponseMapper> getProductionStagesByOrgIdForDropdown(String orgId) {
		List<ProductionStages> list = productionStagesRepository.findByOrgIdAndLiveIndAndPublishInd(orgId, true, true);
		List<ProductionStagesResponseMapper> mapperList = new ArrayList<>();
		System.out.println("opportunityStagesList+++++++++++++++++=========" + list.toString());
		if (null != list && !list.isEmpty()) {
			list.stream().map(li -> {
				System.out.println("getOrgId111+++++++++++++++++=========" + li.getOrgId());
				if (!li.getStageName().equalsIgnoreCase("Lost")) {
					if (!li.getStageName().equalsIgnoreCase("Won")) {
						System.out.println("getOrgId222+++++++++++++++++=========" + li.getOrgId());
						ProductionStagesResponseMapper resultMapper = getProductionStagesDetails(
								li.getProductionStagesId());
						mapperList.add(resultMapper);
					}
				}
				return mapperList;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

}
