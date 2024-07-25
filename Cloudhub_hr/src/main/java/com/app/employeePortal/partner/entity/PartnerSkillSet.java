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
@Table(name="partner_skill_set")
public class PartnerSkillSet {
	@Id
	@GenericGenerator(name = "skill_set_details_id", strategy = "com.app.employeePortal.partner.generator.PartnerSkillSetGenerator")
	@GeneratedValue(generator = "skill_set_details_id")
	
	@Column(name = "skill_set_details_id")
	private String skillSetDetailsId;
	
	@Column(name = "skill_name")
	private String skillName;
	
	@Column(name="partner_id")
	private String partnerId;

	@Column(name = "creation_date")
	private Date creationDate;

}
