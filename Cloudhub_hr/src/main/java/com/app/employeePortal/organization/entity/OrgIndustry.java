package com.app.employeePortal.organization.entity;

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
@Table(name="org_industry")
public class OrgIndustry {
	

	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.organization.generator.IndustryGenerator")
	@GeneratedValue(generator = "id")
	
	@Column(name="id")
	private String id;
	
	@Column(name="type")
	private String type;
	
    @Column(name="update_date")
	private Date updateDate;
    
    @Column(name="org_id")
	private String orgId;
    
    @Column(name="user_id")
	private String userId;
    
    @Column(name = "live_ind", nullable =false)
	private boolean liveInd = false;
}
