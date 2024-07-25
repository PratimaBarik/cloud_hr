package com.app.employeePortal.contact.service;

import com.app.employeePortal.contact.mapper.ContactDesignationMapper;

public interface ContactDesignationService {
	public String addContactDesignation(ContactDesignationMapper contactDesignationMapper);
	
	public ContactDesignationMapper getContactDesignationByDesignationId(String contactDesignationId);

	public ContactDesignationMapper updateContactDesignation(ContactDesignationMapper contactDesignationMapper);
	
	public boolean deleteContactDesignation(String contactDesignationId);
}
