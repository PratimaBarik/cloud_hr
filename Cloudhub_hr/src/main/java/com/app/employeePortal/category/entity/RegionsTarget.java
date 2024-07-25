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
@Table(name = "regions_target")

public class RegionsTarget {
	@Id
	@GenericGenerator(name = "regions_target_id", strategy = "com.app.employeePortal.category.generator.RegionsTargetGenerator")
	@GeneratedValue(generator = "regions_target_id")

	@Column(name = "regions_target_id")
	private String regionsTargetId;
	
	@Column(name = "regions_id")
	private String regionsId;

	@Column(name = "sales")
	private double sales;
	
	@Column(name = "year")
	private double year;
	
	@Column(name = "sales_currency")
	private String salesCurrency;
	
	@Column(name = "fulfilment")
	private double fulfilment;
	
	@Column(name = "investment")
	private double investment;
	
	@Column(name = "investment_currency")
	private String investmentCurrency;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "live_ind", nullable = false)
	private boolean liveInd = false;

	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "quarter")
	private String quarter;
	
	@Column(name = "kpi_sales")
	private String kpiSales;
	
	@Column(name = "kpi_fullfillment")
	private String kpiFullfillment;
	
	@Column(name = "kpi_investment")
	private String kpiInvestment;
}
