package com.app.employeePortal.category.entity;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "machinary_location_cellLink")

public class MachinaryLocationCellLink {
	@Id
	@GenericGenerator(name = "machinary_location_cellLink_id", strategy = "com.app.employeePortal.category.generator.MachinaryLocationCellLinkGenerator")
    @GeneratedValue(generator = "machinary_location_cellLink_id")
	
	@Column(name="machinaryLocationCellLinkId")
	private String machinaryLocationCellLinkId;
	
	@Column(name="machinary_location_link_id")
	private String machinaryLocationLinkId;
	
	@Column(name="cellId")
	private String cellId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="creation_date")
	private Date creationDate;
	

}

