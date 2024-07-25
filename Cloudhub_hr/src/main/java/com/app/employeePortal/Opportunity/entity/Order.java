
package com.app.employeePortal.Opportunity.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order_details")
public class Order {

	@Id
//	@GenericGenerator(name = "id", strategy = "com.app.NuboxedErp.generator.OrderGenerator")
//	@GeneratedValue(generator = "id")

	@Column(name = "order_catalog_id")
	private String id;

	@Column(name = "order_id")
    private String orderId;
	
	@Column(name = "parent_order_id")
	private String parentOderId;

	@Column(name = "order_date")
	private Date orderDate;

	@Column(name = "order_status")
	private String orderStatus;

	@Column(name = "order_amount")
	private float orderAmount;

	@Column(name = "completion_ind")
	private boolean completionInd;

	@Column(name = "subscription_type")
	private String subscriptionType;

	@Column(name = "no_of_days")
	private int noOfDays;

	@Column(name = "subscription_start_date")
	private Date subscriptionStartDate;

	@Column(name = "subscription_end_date")
	private Date subscriptionEndDate;

	@Column(name = "renew_ind")
	private boolean renewInd;

	@Column(name = "pause_ind")
	private boolean pauseInd;

	@Column(name = "pause_date")
	private Date pauseDate;

	@Column(name = "pause_no_of_days")
	private int pauseNoOfDays;

	@Column(name = "order_value")
	private float orderValue;

	@Column(name = "longitude")
	private float longitude;

	@Column(name = "latitude")
	private float latitude;

	@Column(name = "timeStamp")
	private String timeStamp;

	@Column(name = "paid_ind")
	private boolean paidInd;

	@Column(name = "payableAmount")
	private float payableAmount;

	@Column(name = "delivery_type")
	private String deliveryType;

	@Column(name = "alter_days")
	private int alterDays;

	@Column(name = "reason")
	private String reason;

	@Column(name = "renew_count")
	private int renewCount;

	@Column(name = "delete_ind", nullable = false)
	private boolean deleteInd = false;

	@Column(name = "distributor_offer_details")
	private String distributorOfferDetails;

	@Column(name = "delivery_unit")
	private String deliveryUnit;

	@Column(name = "delivery_value")
	private float deliveryValue = 0;

	@Column(name = "delivery_charge")
	private float deliveryCharge = 0;

	@Column(name = "delivery_charge_details")
	private String deliveryChargeDetails;

	@Column(name = "delivery_start_date")
	private Date deliveryStartDate;

	@Column(name = "delivery_end_date")
	private Date deliveryEndDate;

	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "opportunityId")
	private String opportunityId;
	
	@Column(name = "opportunityInd")
	private boolean opportunityInd;

}
