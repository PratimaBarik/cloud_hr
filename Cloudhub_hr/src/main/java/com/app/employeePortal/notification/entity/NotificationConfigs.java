package com.app.employeePortal.notification.entity;

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
@Table(name="notification_config")
public class NotificationConfigs {
	
	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.notification.generator.NotificationConfigGenerator")
	@GeneratedValue(generator = "id")
	
	@Column(name="id")
	private String id;
	
	@Column(name="name") 
	private String name;
	
	@Column(name="create_ind", nullable = false)
	private boolean createInd=false;
	
	@Column(name="update_ind", nullable = false)
	private boolean updateInd=false;
	
	@Column(name="delete_ind", nullable = false)
	private boolean deleteInd=false;
	
	@Column(name="reporting_manager")
	private boolean reportingManager;
	
	@Column(name="reporting_manager_1")
	private boolean reportingManager1;
	
	@Column(name="admin")
	private boolean admin;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="updated_date")
	private Date updatedDate;
	
}
