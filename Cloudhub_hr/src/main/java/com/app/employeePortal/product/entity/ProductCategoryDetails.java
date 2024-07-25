package com.app.employeePortal.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product_category_details")

public class ProductCategoryDetails {

	@Id
//	@GenericGenerator(name = "id", strategy = "com.app.NuboxedErp.generator.ProductCategoryDetailsGenerator")
//	@GeneratedValue(generator = "id")

	@Column(name = "product_category_details_id")
	private String id;

	@Column(name = "category_name")
	private String categoryName;
	
	@Column(name = "image_id")
	private String imageId;

}
