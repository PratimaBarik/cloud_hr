package com.app.employeePortal.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "product_sub_attribute")
@NotNull
public class ProductSubAtrributeDetails {

	@Id
//	@GenericGenerator(name = "id", strategy = "com.app.NuboxedErp.generator.ProductSubAtrributeGenerator")
//	@GeneratedValue(generator = "id")
	
	
	@Column(name="product_sub_attribute_id")
	private String id;
	
	@Column(name="sub_attribute_name")
	private String subAttributeName;
	
	

}
