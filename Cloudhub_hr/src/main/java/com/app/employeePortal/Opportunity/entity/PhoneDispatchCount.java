package com.app.employeePortal.Opportunity.entity;

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
@Table(name = "phone_dispatch_count")
public class PhoneDispatchCount {

	@Id
	@GenericGenerator(name = "id", strategy="com.app.employeePortal.Opportunity.generator.PhoneDispatchCountGenerator")
    @GeneratedValue(generator ="id")
	
	@Column(name = "phone_dispatch_count_id")
    private String id;
	
	@Column(name = "order_phone_id")
    private String orderPhoneId;
	
	@Column(name = "total_quantity")
    private int totalQuantity;
	
	@Column(name = "remaining_quantity")
    private int remainingQuantity;
	
	@Column(name = "receive_remaining_quantity")
    private int receiveRemainingQuantity;
	
	@Column(name = "total_receive_quantity")
    private int totalReceiveQuantity;
	
	@Column(name = "repair_remaining_quantity")
    private int repairRemainingQuantity;
	
	@Column(name = "dispatch_remaining_quantity")
    private int dispatchRemainingQuantity;
	
	@Column(name="opportunity_id")
	private String opportunityId;
}
