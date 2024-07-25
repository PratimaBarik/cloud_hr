package com.app.employeePortal.version.entity;

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
@Table(name = "version_catalog_link")
public class VersionCatalogLink {

	@Id
	@GenericGenerator(name = "version_catalogue_id", strategy = "com.app.employeePortal.version.generator.VersionCatalogLinkGenerator")
	@GeneratedValue(generator = "version_catalogue_id")

	@Column(name = "version_catalogue_id")
	private String versionCatalogueId;

	@Column(name = "offers_id")
	private String offerId;

	@Column(name = "version_id")
	private String versionId;

	@Column(name = "item_name")
	private String itemName;

	@Column(name = "unit", nullable = false)
	private int unit;

	@Column(name = "price", nullable = false)
	private float price;

	@Column(name = "brand")
	private String brand;

	@Column(name = "model")
	private String model;

	@Column(name = "category")
	private String category;

	@Column(name = "sub_category")
	private String subCategory;

	@Column(name = "attribute")
	private String attribute;

	@Column(name = "sub_attribute")
	private String subAttribute;

	@Column(name = "article_no")
	private String articleNo;

	@Column(name = "description", columnDefinition = "LongText")
	private String description;

	@Column(name = "btw", nullable = false)
	private float btw;

}
