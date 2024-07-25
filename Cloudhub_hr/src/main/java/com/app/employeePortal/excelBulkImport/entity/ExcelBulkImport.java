package com.app.employeePortal.excelBulkImport.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "excel_bulk_import")

public class ExcelBulkImport {
	
	@Id
    @GenericGenerator(name = "excel_bulk_import_id", strategy = "com.app.employeePortal.excelBulkImport.generator.ExcelBulkImportGenerator")
    @GeneratedValue(generator = "excel_bulk_import_id")

    @Column(name = "excel_bulk_import_id",length =60,nullable = false)
    private String excel_bulk_import_id;

    @Lob
    @Column(name = "excel_json")
    private String excel_json;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "import_date")
    private Date import_date;

    @Column(name = "file_extension",length =60)
    private String file_extension;

}
