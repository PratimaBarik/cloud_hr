package com.app.employeePortal.category.entity;

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

@Entity
@Getter
@Setter
@Table(name = "secrete_key")

public class SecreteKey {
	@Id
	@GenericGenerator(name = "secrete_key_id", strategy = "com.app.employeePortal.category.generator.SecreteKeyGenerator")
	@GeneratedValue(generator = "secrete_key_id")

	@Column(name = "secrete_key_id")
	private String secreteKeyId;

	@Convert(converter = AesEncryptor.class)
	@Column(name = "epiKey")
	private String epiKey;

	@Convert(converter = AesEncryptor.class)
	@Column(name = "secrete_key")
	private String secreteKey;
	
	@Column(name = "type")
	private String type;

	@Column(name = "creation_date")
	private Date creationDate;

}
