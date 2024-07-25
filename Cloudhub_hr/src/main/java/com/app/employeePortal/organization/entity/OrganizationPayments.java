package com.app.employeePortal.organization.entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

import com.app.employeePortal.config.AesEncryptor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "organization_payment")
public class OrganizationPayments {

    @Id
    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.organization.generator.OrganizationPaymentGenerator")
    @GeneratedValue(generator = "id")

    @Column(name = "id")
    private String id;

    @Convert(converter = AesEncryptor.class)
    @Column(name = "payment_gateway")
    private String paymentGateway;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;
    
    @Convert(converter = AesEncryptor.class)
    @Column(name = "access_key")
	private String accessKey;
    
    @Convert(converter = AesEncryptor.class)
    @Column(name = "secreate_key")
	private String secreateKey;
    
    @Column(name = "org_id")
	private String orgId;
   }
