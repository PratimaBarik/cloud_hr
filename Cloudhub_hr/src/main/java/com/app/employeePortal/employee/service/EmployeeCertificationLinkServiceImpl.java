package com.app.employeePortal.employee.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.category.entity.CertificationLibrary;
import com.app.employeePortal.category.repository.CertificationLibraryRepository;
import com.app.employeePortal.employee.entity.EmployeeCertificationLink;
import com.app.employeePortal.employee.mapper.EmployeeCertificationLinkMapper;
import com.app.employeePortal.employee.repository.EmployeeCertificationLinkRepository;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class EmployeeCertificationLinkServiceImpl implements EmployeeCertificationLinkService {

	@Autowired
	EmployeeCertificationLinkRepository employeeCertificationLinkRepository;
	@Autowired
	CertificationLibraryRepository certificationLibraryRepository;
	@Override
	public String saveEmployeeCertification(EmployeeCertificationLinkMapper employeeCertificationLinkMapper) {

		CertificationLibrary certificationLibrary1 = certificationLibraryRepository
				.getByEmployeeCertificationNameAndLiveInd(
						employeeCertificationLinkMapper.getEmployeeCertificationName());

		if (null != certificationLibrary1) {
			EmployeeCertificationLink employeeCertificationLink = new EmployeeCertificationLink();
			employeeCertificationLink
					.setEmployeeCertificationName(certificationLibrary1.getCertificationId());
			employeeCertificationLink.setEmployeeId(employeeCertificationLinkMapper.getEmployeeId());
			employeeCertificationLink.setCreationDate(new Date());
			employeeCertificationLink.setUserId(employeeCertificationLinkMapper.getUserId());
			employeeCertificationLink.setOrgId(employeeCertificationLinkMapper.getOrgId());

			employeeCertificationLinkRepository.save(employeeCertificationLink);
		} else {

			CertificationLibrary newCertificationLibrary = new CertificationLibrary();
			
			newCertificationLibrary.setName(employeeCertificationLinkMapper.getEmployeeCertificationName());
			newCertificationLibrary.setOrgId(employeeCertificationLinkMapper.getOrgId());
			newCertificationLibrary.setUserId(employeeCertificationLinkMapper.getUserId());
			newCertificationLibrary.setCreationDate(new Date());
			newCertificationLibrary.setLiveInd(true);
			newCertificationLibrary.setEditInd(true);
			certificationLibraryRepository.save(newCertificationLibrary);


			EmployeeCertificationLink employeeCertificationLink = new EmployeeCertificationLink();
			employeeCertificationLink
					.setEmployeeCertificationName(newCertificationLibrary.getCertificationId());
			employeeCertificationLink.setEmployeeId(employeeCertificationLinkMapper.getEmployeeId());
			employeeCertificationLink.setCreationDate(new Date());
			employeeCertificationLink.setUserId(employeeCertificationLinkMapper.getUserId());
			employeeCertificationLink.setOrgId(employeeCertificationLinkMapper.getOrgId());

			employeeCertificationLinkRepository.save(employeeCertificationLink);
		
		}

		return employeeCertificationLinkMapper.getEmployeeCertificationName();
	}
	
	@Override
	public boolean checkCertificationInCertificationSet(String employeeCertificationName, String employeeId) {
		List<CertificationLibrary> certificationLibrary = certificationLibraryRepository.findByNameContaining(employeeCertificationName);
		for (CertificationLibrary certificationLibrary2 : certificationLibrary) {
		if (null!=certificationLibrary2) {
			List<EmployeeCertificationLink> employeeCertificationLink =employeeCertificationLinkRepository.findByEmployeeCertificationNameAndEmployeeId(certificationLibrary2.getCertificationId(),employeeId);
			if (employeeCertificationLink.size() > 0) {
			return true;
			}
		}
		}
		return false;
	}

	@Override
	public List<EmployeeCertificationLinkMapper> getEmployeeCertificationDetails(String employeeId) {
		List<EmployeeCertificationLinkMapper> resultList = new ArrayList<EmployeeCertificationLinkMapper>();
		List<EmployeeCertificationLink> list = employeeCertificationLinkRepository
				.getEmployeeCertificationById(employeeId);
		if (null != list && !list.isEmpty()) {
			list.stream().map(employeeCertificationLink->{
				EmployeeCertificationLinkMapper resultMapper = new EmployeeCertificationLinkMapper();
				
				CertificationLibrary certificationLibrary1 = certificationLibraryRepository
						.findByCertificationId(employeeCertificationLink.getEmployeeCertificationName());
				if(null!=certificationLibrary1) {
					
					resultMapper.setEmployeeCertificationName(certificationLibrary1.getName());
				}
				resultMapper.setEmployeeCertificationLinkId(employeeCertificationLink.getEmployeeCertificationLinkId());
				resultMapper.setEmployeeId(employeeCertificationLink.getEmployeeId());
				resultMapper.setCreationDate(Utility.getISOFromDate(employeeCertificationLink.getCreationDate()));
				resultMapper.setOrgId(employeeCertificationLink.getOrgId());
				resultMapper.setUserId(employeeCertificationLink.getUserId());
				
					resultList.add(resultMapper);
					return resultList;
			}).collect(Collectors.toList());

		}

		return resultList;
	}

	@Override
	public String deleteEmployeeCertification(String employeeCertificationLinkId) {
		String message =null;
			if (null != employeeCertificationLinkId) {
				EmployeeCertificationLink employeeCertificationLink = employeeCertificationLinkRepository
						.findByEmployeeCertificationLinkId(employeeCertificationLinkId);
				if (null != employeeCertificationLink) {
					employeeCertificationLinkRepository.delete(employeeCertificationLink);
					message = "This is deleted successfully";
				}
			}
			return message;

		}
	
}