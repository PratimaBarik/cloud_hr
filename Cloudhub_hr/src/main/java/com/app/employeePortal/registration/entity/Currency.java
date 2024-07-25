package com.app.employeePortal.registration.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="currency")
@JsonIgnoreProperties(ignoreUnknown = true)


public class Currency {
	
	@Id
	@Column(name="currency_id")
	private String currency_id;
	
	@Column(name="currency_name")
	private String currencyName;
	
	@Column(name="currency_code")
	private String currency_code;
	
	@Column(name="mandatory_ind", nullable =false)
	private boolean mandatoryInd=false;

	@Column(name = "updation_date")
	private Date updationDate;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="orgId")
	private String orgId;
	
	@Column(name="sales_ind", nullable =false)
	private boolean salesInd=false;
	
	@Column(name="investor_ind", nullable =false)
	private boolean investorInd=false;
}
