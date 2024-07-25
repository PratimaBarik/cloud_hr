package com.app.employeePortal.candidate.entity;

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
@Table(name = "candidate_video_link")
public class CandidateVideoLink {

	@Id
	@GenericGenerator(name = "candidate_video_link_id", strategy = "com.app.employeePortal.candidate.generator.CandidateVideoLinkGenerater")
    @GeneratedValue(generator = "candidate_video_link_id")
	
	@Column(name="candidate_video_link_id")
	private String id;
	
	@Column(name="candidate_id")
	private String candidateId;
	
	@Column(name="video_clips_id")
	private String videoClipsId;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="share_ind", nullable =false)
    private boolean shareInd = false;
}
