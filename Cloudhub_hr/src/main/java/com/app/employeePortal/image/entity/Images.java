package com.app.employeePortal.image.entity;

import java.util.Date;

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
@Table(name="images")
public class Images {
	@Id
	@GenericGenerator(name = "image_id", strategy = "com.app.employeePortal.image.generator.ImageGenerator")
	@GeneratedValue(generator = "image_id")
	
	
	@Column(name="image_id")
	private String image_id;
	
	@Column(name="image_name")
	private String image_name;
	
	@Column(name="image_path")
	private String image_path;
	
//	@Lob
//	@Column(name="image_data")
//	private byte[] image_data;

	@Column(name="image_type")
	private String image_type;
	
	@Column(name="image_size")
	private long image_size;
	
    @Column(name="creation_date")
	private Date creation_date;
	
    @Column(name="live_ind")
	private boolean live_ind;

	

	
    
}
