package com.app.employeePortal.investor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Entity
@Getter
@Setter
@Table(name="investorOpp_included_link")
public class InvestorOppIncludedLink {
	
	@Id
	@GenericGenerator(name = "investorOpp_included_link_id",strategy="com.app.employeePortal.investor.generator.InvestorOppIncludedLinkGenerator")
	@GeneratedValue(generator = "investorOpp_included_link_id")
	
	@Column(name="investorOpp_included_link_id")
	private String investorOppIncludedLinkId;
	
	@Column(name="investorOpp_id")
	private String investorOppId ;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="live_ind")
	private boolean liveInd;

	
	
	

}
