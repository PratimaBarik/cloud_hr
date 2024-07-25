package com.app.employeePortal.recruitment.entity;

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
@Table(name = "recruitment_close_rule")
public class RecruitmentCloseRule {

	@Id
	@GenericGenerator(name = "recruitment_close_rule_id", strategy = "com.app.employeePortal.recruitment.generator.RecruitmentCloseRuleGenerator")
	@GeneratedValue(generator = "recruitment_close_rule_id")

	@Column(name = "recruitment_close_rule_id")
	private String recruitmentCloseRuleId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "time_period")
	private int timePeriod;

	@Column(name = "opp_time_period")
	private int oppTimePeriod;

	@Column(name = "order_time_period")
	private int orderTimePeriod;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "inspection_required_ind", nullable = false)
	private boolean inspectionRequiredInd = false;

	@Column(name = "job_aniversary_email_ind", nullable = false)
	private boolean jobAniEmailInd = false;

	@Column(name = "birthday_email_ind", nullable = false)
	private boolean birthdayEmailInd = false;

//        @Column(name="production_ind", nullable = false)
//        private boolean productionInd=false;
//        
//        @Column(name="repair_ind", nullable = false)
//        private boolean repairInd=false;

//	@Column(name = "make_to_ind", nullable = false)
//	private boolean makeToInd = false;

	@Column(name = "independent_ind", nullable = false)
	private boolean independentInd = false;

	@Column(name = "part_no_ind", nullable = false)
	private boolean partNoInd = false;

	@Column(name = "trnsfr_to_erp_qtion_win_ind", nullable = false)
	private boolean trnsfrToErpQtionWinInd = false;

	@Column(name = "trnsfr_evthng_to_erp_ind", nullable = false)
	private boolean trnsfrEvthngToErpInd = false;

	@Column(name = "process_ind", nullable = false)
	private boolean processInd = false;

	@Column(name = "type_ind", nullable = false)
	private boolean typeInd = false;

	@Column(name = "fifo_ind", nullable = false)
	private boolean fifoInd = false;

	@Column(name = "pro_ind", nullable = false)
	private boolean proInd = false;

	@Column(name = "repair_ord_ind", nullable = false)
	private boolean repairOrdInd = false;
	
	@Column(name = "qc_ind", nullable = false)
	private boolean qcInd = false;

	@Column(name = "repair_process_ind", nullable = false)
	private boolean repairProcessInd = false;
}
