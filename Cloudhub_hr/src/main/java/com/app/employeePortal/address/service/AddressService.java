package com.app.employeePortal.address.service;

import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;

public interface AddressService {

	public String saveAddressProcess(AddressMapper addressMapper);
	
	public AddressMapper getAddressDetails(String addressId);

	public AddressMapper updateAddressDetails(AddressMapper addressMapper,String addressId);

	public List<AddressMapper>  employeeAddressLink(AddressMapper addressMapper, String employeeId);

	public List<AddressMapper>  employeeContactAddressLink(AddressMapper addressMapper, String personId, String employeeId);

	public List<AddressMapper> getCustomerAddressListById(String id);

	public List<AddressMapper> getContactAddressListById(String id);

	public List<AddressMapper> getLeadsAddressListById(String id);

	public List<AddressMapper> getInvestorAddressListById(String id);

	public List<AddressMapper> getInvestorLeadsAddressListById(String id);

	AddressMapper saveAddressByType(String type, AddressMapper addressMapper,String id);

	AddressMapper makePrimary(String addressId, boolean primaryInd);
}
