package com.app.employeePortal.category.entity;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "machinary_location_link")

public class MachinaryLocationLink {
	@Id
	@GenericGenerator(name = "machinary_location_link_id", strategy = "com.app.employeePortal.category.generator.MachinaryLocationLinkGenerator")
    @GeneratedValue(generator = "machinary_location_link_id")
	
	@Column(name="machinary_location_link_id")
	private String machinaryLocationLinkId;
	
	@Column(name="machinary_id")
	private String machinaryId;
	
	@Column(name="locationId")
	private String locationId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="machine_code")
	private String machineCode;
	
	@Column(name="cellId")
	private String cellId;
}

