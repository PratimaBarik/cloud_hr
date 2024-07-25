package com.app.employeePortal.category.entity;

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
@Table(name = "library_type")
public class LibraryType {
	
	@Id
    @GenericGenerator(name = "id", strategy = "com.app.employeePortal.category.generator.LibraryTypeGenerator")
    @GeneratedValue(generator = "id")

	@Column(name="library_type_id")
	private String id;
	
	@Column(name="library_type")
	private String libraryType;
	
	@Column(name = "org_id")
    private String orgId;

	@Column(name = "user_id")
    private String userId;

}
