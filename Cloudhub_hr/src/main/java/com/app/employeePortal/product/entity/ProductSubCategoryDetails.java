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
@Table(name = "product_subcategory_details")
public class ProductSubCategoryDetails {
	
	@Id
//    @GenericGenerator(name = "id", strategy = "com.app.NuboxedErp.generator.ProductSubCategoryGenerator")
//    @GeneratedValue(generator = "id")

	
	@Column(name="product_sub_category_id")
	private String id;
		
	@Column(name="sub_category_name")
	private String subCategoryName;
	


}
