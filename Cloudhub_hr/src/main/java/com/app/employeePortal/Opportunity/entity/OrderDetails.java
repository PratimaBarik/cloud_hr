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
@Table(name = "order_details_phone")
public class OrderDetails{
	
	@Id
//    @GenericGenerator(name = "id", strategy = "com.app.NuboxedErp.generator.OrderDetailsGenerator")
//    @GeneratedValue(generator = "id")
	
	@Column(name = "order_id")
    private String id;
	
	@Column(name = "availability_date")
	private Date availabilityDate;
	    
	@Column(name = "delivery_date")
	private Date deliveryDate;
	
	@Column(name = "pickup_delivery_date")
	private Date pickupDeliveryDate;
	
	@Column(name = "full_load_truck_ind")
	private boolean fullLoadTruckInd;
	
	@Column(name = "deliver_to_business_ind")
	private boolean deliverToBusinessInd;
	
	@Column(name = "private_ind")
	private boolean privateInd;
	
	@Column(name = "no_of_cars")
	private int noOfCars;
	
	@Column(name = "payment_in_terms")
	private int paymentInTerms;
	
	@Column(name = "comments")
	private String comments;
	
	@Column(name = "offer_price")
	private float offerPrice;
	
	@Column(name = "payable_offer_price")
	private float payableOfferPrice;
	
	@Column(name = "expected_price")
	private float expectedPrice;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "customer_id")
	private String customerId;
	
	@Column(name = "shipper_company_id")
	private String shipperCompanyId;
	
	@Column(name = "new_order_no")
	private String newOrderNo;
	
	@Column(name = "new_no_date")
	private Date newNoDate;
	
	@Column(name = "pickup_to_time")
	private String pickupToTime;
	
	@Column(name = "pickup_from_time")
	private String pickupFromTime;
	
	@Column(name = "delivery_to_time")
	private String deliveryToTime;
	
	@Column(name = "delivery_from_time")
	private String deliveryFromTime;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "distributor_id")
	private String distributorId;
	
	@Column(name = "transfer_ind")
	private int transferInd;
	
	@Column(name = "contact_person_id")
	private String contactPersonId;
	
	@Column(name = "price_confirm_ind")
	private boolean priceConfirmInd;
	
	@Column(name = "customer_price_ind")
	private boolean customerPriceInd;
	
	@Column(name = "qc_status")
	private String qcStatus;
	
	@Column(name = "repair_status")
	private String repairStatus;
	
	@Column(name = "awb_no")
	private String awbNo;
	
	@Column(name = "pickupInd")
	private boolean pickupInd;
	
	@Column(name = "dispatch_awb_no")
	private String dispatchAwbNo;
	
	@Column(name = "picked_ind")
	private boolean pickedInd;
	
	@Column(name = "shipper_id")
	private String shipperId;
	
	@Column(name = "dispatch_date")
	private Date dispatchDate;
	
	@Column(name = "dispatched_by")
	private String dispatchedBy;
	
	@Column(name = "inspection_ind")
	private int inspectionInd;
	
	@Column(name = "start_inspection_user")
	private String startInspectionUser;
	
	@Column(name = "start_inspection_date")
	private Date startInspectionDate;
	
	@Column(name = "stop_inspection_user")
	private String stopInspectionUser;
	
	@Column(name = "stop_inspection_date")
	private Date stopInspectionDate;
	
	@Column(name = "start_dispatch_inspection_user")
	private String startDispatchInspectionUser;
	
	@Column(name = "start_dispatch_inspection_date")
	private Date startDispatchInspectionDate;
	
	@Column(name = "stop_dispatch_inspection_user")
	private String stopDispatchInspectionUser;
	
	@Column(name = "stop_dispatch_inspection_date")
	private Date stopDispatchInspectionDate;
	
	@Column(name = "dispatch_inspection_ind")
	private int dispatchInspectionInd;
	
	@Column(name = "priority")
	private String priority;
	
	@Column(name = "dispatch_received_ind")
	private boolean dispatchReceivedInd;
	
	@Column(name = "dispatch_received_date")
	private Date dispatchReceivedDate;	
	
	@Column(name = "un_loading_user_id")
	private String unloadingUserId;
	
	@Column(name = "order_confirmed_user_id")
	private String orderConfirmedUserId;
	
	@Column(name = "order_confirmed_date")
	private Date orderConfirmedDate;
	
	@Column(name = "qc_repair_user_id")
	private String qcRepairUserId;
	
	@Column(name = "qc_repair_start_date")
	private Date qcRepairStartDate;
	
	@Column(name = "packed_user_id")
	private String packedUserId;
	
	@Column(name = "packed_date")
	private Date packedDate;
	
	@Column(name = "paid_ind")
	private boolean paidInd;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "order_currency_id")
	private String orderCurrencyId;
	
	@Column(name = "opportunityId")
	private String opportunityId;
	
	@Column(name = "opportunityInd",nullable=false)
	private boolean opportunityInd=false;
	
//	@Column(name = "qc_complete_date")
//	private Date qcCompleteDate;
//	
//	@Column(name = "repair_complete_date")
//	private Date repairCompleteDate;
//	
//	@OneToMany(mappedBy = "orderDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<OrderLoadingAddressLink> orderLoadingAddressLink;
//	
//	@OneToMany(mappedBy = "orderDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<OrderUnloadingAddressLink> orderUnloadingAddressLink;
//	
//	@OneToMany(mappedBy = "orderDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<OrderDetailsNoteLink> notes;
}
