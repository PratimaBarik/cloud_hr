package com.app.employeePortal.category.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.category.entity.Industry;
import com.app.employeePortal.category.entity.RoleType;
import com.app.employeePortal.category.mapper.IndustryMapper;
import com.app.employeePortal.category.repository.IndustryRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class IndustryServiceImpl implements IndustryService {

	@Autowired
	IndustryRepository industryRepository;
	@Autowired
	EmployeeService employeeService;
	private String[] headings = { "Name" };

	@Override
	public IndustryMapper saveIndustry(IndustryMapper mapper) {
		String Id = null;
		if (mapper != null) {
			Industry industry = new Industry();
			industry.setCreationDate(new Date());
			industry.setLiveInd(true);
			industry.setName(mapper.getName());
			industry.setOrgId(mapper.getOrgId());
			industry.setUserId(mapper.getUserId());
			industry.setUpdatedBy(mapper.getUserId());
			industry.setUpdationDate(new Date());
			Id = industryRepository.save(industry).getIndustryId();

		}
		IndustryMapper resultMapper = getIndustryById(Id);
		return resultMapper;
	}

	public IndustryMapper getIndustryById(String industryId) {

		Industry industry = industryRepository.findByIndustryIdAndLiveInd(industryId,true);
		IndustryMapper resultMapper = new IndustryMapper();

		if (null != industry) {
			resultMapper.setCreationDate(Utility.getISOFromDate(industry.getCreationDate()));
			resultMapper.setLiveInd(industry.isLiveInd());
			resultMapper.setName(industry.getName());
			resultMapper.setOrgId(industry.getOrgId());
			resultMapper.setUserId(industry.getUserId());
			resultMapper.setIndustryId(industryId);
			resultMapper.setUpdatedBy(employeeService.getEmployeeFullName(industry.getUserId()));
			resultMapper.setUpdationDate(Utility.getISOFromDate(industry.getCreationDate()));
		}

		return resultMapper;
	}

	@Override
	public List<IndustryMapper> getIndustryMapperByOrgId(String orgId) {

		List<IndustryMapper> resultMapper = new ArrayList<>();
		List<Industry> list = industryRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getIndustryById(li.getIndustryId())).collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<Industry> list1 = industryRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public IndustryMapper updateIndustry(String industryId, IndustryMapper mapper) {

		Industry industry = industryRepository.findByIndustryIdAndLiveInd(industryId,true);
		if (null != industry) {

			industry.setCreationDate(new Date());
			industry.setLiveInd(true);
			industry.setName(mapper.getName());
			industry.setOrgId(mapper.getOrgId());
			industry.setUserId(mapper.getUserId());
			industry.setUpdatedBy(mapper.getUserId());
			industry.setUpdationDate(new Date());
			industryRepository.save(industry);
		}
		IndustryMapper resultMapper = getIndustryById(industryId);
		return resultMapper;
	}

	@Override
	public void deleteIndustry(String industryId, String userId) {

		if (null != industryId) {
			Industry Industry = industryRepository.findByIndustryIdAndLiveInd(industryId,true);
			Industry.setLiveInd(false);
			Industry.setUpdatedBy(userId);
			Industry.setUpdationDate(new Date());
			industryRepository.save(Industry);
		}
	}

	@Override
	public List<IndustryMapper> getIndustryByName(String name, String orgId) {
		List<Industry> list = industryRepository.findByNameContainingAndLiveIndAndOrgId(name, true,orgId);
		List<IndustryMapper> resultMapper = new ArrayList<IndustryMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(itemTask -> {
				IndustryMapper mapper = getIndustryById(itemTask.getIndustryId());
				if (null != mapper) {
					resultMapper.add(mapper);
				}
				return resultMapper;
			}).collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<Industry> list1 = industryRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public HashMap getIndustryCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<Industry> list = industryRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("IndustryCount", list.size());
		return map;
	}

	@Override
	public boolean checkNameInIndustry(String name, String orgId) {
		List<Industry> industry = industryRepository.findByNameAndLiveIndAndOrgId(name, true,orgId);
		if (industry.size() > 0) {
			return true;
		}
		return false;
	}

}