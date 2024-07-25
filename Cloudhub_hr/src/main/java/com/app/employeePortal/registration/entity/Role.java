package com.app.employeePortal.registration.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {
	
	 	@Id
	    @Column(name = "role_id")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer role_id;
	    
	    @Column(name = "role_name")
        private String role_name;
	    
	    @Column(name="department_id")
		private String department_id;

	    @Column(name="edit_ind")
		private boolean editInd;
	    
	    @Column(name = "creation_date")
		private Date creationDate;
		
	    
	    
	    

}
