package com.app.employeePortal.support.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product  {

	@Id
//	@GenericGenerator(name = "id", strategy = "com.app.NuboxedErp.generator.ProductGenerator")
//	@GeneratedValue(generator = "id")

	@Column(name = "product_id")
	private String id;

	@Column(name = "image_id")
	private String imageId;

	@Column(name = "category")
	private String category;

	@Column(name = "sub_category")
	private String subCategory;

	@Column(name = "name")
	private String name;

	@Column(name = "product_full_name")
	private String productFullName;

	@Column(name = "attribute")
	private String attribute;

	@Column(name = "sub_attribute")
	private String subAttribute;

	@Column(name = "price")
	private float price;

	@Column(name = "price_valid_date")
	private String priceValidDate;

	@Column(name = "max_discount")
	private float maxDiscount;

	@Column(name = "max_discount_valid_date")
	private String maxDiscountValidDate;

	@Column(name = "unit_in_stock")
	private String unitInStock;

	@Column(name = "tax")
	private float tax;

	@Column(name = "description")
	private String description;

	@Column(name = "distributor_max_margin")
	private float distributorMaxMargin;

	@Column(name = "distributor_allowed_margin")
	private float distributorAllowedMargin;

	@Column(name = "margin_type")
	private String marginType;

	@Column(name = "reason")
	private String reason;

	@Column(name = "consumer_max_margin")
	private float consumerMaxMargin;

	@Column(name = "consumer_margin_type")
	private String consumerMarginType;

	@Column(name = "gst_include_ind")
	private boolean gstIncludeInd;

	@Column(name = "distributor_margin_ind")
	private boolean distributorMarginInd;

	@Column(name = "customer_margin_ind")
	private boolean customerMarginInd;

	@Column(name = "consumer_allowed_margin")
	private float consumerAllowedMargin;

	@Column(name = "expire_days")
	private String expireDays;

	@Column(name = "best_before")
	private String bestBefore;

	@Column(name = "alert")
	private String alert;

	@Column(name = "subscription_ind")
	private boolean subscriptionInd;

	@Column(name = "publish_ind")
	private boolean publishInd;
	
	@Column(name = "active")
	private boolean active;

	@Column(name = "article_no")
	private String articleNo;

	@Column(name = "user_id")
	private String userId;
}
