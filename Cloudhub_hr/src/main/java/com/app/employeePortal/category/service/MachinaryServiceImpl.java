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

import com.app.employeePortal.candidate.entity.SkillSetDetails;
import com.app.employeePortal.category.entity.CellDetails;
import com.app.employeePortal.category.entity.Machinary;
import com.app.employeePortal.category.entity.MachinaryLocationCellLink;
import com.app.employeePortal.category.entity.MachinaryLocationLink;
import com.app.employeePortal.category.mapper.MachinaryLocationCellMapper;
import com.app.employeePortal.category.mapper.MachinaryLocationMapper;
import com.app.employeePortal.category.mapper.MachinaryMapper;
import com.app.employeePortal.category.repository.CellProductionLinkRepository;
import com.app.employeePortal.category.repository.MachinaryLocationCellLinkReypository;
import com.app.employeePortal.category.repository.MachinaryLocationLinkReypository;
import com.app.employeePortal.category.repository.MachinaryRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.location.entity.LocationDetails;
import com.app.employeePortal.location.repository.LocationDetailsRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional

public class MachinaryServiceImpl implements MachinaryService {

	@Autowired
	MachinaryRepository machinaryRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	MachinaryLocationLinkReypository machinaryLocationLinkReypository;
	@Autowired
	MachinaryLocationCellLinkReypository machinaryLocationCellLinkReypository;
	@Autowired
	LocationDetailsRepository locationDetailsRepository;
	@Autowired CellProductionLinkRepository cellProductionLinkRepository;

	@Override
	public MachinaryMapper saveMachinary(MachinaryMapper mapper) {
		String machinaryId = null;
		if (mapper != null) {
			Machinary machinary = new Machinary();
			machinary.setCreationDate(new Date());
			machinary.setLiveInd(true);
			machinary.setName(mapper.getName());
			machinary.setOrgId(mapper.getOrgId());
			machinary.setUpdatedBy(mapper.getUserId());
			machinary.setUpdationDate(new Date());
			machinary.setUserId(mapper.getUserId());
			machinary.setDescription(mapper.getDescription());

			machinaryId = machinaryRepository.save(machinary).getMachinaryId();

		}
		MachinaryMapper resultMapper = getMachinaryByMachinaryId(machinaryId);
		return resultMapper;
	}

	public MachinaryMapper getMachinaryByMachinaryId(String machinaryId) {

		Machinary machinary = machinaryRepository.findByMachinaryIdAndLiveInd(machinaryId, true);
		MachinaryMapper machinaryMapper = new MachinaryMapper();

		if (null != machinary) {

			machinaryMapper.setCreationDate(Utility.getISOFromDate(machinary.getCreationDate()));
			machinaryMapper.setLiveInd(true);
			machinaryMapper.setName(machinary.getName());
			machinaryMapper.setOrgId(machinary.getOrgId());
			machinaryMapper.setUpdatedBy(employeeService.getEmployeeFullName(machinary.getUserId()));
			machinaryMapper.setUpdationDate(Utility.getISOFromDate(machinary.getUpdationDate()));
			machinaryMapper.setUserId(machinary.getUserId());
			machinaryMapper.setMachinaryId(machinary.getMachinaryId());
			machinaryMapper.setDescription(machinary.getDescription());

		}

		return machinaryMapper;
	}

