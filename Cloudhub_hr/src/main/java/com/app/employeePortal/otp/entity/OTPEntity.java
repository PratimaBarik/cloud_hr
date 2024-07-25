package com.app.employeePortal.otp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otp_verification")
public class OTPEntity {
	
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "otp_id", updatable = false, nullable = false)
	    private Long id;

	    @Column(name = "otp", nullable = false)
	    private Integer otp;

	    @Column(name = "email_id")
	    private String emailId;

	    @Column(name = "phone")
	    private String phone;

	    @Column(name = "validate_ind")
	    private Boolean validateIND;

}
