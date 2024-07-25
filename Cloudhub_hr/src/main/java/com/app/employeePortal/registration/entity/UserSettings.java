package com.app.employeePortal.registration.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.app.employeePortal.config.AesEncryptor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Entity
@Getter
@Setter
@Table(name="user_settings")
public class UserSettings {


	@Id
	@GenericGenerator(name = "user_settings_id", strategy = "com.app.employeePortal.registration.generator.UserSettingsGenerator")
	@GeneratedValue(generator = "user_settings_id")
	
	
	@Column(name="user_settings_id")
	private String id;
	
	@Column(name="user_id")
	private String userId;
	
	
	@Column(name="user_type")
	private String userType;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="confirm_password")
	private String confirmPassword;
	
	@Column(name="creation_date")
	private Date creationDate;
	

	@Column(name="user_active_ind")
	private boolean userActiveInd;
	
	@Column(name="email_val_ind")
	private boolean emailValInd;
	
	@Column(name="password_active_ind")
	private boolean passwordActiveInd;
	
	@Column(name="device_val_ind")
	private boolean deviceValInd;
	
	@Column(name="live_ind")
	private boolean liveInd;

	//@Column(name="otp")
	//private String otp;

	
}
