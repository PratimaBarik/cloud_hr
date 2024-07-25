package com.app.employeePortal.customer.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
import com.app.employeePortal.Opportunity.repository.OpportunityDetailsRepository;
import com.app.employeePortal.config.AesEncryptor;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.investor.entity.Investor;
import com.app.employeePortal.investor.entity.InvestorOpportunity;
import com.app.employeePortal.investor.repository.InvestorOpportunityRepo;
import com.app.employeePortal.investor.repository.InvestorRepository;
import com.app.employeePortal.investorleads.entity.InvestorLeads;
import com.app.employeePortal.investorleads.repository.InvestorLeadsRepository;
import com.app.employeePortal.leads.entity.Leads;
import com.app.employeePortal.leads.repository.LeadsRepository;
import com.app.employeePortal.registration.entity.UserSettings;
import com.app.employeePortal.registration.repository.UserSettingsRepository;

@Transactional
@RestController
@CrossOrigin(maxAge = 3600)
@CacheConfig(cacheNames = { "migration" })
public class MigrationController {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AesEncryptor encryptor;
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	LeadsRepository leadsRepository;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	InvestorRepository investorRepository;
	@Autowired
	UserSettingsRepository userSettingsRepository;
	@Autowired
	InvestorLeadsRepository investorLeadsRepository;
	@Autowired
	OpportunityDetailsRepository opportunityDetailsRepository;
	@Autowired
	InvestorOpportunityRepo InvestorOpportunityRepo;

	@PostMapping("/api/v1/migration/customerTable")
	public String migrateCustomerTable() {

		List<Customer> CustomerList = customerRepository.findAll();
		for (Customer Customer : CustomerList) {
//			String unencryptedemail = Customer.getCustomerId();
//			System.out.println("emailid====="+Customer.getCustomerId());			
//			Customer uncryptedUser = customerRepository.findById(unencryptedemail).get();
			String encryptrdEmail = encryptor.convertToDatabaseColumn(Customer.getEmail());
			String encryptedName = encryptor.convertToDatabaseColumn(Customer.getName());
			String encryptedPhoneNo = encryptor.convertToDatabaseColumn(Customer.getPhoneNumber());
			String encryptedurl = encryptor.convertToDatabaseColumn(Customer.getUrl());
			Customer.setEmail(encryptrdEmail);
			Customer.setPhoneNumber(encryptedPhoneNo);
			Customer.setName(encryptedName);
			Customer.setUrl(encryptedurl);
			customerRepository.save(Customer);
		}

		return "migrated";
	}

	@PostMapping("/api/v1/migration/employeeTable")
	public String migrateUserTable() {

		List<EmployeeDetails> empList = employeeRepository.findAll();
		for (EmployeeDetails employee : empList) {

//			String unencryptedemail = employee.getEmailId();
//			EmployeeDetails uncryptedUser = employeeRepository.findByEmailId(unencryptedemail);
			String encryptrdEmail = encryptor.convertToDatabaseColumn(employee.getEmailId());
			String encryptedFirstName = encryptor.convertToDatabaseColumn(employee.getFirstName());
			String encryptedLastName = encryptor.convertToDatabaseColumn(employee.getLastName());
			String encryptedMiddleName = encryptor.convertToDatabaseColumn(employee.getMiddleName());
			String encryptedFullName = encryptor.convertToDatabaseColumn(employee.getFullName());
			String encryptedPhoneNo = encryptor.convertToDatabaseColumn(employee.getPhoneNo());
			String encryptedMobile = encryptor.convertToDatabaseColumn(employee.getMobileNo());
			employee.setEmailId(encryptrdEmail);
			employee.setFirstName(encryptedFirstName);
			employee.setMiddleName(encryptedMiddleName);
			employee.setLastName(encryptedLastName);
			employee.setFullName(encryptedFullName);
			employee.setPhoneNo(encryptedPhoneNo);
			employee.setMobileNo(encryptedMobile);
			employeeRepository.save(employee);
		}

		return "migrated";
	}

	@PostMapping("/api/v1/migration/investorTable")
	public String migrateInvestorTable() {

		List<Investor> InvestorList = investorRepository.findAll();
		for (Investor Investor : InvestorList) {

//			String unencryptedemail = Investor.getEmail();
//			Investor uncryptedUser = investorRepository.findByEmail(unencryptedemail);
			String encryptrdEmail = encryptor.convertToDatabaseColumn(Investor.getEmail());
			String encryptedFullName = encryptor.convertToDatabaseColumn(Investor.getName());
			String encryptedPhoneNo = encryptor.convertToDatabaseColumn(Investor.getPhoneNumber());
			String encryptedurl = encryptor.convertToDatabaseColumn(Investor.getUrl());
			Investor.setEmail(encryptrdEmail);
			Investor.setName(encryptedFullName);
			Investor.setPhoneNumber(encryptedPhoneNo);
			Investor.setUrl(encryptedurl);
			investorRepository.save(Investor);
		}

		return "migrated";
	}

