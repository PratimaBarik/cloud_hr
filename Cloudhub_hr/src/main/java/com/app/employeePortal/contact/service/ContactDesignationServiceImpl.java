package com.app.employeePortal.contact.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.contact.entity.ContactDesignation;
import com.app.employeePortal.contact.mapper.ContactDesignationMapper;
import com.app.employeePortal.contact.repository.ContactDesignationRepository;

@Service
@Transactional
public class ContactDesignationServiceImpl implements ContactDesignationService {

	@Autowired
	ContactDesignationRepository contactDesignationRepository;

	@Override
	public String addContactDesignation(ContactDesignationMapper contactDesignationMapper) {
		String id = null;
		if (contactDesignationMapper != null) {

			ContactDesignation contactDesignation = new ContactDesignation();

			contactDesignation.setContact_designation_name(contactDesignationMapper.getContactDesignationName());
			contactDesignation.setCreator_id(contactDesignationMapper.getCreatorId());
			contactDesignation.setCreation_date(new Date());
			contactDesignation.setLive_ind(true);

			ContactDesignation contact = contactDesignationRepository.save(contactDesignation);
			id = contact.getContact_designation_id();
		}
		return id;

	}

	@Override
	public ContactDesignationMapper getContactDesignationByDesignationId(String contactDesignationId) {

		ContactDesignation designation = contactDesignationRepository.getContactDesignationById(contactDesignationId);
		ContactDesignationMapper designationMapper = new ContactDesignationMapper();

		if (null != designation) {

			designationMapper.setContactDesignationId(designation.getContact_designation_id());
			designationMapper.setContactDesignationName(designation.getContact_designation_name());
		}

		return designationMapper;
	}

	/* For Updating Contact Desiganation */
	@Override
	public ContactDesignationMapper updateContactDesignation(ContactDesignationMapper contactDesignationMapper) {

		ContactDesignation contactDesignation = contactDesignationRepository.getContactDesignationById(contactDesignationMapper.getContactDesignationId());
		
		
		if(null != contactDesignationMapper.getContactDesignationName() )
	    	
			contactDesignation.setContact_designation_name(contactDesignationMapper.getContactDesignationName());
   	 
		if(null != contactDesignationMapper.getCreatorId())
   	 
			contactDesignation.setCreator_id(contactDesignationMapper.getCreatorId());
   	 
		
		     	     	 
		contactDesignationRepository.save(contactDesignation);

		ContactDesignationMapper resultMapper = getContactDesignationByDesignationId(contactDesignationMapper.getContactDesignationId());
		    	 
		return resultMapper;
	}

	@Override
	public boolean deleteContactDesignation(String contactDesignationId) {

		ContactDesignation contactDesignation = contactDesignationRepository.getOne(contactDesignationId);
		
		if(contactDesignation != null ) {
			
			contactDesignationRepository.delete(contactDesignation);
			return true;
			
		}
		return false;
	}
	 
}
