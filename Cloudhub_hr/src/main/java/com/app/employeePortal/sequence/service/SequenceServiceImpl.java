package com.app.employeePortal.sequence.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.sequence.entity.Sequence;
import com.app.employeePortal.sequence.entity.SequenceDelete;
import com.app.employeePortal.sequence.entity.SequenceRule;
import com.app.employeePortal.sequence.mapper.SequenceMapper;
import com.app.employeePortal.sequence.mapper.SequenceRuleMapper;
import com.app.employeePortal.sequence.repository.SequenceDeleteRepository;
import com.app.employeePortal.sequence.repository.SequenceRepository;
import com.app.employeePortal.sequence.repository.SequenceRuleRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class SequenceServiceImpl implements SequenceService {
	@Autowired
	SequenceRepository sequenceRepository;
	@Autowired
	SequenceRuleRepository sequenceRuleRepository;
	@Autowired
	SequenceDeleteRepository sequenceDeleteRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	
	
	@Override
	public String saveSequence(SequenceMapper sequenceMapper) {
		String sequenceId=null;
		if(sequenceMapper != null) {
			Sequence sequence = new Sequence();
			sequence.setSequenceId(sequenceMapper.getSequenceId());
			sequence.setName(sequenceMapper.getName());
			sequence.setOrgId(sequenceMapper.getOrgId());
			//sequence.setCreationDate(new Date());
			sequence.setUserId(sequenceMapper.getUserId());
			sequence.setPriority(sequenceMapper.getPriority());
			sequence.setType(sequenceMapper.getType());
			sequence.setNoOfDays(sequenceMapper.getNoOfDays());

			sequenceId= sequenceRepository.save(sequence).getSequenceId();
			
			
			SequenceDelete sequenceDelete=new SequenceDelete(); 
			sequenceDelete.setSequenceId(sequenceId);
			sequenceDelete.setOrgId(sequenceMapper.getOrgId());
			sequenceDelete.setUserId(sequenceMapper.getUserId());
			sequenceDelete.setUpdationDate(new Date());
			sequenceDeleteRepository.save(sequenceDelete);
		}
		return sequenceId;
	}

	@Override
	public List<SequenceMapper> getSequenceByOrgId(String orgId) {
		
		List<Sequence> sequence = sequenceRepository.getSequenceByOrgId(orgId);

		List<SequenceMapper> sequenceMapper = new ArrayList<>();

		if (null != sequence) {
			
			sequence.stream().map(sequence1 ->  {
				SequenceMapper sequenceMapper1 =new SequenceMapper();
				sequenceMapper1.setName(sequence1.getName());
				sequenceMapper1.setOrgId(sequence1.getOrgId());
				sequenceMapper1.setUserId(sequence1.getUserId());
			//sequenceMapper.setMileageDate(Utility.getISOFromDate(mileageDetails.getMileage_date()));
				sequenceMapper1.setPriority(sequence1.getPriority());
				sequenceMapper1.setType(sequence1.getType());
				sequenceMapper1.setNoOfDays(sequence1.getNoOfDays());
				sequenceMapper1.setSequenceId(sequence1.getSequenceId());
				sequenceMapper.add(sequenceMapper1);
				return sequenceMapper;
			}).collect(Collectors.toList());
		}
		
		List<SequenceDelete> sequenceDelete = sequenceDeleteRepository.findByOrgId(orgId);
		if (null != sequenceDelete && !sequenceDelete.isEmpty()) {
			Collections.sort(sequenceDelete,
					( p1,p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			
			sequenceMapper.get(0).setUpdationDate(Utility.getISOFromDate(sequenceDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(sequenceDelete.get(0).getUserId());
			if(null!=employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					sequenceMapper.get(0).setUpdatedName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					sequenceMapper.get(0).setUpdatedName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		
		
		return sequenceMapper;
		}

	@Override
	public SequenceRuleMapper createSequenceRule(SequenceRuleMapper sequenceRuleMapper) {
		 SequenceRule sequenceRule = sequenceRuleRepository.findBySequenceId(sequenceRuleMapper.getSequenceId());
		 SequenceRuleMapper sequenceMapper1 =new SequenceRuleMapper();
	        if (null != sequenceRule ) {
	          
	            sequenceRule.setOrgId(sequenceRuleMapper.getOrgId());
	            sequenceRule.setUserId(sequenceRuleMapper.getUserId());
	            sequenceRule.setFalseSequenceId(sequenceRuleMapper.getFalseSequenceId());
	            sequenceRule.setFalseSequenceRule(sequenceRuleMapper.getFalseSequenceRule());
	            sequenceRule.setNoInputSequenceId(sequenceRuleMapper.getNoInputSequenceId());
	            sequenceRule.setNoInputSequenceRule(sequenceRuleMapper.getNoInputSequenceRule());
	            sequenceRule.setSequenceId(sequenceRuleMapper.getSequenceId());
	            sequenceRule.setTrueSequenceId(sequenceRuleMapper.getTrueSequenceId());
	            sequenceRule.setTrueSequenceRule(sequenceRuleMapper.getTrueSequenceRule());
	            sequenceRuleRepository.save(sequenceRule);
	            sequenceMapper1= getSequenceRuleBySequenceId(sequenceRuleMapper.getSequenceId());
	            return sequenceMapper1;
	            }else { 
	               
	            	SequenceRule sequenceRule1 = new SequenceRule();
	            	sequenceRule1.setOrgId(sequenceRuleMapper.getOrgId());
	            	sequenceRule1.setUserId(sequenceRuleMapper.getUserId());
	            	sequenceRule1.setFalseSequenceId(sequenceRuleMapper.getFalseSequenceId());
	            	sequenceRule1.setFalseSequenceRule(sequenceRuleMapper.getFalseSequenceRule());
	            	sequenceRule1.setNoInputSequenceId(sequenceRuleMapper.getNoInputSequenceId());
	            	sequenceRule1.setNoInputSequenceRule(sequenceRuleMapper.getNoInputSequenceRule());
		            sequenceRule1.setSequenceId(sequenceRuleMapper.getSequenceId());
		            sequenceRule1.setTrueSequenceId(sequenceRuleMapper.getTrueSequenceId());
		            sequenceRule1.setTrueSequenceRule(sequenceRuleMapper.getTrueSequenceRule());
		            sequenceRuleRepository.save(sequenceRule1);
		            sequenceMapper1 = getSequenceRuleBySequenceId(sequenceRuleMapper.getSequenceId());
		            return sequenceMapper1;
	}
	        
	}
	
	@Override
public SequenceRuleMapper getSequenceRuleBySequenceId(String SequenceId) {
		
		SequenceRule sequence = sequenceRuleRepository.findBySequenceId(SequenceId);
		SequenceRuleMapper sequenceMapper1 =new SequenceRuleMapper();
//		List<SequenceMapper> sequenceMapper = new ArrayList<>();

		if (null != sequence) {
				
			sequenceMapper1.setOrgId(sequence.getOrgId());
			sequenceMapper1.setUserId(sequence.getUserId());
			sequenceMapper1.setFalseSequenceId(sequence.getFalseSequenceId());
			sequenceMapper1.setFalseSequenceRule(sequence.getFalseSequenceRule());
			sequenceMapper1.setNoInputSequenceId(sequence.getNoInputSequenceId());
			sequenceMapper1.setNoInputSequenceRule(sequence.getNoInputSequenceRule());
			sequenceMapper1.setSequenceId(sequence.getSequenceId());
			sequenceMapper1.setTrueSequenceId(sequence.getTrueSequenceId());
			sequenceMapper1.setTrueSequenceRule(sequence.getTrueSequenceRule());
			sequenceMapper1.setSequenceRuleId(sequence.getSequenceRuleId());
			}
		
		return sequenceMapper1;
		}

	@Override
	public SequenceMapper updateSequence(String sequenceId, SequenceMapper sequenceMapper) {
		SequenceMapper resultMapper = null;

		Sequence sequence = sequenceRepository.getSequenceBySequenceId(sequenceId);

		if (null != sequence) {

			if (null != sequenceMapper.getName()) {
				sequence.setName(sequenceMapper.getName());
			}
			if (null != sequenceMapper.getOrgId()) {
				sequence.setOrgId(sequenceMapper.getOrgId());
			} 			
			if (null != sequenceMapper.getUserId()) {
				sequence.setUserId(sequenceMapper.getUserId());
			} 
			if (null != sequenceMapper.getPriority()) {
				sequence.setPriority(sequenceMapper.getPriority());
			} 
			if (null != sequenceMapper.getType()) {
				sequence.setType(sequenceMapper.getType());
			} 
			if (0 != sequenceMapper.getNoOfDays()) {
				sequence.setNoOfDays(sequenceMapper.getNoOfDays());
			}
			Sequence updatedSequence = sequenceRepository.save(sequence);
			
			SequenceDelete equenceDelete=sequenceDeleteRepository.findBySequenceId(sequenceId);
			if (null != equenceDelete) {
				if (null != sequenceMapper.getOrgId()) {
					equenceDelete.setOrgId(sequenceMapper.getOrgId());
				} 			
				if (null != sequenceMapper.getUserId()) {
					equenceDelete.setUserId(sequenceMapper.getUserId());
				}
				equenceDelete.setUpdationDate(new Date());
				sequenceDeleteRepository.save(equenceDelete);
			}else {
				SequenceDelete sequenceDelete = new SequenceDelete();
				sequenceDelete.setSequenceId(sequenceId);
				sequenceDelete.setUserId(sequenceMapper.getUserId());
				sequenceDelete.setOrgId(sequenceMapper.getOrgId());
				sequenceDelete.setUpdationDate(new Date());
				sequenceDeleteRepository.save(sequenceDelete);
			}
			resultMapper = getSequenceBySequenceId(updatedSequence.getSequenceId());
			
		}
		return resultMapper;
	}

	private SequenceMapper getSequenceBySequenceId(String sequenceId) {
		Sequence sequence = sequenceRepository.findBySequenceeId(sequenceId);
		SequenceMapper sequenceMapper = new SequenceMapper();

		if (null != sequence) {
							
			sequenceMapper.setName(sequence.getName());
			sequenceMapper.setOrgId(sequence.getOrgId());
			sequenceMapper.setUserId(sequence.getUserId());
			sequenceMapper.setPriority(sequence.getPriority());
			sequenceMapper.setType(sequence.getType());
			sequenceMapper.setNoOfDays(sequence.getNoOfDays());
			sequenceMapper.setSequenceId(sequence.getSequenceId());
			}
		return sequenceMapper;
	}
	@Override
	public boolean deleteSequence(String sequenceId) {
		
		Sequence sequence = sequenceRepository.getOne(sequenceId);
		if(sequence != null ) {
			SequenceDelete equenceDelete=sequenceDeleteRepository.findBySequenceId(sequenceId);
			if(null!=equenceDelete) {
				equenceDelete.setUpdationDate(new Date());
				sequenceDeleteRepository.save(equenceDelete);
			}
			sequenceRepository.delete(sequence);
			return true;
			}
		return false;
		}
}