package com.app.employeePortal.partner.entity;

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
@Table(name="partner_info")
public class PartnerInfo {
	
	@Id
	@GenericGenerator(name = "partner_id", strategy = "com.app.employeePortal.partner.generator.PartnerInfoGenerator")
	@GeneratedValue(generator = "partner_id")
	
	@Column(name="partner_id")
	private String partner_id;
	
	@Column(name="creator_id")
	private String creator_id;
	
	@Column(name="creation_date")
	private Date creation_date;
	

}
