package com.app.employeePortal.address.controller;

import static com.app.employeePortal.authentication.constants.Constants.TOKEN_PREFIX;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.address.service.AddressService;
import com.app.employeePortal.authentication.config.TokenProvider;

@RestController
@CrossOrigin(maxAge = 3600)
public class AddressController {

	@Autowired
	AddressService addressService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@PostMapping("/api/v1/address")
	public ResponseEntity<?> createAddress(@RequestBody AddressMapper addressMapper,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			String addressId = addressService.saveAddressProcess(addressMapper);

			return new ResponseEntity<>(addressId, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/address/{addressId}")

	public ResponseEntity<?> updateAddressDetails(@RequestBody AddressMapper addressMapper,
			@PathVariable("addressId") String addressId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {

			String authToken = authorization.replace(TOKEN_PREFIX, "");

			addressMapper.setCreatorId(jwtTokenUtil.getUserIdFromToken(authToken));
			AddressMapper addressMappernew = addressService.updateAddressDetails(addressMapper, addressId);

			return new ResponseEntity<AddressMapper>(addressMappernew, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@PostMapping("/api/v1/address/employee/{employeeId}")
	public ResponseEntity<?> linkAddressToEmployee(@RequestBody AddressMapper addressMapper,
			@PathVariable("employeeId") String employeeId, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<AddressMapper> addressList = addressService.employeeAddressLink(addressMapper, employeeId);

			return new ResponseEntity<>(addressList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/address/person/{personId}/{employeeId}")
	public ResponseEntity<?> linkAddressToEmployeeContactPerson(@RequestBody AddressMapper addressMapper,
			@PathVariable("personId") String personId, @PathVariable("employeeId") String employeeId,
			@RequestHeader("Authorization") String authorization, HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<AddressMapper> addressList = addressService.employeeContactAddressLink(addressMapper, personId,
					employeeId);

			return new ResponseEntity<>(addressList, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/api/v1/address/type/{id}/{type}")
	public ResponseEntity<List<?>> getAddressListByIdAndType(@PathVariable("id") String id,
			@PathVariable("type") String type, @RequestHeader("Authorization") String authorization,
			HttpServletRequest request) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			List<AddressMapper> employeeDetailsList = new ArrayList<>();
			if (type.equalsIgnoreCase("customer")) {
				employeeDetailsList = addressService.getCustomerAddressListById(id);
				return new ResponseEntity<>(employeeDetailsList, HttpStatus.OK);
			}
			if (type.equalsIgnoreCase("contact")) {
				employeeDetailsList = addressService.getContactAddressListById(id);
				return new ResponseEntity<>(employeeDetailsList, HttpStatus.OK);
			}
			if (type.equalsIgnoreCase("leads")) {
				employeeDetailsList = addressService.getLeadsAddressListById(id);
				return new ResponseEntity<>(employeeDetailsList, HttpStatus.OK);
			}
			if (type.equalsIgnoreCase("investor")) {
				employeeDetailsList = addressService.getInvestorAddressListById(id);
				return new ResponseEntity<>(employeeDetailsList, HttpStatus.OK);
			}
			if (type.equalsIgnoreCase("investorLeads")) {
				employeeDetailsList = addressService.getInvestorLeadsAddressListById(id);
				return new ResponseEntity<>(employeeDetailsList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/api/v1/saveAddressByType/{type}/{id}")
	public ResponseEntity<?> saveAddressByType(@PathVariable("type") String type,@PathVariable("id") String id,@RequestBody AddressMapper addressMapper,@RequestHeader("Authorization") String authorization) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			AddressMapper mapper=addressService.saveAddressByType(type,addressMapper,id);
			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/api/v1/address/makePrimary/{addressId}/{primaryInd}")
	public ResponseEntity<?> makePrimary(@RequestHeader("Authorization") String authorization,@PathVariable("addressId")String addressId,@PathVariable("primaryInd")boolean primaryInd) {

		if (authorization != null && authorization.startsWith(TOKEN_PREFIX)) {
			AddressMapper mapper=addressService.makePrimary(addressId,primaryInd);
			return new ResponseEntity<>(mapper, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