	@PostMapping("/api/v1/migration/contactDetailsTable")
	public String migrateContactDetailsTable() {

		List<ContactDetails> ContactList = contactRepository.findAll();
		for (ContactDetails Contact : ContactList) {

//			String unencryptedemail = Contact.getEmail_id();
//			ContactDetails uncryptedUser = contactRepository.getByEmailIdAndLiveInd(unencryptedemail);
			String encryptrdEmail = encryptor.convertToDatabaseColumn(Contact.getEmail_id());
			String encryptedFirstName = encryptor.convertToDatabaseColumn(Contact.getFirst_name());
			String encryptedLastName = encryptor.convertToDatabaseColumn(Contact.getLast_name());
			String encryptedMiddleName = encryptor.convertToDatabaseColumn(Contact.getMiddle_name());
			String encryptedFullName = encryptor.convertToDatabaseColumn(Contact.getFullName());
			String encryptedPhoneNo = encryptor.convertToDatabaseColumn(Contact.getPhone_number());
			String encryptedMobileNo = encryptor.convertToDatabaseColumn(Contact.getMobile_number());
			String encryptedWpMobileNo = encryptor.convertToDatabaseColumn(Contact.getWhatsappNumber());
			Contact.setEmail_id(encryptrdEmail);
			Contact.setFirst_name(encryptedFirstName);
			Contact.setMiddle_name(encryptedMiddleName);
			Contact.setLast_name(encryptedLastName);
			Contact.setFullName(encryptedFullName);
			Contact.setPhone_number(encryptedPhoneNo);
			Contact.setMobile_number(encryptedMobileNo);
			Contact.setWhatsappNumber(encryptedWpMobileNo);

			contactRepository.save(Contact);
		}

		return "migrated";
	}

	@PostMapping("/api/v1/migration/leadsTable")
	public String migrateLeadsTable() {

		List<Leads> LeadsList = leadsRepository.findAll();
		for (Leads leads : LeadsList) {
//			String unencryptedemail = leads.getEmail();
//			Leads uncryptedUser = leadsRepository.findByEmail(unencryptedemail);
			String encryptrdEmail = encryptor.convertToDatabaseColumn(leads.getEmail());
			String encryptedFirstName = encryptor.convertToDatabaseColumn(leads.getFirstName());
			String encryptedLastName = encryptor.convertToDatabaseColumn(leads.getLastName());
			String encryptedMiddleName = encryptor.convertToDatabaseColumn(leads.getMiddleName());
			String encryptedPhoneNo = encryptor.convertToDatabaseColumn(leads.getPhoneNumber());
			String encryptedurl = encryptor.convertToDatabaseColumn(leads.getUrl());
			String encryptedname = encryptor.convertToDatabaseColumn(leads.getName());
			leads.setEmail(encryptrdEmail);
			leads.setFirstName(encryptedFirstName);
			leads.setMiddleName(encryptedMiddleName);
			leads.setLastName(encryptedLastName);
			leads.setPhoneNumber(encryptedPhoneNo);
			leads.setUrl(encryptedurl);
			leads.setName(encryptedname);
			leadsRepository.save(leads);
		}

		return "migrated";
	}

	@PostMapping("/api/v1/migration/employeeSettingTable")
	public String migrateUserSettingTable() {

		List<UserSettings> empList = userSettingsRepository.findAll();
		for (UserSettings employee : empList) {
			
//				System.out.println("getEmail====" + us.getEmail());
				String encryptrdEmail = encryptor.convertToDatabaseColumn(employee.getEmail());
//				System.out.println("encryptrdEmail====" + encryptrdEmail);
				employee.setEmail(encryptrdEmail);
				userSettingsRepository.save(employee);
			
		}

		return "migrated";
	}

	@PostMapping("/api/v1/migration/investorleadsTable")
	public String migrateInvestorLeadsTable() {

		List<InvestorLeads> investorleadsList = investorLeadsRepository.findAll();
		for (InvestorLeads investorleads : investorleadsList) {
			String encryptedname = encryptor.convertToDatabaseColumn(investorleads.getName());
			String encryptedFirstName = encryptor.convertToDatabaseColumn(investorleads.getFirstName());
			String encryptedLastName = encryptor.convertToDatabaseColumn(investorleads.getLastName());
			String encryptedMiddleName = encryptor.convertToDatabaseColumn(investorleads.getMiddleName());
			String encryptedurl = encryptor.convertToDatabaseColumn(investorleads.getUrl());
			String encryptedPhoneNo = encryptor.convertToDatabaseColumn(investorleads.getPhoneNumber());
			String encryptrdEmail = encryptor.convertToDatabaseColumn(investorleads.getEmail());
			
			investorleads.setEmail(encryptrdEmail);
			investorleads.setFirstName(encryptedFirstName);
			investorleads.setMiddleName(encryptedMiddleName);
			investorleads.setLastName(encryptedLastName);
			investorleads.setPhoneNumber(encryptedPhoneNo);
			investorleads.setUrl(encryptedurl);
			investorleads.setName(encryptedname);
			investorLeadsRepository.save(investorleads);
		}

		return "migrated";
	}

	@PostMapping("/api/v1/migration/opportunity")
	public String migrateOpportunityTable() {

		List<OpportunityDetails> opportunityList = opportunityDetailsRepository.findAll();
		for (OpportunityDetails opportunity : opportunityList) {
			
			String encryptedname = encryptor.convertToDatabaseColumn(opportunity.getOpportunityName());
			opportunity.setOpportunityName(encryptedname);
			opportunityDetailsRepository.save(opportunity);
		}

		return "migrated";
	}
	
	@PostMapping("/api/v1/migration/investorOpportunity")
	public String migrateInvestorOpportunityTable() {

		List<InvestorOpportunity> investorOpportunityList = InvestorOpportunityRepo.findAll();
		for (InvestorOpportunity investorOpportunity : investorOpportunityList) {
			
			String encryptedname = encryptor.convertToDatabaseColumn(investorOpportunity.getOpportunityName());
			investorOpportunity.setOpportunityName(encryptedname);
			InvestorOpportunityRepo.save(investorOpportunity);
		}

		return "migrated";
	}
	
}
