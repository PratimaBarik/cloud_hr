package com.app.employeePortal.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.app.employeePortal.attendance.entity.Auditable;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cell_chamber_link")
public class CellChamberLink extends Auditable{
	
	@Id
//    @GenericGenerator(name = "id", strategy = "com.app.NuboxedErp.generator.CellChamberLinkGenerator")
//    @GeneratedValue(generator = "id")
	
	@Column(name = "cell_chamber_link_id")
    private String id;

	@Column(name = "location_details_id")
    private String locationDetailsId;
	
	@Column(name = "cell_id")
    private String cellId;
	
	@Column(name = "cell_chamber")
    private String cellChamber;
	
	@Column(name = "userId")
    private String UserId;
	
	@Column(name = "Org_id")
    private String orgId;
	
	@Column(name = "used_ind")
    private boolean usedInd;

}
