package com.app.employeePortal.category.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.category.entity.CertificationLibrary;
import com.app.employeePortal.category.entity.CertificationLibraryDelete;
import com.app.employeePortal.category.mapper.CertificationLibraryMapper;
import com.app.employeePortal.category.repository.CertificationLibraryDeleteRepository;
import com.app.employeePortal.category.repository.CertificationLibraryRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class CertificationLibraryServiceImpl implements CertificationLibraryService {
	@Autowired
	CertificationLibraryRepository certificationLibraryRepository;
	@Autowired
	CertificationLibraryDeleteRepository certificationLibraryDeleteRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Override
	public CertificationLibraryMapper saveCertificationLibrary(CertificationLibraryMapper certificationLibraryMapper) {
		String certificationId=null;
		if(certificationLibraryMapper != null) {
			CertificationLibrary certificationLibrary = new CertificationLibrary();

			certificationLibrary.setName(certificationLibraryMapper.getName());
			certificationLibrary.setOrgId(certificationLibraryMapper.getOrgId());
			certificationLibrary.setUserId(certificationLibraryMapper.getUserId());
			certificationLibrary.setCreationDate(new Date());
			certificationLibrary.setLiveInd(true);
			certificationLibrary.setEditInd(true);

			certificationId= certificationLibraryRepository.save(certificationLibrary).getCertificationId();
			
			CertificationLibraryDelete certificationLibraryDelete=new CertificationLibraryDelete();
			certificationLibraryDelete.setCertificationId(certificationId);
			certificationLibraryDelete.setOrgId(certificationLibraryMapper.getOrgId());
			certificationLibraryDelete.setUserId(certificationLibraryMapper.getUserId());
			certificationLibraryDelete.setUpdationDate(new Date());
			certificationLibraryDeleteRepository.save(certificationLibraryDelete);
				}
		return getCertificationLibraryByCertificationId(certificationId);
		}
	@Override
	public CertificationLibraryMapper getCertificationLibraryByCertificationId(String certificationId) {
		CertificationLibrary certificationLibrary = certificationLibraryRepository.findByCertificationId(certificationId);
		CertificationLibraryMapper certificationLibraryMapper =new CertificationLibraryMapper();
//		List<SequenceMapper> sequenceMapper = new ArrayList<>();

		if (null != certificationLibrary) {
				
			certificationLibraryMapper.setCertificationId(certificationLibrary.getCertificationId());
			certificationLibraryMapper.setName(certificationLibrary.getName());
			certificationLibraryMapper.setOrgId(certificationLibrary.getOrgId());
			certificationLibraryMapper.setUserId(certificationLibrary.getUserId());
			certificationLibraryMapper.setCreationDate(Utility.getISOFromDate(certificationLibrary.getCreationDate()));
			certificationLibraryMapper.setLiveInd(certificationLibrary.isLiveInd());
			certificationLibraryMapper.setEditInd(certificationLibrary.isEditInd());
			}
		
		return certificationLibraryMapper;
		}
	@Override
	public CertificationLibraryMapper updateCertificationLibrary(String certificationId,
			CertificationLibraryMapper certificationLibraryMapper) {
		CertificationLibraryMapper resultMapper = null;

		CertificationLibrary certificationLibrary = certificationLibraryRepository.getCertificationLibraryByCertificationId(certificationId);

		if (null != certificationLibrary) {

			if (null != certificationLibraryMapper.getName()) {
				certificationLibrary.setName(certificationLibraryMapper.getName());
			}
			 certificationLibraryRepository.save(certificationLibrary);
			 
			 CertificationLibraryDelete certificationLibraryDelete =certificationLibraryDeleteRepository.findByCertificationId(certificationId);
			 if (null != certificationLibraryDelete) {
				 certificationLibraryDelete.setUpdationDate(new Date());
				 certificationLibraryDeleteRepository.save(certificationLibraryDelete);
			 }else {
				 CertificationLibraryDelete certificationLibraryDelete1 = new CertificationLibraryDelete();
				 certificationLibraryDelete1.setCertificationId(certificationId);
				 certificationLibraryDelete1.setUserId(certificationLibraryMapper.getUserId());
				 certificationLibraryDelete1.setOrgId(certificationLibraryMapper.getOrgId());
				 certificationLibraryDelete1.setUpdationDate(new Date());
					certificationLibraryDeleteRepository.save(certificationLibraryDelete1);
				}
		}
		resultMapper = getCertificationLibraryByCertificationId(certificationId);

		return resultMapper;
	}
	@Override
	public boolean deleteCertificationLibrary(String certificationId) {
		CertificationLibrary certificationLibrary = certificationLibraryRepository.getOne(certificationId);
		if(certificationLibrary != null ) {
			
			CertificationLibraryDelete certificationLibraryDelete = certificationLibraryDeleteRepository.findByCertificationId(certificationId);
			if(certificationLibraryDelete != null ) {
				 certificationLibraryDelete.setUpdationDate(new Date());
				 certificationLibraryDeleteRepository.save(certificationLibraryDelete);
			}
			
			certificationLibraryRepository.delete(certificationLibrary);
			return true;
			}
		return false;
	}
	@Override
	public List<CertificationLibraryMapper> getCertificationLibraryByOrgId(String orgId) {
		List<CertificationLibrary> certificationLibrary = certificationLibraryRepository.getCertificationLibraryByOrgId(orgId);

		List<CertificationLibraryMapper> certificationLibraryMapper = new ArrayList<>();

		if (null != certificationLibrary && !certificationLibrary.isEmpty()) {
			
			certificationLibrary.stream().map(certificationLibrary1 -> {
				CertificationLibraryMapper certificationLibraryMapper1 =new CertificationLibraryMapper();
				certificationLibraryMapper1.setCertificationId(certificationLibrary1.getCertificationId());
				certificationLibraryMapper1.setName(certificationLibrary1.getName());
				certificationLibraryMapper1.setOrgId(certificationLibrary1.getOrgId());
				certificationLibraryMapper1.setUserId(certificationLibrary1.getUserId());
				certificationLibraryMapper1.setCreationDate(Utility.getISOFromDate(certificationLibrary1.getCreationDate()));
				certificationLibraryMapper1.setLiveInd(certificationLibrary1.isLiveInd());
				certificationLibraryMapper1.setEditInd(certificationLibrary1.isEditInd());
				certificationLibraryMapper.add(certificationLibraryMapper1);
				return certificationLibraryMapper;
			}).collect(Collectors.toList());
		}
		
		Collections.sort(certificationLibraryMapper, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<CertificationLibraryDelete> certificationLibraryDelete = certificationLibraryDeleteRepository.findByOrgId(orgId);
		if (null != certificationLibraryDelete && !certificationLibraryDelete.isEmpty()) {
			Collections.sort(certificationLibraryDelete,
					( p1,p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));
			
			certificationLibraryMapper.get(0).setUpdationDate(Utility.getISOFromDate(certificationLibraryDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(certificationLibraryDelete.get(0).getUserId());
			if(null!=employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					certificationLibraryMapper.get(0).setUpdatedname(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					certificationLibraryMapper.get(0).setUpdatedname(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		
		
		return certificationLibraryMapper;
		}
	@Override
	public List<CertificationLibraryMapper> getCertificationsLibraryByNameByOrgLevel(String name, String orgId) {
		List<CertificationLibrary> list = certificationLibraryRepository.findByNameContainingAndOrgId(name,orgId);
		List<CertificationLibraryMapper> resultList = new ArrayList<CertificationLibraryMapper>();
		if (null != list && !list.isEmpty()) {

			list.stream().map(certificationLibrary -> {
				System.out.println("CertificationLibrary=========" + certificationLibrary.getCertificationId());
				CertificationLibraryMapper certificationLibraryMapper = getCertificationLibraryByCertificationId(certificationLibrary.getCertificationId());
				if (null != certificationLibraryMapper) {
					resultList.add(certificationLibraryMapper);

				}
				return resultList;
			}).collect(Collectors.toList());

		}
		return resultList;

	}

	@Override
	public boolean checkCertificationNameExistByOrgLevel(String name,String orgId) {
		List<CertificationLibrary> list=certificationLibraryRepository.findByNameAndLiveIndAndOrgId(name,true,orgId);
		if(!list.isEmpty()){
			return true;
		}
		return false;
	}

	@Override
	public boolean checkCertificationNameExistForUpdateByOrgLevel(String certificationId, String name, String orgId) {
		List<CertificationLibrary> list=certificationLibraryRepository.findByNameContainingAndCertificationIdAndLiveIndAndOrgId(name,certificationId,true,orgId);
		if(!list.isEmpty()){
			return true;
		}
		return false;
	}
	@Override
	public HashMap getCertificationsLibraryCountByOrgId(String orgId) {
		List<CertificationLibrary> certificationLibrary = certificationLibraryRepository.getCertificationLibraryByOrgId(orgId);
		HashMap map = new HashMap();
        map.put("certificationLibraryCount", certificationLibrary.size());
        return map;
	}
}
