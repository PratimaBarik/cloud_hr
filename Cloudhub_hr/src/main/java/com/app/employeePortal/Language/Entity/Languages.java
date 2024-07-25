package com.app.employeePortal.Language.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="languages")
public class Languages {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY )
	    private long languageId;
	    
	    @Column(name="language")
	    private String language;

	    @Column(name="language_code")
	    private String languageCode;
	    
	    @Column(name="active_ind",nullable = false)
	    private boolean activeInd;

	    @Column(name="base_ind",nullable = false)
	    private boolean baseInd;
	    
	    @Column(name="mandatory_ind",nullable = false)
	    private boolean mandatoryInd;

}
