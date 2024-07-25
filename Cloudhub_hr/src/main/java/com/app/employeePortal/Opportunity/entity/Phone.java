package com.app.employeePortal.Opportunity.entity;

import java.time.LocalDateTime;
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
@Table(name = "phone")
public class Phone extends Auditable {

	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.Opportunity.generator.PhoneGenerator")
    @GeneratedValue(generator = "id")
	
	@Column(name="phone_id")
	private String id;
	
	@Column(name="company")
	private String company;
	
	@Column(name="model")
	private String model;
	
	@Column(name="order_phone_id")
	private String orderPhoneId;
	
	@Column(name="excel_id")
	private String excelId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="distributor_id")
	private String distributorId;
	
	@Column(name="IMEI")
	private String IMEI;

	@Column(name="qcStatus")
	private String qcStatus;
	
	@Column(name="ph_tech_ind")
	private boolean phTechInd;
	
	@Column(name="ph_repair_ind")
	private boolean phRepairInd;
	
	@Column(name="repair_Status")
	private String repairStatus;
	
	@Column(name = "qc_start_time")
    private LocalDateTime qcStartTime;
	
	@Column(name = "qc_end_time")
    private LocalDateTime qcEndTime;
	
	@Column(name = "repair_start_time")
    private LocalDateTime repairStartTime;
	
	@Column(name = "repair_end_time")
    private LocalDateTime repairEndTime;
	
	@Column(name = "total_time")
    private LocalDateTime totalTime;
	
	@Column(name="task1")
	private String task1;
	
	@Column(name="task1_ind")
	private boolean task1Ind;
	
	@Column(name="task1_complete_ind")
	private boolean task1CompleteInd;
	
	@Column(name="task2")
	private String task2;
	
	@Column(name="task2_Ind")
	private boolean task2Ind;
	
	@Column(name="task2_complete_ind")
	private boolean task2CompleteInd;
	
	@Column(name="task3")
	private String task3;
	
	@Column(name="task3_Ind")
	private boolean task3Ind;
	
	@Column(name="task3_complete_ind")
	private boolean task3CompleteInd;
	
	@Column(name="receive_phone_ind")
	private boolean receivePhoneInd;
	
	@Column(name="OS")
	private String OS;
	
	@Column(name="GB")
	private String GB;
	
	@Column(name="color")
	private String color;
	
	@Column(name="conditions")
	private String conditions;
	
	@Column(name="receive_company")
	private String receiveCompany;
	
	@Column(name="receive_model")
	private String receiveModel;
	
	@Column(name="receive_IMEI")
	private String receiveIMEI;
	
	@Column(name="receive_OS")
	private String receiveOS;
	
	@Column(name="receive_GB")
	private String receiveGB;
	
	@Column(name="receive_color")
	private String receiveColor;
	
	@Column(name="receive_condition")
	private String receiveCondition;
	
	@Column(name="mismatch_ind")
	private boolean mismatchInd;
	
	@Column(name="dispatch_phone_ind")
	private boolean dispatchPhoneInd;
	
	@Column(name="dispatch_company")
	private String dispatchCompany;
	
	@Column(name="dispatch_model")
	private String dispatchModel;
	
	@Column(name="dispatch_IMEI")
	private String dispatchIMEI;
	
	@Column(name="dispatch_OS")
	private String dispatchOS;
	
	@Column(name="dispatch_GB")
	private String dispatchGB;
	
	@Column(name="dispatch_color")
	private String dispatchColor;
	
	@Column(name="dispatch_condition")
	private String dispatchCondition;
	
	@Column(name="dispatch_mismatch_ind")
	private boolean dispatchMismatchInd;
	
	@Column(name="receive_phone_user")
	private String receivePhoneUser;

	@Column(name="receive_phone_date")
	private Date receivePhoneDate;
	
	@Column(name="repair_technician_id")
	private String repairTechnicianId;

	@Column(name="dispatch_phone_user")
	private String dispatchPhoneUser;

	@Column(name="dispatch_phone_date")
	private Date dispatchPhoneDate;
	
	@Column(name = "qc_inspection_ind")
	private int qcInspectionInd;
	
	@Column(name = "repair_inspection_ind")
	private int repairInspectionInd;
	
	@Column(name="defination_id")
	private String definationId;
	
	@Column(name="repair_pause_ind",nullable=false)
	private boolean repairPauseInd=false;
	
	@Column(name="repair_resume_ind",nullable=false)
	private boolean repairResumeInd=false;
	
	@Column(name = "repair_pause_time")
    private LocalDateTime repairPauseTime;
	
	@Column(name = "repair_resume_time")
    private LocalDateTime repairResumeTime;
	
	@Column(name="customer_id")
	private String customerId;
	
	@Column(name="opportunity_id")
	private String opportunityId;
	
	@Column(name="active_status")
	private String activeStatus;
	
	@Column(name = "created_at")
    private Date createAt;
	
//	@Column(name = "created_at")
//	private Date createAt;
//	
	
//	@OneToMany(mappedBy = "phone", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<PhoneNotesLink> notes;
	
	/*@OneToOne(mappedBy = "phone", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private QrCode qrCode;*/
}
