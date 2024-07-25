package com.app.employeePortal.version.entity;

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
@Table(name = "version")
public class Version {

	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.version.generator.VersionGenerator")
	@GeneratedValue(generator = "id")

	@Column(name = "version_id")
	private String id;

	@Column(name = "offerId")
	private String offerId;

	@Column(name = "serviceId")
	private String serviceId;

	@Column(name = "version_type")
	private String versionType;

	@Column(name = "explanatory_note")
	private String explanatoryNote;

	@Column(name = "btw", nullable = false)
	private float btw;

	// @Column(name = "currency") // private String currency;

	@Column(name = "offer_date")
	private Date offerDate;

	@Column(name = "salutation")
	private String salutation;

	@Column(name = "comment")
	private String comment;

}
