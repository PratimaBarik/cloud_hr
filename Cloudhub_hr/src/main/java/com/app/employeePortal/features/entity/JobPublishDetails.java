package com.app.employeePortal.features.entity;

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
@Table(name = "job_publish_details")
public class JobPublishDetails {

	@Id
	@GenericGenerator(name = "job_publish_details_id", strategy = "com.app.employeePortal.features.generator.JobPublishGenerator")
	@GeneratedValue(generator = "job_publish_details_id")

	@Column(name = "job_publish_details_id")
	private String id;
	
	@Column(name = "org_id")
	private String orgId;
	
	@Column(name = "website")
	private String website;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "live_ind")
	private boolean liveInd;

	@Override
	public String toString() {
		return "JobPublishDetails [id=" + id + ", orgId=" + orgId + ", website=" + website + ", creationDate="
				+ creationDate + ", liveInd=" + liveInd + ", getId()=" + getId() + ", getOrgId()=" + getOrgId()
				+ ", getWebsite()=" + getWebsite() + ", getCreationDate()=" + getCreationDate() + ", isLiveInd()="
				+ isLiveInd() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	

}
