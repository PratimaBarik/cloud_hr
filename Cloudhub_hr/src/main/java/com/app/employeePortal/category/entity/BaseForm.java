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
@Table(name = "base_form")

public class BaseForm {
	@Id
	@GenericGenerator(name = "base_form_id", strategy = "com.app.employeePortal.category.generator.BaseFormGenerator")
    @GeneratedValue(generator = "base_form_id")
	
	@Column(name="base_form_id")
	private String baseFormId;
	
	@Column(name="form_type")
	private String formType;
	
	@Column(name="base_form_type")
	private String baseFormType;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="updation_date")
	private Date updationDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="name_Ind", nullable =false)
	private boolean nameInd =false;
	
	@Column(name="dail_code_ind", nullable =false)
	private boolean dailCodeInd =false;
	
	@Column(name="phone_no_Ind", nullable =false)
	private boolean phoneNoInd =false;
	
	@Column(name="sector_Ind", nullable =false)
	private boolean sectorInd =false;
	
	@Column(name="source_Ind", nullable =false)
	private boolean sourceInd =false;
	
	@Column(name="note_Ind", nullable =false)
	private boolean noteInd =false;
	
	@Column(name="assigned_to_Ind", nullable =false)
	private boolean assignedToInd =false;
	
	@Column(name="vat_no_Ind", nullable =false)
	private boolean vatNoInd =false;
	
	@Column(name="business_reg_Ind", nullable =false)
	private boolean businessRegInd =false;
	
	@Column(name="address_Ind", nullable =false)
	private boolean addressInd =false;
	
	@Column(name="potential_Ind", nullable =false)
	private boolean potentialInd =false;
	
	@Column(name="potential_currency_Ind", nullable =false)
	private boolean potentialCurrencyInd =false;
	
	@Column(name="type_Ind", nullable =false)
	private boolean typeInd =false;
	
	@Column(name="first_name_Ind", nullable =false)
	private boolean firstNameInd =false;
	
	@Column(name="middle_name_Ind", nullable =false)
	private boolean middleNameInd =false;
	
	@Column(name="last_name_Ind", nullable =false)
	private boolean lastNameInd =false;
	
	@Column(name="image_upload_Ind", nullable =false)
	private boolean imageUploadInd =false;
	
	@Column(name="url_Ind", nullable =false)
	private boolean urlInd =false;
	
	@Column(name="lob_Ind", nullable =false)
	private boolean lobInd =false;
	
	@Column(name="ship_by_Ind", nullable =false)
	private boolean shipByInd =false;
	
	@Column(name="api_Ind", nullable =false)
	private boolean apiInd =false;
	
	@Column(name="approve_Ind", nullable =false)
	private boolean approveInd =false;
	
//	@Column(name="email_Ind", nullable =false)
//	private boolean emailInd =false;
//	
//	@Column(name="email_Ind", nullable =false)
//	private boolean emailInd =false;

}

