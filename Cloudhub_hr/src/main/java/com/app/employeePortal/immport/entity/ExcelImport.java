package com.app.employeePortal.immport.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "excel_import")
public class ExcelImport {
	
	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.immport.generator.ExcelImportGenerator")
	@GeneratedValue(generator = "id")
	
	@Column(name="excel_import_id")
	private String id;
	
	@Lob
	@Column(name="excel_json")
	private String excelJson;
	
	@Column(name="import_date")
	private Date importDate;
	
	@Column(name="file_extension")
	private String fileExtension;

}
