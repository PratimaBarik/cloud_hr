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
@Table(name = "product_attribute_details")
public class ProductAttributeDetails {
	
	@Id
//    @GenericGenerator(name = "id", strategy = "com.app.NuboxedErp.generator.ProductAttributeDetailsGenerator")
//    @GeneratedValue(generator = "id")

	@Column(name="product_attribute_id")
	private String id;
	
	@Column(name="attribute_name")
	private String attributeName;
	
	
}
