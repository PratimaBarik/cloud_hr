package com.app.employeePortal.api.entity;

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
@Table(name="currency_exchange")
public class CurrencyExchange {
	
	@Id
	@GenericGenerator(name = "exchange_id", strategy = "com.app.employeePortal.api.generator.CurrencyExchangeGenerator")
	@GeneratedValue(generator = "exchange_id")
	
	@Column(name="exchange_id")
	private String exchangeId;

	@Column(name="record_date")
	private Date recordDate;
	
	
	
	@Column(name="base")
	private String base;
	
	@Column(name="INR")
	private double INR;
	
	@Column(name="USD")
	private double USD;
	
	@Column(name="GBP")
	private double GBP;
	
	@Column(name="EUR")
	private double EUR;
	
	@Column(name="AUD")
	private double AUD;
	
	@Column(name="CAD")
	private double CAD;
	
	@Column(name="SGD")
	private double SGD;
	
	@Column(name="BDT")
	private double BDT;
	

	

	

	
	
}
