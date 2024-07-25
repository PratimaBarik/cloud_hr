package com.app.employeePortal.category.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.category.entity.PaymentCategory;
import com.app.employeePortal.category.entity.ServiceLine;
import com.app.employeePortal.category.entity.UserSalaryBreakout;
import com.app.employeePortal.category.mapper.PaymentMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmtReqMapper;
import com.app.employeePortal.category.mapper.PerformanceMgmtRespMapper;
import com.app.employeePortal.category.mapper.UserSalaryBreakoutReqMapper;
import com.app.employeePortal.category.mapper.UserSalaryBreakoutResponseMapper;
import com.app.employeePortal.category.repository.UserSalaryBreakoutRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class UserSalaryBreakoutServiceImpl implements UserSalaryBreakoutService {

	@Autowired
	UserSalaryBreakoutRepository userSalaryBreakoutRepository;
	@Autowired
	EmployeeService employeeService;

	@Override
	public UserSalaryBreakoutResponseMapper saveUserSalaryBreakout(UserSalaryBreakoutReqMapper requestMapper) {
		String UserSalaryBreakoutId = null;
		UserSalaryBreakout userSalaryBreakout1 = userSalaryBreakoutRepository
				.findByDepartmentIdAndRoleTypeId(requestMapper.getDepartmentId(),requestMapper.getRoleTypeId());
		if (null!=userSalaryBreakout1) {
			userSalaryBreakout1.setCreationDate(new Date());
			userSalaryBreakout1.setLiveInd(true);
			userSalaryBreakout1.setDepartmentId(requestMapper.getDepartmentId());
			userSalaryBreakout1.setRoleTypeId(requestMapper.getRoleTypeId());
			userSalaryBreakout1.setOrgId(requestMapper.getOrgId());
			userSalaryBreakout1.setUpdatedBy(requestMapper.getUserId());
			userSalaryBreakout1.setUpdationDate(new Date());
			userSalaryBreakout1.setUserId(requestMapper.getUserId());
			userSalaryBreakout1.setBasic(requestMapper.getBasic());
			userSalaryBreakout1.setHousing(requestMapper.getHousing());
			userSalaryBreakout1.setOthers(requestMapper.getOthers());
			userSalaryBreakout1.setTransportation(requestMapper.getTransportation());

			UserSalaryBreakoutId = userSalaryBreakoutRepository.save(userSalaryBreakout1)
					.getUserSalaryBreakoutId();
		}else {
			
			UserSalaryBreakout userSalaryBreakout = new UserSalaryBreakout();
			userSalaryBreakout.setCreationDate(new Date());
			userSalaryBreakout.setLiveInd(true);
			userSalaryBreakout.setDepartmentId(requestMapper.getDepartmentId());
			userSalaryBreakout.setRoleTypeId(requestMapper.getRoleTypeId());
			userSalaryBreakout.setOrgId(requestMapper.getOrgId());
			userSalaryBreakout.setUpdatedBy(requestMapper.getUserId());
			userSalaryBreakout.setUpdationDate(new Date());
			userSalaryBreakout.setUserId(requestMapper.getUserId());
			userSalaryBreakout.setBasic(requestMapper.getBasic());
			userSalaryBreakout.setHousing(requestMapper.getHousing());
			userSalaryBreakout.setOthers(requestMapper.getOthers());
			userSalaryBreakout.setTransportation(requestMapper.getTransportation());

			UserSalaryBreakoutId = userSalaryBreakoutRepository.save(userSalaryBreakout)
					.getUserSalaryBreakoutId();

		}

		return getUserSalaryBreakoutById(UserSalaryBreakoutId);
	}

	public UserSalaryBreakoutResponseMapper getUserSalaryBreakoutById(String userSalaryBreakoutId) {

		UserSalaryBreakout userSalaryBreakout = userSalaryBreakoutRepository
				.findByUserSalaryBreakoutId(userSalaryBreakoutId);
		UserSalaryBreakoutResponseMapper resultMapper = new UserSalaryBreakoutResponseMapper();

		if (null != userSalaryBreakout) {

			resultMapper.setCreationDate(Utility.getISOFromDate(userSalaryBreakout.getCreationDate()));
			resultMapper.setLiveInd(userSalaryBreakout.isLiveInd());
			resultMapper.setDepartmentId(userSalaryBreakout.getDepartmentId());
			resultMapper.setBasic(userSalaryBreakout.getBasic());
			resultMapper.setRoleTypeId(userSalaryBreakout.getRoleTypeId());
			resultMapper.setHousing(userSalaryBreakout.getHousing());
			resultMapper.setOthers(userSalaryBreakout.getOthers());
			resultMapper.setTransportation(userSalaryBreakout.getTransportation());
			resultMapper.setOrgId(userSalaryBreakout.getOrgId());
			resultMapper.setUpdatedBy(employeeService.getEmployeeFullName(userSalaryBreakout.getUserId()));
			resultMapper.setUpdationDate(Utility.getISOFromDate(userSalaryBreakout.getUpdationDate()));
			resultMapper.setUserId(userSalaryBreakout.getUserId());
			resultMapper.setUserSalaryBreakoutId(userSalaryBreakout.getUserSalaryBreakoutId());
			resultMapper.setUserSalaryBreakoutId(userSalaryBreakoutId);
		}

		return resultMapper;
	}


	@Override
	public UserSalaryBreakoutResponseMapper updateUserSalaryBreakout(String userSalaryBreakoutId,
			UserSalaryBreakoutReqMapper requestMapper) {
		UserSalaryBreakout userSalaryBreakout = userSalaryBreakoutRepository
				.findByUserSalaryBreakoutId(userSalaryBreakoutId);
		if (null != userSalaryBreakout) {

			userSalaryBreakout.setCreationDate(new Date());
			userSalaryBreakout.setLiveInd(true);
			userSalaryBreakout.setDepartmentId(requestMapper.getDepartmentId());
			userSalaryBreakout.setRoleTypeId(requestMapper.getRoleTypeId());
			userSalaryBreakout.setOrgId(requestMapper.getOrgId());
			userSalaryBreakout.setUpdatedBy(requestMapper.getUserId());
			userSalaryBreakout.setUpdationDate(new Date());
			userSalaryBreakout.setUserId(requestMapper.getUserId());
			userSalaryBreakout.setBasic(requestMapper.getBasic());
			userSalaryBreakout.setHousing(requestMapper.getHousing());
			userSalaryBreakout.setOthers(requestMapper.getOthers());
			userSalaryBreakout.setTransportation(requestMapper.getTransportation());

			userSalaryBreakoutRepository.save(userSalaryBreakout);
		}
		UserSalaryBreakoutResponseMapper resultMapper = getUserSalaryBreakoutById(userSalaryBreakoutId);
		return resultMapper;
	}

	@Override
	public void deleteUserSalaryBreakout(String userSalaryBreakoutId, String userId) {

		if (null != userSalaryBreakoutId) {
			UserSalaryBreakout userSalaryBreakout = userSalaryBreakoutRepository
					.findByUserSalaryBreakoutId(userSalaryBreakoutId);

			userSalaryBreakout.setUpdationDate(new Date());
			userSalaryBreakout.setUpdatedBy(userId);
			userSalaryBreakout.setLiveInd(false);
			userSalaryBreakoutRepository.save(userSalaryBreakout);
		}
	}

	@Override
	public UserSalaryBreakoutResponseMapper getUserSalaryBreakoutByRoleTypeId(String roleTypeId) {

		UserSalaryBreakoutResponseMapper resultMapper = new UserSalaryBreakoutResponseMapper();
		UserSalaryBreakout list = userSalaryBreakoutRepository.findByRoleTypeIdAndLiveInd(roleTypeId, true);
		if (null != list) {
			resultMapper =  getUserSalaryBreakoutById(list.getUserSalaryBreakoutId());
		}
		return resultMapper;
	}

	@Override
	public HashMap getUserSalaryBreakoutCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<UserSalaryBreakout> list = userSalaryBreakoutRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("UserSalaryBreakoutCount", list.size());
		return map;
	}

}