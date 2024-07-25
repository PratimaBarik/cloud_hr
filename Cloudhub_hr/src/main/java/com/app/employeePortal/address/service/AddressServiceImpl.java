package com.app.employeePortal.address.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.app.employeePortal.leads.entity.Leads;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.address.entity.AddressDetails;
import com.app.employeePortal.address.entity.AddressInfo;
import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.address.repository.AddressInfoRepository;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.contact.entity.ContactAddressLink;
import com.app.employeePortal.contact.repository.ContactAddressLinkRepository;
import com.app.employeePortal.customer.entity.CustomerAddressLink;
import com.app.employeePortal.customer.repository.CustomerAddressLinkRepository;
import com.app.employeePortal.employee.entity.EmployeeAddressLink;
import com.app.employeePortal.employee.entity.EmployeeContactAddressLink;
import com.app.employeePortal.employee.repository.EmployeeAddressLinkRepository;
import com.app.employeePortal.employee.repository.EmployeeContactAddressRepository;
import com.app.employeePortal.investor.entity.InvestorAddressLink;
import com.app.employeePortal.investor.repository.InvestorAddressLinkRepo;
import com.app.employeePortal.investorleads.entity.InvestorLeadsAddressLink;
import com.app.employeePortal.investorleads.repository.InvestorLeadsAddressLinkRepository;
import com.app.employeePortal.leads.entity.LeadsAddressLink;
import com.app.employeePortal.leads.repository.LeadsAddressLinkRepository;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
	@Autowired
	AddressInfoRepository addressInfoRepository;

	@Autowired
	AddressRepository addressDetailsRepository;

	@Autowired
	EmployeeAddressLinkRepository employeeAddressLinkRepository;

	@Autowired
	EmployeeContactAddressRepository employeeContactAddressRepository;

	@Autowired
	CustomerAddressLinkRepository customerAddressLinkRepository;
	
	@Autowired
	ContactAddressLinkRepository contactAddressLinkRepository;
	
	@Autowired
	LeadsAddressLinkRepository leadsAddressLinkRepository;
	@Autowired
	InvestorLeadsAddressLinkRepository investorLeadsAddressLinkRepository;
	@Autowired
	InvestorAddressLinkRepo investorAddressLinkRepository;

	@Override
	public String saveAddressProcess(AddressMapper addressMapper) {
		/* insert to adressInfo table */
		AddressInfo addressInfo = new AddressInfo();
		addressInfo.setCreationDate(new Date());
		addressInfo.setCreatorId(addressMapper.getEmployeeId());
		AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);
		String addressInfoId = addressInfoo.getId();

		if (null != addressInfoId) {
			/* insert to adressDetails table */
			AddressDetails addressDetails = new AddressDetails();
			addressDetails.setAddressId(addressInfoId);
			addressDetails.setAddressLine1(addressMapper.getAddress1());
			addressDetails.setAddressLine2(addressMapper.getAddress2());
			addressDetails.setAddressType(addressMapper.getAddressType());
			addressDetails.setCity(addressMapper.getCity());
			addressDetails.setStreet(addressMapper.getStreet());
			addressDetails.setTown(addressMapper.getTown());
			addressDetails.setState(addressMapper.getState());
			addressDetails.setPostalCode(addressMapper.getPostalCode());
			addressDetails.setCountry(addressMapper.getCountry());
			addressDetails.setLatitude(addressMapper.getLatitude());
			addressDetails.setLongitude(addressMapper.getLongitude());
			addressDetails.setCreationDate(new Date());
			addressDetails.setCreatorId(addressMapper.getEmployeeId());
			addressDetails.setLiveInd(true);
			addressDetails.setHouseNo(addressMapper.getHouseNo());
			addressDetails.setPrimaryInd(false);

			AddressDetails adressDetatilss = addressDetailsRepository.save(addressDetails);
			String addressDetaislsId = adressDetatilss.getId();
		}

		/*
		 * if( null!=addressMapper.getContactPersonId()) {
		 * System.out.println("inside contact address ");
		 * 
		 * EmployeeContactAddressLink employeeContactAddressLink = new
		 * EmployeeContactAddressLink();
		 * employeeContactAddressLink.setContact_person_id(addressMapper.
		 * getContactPersonId());
		 * employeeContactAddressLink.setContact_address_id(addressId);
		 * employeeContactAddressLink.setEmployee_id(addressMapper.getEmployeeId());
		 * employeeContactAddressLink.setCreation_date(new Date());
		 * employeeContactAddressLink.setLive_ind(true);
		 * employeeContactAddressRepository.save(employeeContactAddressLink); } else{
		 * System.out.println("inside employee address "); EmployeeAddressLink
		 * employeeAddressLink = new EmployeeAddressLink();
		 * employeeAddressLink.setAddress_id(addressId);
		 * employeeAddressLink.setEmployee_id(addressMapper.getEmployeeId());
		 * employeeAddressLink.setCreation_date(new Date());
		 * employeeAddressLink.setLive_ind(true);
		 * employeeAddressLinkRepository.save(employeeAddressLink); }
		 */

		return addressInfoId;
	}

	@Override
	public AddressMapper getAddressDetails(String addressId) {

		AddressDetails addressDetails = addressDetailsRepository.getAddressDetailsByAddressId(addressId);

		AddressMapper addressMapper = new AddressMapper();
		if (null != addressDetails) {
			addressMapper.setAddress1(addressDetails.getAddressLine1());
			addressMapper.setAddress2(addressDetails.getAddressLine2());
			addressMapper.setAddressType(addressDetails.getAddressType());
			addressMapper.setCity(addressDetails.getCity());
			addressMapper.setCountry(addressDetails.getCountry());
			addressMapper.setLatitude(addressDetails.getLatitude());
			addressMapper.setLongitude(addressDetails.getLongitude());
			addressMapper.setPostalCode(addressDetails.getPostalCode());
			addressMapper.setTown(addressDetails.getTown());
			addressMapper.setStreet(addressDetails.getStreet());
			addressMapper.setState(addressDetails.getState());
			addressMapper.setAddressId(addressId);
			addressMapper.setHouseNo(addressDetails.getHouseNo());
			addressMapper.setPrimaryInd(addressDetails.isPrimaryInd());
		}

		return addressMapper;
	}

	@Override
	public AddressMapper updateAddressDetails(AddressMapper addressMapper, String addressId) {

		if (null != addressId) {

			AddressDetails addressDetails = addressDetailsRepository.getAddressDetailsByAddressId(addressId);

			if (null != addressDetails) {

				if (null != addressMapper.getAddress1()) {
					addressDetails.setAddressLine1(addressMapper.getAddress1());
				}

				if (null != addressMapper.getAddress2()) {
					addressDetails.setAddressLine2(addressMapper.getAddress2());
				}

				if (null != addressMapper.getAddressType()) {
					addressDetails.setAddressType((addressMapper.getAddressType()));
				}

				if (null != addressMapper.getCity()) {
					addressDetails.setCity(addressMapper.getCity());
				}

				if (null != addressMapper.getCountry()) {
					addressDetails.setCountry(addressMapper.getCountry());
				}

				if (null != addressMapper.getPostalCode()) {
					addressDetails.setPostalCode(addressMapper.getPostalCode());
				}

				if (null != addressMapper.getLatitude()) {
					addressDetails.setLatitude(addressMapper.getLatitude());
				}

				if (null != addressMapper.getLongitude()) {
					addressDetails.setLongitude(addressMapper.getLongitude());
				}

				if (null != addressMapper.getTown()) {
					addressDetails.setTown(addressMapper.getTown());
				}

				if (null != addressMapper.getStreet()) {
					addressDetails.setStreet(addressMapper.getStreet());
				}

				if (null != addressMapper.getState()) {
					addressDetails.setState(addressMapper.getState());
				}
				if (null != addressMapper.getHouseNo()) {
					addressDetails.setHouseNo(addressMapper.getHouseNo());
				}

				addressDetails.setCreatorId(addressMapper.getCreatorId());
				addressDetails.setLiveInd(true);
				addressDetails.setCreationDate(addressDetails.getCreationDate());
				addressDetails.setAddressId(addressId);
				addressDetailsRepository.save(addressDetails);

			}

		}
		AddressMapper resultMapper = getAddressDetails(addressId);

		return resultMapper;
	}

	@Override
	public List<AddressMapper> employeeAddressLink(AddressMapper addressMapper, String employeeId) {

		/* insert to adressInfo table */
		AddressInfo addressInfo = new AddressInfo();
		addressInfo.setCreationDate(new Date());
		addressInfo.setCreatorId(addressMapper.getEmployeeId());
		AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);
		String addressId = addressInfoo.getId();

		if (null != addressId) {
			/* insert to adressDetails table */
			AddressDetails addressDetails = new AddressDetails();
			addressDetails.setAddressId(addressId);
			addressDetails.setAddressLine1(addressMapper.getAddress1());
			addressDetails.setAddressLine2(addressMapper.getAddress2());
			addressDetails.setAddressType(addressMapper.getAddressType());
			addressDetails.setCity(addressMapper.getCity());
			addressDetails.setStreet(addressMapper.getStreet());
			addressDetails.setTown(addressMapper.getTown());
			addressDetails.setState(addressMapper.getState());
			addressDetails.setPostalCode(addressMapper.getPostalCode());
			addressDetails.setCountry(addressMapper.getCountry());
			addressDetails.setLatitude(addressMapper.getLatitude());
			addressDetails.setLongitude(addressMapper.getLongitude());
			addressDetails.setCreationDate(new Date());
			addressDetails.setCreatorId(addressMapper.getEmployeeId());
			addressDetails.setLiveInd(true);
			addressDetails.setHouseNo(addressMapper.getHouseNo());

			AddressDetails adressDetatilss = addressDetailsRepository.save(addressDetails);
			String addressDetaislsId = adressDetatilss.getId();

			/* insert to employee address link table */
			EmployeeAddressLink employeeAddressLink = new EmployeeAddressLink();
			employeeAddressLink.setAddressId(addressId);
			employeeAddressLink.setEmployeeId(employeeId);
			employeeAddressLink.setCreationDate(new Date());
			employeeAddressLink.setLiveInd(true);
			employeeAddressLinkRepository.save(employeeAddressLink);
		}

		List<EmployeeAddressLink> employeeAddressList = employeeAddressLinkRepository.getAddressListByEmpId(employeeId);
		if (null != employeeAddressList && !employeeAddressList.isEmpty()) {

			return employeeAddressList.stream().map(employeeAddressLink -> {

				if (null != employeeAddressLink.getAddressId()) {
					AddressMapper addressMapper1 = getAddressDetails(employeeAddressLink.getAddressId());
					return addressMapper1;
				}
				return null;
			}).collect(Collectors.toList());

		}

		return null;
	}

	@Override
	public List<AddressMapper> employeeContactAddressLink(AddressMapper addressMapper, String personId,
			String employeeId) {

		/* insert to adressInfo table */
		AddressInfo addressInfo = new AddressInfo();
		addressInfo.setCreationDate(new Date());
		addressInfo.setCreatorId(addressMapper.getEmployeeId());
		AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);
		String addressId = addressInfoo.getId();

		if (null != addressId) {
			/* insert to adressDetails table */
			AddressDetails addressDetails = new AddressDetails();
			addressDetails.setAddressId(addressId);
			addressDetails.setAddressLine1(addressMapper.getAddress1());
			addressDetails.setAddressLine2(addressMapper.getAddress2());
			addressDetails.setAddressType(addressMapper.getAddressType());
			addressDetails.setCity(addressMapper.getCity());
			addressDetails.setStreet(addressMapper.getStreet());
			addressDetails.setTown(addressMapper.getTown());
			addressDetails.setState(addressMapper.getState());
			addressDetails.setPostalCode(addressMapper.getPostalCode());
			addressDetails.setCountry(addressMapper.getCountry());
			addressDetails.setLatitude(addressMapper.getLatitude());
			addressDetails.setLongitude(addressMapper.getLongitude());
			addressDetails.setCreationDate(new Date());
			addressDetails.setCreatorId(addressMapper.getEmployeeId());
			addressDetails.setLiveInd(true);
			addressDetails.setHouseNo(addressMapper.getHouseNo());

			AddressDetails adressDetatilss = addressDetailsRepository.save(addressDetails);
			String addressDetaislsId = adressDetatilss.getId();
			/* insert to employee contact address link table */

			EmployeeContactAddressLink employeeContactAddressLink = new EmployeeContactAddressLink();
			employeeContactAddressLink.setContactPersonId(personId);
			employeeContactAddressLink.setContactAddressId(addressId);
			employeeContactAddressLink.setEmployeeId(employeeId);
			employeeContactAddressLink.setCreationDate(new Date());
			employeeContactAddressLink.setLiveInd(true);
			employeeContactAddressRepository.save(employeeContactAddressLink);
		}
		List<EmployeeContactAddressLink> list = employeeContactAddressRepository
				.getEmployeeContactAddressById(employeeId, personId);
		if (null != list && !list.isEmpty()) {
			return list.stream().map(employeeContactAddressLink -> {
				AddressMapper addressMapper1 = getAddressDetails(employeeContactAddressLink.getContactAddressId());
				return addressMapper1;

			}).collect(Collectors.toList());
		}
		return null;

	}

	@Override
	public List<AddressMapper> getCustomerAddressListById(String id) {
		List<AddressMapper> resultMapper = new ArrayList<>();
		List<CustomerAddressLink> customerAddress = customerAddressLinkRepository.getAddressListByCustomerId(id);
		if (null != customerAddress && !customerAddress.isEmpty()) {
			for (CustomerAddressLink customerAddressLink : customerAddress) {
				AddressDetails address = addressDetailsRepository
						.getAddressDetailsByAddressId(customerAddressLink.getAddressId());
				if (address != null) {
					AddressMapper mapper = getAddressDetails(address.getAddressId());
					resultMapper.add(mapper);

				}

			}
		}

		return resultMapper;
	}

	@Override
	public List<AddressMapper> getContactAddressListById(String id) {
		List<AddressMapper> resultMapper = new ArrayList<>();
		List<ContactAddressLink> contactAddress = contactAddressLinkRepository.getAddressListByContactId(id);
		if (null != contactAddress && !contactAddress.isEmpty()) {
			for (ContactAddressLink contactAddressLink : contactAddress) {
				AddressDetails address = addressDetailsRepository
						.getAddressDetailsByAddressId(contactAddressLink.getAddress_id());
				if (address != null) {
					AddressMapper mapper = getAddressDetails(address.getAddressId());
					resultMapper.add(mapper);

				}

			}
		}

		return resultMapper;
	}

	@Override
	public List<AddressMapper> getLeadsAddressListById(String id) {
		List<AddressMapper> resultMapper = new ArrayList<>();
		List<LeadsAddressLink> leadsAddress = leadsAddressLinkRepository.getAddressListByLeadsId(id);
		if (null != leadsAddress && !leadsAddress.isEmpty()) {
			for (LeadsAddressLink leadsAddressLink : leadsAddress) {
				AddressDetails address = addressDetailsRepository
						.getAddressDetailsByAddressId(leadsAddressLink.getAddressId());
				if (address != null) {
					AddressMapper mapper = getAddressDetails(address.getAddressId());
					resultMapper.add(mapper);

				}

			}
		}

		return resultMapper;
	}

	@Override
	public List<AddressMapper> getInvestorAddressListById(String id) {
		List<AddressMapper> resultMapper = new ArrayList<>();
		List<InvestorAddressLink> investorAddressLink = investorAddressLinkRepository.getAddressListByInvestorId(id);
		if (null != investorAddressLink && !investorAddressLink.isEmpty()) {
			for (InvestorAddressLink investorAddress : investorAddressLink) {
				AddressDetails address = addressDetailsRepository
						.getAddressDetailsByAddressId(investorAddress.getAddressId());
				if (address != null) {
					AddressMapper mapper = getAddressDetails(address.getAddressId());
					resultMapper.add(mapper);

				}

			}
		}

		return resultMapper;
	}

	@Override
	public List<AddressMapper> getInvestorLeadsAddressListById(String id) {
		List<AddressMapper> resultMapper = new ArrayList<>();
		List<InvestorLeadsAddressLink> leadsAddress = investorLeadsAddressLinkRepository.getAddressListByInvestorLeadsId(id);
		if (null != leadsAddress && !leadsAddress.isEmpty()) {
			for (InvestorLeadsAddressLink leadsAddressLink : leadsAddress) {
				AddressDetails address = addressDetailsRepository
						.getAddressDetailsByAddressId(leadsAddressLink.getAddressId());
				if (address != null) {
					AddressMapper mapper = getAddressDetails(address.getAddressId());
					resultMapper.add(mapper);

				}

			}
		}

		return resultMapper;
	}

	@Override
	public AddressMapper saveAddressByType(String type, AddressMapper addressMapper,String id) {
		AddressMapper mapper=new AddressMapper();
		if(null!=addressMapper){
			String addressId=saveAddressProcess(addressMapper);
			if(type.equalsIgnoreCase("customer")){
				CustomerAddressLink customerAddress = new CustomerAddressLink();
				customerAddress.setAddressId(addressId);
				customerAddress.setCreationDate(new Date());
				customerAddress.setCustomerId(id);
				customerAddressLinkRepository.save(customerAddress);
				mapper=getAddressDetails(addressId);
			}
			else if (type.equalsIgnoreCase("contact")) {
				ContactAddressLink contactAddressLink = new ContactAddressLink();
				contactAddressLink.setAddress_id(addressId);
				contactAddressLink.setCreation_date(new Date());
				contactAddressLink.setContact_id(id);
				contactAddressLinkRepository.save(contactAddressLink);
				mapper=getAddressDetails(addressId);
			}
			else if (type.equalsIgnoreCase("leads")) {
				LeadsAddressLink leadsAddressLink = new LeadsAddressLink();
				leadsAddressLink.setAddressId(addressId);
				leadsAddressLink.setCreationDate(new Date());
				leadsAddressLink.setLeadsId(id);
				leadsAddressLinkRepository.save(leadsAddressLink);
				mapper=getAddressDetails(addressId);
			}
			else if (type.equalsIgnoreCase("investor")) {
				InvestorAddressLink investorAddressLink = new InvestorAddressLink();
				investorAddressLink.setAddressId(addressId);
				investorAddressLink.setCreationDate(new Date());
				investorAddressLink.setInvestorId(id);
				investorAddressLinkRepository.save(investorAddressLink);
				mapper=getAddressDetails(addressId);
			}
			else if (type.equalsIgnoreCase("investorLeads")) {
				InvestorLeadsAddressLink investorLeadsAddressLink = new InvestorLeadsAddressLink();
				investorLeadsAddressLink.setAddressId(addressId);
				investorLeadsAddressLink.setCreationDate(new Date());
				investorLeadsAddressLink.setInvestorLeadsId(id);
				investorLeadsAddressLinkRepository.save(investorLeadsAddressLink);
				mapper=getAddressDetails(addressId);
			}
		}
        return mapper;
    }

	@Override
	public AddressMapper makePrimary(String addressId, boolean primaryInd) {
		AddressMapper  mapper=new AddressMapper();
		AddressDetails addressDetails = addressDetailsRepository.getAddressDetailsByAddressId(addressId);
		if(null!=addressDetails){
			addressDetails.setPrimaryInd(primaryInd);
			mapper=getAddressDetails(addressDetailsRepository.save(addressDetails).getAddressId());
		}
        return mapper;
    }


}
