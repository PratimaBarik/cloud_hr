package com.app.employeePortal.organization.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "subscription_price")
public class SubsriptionPrice {

	@Id
	@GenericGenerator(name = "subscription_price_id", strategy = "com.app.employeePortal.organization.generator.SubsriptionPriceGenerator")
	@GeneratedValue(generator = "id")

	@Column(name = "subscription_Price_id")
	private String subscriptionPriceId;

	@Column(name = "subscriptionType")
	private int subscriptionType;

	@Column(name = "price")
	private float price;

}
