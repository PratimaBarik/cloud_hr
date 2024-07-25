package com.app.employeePortal.distributor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.app.employeePortal.attendance.entity.Auditable;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "distributor_contactPerson_link")
public class DistributorContactPersonLink extends Auditable{
	
	@Id
    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.distributor.generator.DistributorContactPersonLinkGenerator")
	@GeneratedValue(generator = "id")

	@Column(name = "distributor_contactPerson_link_id")
    private String id;
    
	@Column(name = "distributor_id")
    private String distributorId;

    @Column(name = "contact_id")
    private String contactId;

}
