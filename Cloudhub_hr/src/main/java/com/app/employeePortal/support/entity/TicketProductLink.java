package com.app.employeePortal.support.entity;

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
@Table(name = "ticket_product_link")

public class TicketProductLink {
	@Id
	@GenericGenerator(name = "ticket_product_link_id", strategy = "com.app.employeePortal.support.generator.TicketProductLinkGenerator")
    @GeneratedValue(generator = "ticket_product_link_id")
	
	@Column(name="ticket_product_link_id")
	private String ticketProductLinkId;
	
	@Column(name="ticket_id")
	private String ticketId;
	
	@Column(name="productId")
	private String productId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="creation_date")
	private Date creationDate;
	
}

