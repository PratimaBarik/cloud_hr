package com.app.employeePortal.Workflow.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.app.employeePortal.category.entity.WorkflowCategory;
import com.app.employeePortal.category.repository.WorkflowCategoryRepo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.employeePortal.Workflow.entity.Stages;
import com.app.employeePortal.Workflow.entity.StagesTask;
import com.app.employeePortal.Workflow.entity.WorkflowDetails;
import com.app.employeePortal.Workflow.mapper.StagesRequestMapper;
import com.app.employeePortal.Workflow.mapper.StagesResponseMapper;
import com.app.employeePortal.Workflow.mapper.StagesTaskRequestMapper;
import com.app.employeePortal.Workflow.mapper.StagesTaskResponseMapper;
import com.app.employeePortal.Workflow.mapper.WorkflowRequestMapper;
import com.app.employeePortal.Workflow.mapper.WorkflowResponseMapper;
import com.app.employeePortal.Workflow.repository.StagesRepository;
import com.app.employeePortal.Workflow.repository.StagesTaskRepository;
import com.app.employeePortal.Workflow.repository.WorkflowDetailsRepository;
import com.app.employeePortal.category.entity.TaskChecklist;
import com.app.employeePortal.category.entity.TaskChecklistStageLink;
import com.app.employeePortal.category.repository.TaskChecklistRepository;
import com.app.employeePortal.category.repository.TaskChecklistStageLinkRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.investor.entity.InvestorOppStages;
import com.app.employeePortal.investor.entity.InvestorOppWorkflow;
import com.app.employeePortal.investor.repository.InvestorOppStagesRepo;
import com.app.employeePortal.investor.repository.InvestorOppWorkflowRepo;
import com.app.employeePortal.leads.entity.Leads;
import com.app.employeePortal.opportunityWorkflow.entity.OpportunityStages;
import com.app.employeePortal.opportunityWorkflow.entity.OpportunityWorkflowDetails;
import com.app.employeePortal.opportunityWorkflow.repository.OpportunityStagesRepository;
import com.app.employeePortal.opportunityWorkflow.repository.OpportunityWorkflowDetailsRepository;
import com.app.employeePortal.productionWorkflow.entity.ProductionStages;
import com.app.employeePortal.productionWorkflow.entity.ProductionWorkflowDetails;
import com.app.employeePortal.productionWorkflow.repository.ProductionStagesRepository;
import com.app.employeePortal.productionWorkflow.repository.ProductionWorkflowDetailsRepository;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.repairWorkflow.entity.RepairStages;
import com.app.employeePortal.repairWorkflow.entity.RepairWorkflowDetails;
import com.app.employeePortal.repairWorkflow.repository.RepairStagesRepository;
import com.app.employeePortal.repairWorkflow.repository.RepairWorkflowDetailsRepository;
import com.app.employeePortal.unboardingWorkflow.entity.SupplierUnboardingStages;
import com.app.employeePortal.unboardingWorkflow.entity.SupplierUnboardingWorkflowDetails;
import com.app.employeePortal.unboardingWorkflow.entity.UnboardingStages;
import com.app.employeePortal.unboardingWorkflow.entity.UnboardingWorkflowDetails;
import com.app.employeePortal.unboardingWorkflow.repository.SupplierUnboardingStagesRepository;
import com.app.employeePortal.unboardingWorkflow.repository.SupplierUnboardingWorkflowDetailsRepository;
import com.app.employeePortal.unboardingWorkflow.repository.UnboardingStagesRepository;
import com.app.employeePortal.unboardingWorkflow.repository.UnboardingWorkflowDetailsRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class WorkflowServiceImpl implements WorkflowService {

    @Autowired
    WorkflowDetailsRepository WorkflowDetailsRepository;

    @Autowired
    StagesRepository StagesRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    StagesTaskRepository stagesTaskRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    OpportunityWorkflowDetailsRepository oppWFRepository;
    @Autowired
    OpportunityStagesRepository oppStagesRepository;

    @Autowired
    InvestorOppWorkflowRepo investorOppWorkflowRepo;
    @Autowired
    InvestorOppStagesRepo investorOppStagesRepo;

    @Autowired
    UnboardingStagesRepository unboardingStagesRepository;
    @Autowired
    UnboardingWorkflowDetailsRepository unboardingWorkflowDetailsRepository;

    @Autowired
    SupplierUnboardingStagesRepository supplierUnboardingStagesRepository;
    @Autowired
    SupplierUnboardingWorkflowDetailsRepository supplierUnboardingWorkflowDetailsRepository;

    @Autowired
    ProductionWorkflowDetailsRepository productionWorkflowDetailsRepository;
    @Autowired
    ProductionStagesRepository productionStagesRepository;

    @Autowired
    RepairWorkflowDetailsRepository repairWorkflowDetailsRepository;
    @Autowired
    RepairStagesRepository repairStagesRepository;

    @Autowired
    TaskChecklistRepository taskChecklistRepository;
    @Autowired
    TaskChecklistStageLinkRepository taskChecklistStageLinkRepository;

    @Autowired
    WorkflowCategoryRepo workflowCategoryRepo;

    @Override
    public WorkflowResponseMapper saveWorkflow(WorkflowRequestMapper requestMapper) {
        ArrayList<String> names = new ArrayList<String>(Arrays.asList("Won", "Lost"));
        String Ids = null;
        WorkflowDetails db = new WorkflowDetails();
        db.setWorkflowName(requestMapper.getWorkflowName());
        db.setLiveInd(true);
        db.setOrgId(requestMapper.getOrgId());
        db.setPublishInd(false);
        db.setUserId(requestMapper.getUserId());
        db.setCreationDate(new Date());
        db.setUpdationDate(new Date());
        db.setUpdatedBy(requestMapper.getUserId());
        db.setType(requestMapper.getType());
        Ids = WorkflowDetailsRepository.save(db).getWorkflowDetailsId();
        for (int i = 0; i < names.size(); i++) {
            Stages unboardingStages = new Stages();
            unboardingStages.setStageName(names.get(i));
            unboardingStages.setLiveInd(true);
            unboardingStages.setUserId(requestMapper.getUserId());
            unboardingStages.setOrgId(requestMapper.getOrgId());
            unboardingStages.setPublishInd(false);
            unboardingStages.setCreationDate(new Date());
            unboardingStages.setWorkflowDetailsId(Ids);
            unboardingStages.setUpdatedBy(requestMapper.getUserId());
            unboardingStages.setUpdationDate(new Date());
            if (names.get(i) == "Won") {
                unboardingStages.setProbability(100);
            } else {
                unboardingStages.setProbability(0);
            }
            StagesRepository.save(unboardingStages).getStagesId();
        }
        WorkflowResponseMapper resultMapper = getWorkflowDetails(Ids);
        return resultMapper;
    }
    
    @Override
	public boolean workflowExistsByName(String workflowName, String orgId, String type) {
		List<WorkflowDetails> db = WorkflowDetailsRepository.findByWorkflowNameAndOrgIdAndTypeAndLiveInd(workflowName,orgId,type,true);
		if (null != db && !db.isEmpty()) {
			return true;
		}
		return false;
	}


    private WorkflowResponseMapper getWorkflowDetails(String WorkflowDetailsId) {
        WorkflowDetails db = WorkflowDetailsRepository
                .getByWorkflowDetailsId(WorkflowDetailsId);
        WorkflowResponseMapper responseMapper = new WorkflowResponseMapper();
        if (null != db) {
            responseMapper.setWorkflowDetailsId(WorkflowDetailsId);
            responseMapper.setWorkflowName(db.getWorkflowName());
            responseMapper.setLiveInd(db.isLiveInd());
            responseMapper.setPublishInd(db.isPublishInd());
            responseMapper.setOrgId(db.getOrgId());
            responseMapper.setUserId(db.getUserId());
            responseMapper.setCreationDate(Utility.getISOFromDate(db.getCreationDate()));
            responseMapper.setUpdationDate(Utility.getISOFromDate(db.getUpdationDate()));
            responseMapper.setName(employeeService.getEmployeeFullName(db.getUpdatedBy()));
            responseMapper.setType(db.getType());
            if (!StringUtils.isEmpty(db.getType())) {
                WorkflowCategory workflowCategory = workflowCategoryRepo.findById(db.getType()).orElse(null);
                if (null != workflowCategory) {
                    responseMapper.setTypeName(workflowCategory.getName());
                }
            }
            responseMapper.setGlobalInd(db.isGlobalInd());

        }
        return responseMapper;
    }

    @Override
    public List<WorkflowResponseMapper> getWorkflowListByOrgId(String orgId, String type) {
        List<WorkflowDetails> list = WorkflowDetailsRepository.findByOrgIdAndTypeAndLiveInd(orgId, type, true);
        List<WorkflowResponseMapper> mapperList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            list.stream().map(li -> {
                WorkflowResponseMapper responseMapper = new WorkflowResponseMapper();
                responseMapper.setWorkflowDetailsId(li.getWorkflowDetailsId());
                responseMapper.setWorkflowName(li.getWorkflowName());
                responseMapper.setLiveInd(li.isLiveInd());
                responseMapper.setPublishInd(li.isPublishInd());
                responseMapper.setOrgId(li.getOrgId());
                responseMapper.setUserId(li.getUserId());
                responseMapper.setGlobalInd(li.isGlobalInd());
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
    public WorkflowResponseMapper updateWorkflowDetails(String WorkflowDetailsId,
                                                        WorkflowRequestMapper requestMapper) {
        WorkflowDetails db = WorkflowDetailsRepository
                .getByWorkflowDetailsId(WorkflowDetailsId);
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
            WorkflowDetailsRepository.save(db);
        }
        WorkflowResponseMapper responseMapper = getWorkflowDetails(WorkflowDetailsId);
        return responseMapper;
    }

    @Override
    public void deleteWorkflowDetails(String WorkflowDetailsId, String userId) {
        WorkflowDetails db = WorkflowDetailsRepository
                .getByWorkflowDetailsId(WorkflowDetailsId);
        if (null != db) {
            db.setUpdationDate(new Date());
            db.setUpdatedBy(userId);
            db.setLiveInd(false);
            WorkflowDetailsRepository.save(db);
        }
    }

    @Override
    public boolean stageExistsByWeightage(double probability, String WorkflowDetailsId) {
        List<Stages> list = StagesRepository
                .findByWorkflowDetailsIdAndLiveInd(WorkflowDetailsId, true);
        if (null != list && !list.isEmpty()) {
            for (Stages Stages : list) {
                if (Stages.getProbability() == probability) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public StagesResponseMapper saveStages(StagesRequestMapper requestMapper) {
        String Ids = null;
        Stages db = new Stages();

        db.setStageName(requestMapper.getStageName());
        db.setLiveInd(true);
        db.setUserId(requestMapper.getUserId());
        db.setOrgId(requestMapper.getOrgId());
        db.setPublishInd(false);
        db.setProbability(requestMapper.getProbability());
        db.setDays(requestMapper.getDays());
        // newOpportunityStages.setResponsible(opportunityStagesMapper.getResponsible());
        db.setCreationDate(new Date());
        db.setWorkflowDetailsId(requestMapper.getWorkflowDetailsId());
        db.setUpdationDate(new Date());
        db.setUpdatedBy(requestMapper.getUserId());
        Ids = StagesRepository.save(db).getStagesId();
        StagesResponseMapper resultMapper = getStagesDetails(Ids);
        return resultMapper;
    }

    private StagesResponseMapper getStagesDetails(String StagesId) {
        Stages db = StagesRepository.getByStagesId(StagesId);
        System.out.println("supplierUnboardingStages ------" + db.getWorkflowDetailsId());
        StagesResponseMapper responseMapper = new StagesResponseMapper();
        if (null != db) {
            System.out.println("supplierUnboardingStages22 ------" + db.getWorkflowDetailsId());
            responseMapper.setStagesId(db.getStagesId());
            responseMapper.setStageName(db.getStageName());
            responseMapper.setLiveInd(db.isLiveInd());
            responseMapper.setPublishInd(db.isPublishInd());
            responseMapper.setOrgId(db.getOrgId());
            responseMapper.setProbability(db.getProbability());
            responseMapper.setDays(db.getDays());
            responseMapper.setCreationDate(Utility.getISOFromDate(db.getCreationDate()));
            responseMapper.setUserId(db.getUserId());
            responseMapper.setWorkflowDetailsId(db.getWorkflowDetailsId());
            responseMapper.setUpdationDate(Utility.getISOFromDate(db.getUpdationDate()));
            responseMapper.setName(employeeService.getEmployeeFullName(db.getUpdatedBy()));
            responseMapper.setGlobalInd(db.isGlobalInd());
            System.out.println("getEmployeeFullName ------" + employeeService.getEmployeeFullName(db.getUpdatedBy()));
        }
        return responseMapper;
    }

    @Override
    public List<StagesResponseMapper> getStagesByWorkflowDetailsId(
            String WorkflowDetailsId) {
        List<Stages> list = StagesRepository
                .findByWorkflowDetailsIdAndLiveInd(WorkflowDetailsId, true);
        List<StagesResponseMapper> mapperList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            list.stream().map(li -> {
                if (!li.getStageName().equalsIgnoreCase("Lost")) {
                    if (!li.getStageName().equalsIgnoreCase("Won")) {
                        StagesResponseMapper responseMapper = new StagesResponseMapper();
                        responseMapper.setStagesId(li.getStagesId());
                        responseMapper.setStageName(li.getStageName());
                        responseMapper.setLiveInd(li.isLiveInd());
                        responseMapper.setPublishInd(li.isPublishInd());
                        responseMapper.setOrgId(li.getOrgId());
                        responseMapper.setUserId(li.getUserId());
                        responseMapper.setProbability(li.getProbability());
                        responseMapper.setDays(li.getDays());
                        responseMapper.setGlobalInd(li.isGlobalInd());
                        // opportunityStagesMapper.setResponsible(li.getResponsible());
                        responseMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
                        responseMapper.setWorkflowDetailsId(li.getWorkflowDetailsId());
                        responseMapper.setUpdationDate(Utility.getISOFromDate(li.getUpdationDate()));
                        mapperList.add(responseMapper);
                        if (null != mapperList && !mapperList.isEmpty()) {

                            Collections.sort(mapperList,
                                    (StagesResponseMapper c1, StagesResponseMapper c2) -> Double
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
    public StagesResponseMapper updateStagesId(String StagesId,
                                               StagesRequestMapper requestMapper) {
        Stages db = StagesRepository.getByStagesId(StagesId);
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
            StagesRepository.save(db);
        }
        StagesResponseMapper responseMapper = getStagesDetails(StagesId);
        return responseMapper;
    }

    @Override
    public void deleteStagesById(String StagesId, String userId) {
        Stages db = StagesRepository.getByStagesId(StagesId);
        if (null != db) {
            db.setUpdationDate(new Date());
            db.setUpdatedBy(userId);
            db.setLiveInd(false);
            StagesRepository.save(db);
        }
    }

    @Override
    public StagesResponseMapper updateStagesPubliahInd(
            StagesRequestMapper requestMapper) {
        StagesResponseMapper resultMapper = null;
        if (null != requestMapper.getStagesId()) {
            Stages db = StagesRepository
                    .getByStagesId(requestMapper.getStagesId());
            if (null != db.getStagesId()) {
                db.setPublishInd(requestMapper.isPublishInd());
                StagesRepository.save(db);
            }
            resultMapper = getStagesDetails(requestMapper.getStagesId());
        }
        return resultMapper;
    }

    @Override
    public WorkflowResponseMapper updateWorkflowDetailsPublishInd(
            StagesRequestMapper requestMapper) {
        WorkflowResponseMapper resultMapper = null;
        if (null != requestMapper.getWorkflowDetailsId()) {
            WorkflowDetails db = WorkflowDetailsRepository
                    .getByWorkflowDetailsId(requestMapper.getWorkflowDetailsId());
            if (null != db) {
                db.setPublishInd(requestMapper.isPublishInd());
                WorkflowDetailsRepository.save(db);
            }
            resultMapper = getWorkflowDetails(requestMapper.getWorkflowDetailsId());
        }
        return resultMapper;
    }

    @Override
    public List<WorkflowResponseMapper> getWorkflowListByOrgIdForDropdown(String orgId, String type) {
        List<WorkflowDetails> list = WorkflowDetailsRepository
                .findByOrgIdAndLiveIndAndPublishIndAndType(orgId, true, true, type);
        List<WorkflowResponseMapper> mapperList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            list.stream().map(li -> {
                WorkflowResponseMapper mapper = getWorkflowDetails(
                        li.getWorkflowDetailsId());
                mapperList.add(mapper);
                return mapperList;
            }).collect(Collectors.toList());
        }
        return mapperList;
    }

    @Override
    public List<StagesResponseMapper> getStagesByOrgIdForDropdown(String orgId) {
        List<Stages> list = StagesRepository.findByOrgIdAndLiveIndAndPublishInd(orgId, true, true);
        List<StagesResponseMapper> mapperList = new ArrayList<>();
        System.out.println("opportunityStagesList+++++++++++++++++=========" + list.toString());
        if (null != list && !list.isEmpty()) {
            list.stream().map(li -> {
                System.out.println("getOrgId111+++++++++++++++++=========" + li.getOrgId());
                if (!li.getStageName().equalsIgnoreCase("Lost")) {
                    if (!li.getStageName().equalsIgnoreCase("Won")) {
                        System.out.println("getOrgId222+++++++++++++++++=========" + li.getOrgId());
                        StagesResponseMapper resultMapper = getStagesDetails(
                                li.getStagesId());
                        mapperList.add(resultMapper);
                    }
                }
                return mapperList;
            }).collect(Collectors.toList());
        }
        return mapperList;
    }

    @Override
    public WorkflowResponseMapper updateGlobalIndForWorkflow(String orgId, boolean globalInd, String workflowId) {
        WorkflowResponseMapper mapper = new WorkflowResponseMapper();
        WorkflowDetails workflowDetails = WorkflowDetailsRepository.findByWorkflowDetailsIdAndOrgId(workflowId, orgId);
        if (null != workflowDetails) {
            workflowDetails.setGlobalInd(globalInd);
            List<Stages> stages = StagesRepository.findByWorkflowDetailsIdAndLiveInd(workflowId, true);
            if (null != stages && !stages.isEmpty()) {
                for (Stages stage : stages) {
                    stage.setGlobalInd(globalInd);
                    StagesRepository.save(stage);
                    
                    List<StagesTask> list = stagesTaskRepository
                            .findByStageIdAndOrgIdAndLiveInd(stage.getStagesId(), orgId, true);
                    if (null != list && !list.isEmpty()) {
                        list.stream().forEach(li -> {
                        	 li.setGlobalInd(globalInd);
                        	 stagesTaskRepository.save(li);
                        });
                    }
                }
            }
            WorkflowDetailsRepository.save(workflowDetails);
            mapper = getWorkflowDetails(workflowDetails.getWorkflowDetailsId());
        }
        return mapper;
    }

    @Override
    public List<WorkflowResponseMapper> listOfWorkflowWithGlobalInd(String orgId, String type) {
        List<WorkflowDetails> detailsList = WorkflowDetailsRepository.findByOrgIdAndTypeAndLiveIndAndGlobalInd(orgId, type, true, true);
        return detailsList.stream().map(w -> getWorkflowDetails(w.getWorkflowDetailsId())).collect(Collectors.toList());
    }

    @Override
    public WorkflowResponseMapper createNewWorkflowForOrg(String orgId, String workflowId, String userId, String type) {
        WorkflowResponseMapper workflowResponseMapper = new WorkflowResponseMapper();
        WorkflowDetails workflowDetails = WorkflowDetailsRepository.findById(workflowId).orElse(null);
        if (null != workflowDetails) {
            WorkflowDetails workflow1 = WorkflowDetailsRepository.
                    findByWorkflowNameAndOrgIdAndLiveInd(workflowDetails.getWorkflowName(), orgId, true);
            if (null == workflow1) {
                throw new ResponseStatusException(HttpStatus.OK, "Workflow Already Exist For This Organization");
            } else {
                WorkflowDetails workflow = new WorkflowDetails();
                workflow.setWorkflowName(workflowDetails.getWorkflowName());
                workflow.setLiveInd(workflowDetails.isLiveInd());
                workflow.setOrgId(orgId);
                workflow.setPublishInd(workflowDetails.isPublishInd());
                workflow.setUserId(userId);
                workflow.setCreationDate(new Date());
                workflow.setUpdationDate(new Date());
                workflow.setUpdatedBy(userId);
                workflow.setType(type);
                WorkflowDetails details = WorkflowDetailsRepository.save(workflow);
                workflowResponseMapper = getWorkflowDetails(details.getWorkflowDetailsId());

                List<Stages> stages = StagesRepository.findByWorkflowDetailsIdAndLiveInd(workflowDetails.getWorkflowDetailsId(), true);
                if (null != stages && !stages.isEmpty()) {
                    for (Stages stage : stages) {
                        Stages oppWFstages = new Stages();
                        oppWFstages.setStageName(stage.getStageName());
                        oppWFstages.setLiveInd(stage.isLiveInd());
                        oppWFstages.setUserId(userId);
                        oppWFstages.setOrgId(orgId);
                        oppWFstages.setPublishInd(stage.isPublishInd());
                        oppWFstages.setCreationDate(new Date());
                        oppWFstages.setWorkflowDetailsId(details.getWorkflowDetailsId());
                        oppWFstages.setUpdatedBy(userId);
                        oppWFstages.setUpdationDate(new Date());
                        oppWFstages.setProbability(stage.getProbability());

                        StagesRepository.save(oppWFstages);
                        
                        List<StagesTask> list = stagesTaskRepository
                                .findByStageIdAndOrgIdAndLiveInd(stage.getStagesId(), orgId, true);
                        if (null != list && !list.isEmpty()) {
                            list.stream().forEach(li -> {
                            	StagesTask db = new StagesTask();

                                db.setStageTaskName(li.getStageTaskName());
                                db.setLiveInd(true);
                                db.setUserId(userId);
                                db.setOrgId(orgId);
                                db.setMandatoryInd(false);
                                db.setDepartmentId(li.getDepartmentId());
                                db.setStageId(li.getStageId());
                                db.setCreationDate(new Date());
                                db.setUpdationDate(new Date());
                                db.setUpdatedBy(userId);
                                stagesTaskRepository.save(db);
                            });
                        }
                        
                    }
                }
            }
        }
        return workflowResponseMapper;
    }

    @Override
    public StagesTaskResponseMapper createStageTask(StagesTaskRequestMapper requestMapper) {
        String Id = null;
        StagesTask db = new StagesTask();

        db.setStageTaskName(requestMapper.getStageTaskName());
        db.setLiveInd(true);
        db.setUserId(requestMapper.getUserId());
        db.setOrgId(requestMapper.getOrgId());
        db.setMandatoryInd(false);
        db.setDepartmentId(requestMapper.getDepartmentId());
        db.setStageId(requestMapper.getStageId());
        db.setCreationDate(new Date());
        db.setUpdationDate(new Date());
        db.setUpdatedBy(requestMapper.getUserId());
        Id = stagesTaskRepository.save(db).getStagesTaskId();
        StagesTaskResponseMapper resultMapper = getStagesTaskDetails(Id);
        return resultMapper;
    }

    private StagesTaskResponseMapper getStagesTaskDetails(String StagesTaskId) {
        StagesTaskResponseMapper responseMapper = new StagesTaskResponseMapper();
        StagesTask db = stagesTaskRepository.getByStagesTaskId(StagesTaskId);
        if (null != db) {
            responseMapper.setStagesId(db.getStageId());
            Stages db1 = StagesRepository.getByStagesId(db.getStageId());
            if (null != db1) {
                responseMapper.setStageName(db1.getStageName());
            }
            responseMapper.setLiveInd(db.isLiveInd());
            responseMapper.setMandatoryInd(db.isMandatoryInd());
            responseMapper.setOrgId(db.getOrgId());
            responseMapper.setUserId(db.getUserId());
            responseMapper.setDepartmentId(db.getDepartmentId());
            Department dbDepartment = departmentRepository.getDepartmentDetails(db.getDepartmentId());
            if (null != dbDepartment) {
                responseMapper.setDepartment(dbDepartment.getDepartmentName());
            }
            responseMapper.setStagesTaskId(db.getStagesTaskId());
            responseMapper.setStageTaskName(db.getStageTaskName());
            responseMapper.setCreationDate(Utility.getISOFromDate(db.getCreationDate()));
            responseMapper.setUpdationDate(Utility.getISOFromDate(db.getUpdationDate()));
            responseMapper.setUpdatedBy(employeeService.getEmployeeFullName(db.getUpdatedBy()));
            responseMapper.setGlobalInd(db.isGlobalInd());
            System.out.println("getEmployeeFullName ------" + employeeService.getEmployeeFullName(db.getUpdatedBy()));
        }
        return responseMapper;
    }

    @Override
    public List<StagesTaskResponseMapper> getStageTaskByStageId(String orgId, String stageId) {
        List<StagesTaskResponseMapper> mapperList = new ArrayList<>();
        List<StagesTask> list = stagesTaskRepository
                .findByStageIdAndOrgIdAndLiveInd(stageId, orgId, true);

        if (null != list && !list.isEmpty()) {
            list.stream().map(li -> {
                StagesTaskResponseMapper mapper = getStagesTaskDetails(li.getStagesTaskId());
                if (null != mapper) {
                    mapperList.add(mapper);
                }
                return mapper;
            }).collect(Collectors.toList());
            Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
            if (null != mapperList && !mapperList.isEmpty()) {
                mapperList.get(0).setUpdationDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
                mapperList.get(0).setUpdatedBy(
                        employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
            }
        }
        return mapperList;
    }

    @Override
    public String moveWF(String type) {
        if (type.equalsIgnoreCase("opportunity")) {
            int wcount = 0;
            List<OpportunityWorkflowDetails> workflowDetailss = oppWFRepository.findAll().stream().filter(a -> a.isLiveInd()).collect(Collectors.toList());
            if (null != workflowDetailss && !workflowDetailss.isEmpty()) {
                for (OpportunityWorkflowDetails workflowDetails : workflowDetailss) {
                    WorkflowDetails workflow = new WorkflowDetails();
                    workflow.setWorkflowName(workflowDetails.getWorkflowName());
                    workflow.setLiveInd(workflowDetails.isLiveInd());
                    workflow.setOrgId(workflowDetails.getOrgId());
                    workflow.setPublishInd(workflowDetails.isPublishInd());
                    workflow.setUserId(workflow.getUserId());
                    workflow.setCreationDate(workflowDetails.getCreationDate());
                    workflow.setUpdationDate(new Date());
                    workflow.setUpdatedBy(workflow.getUserId());
                    workflow.setType("Opportunity");
                    WorkflowDetails detailss = WorkflowDetailsRepository.save(workflow);
                    List<OpportunityStages> opportunityStages = oppStagesRepository.findByOpportunityWorkflowDetailsIdAndLiveInd(workflowDetails.getOpportunityWorkflowDetailsId(), true);
                    if (null != opportunityStages && !opportunityStages.isEmpty()) {
                        for (OpportunityStages stage : opportunityStages) {
                            Stages oppWFstages = new Stages();
                            oppWFstages.setStageName(stage.getStageName());
                            oppWFstages.setLiveInd(stage.isLiveInd());
                            oppWFstages.setUserId(stage.getUserId());
                            oppWFstages.setOrgId(stage.getOrgId());
                            oppWFstages.setPublishInd(stage.isPublishInd());
                            oppWFstages.setCreationDate(new Date());
                            oppWFstages.setWorkflowDetailsId(detailss.getWorkflowDetailsId());
                            oppWFstages.setUpdatedBy(stage.getUserId());
                            oppWFstages.setUpdationDate(new Date());
                            oppWFstages.setProbability(stage.getProbability());
                            StagesRepository.save(oppWFstages);
                        }
                    }
                    wcount++;
                }

            }
            return "import success  " + type + " " + wcount;
        } else if (type.equalsIgnoreCase("investorOpportunity")) {
            int wcount = 0;
            List<InvestorOppWorkflow> workflowDetailss = investorOppWorkflowRepo.findAll().stream().filter(a -> a.isLiveInd()).collect(Collectors.toList());
            if (null != workflowDetailss && !workflowDetailss.isEmpty()) {
                for (InvestorOppWorkflow workflowDetails : workflowDetailss) {
                    WorkflowDetails workflow = new WorkflowDetails();
                    workflow.setWorkflowName(workflowDetails.getWorkflowName());
                    workflow.setLiveInd(workflowDetails.isLiveInd());
                    workflow.setOrgId(workflowDetails.getOrgId());
                    workflow.setPublishInd(workflowDetails.isPublishInd());
                    workflow.setUserId(workflow.getUserId());
                    workflow.setCreationDate(workflowDetails.getCreationDate());
                    workflow.setUpdationDate(new Date());
                    workflow.setUpdatedBy(workflow.getUserId());
                    workflow.setType("InvestorOpportunity");
                    WorkflowDetails detailss = WorkflowDetailsRepository.save(workflow);
                    List<InvestorOppStages> opportunityStages = investorOppStagesRepo.findByInvestorOppWorkflowIdAndLiveInd(workflowDetails.getInvestorOppWorkflowId(), true);
                    if (null != opportunityStages && !opportunityStages.isEmpty()) {
                        for (InvestorOppStages stage : opportunityStages) {
                            Stages oppWFstages = new Stages();
                            oppWFstages.setStageName(stage.getStageName());
                            oppWFstages.setLiveInd(stage.isLiveInd());
                            oppWFstages.setUserId(stage.getUserId());
                            oppWFstages.setOrgId(stage.getOrgId());
                            oppWFstages.setPublishInd(stage.isPublishInd());
                            oppWFstages.setCreationDate(new Date());
                            oppWFstages.setWorkflowDetailsId(detailss.getWorkflowDetailsId());
                            oppWFstages.setUpdatedBy(stage.getUserId());
                            oppWFstages.setUpdationDate(new Date());
                            oppWFstages.setProbability(stage.getProbability());
                            StagesRepository.save(oppWFstages);
                        }
                    }
                    wcount++;
                }

            }
            return "import success  " + type + " " + wcount;
        } else if (type.equalsIgnoreCase("userOnboarding")) {
            int wcount = 0;
            List<UnboardingWorkflowDetails> workflowDetailss = unboardingWorkflowDetailsRepository.findAll().stream().filter(a -> a.isLiveInd()).collect(Collectors.toList());
            if (null != workflowDetailss && !workflowDetailss.isEmpty()) {
                for (UnboardingWorkflowDetails workflowDetails : workflowDetailss) {
                    WorkflowDetails workflow = new WorkflowDetails();
                    workflow.setWorkflowName(workflowDetails.getWorkflowName());
                    workflow.setLiveInd(workflowDetails.isLiveInd());
                    workflow.setOrgId(workflowDetails.getOrgId());
                    workflow.setPublishInd(workflowDetails.isPublishInd());
                    workflow.setUserId(workflow.getUserId());
                    workflow.setCreationDate(workflowDetails.getCreationDate());
                    workflow.setUpdationDate(new Date());
                    workflow.setUpdatedBy(workflow.getUserId());
                    workflow.setType("UserOnboarding");
                    WorkflowDetails detailss = WorkflowDetailsRepository.save(workflow);
                    List<UnboardingStages> opportunityStages = unboardingStagesRepository.findByUnboardingWorkflowDetailsIdAndLiveInd(workflowDetails.getUnboardingWorkflowDetailsId(), true);
                    if (null != opportunityStages && !opportunityStages.isEmpty()) {
                        for (UnboardingStages stage : opportunityStages) {
                            Stages oppWFstages = new Stages();
                            oppWFstages.setStageName(stage.getStageName());
                            oppWFstages.setLiveInd(stage.isLiveInd());
                            oppWFstages.setUserId(stage.getUserId());
                            oppWFstages.setOrgId(stage.getOrgId());
                            oppWFstages.setPublishInd(stage.isPublishInd());
                            oppWFstages.setCreationDate(new Date());
                            oppWFstages.setWorkflowDetailsId(detailss.getWorkflowDetailsId());
                            oppWFstages.setUpdatedBy(stage.getUserId());
                            oppWFstages.setUpdationDate(new Date());
                            oppWFstages.setProbability(stage.getProbability());
                            StagesRepository.save(oppWFstages);
                        }
                    }
                    wcount++;
                }

            }
            return "import success  " + type + " " + wcount;
        } else if (type.equalsIgnoreCase("supplierOnboarding")) {
            int wcount = 0;
            List<SupplierUnboardingWorkflowDetails> workflowDetailss = supplierUnboardingWorkflowDetailsRepository.findAll().stream().filter(a -> a.isLiveInd()).collect(Collectors.toList());
            if (null != workflowDetailss && !workflowDetailss.isEmpty()) {
                for (SupplierUnboardingWorkflowDetails workflowDetails : workflowDetailss) {
                    WorkflowDetails workflow = new WorkflowDetails();
                    workflow.setWorkflowName(workflowDetails.getWorkflowName());
                    workflow.setLiveInd(workflowDetails.isLiveInd());
                    workflow.setOrgId(workflowDetails.getOrgId());
                    workflow.setPublishInd(workflowDetails.isPublishInd());
                    workflow.setUserId(workflow.getUserId());
                    workflow.setCreationDate(workflowDetails.getCreationDate());
                    workflow.setUpdationDate(new Date());
                    workflow.setUpdatedBy(workflow.getUserId());
                    workflow.setType("SupplierOnboarding");
                    WorkflowDetails detailss = WorkflowDetailsRepository.save(workflow);
                    List<SupplierUnboardingStages> opportunityStages = supplierUnboardingStagesRepository.findBySupplierUnboardingWorkflowDetailsIdAndLiveInd(workflowDetails.getSupplierUnboardingWorkflowDetailsId(), true);
                    if (null != opportunityStages && !opportunityStages.isEmpty()) {
                        for (SupplierUnboardingStages stage : opportunityStages) {
                            Stages oppWFstages = new Stages();
                            oppWFstages.setStageName(stage.getStageName());
                            oppWFstages.setLiveInd(stage.isLiveInd());
                            oppWFstages.setUserId(stage.getUserId());
                            oppWFstages.setOrgId(stage.getOrgId());
                            oppWFstages.setPublishInd(stage.isPublishInd());
                            oppWFstages.setCreationDate(new Date());
                            oppWFstages.setWorkflowDetailsId(detailss.getWorkflowDetailsId());
                            oppWFstages.setUpdatedBy(stage.getUserId());
                            oppWFstages.setUpdationDate(new Date());
                            oppWFstages.setProbability(stage.getProbability());
                            StagesRepository.save(oppWFstages);
                        }
                    }
                    wcount++;
                }

            }
            return "import success  " + type + " " + wcount;
        } else if (type.equalsIgnoreCase("production")) {
            int wcount = 0;
            List<ProductionWorkflowDetails> workflowDetailss = productionWorkflowDetailsRepository.findAll().stream().filter(a -> a.isLiveInd()).collect(Collectors.toList());
            if (null != workflowDetailss && !workflowDetailss.isEmpty()) {
                for (ProductionWorkflowDetails workflowDetails : workflowDetailss) {
                    WorkflowDetails workflow = new WorkflowDetails();
                    workflow.setWorkflowName(workflowDetails.getWorkflowName());
                    workflow.setLiveInd(workflowDetails.isLiveInd());
                    workflow.setOrgId(workflowDetails.getOrgId());
                    workflow.setPublishInd(workflowDetails.isPublishInd());
                    workflow.setUserId(workflow.getUserId());
                    workflow.setCreationDate(workflowDetails.getCreationDate());
                    workflow.setUpdationDate(new Date());
                    workflow.setUpdatedBy(workflow.getUserId());
                    workflow.setType("Production");
                    WorkflowDetails detailss = WorkflowDetailsRepository.save(workflow);
                    List<ProductionStages> opportunityStages = productionStagesRepository.findByProductionWorkflowDetailsIdAndLiveInd(workflowDetails.getProductionWorkflowDetailsId(), true);
                    if (null != opportunityStages && !opportunityStages.isEmpty()) {
                        for (ProductionStages stage : opportunityStages) {
                            Stages oppWFstages = new Stages();
                            oppWFstages.setStageName(stage.getStageName());
                            oppWFstages.setLiveInd(stage.isLiveInd());
                            oppWFstages.setUserId(stage.getUserId());
                            oppWFstages.setOrgId(stage.getOrgId());
                            oppWFstages.setPublishInd(stage.isPublishInd());
                            oppWFstages.setCreationDate(new Date());
                            oppWFstages.setWorkflowDetailsId(detailss.getWorkflowDetailsId());
                            oppWFstages.setUpdatedBy(stage.getUserId());
                            oppWFstages.setUpdationDate(new Date());
                            oppWFstages.setProbability(stage.getProbability());
                            StagesRepository.save(oppWFstages);
                        }
                    }
                    wcount++;
                }

            }
            return "import success  " + type + " " + wcount;
        } else if (type.equalsIgnoreCase("repair")) {
            int wcount = 0;
            List<RepairWorkflowDetails> workflowDetailss = repairWorkflowDetailsRepository.findAll().stream().filter(a -> a.isLiveInd()).collect(Collectors.toList());
            if (null != workflowDetailss && !workflowDetailss.isEmpty()) {
                for (RepairWorkflowDetails workflowDetails : workflowDetailss) {
                    WorkflowDetails workflow = new WorkflowDetails();
                    workflow.setWorkflowName(workflowDetails.getWorkflowName());
                    workflow.setLiveInd(workflowDetails.isLiveInd());
                    workflow.setOrgId(workflowDetails.getOrgId());
                    workflow.setPublishInd(workflowDetails.isPublishInd());
                    workflow.setUserId(workflow.getUserId());
                    workflow.setCreationDate(workflowDetails.getCreationDate());
                    workflow.setUpdationDate(new Date());
                    workflow.setUpdatedBy(workflow.getUserId());
                    workflow.setType("Repair");
                    WorkflowDetails detailss = WorkflowDetailsRepository.save(workflow);
                    List<RepairStages> opportunityStages = repairStagesRepository.findByRepairWorkflowDetailsIdAndLiveInd(workflowDetails.getRepairWorkflowDetailsId(), true);
                    if (null != opportunityStages && !opportunityStages.isEmpty()) {
                        for (RepairStages stage : opportunityStages) {
                            Stages oppWFstages = new Stages();
                            oppWFstages.setStageName(stage.getStageName());
                            oppWFstages.setLiveInd(stage.isLiveInd());
                            oppWFstages.setUserId(stage.getUserId());
                            oppWFstages.setOrgId(stage.getOrgId());
                            oppWFstages.setPublishInd(stage.isPublishInd());
                            oppWFstages.setCreationDate(new Date());
                            oppWFstages.setWorkflowDetailsId(detailss.getWorkflowDetailsId());
                            oppWFstages.setUpdatedBy(stage.getUserId());
                            oppWFstages.setUpdationDate(new Date());
                            oppWFstages.setProbability(stage.getProbability());
                            StagesRepository.save(oppWFstages);
                        }
                    }
                    wcount++;
                }

            }
            return "import success  " + type + " " + wcount;
        } else if (type.equalsIgnoreCase("task")) {
            int wcount = 0;
            List<TaskChecklist> workflowDetailss = taskChecklistRepository.findAll().stream().filter(a -> a.isLiveInd()).collect(Collectors.toList());
            if (null != workflowDetailss && !workflowDetailss.isEmpty()) {
                for (TaskChecklist workflowDetails : workflowDetailss) {
                    WorkflowDetails workflow = new WorkflowDetails();
                    workflow.setWorkflowName(workflowDetails.getTaskChecklistName());
                    workflow.setLiveInd(workflowDetails.isLiveInd());
                    workflow.setOrgId(workflowDetails.getOrgId());
//                    workflow.setPublishInd(workflowDetails.isPublishInd());
                    workflow.setUserId(workflow.getUserId());
                    workflow.setCreationDate(workflowDetails.getCreationDate());
                    workflow.setUpdationDate(new Date());
                    workflow.setUpdatedBy(workflow.getUserId());
                    workflow.setType("Task");
                    WorkflowDetails detailss = WorkflowDetailsRepository.save(workflow);
                    List<TaskChecklistStageLink> opportunityStages = taskChecklistStageLinkRepository.findByTaskChecklistIdAndLiveInd(workflowDetails.getTaskChecklistId(), true);
                    if (null != opportunityStages && !opportunityStages.isEmpty()) {
                        for (TaskChecklistStageLink stage : opportunityStages) {
                            Stages oppWFstages = new Stages();
                            oppWFstages.setStageName(stage.getTaskChecklistStageName());
                            oppWFstages.setLiveInd(stage.isLiveInd());
                            oppWFstages.setUserId(stage.getUserId());
                            oppWFstages.setOrgId(stage.getOrgId());
//                            oppWFstages.setPublishInd(stage.isPublishInd());
                            oppWFstages.setCreationDate(new Date());
                            oppWFstages.setWorkflowDetailsId(detailss.getWorkflowDetailsId());
                            oppWFstages.setUpdatedBy(stage.getUserId());
                            oppWFstages.setUpdationDate(new Date());
                            oppWFstages.setProbability(stage.getProbability());
                            StagesRepository.save(oppWFstages);
                        }
                    }
                    wcount++;
                }
            }
            return "import success  " + type + " " + wcount;
        } else {
            return type;
        }
    }

    @Override
    public StagesTaskResponseMapper updateMandatoryIndForStagesTask(String stagesTaskId, boolean mandatoryInd) {
        StagesTaskResponseMapper responseMapper = new StagesTaskResponseMapper();
        StagesTask stagesTask = stagesTaskRepository.findById(stagesTaskId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "stagestask ot found with id " + stagesTaskId));
        if (null != stagesTask) {
            stagesTask.setMandatoryInd(mandatoryInd);
            responseMapper = getStagesTaskDetails(stagesTaskRepository.save(stagesTask).getStagesTaskId());
        }
        return responseMapper;
    }

    @Override
    public StagesTaskResponseMapper updateStagesTask(String stagesTaskId, StagesTaskRequestMapper requestMapper) {
        StagesTaskResponseMapper mapper = new StagesTaskResponseMapper();
        StagesTask stagesTask = stagesTaskRepository.getByStagesTaskId(stagesTaskId);
        if (null != stagesTask) {
            if (null != requestMapper.getStageTaskName() && !requestMapper.getStageTaskName().isEmpty()) {
                stagesTask.setStageTaskName(requestMapper.getStageTaskName());
            }
            if (null != requestMapper.getDepartmentId() && !requestMapper.getDepartmentId().isEmpty()) {
                stagesTask.setDepartmentId(requestMapper.getDepartmentId());
            }
            stagesTask.setUpdationDate(new Date());
            stagesTask.setUpdatedBy(requestMapper.getUserId());
            mapper = getStagesTaskDetails(stagesTaskRepository.save(stagesTask).getStagesTaskId());
        }
        return mapper;
    }

    @Override
    public String deleteReinstateStagesTask(String stagesTaskId, boolean liveInd, String userId) {
        String msg = null;
        StagesTask stagesTask = stagesTaskRepository.getByStagesTaskId(stagesTaskId);
        if (null != stagesTask) {
            if (!liveInd) {
                stagesTask.setLiveInd(false);
                stagesTask.setUpdationDate(new Date());
                stagesTask.setUpdatedBy(userId);
                stagesTaskRepository.save(stagesTask);
                msg = "deleted successfully";
            } else {
                stagesTask.setLiveInd(true);
                stagesTask.setUpdationDate(new Date());
                stagesTask.setUpdatedBy(userId);
                stagesTaskRepository.save(stagesTask);
                msg = "reinstated successfully";
            }
        }
        return msg;
    }

}
