package com.app.employeePortal.api.entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.app.employeePortal.attendance.entity.Auditable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payment")
public class Payment extends Auditable {

    @Id
    @GenericGenerator(name = "payment_id", strategy = "com.app.employeePortal.api.generator.PaymentGenerator")
    @GeneratedValue(generator = "payment_id")

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;
    
    @Column(name="stripe_payment_id")
    private String StripePaymentId;
    
    @Column(name="stripe_payment_ind")
    private boolean StripePaymentInd;
    
    @Column(name = "signature")
    private String signature;
    
    @Column(name = "payment_status")
    private String paymentStatus;
    
    @Column(name = "upi_payment_id")
    private String upiPaymentId;

}
