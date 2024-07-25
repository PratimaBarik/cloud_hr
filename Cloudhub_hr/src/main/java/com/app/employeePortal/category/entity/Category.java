package com.app.employeePortal.category.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@Table(name = "category")
public class Category {

	@Id
	@GenericGenerator(name = "category_id", strategy = "com.app.employeePortal.category.generator.CategoryGenerator")
	@GeneratedValue(generator = "category_id")

	@Column(name = "category_id")
	private String categoryId;

	@Column(name = "name")
	private String name;

	@Column(name = "orgId")
	private String orgId;

	@Column(name = "userId")
	private String userId;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "liveInd")
	private boolean liveInd;
	
	@Column(name = "updationDate")
	private Date updationDate;
	
	@Column(name = "updatedBy")
	private String updatedBy;
	
}
