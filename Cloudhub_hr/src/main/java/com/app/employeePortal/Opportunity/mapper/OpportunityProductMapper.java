package com.app.employeePortal.Opportunity.mapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpportunityProductMapper {

	private String opportunityId;

	private String userId;

	private String category;

	private String categoryName;

	private String subCategory;

	private String subCategoryName;

	private String attribute;

	private String attributeName;

	private String subAttribute;

	private String subAttributeName;
	
	private String model;

	private String brand;

	private float unit;

	private String partNumber;

	private String imageId;

	private float inputPrice;

	private float suggestedPrice;
	
}
