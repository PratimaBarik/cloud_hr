package com.app.employeePortal.address.mapper;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({ "address1", "address2", "city", "postalCode", "country","longitude","latitude","houseNo"})
@Getter
@Setter
public class AddressViewMapper {
	
	@JsonProperty("address1")
	private String address1;
	
	@JsonProperty("address2")
	private String address2;
	
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("postalCode")
	private String postalCode;
	
	
	@JsonProperty("state")
	private String state;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("longitude")
	private String longitude ;
	
	@JsonProperty("latitude")
	private String latitude ;

	@JsonProperty("employeeId")
	private String employeeId ;
	
	@JsonProperty("houseNo")
	private String houseNo;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	

	@Override
	public String toString() {
		return "AddressViewMapper [address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", postalCode="
				+ postalCode + ", state=" + state + ", country=" + country + ", longitude=" + longitude + ", latitude="
				+ latitude + ", employeeId=" + employeeId + "]";
	}
	
	
	

}