	@Override
	public List<MachinaryMapper> getMachinaryByOrgId(String orgId) {

		List<MachinaryMapper> resultMapper = new ArrayList<>();
		List<Machinary> list = machinaryRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getMachinaryByMachinaryId(li.getMachinaryId()))
					.collect(Collectors.toList());
		}
		Collections.sort(resultMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<Machinary> list1 = machinaryRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultMapper.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultMapper.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultMapper;
	}

	@Override
	public MachinaryMapper updateMachinary(String machinaryId, MachinaryMapper mapper) {

		Machinary machinary = machinaryRepository.findByMachinaryIdAndLiveInd(machinaryId, true);
		if (null != machinary) {

			machinary.setLiveInd(true);
			machinary.setName(mapper.getName());
			machinary.setOrgId(mapper.getOrgId());
			machinary.setUpdatedBy(mapper.getUserId());
			machinary.setUpdationDate(new Date());
			machinary.setUserId(mapper.getUserId());
			machinary.setDescription(mapper.getDescription());
			machinaryRepository.save(machinary);
		}
		MachinaryMapper resultMapper = getMachinaryByMachinaryId(machinaryId);
		return resultMapper;
	}

	@Override
	public void deleteMachinary(String machinaryId, String userId) {

		if (null != machinaryId) {
			Machinary machinary = machinaryRepository.findByMachinaryIdAndLiveInd(machinaryId, true);

			machinary.setUpdationDate(new Date());
			machinary.setUpdatedBy(userId);
			machinary.setLiveInd(false);
			machinaryRepository.save(machinary);
		}
	}

	@Override
	public List<MachinaryMapper> getMachinaryByName(String name, String orgId) {
		List<Machinary> list = machinaryRepository.findByNameContainingAndLiveIndAndOrgId(name, true, orgId);
		List<MachinaryMapper> resultList = new ArrayList<MachinaryMapper>();
		if (null != list && !list.isEmpty()) {
			list.stream().map(Quality -> {
				MachinaryMapper mapper = getMachinaryByMachinaryId(Quality.getMachinaryId());
				if (null != mapper) {
					resultList.add(mapper);
				}
				return resultList;
			}).collect(Collectors.toList());
		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<Machinary> list1 = machinaryRepository.findAll();
		if (null != list1 && !list1.isEmpty()) {
			Collections.sort(list1, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultList.get(0).setUpdationDate(Utility.getISOFromDate(list1.get(0).getUpdationDate()));
			resultList.get(0).setUpdatedBy(employeeService.getEmployeeFullName(list1.get(0).getUpdatedBy()));
		}
		return resultList;
	}

	@Override
	public HashMap getMachinaryCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<Machinary> list = machinaryRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("machinaryCount", list.size());
		return map;
	}

	@Override
	public boolean checkNameInMachinary(String name, String orgId) {
		List<Machinary> machinary = machinaryRepository.findByNameAndLiveIndAndOrgId(name, true, orgId);
		if (machinary.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkNameInMachinaryInUpdate(String name, String orgId, String machinaryId) {
		List<Machinary> machinary = machinaryRepository.findByNameContainingAndMachinaryIdNotAndLiveIndAndOrgId(name,
				machinaryId, true, orgId);
		if (machinary.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public MachinaryLocationMapper saveMachinaryLocation(MachinaryLocationMapper mapper) {
		String id = null;
		if (mapper != null) {
			MachinaryLocationLink mll = new MachinaryLocationLink();
			mll.setCreationDate(new Date());
			mll.setLiveInd(true);
			mll.setLocationId(mapper.getLocationId());
			mll.setMachinaryId(mapper.getMachinaryName());
			mll.setOrgId(mapper.getOrgId());
			mll.setUserId(mapper.getUserId());
			mll.setMachineCode(mapper.getMachineCode());
			id = machinaryLocationLinkReypository.save(mll).getMachinaryLocationLinkId();
		}
		MachinaryLocationMapper resultMapper = getByMachinaryLocationLinkId(id);
		return resultMapper;
	}

	private MachinaryLocationMapper getByMachinaryLocationLinkId(String id) {
		MachinaryLocationLink mll = machinaryLocationLinkReypository.getById(id);
		MachinaryLocationMapper resultMapper = new MachinaryLocationMapper();
		if (null != mll) {
			resultMapper.setCreationDate(Utility.getISOFromDate(mll.getCreationDate()));
			resultMapper.setLiveInd(mll.isLiveInd());
			resultMapper.setOrgId(mll.getOrgId());
			resultMapper.setUserId(mll.getUserId());
			resultMapper.setMachinaryLocationLinkId(id);
			resultMapper.setMachineCode(mll.getMachineCode());
			if (null != mll.getCellId() && !mll.getCellId().isEmpty()) {
				CellDetails cellDetails = cellProductionLinkRepository.findById(mll.getCellId()).get();
				if (null != cellDetails) {
					resultMapper.setLocationId(cellDetails.getId());
					resultMapper.setLocationName(cellDetails.getCell());
				}
			}
			if (null != mll.getMachinaryId() && !mll.getMachinaryId().isEmpty()) {
				Machinary machinary = machinaryRepository.getById(mll.getMachinaryId());
				if (null != machinary) {
					resultMapper.setMachinaryId(machinary.getMachinaryId());
					resultMapper.setMachinaryName(machinary.getName());
				}
			}
		}
		return resultMapper;
	}

	@Override
	public List<MachinaryLocationMapper> getMachinaryLocationByLocationId(String locationId) {

		List<MachinaryLocationMapper> resultMapper = new ArrayList<>();
		List<MachinaryLocationLink> list = machinaryLocationLinkReypository.findByLocationIdAndLiveInd(locationId, true);
		if (null != list) {
			resultMapper = list.stream().map(li -> getByMachinaryLocationLinkId(li.getMachinaryLocationLinkId()))
					.collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public MachinaryLocationMapper saveMachinaryLocationCell(MachinaryLocationCellMapper mapper) {
	MachinaryLocationLink cell = machinaryLocationLinkReypository.findByMachinaryLocationLinkId(mapper.getMachinaryLocationLinkId());
	MachinaryLocationMapper response = null;
		if (null !=cell) {
					MachinaryLocationCellLink mlcl = new MachinaryLocationCellLink();
					mlcl.setCellId(mapper.getCellId());
					machinaryLocationCellLinkReypository.save(mlcl);
			
					response =	getByMachinaryLocationLinkId(mapper.getMachinaryLocationLinkId());
		}
		return response;
	}

}