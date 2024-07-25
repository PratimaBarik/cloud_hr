package com.app.employeePortal.investor.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.investor.entity.InvestorOppStages;
import com.app.employeePortal.investor.entity.InvestorOppWorkflow;
import com.app.employeePortal.investor.mapper.InvestorOppStagesMapper;
import com.app.employeePortal.investor.mapper.InvestorOppWorkflowMapper;
import com.app.employeePortal.investor.repository.InvestorOppStagesRepo;
import com.app.employeePortal.investor.repository.InvestorOppWorkflowRepo;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class InvestorOppWorkflowServiceImpl implements InvestorOppWorkflowService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    InvestorOppWorkflowRepo investorOppWorkflowRepo;
    @Autowired
    InvestorOppStagesRepo investorOppStagesRepo;
    @Autowired
    EmployeeService employeeService;
    @Override
    public InvestorOppWorkflowMapper saveInvOpportunityWorkflow(InvestorOppWorkflowMapper opportunityWorkflowMapper) {
        ArrayList<String> names = new ArrayList<String>(Arrays.asList("Won", "Lost"));

        String Ids = null;
        InvestorOppWorkflow newOpportunityWorkflowDetails = getInvestorOppWorkflow(opportunityWorkflowMapper);
        Ids = investorOppWorkflowRepo.save(newOpportunityWorkflowDetails)
                .getInvestorOppWorkflowId();

//        OpportunityWorkflowDetailsDelete opportunityWorkflowDetailsDelete = new OpportunityWorkflowDetailsDelete();
//        opportunityWorkflowDetailsDelete.setOrgId(opportunityWorkflowMapper.getOrgId());
//        opportunityWorkflowDetailsDelete.setOpportunityWorkflowDetailsId(Ids);
//        opportunityWorkflowDetailsDelete.setUpdationDate(new Date());
//        opportunityWorkflowDetailsDelete.setUserId(opportunityWorkflowMapper.getUserId());
//        opportunityWorkflowDetailsDeleteRepository.save(opportunityWorkflowDetailsDelete);
        for (int i = 0; i < names.size(); i++) {

            InvestorOppStages newOpportunityStages = new InvestorOppStages();

            newOpportunityStages.setStageName(names.get(i));
            newOpportunityStages.setLiveInd(true);
            newOpportunityStages.setUserId(opportunityWorkflowMapper.getUserId());
            newOpportunityStages.setOrgId(opportunityWorkflowMapper.getOrgId());
            newOpportunityStages.setPublishInd(false);
            newOpportunityStages.setCreationDate(new Date());
            newOpportunityStages.setInvestorOppWorkflowId(Ids);
            newOpportunityStages.setUpdatedBy(opportunityWorkflowMapper.getUserId());
            newOpportunityStages.setUpdatedOn(new Date());

            if (Objects.equals(names.get(i), "Won")) {

                newOpportunityStages.setProbability(100);
            } else {

                newOpportunityStages.setProbability(0);
            }
            investorOppStagesRepo.save(newOpportunityStages);
        }
        InvestorOppWorkflowMapper resultMapper=getInvOpportunityWorkflow(Ids);
        return resultMapper;
    }

    @NotNull
    private static InvestorOppWorkflow getInvestorOppWorkflow(InvestorOppWorkflowMapper opportunityWorkflowMapper) {
        InvestorOppWorkflow newOpportunityWorkflowDetails = new InvestorOppWorkflow();
//        newOpportunityWorkflowDetails
//                .setInvestorOppWorkflowId(opportunityWorkflowMapper.getInvestorOppWorkflowId());
        newOpportunityWorkflowDetails.setWorkflowName(opportunityWorkflowMapper.getWorkflowName());
        newOpportunityWorkflowDetails.setLiveInd(true);
        newOpportunityWorkflowDetails.setOrgId(opportunityWorkflowMapper.getOrgId());
        newOpportunityWorkflowDetails.setPublishInd(opportunityWorkflowMapper.isPublishInd());
        newOpportunityWorkflowDetails.setUserId(opportunityWorkflowMapper.getUserId());
        newOpportunityWorkflowDetails.setCreationDate(new Date());
        newOpportunityWorkflowDetails.setUpdatedOn(new Date());
        newOpportunityWorkflowDetails.setUpdatedBy(opportunityWorkflowMapper.getUserId());
        return newOpportunityWorkflowDetails;
    }

    @Override
    public List<InvestorOppWorkflowMapper> getInvOppWorkflowListByOrgId(String orgId) {
        List<InvestorOppWorkflow> inOpportunityWorkflowDetailsList = investorOppWorkflowRepo
                .findByOrgIdAndLiveInd(orgId, true);
        List<InvestorOppWorkflowMapper> mapperList = new ArrayList<>();
        if (null != inOpportunityWorkflowDetailsList && !inOpportunityWorkflowDetailsList.isEmpty()) {

            inOpportunityWorkflowDetailsList.stream().map(li -> {

                InvestorOppWorkflowMapper opportunityWorkflowMapper = new InvestorOppWorkflowMapper();

                opportunityWorkflowMapper.setInvestorOppWorkflowId(li.getInvestorOppWorkflowId());
                opportunityWorkflowMapper.setWorkflowName(li.getWorkflowName());
                opportunityWorkflowMapper.setLiveInd(li.isLiveInd());
                opportunityWorkflowMapper.setPublishInd(li.isPublishInd());
                opportunityWorkflowMapper.setOrgId(li.getOrgId());
                opportunityWorkflowMapper.setUserId(li.getUserId());
                opportunityWorkflowMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));

                mapperList.add(opportunityWorkflowMapper);

                return mapperList;
            }).collect(Collectors.toList());

        }

        if (null != inOpportunityWorkflowDetailsList && !inOpportunityWorkflowDetailsList.isEmpty()) {
            inOpportunityWorkflowDetailsList.sort((p1, p2) -> p2.getUpdatedOn().compareTo(p1.getUpdatedOn()));
            mapperList.get(0)
                    .setUpdationDate(Utility.getISOFromDate(inOpportunityWorkflowDetailsList.get(0).getUpdatedOn()));
            EmployeeDetails employeeDetails = employeeRepository
                    .getEmployeeByUserId(inOpportunityWorkflowDetailsList.get(0).getUserId());
            if (null != employeeDetails) {
                String middleName = " ";
                String lastName = "";

                if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                    lastName = employeeDetails.getLastName();
                }

                if (employeeDetails.getMiddleName() != null && !employeeDetails.getMiddleName().isEmpty()) {

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
    public InvestorOppWorkflowMapper updateInvOpportunityWorkflow(String investorOppWorkflowId, InvestorOppWorkflowMapper investorOppWorkflowMapper) {
        InvestorOppWorkflow opportunityWorkflowDetails = investorOppWorkflowRepo
                .getById(investorOppWorkflowId);
        if (null != opportunityWorkflowDetails) {

            if (null != investorOppWorkflowMapper.getWorkflowName()) {
                opportunityWorkflowDetails.setWorkflowName(investorOppWorkflowMapper.getWorkflowName());
            }
            if (null != investorOppWorkflowMapper.getUserId()) {
                opportunityWorkflowDetails.setUserId(investorOppWorkflowMapper.getUserId());
                opportunityWorkflowDetails.setUpdatedBy(investorOppWorkflowMapper.getUserId());
            }
            if (null != investorOppWorkflowMapper.getOrgId()) {
                opportunityWorkflowDetails.setOrgId(investorOppWorkflowMapper.getOrgId());
            }
            opportunityWorkflowDetails.setUpdatedOn(new Date());
            investorOppWorkflowRepo.save(opportunityWorkflowDetails);

//                OpportunityWorkflowDetailsDelete opportunityWorkflowDetailsDelete = opportunityWorkflowDetailsDeleteRepository
//                        .findByOpportunityWorkflowDetailsId(opportunityWorkflowDetailsId);
//                if (null != opportunityWorkflowDetailsDelete) {
//                    if (null != opportunityWorkflowMapper.getUserId()) {
//                        opportunityWorkflowDetailsDelete.setUserId(opportunityWorkflowMapper.getUserId());
//                    }
//                    if (null != opportunityWorkflowMapper.getOrgId()) {
//                        opportunityWorkflowDetailsDelete.setOrgId(opportunityWorkflowMapper.getOrgId());
//                    }
//                    opportunityWorkflowDetailsDelete.setUpdationDate(new Date());
//                    opportunityWorkflowDetailsDeleteRepository.save(opportunityWorkflowDetailsDelete);
//                }
            return getInvOpportunityWorkflow(
                    opportunityWorkflowDetails.getInvestorOppWorkflowId());
        }
        return investorOppWorkflowMapper;
    }

    @Override
    public InvestorOppWorkflowMapper getInvOpportunityWorkflow(String investorOppWorkflowId) {
        InvestorOppWorkflow opportunityWorkflowDetails = investorOppWorkflowRepo
                .getById(investorOppWorkflowId);
        InvestorOppWorkflowMapper opportunityWorkflowMapper = new InvestorOppWorkflowMapper();
        if (null != opportunityWorkflowDetails) {
            opportunityWorkflowMapper
                    .setInvestorOppWorkflowId(opportunityWorkflowDetails.getInvestorOppWorkflowId());
            opportunityWorkflowMapper.setWorkflowName(opportunityWorkflowDetails.getWorkflowName());
            opportunityWorkflowMapper.setLiveInd(opportunityWorkflowDetails.isLiveInd());
            opportunityWorkflowMapper.setPublishInd(opportunityWorkflowDetails.isPublishInd());
            opportunityWorkflowMapper.setOrgId(opportunityWorkflowDetails.getOrgId());
            opportunityWorkflowMapper.setUserId(opportunityWorkflowDetails.getUserId());
            opportunityWorkflowMapper
                    .setCreationDate(Utility.getISOFromDate(opportunityWorkflowDetails.getCreationDate()));
            opportunityWorkflowMapper.setUpdationDate(Utility.getISOFromDate(opportunityWorkflowDetails.getUpdatedOn()));
            opportunityWorkflowMapper.setName(employeeService.getEmployeeFullName(opportunityWorkflowDetails.getUpdatedBy()));
        }
        return opportunityWorkflowMapper;
    }

    @Override
    public void deleteInvOpportunityWorkflowById(String investorOppWorkflowId, String loggedInUserId) {
        if (null != investorOppWorkflowId) {
            InvestorOppWorkflow opportunityWorkflowDetails = investorOppWorkflowRepo
                    .getById(investorOppWorkflowId);

            if (null != opportunityWorkflowDetails) {
                opportunityWorkflowDetails.setLiveInd(false);
                opportunityWorkflowDetails.setUpdatedOn(new Date());
                opportunityWorkflowDetails.setUserId(loggedInUserId);
                investorOppWorkflowRepo.save(opportunityWorkflowDetails);

            }

        }

    }

    @Override
    public boolean stageExistsByWeightage(double probability, String investorOppStagesId) {
        List<InvestorOppStages> list = investorOppStagesRepo.findByInvestorOppStagesIdAndLiveInd(investorOppStagesId, true);
        if (null != list && !list.isEmpty()) {
            for (InvestorOppStages opportunityStages : list) {
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
    public InvestorOppStagesMapper saveInvOpportunityStages(InvestorOppStagesMapper opportunityStagesMapper) {
        String Ids = null;
        InvestorOppStages newOpportunityStages = new InvestorOppStages();
        newOpportunityStages.setStageName(opportunityStagesMapper.getStageName());
        newOpportunityStages.setLiveInd(true);
        newOpportunityStages.setUserId(opportunityStagesMapper.getUserId());
        newOpportunityStages.setOrgId(opportunityStagesMapper.getOrgId());
        newOpportunityStages.setPublishInd(opportunityStagesMapper.isPublishInd());
        newOpportunityStages.setProbability(opportunityStagesMapper.getProbability());
        newOpportunityStages.setDays(opportunityStagesMapper.getDays());
        newOpportunityStages.setResponsible(opportunityStagesMapper.getResponsible());
        newOpportunityStages.setCreationDate(new Date());
        newOpportunityStages.setInvestorOppWorkflowId(opportunityStagesMapper.getInvestorOppWorkflowId());
        newOpportunityStages.setUpdatedBy(opportunityStagesMapper.getUserId());
        newOpportunityStages.setUpdatedOn(new Date());
        Ids = investorOppStagesRepo.save(newOpportunityStages).getInvestorOppStagesId();
        InvestorOppStagesMapper opportunityStagesMapperr = getOpportunityStagesDetails(Ids);
        return opportunityStagesMapperr;
    }

    @Override
    public List<InvestorOppStagesMapper> getStagesByInvOppworkFlowId(String investorOppWorkflowId) {
        List<InvestorOppStages> opportunityStagesList = investorOppStagesRepo
                .findByInvestorOppWorkflowIdAndLiveInd(investorOppWorkflowId, true);

        List<InvestorOppStagesMapper> mapperList = new ArrayList<>();
        if (null != opportunityStagesList && !opportunityStagesList.isEmpty()) {

            opportunityStagesList.stream().map(li -> {

                if (!li.getStageName().equalsIgnoreCase("Lost")) {
                    if (!li.getStageName().equalsIgnoreCase("Won")) {

                        InvestorOppStagesMapper opportunityStagesMapper = new InvestorOppStagesMapper();

                        opportunityStagesMapper.setInvestorOppStagesId(li.getInvestorOppStagesId());
                        opportunityStagesMapper.setStageName(li.getStageName());
                        opportunityStagesMapper.setLiveInd(li.isLiveInd());
                        opportunityStagesMapper.setPublishInd(li.isPublishInd());
                        opportunityStagesMapper.setOrgId(li.getOrgId());
                        opportunityStagesMapper.setUserId(li.getUserId());
                        opportunityStagesMapper.setProbability(li.getProbability());
                        opportunityStagesMapper.setDays(li.getDays());
                        // opportunityStagesMapper.setResponsible(li.getResponsible());
                        opportunityStagesMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
                        opportunityStagesMapper.setInvestorOppWorkflowId(li.getInvestorOppWorkflowId());

                        mapperList.add(opportunityStagesMapper);

                        if (!mapperList.isEmpty()) {

                            mapperList.sort((c1, c2) -> Double.compare(c1.getProbability(), c2.getProbability()));

                        }

                    }
                }

                return mapperList;
            }).collect(Collectors.toList());

        }
        if (null != opportunityStagesList && !opportunityStagesList.isEmpty()) {
            opportunityStagesList.sort((p1, p2) -> p2.getUpdatedOn().compareTo((p1.getUpdatedOn())));
            mapperList.get(0).setUpdationDate(Utility.getISOFromDate(opportunityStagesList.get(0).getUpdatedOn()));
            EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(opportunityStagesList.get(0).getUserId());
            if (null != employeeDetails) {
                String middleName = " ";
                String lastName = "";

                if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                    lastName = employeeDetails.getLastName();
                }

                if (employeeDetails.getMiddleName() != null && !employeeDetails.getMiddleName().isEmpty()) {

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
    public InvestorOppStagesMapper updateInvOpportunityStages(String investorOppStagesId, InvestorOppStagesMapper opportunityStagesMapper) {
        InvestorOppStages opportunityStages = investorOppStagesRepo
                .getById(investorOppStagesId);
        InvestorOppStagesMapper opportunityStagesMapperr = new InvestorOppStagesMapper();
        if (null != opportunityStages) {

            if (null != opportunityStagesMapper.getStageName()) {
                opportunityStages.setStageName(opportunityStagesMapper.getStageName());
            }
            if (null != opportunityStagesMapper.getOrgId()) {
                opportunityStages.setOrgId(opportunityStagesMapper.getOrgId());
            }
            if (0 != opportunityStagesMapper.getProbability()) {
                opportunityStages.setProbability(opportunityStagesMapper.getProbability());
            }
            if (0 != opportunityStagesMapper.getDays()) {
                opportunityStages.setDays(opportunityStagesMapper.getDays());
            }

            if (null != opportunityStagesMapper.getOrgId()) {
                opportunityStages.setOrgId(opportunityStagesMapper.getOrgId());
            }
            investorOppStagesRepo.save(opportunityStages);
            opportunityStagesMapperr = getOpportunityStagesDetails(
                    opportunityStages.getInvestorOppStagesId());
            return opportunityStagesMapperr;
        }
        return opportunityStagesMapperr;
    }

    @Override
    public InvestorOppStagesMapper getOpportunityStagesDetails(String investorOppStagesId) {
        InvestorOppStages opportunityStages = investorOppStagesRepo
                .getById(investorOppStagesId);
        InvestorOppStagesMapper opportunityStagesMapper = new InvestorOppStagesMapper();
        if (null != opportunityStages) {
            opportunityStagesMapper.setInvestorOppStagesId(opportunityStages.getInvestorOppStagesId());
            opportunityStagesMapper.setStageName(opportunityStages.getStageName());
            opportunityStagesMapper.setLiveInd(opportunityStages.isLiveInd());
            opportunityStagesMapper.setPublishInd(opportunityStages.isPublishInd());
            opportunityStagesMapper.setOrgId(opportunityStages.getOrgId());
            opportunityStagesMapper.setProbability(opportunityStages.getProbability());
            opportunityStagesMapper.setResponsible(opportunityStages.getResponsible());
            opportunityStagesMapper.setDays(opportunityStages.getDays());
            opportunityStagesMapper.setCreationDate(Utility.getISOFromDate(opportunityStages.getCreationDate()));
            opportunityStagesMapper.setInvestorOppWorkflowId(opportunityStages.getInvestorOppWorkflowId());
            opportunityStagesMapper.setUpdationDate(Utility.getISOFromDate(opportunityStages.getUpdatedOn()));
            opportunityStagesMapper.setName(employeeService.getEmployeeFullName(opportunityStages.getUpdatedBy()));
        }
        return opportunityStagesMapper;
    }

    @Override
    public void deleteInvOpportunityStagesById(String investorOppStagesId,String loggedInUserId) {
        if (null != investorOppStagesId) {
            InvestorOppStages opportunityStages = investorOppStagesRepo
                    .getById(investorOppStagesId);

            if (null != opportunityStages) {
                opportunityStages.setUpdatedOn(new Date());
                opportunityStages.setUpdatedBy(loggedInUserId);
                opportunityStages.setLiveInd(false);
                investorOppStagesRepo.save(opportunityStages);
            }

        }
    }

    @Override
    public List<InvestorOppStagesMapper> getStagesByOrgId(String orgId) {
        List<InvestorOppStages> opportunityStagesList = investorOppStagesRepo.findByOrgIdAndLiveInd(orgId, true);
        List<InvestorOppStagesMapper> mapperList = new ArrayList<>();
        System.out.println("opportunityStagesList+++++++++++++++++=========" + opportunityStagesList.toString());
        if (null != opportunityStagesList && !opportunityStagesList.isEmpty()) {

            opportunityStagesList.stream().map(li -> {

                System.out.println("getOrgId+++++++++++++++++=========" + li.getOrgId());

                if (!li.getStageName().equalsIgnoreCase("Lost")) {
                    if (!li.getStageName().equalsIgnoreCase("Won")) {

                        System.out.println("getOrgId+++++++++++++++++=========" + li.getOrgId());

                        InvestorOppStagesMapper opportunityStagesMapper = new InvestorOppStagesMapper();

                        opportunityStagesMapper.setInvestorOppStagesId(li.getInvestorOppStagesId());
                        opportunityStagesMapper.setStageName(li.getStageName());
                        opportunityStagesMapper.setLiveInd(li.isLiveInd());
                        opportunityStagesMapper.setPublishInd(li.isPublishInd());
                        opportunityStagesMapper.setOrgId(li.getOrgId());
                        opportunityStagesMapper.setUserId(li.getUserId());
                        opportunityStagesMapper.setProbability(li.getProbability());
                        opportunityStagesMapper.setDays(li.getDays());
                        // opportunityStagesMapper.setResponsible(li.getResponsible());
                        opportunityStagesMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
                        opportunityStagesMapper.setInvestorOppWorkflowId(li.getInvestorOppWorkflowId());

                        mapperList.add(opportunityStagesMapper);

                    }
                }

                return mapperList;

            }).collect(Collectors.toList());
        }

        if (null != opportunityStagesList && !opportunityStagesList.isEmpty()) {
            opportunityStagesList.sort((p1, p2) -> p2.getUpdatedOn().compareTo(p1.getUpdatedOn()));

            mapperList.get(0).setUpdationDate(Utility.getISOFromDate(opportunityStagesList.get(0).getUpdatedOn()));
            EmployeeDetails employeeDetails = employeeRepository
                    .getEmployeeByUserId(opportunityStagesList.get(0).getUserId());
            if (null != employeeDetails) {
                String middleName = " ";
                String lastName = "";

                if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                    lastName = employeeDetails.getLastName();
                }

                if (employeeDetails.getMiddleName() != null && !employeeDetails.getMiddleName().isEmpty()) {

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
    public InvestorOppStagesMapper updateInvOpportunityStagesPublishInd(InvestorOppStagesMapper opportunityStagesMapper) {
        InvestorOppStagesMapper resultMapper = null;

        if (null != opportunityStagesMapper.getInvestorOppStagesId()) {
            InvestorOppStages opportunityStages = investorOppStagesRepo.getById(opportunityStagesMapper.getInvestorOppStagesId());
            if (null != opportunityStages) {
                opportunityStages.setPublishInd(opportunityStagesMapper.isPublishInd());
                investorOppStagesRepo.save(opportunityStages);

            }
            resultMapper = getOpportunityStagesDetails(opportunityStagesMapper.getInvestorOppStagesId());
        }
        return resultMapper;
    }

    @Override
    public InvestorOppWorkflowMapper updateInvOpportunityWorkflowDetailsPublishInd(InvestorOppWorkflowMapper opportunityWorkflowMapper) {
        InvestorOppWorkflowMapper resultMapper = null;

        if (null != opportunityWorkflowMapper.getInvestorOppWorkflowId()) {
            InvestorOppWorkflow opportunityWorkflow = investorOppWorkflowRepo.getById(opportunityWorkflowMapper.getInvestorOppWorkflowId());
            if (null != opportunityWorkflow) {
                opportunityWorkflow.setPublishInd(opportunityWorkflowMapper.isPublishInd());
                investorOppWorkflowRepo.save(opportunityWorkflow);
            }
            resultMapper = getInvOpportunityWorkflow(opportunityWorkflowMapper.getInvestorOppWorkflowId());
        }
        return resultMapper;
    }

	@Override
	public List<InvestorOppWorkflowMapper> getWorkflowListByOrgIdForDropDown(String orgId) {
		List<InvestorOppWorkflow> inOpportunityWorkflowDetailsList = investorOppWorkflowRepo
                .findByOrgIdAndLiveIndAndPublishInd(orgId, true, true);
        List<InvestorOppWorkflowMapper> mapperList = new ArrayList<>();
        if (null != inOpportunityWorkflowDetailsList && !inOpportunityWorkflowDetailsList.isEmpty()) {

            inOpportunityWorkflowDetailsList.stream().map(li -> {

                InvestorOppWorkflowMapper opportunityWorkflowMapper = new InvestorOppWorkflowMapper();

                opportunityWorkflowMapper.setInvestorOppWorkflowId(li.getInvestorOppWorkflowId());
                opportunityWorkflowMapper.setWorkflowName(li.getWorkflowName());
                opportunityWorkflowMapper.setLiveInd(li.isLiveInd());
                opportunityWorkflowMapper.setPublishInd(li.isPublishInd());
                opportunityWorkflowMapper.setOrgId(li.getOrgId());
                opportunityWorkflowMapper.setUserId(li.getUserId());
                opportunityWorkflowMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));

                mapperList.add(opportunityWorkflowMapper);

                return mapperList;
            }).collect(Collectors.toList());
        }
        return mapperList;
	}

	@Override
	public List<InvestorOppStagesMapper> getOpportunityWorkflowStagesByOrgIdForDropDown(String orgId) {
		 List<InvestorOppStages> opportunityStagesList = investorOppStagesRepo.findByOrgIdAndLiveIndAndPublishInd(orgId, true, true);
	        List<InvestorOppStagesMapper> mapperList = new ArrayList<>();
	        System.out.println("opportunityStagesList+++++++++++++++++=========" + opportunityStagesList.toString());
	        if (null != opportunityStagesList && !opportunityStagesList.isEmpty()) {

	            opportunityStagesList.stream().map(li -> {

	                System.out.println("getOrgId+++++++++++++++++=========" + li.getOrgId());

	                if (!li.getStageName().equalsIgnoreCase("Lost")) {
	                    if (!li.getStageName().equalsIgnoreCase("Won")) {

	                        System.out.println("getOrgId+++++++++++++++++=========" + li.getOrgId());

	                        InvestorOppStagesMapper opportunityStagesMapper = new InvestorOppStagesMapper();

	                        opportunityStagesMapper.setInvestorOppStagesId(li.getInvestorOppStagesId());
	                        opportunityStagesMapper.setStageName(li.getStageName());
	                        opportunityStagesMapper.setLiveInd(li.isLiveInd());
	                        opportunityStagesMapper.setPublishInd(li.isPublishInd());
	                        opportunityStagesMapper.setOrgId(li.getOrgId());
	                        opportunityStagesMapper.setUserId(li.getUserId());
	                        opportunityStagesMapper.setProbability(li.getProbability());
	                        opportunityStagesMapper.setDays(li.getDays());
	                        // opportunityStagesMapper.setResponsible(li.getResponsible());
	                        opportunityStagesMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
	                        opportunityStagesMapper.setInvestorOppWorkflowId(li.getInvestorOppWorkflowId());

	                        mapperList.add(opportunityStagesMapper);

	                    }
	                }

	                return mapperList;

	            }).collect(Collectors.toList());
	        }
		return mapperList;
	}
}
