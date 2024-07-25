package com.app.employeePortal.category.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "currency_conversion")
public class CurrencyConversion {
	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.category.generator.CurrencyConversionGenerator")
	@GeneratedValue(generator = "id")

	@Column(name = "currency_conversion_id")
	private String currencyConversionId;
	
	@Column(name = "reporting_currency")
    private String reportingCurrency;
	
	@Column(name = "conversion_currency")
    private String conversionCurrency;
	
	@Column(name = "reporting_factor")
    private float reportingFactor;
	
	@Column(name = "conversion_factor")
    private float conversionFactor;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind", nullable= false)
	private boolean liveInd=false;
	
	@Column(name="creation_date")
	private Date creationDate;

}
